# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 39.107.103.131 (MySQL 5.6.37)
# Database: crawler
# Generation Time: 2019-01-09 00:52:54 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table t_article
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_article`;

CREATE TABLE `t_article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comment` varchar(1024) NOT NULL DEFAULT ' ',
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `list_url` varchar(150) NOT NULL DEFAULT '',
  `info_url` varchar(150) NOT NULL DEFAULT ' ',
  `seed_id` int(11) NOT NULL,
  `category_id` int(11) DEFAULT NULL,
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '0:连载中 1.已完结 2.采集源出错 3.url已经修正',
  `click_times` int(11) NOT NULL DEFAULT '0' COMMENT '点击次数',
  `img` varchar(255) NOT NULL DEFAULT ' ',
  `author` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT ' ',
  `modify_date` varchar(8) DEFAULT ' ' COMMENT '修改日期',
  `brother_state` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `url` (`list_url`),
  KEY `seed_id` (`seed_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;



# Dump of table t_brother
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_brother`;

CREATE TABLE `t_brother` (
  `brother_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `article_id` int(11) NOT NULL,
  `brother_url` varchar(200) NOT NULL DEFAULT '',
  `domain_id` int(11) DEFAULT NULL,
  `brother_score` int(11) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_turns` int(11) DEFAULT '0',
  PRIMARY KEY (`brother_id`),
  KEY `brother_url_key` (`brother_url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table t_brother_chapter
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_brother_chapter`;

CREATE TABLE `t_brother_chapter` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `brother_id` int(11) DEFAULT NULL,
  `seq_id` int(11) DEFAULT NULL,
  `title` varchar(200) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `local_url` varchar(100) DEFAULT NULL,
  `chapter_id` int(11) DEFAULT NULL,
  `collect_flag` int(11) DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `brother_id_key` (`brother_id`),
  KEY `chapter_id_idx` (`chapter_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table t_category
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_category`;

CREATE TABLE `t_category` (
  `id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

LOCK TABLES `t_category` WRITE;
/*!40000 ALTER TABLE `t_category` DISABLE KEYS */;

INSERT INTO `t_category` (`id`, `category_name`)
VALUES
	(1,'言情小说'),
	(2,'仙侠修真'),
	(3,'都市小说'),
	(4,'历史小说'),
	(5,'玄幻小说'),
	(6,'网游小说'),
	(7,'竞技小说'),
	(8,'科幻小说'),
	(9,'其他小说'),
	(10,'精选小说');

/*!40000 ALTER TABLE `t_category` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_chapter
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_chapter`;

CREATE TABLE `t_chapter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `artile_id` int(11) NOT NULL,
  `seq_id` int(11) NOT NULL DEFAULT '0',
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `url` varchar(150) NOT NULL,
  `local_url` varchar(150) DEFAULT ' ',
  `collect_flag` tinyint(1) NOT NULL DEFAULT '0',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `url` (`url`),
  KEY `artile_id` (`artile_id`),
  KEY `getToBeCrawl` (`collect_flag`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;



# Dump of table t_chapter_all
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_chapter_all`;

CREATE TABLE `t_chapter_all` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `artile_id` int(11) NOT NULL,
  `seq_id` int(11) NOT NULL DEFAULT '0',
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `url` varchar(150) NOT NULL,
  `local_url` varchar(150) DEFAULT ' ',
  `collect_flag` tinyint(1) NOT NULL DEFAULT '0',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `url` (`url`),
  KEY `artile_id` (`artile_id`),
  KEY `getToBeCrawl` (`collect_flag`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;



# Dump of table t_comments
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_comments`;

CREATE TABLE `t_comments` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `comment` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `t_comments` WRITE;
/*!40000 ALTER TABLE `t_comments` DISABLE KEYS */;

