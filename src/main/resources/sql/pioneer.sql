/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.7.18 : Database - pioneer_2.0
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`pioneer_2.0` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `pioneer_2.0`;

/*Table structure for table `Advise` */

DROP TABLE IF EXISTS `Advise`;

CREATE TABLE `Advise` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `studentid` varchar(20) NOT NULL COMMENT '学生编号',
  `facultyid` varchar(20) NOT NULL COMMENT '教师编号',
  `status` varchar(20) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `operator` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `Advise` */

LOCK TABLES `Advise` WRITE;

insert  into `Advise`(`id`,`studentid`,`facultyid`,`status`,`updateTime`,`operator`) values (1,'9201701103','9201701102','1','2017-09-30 10:27:38','9201701000'),(2,'9201701942','9201701100','1','2017-09-30 10:27:38','9201701000'),(4,'9201701309','9201701100','1','2017-09-30 10:27:38','9201701000'),(5,'9201701310','9201701100','1','2017-11-06 15:35:19','9201701000'),(6,'9201701101','9201701100','1','2017-09-30 10:27:38','9201701000'),(7,'9201701103','9201701100','1','2017-09-30 10:27:38','9201701000'),(9,'9201701784','9201701102','1','2017-09-30 10:27:38','9201701000'),(10,'9201701942','9201702937','1','2017-11-06 16:01:53','9201701000');

UNLOCK TABLES;

/*Table structure for table `Course` */

DROP TABLE IF EXISTS `Course`;

CREATE TABLE `Course` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `crn` varchar(20) NOT NULL COMMENT '课程编号',
  `name` varchar(100) NOT NULL COMMENT '课程名',
  `credits` int(11) DEFAULT NULL COMMENT '学分',
  `precrn` varchar(20) DEFAULT NULL COMMENT '要求课程',
  `couLev` varchar(11) DEFAULT NULL COMMENT '课程等级',
  `couSec` varchar(11) DEFAULT NULL COMMENT '课程Section',
  `classroom` varchar(100) DEFAULT NULL COMMENT '教室',
  `startDate` varchar(20) DEFAULT NULL COMMENT '起时间YYYY-MM-DD',
  `endDate` varchar(20) DEFAULT NULL COMMENT '终时间YYYY-MM-DD',
  `day` varchar(20) DEFAULT NULL COMMENT 'MWF/TTR',
  `startTime` varchar(20) DEFAULT NULL COMMENT '起时间HH:MM:SS',
  `endTime` varchar(20) DEFAULT NULL COMMENT '终时间HH:MM:SS',
  `capa` int(11) DEFAULT NULL COMMENT '最大人数',
  `facultyid` varchar(20) NOT NULL COMMENT '教师ID',
  `info` varchar(20) NOT NULL,
  `createtime` varchar(20) DEFAULT NULL,
  `updatetime` varchar(20) DEFAULT NULL,
  `comment` text COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `Course` */

LOCK TABLES `Course` WRITE;

insert  into `Course`(`id`,`crn`,`name`,`credits`,`precrn`,`couLev`,`couSec`,`classroom`,`startDate`,`endDate`,`day`,`startTime`,`endTime`,`capa`,`facultyid`,`info`,`createtime`,`updatetime`,`comment`) values (2,'120170164','Test2',4,'/','203','01','小教室','2017-09-01','2017-12-31','t/tr/','10:00:00','11:00:00',20,'9201701840','2017-01','2017-08-10 15:55:53','2017-09-03 00:00:12','qwerq2'),(3,'120170123','Test3',4,'120170164/','300','02','小教室','2017-09-01','2017-12-31','t/tr/','10:00:00','11:00:00',50,'9201701000','2017-01','2017-08-10 17:06:39','2017-08-10 17:06:39',NULL),(6,'120170174','upper',4,'/','499','01',NULL,'2017-06-01','2017-06-02','m/w/f/','11:00:00','12:00:00',1,'9201701102','2017-01','2017-08-18 16:51:05','2017-08-18 16:51:05',NULL),(7,'120170149','delete',4,'/','100','01','小教室','2017-09-01','2017-12-31','m/t/w/tr/f/','10:00:00','11:00:00',20,'9201701000','2017-01','2017-09-03 00:04:50','2017-09-03 00:04:50','qwe'),(8,'120170125','test4',4,'120170164/','100','01','阶梯教室','2017-09-01','2017-12-31','m/w/f/','10:00:00','11:00:00',50,'9201701848','2017-01','2017-09-03 14:05:52','2017-09-03 14:05:52',''),(9,'120170189','新课程',2,'120170164/120170125/','100','01','as','2018-10-01','2018-10-30','sa/s/','06:00:00','06:00:00',41,'9201702937','2017-01','2017-10-09 10:59:04','2017-10-09 10:59:04',NULL);

UNLOCK TABLES;

/*Table structure for table `Message` */

DROP TABLE IF EXISTS `Message`;

