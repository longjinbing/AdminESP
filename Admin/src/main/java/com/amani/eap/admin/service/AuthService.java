package com.amani.eap.admin.service;

/**
 * @Author 鬼王
 * @Date 2018/04/18 13:14
 */
public interface AuthService {

    String login(String username, String secret);

    /*String refresh(String oldToken);

    Boolean validate(String token,String resource);

    UserVo getUserInfo(String username);

    Boolean invalid(String token);*/

}
