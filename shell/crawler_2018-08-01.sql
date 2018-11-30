# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 39.107.103.131 (MySQL 5.6.37)
# Database: crawler
# Generation Time: 2018-08-01 11:13:59 +0000
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




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
