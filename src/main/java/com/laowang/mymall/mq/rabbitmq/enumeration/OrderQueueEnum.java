package com.laowang.mymall.mq.rabbitmq.enumeration;

import lombok.Getter;

/**
 * @program: my-mall
 * @description: 订单模块-消息队列枚举
 * @author: Laowang
 * @create: 2023-06-09 10:25
 */
@Getter
public enum OrderQueueEnum {
    /**
     * 取消订单消息队列
     */
    QUEUE_ORDER_CANCEL("mymall.order.direct","mymall.order.cancel","mymall.order.cancel"),
    /**
     * 取消订单ttl消息队列
     */
    QUEUE_TTL_ORDER_CANCEL("mymall.order.direct.ttl","mymall.order.cancel.ttl","mymall.order.cancel.ttl");
    /**
     * 交换器名称
     */
    private final String exchange;
    /**
     * 队列名称
     */
    private final String name;
    /**
     * 路由键
     */
    private final String routeKey;
    OrderQueueEnum(String exchange, String name, String routeKey){
        this.exchange = exchange;
        this.name = name;
        this.routeKey = routeKey;
    }
}

