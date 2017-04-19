package com.xunishop.manager.utils.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 访问页面识别正则
 * <p>Title: AccessURIRegexUtil.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: Sage</p>
 * @author 五虎将
 * @date 2016年9月4日上午10:42:01
 * @version 1.0
 */
public class AccessURIRegexUtil {

	
	/**
	 * 是否是需要监控的页面
	 * <p>Title: accessPage</p>
	 * <p>Description: </p>
	 * @return
	 */
	public static boolean accessPage(String uri){
		
		//目前监控的页面 
		//===== pc 站点
		// /images/1/list
		// /images/detail
		// /gif/list
		//===== m 站 
		// /m/image/1/detail/list
		// /m/image/detail
		Pattern pattern = Pattern.
				compile("(/images/\\d/list)|(/images/detail)|(/gif/list)|(/m/image/\\d/detail/list)|(/m/image/detail)|(/m/gif/detail/list)");
		
		Matcher matcher = pattern.matcher(uri);
		
		return matcher.matches();
	}
	
	public static void main(String[] args) {
		boolean accessPage = accessPage("/m/gif/detail/list");
		System.out.println(accessPage);
	}
	
	
}
