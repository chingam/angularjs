package net.m4.mtrak.router.config;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.metafour.mtrak.router.MtrakRouterApplication;
import com.metafour.mtrak.router.config.MTrakConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MtrakRouterApplication.class)
@WebIntegrationTest
public class MTrakConfigTest {
	private Logger logger = LoggerFactory.getLogger(MTrakConfigTest.class);

	@Autowired
	private MTrakConfig mtConfig;

	@Test
	public void test() {
		assertNotNull(mtConfig.getDefaultIni().getPrefix());
		assertNotNull(mtConfig.getDefaultIni().getSuffix());
		logger.debug(mtConfig.getDefaultIni().getPrefix());
		logger.debug(mtConfig.getDefaultIni().getSuffix());
		logger.debug("Other ini map size is {}", mtConfig.getOtherInis().size());
		logger.debug("================================Other ini configurations================================");
		mtConfig.getOtherInis().stream().forEach(oth -> {
			logger.debug(Arrays.toString(oth.getCodes().toArray()));
			logger.debug(oth.getPrefix());
			logger.debug(oth.getSuffix());
		});
		logger.debug("================================Other ini configurations================================");
	}

}
