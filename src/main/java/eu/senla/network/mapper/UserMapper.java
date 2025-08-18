package eu.senla.network.mapper;

import eu.senla.network.domains.entities.UserEntity;
import eu.senla.network.domains.entities.UserRoleEntity;
import eu.senla.network.models.dto.SignUpDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userRoles", expression = "java(getDefaultRoles())")
    @Mapping(target = "password", expression = "java(encodePassword(dto.getPassword()))")
    UserEntity signUpDtoToUserEntity(SignUpDto dto);

    default String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
    default Set<UserRoleEntity> getDefaultRoles() {
        return Collections.singleton(UserRoleEntity.USER);
    }
}
