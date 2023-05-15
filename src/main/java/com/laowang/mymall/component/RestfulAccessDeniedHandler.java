package com.laowang.mymall.component;

import cn.hutool.json.JSONUtil;
import com.laowang.mymall.common.api.CommonResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: my-mall
 * @description: 当访问接口没有权限时，访问拒绝的处理器
 * @author: Laowang
 * @create: 2023-05-15 08:59
 */
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 设置响应编码格式
        response.setCharacterEncoding("UTF-8");
        // 设置响应内容类型
        response.setContentType("application/json");
        // 响应Writer流写入响应结果
        response.getWriter().println(JSONUtil.parse(CommonResult.forbidden(accessDeniedException.getMessage())));
        // 刷新响应Writer流
        response.getWriter().flush();
    }
}
