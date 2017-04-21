package com.xunishop.manager.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xunishop.manager.dao.CodeShowMapper;
import com.xunishop.manager.domain.Code;
import com.xunishop.manager.service.ICodeShowService;

@Service
@Transactional
public class CodeShowService implements ICodeShowService {

    @Resource
    private CodeShowMapper codeShowMapper;
    
    /**
     * 获取最新可以的code
     */
    @Override
    public Code getNextCode() {
        
        Code code = codeShowMapper.getNextCode();
        
        if(code!=null && code.getId()!=null){
            //更新返回
            codeShowMapper.updateCodeFlag(code.getId());
            
            return code;
        }
        
        return null;
    }

}
