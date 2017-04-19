package com.xunishop.manager.controller;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xunishop.manager.domain.ResultData;
import com.xunishop.manager.redis.RedisDao;

@Controller
@RequestMapping("/test")
public class TestController {

    @Resource
    private RedisDao redisDao;
    
    @RequestMapping("/login")
    public ModelAndView loginView(){
        
        
        HashMap<String, String> messages= new HashMap<String,String>();
        messages.put("title", "请登录!");
        messages.put("message", "请输入您的账号密码!");
        
        return new ModelAndView("/login", messages);
    }
    
    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,HttpServletResponse response){
        
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if("user_token".equals(cookie.getName()) && cookie.getValue() !=null){
                System.out.println("logout : " + cookie.getValue());
                redisDao.delByKey(cookie.getValue());
                
                Cookie newCookie = new Cookie("user_token", null);
                newCookie.setMaxAge(0);
                response.addCookie(newCookie);
                
            }
        }
        
        ResultData<String> result = new  ResultData<String>();
        result.setSuccess(true);
        
        HashMap<String, Object> model = new HashMap<String,Object>();
        model.put("result", result);
        
        return new ModelAndView("/login",model);
    }
    
}
