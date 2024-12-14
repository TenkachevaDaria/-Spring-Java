package tenkacheva.work.app.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import tenkacheva.work.app.dtos.PersonDTO;
import tenkacheva.work.app.models.Person;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PersonMapper {
    PersonDTO personToPersonDTO(Person person);
}
