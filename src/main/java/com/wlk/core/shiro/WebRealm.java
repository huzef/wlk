package com.wlk.core.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import com.alibaba.fastjson.JSONObject;
import com.wlk.core.enums.LoginType;


public class WebRealm  extends AuthorizingRealm {
	static Logger logger = Logger.getLogger(AuthorizingRealm.class);
    @Override
    public String getName() {
        return LoginType.USER.toString();
    }

	 /**
     * 验证当前登录的Subject
     * WebLoginController.login()方法中执行Subject.login()时 执行此方法
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    	CustomizedToken  upToken = (CustomizedToken) token;
		logger.info("PC登录==============》"+upToken.getUsername());
		String accout = upToken.getUsername();
		Map<String, Object> map=new HashMap<>();
		map.put("accout", accout);
		// 密码. 
		Object credentials = null;
		// 盐值
		ByteSource credentialsSalt=null;

		Object principal = accout;
		//realmName: 当前 realm 对象的 name. 调用父类的 getName() 方法即可
		String realmName = getName();
		SimpleAuthenticationInfo info = null; //new SimpleAuthenticationInfo(principal, credentials, realmName);
		info = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);
		return info;	

    }

	@Override  					
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		logger.info("权限==============》"+arg0.getPrimaryPrincipal());
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermission("permission:add");
		info.addRole("user");
		return info;
	}
	
	
	public static void main(String[] args) {
		String hashAlgorithmName = "MD5";//加密类型
		Object credentials = "9876543210";//密码
		Object salt = ByteSource.Util.bytes("h1z1f10");//盐值
		int hashIterations = 1024;
		Object result = new SimpleHash(hashAlgorithmName, credentials, salt,hashIterations);
		System.out.println(result.toString());
	}

}
