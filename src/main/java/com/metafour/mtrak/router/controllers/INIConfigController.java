package com.metafour.mtrak.router.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.metafour.mtrak.router.entities.DragAndDrop;
import com.metafour.mtrak.router.entities.EventLog;
import com.metafour.mtrak.router.entities.GeneralLog;
import com.metafour.mtrak.router.service.EventLogService;
import com.metafour.mtrak.router.service.GeneralLogService;

/**
 * @author Minhaj
 *
 */
@RestController
public class INIConfigController {
	private Logger logger = LoggerFactory.getLogger(INIConfigController.class);

	@Autowired
	EventLogService eventLogService;
	
	@Autowired
	GeneralLogService generalLogService;
	
	String code="";
	@RequestMapping(value="/cache/{code}", method = RequestMethod.GET)
	public HashMap<String, Object> cacheData(@PathVariable String code) {
		HashMap<String, Object> response=new HashMap<String, Object>();
		if(code!=null){
			this.code=code;
			response.put("message", "success");
		}else{
			response.put("message", "fail");
		}
		return response;
	}
	
	
	
	@RequestMapping(value="/getCache", method = RequestMethod.GET)
	public HashMap<String, Object> getCacheData() {
		HashMap<String, Object> response=new HashMap<String, Object>();
		if(this.code!=null && !this.code.equalsIgnoreCase("")){
			response.put("message", this.code);
			this.code="";
		}else{
			response.put("message", "fail");
		}
		return response;
	}
	
	
	
	
	GeneralLog cacheData;
	@RequestMapping(value="/generalConfig", method = RequestMethod.POST)
	public HashMap<String, Object> generalDataSave(@RequestBody GeneralLog generalLog) {
		HashMap<String, Object> response=new HashMap<String, Object>();
		
		if(generalLog!=null && !eventDatas.isEmpty() && eventDatas!=null){
			System.out.println("list data >>>>>>>");
			System.out.println(eventDatas.size());
				generalLogService.save(generalLog, eventDatas);
				response.put("message", "Success");
		}else{
			response.put("message", "operation fail");
		}
		
		return response;
	}
	
	
	@RequestMapping(value="/sortingUpdate", method = RequestMethod.POST)
	public HashMap<String, Object> sortingUpdate(@RequestBody ArrayList<DragAndDrop> dragAndDrops) {
		HashMap<String, Object> response=new HashMap<String, Object>();
		
		if(!dragAndDrops.isEmpty()){
			generalLogService.updateByCode(dragAndDrops);
			for (DragAndDrop dragAndDrop : dragAndDrops) {
				System.out.println("value>>"+dragAndDrop.getCode()+"ID >>"+dragAndDrop.getId());
			}
				response.put("message", "Success");
		}else{
			response.put("message", "operation fail");
		}
		
		return response;
	}
	
	
	@RequestMapping(value="/getsort", method = RequestMethod.GET)
	public ArrayList<DragAndDrop> getsort() {
		ArrayList<DragAndDrop> res=new ArrayList<DragAndDrop>();
		DragAndDrop obj=new DragAndDrop();
		obj.setCode("M");
		obj.setId(1);
		res.add(obj);
		
		DragAndDrop obj1=new DragAndDrop();
		obj1.setCode("M");
		obj1.setId(1);
		res.add(obj1);
		return res;
	}
	
	
	
	
	@RequestMapping(value="/fetch/clear", method = RequestMethod.GET)
	public HashMap<String, Object> getClear() {
		HashMap<String, Object> response=new HashMap<String, Object>();
		try {
			if(this.code.equalsIgnoreCase("")==Boolean.TRUE || this.code==null){
				if(!eventDatas.isEmpty()){
					eventDatas.clear();
					
					System.out.println("clean success");
				}
				response.put("message", "success");
			}else{
//				fetchByCode(this.code);
			}
			
			
			
			
			
			
			
			
		} catch (Exception e) {
			logger.error("Failed to clean");
		}
		return response;
	}
	
	
	
	
	

