package com.laowang.mymall.nosql.elasticsearch.controller;

import com.laowang.mymall.common.api.CommonPage;
import com.laowang.mymall.common.api.CommonResult;
import com.laowang.mymall.nosql.elasticsearch.document.EsPmsProduct;
import com.laowang.mymall.nosql.elasticsearch.service.EsPmsProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @program: my-mall
 * @description: ES-商品模块-搜索管理Controller
 * @author: Laowang
 * @create: 2023-05-30 10:52
 */
@Controller
@Api(tags = "EsPmsProductController")
@RequestMapping("/esPmsProduct")
public class EsPmsProductController {

    @Autowired
    private EsPmsProductService esPmsProductService;

    @ApiOperation(value = "导入数据库中所有的商品到ES中")
    @PostMapping("/importAll")
    @ResponseBody
    public CommonResult<Integer> importAllList(){
        int count = esPmsProductService.importAll();
        return CommonResult.success(count);
    }

    @ApiOperation(value = "根据ID从ES删除商品")
    @GetMapping("/delete/{id}")
    @ResponseBody
    public CommonResult<Object> delete(@PathVariable Long id){
        esPmsProductService.delete(id);
        return CommonResult.success(null);
    }

    @ApiOperation(value = "把数据库里ID的商品加入ES中")
    @RequestMapping(value = "/create/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<EsPmsProduct> create(@PathVariable Long id) {
        EsPmsProduct esProduct = esPmsProductService.create(id);
        if (esProduct != null) {
            return CommonResult.success(esProduct);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "简单搜索")
    @RequestMapping(value = "/search/simple", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<EsPmsProduct>> simpleSearch(@RequestParam(required = false) String keyword,
                                                               @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                               @RequestParam(required = false, defaultValue = "5") Integer pageSize){
        Page<EsPmsProduct> page = esPmsProductService.search(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(page.toList()));
    }

}
