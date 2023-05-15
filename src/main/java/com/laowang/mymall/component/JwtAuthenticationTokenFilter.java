package com.laowang.mymall.component;

import com.laowang.mymall.common.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: my-mall
 * @description: JWT 登录授权过滤器
 * @author: Laowang
 * @create: 2023-05-14 20:08
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * 请求过滤器，查看该请求是否具有权限
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 请求体中，“Authentication”key对应的value字符串
        String authHeader = request.getHeader(this.tokenHeader);
        // 判断header是否可用，进一步提取header中的Bearer后面的加密token串
        if (authHeader != null && authHeader.startsWith(this.tokenHead)){
            // "Bearer "后面的部分就是token
            String authToken = authHeader.substring(this.tokenHead.length());
            // JWT工具从token中解析出username
            String username = jwtTokenUtil.getUsernameFromToken(authToken);
            LOGGER.info("checking username:{}",username);
            // 判断该用户名是否已经登录成功授权，如果没有，进一步判断是否符合授权条件
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                // 通过框架Bean获得该UserDetails对象
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                // JWT验证token和userDetails信息是否能对的上，匹配就进一步加入请求授权白名单
                if (jwtTokenUtil.validateToken(authToken,userDetails)){
                    // 初始化用户密码验证token对象，此时只包含普通UserDetails属性
                    UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(
                            userDetails,null,userDetails.getAuthorities());
                    // 进一步封装request设置details属性值为WebAuthenticationDetails对象
                    upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    LOGGER.info("authenticated user:{}",username);
                    // Security上下文容器收入后，正式给该请求授权
                    SecurityContextHolder.getContext().setAuthentication(upat);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
