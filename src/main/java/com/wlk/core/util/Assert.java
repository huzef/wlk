package com.wlk.core.util;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;


/**
 */
public abstract class Assert {
	/*private static String getMessage(String key, Object... args) {
		//return Resources.getMessage(key, args);
		return null;
	}*/

	/**  */
	public static void isTrue(boolean expression, String key) {
		if (!expression) {
			throw new IllegalArgumentException(key);
		}
	}

	/**  */
	public static void isNull(Object object, String key) {
		if (object != null) {
			throw new IllegalArgumentException(key);
		}
	}

	/** (key_IS_NULL) */
	public static void notNull(Object object, String key, Object... args) {
		if (object == null) {
			throw new IllegalArgumentException(key);
		}
	}

	/**  */
	public static void hasLength(String text, String key) {
		if (StringUtils.isEmpty(text)) {
			throw new IllegalArgumentException(key);
		}
	}

	/**  */
	public static void hasText(String text, String key) {
		if (StringUtils.isBlank(text)) {
			throw new IllegalArgumentException(key);
		}
	}

	/**  */
	public static void doesNotContain(String textToSearch, String substring, String key) {
		if (StringUtils.isNotBlank(textToSearch) && StringUtils.isNotBlank(substring)
				&& textToSearch.contains(substring)) {
			throw new IllegalArgumentException(key);
		}
	}

	/** */
	public static void notEmpty(Object[] array, String key, Object... args) {
		if (ObjectUtils.isEmpty(array)) {
			throw new IllegalArgumentException(key);
		}
	}

	/**  */
	public static void noNullElements(Object[] array, String key) {
		if (array != null) {
			for (Object element : array) {
				if (element == null) {
					throw new IllegalArgumentException(key);
				}
			}
		}
	}

	/**  */
	public static void notEmpty(Collection<?> collection, String key) {
		if (CollectionUtils.isEmpty(collection)) {
			throw new IllegalArgumentException(key);
		}
	}

	/**  */
	public static void notEmpty(Map<?, ?> map, String key) {
		if (CollectionUtils.isEmpty(map)) {
			throw new IllegalArgumentException(key);
		}
	}

	/**  */
	public static void isInstanceOf(Class<?> type, Object obj, String key) {
		notNull(type, key);
		if (!type.isInstance(obj)) {
			throw new IllegalArgumentException(key);
		}
	}

	/**  */
	public static void isAssignable(Class<?> superType, Class<?> subType, String key) {
		notNull(superType, key);
		if (subType == null || !superType.isAssignableFrom(subType)) {
			throw new IllegalArgumentException(key);
		}
	}

	/** 空字符或NULL */
	public static void isBlank(String text, String key) {
		if (StringUtils.isNotBlank(text)) {
			throw new IllegalArgumentException(key);
		}
	}

	/** 非空字符串(key_IS_NULL) */
	public static void isNotBlank(String text, String key) {
		if (StringUtils.isBlank(text)) {
			throw new IllegalArgumentException(key);
		}
	}

	/** 允许最小值 */
	public static void min(Integer value, Integer min, String key) {
		notNull(value, key);
		if (value < min) {
			throw new IllegalArgumentException(key);
		}
	}

	/** 允许最大值 */
	public static void max(Integer value, Integer max, String key) {
		notNull(value, key);
		if (value > max) {
			throw new IllegalArgumentException(key);
		}
	}

	/** 允许值范围 */
	public static void range(Integer value, Integer min, Integer max, String key) {
		min(value, min, key);
		max(value, max, key);
	}

	/** 允许最小值 */
	public static void min(Float value, Float min, String key) {
		notNull(value, key);
		if (value < min) {
			throw new IllegalArgumentException(key);
		}
	}

	/** 允许最大值 */
	public static void max(Float value, Float max, String key) {
		notNull(value, key);
		if (value > max) {
			throw new IllegalArgumentException(key);
		}
	}

	/** 允许值范围 */
	public static void range(Float value, Float min, Float max, String key) {
		min(value, min, key);
		max(value, max, key);
	}

	/** 允许最小值 */
	public static void min(Double value, Double min, String key) {
		notNull(value, key);
		if (value < min) {
			throw new IllegalArgumentException(key);
		}
	}

	/** 允许最大值 */
	public static void max(Double value, Double max, String key) {
		notNull(value, key);
		if (value > max) {
			throw new IllegalArgumentException(key);
		}
	}

	/** 允许值范围 */
	public static void range(Double value, Double min, Double max, String key) {
		min(value, min, key);
		max(value, max, key);
	}

	/** 字符长度(key_LENGTH) */
	public static void length(String text, Integer min, Integer max, String key) {
		notNull(text, key);
		if (min != null && text.length() < min) {
			throw new IllegalArgumentException(key);
		}
		if (max != null && text.length() > max) {
			throw new IllegalArgumentException(key);
		}
	}

	/** 未来某一天 */
	public static void future(Date date, String key) {
		if (date != null && date.compareTo(new Date()) <= 0) {
			throw new IllegalArgumentException(key);
		}
	}


	/** 邮箱 */
	public static void email(String text) {
		String regex = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		pattern(text, regex, true, "邮箱格式错误");
	}

	/** 手机号 */
	public static void mobile(String text) {
		String regex = "((^(13|15|17|18)[0-9]{9}$)|(^(199|198|166|146|148)[0-9]{8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
		pattern(text, regex, true, "手机号格式错误");
	}
	
	public static void main(String[] args) {
		String text = "147";
		text += "12345678";
		mobile(text);
	}

	/** 正则表达式 */
	public static void pattern(String text, String regex, boolean flag, String key) {
		boolean result = false;
		try {
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(text);
			result = matcher.matches();
		} catch (Exception e) {
			result = false;
		}
		if (result != flag) {
			throw new IllegalArgumentException(key);
		}
	}
}
