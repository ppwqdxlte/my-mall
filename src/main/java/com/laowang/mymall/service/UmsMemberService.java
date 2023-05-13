package com.laowang.mymall.service;

public interface UmsMemberService {

    String getAuthCode(String telephone);

    Boolean verifyAuthCode(String telephone, String authCode);
}
