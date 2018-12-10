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
 * 律师形象照
 * </p>
 *
 * @author hzf
 * @since 2018-12-10
 */
@ApiModel
@Data
@Accessors(chain = true)
public class WImage extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	@ApiModelProperty(value = "主键", required = false)
	private Integer id;
    /**
     * 用户id
     */
	@ApiModelProperty(value = "用户id", required = false)
	private Integer userId;
    /**
     * 路径
     */
	@ApiModelProperty(value = "路径", required = false)
	private String path;
    /**
     * 区分图片还是视频（1-图片 2-视频）
     */
	@ApiModelProperty(value = "区分图片还是视频（1-图片 2-视频）", required = false)
	private Integer isImage;

	@Override
	public String toString() {
		return "WImage{" +
			", id=" + id +
			", userId=" + userId +
			", path=" + path +
			", isImage=" + isImage +
			"}";
	}
}