CREATE TABLE `Message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `senderid` varchar(50) NOT NULL COMMENT '发送者ID',
  `receiverid` varchar(50) DEFAULT NULL COMMENT '接收者ID',
  `subject` varchar(20) DEFAULT NULL COMMENT '类型',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `body` text COMMENT '内容',
  `status` varchar(10) DEFAULT NULL COMMENT '已读、未读、草稿',
  `date` varchar(50) DEFAULT NULL COMMENT '创建时间',
  `pic` text,
  `attachment` varchar(50) DEFAULT NULL,
  `tag` varchar(50) DEFAULT NULL,
  `labels` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8;

/*Data for the table `Message` */

LOCK TABLES `Message` WRITE;

insert  into `Message`(`id`,`senderid`,`receiverid`,`subject`,`title`,`body`,`status`,`date`,`pic`,`attachment`,`tag`,`labels`) values (49,'9000000000','9000000000','用户注册','注册信息','注意!接收到来自9201702937的请求注册信息','UNREAD','2017-09-20 13:37:44',NULL,NULL,'work','inbox/important/'),(50,'9000000000','9000000000','用户注册','注册信息','注意!接收到来自9201703122的请求注册信息','UNREAD','2017-09-20 13:39:29',NULL,NULL,'work','inbox/important/'),(54,'9000000000','9201702937',NULL,'账户信息','您的接收到来自管理员的一条消息:你的用户已成功创建','UNREAD','2017-09-22 16:58:04',NULL,NULL,'work','[\'inbox\',\'important\']'),(55,'9000000000','9000000000','用户注册','注册信息','注意!接收到来自9201702574的请求注册信息','UNREAD','2017-09-25 17:05:47',NULL,NULL,'work','inbox/important/'),(56,'9000000000','9201703122',NULL,'账户信息','您的接收到来自管理员的一条消息:你的用户已成功创建','UNREAD','2017-09-25 17:07:45',NULL,NULL,'work','[\'inbox\',\'important\']'),(57,'9000000000','9201702574',NULL,'账户信息','您的接收到来自管理员的一条消息:你的用户已成功创建','UNREAD','2017-09-25 17:07:58',NULL,NULL,'work','[\'inbox\',\'important\']'),(58,'9000000000','9000000000','用户注册','注册信息','注意!接收到来自9201702884的请求注册信息','UNREAD','2017-09-25 17:09:52',NULL,NULL,'work','inbox/important/'),(59,'9000000000','9201702884',NULL,'账户信息','您的接收到来自管理员的一条消息:你的用户已成功创建','UNREAD','2017-09-25 17:10:21',NULL,NULL,'work','[\'inbox\',\'important\']'),(60,'9000000000','9000000000','用户注册','注册信息','注意!接收到来自9201702887的请求注册信息','UNREAD','2017-09-30 10:19:10',NULL,NULL,'work','inbox/important/'),(61,'9000000000','9201702887',NULL,'账户信息','您的接收到来自管理员的一条消息:你的用户已成功创建','UNREAD','2017-09-30 10:22:02',NULL,NULL,'work','[\'inbox\',\'important\']'),(62,'9000000000','9201702887',NULL,'账户信息','您的接收到来自管理员的一条消息:你的用户已成功创建','UNREAD','2017-09-30 10:26:52',NULL,NULL,'work','[\'inbox\',\'important\']'),(63,'9000000000','9000000000','用户注册','注册信息','注意!接收到来自9201702745的请求注册信息','UNREAD','2017-09-30 11:40:30',NULL,NULL,'work','inbox/important/'),(64,'9000000000','9201702745',NULL,'账户信息','您的接收到来自管理员的一条消息:你的用户已成功创建','UNREAD','2017-09-30 11:42:36',NULL,NULL,'work','[\'inbox\',\'important\']'),(65,'9000000000','9201702887',NULL,'账户信息','您的接收到来自管理员的一条消息:你的用户已成功创建','UNREAD','2017-09-30 11:49:34',NULL,NULL,'work','[\'inbox\',\'important\']'),(66,'9000000000','9201702544',NULL,'账户信息','您的接收到来自管理员的一条消息:你的用户已成功创建','UNREAD','2017-09-30 11:51:45',NULL,NULL,'work','[\'inbox\',\'important\']'),(67,'9000000000','9000000000','用户注册','注册信息','注意!接收到来自9201702226的请求注册信息','UNREAD','2017-09-30 13:54:41',NULL,NULL,'work','inbox/important/'),(68,'9000000000','9000000000','用户注册','注册信息','注意!接收到来自9201702370的请求注册信息','UNREAD','2017-10-11 15:53:59',NULL,NULL,'work','inbox/important/'),(69,'9000000000','9000000000','用户注册','注册信息','注意!接收到来自9201702201的请求注册信息','UNREAD','2017-10-11 15:57:59',NULL,NULL,'work','inbox/important/'),(70,'9000000000','9000000000','用户注册','注册信息','注意!接收到来自9201702622的请求注册信息','UNREAD','2017-10-11 15:59:21',NULL,NULL,'work','inbox/important/');

UNLOCK TABLES;

/*Table structure for table `Person` */

DROP TABLE IF EXISTS `Person`;

CREATE TABLE `Person` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `userid` varchar(20) NOT NULL COMMENT '主编号',
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `firstname` varchar(100) NOT NULL COMMENT '名',
  `lastname` varchar(100) NOT NULL COMMENT '姓',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `info` varchar(100) NOT NULL COMMENT '信息',
  `birthday` varchar(100) DEFAULT NULL COMMENT '生日',
  `email` varchar(100) DEFAULT NULL COMMENT '电子邮件',
  `tel` varchar(100) DEFAULT NULL COMMENT '电话号',
  `qq` varchar(100) DEFAULT NULL COMMENT 'qq号',
  `weChat` varchar(100) DEFAULT NULL COMMENT '微信号',
  `dorm` varchar(100) DEFAULT NULL COMMENT '宿舍号',
  `gender` varchar(20) NOT NULL COMMENT '性别',
  `createTime` varchar(100) NOT NULL COMMENT '创建时间',
  `updateTime` varchar(100) NOT NULL COMMENT '修改时间',
  `status` varchar(20) NOT NULL COMMENT '状态',
  `type` varchar(20) NOT NULL COMMENT '属性',
  `comment` text COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

/*Data for the table `Person` */

LOCK TABLES `Person` WRITE;

insert  into `Person`(`id`,`userid`,`username`,`firstname`,`lastname`,`password`,`info`,`birthday`,`email`,`tel`,`qq`,`weChat`,`dorm`,`gender`,`createTime`,`updateTime`,`status`,`type`,`comment`) values (1,'9201701000','admin','administrator','system','e10adc3949ba59abbe56e057f20f883e','2017-01','1980-01-01','admin@pioneer.edu','13220184951','1343214384','1343214384','','male','2017-08-08 07:00:00','2017-10-09 17:50:50','1','s/f/a/','请勿删除'),(2,'9201701687','admLShilei','Shilei','Lin','c810ec29fed33d8e79ccee3b2589d115','2017-01','1996-11-12','lin.shilei@outlook.com',NULL,'1343214384',NULL,'','male','2017-08-08 12:44:23','2017-09-25 16:16:28','1','a/',NULL),(3,'9201701100','testTeacher','first','last','65b89c6cbb58ae07b15c9d001ef768e0','2017-01','1980-01-01','testTeacher@pionner.com',NULL,NULL,NULL,NULL,'female','2017-08-08 13:33:23','2017-09-25 16:17:43','1','f/',NULL),(4,'9201701101','testStudent','first1','last1','39957c100f046dadeeda60c077f87add','2017-01','1997-01-01','testStudent@pionner.com',NULL,NULL,NULL,NULL,'female','2017-08-08 13:33:24','2017-09-25 16:17:47','1','s/',NULL),(5,'9201701102','testTeacher2','first','last2','fa2c5ff0989f481f02ea54c6368382bc','2017-01','1980-01-01','testTeacher2@pioneer.com',NULL,NULL,NULL,NULL,'male','2017-08-10 18:00:00','2017-09-25 16:18:45','1','f/a/',NULL),(6,'9201701103','testStudent','first','last3','f0d4a0759284a87b7e7031091f230727','2017-01','1997-01-01','testStudent@pioneer.com',NULL,NULL,NULL,NULL,'male','2017-08-10 18:00:00','2017-09-25 16:18:41','1','s/',NULL),(7,'9201701942','Tdisable','disable','Test','e5485a9d0622cdf031da25286337d6dd','2017-01','1980-07-07','4564@qq.com',NULL,'4564',NULL,'78','male','2017-08-14 14:23:13','2017-09-25 16:18:38','1','s/',NULL),(8,'9201701309','ttestStu3','testStu3','test','804a8c28891c3211b3075baeb800c504','2017-01','1992-01-01','564654@qq.com',NULL,'456464',NULL,'4654','female','2017-08-14 15:51:55','2017-09-25 16:18:34','1','s/',NULL),(9,'9201701310','rick','KOKIA','ERI','487ef5eeb53eb03654198edfd545a513','2017-01','1992-01-01','54849684@qq.com','13385241458',NULL,NULL,NULL,'male','2017-08-14 15:51:55','2017-09-25 16:18:31','1','s/',NULL),(10,'9201701784','zhengsan784','三','张','a8156a924c4a701ca465bc5e9cd46484','2017-01','1996-12-31','1111@ee.edu',NULL,'123',NULL,'123','male','2017-09-01 21:23:00','2017-09-25 16:18:28','1','s/',NULL),(11,'9201701840','lili840','四','李','492d18d57e97001bd9bffa45911c55de','2017-01','1998-05-23','lisi@pioneer.edu','123','1234123','weca','12','female','2017-09-02 16:10:12','2017-09-25 16:18:25','0','f/a/','first'),(12,'9201701261','meimeili261','梅梅','李','16ea29c07c4a343f1aa569d147936d81','2017-01','1992-01-01','123124',NULL,'1123',NULL,'123','female','2017-09-02 16:12:49','2017-09-25 16:18:21','1','s/','new Student'),(13,'9201701848','ww184','五','王','f11f5d8566cccdef4e9cfa29d80a4223','2017-01','1997-08-12','7897@qq.com',NULL,'7987','7897','7894','female','2017-09-03 13:32:21','2017-09-25 16:18:18','1','f/','新老师'),(14,'9201701876','ew876','二','王','dff9c3e4686c4faceb0e7f9175b6defc','2017-01','2000-09-17','sadfasf@qq.com',NULL,'sadfasd','67897','546','female','2017-09-03 13:56:50','2017-09-25 16:18:15','1','s/',''),(15,'9201702937','DoeJon937','Doe','Jon','7d66eb52a44ad4dc575674396a78203d','2017-02','2017-09-08','1343214384@qq.com',NULL,'1343214384',NULL,NULL,'male','2017-09-22 16:57:59','2017-09-25 16:18:11','1','f/a/',''),(17,'9201702574','ew574','二','王','8052d26a6da25a6e69d4bb6ac3bb1280','2017-02','2017-09-25','asdfasfasdfasdf@qq.com',NULL,'61244423',NULL,NULL,'male','2017-09-25 17:07:58','2017-09-25 17:07:58','1','s/f/',''),(18,'9201702884','sw884','三','王','f5bb0c8de146c67b44babbf4e6584cc0','2017-02','2017-09-20','asdfasdfaf@qq.com',NULL,'659785412',NULL,NULL,'male','2017-09-25 17:10:21','2017-09-25 17:10:21','1','s/',''),(19,'9201702887','asdfsadf887','asdf','sadf','79f9f338abd398d724e15a17e2c6ee62','2017-02','2017-09-07','asdfasfasdfasdf@qq.com',NULL,'123213324',NULL,NULL,'male','2017-09-30 10:22:02','2017-09-30 10:22:02','1','s/',''),(21,'9201702745','asdfasdfasdfsad745','asdfasdf','asdfsad','533e37ee96dfcd0ed7639d69fc590095','2017-02','2017-09-07','adsfa@qq.com',NULL,'456498784784',NULL,NULL,'female','2017-09-30 11:42:36','2017-09-30 11:42:36','1','a/',''),(23,'9201702544','asdfsadf544','asdf','sadf','79f9f338abd398d724e15a17e2c6ee62','2017-02','2017-09-07','asdfasfasdfasdf@qq.com',NULL,'123213324',NULL,NULL,'male','2017-09-30 11:51:45','2017-09-30 11:51:45','1','f/','');

UNLOCK TABLES;

/*Table structure for table `Pin` */

DROP TABLE IF EXISTS `Pin`;

CREATE TABLE `Pin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pin` int(11) NOT NULL,
  `info` varchar(20) NOT NULL,
  `facultyid` varchar(11) DEFAULT NULL,
  `studentid` varchar(20) DEFAULT NULL,
  `role` int(11) NOT NULL COMMENT '1：选课，2：成绩',
  `startTime` varchar(20) NOT NULL,
  `endTime` varchar(20) NOT NULL,
  `createTime` varchar(20) DEFAULT NULL,
  `remark` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

/*Data for the table `Pin` */

LOCK TABLES `Pin` WRITE;

insert  into `Pin`(`id`,`pin`,`info`,`facultyid`,`studentid`,`role`,`startTime`,`endTime`,`createTime`,`remark`) values (1,996864,'2017-01','9201701000',NULL,2,'2017-11-07 00:00:00','2017-11-07 00:00:00','2017-11-07 12:57:11','Test'),(2,436668,'2017-01','9201701100',NULL,2,'2017-11-07 00:00:00','2017-11-07 00:00:00','2017-11-07 12:57:11','Test'),(3,543925,'2017-01',NULL,'9201701000',1,'2017-11-07 00:00:00','2017-11-07 00:00:00','2017-11-07 12:57:11','Test'),(4,602351,'2017-01','9201701102',NULL,2,'2017-11-07 00:00:00','2017-11-07 00:00:00','2017-11-07 12:57:11','Test'),(5,979142,'2017-01',NULL,'9201701101',1,'2017-11-07 00:00:00','2017-11-07 00:00:00','2017-11-07 12:57:11','Test'),(6,609280,'2017-01','9201701848',NULL,2,'2017-11-07 00:00:00','2017-11-07 00:00:00','2017-11-07 12:57:11','Test'),(7,680038,'2017-01',NULL,'9201701103',1,'2017-11-07 00:00:00','2017-11-07 00:00:00','2017-11-07 12:57:11','Test'),(8,978866,'2017-01',NULL,'9201701942',1,'2017-11-07 00:00:00','2017-11-07 00:00:00','2017-11-07 12:57:11','Test');

UNLOCK TABLES;

/*Table structure for table `Role` */

DROP TABLE IF EXISTS `Role`;

CREATE TABLE `Role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` varchar(20) NOT NULL,
  `Role` varchar(50) NOT NULL,
  `role_name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `Role` */

