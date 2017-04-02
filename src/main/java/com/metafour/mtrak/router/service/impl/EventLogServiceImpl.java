package com.metafour.mtrak.router.service.impl;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
	ArrayList<EventLog> eventDatas = new ArrayList<EventLog>();

	@Autowired
	EventLogRepo eventLogRepo;

	@Override
	@Transactional(readOnly = true)
	public EventLog findBySystemCodeAndTypeAndCode(String systemCode, String type, String code) {
		return eventLogRepo.findBySystemCodeAndTypeAndCode(systemCode, type, code);
	}

	@Override
	@Transactional(readOnly = true)
	public ArrayList<EventLog> findAllBySystemCodeAndTypeOrderByCode(String systemCode, String type) {
		return eventLogRepo.findAllBySystemCodeAndTypeOrderByCode(systemCode,type);
	}

	@Override
	@Transactional
	public EventLog save(EventLog obj) {
		return eventLogRepo.save(obj);
	}

	@Override
	@Transactional
	public void delete(EventLog obj) {
		eventLogRepo.delete(obj);
	}

	@Override
	public boolean checkDuplicate(EventLog eventLog) {
		for (int i = 0; i < eventDatas.size(); i++) {
			EventLog eLog = eventDatas.get(i);
			if (eLog.getCode().equalsIgnoreCase(eventLog.getCode())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void addEvent(EventLog eventLog) {
		eventDatas.add(eventLog);
	}

	@Override
	public void modifiedEvent(EventLog eventLog) {
		ArrayList<EventLog> eventList = (ArrayList<EventLog>) eventDatas.stream().filter(r -> r.getCode().equals(eventLog.getCode().trim())).collect(Collectors.toList());
		if (!eventList.isEmpty()) {
			eventDatas.remove(eventList.get(0));
			eventDatas.add(eventLog);
		}
	}

	@Override
	public void deleteEvent(String eventCode) {
		if (!eventDatas.isEmpty()) {
			for (int i = 0; i < eventDatas.size(); i++) {
				if (eventDatas.get(i).getCode().equals(eventCode)) {
					eventDatas.remove(i);
					System.out.println("delete data >>>>>>>");
					System.out.println(eventDatas.size());
				}
			}
		}

	}

	@Override
	public ArrayList<EventLog> eventList() {
		return eventDatas;
	}

	@Override
	public void clearEventList() {
		eventDatas.clear();
	}

}