INSERT INTO `t_comments` (`id`, `comment`)
VALUES
	(1,'select * from t_future_crawler where crawler_state  in (\'F\',\'E\') order by id desc limit 10\n'),
	(2,'select * from t_future_page_loader where loader_state=\'F\' order by id desc limit 10'),
	(3,'select * from t_future_page_loader where state_code!=200\n'),
	(4,'update t_future_page_loader set state_code = null,response =null ,loader_state=\'P\' where state_code!=200\n'),
	(5,'update `t_future_crawler` set response = null,crawler_state=\'A\''),
	(6,'select distinct(artile_id) from t_chapter where collect_flag=1'),
	(7,'insert into t_wooniu_syn_ids(article_id) select distinct(artile_id) from t_chapter where collect_flag=1 and artile_id between 1621 and 1720\n'),
	(8,'select max(article_id) from t_wooniu_syn_ids'),
	(9,'update t_future_crawler a,t_chapter b set a.response=\'\',a.crawler_state=\'A\' where a.ref_id = b.id and b.artile_id=366\n'),
	(10,'select a.* from t_future_crawler a,t_chapter b where a.ref_id = b.id and b.artile_id=366'),
	(11,'insert into t_chapter select * from t_chapter where artile_id<4000'),
	(12,'delete from t_chapter where artile_id<4000'),
	(13,'select artile_id,count(1) from t_chapter group by artile_id having count(1)>100'),
	(14,'insert into t_chapter select * from t_chapter_all where artile_id in(\nselect artile_id from t_chapter_all where artile_id between 30001 and 50000 group by artile_id having count(1)<50\n)');

/*!40000 ALTER TABLE `t_comments` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_domain_css_selector
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_domain_css_selector`;

CREATE TABLE `t_domain_css_selector` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `domain_id` int(11) NOT NULL,
  `level1_selector` varchar(100) DEFAULT NULL,
  `level2_selector` varchar(200) DEFAULT NULL,
  `level3_selector` varchar(100) DEFAULT NULL,
  `extra_rule` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `domain_id` (`domain_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `t_domain_css_selector` WRITE;
/*!40000 ALTER TABLE `t_domain_css_selector` DISABLE KEYS */;

