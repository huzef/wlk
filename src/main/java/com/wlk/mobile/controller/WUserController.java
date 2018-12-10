package com.wlk.mobile.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageTypeSpecifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wlk.core.util.json.Json;
import com.wlk.core.util.string.StringUtil;
import com.wlk.mobile.entity.WBrowse;
import com.wlk.mobile.entity.WCase;
import com.wlk.mobile.entity.WCollection;
import com.wlk.mobile.entity.WImage;
import com.wlk.mobile.entity.WLike;
import com.wlk.mobile.entity.WUser;
import com.wlk.mobile.service.IWBrowseService;
import com.wlk.mobile.service.IWCaseService;
import com.wlk.mobile.service.IWCollectionService;
import com.wlk.mobile.service.IWImageService;
import com.wlk.mobile.service.IWLikeService;
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
	@Autowired
	IWImageService imageService;
	@Autowired
	IWCaseService caseService;
	@Autowired
	IWCollectionService collectionService;
	@Autowired
	IWLikeService likeService;
	@Autowired
	IWBrowseService browseService;
	/**
	 * 用户信息查询
	 * @author hzf
	 * @since 2018-12-06
	 * @return
	 */
	@ApiOperation(value = "律师信息查询", notes = "律师信息查询")
	@RequestMapping(value="search",method=RequestMethod.POST)
	public Json search(@ApiParam(value = "页码", required = false, defaultValue = "1")   @RequestParam(value="pageNum",required=false,defaultValue="1") Integer pageNum,
			@ApiParam(value = "分页条数", required = false, defaultValue = "10")   @RequestParam(value="pageSize",required=false,defaultValue="10") Integer pageSize,
			WUser user){
		Page<WUser> page=new Page<>(pageNum, pageSize);
		QueryWrapper<WUser> qw=new QueryWrapper<WUser>();
		qw.apply("MATCH(address) AGAINST({0})",user.getAddress());
		//qw.and("MATCH(address) AGAINST({0})",user.getAddress());
		//qw.like("address", user.getAddress());
		qw.eq("is_law", 1);
		if(StringUtil.isNotNull(user.getFirstName())){
		 qw.apply("MATCH(first_name,field) AGAINST({0})",user.getFirstName());
		 //qw.like("first_name", user.getFirstName());	
		}
		return ajaxJson(userService.page(page, qw));
	}

	
	
	@ApiOperation(value = "当前登录用户信息", notes = "当前登录用户信息")
	@RequestMapping(value="getOne",method=RequestMethod.POST)
	public Json getOne(WUser user){
		user.setId(TokenManager.getToken().getId());
		WUser  u=userService.getOne(new QueryWrapper<WUser>().eq("id", user.getId()).eq("is_law", 1));
		if(StringUtil.isNotNull(u)){
			u.setWImages(imageService.list(new QueryWrapper<WImage>().eq("user_id", u.getId())));
			u.setWcases(caseService.list(new QueryWrapper<WCase>().eq("user_id", u.getId())));
			u.setCountBrowse(browseService.count(new QueryWrapper<WBrowse>().eq("user_id", u.getId())));		
			u.setBrowse(userService.queryBrowseForThree(u.getId()));
			u.setCountLike(likeService.count(new QueryWrapper<WLike>().eq("user_id", u.getId())));
			//是否已经点赞
			u.setIsLike(likeService.count(new QueryWrapper<WLike>().eq("user_id", u.getId()).eq("like_id", u.getId())));
			//是否已经收藏
			u.setIsCollection(collectionService.count(new QueryWrapper<WCollection>().eq("user_id", u.getId()).eq("collection_id", u.getId())));
			
		}
		return ajaxJson(u);
	}
	@ApiOperation(value = "单独单个用户信息", notes = "单独单个用户信息")
	@RequestMapping(value="getOne1",method=RequestMethod.POST)
	public Json getOne1(WUser user,Integer DetailsUserId){
		
		WUser  u=userService.getOne(new QueryWrapper<WUser>().eq("id", user.getId()).eq("is_law", 1));
		if(StringUtil.isNotNull(u)){
			u.setWImages(imageService.list(new QueryWrapper<WImage>().eq("user_id", u.getId())));
			u.setWcases(caseService.list(new QueryWrapper<WCase>().eq("user_id", u.getId())));
			u.setCountBrowse(browseService.count(new QueryWrapper<WBrowse>().eq("user_id", u.getId())));		
			u.setBrowse(userService.queryBrowseForThree(u.getId()));
			u.setCountLike(likeService.count(new QueryWrapper<WLike>().eq("user_id", u.getId())));
			Integer ids=0;
			if(StringUtil.isNotNull(DetailsUserId)){
				ids=TokenManager.getToken().getId();
			}else{
				ids=u.getId();
			}
			//是否已经点赞
			u.setIsLike(likeService.count(new QueryWrapper<WLike>().eq("user_id", u.getId()).eq("like_id", ids)));
			//是否已经收藏
			u.setIsCollection(collectionService.count(new QueryWrapper<WCollection>().eq("user_id", u.getId()).eq("collection_id", ids)));
			
		}
		return ajaxJson(u);
	}
	
	@ApiOperation(value = "单个用户信息用于修改", notes = "单个用户信息用于修改")
	@RequestMapping(value="getOneOfUpdate",method=RequestMethod.POST)
	public Json getOneOfUpdate(WUser user,String imageId,String caseId){
		user.setId(TokenManager.getToken().getId());
		WUser  u=userService.getOne(new QueryWrapper<WUser>().eq("id", user.getId()));
		if(StringUtil.isNotNull(imageId)){
			u.setWImages(imageService.list(new QueryWrapper<WImage>().eq("user_id", u.getId())));
		}
		if(StringUtil.isNotNull(caseId)){
			
			u.setWcases(caseService.list(new QueryWrapper<WCase>().eq("user_id", u.getId())));
		}
		return ajaxJson(u);
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
		user.setId(TokenManager.getToken().getId());
		if(StringUtil.isNotNull(user.getImges())){
			imageService.remove(new QueryWrapper<WImage>().eq("user_id",user.getId()));
			String[] str=user.getImges().split(",");
			List<WImage> lis=new ArrayList<>();
			for (int i = 0; i < str.length; i++) {
				WImage image=new WImage();
				image.setCreateDate(new Date());
				image.setUserId(user.getId());
				image.setPath(str[i]);
				lis.add(image);
				//imageService.save(image);
			}
			imageService.saveBatch(lis);
		}
		if(StringUtil.isNotNull(user.getCases())){
			caseService.remove(new QueryWrapper<WCase>().eq("user_id",user.getId()));
			WCase ca=new WCase();
			ca.setCreateDate(new Date());
			ca.setContent(user.getCases());
			ca.setUserId(user.getId());
			caseService.save(ca);
		}
		user.setLastLoginDate(new Date());
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
