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
 * 浏览记录
 * </p>
 *
 * @author hzf
 * @since 2018-12-06
 */
@ApiModel
@Data
@Accessors(chain = true)
public class WBrowse extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	@ApiModelProperty(value = "主键", required = false)
	private Integer id;
    /**
     * 被浏览者Id
     */
	@ApiModelProperty(value = "被浏览者Id", required = false)
	private Integer userId;
    /**
     * 浏览着id
     */
	@ApiModelProperty(value = "浏览着id", required = false)
	private Integer browseId;


	@Override
	public String toString() {
		return "WBrowse{" +
			", id=" + id +
			", userId=" + userId +
			", browseId=" + browseId +
			"}";
	}
}
