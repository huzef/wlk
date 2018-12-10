package com.wlk.mobile.service;

import com.wlk.mobile.entity.WCollection;
import com.wlk.mobile.entity.WUser;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户信息 服务类
 * </p>
 *
 * @author hzf
 * @since 2018-12-06
 */
public interface IWUserService extends IService<WUser> {
	
	
	List<WUser> queryBrowseForThree(Integer id);

	Page<WUser> queryCollectionList(Page<WCollection> page, Integer collectionId);
}
