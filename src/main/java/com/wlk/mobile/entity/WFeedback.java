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
 * 意见反馈
 * </p>
 *
 * @author hzf
 * @since 2018-12-21
 */
@ApiModel
@Data
@Accessors(chain = true)
public class WFeedback extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	@ApiModelProperty(value = "主键", required = false)
	private Integer id;
    /**
     * 用户ID外键
     */
	@ApiModelProperty(value = "用户ID外键", required = false)
	private Integer userId;
	@ApiModelProperty(value = "", required = false)
	private String content;


	@Override
	public String toString() {
		return "WFeedback{" +
			", id=" + id +
			", userId=" + userId +
			", content=" + content +
			"}";
	}
}
