package com.laowang.mymall.mq.rabbitmq.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @program: my-mall
 * @description: TODO 前台订单，以后完善代码
 * @author: Laowang
 * @create: 2023-06-09 12:45
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PortalOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "订单ID")
    private Long id;
}
