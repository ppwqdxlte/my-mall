package com.laowang.mymall.mallsearch.service.impl;

import com.laowang.mymall.mallsearch.document.EsPmsProduct;
import com.laowang.mymall.mallsearch.mapper.EsPmsProductMapper;
import com.laowang.mymall.mallsearch.repository.EsPmsProductRepository;
import com.laowang.mymall.mallsearch.service.EsPmsProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * @program: my-mall
 * @description: es-商品模块商品service实现类
 * @author: Laowang
 * @create: 2023-06-20 14:14
 */
@Service
public class EsPmsProductServiceImpl implements EsPmsProductService {

    @Autowired
    private EsPmsProductMapper esPmsProductMapper;

    @Autowired
    private EsPmsProductRepository esPmsProductRepository;

    @Override
    public int importAll() {
        List<EsPmsProduct> esPmsProducts = esPmsProductMapper.getPmsProductList(null);
        Iterable<EsPmsProduct> productIterable = esPmsProductRepository.saveAll(esPmsProducts);
        Iterator<EsPmsProduct> iterator = productIterable.iterator();
        int result = 0;
        while (iterator.hasNext()) {
            result++;
            iterator.next();
        }
        return result;
    }

    @Override
    public void delete(Long id) {
        esPmsProductRepository.deleteById(id);
    }

    @Override
    public EsPmsProduct create(Long id) {
        List<EsPmsProduct> pmsProductList = esPmsProductMapper.getPmsProductList(id);
        if (pmsProductList.size() > 0) {
            return esPmsProductRepository.save(pmsProductList.get(0));
        }
        return null;
    }

    @Override
    public void delete(List<Long> ids) {
        esPmsProductRepository.deleteAllById(ids);
    }

    @Override
    public Page<EsPmsProduct> search(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return esPmsProductRepository.findByNameOrSubTitleOrKeywords(keyword, keyword, keyword, pageable);
    }
}
