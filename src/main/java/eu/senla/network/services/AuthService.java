package eu.senla.network.services;

import eu.senla.network.domains.entities.UserEntity;
import eu.senla.network.domains.entities.UserRoleEntity;
import eu.senla.network.exceptions.AuthException;
import eu.senla.network.exceptions.DuplicateUserException;
import eu.senla.network.exceptions.UserNotFoundException;
import eu.senla.network.mapper.UserMapper;
import eu.senla.network.models.dto.JwtAuthentication;
import eu.senla.network.models.dto.JwtRequestDto;
import eu.senla.network.models.dto.JwtResponseDto;
import eu.senla.network.models.dto.SignUpDto;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService userDetailsService;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public JwtResponseDto login(JwtRequestDto requestDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestDto.getLogin(), requestDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = userDetailsService.loadUserByUsername(requestDto.getLogin());
            UserEntity user = (UserEntity) userDetails;
            String accessToken = jwtProvider.generateAccessToken(user);
            String refreshToken = jwtProvider.generateRefreshToken(user);
            refreshStorage.put(user.getLogin(), refreshToken);
            return JwtResponseDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid login or password", e);
        }
    }

    public UserDetails signUp(SignUpDto signUpDto) {
        try {
            userService.getUserByLogin(signUpDto.getLogin());
            throw new DuplicateUserException(String.format("User with login %s already exists", signUpDto.getLogin()));
        } catch (UserNotFoundException e) {
            log.debug(String.format("User with login = %s is not found", signUpDto.getLogin()));
        }
        UserEntity userEntity = userMapper.signUpDtoToUserEntity(signUpDto);
        userService.registerUser(userEntity);
        return User.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .roles(UserRoleEntity.USER.name())
                .build();

    }

    public JwtResponseDto getAccessToken(String refreshToken) {
        if (jwtProvider.isRefreshTokenValid(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String savedRefreshToken = refreshStorage.get(login);
            if (Objects.nonNull(savedRefreshToken) && savedRefreshToken.equals(refreshToken)) {
                final UserEntity user = userService.getUserByLogin(login);
                final String accessToken = jwtProvider.generateAccessToken(user);
                return JwtResponseDto.builder()
                        .refreshToken(null)
                        .accessToken(accessToken)
                        .build();
            }

        }
        return JwtResponseDto.builder()
                .refreshToken(null)
                .accessToken(null)
                .build();

    }

    public JwtResponseDto refresh(String refreshToken) {
        if (jwtProvider.isRefreshTokenValid(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String savedRefreshToken = refreshStorage.get(login);
            if (Objects.nonNull(savedRefreshToken) && savedRefreshToken.equals(refreshToken)) {
                final UserEntity user = userService.getUserByLogin(login);
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                refreshStorage.put(user.getLogin(), newRefreshToken);
                return JwtResponseDto.builder()
                        .refreshToken(newRefreshToken)
                        .accessToken(accessToken)
                        .build();
            }
        }
        throw new AuthException("Jwt token is invalid ");
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

}
