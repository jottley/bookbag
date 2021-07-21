package net.ottleys.bookbag.messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import net.ottleys.bookbag.model.Content;

public class ExtractionSender {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue queue;

    public void send(Content content) {
        this.template.convertAndSend(queue.getName(), content);
    }
    
}
