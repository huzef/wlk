package com.wlk.core.support.fdfs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class FdfsConfig {
	
		@Value("${fdfs.web-server-url}")
		private String webServerUrl;
		
		@Value("${fdfs.resHost}")
	    private String resHost;
	 
	    @Value("${fdfs.storagePort}")
	    private String storagePort;
	 
	    

}
