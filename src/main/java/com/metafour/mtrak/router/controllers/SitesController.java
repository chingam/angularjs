package com.metafour.mtrak.router.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.metafour.mtrak.router.entities.GeneralLog;
import com.metafour.mtrak.router.service.GeneralLogService;

@RestController
public class SitesController {

	private Logger logger = LoggerFactory.getLogger(SitesController.class);
	
	@Autowired
	private GeneralLogService generalLogService;
	
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
}
