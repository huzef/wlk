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
 * 浏览记录控制器
 * </p>
 *
 * @author hzf
 * @since 2018-12-06
 */
@Api(value = "浏览记录", description = "浏览记录")
@RestController
@RequestMapping("/browse")
public class WBrowseController extends BaseController {


	/**
	 * 浏览记录查询
	 * @author hzf
	 * @since 2018-12-06
	 * @return
	 */
	@ApiOperation(value = "浏览记录查询", notes = "浏览记录查询")
	@RequestMapping(value="list",method=RequestMethod.POST)
	public Json list(){
		
		return ajaxJson();
	}
	
	/**
	 * 浏览记录删除
	 * @author hzf
	 * @since 2018-12-06
	 * @return
	 */
	@ApiOperation(value = "浏览记录删除", notes = "浏览记录删除")
	@RequestMapping(value="del",method=RequestMethod.POST)
	public Json delete(){
		
		return ajaxJson();
	}
	
	/**
	 * 浏览记录修改
	 * @author hzf
	 * @since 2018-12-06
	 * @return
	 */
	@ApiOperation(value = "浏览记录修改", notes = "浏览记录修改")
	@RequestMapping(value="update",method=RequestMethod.POST)
	public Json update(){
		return ajaxJson();
	}
	
	/**
	 * 浏览记录添加
	 * @author hzf
	 * @since 2018-12-06
	 * @return
	 */
	@ApiOperation(value = "浏览记录添加", notes = "浏览记录添加")
	@RequestMapping(value="add",method=RequestMethod.POST)
	public Json add(){
		return ajaxJson();
	}
	
}
