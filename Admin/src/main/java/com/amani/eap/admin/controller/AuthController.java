package com.amani.eap.admin.controller;

import com.amani.eap.common.response.ResponseMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author 鬼王
 * @Date 2018/04/18 11:06
 */
@RestController
@RequestMapping("jwt")
public class AuthController {

    @Value("${environment.jwt.header}")
    private String tokenHeader;

    @PostMapping("token")
    public ResponseMessage<String> token() {
        return new ResponseMessage<>();
    }

    @GetMapping("invalid")
    public ResponseEntity<?> invalid(String token, HttpServletRequest request) {
        request.getSession().invalidate();

        return ResponseEntity.ok(true);
    }

    @GetMapping("user")
    public String user() {
        return "test.11..";
    }

}
