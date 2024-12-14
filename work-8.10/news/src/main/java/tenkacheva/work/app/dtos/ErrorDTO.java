package tenkacheva.work.app.dtos;

import org.springframework.http.HttpStatus;

public record ErrorDTO(String error, HttpStatus statusCode) {
}
