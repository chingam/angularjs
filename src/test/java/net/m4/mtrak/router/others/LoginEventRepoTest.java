package net.m4.mtrak.router.others;

import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.metafour.mtrak.router.MtrakRouterApplication;
import com.metafour.mtrak.router.entities.EventLog;
import com.metafour.mtrak.router.entities.GeneralLog;
import com.metafour.mtrak.router.service.EventLogService;
import com.metafour.mtrak.router.service.GeneralLogService;

/**
 * 
 * @author noor
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MtrakRouterApplication.class)
@WebIntegrationTest
public class LoginEventRepoTest {

	@Autowired
	EventLogService leRepo;
	
	@Autowired
	private GeneralLogService generalLogService;
	
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		/*EventLog event=new EventLog();
		event.setCode("M");
		event.setSystemCode("it5555");
		leRepo.save(event);*/
		
		/*List<GeneralLog> datas=generalLogService.findAllByCode("it5555");
		for (GeneralLog generalLog : datas) {
			System.out.println("data........"+generalLog.getDescription());
		}*/
		GeneralLog g=generalLogService.findByCode("it5555");
		System.out.println("DDDDDDDDDDDD>>>>>>>>>>>"+g.getCode());
		
		
		System.out.println("Success");
	}


}
