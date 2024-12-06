package tenkacheva.work.app.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import tenkacheva.work.app.dtos.ComprehensiveNewsDTO;
import tenkacheva.work.app.dtos.NewsDTO;
import tenkacheva.work.app.models.News;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NewsMapper {
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "comments", expression = "java(news.getComments().size())")
    NewsDTO newsToNewsDTO(News news);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "authorId", source = "author.id")
    ComprehensiveNewsDTO newsToComprehensiveNewsDTO(News news);
}
