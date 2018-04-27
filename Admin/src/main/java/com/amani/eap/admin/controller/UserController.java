package com.amani.eap.admin.controller;

import com.amani.eap.admin.entity.User;
import com.amani.eap.admin.service.UserService;
import com.amani.eap.common.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 鬼王
 * @Date 2018/04/18 14:10
 */
@RestController
@RequestMapping("user")
public class UserController extends BaseController<UserService, User> {

}
