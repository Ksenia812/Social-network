package eu.senla.network.controller;

import eu.senla.network.models.dto.JwtRequestDto;
import eu.senla.network.models.dto.JwtResponseDto;
import eu.senla.network.models.dto.RefreshJwtRequestDto;
import eu.senla.network.models.dto.SignUpDto;
import eu.senla.network.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    public ResponseEntity<JwtResponseDto> signIn(@RequestBody JwtRequestDto authRequest) {
        log.debug("Login request received: {}", authRequest);
        final JwtResponseDto token = authService.login(authRequest);
        log.debug("Token generated: {}", token);
        return ResponseEntity.ok(token);
    }

    @Override
    public ResponseEntity<JwtResponseDto> getNewAccessToken(@RequestBody RefreshJwtRequestDto request) {
        JwtResponseDto jwtResponse = authService.getAccessToken(request.refreshToken());
        return ResponseEntity.ok(jwtResponse);
    }

    @Override
    public ResponseEntity<JwtResponseDto> getNewRefreshToken(@RequestBody RefreshJwtRequestDto request) {
        JwtResponseDto jwtResponse = authService.refresh(request.refreshToken());
        return ResponseEntity.ok(jwtResponse);
    }

    @Override
    public ResponseEntity<Object> signUpUser(@RequestBody SignUpDto signUpDto) {
        UserDetails userDetails = authService.signUp(signUpDto);
        return new ResponseEntity<>(String.format("User with login = %s registered successfully",
                userDetails.getUsername()), HttpStatus.CREATED);
    }
}

