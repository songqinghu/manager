package com.xunishop.manager.domain;

import java.util.Date;

/**
 * @Description: 激活码类
 * @author: songqinghu
 * @date: 2017年4月21日 下午3:39:29
 * Version:1.0
 */
public class Code {

    private Long id;
    
    private String code;
    
    private Long codetype;
    
    private String uuid;
    
    private Long userId;
    
    private Date updatedate;
    
    private Long flag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getCodetype() {
        return codetype;
    }

    public void setCodetype(Long codetype) {
        this.codetype = codetype;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public Long getFlag() {
        return flag;
    }

    public void setFlag(Long flag) {
        this.flag = flag;
    }
    
}
