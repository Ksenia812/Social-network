package eu.senla.network.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
public class ErrorResponseDto {
    @NotBlank
    private String error;

    @NotBlank
    private String message;

}
