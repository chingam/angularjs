package com.metafour.mtrak.router.service;

import java.util.ArrayList;

import com.metafour.mtrak.router.entities.EventLog;

/**
 * @author minhaj
 *
 */
public interface EventLogService {
	EventLog findBySystemCodeAndTypeAndCode(String systemCode, String type, String code);
	ArrayList<EventLog> findAllBySystemCodeAndTypeOrderByCode(String systemCode, String type);
	EventLog save(EventLog obj);
	void delete(EventLog obj);
	boolean checkDuplicate(EventLog eventLog);
	void addEvent(EventLog eventLog);
	void modifiedEvent(EventLog eventLog);
	void deleteEvent(String eventCode);
	ArrayList<EventLog> eventList();
	void clearEventList();
}
