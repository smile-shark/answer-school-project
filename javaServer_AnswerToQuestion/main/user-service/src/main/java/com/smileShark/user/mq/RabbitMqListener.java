package com.smileShark.user.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class RabbitMqListener {
    @RabbitListener(queues = "simple.queue")
    public void listenSimple(String msg){
        log.info("Received message: {}",msg);
    }
    @RabbitListener(queues = "work.queue")
    public void listenWork1(String msg){
        System.out.println("消费者1收到消息："+msg+","+ LocalDateTime.now());
    }
    @RabbitListener(queues = "work.queue")
    public void listenWork2(String msg){
        System.err.println("消费者2收到消息："+msg+","+ LocalDateTime.now());
    }
    @RabbitListener(queues = "fanout.queue1")
    public void listenFanout1(String msg){
        System.out.println("消费者1收到消息："+msg+","+ LocalDateTime.now());
    }
    @RabbitListener(queues = "fanout.queue2")
    public void listenFanout2(String msg){
        System.err.println("消费者2收到消息："+msg+","+ LocalDateTime.now());
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name="direct.queue1",declare = "true"),
            exchange = @Exchange(name = "answer-question.direct",type = ExchangeTypes.DIRECT),
            key = {"red","yellow"}
    ))
    public void listenDirect1(String msg){
        System.out.println("消费者1收到消息："+msg+","+ LocalDateTime.now());
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name="direct.queue2",declare = "true"),
            exchange = @Exchange(name = "answer-question.direct",type = ExchangeTypes.DIRECT),
            key = {"blue","yellow"}
    ))
    public void listenDirect2(String msg){
        System.err.println("消费者2收到消息："+msg+","+ LocalDateTime.now());
    }
    @RabbitListener(queues = "topic.queue1")
    public void listenTopic1(String msg){
        System.out.println();
        System.out.println("消费者1收到消息："+msg+","+ LocalDateTime.now());
    }
    @RabbitListener(queues = "topic.queue2")
    public void listenTopic2(String msg){
        System.err.println("消费者2收到消息："+msg+","+ LocalDateTime.now());
    }
}
