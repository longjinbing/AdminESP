package com.amani.eap.admin.controller;

import com.amani.eap.admin.entity.User;
import com.amani.eap.admin.service.AuthService;
import com.amani.eap.common.constant.BaseConstants;
import com.amani.eap.common.response.ResponseMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author 鬼王
 * @Date 2018/04/18 11:06
 */
@RestController
@RequestMapping("jwt")
public class AuthController {

    @Value("${environments.jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthService authService;

    @PostMapping("token")
    public ResponseMessage<String> token(@RequestBody User user) {
        ResponseMessage<String> rm = new ResponseMessage<>();

        boolean notEmpty = !StringUtils.isAnyEmpty(user.getUsername(), user.getPassword());
        String token = notEmpty?authService.login(user.getUsername(), user.getPassword()):null;

        return notEmpty?(StringUtils.isNotEmpty(token)?rm.success(token):rm.loginError(BaseConstants.MSG_LOGIN_ERROR))
                :rm.loginError(BaseConstants.MSG_LOGIN_EMPTY);
    }

    @PostMapping("invalid")
    public ResponseEntity<?> invalid(String token, HttpServletRequest request) {
        request.getSession().invalidate();

        return ResponseEntity.ok(true);
    }

    @GetMapping("user")
    public ResponseMessage<User> user() {
        return new ResponseMessage();
    }

}
