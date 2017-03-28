package com.metafour.mtrak.router.service.impl;

import java.io.File;
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
		if(obj!=null){
			generalLogRepo.delete(obj);
			ArrayList<EventLog> events=eventLogRepo.findAllBySystemCodeAndTypeOrderByCode(obj.getCode(), obj.getType());
			if(events.size()>0 && events!=null){
				eventLogRepo.deleteAllBySystemCodeAndType(obj.getCode(), obj.getType());
			}
			File file = new File("/tmp" + File.separator + obj.getCode()+".ini");
			System.out.println("file path >>>>>>>>>>>>"+file.getPath());
			if (file.exists()) {
				file.delete();
			}
		}
	
	}

	@Override
	@Transactional
	public GeneralLog save(GeneralLog obj, ArrayList<EventLog> eventDatas) {
		// TODO Auto-generated method stub
		GeneralLog generalLog = null;
		if(obj.getCode()!=null){
			generalLog=generalLogRepo.save(obj);
			
			if(eventDatas.size()>0 && eventDatas!=null && !eventDatas.isEmpty()){
				eventLogRepo.deleteAllBySystemCodeAndType(obj.getCode(), obj.getType());
				for (EventLog eventLog2 : eventDatas) {
					eventLog2.setSystemCode(obj.getCode());
					eventLog2.setType(obj.getType());
					eventLogRepo.save(eventLog2);
				}
			}
		}
		WriteUtils.writeINIFIle("/tmp", obj.getCode()+".ini", obj, eventLogRepo.findAllBySystemCodeAndTypeOrderByCode(obj.getCode(), obj.getType()));
		eventDatas=null;
		return generalLog;
	}
	
	@Override
	@Transactional
	public GeneralLog copy(GeneralLog obj, ArrayList<EventLog> eventDatas) {
		if(obj.getCode()!=null){
			generalLogRepo.save(obj);
			
			if(eventDatas.size()>0 && eventDatas!=null && !eventDatas.isEmpty()){
				for (EventLog eventLog2 : eventDatas) {
					eventLog2.setSystemCode(obj.getCode());
					eventLog2.setType(obj.getType());
					eventLogRepo.save(eventLog2);
				}
			}
		}
	
	WriteUtils.writeINIFIle("/tmp", obj.getCode()+".ini", obj, eventLogRepo.findAllBySystemCodeAndTypeOrderByCode(obj.getCode(), obj.getType()));
	eventDatas=null;
	return null;
	}

	@Override
	@Transactional
	public GeneralLog save(GeneralLog obj) {
		if(obj.getCode()!=null){
			generalLogRepo.save(obj);
		}
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

	@Override
	public List<GeneralLog> findAll() {
		// TODO Auto-generated method stub
		return generalLogRepo.findAll();
	}

	@Override
	public GeneralLog findByCodeAndType(String code, String type) {
		// TODO Auto-generated method stub
		return generalLogRepo.findByCodeAndType(code, type);
	}

	

}
