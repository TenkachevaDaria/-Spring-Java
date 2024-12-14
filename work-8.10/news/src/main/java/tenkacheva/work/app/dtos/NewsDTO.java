package tenkacheva.work.app.dtos;

public record NewsDTO(long id, long categoryId, long authorId, String content, int comments) {
}
