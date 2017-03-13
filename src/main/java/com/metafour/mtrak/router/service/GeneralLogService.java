package com.metafour.mtrak.router.service;

import java.util.ArrayList;
import java.util.List;

import com.metafour.mtrak.router.entities.DragAndDrop;
import com.metafour.mtrak.router.entities.EventLog;
import com.metafour.mtrak.router.entities.GeneralLog;

/**
 * @author minhaj
 *
 */
public interface GeneralLogService {
	GeneralLog findByCode(String code);
	List<GeneralLog> findAllByCode(String code);
	GeneralLog save(GeneralLog obj);
	GeneralLog save(GeneralLog obj,  ArrayList<EventLog> eventDatas);
	void deleteAllBySystemCode(String systemCode);
	void delete(GeneralLog obj);
}