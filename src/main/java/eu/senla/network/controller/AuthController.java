package eu.senla.network.controller;

import eu.senla.network.models.dto.JwtRequestDto;
import eu.senla.network.models.dto.JwtResponseDto;
import eu.senla.network.models.dto.RefreshJwtRequestDto;
import eu.senla.network.models.dto.SignUpDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication", description = "API for user authentication and token management")
@RequestMapping("/api/v1/auth")
public interface AuthController {

    @Operation(summary = "User login", description = "Authenticate user and return JWT token")
    @ApiResponse(responseCode = "200", description = "Successful login")
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<JwtResponseDto> signIn(@RequestBody JwtRequestDto authRequest);

    @Operation(summary = "Get new access token", description = "Refresh access token using refresh token")
    @ApiResponse(responseCode = "200", description = "New access token returned")
    @PostMapping("/token")
    ResponseEntity<JwtResponseDto> getNewAccessToken(@RequestBody RefreshJwtRequestDto request);

    @Operation(summary = "Refresh refresh token", description = "Get new refresh token using existing one")
    @ApiResponse(responseCode = "200", description = "New refresh token returned")
    @PostMapping("/token/refresh")
    ResponseEntity<JwtResponseDto> getNewRefreshToken(@RequestBody RefreshJwtRequestDto request);

    @Operation(summary = "User registration", description = "Register new user")
    @ApiResponse(responseCode = "201", description = "User registered successfully")
    @PostMapping("/register")
    ResponseEntity<Object> signUpUser(@RequestBody SignUpDto signUpDto);
}