package com.laowang.mymall.nosql.elasticsearch.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @program: my-mall
 * @description: ES-响应体advice
 * @author: Laowang
 * @create: 2023-05-31 10:11
 */
@ControllerAdvice(basePackages = "com.laowang.mymall.nosql.elasticsearch.controller")
public class EsReponseBodyAdvice implements ResponseBodyAdvice {

    protected final Logger logger = LoggerFactory.getLogger(EsReponseBodyAdvice.class);

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        Class targetClass = returnType.getMethod().getDeclaringClass();
        logger.info("supports execute methodParameter={} targetClass={} class={}", returnType, targetClass, converterType);
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        logger.info("beforeBodyWrite data={}", body);
        // 这里添加必要的响应头,但是不好使，没有生效，操他腚的
        response.getHeaders().set("X-Elastic-Product", "Elasticsearch");
        return body;
    }
}
