package com.metafour.mtrak.router.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.metafour.mtrak.router.entities.EventLog;
import com.metafour.mtrak.router.entities.GeneralLog;
import com.metafour.mtrak.router.service.EventLogService;
import com.metafour.mtrak.router.service.GeneralLogService;

@RestController
public class MtrakApplicationController {

	private Logger logger = LoggerFactory.getLogger(MtrakApplicationController.class);
	
	@Autowired
	private GeneralLogService generalLogService;

	@Autowired
	EventLogService eventLogService;
	
	String code="";
	
	@RequestMapping(value="/fetch/sites", method = RequestMethod.GET)
	public List<GeneralLog> fetchingData() {
		return generalLogService.findAll();
	}
	
	
	@RequestMapping(value="/copy", method = RequestMethod.POST)
	public HashMap<String, Object> copyData(@RequestBody GeneralLog generalLog) {
		HashMap<String, Object> res = new HashMap<String, Object>();
		try {
			if(generalLog!=null){
				GeneralLog obj = isExist(generalLog);
				if(obj!=null){
					res.put("message", obj.getCode()+" already exist");
				}else{
					generalLogService.copy(generalLog, logs);
					res.put("message", "success");
				}
			}
		} catch (Exception e) {
			logger.error("{}",e+e.getMessage());
		}
		return res;
	}
	
	@RequestMapping(value="/sites/save", method = RequestMethod.POST)
	public HashMap<String, Object> saveData(@RequestBody GeneralLog generalLog) {
		HashMap<String, Object> res = new HashMap<String, Object>();
		try {
			if(generalLog!=null){
				if(isExist(generalLog)!=null){
					res.put("message", generalLog.getCode()+" already exist");
				}else{
					generalLogService.save(generalLog);
					res.put("message", "success");
				}
			}
		} catch (Exception e) {
			logger.error("{}",e+e.getMessage());
		}
		return res;
	}
	
	ArrayList<EventLog> logs=new ArrayList<EventLog>();
	@RequestMapping(value="/site/system/{systemCode}/type/{type}", method = RequestMethod.GET)
	public HashMap<String, Object> fetchByCode(@PathVariable String systemCode, @PathVariable String type) {
		HashMap<String, Object> response=new HashMap<String, Object>();
		try {
			if (systemCode != null) {
				logs.clear();
				logs=eventLogService.findAllBySystemCodeAndTypeOrderByCode(systemCode, type);
				response.put("gData", generalLogService.findByCodeAndType(systemCode, type));
				response.put("eventList", logs);
				return response;
			} else {
				return null;
			}

		} catch (Exception e) {
			logger.error("Failed to prepare data for report");
		}
		return null;
	}
	
	
	
	@RequestMapping(value="/site/delete/{systemCode}/type/{type}", method = RequestMethod.GET)
	public HashMap<String, Object> delete(@PathVariable String systemCode, @PathVariable String type) {
		HashMap<String, Object> response=new HashMap<String, Object>();
		try {
			if (systemCode != null) {
				GeneralLog obj=new GeneralLog();
				obj.setCode(systemCode);
				obj.setType(type);
				generalLogService.delete(obj);
				response.put("message", "success");
			} else {
				response.put("message", "failed to data delete");
			}

		} catch (Exception e) {
			logger.error("Failed to prepare data for report");
		}
		return response;
	}
	
	
	private GeneralLog isExist(GeneralLog generalLog) {
		GeneralLog obj=generalLogService.findByCodeAndType(generalLog.getCode().trim(), generalLog.getType().trim());
		return obj;
	}
	
	
	
}
