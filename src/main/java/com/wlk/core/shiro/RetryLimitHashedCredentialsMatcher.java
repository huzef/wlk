package com.wlk.core.shiro;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;

import org.apache.shiro.cache.MemoryConstrainedCacheManager;


public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {
	 private Ehcache passwordRetryCache; 
	private Cache<String, Integer> cache;  

	    /** 
	     * 自定义密码错误上限 
	     */  
	    private Integer retryMax;  
	    
	  
	    public Integer getRetryMax() {
			return retryMax;
		}

		public void setRetryMax(Integer retryMax) {
			this.retryMax = retryMax;
		}

		public RetryLimitHashedCredentialsMatcher(org.apache.shiro.cache.CacheManager cacheManager) {  
			/* CacheManager cacheManager = CacheManager.newInstance(CacheManager.class  
		                .getClassLoader().getResource("config/ehcache.xml"));*/  
			// passwordRetryCache = CacheManager.getInstance().getCache("passwordRetryCache");  
	        cache = cacheManager.getCache("passwordRetryCache");  
	    } 	  

		/* @Override  
		    public boolean doCredentialsMatch(AuthenticationToken token,  
		            AuthenticationInfo info) {  
		        String username = (String) token.getPrincipal();  
		        // retry count + 1  
		        Element element = passwordRetryCache.get(username);  
		        if (element == null) {  
		            element = new Element(username, new AtomicInteger(0));  
		            passwordRetryCache.put(element);  
		        }  
		        AtomicInteger retryCount = (AtomicInteger) element.getObjectValue();  
		        if (retryCount.incrementAndGet() > 3) {  
		            // if retry count > 5 throw  
		            System.out.println("已经超过尝试次数");  
		            throw new ExcessiveAttemptsException();  
		        }  
		  
		        boolean matches = super.doCredentialsMatch(token, info);  
		        if (matches) {  
		            // clear retry count  
		            passwordRetryCache.remove(username);  
		        }  
		        return matches;  
		    }  */
		@Override  
	    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) throws ExcessiveAttemptsException {  
	        String username = (String)token.getPrincipal();  
	        Integer retryCount = cache.get(username);  
	        if(retryCount == null) {  
	            retryCount = new Integer(0);  
	            cache.put(username, retryCount);  
	        }  
	  
	  
	        if(retryCount >= retryMax) {  
	            throw new ExcessiveAttemptsException("您已连续错误达" + retryMax + "次！请N分钟后再试");  
	        }  
	  
	        //if( cache.>1){  
	            cache.put(username, ++retryCount);  
	        //}  
	        //调用父类的校验方法  
	        boolean matches = super.doCredentialsMatch(token, info);  
	        if(matches) {  
	            cache.remove(username);  
	        }else {  
	            throw new IncorrectCredentialsException("密码错误，已错误" + retryCount + "次，最多错误" + retryMax + "次");  
	        }  
	        return true;  
	    }  
}
