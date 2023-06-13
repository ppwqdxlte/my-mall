package com.laowang.mymall.mq.rabbitmq.service.impl;

import com.laowang.mymall.mq.rabbitmq.entity.PortalOrder;
import com.laowang.mymall.mq.rabbitmq.entity.PortalOrderParam;
import com.laowang.mymall.mq.rabbitmq.sender.OrderSender;
import com.laowang.mymall.mq.rabbitmq.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: my-mall
 * @description: 订单模块-消息处理service实现类
 * @author: Laowang
 * @create: 2023-06-09 12:07
 */
@Service
public class OrderServiceImpl implements OrderService {
    private final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);
    /**
     * 订单消息发送者
     */
    @Autowired
    private OrderSender orderSender;
    @Override
    public PortalOrder generateOrder(PortalOrderParam orderParam) {
        // TODO 执行一系类下单操作，具体参考mall项目
        LOGGER.info("process generateOrder");
        // TODO 下单完成后开启一个延迟消息，用于当用户没有付款时取消订单（orderId应该在下单后生成）
        sendDelayMessageCancelOrder(11L); // 暂时随便给一个订单号，以后再完善代码
        return new PortalOrder(); // 随便发一个非空对象应付一下
    }

    @Override
    public int cancelOrder(Long orderId) {
        // TODO 执行一系类取消订单操作，具体参考mall项目
        LOGGER.info("process cancelOrder orderId:{}",orderId);
        return 1;
    }

    private void sendDelayMessageCancelOrder(Long orderId){
        long delayTimes = 2 * 60 * 1000;// 2分钟对应的毫秒，2分钟不付款就取消订单
        // 发送延迟消息
        orderSender.sendOrderCancelTtlMessage(orderId,delayTimes);
    }
}
