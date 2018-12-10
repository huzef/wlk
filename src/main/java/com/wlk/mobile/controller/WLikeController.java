package com.wlk.mobile.controller;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wlk.core.util.Assert;
import com.wlk.core.util.json.Json;
import com.wlk.mobile.entity.WLike;
import com.wlk.mobile.service.IWLikeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wlk.core.base.BaseController;
import com.wlk.core.enums.HttpCode;
import com.wlk.core.support.tokeManager.TokenManager;

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

	@Autowired
	IWLikeService likeService;
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
	public Json add(WLike like){
		Assert.notNull(like.getUserId(),"被点赞者不能为空");
		like.setLikeId(TokenManager.getToken().getId());
		List<WLike> lis=likeService.list(new QueryWrapper<WLike>().eq("user_id", like.getUserId()).eq("like_id", like.getLikeId()));
		if(lis.size()>0){
			return ajaxJson("您已经点过赞！");
		}else{
			
			like.setCreateDate(new Date());
			likeService.save(like);
		}
		return ajaxJson("感谢您的支持！");
	}
	
}
