package com.metafour.mtrak.router.repositories;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.metafour.mtrak.router.entities.EventLog;

/**
 * @author minhaj
 *
 */
@Repository
public interface EventLogRepo extends CrudRepository<EventLog, Long> {

	ArrayList<EventLog> findAllBySystemCodeAndTypeOrderByCode(String systemCode, String type);
	void deleteAllBySystemCodeAndType(String systemCode, String type);
	EventLog findBySystemCodeAndTypeAndCode(String systemCode, String type, String code);
	
	
}
