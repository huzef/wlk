package com.wlk.mobile.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.wlk.core.util.json.Json;
import com.wlk.core.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 * <p>
 * 点赞控制器
 * </p>
 *
 * @author hzf
 * @since 2018-12-06
 */
@Api(value = "点赞", description = "点赞")
@RestController
@RequestMapping("/like")
public class WLikeController extends BaseController {


	/**
	 * 点赞查询
	 * @author hzf
	 * @since 2018-12-06
	 * @return
	 */
	@ApiOperation(value = "点赞查询", notes = "点赞查询")
	@RequestMapping(value="list",method=RequestMethod.POST)
	public Json list(){
		
		return ajaxJson();
	}
	
	/**
	 * 点赞删除
	 * @author hzf
	 * @since 2018-12-06
	 * @return
	 */
	@ApiOperation(value = "点赞删除", notes = "点赞删除")
	@RequestMapping(value="del",method=RequestMethod.POST)
	public Json delete(){
		
		return ajaxJson();
	}
	
	/**
	 * 点赞修改
	 * @author hzf
	 * @since 2018-12-06
	 * @return
	 */
	@ApiOperation(value = "点赞修改", notes = "点赞修改")
	@RequestMapping(value="update",method=RequestMethod.POST)
	public Json update(){
		return ajaxJson();
	}
	
	/**
	 * 点赞添加
	 * @author hzf
	 * @since 2018-12-06
	 * @return
	 */
	@ApiOperation(value = "点赞添加", notes = "点赞添加")
	@RequestMapping(value="add",method=RequestMethod.POST)
	public Json add(){
		return ajaxJson();
	}
	
}