LOCK TABLES `Role` WRITE;

insert  into `Role`(`id`,`userid`,`Role`,`role_name`) values (2,'9000000000','1','系统管理员');

UNLOCK TABLES;

/*Table structure for table `Student` */

DROP TABLE IF EXISTS `Student`;

CREATE TABLE `Student` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Studentid` varchar(100) NOT NULL,
  `max_credits` int(20) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `Student` */

LOCK TABLES `Student` WRITE;

insert  into `Student`(`id`,`Studentid`,`max_credits`) values (1,'9201701101',12),(2,'9201701103',18),(3,'9201701942',18),(4,'9201701309',18),(5,'9201701310',18),(6,'9201701784',18),(7,'9201701261',18),(8,'9201701876',12),(9,'9201703122',12),(10,'9201702574',12),(11,'9201702884',12),(12,'9201702887',12),(13,'9201702887',12);

UNLOCK TABLES;

/*Table structure for table `TempCourse` */

DROP TABLE IF EXISTS `TempCourse`;

CREATE TABLE `TempCourse` (
  `id` int(11) NOT NULL,
  `crn` varchar(20) NOT NULL,
  `status` varchar(20) DEFAULT NULL,
  `course_json` text NOT NULL,
  `createtime` varchar(50) DEFAULT NULL,
  `updatetime` varchar(50) DEFAULT NULL,
  `operator` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `TempCourse` */

LOCK TABLES `TempCourse` WRITE;

UNLOCK TABLES;

/*Table structure for table `TempUser` */

DROP TABLE IF EXISTS `TempUser`;

CREATE TABLE `TempUser` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `userid` varchar(20) NOT NULL,
  `status` varchar(20) DEFAULT NULL COMMENT '0（处理中）,1（通过）,-1(拒绝)',
  `user_json` text NOT NULL,
  `createtime` varchar(50) DEFAULT NULL,
  `updatetime` varchar(50) DEFAULT NULL,
  `operator` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Data for the table `TempUser` */

LOCK TABLES `TempUser` WRITE;

insert  into `TempUser`(`id`,`userid`,`status`,`user_json`,`createtime`,`updatetime`,`operator`) values (10,'9201702887','0','{\"qq\":\"123213324\",\"birthday\":\"2017-09-07\",\"firstname\":\"asdf\",\"password\":\"79f9f338abd398d724e15a17e2c6ee62\",\"gender\":\"male\",\"tel\":\"23141234123\",\"email\":\"asdfasfasdfasdf@qq.com\",\"info\":\"2017-02\",\"lastname\":\"sadf\"}','2017-09-30 10:27:38','2017-09-30 11:51:45','9201701000'),(11,'9201702745','1','{\"qq\":\"456498784784\",\"birthday\":\"2017-09-07\",\"firstname\":\"asdfasdf\",\"password\":\"533e37ee96dfcd0ed7639d69fc590095\",\"gender\":\"female\",\"tel\":\"21316584798\",\"email\":\"adsfa@qq.com\",\"info\":\"2017-02\",\"lastname\":\"asdfsad\"}','2017-09-30 11:40:30','2017-09-30 11:42:36','9201701000'),(12,'9201702226','-1','{\"qq\":\"78545678786545\",\"birthday\":\"2017-09-12\",\"firstname\":\"asdf\",\"password\":\"8e580c77b8d2fc2cdde5163039c07b4a\",\"gender\":\"female\",\"tel\":\"54657847543\",\"email\":\"asdf@qq.com\",\"info\":\"2017-02\",\"lastname\":\"asdfsad\"}','2017-09-30 13:54:41','2017-09-30 13:55:00','9201701000'),(13,'9201702370','0','{\"qq\":\"258476\",\"birthday\":\"2017-10-12\",\"firstname\":\"wang\",\"password\":\"e10adc3949ba59abbe56e057f20f883e\",\"gender\":\"female\",\"tel\":\"125635874\",\"email\":\"adminn@admin.com\",\"info\":\"2017-02\",\"lastname\":\"shit\"}','2017-10-11 15:53:59','2017-10-11 15:53:59','9201701000'),(14,'9201702201','0','{\"qq\":\"5546844\",\"birthday\":\"2017-10-03\",\"firstname\":\"wang\",\"password\":\"e10adc3949ba59abbe56e057f20f883e\",\"gender\":\"female\",\"tel\":\"8648486\",\"email\":\"admin1@ad.com\",\"info\":\"2017-02\",\"lastname\":\"shit\"}','2017-10-11 15:57:59','2017-10-11 15:57:59','9201701000'),(15,'9201702622','0','{\"qq\":\"5546844\",\"birthday\":\"2017-10-03\",\"firstname\":\"wang\",\"password\":\"d41d8cd98f00b204e9800998ecf8427e\",\"gender\":\"female\",\"tel\":\"8648486\",\"email\":\"admin1@ad.com\",\"info\":\"2017-02\",\"lastname\":\"shit\"}','2017-10-11 15:59:21','2017-10-11 15:59:21','9201701000');

UNLOCK TABLES;

/*Table structure for table `Transcript` */

DROP TABLE IF EXISTS `Transcript`;

CREATE TABLE `Transcript` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `Studentid` varchar(20) NOT NULL COMMENT '学生ID',
  `crn` varchar(20) NOT NULL COMMENT '课程ID',
  `grade` varchar(20) NOT NULL COMMENT '成绩',
  `complete` varchar(20) NOT NULL COMMENT '完成状态',
  `assigntime` varchar(20) NOT NULL COMMENT '时间',
  `operator` varchar(50) NOT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

/*Data for the table `Transcript` */

LOCK TABLES `Transcript` WRITE;

insert  into `Transcript`(`id`,`Studentid`,`crn`,`grade`,`complete`,`assigntime`,`operator`) values (15,'9201701101','120170164','B','1','2017-11-06 17:02:44','9201701000'),(16,'9201701103','120170164','A','0','2017-08-24 09:34:40','9201701000'),(17,'9201701942','120170164','*','0','2017-08-23 17:47:54','9201701000'),(18,'9201701309','120170164','B','1','2017-08-24 09:35:14','9201701000'),(19,'9201701310','120170164','*','0','2017-09-01 20:10:41','9201701000'),(20,'9201701942','120170123','*','0','2017-09-02 14:46:23','9201701000'),(21,'9201701103','120170123','*','0','2017-09-02 14:46:24','9201701000'),(22,'9201701101','120170123','*','0','2017-09-02 14:46:27','9201701000'),(23,'9201701101','120170125','*','0','2017-09-03 14:07:15','9201701000'),(24,'9201701876','120170125','F','-1','2017-09-03 14:07:27','9201701000'),(26,'9201701784','120170164','*','0','2017-10-16 20:58:45','9201701000');

UNLOCK TABLES;

/* Function  structure for function  `Get_Complete_Credits` */

/*!50003 DROP FUNCTION IF EXISTS `Get_Complete_Credits` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` FUNCTION `Get_Complete_Credits`(sid varchar(100)) RETURNS int(11)
BEGIN
	DECLARE credits INT DEFAULT 0;
	DECLARE counts INT DEFAULT 0;
	
	SELECT COUNT(*) INTO counts FROM TranscriptView t WHERE t.Studentid = sid AND t.complete = '1';
	
	if counts > 0 then
		SELECT SUM(t.credits) INTO credits FROM TranscriptView t WHERE t.Studentid = sid and t.complete = '1';
	end if;	
	
	RETURN credits;