	@RequestMapping(value="/fetch/system/{systemCode}", method = RequestMethod.GET)
	@ApiOperation(tags="Event Logs", value="Site Code", notes="Get device upload logs")
	public HashMap<String, Object> fetchByCode(@ApiParam(value = "systemCode", required = true) @PathVariable String systemCode) {
		HashMap<String, Object> response=new HashMap<String, Object>();
		try {
			if (systemCode != null) {
				eventDatas.clear();
				response.put("gData", generalLogService.findByCode(systemCode));
				eventDatas=eventLogService.findAllBySystemCode(systemCode);
				response.put("eventList", eventDatas);
				return response;
			} else {
				return null;
			}

		} catch (Exception e) {
			logger.error("Failed to prepare data for report");
		}
		return null;
	}
	
	
	@RequestMapping(value="/eventConfig", method = RequestMethod.POST)
	public HashMap<String, Object> eventDataSave(@RequestBody EventLog eventLog) {
		HashMap<String, Object> response=new HashMap<String, Object>();
		
		if(eventLog!=null && eventLog.getSystemCode()!=null){
			System.out.println("Cache Data......"+cacheData.getCode());
			eventLogService.save(eventLog);
			
			ArrayList<EventLog> eventLogs = eventLogService.findAllBySystemCode(cacheData.getCode());
			writeINIFIle("/tmp", cacheData.getCode()+".ini", cacheData, eventLogs);
			
			response.put("message", "Success");
			
		}else{
			response.put("message", "operation fail");
		}
		
		return response;
	}
	
	
	
	ArrayList<EventLog> eventDatas =new ArrayList<EventLog>();
	@RequestMapping(value="/eventConfig2", method = RequestMethod.POST)
	public HashMap<String, Object> eventDataSave2(@RequestBody EventLog eventLog) {
		HashMap<String, Object> response=new HashMap<String, Object>();
		
		if(eventLog!=null){
			eventDatas.add(eventLog);
			response.put("message", "Success");
		}else{
			response.put("message", "operation fail");
		}
		
		return response;
	}
	
	@RequestMapping(value="/generalConfig2", method = RequestMethod.POST)
	public HashMap<String, Object> generalDataSave2(@RequestBody GeneralLog generalLog) {
		HashMap<String, Object> response=new HashMap<String, Object>();
		
		if(generalLog!=null){
				cacheData=generalLog;
				generalLogService.save(generalLog);
				response.put("message", "Success");
		}else{
			response.put("message", "operation fail");
		}
		
		return response;
	}
	
	
	@RequestMapping(value="/eventConfigu2", method = RequestMethod.POST)
	public HashMap<String, Object> eventDataSaveu2(@RequestBody EventLog eventLog) {
		HashMap<String, Object> response=new HashMap<String, Object>();
		
		if(eventLog!=null){
			eventDatas.add(eventLog);
			response.put("message", "Success");
		}else{
			response.put("message", "operation fail");
		}
		
		return response;
	}
	
	
	@RequestMapping(value="/eventConfigu/update", method = RequestMethod.POST)
	public HashMap<String, Object> eventUpdate(@RequestBody EventLog eventLog) {
		HashMap<String, Object> response=new HashMap<String, Object>();
		
		if(eventLog!=null){
			ArrayList<EventLog> eventList = (ArrayList<EventLog>) eventDatas.stream().filter(r -> r.getCode().equals(eventLog.getCode())).collect(Collectors.toList());
			if (!eventList.isEmpty())
				eventDatas.remove(eventList.get(0));
				eventDatas.add(eventLog);
				response.put("message", "Success");
		}else{
			response.put("message", "operation fail");
		}
		
		return response;
	}
	
	
	
	
	@RequestMapping(value="/delete2/{code}", method = RequestMethod.GET)
	@ApiOperation(tags="Event Logs", value="Site Code", notes="Get device upload logs")
	public HashMap<String, Object> delete2(@ApiParam(value = "Month as yyyy-MM to get logs", required = true) @PathVariable String code) {
		HashMap<String, Object> response=new HashMap<String, Object>();
		if(code!=null){
			if (!eventDatas.isEmpty()) {
				for (int i = 0; i < eventDatas.size(); i++) {
					if (eventDatas.get(i).getCode().equals(code)) {
						eventDatas.remove(i);
						System.out.println("delete data >>>>>>>");
						System.out.println(eventDatas.size());
						response.put("message", "Success");
					}
				}
			}
		}else{
			response.put("message", "operation fail");
		}
		return response;
	}
	
	
	
	@RequestMapping(value="/fetch/all", method = RequestMethod.GET)
	@ApiOperation(tags="Event Logs", value="Site Code", notes="Get device upload logs")
	public ArrayList<EventLog> fetchAll() {
		try {
			if (!eventDatas.isEmpty() && eventDatas != null) {
				return eventDatas;
			} else {
				return null;
			}

		} catch (Exception e) {
			logger.error("Failed to prepare data for report");
		}
		return null;
	}
	
	
	
