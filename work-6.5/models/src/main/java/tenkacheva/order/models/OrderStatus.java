package tenkacheva.order.models;

import java.time.Instant;

public record OrderStatus(String status, Instant date) {
}