END */$$
DELIMITER ;

/* Function  structure for function  `Get_Course_Date` */

/*!50003 DROP FUNCTION IF EXISTS `Get_Course_Date` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` FUNCTION `Get_Course_Date`(crn VARCHAR(20)) RETURNS varchar(100) CHARSET utf8
BEGIN
	
	DECLARE startdate VARCHAR(100);
	DECLARE enddate VARCHAR(100);
	DECLARE ctime VARCHAR(100);
	
	SELECT c.startdate, c.enddate INTO startdate,enddate FROM Course c WHERE c.crn = crn;
	SET ctime = CONCAT(startdate," to ",enddate);
	
	
	RETURN ctime ;
    END */$$
DELIMITER ;

/* Function  structure for function  `Get_Course_Status` */

/*!50003 DROP FUNCTION IF EXISTS `Get_Course_Status` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` FUNCTION `Get_Course_Status`(crn varchar(100)) RETURNS int(11)
BEGIN
	DECLARE enddate VARCHAR(100);
	DECLARE startdate VARCHAR(100);
	DECLARE STATUS INT(11);
	
	SELECT c.enddate, c.startdate INTO enddate, startdate FROM Course c WHERE c.crn = crn;
	
	IF UNIX_TIMESTAMP(NOW())>= UNIX_TIMESTAMP(enddate) THEN
		SET STATUS = -1;
	elseIF UNIX_TIMESTAMP(NOW())>= UNIX_TIMESTAMP(startdate) THEN
		set status = 0;
	ELSE
		SET STATUS = 1;
	END IF;
	
	RETURN STATUS ;
    END */$$
