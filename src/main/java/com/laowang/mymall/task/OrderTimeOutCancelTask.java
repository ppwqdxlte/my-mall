package com.laowang.mymall.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @program: my-mall
 * @description: 订单超时取消并解锁库存的定时器
 * @author: Laowang
 * @create: 2023-05-15 16:00
 */
@Component
public class OrderTimeOutCancelTask {

    private final Logger LOGGER = LoggerFactory.getLogger(OrderTimeOutCancelTask.class);

    /**
     * cron表达式语法：Seconds Minutes Hours DayofMonth Month DayofWeek [year]
     * 每10分钟扫描一次，扫描设定超时时间之前下的订单，如果没支付则取消该订单
     */
    @Scheduled(cron = "0 0/10 * ? * ?")
    private void cancelTimeOutOrder(){
        // TODO: 此处应调用取消订单的方法，具体查看mall项目源码
        LOGGER.info("订单取消，并根据sku编号释放锁定库存");
    }
}
