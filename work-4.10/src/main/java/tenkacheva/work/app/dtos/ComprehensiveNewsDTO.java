package tenkacheva.work.app.dtos;

import java.util.List;

public record ComprehensiveNewsDTO(long id, long categoryId, long authorId, String content, List<CommentDTO> comments) {
}
