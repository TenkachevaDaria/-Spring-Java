package tenkacheva.work.app.mappers;

import org.mapstruct.Mapper;
import tenkacheva.work.app.dtos.UserDTO;
import tenkacheva.work.app.models.User;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper {
    UserDTO userToUserDTO(User user);
}
