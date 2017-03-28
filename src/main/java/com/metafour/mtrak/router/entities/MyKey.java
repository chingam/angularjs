package com.metafour.mtrak.router.entities;

import java.io.Serializable;

public class MyKey implements Serializable {
	private static final long serialVersionUID = -3618618673001276639L;
	private String code;
	private String systemCode;
	private String type;
	
	
	public MyKey(String code, String systemCode, String type) {
		super();
		this.code = code;
		this.systemCode = systemCode;
		this.type = type;
	}
	public MyKey() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSystemCode() {
		return systemCode;
	}
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
	
	
	
	
	}
