/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : online_shopping

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 30/07/2024 16:21:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for address_book
-- ----------------------------
DROP TABLE IF EXISTS `address_book`;
CREATE TABLE `address_book`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `province_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '省级区划编号',
  `province_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '省级名称',
  `city_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '市级区划编号',
  `city_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '市级名称',
  `district_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '区级区划编号',
  `district_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '区级名称',
  `detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '详细地址',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `is_default` int NOT NULL COMMENT '是否为默认地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '地址簿' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of address_book
-- ----------------------------
INSERT INTO `address_book` VALUES (1, 12, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '江西省南昌市青云谱区象湖公寓警民路北区', '2024-03-05 22:33:43', '2024-03-23 20:14:17', 1);
INSERT INTO `address_book` VALUES (2, 6, '11', '北京市', '1101', '秦皇岛市', '110101', '东城区', '看了看看看', '2024-03-06 11:10:43', '2024-03-08 15:56:31', 1);
INSERT INTO `address_book` VALUES (4, 12, '13', '河北省', '1303', '秦皇岛市', '130304', '北戴河区', '看了看看看', '2024-03-06 11:10:43', '2024-03-23 20:14:17', 0);
INSERT INTO `address_book` VALUES (5, 1, '11', '北京市', '1101', '市辖区', '110102', '西城区', '河北省唐山市路北区象湖公寓警民路北区', '2024-03-05 22:33:43', '2024-06-12 15:12:43', 1);
INSERT INTO `address_book` VALUES (6, 1, '15', '内蒙古自治区', '1504', '赤峰市', '150421', '阿鲁科尔沁旗', '嘻嘻', '2024-03-06 11:10:43', '2024-06-12 15:12:43', 0);
INSERT INTO `address_book` VALUES (7, 30, '11', '北京市', '1101', '市辖区', '110102', '西城区', '123', '2024-03-17 17:10:08', '2024-03-17 17:10:08', 1);
INSERT INTO `address_book` VALUES (8, 1, '13', '河北省', '1303', '秦皇岛市', '130304', '北戴河区', '1234543', '2024-04-09 17:32:23', '2024-06-12 15:12:43', 0);
INSERT INTO `address_book` VALUES (11, 131, '12', '天津市', '1201', '市辖区', '120103', '河西区', '666', '2024-05-02 15:17:41', '2024-05-02 15:17:41', 1);
INSERT INTO `address_book` VALUES (12, 1, '14', '山西省', '1405', '晋城市', '140525', '泽州县', '123', '2024-05-07 14:32:41', '2024-06-12 15:12:43', 0);

-- ----------------------------
-- Table structure for apply_for_create_store
-- ----------------------------
DROP TABLE IF EXISTS `apply_for_create_store`;
CREATE TABLE `apply_for_create_store`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '申请编号',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `store_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商店名称',
  `province_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '省编码',
  `province_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '省名',
  `city_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '市编号',
  `city_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '市名称',
  `district_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '区编码',
  `district_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '区名称',
  `status` int NOT NULL COMMENT '申请状态 0 待审核 1 成功 2 拒绝',
  `detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '详细地址',
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '拒绝理由',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of apply_for_create_store
-- ----------------------------
INSERT INTO `apply_for_create_store` VALUES (21, 'msxwansui', 'e10adc3949ba59abbe56e057f20f883e', '123456', '12', '天津市', '1201', '市辖区', '120102', '河东区', 2, '1234567891000', '123\n456', '2024-03-25 21:46:18', '2024-03-25 21:48:38');
INSERT INTO `apply_for_create_store` VALUES (23, 'msxwansui', 'e10adc3949ba59abbe56e057f20f883e', '我的小小店铺', '13', '河北省', '1302', '唐山市', '130203', '路北区', 1, '12345647892', '', '2024-03-25 22:04:37', '2024-03-25 22:05:15');
INSERT INTO `apply_for_create_store` VALUES (24, '666666', 'e10adc3949ba59abbe56e057f20f883e', '11111', '12', '天津市', '1201', '市辖区', '120103', '河西区', 1, '789465168456', '', '2024-05-17 10:52:25', '2024-05-17 13:54:30');
INSERT INTO `apply_for_create_store` VALUES (25, 'xxxxxx', 'e10adc3949ba59abbe56e057f20f883e', 'fsaddwefsa', '12', '天津市', '1201', '市辖区', '120102', '河东区', 2, 'fsawefsaweqwr', 'fwasda', '2024-06-12 15:05:02', '2024-06-12 15:05:19');
INSERT INTO `apply_for_create_store` VALUES (26, 'xxxxxx', 'e10adc3949ba59abbe56e057f20f883e', 'fwaesad', '13', '河北省', '1302', '唐山市', '130203', '路北区', 1, 'ewqrsafsagdsre', '', '2024-06-12 15:05:33', '2024-06-12 15:05:42');

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '购物车id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `goods_id` bigint NOT NULL COMMENT '商品id',
  `goods_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
  `number` int NOT NULL COMMENT '购买数量',
  `total_price` decimal(8, 2) NOT NULL COMMENT '总价',
  `cover_pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `goods_price` decimal(8, 2) NULL DEFAULT NULL COMMENT '商品单价',
  `discount` double NULL DEFAULT NULL COMMENT '商品折扣',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 130 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '购物车' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cart
-- ----------------------------
INSERT INTO `cart` VALUES (106, 35, 14, '你好', 1, 525.70, NULL, 1502.00, 0.35, '2024-04-03 10:39:07', '2024-04-03 10:39:07');
INSERT INTO `cart` VALUES (119, 1, 1, '衣服222', 2, 1173.49, '1710410388438.jpg', 1235.25, 0.95, '2024-06-12 15:00:22', '2024-05-06 21:21:51');
INSERT INTO `cart` VALUES (120, 1, 39, '系欸子', 1, 100.00, NULL, 100.00, 1, '2024-05-06 21:22:01', '2024-05-06 21:22:01');
INSERT INTO `cart` VALUES (125, 1, 91, '书包', 1, 23.09, NULL, 230.87, 0.1, '2024-05-06 22:44:12', '2024-05-06 22:44:12');
INSERT INTO `cart` VALUES (127, 1, 110, '青霉素', 1, 1617.34, NULL, 1777.30, 0.91, '2024-05-06 22:44:20', '2024-05-06 22:44:20');
INSERT INTO `cart` VALUES (128, 1, 54, '拖把', 1, 340.16, NULL, 1308.29, 0.26, '2024-06-11 15:32:34', '2024-06-11 15:32:34');
INSERT INTO `cart` VALUES (129, 6, 1, '衣服222', 4, 4693.96, NULL, 1235.25, 0.95, '2024-06-12 14:57:59', '2024-06-12 14:57:59');

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名称',
  `category_status` int NOT NULL COMMENT '分类状态 1启用 0禁用',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '服装', 1, '2024-04-17 15:38:17', '2024-03-26 10:52:17');
INSERT INTO `category` VALUES (2, '数码', 1, '2024-05-19 16:05:36', '2024-03-26 11:06:09');
INSERT INTO `category` VALUES (3, '应用程式及游戏', 1, '2012-11-03 01:29:42', '2011-01-02 14:27:22');
INSERT INTO `category` VALUES (4, '收藏品及美术用品', 1, '2007-10-28 18:12:36', '2010-06-02 12:09:11');
INSERT INTO `category` VALUES (5, '手工制作', 1, '2005-08-29 07:45:33', '2020-02-25 18:26:42');
INSERT INTO `category` VALUES (6, '其他', 1, '2024-04-17 15:38:11', '2008-02-15 10:28:56');
INSERT INTO `category` VALUES (7, '工具与家居装饰', 1, '2015-03-21 12:10:02', '2010-10-25 15:30:55');
INSERT INTO `category` VALUES (8, '美容与个人护理', 1, '2017-03-16 22:53:06', '2012-05-29 01:13:36');
INSERT INTO `category` VALUES (9, '乐器用品', 1, '2009-04-15 15:07:08', '2022-03-03 02:57:30');
INSERT INTO `category` VALUES (10, '运动与户外用品', 1, '2013-01-27 12:25:04', '2020-02-16 14:30:26');
INSERT INTO `category` VALUES (11, '1234', 0, '2024-05-19 16:06:01', '2024-05-19 16:05:57');

-- ----------------------------
-- Table structure for comment_action
-- ----------------------------
DROP TABLE IF EXISTS `comment_action`;
CREATE TABLE `comment_action`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint NOT NULL COMMENT '行动人id',
  `comment_id` bigint NOT NULL COMMENT '评论id',
  `action` int NOT NULL DEFAULT 3 COMMENT '评论行为',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `goods_id` bigint NOT NULL COMMENT '商品id',
  `store_id` bigint NOT NULL COMMENT '商店id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '评论行为表（点赞点踩）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment_action
-- ----------------------------
INSERT INTO `comment_action` VALUES (1, 1, 10, 3, '2024-04-19 14:32:38', '2024-04-19 15:38:27', 1, 1);
INSERT INTO `comment_action` VALUES (2, 1, 11, 1, '2024-04-19 15:40:09', '2024-04-20 14:01:47', 1, 1);
INSERT INTO `comment_action` VALUES (3, 6, 12, 3, '2024-04-20 14:02:13', '2024-04-20 14:02:13', 1, 1);
INSERT INTO `comment_action` VALUES (4, 1, 13, 3, '2024-04-20 14:08:21', '2024-04-20 14:08:21', 1, 1);
INSERT INTO `comment_action` VALUES (5, 1, 12, 3, '2024-04-20 16:13:18', '2024-04-20 16:14:54', 1, 1);
INSERT INTO `comment_action` VALUES (6, 1, 14, 3, '2024-04-21 21:30:36', '2024-04-21 21:30:36', 1, 1);
INSERT INTO `comment_action` VALUES (7, 6, 15, 3, '2024-04-22 10:14:28', '2024-04-22 10:14:28', 74, 42);
INSERT INTO `comment_action` VALUES (8, 6, 16, 3, '2024-04-22 10:30:32', '2024-04-22 10:30:32', 74, 42);
INSERT INTO `comment_action` VALUES (9, 6, 17, 3, '2024-04-22 10:35:42', '2024-04-22 10:35:42', 74, 42);
INSERT INTO `comment_action` VALUES (10, 1, 18, 3, '2024-04-22 10:35:49', '2024-04-22 10:35:49', 1, 1);
INSERT INTO `comment_action` VALUES (11, 6, 19, 3, '2024-04-22 10:59:02', '2024-04-22 10:59:02', 14, 5);
INSERT INTO `comment_action` VALUES (12, 6, 20, 3, '2024-05-02 11:01:00', '2024-05-02 11:01:00', 1, 1);
INSERT INTO `comment_action` VALUES (13, 131, 21, 3, '2024-05-02 14:42:00', '2024-05-02 14:42:00', 1, 1);
INSERT INTO `comment_action` VALUES (14, 1, 21, 3, '2024-05-03 20:04:57', '2024-05-03 20:05:18', 1, 1);
INSERT INTO `comment_action` VALUES (15, 1, 22, 3, '2024-06-12 14:19:57', '2024-06-12 14:19:57', 307, 47);
INSERT INTO `comment_action` VALUES (16, 1, 23, 2, '2024-06-12 15:10:01', '2024-06-12 16:44:24', 1, 1);
INSERT INTO `comment_action` VALUES (17, 1, 24, 1, '2024-06-12 15:10:17', '2024-06-12 20:19:12', 1, 1);
INSERT INTO `comment_action` VALUES (18, 6, 25, 2, '2024-06-12 15:10:42', '2024-06-12 21:21:12', 1, 1);
INSERT INTO `comment_action` VALUES (19, 6, 24, 1, '2024-06-12 15:10:48', '2024-06-12 16:48:46', 1, 1);
INSERT INTO `comment_action` VALUES (20, 1, 25, 3, '2024-06-12 16:43:57', '2024-06-12 21:35:52', 1, 1);

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `user_id` bigint NOT NULL COMMENT '发表人id',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户头像',
  `goods_id` bigint NOT NULL COMMENT '商品id',
  `store_id` bigint NOT NULL COMMENT '商店id',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论内容',
  `star` decimal(3, 1) NOT NULL COMMENT '评分',
  `like_count` int NOT NULL DEFAULT 0 COMMENT '点赞数',
  `reply_count` int NOT NULL DEFAULT 0 COMMENT '回复数',
  `parent_comment_id` bigint NULL DEFAULT NULL COMMENT '父评论id（若为顶级评论，则为NULL）',
  `comment_status` int NOT NULL COMMENT '评论状态 1 隐藏 0 显示',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `report_count` int NOT NULL DEFAULT 0,
  `dislike_count` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT `comments_chk_1` CHECK (`star` between 0.5 and 5)
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comments
-- ----------------------------
INSERT INTO `comments` VALUES (10, 1, 'admin', '1715051190485.jpg', 1, 1, '很好的商品', 4.0, 0, 0, NULL, 0, '2024-04-19 14:32:38', '2024-04-22 21:25:16', 1, 0);
INSERT INTO `comments` VALUES (11, 1, 'admin', '1715051190485.jpg', 1, 1, '哈哈\n', 3.0, 1, 0, 10, 0, '2024-04-19 15:40:09', '2024-04-22 20:52:48', 0, 0);
INSERT INTO `comments` VALUES (13, 1, 'admin', '1715051190485.jpg', 1, 1, '嘻嘻', 5.0, 0, 0, NULL, 0, '2024-04-20 14:08:21', '2024-04-22 21:20:14', 0, 0);
INSERT INTO `comments` VALUES (14, 1, 'admin', '1715051190485.jpg', 1, 1, '123', 5.0, 0, 0, 10, 0, '2024-04-21 21:30:36', '2024-04-22 20:52:51', 0, 0);
INSERT INTO `comments` VALUES (17, 6, '123456', 'default.png', 74, 42, '哈哈', 5.0, 0, 0, NULL, 0, '2024-04-22 10:35:42', '2024-04-22 10:35:42', 0, 0);
INSERT INTO `comments` VALUES (18, 1, 'admin', '1715051190485.jpg', 1, 1, '123', 5.0, 0, 0, NULL, 0, '2024-04-22 10:35:49', '2024-04-22 20:52:57', 0, 0);
INSERT INTO `comments` VALUES (20, 6, '123456', 'default.png', 1, 1, '123', 5.0, 0, 0, NULL, 0, '2024-05-02 11:01:00', '2024-05-02 12:22:39', 0, 0);
INSERT INTO `comments` VALUES (21, 131, 'qwert', 'default.png', 1, 1, '6', 2.0, 0, 0, NULL, 0, '2024-05-02 14:42:00', '2024-05-03 20:05:18', 0, 0);
INSERT INTO `comments` VALUES (23, 1, 'admin', '1715051190485.jpg', 1, 1, 'fawdas', 5.0, 0, 0, NULL, 0, '2024-06-12 15:10:01', '2024-06-12 16:44:24', 0, 1);
INSERT INTO `comments` VALUES (24, 1, 'admin', '1715051190485.jpg', 1, 1, 'fawe', 5.0, 1, 0, 23, 0, '2024-06-12 15:10:17', '2024-06-12 20:19:12', 1, 0);
INSERT INTO `comments` VALUES (25, 6, '123456', 'default.png', 1, 1, 'fsaew', 5.0, 0, 0, 23, 0, '2024-06-12 15:10:42', '2024-06-12 21:35:52', 0, 1);

-- ----------------------------
-- Table structure for favorite
-- ----------------------------
DROP TABLE IF EXISTS `favorite`;
CREATE TABLE `favorite`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '收藏夹id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `goods_id` bigint NULL DEFAULT NULL COMMENT '商品id',
  `store_id` bigint NULL DEFAULT NULL COMMENT '商店id',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 191 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户的收藏夹' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of favorite
-- ----------------------------
INSERT INTO `favorite` VALUES (190, 1, 714, 31, '2024-04-13 17:35:06', '2024-04-13 17:35:06');

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `store_id` bigint NOT NULL COMMENT '店家id',
  `category_id` bigint NULL DEFAULT NULL COMMENT '分类id',
  `goods_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名',
  `price` double NOT NULL COMMENT '价格',
  `total` bigint NOT NULL COMMENT '商品总量',
  `discount` double NOT NULL COMMENT '商品折扣',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品描述\n',
  `cover_pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品封面',
  `status` int NOT NULL COMMENT '商品状态 0下架 1上架',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1045 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (1, 1, 1, '衣服222', 1235.25, 49, 0.95, '衣服', '1710410388438.jpg', 1, '2024-03-11 16:13:15', '2024-07-03 21:18:49');
INSERT INTO `goods` VALUES (4, 1, 1, '衣服222', 100, 92, 1, '衣服', '默认商品图.png', 1, '2024-03-11 16:13:15', '2024-04-15 16:52:16');
INSERT INTO `goods` VALUES (11, 5, 1, '裤子', 100, 93, 1, '衣服', '默认商品图.png', 1, '2024-03-11 16:13:15', '2024-03-22 17:05:11');
INSERT INTO `goods` VALUES (12, 5, 1, '裤子', 100, 100, 1, '衣服', '默认商品图.png', 1, '2024-03-12 14:27:10', '2024-03-15 10:54:34');
INSERT INTO `goods` VALUES (13, 5, 1, '草鞋', 1500, 140, 1, '6', '默认商品图.png', 1, '2024-03-12 21:19:33', '2024-03-29 16:09:05');
INSERT INTO `goods` VALUES (14, 5, 1, '你好', 1502, 507, 0.35, '123', '默认商品图.png', 1, '2024-03-12 21:25:28', '2024-04-20 19:27:28');
INSERT INTO `goods` VALUES (15, 5, 1, '123', 213, 0, 0.2, '12', '默认商品图.png', 1, '2024-03-12 21:26:42', '2024-03-23 10:43:29');
INSERT INTO `goods` VALUES (24, 5, 1, '裤子', 200, 500, 1, '123', '默认商品图.png', 1, '2024-03-13 11:02:35', '2024-03-15 10:54:41');
INSERT INTO `goods` VALUES (25, 5, 1, '裤子', 200, 497, 1, '123', '默认商品图.png', 1, '2024-03-13 11:03:01', '2024-03-27 15:44:54');
INSERT INTO `goods` VALUES (38, 5, 1, '123', 100, 106, 0.95, '123', '1710313297180.jpg', 1, '2024-03-13 15:01:37', '2024-03-29 08:57:47');
INSERT INTO `goods` VALUES (39, 1, 1, '系欸子', 100, 327, 1, '123', '1710386594702.jpg', 1, '2024-03-14 11:23:14', '2024-04-07 15:53:41');
INSERT INTO `goods` VALUES (40, 1, 2, '短袖', 520.5, 488, 1, 'ds', '1711437677985.jpg', 1, '2024-03-26 15:21:18', '2024-04-01 15:42:39');
INSERT INTO `goods` VALUES (41, 66, 2, '鞋子', 1327.6, 8721, 0.19, 'Sometimes you win, sometimes you learn. Instead of wondering when your next vacation                ', '默认商品图.png', 0, '2015-05-31 18:12:31', '2011-05-26 20:42:05');
INSERT INTO `goods` VALUES (44, 64, 7, '垃圾桶', 1962.1, 6910, 0, 'To successfully establish a new connection to local/remote server - no matter via                   ', '默认商品图.png', 1, '2010-05-10 10:05:32', '2004-11-24 20:07:06');
INSERT INTO `goods` VALUES (45, 46, 6, '键盘', 69.27, 6516, 0.16, 'Optimism is the one quality more associated with success and happiness than any other.              ', '默认商品图.png', 1, '2010-11-29 15:15:23', '2024-04-03 10:17:09');
INSERT INTO `goods` VALUES (46, 49, 8, '保鲜膜', 551.32, 999, 0.19, 'Export Wizard allows you to export data from tables, collections, views, or query                   ', '默认商品图.png', 0, '2010-06-05 09:26:17', '2012-11-03 16:59:56');
INSERT INTO `goods` VALUES (47, 32, 7, '电脑', 893.33, 234, 0.03, 'After comparing data, the window shows the number of records that will be inserted,                 ', '默认商品图.png', 0, '2018-05-28 00:21:23', '2010-05-24 11:01:31');
INSERT INTO `goods` VALUES (48, 33, 9, '橘子', 573.38, 5676, 0.09, 'Always keep your eyes open. Keep watching. Because whatever you see can inspire you.                ', '默认商品图.png', 0, '2006-10-02 23:32:41', '2015-05-16 02:05:51');
INSERT INTO `goods` VALUES (49, 55, 7, '保鲜膜', 1145.28, 5884, 0.77, 'The repository database can be an existing MySQL, MariaDB, PostgreSQL, SQL Server,                  ', '默认商品图.png', 1, '2012-07-12 02:18:35', '2017-03-28 11:08:24');
INSERT INTO `goods` VALUES (50, 66, 9, '锅', 1553.88, 9422, 0.1, 'If you wait, all that happens is you get older. A comfort zone is a beautiful place,                ', '默认商品图.png', 1, '2023-11-03 03:45:48', '2020-09-28 15:52:31');
INSERT INTO `goods` VALUES (52, 45, 2, '电脑', 1145.42, 8567, 0.4, 'What you get by achieving your goals is not as important as what you become by achieving your goals.', '默认商品图.png', 1, '2023-08-12 12:32:09', '2013-09-09 17:33:44');
INSERT INTO `goods` VALUES (54, 43, 5, '拖把', 1308.29, 4897, 0.26, 'To connect to a database or schema, simply double-click it in the pane.                             ', '默认商品图.png', 1, '2008-06-18 04:38:43', '2024-06-11 15:32:35');
INSERT INTO `goods` VALUES (55, 60, 3, '袜子', 1514.08, 1701, 0.85, 'Sometimes you win, sometimes you learn. Navicat is a multi-connections Database Administration      ', '默认商品图.png', 0, '2009-04-07 16:41:15', '2003-10-15 12:39:42');
INSERT INTO `goods` VALUES (56, 62, 2, '雨鞋', 1159.7, 3070, 0.84, 'Anyone who has ever made anything of importance was disciplined. If opportunity doesn’t           ', '默认商品图.png', 0, '2004-08-25 14:23:01', '2013-12-24 20:17:09');
INSERT INTO `goods` VALUES (57, 5, 9, '书包', 1381.41, 1719, 0.58, 'The Navigation pane employs tree structure which allows you to take action upon the                 ', '默认商品图.png', 1, '2008-03-19 15:51:37', '2004-08-06 02:32:06');
INSERT INTO `goods` VALUES (59, 52, 4, '手机', 224.86, 7226, 0.74, 'The Main Window consists of several toolbars and panes for you to work on connections,              ', '默认商品图.png', 0, '2015-06-24 20:44:54', '2017-10-11 14:39:09');
INSERT INTO `goods` VALUES (62, 33, 1, '橘子', 1728.04, 6483, 0.48, 'Flexible settings enable you to set up a custom key for comparison and synchronization.             ', '默认商品图.png', 0, '2004-04-30 22:57:57', '2019-06-01 03:31:47');
INSERT INTO `goods` VALUES (63, 51, 9, '999感冒灵', 1562.74, 9033, 0.34, 'Navicat Monitor can be installed on any local computer or virtual machine and does                  ', '默认商品图.png', 0, '2010-04-30 05:35:04', '2000-11-26 01:44:36');
INSERT INTO `goods` VALUES (65, 35, 9, '陶瓷碗', 120.17, 8363, 0.21, 'Difficult circumstances serve as a textbook of life for people. If you wait, all                    ', '默认商品图.png', 1, '2001-07-01 12:39:01', '2021-09-06 21:58:29');
INSERT INTO `goods` VALUES (66, 32, 9, '鞋子', 688.41, 1526, 0.95, 'Navicat 15 has added support for the system-wide dark mode. SQL Editor allows you                   ', '默认商品图.png', 0, '2015-03-11 00:42:07', '2002-06-13 16:10:36');
INSERT INTO `goods` VALUES (67, 36, 3, '垃圾袋', 1374.12, 3113, 0.45, 'Flexible settings enable you to set up a custom key for comparison and synchronization.             ', '默认商品图.png', 1, '2017-07-23 02:31:12', '2010-11-11 17:55:57');
INSERT INTO `goods` VALUES (69, 66, 10, '裙子', 63.92, 6670, 0.59, 'Typically, it is employed as an encrypted version of Telnet. Secure SHell (SSH) is                  ', '默认商品图.png', 0, '2011-09-14 11:35:18', '2000-10-18 18:38:50');
INSERT INTO `goods` VALUES (71, 56, 5, '数据线', 617, 2269, 0.9, 'Navicat authorizes you to make connection to remote servers running on different                    ', '默认商品图.png', 0, '2011-02-22 03:44:58', '2021-08-09 21:16:30');
INSERT INTO `goods` VALUES (74, 42, 7, '撮箕', 1272.26, 3397, 0.97, 'HTTP Tunneling is a method for connecting to a server that uses the same protocol                   ', '默认商品图.png', 1, '2021-03-07 23:30:30', '2023-05-02 10:48:59');
INSERT INTO `goods` VALUES (75, 31, 5, '梨', 395.32, 100, 0.23, 'Anyone who has ever made anything of importance was disciplined. Secure Sockets Layer(SSL)          ', '默认商品图.png', 1, '2005-01-01 07:26:12', '2011-09-18 12:30:34');
INSERT INTO `goods` VALUES (76, 55, 6, '裙子', 451.19, 8249, 0.89, 'SSH serves to prevent such vulnerabilities and allows you to access a remote server\'s               ', '默认商品图.png', 0, '2002-10-27 12:34:50', '2011-07-06 14:08:40');
INSERT INTO `goods` VALUES (78, 42, 3, '铁盆', 950.75, 8635, 0.51, 'In the Objects tab, you can use the List List, Detail Detail and ER Diagram ER Diagram              ', '默认商品图.png', 1, '2007-08-28 05:25:24', '2022-08-20 10:26:39');
INSERT INTO `goods` VALUES (81, 42, 2, '手机', 685.17, 768, 0.32, 'To get a secure connection, the first thing you need to do is to install OpenSSL                    ', '默认商品图.png', 0, '2003-06-09 10:04:39', '2004-02-03 15:19:17');
INSERT INTO `goods` VALUES (82, 59, 6, '垃圾桶', 94.62, 8376, 0.49, 'Typically, it is employed as an encrypted version of Telnet. The Synchronize to Database            ', '默认商品图.png', 1, '2017-05-24 18:49:55', '2011-02-25 15:50:02');
INSERT INTO `goods` VALUES (83, 41, 8, '裤子', 1600.41, 6548, 0.48, 'To clear or reload various internal caches, flush tables, or acquire locks, control-click           ', '默认商品图.png', 1, '2022-06-11 19:27:23', '2024-04-07 19:54:52');
INSERT INTO `goods` VALUES (84, 64, 7, '数据线', 130.46, 1379, 0.26, 'The On Startup feature allows you to control what tabs appear when you launch Navicat.              ', '默认商品图.png', 1, '2000-12-10 17:35:28', '2023-02-26 09:18:02');
INSERT INTO `goods` VALUES (85, 36, 6, '拖把', 1923.22, 8533, 0.45, 'Genius is an infinite capacity for taking pains. Sometimes you win, sometimes you learn.            ', '默认商品图.png', 0, '2002-07-14 21:54:19', '2002-12-21 07:30:59');
INSERT INTO `goods` VALUES (86, 47, 5, '草莓', 1153.86, 9091, 0.14, 'Difficult circumstances serve as a textbook of life for people. The On Startup feature              ', '默认商品图.png', 1, '2000-02-22 02:39:22', '2020-11-28 13:05:00');
INSERT INTO `goods` VALUES (87, 51, 7, '橘子', 524.07, 2319, 0.91, 'Flexible settings enable you to set up a custom key for comparison and synchronization.             ', '默认商品图.png', 1, '2014-06-25 08:19:10', '2014-04-25 23:18:22');
INSERT INTO `goods` VALUES (88, 66, 3, '袜子', 211.55, 7918, 0.63, 'With its well-designed Graphical User Interface(GUI), Navicat lets you quickly and                  ', '默认商品图.png', 0, '2005-10-20 06:09:56', '2004-11-22 09:30:21');
INSERT INTO `goods` VALUES (89, 54, 3, '摄像机', 1402.57, 9592, 0.67, 'All the Navicat Cloud objects are located under different projects. You can share                   ', '默认商品图.png', 0, '2006-04-28 12:13:42', '2023-12-10 12:03:36');
INSERT INTO `goods` VALUES (91, 55, 7, '书包', 230.87, 126, 0.1, 'Secure Sockets Layer(SSL) is a protocol for transmitting private documents via the Internet.        ', '默认商品图.png', 1, '2009-10-29 01:17:56', '2023-08-25 20:36:39');
INSERT INTO `goods` VALUES (94, 53, 7, '毛巾', 469.16, 7565, 0.47, 'Flexible settings enable you to set up a custom key for comparison and synchronization.             ', '默认商品图.png', 0, '2017-08-31 21:48:47', '2007-01-31 12:51:52');
INSERT INTO `goods` VALUES (95, 52, 5, '手提包', 1659.33, 6277, 0.86, 'To start working with your server in Navicat, you should first establish a connection               ', '默认商品图.png', 1, '2007-07-31 05:15:36', '2024-04-07 20:12:02');
INSERT INTO `goods` VALUES (96, 44, 4, '背包', 1126.16, 6605, 0.59, 'Optimism is the one quality more associated with success and happiness than any other.              ', '默认商品图.png', 1, '2012-01-03 13:29:45', '2011-06-22 22:04:04');
INSERT INTO `goods` VALUES (97, 60, 2, '钱包', 802.78, 9442, 0.7, 'Champions keep playing until they get it right. Navicat provides powerful tools for                 ', '默认商品图.png', 0, '2004-03-23 03:43:30', '2019-10-31 07:10:48');
INSERT INTO `goods` VALUES (100, 38, 8, '锅', 1280.06, 4314, 0.32, 'Creativity is intelligence having fun. Export Wizard allows you to export data from                 ', '默认商品图.png', 1, '2010-08-30 14:22:54', '2023-03-25 18:44:25');
INSERT INTO `goods` VALUES (101, 50, 5, '锅', 1791.19, 7574, 0.61, 'After comparing data, the window shows the number of records that will be inserted,                 ', '默认商品图.png', 1, '2008-03-22 06:25:02', '2002-01-23 16:09:46');
INSERT INTO `goods` VALUES (102, 18, 7, '西瓜', 1125.79, 3599, 0.56, 'Navicat allows you to transfer data from one database and/or schema to another with                 ', '默认商品图.png', 1, '2007-11-10 00:46:37', '2020-03-02 16:22:15');
INSERT INTO `goods` VALUES (103, 51, 4, '草莓', 1521.35, 1384, 0.21, 'SSH serves to prevent such vulnerabilities and allows you to access a remote server\'s               ', '默认商品图.png', 1, '2018-02-22 15:36:35', '2014-12-15 11:02:09');
INSERT INTO `goods` VALUES (108, 38, 4, '音响', 1019.81, 9075, 0.03, 'If you wait, all that happens is you get older. To connect to a database or schema,                 ', '默认商品图.png', 1, '2007-08-17 14:11:51', '2012-10-21 00:04:17');
INSERT INTO `goods` VALUES (109, 41, 2, '青霉素', 480.37, 7643, 0.26, 'The Navigation pane employs tree structure which allows you to take action upon the                 ', '默认商品图.png', 0, '2001-08-20 14:36:32', '2020-03-17 06:22:11');
INSERT INTO `goods` VALUES (110, 65, 4, '青霉素', 1777.3, 1399, 0.91, 'In the middle of winter I at last discovered that there was in me an invincible summer.             ', '默认商品图.png', 1, '2018-11-28 18:05:51', '2005-04-19 21:47:28');
INSERT INTO `goods` VALUES (111, 62, 9, '垃圾桶', 1848.17, 8701, 0.5, 'In a Telnet session, all communications, including username and password, are transmitted           ', '默认商品图.png', 1, '2017-09-03 06:46:35', '2003-06-14 23:25:07');
INSERT INTO `goods` VALUES (112, 60, 10, '键盘', 480.84, 2035, 0.42, 'You cannot save people, you can just love them. Navicat 15 has added support for                    ', '默认商品图.png', 0, '2011-04-11 15:01:32', '2006-08-12 00:15:05');
INSERT INTO `goods` VALUES (113, 59, 2, '雨鞋', 1798.03, 2647, 0.64, 'Difficult circumstances serve as a textbook of life for people. The past has no power               ', '默认商品图.png', 1, '2003-02-25 22:45:50', '2011-03-11 17:07:27');
INSERT INTO `goods` VALUES (116, 1, 1, '铁盆', 117.72, 636, 0.1, 'HTTP Tunneling is a method for connecting to a server that uses the same protocol                   ', '默认商品图.png', 0, '2020-09-08 14:11:01', '2007-08-02 03:11:28');
INSERT INTO `goods` VALUES (117, 5, 7, '扫把', 445.68, 912, 0.87, 'To successfully establish a new connection to local/remote server - no matter via                   ', '默认商品图.png', 1, '2003-08-17 19:02:38', '2020-08-21 02:11:31');
INSERT INTO `goods` VALUES (118, 44, 1, '垃圾袋', 901.9, 1051, 0.46, 'Navicat Data Modeler enables you to build high-quality conceptual, logical and physical             ', '默认商品图.png', 0, '2019-07-05 06:05:50', '2000-10-12 18:39:08');
INSERT INTO `goods` VALUES (121, 65, 7, '梨', 1129.64, 9870, 0.35, 'Export Wizard allows you to export data from tables, collections, views, or query                   ', '默认商品图.png', 0, '2005-06-29 03:16:23', '2018-06-19 11:16:34');
INSERT INTO `goods` VALUES (122, 58, 6, '书包', 1964.05, 1820, 0.43, 'Export Wizard allows you to export data from tables, collections, views, or query                   ', '默认商品图.png', 0, '2014-04-15 03:13:57', '2001-02-25 02:25:23');
INSERT INTO `goods` VALUES (124, 38, 5, '裤子', 262.06, 493, 0.45, 'Import Wizard allows you to import data to tables/collections from CSV, TXT, XML, DBF and more.     ', '默认商品图.png', 0, '2007-02-28 08:37:08', '2006-09-19 23:57:23');
INSERT INTO `goods` VALUES (125, 51, 9, '拖把', 1361.64, 9017, 0.32, 'In the middle of winter I at last discovered that there was in me an invincible summer.             ', '默认商品图.png', 1, '2021-07-26 08:03:55', '2017-04-20 22:45:59');
INSERT INTO `goods` VALUES (129, 33, 10, '电脑', 1625.62, 1883, 0.67, 'To start working with your server in Navicat, you should first establish a connection               ', '默认商品图.png', 1, '2019-06-18 23:08:48', '2024-06-12 14:58:55');
INSERT INTO `goods` VALUES (133, 37, 1, '鞋子', 1012.51, 477, 0, 'In the middle of winter I at last discovered that there was in me an invincible summer.             ', '默认商品图.png', 1, '2023-03-09 05:08:12', '2005-11-04 08:05:36');
INSERT INTO `goods` VALUES (136, 54, 5, '扫把', 1056.55, 8021, 0.41, 'To connect to a database or schema, simply double-click it in the pane.                             ', '默认商品图.png', 1, '2019-05-30 03:55:03', '2017-02-24 23:27:21');
INSERT INTO `goods` VALUES (138, 52, 10, '撮箕', 327.35, 8306, 0.4, 'Import Wizard allows you to import data to tables/collections from CSV, TXT, XML, DBF and more.     ', '默认商品图.png', 0, '2007-10-07 16:28:16', '2005-01-05 20:07:17');
INSERT INTO `goods` VALUES (139, 51, 8, '摄像机', 1369.26, 8558, 0.45, 'To get a secure connection, the first thing you need to do is to install OpenSSL                    ', '默认商品图.png', 0, '2004-10-29 18:27:55', '2023-07-04 08:12:43');
INSERT INTO `goods` VALUES (142, 1, 8, '保鲜膜', 588.91, 56, 0.68, 'I may not have gone where I intended to go, but I think I have ended up where I needed to be.       ', '默认商品图.png', 0, '2004-06-02 02:33:38', '2021-01-17 03:12:29');
INSERT INTO `goods` VALUES (148, 61, 7, '西瓜', 1938.15, 3797, 0.86, 'Navicat Monitor can be installed on any local computer or virtual machine and does                  ', '默认商品图.png', 0, '2014-03-31 16:57:10', '2014-03-02 00:13:08');
INSERT INTO `goods` VALUES (152, 16, 6, '阿莫西尼', 912.57, 5314, 0.3, 'Anyone who has ever made anything of importance was disciplined. Navicat Monitor                    ', '默认商品图.png', 1, '2006-08-16 20:44:10', '2013-08-17 17:07:17');
INSERT INTO `goods` VALUES (155, 50, 9, '毛巾', 1953.21, 6365, 0.79, 'Sometimes you win, sometimes you learn. Genius is an infinite capacity for taking pains.            ', '默认商品图.png', 1, '2009-08-29 15:13:21', '2000-01-11 11:40:26');
INSERT INTO `goods` VALUES (156, 31, 2, '雨衣', 1298.52, 1110, 0.28, 'Creativity is intelligence having fun. Monitored servers include MySQL, MariaDB and                 ', '默认商品图.png', 0, '2002-06-11 02:58:35', '2008-11-08 13:36:28');
INSERT INTO `goods` VALUES (157, 16, 8, '999感冒灵', 739.55, 8201, 0.32, 'If your Internet Service Provider (ISP) does not provide direct access to its server,               ', '默认商品图.png', 1, '2014-07-13 20:11:44', '2023-09-17 12:43:00');
INSERT INTO `goods` VALUES (162, 43, 3, '梨', 1192.38, 2674, 0.63, 'The On Startup feature allows you to control what tabs appear when you launch Navicat.              ', '默认商品图.png', 1, '2006-01-05 07:09:05', '2001-10-15 12:02:35');
INSERT INTO `goods` VALUES (166, 35, 3, '衣服', 263.97, 5150, 0.12, 'To successfully establish a new connection to local/remote server - no matter via                   ', '默认商品图.png', 1, '2007-12-02 05:28:20', '2020-11-29 08:19:57');
INSERT INTO `goods` VALUES (167, 1, 6, '衣服', 770.87, 8474, 0.6, 'All the Navicat Cloud objects are located under different projects. You can share                   ', '默认商品图.png', 1, '2016-06-17 07:14:17', '2011-08-25 04:53:01');
INSERT INTO `goods` VALUES (172, 32, 7, '青霉素', 905.04, 3650, 0.46, 'If the Show objects under schema in navigation pane option is checked at the Preferences            ', '默认商品图.png', 0, '2000-05-22 03:23:06', '2020-12-12 01:30:26');
INSERT INTO `goods` VALUES (174, 40, 2, '橙子', 1740.31, 6579, 0.52, 'Navicat Data Modeler is a powerful and cost-effective database design tool which                    ', '默认商品图.png', 0, '2001-06-19 17:20:07', '2009-04-28 17:36:16');
INSERT INTO `goods` VALUES (175, 62, 8, '保鲜膜', 1722.15, 9532, 0.89, 'The Synchronize to Database function will give you a full picture of all database differences.      ', '默认商品图.png', 1, '2006-10-05 18:01:09', '2023-08-16 23:22:18');
INSERT INTO `goods` VALUES (180, 62, 1, '书包', 1477.98, 1992, 0.76, 'To get a secure connection, the first thing you need to do is to install OpenSSL                    ', '默认商品图.png', 0, '2004-03-25 04:55:51', '2014-02-07 09:29:09');
INSERT INTO `goods` VALUES (181, 35, 3, '草莓', 610.93, 95, 0.75, 'It provides strong authentication and secure encrypted communications between two                   ', '默认商品图.png', 0, '2011-07-12 23:12:56', '2023-03-27 17:16:56');
INSERT INTO `goods` VALUES (182, 32, 5, '梨', 1784.84, 8604, 0.97, 'How we spend our days is, of course, how we spend our lives. A man’s best friends                 ', '默认商品图.png', 1, '2007-01-19 09:29:28', '2021-07-24 11:59:48');
INSERT INTO `goods` VALUES (186, 45, 1, '铁盆', 1887.91, 4607, 0.25, 'With its well-designed Graphical User Interface(GUI), Navicat lets you quickly and                  ', '默认商品图.png', 1, '2009-03-04 04:14:35', '2016-11-08 02:42:21');
INSERT INTO `goods` VALUES (193, 33, 3, '阿莫西尼', 786.59, 220, 0.94, 'Success consists of going from failure to failure without loss of enthusiasm.                       ', '默认商品图.png', 1, '2005-12-11 04:15:02', '2016-10-27 13:43:29');
INSERT INTO `goods` VALUES (194, 45, 4, '铁盆', 808.96, 2901, 0.58, 'What you get by achieving your goals is not as important as what you become by achieving your goals.', '默认商品图.png', 0, '2019-10-31 04:33:15', '2015-09-12 19:23:57');
INSERT INTO `goods` VALUES (195, 56, 7, '阿莫西尼', 128.93, 5230, 0.44, 'Typically, it is employed as an encrypted version of Telnet. Export Wizard allows                   ', '默认商品图.png', 0, '2003-02-18 16:09:05', '2006-01-25 22:57:58');
INSERT INTO `goods` VALUES (196, 66, 1, '充电宝', 318.76, 161, 0.25, 'Flexible settings enable you to set up a custom key for comparison and synchronization.             ', '默认商品图.png', 1, '2012-06-30 14:31:58', '2013-11-09 09:22:26');
INSERT INTO `goods` VALUES (200, 66, 7, '撮箕', 889.59, 8263, 0.89, 'It can also manage cloud databases such as Amazon Redshift, Amazon RDS, Alibaba Cloud.              ', '默认商品图.png', 1, '2015-05-19 08:38:06', '2019-07-21 09:35:02');
INSERT INTO `goods` VALUES (201, 63, 8, '钱包', 690.21, 7028, 0.17, 'The Information Pane shows the detailed object information, project activities, the                 ', '默认商品图.png', 0, '2022-05-05 18:37:30', '2020-01-22 15:56:00');
INSERT INTO `goods` VALUES (202, 40, 7, '西瓜', 1571.84, 2486, 0.49, 'A query is used to extract data from the database in a readable format according                    ', '默认商品图.png', 0, '2005-03-24 08:55:09', '2011-05-14 05:02:15');
INSERT INTO `goods` VALUES (204, 16, 2, '衣服', 1731.24, 1794, 0.17, 'Navicat 15 has added support for the system-wide dark mode. What you get by achieving               ', '默认商品图.png', 0, '2018-06-27 13:44:11', '2016-12-21 15:59:42');
INSERT INTO `goods` VALUES (208, 48, 3, '手提包', 301.93, 7823, 0.5, 'Navicat Data Modeler enables you to build high-quality conceptual, logical and physical             ', '默认商品图.png', 1, '2022-08-16 14:46:16', '2013-02-11 06:59:16');
INSERT INTO `goods` VALUES (210, 48, 7, '苹果', 1498.77, 3484, 0.09, 'If the Show objects under schema in navigation pane option is checked at the Preferences            ', '默认商品图.png', 0, '2001-01-06 21:15:43', '2022-07-23 18:26:55');
INSERT INTO `goods` VALUES (211, 32, 6, '毛巾', 1560.96, 402, 0.64, 'Secure Sockets Layer(SSL) is a protocol for transmitting private documents via the Internet.        ', '默认商品图.png', 1, '2004-12-29 13:59:53', '2011-03-08 06:25:49');
INSERT INTO `goods` VALUES (218, 33, 6, '数据线', 1734.14, 8686, 0.51, 'If opportunity doesn’t knock, build a door. Champions keep playing until they get it right.       ', '默认商品图.png', 0, '2011-02-10 08:36:42', '2017-03-20 04:22:22');
INSERT INTO `goods` VALUES (219, 31, 9, '键盘', 1919.55, 4956, 0.67, 'Navicat Cloud provides a cloud service for synchronizing connections, queries, model                ', '默认商品图.png', 0, '2017-05-19 17:05:08', '2011-12-28 08:53:53');
INSERT INTO `goods` VALUES (220, 38, 4, '撮箕', 578.4, 8340, 0.86, 'Typically, it is employed as an encrypted version of Telnet. After logged in the                    ', '默认商品图.png', 0, '2000-11-16 13:15:25', '2004-03-09 15:14:25');
INSERT INTO `goods` VALUES (221, 48, 8, '拖把', 490.05, 3259, 0.1, 'Navicat Data Modeler enables you to build high-quality conceptual, logical and physical             ', '默认商品图.png', 0, '2002-12-08 06:34:02', '2001-07-09 10:09:55');
INSERT INTO `goods` VALUES (224, 47, 4, '铁盆', 1033.37, 9469, 0.61, 'Typically, it is employed as an encrypted version of Telnet. There is no way to happiness.          ', '默认商品图.png', 1, '2019-02-23 07:47:20', '2016-06-11 02:01:14');
INSERT INTO `goods` VALUES (228, 66, 9, '键盘', 1226.13, 1415, 0.66, 'To clear or reload various internal caches, flush tables, or acquire locks, control-click           ', '默认商品图.png', 1, '2021-06-11 10:34:32', '2002-02-09 04:30:51');
INSERT INTO `goods` VALUES (229, 56, 2, '裤子', 762.87, 8307, 0.82, 'To open a query using an external editor, control-click it and select Open with External            ', '默认商品图.png', 0, '2022-12-14 16:49:47', '2007-05-07 04:46:07');
INSERT INTO `goods` VALUES (231, 52, 9, '青霉素', 1266.81, 1147, 0.85, 'The repository database can be an existing MySQL, MariaDB, PostgreSQL, SQL Server,                  ', '默认商品图.png', 1, '2004-09-05 12:12:17', '2005-12-05 22:25:11');
INSERT INTO `goods` VALUES (234, 37, 6, '雨鞋', 1925.96, 2814, 0.83, 'In the middle of winter I at last discovered that there was in me an invincible summer.             ', '默认商品图.png', 0, '2003-11-21 17:23:56', '2003-02-02 03:22:04');
INSERT INTO `goods` VALUES (235, 61, 6, '毛巾', 1448.05, 6299, 0.38, 'To start working with your server in Navicat, you should first establish a connection               ', '默认商品图.png', 0, '2019-06-21 16:19:05', '2015-04-05 10:16:17');
INSERT INTO `goods` VALUES (238, 49, 6, '999感冒灵', 575.64, 701, 0.74, 'The reason why a great man is great is that he resolves to be a great man.                          ', '默认商品图.png', 0, '2006-08-11 17:55:44', '2021-02-26 14:29:14');
INSERT INTO `goods` VALUES (241, 56, 7, '扫把', 174.68, 4434, 0.1, 'The reason why a great man is great is that he resolves to be a great man.                          ', '默认商品图.png', 0, '2016-02-17 12:23:29', '2019-09-18 05:15:36');
INSERT INTO `goods` VALUES (242, 18, 4, '钱包', 166.5, 4843, 0.42, 'All journeys have secret destinations of which the traveler is unaware.                             ', '默认商品图.png', 1, '2006-07-23 13:58:22', '2020-08-30 22:11:57');
INSERT INTO `goods` VALUES (245, 33, 5, '垃圾袋', 607.42, 2281, 0.68, 'It provides strong authentication and secure encrypted communications between two                   ', '默认商品图.png', 1, '2004-08-21 01:05:43', '2011-02-09 19:50:07');
INSERT INTO `goods` VALUES (247, 33, 9, '垃圾袋', 1586.17, 4403, 0.02, 'Always keep your eyes open. Keep watching. Because whatever you see can inspire you.                ', '默认商品图.png', 0, '2013-04-10 09:45:34', '2015-07-14 15:51:53');
INSERT INTO `goods` VALUES (249, 31, 2, '垃圾桶', 276.94, 3873, 0.55, 'Optimism is the one quality more associated with success and happiness than any other.              ', '默认商品图.png', 0, '2008-02-02 05:57:30', '2014-02-15 11:28:03');
INSERT INTO `goods` VALUES (251, 57, 3, '铁盆', 173.34, 5400, 0.78, 'Navicat Monitor can be installed on any local computer or virtual machine and does                  ', '默认商品图.png', 0, '2011-07-21 05:53:40', '2015-05-26 02:21:09');
INSERT INTO `goods` VALUES (253, 61, 1, '袜子', 138.26, 1579, 0.11, 'SQL Editor allows you to create and edit SQL text, prepare and execute selected queries.            ', '默认商品图.png', 1, '2011-06-15 22:06:05', '2017-04-24 04:57:11');
INSERT INTO `goods` VALUES (254, 16, 2, '拖把', 1878.69, 4833, 0.16, 'To connect to a database or schema, simply double-click it in the pane.                             ', '默认商品图.png', 0, '2019-02-27 14:08:40', '2018-01-08 04:34:55');
INSERT INTO `goods` VALUES (255, 35, 1, '裙子', 122.85, 1698, 0.71, 'Anyone who has never made a mistake has never tried anything new. Instead of wondering              ', '默认商品图.png', 0, '2016-06-22 09:40:11', '2021-02-26 09:02:03');
INSERT INTO `goods` VALUES (258, 62, 8, '垃圾桶', 1307.04, 5486, 0.36, 'Navicat Data Modeler is a powerful and cost-effective database design tool which                    ', '默认商品图.png', 1, '2004-06-26 11:46:47', '2020-06-08 12:01:22');
INSERT INTO `goods` VALUES (259, 31, 7, '撮箕', 524.73, 7793, 0.98, 'The Navigation pane employs tree structure which allows you to take action upon the                 ', '默认商品图.png', 0, '2003-09-13 19:10:56', '2019-02-09 12:20:01');
INSERT INTO `goods` VALUES (260, 31, 2, '阿莫西尼', 1998.36, 6081, 0.69, 'The Main Window consists of several toolbars and panes for you to work on connections,              ', '默认商品图.png', 1, '2000-03-04 11:13:16', '2012-12-05 07:18:41');
INSERT INTO `goods` VALUES (261, 50, 6, '书包', 1680.23, 9575, 0.88, 'The first step is as good as half over. There is no way to happiness. Happiness is the way.         ', '默认商品图.png', 0, '2004-09-20 05:49:17', '2023-03-11 04:25:32');
INSERT INTO `goods` VALUES (262, 18, 9, '毛巾', 1745.61, 2487, 0.34, 'After logged in the Navicat Cloud feature, the Navigation pane will be divided into                 ', '默认商品图.png', 1, '2013-11-29 08:57:06', '2001-11-19 07:17:53');
INSERT INTO `goods` VALUES (265, 56, 7, '垃圾桶', 1287.8, 1795, 0.6, 'Navicat provides powerful tools for working with queries: Query Editor for editing                  ', '默认商品图.png', 0, '2013-09-13 05:27:07', '2007-06-14 00:50:32');
INSERT INTO `goods` VALUES (268, 37, 8, '拖把', 740.36, 1700, 0.8, 'Export Wizard allows you to export data from tables, collections, views, or query                   ', '默认商品图.png', 0, '2006-10-10 13:52:04', '2001-04-23 21:47:04');
INSERT INTO `goods` VALUES (269, 46, 8, '音响', 1005.4, 2099, 0.41, 'A query is used to extract data from the database in a readable format according                    ', '默认商品图.png', 1, '2019-01-22 03:40:37', '2003-06-04 18:35:12');
INSERT INTO `goods` VALUES (270, 43, 9, '裙子', 25.91, 3324, 0.2, 'Navicat 15 has added support for the system-wide dark mode. The past has no power                   ', '默认商品图.png', 1, '2005-01-18 14:21:20', '2005-01-01 00:35:34');
INSERT INTO `goods` VALUES (271, 66, 9, '橘子', 1279.98, 851, 0.17, 'All the Navicat Cloud objects are located under different projects. You can share                   ', '默认商品图.png', 0, '2005-01-14 17:51:21', '2018-11-25 11:46:31');
INSERT INTO `goods` VALUES (272, 42, 8, '衣服', 419.08, 7192, 0.48, 'The On Startup feature allows you to control what tabs appear when you launch Navicat.              ', '默认商品图.png', 1, '2000-05-07 23:56:34', '2011-10-29 04:37:30');
INSERT INTO `goods` VALUES (273, 49, 4, '裤子', 560.18, 2618, 0.73, 'A man’s best friends are his ten fingers. Navicat is a multi-connections Database                 ', '默认商品图.png', 0, '2021-01-27 06:46:06', '2012-03-18 10:28:41');
INSERT INTO `goods` VALUES (275, 62, 5, '陶瓷碗', 609.5, 2053, 0.85, 'Sometimes you win, sometimes you learn. A man is not old until regrets take the place of dreams.    ', '默认商品图.png', 1, '2018-06-13 10:30:39', '2011-08-14 19:08:56');
INSERT INTO `goods` VALUES (282, 58, 1, '阿莫西尼', 333.26, 1785, 0.47, 'Navicat Monitor is a safe, simple and agentless remote server monitoring tool that                  ', '默认商品图.png', 0, '2013-06-07 06:50:55', '2010-12-18 17:46:00');
INSERT INTO `goods` VALUES (283, 42, 5, '数据线', 1800.39, 2153, 0.76, 'All journeys have secret destinations of which the traveler is unaware.                             ', '默认商品图.png', 0, '2006-10-04 23:15:50', '2014-03-24 03:54:40');
INSERT INTO `goods` VALUES (284, 65, 5, '雨衣', 1340.48, 2369, 0.09, 'To successfully establish a new connection to local/remote server - no matter via                   ', '默认商品图.png', 0, '2016-05-24 08:50:04', '2016-12-26 07:18:43');
INSERT INTO `goods` VALUES (287, 52, 4, '保鲜膜', 1537.97, 1395, 0.62, 'I destroy my enemies when I make them my friends. To clear or reload various internal               ', '默认商品图.png', 0, '2008-09-20 13:15:39', '2011-02-02 23:24:41');
INSERT INTO `goods` VALUES (290, 32, 7, '阿莫西尼', 1402.62, 8088, 0.7, 'The repository database can be an existing MySQL, MariaDB, PostgreSQL, SQL Server,                  ', '默认商品图.png', 0, '2023-10-11 02:21:00', '2011-07-13 01:57:06');
INSERT INTO `goods` VALUES (291, 41, 2, '草莓', 1472.74, 6706, 0.27, 'Navicat authorizes you to make connection to remote servers running on different                    ', '默认商品图.png', 0, '2002-11-29 18:19:34', '2000-04-09 06:49:16');
INSERT INTO `goods` VALUES (292, 57, 3, '数据线', 1216, 7207, 0.51, 'The past has no power over the present moment. I may not have gone where I intended                 ', '默认商品图.png', 1, '2023-06-06 16:20:40', '2015-09-06 10:42:26');
INSERT INTO `goods` VALUES (295, 65, 9, '音响', 312.91, 4770, 0.27, 'Success consists of going from failure to failure without loss of enthusiasm.                       ', '默认商品图.png', 1, '2016-07-03 02:31:50', '2000-08-30 10:24:57');
INSERT INTO `goods` VALUES (296, 63, 4, '钱包', 1842.04, 7257, 0.05, 'With its well-designed Graphical User Interface(GUI), Navicat lets you quickly and                  ', '默认商品图.png', 0, '2021-03-12 04:14:57', '2017-07-18 16:55:51');
INSERT INTO `goods` VALUES (298, 66, 8, '青霉素', 1735.97, 8693, 0.34, 'If it scares you, it might be a good thing to try. Secure Sockets Layer(SSL) is a                   ', '默认商品图.png', 1, '2019-03-31 12:45:27', '2013-02-04 14:08:05');
INSERT INTO `goods` VALUES (300, 37, 8, '橙子', 1723.22, 6691, 0.24, 'Navicat 15 has added support for the system-wide dark mode. To successfully establish               ', '默认商品图.png', 0, '2011-12-24 22:28:46', '2023-10-23 13:32:10');
INSERT INTO `goods` VALUES (301, 57, 2, '999感冒灵', 780.54, 9030, 0.17, 'Export Wizard allows you to export data from tables, collections, views, or query                   ', '默认商品图.png', 1, '2002-02-19 02:47:13', '2004-03-30 03:52:12');
INSERT INTO `goods` VALUES (302, 43, 7, '橘子', 1264.45, 1499, 0.42, 'Instead of wondering when your next vacation is, maybe you should set up a life you                 ', '默认商品图.png', 0, '2008-09-27 03:07:13', '2024-01-03 17:42:16');
INSERT INTO `goods` VALUES (303, 52, 3, '橙子', 1218.1, 3103, 0.26, 'In the middle of winter I at last discovered that there was in me an invincible summer.             ', '默认商品图.png', 0, '2017-12-20 10:16:01', '2008-11-06 06:27:59');
INSERT INTO `goods` VALUES (306, 52, 10, '鞋子', 1315.31, 5615, 0.61, 'Navicat provides a wide range advanced features, such as compelling code editing                    ', '默认商品图.png', 1, '2014-06-22 02:03:02', '2001-12-08 21:41:33');
INSERT INTO `goods` VALUES (307, 47, 6, '扫把', 616.8, 8040, 0.32, 'Navicat Cloud provides a cloud service for synchronizing connections, queries, model                ', '默认商品图.png', 1, '2016-11-20 09:45:20', '2019-09-16 12:10:40');
INSERT INTO `goods` VALUES (309, 31, 8, '999感冒灵', 156.19, 8253, 0.48, 'Genius is an infinite capacity for taking pains. Anyone who has never made a mistake                ', '默认商品图.png', 0, '2009-12-23 12:36:00', '2005-07-14 17:02:56');
INSERT INTO `goods` VALUES (311, 34, 5, '西瓜', 1398.8, 7053, 1, 'You can select any connections, objects or projects, and then select the corresponding              ', '默认商品图.png', 1, '2011-05-28 12:02:02', '2016-06-29 17:58:50');
INSERT INTO `goods` VALUES (316, 18, 4, '垃圾桶', 1866.62, 5742, 0.51, 'What you get by achieving your goals is not as important as what you become by achieving your goals.', '默认商品图.png', 1, '2013-07-30 23:54:26', '2010-01-26 20:39:01');
INSERT INTO `goods` VALUES (320, 60, 3, '苹果', 1382.76, 8373, 0.53, 'Actually it is just in an idea when feel oneself can achieve and cannot achieve.                    ', '默认商品图.png', 0, '2003-07-06 14:17:01', '2020-03-05 08:22:18');
INSERT INTO `goods` VALUES (321, 50, 8, '撮箕', 414.09, 717, 0.2, 'Navicat Data Modeler is a powerful and cost-effective database design tool which                    ', '默认商品图.png', 0, '2022-06-12 14:29:10', '2000-09-12 14:51:29');
INSERT INTO `goods` VALUES (322, 51, 7, '雨衣', 68.02, 1922, 0.59, 'To connect to a database or schema, simply double-click it in the pane.                             ', '默认商品图.png', 0, '2012-04-12 05:31:33', '2017-12-10 09:32:21');
INSERT INTO `goods` VALUES (324, 43, 6, '钱包', 410.54, 7883, 0.63, 'The Information Pane shows the detailed object information, project activities, the                 ', '默认商品图.png', 1, '2008-03-04 20:10:14', '2018-07-29 17:32:25');
INSERT INTO `goods` VALUES (325, 51, 4, '手提包', 1274.87, 3435, 0.91, 'Optimism is the one quality more associated with success and happiness than any other.              ', '默认商品图.png', 1, '2001-11-27 05:13:18', '2010-01-09 17:33:28');
INSERT INTO `goods` VALUES (326, 49, 5, '扫把', 128.48, 9576, 0.64, 'If your Internet Service Provider (ISP) does not provide direct access to its server,               ', '默认商品图.png', 1, '2008-07-12 16:52:48', '2021-06-26 13:25:06');
INSERT INTO `goods` VALUES (328, 53, 9, '撮箕', 1541.1, 4736, 0.87, 'In the Objects tab, you can use the List List, Detail Detail and ER Diagram ER Diagram              ', '默认商品图.png', 0, '2016-09-13 07:26:18', '2005-12-25 19:34:16');
INSERT INTO `goods` VALUES (330, 5, 8, '保鲜膜', 13.75, 911, 0.68, 'Creativity is intelligence having fun. The On Startup feature allows you to control                 ', '默认商品图.png', 1, '2022-06-25 00:01:35', '2016-08-17 15:36:20');
INSERT INTO `goods` VALUES (331, 60, 8, '西瓜', 247.79, 1161, 0.96, 'All journeys have secret destinations of which the traveler is unaware.                             ', '默认商品图.png', 1, '2005-11-19 19:17:28', '2012-09-13 15:05:02');
INSERT INTO `goods` VALUES (332, 47, 2, '雨鞋', 1253.19, 1478, 0.15, 'The On Startup feature allows you to control what tabs appear when you launch Navicat.              ', '默认商品图.png', 1, '2012-05-08 21:16:05', '2012-10-13 09:42:32');
INSERT INTO `goods` VALUES (333, 16, 9, '保鲜膜', 1565.67, 8508, 0.5, 'What you get by achieving your goals is not as important as what you become by achieving your goals.', '默认商品图.png', 1, '2014-02-01 13:58:24', '2022-09-10 08:02:40');
INSERT INTO `goods` VALUES (334, 61, 2, '手提包', 1393.15, 41, 0.12, 'To successfully establish a new connection to local/remote server - no matter via                   ', '默认商品图.png', 1, '2006-06-20 19:47:29', '2003-08-18 20:31:30');
INSERT INTO `goods` VALUES (337, 50, 5, '青霉素', 1790.51, 2337, 0.75, 'Difficult circumstances serve as a textbook of life for people. Sometimes you win,                  ', '默认商品图.png', 0, '2021-12-09 19:42:18', '2018-12-19 18:17:03');
INSERT INTO `goods` VALUES (338, 33, 3, '电脑', 689.58, 7294, 0.96, 'SQL Editor allows you to create and edit SQL text, prepare and execute selected queries.            ', '默认商品图.png', 1, '2015-05-04 23:22:55', '2004-04-14 06:38:58');
INSERT INTO `goods` VALUES (342, 16, 9, '撮箕', 1679.18, 2418, 0.02, 'It is used while your ISPs do not allow direct connections, but allows establishing                 ', '默认商品图.png', 0, '2000-12-10 15:24:53', '2017-03-13 08:43:42');
INSERT INTO `goods` VALUES (344, 66, 5, '背包', 614.8, 1958, 0.36, 'How we spend our days is, of course, how we spend our lives. The Main Window consists               ', '默认商品图.png', 0, '2023-11-01 21:57:17', '2003-01-18 12:38:53');
INSERT INTO `goods` VALUES (349, 46, 7, '苹果', 1298.25, 4073, 0.95, 'Navicat Cloud provides a cloud service for synchronizing connections, queries, model                ', '默认商品图.png', 0, '2011-05-05 22:12:02', '2019-09-19 15:29:28');
INSERT INTO `goods` VALUES (351, 33, 9, '梨', 1834.52, 2335, 0.86, 'The Information Pane shows the detailed object information, project activities, the                 ', '默认商品图.png', 0, '2002-06-26 22:16:23', '2013-07-21 20:42:32');
INSERT INTO `goods` VALUES (353, 58, 8, '衣服', 1395.68, 6217, 0.81, 'The Information Pane shows the detailed object information, project activities, the                 ', '默认商品图.png', 1, '2019-04-22 02:52:25', '2016-07-06 05:28:07');
INSERT INTO `goods` VALUES (354, 36, 6, '扫把', 324.21, 2332, 0.96, 'It wasn’t raining when Noah built the ark. The Information Pane shows the detailed                ', '默认商品图.png', 0, '2008-06-11 08:59:10', '2023-02-28 19:58:04');
INSERT INTO `goods` VALUES (355, 43, 7, '钱包', 564.01, 3023, 0.47, 'Sometimes you win, sometimes you learn. To start working with your server in Navicat,               ', '默认商品图.png', 0, '2000-01-19 11:48:44', '2021-01-19 15:47:17');
INSERT INTO `goods` VALUES (356, 65, 7, '青霉素', 359.09, 1766, 0.71, 'The reason why a great man is great is that he resolves to be a great man.                          ', '默认商品图.png', 0, '2022-10-01 06:12:38', '2003-02-27 04:20:37');
INSERT INTO `goods` VALUES (357, 63, 7, '陶瓷碗', 1894.47, 1155, 0.39, 'After comparing data, the window shows the number of records that will be inserted,                 ', '默认商品图.png', 1, '2013-01-19 15:08:25', '2013-05-19 17:32:48');
INSERT INTO `goods` VALUES (359, 33, 5, '手提包', 460.76, 8894, 0.45, 'If the plan doesn’t work, change the plan, but never the goal. I destroy my enemies               ', '默认商品图.png', 1, '2009-08-19 21:34:17', '2018-04-03 07:52:47');
INSERT INTO `goods` VALUES (360, 55, 2, '锅', 116.87, 6056, 0.39, 'To open a query using an external editor, control-click it and select Open with External            ', '默认商品图.png', 0, '2001-03-30 03:31:08', '2020-09-14 01:53:24');
INSERT INTO `goods` VALUES (361, 34, 8, '充电宝', 216.18, 6264, 0.1, 'To clear or reload various internal caches, flush tables, or acquire locks, control-click           ', '默认商品图.png', 0, '2008-08-17 14:25:53', '2000-02-03 15:24:34');
INSERT INTO `goods` VALUES (363, 54, 2, '梨', 359.08, 1113, 0.12, 'SQL Editor allows you to create and edit SQL text, prepare and execute selected queries.            ', '默认商品图.png', 0, '2018-08-06 19:17:11', '2012-05-21 10:53:33');
INSERT INTO `goods` VALUES (364, 62, 9, '999感冒灵', 764.92, 5263, 0.67, 'Anyone who has ever made anything of importance was disciplined. If it scares you,                  ', '默认商品图.png', 1, '2001-11-07 08:37:56', '2002-02-09 15:38:18');
INSERT INTO `goods` VALUES (372, 1, 9, '手提包', 14.15, 9933, 0.36, 'To get a secure connection, the first thing you need to do is to install OpenSSL                    ', '默认商品图.png', 1, '2013-05-11 00:38:35', '2024-04-07 15:53:34');
INSERT INTO `goods` VALUES (373, 55, 2, '裙子', 1813.6, 735, 0.24, 'The Navigation pane employs tree structure which allows you to take action upon the                 ', '默认商品图.png', 1, '2004-07-13 10:33:49', '2016-08-11 11:14:37');
INSERT INTO `goods` VALUES (374, 36, 4, '充电宝', 518.34, 1479, 0.51, 'Navicat provides powerful tools for working with queries: Query Editor for editing                  ', '默认商品图.png', 1, '2002-11-22 14:17:09', '2020-03-09 19:09:08');
INSERT INTO `goods` VALUES (375, 33, 7, '充电宝', 1053.82, 5668, 0.37, 'The first step is as good as half over. Difficult circumstances serve as a textbook                 ', '默认商品图.png', 0, '2006-11-28 02:13:11', '2011-05-27 14:59:53');
INSERT INTO `goods` VALUES (376, 55, 6, '电脑', 1450.32, 5737, 0.6, 'A query is used to extract data from the database in a readable format according                    ', '默认商品图.png', 0, '2002-08-20 20:48:57', '2005-08-15 19:05:09');
INSERT INTO `goods` VALUES (382, 57, 9, '雨鞋', 1529.29, 8433, 0.96, 'I will greet this day with love in my heart. If opportunity doesn’t knock, build a door.          ', '默认商品图.png', 1, '2020-12-22 04:09:22', '2001-12-28 07:31:24');
INSERT INTO `goods` VALUES (390, 54, 9, '手提包', 708.95, 9995, 0.5, 'Navicat allows you to transfer data from one database and/or schema to another with                 ', '默认商品图.png', 1, '2021-10-30 12:11:53', '2005-09-03 16:33:37');
INSERT INTO `goods` VALUES (392, 45, 3, '手机', 1711.17, 7127, 0.35, 'You will succeed because most people are lazy. All journeys have secret destinations                ', '默认商品图.png', 0, '2018-12-19 10:19:59', '2001-11-09 02:19:42');
INSERT INTO `goods` VALUES (394, 52, 9, '背包', 632.63, 3051, 0.9, 'Navicat Cloud provides a cloud service for synchronizing connections, queries, model                ', '默认商品图.png', 0, '2006-10-24 00:44:42', '2001-05-09 15:11:59');
INSERT INTO `goods` VALUES (395, 33, 6, '背包', 158.61, 2982, 0.51, 'Navicat Data Modeler is a powerful and cost-effective database design tool which                    ', '默认商品图.png', 1, '2013-02-17 02:29:07', '2020-07-14 18:18:31');
INSERT INTO `goods` VALUES (399, 58, 4, '西瓜', 509.87, 6844, 0.82, 'You can select any connections, objects or projects, and then select the corresponding              ', '默认商品图.png', 0, '2008-07-09 05:16:07', '2023-07-30 20:46:17');
INSERT INTO `goods` VALUES (401, 47, 8, '扫把', 1957.27, 9667, 0.65, 'Difficult circumstances serve as a textbook of life for people. Instead of wondering                ', '默认商品图.png', 1, '2022-04-30 06:30:54', '2014-06-04 08:45:39');
INSERT INTO `goods` VALUES (405, 62, 9, '钱包', 904.75, 4963, 0.36, 'Navicat Data Modeler is a powerful and cost-effective database design tool which                    ', '默认商品图.png', 0, '2000-04-01 00:21:11', '2008-01-14 11:52:16');
INSERT INTO `goods` VALUES (407, 60, 10, '草莓', 1655.67, 3449, 0.44, 'Difficult circumstances serve as a textbook of life for people. All the Navicat Cloud               ', '默认商品图.png', 0, '2000-04-25 00:52:06', '2000-07-27 07:00:05');
INSERT INTO `goods` VALUES (408, 40, 6, '阿莫西尼', 1399.27, 3345, 0.71, 'How we spend our days is, of course, how we spend our lives. The first step is as good as half over.', '默认商品图.png', 1, '2018-06-25 14:28:16', '2007-11-13 11:49:10');
INSERT INTO `goods` VALUES (409, 18, 7, '毛巾', 61.17, 3758, 0.17, 'To get a secure connection, the first thing you need to do is to install OpenSSL                    ', '默认商品图.png', 0, '2020-01-16 06:34:48', '2006-06-27 21:38:38');
INSERT INTO `goods` VALUES (410, 52, 8, '背包', 634.99, 2778, 0.52, 'The Information Pane shows the detailed object information, project activities, the                 ', '默认商品图.png', 0, '2006-12-27 15:34:15', '2018-03-06 08:32:07');
INSERT INTO `goods` VALUES (411, 64, 5, '铁盆', 1564.75, 9259, 0.22, 'Navicat Monitor is a safe, simple and agentless remote server monitoring tool that                  ', '默认商品图.png', 1, '2012-05-02 17:20:55', '2018-08-22 09:09:27');
INSERT INTO `goods` VALUES (414, 41, 5, '扫把', 1435.11, 829, 0.99, 'A man’s best friends are his ten fingers. SSH serves to prevent such vulnerabilities              ', '默认商品图.png', 1, '2013-06-14 22:41:09', '2022-07-07 06:10:45');
INSERT INTO `goods` VALUES (417, 50, 9, '键盘', 1161.86, 1753, 0.02, 'After logged in the Navicat Cloud feature, the Navigation pane will be divided into                 ', '默认商品图.png', 1, '2007-07-13 03:04:22', '2004-02-23 21:33:14');
INSERT INTO `goods` VALUES (419, 5, 2, '摄像机', 95.22, 9005, 0.29, 'The On Startup feature allows you to control what tabs appear when you launch Navicat.              ', '默认商品图.png', 1, '2018-08-23 00:21:17', '2016-05-26 22:58:02');
INSERT INTO `goods` VALUES (420, 33, 9, '袜子', 1720.14, 7850, 0.88, 'Navicat Monitor can be installed on any local computer or virtual machine and does                  ', '默认商品图.png', 1, '2008-02-25 11:15:47', '2005-02-27 03:46:43');
INSERT INTO `goods` VALUES (424, 55, 8, '垃圾桶', 1621.27, 1968, 0.67, 'The Information Pane shows the detailed object information, project activities, the                 ', '默认商品图.png', 0, '2003-04-19 06:32:21', '2022-05-16 11:43:29');
INSERT INTO `goods` VALUES (425, 36, 7, '梨', 1748.43, 5871, 0.53, 'A man’s best friends are his ten fingers. HTTP Tunneling is a method for connecting               ', '默认商品图.png', 1, '2018-06-29 01:28:10', '2000-09-30 11:36:03');
INSERT INTO `goods` VALUES (429, 59, 4, '裤子', 1121.69, 2744, 0.53, 'Navicat Monitor is a safe, simple and agentless remote server monitoring tool that                  ', '默认商品图.png', 1, '2016-07-15 11:52:48', '2022-07-19 09:10:04');
INSERT INTO `goods` VALUES (431, 57, 6, '音响', 1666.59, 4249, 0.34, 'There is no way to happiness. Happiness is the way. The Synchronize to Database function            ', '默认商品图.png', 0, '2014-02-01 16:47:54', '2008-05-25 19:28:51');
INSERT INTO `goods` VALUES (433, 57, 2, '阿莫西尼', 1346, 6804, 0.91, 'Creativity is intelligence having fun. Monitored servers include MySQL, MariaDB and                 ', '默认商品图.png', 0, '2003-10-16 22:53:44', '2023-05-26 05:32:04');
INSERT INTO `goods` VALUES (434, 5, 1, '橙子', 258.49, 706, 0.65, 'The Synchronize to Database function will give you a full picture of all database differences.      ', '默认商品图.png', 1, '2003-05-13 05:17:01', '2010-04-23 01:58:57');
INSERT INTO `goods` VALUES (435, 34, 6, '青霉素', 316.88, 5814, 0.29, 'It can also manage cloud databases such as Amazon Redshift, Amazon RDS, Alibaba Cloud.              ', '默认商品图.png', 1, '2000-01-02 03:36:14', '2017-01-21 02:24:09');
INSERT INTO `goods` VALUES (436, 45, 4, '音响', 1413.59, 8628, 0.75, 'A man’s best friends are his ten fingers. The reason why a great man is great is                  ', '默认商品图.png', 0, '2012-10-30 00:07:52', '2008-04-16 03:01:33');
INSERT INTO `goods` VALUES (437, 34, 2, '撮箕', 194.87, 6064, 0.56, 'If it scares you, it might be a good thing to try. It is used while your ISPs do                    ', '默认商品图.png', 0, '2024-02-06 18:19:40', '2016-02-06 16:22:06');
INSERT INTO `goods` VALUES (440, 50, 2, '梨', 1745.98, 9706, 0.88, 'Anyone who has never made a mistake has never tried anything new. Navicat is a multi-connections    ', '默认商品图.png', 0, '2018-10-22 09:45:50', '2016-12-05 08:14:49');
INSERT INTO `goods` VALUES (441, 31, 6, '扫把', 239.1, 1699, 0.36, 'If opportunity doesn’t knock, build a door. The reason why a great man is great                   ', '默认商品图.png', 1, '2012-05-31 13:27:12', '2016-03-03 18:26:46');
INSERT INTO `goods` VALUES (442, 33, 8, '锅', 1673.62, 3586, 0.04, 'Anyone who has never made a mistake has never tried anything new. It wasn’t raining               ', '默认商品图.png', 1, '2019-12-23 01:26:38', '2009-05-25 09:59:48');
INSERT INTO `goods` VALUES (444, 31, 6, '雨衣', 1295.93, 1167, 0.78, 'In the middle of winter I at last discovered that there was in me an invincible summer.             ', '默认商品图.png', 1, '2015-08-28 01:50:51', '2012-01-06 01:57:51');
INSERT INTO `goods` VALUES (445, 36, 8, '毛巾', 412.38, 3770, 0.99, 'Navicat 15 has added support for the system-wide dark mode. You can select any connections,         ', '默认商品图.png', 0, '2006-02-05 06:57:16', '2011-03-31 00:21:16');
INSERT INTO `goods` VALUES (446, 62, 3, '键盘', 26.07, 6151, 0.54, 'A query is used to extract data from the database in a readable format according                    ', '默认商品图.png', 0, '2005-07-12 09:42:41', '2009-01-22 07:02:24');
INSERT INTO `goods` VALUES (447, 31, 9, '垃圾桶', 542.54, 6439, 0.33, 'I may not have gone where I intended to go, but I think I have ended up where I needed to be.       ', '默认商品图.png', 0, '2014-02-18 00:09:52', '2004-07-03 18:54:59');
INSERT INTO `goods` VALUES (448, 54, 8, '裙子', 312.38, 1841, 0.38, 'If the plan doesn’t work, change the plan, but never the goal. The On Startup feature             ', '默认商品图.png', 0, '2023-12-02 02:24:43', '2002-11-29 07:17:45');
INSERT INTO `goods` VALUES (449, 60, 4, '鞋子', 1942.27, 1513, 0.17, 'The Information Pane shows the detailed object information, project activities, the                 ', '默认商品图.png', 1, '2022-10-23 15:09:50', '2002-03-30 16:45:23');
INSERT INTO `goods` VALUES (450, 43, 3, '橙子', 1300.86, 1696, 0.93, 'The On Startup feature allows you to control what tabs appear when you launch Navicat.              ', '默认商品图.png', 1, '2005-10-14 22:51:07', '2001-02-01 04:56:30');
INSERT INTO `goods` VALUES (451, 44, 4, '裙子', 1392.72, 197, 0.1, 'To successfully establish a new connection to local/remote server - no matter via                   ', '默认商品图.png', 0, '2018-04-17 03:44:21', '2009-07-20 07:10:43');
INSERT INTO `goods` VALUES (453, 60, 5, '垃圾袋', 1324.24, 6664, 0.29, 'Difficult circumstances serve as a textbook of life for people. All journeys have                   ', '默认商品图.png', 0, '2022-06-16 05:04:24', '2002-10-03 10:20:51');
INSERT INTO `goods` VALUES (454, 43, 8, '西瓜', 125.53, 705, 0.09, 'It is used while your ISPs do not allow direct connections, but allows establishing                 ', '默认商品图.png', 1, '2007-03-22 06:29:09', '2009-04-03 00:21:12');
INSERT INTO `goods` VALUES (455, 38, 4, '键盘', 1628.55, 5663, 0.96, 'All the Navicat Cloud objects are located under different projects. You can share                   ', '默认商品图.png', 1, '2005-10-18 20:03:05', '2011-10-31 01:48:42');
INSERT INTO `goods` VALUES (457, 38, 8, '书包', 168.01, 4961, 0.88, 'I destroy my enemies when I make them my friends. Navicat is a multi-connections                    ', '默认商品图.png', 0, '2012-07-20 23:33:41', '2016-03-22 14:20:05');
INSERT INTO `goods` VALUES (458, 34, 9, '拖把', 1170.34, 1272, 0.66, 'You can select any connections, objects or projects, and then select the corresponding              ', '默认商品图.png', 0, '2013-12-25 08:41:22', '2002-08-20 10:41:45');
INSERT INTO `goods` VALUES (459, 64, 8, '充电宝', 1616.4, 3177, 0.74, 'All the Navicat Cloud objects are located under different projects. You can share                   ', '默认商品图.png', 0, '2010-04-11 09:01:59', '2004-04-25 20:57:38');
INSERT INTO `goods` VALUES (462, 36, 7, '苹果', 1137.86, 9502, 0.62, 'A query is used to extract data from the database in a readable format according                    ', '默认商品图.png', 0, '2000-04-04 02:50:03', '2015-12-12 03:38:23');
INSERT INTO `goods` VALUES (463, 61, 8, '手提包', 1713.6, 5707, 0.17, 'Anyone who has ever made anything of importance was disciplined. What you get by                    ', '默认商品图.png', 0, '2000-04-25 15:46:52', '2006-05-04 05:11:21');
INSERT INTO `goods` VALUES (464, 60, 6, '充电宝', 1759.42, 4778, 0.32, 'Genius is an infinite capacity for taking pains. The Information Pane shows the detailed            ', '默认商品图.png', 1, '2005-05-21 05:58:59', '2014-05-12 07:14:18');
INSERT INTO `goods` VALUES (468, 66, 4, '铁盆', 914.88, 4003, 0.14, 'Actually it is just in an idea when feel oneself can achieve and cannot achieve.                    ', '默认商品图.png', 1, '2019-05-25 19:27:51', '2017-01-14 22:03:30');
INSERT INTO `goods` VALUES (469, 44, 6, '键盘', 1277.42, 3787, 0.23, 'Flexible settings enable you to set up a custom key for comparison and synchronization.             ', '默认商品图.png', 1, '2018-09-26 11:01:21', '2000-02-20 14:50:25');
INSERT INTO `goods` VALUES (471, 48, 5, '手机', 943.89, 916, 0.79, 'Navicat Monitor is a safe, simple and agentless remote server monitoring tool that                  ', '默认商品图.png', 0, '2004-06-15 05:43:24', '2015-03-24 07:51:25');
INSERT INTO `goods` VALUES (474, 64, 8, '雨衣', 840.46, 1770, 0, 'If your Internet Service Provider (ISP) does not provide direct access to its server,               ', '默认商品图.png', 0, '2002-08-30 01:16:52', '2010-09-09 08:10:11');
INSERT INTO `goods` VALUES (476, 41, 3, '背包', 265.64, 8736, 0.76, 'If you wait, all that happens is you get older. The Main Window consists of several                 ', '默认商品图.png', 0, '2016-07-29 12:34:50', '2008-11-05 14:06:28');
INSERT INTO `goods` VALUES (478, 48, 2, '陶瓷碗', 294.6, 1602, 0.98, 'In the middle of winter I at last discovered that there was in me an invincible summer.             ', '默认商品图.png', 1, '2012-06-26 04:25:06', '2009-06-07 04:05:24');
INSERT INTO `goods` VALUES (479, 47, 6, '西瓜', 1894.96, 9774, 0.74, 'The first step is as good as half over. Navicat is a multi-connections Database Administration      ', '默认商品图.png', 0, '2019-10-07 06:31:33', '2002-09-01 07:43:01');
INSERT INTO `goods` VALUES (480, 35, 4, '键盘', 1727.29, 3964, 0.31, 'If you wait, all that happens is you get older. Navicat Monitor can be installed                    ', '默认商品图.png', 0, '2005-12-12 01:45:29', '2005-06-05 13:36:18');
INSERT INTO `goods` VALUES (482, 43, 8, '保鲜膜', 666.19, 9893, 0.35, 'It wasn’t raining when Noah built the ark. Navicat is a multi-connections Database                ', '默认商品图.png', 1, '2008-04-21 02:26:38', '2023-04-20 11:59:08');
INSERT INTO `goods` VALUES (483, 45, 3, '梨', 1271.03, 7862, 0.45, 'Secure Sockets Layer(SSL) is a protocol for transmitting private documents via the Internet.        ', '默认商品图.png', 0, '2023-05-07 19:28:20', '2010-10-05 18:22:23');
INSERT INTO `goods` VALUES (487, 16, 5, '摄像机', 885.42, 9469, 0, 'Monitored servers include MySQL, MariaDB and SQL Server, and compatible with cloud                  ', '默认商品图.png', 0, '2017-10-05 06:03:32', '2020-10-09 20:59:22');
INSERT INTO `goods` VALUES (488, 65, 5, '摄像机', 1520.34, 2468, 0, 'The Navigation pane employs tree structure which allows you to take action upon the                 ', '默认商品图.png', 1, '2016-11-01 02:17:09', '2023-08-02 13:11:51');
INSERT INTO `goods` VALUES (492, 32, 6, '雨鞋', 276.23, 4033, 0.74, 'Navicat authorizes you to make connection to remote servers running on different                    ', '默认商品图.png', 1, '2013-08-18 00:59:35', '2001-11-10 13:35:36');
INSERT INTO `goods` VALUES (494, 44, 4, '背包', 1246.21, 3825, 0.67, 'Difficult circumstances serve as a textbook of life for people. If opportunity doesn’t            ', '默认商品图.png', 0, '2014-01-13 06:01:47', '2006-11-25 12:09:55');
INSERT INTO `goods` VALUES (495, 46, 5, '草莓', 140.78, 8282, 0.9, 'Navicat Cloud could not connect and access your databases. By which it means, it                    ', '默认商品图.png', 0, '2017-05-08 13:27:54', '2004-10-21 11:05:16');
INSERT INTO `goods` VALUES (501, 43, 9, '撮箕', 1345.85, 188, 0.22, 'The first step is as good as half over. Navicat 15 has added support for the system-wide dark mode. ', '默认商品图.png', 0, '2000-12-21 19:22:00', '2007-07-21 08:59:39');
INSERT INTO `goods` VALUES (503, 65, 6, '电脑', 335.15, 1745, 0.95, 'Anyone who has never made a mistake has never tried anything new. Difficult circumstances           ', '默认商品图.png', 1, '2022-11-20 11:38:03', '2008-09-17 18:51:05');
INSERT INTO `goods` VALUES (511, 54, 8, '锅', 54.35, 6329, 0.54, 'Difficult circumstances serve as a textbook of life for people. I will greet this                   ', '默认商品图.png', 0, '2022-08-19 05:14:02', '2022-07-05 00:09:05');
INSERT INTO `goods` VALUES (512, 43, 7, '陶瓷碗', 1918.33, 6277, 0.91, 'A man’s best friends are his ten fingers. The Navigation pane employs tree structure              ', '默认商品图.png', 1, '2011-11-09 18:33:39', '2011-12-05 02:58:00');
INSERT INTO `goods` VALUES (513, 50, 2, '扫把', 846.6, 4610, 0.83, 'Navicat Monitor can be installed on any local computer or virtual machine and does                  ', '默认商品图.png', 1, '2004-08-12 20:55:28', '2001-06-25 02:00:47');
INSERT INTO `goods` VALUES (517, 44, 1, '橙子', 1297.26, 2696, 0.81, 'Remember that failure is an event, not a person. It collects process metrics such                   ', '默认商品图.png', 0, '2019-12-09 08:18:15', '2008-06-03 04:09:58');
INSERT INTO `goods` VALUES (519, 52, 3, '衣服', 690.29, 4151, 0.02, 'A man is not old until regrets take the place of dreams. Navicat Monitor is a safe,                 ', '默认商品图.png', 1, '2004-02-18 14:47:54', '2011-10-14 04:51:30');
INSERT INTO `goods` VALUES (525, 60, 5, '手提包', 420.7, 3511, 0.66, 'Anyone who has never made a mistake has never tried anything new. Export Wizard allows              ', '默认商品图.png', 0, '2019-07-28 10:47:37', '2008-02-03 05:24:40');
INSERT INTO `goods` VALUES (528, 53, 2, '青霉素', 1852.13, 3391, 0.42, 'In the middle of winter I at last discovered that there was in me an invincible summer.             ', '默认商品图.png', 1, '2021-02-09 02:41:04', '2020-12-20 16:17:18');
INSERT INTO `goods` VALUES (531, 54, 7, '手机', 779.76, 7376, 0.24, 'I destroy my enemies when I make them my friends. The repository database can be                    ', '默认商品图.png', 0, '2022-08-08 18:19:56', '2014-08-16 03:06:50');
INSERT INTO `goods` VALUES (532, 48, 4, '橙子', 93.1, 6705, 0.21, 'Genius is an infinite capacity for taking pains. Typically, it is employed as an                    ', '默认商品图.png', 0, '2000-07-06 02:26:56', '2014-01-28 03:22:37');
INSERT INTO `goods` VALUES (533, 64, 9, '裙子', 67.29, 8548, 0.13, 'You will succeed because most people are lazy. Monitored servers include MySQL, MariaDB             ', '默认商品图.png', 1, '2017-11-11 02:10:48', '2019-07-28 11:49:47');
INSERT INTO `goods` VALUES (534, 37, 5, '雨衣', 808.44, 1352, 0.62, 'Creativity is intelligence having fun. HTTP Tunneling is a method for connecting                    ', '默认商品图.png', 1, '2013-03-01 23:50:57', '2018-04-08 07:48:47');
INSERT INTO `goods` VALUES (535, 31, 2, '撮箕', 538.39, 2809, 0.44, 'There is no way to happiness. Happiness is the way. Navicat Monitor requires a repository           ', '默认商品图.png', 1, '2003-06-24 20:04:26', '2014-11-07 06:29:26');
INSERT INTO `goods` VALUES (536, 47, 5, '手机', 1700.99, 8435, 0.91, 'Difficult circumstances serve as a textbook of life for people. In the Objects tab,                 ', '默认商品图.png', 1, '2009-04-30 04:01:58', '2012-11-12 05:47:49');
INSERT INTO `goods` VALUES (537, 5, 4, '999感冒灵', 1850.88, 8211, 0.23, 'The Navigation pane employs tree structure which allows you to take action upon the                 ', '默认商品图.png', 1, '2009-01-29 18:10:48', '2002-04-17 09:28:40');
INSERT INTO `goods` VALUES (538, 5, 10, '数据线', 960.68, 8043, 0.49, 'SSH serves to prevent such vulnerabilities and allows you to access a remote server\'s               ', '默认商品图.png', 0, '2021-06-05 13:42:09', '2007-05-19 23:18:55');
INSERT INTO `goods` VALUES (541, 66, 6, '梨', 218.21, 4718, 0.87, 'The On Startup feature allows you to control what tabs appear when you launch Navicat.              ', '默认商品图.png', 1, '2023-12-04 14:10:04', '2000-05-26 08:29:56');
INSERT INTO `goods` VALUES (543, 53, 7, '青霉素', 1421.53, 1698, 0.46, 'You must be the change you wish to see in the world. Navicat 15 has added support                   ', '默认商品图.png', 0, '2006-04-19 19:56:22', '2021-02-25 02:18:46');
INSERT INTO `goods` VALUES (546, 49, 2, '手提包', 1614.5, 5054, 0.96, 'Navicat Cloud could not connect and access your databases. By which it means, it                    ', '默认商品图.png', 0, '2000-11-17 04:26:12', '2014-11-22 18:03:27');
INSERT INTO `goods` VALUES (547, 44, 1, '锅', 1112.65, 6958, 0.79, 'You must be the change you wish to see in the world. Navicat Monitor requires a repository          ', '默认商品图.png', 0, '2006-12-31 16:51:36', '2018-12-10 19:42:55');
INSERT INTO `goods` VALUES (550, 16, 9, '扫把', 998.38, 7836, 0.34, 'Import Wizard allows you to import data to tables/collections from CSV, TXT, XML, DBF and more.     ', '默认商品图.png', 0, '2017-03-07 03:50:18', '2013-05-17 05:34:36');
INSERT INTO `goods` VALUES (552, 47, 5, '撮箕', 124.87, 9914, 0.92, 'It wasn’t raining when Noah built the ark. To start working with your server in                   ', '默认商品图.png', 1, '2001-10-29 05:26:45', '2016-08-06 22:01:47');
INSERT INTO `goods` VALUES (554, 55, 7, '撮箕', 296.35, 6802, 0.68, 'If the plan doesn’t work, change the plan, but never the goal. I may not have gone                ', '默认商品图.png', 1, '2008-04-08 02:20:55', '2019-05-25 21:41:10');
INSERT INTO `goods` VALUES (555, 57, 3, '西瓜', 452.38, 7423, 0.78, 'If you wait, all that happens is you get older. Navicat Data Modeler enables you                    ', '默认商品图.png', 0, '2000-12-21 09:39:56', '2022-07-20 11:37:55');
INSERT INTO `goods` VALUES (556, 63, 7, '青霉素', 1085.89, 6548, 0.68, 'A comfort zone is a beautiful place, but nothing ever grows there. A man’s best                   ', '默认商品图.png', 1, '2019-06-24 05:50:11', '2009-10-26 01:16:59');
INSERT INTO `goods` VALUES (557, 34, 10, '雨鞋', 635.06, 6184, 0.73, 'The Synchronize to Database function will give you a full picture of all database differences.      ', '默认商品图.png', 1, '2001-02-17 15:55:02', '2021-02-11 20:21:03');
INSERT INTO `goods` VALUES (558, 43, 9, '橙子', 1836.67, 1953, 0.05, 'Success consists of going from failure to failure without loss of enthusiasm.                       ', '默认商品图.png', 0, '2010-07-01 06:34:15', '2017-01-08 07:28:41');
INSERT INTO `goods` VALUES (560, 52, 2, '手机', 675.83, 4976, 0.16, 'The On Startup feature allows you to control what tabs appear when you launch Navicat.              ', '默认商品图.png', 0, '2011-05-25 01:24:44', '2012-04-10 17:06:29');
INSERT INTO `goods` VALUES (563, 33, 5, '梨', 1961.01, 3724, 0.37, 'A man’s best friends are his ten fingers. There is no way to happiness. Happiness is the way.     ', '默认商品图.png', 0, '2014-03-26 04:50:06', '2000-05-16 01:12:31');
INSERT INTO `goods` VALUES (567, 65, 4, '阿莫西尼', 1922.98, 1633, 0.72, 'Navicat authorizes you to make connection to remote servers running on different                    ', '默认商品图.png', 1, '2011-09-07 12:37:19', '2008-11-09 21:54:41');
INSERT INTO `goods` VALUES (571, 50, 4, '撮箕', 1481.07, 1363, 0.45, 'The On Startup feature allows you to control what tabs appear when you launch Navicat.              ', '默认商品图.png', 0, '2013-08-02 02:27:19', '2021-06-28 01:26:18');
INSERT INTO `goods` VALUES (572, 55, 10, '铁盆', 1901.48, 8701, 0.45, 'Anyone who has never made a mistake has never tried anything new. Navicat Cloud provides            ', '默认商品图.png', 1, '2017-05-07 18:45:28', '2009-05-13 17:24:54');
INSERT INTO `goods` VALUES (574, 61, 3, '书包', 387.73, 3430, 0.29, 'Navicat allows you to transfer data from one database and/or schema to another with                 ', '默认商品图.png', 0, '2002-12-13 10:11:00', '2008-08-24 12:41:30');
INSERT INTO `goods` VALUES (575, 59, 9, '梨', 1741.96, 8802, 0.59, 'If opportunity doesn’t knock, build a door. A query is used to extract data from                  ', '默认商品图.png', 1, '2013-11-05 15:19:19', '2007-08-18 04:15:27');
INSERT INTO `goods` VALUES (577, 55, 4, '阿莫西尼', 19.92, 6962, 0.04, 'To start working with your server in Navicat, you should first establish a connection               ', '默认商品图.png', 0, '2019-05-23 19:54:41', '2021-05-10 16:11:49');
INSERT INTO `goods` VALUES (578, 66, 6, '毛巾', 192.12, 8478, 0.93, 'Genius is an infinite capacity for taking pains. You must be the change you wish                    ', '默认商品图.png', 0, '2013-09-26 06:33:02', '2015-05-18 17:35:43');
INSERT INTO `goods` VALUES (579, 64, 5, '雨衣', 219.34, 1604, 0.5, 'I may not have gone where I intended to go, but I think I have ended up where I needed to be.       ', '默认商品图.png', 0, '2012-01-09 17:38:11', '2014-01-16 03:47:24');
INSERT INTO `goods` VALUES (580, 45, 7, '铁盆', 776.81, 7796, 0.46, 'Success consists of going from failure to failure without loss of enthusiasm.                       ', '默认商品图.png', 1, '2019-09-10 11:55:48', '2002-11-23 00:16:16');
INSERT INTO `goods` VALUES (581, 35, 5, '垃圾袋', 466.39, 9263, 0.16, 'If opportunity doesn’t knock, build a door. In other words, Navicat provides the                  ', '默认商品图.png', 1, '2014-06-25 20:21:55', '2000-01-30 08:09:18');
INSERT INTO `goods` VALUES (582, 31, 5, '键盘', 1465.32, 2995, 0.06, 'HTTP Tunneling is a method for connecting to a server that uses the same protocol                   ', '默认商品图.png', 0, '2005-03-31 17:57:30', '2020-10-15 22:40:16');
INSERT INTO `goods` VALUES (583, 61, 4, '背包', 972.69, 8988, 0.5, 'Typically, it is employed as an encrypted version of Telnet. Sometimes you win, sometimes you learn.', '默认商品图.png', 1, '2022-06-06 00:27:59', '2015-04-17 13:50:07');
INSERT INTO `goods` VALUES (585, 60, 7, '雨衣', 371.81, 803, 0.55, 'I destroy my enemies when I make them my friends. Secure Sockets Layer(SSL) is a                    ', '默认商品图.png', 0, '2007-06-18 19:03:41', '2019-02-04 03:38:34');
INSERT INTO `goods` VALUES (588, 1, 5, '铁盆', 903.97, 2128, 0.19, 'Anyone who has ever made anything of importance was disciplined. To clear or reload                 ', '默认商品图.png', 1, '2017-09-13 13:53:56', '2005-04-16 19:20:10');
INSERT INTO `goods` VALUES (589, 59, 5, '充电宝', 941.93, 1337, 0.75, 'After logged in the Navicat Cloud feature, the Navigation pane will be divided into                 ', '默认商品图.png', 1, '2018-05-04 10:24:58', '2018-09-15 06:58:11');
INSERT INTO `goods` VALUES (590, 16, 7, '999感冒灵', 655.17, 571, 0.49, 'Sometimes you win, sometimes you learn. To open a query using an external editor,                   ', '默认商品图.png', 1, '2008-04-22 14:05:06', '2017-01-20 21:01:24');
INSERT INTO `goods` VALUES (591, 44, 4, '垃圾袋', 1706.43, 3522, 0.47, 'Navicat 15 has added support for the system-wide dark mode. Remember that failure                   ', '默认商品图.png', 1, '2008-10-30 17:40:53', '2013-06-24 16:24:32');
INSERT INTO `goods` VALUES (592, 45, 6, '苹果', 798.78, 707, 0.45, 'It wasn’t raining when Noah built the ark. Always keep your eyes open. Keep watching.             ', '默认商品图.png', 0, '2021-08-06 02:35:24', '2018-03-29 05:58:42');
INSERT INTO `goods` VALUES (597, 44, 5, '手提包', 1938.19, 9169, 0.46, 'After logged in the Navicat Cloud feature, the Navigation pane will be divided into                 ', '默认商品图.png', 1, '2005-07-17 00:57:28', '2013-12-28 07:58:34');
INSERT INTO `goods` VALUES (600, 49, 4, '钱包', 344.95, 3035, 0.71, 'A query is used to extract data from the database in a readable format according                    ', '默认商品图.png', 0, '2015-05-03 23:49:12', '2011-08-15 12:35:44');
INSERT INTO `goods` VALUES (607, 58, 7, '扫把', 1657.62, 5922, 0.19, 'In the Objects tab, you can use the List List, Detail Detail and ER Diagram ER Diagram              ', '默认商品图.png', 0, '2009-04-14 21:59:51', '2016-10-27 22:55:42');
INSERT INTO `goods` VALUES (608, 55, 9, '鞋子', 875.04, 5952, 0.65, 'HTTP Tunneling is a method for connecting to a server that uses the same protocol                   ', '默认商品图.png', 1, '2011-10-03 18:50:52', '2019-06-24 14:37:21');
INSERT INTO `goods` VALUES (609, 61, 6, '橙子', 991.54, 3654, 0.54, 'The reason why a great man is great is that he resolves to be a great man.                          ', '默认商品图.png', 1, '2004-12-19 02:52:55', '2003-02-27 01:50:48');
INSERT INTO `goods` VALUES (613, 40, 8, '橙子', 267.92, 9365, 0.21, 'The Main Window consists of several toolbars and panes for you to work on connections,              ', '默认商品图.png', 0, '2019-03-31 22:57:07', '2003-09-20 05:39:03');
INSERT INTO `goods` VALUES (614, 62, 7, '背包', 148.09, 8339, 0.39, 'In the middle of winter I at last discovered that there was in me an invincible summer.             ', '默认商品图.png', 0, '2018-08-27 04:24:34', '2008-10-15 07:40:38');
INSERT INTO `goods` VALUES (616, 54, 5, '垃圾袋', 292.78, 2591, 0.2, 'Instead of wondering when your next vacation is, maybe you should set up a life you                 ', '默认商品图.png', 0, '2019-06-19 15:56:52', '2013-12-20 18:37:53');
INSERT INTO `goods` VALUES (617, 56, 5, '999感冒灵', 856.71, 4457, 0.59, 'Secure SHell (SSH) is a program to log in into another computer over a network, execute             ', '默认商品图.png', 0, '2016-06-28 23:11:17', '2002-06-27 04:50:09');
INSERT INTO `goods` VALUES (618, 61, 6, '999感冒灵', 336.55, 3531, 0.3, 'A man is not old until regrets take the place of dreams. To successfully establish                  ', '默认商品图.png', 0, '2017-06-16 17:27:21', '2002-01-09 03:17:04');
INSERT INTO `goods` VALUES (619, 43, 9, '陶瓷碗', 469.44, 5937, 0.2, 'I may not have gone where I intended to go, but I think I have ended up where I needed to be.       ', '默认商品图.png', 0, '2019-08-28 05:53:54', '2020-12-04 23:48:29');
INSERT INTO `goods` VALUES (629, 54, 7, '苹果', 1478.62, 5832, 0.18, 'You will succeed because most people are lazy. I will greet this day with love in my heart.         ', '默认商品图.png', 0, '2002-05-16 00:32:17', '2000-02-27 04:32:59');
INSERT INTO `goods` VALUES (630, 52, 9, '999感冒灵', 996.58, 154, 0.96, 'After comparing data, the window shows the number of records that will be inserted,                 ', '默认商品图.png', 0, '2007-07-27 03:00:25', '2016-09-13 13:32:03');
INSERT INTO `goods` VALUES (631, 42, 2, '垃圾袋', 329.01, 1158, 0.22, 'What you get by achieving your goals is not as important as what you become by achieving your goals.', '默认商品图.png', 1, '2012-03-09 22:49:13', '2010-06-11 14:23:55');
INSERT INTO `goods` VALUES (634, 49, 8, '衣服', 1829.87, 4756, 0.17, 'Navicat authorizes you to make connection to remote servers running on different                    ', '默认商品图.png', 0, '2010-06-19 03:45:05', '2021-07-31 12:49:50');
INSERT INTO `goods` VALUES (636, 61, 4, '草莓', 125.33, 1408, 0.69, 'In the middle of winter I at last discovered that there was in me an invincible summer.             ', '默认商品图.png', 0, '2024-04-01 03:18:50', '2000-05-25 21:03:53');
INSERT INTO `goods` VALUES (638, 31, 6, '拖把', 722.38, 6144, 0.86, 'Sometimes you win, sometimes you learn. You will succeed because most people are lazy.              ', '默认商品图.png', 1, '2017-11-12 21:55:02', '2022-07-07 00:44:17');
INSERT INTO `goods` VALUES (640, 18, 8, '橙子', 1824.12, 8237, 0.93, 'Monitored servers include MySQL, MariaDB and SQL Server, and compatible with cloud                  ', '默认商品图.png', 0, '2003-10-12 23:00:35', '2012-09-05 11:09:04');
INSERT INTO `goods` VALUES (647, 52, 5, '手机', 552.82, 4533, 0.53, 'After comparing data, the window shows the number of records that will be inserted,                 ', '默认商品图.png', 1, '2015-05-22 00:43:19', '2015-12-04 08:43:11');
INSERT INTO `goods` VALUES (648, 61, 7, '锅', 173.54, 9196, 0.87, 'To connect to a database or schema, simply double-click it in the pane.                             ', '默认商品图.png', 0, '2012-12-11 16:49:10', '2007-06-18 22:57:52');
INSERT INTO `goods` VALUES (649, 36, 5, '鞋子', 688.56, 1703, 0.85, 'To open a query using an external editor, control-click it and select Open with External            ', '默认商品图.png', 0, '2019-03-02 19:07:44', '2001-06-10 01:28:34');
INSERT INTO `goods` VALUES (651, 18, 6, '垃圾桶', 699.08, 9598, 0.12, 'Navicat Data Modeler enables you to build high-quality conceptual, logical and physical             ', '默认商品图.png', 1, '2016-08-01 09:40:31', '2023-12-06 13:27:52');
INSERT INTO `goods` VALUES (652, 54, 4, '保鲜膜', 152.55, 4182, 0.97, 'The first step is as good as half over. The On Startup feature allows you to control                ', '默认商品图.png', 0, '2004-10-17 16:11:19', '2016-08-11 01:05:53');
INSERT INTO `goods` VALUES (654, 52, 4, '垃圾袋', 1291.65, 7172, 0.88, 'You cannot save people, you can just love them. You must be the change you wish to see in the world.', '默认商品图.png', 0, '2010-01-07 03:00:32', '2004-02-21 00:01:00');
INSERT INTO `goods` VALUES (660, 5, 9, '青霉素', 1497.89, 5185, 0.22, 'Actually it is just in an idea when feel oneself can achieve and cannot achieve.                    ', '默认商品图.png', 0, '2012-10-02 12:05:19', '2006-12-14 00:26:30');
INSERT INTO `goods` VALUES (661, 59, 4, '键盘', 563.4, 5874, 0.55, 'Navicat Data Modeler is a powerful and cost-effective database design tool which                    ', '默认商品图.png', 0, '2013-10-13 19:18:54', '2019-01-31 11:19:32');
INSERT INTO `goods` VALUES (662, 59, 10, '撮箕', 510.1, 7114, 0.85, 'Always keep your eyes open. Keep watching. Because whatever you see can inspire you.                ', '默认商品图.png', 0, '2021-07-04 07:23:23', '2000-11-06 11:35:42');
INSERT INTO `goods` VALUES (668, 64, 3, '充电宝', 111.85, 8704, 0.28, 'If opportunity doesn’t knock, build a door. Navicat 15 has added support for the                  ', '默认商品图.png', 0, '2005-04-11 18:53:19', '2007-08-18 08:30:00');
INSERT INTO `goods` VALUES (669, 51, 1, '裙子', 1903.77, 3603, 0.83, 'Navicat provides powerful tools for working with queries: Query Editor for editing                  ', '默认商品图.png', 0, '2020-08-02 20:03:51', '2000-06-15 17:28:49');
INSERT INTO `goods` VALUES (670, 48, 7, '衣服', 1659.68, 13, 0.43, 'Secure SHell (SSH) is a program to log in into another computer over a network, execute             ', '默认商品图.png', 1, '2016-07-28 06:30:29', '2003-09-11 00:33:32');
INSERT INTO `goods` VALUES (674, 38, 1, '拖把', 390.52, 5170, 0.14, 'Import Wizard allows you to import data to tables/collections from CSV, TXT, XML, DBF and more.     ', '默认商品图.png', 0, '2000-07-23 08:57:32', '2017-05-27 02:43:29');
INSERT INTO `goods` VALUES (675, 16, 9, '橙子', 1075.72, 8119, 0.87, 'To clear or reload various internal caches, flush tables, or acquire locks, control-click           ', '默认商品图.png', 1, '2015-03-22 06:58:22', '2021-12-04 05:33:59');
INSERT INTO `goods` VALUES (677, 60, 4, '梨', 1134.45, 1353, 0.61, 'If you wait, all that happens is you get older. In a Telnet session, all communications,            ', '默认商品图.png', 1, '2000-03-21 21:11:38', '2000-10-25 21:25:10');
INSERT INTO `goods` VALUES (678, 40, 9, '手机', 754.22, 6124, 0.53, 'A query is used to extract data from the database in a readable format according                    ', '默认商品图.png', 0, '2006-01-28 15:49:34', '2001-07-27 01:18:13');
INSERT INTO `goods` VALUES (685, 34, 9, '扫把', 942.91, 893, 0.96, 'Navicat authorizes you to make connection to remote servers running on different                    ', '默认商品图.png', 1, '2004-10-14 13:58:55', '2008-12-13 19:37:14');
INSERT INTO `goods` VALUES (688, 51, 6, '裤子', 492.6, 5152, 0.77, 'I may not have gone where I intended to go, but I think I have ended up where I needed to be.       ', '默认商品图.png', 1, '2015-12-04 00:10:05', '2023-02-02 08:04:25');
INSERT INTO `goods` VALUES (689, 51, 10, '鞋子', 372.24, 385, 0.7, 'Anyone who has ever made anything of importance was disciplined. A comfort zone is                  ', '默认商品图.png', 1, '2005-06-23 17:28:36', '2004-09-27 18:20:23');
INSERT INTO `goods` VALUES (691, 65, 2, '撮箕', 840.17, 2065, 0.18, 'Such sessions are also susceptible to session hijacking, where a malicious user takes               ', '默认商品图.png', 1, '2021-04-09 12:13:26', '2004-01-24 07:41:25');
INSERT INTO `goods` VALUES (694, 57, 8, '毛巾', 1575.33, 2486, 0.13, 'With its well-designed Graphical User Interface(GUI), Navicat lets you quickly and                  ', '默认商品图.png', 1, '2017-02-04 21:38:06', '2014-07-18 11:46:02');
INSERT INTO `goods` VALUES (696, 32, 10, '音响', 520.51, 4827, 0.51, 'Navicat Cloud could not connect and access your databases. By which it means, it                    ', '默认商品图.png', 0, '2001-01-26 11:11:33', '2002-09-02 02:26:28');
INSERT INTO `goods` VALUES (697, 65, 3, '西瓜', 91.78, 9442, 0.72, 'If you wait, all that happens is you get older. The Main Window consists of several                 ', '默认商品图.png', 0, '2007-02-03 08:17:19', '2013-05-06 18:27:33');
INSERT INTO `goods` VALUES (701, 66, 8, '鞋子', 264.48, 1437, 0.48, 'In the middle of winter I at last discovered that there was in me an invincible summer.             ', '默认商品图.png', 0, '2019-02-23 21:21:31', '2002-03-16 17:22:12');
INSERT INTO `goods` VALUES (702, 35, 2, '电脑', 1795.68, 1854, 0.01, 'Navicat Monitor is a safe, simple and agentless remote server monitoring tool that                  ', '默认商品图.png', 1, '2020-11-08 23:20:44', '2012-01-31 16:35:34');
INSERT INTO `goods` VALUES (703, 63, 9, '橙子', 1965.92, 4428, 0.92, 'Navicat is a multi-connections Database Administration tool allowing you to connect                 ', '默认商品图.png', 1, '2023-10-14 15:10:55', '2022-10-15 18:46:04');
INSERT INTO `goods` VALUES (705, 33, 9, '书包', 869.46, 1356, 0.36, 'A man’s best friends are his ten fingers. Typically, it is employed as an encrypted               ', '默认商品图.png', 1, '2023-11-11 21:55:39', '2003-08-24 08:26:29');
INSERT INTO `goods` VALUES (706, 58, 4, '青霉素', 938.41, 851, 0.13, 'Anyone who has never made a mistake has never tried anything new. The Navigation                    ', '默认商品图.png', 1, '2019-05-31 03:25:28', '2022-07-10 03:43:27');
INSERT INTO `goods` VALUES (714, 31, 3, '摄像机', 1675.64, 9085, 0.2, 'Navicat Cloud provides a cloud service for synchronizing connections, queries, model                ', '默认商品图.png', 1, '2022-01-11 14:26:18', '2009-08-21 09:23:38');
INSERT INTO `goods` VALUES (716, 45, 6, '拖把', 1626.75, 3146, 0.64, 'Success consists of going from failure to failure without loss of enthusiasm.                       ', '默认商品图.png', 0, '2002-03-29 09:16:17', '2023-11-30 10:30:44');
INSERT INTO `goods` VALUES (718, 34, 6, '钱包', 427.99, 5462, 0.3, 'You will succeed because most people are lazy. Always keep your eyes open. Keep watching.           ', '默认商品图.png', 0, '2016-07-04 17:35:47', '2006-06-14 02:15:45');
INSERT INTO `goods` VALUES (719, 37, 9, '音响', 794.93, 6867, 0.91, 'It is used while your ISPs do not allow direct connections, but allows establishing                 ', '默认商品图.png', 0, '2000-06-05 00:59:34', '2005-06-26 15:09:01');
INSERT INTO `goods` VALUES (722, 32, 6, '充电宝', 1663.85, 8002, 0.25, 'The On Startup feature allows you to control what tabs appear when you launch Navicat.              ', '默认商品图.png', 1, '2018-11-03 05:59:14', '2023-06-25 16:00:51');
INSERT INTO `goods` VALUES (725, 54, 7, '手机', 1001.93, 5543, 0.21, 'Export Wizard allows you to export data from tables, collections, views, or query                   ', '默认商品图.png', 1, '2013-02-20 09:26:42', '2011-08-07 05:32:27');
INSERT INTO `goods` VALUES (726, 36, 5, '雨鞋', 795.97, 5485, 0.27, 'Navicat Cloud provides a cloud service for synchronizing connections, queries, model                ', '默认商品图.png', 1, '2021-04-28 09:00:36', '2004-03-01 17:41:34');
INSERT INTO `goods` VALUES (727, 51, 6, '西瓜', 1741.3, 9981, 0.66, 'Optimism is the one quality more associated with success and happiness than any other.              ', '默认商品图.png', 1, '2003-08-31 15:38:26', '2000-07-21 13:02:38');
INSERT INTO `goods` VALUES (730, 52, 3, '雨鞋', 492.95, 6272, 0.2, 'Monitored servers include MySQL, MariaDB and SQL Server, and compatible with cloud                  ', '默认商品图.png', 1, '2004-12-30 10:30:51', '2021-04-03 17:19:28');
INSERT INTO `goods` VALUES (731, 41, 6, '拖把', 1066.62, 4076, 0.3, 'To connect to a database or schema, simply double-click it in the pane.                             ', '默认商品图.png', 1, '2003-12-22 05:17:55', '2022-10-18 17:42:38');
INSERT INTO `goods` VALUES (732, 31, 7, '背包', 961.13, 7441, 0.23, 'Flexible settings enable you to set up a custom key for comparison and synchronization.             ', '默认商品图.png', 0, '2021-06-17 05:33:58', '2010-02-07 10:44:02');
INSERT INTO `goods` VALUES (733, 37, 5, '扫把', 1656.43, 2140, 0.42, 'I may not have gone where I intended to go, but I think I have ended up where I needed to be.       ', '默认商品图.png', 1, '2007-11-04 01:32:37', '2002-08-20 12:45:24');
INSERT INTO `goods` VALUES (741, 63, 3, '毛巾', 356.14, 6220, 0.91, 'I may not have gone where I intended to go, but I think I have ended up where I needed to be.       ', '默认商品图.png', 0, '2002-05-16 14:08:15', '2005-03-26 10:55:23');
INSERT INTO `goods` VALUES (743, 56, 4, '锅', 1165.58, 2609, 0.25, 'The Information Pane shows the detailed object information, project activities, the                 ', '默认商品图.png', 1, '2005-06-09 01:38:51', '2000-05-02 11:58:11');
INSERT INTO `goods` VALUES (744, 44, 3, '钱包', 1179.33, 2369, 0.75, 'Export Wizard allows you to export data from tables, collections, views, or query                   ', '默认商品图.png', 0, '2007-04-14 06:16:44', '2000-02-20 13:17:28');
INSERT INTO `goods` VALUES (745, 38, 6, '橙子', 1053.12, 6721, 0.67, 'Optimism is the one quality more associated with success and happiness than any other.              ', '默认商品图.png', 0, '2010-01-20 10:07:59', '2000-11-20 13:26:23');
INSERT INTO `goods` VALUES (748, 41, 5, '999感冒灵', 927.57, 8345, 0.69, 'Navicat Monitor requires a repository to store alerts and metrics for historical analysis.          ', '默认商品图.png', 1, '2007-09-03 00:50:14', '2013-10-11 23:42:20');
INSERT INTO `goods` VALUES (750, 62, 7, '充电宝', 1603.24, 5533, 0.59, 'To clear or reload various internal caches, flush tables, or acquire locks, control-click           ', '默认商品图.png', 1, '2006-12-28 16:59:53', '2006-05-11 04:37:09');
INSERT INTO `goods` VALUES (755, 53, 8, '钱包', 258.27, 281, 0.86, 'Difficult circumstances serve as a textbook of life for people. Genius is an infinite               ', '默认商品图.png', 0, '2021-12-26 23:47:30', '2021-09-04 17:34:25');
INSERT INTO `goods` VALUES (760, 58, 6, '锅', 196.25, 3921, 0.41, 'In the Objects tab, you can use the List List, Detail Detail and ER Diagram ER Diagram              ', '默认商品图.png', 0, '2022-05-24 10:19:21', '2002-10-30 11:13:47');
INSERT INTO `goods` VALUES (764, 54, 1, '撮箕', 275.48, 741, 0.27, 'If you wait, all that happens is you get older. To get a secure connection, the first               ', '默认商品图.png', 1, '2010-11-13 01:47:32', '2023-12-27 17:50:10');
INSERT INTO `goods` VALUES (767, 61, 8, '青霉素', 707.83, 8719, 0.87, 'It provides strong authentication and secure encrypted communications between two                   ', '默认商品图.png', 1, '2012-09-29 09:44:45', '2013-03-11 04:24:18');
INSERT INTO `goods` VALUES (769, 59, 6, '保鲜膜', 824.8, 6356, 0.45, 'Navicat authorizes you to make connection to remote servers running on different                    ', '默认商品图.png', 1, '2007-08-16 04:02:13', '2000-05-15 22:28:02');
INSERT INTO `goods` VALUES (770, 45, 2, '毛巾', 635.46, 1081, 0.2, 'In the Objects tab, you can use the List List, Detail Detail and ER Diagram ER Diagram              ', '默认商品图.png', 0, '2016-12-15 17:05:19', '2000-01-03 22:45:23');
INSERT INTO `goods` VALUES (776, 38, 6, '扫把', 1766.79, 5471, 0.21, 'If you wait, all that happens is you get older. Navicat Monitor is a safe, simple                   ', '默认商品图.png', 0, '2006-06-04 07:58:03', '2022-11-24 14:24:54');
INSERT INTO `goods` VALUES (779, 42, 6, '袜子', 698.31, 6613, 0.32, 'Such sessions are also susceptible to session hijacking, where a malicious user takes               ', '默认商品图.png', 0, '2006-09-07 16:04:40', '2014-12-05 12:32:25');
INSERT INTO `goods` VALUES (780, 56, 1, '书包', 422.13, 9373, 0.07, 'What you get by achieving your goals is not as important as what you become by achieving your goals.', '默认商品图.png', 0, '2016-12-13 11:23:39', '2001-02-13 17:53:06');
INSERT INTO `goods` VALUES (781, 45, 1, '西瓜', 667.93, 575, 0.49, 'A comfort zone is a beautiful place, but nothing ever grows there. SSH serves to                    ', '默认商品图.png', 0, '2011-02-23 15:07:19', '2001-05-15 07:34:00');
INSERT INTO `goods` VALUES (784, 53, 8, '保鲜膜', 937.5, 3157, 0.76, 'Secure Sockets Layer(SSL) is a protocol for transmitting private documents via the Internet.        ', '默认商品图.png', 1, '2024-01-28 03:19:50', '2007-03-13 16:59:26');
INSERT INTO `goods` VALUES (785, 60, 3, '西瓜', 689.87, 5505, 0.37, 'To open a query using an external editor, control-click it and select Open with External            ', '默认商品图.png', 1, '2012-09-02 17:41:03', '2020-01-09 22:20:20');
INSERT INTO `goods` VALUES (787, 50, 4, '扫把', 81.77, 6211, 0.89, 'With its well-designed Graphical User Interface(GUI), Navicat lets you quickly and                  ', '默认商品图.png', 0, '2013-05-20 03:05:37', '2011-06-02 15:44:55');
INSERT INTO `goods` VALUES (788, 64, 7, '陶瓷碗', 1231.46, 5027, 0.88, 'If your Internet Service Provider (ISP) does not provide direct access to its server,               ', '默认商品图.png', 1, '2011-02-01 10:25:32', '2004-02-27 14:05:57');
INSERT INTO `goods` VALUES (790, 34, 1, '橘子', 899.63, 7511, 0.27, 'Navicat Cloud could not connect and access your databases. By which it means, it                    ', '默认商品图.png', 1, '2016-07-22 02:22:07', '2022-09-06 18:48:01');
INSERT INTO `goods` VALUES (792, 56, 8, '音响', 1370.59, 3274, 0.36, 'Import Wizard allows you to import data to tables/collections from CSV, TXT, XML, DBF and more.     ', '默认商品图.png', 1, '2022-12-07 21:48:03', '2011-09-29 09:22:44');
INSERT INTO `goods` VALUES (794, 66, 1, '撮箕', 166.5, 443, 0.79, 'After comparing data, the window shows the number of records that will be inserted,                 ', '默认商品图.png', 1, '2013-04-17 23:45:02', '2012-09-24 15:25:01');
INSERT INTO `goods` VALUES (795, 43, 5, '音响', 1792.56, 2596, 0.75, 'If you wait, all that happens is you get older. Navicat authorizes you to make connection           ', '默认商品图.png', 0, '2008-12-03 17:27:48', '2022-01-16 00:25:42');
INSERT INTO `goods` VALUES (797, 41, 2, '999感冒灵', 898.73, 9321, 0.8, 'It is used while your ISPs do not allow direct connections, but allows establishing                 ', '默认商品图.png', 1, '2011-03-11 15:53:59', '2001-11-04 05:32:23');
INSERT INTO `goods` VALUES (804, 37, 6, '橘子', 476.78, 1914, 0.9, 'Difficult circumstances serve as a textbook of life for people. You will succeed                    ', '默认商品图.png', 1, '2006-01-24 01:11:18', '2001-03-16 21:54:56');
INSERT INTO `goods` VALUES (806, 48, 5, '草莓', 58.66, 9011, 0.29, 'Typically, it is employed as an encrypted version of Telnet. Navicat Monitor is a                   ', '默认商品图.png', 1, '2005-11-02 23:51:24', '2014-03-19 17:03:54');
INSERT INTO `goods` VALUES (808, 16, 4, '数据线', 1263.02, 8515, 0.61, 'Success consists of going from failure to failure without loss of enthusiasm.                       ', '默认商品图.png', 1, '2008-01-12 11:23:13', '2011-12-03 17:41:24');
INSERT INTO `goods` VALUES (809, 46, 8, '毛巾', 149.36, 3498, 0.26, 'To clear or reload various internal caches, flush tables, or acquire locks, control-click           ', '默认商品图.png', 0, '2006-12-18 08:55:46', '2000-01-27 16:45:15');
INSERT INTO `goods` VALUES (813, 59, 7, '鞋子', 1234.65, 6447, 0.89, 'To open a query using an external editor, control-click it and select Open with External            ', '默认商品图.png', 0, '2008-08-17 02:36:33', '2005-07-21 03:45:48');
INSERT INTO `goods` VALUES (814, 5, 4, '撮箕', 121.05, 3722, 0.7, 'Difficult circumstances serve as a textbook of life for people. Navicat Cloud could                 ', '默认商品图.png', 0, '2013-08-28 16:26:14', '2007-07-23 22:25:23');
INSERT INTO `goods` VALUES (817, 53, 2, '雨衣', 1321.52, 7218, 0.81, 'Monitored servers include MySQL, MariaDB and SQL Server, and compatible with cloud                  ', '默认商品图.png', 0, '2023-03-16 07:24:21', '2005-07-27 07:39:28');
INSERT INTO `goods` VALUES (818, 50, 3, '雨衣', 1029.29, 968, 0.79, 'There is no way to happiness. Happiness is the way. Anyone who has never made a mistake             ', '默认商品图.png', 1, '2018-08-18 20:02:20', '2012-02-13 21:02:58');
INSERT INTO `goods` VALUES (821, 50, 8, '苹果', 1562.47, 3222, 0.95, 'Such sessions are also susceptible to session hijacking, where a malicious user takes               ', '默认商品图.png', 0, '2014-03-07 15:28:23', '2014-07-12 06:46:05');
INSERT INTO `goods` VALUES (822, 49, 8, '摄像机', 1561.73, 4896, 0.61, 'A comfort zone is a beautiful place, but nothing ever grows there. If it scares you,                ', '默认商品图.png', 1, '2001-09-02 18:55:35', '2003-11-06 11:04:47');
INSERT INTO `goods` VALUES (825, 61, 10, '锅', 1656.3, 1793, 0.92, 'Optimism is the one quality more associated with success and happiness than any other.              ', '默认商品图.png', 1, '2016-06-20 07:59:21', '2004-02-23 17:56:55');
INSERT INTO `goods` VALUES (831, 59, 4, '梨', 1334.75, 6528, 0.72, 'To clear or reload various internal caches, flush tables, or acquire locks, control-click           ', '默认商品图.png', 1, '2004-09-14 04:11:44', '2022-11-07 05:32:16');
INSERT INTO `goods` VALUES (832, 48, 2, '苹果', 1841.08, 7647, 0.25, 'The past has no power over the present moment. If opportunity doesn’t knock, build a door.        ', '默认商品图.png', 1, '2016-11-03 07:34:56', '2015-11-21 07:31:31');
INSERT INTO `goods` VALUES (834, 38, 4, '电脑', 1822.17, 6611, 0.18, 'The On Startup feature allows you to control what tabs appear when you launch Navicat.              ', '默认商品图.png', 1, '2023-09-02 10:10:28', '2002-01-21 04:04:20');
INSERT INTO `goods` VALUES (835, 1, 7, '裙子', 517.53, 7354, 0.78, 'In other words, Navicat provides the ability for data in different databases and/or                 ', '默认商品图.png', 0, '2004-02-24 08:46:24', '2008-10-29 13:58:29');
INSERT INTO `goods` VALUES (841, 37, 9, '钱包', 788.21, 2332, 0.72, 'Remember that failure is an event, not a person. How we spend our days is, of course,               ', '默认商品图.png', 0, '2012-08-27 12:19:31', '2012-01-19 17:35:43');
INSERT INTO `goods` VALUES (844, 52, 9, '摄像机', 181.61, 3263, 0.28, 'HTTP Tunneling is a method for connecting to a server that uses the same protocol                   ', '默认商品图.png', 0, '2015-06-07 22:51:11', '2002-12-12 14:45:35');
INSERT INTO `goods` VALUES (847, 32, 10, '垃圾袋', 1691.27, 4205, 0.17, 'In a Telnet session, all communications, including username and password, are transmitted           ', '默认商品图.png', 1, '2010-08-04 23:35:24', '2008-01-23 02:16:09');
INSERT INTO `goods` VALUES (849, 44, 3, '钱包', 1236.52, 2700, 0.27, 'Difficult circumstances serve as a textbook of life for people. It wasn’t raining                 ', '默认商品图.png', 1, '2012-06-11 10:55:25', '2000-06-28 12:47:07');
INSERT INTO `goods` VALUES (852, 43, 4, '草莓', 121.21, 6447, 0.27, 'Navicat Cloud could not connect and access your databases. By which it means, it                    ', '默认商品图.png', 1, '2011-12-29 06:00:55', '2010-10-28 03:23:00');
INSERT INTO `goods` VALUES (853, 37, 7, '垃圾桶', 803.35, 7019, 0.32, 'Anyone who has ever made anything of importance was disciplined. A comfort zone is                  ', '默认商品图.png', 0, '2015-10-31 23:08:17', '2003-02-11 10:29:16');
INSERT INTO `goods` VALUES (854, 1, 3, '撮箕', 1358.49, 2390, 0.18, 'Navicat provides a wide range advanced features, such as compelling code editing                    ', '默认商品图.png', 1, '2023-02-18 14:27:57', '2021-03-22 05:54:38');
INSERT INTO `goods` VALUES (860, 54, 1, '音响', 367.69, 4983, 0.48, 'Typically, it is employed as an encrypted version of Telnet. Navicat Monitor is a                   ', '默认商品图.png', 0, '2014-08-27 13:35:50', '2000-04-30 21:03:36');
INSERT INTO `goods` VALUES (863, 16, 5, '数据线', 417.67, 211, 0.61, 'Navicat provides a wide range advanced features, such as compelling code editing                    ', '默认商品图.png', 1, '2007-01-01 09:22:25', '2017-01-10 16:16:01');
INSERT INTO `goods` VALUES (865, 41, 4, '拖把', 212.64, 8791, 0.24, 'It can also manage cloud databases such as Amazon Redshift, Amazon RDS, Alibaba Cloud.              ', '默认商品图.png', 1, '2000-02-02 04:51:19', '2022-11-08 11:38:00');
INSERT INTO `goods` VALUES (867, 59, 5, '书包', 1488.94, 9969, 0.7, 'Secure SHell (SSH) is a program to log in into another computer over a network, execute             ', '默认商品图.png', 0, '2005-01-26 13:29:47', '2020-02-15 07:58:28');
INSERT INTO `goods` VALUES (868, 62, 7, '鞋子', 499, 408, 0.51, 'The first step is as good as half over. If your Internet Service Provider (ISP) does                ', '默认商品图.png', 0, '2021-11-02 12:02:36', '2022-07-21 07:15:06');
INSERT INTO `goods` VALUES (869, 53, 5, '摄像机', 1403.91, 2802, 0.35, 'To clear or reload various internal caches, flush tables, or acquire locks, control-click           ', '默认商品图.png', 1, '2008-08-22 04:32:17', '2006-10-21 20:20:18');
INSERT INTO `goods` VALUES (873, 41, 3, '保鲜膜', 1212.11, 6317, 0.66, 'The Main Window consists of several toolbars and panes for you to work on connections,              ', '默认商品图.png', 0, '2005-03-01 19:08:38', '2019-08-01 04:20:47');
INSERT INTO `goods` VALUES (874, 59, 2, '键盘', 1359.28, 1044, 0.05, 'It provides strong authentication and secure encrypted communications between two                   ', '默认商品图.png', 1, '2007-05-29 22:15:10', '2006-04-22 06:14:09');
INSERT INTO `goods` VALUES (875, 50, 1, '苹果', 1862.66, 3965, 0.62, 'Such sessions are also susceptible to session hijacking, where a malicious user takes               ', '默认商品图.png', 1, '2021-08-29 09:58:03', '2007-08-01 05:10:53');
INSERT INTO `goods` VALUES (877, 50, 4, '拖把', 842.97, 1028, 0.23, 'You cannot save people, you can just love them. What you get by achieving your goals                ', '默认商品图.png', 1, '2003-02-05 08:18:04', '2002-12-06 01:13:27');
INSERT INTO `goods` VALUES (878, 65, 7, '锅', 1748.38, 5935, 0.44, 'I destroy my enemies when I make them my friends. The Main Window consists of several               ', '默认商品图.png', 1, '2010-10-10 20:48:05', '2015-06-02 23:28:02');
INSERT INTO `goods` VALUES (879, 57, 5, '鞋子', 1335.74, 9311, 0.56, 'Flexible settings enable you to set up a custom key for comparison and synchronization.             ', '默认商品图.png', 1, '2003-06-12 18:34:11', '2020-04-14 18:13:39');
INSERT INTO `goods` VALUES (880, 18, 7, '充电宝', 1668.76, 3562, 0.48, 'You will succeed because most people are lazy. Navicat provides powerful tools for                  ', '默认商品图.png', 0, '2008-06-19 12:10:24', '2012-01-16 06:37:45');
INSERT INTO `goods` VALUES (881, 46, 4, '裙子', 525.97, 1724, 0.07, 'Navicat Cloud provides a cloud service for synchronizing connections, queries, model                ', '默认商品图.png', 0, '2023-11-04 15:49:40', '2022-10-23 04:29:20');
INSERT INTO `goods` VALUES (882, 16, 10, '垃圾袋', 1724.77, 1257, 0.91, 'Navicat authorizes you to make connection to remote servers running on different                    ', '默认商品图.png', 1, '2022-09-17 02:59:33', '2012-11-11 10:21:31');
INSERT INTO `goods` VALUES (885, 33, 10, '雨鞋', 127.35, 7184, 0.55, 'Navicat is a multi-connections Database Administration tool allowing you to connect                 ', '默认商品图.png', 0, '2006-10-25 23:04:41', '2012-06-23 18:31:20');
INSERT INTO `goods` VALUES (886, 48, 6, '雨衣', 909.18, 628, 0.19, 'Optimism is the one quality more associated with success and happiness than any other.              ', '默认商品图.png', 1, '2010-01-09 21:01:37', '2002-07-04 01:55:09');
INSERT INTO `goods` VALUES (887, 35, 4, '苹果', 1894.67, 6143, 0.28, 'A query is used to extract data from the database in a readable format according                    ', '默认商品图.png', 1, '2012-12-31 14:37:39', '2021-09-25 12:59:52');
INSERT INTO `goods` VALUES (890, 16, 1, '撮箕', 620.48, 6245, 0.87, 'After comparing data, the window shows the number of records that will be inserted,                 ', '默认商品图.png', 1, '2008-11-24 05:39:47', '2023-01-08 06:20:15');
INSERT INTO `goods` VALUES (892, 55, 2, '橙子', 1650.97, 4055, 0.58, 'You must be the change you wish to see in the world. HTTP Tunneling is a method for                 ', '默认商品图.png', 0, '2002-01-12 09:45:21', '2018-10-01 04:05:44');
INSERT INTO `goods` VALUES (895, 45, 6, '陶瓷碗', 398.97, 6605, 0.15, 'In a Telnet session, all communications, including username and password, are transmitted           ', '默认商品图.png', 1, '2002-05-15 21:31:30', '2003-10-26 04:45:12');
INSERT INTO `goods` VALUES (898, 47, 6, '雨衣', 13.15, 9198, 0.48, 'Remember that failure is an event, not a person. Difficult circumstances serve as                   ', '默认商品图.png', 0, '2001-12-06 00:31:03', '2002-12-28 13:48:26');
INSERT INTO `goods` VALUES (901, 56, 3, '雨鞋', 784.77, 4786, 0.75, 'Remember that failure is an event, not a person. I will greet this day with love in my heart.       ', '默认商品图.png', 1, '2015-12-20 19:42:54', '2005-12-21 14:47:44');
INSERT INTO `goods` VALUES (903, 34, 8, '背包', 837.21, 520, 0.63, 'Always keep your eyes open. Keep watching. Because whatever you see can inspire you.                ', '默认商品图.png', 0, '2016-02-22 20:13:56', '2021-03-17 14:02:16');
INSERT INTO `goods` VALUES (904, 55, 7, '键盘', 1435.23, 4901, 0.55, 'Always keep your eyes open. Keep watching. Because whatever you see can inspire you.                ', '默认商品图.png', 0, '2000-02-07 13:27:49', '2004-01-20 06:41:32');
INSERT INTO `goods` VALUES (906, 35, 3, '苹果', 1014.68, 2263, 0.43, 'I may not have gone where I intended to go, but I think I have ended up where I needed to be.       ', '默认商品图.png', 1, '2001-03-16 03:32:04', '2000-02-19 15:39:05');
INSERT INTO `goods` VALUES (907, 33, 5, '保鲜膜', 189.52, 9429, 0.04, 'Optimism is the one quality more associated with success and happiness than any other.              ', '默认商品图.png', 1, '2004-03-18 03:55:04', '2002-01-16 20:18:26');
INSERT INTO `goods` VALUES (908, 18, 2, '雨衣', 935.55, 2616, 0.6, 'To successfully establish a new connection to local/remote server - no matter via                   ', '默认商品图.png', 1, '2013-11-01 18:35:25', '2002-12-27 14:10:36');
INSERT INTO `goods` VALUES (910, 53, 6, '梨', 965.52, 4063, 0.83, 'Secure Sockets Layer(SSL) is a protocol for transmitting private documents via the Internet.        ', '默认商品图.png', 0, '2023-11-11 07:48:26', '2008-04-05 23:39:17');
INSERT INTO `goods` VALUES (912, 44, 4, '音响', 209.14, 3336, 0.83, 'Success consists of going from failure to failure without loss of enthusiasm.                       ', '默认商品图.png', 0, '2016-02-13 04:06:03', '2012-08-14 16:33:36');
INSERT INTO `goods` VALUES (913, 50, 4, '裤子', 1280.2, 1897, 0.21, 'To open a query using an external editor, control-click it and select Open with External            ', '默认商品图.png', 1, '2011-02-05 11:49:53', '2010-06-22 16:00:34');
INSERT INTO `goods` VALUES (915, 58, 3, '草莓', 1392.75, 9934, 0.26, 'Creativity is intelligence having fun. Navicat Data Modeler enables you to build                    ', '默认商品图.png', 0, '2001-04-19 13:53:03', '2023-09-24 12:24:55');
INSERT INTO `goods` VALUES (916, 41, 4, '手提包', 942.66, 3397, 0.22, 'Navicat provides powerful tools for working with queries: Query Editor for editing                  ', '默认商品图.png', 1, '2003-12-14 04:27:18', '2020-12-21 22:30:07');
INSERT INTO `goods` VALUES (917, 33, 8, '999感冒灵', 1186.44, 7717, 0.34, 'Secure Sockets Layer(SSL) is a protocol for transmitting private documents via the Internet.        ', '默认商品图.png', 0, '2005-04-08 17:09:31', '2013-04-29 22:34:15');
INSERT INTO `goods` VALUES (918, 62, 6, '扫把', 1244.48, 3688, 0.3, 'Navicat Monitor can be installed on any local computer or virtual machine and does                  ', '默认商品图.png', 1, '2002-11-11 21:02:37', '2021-01-22 04:43:17');
INSERT INTO `goods` VALUES (921, 63, 7, '雨鞋', 1853.75, 7618, 0.34, 'It wasn’t raining when Noah built the ark. Success consists of going from failure                 ', '默认商品图.png', 0, '2018-09-06 05:06:30', '2020-09-15 10:01:14');
INSERT INTO `goods` VALUES (923, 18, 7, '手提包', 1345.04, 6612, 0.07, 'In a Telnet session, all communications, including username and password, are transmitted           ', '默认商品图.png', 1, '2002-07-13 03:49:46', '2003-09-28 15:46:10');
INSERT INTO `goods` VALUES (924, 62, 4, '梨', 1417.54, 3009, 0.3, 'Navicat Data Modeler is a powerful and cost-effective database design tool which                    ', '默认商品图.png', 1, '2004-04-11 00:03:21', '2015-04-12 21:14:44');
INSERT INTO `goods` VALUES (925, 43, 5, '数据线', 531.76, 6508, 0.32, 'If the plan doesn’t work, change the plan, but never the goal. It wasn’t raining                ', '默认商品图.png', 0, '2001-07-12 18:07:33', '2022-10-12 20:06:53');
INSERT INTO `goods` VALUES (926, 18, 7, '背包', 795.18, 5018, 0.91, 'The first step is as good as half over. If it scares you, it might be a good thing to try.          ', '默认商品图.png', 1, '2009-07-15 00:59:21', '2021-04-03 17:09:16');
INSERT INTO `goods` VALUES (928, 49, 2, '保鲜膜', 981.95, 6899, 0.83, 'Navicat Data Modeler enables you to build high-quality conceptual, logical and physical             ', '默认商品图.png', 0, '2000-12-19 13:57:44', '2004-01-31 13:25:51');
INSERT INTO `goods` VALUES (930, 31, 3, '摄像机', 1944.41, 5975, 0.3, 'To start working with your server in Navicat, you should first establish a connection               ', '默认商品图.png', 0, '2009-01-04 04:02:38', '2007-03-09 03:59:37');
INSERT INTO `goods` VALUES (931, 49, 5, '袜子', 772.83, 3140, 0.9, 'How we spend our days is, of course, how we spend our lives. Navicat Monitor is a                   ', '默认商品图.png', 0, '2002-06-30 10:40:55', '2011-07-25 18:25:54');
INSERT INTO `goods` VALUES (932, 40, 6, '苹果', 940.05, 4938, 0.9, 'I will greet this day with love in my heart. To clear or reload various internal                    ', '默认商品图.png', 0, '2013-02-03 23:55:30', '2015-11-30 04:01:21');
INSERT INTO `goods` VALUES (934, 46, 6, '999感冒灵', 1595.8, 876, 0.38, 'If the Show objects under schema in navigation pane option is checked at the Preferences            ', '默认商品图.png', 1, '2018-10-14 03:05:49', '2019-08-14 10:17:07');
INSERT INTO `goods` VALUES (939, 52, 8, '电脑', 1198.89, 4694, 0.82, 'To open a query using an external editor, control-click it and select Open with External            ', '默认商品图.png', 1, '2011-06-03 14:58:27', '2007-06-29 15:53:28');
INSERT INTO `goods` VALUES (940, 34, 8, '拖把', 175.85, 1271, 0.73, 'In other words, Navicat provides the ability for data in different databases and/or                 ', '默认商品图.png', 1, '2002-06-15 05:52:47', '2009-12-23 01:17:34');
INSERT INTO `goods` VALUES (942, 65, 2, '青霉素', 515.65, 5502, 0.01, 'To successfully establish a new connection to local/remote server - no matter via                   ', '默认商品图.png', 0, '2003-12-15 22:57:01', '2014-11-18 22:15:36');
INSERT INTO `goods` VALUES (943, 38, 6, '青霉素', 450.02, 2074, 0.27, 'Creativity is intelligence having fun. A man’s best friends are his ten fingers.                  ', '默认商品图.png', 1, '2017-03-03 06:58:11', '2006-01-11 06:38:23');
INSERT INTO `goods` VALUES (945, 45, 7, '雨衣', 1173.65, 7420, 0.16, 'The Main Window consists of several toolbars and panes for you to work on connections,              ', '默认商品图.png', 1, '2023-02-02 23:52:21', '2010-11-01 15:17:09');
INSERT INTO `goods` VALUES (946, 34, 5, '钱包', 1867.4, 7430, 0.2, 'In other words, Navicat provides the ability for data in different databases and/or                 ', '默认商品图.png', 0, '2004-10-25 20:41:03', '2003-11-02 18:00:41');
INSERT INTO `goods` VALUES (947, 50, 3, '苹果', 108.82, 4520, 0.52, 'To successfully establish a new connection to local/remote server - no matter via                   ', '默认商品图.png', 1, '2005-05-16 01:07:44', '2004-04-22 13:35:32');
INSERT INTO `goods` VALUES (948, 54, 8, '音响', 21.6, 1393, 0.4, 'If it scares you, it might be a good thing to try. To connect to a database or schema,              ', '默认商品图.png', 1, '2000-10-25 21:29:43', '2018-04-02 09:00:39');
INSERT INTO `goods` VALUES (950, 41, 2, '鞋子', 1501.41, 9969, 0.08, 'The first step is as good as half over. HTTP Tunneling is a method for connecting                   ', '默认商品图.png', 1, '2021-02-05 21:52:17', '2005-03-17 23:27:02');
INSERT INTO `goods` VALUES (951, 60, 5, '钱包', 606.52, 8878, 0.56, 'In the middle of winter I at last discovered that there was in me an invincible summer.             ', '默认商品图.png', 1, '2019-02-28 23:32:27', '2015-06-08 07:46:38');
INSERT INTO `goods` VALUES (954, 50, 8, '鞋子', 408.27, 8956, 0.31, 'The On Startup feature allows you to control what tabs appear when you launch Navicat.              ', '默认商品图.png', 1, '2019-08-17 02:41:01', '2015-08-15 06:23:59');
INSERT INTO `goods` VALUES (955, 66, 2, '手机', 755.34, 3614, 0.22, 'If your Internet Service Provider (ISP) does not provide direct access to its server,               ', '默认商品图.png', 0, '2011-06-07 19:32:22', '2022-02-19 16:40:27');
INSERT INTO `goods` VALUES (956, 43, 2, '锅', 1132.94, 2585, 0.49, 'To get a secure connection, the first thing you need to do is to install OpenSSL                    ', '默认商品图.png', 0, '2023-01-17 19:56:52', '2004-08-21 10:21:28');
INSERT INTO `goods` VALUES (957, 65, 2, '书包', 1102.77, 6028, 0.11, 'Actually it is just in an idea when feel oneself can achieve and cannot achieve.                    ', '默认商品图.png', 0, '2008-04-25 22:05:24', '2001-10-09 00:42:15');
INSERT INTO `goods` VALUES (960, 35, 5, '橘子', 328.45, 2377, 0.16, 'Navicat Cloud provides a cloud service for synchronizing connections, queries, model                ', '默认商品图.png', 0, '2021-09-15 19:59:05', '2014-01-09 05:06:09');
INSERT INTO `goods` VALUES (964, 34, 8, '背包', 737.18, 177, 0.95, 'SSH serves to prevent such vulnerabilities and allows you to access a remote server\'s               ', '默认商品图.png', 1, '2020-08-21 13:21:03', '2001-04-10 00:08:22');
INSERT INTO `goods` VALUES (965, 58, 6, '手提包', 1094.75, 2120, 0.36, 'All the Navicat Cloud objects are located under different projects. You can share                   ', '默认商品图.png', 1, '2023-08-17 20:36:44', '2013-07-02 06:17:32');
INSERT INTO `goods` VALUES (967, 44, 3, '毛巾', 1563.3, 1803, 0.39, 'SSH serves to prevent such vulnerabilities and allows you to access a remote server\'s               ', '默认商品图.png', 0, '2019-01-11 19:43:44', '2017-04-24 17:01:12');
INSERT INTO `goods` VALUES (968, 36, 9, '毛巾', 203.45, 4826, 0.63, 'Navicat Monitor can be installed on any local computer or virtual machine and does                  ', '默认商品图.png', 1, '2015-03-04 09:16:19', '2015-01-16 05:58:38');
INSERT INTO `goods` VALUES (969, 54, 10, '裙子', 1495.27, 2612, 0.5, 'The reason why a great man is great is that he resolves to be a great man.                          ', '默认商品图.png', 1, '2007-01-30 15:06:15', '2023-05-09 22:37:29');
INSERT INTO `goods` VALUES (970, 50, 6, '钱包', 1653.99, 9290, 0.67, 'To get a secure connection, the first thing you need to do is to install OpenSSL                    ', '默认商品图.png', 0, '2003-05-01 17:42:58', '2017-12-21 18:18:25');
INSERT INTO `goods` VALUES (972, 58, 3, '音响', 106.05, 6695, 0.38, 'Flexible settings enable you to set up a custom key for comparison and synchronization.             ', '默认商品图.png', 0, '2002-02-14 19:52:00', '2015-11-25 08:43:49');
INSERT INTO `goods` VALUES (975, 33, 3, '保鲜膜', 1298.52, 5032, 0.66, 'Success consists of going from failure to failure without loss of enthusiasm.                       ', '默认商品图.png', 1, '2014-09-14 10:37:30', '2022-11-08 03:28:56');
INSERT INTO `goods` VALUES (980, 36, 10, '陶瓷碗', 314.96, 4095, 0.28, 'You will succeed because most people are lazy. In a Telnet session, all communications,             ', '默认商品图.png', 0, '2018-12-02 11:26:59', '2010-07-22 19:39:59');
INSERT INTO `goods` VALUES (981, 62, 1, '雨鞋', 642.27, 2630, 0.73, 'Genius is an infinite capacity for taking pains. You must be the change you wish                    ', '默认商品图.png', 0, '2018-07-11 05:10:37', '2017-11-25 19:37:14');
INSERT INTO `goods` VALUES (987, 5, 5, '书包', 1665.43, 6884, 0.94, 'Navicat Monitor is a safe, simple and agentless remote server monitoring tool that                  ', '默认商品图.png', 0, '2007-05-16 05:49:41', '2008-02-21 05:03:28');
INSERT INTO `goods` VALUES (991, 18, 3, '梨', 88.64, 1580, 0.97, 'Difficult circumstances serve as a textbook of life for people. It wasn’t raining                 ', '默认商品图.png', 1, '2014-02-26 02:02:51', '2008-05-04 10:04:53');
INSERT INTO `goods` VALUES (992, 60, 1, '西瓜', 882.07, 1810, 0.16, 'Navicat 15 has added support for the system-wide dark mode. To start working with                   ', '默认商品图.png', 0, '2013-06-12 07:10:07', '2006-02-22 19:20:57');
INSERT INTO `goods` VALUES (994, 50, 7, '雨鞋', 1263, 3114, 0.92, 'To connect to a database or schema, simply double-click it in the pane.                             ', '默认商品图.png', 1, '2002-03-16 21:01:24', '2022-07-11 01:13:54');
INSERT INTO `goods` VALUES (995, 61, 2, '橙子', 1156.07, 1673, 0.04, 'You will succeed because most people are lazy. Navicat Cloud provides a cloud service               ', '默认商品图.png', 1, '2018-03-24 21:23:19', '2017-10-21 23:31:36');
INSERT INTO `goods` VALUES (996, 42, 3, '西瓜', 581.53, 307, 0.19, 'It provides strong authentication and secure encrypted communications between two                   ', '默认商品图.png', 0, '2013-03-13 18:20:02', '2015-07-21 07:14:06');
INSERT INTO `goods` VALUES (997, 65, 3, '陶瓷碗', 1735.65, 8678, 0.53, 'Export Wizard allows you to export data from tables, collections, views, or query                   ', '默认商品图.png', 0, '2006-09-30 17:43:15', '2013-06-14 13:26:59');
INSERT INTO `goods` VALUES (998, 64, 4, '梨', 57.53, 8159, 0.91, 'It provides strong authentication and secure encrypted communications between two                   ', '默认商品图.png', 0, '2008-07-25 06:39:18', '2005-02-26 03:35:02');
INSERT INTO `goods` VALUES (1002, 63, 9, '西瓜', 685.44, 9207, 0.23, 'In other words, Navicat provides the ability for data in different databases and/or                 ', '默认商品图.png', 0, '2010-05-02 17:37:55', '2010-10-22 21:06:30');
INSERT INTO `goods` VALUES (1003, 58, 2, '梨', 1930.49, 9420, 0.7, 'Export Wizard allows you to export data from tables, collections, views, or query                   ', '默认商品图.png', 1, '2006-10-30 18:51:52', '2010-10-16 00:12:39');
INSERT INTO `goods` VALUES (1004, 66, 6, '摄像机', 317.16, 227, 0.76, 'SSH serves to prevent such vulnerabilities and allows you to access a remote server\'s               ', '默认商品图.png', 1, '2019-12-19 09:49:26', '2019-02-18 07:33:29');
INSERT INTO `goods` VALUES (1006, 16, 2, '裙子', 1491.99, 3868, 0.7, 'Navicat Monitor can be installed on any local computer or virtual machine and does                  ', '默认商品图.png', 0, '2013-08-29 15:27:05', '2014-10-14 17:18:20');
INSERT INTO `goods` VALUES (1008, 55, 6, '音响', 1274.25, 6685, 0.53, 'Navicat provides a wide range advanced features, such as compelling code editing                    ', '默认商品图.png', 1, '2012-12-06 21:16:53', '2007-11-04 18:30:37');
INSERT INTO `goods` VALUES (1009, 41, 5, '陶瓷碗', 1159.68, 1106, 0.87, 'Typically, it is employed as an encrypted version of Telnet. In a Telnet session,                   ', '默认商品图.png', 0, '2021-12-20 17:19:03', '2009-02-07 01:17:26');
INSERT INTO `goods` VALUES (1011, 37, 10, '拖把', 1505.25, 3547, 0.15, 'Champions keep playing until they get it right. It provides strong authentication                   ', '默认商品图.png', 0, '2023-11-08 08:57:27', '2017-09-17 14:49:58');
INSERT INTO `goods` VALUES (1016, 16, 1, '电脑', 1950.23, 9287, 0.96, 'After logged in the Navicat Cloud feature, the Navigation pane will be divided into                 ', '默认商品图.png', 1, '2011-06-12 11:02:23', '2015-05-29 10:15:01');
INSERT INTO `goods` VALUES (1017, 53, 3, '钱包', 1099.58, 8177, 0.63, 'If it scares you, it might be a good thing to try. To clear or reload various internal              ', '默认商品图.png', 0, '2001-07-11 09:27:37', '2016-04-25 12:05:56');
INSERT INTO `goods` VALUES (1018, 46, 9, '鞋子', 790.61, 5588, 0.61, 'To successfully establish a new connection to local/remote server - no matter via                   ', '默认商品图.png', 0, '2016-06-17 05:04:54', '2014-01-18 23:37:43');
INSERT INTO `goods` VALUES (1022, 57, 6, '袜子', 888.59, 4735, 0.74, 'Sometimes you win, sometimes you learn. Always keep your eyes open. Keep watching.                  ', '默认商品图.png', 1, '2005-09-16 02:06:42', '2013-03-16 03:05:17');
INSERT INTO `goods` VALUES (1024, 44, 9, '摄像机', 1573.19, 1200, 0.25, 'To get a secure connection, the first thing you need to do is to install OpenSSL                    ', '默认商品图.png', 0, '2001-05-21 03:41:56', '2015-08-13 08:56:46');
INSERT INTO `goods` VALUES (1025, 33, 2, '苹果', 1355.93, 4976, 0.75, 'What you get by achieving your goals is not as important as what you become by achieving your goals.', '默认商品图.png', 1, '2006-05-30 11:30:19', '2014-06-11 21:52:56');
INSERT INTO `goods` VALUES (1026, 63, 5, '数据线', 1411.73, 2294, 0.36, 'If it scares you, it might be a good thing to try. The first step is as good as half over.          ', '默认商品图.png', 0, '2005-09-04 03:10:56', '2020-08-13 05:33:23');
INSERT INTO `goods` VALUES (1029, 5, 1, '保鲜膜', 1188.68, 9379, 0.21, 'SQL Editor allows you to create and edit SQL text, prepare and execute selected queries.            ', '默认商品图.png', 1, '2011-06-29 07:08:02', '2006-06-01 04:59:39');
INSERT INTO `goods` VALUES (1030, 49, 8, '梨', 353.93, 315, 0.13, 'Navicat authorizes you to make connection to remote servers running on different                    ', '默认商品图.png', 0, '2006-11-10 00:11:03', '2023-08-29 20:42:35');
INSERT INTO `goods` VALUES (1031, 49, 4, '电脑', 1954.24, 3965, 0.33, 'Monitored servers include MySQL, MariaDB and SQL Server, and compatible with cloud                  ', '默认商品图.png', 0, '2013-07-13 09:08:45', '2015-03-25 07:11:26');
INSERT INTO `goods` VALUES (1034, 36, 9, '手提包', 1586.29, 2527, 0.76, 'Navicat Data Modeler is a powerful and cost-effective database design tool which                    ', '默认商品图.png', 0, '2019-04-25 11:56:16', '2015-03-26 11:13:31');
INSERT INTO `goods` VALUES (1035, 43, 8, '苹果', 1974.01, 9330, 0.01, 'If your Internet Service Provider (ISP) does not provide direct access to its server,               ', '默认商品图.png', 1, '2007-03-13 00:06:37', '2024-04-02 16:52:17');
INSERT INTO `goods` VALUES (1040, 50, 2, '毛巾', 1212.51, 4958, 0.97, 'Instead of wondering when your next vacation is, maybe you should set up a life you                 ', '默认商品图.png', 1, '2000-02-02 18:49:12', '2024-04-20 19:26:59');
INSERT INTO `goods` VALUES (1041, 1, 1, '裙子', 500, 200, 0.5, '123', '默认商品图.png', 1, '2024-04-06 15:29:49', '2024-04-06 15:29:49');
INSERT INTO `goods` VALUES (1044, 1, 6, 'dddd', 1, 2333, 0.1, '123', '默认商品图.png', 1, '2024-05-16 22:22:01', '2024-05-16 22:22:01');
INSERT INTO `goods` VALUES (1045, 1, 7, '哈哈', 20000, 1000, 0.35, '1234', '1715870713349.jpg', 1, '2024-05-16 22:45:13', '2024-05-16 22:45:14');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '内容',
  `send_id` bigint NOT NULL COMMENT '发送者id',
  `receive_id` bigint NOT NULL COMMENT '接收者id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间\n',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (15, '6\n', 1, 1, '2024-05-26 12:22:05', '2024-05-26 12:22:05');
INSERT INTO `message` VALUES (16, 's', 1, 55, '2024-05-26 12:23:27', '2024-05-26 12:23:27');
INSERT INTO `message` VALUES (17, '哈哈\n', 1, 55, '2024-05-26 14:44:08', '2024-05-26 14:44:08');
INSERT INTO `message` VALUES (18, '666666\n', 1, 55, '2024-05-26 16:24:42', '2024-05-26 16:24:42');
INSERT INTO `message` VALUES (19, '123456\n', 1, 1, '2024-05-27 10:24:58', '2024-05-27 10:24:58');
INSERT INTO `message` VALUES (20, '4\n', 1, 1, '2024-05-27 10:25:49', '2024-05-27 10:25:49');
INSERT INTO `message` VALUES (21, '123', 6, 1, '2024-05-27 14:09:52', '2024-05-27 14:09:52');
INSERT INTO `message` VALUES (22, '456\n', 1, 1, '2024-05-27 14:29:26', '2024-05-27 14:29:26');
INSERT INTO `message` VALUES (23, '哈哈\n', 1, 1, '2024-05-27 14:30:12', '2024-05-27 14:30:12');
INSERT INTO `message` VALUES (24, '嘻嘻\n', 6, 1, '2024-05-27 14:30:21', '2024-05-27 14:30:21');
INSERT INTO `message` VALUES (25, '5687\n', 1, 1, '2024-05-27 14:31:36', '2024-05-27 14:31:36');
INSERT INTO `message` VALUES (26, '687\n', 1, 1, '2024-05-27 14:32:27', '2024-05-27 14:32:27');
INSERT INTO `message` VALUES (27, '659878\n', 1, 1, '2024-05-27 14:33:36', '2024-05-27 14:33:36');
INSERT INTO `message` VALUES (28, '123\n', 1, 1, '2024-06-12 15:01:26', '2024-06-12 15:01:26');
INSERT INTO `message` VALUES (29, 'fsad', 1, 1, '2024-06-12 15:01:39', '2024-06-12 15:01:39');
INSERT INTO `message` VALUES (30, 'vsa', 6, 1, '2024-06-12 15:01:49', '2024-06-12 15:01:49');
INSERT INTO `message` VALUES (31, '1234', 1, 1, '2024-06-12 15:02:02', '2024-06-12 15:02:02');
INSERT INTO `message` VALUES (32, '1\n', 6, 1, '2024-07-04 19:46:18', '2024-07-04 19:46:18');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `orders_number` bigint NULL DEFAULT NULL COMMENT '订单号',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `goods_id` bigint NOT NULL COMMENT '商品id',
  `store_id` bigint NULL DEFAULT NULL COMMENT '商店id',
  `total_price` decimal(8, 2) NOT NULL COMMENT '商品总价',
  `goods_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名',
  `status` int NOT NULL COMMENT '订单状态 1待发货 2已发货 3退款',
  `number` int NOT NULL COMMENT '商品数量',
  `province_code` int NULL DEFAULT NULL COMMENT '省号',
  `province_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '省名',
  `city_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '市号',
  `city_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '市名',
  `district_code` int NULL DEFAULT NULL COMMENT '区号',
  `district_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '区名',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址详情',
  `pay` int NOT NULL COMMENT '支付方式 0钱包 1微信 2支付宝 （仅模拟）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 167 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (134, 221504585225867264, 1, 1, 1, 1173.49, '衣服222', 1, 1, 13, '河北省', '1302', '唐山市', 130203, '路北区', '2024-04-14 21:40:08', '2024-04-14 21:40:08', '河北省唐山市路北区象湖公寓警民路北区', 0);
