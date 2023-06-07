package com.laowang.mymall.nosql.elasticsearch.service.impl;

import com.laowang.mymall.nosql.elasticsearch.dao.EsPmsProductDao;
import com.laowang.mymall.nosql.elasticsearch.document.EsPmsProduct;
import com.laowang.mymall.nosql.elasticsearch.repository.EsPmsProductRepository;
import com.laowang.mymall.nosql.elasticsearch.service.EsPmsProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * @program: my-mall
 * @description: ES-商品模块-搜索管理Service实现类
 * @author: Laowang
 * @create: 2023-05-28 13:25
 */
@Service
public class EsPmsProductServiceImpl implements EsPmsProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EsPmsProductServiceImpl.class);

    @Autowired
    private EsPmsProductDao esPmsProductDao;

    @Autowired
    EsPmsProductRepository esPmsProductRepository;

    @Override
    public int importAll() {
        List<EsPmsProduct> esPmsProducts = esPmsProductDao.getAllEsProductList(null);
        Iterable<EsPmsProduct> productsIterable = esPmsProductRepository.saveAll(esPmsProducts);
        Iterator<EsPmsProduct> iterator = productsIterable.iterator();
        int result = 0;
        while (iterator.hasNext()){
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
        List<EsPmsProduct> products = esPmsProductDao.getAllEsProductList(id);
        EsPmsProduct result = null;
        if (products.size() > 0){
            result = products.get(0);
            esPmsProductRepository.save(result);
        }
        return result;
    }

    @Override
    public void delete(List<Long> ids) {
        esPmsProductRepository.deleteAllById(ids);
    }

    @Override
    public Page<EsPmsProduct> search(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return esPmsProductRepository.findByNameOrSubTitleOrKeywords(keyword,keyword,keyword,pageable);
    }
}
