package com.laowang.mymall.mq.rabbitmq.config;

import com.laowang.mymall.mq.rabbitmq.enumeration.OrderQueueEnum;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: my-mall
 * @description: 订单模块-amqp-消息队列配置
 * @author: Laowang
 * @create: 2023-06-09 10:42
 */
@Configuration
public class OrderAmqpConfig {
    /**
     * @notation: mymall.order.direct（取消订单消息队列所绑定的交换机）:绑定的队列为mymall.order.cancel，一旦有消息以mymall.order.cancel为路由键发过来，会发送到此队列。
     * @return 取消订单-实际消费消息队列-绑定的direct交换机
     */
    @Bean(name = "orderCancelDirectExchange")
    public DirectExchange orderCancelDirectExchange(){
        return ExchangeBuilder
                .directExchange(OrderQueueEnum.QUEUE_ORDER_CANCEL.getExchange())
                .durable(true)
                .build();
    }

    /**
     * @notation: mymall.order.direct.ttl（订单延迟消息队列所绑定的交换机）:绑定的队列为mymall.order.cancel.ttl，一旦有消息以mymall.order.cancel.ttl为路由键发送过来，会转发到此队列，并在此队列保存一定时间，等到超时后会自动将消息发送到mymall.order.cancel（取消订单消息消费队列）。
     * @return 取消订单-延迟消息队列-绑定的direct交换机
     */
    @Bean(name = "orderCancelTtlDirectExchange")
    public DirectExchange orderCancelTtlDirectExchange(){
        return ExchangeBuilder
                .directExchange(OrderQueueEnum.QUEUE_TTL_ORDER_CANCEL.getExchange())
                .durable(true)
                .build();
    }

    /**
     * @return 订单取消-实际消费-消息队列
     */
    @Bean(name = "orderCancelQueue")
    public Queue orderCancelQueue(){
        return new Queue(OrderQueueEnum.QUEUE_ORDER_CANCEL.getName());
    }

    /**
     * @return 取消订单-延迟消息-死信队列
     */
    @Bean(name = "orderCancelTtlQueue")
    public Queue orderCancelTtlQueue(){
        return QueueBuilder
                .durable(OrderQueueEnum.QUEUE_TTL_ORDER_CANCEL.getName())
                .withArgument("x-dead-letter-exchange", OrderQueueEnum.QUEUE_ORDER_CANCEL.getExchange())      //到期后转发的交换器
                .withArgument("x-dead-letter-routing-key", OrderQueueEnum.QUEUE_ORDER_CANCEL.getRouteKey())   //到期后转发的路由键
                .build();
    }

    /**
     * 订单取消-消费队列-绑定
     * @param orderCancelDirectExchange 交换器
     * @param orderCancelQueue 消费队列
     * @return 靠路由键将消费队列绑定到交换器
     */
    @Bean(name = "orderCancelBinding")
    public Binding orderCancelBinding(DirectExchange orderCancelDirectExchange,Queue orderCancelQueue){
        return BindingBuilder
                .bind(orderCancelQueue)
                .to(orderCancelDirectExchange)
                .with(OrderQueueEnum.QUEUE_ORDER_CANCEL.getRouteKey());
    }

    /**
     * 订单取消-延迟队列-绑定
     * @param orderCancelTtlDirectExchange 交换器
     * @param orderCancelTtlQueue 延迟队列
     * @return 靠路由键将延迟队列绑定到交换器
     */
    @Bean(name = "orderCancelTtlBinding")
    public Binding orderCancelTtlBinding(DirectExchange orderCancelTtlDirectExchange,Queue orderCancelTtlQueue){
        return BindingBuilder
                .bind(orderCancelTtlQueue)
                .to(orderCancelTtlDirectExchange)
                .with(OrderQueueEnum.QUEUE_TTL_ORDER_CANCEL.getRouteKey());
    }
}
