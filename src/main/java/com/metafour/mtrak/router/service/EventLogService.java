package com.metafour.mtrak.router.service;

import java.util.ArrayList;

import com.metafour.mtrak.router.entities.EventLog;

/**
 * @author noor
 *
 */
public interface EventLogService {
	EventLog findByCodeAndSystemCode(String code, String systemCode);
	ArrayList<EventLog> findAllBySystemCode(String systemCode);
	EventLog save(EventLog obj);
	void delete(EventLog obj);
}
