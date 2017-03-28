package com.metafour.mtrak.router.service.impl;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metafour.mtrak.router.entities.EventLog;
import com.metafour.mtrak.router.repositories.EventLogRepo;
import com.metafour.mtrak.router.service.EventLogService;

/**
 * @author minhaj
 *
 */
@Service
public class EventLogServiceImpl implements EventLogService {
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(EventLogServiceImpl.class);

	@Autowired
	EventLogRepo eventLogRepo;
	
	@Override
	@Transactional(readOnly = true)
	public EventLog findBySystemCodeAndTypeAndCode(String systemCode, String type, String code){
		// TODO Auto-generated method stub
		return eventLogRepo.findBySystemCodeAndTypeAndCode(systemCode, type, code);
	}

	@Override
	@Transactional(readOnly = true)
	public ArrayList<EventLog> findAllBySystemCodeAndTypeOrderByCode(String systemCode, String type) {
		// TODO Auto-generated method stub
		return eventLogRepo.findAllBySystemCodeAndTypeOrderByCode(systemCode, type);
	}

	@Override
	@Transactional
	public EventLog save(EventLog obj) {
		// TODO Auto-generated method stub
		return eventLogRepo.save(obj);
	}

	@Override
	@Transactional
	public void delete(EventLog obj) {
		eventLogRepo.delete(obj);
	}
	

}