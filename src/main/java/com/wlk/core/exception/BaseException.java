package com.wlk.core.exception;

import org.apache.commons.lang3.StringUtils;

import org.springframework.ui.ModelMap;

import com.wlk.core.enums.HttpCode;







/**
 * 业务异常基类，所有业务异常继承于此异常
 * 
 * @author hzf
 * 
 *   定义异常时，需要先确定异常所属模块。例如：添加合同报错 可以定义为 [10010001] 前四位数为系统模块编号，后4位为错误代码 ,唯一 <br>
 *         用户服务异常 1001 <br>
 */
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = -5875371379845226068L;

	/**
	 * 数据库操作,insert返回0
	 */
	public static final BaseException DB_INSERT_RESULT_0 = new BaseException(90040001, "数据库操作,insert返回0");

	/**
	 * 数据库操作,update返回0
	 */
	public static final BaseException DB_UPDATE_RESULT_0 = new BaseException(90040002, "数据库操作,update返回0");

	/**
	 * 数据库操作,selectOne返回null
	 */
	public static final BaseException DB_SELECTONE_IS_NULL = new BaseException(90040003, "数据库操作,selectOne返回null");

	/**
	 * 数据库操作,list返回null
	 */
	public static final BaseException DB_LIST_IS_NULL = new BaseException(90040004, "数据库操作,list返回null");

	/**
	 * Token 验证不通过
	 */
	public static final BaseException TOKEN_IS_ILLICIT = new BaseException(90040005, "Token 验证非法");
	/**
	 * 会话超时　获取session时，如果是空，throws 下面这个异常 拦截器会拦截爆会话超时页面
	 */
	public static final BaseException SESSION_IS_OUT_TIME = new BaseException(90040006, "会话超时");

	/**
	 * 获取序列出错
	 */
	public static final BaseException DB_GET_SEQ_NEXT_VALUE_ERROR = new BaseException(90040007, "获取序列出错");

	/**
	 * 异常信息
	 */
	protected String msg;

	/**
	 * 具体异常码
	 */
	protected int code;

	public BaseException(int code, String msgFormat, Object... args) {
		super(String.format(msgFormat, args));
		this.code = code;
		this.msg = String.format(msgFormat, args);
	}

	public BaseException() {
		super();
	}

	public String getMsg() {
		return msg;
	}

	public int getCode() {
		return code;
	}

	/**
	 * 实例化异常
	 * 
	 * @param msgFormat
	 * @param args
	 * @return
	 */
	public BaseException newInstance(String msgFormat, Object... args) {
		return new BaseException(this.code, msgFormat, args);
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}

	public BaseException(String message) {
		super(message);
	}

	public void handler(ModelMap modelMap) {
		modelMap.put("code", HttpCode.RUNTIME_ERROR.getValue());
		if (StringUtils.isNotBlank(getMessage())) {
			modelMap.put("msg", getMessage());
		} else {
			modelMap.put("msg", HttpCode.RUNTIME_ERROR.getDesc());
		}
		modelMap.put("timestamp", System.currentTimeMillis());
	}
	

}
