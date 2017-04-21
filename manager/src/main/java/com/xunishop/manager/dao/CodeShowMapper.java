package com.xunishop.manager.dao;

import com.xunishop.manager.domain.Code;

public interface CodeShowMapper {

    public Code getNextCode();
    
    public void updateCodeFlag(Long id);
    
}
