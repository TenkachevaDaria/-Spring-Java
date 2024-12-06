package tenkacheva.work.app.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import tenkacheva.work.app.dtos.CommentDTO;
import tenkacheva.work.app.models.Comment;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {
    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "newsId", source = "news.id")
    CommentDTO commentToCommentDTO(Comment comment);

    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "newsId", source = "news.id")
    List<CommentDTO> commentsToCommentDTOs(List<Comment> comments);
}
