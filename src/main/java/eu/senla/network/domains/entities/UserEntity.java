package eu.senla.network.domains.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "user")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(generator = "uuid2")
    private UUID id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String email;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRoleEntity userRole;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
