package com.laowang.mymall.nosql.mongodb.service.impl;

import com.laowang.mymall.nosql.mongodb.document.MgMemberReadHistory;
import com.laowang.mymall.nosql.mongodb.repository.MgMemberReadHistoryRepository;
import com.laowang.mymall.nosql.mongodb.service.MgMemberReadHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @program: my-mall
 * @description: mg-会员浏览商品历史service实现类
 * @author: Laowang
 * @create: 2023-06-08 15:05
 */
@Service
public class MgMemberReadHistoryServiceImpl implements MgMemberReadHistoryService {

    @Autowired
    private MgMemberReadHistoryRepository mgMemberReadHistoryRepository;

    @Override
    public int create(MgMemberReadHistory memberReadHistory) {
        memberReadHistory.setId(null);
        memberReadHistory.setCreateTime(new Date());
        mgMemberReadHistoryRepository.save(memberReadHistory);
        return 1;
    }

    @Override
    public int delete(List<String> ids) {
        mgMemberReadHistoryRepository.deleteAllById(ids);
        return ids.size();
    }

    @Override
    public List<MgMemberReadHistory> list(Long memberId) {
        return mgMemberReadHistoryRepository.findByMemberIdOrderByCreateTimeDesc(memberId);
    }
}
