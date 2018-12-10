package com.wlk.mobile.service.impl;

import com.wlk.mobile.entity.WCollection;
import com.wlk.mobile.entity.WUser;
import com.wlk.mobile.mapper.WUserMapper;
import com.wlk.mobile.service.IWUserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author hzf
 * @since 2018-12-06
 */
@Service
public class WUserServiceImpl extends ServiceImpl<WUserMapper, WUser> implements IWUserService {

	@Autowired
	WUserMapper userMapper;
	@Override
	public List<WUser> queryBrowseForThree(Integer id) {
		
		return userMapper.queryBrowseForThree(id);
	}
	@Override
	public Page<WUser> queryCollectionList(Page<WCollection> page, Integer collectionId) {
		return userMapper.queryCollectionList(page,collectionId);
	}
	
}
