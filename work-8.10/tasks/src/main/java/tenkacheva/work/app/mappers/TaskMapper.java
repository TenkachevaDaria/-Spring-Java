package tenkacheva.work.app.mappers;

import org.mapstruct.Mapper;
import tenkacheva.work.app.dtos.TaskDTO;
import tenkacheva.work.app.models.Task;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TaskMapper {
    TaskDTO taskToTaskDTO(Task task);
}
