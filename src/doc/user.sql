
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `rec_id` bigint(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL DEFAULT '0',
  `user_name` varchar(255) NOT NULL DEFAULT '',
  `sex` varchar(10) NOT NULL DEFAULT '',
  `address` varchar(255) NOT NULL DEFAULT '',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_valid` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`rec_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;


insert  into `user`(`rec_id`,`user_id`,`user_name`,`sex`,`address`,`create_time`,`is_valid`) values (1,1,'丽丽3','female','address1','2017-04-01 09:43:57',1),(2,2,'Lucy','female','address2','2017-04-02 09:43:57',1),(3,3,'Ted','male','address3','2017-04-03 09:43:57',1),(4,4,'Henry','male','address4','2017-04-04 09:43:57',1),(5,5,'John','male','address55','2017-04-05 09:43:57',1),(6,6,'6','male','address6','2017-04-06 09:43:57',1),(7,7,'7','male','address7','2017-04-06 09:43:57',1),(8,8,'8','male','address8','2017-04-06 09:43:57',1);


