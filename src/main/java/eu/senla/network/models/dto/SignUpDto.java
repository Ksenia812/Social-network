package eu.senla.network.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SignUpDto {
    private String name;
    private String surname;
    private String email;
    private String login;
    private String password;
    private LocalDate birthDate;
}
