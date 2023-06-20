package com.laowang.mymall.mallsearch.controller;

import com.laowang.mymall.mallsearch.document.EsPmsProduct;
import com.laowang.mymall.mallsearch.service.EsPmsProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @program: my-mall
 * @description: es-商品模块商品controller
 * @author: Laowang
 * @create: 2023-06-20 14:41
 */
@Api(tags = "ES-商品模块-商品搜索")
@Controller
@RequestMapping("/esPmsProduct")
public class EsPmsProductController {
    @Autowired
    private EsPmsProductService esPmsProductService;

    @ApiOperation(value = "导入数据库中所有的商品到ES中")
    @PostMapping("/importAll")
    @ResponseBody
    public int importAllList() {
        return esPmsProductService.importAll();
    }

    @ApiOperation(value = "根据ID从ES删除商品")
    @GetMapping("/delete/{id}")
    @ResponseBody
    public Long delete(@PathVariable Long id) {
        esPmsProductService.delete(id);
        return id;
    }

    @ApiOperation(value = "把数据库里ID的商品加入ES中")
    @RequestMapping(value = "/create/{id}", method = RequestMethod.POST)
    @ResponseBody
    public EsPmsProduct create(@PathVariable Long id) {
        return esPmsProductService.create(id);
    }

    @ApiOperation(value = "简单搜索")
    @RequestMapping(value = "/search/simple", method = RequestMethod.GET)
    @ResponseBody
    public Page<EsPmsProduct> simpleSearch(@RequestParam(required = false) String keyword,
                                           @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                           @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        return esPmsProductService.search(keyword, pageNum, pageSize);
    }

}
