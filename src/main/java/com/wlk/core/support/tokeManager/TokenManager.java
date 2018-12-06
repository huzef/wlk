package com.wlk.core.support.tokeManager;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import com.wlk.mobile.entity.WUser;





/**
 * Shiro管理下的Token工具类
 * @author hzf
 *
 */
public class TokenManager {
	

	/**
	 * 获取当前用户的用户对象
	 * @return
	 */
	public static WUser getToken(){
		WUser token= (WUser) getValSession(getTokenId());
		return token;
	}
	
	/**
	 * 获取当前用户sessionid(手机号)
	 * @return
	 */
	public static String  getTokenId(){
		String  tokenId= (String) SecurityUtils.getSubject().getPrincipal();
		return tokenId;
	}
	
	
	/**
	 * 获取当前用户的Session
	 * @return
	 */
	public static Session getSession(){
		return SecurityUtils.getSubject().getSession();
	}
	
	/**
	 * 获取当前用户ID
	 * @return
	 */
	public static Integer getUserId(Object key){
		return getToken()==null?null:getToken().getId();
	}
	
	/**
	 * 把值放入到当前登录用户的Session里
	 * @param key
	 * @param value
	 */
	public static void setValSession(Object key ,Object value){
		getSession().setAttribute(key, value);
	}
	/**
	 * 从当前登录用户的Session里取值
	 * @param key
	 * @return
	 */
	public static Object getValSession(Object key){
		return getSession().getAttribute(key);
	}
	/**
	 * 判断是否登录
	 * @return
	 */
	public static boolean isLogin() {
		return null != SecurityUtils.getSubject().getPrincipal();
	}
	/**
	 * 退出登录
	 */
	public static void logout() {
		SecurityUtils.getSubject().logout();
	}
	

}
