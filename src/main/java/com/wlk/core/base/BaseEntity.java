package com.wlk.core.base;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 基础类
 * @author Admin
 *
 */
@Data
@Accessors(chain = true)
public class BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date createDate;

	@Override
	public String toString() {
		return "BaseEntity [createDate=" + createDate + "]";
	}
	

	

}