	@RequestMapping(value="/fetch/{code}", method = RequestMethod.GET)
	@ApiOperation(tags="Event Logs", value="Site Code", notes="Get device upload logs")
	public ArrayList<EventLog> getLogs(@ApiParam(value = "Month as yyyy-MM to get logs", required = true) @PathVariable String code) {
		try {
			ArrayList<EventLog> eventLogs = eventLogService.findAllBySystemCode(code);
			if (!eventLogs.isEmpty() && eventLogs != null) {
				return eventLogs;
			} else {
				return null;
			}

		} catch (Exception e) {
			logger.error("Failed to prepare data for report");
		}
		return null;
	}
	
	
	@RequestMapping(value="/delete/{systemCode}/{code}", method = RequestMethod.GET)
	@ApiOperation(tags="Event Logs", value="Site Code", notes="Get device upload logs")
	public HashMap<String, Object> delete(@ApiParam(value = "Month as yyyy-MM to get logs", required = true) @PathVariable String code, @ApiParam(value = "Month as yyyy-MM to get logs", required = true) @PathVariable String systemCode) {
		HashMap<String, Object> response=new HashMap<String, Object>();
		try {
			EventLog eventLogs = eventLogService.findByCodeAndSystemCode(code, systemCode);
			if(eventLogs!=null){
				eventLogService.delete(eventLogs);
				response.put("message", "Success");
			}

		} catch (Exception e) {
			logger.error("Failed to data delete");
		}
		return response;
	}
	
	
	
	
	
