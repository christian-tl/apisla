/*
Navicat MySQL Data Transfer

Source Server         : 10.3.16.122
Source Server Version : 50718
Source Host           : 10.3.16.122:3306
Source Database       : apisla

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2018-11-23 16:18:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for api_sla_log
-- ----------------------------
DROP TABLE IF EXISTS `api_sla_log`;
CREATE TABLE `api_sla_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ip` varchar(255) DEFAULT NULL,
  `api_url` varchar(512) DEFAULT NULL,
  `apiId` int(11) DEFAULT NULL,
  `sysId` int(11) DEFAULT NULL,
  `slatime` varchar(45) DEFAULT NULL,
  `total` int(11) DEFAULT NULL,
  `success` int(11) DEFAULT NULL,
  `fail` int(11) DEFAULT NULL,
  `sla500` int(11) DEFAULT NULL,
  `sla800` int(11) DEFAULT NULL,
  `sla995` int(11) DEFAULT NULL,
  `sla999` int(11) DEFAULT NULL,
  `stattype` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index1` (`apiId`,`sysId`,`slatime`,`stattype`) USING BTREE,
  KEY `index_slatime` (`slatime`)
) ENGINE=InnoDB AUTO_INCREMENT=1854693254 DEFAULT CHARSET=utf8;
