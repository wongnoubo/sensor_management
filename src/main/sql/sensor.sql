CREATE TABLE `admin` (
  `admin_id` int(11) NOT NULL,
  `password` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `admin`
--

INSERT INTO `admin` (`admin_id`, `password`) VALUES
(20180001, '111111');

CREATE TABLE `sensor_info` (
  `sensor_id` bigint(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `address` varchar(50) NOT NULL,
  `introduction` text,
  `price` decimal(10,2) NOT NULL,
  `state` smallint(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- 转存表中的数据 `sensor_info`
--
insert into `sensor_info` (`sensor_id`,`name`,`address`,`introduction`,`price`,`state`) values
(01000001,'温度传感器','儿童房','该温度传感器采集儿童房的温度','6','1'),
(01000002,'温度传感器','厨房','采集厨房的温度','6','1'),
(01000003,'温度传感器','客厅','采集客厅温度','6','1'),
(01000004,'温度传感器','主卧','采集主卧温度','6','1'),
(02000001,'湿度传感器','主卧','采集主卧湿度','10','1'),
(02000002,'湿度传感器','厨房','采集厨房湿度','10','1'),
(02000003,'湿度传感器','客厅','采集客厅温度','10','1'),
(02000004,'湿度传感器','儿童房','采集儿童房温度','10','1'),
(03000001,'烟雾传感器','厨房','采集厨房烟雾浓度','20','1'),
(03000002,'烟雾传感器','主卧','采集主卧烟雾浓度','20','1'),
(03000003,'烟雾传感器','儿童房','采集儿童房烟雾浓度','20','1'),
(03000004,'烟雾传感器','客厅','采集客厅烟雾浓度','20','1'),
(04000001,'红外人体传感器','客厅','采集客厅门口信息','10','1'),
(04000002,'红外人体传感器','阳台','采集阳台门口信息','10','1');

ALTER TABLE `admin`
  ADD PRIMARY KEY (`admin_id`);

ALTER TABLE `sensor_info`
  ADD PRIMARY KEY (`sensor_id`);

alter table sensor_info modify sensor_id int auto_increment;--设置为自增