package com.wlk.mobile.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.shiro.session.mgt.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vdurmont.emoji.EmojiParser;
import com.wlk.core.base.BaseController;
import com.wlk.core.enums.HttpCode;
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

	@Autowired
	IWUserService userService;
	
	@Autowired
	WeChatConfig weChatConfig;

	@ApiOperation(value = "微信登录", notes = "微信登录")
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public Json login(HttpSession session,WUser user,String code){

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
		TokenManager.setValSession(user.getWOpid(),u.size()>0?u.get(0):user);
		return ajaxJson(u.size()>0?u.get(0):user);
	
	}
}
