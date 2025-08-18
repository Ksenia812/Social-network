package eu.senla.network.controller;

import eu.senla.network.exceptions.AuthException;
import eu.senla.network.exceptions.DuplicateUserException;
import eu.senla.network.models.dto.JwtRequestDto;
import eu.senla.network.models.dto.JwtResponseDto;
import eu.senla.network.models.dto.RefreshJwtRequestDto;
import eu.senla.network.models.dto.SignUpDto;
import eu.senla.network.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping(
            path = "/signIn", produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<JwtResponseDto> signIn(@RequestBody JwtRequestDto authRequest) {
        log.debug("Login request received: {}", authRequest);
        final JwtResponseDto token = authService.login(authRequest);
        log.debug("Token generated: {}", token);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/accessToken")
    public ResponseEntity<JwtResponseDto> getNewAccessToken(@RequestBody RefreshJwtRequestDto request) {
        try {
            JwtResponseDto jwtResponse = authService.getAccessToken(request.refreshToken);
            return ResponseEntity.ok(jwtResponse);
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponseDto> getNewRefreshToken(@RequestBody RefreshJwtRequestDto request) {
        try {
            JwtResponseDto jwtResponse = authService.refresh(request.refreshToken);
            return ResponseEntity.ok(jwtResponse);
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/signUp")
    public ResponseEntity<Object> signUpUser(@RequestBody SignUpDto signUpDto) {
        try {
            UserDetails userDetails = authService.signUp(signUpDto);
            return new ResponseEntity<>(String.format("User with login = %s registered successfully",
                    userDetails.getUsername()), HttpStatus.CREATED);

        } catch (DuplicateUserException e) {
            return new ResponseEntity<>("Registration failed: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Registration failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

