package tenkacheva.work.app.dtos;

import java.io.Serializable;

public record BookDTO(long id, String title, String author, String category) implements Serializable {
}
