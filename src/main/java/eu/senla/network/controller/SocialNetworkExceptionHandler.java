package eu.senla.network.controller;

import eu.senla.network.exceptions.UnauthorizedException;
import eu.senla.network.models.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

@Slf4j
@ControllerAdvice(assignableTypes = {AuthController.class})
public class SocialNetworkExceptionHandler {


    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponseDto> handleException(UnauthorizedException e) {
        String code = "REFRESH_TOKEN_ERROR";
        logException(e, code);
        ErrorResponseDto errorResponseDto = prepareErrorResponseBody(code, e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponseDto);
    }

    private void logException(Exception e, String errorCode) {
        log.error("{}: code = [{}], message = [{}]", e.getClass().getSimpleName(), errorCode, e.getMessage(), e);
    }

    private ErrorResponseDto prepareErrorResponseBody(String code, String message) {
        return new ErrorResponseDto(code, message);
    }
}
