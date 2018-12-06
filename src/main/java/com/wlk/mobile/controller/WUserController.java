package com.wlk.mobile.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.wlk.core.util.json.Json;
import com.wlk.mobile.entity.WUser;
import com.wlk.mobile.service.IWUserService;
import com.wlk.core.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 * <p>
 * 用户信息控制器
 * </p>
 *
 * @author hzf
 * @since 2018-12-06
 */
@Api(value = "用户信息", description = "用户信息")
@RestController
@RequestMapping("/user")
public class WUserController extends BaseController {

	@Autowired
	IWUserService userService;
	/**
	 * 用户信息查询
	 * @author hzf
	 * @since 2018-12-06
	 * @return
	 */
	@ApiOperation(value = "用户信息查询", notes = "用户信息查询")
	@RequestMapping(value="list",method=RequestMethod.POST)
	public Json list(){
		
		return ajaxJson();
	}
	
	/**
	 * 用户信息删除
	 * @author hzf
	 * @since 2018-12-06
	 * @return
	 */
	@ApiOperation(value = "用户信息删除", notes = "用户信息删除")
	@RequestMapping(value="del",method=RequestMethod.POST)
	public Json delete(){
		
		return ajaxJson();
	}
	
	/**
	 * 用户信息修改
	 * @author hzf
	 * @since 2018-12-06
	 * @return
	 */
	@ApiOperation(value = "用户信息修改", notes = "用户信息修改")
	@RequestMapping(value="update",method=RequestMethod.POST)
	public Json update(WUser user){
		userService.saveOrUpdate(user);
		return ajaxJson(user);
	}
	
	/**
	 * 用户信息添加
	 * @author hzf
	 * @since 2018-12-06
	 * @return
	 */
	@ApiOperation(value = "用户信息添加", notes = "用户信息添加")
	@RequestMapping(value="add",method=RequestMethod.POST)
	public Json add(){
		return ajaxJson();
	}
	
}
