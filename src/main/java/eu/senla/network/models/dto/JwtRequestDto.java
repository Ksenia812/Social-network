package eu.senla.network.models.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class JwtRequestDto {
    private String login;
    private String password;
}
