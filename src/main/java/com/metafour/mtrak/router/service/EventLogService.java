package com.metafour.mtrak.router.service;

import java.util.ArrayList;

import com.metafour.mtrak.router.entities.EventLog;

/**
 * @author noor
 *
 */
public interface EventLogService {
	EventLog findBySystemCodeAndTypeAndCode(String systemCode, String type, String code);
	ArrayList<EventLog> findAllBySystemCodeAndTypeOrderByCode(String systemCode, String type);
	EventLog save(EventLog obj);
	void delete(EventLog obj);
}
