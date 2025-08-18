package eu.senla.network.services;

import eu.senla.network.database.DatabaseService;
import eu.senla.network.domains.entities.UserEntity;
import eu.senla.network.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final DatabaseService databaseService;

    @Override
    public UserEntity getUserByLogin(String login) {
        return databaseService.getUserByLogin(login)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with login %s is not found", login)));
    }

    @Override
    public UUID registerUser(UserEntity user) {
        return databaseService.saveUserEntity(user).getId();
    }
}
