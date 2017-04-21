package com.xunishop.manager.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.xunishop.manager.redis.RedisDao;

/**
 * @Description: 用户登录信息校验
 * @author: songqinghu
 * @date: 2017年4月21日 下午2:17:27
 * Version:1.0
 */
public class UserLoginInterceptor implements HandlerInterceptor{

    @Resource
    private RedisDao redisDaoImpl; 
    
    
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {
        
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
        
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if("user_token".equals(cookie.getName()) && cookie.getValue() !=null){ 
                String username = redisDaoImpl.getValueByKey(cookie.getValue());
                if(username !=null && username.length()>0){
//                    System.out.println(username + " access web site ");
                    return true;
                }
            }
        }
        response.sendRedirect("/");  
        return false;
    }

}