DELIMITER ;

/* Function  structure for function  `Get_Course_Time` */

/*!50003 DROP FUNCTION IF EXISTS `Get_Course_Time` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` FUNCTION `Get_Course_Time`(crn VARCHAR(20)) RETURNS varchar(100) CHARSET utf8
BEGIN
	DECLARE starttime VARCHAR(100);
	DECLARE endtime VARCHAR(100);
	DECLARE ctime VARCHAR(100);
	
	SELECT c.starttime, c.endtime  INTO starttime,endtime FROM Course c WHERE c.crn = crn;
	SET ctime = CONCAT(starttime,"-",endtime);
	
	
	RETURN ctime ;
	
    END */$$
DELIMITER ;

/* Function  structure for function  `Get_In_Progress_Credits` */

/*!50003 DROP FUNCTION IF EXISTS `Get_In_Progress_Credits` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` FUNCTION `Get_In_Progress_Credits`(sid VARCHAR(100)) RETURNS int(11)
BEGIN
	DECLARE credits INT DEFAULT 0;
	DECLARE counts INT DEFAULT 0;
	
	SELECT COUNT(*) INTO counts FROM TranscriptView t WHERE t.Studentid = sid AND t.complete = '0';
	
	IF counts > 0 THEN
		SELECT SUM(t.credits) INTO credits FROM TranscriptView t WHERE t.Studentid = sid AND t.complete = '0';
	END IF;	
	
	RETURN credits;
	
    END */$$
