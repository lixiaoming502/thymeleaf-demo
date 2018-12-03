package com.example.thymeleaf;

import com.example.thymeleaf.cron.*;
import com.example.thymeleaf.dao.WooniuSynIdMapper;
import com.example.thymeleaf.model.FutureCrawler;
import com.example.thymeleaf.service.*;
import com.example.thymeleaf.util.AppUtils;
import com.example.thymeleaf.vo.SearchResultPage;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

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

	@Autowired
	private BrotherCroner brotherCroner;

	@Autowired
	private ChapterContentCallBackCroner chapterContentCallBackCroner;

	@Autowired
	private BaiduBrother baiduBrother;

	@Autowired
	private WooniuSynCroner wooniuSynCroner;

	@Autowired
	private WooniuSynIdMapper wooniuSynIdMapper;


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
	public void testCrawler_pageloader_crawler(){
		futureCrawlerCroner.work();
		AppUtils.sleep(5);
		futurePageLoaderCroner.work();
		futureCrawlerCroner.work();
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

	@Test
	public void test_brotherCroner(){
		brotherCroner.work();
	}

	@Test
	public void test_chapterContentCallBackCroner(){
		chapterContentCallBackCroner.work();
	}


	@Test
	public void test_baiduBrother2() throws UnsupportedEncodingException, InterruptedException, ExecutionException, TimeoutException {
		String title = "满城东风许佳人";
		List<String> pureLst = baiduBrother.loadPureChpLst(12);
		List<Pair> all = new ArrayList<>();
		SearchResultPage r = baiduBrother.search(title);
		if(r.getResultItems().size()>0){
			cc(pureLst, all, r);
		}




		all.stream().forEach(pair -> {
			System.out.println(pair.getLeft()+"|"+pair.getRight());
		});
		//String url = r.getResultItems().get(0);
		//System.out.println("select:"+url);

	}

	@Test
	public void test_baiduBrother() throws UnsupportedEncodingException, InterruptedException, ExecutionException, TimeoutException {
		String title = "满城东风许佳人";
		for(int i=0;i<10;i++){
			SearchResultPage bb = baiduBrother.search(title);
			System.out.println("size:"+bb.getResultItems().size());
		}

	}

	@Test
	public void test_wooniuSynCroner(){
		wooniuSynCroner.work();
	}

	private void cc(List<String> pureLst, List<Pair> all, SearchResultPage r) {
		long t1 = System.currentTimeMillis();

		HashSet<String> locations = new HashSet<>();
		r.getResultItems().stream().forEach(url->{
			try {
				System.out.println("select:"+url);

				Pair pair = baiduBrother.analysisCssSelector(url, pureLst,locations);
				//System.out.println(pair.getLeft()+"|"+pair.getRight());
				if(pair!=null){
					all.add(pair);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		long cost = System.currentTimeMillis()-t1;
		System.out.println("cost "+cost );
	}

	@Test
	public void test_wooniuSynIdMapper(){
		System.out.println("##############################################");
		int cnt = wooniuSynIdMapper.querySynCountByDate("20181129");
		System.out.println("####################################cnt:"+cnt);
	}

}
