package com.xunishop.manager.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xunishop.manager.dao.FileUploadMapper;
import com.xunishop.manager.domain.Code;
import com.xunishop.manager.service.IFileUploadService;

@Service
@Transactional
public class FileUploadService implements IFileUploadService {

    @Resource
    private FileUploadMapper fileUploadMapper;
    
    @Override
    public boolean addBitCode(List<String> lines) {
        
        try {
            List<Code> codes = new ArrayList<Code>();
            
            for (String line : lines) {
                if(line!=null && line.length()>0){
                    Code code = new Code();
                    code.setCode(line);
                    code.setCodetype(1l);
                    code.setUserId(1l);
                    code.setUuid(UUID.randomUUID().toString());
                    codes.add(code);
                }
            }
            
            //添加
            fileUploadMapper.addBitCode(codes);
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();  
        }
        
        return false;
    }

}