	public static void writeINIFIle(String srcDir, String trgFileName, GeneralLog generalLog, ArrayList<EventLog> eventLogs) {
        String str = "";
        String path=System.getProperty("line.separator");
        System.out.println(path);
        try {
            boolean lc = false;
            String recno = "";
            FileWriter fw = null;
            BufferedWriter TTTCOF = null;
            File file = new File(srcDir + File.separator + trgFileName);
            if (!file.exists()) {
                fw = new FileWriter(srcDir + File.separator + trgFileName, true);
                TTTCOF = new BufferedWriter(fw);
                TTTCOF.write("[General]");
                TTTCOF.write("\r\n");
                TTTCOF.write("Logo                    = "+generalLog.getLogo()!=null?generalLog.getLogo():"");
                TTTCOF.write("\r\n");
                TTTCOF.write("VersionFile             = "+generalLog.getVersionFileUrl()!=null?generalLog.getVersionFileUrl():"https://ms.m4.net/mtrak/update/live/version.ini");
                TTTCOF.write("\r\n");
                TTTCOF.write("\r\n");
                TTTCOF.write("[SystemTable]");
                TTTCOF.write("\r\n");
                TTTCOF.write("Code \t\t\t= "+generalLog.getCode()!=null?generalLog.getCode():"it5555");
                TTTCOF.write("\r\n");
                TTTCOF.write("Description		= "+generalLog.getDescription()!=null?generalLog.getDescription():"iTrak UAT Site");
                
                TTTCOF.write("Type			= "+generalLog.getType()!=null?generalLog.getType():"iTrak");
                TTTCOF.write("\r\n");
                TTTCOF.write("Email			= "+generalLog.getEmail()!=null?generalLog.getEmail():"mmit5555@ms.m4.net");
                TTTCOF.write("\r\n");
                TTTCOF.write("UploadURL               = "+generalLog.getUploadURL()!=null?generalLog.getUploadURL():"https://ms.m4.net/it5555/PdaUpload");
                TTTCOF.write("\r\n");
                TTTCOF.write("UploadPath              = "+generalLog.getUploadPath()!=null?generalLog.getUploadPath():"/itrak2/it5555/p_itrak/import");
                TTTCOF.write("\r\n");
                TTTCOF.write("ProcessURL              = "+generalLog.getProcessURL()!=null?generalLog.getProcessURL():"https://ms.m4.net/it5555/PdaManager?v=1.0");
                TTTCOF.write("\r\n");
                TTTCOF.write("DataCheckInterval	= "+generalLog.getDataCheckInterval());
                TTTCOF.write("\r\n");
                TTTCOF.write("BlockScanLimit          = "+generalLog.getBlockScanLimit());
                TTTCOF.write("\r\n");
                TTTCOF.write("WarningScanLimit        = "+generalLog.getWarningScanLimit());
                TTTCOF.write("\r\n");
                TTTCOF.write("MaxUploadSize		= "+generalLog.getMaxUploadSize());
                TTTCOF.write("\r\n");
                TTTCOF.write("UploadSleepInterval     = "+generalLog.getUploadSleepInterval());
                TTTCOF.write("\r\n");
                TTTCOF.write("JobCreate               = "+generalLog.getJobCreate());
                TTTCOF.write("\r\n");
                TTTCOF.write("DeliveryTimeDuration    = "+generalLog.getDeliveryTimeDuration());
                TTTCOF.write("\r\n");
                TTTCOF.write("ValidateMessenger       = "+generalLog.getValidateMessenger());
                TTTCOF.write("\r\n");
                TTTCOF.write("GPSEnable               = "+generalLog.getgPSEnable());
                TTTCOF.write("\r\n");
                TTTCOF.write("\r\n");
                
                for (EventLog eventLog : eventLogs) {
					
				
                TTTCOF.write("[EventTable]");
                TTTCOF.write("\r\n");
                TTTCOF.write("Type			= "+eventLog.getType());
                TTTCOF.write("\r\n");
                TTTCOF.write("Code			= "+eventLog.getCode());
                TTTCOF.write("\r\n");
                TTTCOF.write("Description		= "+eventLog.getDescription());
                TTTCOF.write("\r\n");
                TTTCOF.write("RqAdditionalText	= "+eventLog.getRqAdditionalText());
                TTTCOF.write("\r\n");
                TTTCOF.write("RqSignature		= "+eventLog.getRqSignature());
                TTTCOF.write("\r\n");
                TTTCOF.write("RqShelfmark		= "+eventLog.getRqShelfmark());
                TTTCOF.write("\r\n");
                TTTCOF.write("LbAdditionalText        = "+eventLog.getLbAdditionalText());
                TTTCOF.write("\r\n");
                TTTCOF.write("MnAdditionalText        = "+eventLog.getMnAdditionalText());
                TTTCOF.write("\r\n");
                TTTCOF.write("\r\n");
                }
                
                TTTCOF.flush();
                TTTCOF.close();
                fw.close();
            } else {
            	file.delete();
            	fw = new FileWriter(srcDir + "/" + trgFileName, true);
                TTTCOF = new BufferedWriter(fw);
                TTTCOF.write("[General]");
                TTTCOF.write("\r\n");
                TTTCOF.write("Logo                    = "+"https://ms.m4.net/mtrak/config/logo.jpg");
                TTTCOF.write("\r\n");
                TTTCOF.write("VersionFile             = "+"https://ms.m4.net/mtrak/update/live/version.ini");
                TTTCOF.write("\r\n");
                TTTCOF.write("\r\n");
                TTTCOF.write("[SystemTable]");
                TTTCOF.write("\r\n");
                TTTCOF.write("Code \t\t\t= "+"it5555");
                TTTCOF.write("\r\n");
                TTTCOF.write("Description		= "+"iTrak BD Site");
                
                TTTCOF.write("Type			= "+"iTrak");
                TTTCOF.write("\r\n");
                TTTCOF.write("Email			= "+"mmit5555@ms.m4.net");
                TTTCOF.write("\r\n");
                TTTCOF.write("UploadURL               = "+"https://ms.m4.net/it5555/PdaUpload");
                TTTCOF.write("\r\n");
                TTTCOF.write("UploadPath              = "+"/itrak2/it5555/p_itrak/import");
                TTTCOF.write("\r\n");
                TTTCOF.write("ProcessURL              = "+"https://ms.m4.net/it5555/PdaManager?v=1.0");
                TTTCOF.write("\r\n");
                TTTCOF.write("DataCheckInterval	= "+1);
                TTTCOF.write("\r\n");
                TTTCOF.write("BlockScanLimit          = "+100);
                TTTCOF.write("\r\n");
                TTTCOF.write("WarningScanLimit        = "+75);
                TTTCOF.write("\r\n");
                TTTCOF.write("MaxUploadSize		= "+10);
                TTTCOF.write("\r\n");
                TTTCOF.write("UploadSleepInterval     = "+5);
                TTTCOF.write("\r\n");
                TTTCOF.write("JobCreate               = "+"N");
                TTTCOF.write("\r\n");
                TTTCOF.write("DeliveryTimeDuration    = "+240);
                TTTCOF.write("\r\n");
                TTTCOF.write("ValidateMessenger       = "+"Y");
                TTTCOF.write("\r\n");
                TTTCOF.write("GPSEnable               = "+"Y");
                TTTCOF.write("\r\n");
                TTTCOF.write("\r\n");
                
                
                TTTCOF.write("[EventTable]");
                TTTCOF.write("\r\n");
                TTTCOF.write("Type			= "+"iTrak");
                TTTCOF.write("\r\n");
                TTTCOF.write("Code			= "+"M");
                TTTCOF.write("\r\n");
                TTTCOF.write("Description		= "+"Mail-run");
                TTTCOF.write("\r\n");
                TTTCOF.write("RqAdditionalText	= "+"N");
                TTTCOF.write("\r\n");
                TTTCOF.write("RqSignature		= "+"N");
                TTTCOF.write("\r\n");
                TTTCOF.write("RqShelfmark		= "+"N");
                TTTCOF.write("\r\n");
                TTTCOF.write("LbAdditionalText        = "+"Additional text");
                TTTCOF.write("\r\n");
                TTTCOF.write("MnAdditionalText        = "+"Y");
                TTTCOF.write("\r\n");
                TTTCOF.write("\r\n");
                
                TTTCOF.flush();
                TTTCOF.close();
                fw.close();
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	
	
	

}
