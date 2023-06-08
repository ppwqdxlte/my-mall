package com.laowang.mymall.nosql.mongodb.service;

import com.laowang.mymall.nosql.mongodb.document.MgMemberReadHistory;

import java.util.List;

/**
 * @program: my-mall
 * @description: mg-会员浏览商品历史service
 * @author: Laowang
 * @create: 2023-06-08 15:04
 */
public interface MgMemberReadHistoryService {
    /**
     * 生成浏览记录
     */
    int create(MgMemberReadHistory memberReadHistory);

    /**
     * 批量删除浏览记录
     */
    int delete(List<String> ids);

    /**
     * 获取用户浏览历史记录
     */
    List<MgMemberReadHistory> list(Long memberId);
}
