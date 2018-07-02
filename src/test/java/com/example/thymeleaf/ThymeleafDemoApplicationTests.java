package com.example.thymeleaf;

import com.example.thymeleaf.cron.FutureCrawlerCroner;
import com.example.thymeleaf.cron.FuturePageLoaderCroner;
import com.example.thymeleaf.model.FutureCrawler;
import com.example.thymeleaf.service.FutureCrawlerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ThymeleafDemoApplicationTests {

	@Autowired
	private FutureCrawlerCroner futureCrawlerCroner;

	@Autowired
	private FuturePageLoaderCroner futurePageLoaderCroner;

	@Autowired
	private FutureCrawlerService futureCrawlerService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void test_futureCrawlerCroner(){
		futureCrawlerCroner.work();
	}

	@Test
	public void test_futurePageLoaderCroner(){
		futurePageLoaderCroner.work();
	}

	@Test
	public void test_futureCrawlerService(){
		FutureCrawler ff = futureCrawlerService.getToBeCrawlByDomainId(1);
		System.out.println(ff);
	}

}
