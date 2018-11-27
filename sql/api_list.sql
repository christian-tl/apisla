/*
Navicat MySQL Data Transfer

Source Server         : 10.3.16.122
Source Server Version : 50718
Source Host           : 10.3.16.122:3306
Source Database       : apisla

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2018-11-23 16:18:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for api_list
-- ----------------------------
DROP TABLE IF EXISTS `api_list`;
CREATE TABLE `api_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `api_url` varchar(512) DEFAULT NULL,
  `api_name` varchar(255) DEFAULT NULL,
  `apiId` varchar(5) DEFAULT NULL,
  `sysId` varchar(5) DEFAULT NULL,
  `category` varchar(5) DEFAULT NULL COMMENT 'api分类，c1;促销 c2;价格 c3;商家 c4:库存 c5;购物车PC c6;购物车无线 c7;购物车结算 c8:购物车外部  c9;PIM ',
  `logs` varchar(5) DEFAULT NULL COMMENT '1:只有容器日志 2:只有程序日志 3:都有',
  `stattype` varchar(45) DEFAULT 'provider' COMMENT 'provider , consumer',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10315 DEFAULT CHARSET=utf8;