INSERT INTO `orders` VALUES (135, 221514911119970304, 1, 1, 1, 1173.49, '衣服222', 1, 1, 13, '河北省', '1302', '唐山市', 130203, '路北区', '2024-04-14 22:21:10', '2024-04-14 22:21:10', '河北省唐山市路北区象湖公寓警民路北区', 0);
INSERT INTO `orders` VALUES (136, 221514945190301696, 1, 1, 1, 1173.49, '衣服222', 1, 1, 13, '河北省', '1302', '唐山市', 130203, '路北区', '2024-04-14 22:21:18', '2024-04-14 22:21:18', '河北省唐山市路北区象湖公寓警民路北区', 0);
INSERT INTO `orders` VALUES (137, 221515101512011776, 1, 1, 1, 1173.49, '衣服222', 1, 1, 13, '河北省', '1302', '唐山市', 130203, '路北区', '2024-04-14 22:21:56', '2024-04-14 22:21:56', '河北省唐山市路北区象湖公寓警民路北区', 0);
INSERT INTO `orders` VALUES (139, 221515666476371968, 1, 1, 1, 1173.49, '衣服222', 1, 1, 13, '河北省', '1302', '唐山市', 130203, '路北区', '2024-04-14 22:24:10', '2024-04-14 22:24:10', '河北省唐山市路北区象湖公寓警民路北区', 0);
INSERT INTO `orders` VALUES (140, 221515850044280832, 1, 1, 1, 1173.49, '衣服222', 1, 1, 13, '河北省', '1302', '唐山市', 130203, '路北区', '2024-04-14 22:24:54', '2024-04-14 22:24:54', '河北省唐山市路北区象湖公寓警民路北区', 0);
INSERT INTO `orders` VALUES (141, 221516351880171520, 1, 1, 1, 1173.49, '衣服222', 1, 1, 13, '河北省', '1302', '唐山市', 130203, '路北区', '2024-04-14 22:26:54', '2024-04-14 22:26:54', '河北省唐山市路北区象湖公寓警民路北区', 0);
INSERT INTO `orders` VALUES (142, 221516464497233920, 1, 1, 1, 1173.49, '衣服222', 1, 1, 13, '河北省', '1302', '唐山市', 130203, '路北区', '2024-04-14 22:27:21', '2024-04-14 22:27:21', '河北省唐山市路北区象湖公寓警民路北区', 0);
INSERT INTO `orders` VALUES (143, 221516516548546560, 1, 1, 1, 1173.49, '衣服222', 1, 1, 13, '河北省', '1302', '唐山市', 130203, '路北区', '2024-04-14 22:27:33', '2024-04-14 22:27:33', '河北省唐山市路北区象湖公寓警民路北区', 0);
INSERT INTO `orders` VALUES (144, 221516614460379136, 1, 1, 1, 1173.49, '衣服222', 1, 1, 13, '河北省', '1302', '唐山市', 130203, '路北区', '2024-04-14 22:27:56', '2024-04-14 22:27:56', '河北省唐山市路北区象湖公寓警民路北区', 0);
INSERT INTO `orders` VALUES (145, 221516930387939328, 1, 1, 1, 1173.49, '衣服222', 1, 1, 13, '河北省', '1302', '唐山市', 130203, '路北区', '2024-04-14 22:29:12', '2024-04-14 22:29:12', '河北省唐山市路北区象湖公寓警民路北区', 0);
INSERT INTO `orders` VALUES (146, 221517182075539456, 1, 1, 1, 1173.49, '衣服222', 1, 1, 13, '河北省', '1302', '唐山市', 130203, '路北区', '2024-04-14 22:30:12', '2024-04-14 22:30:12', '河北省唐山市路北区象湖公寓警民路北区', 0);
INSERT INTO `orders` VALUES (147, 221517414217682944, 1, 1, 1, 1173.49, '衣服222', 5, 1, 13, '河北省', '1302', '唐山市', 130203, '路北区', '2024-04-14 22:31:07', '2024-04-22 08:37:02', '河北省唐山市路北区象湖公寓警民路北区', 0);
INSERT INTO `orders` VALUES (152, 221790433783713792, 1, 4, 1, 100.00, '衣服222', 1, 1, 13, '河北省', '1302', '唐山市', 130203, '路北区', '2024-04-15 16:36:00', '2024-04-15 16:36:00', '河北省唐山市路北区象湖公寓警民路北区', 0);
INSERT INTO `orders` VALUES (153, 221793955711422464, 1, 4, 1, 100.00, '衣服222', 1, 1, 13, '河北省', '1302', '唐山市', 130203, '路北区', '2024-04-15 16:50:00', '2024-04-15 16:50:00', '河北省唐山市路北区象湖公寓警民路北区', 0);
INSERT INTO `orders` VALUES (154, 221794527059513344, 1, 4, 1, 100.00, '衣服222', 1, 1, 13, '河北省', '1302', '唐山市', 130203, '路北区', '2024-04-15 16:52:16', '2024-04-15 16:52:16', '河北省唐山市路北区象湖公寓警民路北区', 0);
INSERT INTO `orders` VALUES (155, 223645400924557312, 1, 1040, 50, 7056.78, '毛巾', 1, 6, NULL, NULL, NULL, NULL, NULL, NULL, '2024-04-20 19:26:59', '2024-04-20 19:26:59', NULL, 0);
INSERT INTO `orders` VALUES (156, 223645522840391680, 1, 14, 5, 525.70, '你好', 1, 2, NULL, NULL, NULL, NULL, NULL, NULL, '2024-04-20 19:27:28', '2024-04-20 19:27:28', NULL, 0);
INSERT INTO `orders` VALUES (160, 242430583899295744, 1, 54, 43, 340.16, '拖把', 1, 1, 11, '北京市', '1101', '市辖区', 110102, '西城区', '2024-06-11 15:32:35', '2024-06-11 15:32:35', '河北省唐山市路北区象湖公寓警民路北区', 0);
INSERT INTO `orders` VALUES (161, 242784499140792320, 6, 129, 33, 1089.17, '电脑', 1, 2, NULL, NULL, NULL, NULL, NULL, NULL, '2024-06-12 14:58:55', '2024-06-12 14:58:55', NULL, 0);
INSERT INTO `orders` VALUES (162, 242784864804409344, 1, 1, 1, 1173.49, '衣服222', 3, 1, 11, '北京市', '1101', '市辖区', 110102, '西城区', '2024-06-12 15:00:22', '2024-06-12 15:09:26', '河北省唐山市路北区象湖公寓警民路北区', 0);
INSERT INTO `orders` VALUES (163, 242784924053147648, 1, 1, 1, 1173.49, '衣服222', 1, 1, 11, '北京市', '1101', '市辖区', 110102, '西城区', '2024-06-12 15:00:37', '2024-06-12 15:00:37', '河北省唐山市路北区象湖公寓警民路北区', 0);
INSERT INTO `orders` VALUES (164, 250469915847430144, 1, 1, 1, 1173.49, '衣服222', 1, 1, 11, '北京市', '1101', '市辖区', 110102, '西城区', '2024-07-03 19:58:01', '2024-07-03 19:58:01', '河北省唐山市路北区象湖公寓警民路北区', 0);
INSERT INTO `orders` VALUES (165, 250470103659974656, 1, 1, 1, 1173.49, '衣服222', 1, 1, 11, '北京市', '1101', '市辖区', 110102, '西城区', '2024-07-03 19:58:46', '2024-07-03 19:58:46', '河北省唐山市路北区象湖公寓警民路北区', 0);
INSERT INTO `orders` VALUES (166, 250470338306117632, 1, 1, 1, 1173.49, '衣服222', 1, 1, 11, '北京市', '1101', '市辖区', 110102, '西城区', '2024-07-03 19:59:42', '2024-07-03 19:59:42', '河北省唐山市路北区象湖公寓警民路北区', 0);
INSERT INTO `orders` VALUES (167, 250490248147111936, 1, 1, 1, 1173.49, '衣服222', 1, 1, 11, '北京市', '1101', '市辖区', 110102, '西城区', '2024-07-03 21:18:49', '2024-07-03 21:18:49', '河北省唐山市路北区象湖公寓警民路北区', 0);

