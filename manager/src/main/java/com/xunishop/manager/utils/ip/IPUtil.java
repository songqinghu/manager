package com.xunishop.manager.utils.ip;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class IPUtil {

	/**
	 * 获取用户ip
	 * <p>Title: getRemoteIpAddr</p>
	 * <p>Description: </p>
	 * @param request
	 * @return
	 * @throws Exception
	 */
    public static String getRemoteIpAddr(HttpServletRequest request) throws Exception{  
        String ip = request.getHeader("X-Real-IP");  
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {  
            return ip;  
        }  
        ip = request.getHeader("X-Forwarded-For");  
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {  
            // 多次反向代理后会有多个IP值，第一个为真实IP。  
            int index = ip.indexOf(',');  
            if (index != -1) {  
                return ip.substring(0, index);  
            } else {  
                return ip;  
            }  
        } else {  
            return request.getRemoteAddr();  
        }  
    }  
}
