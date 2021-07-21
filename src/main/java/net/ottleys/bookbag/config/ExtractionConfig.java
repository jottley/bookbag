package net.ottleys.bookbag.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.ottleys.bookbag.messaging.ExtractionSender;

@Configuration
public class ExtractionConfig {
    
    /*@Bean
    public Queue extraction() {
        return new Queue("extraction");
    }

    @Bean
    public ExtractionSender sender() {
        return new ExtractionSender();
    }*/
    
}
