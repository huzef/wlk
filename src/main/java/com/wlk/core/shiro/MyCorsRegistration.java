package com.wlk.core.shiro;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;

public class MyCorsRegistration extends CorsRegistration {

	public MyCorsRegistration(String pathPattern) {
		super(pathPattern);
		// TODO Auto-generated constructor stub
	}
	   @Override
	    public CorsConfiguration getCorsConfiguration() {
	        return super.getCorsConfiguration();
	    }
}
