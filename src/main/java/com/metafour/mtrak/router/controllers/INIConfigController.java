package com.metafour.mtrak.router.controllers;

import java.util.ArrayList;
import java.util.HashMap;

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

/**
 * @author Minhaj
 *
 */
@RestController
public class INIConfigController {
	private Logger logger = LoggerFactory.getLogger(INIConfigController.class);

	@Autowired
	EventLogService eventLogService;

	@Autowired
	GeneralLogService generalLogService;
	String code = "";
	String type = "";

	@RequestMapping(value = "/cache/{code}/type/{type}", method = RequestMethod.GET)
	public HashMap<String, Object> setSession(@PathVariable String code, @PathVariable String type) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		if (code != null) {
			this.code = code;
			this.type = type;
			response.put("message", "success");
		} else {
			response.put("message", "fail");
		}
		return response;
	}

	// TODO
	@RequestMapping(value = "/getCache", method = RequestMethod.GET)
	public HashMap<String, Object> getSession() {
		HashMap<String, Object> response = new HashMap<String, Object>();
		if (this.code != null && !this.code.equalsIgnoreCase("")) {
			response.put("code", this.code);
			response.put("type", this.type);
		} else {
			response.put("message", "fail");
		}
		return response;
	}

	// TODO
	@RequestMapping(value = "/generalConfig", method = RequestMethod.POST)
	public HashMap<String, Object> generalDataSave(@RequestBody GeneralLog generalLog) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		if (generalLog != null) {
			generalLogService.save(generalLog, eventLogService.eventList());
			response.put("message", "Success");
		} else {
			response.put("message", "operation fail");
		}
		return response;
	}

	// TODO
	@RequestMapping(value = "/fetch/clear", method = RequestMethod.GET)
	public HashMap<String, Object> removeAllList() {
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			eventLogService.clearEventList();
		} catch (Exception e) {
			logger.error("Failed to clean");
		}
		return response;
	}

	// TODO
	@RequestMapping(value = "/fetch/system/{systemCode}/type/{type}", method = RequestMethod.GET)
	public HashMap<String, Object> fetchByCode(@PathVariable String systemCode, @PathVariable String type) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if (systemCode != null) {
				ArrayList<EventLog> datas = eventLogService.findAllBySystemCodeAndTypeOrderByCode(systemCode, type);
				eventLogService.clearEventList();
				datas.stream().forEach(d -> {
					eventLogService.addEvent(d);
				});
				response.put("gData", generalLogService.findByCodeAndType(systemCode, type));
				response.put("eventList", datas);
				return response;
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("Failed to fetching data");
		}
		return null;
	}

	// TODO
	@RequestMapping(value = "/eventConfig2", method = RequestMethod.POST)
	public HashMap<String, Object> eventDataSave2(@RequestBody EventLog eventLog) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		if (eventLog != null) {
			if (eventLogService.checkDuplicate(eventLog)) {
				response.put("message", eventLog.getCode() + " already exist");
			} else {
				eventLogService.addEvent(eventLog);
				response.put("message", "success");
			}
		} else {
			response.put("message", "not success");
		}
		return response;
	}

	// TODO
	@RequestMapping(value = "/eventConfigu/update", method = RequestMethod.POST)
	public HashMap<String, Object> eventUpdate(@RequestBody EventLog eventLog) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		if (eventLog != null) {
			eventLogService.modifiedEvent(eventLog);
			response.put("message", "Success");
		} else {
			response.put("message", "operation fail");
		}
		return response;
	}

	// TODO
	@RequestMapping(value = "/delete2/{code}", method = RequestMethod.GET)
	public HashMap<String, Object> delete2(@PathVariable String code) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		if (code != null) {
			eventLogService.deleteEvent(code);
			response.put("message", "Success");
		} else {
			response.put("message", "operation fail");
		}
		return response;
	}

	// TODO
	@RequestMapping(value = "/fetch/all", method = RequestMethod.GET)
	public ArrayList<EventLog> fetchAll() {
		return eventLogService.eventList();
	}

	@RequestMapping(value = "/fetch/{code}/type/{type}", method = RequestMethod.GET)
	public ArrayList<EventLog> getLogs(@PathVariable String code, @PathVariable String type) {
		try {
			ArrayList<EventLog> eventLogs = eventLogService.findAllBySystemCodeAndTypeOrderByCode(code, type);
			if (!eventLogs.isEmpty() && eventLogs != null) {
				eventLogService.clearEventList();
				eventLogs.stream().forEach(l -> {
					eventLogService.addEvent(l);
				});
				return eventLogs;
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("Failed to prepare data for report");
		}
		return null;
	}

	@RequestMapping(value = "/delete/{systemCode}/{code}/type/{type}", method = RequestMethod.GET)
	public HashMap<String, Object> delete(@PathVariable String code, @PathVariable String systemCode, @PathVariable String type) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			EventLog eventLogs = eventLogService.findBySystemCodeAndTypeAndCode(systemCode, type, code);
			if (eventLogs != null) {
				eventLogService.delete(eventLogs);
				response.put("message", "Success");
			}
		} catch (Exception e) {
			logger.error("Failed to data delete");
		}
		return response;
	}

}
