CREATE TABLE `admin` (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `password` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `admin`
--

INSERT INTO `admin` (`admin_id`, `password`) VALUES
(20180001, '123456');

CREATE TABLE `sensor_info` (
  `sensorId` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `sensorName` varchar(50) NOT NULL,
  `sensorAddress` varchar(50) NOT NULL,
  `sensorIntroduction` text,
  `sensorPrice` decimal(10,2) NOT NULL,
  `sensortableName` varchar(50) not null,
  `sensorState` smallint(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- 转存表中的数据 `sensor_info`
--
insert into `sensor_info` (`sensorId`,`sensorName`,`sensorAddress`,`sensorIntroduction`,`sensorPrice`,`sensorState`) values
(1,'温度传感器','儿童房','该温度传感器采集儿童房的温度','6','1'),
(2,'温度传感器','厨房','采集厨房的温度','6','1'),
(3,'温度传感器','客厅','采集客厅温度','6','1'),
(4,'温度传感器','主卧','采集主卧温度','6','1'),
(5,'湿度传感器','主卧','采集主卧湿度','10','1'),
(6,'湿度传感器','厨房','采集厨房湿度','10','1'),
(7,'湿度传感器','客厅','采集客厅温度','10','1'),
(8,'湿度传感器','儿童房','采集儿童房温度','10','1'),
(9,'烟雾传感器','厨房','采集厨房烟雾浓度','20','1'),
(10,'烟雾传感器','主卧','采集主卧烟雾浓度','20','1'),
(11,'烟雾传感器','儿童房','采集儿童房烟雾浓度','20','1'),
(12,'烟雾传感器','客厅','采集客厅烟雾浓度','20','1'),
(13,'红外人体传感器','客厅','采集客厅门口信息','10','1'),
(14,'红外人体传感器','阳台','采集阳台门口信息','10','1');


CREATE TABLE if not exists `thvalue` (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `thvalue` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE if not exists `cputemp` (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `cputemp` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE  if not exists `img`(
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `imgname` varchar(50) not null,
  `imgs` longblob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE  if not exists `sensortablename`(
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `tablename` varchar(50) not null,
  `sensortype` varchar(50) not null,
  `sensoraddress` varchar(50) not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
