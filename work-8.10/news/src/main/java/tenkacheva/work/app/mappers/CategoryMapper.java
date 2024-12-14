package tenkacheva.work.app.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import tenkacheva.work.app.dtos.CategoryDTO;
import tenkacheva.work.app.models.Category;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
    CategoryDTO categoryToCategoryDTO(Category category);
}
