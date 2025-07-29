package com.mg.identity_service.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String ADD_PHOTO_QUEUE = "photo-add-queue";
    public static final String DELETE_PHOTO_QUEUE = "photo-delete-queue";

    public static final String EXCHANGE_NAME = "media-exchange";

    public static final String ADD_PHOTO_ROUTING_KEY = "photo.add";
    public static final String DELETE_PHOTO_ROUTING_KEY = "photo.delete";

    @Bean
    public Queue addPhotoQueue() {
        return new Queue(ADD_PHOTO_QUEUE, true);
    }

    @Bean
    public Queue deletePhotoQueue() {
        return new Queue(DELETE_PHOTO_QUEUE, true);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding addPhotoBinding() {
        return BindingBuilder.bind(addPhotoQueue())
                .to(exchange())
                .with(ADD_PHOTO_ROUTING_KEY);
    }

    @Bean
    public Binding deletePhotoBinding() {
        return BindingBuilder.bind(deletePhotoQueue())
                .to(exchange())
                .with(DELETE_PHOTO_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }


}
