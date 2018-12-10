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
 * 律师形象照控制器
 * </p>
 *
 * @author hzf
 * @since 2018-12-10
 */
@Api(value = "律师形象照", description = "律师形象照")
@RestController
@RequestMapping("/image")
public class WImageController extends BaseController {


	/**
	 * 律师形象照查询
	 * @author hzf
	 * @since 2018-12-10
	 * @return
	 */
	@ApiOperation(value = "律师形象照查询", notes = "律师形象照查询")
	@RequestMapping(value="list",method=RequestMethod.POST)
	public Json list(){
		
		return ajaxJson();
	}
	
	/**
	 * 律师形象照删除
	 * @author hzf
	 * @since 2018-12-10
	 * @return
	 */
	@ApiOperation(value = "律师形象照删除", notes = "律师形象照删除")
	@RequestMapping(value="del",method=RequestMethod.POST)
	public Json delete(){
		
		return ajaxJson();
	}
	
	/**
	 * 律师形象照修改
	 * @author hzf
	 * @since 2018-12-10
	 * @return
	 */
	@ApiOperation(value = "律师形象照修改", notes = "律师形象照修改")
	@RequestMapping(value="update",method=RequestMethod.POST)
	public Json update(){
		return ajaxJson();
	}
	
	/**
	 * 律师形象照添加
	 * @author hzf
	 * @since 2018-12-10
	 * @return
	 */
	@ApiOperation(value = "律师形象照添加", notes = "律师形象照添加")
	@RequestMapping(value="add",method=RequestMethod.POST)
	public Json add(){
		return ajaxJson();
	}
	
}
