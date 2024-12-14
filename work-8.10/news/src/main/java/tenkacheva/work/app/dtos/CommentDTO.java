package tenkacheva.work.app.dtos;

public record CommentDTO(long id, long authorId, long newsId, String content) {
}
