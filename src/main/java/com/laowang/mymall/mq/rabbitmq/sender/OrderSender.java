package com.laowang.mymall.mq.rabbitmq.sender;

import com.laowang.mymall.mq.rabbitmq.enumeration.OrderQueueEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: my-mall
 * @description: 订单模块-消息发送者
 * @notation: 这里发送【延迟消息】，通过延迟路由键转发到延迟交换器
 * @author: Laowang
 * @create: 2023-06-09 11:27
 */
@Component
public class OrderSender {
    private final Logger LOGGER = LoggerFactory.getLogger(OrderSender.class);
    /**
     * amqp-消息队列模板
     */
    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 给延迟队列发送取消订单的消息
     * @param orderId 订单ID
     * @param delayTimes 延迟毫秒值
     */
    public void sendOrderCancelTtlMessage(Long orderId,final Long delayTimes){
        amqpTemplate.convertAndSend(
                OrderQueueEnum.QUEUE_TTL_ORDER_CANCEL.getExchange(),
                OrderQueueEnum.QUEUE_TTL_ORDER_CANCEL.getRouteKey(),
                orderId,
                message -> {
                    // 设置延迟毫秒值
                    message.getMessageProperties().setExpiration(String.valueOf(delayTimes));
                    return message;
                });
        LOGGER.info("Send delayed order-cancel message, orderId:{}",orderId);
    }
}
