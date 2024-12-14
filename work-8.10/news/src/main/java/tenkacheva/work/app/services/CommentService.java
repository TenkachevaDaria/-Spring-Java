package tenkacheva.work.app.services;

import tenkacheva.work.app.dtos.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO get(long id);

    long add(long authorId, long newsId, String content);

    List<CommentDTO> getAll(long newsId);

    void update(long id, long authorId, long newsId, String content);

    boolean delete(long id);
}
