package com.laowang.mymall.nosql.mongodb.repository;

import com.laowang.mymall.nosql.mongodb.document.MgMemberReadHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: my-mall
 * @description: mg-用户商品浏览历史repository
 * @author: Laowang
 * @create: 2023-06-08 14:58
 */
@Component
public interface MgMemberReadHistoryRepository extends MongoRepository<MgMemberReadHistory,String> {
    /**
     * 根据会员ID按照浏览历史的创建时间降序查找浏览记录列表
     * @param memberId 会员ID
     * @return 浏览历史列表（时间降序，最近浏览优先展示）
     */
    List<MgMemberReadHistory> findByMemberIdOrderByCreateTimeDesc(Long memberId);
}
