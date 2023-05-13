package com.laowang.mymall.controller;

import com.laowang.mymall.common.api.CommonResult;
import com.laowang.mymall.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/** 注意：用户管理-会员管理模块还未生成代码，没有完善，无法发送短信验证码以及Mysql持久化
 * @program: my-mall
 * @description: 用户管理模块之会员登录注册管理Controller
 * @author: Laowang
 * @create: 2023-05-13 18:23
 */
@Api(tags = "会员登录注册管理")
@Controller
@RequestMapping("/sso")
public class UmsMemberController {

    @Autowired
    private UmsMemberService umsMemberService;

    @ApiOperation("获取验证码")
    @PostMapping("/getAuthCode")
    @ResponseBody
    public CommonResult<String> getAuthCode(@RequestParam String telephone){
        return CommonResult.success(umsMemberService.getAuthCode(telephone));
    }

    @ApiOperation("判断验证码是否正确")
    @PostMapping("/veriftAuthCode")
    @ResponseBody
    public CommonResult<Boolean> verifyAuthCode(@RequestParam String telephone,@RequestParam String authCode){
        return CommonResult.success(umsMemberService.verifyAuthCode(telephone,authCode));
    }
}