-- ----------------------------
-- Table structure for report
-- ----------------------------
DROP TABLE IF EXISTS `report`;
CREATE TABLE `report`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '举报',
  `user_id` bigint NOT NULL COMMENT '举报人id',
  `comment_id` bigint NOT NULL COMMENT '被举报评论的id',
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '举报原因',
  `is_award` int NOT NULL DEFAULT 0 COMMENT '是否奖励该举报用户 0 未奖励 1已奖励',
  `status` int NOT NULL DEFAULT 1 COMMENT '举报状态 1 待处理 2 已处理',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '举报表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of report
-- ----------------------------
INSERT INTO `report` VALUES (18, 6, 20, 's', 0, 2, '2024-05-02 12:19:58', '2024-05-02 11:01:03');
INSERT INTO `report` VALUES (19, 6, 24, 'fwadsf', 0, 1, '2024-06-12 15:11:05', '2024-06-12 15:11:05');

-- ----------------------------
-- Table structure for store
-- ----------------------------
DROP TABLE IF EXISTS `store`;
CREATE TABLE `store`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `store_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '店名',
  `status` int NOT NULL COMMENT '店的状态 0关店 1开店',
  `logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商店logo',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 73 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '店名' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of store
-- ----------------------------
INSERT INTO `store` VALUES (1, 6, '鸿星尔克旗舰店', 1, '试用商户.png', '2024-03-10 16:44:45', '2024-05-16 20:28:43');
INSERT INTO `store` VALUES (5, 12, '456', 1, '1711272137941.png', '2024-03-11 16:02:54', '2024-03-24 19:02:03');
INSERT INTO `store` VALUES (16, 22, '我的小小店铺', 1, '试用商户.png', '2024-03-25 22:05:24', '2024-03-25 22:05:24');
INSERT INTO `store` VALUES (18, 45, '卢嘉伦', 1, '试用商户.png', '2001-08-30 07:06:46', '2006-03-26 13:26:04');
INSERT INTO `store` VALUES (31, 39, '卢子韬', 1, '试用商户.png', '2011-10-24 20:48:39', '2005-06-22 08:43:11');
INSERT INTO `store` VALUES (32, 47, '武杰宏', 1, '试用商户.png', '2000-10-04 20:34:25', '2020-03-06 01:59:01');
INSERT INTO `store` VALUES (33, 54, '冯詩涵', 1, '试用商户.png', '2004-02-03 09:40:00', '2017-05-01 06:24:37');
INSERT INTO `store` VALUES (34, 33, '严杰宏', 1, '试用商户.png', '2012-07-07 07:27:56', '2023-02-06 07:12:13');
INSERT INTO `store` VALUES (35, 49, '陆岚', 1, '试用商户.png', '2018-11-21 09:27:31', '2020-12-20 11:21:44');
INSERT INTO `store` VALUES (36, 54, '贾安琪', 1, '试用商户.png', '2015-02-23 06:31:27', '2003-05-07 19:03:23');
INSERT INTO `store` VALUES (37, 36, '姜岚', 1, '试用商户.png', '2005-12-28 13:06:09', '2018-02-05 12:14:54');
INSERT INTO `store` VALUES (38, 43, '郝宇宁', 1, '试用商户.png', '2001-10-18 09:37:56', '2008-11-07 04:49:17');
INSERT INTO `store` VALUES (40, 50, '熊震南', 1, '试用商户.png', '2012-03-02 10:26:58', '2021-07-25 22:14:24');
INSERT INTO `store` VALUES (41, 47, '宋睿', 1, '试用商户.png', '2001-03-01 11:48:59', '2015-11-28 21:32:20');
INSERT INTO `store` VALUES (42, 34, '李嘉伦', 1, '试用商户.png', '2007-06-29 17:54:47', '2009-09-23 05:10:00');
INSERT INTO `store` VALUES (43, 62, '陈宇宁', 1, '试用商户.png', '2002-11-09 13:37:04', '2012-09-03 21:12:57');
INSERT INTO `store` VALUES (44, 68, '杨岚', 1, '试用商户.png', '2023-11-13 12:28:44', '2022-04-28 04:52:43');
INSERT INTO `store` VALUES (45, 39, '严秀英', 1, '试用商户.png', '2004-03-16 21:01:47', '2015-09-15 19:25:45');
INSERT INTO `store` VALUES (46, 39, '沈詩涵', 1, '试用商户.png', '2018-07-01 10:06:11', '2006-11-19 23:38:26');
INSERT INTO `store` VALUES (47, 71, '顾致远', 1, '试用商户.png', '2024-02-10 14:41:36', '2014-08-30 09:36:09');
INSERT INTO `store` VALUES (48, 53, '余震南', 1, '试用商户.png', '2018-02-13 23:46:22', '2007-12-04 02:59:12');
INSERT INTO `store` VALUES (49, 50, '高秀英', 1, '试用商户.png', '2007-06-20 01:37:13', '2020-06-25 08:03:18');
INSERT INTO `store` VALUES (50, 38, '阎杰宏', 1, '试用商户.png', '2011-03-10 19:52:34', '2013-06-16 02:10:31');
INSERT INTO `store` VALUES (51, 69, '姚震南', 1, '试用商户.png', '2021-07-07 19:35:07', '2021-05-10 06:38:44');
INSERT INTO `store` VALUES (52, 72, '梁宇宁', 1, '试用商户.png', '2023-08-03 21:38:28', '2014-01-11 21:32:50');
INSERT INTO `store` VALUES (53, 37, '莫秀英', 1, '试用商户.png', '2003-09-18 08:35:59', '2013-10-24 00:29:49');
INSERT INTO `store` VALUES (54, 32, '毛子韬', 1, '试用商户.png', '2015-09-19 05:51:42', '2012-02-09 05:18:54');
INSERT INTO `store` VALUES (55, 75, '常睿', 1, '试用商户.png', '2003-10-11 00:36:10', '2013-10-03 07:35:00');
INSERT INTO `store` VALUES (56, 39, '邱安琪', 1, '试用商户.png', '2007-05-29 06:49:50', '2009-09-30 01:35:08');
INSERT INTO `store` VALUES (57, 54, '吕詩涵', 1, '试用商户.png', '2014-06-07 08:19:57', '2001-10-09 21:50:43');
INSERT INTO `store` VALUES (58, 61, '姚宇宁', 1, '试用商户.png', '2014-05-14 11:03:59', '2019-01-27 16:57:15');
INSERT INTO `store` VALUES (59, 35, '林杰宏', 1, '试用商户.png', '2004-08-16 11:22:44', '2002-03-05 15:09:45');
INSERT INTO `store` VALUES (60, 57, '许秀英', 1, '试用商户.png', '2020-11-04 13:39:19', '2021-09-08 15:47:01');
INSERT INTO `store` VALUES (61, 45, '熊安琪', 1, '试用商户.png', '2002-05-08 10:36:58', '2010-11-08 01:26:50');
INSERT INTO `store` VALUES (62, 43, '王杰宏', 1, '试用商户.png', '2011-10-29 03:35:13', '2002-02-03 20:47:59');
INSERT INTO `store` VALUES (63, 41, '陆宇宁', 1, '试用商户.png', '2008-07-05 04:48:41', '2007-07-14 14:58:41');
INSERT INTO `store` VALUES (64, 56, '于云熙', 1, '试用商户.png', '2010-07-11 16:51:57', '2015-11-09 15:57:01');
INSERT INTO `store` VALUES (65, 71, '贾子韬', 1, '试用商户.png', '2000-06-09 09:19:42', '2024-04-08 15:02:50');
INSERT INTO `store` VALUES (66, 59, '谭宇宁', 1, '试用商户.png', '2021-04-03 18:22:58', '2023-12-09 09:27:59');
INSERT INTO `store` VALUES (72, 133, '11111', 1, '试用商户.png', '2024-05-17 13:54:34', '2024-05-17 13:54:34');
INSERT INTO `store` VALUES (73, 135, 'fwaesad', 1, '试用商户.png', '2024-06-12 15:05:44', '2024-06-12 15:05:44');

