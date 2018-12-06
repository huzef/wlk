package com.wlk.core.base;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.wlk.core.enums.HttpCode;
import com.wlk.core.exception.BaseException;
import com.wlk.core.exception.IllegalParameterException;
import com.wlk.core.util.json.Json;








/**
 * 控制器基类
 * @author hzf
 * @version 2017-09-01
 * @param <T>
 * @param <T>
 *
 */
@SuppressWarnings("rawtypes")
public class BaseController<T extends Model>   {

	final static Logger logger = LoggerFactory.getLogger(BaseController.class);
	/**
	 * 返回json
	 * @param code
	 * @param data
	 * @return
	 */
	protected Json ajaxJson(HttpCode code, Object data) {
		return Json.ajaxJson(code, data);
	}
	protected Json ajaxJson(Object data) {
		Json j = new Json();
		j.setCode(HttpCode.OK.getValue());
		j.setMsg(HttpCode.OK.getDesc());
		j.setData(data);
		return j;
	}
	/**
	 *返回json 为空处理
	 * @param msg
	 * @return
	 */
	protected Json ajaxJson(String msg) {
		return Json.ajaxJson(msg);
	}
	/**
	 *返回成功json
	 * @param msg
	 * @return
	 */
	protected Json ajaxJson() {
		return Json.ajaxJson();
	}
	
	/** 异常处理 */
	@ExceptionHandler(Exception.class)
	public void exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex)
			throws Exception {
		logger.error("OH,MY GOD! SOME ERRORS OCCURED! AS FOLLOWS :", ex);
		ModelMap modelMap = new ModelMap();
		if (ex instanceof BaseException) {
			((BaseException) ex).handler(modelMap);
		} else if (ex instanceof IllegalArgumentException) {
			new IllegalParameterException(ex.getMessage()).handler(modelMap);
		} else if(ex instanceof MultipartException){
			
			modelMap.put("code", HttpCode.FILE_SERVER_ERROR.getValue());
			String msg = StringUtils.defaultIfBlank(ex.getMessage(), HttpCode.FILE_SERVER_ERROR.getDesc());
			modelMap.put("msg", msg.length() > 100 ? "系统走神了,请稍候再试." : msg);
		}else {
			modelMap.put("code", HttpCode.INTERNAL_SERVER_ERROR.getValue());
			String msg = StringUtils.defaultIfBlank(ex.getMessage(), HttpCode.INTERNAL_SERVER_ERROR.getDesc());
			modelMap.put("msg", msg.length() > 100 ? "系统走神了,请稍候再试." : msg);
		}
		response.setContentType("application/json;charset=UTF-8");
		modelMap.put("timestamp", System.currentTimeMillis());
		logger.info((String) JSON.toJSON(modelMap));
		byte[] bytes = JSON.toJSONBytes(modelMap, SerializerFeature.DisableCircularReferenceDetect);
		//response.getWriter().append(modelMap.toString());
		response.getOutputStream().write(bytes);
	}
}
