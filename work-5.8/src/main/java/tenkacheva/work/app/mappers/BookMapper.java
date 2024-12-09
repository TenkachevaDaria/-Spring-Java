package tenkacheva.work.app.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tenkacheva.work.app.dtos.BookDTO;
import tenkacheva.work.app.models.Book;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BookMapper {
    @Mapping(target = "category", source = "category.name")
    BookDTO bookToBookDTO(Book book);
}
