CREATE TABLE `t_seeds` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(150) NOT NULL,
  `category_id` tinyint(4) NOT NULL,
  `modify_date` varchar(8) NOT NULL COMMENT 'aa',
  `parse_class` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `url` (`url`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5101 DEFAULT CHARSET=utf8;

CREATE TABLE `t_future_crawler_cfg` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `domain_name` varchar(100) NOT NULL DEFAULT '',
  `gap` int(3) NOT NULL DEFAULT '3' COMMENT '单位：秒',
  `last_crawl_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=56137 DEFAULT CHARSET=utf8;