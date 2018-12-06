package com.wlk.mobile.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wlk.core.base.BaseEntity;


import lombok.Data;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * <p>
 * 点赞
 * </p>
 *
 * @author hzf
 * @since 2018-12-06
 */
@ApiModel
@Data
@Accessors(chain = true)
public class WLike extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	@ApiModelProperty(value = "主键", required = false)
	private Integer id;
    /**
     * 被点赞者
     */
	@ApiModelProperty(value = "被点赞者", required = false)
	private Integer userId;
    /**
     * 点赞者id
     */
	@ApiModelProperty(value = "点赞者id", required = false)
	private Integer likeId;


	@Override
	public String toString() {
		return "WLike{" +
			", id=" + id +
			", userId=" + userId +
			", likeId=" + likeId +
			"}";
	}
}
