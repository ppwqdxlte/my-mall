package com.laowang.mymall.mq.rabbitmq.controller;

import com.laowang.mymall.common.api.CommonResult;
import com.laowang.mymall.mq.rabbitmq.entity.PortalOrder;
import com.laowang.mymall.mq.rabbitmq.entity.PortalOrderParam;
import com.laowang.mymall.mq.rabbitmq.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: my-mall
 * @description: 订单模块-controller
 * @author: Laowang
 * @create: 2023-06-09 12:36
 */
@Api(tags = "订单管理")
@Controller
@RequestMapping("/order")
public class OrderController {
    /**
     * 订单服务
     */
    @Autowired
    private OrderService orderService;

    @ApiOperation("根据购物车信息生成订单")
    @PostMapping("/generateOrder")
    @ResponseBody
    public CommonResult<PortalOrder> generateOrder(@RequestBody PortalOrderParam orderParam){
        return CommonResult.success(orderService.generateOrder(orderParam));
    }
}
