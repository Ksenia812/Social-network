package eu.senla.network.domains.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class CredentialsEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    private UUID id;
    @Column
    private String login;
    @Column
    private String password;
    @JoinColumn(name = "user_id",nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private UserEntity user;


}
