package com.xunishop.manager.interceptor;

import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.xunishop.manager.redis.RedisDao;
import com.xunishop.manager.utils.ip.IPUtil;
import com.xunishop.manager.utils.redis.RedisKeyUtil;
import com.xunishop.manager.utils.regex.AccessURIRegexUtil;
/**
 * 爬虫简单防御系统
 * <p>Title: DefendSpiderInterceptor.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: Sage</p>
 * @author 五虎将
 * @date 2016年9月3日下午10:57:38
 * @version 1.0
 */
public class DefendSpiderInterceptor implements HandlerInterceptor {

	@Resource
	private RedisDao redisDaoImpl; 
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String userAgent = request.getHeader("User-Agent");
		if(userAgent ==null || userAgent.length()==0){
			response.setStatus(403);
			return false;
		}
		
		String userIP = IPUtil.getRemoteIpAddr(request);
		//爬虫--白名单 --各大搜索引擎 --放行
		if(Pattern.compile("^66\\.249\\..+").matcher(userIP).find()){//谷歌
			return true;
		}
		//爬虫黑名单 -- ip禁封10天
		if(redisDaoImpl.getValueByKey(RedisKeyUtil.getSpiderBlackListKey(userIP))!=null){
			response.setStatus(403);
			return false;
		}
		//初次识别 -- 不属于上述情况 纪录ip 放入 一级识别中 spider_one_ip 过期时间设置为1分钟
		String uri = request.getRequestURI();
		
		//正常爬虫访问的频率是很低的 算 1分钟10次就很高了
		if(AccessURIRegexUtil.accessPage(uri)){ //加1
			Boolean flag = redisDaoImpl.incrValueByKey(RedisKeyUtil.getSpiderOneKey(userIP),60,20); //1 分钟 多余20次异常
			//如果是 false 需要进入二级识别 加1  三次进入黑名单 
			if(!flag){
				Boolean twoFlag = redisDaoImpl.incrValueByKey(RedisKeyUtil.getSpiderTwoKey(userIP),86400,2); //1天 多余 3次 异常
				
				if(!twoFlag){
					redisDaoImpl.incrValueByKey(RedisKeyUtil.getSpiderBlackListKey(userIP),86400*10,Integer.MAX_VALUE); 
				}
				response.setStatus(403);
				return false;
			}
		}else if("/spider/show/count".equals(uri)){ //减1
			redisDaoImpl.decrValueByKey(RedisKeyUtil.getSpiderOneKey(userIP));
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
