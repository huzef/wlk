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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import com.wlk.core.base.BaseController;

public class UserRealm  extends AuthorizingRealm {
	final static Logger logger = LoggerFactory.getLogger(AuthorizingRealm.class);
	

	 /**
     * 验证当前登录的Subject
     * WebLoginController.login()方法中执行Subject.login()时 执行此方法
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    	UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		logger.info("管理者登录==============》"+upToken.getUsername());
		String username = upToken.getUsername();
		Map<String, Object> map=new HashMap<>();
		map.put("nickname", username);
		// 密码. 
		Object credentials = null;
		// 盐值
		ByteSource credentialsSalt=null;

		Object principal = username;
		//realmName: 当前 realm 对象的 name. 调用父类的 getName() 方法即可
		String realmName = getName();
		SimpleAuthenticationInfo info = null; //new SimpleAuthenticationInfo(principal, credentials, realmName);
		info = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);
		return info;	
    	/*String loginName = (String) token.getPrincipal();
        // 获取用户密码
        String password = new String((char[]) token.getCredentials());
        UUser user = userService.selectOne(new EntityWrapper<UUser>()
        		.eq("nickname", loginName).eq("pswd", password));
        if (user == null) {
            //没找到帐号
            throw new UnknownAccountException();
        }
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getNickname(),
                user.getPswd(),
                //ByteSource.Util.bytes("salt"), salt=username+salt,采用明文访问时，不需要此句
                getName()
        );
        //session中不需要保存密码
        user.setPswd("");
        //将用户信息放入session中
        SecurityUtils.getSubject().getSession().setAttribute("crmforpatent", user);
        return authenticationInfo;*/
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
