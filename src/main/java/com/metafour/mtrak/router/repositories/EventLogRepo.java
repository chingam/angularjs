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

	EventLog findByCodeAndSystemCode(String code, String systemCode);
	ArrayList<EventLog> findAllBySystemCodeOrderByIdAsc(String systemCode);
	void deleteAllBySystemCode(String systemCode);
	
	
	
}
