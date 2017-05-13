-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 2017-04-29 08:08:43
-- 服务器版本： 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `iteam`
--

-- --------------------------------------------------------

--
-- 表的结构 `facultylist`
--

CREATE TABLE IF NOT EXISTS `facultylist` (
  `faculty_name` varchar(255) CHARACTER SET utf32 COLLATE utf32_unicode_ci NOT NULL COMMENT '院系名',
  `faculty_teamnum` int(11) NOT NULL COMMENT '院系下社团数',
  `faculty_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '院系标识id',
  PRIMARY KEY (`faculty_id`),
  KEY `faculty_id` (`faculty_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- 转存表中的数据 `facultylist`
--

INSERT INTO `facultylist` (`faculty_name`, `faculty_teamnum`, `faculty_id`) VALUES
('信院', 1, 2);

-- --------------------------------------------------------

--
-- 表的结构 `passagetag`
--

CREATE TABLE IF NOT EXISTS `passagetag` (
  `tag_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `tag_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章标签的唯一标识',
  PRIMARY KEY (`tag_id`),
  KEY `tag_id` (`tag_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- 转存表中的数据 `passagetag`
--

INSERT INTO `passagetag` (`tag_name`, `tag_id`) VALUES
('社团花边', 1);

-- --------------------------------------------------------

--
-- 表的结构 `team`
--

CREATE TABLE IF NOT EXISTS `team` (
  `team_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '社团名',
  `team_brief` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '社团简介',
  `team_tag1` int(11) NOT NULL DEFAULT '0' COMMENT '社团标签之一',
  `team_tag2` int(11) NOT NULL DEFAULT '0' COMMENT '社团标签之二',
  `team_tag3` int(11) NOT NULL DEFAULT '0' COMMENT '社团标签之三',
  `team_tag4` int(11) NOT NULL DEFAULT '0' COMMENT '社团标签之四',
  `team_faculty` int(11) NOT NULL DEFAULT '0' COMMENT '社团所属院系',
  `team_logo` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '社团头像路径',
  `team_heat` int(11) NOT NULL DEFAULT '0' COMMENT '社团热度',
  `team_scale` int(11) NOT NULL DEFAULT '1' COMMENT '社团人数',
  `team_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '社团id',
  PRIMARY KEY (`team_id`),
  KEY `team_id` (`team_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 COMMENT='社团表' AUTO_INCREMENT=2 ;

--
-- 转存表中的数据 `team`
--

INSERT INTO `team` (`team_name`, `team_brief`, `team_tag1`, `team_tag2`, `team_tag3`, `team_tag4`, `team_faculty`, `team_logo`, `team_heat`, `team_scale`, `team_id`) VALUES
('IT', 'NONE', 1, 0, 0, 0, 2, 'image/teamlogo/default.jpg', 0, 1, 1);

-- --------------------------------------------------------

--
-- 表的结构 `teamactivity`
--

CREATE TABLE IF NOT EXISTS `teamactivity` (
  `team_id` int(11) NOT NULL COMMENT '社团标识id',
  `activity_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '社团活动名',
  `activity_content` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '活动内容介绍',
  `activity_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '关于活动的时间描述',
  `activity_participant` int(11) NOT NULL COMMENT '活动报名人数',
  `activity_participant_id` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '报名人的存储形式',
  `activity_privacy` tinyint(1) NOT NULL COMMENT '活动公开/私有'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- 转存表中的数据 `teamactivity`
--

INSERT INTO `teamactivity` (`team_id`, `activity_title`, `activity_content`, `activity_time`, `activity_participant`, `activity_participant_id`, `activity_privacy`) VALUES
(1, '踏青', '美滋滋', '2017-4-22 10:52', 1, '1', 1);

-- --------------------------------------------------------

--
-- 表的结构 `teampassage`
--

CREATE TABLE IF NOT EXISTS `teampassage` (
  `team_id` int(11) NOT NULL COMMENT '社团名',
  `passage_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '推文标题',
  `passage_content` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '推文内容',
  `passage_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '推文发布时间',
  `passage_readnum` int(11) NOT NULL COMMENT '文章浏览量',
  `passage_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章索引',
  `passage_picture` varchar(255) NOT NULL COMMENT '文章图片',
  `team_logo` varchar(255) NOT NULL COMMENT '社团头像',
  `team_name` varchar(255) NOT NULL COMMENT '社团名',
  `passage_tag` int(11) NOT NULL COMMENT '文章分类标签ID',
  PRIMARY KEY (`passage_id`),
  KEY `passage_id` (`passage_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- 转存表中的数据 `teampassage`
--

INSERT INTO `teampassage` (`team_id`, `passage_title`, `passage_content`, `passage_time`, `passage_readnum`, `passage_id`, `passage_picture`, `team_logo`, `team_name`, `passage_tag`) VALUES
(1, '庆祝爱特项目一完成', '美滋滋', '2017-4-22 10:53', 1, 1, 'image/passage/default.png', 'image/teamlogo/default.jpg', 'IT', 1);

-- --------------------------------------------------------

--
-- 表的结构 `teamtag`
--

CREATE TABLE IF NOT EXISTS `teamtag` (
  `tag_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '标签的名字',
  `tag_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'tag标识',
  PRIMARY KEY (`tag_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- 转存表中的数据 `teamtag`
--

INSERT INTO `teamtag` (`tag_name`, `tag_id`) VALUES
('IT技术', 1);

-- --------------------------------------------------------

--
-- 表的结构 `teamuser`
--

CREATE TABLE IF NOT EXISTS `teamuser` (
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '用户名',
  `user_stunum` bigint(20) NOT NULL COMMENT '用户学号',
  `user_sex` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '用户性别',
  `user_brief` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '用户个人简介',
  `user_signature` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '用户个性签名',
  `user_head` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '用户头像路径',
  `user_faculty` int(11) NOT NULL COMMENT '用户院系',
  `user_code` varchar(255) CHARACTER SET utf32 COLLATE utf32_unicode_ci NOT NULL COMMENT '用户密码',
  PRIMARY KEY (`user_stunum`),
  KEY `user_stunum` (`user_stunum`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- 转存表中的数据 `teamuser`
--

INSERT INTO `teamuser` (`user_name`, `user_stunum`, `user_sex`, `user_brief`, `user_signature`, `user_head`, `user_faculty`, `user_code`) VALUES
('小明', 15020031000, '男', '蛤？', '我很帅', 'image/userhead/default.png', 1, 'Itdn2016'),
('Liz', 15020031001, '女', '这个人很懒，什么也都没留下', '这个人很懒，什么也都没留下', 'image/userhead/default.png', 1, 'Liz');

-- --------------------------------------------------------

--
-- 表的结构 `usermessage`
--

CREATE TABLE IF NOT EXISTS `usermessage` (
  `user_stunum` bigint(20) NOT NULL COMMENT '学生学号',
  `team_id` int(11) NOT NULL COMMENT '社团标识id',
  `message_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '消息标题',
  `message_content` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '消息正文',
  `message_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '消息发布时间',
  `message_unread` tinyint(1) NOT NULL DEFAULT '1' COMMENT '消息是否未阅读',
  `message_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '消息唯一标识',
  PRIMARY KEY (`message_id`),
  KEY `message_id` (`message_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- 转存表中的数据 `usermessage`
--

INSERT INTO `usermessage` (`user_stunum`, `team_id`, `message_title`, `message_content`, `message_time`, `message_unread`, `message_id`) VALUES
(15020031000, 1, '恭喜你成为IT2017的一员', '美滋滋', '2017-4-22 10:58', 1, 1);

-- --------------------------------------------------------

--
-- 表的结构 `userteamrelation`
--

CREATE TABLE IF NOT EXISTS `userteamrelation` (
  `user_stunum` bigint(20) NOT NULL COMMENT '用户学号',
  `team_id` int(11) NOT NULL COMMENT '社团标识id',
  `user_team_relation` int(11) NOT NULL COMMENT '用户与社团关系（关注，报名中，成员，管理员）',
  `relation_discription` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '关系描述（用于在报名时作为附加内容）'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='用户社团关系表';

--
-- 转存表中的数据 `userteamrelation`
--

INSERT INTO `userteamrelation` (`user_stunum`, `team_id`, `user_team_relation`, `relation_discription`) VALUES
(15020031000, 1, 4, '爱特2017维护部门负责人'),
(15020031001, 1, 2, '');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
