package com.wlk.mobile.service.impl;

import com.wlk.mobile.entity.WUser;
import com.wlk.mobile.mapper.WUserMapper;
import com.wlk.mobile.service.IWUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
	
}
