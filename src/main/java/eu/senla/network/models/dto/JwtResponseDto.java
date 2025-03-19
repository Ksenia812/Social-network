package eu.senla.network.models.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JwtResponseDto {
    private final String type = "Bearer";
    private String accessToken;
    private String refreshToken;

}
