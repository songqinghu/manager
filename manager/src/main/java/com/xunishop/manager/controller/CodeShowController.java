package com.xunishop.manager.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @Description: 操作激活码
 * @author: songqinghu
 * @date: 2017年4月21日 下午4:13:14
 * Version:1.0
 */
import org.springframework.web.bind.annotation.ResponseBody;

import com.xunishop.manager.domain.Code;
import com.xunishop.manager.domain.ResultData;
import com.xunishop.manager.service.ICodeShowService;
import com.xunishop.manager.service.impl.CodeShowService;
@Controller
@RequestMapping("/code")
public class CodeShowController {

    @Resource
    private ICodeShowService codeShowService;
    
    @RequestMapping("/get")
    @ResponseBody
    public ResultData<String> getNextCode(HttpServletRequest request){
      
        //限制每天的读取量
        ResultData<String> result = new ResultData<String>();
        try {
            Code code = codeShowService.getNextCode();
            
            if(code!=null){
                result.setSuccess(true);
                result.setData(code.getCode());
                return result;
            }
            
        } catch (Exception e) {
            
            e.printStackTrace();
            
        }
        
        result.setSuccess(false);
        result.setMessage("请求失败!");
        
        return result;
    }
    
}
