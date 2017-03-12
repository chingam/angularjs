package com.metafour.mtrak.router.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import com.metafour.mtrak.router.entities.EventLog;
import com.metafour.mtrak.router.entities.GeneralLog;

public class WriteUtils {

	public static void writeINIFIle(String srcDir, String trgFileName, GeneralLog generalLog, ArrayList<EventLog> eventLogs) {
        String path=System.getProperty("line.separator");
        System.out.println(path);
        try {
            FileWriter fw = null;
            BufferedWriter iniWriter = null;
            File file = new File(srcDir + File.separator + trgFileName);
            if (!file.exists()) {
                fw = new FileWriter(srcDir + File.separator + trgFileName, true);
                iniWriter = new BufferedWriter(fw);
                iniWriter.write("[General]");
                iniWriter.write("\r\n");
                iniWriter.write("Logo                    = "+generalLog.getLogo());
                iniWriter.write("\r\n");
                iniWriter.write("VersionFile             = "+generalLog.getVersionFileUrl());
                iniWriter.write("\r\n");
                iniWriter.write("\r\n");
                iniWriter.write("[SystemTable]");
                iniWriter.write("\r\n");
                iniWriter.write("Code 			= "+generalLog.getCode());
                iniWriter.write("\r\n");
                iniWriter.write("Description		= "+generalLog.getDescription());
                
                iniWriter.write("\r\n");
                iniWriter.write("Type			= "+generalLog.getType());
                iniWriter.write("\r\n");
                iniWriter.write("Email			= "+generalLog.getEmail());
                iniWriter.write("\r\n");
                iniWriter.write("UploadURL               = "+generalLog.getUploadURL());
                iniWriter.write("\r\n");
                iniWriter.write("UploadPath              = "+generalLog.getUploadPath());
                iniWriter.write("\r\n");
                iniWriter.write("ProcessURL              = "+generalLog.getProcessURL());
                iniWriter.write("\r\n");
                iniWriter.write("DataCheckInterval	= "+generalLog.getDataCheckInterval());
                iniWriter.write("\r\n");
                iniWriter.write("BlockScanLimit          = "+generalLog.getBlockScanLimit());
                iniWriter.write("\r\n");
                iniWriter.write("WarningScanLimit        = "+generalLog.getWarningScanLimit());
                iniWriter.write("\r\n");
                iniWriter.write("MaxUploadSize		= "+generalLog.getMaxUploadSize());
                iniWriter.write("\r\n");
                iniWriter.write("UploadSleepInterval     = "+generalLog.getUploadSleepInterval());
                iniWriter.write("\r\n");
                iniWriter.write("JobCreate               = "+generalLog.getJobCreate());
                iniWriter.write("\r\n");
                iniWriter.write("DeliveryTimeDuration    = "+generalLog.getDeliveryTimeDuration());
                iniWriter.write("\r\n");
                iniWriter.write("ValidateMessenger       = "+generalLog.getValidateMessenger());
                iniWriter.write("\r\n");
                iniWriter.write("GPSEnable               = "+generalLog.getgPSEnable());
                iniWriter.write("\r\n");
                iniWriter.write("\r\n");
                
                for (EventLog eventLog : eventLogs) {
					
				
                iniWriter.write("[EventTable]");
                iniWriter.write("\r\n");
                iniWriter.write("Type			= "+eventLog.getType());
                iniWriter.write("\r\n");
                iniWriter.write("Code			= "+eventLog.getCode());
                iniWriter.write("\r\n");
                iniWriter.write("Description		= "+eventLog.getDescription());
                iniWriter.write("\r\n");
                iniWriter.write("RqAdditionalText	= "+eventLog.getRqAdditionalText());
                iniWriter.write("\r\n");
                iniWriter.write("RqSignature		= "+eventLog.getRqSignature());
                iniWriter.write("\r\n");
                iniWriter.write("RqShelfmark		= "+eventLog.getRqShelfmark());
                iniWriter.write("\r\n");
                iniWriter.write("LbAdditionalText        = "+eventLog.getLbAdditionalText());
                iniWriter.write("\r\n");
                iniWriter.write("MnAdditionalText        = "+eventLog.getMnAdditionalText());
                iniWriter.write("\r\n");
                iniWriter.write("\r\n");
                }
                
                iniWriter.flush();
                iniWriter.close();
                fw.close();
            } else {
            	file.delete();


                fw = new FileWriter(srcDir + File.separator + trgFileName, true);
                iniWriter = new BufferedWriter(fw);
                iniWriter.write("[General]");
                iniWriter.write("\r\n");
                iniWriter.write("Logo                    = "+generalLog.getLogo());
                iniWriter.write("\r\n");
                iniWriter.write("VersionFile             = "+generalLog.getVersionFileUrl());
                iniWriter.write("\r\n");
                iniWriter.write("\r\n");
                iniWriter.write("[SystemTable]");
                iniWriter.write("\r\n");
                iniWriter.write("Code 			= "+generalLog.getCode());
                iniWriter.write("\r\n");
                iniWriter.write("Description		= "+generalLog.getDescription());
                iniWriter.write("\r\n");
                iniWriter.write("Type			= "+generalLog.getType());
                iniWriter.write("\r\n");
                iniWriter.write("Email			= "+generalLog.getEmail());
                iniWriter.write("\r\n");
                iniWriter.write("UploadURL               = "+generalLog.getUploadURL());
                iniWriter.write("\r\n");
                iniWriter.write("UploadPath              = "+generalLog.getUploadPath());
                iniWriter.write("\r\n");
                iniWriter.write("ProcessURL              = "+generalLog.getProcessURL());
                iniWriter.write("\r\n");
                iniWriter.write("DataCheckInterval	= "+generalLog.getDataCheckInterval());
                iniWriter.write("\r\n");
                iniWriter.write("BlockScanLimit          = "+generalLog.getBlockScanLimit());
                iniWriter.write("\r\n");
                iniWriter.write("WarningScanLimit        = "+generalLog.getWarningScanLimit());
                iniWriter.write("\r\n");
                iniWriter.write("MaxUploadSize		= "+generalLog.getMaxUploadSize());
                iniWriter.write("\r\n");
                iniWriter.write("UploadSleepInterval     = "+generalLog.getUploadSleepInterval());
                iniWriter.write("\r\n");
                iniWriter.write("JobCreate               = "+generalLog.getJobCreate());
                iniWriter.write("\r\n");
                iniWriter.write("DeliveryTimeDuration    = "+generalLog.getDeliveryTimeDuration());
                iniWriter.write("\r\n");
                iniWriter.write("ValidateMessenger       = "+generalLog.getValidateMessenger());
                iniWriter.write("\r\n");
                iniWriter.write("GPSEnable               = "+generalLog.getgPSEnable());
                iniWriter.write("\r\n");
                iniWriter.write("\r\n");
                
                for (EventLog eventLog : eventLogs) {
					
				
                iniWriter.write("[EventTable]");
                iniWriter.write("\r\n");
                iniWriter.write("Type			= "+eventLog.getType());
                iniWriter.write("\r\n");
                iniWriter.write("Code			= "+eventLog.getCode());
                iniWriter.write("\r\n");
                iniWriter.write("Description		= "+eventLog.getDescription());
                iniWriter.write("\r\n");
                iniWriter.write("RqAdditionalText	= "+eventLog.getRqAdditionalText());
                iniWriter.write("\r\n");
                iniWriter.write("RqSignature		= "+eventLog.getRqSignature());
                iniWriter.write("\r\n");
                iniWriter.write("RqShelfmark		= "+eventLog.getRqShelfmark());
                iniWriter.write("\r\n");
                iniWriter.write("LbAdditionalText        = "+eventLog.getLbAdditionalText());
                iniWriter.write("\r\n");
                iniWriter.write("MnAdditionalText        = "+eventLog.getMnAdditionalText());
                iniWriter.write("\r\n");
                iniWriter.write("\r\n");
                }
                
                iniWriter.flush();
                iniWriter.close();
                fw.close();
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
