package eu.senla.network.models.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JwtRequestDto {
    private String login;
    private String password;
}
