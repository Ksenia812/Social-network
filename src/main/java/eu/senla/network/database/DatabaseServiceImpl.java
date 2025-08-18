package eu.senla.network.database;

import eu.senla.network.domains.entities.UserEntity;
import eu.senla.network.domains.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DatabaseServiceImpl implements DatabaseService {
    private final UserRepository userRepository;

    @Override
    public Optional<UserEntity> getUserByLogin(String login) {
        return userRepository.findByLogin(login);

    }

    @Override
    public UserEntity saveUserEntity(UserEntity user) {
        return userRepository.save(user);
    }
}
