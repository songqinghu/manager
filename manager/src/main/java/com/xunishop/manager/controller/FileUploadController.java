package com.xunishop.manager.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.xunishop.manager.service.IFileUploadService;
/**
 * @Description: 文件上传
 * @author: songqinghu
 * @date: 2017年4月21日 下午2:41:46
 * Version:1.0
 */
@Controller
@RequestMapping("/upload")
public class FileUploadController {

    
    @Resource
    private IFileUploadService fileUploadService;
    
    @RequestMapping(value = "/tieba",method=RequestMethod.POST)  
    public ModelAndView upload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, ModelMap model) {  
  
        if(file !=null){
            String fileName = file.getOriginalFilename(); 
            
            if(fileName.endsWith(".txt")){
                //保存  
                try {  
                    
                    List<String> lines = IOUtils.readLines(file.getInputStream());
                    
                    fileUploadService.addBitCode(lines);
//                    for (String line : lines) {
//                        //这里处理写入数据库
//                        System.out.println(line);
//                    }
        //            file.transferTo(targetFile);  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }
        }
       
        return new ModelAndView("file");  
    }  
    
}
