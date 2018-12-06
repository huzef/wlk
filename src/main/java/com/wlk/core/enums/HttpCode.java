package com.wlk.core.enums;



/**
 * Ajax 请求时的自定义查询状态码，主要参考Http状态码，但并不完全对应
 * 
 */
public enum HttpCode {
	OK("请求成功",200),
	MULTI_STATUS("频繁操作",201),
	NAME_PWD_ERROR("用户名或密码不正确",202),
	NAME_DISABLED("用户被禁用,请联系管理员！",203),
	ADD_ERROR("添加错误",204),
	UPDATE_ERROR("修改错误",2041),
	DEL_ERROR("删除错误",2042),
	MSG_ERROR("验证码错误,请重新获取",205),
	MSG_FAILURE("获取验证码错误,请重新获取",206),
	IS_REGISTER("用户已经存在！",207),
	PWD_ERROR("密码错误",208),
	NAME_ERROR("没有该用户,请联系管理员！",209),
	NAME_IS_NULL("没有该用户,请检查是否已注册！！",209),
	LOGIN_FAIL("登录失败",303),
	BAD_REQUEST("请求参数出错",400),
	IS_NULL("请求参数为空",405),
	UNAUTHORIZED("没有登录",401),
	FORBIDDEN("没有权限",403),
	NOT_FOUND("请求路径不正确",404),
	REQUEST_TIMEOUT("请求超时 ",408),
	CONFLICT("发生冲突",409),
	GONE("已被删除",410),
	LOCKED("已被锁定",423),
	FILE_SERVER_ERROR("上传文件出错",502),
	INTERNAL_SERVER_ERROR("服务器出错",500),
	RUNTIME_ERROR("运行出错",501),
	//第三方状态
	THREE_LOGIN_FAIL("微信code失效",40029),
	THREE_LOGIN("微信code已登录",40163);

	private String desc;
	private int value;
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}

	private HttpCode(String desc, int value) {
		this.desc = desc;
		this.value = value;
	}
}
