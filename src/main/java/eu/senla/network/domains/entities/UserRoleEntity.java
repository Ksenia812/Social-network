package eu.senla.network.domains.entities;

public enum UserRoleEntity {
    ADMIN("admin"),
    USER("user");

    private final String role;

    UserRoleEntity(String role) {
        this.role = role;
    }

    public String getValue() {
        return role;
    }
}