INSERT INTO `t_domain_css_selector` (`id`, `domain_id`, `level1_selector`, `level2_selector`, `level3_selector`, `extra_rule`)
VALUES
	(16,15,NULL,'#list-chapterAll > dl.panel-body.panel-chapterlist > dd.col-md-3 > a','#htmlContent','{\"l3_preUrl\":\"\",\"sensitive_words\":[{\"end\":\"biqugebook.com，最快更新\",\"begin\":\"笔~趣*阁\"}]}'),
	(17,16,NULL,'#list > dl > dd > a','#content','{\"l3_preUrl\":\"http://www.tycqxs.com\"}'),
	(23,22,NULL,'#list > dl > dd > a','#content','{\"l3_preUrl\":\"https://www.liewen.cc\"}'),
	(24,23,NULL,'#j-catalogWrap > div.volume-wrap > div.volume:nth-child(2) > ul.cf > li > a',NULL,'{\"l2_example_url\":\"https://book.qidian.com/info/40717\",\"l3_preUrl\":\"\",\"l3_example_url\":\"https://book.qidian.com//read.qidian.com/chapter/hIlejGWIHCc1/71MuUTQepkI1\"}'),
	(25,24,NULL,'html > body > div.readerListBody > div.readerListShow > table.acss > tbody > tr > td.ccss > a','#content','{\"l2_example_url\":\"http://www.baoliny.com/3186/index.html\",\"l3_preUrl\":\"\",\"l3_example_url\":\"http://www.baoliny.com/3186/1791226.html\"}'),
	(26,25,NULL,'html > body > div.mulu > ul > li > a','.yd_text2','{\"l2_example_url\":\"https://www.88dush.com/xiaoshuo/0/599/index.html\",\"l3_preUrl\":\"\",\"l3_example_url\":\"https://www.88dush.com/xiaoshuo/0/599/169065.html\"}'),
	(27,26,NULL,'#list > dl > dd > a','#content','{\"l2_example_url\":\"http://www.biqiku.com/18/18090/\",\"l3_preUrl\":\"\",\"l3_example_url\":\"http://www.biqiku.com/18/18090/9574348.html\"}'),
	(28,27,NULL,'#dirsort02 > li > span.splone > a',NULL,'{\"l2_example_url\":\"http://www.shushu8.com/b_qyq.htm\",\"l3_preUrl\":\"\",\"l3_example_url\":\"http://www.shushu8.com/danjing/9\"}'),
	(29,28,NULL,'#chapterlist > p > a','#chaptercontent','{\"l2_example_url\":\"https://m.biqudao.com/bqge25229/all.html\",\"l3_preUrl\":\"\",\"l3_example_url\":\"https://m.biqudao.com/bqge25229/1477805.html\"}'),
	(30,29,NULL,'#main > div.inner > dl.chapterlist > dd > a','#BookText','{\"l2_example_url\":\"http://www.aoyuge.com/41/41930/index.html\",\"l3_preUrl\":\"\",\"l3_example_url\":\"http://www.aoyuge.com/41/41930/20832591.html\"}'),
	(31,30,NULL,'#list > dl > dd > a',NULL,'{\"l2_example_url\":\"https://www.qu.la/book/55046/\",\"l3_preUrl\":\"\",\"l3_example_url\":\"https://www.qu.la/book/55046/3340659.html\"}'),
	(32,31,NULL,'#list > dl > dd > a','#content','{\"l2_example_url\":\"https://www.sanjiangge.com/book/148/148883/index.html\",\"l3_preUrl\":\"\",\"l3_example_url\":\"https://www.sanjiangge.com/book/148/148883/47570916.html\"}'),
	(33,32,NULL,'html > body > div.wrapper_list > div.insert_list > dl > dd > ul > li > a','#BookText','{\"l2_example_url\":\"https://www.vodtw.com/Html/Book/49/49297/\",\"l3_preUrl\":\"\",\"l3_example_url\":\"https://www.vodtw.com/Html/Book/49/49297/29934152.html\"}'),
	(34,33,NULL,'#at > tbody > tr:nth-child(3) > td.L:nth-child(3) > a',NULL,'{\"l2_example_url\":\"https://www.11kt.cn/read/131950/index.html\",\"l3_preUrl\":\"\"}'),
	(35,34,NULL,'#list > dl > dd:nth-child(25) > a',NULL,'{\"l2_example_url\":\"http://www.126shu.com/86460/\",\"l3_preUrl\":\"\"}'),
	(36,35,NULL,'#list > dl > dd:nth-child(11) > a',NULL,'{\"l2_example_url\":\"https://www.23txt.com/files/article/html/45/45498/\",\"l3_preUrl\":\"\"}'),
	(37,36,NULL,'#list > dl > dd:nth-child(15) > a','#chapter_content','{\"l2_example_url\":\"http://www.beidouxin.com/read/92/92043/\",\"l3_preUrl\":\"\"}'),
	(38,37,NULL,'#chapter > dl > dd > a','#chapter_content','{\"l2_example_url\":\"https://www.jiaoyu123.com/294/294763/\",\"l3_preUrl\":\"\",\"l3_example_url\":\"https://www.jiaoyu123.com/294/294763/63404955.html\"}'),
	(39,38,NULL,'#list > dl > dd > a','#content','{\"l2_example_url\":\"https://www.lindiankanshu.com/227_227955/\",\"l3_preUrl\":\"\",\"l3_example_url\":\"https://www.lindiankanshu.com/227_227955/1347971.html\"}'),
	(40,39,NULL,'#list > dl > dd > a','#content','{\"l2_example_url\":\"https://www.kanshu.la/46/46785/\",\"l3_preUrl\":\"\",\"l3_example_url\":\"https://www.kanshu.la/46/46785/16358427.html\"}'),
	(41,40,NULL,'html > body > section.w-all > div.card.mt20:nth-child(4) > div.body > ul.dirlist.clearfix > li > a','#chaptercontent','{\"l2_example_url\":\"https://www.biquge.in/b_122500/\",\"l3_preUrl\":\"\",\"l3_example_url\":\"https://www.biquge.in/b_122500/1.html\"}'),
	(42,41,NULL,'html > body > section.w-all > div.container.clearfix.mt20 > div.card.mt20.fulldir > div.body > ul.dirlist.three.clearfix > li.hide:nth-child(4) > a',NULL,'{\"l2_example_url\":\"http://www.booksky.cc/297003.html\",\"l3_preUrl\":\"\"}'),
	(43,42,NULL,'#main > div.box.mt10:nth-child(3) > div.book_list > dl > dd:nth-child(3) > a',NULL,'{\"l2_example_url\":\"https://www.xiaoshuoli.com/i64783/\",\"l3_preUrl\":\"\"}');

