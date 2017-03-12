package com.metafour.mtrak.router.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.metafour.mtrak.router.exceptions.MtrakRouterException;

/**
 * @author noor
 *
 */
@Configuration
@ConfigurationProperties(prefix = "mtrak")
public class MTrakConfig {
	private static Logger logger = LoggerFactory.getLogger(MTrakConfig.class);
	private String cachePath = "/tmp/mtrak-router";
	private List<Config> otherInis = new ArrayList<Config>();
	private Config defaultIni = new Config();
	private Boolean useDeviceTimeZone = true;

	public String getCachePath() {
		return cachePath;
	}

	public void setCachePath(String cachePath) {
		this.cachePath = cachePath;
	}

	public List<Config> getOtherInis() {
		return otherInis;
	}

	public void setOtherInis(List<Config> otherInis) {
		this.otherInis = otherInis;
	}

	public Config getDefaultIni() {
		return defaultIni;
	}

	public void setDefaultIni(Config defaultIni) {
		this.defaultIni = defaultIni;
	}

	public Boolean getUseDeviceTimeZone() {
		return useDeviceTimeZone;
	}

	public void setUseDeviceTimeZone(Boolean useDeviceTimeZone) {
		this.useDeviceTimeZone = useDeviceTimeZone;
	}

	public String getIniUrl(String code) {
		Config cf = getConfig(code);
		return cf.getPrefix() + code + cf.getSuffix();
	}

	public Config getConfig(String code) {
		Config cf = null;
		for (Config c : otherInis) {
			if (c.getCodes().contains(code)) {
				cf = c;
				break;
			}
		}
		return cf == null ? defaultIni : cf;
	}
	
	public String getSiteCachePath(String code) throws MtrakRouterException {
		String siteCachePath = getCachePath() + File.separator + code;
		try {
			new File(siteCachePath).mkdirs();
		} catch (SecurityException e) {
			logger.error("Failed to create directory {}", siteCachePath);
			throw new MtrakRouterException("Please contact site administrator and refer the error code FP500.");
		}
		return siteCachePath;
	}

	public static class Config {
		private List<String> codes = new ArrayList<String>();
		private String prefix = "https://ms.m4.net/mtrak/config/";
		private String suffix = ".ini";
		private String valueSeparator = "=";
		private String colorsLabel = "Colors";
		private String colorsSeparator = ",";
		private String coordinateLabel = "IconCoordinate";
		private String coordinateSeparator = ",";
		private String jobcreateLabel = "JobCreate";
		private String validatemessengerLabel = "ValidateMessenger";
		private String deliverytimedurationLabel = "DeliveryTimeDuration";
		private Boolean checkUploadResponse = true;
		private String uploadResponseIdentifier = ":";
		private Boolean storeUploads = false;
		private String gpsEnableLabel = "GPSEnable";
		
		public Config(){}
		
		public Config(String prefix, String suffix){
			this.prefix = prefix;
			this.suffix = suffix;
		}

		public List<String> getCodes() {
			return codes;
		}

		public void setCodes(List<String> codes) {
			this.codes = codes;
		}

		public String getPrefix() {
			return prefix;
		}

		public void setPrefix(String prefix) {
			this.prefix = prefix;
		}

		public String getSuffix() {
			return suffix;
		}

		public void setSuffix(String suffix) {
			this.suffix = suffix;
		}

		public String getValueSeparator() {
			return valueSeparator;
		}

		public void setValueSeparator(String valueSeparator) {
			this.valueSeparator = valueSeparator;
		}

		public String getColorsLabel() {
			return colorsLabel;
		}

		public void setColorsLabel(String colorsLabel) {
			this.colorsLabel = colorsLabel;
		}

		public String getColorsSeparator() {
			return colorsSeparator;
		}

		public void setColorsSeparator(String colorsSeparator) {
			this.colorsSeparator = colorsSeparator;
		}

		public String getCoordinateLabel() {
			return coordinateLabel;
		}

		public void setCoordinateLabel(String coordinateLabel) {
			this.coordinateLabel = coordinateLabel;
		}

		public String getCoordinateSeparator() {
			return coordinateSeparator;
		}

		public void setCoordinateSeparator(String coordinateSeparator) {
			this.coordinateSeparator = coordinateSeparator;
		}

		public String getJobcreateLabel() {
			return jobcreateLabel;
		}

		public void setJobcreateLabel(String jobcreateLabel) {
			this.jobcreateLabel = jobcreateLabel;
		}

		public String getValidatemessengerLabel() {
			return validatemessengerLabel;
		}

		public void setValidatemessengerLabel(String validatemessengerLabel) {
			this.validatemessengerLabel = validatemessengerLabel;
		}

		public String getDeliverytimedurationLabel() {
			return deliverytimedurationLabel;
		}

		public void setDeliverytimedurationLabel(String deliverytimedurationLabel) {
			this.deliverytimedurationLabel = deliverytimedurationLabel;
		}

		public Boolean getCheckUploadResponse() {
			return checkUploadResponse;
		}

		public void setCheckUploadResponse(Boolean checkUploadResponse) {
			this.checkUploadResponse = checkUploadResponse;
		}

		public String getUploadResponseIdentifier() {
			return uploadResponseIdentifier;
		}

		public void setUploadResponseIdentifier(String uploadResponseIdentifier) {
			this.uploadResponseIdentifier = uploadResponseIdentifier;
		}

		public Boolean getStoreUploads() {
			return storeUploads;
		}

		public void setStoreUploads(Boolean storeUploads) {
			this.storeUploads = storeUploads;
		}

		public String getGpsEnableLabel() {
			return gpsEnableLabel;
		}

		public void setGpsEnableLabel(String gpsEnableLabel) {
			this.gpsEnableLabel = gpsEnableLabel;
		}
	}
}
