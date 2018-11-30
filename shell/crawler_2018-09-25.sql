# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 39.107.103.131 (MySQL 5.6.37)
# Database: crawler
# Generation Time: 2018-09-25 02:18:58 +0000
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
  `click_times` int(11) DEFAULT '0' COMMENT '点击次数',
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
  KEY `artile_id` (`artile_id`)
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
	(6,'select distinct(artile_id) from t_chapter where collect_flag=1');

/*!40000 ALTER TABLE `t_comments` ENABLE KEYS */;
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
  PRIMARY KEY (`id`)
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
	(2,'m.xiaoshuoli.com',3,'2018-09-25 10:16:25','utf8','Y',12),
	(3,'www.12kanshu.com',5,'2018-09-25 06:03:09','gb2312','Y',24),
	(4,'www.yqxs.cc',3,'2018-09-25 01:29:45','gb2312','Y',25),
	(5,'www.lewen8.com',5,'2018-07-27 22:03:04','utf8','Y',26);

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
  KEY `crawler_id` (`crawler_id`)
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
	(1,'brotherCroner','work','2018-09-25 10:16:23',10,1),
	(2,'chapterCallBackCroner','work','2018-09-25 10:16:23',10,1),
	(3,'futureCrawlerCallBackCroner','work','2018-09-25 10:16:18',10,1),
	(4,'futureCrawlerCroner','work','2018-09-25 10:16:28',1,1),
	(5,'futurePageLoaderCroner','work','2018-09-25 10:16:28',1,1),
	(6,'panCroner','work','2018-07-27 10:53:53',10,0),
	(7,'chapterContentCallBackCroner','work','2018-09-25 10:16:08',20,1);

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
	(26,'https://www.lewen8.com/lw21321/',8,'20161112','LWCrawler');

/*!40000 ALTER TABLE `t_seeds` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
