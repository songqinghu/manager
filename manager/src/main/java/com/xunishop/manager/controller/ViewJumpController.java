package com.xunishop.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @Description: 页面跳转控制
 * @author: songqinghu
 * @date: 2017年4月21日 下午2:01:10
 * Version:1.0
 */
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/jump")
public class ViewJumpController {

    @RequestMapping("/{view}")
    public ModelAndView viewJump(@PathVariable String view){
        
        return new ModelAndView(view);
    }
    
}