DELIMITER ;

/* Function  structure for function  `Get_Name` */

/*!50003 DROP FUNCTION IF EXISTS `Get_Name` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` FUNCTION `Get_Name`(id varchar(20)) RETURNS varchar(100) CHARSET utf8
BEGIN
	
	DECLARE firstname varchar(100) CHARSET utf8;
	DECLARE lastname  VARCHAR(100) CHARSET utf8;
	declare fname varchar(100) CHARSET utf8;
	
	SELECT p.firstname, p.lastname INTO firstname, lastname FROM Person p WHERE p.userid = id;
	SET fname = CONCAT(lastname,",",firstname);
	
	
	RETURN fname;
    END */$$
DELIMITER ;

/* Function  structure for function  `Get_Not_Complete_Credits` */

/*!50003 DROP FUNCTION IF EXISTS `Get_Not_Complete_Credits` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` FUNCTION `Get_Not_Complete_Credits`(sid VARCHAR(100)) RETURNS int(11)
BEGIN
	DECLARE credits INT DEFAULT 0;
	DECLARE counts INT DEFAULT 0;
	
	SELECT COUNT(*) INTO counts FROM TranscriptView t WHERE t.Studentid = sid AND t.complete = '-1';
	
	IF counts > 0 THEN
		SELECT SUM(t.credits) INTO credits FROM TranscriptView t WHERE t.Studentid = sid AND t.complete = '-1';
	END IF;	
		
    return credits;		
    END */$$
DELIMITER ;

