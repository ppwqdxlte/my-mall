package com.laowang.mymall.mq.rabbitmq.receiver;

import com.laowang.mymall.mq.rabbitmq.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: my-mall
 * @description: 订单模块-消息接收者
 * @notation: 这里处理【实际消费队列】接收【延迟队列】发送来的消息
 * @author: Laowang
 * @create: 2023-06-09 11:40
 */
@Component
@RabbitListener(queues = "mymall.order.cancel")
public class OrderReceiver {
    private final Logger LOGGER = LoggerFactory.getLogger(OrderReceiver.class);
    @Autowired
    private OrderService orderService;
    @RabbitHandler
    public void cancelOrder(Long orderId){
        orderService.cancelOrder(orderId);
        LOGGER.info("Received delayed order-cancel message,and cancel order of orderId:{}",orderId);
    }
}
