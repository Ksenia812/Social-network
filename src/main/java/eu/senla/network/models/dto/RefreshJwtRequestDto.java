package eu.senla.network.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshJwtRequestDto {
    public String refreshToken;
}
