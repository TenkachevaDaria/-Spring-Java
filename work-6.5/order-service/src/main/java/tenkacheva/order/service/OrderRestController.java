package tenkacheva.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tenkacheva.order.models.Order;
import tenkacheva.order.models.OrderEvent;

@RestController
@RequestMapping("/order")
public class OrderRestController {

    @Autowired
    private KafkaTemplate<String, Object> jsonKafkaTemplate;

    @PostMapping
    public void add(Order order) {
        var event = new OrderEvent(order.product(), order.quantity());
        jsonKafkaTemplate.send("order-topic", event);
    }
}
