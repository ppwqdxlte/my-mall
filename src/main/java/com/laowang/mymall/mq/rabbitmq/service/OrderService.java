package com.laowang.mymall.mq.rabbitmq.service;

import com.laowang.mymall.mq.rabbitmq.entity.PortalOrder;
import com.laowang.mymall.mq.rabbitmq.entity.PortalOrderParam;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: my-mall
 * @description: 订单模块-消息处理service
 * @author: Laowang
 * @create: 2023-06-09 11:57
 */
public interface OrderService {
    /**
     * 根据提交信息生成订单
     * @param orderParam 订单参数对象-包含订单信息
     * @return 前台订单
     */
    @Transactional
    PortalOrder generateOrder(PortalOrderParam orderParam);

    /**
     * 取消对应订单号的订单
     * @param orderId 订单ID
     * @return 1-取消成功，0-取消失败
     */
    @Transactional
    int cancelOrder(Long orderId);
}
