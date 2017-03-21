package net.m4.mtrak.router;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import com.metafour.mtrak.router.MtrakRouterApplication;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MtrakRouterApplication.class)
@WebAppConfiguration
public class MtrakRouterApplicationTests {

	@Test
	public void contextLoads() {
	}

}