/*!40000 ALTER TABLE `t_domain_css_selector` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_future_crawler
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_future_crawler`;

CREATE TABLE `t_future_crawler` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `seed_id` int(100) NOT NULL,
  `domain_id` int(11) DEFAULT NULL,
  `level` int(100) NOT NULL,
  `page_url` varchar(100) NOT NULL DEFAULT '',
  `response` varchar(100) DEFAULT NULL,
  `crawler_state` char(1) NOT NULL DEFAULT '' COMMENT 'A:新增 P:处理中 F:完成 T:出错',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `callback_bean` varchar(100) DEFAULT NULL,
  `callback_level` int(11) DEFAULT NULL,
  `callback_status` int(11) DEFAULT NULL,
  `ref_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `getToBeCrawlByDomainId` (`crawler_state`,`domain_id`),
  KEY `getToBeCallBack` (`crawler_state`,`callback_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table t_future_crawler_cfg
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_future_crawler_cfg`;

CREATE TABLE `t_future_crawler_cfg` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `domain_name` varchar(100) NOT NULL DEFAULT '',
  `gap` int(3) NOT NULL DEFAULT '3' COMMENT '单位：秒',
  `last_crawl_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `charset` varchar(100) DEFAULT NULL,
  `enable` varchar(1) DEFAULT NULL,
  `default_parser_seed` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `t_future_crawler_cfg` WRITE;
/*!40000 ALTER TABLE `t_future_crawler_cfg` DISABLE KEYS */;

INSERT INTO `t_future_crawler_cfg` (`id`, `domain_name`, `gap`, `last_crawl_time`, `charset`, `enable`, `default_parser_seed`)
VALUES
	(1,'www.zwdu.com',3,'2018-07-20 19:01:54','gbk','Y',1),
	(2,'m.xiaoshuoli.com',3,'2019-01-09 08:52:50','utf8','Y',12),
	(3,'www.12kanshu.com',5,'2019-01-09 08:30:27','gb2312','Y',24),
	(4,'www.yqxs.cc',3,'2019-01-09 08:07:18','gb2312','Y',25),
	(5,'www.lewen8.com',5,'2018-12-10 10:23:07','utf8','N',26),
	(15,'www.biqugebook.com',5,'2018-12-05 11:11:10','gbk','Y',27),
	(16,'www.tycqxs.com',5,'2018-12-12 03:54:07','utf8','Y',27),
	(22,'www.liewen.cc',5,'2018-12-03 16:59:19','gbk','Y',27),
	(23,'book.qidian.com',5,'2018-12-04 13:29:39','utf8','A',27),
	(24,'www.baoliny.com',5,'2018-12-05 10:43:12','gbk','Y',27),
	(25,'www.88dush.com',5,'2018-12-04 15:04:07','gbk','Y',27),
	(26,'www.biqiku.com',5,'2018-12-04 14:40:17','gbk','Y',27),
	(27,'www.shushu8.com',5,'2018-12-04 14:19:48','gbk','A',27),
	(28,'m.biqudao.com',5,'2018-12-04 15:06:23','utf8','Y',27),
	(29,'www.aoyuge.com',5,'2018-12-05 11:07:10','gbk','Y',27),
	(30,'www.qu.la',5,'2018-12-05 10:23:06','utf8','A',27),
	(31,'www.sanjiangge.com',5,'2018-12-05 10:43:07','gbk','Y',27),
	(32,'www.vodtw.com',5,'2018-12-05 10:43:06','gb2312','Y',27),
	(33,'www.11kt.cn',5,'2018-12-05 10:15:13','gbk','A',27),
	(34,'www.126shu.com',5,'2018-12-05 10:15:13','gbk','A',27),
	(35,'www.23txt.com',5,'2018-12-05 10:15:13','gbk','A',27),
	(36,'www.beidouxin.com',5,'2018-12-05 10:17:14','gbk','A',27),
	(37,'www.jiaoyu123.com',5,'2018-12-05 10:44:36','gbk','Y',27),
	(38,'www.lindiankanshu.com',5,'2018-12-05 11:11:13','utf8','Y',27),
	(39,'www.kanshu.la',5,'2018-12-05 11:11:13','utf8','Y',27),
	(40,'www.biquge.in',5,'2018-12-05 11:11:10','utf8','Y',27),
	(41,'www.booksky.cc',5,'2018-12-05 10:17:14','utf8','A',27),
	(42,'www.xiaoshuoli.com',5,'2018-12-05 10:17:14','gbk','A',27);

