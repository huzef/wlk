package com.wlk.mobile.controller;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wlk.core.util.Assert;
import com.wlk.core.util.json.Json;
import com.wlk.mobile.entity.WFeedback;
import com.wlk.mobile.service.IWFeedbackService;
import com.wlk.core.base.BaseController;
import com.wlk.core.support.tokeManager.TokenManager;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 * <p>
 * 意见反馈控制器
 * </p>
 *
 * @author hzf
 * @since 2018-12-21
 */
@Api(value = "意见反馈", description = "意见反馈")
@RestController
@RequestMapping("/feedback")
public class WFeedbackController extends BaseController {

	@Autowired
	IWFeedbackService feedbackService;

	/**
	 * 意见反馈查询
	 * @author hzf
	 * @since 2018-12-21
	 * @return
	 */
	@ApiOperation(value = "意见反馈查询", notes = "意见反馈查询")
	@RequestMapping(value="list",method=RequestMethod.POST)
	public Json list(){
		
		return ajaxJson();
	}
	
	/**
	 * 意见反馈删除
	 * @author hzf
	 * @since 2018-12-21
	 * @return
	 */
	@ApiOperation(value = "意见反馈删除", notes = "意见反馈删除")
	@RequestMapping(value="del",method=RequestMethod.POST)
	public Json delete(){
		
		return ajaxJson();
	}
	
	/**
	 * 意见反馈修改
	 * @author hzf
	 * @since 2018-12-21
	 * @return
	 */
	@ApiOperation(value = "意见反馈修改", notes = "意见反馈修改")
	@RequestMapping(value="update",method=RequestMethod.POST)
	public Json update(){
		return ajaxJson();
	}
	
	/**
	 * 意见反馈添加
	 * @author hzf
	 * @since 2018-12-21
	 * @return
	 */
	@ApiOperation(value = "意见反馈添加", notes = "意见反馈添加")
	@RequestMapping(value="add",method=RequestMethod.POST)
	public Json add(WFeedback feedback){
		Assert.notNull(feedback.getContent(),"意见内容不能为空");
		feedback.setUserId(TokenManager.getToken().getId());
		feedback.setCreateDate(new Date());
		feedbackService.save(feedback);
		return ajaxJson();
	}
	
}