-- ----------------------------
-- Table structure for store_address
-- ----------------------------
DROP TABLE IF EXISTS `store_address`;
CREATE TABLE `store_address`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `store_id` bigint NOT NULL COMMENT '商店id',
  `province_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '省级区划编号',
  `province_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '省级名称',
  `city_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '市级区划编号',
  `city_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '市级名称',
  `district_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '区级区划编号',
  `district_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '区级名称',
  `detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '详细地址',
  `is_default` int NOT NULL DEFAULT 1 COMMENT '是否是默认地址',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 64 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '地址簿' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of store_address
-- ----------------------------
INSERT INTO `store_address` VALUES (2, 1, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '江西省南昌市青云谱区象湖公寓警民路北区', 1, '2024-03-17 19:54:32', '2024-03-17 19:54:31');
INSERT INTO `store_address` VALUES (5, 5, '11', '北京市', '1101', '秦皇岛市', '110101', '东城区', '看了看看看', 1, '2024-03-19 09:29:23', '2024-03-19 09:29:24');
INSERT INTO `store_address` VALUES (6, 16, '13', '河北省', '1302', '唐山市', '130203', '路北区', '12345647892', 1, '2024-03-25 22:05:24', '2024-03-25 22:05:24');
INSERT INTO `store_address` VALUES (8, 53, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '中国成都市成华区双庆路400号42楼', 1, '2000-02-22 17:11:57', '2013-08-02 17:16:51');
INSERT INTO `store_address` VALUES (9, 54, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '中国北京市東城区東直門內大街365号17楼', 1, '2016-12-27 21:19:37', '2014-01-24 08:56:54');
INSERT INTO `store_address` VALUES (10, 42, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '中国广州市海珠区江南西路461号华润大厦39室', 1, '2021-06-11 13:51:55', '2000-11-25 05:11:46');
INSERT INTO `store_address` VALUES (11, 63, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '中国北京市海淀区清河中街68号180号50楼', 1, '2007-01-19 00:15:19', '2006-01-28 19:33:25');
INSERT INTO `store_address` VALUES (12, 33, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '中国广州市海珠区江南西路903号25楼', 1, '2013-05-09 03:56:40', '2005-03-25 09:28:36');
INSERT INTO `store_address` VALUES (14, 35, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '中国东莞环区南街二巷654号6室', 1, '2013-07-01 07:41:37', '2010-03-11 04:37:53');
INSERT INTO `store_address` VALUES (15, 49, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '中国成都市成华区双庆路403号6号楼', 1, '2010-06-29 20:31:55', '2014-10-21 00:02:38');
INSERT INTO `store_address` VALUES (16, 32, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '中国广州市白云区小坪东路234号华润大厦24室', 1, '2005-02-01 21:04:10', '2000-03-09 10:36:32');
INSERT INTO `store_address` VALUES (18, 47, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '中国北京市房山区岳琉路631号华润大厦33室', 1, '2010-10-15 23:49:08', '2021-12-22 08:20:52');
INSERT INTO `store_address` VALUES (19, 50, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '中国成都市锦江区红星路三段671号32楼', 1, '2010-05-14 12:19:43', '2007-03-18 19:43:57');
INSERT INTO `store_address` VALUES (20, 64, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '中国成都市成华区玉双路6号56号29栋', 1, '2011-01-31 08:42:44', '2013-12-10 05:16:20');
INSERT INTO `store_address` VALUES (22, 36, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '中国上海市闵行区宾川路782号华润大厦24室', 1, '2007-11-18 08:20:00', '2012-04-22 17:49:40');
INSERT INTO `store_address` VALUES (24, 37, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '中国广州市越秀区中山二路669号8号楼', 1, '2020-08-14 00:32:17', '2009-08-17 03:59:05');
INSERT INTO `store_address` VALUES (25, 52, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '中国深圳福田区深南大道242号33号楼', 1, '2010-04-11 02:24:58', '2018-09-15 08:28:51');
INSERT INTO `store_address` VALUES (26, 34, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '中国北京市东城区东单王府井东街435号43室', 1, '2019-03-01 20:59:33', '2001-04-11 21:27:00');
INSERT INTO `store_address` VALUES (27, 38, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '中国上海市浦东新区健祥路479号7号楼', 1, '2023-06-19 00:02:45', '2023-07-19 05:56:48');
INSERT INTO `store_address` VALUES (28, 58, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '中国成都市锦江区红星路三段372号华润大厦25室', 1, '2009-12-28 00:38:43', '2000-10-10 16:14:21');
INSERT INTO `store_address` VALUES (29, 46, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '中国中山紫马岭商圈中山五路826号华润大厦11室', 1, '2015-08-14 11:08:32', '2013-05-22 17:38:23');
INSERT INTO `store_address` VALUES (31, 66, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '中国广州市白云区小坪东路501号华润大厦33室', 1, '2004-10-08 01:24:01', '2016-07-18 22:49:30');
INSERT INTO `store_address` VALUES (32, 40, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '中国北京市西城区西長安街748号7楼', 1, '2006-11-02 01:43:38', '2018-08-18 23:17:19');
INSERT INTO `store_address` VALUES (35, 45, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '中国北京市西城区西長安街980号7栋', 1, '2016-08-15 14:33:28', '2007-08-23 13:37:00');
INSERT INTO `store_address` VALUES (36, 61, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '中国中山紫马岭商圈中山五路709号华润大厦37室', 1, '2014-10-10 20:06:06', '2017-04-13 18:42:13');
INSERT INTO `store_address` VALUES (39, 41, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '中国深圳罗湖区清水河一路638号30楼', 1, '2023-12-28 14:53:21', '2021-08-30 01:27:33');
INSERT INTO `store_address` VALUES (41, 60, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '中国北京市西城区西長安街622号15栋', 1, '2014-03-04 05:33:50', '2003-02-24 07:05:07');
INSERT INTO `store_address` VALUES (43, 43, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '中国深圳罗湖区蔡屋围深南东路947号21楼', 1, '2011-10-07 14:33:48', '2000-07-22 11:17:43');
INSERT INTO `store_address` VALUES (45, 57, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '中国中山天河区大信商圈大信南路677号49楼', 1, '2001-09-16 14:15:23', '2004-07-31 14:34:54');
INSERT INTO `store_address` VALUES (46, 65, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '中国北京市東城区東直門內大街107号华润大厦37室', 1, '2019-10-28 13:19:59', '2018-07-31 11:35:14');
INSERT INTO `store_address` VALUES (47, 56, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '中国成都市成华区二仙桥东三路894号10号楼', 1, '2009-09-20 15:56:56', '2002-02-06 01:12:11');
INSERT INTO `store_address` VALUES (49, 62, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '中国深圳罗湖区蔡屋围深南东路656号华润大厦7室', 1, '2011-06-07 09:02:03', '2022-01-26 23:25:04');
INSERT INTO `store_address` VALUES (50, 48, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '中国成都市成华区玉双路6号245号49室', 1, '2011-12-16 17:12:11', '2017-04-07 00:22:59');
INSERT INTO `store_address` VALUES (52, 59, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '中国北京市西城区西長安街938号华润大厦24室', 1, '2015-02-20 02:19:15', '2012-06-27 01:45:13');
INSERT INTO `store_address` VALUES (53, 44, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '中国东莞环区南街二巷639号32楼', 1, '2009-05-20 05:58:47', '2011-09-25 16:54:05');
INSERT INTO `store_address` VALUES (54, 51, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '中国中山乐丰六路67号12号楼', 1, '2013-11-20 16:24:54', '2001-01-20 15:34:44');
INSERT INTO `store_address` VALUES (55, 31, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '中国北京市房山区岳琉路533号14栋', 1, '2000-07-16 10:07:18', '2010-10-09 05:36:38');
INSERT INTO `store_address` VALUES (56, 55, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '中国东莞东泰五街821号16号楼', 1, '2013-06-02 23:45:03', '2012-02-20 14:19:15');
INSERT INTO `store_address` VALUES (57, 18, '36', '江西省', '3601', '南昌市', '360104', '青云谱区', '中国北京市東城区東直門內大街365号17楼', 1, '2016-12-27 21:19:37', '2014-01-24 08:56:54');
INSERT INTO `store_address` VALUES (58, 67, '12', '天津市', '1201', '市辖区', '120103', '河西区', '789465168456', 1, '2024-05-17 10:56:49', '2024-05-17 10:56:49');
INSERT INTO `store_address` VALUES (59, 68, '12', '天津市', '1201', '市辖区', '120103', '河西区', '789465168456', 1, '2024-05-17 11:16:59', '2024-05-17 11:16:59');
INSERT INTO `store_address` VALUES (60, 69, '12', '天津市', '1201', '市辖区', '120103', '河西区', '789465168456', 1, '2024-05-17 13:50:52', '2024-05-17 13:50:52');
INSERT INTO `store_address` VALUES (61, 70, '12', '天津市', '1201', '市辖区', '120103', '河西区', '789465168456', 1, '2024-05-17 13:52:09', '2024-05-17 13:52:09');
INSERT INTO `store_address` VALUES (62, 71, '12', '天津市', '1201', '市辖区', '120103', '河西区', '789465168456', 1, '2024-05-17 13:53:48', '2024-05-17 13:53:48');
INSERT INTO `store_address` VALUES (63, 72, '12', '天津市', '1201', '市辖区', '120103', '河西区', '789465168456', 1, '2024-05-17 13:54:40', '2024-05-17 13:54:40');
INSERT INTO `store_address` VALUES (64, 73, '13', '河北省', '1302', '唐山市', '130203', '路北区', 'ewqrsafsagdsre', 1, '2024-06-12 15:05:44', '2024-06-12 15:05:44');

-- ----------------------------
-- Table structure for store_sales
-- ----------------------------
DROP TABLE IF EXISTS `store_sales`;
CREATE TABLE `store_sales`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '营业额id',
  `date` date NOT NULL COMMENT '日期',
  `store_id` bigint NOT NULL COMMENT '商店ID',
  `daily_sales` decimal(10, 2) NOT NULL COMMENT '当日营业额',
  `order_count` int NOT NULL COMMENT '订单数',
  `avg_order_amount` decimal(10, 2) NOT NULL COMMENT '平均订单金额',
  `category_id` bigint NOT NULL COMMENT '产品类别ID',
  `user_count` int NOT NULL COMMENT '用户数量',
  `is_withdraw` tinyint(1) NOT NULL COMMENT '是否被提现',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '营业额数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of store_sales
-- ----------------------------
INSERT INTO `store_sales` VALUES (23, '2024-04-22', 1, 1173.49, 1, 1173.49, 1, 1, 1, '2024-04-22 08:37:02', '2024-04-22 08:37:02');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `username` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `phone` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电话号码',
  `status` int NOT NULL COMMENT '用户身份 0：管理员 1：普通用户 2：商家',
  `account_status` int NOT NULL COMMENT '账号状态 0禁用 1启用',
  `ban_start_time` datetime NULL DEFAULT NULL COMMENT '封禁开始时间',
  `ban_end_time` datetime NULL DEFAULT NULL COMMENT '封禁结束时间',
  `forbidden_word` int NOT NULL DEFAULT 1 COMMENT '禁言状态 1不禁言 0禁言',
  `forbidden_start_time` datetime NULL DEFAULT NULL COMMENT '禁言开始时间',
  `forbidden_end_time` datetime NULL DEFAULT NULL COMMENT '禁言结束时间',
  `is_online` int NOT NULL COMMENT '用户在线状态 0不在线 1在线',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `money` decimal(15, 2) NOT NULL DEFAULT 0.00 COMMENT '用户的钱包（仅模拟支付）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_username_uindex`(`username` ASC) USING BTREE,
  UNIQUE INDEX `user_phone_uindex`(`phone` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 135 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'e35cf7b66449df565f93c607d5a81d09', '15226532154', 0, 1, NULL, NULL, 1, NULL, NULL, 0, '1715051190485.jpg', '2024-07-04 19:59:27', 9978580.23, '2024-03-01 09:36:00');
