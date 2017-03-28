package com.metafour.mtrak.router.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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
public class SitesController {

	private Logger logger = LoggerFactory.getLogger(SitesController.class);
	
	String code="";
	
	@Autowired
	private GeneralLogService generalLogService;
	
	@Autowired
	EventLogService eventLogService;
	
	
	@RequestMapping(value="/sites/{code}", method = RequestMethod.GET)
	public List<GeneralLog> getLogs(@PathVariable String code) {
		List<GeneralLog> rps = new ArrayList<GeneralLog>();
		try {
			if(generalLogService.findByCodeLikeOrderByCode(code).size()>1){
				rps=generalLogService.findByCodeLikeOrderByCode(code);
			}
		} catch (Exception e) {
			logger.error("Failed to prepare data for report");
		}
		return generalLogService.findAll();
	}
	
	
	@RequestMapping(value="/sites/copy", method = RequestMethod.POST)
	public HashMap<String, Object> copyData(@RequestBody GeneralLog generalLog) {
		HashMap<String, Object> res = new HashMap<String, Object>();
		try {
			if(generalLog!=null){
				GeneralLog obj=generalLogService.findByCodeAndType(generalLog.getCode().trim(), generalLog.getType().trim());
				if(obj!=null){
					res.put("message", obj.getCode()+" already exist");
				}else{
					for (EventLog eventLog : eventDatas) {
						System.out.println("Code >>>>>>>>>>>>>>>>>>"+eventLog.getCode());
					}
					generalLogService.copy(generalLog, eventDatas);
					res.put("message", "success");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("messsage  >>>>>."+e.getMessage());
		}
		return res;
	}
	
	@RequestMapping(value="/sites/save", method = RequestMethod.POST)
	public HashMap<String, Object> saveData(@RequestBody GeneralLog generalLog) {
		HashMap<String, Object> res = new HashMap<String, Object>();
		try {
			if(generalLog!=null){
				GeneralLog obj=generalLogService.findByCodeAndType(generalLog.getCode().trim(), generalLog.getType().trim());
				if(obj!=null){
					res.put("message", obj.getCode()+" already exist");
				}else{
					for (EventLog eventLog : eventDatas) {
						System.out.println("Code >>>>>>>>>>>>>>>>>>"+eventLog.getCode());
					}
					generalLogService.save(generalLog);
					res.put("message", "success");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("messsage  >>>>>."+e.getMessage());
		}
		return res;
	}
	
	
	ArrayList<EventLog> eventDatas =new ArrayList<EventLog>();
	@RequestMapping(value="/site/system/{systemCode}/type/{type}", method = RequestMethod.GET)
	@ApiOperation(tags="Event Logs", value="Site Code", notes="Get device upload logs")
	public HashMap<String, Object> fetchByCode(@ApiParam(value = "systemCode", required = true) @PathVariable String systemCode, @PathVariable String type) {
		HashMap<String, Object> response=new HashMap<String, Object>();
		try {
			if (systemCode != null) {
				eventDatas.clear();
				response.put("gData", generalLogService.findByCodeAndType(systemCode, type));
				eventDatas=eventLogService.findAllBySystemCodeAndTypeOrderByCode(systemCode, type);
				response.put("eventList", eventDatas);
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
	
	
	
	
	
	
}
