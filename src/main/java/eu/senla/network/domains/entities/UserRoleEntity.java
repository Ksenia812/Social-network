package eu.senla.network.domains.entities;

import org.springframework.security.core.GrantedAuthority;

public enum UserRoleEntity implements GrantedAuthority {
    ADMIN("admin"),
    USER("user");

    private final String role;

    UserRoleEntity(String role) {
        this.role = role;
    }


    @Override
    public String getAuthority() {
        return role;
    }
}
