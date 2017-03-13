package com.metafour.mtrak.router.entities;

import java.io.Serializable;

public class DragAndDrop implements Serializable{

	private static final long serialVersionUID = 4116811742081397426L;
	
	private String code;
	private Integer id;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
