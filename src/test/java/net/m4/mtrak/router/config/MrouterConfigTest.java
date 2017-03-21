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
import com.metafour.mtrak.router.config.MrouterConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MtrakRouterApplication.class)
@WebIntegrationTest
public class MrouterConfigTest {
	private Logger logger = LoggerFactory.getLogger(MrouterConfigTest.class);

	@Autowired
	private MrouterConfig mConfig;

	@Test
	public void test() {
		assertFalse(mConfig.getUsers().isEmpty());
		logger.debug("================================Users================================");
		mConfig.getUsers().stream().forEach(cn -> {
			logger.debug(cn.getCode());
			logger.debug(cn.getPassword());
			logger.debug(Arrays.toString(cn.getRoles()));
		});
		logger.debug("================================Users================================");
	}

}
