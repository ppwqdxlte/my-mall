package com.laowang.mymall.controller;

import com.laowang.mymall.common.api.CommonPage;
import com.laowang.mymall.common.api.CommonResult;
import com.laowang.mymall.mbg.model.PmsBrand;
import com.laowang.mymall.service.PmsBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: my-mall
 * @description: 品牌管理Controller
 * @author: Laowang
 * @create: 2023-05-11 16:52
 */
@Api(tags = "商品品牌管理")
@Controller
@RequestMapping("/brand")
public class PmsBrandController {
    // 日志记录器
    private static final Logger LOGGER = LoggerFactory.getLogger(PmsBrandController.class);
    @Autowired
    private PmsBrandService pmsBrandService;

    @PreAuthorize("hasAuthority('pms:brand:read')")
    @ApiOperation("获取所有品牌列表")
    @GetMapping("/")
    @ResponseBody
    public CommonResult<List<PmsBrand>> getBrandList(){
        return CommonResult.success(pmsBrandService.listAllBrands());
    }

    @PreAuthorize("hasAuthority('pms:brand:create')")
    @ApiOperation("新建品牌")
    @PostMapping("/")
    @ResponseBody
    public CommonResult createBrand(@RequestBody PmsBrand pmsBrand){
        CommonResult commonResult;
        int count = pmsBrandService.createBrand(pmsBrand);
        if (count == 1){
            commonResult = CommonResult.success(pmsBrand);
            LOGGER.debug("createBrand success:{}",pmsBrand);
        } else {
            commonResult = CommonResult.failed("添加失败");
            LOGGER.debug("createBrand failed:{}",pmsBrand);
        }
        return commonResult;
    }

    @PreAuthorize("hasAuthority('pms:brand:delete')")
    @ApiOperation("删除对应id的品牌")
    @DeleteMapping("/{id}")
    @ResponseBody
    public CommonResult deleteBrand(@PathVariable("id") Long id){
        int count = pmsBrandService.deleteBrand(id);
        if (count == 1){
            LOGGER.debug("deleteBrand success :id={}",id);
            return CommonResult.success(null);
        } else {
            LOGGER.debug("deleteBrand failed :id={}",id);
            return CommonResult.failed("删除失败");
        }
    }

    @PreAuthorize("hasAuthority('pms:brand:update')")
    @ApiOperation("修改对应id的品牌信息")
    @PutMapping("/{id}")
    @ResponseBody
    public CommonResult updateBrand(@PathVariable("id") Long id,@RequestBody PmsBrand brand){
        int count = pmsBrandService.updateBrand(id, brand);
        if (count == 1){
            LOGGER.debug("updateBrand success :{}",brand);
            return CommonResult.success(brand);
        } else {
            LOGGER.debug("updateBrand failed :{}",brand);
            return CommonResult.failed("修改失败");
        }
    }

    @PreAuthorize("hasAuthority('pms:brand:read')")
    @ApiOperation("查询对应id的品牌信息")
    @GetMapping("/{id}")
    @ResponseBody
    public CommonResult<PmsBrand> getBrandById(@PathVariable("id") Long id){
        PmsBrand brand = pmsBrandService.getBrand(id);
        if (brand!=null){
            LOGGER.debug("getBrandById success :{}",brand);
            return CommonResult.success(brand);
        } else {
            LOGGER.debug("getBrandById failed :{}", (Object) null);
            return CommonResult.failed("查不到");
        }
    }

    @PreAuthorize("hasAuthority('pms:brand:read')")
    @ApiOperation("分页查询品牌列表")
    @PostMapping(value = "/list")
    @ResponseBody
    public CommonResult<CommonPage<PmsBrand>> listBrandsByPage(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                                               @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        List<PmsBrand> pmsBrands = pmsBrandService.listBrandsByPage(pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(pmsBrands));
    }
}
