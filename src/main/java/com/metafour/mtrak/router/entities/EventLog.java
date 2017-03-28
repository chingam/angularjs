package com.metafour.mtrak.router.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(MyKey.class)
public class EventLog implements Serializable {


	private static final long serialVersionUID = -2596381502814446253L;
	
	public EventLog(String code, String systemCode, String type, String description, String rqSignature,
			String rqAdditionalText, String rqShelfmark, String lbAdditionalText, String mnAdditionalText) {
		super();
		this.code = code;
		this.systemCode = systemCode;
		this.type = type;
		this.description = description;
		this.rqSignature = rqSignature;
		this.rqAdditionalText = rqAdditionalText;
		this.rqShelfmark = rqShelfmark;
		this.lbAdditionalText = lbAdditionalText;
		this.mnAdditionalText = mnAdditionalText;
	}
	public EventLog() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	@Column(name ="code")
	private String code;
	
	@Id
	@Column
	private String systemCode;
	
	@Id
	@Column
	private String type;
	
	@Column
	private String description;
	
	@Column
	private String rqSignature;
	
	@Column
	private String rqAdditionalText;
	
	@Column
	private String rqShelfmark;
	
	@Column
	private String lbAdditionalText;
	
	@Column
	private String mnAdditionalText;
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRqSignature() {
		return rqSignature;
	}
	public void setRqSignature(String rqSignature) {
		this.rqSignature = rqSignature;
	}
	public String getRqAdditionalText() {
		return rqAdditionalText;
	}
	public void setRqAdditionalText(String rqAdditionalText) {
		this.rqAdditionalText = rqAdditionalText;
	}
	public String getRqShelfmark() {
		return rqShelfmark;
	}
	public void setRqShelfmark(String rqShelfmark) {
		this.rqShelfmark = rqShelfmark;
	}
	public String getLbAdditionalText() {
		return lbAdditionalText;
	}
	public void setLbAdditionalText(String lbAdditionalText) {
		this.lbAdditionalText = lbAdditionalText;
	}
	public String getMnAdditionalText() {
		return mnAdditionalText;
	}
	public void setMnAdditionalText(String mnAdditionalText) {
		this.mnAdditionalText = mnAdditionalText;
	}
	public String getSystemCode() {
		return systemCode;
	}
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
	
	
}
