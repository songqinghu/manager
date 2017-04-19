package com.xunishop.manager.controller;

import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xunishop.manager.domain.ResultData;
import com.xunishop.manager.redis.RedisDao;

/**
 * @Description: 登录接口
 * @author: songqinghu
 * @date: 2017年4月19日 下午2:18:16
 * Version:1.0
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Resource
    private RedisDao redisDao;
    
    @RequestMapping("/userLogin")
    @ResponseBody
    public ResultData<String> userLogin(String username,String password,
            HttpServletRequest request,HttpServletResponse response){
        
        ResultData<String> result = new ResultData<String>();
        if("songqinghu".equals(username) && "Song1234".equals(password)){
            
            String token = UUID.randomUUID().toString();
            
            System.out.println("uuid : " + token);
            
            Cookie cookie = new Cookie("user_token",token);
            cookie.setPath("/");
            cookie.setMaxAge(60*30);
            response.addCookie(cookie);
            
            redisDao.setValueByKey(cookie.getValue(), "userId");
            
            result.setSuccess(true);
            result.setData(cookie.getValue());
        }else{
            result.setSuccess(false);
        }
        return result;
    }
    
}
