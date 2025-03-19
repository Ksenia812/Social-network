package eu.senla.network.services;

import eu.senla.network.domains.entities.UserEntity;

public interface UserService {
    UserEntity getUserByLogin(String login);

}
