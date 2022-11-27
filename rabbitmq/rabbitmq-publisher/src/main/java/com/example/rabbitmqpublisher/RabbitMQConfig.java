package com.example.rabbitmqpublisher;

import ch.qos.logback.classic.pattern.MessageConverter;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue queue(){
        return new Queue("message_queue");
    }
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange("message_exchange");
    }
@Bean
    public Binding binding(Queue queue,TopicExchange exchange){

        return BindingBuilder.bind(queue).to(exchange).with("message_routingKey");
    }
@Bean
    public Jackson2JsonMessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory){
        RabbitTemplate template=new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
