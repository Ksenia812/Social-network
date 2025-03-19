package eu.senla.network.services;

import eu.senla.network.database.DatabaseService;
import eu.senla.network.domains.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final DatabaseService databaseService;

    @Override
    public UserEntity getUserByLogin(String login) {
        return databaseService.getUserByLogin(login);
    }
}
