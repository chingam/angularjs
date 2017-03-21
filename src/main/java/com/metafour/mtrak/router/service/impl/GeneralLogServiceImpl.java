package com.metafour.mtrak.router.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metafour.mtrak.router.entities.DragAndDrop;
import com.metafour.mtrak.router.entities.EventLog;
import com.metafour.mtrak.router.entities.GeneralLog;
import com.metafour.mtrak.router.repositories.EventLogRepo;
import com.metafour.mtrak.router.repositories.GeneralLogRepo;
import com.metafour.mtrak.router.service.GeneralLogService;

/**
 * @author minhaj
 *
 */
@Service
public class GeneralLogServiceImpl implements GeneralLogService {
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(GeneralLogServiceImpl.class);

	@Autowired
	GeneralLogRepo generalLogRepo;
	
	@Autowired
	EventLogRepo eventLogRepo;
	
	@Autowired
	EventHql eventHql;

	@Override
	@Transactional(readOnly = true)
	public GeneralLog findByCode(String code) {
		// TODO Auto-generated method stub
		return generalLogRepo.findByCode(code);
	}

	@Override
	@Transactional(readOnly = true)
	public List<GeneralLog> findAllByCode(String code) {
		// TODO Auto-generated method stub
		return generalLogRepo.findAllByCode(code);
	}

	@Override
	@Transactional
	public void delete(GeneralLog obj) {
		generalLogRepo.delete(obj);
	}

	@Override
	@Transactional
	public GeneralLog save(GeneralLog obj, ArrayList<EventLog> eventDatas) {
		// TODO Auto-generated method stub
		GeneralLog generalLog = null;
		for (EventLog eventLog2 : eventDatas) {
			EventLog event=eventLogRepo.findByCodeAndSystemCode(eventLog2.getCode(), obj.getCode());
			if(event!=null){
				eventLogRepo.deleteAllBySystemCode(obj.getCode());
			}
			if(obj.getCode()!=null){
				eventLog2.setSystemCode(obj.getCode());
				generalLog=generalLogRepo.save(obj);
				eventLogRepo.save(eventLog2);
			}
		}
		
		WriteUtils.writeINIFIle("/tmp", obj.getCode()+".ini", obj, eventLogRepo.findAllBySystemCodeOrderByCode(obj.getCode()));
		eventDatas=null;
		return generalLog;
	}

	@Override
	public GeneralLog save(GeneralLog obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAllBySystemCode(String systemCode) {
		// TODO Auto-generated method stub
		
	}

	ArrayList<DragAndDrop> dragAndDrops;
	@Override
	public void updateByCode(ArrayList<DragAndDrop> dragAndDrops) {
		this.dragAndDrops=dragAndDrops;
	}

	@Override
	@Transactional(readOnly=true)
	public List<GeneralLog> findByCodeLikeOrderByCode(String code) {
		// TODO Auto-generated method stub
		return generalLogRepo.findByCodeContaining(code);
	}

	

}
