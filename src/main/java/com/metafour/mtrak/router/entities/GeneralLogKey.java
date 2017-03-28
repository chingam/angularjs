package com.metafour.mtrak.router.entities;

import java.io.Serializable;

public class GeneralLogKey implements Serializable {

	
	private static final long serialVersionUID = 281234918918153158L;
	private String code;
	private String type;
	
	
	public GeneralLogKey() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GeneralLogKey(String code, String type) {
		super();
		this.code = code;
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	}
