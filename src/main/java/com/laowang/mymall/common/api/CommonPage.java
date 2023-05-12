package com.laowang.mymall.common.api;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @program: my-mall
 * @description: 分页数据封装类
 * @author: Laowang
 * @create: 2023-05-11 12:12
 */
public class CommonPage<T> {
    /**
     * 当前页码
     */
    private Integer pageNum;
    /**
     * 页面最多显示的条数
     */
    private Integer pageSize;
    /**
     * 总页数
     */
    private Integer totalPage;
    /**
     * 总记录数
     */
    private Long total;
    /**
     * 结果集
     */
    private List<T> resultList;

    /** 将PageHelper处理产生的list转变为所需要的分页信息
     * @note 静态方法返回泛型需要在返回类型前面再标注<T>
     * @param list PageHelper分页后的list
     * @return 分页信息
     */
    public static <T> CommonPage<T> restPage(List<T> list) {
        CommonPage<T> resultPage = new CommonPage<>();
        PageInfo<T> pageInfo = new PageInfo<T>(list);
        resultPage.setTotalPage(pageInfo.getPages());
        resultPage.setPageNum(pageInfo.getPageNum());
        resultPage.setPageSize(pageInfo.getPageSize());
        resultPage.setTotal(pageInfo.getTotal());
        resultPage.setResultList(pageInfo.getList());
        return resultPage;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }
}
