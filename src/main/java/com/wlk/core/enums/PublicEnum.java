package com.wlk.core.enums;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 公共状态
 * @author hzf
 *
 */
public enum PublicEnum {

	YES("是", 0), 
	
	NO("否", 1);
	
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

	private PublicEnum(String desc, int value) {
		this.desc = desc;
		this.value = value;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList() {
		PublicEnum[] ary = PublicEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", String.valueOf(ary[i].getValue()));
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}

	public static Map<String, Map<String, Object>> toMap() {
		PublicEnum[] ary = PublicEnum.values();
		Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
		for (int num = 0; num < ary.length; num++) {
			Map<String, Object> map = new HashMap<String, Object>();
			String key = String.valueOf(getEnum(ary[num].getValue()));
			map.put("value", String.valueOf(ary[num].getValue()));
			map.put("desc", ary[num].getDesc());
			enumMap.put(key, map);
		}
		return enumMap;
	}
	
	public static PublicEnum getEnum(int value) {
		PublicEnum resultEnum=null;
		PublicEnum[] arry = PublicEnum.values();
		for (int i = 0; i < arry.length; i++) {
			if (arry[i].getValue() == value) {
				resultEnum = arry[i];
				break;
			}
		}
		return resultEnum;
	}

	
	/**
	 * 取枚举的json字符串
	 *
	 * @return
	 */
	public static String getJsonStr() {
		PublicEnum[] enums = PublicEnum.values();
		StringBuffer jsonStr = new StringBuffer("[");
		for (PublicEnum senum : enums) {
			if (!"[".equals(jsonStr.toString())) {
				jsonStr.append(",");
			}
			jsonStr.append("{id:'").append(senum).append("',desc:'").append(senum.getDesc()).append("'}");
		}
		jsonStr.append("]");
		return jsonStr.toString();
	}
}