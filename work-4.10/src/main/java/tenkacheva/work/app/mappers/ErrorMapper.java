package tenkacheva.work.app.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import tenkacheva.work.app.dtos.ErrorDTO;
import tenkacheva.work.app.exceptions.ControllerException;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ErrorMapper {
    @Mapping(target = "error", source = "exception.message")
    ErrorDTO controllerExceptionToErrorDTO(ControllerException exception);
}
