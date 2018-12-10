package com.wlk.mobile.mapper;

import com.wlk.mobile.entity.WCollection;
import com.wlk.mobile.entity.WUser;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
  * 用户信息 Mapper 接口
 * </p>
 *
 * @author hzf
 * @since 2018-12-06
 */
public interface WUserMapper extends BaseMapper<WUser> {
	
	List<WUser> queryBrowseForThree(Integer id);
	
	Page<WUser> queryCollectionList(Page<WCollection> page,@Param("id")Integer id);

}