/* Function  structure for function  `Get_Remain_Capa` */

/*!50003 DROP FUNCTION IF EXISTS `Get_Remain_Capa` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` FUNCTION `Get_Remain_Capa`(crn VARCHAR(20)) RETURNS int(11)
BEGIN
	
	DECLARE remain INT DEFAULT 0;
	DECLARE counts INT DEFAULT 0;
	DECLARE capa INT DEFAULT 0;
	
	SELECT COUNT(*) INTO counts FROM TranscriptView t WHERE t.crn = crn;
	SElect c.capa INTO capa FROM Course c WHERE c.crn = crn;
	SET remain = capa - counts;
	
	RETURN remain;
    END */$$
DELIMITER ;

/*Table structure for table `AdviseView` */

DROP TABLE IF EXISTS `AdviseView`;

/*!50001 DROP VIEW IF EXISTS `AdviseView` */;
/*!50001 DROP TABLE IF EXISTS `AdviseView` */;

/*!50001 CREATE TABLE  `AdviseView`(
 `id` int(11) NOT NULL  default '0' ,
 `Studentid` varchar(20) NOT NULL ,
 `sname` varchar(100) NULL ,
 `facultyid` varchar(20) NOT NULL ,
 `fname` varchar(100) NULL ,
 `status` varchar(20) NULL ,
 `updateTime` varchar(20) NULL ,
 `operator` varchar(50) NULL ,
 `oname` varchar(100) NULL 
)*/;

/*Table structure for table `CourseView` */

DROP TABLE IF EXISTS `CourseView`;

/*!50001 DROP VIEW IF EXISTS `CourseView` */;
/*!50001 DROP TABLE IF EXISTS `CourseView` */;

/*!50001 CREATE TABLE  `CourseView`(
 `id` int(11) NOT NULL  default '0' ,
 `crn` varchar(20) NOT NULL ,
 `name` varchar(100) NOT NULL ,
 `credits` int(11) NULL ,
 `couLev` varchar(11) NULL ,
 `couSec` varchar(11) NULL ,
 `capa` int(11) NULL ,
 `remain` int(11) NULL ,
 `facultyid` varchar(20) NOT NULL ,
 `faculty` varchar(100) NULL ,
 `date` varchar(100) NULL ,
 `Time` varchar(100) NULL ,
 `status` int(11) NULL ,
 `day` varchar(20) NULL ,
 `updatetime` varchar(20) NULL 
)*/;

/*Table structure for table `MessageView` */

DROP TABLE IF EXISTS `MessageView`;

/*!50001 DROP VIEW IF EXISTS `MessageView` */;
/*!50001 DROP TABLE IF EXISTS `MessageView` */;

/*!50001 CREATE TABLE  `MessageView`(
 `id` int(11) NOT NULL  default '0' ,
 `senderid` varchar(50) NOT NULL ,
 `receiverid` varchar(50) NULL ,
 `subject` varchar(20) NULL ,
 `title` varchar(255) NULL ,
 `body` text NULL ,
 `status` varchar(10) NULL ,
 `date` varchar(50) NULL ,
 `pic` text NULL ,
 `attachment` varchar(50) NULL ,
 `labels` varchar(50) NULL ,
 `tag` varchar(50) NULL ,
 `role_name` varchar(50) NOT NULL ,
 `semail` varchar(100) NULL ,
 `sfirst` varchar(100) NOT NULL ,
 `slast` varchar(100) NOT NULL ,
 `pemail` varchar(100) NULL ,
 `pfirst` varchar(100) NOT NULL ,
 `plast` varchar(100) NOT NULL 
)*/;

/*Table structure for table `StudentView` */

DROP TABLE IF EXISTS `StudentView`;

/*!50001 DROP VIEW IF EXISTS `StudentView` */;
/*!50001 DROP TABLE IF EXISTS `StudentView` */;

/*!50001 CREATE TABLE  `StudentView`(
 `id` int(11) unsigned NOT NULL  default '0' ,
 `Studentid` varchar(100) NOT NULL ,
 `max_credits` int(20) unsigned NOT NULL ,
 `lastname` varchar(100) NOT NULL ,
 `firstname` varchar(100) NOT NULL ,
 `status` varchar(20) NOT NULL ,
 `complete` int(11) NULL ,
 `progress` int(11) NULL ,
 `incomplete` int(11) NULL 
)*/;

/*Table structure for table `TranscriptView` */

DROP TABLE IF EXISTS `TranscriptView`;

/*!50001 DROP VIEW IF EXISTS `TranscriptView` */;
/*!50001 DROP TABLE IF EXISTS `TranscriptView` */;

/*!50001 CREATE TABLE  `TranscriptView`(
 `id` int(11) NOT NULL  default '0' ,
 `Studentid` varchar(20) NOT NULL ,
 `sfirst` varchar(100) NOT NULL ,
 `slast` varchar(100) NOT NULL ,
 `crn` varchar(20) NOT NULL ,
 `Coursename` varchar(100) NOT NULL ,
 `credits` int(11) NULL ,
 `grade` varchar(20) NOT NULL ,
 `complete` varchar(20) NOT NULL ,
 `facultyid` varchar(20) NOT NULL ,
 `ffirst` varchar(100) NOT NULL ,
 `flast` varchar(100) NOT NULL ,
 `sname` varchar(100) NULL ,
 `fname` varchar(100) NULL ,
 `date` varchar(100) NULL ,
 `time` varchar(100) NULL ,
 `day` varchar(20) NULL ,
 `assigntime` varchar(20) NOT NULL ,
 `operator` varchar(50) NOT NULL ,
 `oname` varchar(100) NULL 
)*/;

