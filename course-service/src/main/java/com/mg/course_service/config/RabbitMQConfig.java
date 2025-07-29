package com.mg.course_service.config;

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

    public static final String ADD_VIDEO_QUEUE = "video-add-queue";
    public static final String DELETE_VIDEO_QUEUE = "video-delete-queue";

    public static final String EXCHANGE_NAME = "media-exchange";

    public static final String ADD_VIDEO_ROUTING_KEY = "video.add";
    public static final String DELETE_VIDEO_ROUTING_KEY = "video.delete";


    @Bean
    public Queue addVideoQueue() {
        return new Queue(ADD_VIDEO_QUEUE, true);
    }

    @Bean
    public Queue deleteVideoQueue() {
        return new Queue(DELETE_VIDEO_QUEUE, true);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding addVideoBinding() {
        return BindingBuilder.bind(addVideoQueue())
                .to(exchange())
                .with(ADD_VIDEO_ROUTING_KEY);
    }

    @Bean
    public Binding deleteVideoBinding() {
        return BindingBuilder.bind(deleteVideoQueue())
                .to(exchange())
                .with(DELETE_VIDEO_ROUTING_KEY);
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
