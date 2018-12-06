package com.wlk.core.util.json;

import java.io.Serializable;

import com.wlk.core.enums.HttpCode;





/**
 * 返回json
 * @author hzf
 *
 */
public class Json implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int code=HttpCode.OK.getValue();
	private String msg=HttpCode.OK.getDesc();
	
	private Object data = null;
	
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Json [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}
	
	
	/**
	 * 返回json
	 * @param code
	 * @param data
	 * @return
	 */
	public static Json ajaxJson(HttpCode code, Object data) {
		Json j = new Json();
		if (code != null) {
			j.setCode(code.getValue());
			j.setMsg(code.getDesc());
		}
		j.setData(data);
		return j;
	}
	/**
	 *返回json 为空处理
	 * @param msg
	 * @return
	 */
	public static Json ajaxJson(String msg) {
		Json j = new Json();
		if (msg != null|| msg!="") {
			j.setCode(HttpCode.OK.getValue());
			j.setMsg(msg);
		}
		return j;
	}
	/**
	 *返回成功json
	 * @param msg
	 * @return
	 */
	public static Json ajaxJson() {
		return new Json();
	}

}