/*!40000 ALTER TABLE `t_future_crawler_cfg` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_future_page_loader
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_future_page_loader`;

CREATE TABLE `t_future_page_loader` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `crawler_id` int(11) NOT NULL,
  `domain_id` int(11) DEFAULT NULL,
  `page_url` varchar(100) DEFAULT NULL,
  `response` varchar(100) DEFAULT NULL,
  `state_code` int(11) DEFAULT NULL,
  `loader_state` char(1) DEFAULT NULL,
  `paned` char(1) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  `plan_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `crawler_id` (`crawler_id`),
  KEY `getToBeCrawlByDomainId` (`domain_id`,`loader_state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table t_near_text
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_near_text`;

CREATE TABLE `t_near_text` (
  `near_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `article_id` int(11) NOT NULL,
  `text_id` int(11) NOT NULL,
  `origin_text` varchar(1000) DEFAULT NULL,
  `near_text` varchar(1000) DEFAULT NULL,
  `is_online` tinyint(1) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`near_id`),
  UNIQUE KEY `article_text_id` (`article_id`,`text_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table t_schedule_task
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_schedule_task`;

CREATE TABLE `t_schedule_task` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `bean_name` varchar(50) DEFAULT NULL,
  `bean_method` varchar(50) DEFAULT NULL,
  `last_update` timestamp NULL DEFAULT NULL,
  `fix_delay` int(11) DEFAULT NULL,
  `enable` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `t_schedule_task` WRITE;
/*!40000 ALTER TABLE `t_schedule_task` DISABLE KEYS */;

INSERT INTO `t_schedule_task` (`id`, `bean_name`, `bean_method`, `last_update`, `fix_delay`, `enable`)
VALUES
	(1,'brotherCroner','work','2019-01-09 08:52:45',10,1),
	(2,'chapterCallBackCroner','work','2019-01-09 08:52:44',10,1),
	(3,'futureCrawlerCallBackCroner','work','2019-01-09 08:52:50',1,1),
	(4,'futureCrawlerCroner','work','2019-01-09 08:52:50',1,1),
	(5,'futurePageLoaderCroner','work','2019-01-09 08:52:50',1,1),
	(6,'panCroner','work','2018-07-27 10:53:53',10,0),
	(7,'chapterContentCallBackCroner','work','2019-01-09 08:52:33',20,1),
	(8,'wooniuSynCroner','work','2019-01-09 08:52:20',60,1),
	(9,'clearSynChapterCroner','work','2019-01-02 13:40:11',7200,0),
	(12,'byStanderWooniuSynCroner','work','2019-01-09 08:51:49',60,1);

