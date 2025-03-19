package eu.senla.network.database;

import eu.senla.network.domains.entities.UserEntity;

import java.util.Optional;

public interface DatabaseService {
    UserEntity getUserByLogin(String login);
}
