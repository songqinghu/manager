package com.xunishop.manager.controller;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/login")
    public ModelAndView loginView(){
        
        
        HashMap<String, String> messages= new HashMap<String,String>();
        messages.put("title", "请登录!");
        messages.put("message", "请输入您的账号密码!");
        
        return new ModelAndView("/login", messages);
    }
}
