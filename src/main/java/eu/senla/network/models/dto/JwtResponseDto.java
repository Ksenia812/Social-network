package eu.senla.network.models.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JwtResponseDto {
    private static final String TYPE = "Bearer";
    private String accessToken;
    private String refreshToken;

}
