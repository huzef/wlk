package com.wlk.mobile.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vdurmont.emoji.EmojiParser;
import com.wlk.core.base.BaseController;
import com.wlk.core.enums.HttpCode;
import com.wlk.core.enums.LoginType;
import com.wlk.core.shiro.CustomizedToken;
import com.wlk.core.support.tokeManager.TokenManager;
import com.wlk.core.support.wx.WeChatConfig;
import com.wlk.core.support.wx.WxOpenidUtil;
import com.wlk.core.util.json.Json;
import com.wlk.core.util.string.StringUtil;
import com.wlk.mobile.entity.WUser;
import com.wlk.mobile.service.IWUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "登录", description = "登录")
@RestController
public class LoginController extends BaseController {
	static Logger logger = Logger.getLogger(LoginController.class);
	private static final String ADMIN_LOGIN_TYPE = LoginType.ADMIN.toString();
	@Autowired
	IWUserService userService;
	
	@Autowired
	WeChatConfig weChatConfig;

	@ApiOperation(value = "微信登录", notes = "微信登录")
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public Json login(WUser user,String code){
		System.out.println(TokenManager.getValSession("olVJ75RaiMBFmKnPGqZ71t3qdVj0"));
		
		if(!StringUtil.isBlank(code)){
			Map<String, Object> map= WxOpenidUtil.oauth2GetOpenid((weChatConfig.getUrl()+code));
			String openid=(String) map.get("openid");
			//System.err.println("用户code=========="+openid);
			user.setSessionKey((String) map.get("sessionKey"));
			user.setWOpid(openid);
			if(openid.equals("40029")){
				return ajaxJson(HttpCode.LOGIN_FAIL,HttpCode.THREE_LOGIN_FAIL.getDesc());
			}else if(openid.equals("40163")){
				return ajaxJson(HttpCode.LOGIN_FAIL,HttpCode.THREE_LOGIN.getDesc());
			}
		}
		List<WUser> u=userService.list(new QueryWrapper<WUser>().eq("w_opid", user.getWOpid()));
		if(!(u.size()>0)){
			user.setCreateDate(new Date());
			//先将昵称中有表情符号的移除掉 如有需要可以用下面方法转化
			//EmojiParser.parseToAliases(user.getNikeName());将表情符号转为字符
			//EmojiParser.parseToUnicode(u.getNikeName());将字符转为表情符号
			if(StringUtil.isNotNull(user.getNickName())){
				user.setNickName(EmojiParser.removeAllEmojis(user.getNickName()));
			}
			userService.save(user);
		}else{
			user.setId(u.get(0).getId());
			user.setLastLoginDate(new Date());
			//先将昵称中有表情符号的移除掉 如有需要可以用下面方法转化
			//EmojiParser.parseToAliases(user.getNikeName());将表情符号转为字符
			//EmojiParser.parseToUnicode(u.getNikeName());将字符转为表情符号
			if(StringUtil.isNotNull(user.getNickName())){
				user.setNickName(EmojiParser.removeAllEmojis(user.getNickName()));
			}
			userService.updateById(user);
			u.get(0).setSessionKey(user.getSessionKey());
		}	

		this.checkLogin(user);
		System.out.println(TokenManager.getValSession("olVJ75RaiMBFmKnPGqZ71t3qdVj0"));
		return ajaxJson(TokenManager.getToken());
	
	}
	
	@ApiOperation(value = "检查微信登录", notes = "检查微信登录")
	@RequestMapping(value="/checkLogin",method=RequestMethod.POST)
	public Json checkLogin(WUser user){
		
		Subject currentUser = SecurityUtils.getSubject();
		if(!currentUser.isAuthenticated()){
			UsernamePasswordToken token=new UsernamePasswordToken(user.getWOpid(), "9876543210");
			//CustomizedToken token = new CustomizedToken(user.getWOpid(), "80",ADMIN_LOGIN_TYPE);
			//token.setRememberMe(true);//记住我
			try {
				currentUser.login(token);
			}  catch (LockedAccountException e) {
				logger.info("帐号被锁定========"+token.getPrincipal());
				e.printStackTrace();
				return ajaxJson(HttpCode.LOCKED, null);
			}catch (UnknownAccountException e) {
				logger.info("没有找的该账号"+token.getPrincipal());
				e.printStackTrace();
				return ajaxJson(HttpCode.NAME_ERROR, null);
			}catch (DisabledAccountException e) {
				logger.info("帐号被禁用========"+token.getPrincipal());
				e.printStackTrace();
				return ajaxJson(HttpCode.NAME_DISABLED, null);
			} catch (ExpiredCredentialsException e) {
				logger.info("凭证过期========"+token.getPrincipal());
				e.printStackTrace();
				return ajaxJson(HttpCode.REQUEST_TIMEOUT, null);
			}catch (ExcessiveAttemptsException e) {
				logger.info("登录失败次数过多===="+token.getPrincipal());
				e.printStackTrace();
				return ajaxJson(HttpCode.MULTI_STATUS, null);
			}catch(IncorrectCredentialsException e){
				logger.info("账号或密码错误===="+token.getPrincipal());
				e.printStackTrace();
				return ajaxJson(HttpCode.NAME_PWD_ERROR, null);
			}catch (AuthenticationException e) {
				logger.info("登录失败");
				e.printStackTrace();
				return ajaxJson(HttpCode.LOGIN_FAIL, null);
			}
		}
		return ajaxJson(TokenManager.getToken());
	}
}
