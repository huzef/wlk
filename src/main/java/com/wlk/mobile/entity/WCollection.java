package com.wlk.mobile.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.util.Date;

import com.wlk.core.base.BaseEntity;


import lombok.Data;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * <p>
 * 收藏
 * </p>
 *
 * @author hzf
 * @since 2018-12-06
 */
@ApiModel
@Data
@Accessors(chain = true)
public class WCollection extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	@ApiModelProperty(value = "主键", required = false)
	private Integer id;
    /**
     * 被收藏者ID
     */
	@ApiModelProperty(value = "被收藏者ID", required = false)
	private Integer userId;
    /**
     * 收藏者ID
     */
	@ApiModelProperty(value = "收藏者ID", required = false)
	private Integer collectionId;
    /**
     * 是否取消(1-收藏 2-取消)
     */
	@ApiModelProperty(value = "是否取消(1-收藏 2-取消)", required = false)
	private Integer isDel;
    /**
     * 修改时间
     */
	@ApiModelProperty(value = "修改时间", required = false)
	private Date updateDate;


	@Override
	public String toString() {
		return "WCollection{" +
			", id=" + id +
			", userId=" + userId +
			", collectionId=" + collectionId +
			", isDel=" + isDel +
			", updateDate=" + updateDate +
			"}";
	}
}
