package com.wlk.mobile.controller;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.tobato.fastdfs.domain.StorePath;
import com.wlk.core.base.BaseController;
import com.wlk.core.support.fdfs.FastDFSClient;
import com.wlk.core.util.json.Json;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "文件详情", description = "文件详情")
@RestController
@RequestMapping("/file")
public class FileController extends BaseController{
	@Autowired
	FastDFSClient fastDFSClient;
	@ApiOperation(value = "文件上传", notes = "文件上传")
	@RequestMapping(value="upload",method=RequestMethod.POST)
	public Json upload(@RequestParam(value="file",required=false) MultipartFile file){
		try {
			StorePath storePath=fastDFSClient.uploadFile(file);

			String accessUrl=fastDFSClient.getResAccessUrl(storePath);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ajaxJson(null);
	}
}
