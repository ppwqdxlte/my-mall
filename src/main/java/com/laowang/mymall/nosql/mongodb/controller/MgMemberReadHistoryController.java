package com.laowang.mymall.nosql.mongodb.controller;

import com.laowang.mymall.common.api.CommonResult;
import com.laowang.mymall.nosql.mongodb.document.MgMemberReadHistory;
import com.laowang.mymall.nosql.mongodb.service.MgMemberReadHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: my-mall
 * @description: mg-会员浏览商品历史记录controller
 * @author: Laowang
 * @create: 2023-06-08 15:14
 */
@Api(tags = "mg-会员浏览商品历史记录")
@Controller
@RequestMapping("/member/readHistory")
public class MgMemberReadHistoryController {

    @Autowired
    private MgMemberReadHistoryService mgMemberReadHistoryService;

    @ApiOperation("创建浏览记录")
    @PostMapping(value = "/create")
    @ResponseBody
    public CommonResult create(@RequestBody MgMemberReadHistory memberReadHistory) {
        int count = mgMemberReadHistoryService.create(memberReadHistory);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("删除浏览记录")
    @PostMapping(value = "/delete")
    @ResponseBody
    public CommonResult delete(@RequestParam("ids") List<String> ids) {
        int count = mgMemberReadHistoryService.delete(ids);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("展示浏览记录")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<MgMemberReadHistory>> list(Long memberId) {
        List<MgMemberReadHistory> memberReadHistoryList = mgMemberReadHistoryService.list(memberId);
        return CommonResult.success(memberReadHistoryList);
    }
}
