package eu.senla.network.database;

import eu.senla.network.domains.entities.UserEntity;
import eu.senla.network.domains.repositories.UserRepository;
import eu.senla.network.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DatabaseServiceImpl implements DatabaseService{
    private final UserRepository userRepository;
    @Override
    public UserEntity getUserByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(()-> new UserNotFoundException(String.format("User with login %s is not found", login)));
    }
}
