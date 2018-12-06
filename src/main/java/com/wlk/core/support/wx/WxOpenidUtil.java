package com.wlk.core.support.wx;

import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.alibaba.fastjson.JSONObject;
import com.wlk.core.util.string.StringUtil;


/**
 * 获取微信openId
 * @author Admin
 *
 */




public class WxOpenidUtil {
	public static Map<String, Object> oauth2GetOpenid(String url){
		//String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+secret+"&js_code="+code+"&grant_type=authorization_code";
		Map<String, Object> map=new HashMap<String, Object>();
		HttpClient client = new HttpClient();
		PostMethod m=new PostMethod(url);
		String openid=null;
		String k=null;
		try {
			client.executeMethod(m);
			String SubmitResult = m.getResponseBodyAsString();
			 JSONObject  json = JSONObject.parseObject(SubmitResult);
			  //System.err.println(json);
			 openid=(String) json.get("openid");
			if(StringUtil.isBlank(openid)){
				Integer	d= (Integer) json.get("errcode");
				openid=d.toString();
			 }
			k=(String) json.get("session_key");
		} catch (Exception e) {

			e.getMessage();
		} 
		map.put("openid", openid);
		map.put("sessionKey", k);
		return map;

		

	}
	/**
	 * 解密用户手机号
	 * @param key
	 * @param iv
	 * @param encData
	 * @return
	 * @throws Exception
	 */
	 public static String decrypt(String sessionKey, String ivData, String encrypData) throws Exception {  
		 byte[] encData = Base64.decodeBase64(encrypData);       
		 byte[] iv = Base64.decodeBase64(ivData);    
		 byte[] key = Base64.decodeBase64(sessionKey); 
		 AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);        
		 Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");       
		 SecretKeySpec keySpec = new SecretKeySpec(key, "AES");        
		 cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);        
		 //解析解密后的字符串        
		 return new String(cipher.doFinal(encData),"UTF-8");   
		 }
}
