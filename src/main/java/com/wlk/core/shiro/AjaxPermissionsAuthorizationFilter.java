package com.wlk.core.shiro;

import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.alibaba.fastjson.JSONObject;
import com.wlk.core.enums.HttpCode;




public class AjaxPermissionsAuthorizationFilter extends FormAuthenticationFilter  {

	
	   @Override
	    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
	        JSONObject jsonObject = new JSONObject();
	        jsonObject.put("code", HttpCode.UNAUTHORIZED.getValue());
	        jsonObject.put("msg", HttpCode.UNAUTHORIZED.getDesc());
	        PrintWriter out = null;
	        HttpServletResponse res = (HttpServletResponse) response;
	        try {
	            res.setCharacterEncoding("UTF-8");
	            res.setContentType("application/json");
	            out = response.getWriter();
	            out.println(jsonObject);
	        } catch (Exception e) {
	        } finally {
	            if (null != out) {
	                out.flush();
	                out.close();
	            }
	        }
	        return false;
	    }

	    @Bean
	    public FilterRegistrationBean registration(AjaxPermissionsAuthorizationFilter filter) {
	        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
	        registration.setEnabled(false);
	        return registration;
	    }
}
