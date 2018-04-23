package com.amani.eap.admin.service.impl;

import com.amani.eap.admin.service.AuthService;

/**
 * @Author 鬼王
 * @Date 2018/04/18 13:45
 */
public class AuthServiceImpl implements AuthService {

    /*private UserService userService;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Value("${webservice.login.url}")
    private String webServiceUrl;

    @Value("${webservice.login.openAD}")
    private boolean openAD;

    @Autowired
    public AuthServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String login(String username, String password) {
        UserInfo info = userService.getUserByUsername(username);
        String token = "";
        if(!openAD) { // 不采用openad
            if (encoder.matches(password, info.getPassword())) {
                token = generateToken(info);
            }
        }else { //采用openad
            if (LoginUtil.loginAD(webServiceUrl, username, password)) {
                token = generateToken(info);
            }
        }
        return token;
    }

    private String generateToken(UserInfo user){
        //生成jwt
        Map<String, Object> claims = new HashMap<String, Object>();  // Claims包含您想要签署的任何信息
        String iss = AESUtil.AESEncode(ResponseConstant.AES_SECRET,user.getId()+","+user.getUsername()+","+user.getPassword());
        claims.put("iss",iss); //jwt的签发者 保存用户的帐号和密码以及id 使用AES对称加密
        claims.put("sub",user.getUsername()); // JWT所面向的用户 用户的username
        claims.put("iat", new Date());
        claims.put("jti",UUID.randomUUID()); //jwt的唯一身份表示
        //获取token
        String token = JwtTokenUtil.generateToken(claims, ResponseConstant.JWT_SECRET, ResponseConstant.JWT_EXPIRATION);
        return  token;
    }


    @Override
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