/*View structure for view AdviseView */

/*!50001 DROP TABLE IF EXISTS `AdviseView` */;
/*!50001 DROP VIEW IF EXISTS `AdviseView` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `AdviseView` AS select `a`.`id` AS `id`,`a`.`studentid` AS `Studentid`,`Get_Name`(`a`.`studentid`) AS `sname`,`a`.`facultyid` AS `facultyid`,`Get_Name`(`a`.`facultyid`) AS `fname`,`a`.`status` AS `status`,`a`.`updateTime` AS `updateTime`,`a`.`operator` AS `operator`,`Get_Name`(`a`.`operator`) AS `oname` from `Advise` `a` */;

/*View structure for view CourseView */

/*!50001 DROP TABLE IF EXISTS `CourseView` */;
/*!50001 DROP VIEW IF EXISTS `CourseView` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `CourseView` AS select `c`.`id` AS `id`,`c`.`crn` AS `crn`,`c`.`name` AS `name`,`c`.`credits` AS `credits`,`c`.`couLev` AS `couLev`,`c`.`couSec` AS `couSec`,`c`.`capa` AS `capa`,`Get_Remain_Capa`(`c`.`crn`) AS `remain`,`c`.`facultyid` AS `facultyid`,`Get_Name`(`c`.`facultyid`) AS `faculty`,`Get_Course_Date`(`c`.`crn`) AS `date`,`Get_Course_Time`(`c`.`crn`) AS `Time`,`Get_Course_Status`(`c`.`crn`) AS `status`,`c`.`day` AS `day`,`c`.`updatetime` AS `updatetime` from `Course` `c` */;

/*View structure for view MessageView */

/*!50001 DROP TABLE IF EXISTS `MessageView` */;
/*!50001 DROP VIEW IF EXISTS `MessageView` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `MessageView` AS select `m`.`id` AS `id`,`m`.`senderid` AS `senderid`,`m`.`receiverid` AS `receiverid`,`m`.`subject` AS `subject`,`m`.`title` AS `title`,`m`.`body` AS `body`,`m`.`status` AS `status`,`m`.`date` AS `date`,`m`.`pic` AS `pic`,`m`.`attachment` AS `attachment`,`m`.`labels` AS `labels`,`m`.`tag` AS `tag`,`r`.`role_name` AS `role_name`,`s`.`email` AS `semail`,`s`.`firstname` AS `sfirst`,`s`.`lastname` AS `slast`,`p`.`email` AS `pemail`,`p`.`firstname` AS `pfirst`,`p`.`lastname` AS `plast` from (((`Message` `m` join `Role` `r`) join `Person` `p`) join `Person` `s`) where ((`m`.`senderid` = convert(`r`.`userid` using utf8)) and (`m`.`receiverid` = `p`.`userid`) and (`m`.`senderid` = `s`.`userid`)) */;

/*View structure for view StudentView */

/*!50001 DROP TABLE IF EXISTS `StudentView` */;
/*!50001 DROP VIEW IF EXISTS `StudentView` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `StudentView` AS select `s`.`id` AS `id`,`s`.`Studentid` AS `Studentid`,`s`.`max_credits` AS `max_credits`,`p`.`lastname` AS `lastname`,`p`.`firstname` AS `firstname`,`p`.`status` AS `status`,`Get_Complete_Credits`(`s`.`Studentid`) AS `complete`,`Get_In_Progress_Credits`(`s`.`Studentid`) AS `progress`,`Get_Not_Complete_Credits`(`s`.`Studentid`) AS `incomplete` from (`Student` `s` join `Person` `p`) where (`p`.`userid` = `s`.`Studentid`) */;

/*View structure for view TranscriptView */

/*!50001 DROP TABLE IF EXISTS `TranscriptView` */;
/*!50001 DROP VIEW IF EXISTS `TranscriptView` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `TranscriptView` AS select `t`.`id` AS `id`,`t`.`Studentid` AS `Studentid`,`p1`.`firstname` AS `sfirst`,`p1`.`lastname` AS `slast`,`t`.`crn` AS `crn`,`c`.`name` AS `Coursename`,`c`.`credits` AS `credits`,`t`.`grade` AS `grade`,`t`.`complete` AS `complete`,`c`.`facultyid` AS `facultyid`,`p2`.`firstname` AS `ffirst`,`p2`.`lastname` AS `flast`,`Get_Name`(`p1`.`userid`) AS `sname`,`Get_Name`(`p2`.`userid`) AS `fname`,`Get_Course_Date`(`t`.`crn`) AS `date`,`Get_Course_Time`(`t`.`crn`) AS `time`,`c`.`day` AS `day`,`t`.`assigntime` AS `assigntime`,`t`.`operator` AS `operator`,`Get_Name`(`t`.`operator`) AS `oname` from (((`Transcript` `t` join `Course` `c`) join `Person` `p1`) join `Person` `p2`) where ((`t`.`crn` = `c`.`crn`) and (`p1`.`userid` = `t`.`Studentid`) and (`p2`.`userid` = `c`.`facultyid`)) */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
