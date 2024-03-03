CREATE DATABASE  IF NOT EXISTS `rag_chatman` ;
USE `rag_chatman`;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `email` varchar(64) COLLATE utf8_bin UNIQUE COMMENT '邮箱',
    `username` varchar(32) COLLATE utf8_bin UNIQUE COMMENT '用户名',
    `password` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '密码',
    `create_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='用户信息';
INSERT INTO `user` VALUES (1,'1073931273@qq.com','admin','e10adc3949ba59abbe56e057f20f883e','2022-02-17 09:16:20');

DROP TABLE IF EXISTS `library`;
CREATE TABLE `library` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` bigint NOT NULL COMMENT '用户id',
    `path` varchar(255) DEFAULT NULL COMMENT '微信用户唯一标识',
    `name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '姓名',
    `create_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE (`user_id`, `name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='库信息';

DROP TABLE IF EXISTS `chat`;
CREATE TABLE `chat` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` bigint NOT NULL COMMENT '用户id',
    `path` varchar(255) DEFAULT NULL COMMENT '微信用户唯一标识',
    `name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '姓名',
    `create_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE (`user_id`, `name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='库信息';

DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `openid` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '微信用户唯一标识',
    `name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '姓名',
    `phone` varchar(11) COLLATE utf8_bin DEFAULT NULL COMMENT '手机号',
    `sex` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '性别',
    `id_number` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '身份证号',
    `avatar` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '头像',
    `create_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='用户信息';
