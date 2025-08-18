package eu.senla.network.database;

import eu.senla.network.domains.entities.UserEntity;

import java.util.Optional;

public interface DatabaseService {
    Optional<UserEntity> getUserByLogin(String login);
    UserEntity saveUserEntity(UserEntity user);
}
