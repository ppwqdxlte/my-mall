package com.laowang.mymall.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.laowang.mymall.service.RedisService;
import com.laowang.mymall.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @program: my-mall
 * @description: 会员登录验证Service实现类
 * @author: Laowang
 * @create: 2023-05-13 18:35
 */
@Service
public class UmsMemberServiceImpl implements UmsMemberService {

    @Autowired
    private RedisService redisService;

    @Value("${redis.key.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTHCODE;

    @Value("${redis.key.expire.authCode}")
    private Long REDIS_KEY_EXPIRE_AUTHCODE;

    @Override
    public String getAuthCode(String telephone) {
        // 生成随机验证码
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10)); //生成0-9随机整数
        }
        // TODO 调用运营商接口给手机号码发送短信，携带上述随机验证码，异步，是否成功取决于回调是否成功
        // 验证码绑定手机号并存储到redis
        redisService.set(REDIS_KEY_PREFIX_AUTHCODE+telephone,sb.toString());
        redisService.expire(REDIS_KEY_PREFIX_AUTHCODE+telephone,REDIS_KEY_EXPIRE_AUTHCODE);
        // 应该是回调结果，发送成功或失败，肯定不能发送给Controller再给前台吧？
        // 这里返回给Controller是给接口测试看的，无所谓了！
        return sb.toString();
    }

    /** 对输入的验证码进行校验
     * @param telephone 电话号
     * @param authCode 验证码
     * @return 是否正确
     */
    @Override
    public Boolean verifyAuthCode(String telephone, String authCode) {
        if (StringUtils.isEmpty(authCode)){
            return false;
        }
        String realAuthCode = redisService.get(REDIS_KEY_PREFIX_AUTHCODE + telephone);
        return authCode.equals(realAuthCode);
    }
}