INSERT INTO `user` VALUES (6, '123456', 'e10adc3949ba59abbe56e057f20f883e', '13654813547', 3, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2024-07-04 19:55:41', 10983370.96, '2024-03-04 18:32:37');
INSERT INTO `user` VALUES (7, '1', 'e10adc3949ba59abbe56e057f20f883e', '10', 3, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2024-03-06 10:46:12', 100000.00, '2024-03-05 10:02:35');
INSERT INTO `user` VALUES (12, '45678', 'e10adc3949ba59abbe56e057f20f883e', '13970051366', 3, 1, NULL, NULL, 1, NULL, NULL, 0, '1709819023667.jpg', '2024-03-24 19:03:21', 100000.00, '2024-03-05 10:02:35');
INSERT INTO `user` VALUES (16, '4', '5', '3', 1, 1, NULL, NULL, 1, NULL, NULL, 1, '1709819023667.jpg', '2024-03-07 21:43:44', 100000.00, '2024-03-05 10:02:35');
INSERT INTO `user` VALUES (17, '5', 'e10adc3949ba59abbe56e057f20f883e', '5', 1, 1, NULL, NULL, 1, NULL, NULL, 1, '1709819023667.jpg', '2024-03-07 21:43:44', 100000.00, '2024-03-05 10:02:35');
INSERT INTO `user` VALUES (22, 'msxwansui', 'e10adc3949ba59abbe56e057f20f883e', NULL, 3, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2024-03-26 08:33:28', 100000.00, '2024-03-11 18:38:24');
INSERT INTO `user` VALUES (23, 'msxmsx', '849067e70e2d0c860b5a1b3097b0572f', NULL, 3, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2024-03-11 18:44:50', 100000.00, '2024-03-11 18:38:46');
INSERT INTO `user` VALUES (25, '123456789', 'e10adc3949ba59abbe56e057f20f883e', '13698746512', 3, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2024-03-13 15:01:06', 100000.00, '2024-03-13 15:01:06');
INSERT INTO `user` VALUES (30, '48642', 'e10adc3949ba59abbe56e057f20f883e', '13542335755', 2, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2024-04-22 18:42:55', 100000.00, '2024-03-17 17:10:08');
INSERT INTO `user` VALUES (31, 'Tony', 'e10adc3949ba59abbe56e057f20f883e', '19480456500', 1, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2017-06-29 05:34:27', 10000.00, '2018-06-20 16:42:13');
INSERT INTO `user` VALUES (32, 'Ralph', 'e10adc3949ba59abbe56e057f20f883e', '286064783', 2, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2021-10-08 20:37:45', 10000.00, '2017-09-20 09:47:03');
INSERT INTO `user` VALUES (33, 'Rosa', 'e10adc3949ba59abbe56e057f20f883e', '7606532010', 2, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2023-07-26 14:02:12', 10000.00, '2007-09-04 21:34:17');
INSERT INTO `user` VALUES (34, 'Diana', 'e10adc3949ba59abbe56e057f20f883e', '15548996046', 2, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2010-11-03 22:56:02', 10000.00, '2000-12-17 02:50:03');
INSERT INTO `user` VALUES (35, 'Wanda', 'e10adc3949ba59abbe56e057f20f883e', '287670768', 2, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2024-04-03 11:41:51', 1008937.52, '2024-01-02 16:55:42');
INSERT INTO `user` VALUES (36, 'Marilyn', 'e10adc3949ba59abbe56e057f20f883e', '18137374024', 2, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2019-01-26 05:04:32', 10000.00, '2003-03-17 10:12:30');
INSERT INTO `user` VALUES (37, 'Martin', 'e10adc3949ba59abbe56e057f20f883e', '15905803294', 2, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2010-04-03 08:07:25', 10000.00, '2016-02-19 11:12:57');
INSERT INTO `user` VALUES (38, 'Christina', 'e10adc3949ba59abbe56e057f20f883e', '13454078938', 3, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2014-09-11 01:49:26', 10000.00, '2001-04-04 08:54:28');
INSERT INTO `user` VALUES (39, 'Michael', 'e10adc3949ba59abbe56e057f20f883e', '2893644123', 2, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2023-02-10 22:34:58', 10000.00, '2010-11-11 11:42:52');
INSERT INTO `user` VALUES (40, 'Janice', 'e10adc3949ba59abbe56e057f20f883e', '17456929380', 2, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2022-05-27 05:54:04', 10000.00, '2006-11-11 16:31:14');
INSERT INTO `user` VALUES (41, 'Harold', 'e10adc3949ba59abbe56e057f20f883e', '15509488964', 2, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2022-12-11 10:51:14', 10000.00, '2023-10-01 03:28:32');
INSERT INTO `user` VALUES (42, 'Joanne', 'e10adc3949ba59abbe56e057f20f883e', '2829786032', 2, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2017-07-18 10:42:09', 10000.00, '2000-10-02 06:44:47');
INSERT INTO `user` VALUES (43, 'Bruce', 'e10adc3949ba59abbe56e057f20f883e', '19873627006', 3, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2008-03-23 07:39:36', 10000.00, '2004-01-31 11:14:21');
INSERT INTO `user` VALUES (44, 'Melissa', 'e10adc3949ba59abbe56e057f20f883e', '76958428065', 3, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2014-09-27 07:58:27', 10000.00, '2016-04-29 09:51:21');
INSERT INTO `user` VALUES (45, 'Leslie', 'e10adc3949ba59abbe56e057f20f883e', '2894315743', 2, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2024-05-17 10:16:34', 10000.00, '2016-02-01 02:58:56');
INSERT INTO `user` VALUES (46, 'Leonard', 'e10adc3949ba59abbe56e057f20f883e', '19340646185', 2, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2016-07-25 05:45:43', 10000.00, '2006-07-14 15:51:57');
INSERT INTO `user` VALUES (47, 'Josephine', 'e10adc3949ba59abbe56e057f20f883e', '17328191048', 2, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2021-12-22 09:02:07', 10000.00, '2001-01-08 10:43:34');
INSERT INTO `user` VALUES (48, 'Timothy', 'e10adc3949ba59abbe56e057f20f883e', '15694884091', 2, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2006-11-28 18:10:54', 10000.00, '2000-02-21 05:05:53');
INSERT INTO `user` VALUES (49, 'Emma', 'e10adc3949ba59abbe56e057f20f883e', '76931271291', 3, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2022-03-06 09:24:09', 10000.00, '2023-03-18 00:44:23');
INSERT INTO `user` VALUES (50, 'Maria', 'e10adc3949ba59abbe56e057f20f883e', '76000057569', 1, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2021-12-10 11:25:58', 10000.00, '2006-03-27 10:53:42');
INSERT INTO `user` VALUES (51, 'Sylvia', 'e10adc3949ba59abbe56e057f20f883e', '14247264833', 2, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2001-12-09 22:04:04', 10000.00, '2020-09-17 12:04:02');
INSERT INTO `user` VALUES (52, 'Carol', 'e10adc3949ba59abbe56e057f20f883e', '14758154520', 2, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2002-08-01 22:56:59', 10000.00, '2008-01-18 04:24:20');
INSERT INTO `user` VALUES (53, 'Rhonda', 'e10adc3949ba59abbe56e057f20f883e', '14497207581', 3, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2008-09-07 10:55:13', 10000.00, '2021-12-12 01:39:43');
INSERT INTO `user` VALUES (54, 'Mario', 'e10adc3949ba59abbe56e057f20f883e', '76027136167', 1, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2023-02-02 14:11:41', 10000.00, '2008-07-19 15:58:01');
INSERT INTO `user` VALUES (55, 'Christine', 'e10adc3949ba59abbe56e057f20f883e', '219483767', 1, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2013-09-05 02:52:17', 10000.00, '2023-07-27 16:14:25');
INSERT INTO `user` VALUES (56, 'Dorothy', 'e10adc3949ba59abbe56e057f20f883e', '283316375', 3, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2013-02-14 09:31:38', 10000.00, '2002-03-04 23:09:45');
INSERT INTO `user` VALUES (57, 'Jamie', 'e10adc3949ba59abbe56e057f20f883e', '2056067618', 2, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2015-07-15 16:36:25', 10000.00, '2022-03-24 00:11:36');
INSERT INTO `user` VALUES (58, 'Chad', 'e10adc3949ba59abbe56e057f20f883e', '18297580908', 1, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2015-05-07 01:22:32', 10000.00, '2020-10-20 10:09:43');
INSERT INTO `user` VALUES (59, 'Sheila', 'e10adc3949ba59abbe56e057f20f883e', '219031794', 3, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2022-12-15 02:45:11', 10000.00, '2003-05-12 09:16:58');
INSERT INTO `user` VALUES (60, 'Michelle', 'e10adc3949ba59abbe56e057f20f883e', '2147190587', 2, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2005-04-29 18:42:52', 10000.00, '2020-02-01 10:43:03');
INSERT INTO `user` VALUES (61, 'Roger', 'e10adc3949ba59abbe56e057f20f883e', '19368859181', 1, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2001-09-09 17:34:19', 10000.00, '2005-06-15 13:48:19');
INSERT INTO `user` VALUES (62, 'Crystal', 'e10adc3949ba59abbe56e057f20f883e', '18614867351', 1, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2024-04-08 13:49:50', 10013247.48, '2014-07-25 10:35:25');
INSERT INTO `user` VALUES (63, 'Cindy', 'e10adc3949ba59abbe56e057f20f883e', '19246531610', 1, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2017-05-27 22:48:25', 10000.00, '2001-08-05 23:35:20');
INSERT INTO `user` VALUES (64, 'Jimmy', 'e10adc3949ba59abbe56e057f20f883e', '76042981152', 1, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2005-08-14 18:53:40', 10000.00, '2001-07-19 16:03:54');
INSERT INTO `user` VALUES (65, 'Theodore', 'e10adc3949ba59abbe56e057f20f883e', '17421756691', 3, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2011-06-02 07:02:15', 10000.00, '2015-03-30 08:55:50');
INSERT INTO `user` VALUES (66, 'Mike', 'e10adc3949ba59abbe56e057f20f883e', '14519676327', 1, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2006-10-04 08:52:27', 10000.00, '2023-01-04 11:09:44');
INSERT INTO `user` VALUES (67, 'Eric', 'e10adc3949ba59abbe56e057f20f883e', '107040000', 3, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2017-04-13 18:22:35', 10000.00, '2008-12-05 21:48:39');
INSERT INTO `user` VALUES (68, 'Luis', 'e10adc3949ba59abbe56e057f20f883e', '17189642501', 1, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2002-12-20 15:37:17', 10000.00, '2018-02-09 11:40:38');
INSERT INTO `user` VALUES (69, 'Alice', 'e10adc3949ba59abbe56e057f20f883e', '17169046578', 2, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2019-04-12 10:32:37', 10000.00, '2012-12-31 02:12:57');
INSERT INTO `user` VALUES (70, 'Daniel', 'e10adc3949ba59abbe56e057f20f883e', '76921401564', 1, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2007-12-24 15:33:26', 10000.00, '2002-09-15 17:50:18');
INSERT INTO `user` VALUES (71, 'Shawn', 'e10adc3949ba59abbe56e057f20f883e', '14304392284', 2, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2000-11-05 09:53:49', 10000.00, '2020-08-06 04:21:56');
INSERT INTO `user` VALUES (72, 'Jonathan', 'e10adc3949ba59abbe56e057f20f883e', '17269516140', 2, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2021-01-26 11:32:50', 10000.00, '2003-07-15 03:20:37');
INSERT INTO `user` VALUES (73, 'Ronald', 'e10adc3949ba59abbe56e057f20f883e', '7608367176', 3, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2010-07-22 04:13:11', 10000.00, '2015-11-05 07:52:12');
INSERT INTO `user` VALUES (74, 'Alexander', 'e10adc3949ba59abbe56e057f20f883e', '2897093427', 2, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2000-10-10 15:54:09', 10000.00, '2018-09-18 01:51:55');
INSERT INTO `user` VALUES (75, 'Richard', 'e10adc3949ba59abbe56e057f20f883e', '202042836', 2, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2010-07-12 15:16:03', 10000.00, '2000-06-16 12:26:40');
INSERT INTO `user` VALUES (76, 'Danielle', 'e10adc3949ba59abbe56e057f20f883e', '1021624174', 3, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2012-03-30 21:39:04', 10000.00, '2016-12-06 09:18:27');
INSERT INTO `user` VALUES (77, 'Frederick', 'e10adc3949ba59abbe56e057f20f883e', '16610250804', 1, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2016-09-19 10:10:18', 10000.00, '2008-06-01 14:28:37');
INSERT INTO `user` VALUES (78, 'Billy', 'e10adc3949ba59abbe56e057f20f883e', '15167093341', 2, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2005-01-24 00:40:45', 10000.00, '2022-01-10 03:31:24');
INSERT INTO `user` VALUES (79, 'Charles', 'e10adc3949ba59abbe56e057f20f883e', '14438635434', 3, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2003-09-05 12:51:45', 10000.00, '2002-11-14 10:22:14');
INSERT INTO `user` VALUES (80, 'Paul', 'e10adc3949ba59abbe56e057f20f883e', '17732363153', 2, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2002-01-16 19:59:27', 10000.00, '2002-12-21 09:57:06');
INSERT INTO `user` VALUES (81, 'Nicole', 'e10adc3949ba59abbe56e057f20f883e', '7556096604', 3, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2022-05-03 18:36:06', 10000.00, '2005-07-12 17:06:26');
INSERT INTO `user` VALUES (82, 'Vincent', 'e10adc3949ba59abbe56e057f20f883e', '19877527466', 2, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2001-06-03 04:03:09', 10000.00, '2016-04-21 20:07:03');
INSERT INTO `user` VALUES (83, 'Justin', 'e10adc3949ba59abbe56e057f20f883e', '76995726466', 3, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2009-01-17 16:45:18', 10000.00, '2019-05-18 00:33:48');
INSERT INTO `user` VALUES (84, 'Benjamin', 'e10adc3949ba59abbe56e057f20f883e', '15575967624', 2, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2003-09-14 10:56:06', 10000.00, '2019-09-18 12:22:03');
INSERT INTO `user` VALUES (85, 'Kevin', 'e10adc3949ba59abbe56e057f20f883e', '7603753928', 1, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2019-01-09 16:22:57', 10000.00, '2011-04-18 20:11:43');
INSERT INTO `user` VALUES (86, 'Evelyn', 'e10adc3949ba59abbe56e057f20f883e', '13045663066', 3, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2023-10-05 01:11:42', 10000.00, '2020-05-29 15:01:41');
INSERT INTO `user` VALUES (87, 'William', 'e10adc3949ba59abbe56e057f20f883e', '7697790835', 2, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2021-12-29 16:56:23', 10000.00, '2016-09-11 03:55:32');
INSERT INTO `user` VALUES (88, 'Patricia', 'e10adc3949ba59abbe56e057f20f883e', '19458319999', 1, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2024-04-08 15:52:48', 10000.00, '2000-12-12 18:35:54');
INSERT INTO `user` VALUES (89, 'Eva', 'e10adc3949ba59abbe56e057f20f883e', '14827816463', 1, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2007-01-04 14:03:33', 10000.00, '2010-06-23 10:13:27');
INSERT INTO `user` VALUES (90, 'Virginia', 'e10adc3949ba59abbe56e057f20f883e', '285919764', 1, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2002-09-17 01:07:48', 10000.00, '2023-10-19 12:37:41');
INSERT INTO `user` VALUES (91, 'Micheal', 'e10adc3949ba59abbe56e057f20f883e', '17508653377', 1, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2000-01-08 03:26:40', 10000.00, '2015-05-26 19:08:15');
INSERT INTO `user` VALUES (92, 'Judith', 'e10adc3949ba59abbe56e057f20f883e', '76000711921', 1, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2005-04-19 06:38:10', 10000.00, '2007-05-24 15:50:25');
INSERT INTO `user` VALUES (93, 'Marcus', 'e10adc3949ba59abbe56e057f20f883e', '76989582014', 1, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2001-12-03 12:59:37', 10000.00, '2003-03-20 02:03:52');
INSERT INTO `user` VALUES (94, 'Lois', 'e10adc3949ba59abbe56e057f20f883e', '1044746602', 2, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2013-08-24 18:55:34', 10000.00, '2011-03-27 07:00:41');
INSERT INTO `user` VALUES (95, 'Ernest', 'e10adc3949ba59abbe56e057f20f883e', '7555123424', 1, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2021-02-06 11:36:37', 10000.00, '2005-08-14 10:34:30');
INSERT INTO `user` VALUES (96, 'Andrea', 'e10adc3949ba59abbe56e057f20f883e', '2899999986', 2, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2022-11-19 20:14:26', 10000.00, '2004-09-12 05:12:47');
INSERT INTO `user` VALUES (97, 'Ray', 'e10adc3949ba59abbe56e057f20f883e', '19435167848', 3, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2017-02-22 07:06:38', 10000.00, '2015-04-25 23:49:39');
INSERT INTO `user` VALUES (98, 'Samuel', 'e10adc3949ba59abbe56e057f20f883e', '2825451578', 1, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2002-05-16 17:12:15', 10000.00, '2019-03-01 19:24:44');
INSERT INTO `user` VALUES (99, 'Pamela', 'e10adc3949ba59abbe56e057f20f883e', '13631736557', 3, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2024-04-02 16:16:18', 10000.00, '2015-01-26 04:06:32');
INSERT INTO `user` VALUES (100, 'Edith', 'e10adc3949ba59abbe56e057f20f883e', '7606202765', 2, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2021-10-14 06:26:48', 10000.00, '2008-07-31 03:35:39');
INSERT INTO `user` VALUES (101, 'Christopher', 'e10adc3949ba59abbe56e057f20f883e', '13614709155', 3, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2004-08-29 22:16:52', 10000.00, '2000-09-23 20:12:31');
INSERT INTO `user` VALUES (102, 'Nicholas', 'e10adc3949ba59abbe56e057f20f883e', '75529728851', 2, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2004-10-29 02:45:54', 10000.00, '2008-11-24 04:08:43');
INSERT INTO `user` VALUES (103, 'Eddie', 'e10adc3949ba59abbe56e057f20f883e', '7693627153', 1, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2001-09-17 10:56:05', 10000.00, '2024-02-11 21:08:49');
INSERT INTO `user` VALUES (104, 'Mark', 'e10adc3949ba59abbe56e057f20f883e', '13402499609', 3, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2013-03-01 19:01:09', 10000.00, '2018-04-29 08:10:23');
INSERT INTO `user` VALUES (105, 'Kimberly', 'e10adc3949ba59abbe56e057f20f883e', '2093686656', 2, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2005-08-18 15:52:36', 10000.00, '2017-02-05 16:50:00');
INSERT INTO `user` VALUES (106, 'James', 'e10adc3949ba59abbe56e057f20f883e', '17838301515', 2, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2010-06-02 22:31:59', 10000.00, '2013-04-28 00:08:01');
INSERT INTO `user` VALUES (107, 'Katherine', 'e10adc3949ba59abbe56e057f20f883e', '283385307', 2, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2023-10-31 20:07:39', 10000.00, '2022-04-14 12:50:58');
INSERT INTO `user` VALUES (108, 'Heather', 'e10adc3949ba59abbe56e057f20f883e', '19354627068', 2, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2023-02-28 04:20:46', 10000.00, '2010-11-26 21:19:52');
INSERT INTO `user` VALUES (109, 'Sean', 'e10adc3949ba59abbe56e057f20f883e', '16793647498', 3, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2017-09-25 20:18:01', 10000.00, '2022-03-02 19:45:16');
INSERT INTO `user` VALUES (110, 'Angela', 'e10adc3949ba59abbe56e057f20f883e', '15002932849', 2, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2007-11-21 14:18:43', 10000.00, '2013-01-16 14:57:59');
INSERT INTO `user` VALUES (111, 'Elizabeth', 'e10adc3949ba59abbe56e057f20f883e', '14135342307', 3, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2011-02-11 07:38:47', 10000.00, '2013-11-25 23:54:13');
INSERT INTO `user` VALUES (112, 'Russell', 'e10adc3949ba59abbe56e057f20f883e', '76947102387', 3, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2008-12-30 08:26:38', 10000.00, '2017-10-30 16:08:11');
INSERT INTO `user` VALUES (113, 'Dawn', 'e10adc3949ba59abbe56e057f20f883e', '2131054777', 3, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2004-02-26 19:46:13', 10000.00, '2008-03-29 20:48:41');
INSERT INTO `user` VALUES (114, 'Leroy', 'e10adc3949ba59abbe56e057f20f883e', '17633604291', 1, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2002-06-20 18:55:33', 10000.00, '2014-08-18 15:46:35');
INSERT INTO `user` VALUES (115, 'Sara', 'e10adc3949ba59abbe56e057f20f883e', '15517367626', 1, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2016-04-18 08:24:49', 10000.00, '2000-08-20 08:29:53');
INSERT INTO `user` VALUES (116, 'Henry', 'e10adc3949ba59abbe56e057f20f883e', '19257603270', 3, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2011-12-16 05:40:35', 10000.00, '2008-07-28 20:18:24');
INSERT INTO `user` VALUES (117, 'Cheryl', 'e10adc3949ba59abbe56e057f20f883e', '2006227842', 2, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2012-01-15 18:29:41', 10000.00, '2007-10-28 08:27:59');
INSERT INTO `user` VALUES (118, 'Barry', 'e10adc3949ba59abbe56e057f20f883e', '19518339993', 2, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2005-12-04 02:47:59', 10000.00, '2013-07-25 05:44:46');
INSERT INTO `user` VALUES (119, 'Carrie', 'e10adc3949ba59abbe56e057f20f883e', '76973601399', 1, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2016-04-08 03:15:56', 10000.00, '2020-12-19 18:48:38');
INSERT INTO `user` VALUES (120, 'Marvin', 'e10adc3949ba59abbe56e057f20f883e', '17669913346', 3, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2017-02-06 00:22:11', 10000.00, '2015-05-09 04:26:01');
INSERT INTO `user` VALUES (121, 'Valerie', 'e10adc3949ba59abbe56e057f20f883e', '2120703792', 1, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2019-11-29 07:56:37', 10000.00, '2012-04-13 22:05:16');
INSERT INTO `user` VALUES (122, 'Tiffany', 'e10adc3949ba59abbe56e057f20f883e', '75515485321', 2, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2001-03-06 00:21:15', 10000.00, '2011-08-13 04:46:54');
INSERT INTO `user` VALUES (123, 'Antonio', 'e10adc3949ba59abbe56e057f20f883e', '19111945045', 2, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2015-06-09 15:21:39', 10000.00, '2009-07-13 04:10:05');
INSERT INTO `user` VALUES (124, 'Gladys', 'e10adc3949ba59abbe56e057f20f883e', '19499889196', 2, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2014-12-10 11:07:32', 10000.00, '2011-01-01 06:18:09');
INSERT INTO `user` VALUES (125, 'David', 'e10adc3949ba59abbe56e057f20f883e', '16751951192', 3, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2020-06-28 16:10:34', 10000.00, '2022-09-14 14:43:21');
INSERT INTO `user` VALUES (126, 'John', 'e10adc3949ba59abbe56e057f20f883e', '18143160822', 1, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2012-05-28 05:53:22', 10000.00, '2023-12-17 14:32:13');
INSERT INTO `user` VALUES (127, 'Francisco', 'e10adc3949ba59abbe56e057f20f883e', '2858621909', 2, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2002-08-07 07:52:46', 10000.00, '2023-01-12 21:06:38');
INSERT INTO `user` VALUES (128, 'April', 'e10adc3949ba59abbe56e057f20f883e', '108894433', 2, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2017-01-09 07:14:56', 10000.00, '2010-02-12 03:31:04');
INSERT INTO `user` VALUES (129, 'Alan', 'e10adc3949ba59abbe56e057f20f883e', '14631189659', 2, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2016-11-11 07:53:49', 10000.00, '2011-12-19 04:15:17');
INSERT INTO `user` VALUES (130, 'Phillip', 'e10adc3949ba59abbe56e057f20f883e', '17025797586', 2, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2002-08-29 01:30:46', 10000.00, '2000-11-29 00:11:15');
INSERT INTO `user` VALUES (131, 'qwert', 'e10adc3949ba59abbe56e057f20f883e', NULL, 2, 1, NULL, NULL, 1, NULL, NULL, 1, 'default.png', '2024-05-02 14:41:35', 0.00, '2024-05-02 14:41:28');
INSERT INTO `user` VALUES (132, 'fieqpw', 'e10adc3949ba59abbe56e057f20f883e', NULL, 2, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2024-05-06 15:14:05', 0.00, '2024-05-06 15:14:05');
INSERT INTO `user` VALUES (133, '666666', 'e10adc3949ba59abbe56e057f20f883e', NULL, 3, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2024-05-17 14:47:28', 0.00, '2024-05-17 10:18:15');
INSERT INTO `user` VALUES (135, 'xxxxxx', 'e10adc3949ba59abbe56e057f20f883e', NULL, 3, 1, NULL, NULL, 1, NULL, NULL, 0, 'default.png', '2024-06-12 15:29:34', 0.00, '2024-06-12 14:37:04');

-- ----------------------------
-- Table structure for withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `withdraw_record`;
CREATE TABLE `withdraw_record`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id\n',
  `user_id` bigint NOT NULL COMMENT '买家id',
  `seller_id` bigint NOT NULL COMMENT '卖家id',
  `store_id` bigint NOT NULL COMMENT '商店id',
  `goods_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名',
  `number` int NOT NULL COMMENT '商品数量',
  `total_price` decimal(10, 2) NOT NULL COMMENT '商品总价',
  `origin_money` decimal(15, 2) NULL DEFAULT NULL COMMENT '原先的钱',
  `pay` int NOT NULL COMMENT '支付方式',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `withdraw_money` decimal(10, 2) NOT NULL COMMENT '提现金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '提现记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of withdraw_record
-- ----------------------------
INSERT INTO `withdraw_record` VALUES (8, 1, 6, 1, '衣服222', 1, 1173.49, 9984551.82, 0, '2024-04-22 08:37:02', '2024-04-22 08:37:02', 997.47);
INSERT INTO `withdraw_record` VALUES (9, 6, 1, 1, '衣服222', 1, 1173.49, 9984551.82, 0, '2024-04-22 08:37:02', '2024-04-22 08:37:02', 176.02);

SET FOREIGN_KEY_CHECKS = 1;
