package com.wlk.mobile.controller;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wlk.core.util.Assert;
import com.wlk.core.util.json.Json;
import com.wlk.mobile.entity.WBrowse;
import com.wlk.mobile.entity.WCollection;
import com.wlk.mobile.service.IWCollectionService;
import com.wlk.mobile.service.IWUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlk.core.base.BaseController;
import com.wlk.core.support.tokeManager.TokenManager;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
/**
 * <p>
 * 收藏控制器
 * </p>
 *
 * @author hzf
 * @since 2018-12-06
 */
@Api(value = "收藏", description = "收藏")
@RestController
@RequestMapping("/collection")
public class WCollectionController extends BaseController {

	@Autowired
	IWCollectionService collectionService;
	
	@Autowired
	IWUserService userService;
	/**
	 * 收藏查询
	 * @author hzf
	 * @since 2018-12-06
	 * @return
	 */
	@ApiOperation(value = "收藏查询", notes = "收藏查询")
	@RequestMapping(value="page",method=RequestMethod.POST)
	public Json page(@ApiParam(value = "页码", required = false, defaultValue = "1")   @RequestParam(value="pageNum",required=false,defaultValue="1") Integer pageNum,
			@ApiParam(value = "分页条数", required = false, defaultValue = "10")   @RequestParam(value="pageSize",required=false,defaultValue="10") Integer pageSize,
			WCollection collection){
		collection.setCollectionId(TokenManager.getToken().getId());
		Page<WCollection> page=new Page<>(pageNum, pageSize);
		return ajaxJson(userService.queryCollectionList(page,collection.getCollectionId()));
	}
	
	/**
	 * 收藏删除
	 * @author hzf
	 * @since 2018-12-06
	 * @return
	 */
	@ApiOperation(value = "收藏删除", notes = "收藏删除")
	@RequestMapping(value="del",method=RequestMethod.POST)
	public Json delete(){
		
		return ajaxJson();
	}
	
	/**
	 * 收藏修改
	 * @author hzf
	 * @since 2018-12-06
	 * @return
	 */
	@ApiOperation(value = "收藏修改", notes = "收藏修改")
	@RequestMapping(value="update",method=RequestMethod.POST)
	public Json update(){
		return ajaxJson();
	}
	
	/**
	 * 收藏添加
	 * @author hzf
	 * @since 2018-12-06
	 * @return
	 */
	@ApiOperation(value = "收藏添加", notes = "收藏添加")
	@RequestMapping(value="add",method=RequestMethod.POST)
	public Json add(WCollection collection){
		Assert.notNull(collection.getUserId(),"被收藏者不能为空");
		collection.setCollectionId(TokenManager.getToken().getId());
		List<WCollection> lis= collectionService.list(new QueryWrapper<WCollection>().eq("user_id", collection.getUserId()).eq("collection_id", collection.getCollectionId()));
		if(lis.size()>0){
			
			return ajaxJson("您已经收藏！");
		}else{
			
			collection.setCreateDate(new Date());
			collectionService.save(collection);
			return ajaxJson("感谢您的支持！");
		}
	}
	
}
