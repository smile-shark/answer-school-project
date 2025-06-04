package com.smileShark.user.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class DirectConfiguration {
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("answer-question.direct");
    }
    @Bean
    public Queue directQueue1(){
        return new Queue("direct.queue1");
    }
    @Bean
    public Queue directQueue2(){
        return QueueBuilder.durable("direct.queue2").build();
    }
    @Bean
    public Binding directBinding1(Queue directQueue1,DirectExchange directExchange){
        return BindingBuilder.bind(directQueue1).to(directExchange).with("red");
    }
    @Bean
    public Binding directBinding1Yellow(Queue directQueue1,DirectExchange directExchange){
        return BindingBuilder.bind(directQueue1).to(directExchange).with("yellow");
    }
    @Bean
    public Binding directBinding2(Queue directQueue2,DirectExchange directExchange){
        return BindingBuilder.bind(directQueue2).to(directExchange).with("blue");
    }
}
