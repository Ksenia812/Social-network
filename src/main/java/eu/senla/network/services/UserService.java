package eu.senla.network.services;

import eu.senla.network.domains.entities.UserEntity;

import java.util.UUID;

public interface UserService {
    UserEntity getUserByLogin(String login);

    UUID registerUser(UserEntity user);

}