/*!40000 ALTER TABLE `t_schedule_task` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_seeds
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_seeds`;

CREATE TABLE `t_seeds` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(150) NOT NULL,
  `category_id` tinyint(4) NOT NULL,
  `modify_date` varchar(8) NOT NULL COMMENT 'aa',
  `parse_class` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `url` (`url`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

LOCK TABLES `t_seeds` WRITE;
/*!40000 ALTER TABLE `t_seeds` DISABLE KEYS */;

INSERT INTO `t_seeds` (`id`, `url`, `category_id`, `modify_date`, `parse_class`)
VALUES
	(12,'https://m.xiaoshuoli.com/ph/week_1.html',0,'','xslCrawler'),
	(1,'http://www.81zw.com/yanqing/',1,'20170302','zwduCrawler'),
	(2,'http://www.81zw.com/xianxia/',2,'20170105','N81ZW_Parser'),
	(3,'http://www.81zw.com/dushi/',3,'20161122','N81ZW_Parser'),
	(4,'http://www.81zw.com/lishi/',4,'20161122','N81ZW_Parser'),
	(5,'http://www.81zw.com/xuanhuan/',5,'','N81ZW_Parser'),
	(6,'http://www.81zw.com/wangyou/',6,'','N81ZW_Parser'),
	(7,'http://www.81zw.com/jingji/',7,'20140722','N81ZW_Parser'),
	(8,'http://www.81zw.com/yanqing/2.html',1,'20161109','N81ZW_Parser'),
	(9,'http://www.81zw.com/yanqing/3.html',1,'','N81ZW_Parser'),
	(10,'http://www.81zw.com/yanqing/4.html',1,'','N81ZW_Parser'),
	(11,'http://www.81zw.com/kehuan/',8,'20140722','N81ZW_Parser'),
	(13,'http://m.xiaoxiaoshuwu.com/top-allvisit-2/	',1,'20161111','N81ZW_Parser'),
	(14,'http://m.xiaoxiaoshuwu.com/top-allvisit-3/	',1,'','N81ZW_Parser'),
	(15,'http://m.xiaoxiaoshuwu.com/top-allvisit-4/	',1,'','N81ZW_Parser'),
	(16,'http://m.xiaoxiaoshuwu.com/top-allvisit-5/	',1,'','N81ZW_Parser'),
	(17,'http://m.xiaoxiaoshuwu.com/top-allvisit-6/	',1,'','N81ZW_Parser'),
	(18,'http://m.xiaoxiaoshuwu.com/top-allvisit-7/	',1,'','N81ZW_Parser'),
	(19,'http://m.xiaoxiaoshuwu.com/top-allvisit-8/	',1,'','N81ZW_Parser'),
	(20,'http://m.xiaoxiaoshuwu.com/top-allvisit-9/	',1,'','N81ZW_Parser'),
	(21,'http://m.xiaoxiaoshuwu.com/top-allvisit-10/	',1,'','N81ZW_Parser'),
	(22,'http://m.34zww.com/wapbook/6915/',8,'20170107','N43zww_Parser'),
	(23,'http://m.xiaoxiaoshuwu.com/top-allvisit-11/',8,'20161112','Xxsw_Parser'),
	(24,'http://www.12kanshu.com',8,'20161112','KKCrawler'),
	(25,'http://www.yqxs.cc',8,'20161112','yqxsCrawler'),
	(26,'https://www.lewen8.com/lw21321/',8,'20161112','LWCrawler'),
	(27,'https://cssSelectorCrawler.com',8,'20161112','cssSelectorCrawler');

/*!40000 ALTER TABLE `t_seeds` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_wooniu_syn_ids
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_wooniu_syn_ids`;

CREATE TABLE `t_wooniu_syn_ids` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `article_id` int(11) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  `pos` int(11) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `article_idx` (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
