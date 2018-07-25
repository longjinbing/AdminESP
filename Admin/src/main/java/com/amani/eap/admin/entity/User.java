package com.amani.eap.admin.entity;

import com.amani.eap.common.entity.BaseEntity;

import javax.persistence.Table;

/**
 * 用户基本信息
 *
 * @Author 鬼王
 * @Date 2018/04/24 08:03:55
 */
@Table(name = "base_user")
public class User extends BaseEntity {

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    private String name;

    private String email;

    private String mobile;

    private Integer enabled;

    public User() {}

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }
}