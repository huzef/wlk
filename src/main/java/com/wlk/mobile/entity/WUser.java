package com.wlk.mobile.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * 用户信息
 * </p>
 *
 * @author hzf
 * @since 2018-12-06
 */
@ApiModel
@Data
@Accessors(chain = true)
public class WUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	@ApiModelProperty(value = "主键", required = false)
	private Integer id;
    /**
     * 微信openid
     */
	@ApiModelProperty(value = "微信openid", required = false)
	private String wOpid;
    /**
     * 名字
     */
	@ApiModelProperty(value = "名字", required = false)
	private String firstName;
    /**
     * 头像本地文件路径
     */
	@ApiModelProperty(value = "头像本地文件路径", required = false)
	private String photoFilePath;
	/**
	 * 头像本地文件路径
	 */
	@ApiModelProperty(value = "性别", required = false)
	private Integer gender;
    /**
     * 昵称
     */
	@ApiModelProperty(value = "昵称", required = false)
	private String nickName;
    /**
     * 手机号
     */
	@ApiModelProperty(value = "手机号", required = false)
	private String mobilePhoneNumber;
    /**
     * 微信号
     */
	@ApiModelProperty(value = "微信号", required = false)
	private String wechatNumber;
    /**
     * 公司名
     */
	@ApiModelProperty(value = "公司名", required = false)
	private String organization;
    /**
     * 头衔
     */
	@ApiModelProperty(value = "头衔", required = false)
	private String honor;
    /**
     * 擅长领域
     */
	@ApiModelProperty(value = "擅长领域", required = false)
	private String field;
    /**
     * 个人介绍
     */
	@ApiModelProperty(value = "个人介绍", required = false)
	private String introduce;
    /**
     * 邮箱
     */
	@ApiModelProperty(value = "邮箱", required = false)
	private String email;
    /**
     * 地址
     */
	@ApiModelProperty(value = "地址", required = false)
	private String address;
    /**
     * 律师证编号
     */
	@ApiModelProperty(value = "律师证编号", required = false)
	private String lawNum;
    /**
     * 是否是律师（1-是 2-否）
     */
	@ApiModelProperty(value = "是否是律师（1-是 2-否）", required = false)
	private Integer isLaw;
    /**
     * 是否禁用（1-正常 2-禁用）
     */
	@ApiModelProperty(value = "是否禁用（1-正常 2-禁用）", required = false)
	private Integer isDel;
    /**
     * 最后登录时间
     */
	@ApiModelProperty(value = "最后登录时间", required = false)
	private Date lastLoginDate;

	/*=========================非数据库字段==========================*/
	@TableField(exist=false)
	private String sessionKey;
	

	@Override
	public String toString() {
		return "WUser{" +
			", id=" + id +
			", wOpid=" + wOpid +
			", firstName=" + firstName +
			", photoFilePath=" + photoFilePath +
			", nickName=" + nickName +
			", mobilePhoneNumber=" + mobilePhoneNumber +
			", wechatNumber=" + wechatNumber +
			", organization=" + organization +
			", honor=" + honor +
			", field=" + field +
			", introduce=" + introduce +
			", email=" + email +
			", address=" + address +
			", lawNum=" + lawNum +
			", isLaw=" + isLaw +
			", isDel=" + isDel +
			", lastLoginDate=" + lastLoginDate +
			"}";
	}
}
