package com.example.thymeleaf;

import com.example.thymeleaf.cron.*;
import com.example.thymeleaf.model.FutureCrawler;
import com.example.thymeleaf.service.*;
import com.example.thymeleaf.util.AppUtils;
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

	@Autowired
	private PanCroner panCroner;

	@Autowired
	private FixInvalidArticle fixInvalidArticle;

	@Autowired
	private ArticleUpdater articleUpdater;

	@Autowired
	private FutureCrawlerCfgService futureCrawlerCfgService;

	@Autowired
	private ArticleCallBack articleCallBack;

	@Autowired
	private ChapterCallBackCroner chapterCallBackCroner;

	@Autowired
	private FutureCrawlerCallBackCroner futureCrawlerCallBackCroner;


	@Test
	public void contextLoads() {
	}

	@Test
	public void test_futureCrawlerCroner(){
		futureCrawlerCroner.work();
	}

	@Test
	public void test_futurePageLoaderCroner(){
		for(int i=0;i<1;i++){
			futurePageLoaderCroner.work();
			AppUtils.sleep(3);
		}

	}

	@Test
	public void test_futureCrawlerService(){
		FutureCrawler ff = futureCrawlerService.getToBeCrawlByDomainId(1);
		System.out.println(ff);
	}

	@Test
	public void test_Panconer(){
		panCroner.work();
	}

	@Test
	public void test_fixInvalidArticle() throws Exception {
		fixInvalidArticle.work();
	}

	@Test
	public void test_articleUpdater() throws Exception {
		articleUpdater.work();
	}

	@Test
	public void test_futureCrawlerCfgService() throws Exception {
		String domainName = "www.zwdu.com";
		futureCrawlerCfgService.queryByDomain(domainName);
	}

	@Test
	public void test_articleCallBack(){
		articleCallBack.callback(1,2);
	}

	@Test
	public void test_chapterCallBackCroner(){
		chapterCallBackCroner.work();
	}

	@Test
	public void test_futureCrawlerCallBackCroner(){
		futureCrawlerCallBackCroner.work();
	}

}
