package com.metafour.mtrak.router.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class GeneralLog implements Serializable{

	private static final long serialVersionUID = -5211490759059570649L;
	
	@Id
	@Column(name ="code")
	private String code;
	
	@Column(length=1000)
	private String logo;
	
	@Column(length=1000)
	private String versionFileUrl;
	
	@Column(length=1000)
	private String description;
	
	@Column
	private String type;
	
	@Column
	private String email;
	
	@Column(length=1000)
	private String uploadURL;
	
	@Column(length=1000)
	private String uploadPath;
	
	@Column(length=1000)
	private String processURL;
	
	@Column
	private Integer dataCheckInterval;
	
	@Column
	private Integer blockScanLimit;
	
	@Column
	private Integer warningScanLimit;
	
	@Column
	private Integer maxUploadSize;
	
	@Column
	private Integer uploadSleepInterval;
	
	@Column
	private String jobCreate;
	
	@Column
	private Integer deliveryTimeDuration;
	
	@Column
	private String validateMessenger;
	
	@Column
	private String gPSEnable;
	
	
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getVersionFileUrl() {
		return versionFileUrl;
	}
	public void setVersionFileUrl(String versionFileUrl) {
		this.versionFileUrl = versionFileUrl;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUploadURL() {
		return uploadURL;
	}
	public void setUploadURL(String uploadURL) {
		this.uploadURL = uploadURL;
	}
	public String getUploadPath() {
		return uploadPath;
	}
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	public String getProcessURL() {
		return processURL;
	}
	public void setProcessURL(String processURL) {
		this.processURL = processURL;
	}
	public Integer getDataCheckInterval() {
		return dataCheckInterval;
	}
	public void setDataCheckInterval(Integer dataCheckInterval) {
		this.dataCheckInterval = dataCheckInterval;
	}
	public Integer getBlockScanLimit() {
		return blockScanLimit;
	}
	public void setBlockScanLimit(Integer blockScanLimit) {
		this.blockScanLimit = blockScanLimit;
	}
	public Integer getWarningScanLimit() {
		return warningScanLimit;
	}
	public void setWarningScanLimit(Integer warningScanLimit) {
		this.warningScanLimit = warningScanLimit;
	}
	public Integer getMaxUploadSize() {
		return maxUploadSize;
	}
	public void setMaxUploadSize(Integer maxUploadSize) {
		this.maxUploadSize = maxUploadSize;
	}
	public Integer getUploadSleepInterval() {
		return uploadSleepInterval;
	}
	public void setUploadSleepInterval(Integer uploadSleepInterval) {
		this.uploadSleepInterval = uploadSleepInterval;
	}
	public String getJobCreate() {
		return jobCreate;
	}
	public void setJobCreate(String jobCreate) {
		this.jobCreate = jobCreate;
	}
	public Integer getDeliveryTimeDuration() {
		return deliveryTimeDuration;
	}
	public void setDeliveryTimeDuration(Integer deliveryTimeDuration) {
		this.deliveryTimeDuration = deliveryTimeDuration;
	}
	public String getValidateMessenger() {
		return validateMessenger;
	}
	public void setValidateMessenger(String validateMessenger) {
		this.validateMessenger = validateMessenger;
	}
	public String getgPSEnable() {
		return gPSEnable;
	}
	public void setgPSEnable(String gPSEnable) {
		this.gPSEnable = gPSEnable;
	}
	
	
}
