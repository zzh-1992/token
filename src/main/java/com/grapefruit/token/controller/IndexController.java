package com.grapefruit.token.controller;

/**
 * @author 柚子苦瓜茶  token测试
 * @version 1.0
 * @ModifyTime 2020/9/9 19:48:57
 */

import com.grapefruit.token.entity.Stu;
import com.grapefruit.token.utils.TokenUtils2;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/grapefruit", method = RequestMethod.GET)
public class IndexController {

    //过期时间
    public static final long EXPIRE_Time = 30 * 60 * 1000;

    /**
     * url localhost:8080/grapefruit/login
     * <p>
     * {
     * "userName":"grapefruit",
     * "password":"123"
     * }
     * <p>
     * Headersx
     * contentType application/json
     */
    @RequestMapping("/login")
    public String login(@RequestBody Stu stu) {

        if (!stu.getUserName().equals("grapefruit")) {
            return "没有该用户";
        }
        if (!stu.getPassword().equals("123")) {
            return "密码错误";
        } else {
            String token = TokenUtils2.generateTokenWithHMAC256("ZZZ","2323",EXPIRE_Time);
            return token;
        }
    }

    //后台
    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        String headerToken = request.getHeader("token");

        System.out.println("index token:" + headerToken);

        boolean isLogin = TokenUtils2.checkTokenWithHMAC256(headerToken);

        if (isLogin) {
            return "会话状态持续";
        } else {
            return "会话已经失效";
        }
    }
}
