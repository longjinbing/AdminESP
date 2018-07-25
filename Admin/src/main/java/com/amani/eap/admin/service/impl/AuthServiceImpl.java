package com.amani.eap.admin.service.impl;

import com.amani.eap.admin.entity.User;
import com.amani.eap.admin.service.AuthService;
import com.amani.eap.admin.service.UserService;
import com.amani.eap.common.constant.BaseConstants;
import com.amani.eap.common.constant.ResponseConstant;
import com.amani.eap.common.jwt.JwtTokenUtil;
import com.amani.eap.common.util.AESUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author 鬼王
 * @Date 2018/04/18 13:45
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserService userService;

    @Override
    public String login(String username, String password) {
        String token = null;
        if (!StringUtils.isAnyEmpty(username, password)) {
            User user = userService.selectOne(new User(username));
            if (user != null && new BCryptPasswordEncoder(BaseConstants.ENCODER_STRENGTH)
                    .matches(password, user.getPassword())) {
                token = this.generateToken(user);
            }
        }

        return token;
    }

    private String generateToken(User user){
        //生成jwt
        Map<String, Object> claims = new HashMap<>();  // Claims包含您想要签署的任何信息
        String iss = AESUtil.AESEncode(ResponseConstant.AES_SECRET,
                user.getId() + "," + user.getUsername() + "," + user.getPassword());
        claims.put("iss", iss); //jwt的签发者 保存用户的帐号和密码以及id 使用AES对称加密
        claims.put("sub", user.getUsername()); // JWT所面向的用户 用户的username
        claims.put("iat", new Date());
        claims.put("jti", UUID.randomUUID()); //jwt的唯一身份表示

        return  JwtTokenUtil.generateToken(claims, ResponseConstant.JWT_SECRET, ResponseConstant.JWT_EXPIRATION);
    }


    /*@Override
    public String refresh(String token) {
    *//*    String username = JwtTokenUtil.getUsernameFromToken(token);
        UserInfo info = userService.getUserByUsername(username);*//*
        *//*if (JwtTokenUtil.canTokenBeRefreshed(token, info.getUpdateTime())) {
            return JwtTokenUtil.refreshToken(token);
        }*//*
        return null;
    }

    @Override
    public Boolean validate(String token, String resource) {
*//*        String username = JwtTokenUtil.getUsernameFromToken(token);
        UserInfo info = userService.getUserByUsername(username);*//*
        // return info.getUsername().equals(username) && !JwtTokenUtil.isTokenExpired(token) && validateResource(username, resource);
        return false;
    }

    @Override
    public UserVo getUserInfo(String username) {
        UserInfo user = userService.getUserByUsername(username);
        UserVo uservo = new UserVo();
        BeanUtils.copyProperties(user, uservo);
        List<PermissionInfo> permissionInfos = userService.getPermissionByUsername(username);
        Stream<PermissionInfo> menus = permissionInfos.parallelStream().filter((permission) -> {
            return !BaseConstants.RESOURCE_TYPE_ELEMENT.equals(permission.getType());
        });
        uservo.setMenus(menus.collect(Collectors.toList()));
        Stream<PermissionInfo> elements = permissionInfos.parallelStream().filter((permission) -> {
            return BaseConstants.RESOURCE_TYPE_ELEMENT.equals(permission.getType());
        });
        uservo.setElements(elements.collect(Collectors.toList()));
        uservo.setRoles(userService.getRoleCodesByUsername(username));
        return uservo;
    }

    @Override
    public Boolean invalid(String token) {
        // return jwtTokenUtil.invalid(token); sj
        return false;
    }

    public Boolean validateResource(String username, String resource) {
        String[] res = resource.split(":");
        final String requestUri = res[0];
        final String method = res[1];
        List<PermissionInfo> clientPermissionInfo = userService.getPermissionByUsername(username);
        Collection<PermissionInfo> result = Collections2.filter(clientPermissionInfo, new Predicate<PermissionInfo>() {
            @Override
            public boolean apply(PermissionInfo permissionInfo) {
                String url = permissionInfo.getUri();
                String uri = url.replaceAll("\\{\\*\\}", "[a-zA-Z\\\\d]+");
                String regEx = "^" + uri + "$";
                return (Pattern.compile(regEx).matcher(requestUri).find() || requestUri.startsWith(url + "/"))
                        && method.equals(permissionInfo.getMethod());
            }
        });
        if (result.size() <= 0) {
            return false;
        }
        return true;
    }*/

}
