/*
Navicat MySQL Data Transfer

Source Server         : tms_dev
Source Server Version : 50611
Source Host           : 10.202.185.123:3306
Source Database       : tms

Target Server Type    : MYSQL
Target Server Version : 50611
File Encoding         : 65001

Date: 2016-05-13 10:31:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for address_lnglat
-- ----------------------------
DROP TABLE IF EXISTS `address_lnglat`;
CREATE TABLE `address_lnglat` (
  `POINTID` bigint(20) NOT NULL,
  `ADDRESS` varchar(200) NOT NULL DEFAULT '' COMMENT '地址',
  `LNG` varchar(50) NOT NULL DEFAULT '' COMMENT '经度',
  `LAT` varchar(50) NOT NULL DEFAULT '' COMMENT '纬度',
  `LEVEL` varchar(50) NOT NULL DEFAULT '' COMMENT '地址类型',
  `CREATEID` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人ID',
  `CREATOR` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人',
  `CREATETIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `MODIFIERID` bigint(20) NOT NULL DEFAULT '0' COMMENT '修改人ID',
  `MODIFIER` varchar(64) NOT NULL DEFAULT '' COMMENT '修改人',
  `MODIFYTIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`POINTID`),
  KEY `IDX_ADDRESS` (`ADDRESS`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for address_sites_history
-- ----------------------------
DROP TABLE IF EXISTS `address_sites_history`;
CREATE TABLE `address_sites_history` (
  `RECORDID` bigint(20) NOT NULL,
  `LOCATIONID` bigint(20) NOT NULL DEFAULT '0' COMMENT '站点ID',
  `ADDRESS` varchar(200) NOT NULL DEFAULT '' COMMENT '地址',
  `RETURNTIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '回单时间',
  `CREATEID` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人ID',
  `CREATOR` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人',
  `CREATETIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `MODIFIERID` bigint(20) NOT NULL DEFAULT '0' COMMENT '修改人ID',
  `MODIFIER` varchar(64) NOT NULL DEFAULT '' COMMENT '修改人',
  `MODIFYTIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`RECORDID`),
  KEY `IDX_ADDRESS` (`ADDRESS`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cfg_attr
-- ----------------------------
DROP TABLE IF EXISTS `cfg_attr`;
CREATE TABLE `cfg_attr` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `ATTR_DFN_ID` bigint(20) NOT NULL COMMENT '属性定义主键',
  `NAME` varchar(32) DEFAULT NULL COMMENT '页面展示名字',
  `EDIT_TYPE` varchar(32) DEFAULT NULL COMMENT '类型,checkbox,select,radio,date,time,number,input,spinner',
  `PARAMS` varchar(128) DEFAULT NULL COMMENT 'DataList参数',
  `LABEL` varchar(32) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自定义查询可选属性设置表';

-- ----------------------------
-- Table structure for cfg_attr_dfn
-- ----------------------------
DROP TABLE IF EXISTS `cfg_attr_dfn`;
CREATE TABLE `cfg_attr_dfn` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `TYPE` varchar(32) DEFAULT NULL COMMENT '数据类型',
  `NAME` varchar(32) DEFAULT NULL,
  `DATA_LIST_ID` bigint(20) DEFAULT NULL,
  `REMARKS` varchar(64) DEFAULT NULL,
  `MAX` varchar(64) DEFAULT NULL,
  `MIN` varchar(64) DEFAULT NULL,
  `EXT` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='属性定义表';

-- ----------------------------
-- Table structure for cfg_attr_ins
-- ----------------------------
DROP TABLE IF EXISTS `cfg_attr_ins`;
CREATE TABLE `cfg_attr_ins` (
  `INS_ID` bigint(20) NOT NULL COMMENT '主键',
  `COND_ID` bigint(20) DEFAULT NULL COMMENT '对应条件ID',
  `ATTR_ID` bigint(20) DEFAULT NULL COMMENT '属性ID',
  `ATTR_VALUE` varchar(128) DEFAULT NULL COMMENT '自定义保存对象',
  `OPERATOR` varchar(32) DEFAULT NULL COMMENT '算子',
  `STATUS` tinyint(4) DEFAULT NULL COMMENT '状态0:无效,1:有效',
  `ISMULTIPLE` int(1) NOT NULL DEFAULT '1' COMMENT '是否多选',
  `ISMULTIPLE1` int(1) NOT NULL DEFAULT '1' COMMENT '是否多选',
  PRIMARY KEY (`INS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设置查询条件详细表';

-- ----------------------------
-- Table structure for cfg_attr_set
-- ----------------------------
DROP TABLE IF EXISTS `cfg_attr_set`;
CREATE TABLE `cfg_attr_set` (
  `PAGE_ID` bigint(20) DEFAULT NULL COMMENT '表单Id',
  `ATTR_ID` bigint(20) DEFAULT NULL COMMENT '属性实例Id',
  `STATUS` tinyint(4) DEFAULT NULL COMMENT '状态1:生效,0:失效',
  KEY `IDX_PAGE_ID` (`PAGE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='页面属性关联表';

-- ----------------------------
-- Table structure for cfg_cond
-- ----------------------------
DROP TABLE IF EXISTS `cfg_cond`;
CREATE TABLE `cfg_cond` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `NAME` varchar(64) DEFAULT NULL COMMENT '条件名',
  `CREATE_ID` bigint(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `PAGE_ID` bigint(20) DEFAULT NULL COMMENT '页面Id',
  `EXT1` varchar(64) DEFAULT NULL,
  `ISDEFAULT` int(11) DEFAULT NULL COMMENT '是否默认(1-是，0-否)',
  PRIMARY KEY (`ID`),
  KEY `IDX_PAGE_USER_ID` (`PAGE_ID`,`CREATE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自定义查询组别表';

-- ----------------------------
-- Table structure for cfg_db_acct
-- ----------------------------
DROP TABLE IF EXISTS `cfg_db_acct`;
CREATE TABLE `cfg_db_acct` (
  `DB_ACCT_CODE` varchar(32) NOT NULL COMMENT 'datasource key',
  `USERNAME` varchar(32) DEFAULT NULL,
  `PASSWORD` varchar(32) DEFAULT NULL,
  `URL` varchar(255) DEFAULT NULL,
  `DEFAULT_CONN_MIN` int(11) DEFAULT NULL,
  `DEFAULT_CONN_MAX` int(11) DEFAULT NULL,
  `STATE` char(1) DEFAULT NULL COMMENT 'N(Normal),A(Abandon),E(Abnormal)',
  `DB_TYPE` varchar(2) DEFAULT NULL COMMENT 'M:Master,S:slave',
  `REMARKS` varchar(128) DEFAULT NULL,
  `DB_DIALECT` varchar(32) DEFAULT NULL COMMENT 'ORACLE,MYSQL',
  `ATTACH` varchar(32) DEFAULT NULL COMMENT 'attach to',
  PRIMARY KEY (`DB_ACCT_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统库数据库表';

-- ----------------------------
-- Table structure for cfg_id_generator
-- ----------------------------
DROP TABLE IF EXISTS `cfg_id_generator`;
CREATE TABLE `cfg_id_generator` (
  `TABLE_NAME` varchar(100) NOT NULL COMMENT '表名称',
  `DOMAIN_ID` decimal(6,0) NOT NULL,
  `GENERATOR_TYPE` char(1) NOT NULL COMMENT '类型',
  `SEQUENCE_NAME` varchar(60) DEFAULT NULL COMMENT '序列名',
  `MAX_ID` bigint(20) DEFAULT NULL COMMENT '最大序列',
  `START_VALUE` bigint(20) DEFAULT NULL COMMENT '开始序列',
  `MIN_VALUE` bigint(20) DEFAULT NULL COMMENT '最小值',
  `MAX_VALUE` bigint(20) DEFAULT NULL COMMENT '最大值',
  `INCREMENT_VALUE` bigint(20) DEFAULT NULL,
  `CYCLE_FLAG` char(1) DEFAULT NULL,
  `CACHE_SIZE` bigint(20) DEFAULT NULL COMMENT '缓存值',
  `SEQUENCE_CREATE_SCRIPT` varchar(1000) DEFAULT NULL,
  `HIS_TABLE_NAME` varchar(100) DEFAULT NULL,
  `HIS_SEQUENCE_NAME` varchar(60) DEFAULT NULL,
  `HIS_DATA_FLAG` char(1) DEFAULT NULL,
  `HIS_MAX_ID` bigint(20) DEFAULT NULL,
  `HIS_DONECODE_FLAG` char(1) DEFAULT NULL,
  `STEP_BY` bigint(20) DEFAULT NULL COMMENT '步长',
  `HIS_STEP_BY` bigint(20) DEFAULT NULL,
  `DB_TYPE` varchar(20) NOT NULL DEFAULT 'base' COMMENT 'DB类型',
  PRIMARY KEY (`TABLE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主键自增属性设置表';

-- ----------------------------
-- Table structure for cfg_static_data
-- ----------------------------
DROP TABLE IF EXISTS `cfg_static_data`;
CREATE TABLE `cfg_static_data` (
  `ID` bigint(20) NOT NULL COMMENT 'ID',
  `DATA_TYPE` varchar(128) NOT NULL COMMENT '类型',
  `DATA_CODE` varchar(128) NOT NULL COMMENT 'key',
  `DATA_VALUE` varchar(1024) DEFAULT NULL,
  `DATA_DESC` varchar(128) DEFAULT NULL COMMENT '描述',
  `DATA_ORDER` bigint(20) DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`ID`),
  KEY `IDX_DATA_TYPE` (`DATA_TYPE`,`DATA_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典表';

-- ----------------------------
-- Table structure for cfg_task
-- ----------------------------
DROP TABLE IF EXISTS `cfg_task`;
CREATE TABLE `cfg_task` (
  `CFG_TASK_ID` bigint(20) NOT NULL COMMENT 'Primary Key',
  `TASK_NAME` varchar(64) DEFAULT NULL COMMENT 'Task Name',
  `CFG_TASK_TYPE_CODE` varchar(64) DEFAULT NULL COMMENT 'Process Id',
  `TRIGGER_TYPE` varchar(2) DEFAULT NULL COMMENT 'C:cron,S:simple',
  `TRIGGER_EXPR` varchar(64) DEFAULT NULL COMMENT 'Cron Expression or simple trigger(count interval)。SimpleExpression',
  `PRIPORITY` int(11) DEFAULT NULL COMMENT '1-9',
  `CREATE_USER` varchar(32) DEFAULT NULL COMMENT 'Who create the trigger',
  `TASK_STATE` varchar(2) DEFAULT NULL COMMENT 'N:normal，S:suspend',
  `CREATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Create Date',
  `REMARKS` varchar(128) DEFAULT NULL,
  `START_DATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'task start time',
  `END_DATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'task end time',
  `JOB_EXPR` varchar(128) DEFAULT NULL COMMENT 'sepcifiy the job type implements',
  `JOB_TYPE` varchar(32) DEFAULT NULL COMMENT 'T:doTask,M:method invoke',
  `TASK_PARAMS` varchar(256) DEFAULT NULL COMMENT 'parameter split by &,like name=yuan&age=14',
  `TASK_GROUP` varchar(32) DEFAULT NULL COMMENT 'Task group',
  PRIMARY KEY (`CFG_TASK_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Manage the task,We use the taskId as primary,because of exis';

-- ----------------------------
-- Table structure for cfg_task_detail
-- ----------------------------
DROP TABLE IF EXISTS `cfg_task_detail`;
CREATE TABLE `cfg_task_detail` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `TASK_ID` bigint(20) DEFAULT NULL COMMENT 'cfg_task表id',
  `TASK_NAME` varchar(64) DEFAULT NULL COMMENT '名称',
  `TRIGGER_EXP` varchar(128) DEFAULT NULL COMMENT '执行的类',
  `STATUS` varchar(2) DEFAULT NULL COMMENT '状态',
  `REMARK` varchar(256) DEFAULT NULL COMMENT '描述',
  `MODIFIER_ID` bigint(20) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIER` varchar(64) DEFAULT NULL COMMENT '修改人',
  `MODIFIED_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `CREATOR_ID` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `CREATOR` varchar(64) DEFAULT NULL COMMENT '创建人',
  `CREATED_TIME` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `SORT` int(11) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cfg_task_log
-- ----------------------------
DROP TABLE IF EXISTS `cfg_task_log`;
CREATE TABLE `cfg_task_log` (
  `TASK_ID` bigint(20) NOT NULL COMMENT 'PK',
  `CFG_TASK_ID` bigint(20) DEFAULT NULL,
  `CFG_TASK_DETAIL_ID` bigint(20) DEFAULT NULL COMMENT 'CFG_TASK_DETAIL_ID',
  `START_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'when start execute ',
  `FINISH_DATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'when complete task',
  `STATE` varchar(2) DEFAULT NULL COMMENT 'E:Exception,N:normal',
  `CONTENT` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`TASK_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务日志表';

-- ----------------------------
-- Table structure for cfg_web_data_list
-- ----------------------------
DROP TABLE IF EXISTS `cfg_web_data_list`;
CREATE TABLE `cfg_web_data_list` (
  `list_id` int(8) NOT NULL COMMENT 'ID',
  `list_name` varchar(64) DEFAULT NULL COMMENT '名称',
  `list_type` varchar(32) DEFAULT NULL COMMENT '类型',
  `attr_text` varchar(64) DEFAULT NULL COMMENT '字段名',
  `attr_value` varchar(64) DEFAULT NULL COMMENT '字段值',
  `list_expression` varchar(128) DEFAULT NULL COMMENT '方法类名',
  `ext1` varchar(64) DEFAULT NULL,
  `ext2` varchar(128) DEFAULT NULL,
  KEY `IDX_LIST_ID` (`list_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据获取方法设置表';

-- ----------------------------
-- Table structure for cs_complain
-- ----------------------------
DROP TABLE IF EXISTS `cs_complain`;
CREATE TABLE `cs_complain` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `ORDER_DETAIL_CODE` varchar(32) NOT NULL DEFAULT '' COMMENT '子单号',
  `ORDER_CODE` varchar(32) NOT NULL DEFAULT '' COMMENT '母单号',
  `STATUS` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态',
  `ORDER_TYPE` tinyint(4) NOT NULL DEFAULT '1' COMMENT '运单类型',
  `CARRIER_ID` bigint(20) NOT NULL DEFAULT '-1' COMMENT '配送商id',
  `DEPOT_ID` bigint(20) NOT NULL DEFAULT '-1' COMMENT '站点id',
  `TRACE_NO` varchar(32) NOT NULL DEFAULT '' COMMENT '追踪单号',
  `COMPLAIN_CONTENT` varchar(1024) NOT NULL DEFAULT '' COMMENT '客诉内容',
  `COMPLAIN_REMARK` varchar(2014) NOT NULL DEFAULT '' COMMENT '客诉说明',
  `REPLY_CONTENT` varchar(1024) NOT NULL DEFAULT '' COMMENT '回复内容',
  `REPLY_TIME` varchar(32) NOT NULL DEFAULT '' COMMENT '回复时间',
  `REPLIER` varchar(64) NOT NULL DEFAULT '' COMMENT '回复人',
  `REPLIER_ID` bigint(20) NOT NULL DEFAULT '-1' COMMENT '回复人ID',
  `CREATED_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `CREATOR` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人',
  `MODIFIED_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `MODIFIER` varchar(64) NOT NULL DEFAULT '' COMMENT '修改人',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `ID` bigint(20) NOT NULL,
  `CODE` varchar(32) NOT NULL COMMENT '代码,飞牛赋予客户的代码',
  `NAME` varchar(128) NOT NULL COMMENT '名称',
  `ABBR` varchar(32) DEFAULT NULL COMMENT '简称',
  `CONTACT` varchar(64) DEFAULT NULL COMMENT '联系人',
  `EMAIL` varchar(64) DEFAULT NULL COMMENT '邮件',
  `TELEPHONE` varchar(32) DEFAULT NULL COMMENT '电话',
  `ADDRESS` varchar(128) DEFAULT NULL COMMENT '地址',
  `IS_DELETED` tinyint(4) DEFAULT '0' COMMENT '是否删除',
  `REMARK` varchar(256) DEFAULT NULL COMMENT '备注',
  `CREATOR_ID` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `CREATOR` varchar(64) DEFAULT NULL COMMENT '创建人姓名',
  `CREATED_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `MODIFIER_ID` bigint(20) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIER` varchar(64) DEFAULT NULL COMMENT '修改人姓名',
  `MODIFIED_TIME` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '最后修改时间',
  `COMPANY_ID` bigint(20) DEFAULT NULL COMMENT '公司ID',
  `EXTERNAL_CODE` varchar(32) DEFAULT NULL COMMENT '外部客户赋予飞犇的代码',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_CUSTOMER` (`CODE`,`COMPANY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='委托方';

-- ----------------------------
-- Table structure for customer_order
-- ----------------------------
DROP TABLE IF EXISTS `customer_order`;
CREATE TABLE `customer_order` (
  `ID` bigint(18) NOT NULL DEFAULT '0' COMMENT '主键',
  `CUSTOMER_ORDER_CODE` varchar(40) NOT NULL DEFAULT '' COMMENT '客户订单号',
  `CODE` varchar(40) NOT NULL DEFAULT '' COMMENT '包裹号,客户订单号加包裹号',
  `CANCEL_NO` varchar(40) NOT NULL DEFAULT '' COMMENT '退订单编号',
  `CONSIGNEE` varchar(40) NOT NULL DEFAULT '' COMMENT '客户姓名',
  `CONSIGNEE_MOBILE` varchar(20) DEFAULT NULL COMMENT '收件人手机',
  `CONSIGNEE_PHONE` varchar(50) DEFAULT NULL COMMENT '收件人电话',
  `CONSIGNEE_PROVINCE` varchar(80) NOT NULL DEFAULT '' COMMENT '客户省份',
  `CONSIGNEE_CITY` varchar(80) NOT NULL DEFAULT '' COMMENT '客户城市',
  `CONSIGNEE_AREA` varchar(80) NOT NULL DEFAULT '' COMMENT '客户区域',
  `CONSIGNEE_ADDRESS` varchar(400) NOT NULL DEFAULT '' COMMENT '客户地址',
  `CONSIGNEE_POST_CODE` varchar(20) NOT NULL DEFAULT '' COMMENT '行政编码',
  `PAY_TYPE` varchar(10) NOT NULL DEFAULT '' COMMENT '支付方式',
  `SHOPS_TYPE` varchar(4) NOT NULL DEFAULT '1' COMMENT '是否为自营 (1 自营,2 商城)',
  `TOTAL_PRICE` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '总价格',
  `WEIGHT` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '总重量',
  `SHIPMENT` varchar(4) NOT NULL DEFAULT '' COMMENT '配送方式',
  `DELIVERY_PRO` varchar(40) NOT NULL DEFAULT '' COMMENT '配送属性',
  `INVOICE` varchar(4) NOT NULL DEFAULT '0' COMMENT '是否打印发票(0 不需要,1需要)',
  `IV_NAME` varchar(200) NOT NULL DEFAULT '' COMMENT '发票抬头',
  `ORDER_TYPE` varchar(4) NOT NULL DEFAULT '1' COMMENT '订单类型(1 配送单,2取件单)',
  `LOCATION_ID` bigint(18) NOT NULL DEFAULT '0' COMMENT '站点ID',
  `LOCATION_NAME` varchar(200) NOT NULL DEFAULT '' COMMENT '站点名称',
  `DELIVERY_DEALER_ID` bigint(18) NOT NULL DEFAULT '0' COMMENT '配送商ID',
  `DELIVERY_DEALER_CODE` varchar(20) NOT NULL DEFAULT '' COMMENT '配送商CODE',
  `DELIVERY_DEALER_NAME` varchar(200) NOT NULL DEFAULT '' COMMENT '配送商名称',
  `DG_CODE` varchar(20) NOT NULL DEFAULT '' COMMENT '出货者(门店或仓库)',
  `CREATED_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `MODIFY_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `CREATED_ID` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人ID',
  `MODIFY_ID` bigint(20) NOT NULL DEFAULT '0' COMMENT '修改人ID',
  `ORDER_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '订单生成时间 ',
  `MEMGUID` varchar(200) NOT NULL DEFAULT '' COMMENT '客户ID',
  `PACKAGE_NO` varchar(100) NOT NULL DEFAULT '' COMMENT '包裹号',
  `MAIL_NO` varchar(100) NOT NULL DEFAULT '' COMMENT '运单号',
  `BIG_PEN` varchar(100) NOT NULL DEFAULT '' COMMENT '大头笔信息',
  `PARSE_SUCCESS` varchar(4) NOT NULL DEFAULT '0' COMMENT '0,未解析,1,解析成功.2,默认配送商,3无默认配送商',
  `SHOP_NUMBER` varchar(20) NOT NULL DEFAULT '' COMMENT '门店号',
  `IS_SEND_MSG` bigint(4) NOT NULL DEFAULT '0' COMMENT '是否发送信息,0未发送,1已发送',
  `SITE_PARSE_SUCCESS` varchar(4) NOT NULL DEFAULT '0' COMMENT '3PL专用，3PL站点解析状态，0未处理；1	解析成功；2	网络超时；3	返回报文异常；4	解析接口服务异常；5	解析失败',
  PRIMARY KEY (`ID`),
  KEY `IDX_PARSE_SUCCESS` (`PARSE_SUCCESS`),
  KEY `IDX_IS_SEND_MSG` (`IS_SEND_MSG`),
  KEY `IDX_CUSTOMER_ORDER_CODE` (`CUSTOMER_ORDER_CODE`),
  KEY `IDX_CANCEL_NO` (`CANCEL_NO`),
  KEY `IDX_DG_CODE` (`DG_CODE`),
  KEY `IDX_SHOP_NUMBER` (`SHOP_NUMBER`),
  KEY `IDX_CREATE_TIME` (`CREATED_TIME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for customer_order_info
-- ----------------------------
DROP TABLE IF EXISTS `customer_order_info`;
CREATE TABLE `customer_order_info` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `MEM_GUID` varchar(400) NOT NULL DEFAULT '' COMMENT '客户ID',
  `CUSTOMER_CODE` varchar(80) NOT NULL DEFAULT '' COMMENT '客户订单号',
  `RUN_NUMBER` int(4) NOT NULL DEFAULT '0' COMMENT '执行次数据',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `MODIFY_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `STATUS` int(4) NOT NULL DEFAULT '0' COMMENT '是否处理.0未处理,1已处理',
  `IS_SEND_MSG` int(4) NOT NULL DEFAULT '0' COMMENT '是否发送信息.0未发送,1,已发送',
  `SITE_PARSE_STATUS` int(4) NOT NULL DEFAULT '0' COMMENT '0未处理，1已处理',
  PRIMARY KEY (`ID`),
  KEY `IDX_MEME_GUID` (`MEM_GUID`(255)),
  KEY `IDX_CUSTOMER_CODE` (`CUSTOMER_CODE`),
  KEY `IDX_SATUS` (`STATUS`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for customer_order_log
-- ----------------------------
DROP TABLE IF EXISTS `customer_order_log`;
CREATE TABLE `customer_order_log` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `CUSTOMER_ORDER_ID` bigint(18) NOT NULL DEFAULT '0' COMMENT '客户订单id',
  `CUSTOMER_ORDER_CODE` varchar(40) NOT NULL DEFAULT '' COMMENT '客户订单号',
  `PARSE_RET` int(11) NOT NULL DEFAULT '0' COMMENT '0 解析成功 1数据库配置运能不足 2solr无法解析',
  `PARSE_TIME` int(11) NOT NULL DEFAULT '0' COMMENT '解析时间',
  `PARSE_REASON` varchar(200) NOT NULL DEFAULT '' COMMENT '解析原因',
  `CONSIGNEE_ADDRESS` varchar(255) NOT NULL DEFAULT '' COMMENT '四级、地址',
  `DELIVERY_DEALER_NAME` varchar(200) NOT NULL DEFAULT '' COMMENT '配送商名称',
  `STATUS` int(11) NOT NULL DEFAULT '0' COMMENT '是否处理 0 为处理 1已处理',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2656 DEFAULT CHARSET=utf8 COMMENT='配送商解析订单日志表';

-- ----------------------------
-- Table structure for delivery_bill
-- ----------------------------
DROP TABLE IF EXISTS `delivery_bill`;
CREATE TABLE `delivery_bill` (
  `id` bigint(20) NOT NULL COMMENT '路单表id',
  `code` varchar(32) DEFAULT NULL COMMENT '路单号',
  `state` varchar(32) DEFAULT NULL COMMENT '状态：初始化，已调度，已经发运',
  `location_id` bigint(20) DEFAULT NULL COMMENT '配送站id',
  `staff_id` bigint(20) DEFAULT NULL COMMENT '配送员id',
  `order_num` int(11) DEFAULT NULL COMMENT '母单数量',
  `sub_order_num` int(11) DEFAULT NULL COMMENT '子单数量',
  `pick_up_order_num` int(11) DEFAULT NULL COMMENT '取件单数',
  `print` tinyint(4) DEFAULT NULL COMMENT '是否打印',
  `send_time` timestamp NULL DEFAULT NULL COMMENT '发运时间',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建人',
  `MODIFIER_ID` bigint(20) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIER` varchar(64) DEFAULT NULL COMMENT '修改人',
  `MODIFIED_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='路单表';

-- ----------------------------
-- Table structure for delivery_bill_detail
-- ----------------------------
DROP TABLE IF EXISTS `delivery_bill_detail`;
CREATE TABLE `delivery_bill_detail` (
  `id` bigint(20) NOT NULL COMMENT '路单明细id',
  `delivery_bill_id` bigint(20) DEFAULT NULL COMMENT '路单表id',
  `order_id` bigint(20) DEFAULT NULL COMMENT '母单id',
  `sub_order_id` bigint(20) DEFAULT NULL COMMENT '子单id',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建人',
  `MODIFIER_ID` bigint(20) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIER` varchar(64) DEFAULT NULL COMMENT '修改人',
  `MODIFIED_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='路单详情表';

-- ----------------------------
-- Table structure for delivery_staff
-- ----------------------------
DROP TABLE IF EXISTS `delivery_staff`;
CREATE TABLE `delivery_staff` (
  `ID` bigint(20) NOT NULL,
  `CODE` varchar(32) NOT NULL COMMENT '工号',
  `NAME` varchar(32) NOT NULL COMMENT '姓名',
  `GENDER` tinyint(4) DEFAULT NULL COMMENT '性别',
  `ID_CARD` varchar(32) NOT NULL COMMENT '身份证号',
  `EMPLOYED_DATE` date NOT NULL COMMENT '入职日期',
  `TELEPHONE` varchar(32) DEFAULT NULL COMMENT '联系电话',
  `DEPOT_ID` bigint(20) NOT NULL COMMENT '所属站点',
  `COMPANY_ID` bigint(20) NOT NULL COMMENT '公司ID',
  `QUIT_DATE` date DEFAULT NULL COMMENT '离职日期',
  `NATIVE_PROVINCE` varchar(64) DEFAULT NULL COMMENT '籍贯（省）',
  `NATIVE_CITY` varchar(64) DEFAULT NULL COMMENT '籍贯（市）',
  `NATIVE_ADDRESS` varchar(512) DEFAULT NULL COMMENT '户籍地址',
  `CURRENT_ADDRESS` varchar(512) DEFAULT NULL COMMENT '目前住址',
  `IS_DELETED` tinyint(4) DEFAULT NULL COMMENT '是否失效',
  `CREATOR_ID` bigint(20) NOT NULL COMMENT '创建人ID',
  `CREATOR` varchar(64) NOT NULL COMMENT '创建人姓名',
  `CREATED_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `MODIFIER_ID` bigint(20) NOT NULL COMMENT '修改人ID',
  `MODIFIER` varchar(64) NOT NULL COMMENT '修改人姓名',
  `MODIFIED_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '最后修改时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_DELIVERY_STAFF` (`CODE`,`COMPANY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='配送员表';

-- ----------------------------
-- Table structure for four_area
-- ----------------------------
DROP TABLE IF EXISTS `four_area`;
CREATE TABLE `four_area` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `area_code` varchar(32) DEFAULT NULL COMMENT '区域编码',
  `area_name` varchar(64) DEFAULT NULL COMMENT '区域名称',
  `province` varchar(64) DEFAULT NULL COMMENT '省',
  `city` varchar(64) DEFAULT NULL COMMENT '市',
  `district` varchar(256) DEFAULT NULL COMMENT '区县',
  `location_id` varchar(192) DEFAULT NULL COMMENT '所属站点',
  `is_virtual` varchar(32) DEFAULT '0' COMMENT '是否虚拟(0:自配送 1:2PL 2:虚拟)',
  `is_default` tinyint(4) DEFAULT '0' COMMENT '是否默认',
  `modified_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建人',
  `modifier` varchar(32) DEFAULT NULL COMMENT '修改人',
  `creator_id` bigint(20) DEFAULT NULL,
  `modifier_id` bigint(20) DEFAULT NULL,
  `is_deleted` tinyint(4) DEFAULT '0' COMMENT '0:未删除',
  PRIMARY KEY (`id`),
  KEY `FOUR_AREA_AREA_CODE` (`area_code`),
  KEY `FOUR_AREA_PROVINCE` (`province`),
  KEY `FOUR_AREA_CITY` (`city`),
  KEY `FOUR_AREA_DISTRICT` (`district`(255)),
  KEY `IDX_FOUR_AREA_NAME` (`area_name`)
) ENGINE=InnoDB AUTO_INCREMENT=61052 DEFAULT CHARSET=utf8 COMMENT='四级区域维护表';

-- ----------------------------
-- Table structure for four_area_detail
-- ----------------------------
DROP TABLE IF EXISTS `four_area_detail`;
CREATE TABLE `four_area_detail` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `area_code` varchar(32) DEFAULT NULL COMMENT '区域编码',
  `area_name` varchar(64) DEFAULT NULL COMMENT '区域名称',
  `province` varchar(64) DEFAULT NULL COMMENT '省',
  `city` varchar(64) DEFAULT NULL COMMENT '市',
  `district` varchar(256) DEFAULT NULL COMMENT '区县',
  `location_id` varchar(192) DEFAULT NULL COMMENT '所属站点',
  `is_virtual` varchar(32) DEFAULT '0' COMMENT '是否虚拟(0:自配送 1:2PL 2:虚拟)',
  `is_default` tinyint(4) DEFAULT '0' COMMENT '是否默认',
  `modified_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建人',
  `modifier` varchar(32) DEFAULT NULL COMMENT '修改人',
  `creator_id` bigint(20) DEFAULT NULL,
  `modifier_id` bigint(20) DEFAULT NULL,
  `is_deleted` tinyint(4) DEFAULT '0' COMMENT '0:未删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for four_area_zhj
-- ----------------------------
DROP TABLE IF EXISTS `four_area_zhj`;
CREATE TABLE `four_area_zhj` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `area_code` varchar(32) DEFAULT NULL COMMENT '区域编码',
  `area_name` varchar(64) DEFAULT NULL COMMENT '区域名称',
  `province` varchar(64) DEFAULT NULL COMMENT '省',
  `city` varchar(64) DEFAULT NULL COMMENT '市',
  `district` varchar(256) DEFAULT NULL COMMENT '区县',
  `location_id` varchar(192) DEFAULT NULL COMMENT '所属站点',
  `is_virtual` varchar(32) DEFAULT '0' COMMENT '是否虚拟(0:自配送 1:2PL 2:虚拟)',
  `modified_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建人',
  `modifier` varchar(32) DEFAULT NULL COMMENT '修改人',
  `creator_id` bigint(20) DEFAULT NULL,
  `modifier_id` bigint(20) DEFAULT NULL,
  `is_deleted` tinyint(4) DEFAULT '0' COMMENT '0:未删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for geo_address_detail
-- ----------------------------
DROP TABLE IF EXISTS `geo_address_detail`;
CREATE TABLE `geo_address_detail` (
  `id` bigint(20) NOT NULL,
  `ZIP_CODE` varchar(32) DEFAULT NULL,
  `STATE` varchar(64) DEFAULT NULL,
  `CITY` varchar(64) DEFAULT NULL,
  `DISTRICT` varchar(64) DEFAULT NULL,
  `ADDRESS` varchar(256) DEFAULT NULL,
  `JD` varchar(192) DEFAULT NULL,
  `COUNTRY` varchar(32) DEFAULT NULL,
  `LOGIC_AREA` varchar(256) DEFAULT NULL COMMENT '谁创建',
  `EXT1` varchar(64) DEFAULT NULL,
  `END_STREET_NO` int(11) DEFAULT NULL,
  `START_STREET_NO` int(11) DEFAULT NULL,
  `LAST_MODIFIED_TIME` timestamp NULL DEFAULT NULL,
  `EVEN_FLAG` tinyint(4) DEFAULT NULL COMMENT '奇偶门牌号标记默认-1不区分，0偶数，1奇数',
  `CREATE_TIME` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_BY` bigint(20) DEFAULT NULL,
  `LAST_MODIFIED_BY` bigint(20) DEFAULT NULL COMMENT '谁修改',
  `IS_DELETED` tinyint(4) DEFAULT '0' COMMENT '0:未删除 1:删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for geo_info
-- ----------------------------
DROP TABLE IF EXISTS `geo_info`;
CREATE TABLE `geo_info` (
  `ID` bigint(25) NOT NULL DEFAULT '0',
  `CODE` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `PCODE` varchar(255) DEFAULT NULL,
  `TYPE` tinyint(4) DEFAULT NULL COMMENT '0 洲 1国家，2 省 3：市 4：区县）5:乡镇',
  `CENTER` varchar(4) DEFAULT NULL COMMENT '归属地:NEC东北  NC华北  EC华东  SC华南   CC华中',
  `PARENT_ID` bigint(20) DEFAULT NULL COMMENT '父区域ID',
  `IS_DELETED` tinyint(4) DEFAULT NULL COMMENT '是否失效 0:未失效 1:失效',
  `CREATOR_ID` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `CREATOR` varchar(64) DEFAULT NULL COMMENT '创建者',
  `CREATED_TIME` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `MODIFIER_ID` bigint(20) DEFAULT NULL,
  `MODIFIER` varchar(64) DEFAULT NULL,
  `MODIFIED_TIME` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `SORT_ID` tinyint(4) DEFAULT NULL,
  `SHORT_NAME` varchar(32) DEFAULT NULL COMMENT '简称',
  `TREE_CODE` varchar(64) DEFAULT NULL COMMENT '结构树',
  PRIMARY KEY (`ID`),
  KEY `GEO_INFO_CSCODE` (`CODE`),
  KEY `GEO_INFO_PCODE` (`PCODE`),
  KEY `GEO_INFO_PAREND_ID` (`PARENT_ID`),
  KEY `GEO_INFO_CODE` (`SHORT_NAME`),
  KEY `IDX_GEO_INFO_NAME` (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='地理信息表';

-- ----------------------------
-- Table structure for geo_info_copy
-- ----------------------------
DROP TABLE IF EXISTS `geo_info_copy`;
CREATE TABLE `geo_info_copy` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `CODE` varchar(128) DEFAULT NULL COMMENT '内部代码',
  `P_CODE` varchar(128) DEFAULT NULL COMMENT '父CODE',
  `NAME` varchar(64) DEFAULT NULL COMMENT '名称',
  `TYPE` tinyint(4) DEFAULT NULL COMMENT '0 洲 1国家，2 省 3：市 4：区县）5:乡镇',
  `PARENT_ID` bigint(20) DEFAULT NULL COMMENT '父区域ID',
  `IS_DELETED` tinyint(4) DEFAULT '0' COMMENT '是否失效',
  `CREATOR_ID` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `CREATOR` varchar(64) DEFAULT NULL COMMENT '创建人姓名',
  `CREATED_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `MODIFIER_ID` bigint(20) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIER` varchar(64) DEFAULT NULL COMMENT '修改人姓名',
  `MODIFIED_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `SORT_ID` tinyint(4) DEFAULT NULL COMMENT '排序',
  `CENTER` int(11) DEFAULT NULL COMMENT '归属中心,后续用于分库,分表',
  `SHORT_NAME` varchar(32) DEFAULT NULL COMMENT '简称',
  `TREE_CODE` varchar(64) DEFAULT NULL COMMENT '结构树',
  PRIMARY KEY (`ID`),
  KEY `IDX_PARENT_ID` (`PARENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='地理区域';

-- ----------------------------
-- Table structure for geo_info_detail
-- ----------------------------
DROP TABLE IF EXISTS `geo_info_detail`;
CREATE TABLE `geo_info_detail` (
  `ID` bigint(25) NOT NULL DEFAULT '0',
  `CODE` varchar(255) NOT NULL DEFAULT '',
  `NAME` varchar(255) NOT NULL DEFAULT '',
  `PCODE` varchar(255) NOT NULL DEFAULT '',
  `TYPE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0 洲 1国家，2 省 3：市 4：区县）5:乡镇',
  `CENTER` varchar(4) NOT NULL DEFAULT '' COMMENT '归属地:NEC东北  NC华北  EC华东  SC华南   CC华中',
  `PARENT_ID` bigint(20) NOT NULL DEFAULT '0' COMMENT '父区域ID',
  `IS_DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否失效 0:未失效 1:失效',
  `CREATOR_ID` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人ID',
  `CREATOR` varchar(64) NOT NULL DEFAULT '' COMMENT '创建者',
  `CREATED_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `MODIFIER_ID` bigint(20) NOT NULL DEFAULT '0',
  `MODIFIER` varchar(64) NOT NULL DEFAULT '',
  `MODIFIED_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `SORT_ID` tinyint(4) NOT NULL DEFAULT '0',
  `SHORT_NAME` varchar(32) NOT NULL DEFAULT '' COMMENT '简称',
  `TREE_CODE` varchar(64) NOT NULL DEFAULT '' COMMENT '结构树',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for geo_info_district
-- ----------------------------
DROP TABLE IF EXISTS `geo_info_district`;
CREATE TABLE `geo_info_district` (
  `NAME` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for geo_info_search
-- ----------------------------
DROP TABLE IF EXISTS `geo_info_search`;
CREATE TABLE `geo_info_search` (
  `ID` bigint(25) NOT NULL DEFAULT '0',
  `CODE` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `PCODE` varchar(255) DEFAULT NULL,
  `TYPE` tinyint(4) DEFAULT NULL COMMENT '0 洲 1国家，2 省 3：市 4：区县）5:乡镇',
  `CENTER` varchar(4) DEFAULT NULL COMMENT '归属地:NEC东北  NC华北  EC华东  SC华南   CC华中',
  `PARENT_ID` bigint(20) DEFAULT NULL COMMENT '父区域ID',
  `IS_DELETED` tinyint(4) DEFAULT NULL COMMENT '是否失效 0:未失效 1:失效',
  `CREATOR_ID` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `CREATOR` varchar(64) DEFAULT NULL COMMENT '创建者',
  `CREATED_TIME` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `MODIFIER_ID` bigint(20) DEFAULT NULL,
  `MODIFIER` varchar(64) DEFAULT NULL,
  `MODIFIED_TIME` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `SORT_ID` tinyint(4) DEFAULT NULL,
  `SHORT_NAME` varchar(32) DEFAULT NULL COMMENT '简称',
  `TREE_CODE` varchar(64) DEFAULT NULL COMMENT '结构树'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for interface_address
-- ----------------------------
DROP TABLE IF EXISTS `interface_address`;
CREATE TABLE `interface_address` (
  `address_code` varchar(16) DEFAULT NULL COMMENT '编码',
  `address_name` varchar(64) DEFAULT NULL COMMENT '名称',
  `short_name` varchar(16) DEFAULT NULL COMMENT '简称',
  `address_key` varchar(16) DEFAULT NULL COMMENT '密钥',
  `url` varchar(128) DEFAULT NULL COMMENT '地址',
  `remarks` varchar(256) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='3PL信息表';

-- ----------------------------
-- Table structure for interface_log
-- ----------------------------
DROP TABLE IF EXISTS `interface_log`;
CREATE TABLE `interface_log` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `CUTOMER_ID` varchar(32) DEFAULT NULL COMMENT '委托方【飞牛，RT】',
  `INTERFACE_NAME` varchar(64) DEFAULT NULL COMMENT '接口名称',
  `FILE_NAME` longtext COMMENT '文件名',
  `FILE_TYPE` varchar(6) DEFAULT NULL COMMENT '文件类型【1.配送单,2.配送单商品,3.取件单,4.取件单商品】',
  `REQ_XML` longtext COMMENT '请求报文',
  `RES_XML` longtext COMMENT '返回报文',
  `STATUS` int(6) DEFAULT NULL COMMENT '处理状态',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='接口日志表';

-- ----------------------------
-- Table structure for mall_dict_area_copy
-- ----------------------------
DROP TABLE IF EXISTS `mall_dict_area_copy`;
CREATE TABLE `mall_dict_area_copy` (
  `ID` bigint(25) NOT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `PCODE` varchar(255) DEFAULT NULL,
  `TYPE` tinyint(4) DEFAULT NULL COMMENT '0 洲 1国家，2 省 3：市 4：区县）5:乡镇',
  `PARENT_ID` bigint(20) DEFAULT NULL COMMENT '父区域ID',
  `IS_DELETED` tinyint(4) DEFAULT NULL COMMENT '是否失效 0:未失效 1:失效',
  `CREATOR_ID` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `CREATOR` varchar(64) DEFAULT NULL COMMENT '创建者',
  `CREATED_TIME` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `MODIFIER_ID` bigint(20) DEFAULT NULL,
  `MODIFIER` varchar(64) DEFAULT NULL,
  `MODIFIED_TIME` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `SORT_ID` tinyint(4) DEFAULT NULL,
  `CENTER` int(11) DEFAULT NULL COMMENT '归属地',
  `SHORT_NAME` varchar(32) DEFAULT NULL COMMENT '简称',
  `TREE_CODE` varchar(64) DEFAULT NULL COMMENT '结构树',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for map_point
-- ----------------------------
DROP TABLE IF EXISTS `map_point`;
CREATE TABLE `map_point` (
  `POINTID` bigint(20) NOT NULL COMMENT '主键ID',
  `POLYGONID` bigint(20) NOT NULL DEFAULT '0' COMMENT '遮罩面板ID',
  `LNG` varchar(50) NOT NULL DEFAULT '' COMMENT '经度',
  `LAT` varchar(50) NOT NULL DEFAULT '' COMMENT '纬度',
  `SORTNO` int(5) NOT NULL DEFAULT '0' COMMENT '顺序',
  PRIMARY KEY (`POINTID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for map_polygon
-- ----------------------------
DROP TABLE IF EXISTS `map_polygon`;
CREATE TABLE `map_polygon` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `SITEMARKID` bigint(20) NOT NULL DEFAULT '0' COMMENT '站点标注点ID',
  `STROKECOLOR` varchar(64) NOT NULL DEFAULT '' COMMENT '边线颜色',
  `FILLCOLOR` varchar(64) NOT NULL DEFAULT '' COMMENT '填充颜色',
  `ISENABLE` bigint(5) NOT NULL DEFAULT '1' COMMENT '是否启用(1-启用,0-禁用)',
  `CREATEID` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人编号',
  `CREATOR` varchar(64) NOT NULL COMMENT '创建人',
  `CREATETIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `MODIFIERID` bigint(20) NOT NULL COMMENT '修改人编号',
  `MODIFIER` varchar(64) NOT NULL COMMENT '修改人',
  `MODIFYTIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for map_sitemark
-- ----------------------------
DROP TABLE IF EXISTS `map_sitemark`;
CREATE TABLE `map_sitemark` (
  `MARKID` bigint(20) NOT NULL COMMENT '主键',
  `LOCATIONID` bigint(20) NOT NULL DEFAULT '0' COMMENT '站点ID',
  `ADDRESS` varchar(200) NOT NULL DEFAULT '' COMMENT '地址',
  `LNG` varchar(50) NOT NULL DEFAULT '' COMMENT '经度',
  `LAT` varchar(50) NOT NULL DEFAULT '' COMMENT '纬度',
  `ISENABLE` bigint(5) NOT NULL DEFAULT '1' COMMENT '是否启用(1-启用,0-禁用)',
  `CREATEID` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人编号',
  `CREATOR` varchar(64) NOT NULL COMMENT '创建人',
  `CREATETIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `MODIFIERID` bigint(20) NOT NULL COMMENT '修改人编号',
  `MODIFIER` varchar(64) NOT NULL COMMENT '修改人',
  `MODIFYTIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`MARKID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail` (
  `ID` bigint(20) NOT NULL COMMENT '业务主键Id',
  `ORDER_HEADER_ID` bigint(20) DEFAULT NULL COMMENT '母单id',
  `ORDER_ID` varchar(64) DEFAULT NULL COMMENT '母单号',
  `ORDER_SUB_ID` varchar(64) DEFAULT NULL COMMENT '子单号',
  `ORDER_TYPE` int(2) DEFAULT NULL COMMENT '订单类型',
  `CUSTOMER_ORD_CODE` varchar(64) DEFAULT NULL COMMENT '客户订单号',
  `LOGISTIC_TYPE` tinyint(4) DEFAULT NULL COMMENT '配送产品类别（1，冷冻 2，冷藏 3，普通）',
  `PACKAGE_NO` varchar(64) DEFAULT NULL COMMENT '箱号',
  `PACKAGE_LENGTH` decimal(10,2) DEFAULT NULL COMMENT '箱长',
  `PACKAGE_WIDTH` decimal(10,2) DEFAULT NULL COMMENT '箱宽',
  `PACKAGE_HEIGHT` decimal(10,2) DEFAULT NULL COMMENT '箱高',
  `PACKAGE_WEIGHT` decimal(10,2) DEFAULT NULL COMMENT '箱重量',
  `PACKAGE_VOLUME` decimal(10,2) DEFAULT NULL COMMENT '箱体积',
  `ORD_CREATE_DATE` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单日期',
  `ORD_STATUS` int(11) DEFAULT NULL COMMENT '子订单状态',
  `REMARKS` varchar(128) DEFAULT NULL COMMENT '备注',
  `IS_BIG` tinyint(4) DEFAULT NULL COMMENT '是否大材',
  `CREATOR_ID` bigint(20) DEFAULT NULL COMMENT '创建人ID（预留字段）',
  `CREATOR` varchar(64) DEFAULT NULL COMMENT '创建人姓名（预留字段）',
  `CREATED_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（预留字段）',
  `MODIFIER_ID` bigint(20) DEFAULT NULL COMMENT '修改人ID（预留字段）',
  `MODIFIER` varchar(64) DEFAULT NULL COMMENT '修改人姓名（预留字段）',
  `MODIFIED_TIME` timestamp NULL DEFAULT NULL COMMENT '最后修改时间（预留字段）',
  `ORDER_M_STATUS` int(8) DEFAULT NULL COMMENT '订单发送状态',
  `DELIVERY_STATE` int(11) DEFAULT NULL COMMENT '配送状态',
  `DELIVERY_STAFF_ID` bigint(20) DEFAULT NULL COMMENT '配送员id',
  `DEPOT_ID` bigint(20) DEFAULT NULL COMMENT '站点id',
  `ARRIVE_DEPOT_TIME` timestamp NULL DEFAULT NULL COMMENT '入配送站时间',
  `LEAVE_DEPOT_TIME` timestamp NULL DEFAULT NULL COMMENT '出配送站时间',
  `LEAVE_DC_TIME` timestamp NULL DEFAULT NULL COMMENT '出分拣中心时间',
  `RETURN_TIME` timestamp NULL DEFAULT NULL COMMENT '回单时间',
  `REJECT_REASON` varchar(256) NOT NULL DEFAULT '' COMMENT '拒收|失败原因',
  `DETAINED_REASON` varchar(256) NOT NULL DEFAULT '' COMMENT '滞留原因',
  `RECEIVE_TIME` timestamp NULL DEFAULT NULL COMMENT '仓库回收时间',
  `REFUND_TIME` timestamp NULL DEFAULT NULL COMMENT '站点退货时间',
  `ARRIVE_DC_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '入分拣时间',
  `TRANS_CENTER_ID` bigint(20) NOT NULL DEFAULT '0' COMMENT '二级中转id',
  `TO_TRANS_CENTER_TIME` varchar(32) NOT NULL DEFAULT '' COMMENT '入二级中转时间',
  `LEAVE_TRANS_CENTER_TIME` varchar(32) NOT NULL DEFAULT '' COMMENT '出二级中转时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ORDER_SUB_ID` (`ORDER_SUB_ID`),
  KEY `IDX_GOODS_LOGISTIC_CODE` (`ORDER_HEADER_ID`),
  KEY `idx_od_csid` (`ORDER_SUB_ID`),
  KEY `idx_od_oid` (`ORDER_ID`),
  KEY `IDX_STAFFID` (`DELIVERY_STAFF_ID`),
  KEY `IDX_DEPOTID` (`DEPOT_ID`),
  KEY `IDX_DELIVERYSTATE` (`DELIVERY_STATE`),
  KEY `IDX_LEAVEDEPOTTIME` (`LEAVE_DEPOT_TIME`),
  KEY `IDX_RETURNTIME` (`RETURN_TIME`),
  KEY `IDX_LEAVEDCTIME` (`LEAVE_DC_TIME`),
  KEY `IDX_ARRIVEDEPOTTIME` (`ARRIVE_DEPOT_TIME`),
  KEY `IDX_CREATEDTIME` (`CREATED_TIME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='子单表';

-- ----------------------------
-- Table structure for order_goods
-- ----------------------------
DROP TABLE IF EXISTS `order_goods`;
CREATE TABLE `order_goods` (
  `ID` bigint(20) NOT NULL DEFAULT '0' COMMENT '业务主键Id',
  `LOGISTIC_CODE` varchar(64) DEFAULT NULL COMMENT '母单号',
  `LOGISTIC_SUB_CODE` varchar(64) DEFAULT NULL COMMENT '子单号',
  `CUSTOMER_ORD_CODE` varchar(64) DEFAULT NULL COMMENT '客户订单号',
  `GOODS_NAME` varchar(256) DEFAULT NULL COMMENT '商品名称',
  `GOODS_SIZE` varchar(64) DEFAULT NULL COMMENT '商品尺寸',
  `GOODS_COLOR` varchar(32) DEFAULT NULL COMMENT '子订颜色',
  `GOODS_WEIGHT` decimal(10,2) DEFAULT NULL COMMENT '商品重量',
  `IS_BIG_FLAG` char(1) DEFAULT NULL COMMENT '是否为大件',
  `GOODS_QUANTITY` decimal(10,2) DEFAULT NULL COMMENT '商品数量',
  `CUSTOMER_ID` varchar(20) DEFAULT NULL COMMENT '客户在TMS唯一ID（物流上编号）',
  `CUSTOMER_CODE` varchar(32) DEFAULT NULL COMMENT '客户code（物流上标识）',
  `CREATE_DATE` timestamp NULL DEFAULT NULL COMMENT '入库时间',
  `CUSTOMER_DOOR` int(8) DEFAULT NULL COMMENT '4位门店号',
  `ALOGISTICS_DAY` varchar(64) DEFAULT NULL COMMENT '委派物流日',
  `REMARK` varchar(64) DEFAULT NULL COMMENT '说明',
  `RETURN_REASON` varchar(256) NOT NULL DEFAULT '' COMMENT '商品退件原因',
  PRIMARY KEY (`ID`),
  KEY `IDX_GOODS_LOGISTIC_CODE` (`LOGISTIC_CODE`),
  KEY `IDX_HEADER_CUSTOMORDERCODE` (`CUSTOMER_ORD_CODE`),
  KEY `ORDER_GOODS_SUB_CODE` (`LOGISTIC_SUB_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品信息表';

-- ----------------------------
-- Table structure for order_header
-- ----------------------------
DROP TABLE IF EXISTS `order_header`;
CREATE TABLE `order_header` (
  `AMOUNT` decimal(10,2) DEFAULT NULL COMMENT '货款/取件单货款',
  `APPOINT_DELIVERY_DATE` timestamp NULL DEFAULT NULL,
  `APPOINT_DELIVERY_END` varchar(64) DEFAULT NULL COMMENT '指定到货结束时段，HHMM (24小时制)，e.g. 1200，客人可以指定收到货的最晚结束时间',
  `APPOINT_DELIVERY_START` varchar(64) DEFAULT NULL COMMENT '指定开始结束时段，HHMM (24小时制)，e.g. 1200，客人可以指定收到货的最晚结束时间',
  `ARRIVE_DC_TIME` varchar(64) DEFAULT NULL COMMENT '入分拣中心时间',
  `ARRIVE_DEPOT_TIME` varchar(64) DEFAULT NULL COMMENT '入站时间',
  `CARRIER_ID` bigint(20) DEFAULT NULL COMMENT '配送商ID',
  `CARTON_QUANTITY` int(11) DEFAULT NULL COMMENT '件数（相同主单号的件数）',
  `CENTER_SHIPPING_ID` bigint(20) DEFAULT NULL COMMENT '分拨中心ID',
  `CODE` varchar(64) DEFAULT NULL COMMENT '子单号（托运子单号）',
  `COLLECTED_AMOUNT` decimal(10,2) DEFAULT NULL COMMENT '代收款',
  `COLLECTED_AMOUNT2` decimal(10,2) DEFAULT NULL COMMENT '收款金额/退现金金额',
  `COMPANY_ID` bigint(20) DEFAULT NULL COMMENT '公司ID',
  `CONSIGNEE` varchar(64) DEFAULT NULL COMMENT '收货人',
  `CONSIGNEE_ADDRESS` varchar(256) DEFAULT NULL COMMENT '收件人详细地址（送货地址）',
  `CONSIGNEE_AREA` varchar(64) DEFAULT NULL COMMENT '收件人区',
  `CONSIGNEE_CITY` varchar(64) DEFAULT NULL COMMENT '收件人市',
  `CONSIGNEE_EMAIL` varchar(64) DEFAULT NULL COMMENT '收货人邮件',
  `CONSIGNEE_MOBILE` varchar(64) DEFAULT NULL COMMENT '收货人手机',
  `CONSIGNEE_PROVINCE` varchar(64) DEFAULT NULL COMMENT '收件人省份',
  `CONSIGNEE_TELEPHONE` varchar(64) DEFAULT NULL COMMENT '收货人电话',
  `CONSIGNEE_ZIPCODE` varchar(64) DEFAULT NULL COMMENT '收件邮编',
  `CONSIGNOR` varchar(64) DEFAULT NULL COMMENT '发件人',
  `CONSIGNOR_ADDRESS` varchar(256) DEFAULT NULL COMMENT '发件人详细地址',
  `CONSIGNOR_AREA` varchar(64) DEFAULT NULL COMMENT '发件人区域',
  `CONSIGNOR_CITY` varchar(64) DEFAULT NULL COMMENT '发件人城市',
  `CONSIGNOR_EMAIL` varchar(64) DEFAULT NULL COMMENT '发件人邮箱',
  `CONSIGNOR_MOBILE` varchar(64) DEFAULT NULL COMMENT '发件人手机',
  `CONSIGNOR_PROVINCE` varchar(64) DEFAULT NULL COMMENT '发件人省份',
  `CONSIGNOR_TELEPHONE` varchar(64) DEFAULT NULL COMMENT '发件人电话',
  `CONSIGNOR_ZIPCODE` varchar(64) DEFAULT NULL COMMENT '发件人邮编',
  `CREATED_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间(预留)',
  `CREATOR` varchar(64) DEFAULT NULL COMMENT '创建人姓名(预留)',
  `CREATOR_ID` bigint(20) DEFAULT NULL COMMENT '创建人ID(预留)',
  `CUSTOMER_CODE` varchar(64) DEFAULT NULL COMMENT '物流商编号（公司赋予物流商的客户编号）',
  `CUSTOMER_ID` bigint(20) DEFAULT NULL COMMENT '委托方ID',
  `CUSTOMER_NAME` varchar(64) DEFAULT NULL COMMENT '物流商标识（物流商ID）',
  `CUSTOMER_ORDER_CODE` varchar(64) DEFAULT NULL COMMENT '客户订单号',
  `DELIVERY_MODE` int(11) DEFAULT NULL COMMENT '配送方式',
  `DELIVERY_STAFF_ID` bigint(20) DEFAULT NULL COMMENT '配送员ID',
  `DELIVERY_STATE` int(11) DEFAULT NULL COMMENT '配送状态',
  `DEPOT_ID` bigint(20) DEFAULT NULL COMMENT '配送站点',
  `DETAINED_REASON` int(11) DEFAULT NULL COMMENT '滞留原因',
  `DOMAIN_ID` bigint(20) DEFAULT NULL COMMENT '拒收原因',
  `EXPECTED_ARRIVE_TIME` varchar(64) DEFAULT NULL COMMENT '预定配达时段',
  `EXPECTED_PICKUP_TIME` varchar(64) DEFAULT NULL COMMENT '预定取件时段',
  `FROM_LOCATION_ID` bigint(20) DEFAULT NULL COMMENT '发货地',
  `GOODS_TYPE` int(11) DEFAULT NULL COMMENT '配送产品类别（1，冷冻 2，冷藏 3，普通）',
  `ID` bigint(20) NOT NULL COMMENT '业务主键Id',
  `INSURANCE_VALUE` decimal(10,2) DEFAULT NULL COMMENT '保险价值',
  `IS_BIG` tinyint(4) DEFAULT NULL COMMENT '是否大材',
  `IS_COLLECTION` varchar(64) DEFAULT NULL COMMENT '是否代收款.Y,N',
  `IS_NEED_INVOICE` tinyint(4) DEFAULT NULL COMMENT '是否取回发票',
  `IS_PICKUP` tinyint(4) DEFAULT NULL COMMENT '是否上门取件',
  `IS_TRANSFER` tinyint(4) DEFAULT NULL COMMENT '是否转仓',
  `IS_VALUABLE` tinyint(4) DEFAULT NULL COMMENT '是否贵重物品',
  `IS_VIRTUAL_RETURN` tinyint(4) DEFAULT NULL COMMENT '是否虚拟退货',
  `LEAVE_DC_TIME` timestamp NULL DEFAULT NULL COMMENT '出分拨中心时间',
  `LEAVE_DEPOT_TIME` timestamp NULL DEFAULT NULL COMMENT '出配送站时间',
  `MODIFIED_TIME` timestamp NULL DEFAULT NULL COMMENT '最后修改时间(预留)',
  `MODIFIER` varchar(64) DEFAULT NULL COMMENT '修改人姓名(预留)',
  `MODIFIER_ID` bigint(20) DEFAULT NULL COMMENT '修改人ID(预留)',
  `ORD_CREATE_DATE` timestamp NULL DEFAULT NULL COMMENT '订单日期',
  `ORD_LAST_MODIFIED_DATE` timestamp NULL DEFAULT NULL,
  `ORD_M_STATUS` int(11) DEFAULT NULL COMMENT '处理过程中间状态',
  `ORD_REFERENCE_CODE` varchar(32) DEFAULT NULL COMMENT '取件单的原订单号',
  `ORDER_DATE` varchar(64) DEFAULT NULL COMMENT '下单日期',
  `PARENT_CODE` varchar(64) DEFAULT NULL COMMENT '母单号（托运母单号）',
  `PAYMENT_MODE` varchar(64) DEFAULT NULL COMMENT '付款方式',
  `PRIORITY` int(11) DEFAULT NULL COMMENT '优先级(1自提，其它非自提）',
  `QUANTITY` int(11) DEFAULT NULL COMMENT '数量',
  `REJECT_REASON` int(11) DEFAULT NULL COMMENT '拒收原因',
  `REMARK` varchar(128) DEFAULT NULL COMMENT '备注',
  `RESPONSE_CODE` varchar(64) DEFAULT NULL COMMENT '回应代码',
  `RESPONSE_MSG` varchar(128) DEFAULT NULL COMMENT '响应详细内容',
  `RETRUN_DESC` varchar(64) DEFAULT NULL COMMENT '回单备注',
  `RETURN_TIME` timestamp NULL DEFAULT NULL COMMENT '回单时间',
  `RETURN_TYPE` varchar(64) DEFAULT NULL COMMENT '退货类型',
  `SEND_SMS_TIMES` int(11) DEFAULT NULL COMMENT '发送短信次数',
  `STATUS` int(11) DEFAULT NULL COMMENT '状态',
  `TO_COLLECT_AMOUNT` decimal(10,2) DEFAULT NULL COMMENT '应收金额/应退金额',
  `TO_LOCATION_ID` bigint(20) DEFAULT NULL COMMENT '目的地ID',
  `TRANSFER_LOCATION_ID` bigint(20) DEFAULT NULL COMMENT '转仓分拣中心',
  `TRANSFER_OUT_TIME` timestamp NULL DEFAULT NULL,
  `TYPE` int(11) DEFAULT NULL COMMENT '类型（1，配送单 2：取件单）',
  `VOLUME` decimal(10,2) DEFAULT NULL COMMENT '体积',
  `WEIGHT` decimal(10,2) DEFAULT NULL COMMENT '重量',
  `WH_RECEIVE_TIME` timestamp NULL DEFAULT NULL,
  `IS_SELF_DISTRIBUTION` varchar(4) DEFAULT NULL COMMENT '是否自配送',
  `BILL_CODE1` varchar(512) DEFAULT NULL,
  `PUSH_NUMBER` int(10) NOT NULL DEFAULT '0' COMMENT '推送次数',
  `AREA_CODE` varchar(64) NOT NULL DEFAULT '' COMMENT '行政区域编码',
  `IS_SIGN_RECEIP` varchar(3) DEFAULT '0' COMMENT '签回单',
  `IS_WELLMEETING` varchar(3) DEFAULT '0' COMMENT '揪团',
  `IS_SAVE` varchar(3) DEFAULT '0' COMMENT '保价',
  `TRANS_CENTER_ID` bigint(20) NOT NULL DEFAULT '0' COMMENT '二级中转id',
  `TO_TRANS_CENTER_TIME` varchar(32) NOT NULL DEFAULT '' COMMENT '入二级中转时间',
  `LEAVE_TRANS_CENTER_TIME` varchar(32) NOT NULL DEFAULT '' COMMENT '出二级中转时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `PARENT_CODE` (`PARENT_CODE`),
  KEY `IDX_ORDER_HEADER_FROM` (`FROM_LOCATION_ID`),
  KEY `IDX_ORDER_HEADER_TO` (`TO_LOCATION_ID`),
  KEY `IDX_ORDER_HEADER_CREATETIME` (`CREATED_TIME`),
  KEY `IDX_ORDER_HEADER_DEPOT_ID` (`DEPOT_ID`),
  KEY `IDX_HEADER_LEAVE_DEPOT_TIME` (`LEAVE_DEPOT_TIME`),
  KEY `IDX_HEADER_RETURN_TIME` (`RETURN_TIME`),
  KEY `IDX_HEADER_ARRIVE_DEPOT_TIME` (`ARRIVE_DEPOT_TIME`),
  KEY `IDX_HEADER_CENTER_SHIPPING_ID` (`CENTER_SHIPPING_ID`),
  KEY `IDX_HEADER_CARRIERID` (`CARRIER_ID`),
  KEY `IDX_HEADER_CUSTOMORDERCODE` (`CUSTOMER_ORDER_CODE`),
  KEY `idx_oh_code` (`CODE`),
  KEY `IS_SELF_DISTRIBUTION` (`IS_SELF_DISTRIBUTION`),
  KEY `CUSTOMER_CODE` (`CUSTOMER_CODE`),
  KEY `ORD_M_STATUS` (`ORD_M_STATUS`),
  KEY `PUSH_NUMBER` (`PUSH_NUMBER`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='母单表';

-- ----------------------------
-- Table structure for order_header_bak
-- ----------------------------
DROP TABLE IF EXISTS `order_header_bak`;
CREATE TABLE `order_header_bak` (
  `ID` bigint(20) NOT NULL COMMENT '业务主键Id',
  `CODE` varchar(32) DEFAULT NULL COMMENT '子单号（托运子单号）',
  `PARENT_CODE` varchar(32) DEFAULT NULL COMMENT '母单号（托运母单号）',
  `CUSTOMER_ORDER_CODE` varchar(40) DEFAULT NULL COMMENT '客户订单号',
  `ORDER_DATE` timestamp NULL DEFAULT NULL COMMENT '下单日期',
  `CUSTOMER_ID` bigint(20) DEFAULT NULL COMMENT '委托方ID',
  `COMPANY_ID` bigint(20) DEFAULT NULL COMMENT '公司ID',
  `TYPE` int(11) DEFAULT NULL COMMENT '类型（1，配送单 2：取件单）',
  `GOODS_TYPE` int(11) DEFAULT NULL COMMENT '配送产品类别（1，冷冻 2，冷藏 3，普通）',
  `IS_COLLECTION` char(1) DEFAULT 'N' COMMENT '是否代收款.Y,N',
  `COLLECTED_AMOUNT` float DEFAULT NULL COMMENT '代收款',
  `CONSIGNEE` varchar(128) DEFAULT NULL COMMENT '收货人',
  `CONSIGNEE_TELEPHONE` varchar(64) DEFAULT NULL COMMENT '收货人电话',
  `CONSIGNEE_MOBILE` varchar(64) DEFAULT NULL COMMENT '收货人手机',
  `CONSIGNEE_PROVINCE` varchar(64) DEFAULT NULL COMMENT '收件人省份',
  `CONSIGNEE_CITY` varchar(64) DEFAULT NULL COMMENT '收件人市',
  `CONSIGNEE_AREA` varchar(64) DEFAULT NULL COMMENT '收件人区',
  `CONSIGNEE_ADDRESS` varchar(256) DEFAULT NULL COMMENT '收件人详细地址（送货地址）',
  `CONSIGNEE_ZIPCODE` varchar(16) DEFAULT NULL COMMENT '收件邮编',
  `CONSIGNEE_EMAIL` varchar(64) DEFAULT NULL COMMENT '收货人邮件',
  `IS_NEED_INVOICE` tinyint(4) DEFAULT '0' COMMENT '是否需要发票',
  `DELIVERY_MODE` int(11) DEFAULT NULL COMMENT '配送方式',
  `APPOINT_DELIVERY_DATE` timestamp NULL DEFAULT NULL COMMENT '指定到货日期，YYYYMMDD e.g. 20150110客人可以指定收到货日期有值表示物流商需依照指定到货日期配送, 无值则正常日期出货',
  `APPOINT_DELIVERY_START` varchar(16) DEFAULT NULL COMMENT '指定到货开始时段，同下',
  `APPOINT_DELIVERY_END` varchar(16) DEFAULT NULL COMMENT '指定到货结束时段，HHMM (24小时制)，e.g. 1200，客人可以指定收到货的最晚结束时间',
  `FROM_LOCATION_ID` bigint(20) DEFAULT NULL COMMENT '发货地',
  `TO_LOCATION_ID` bigint(20) DEFAULT NULL COMMENT '目的地ID',
  `DEPOT_ID` bigint(20) DEFAULT NULL COMMENT '配送站点',
  `CARRIER_ID` bigint(20) DEFAULT NULL COMMENT '配送商ID',
  `QUANTITY` int(11) DEFAULT NULL COMMENT '数量',
  `CARTON_QUANTITY` int(11) DEFAULT NULL COMMENT '件数（相同主单号的件数）',
  `STATUS` int(11) DEFAULT NULL COMMENT '状态',
  `PAYMENT_MODE` varchar(32) DEFAULT NULL COMMENT '付款方式',
  `AMOUNT` float DEFAULT NULL COMMENT '货款/取件单货款',
  `TO_COLLECT_AMOUNT` float DEFAULT NULL COMMENT '应收金额/应退金额',
  `COLLECTED_AMOUNT2` float DEFAULT NULL COMMENT '收款金额/退现金金额',
  `ARRIVE_DC_TIME` timestamp NULL DEFAULT NULL COMMENT '入分拣中心时间',
  `LEAVE_DC_TIME` timestamp NULL DEFAULT NULL COMMENT '出分拣中心时间',
  `WH_RECEIVE_TIME` timestamp NULL DEFAULT NULL COMMENT '退货入库时间',
  `TRANSFER_OUT_TIME` timestamp NULL DEFAULT NULL COMMENT '转仓出库时间',
  `IS_VALUABLE` tinyint(4) DEFAULT '0' COMMENT '是否贵重物品',
  `DELIVERY_STATE` int(11) DEFAULT NULL COMMENT '配送状态',
  `RETURN_TYPE` varchar(32) DEFAULT NULL COMMENT '退货类型',
  `SEND_SMS_TIMES` int(11) DEFAULT '0' COMMENT '发送短信次数',
  `PRIORITY` int(11) DEFAULT NULL COMMENT '优先级',
  `IS_PICKUP` tinyint(4) DEFAULT NULL COMMENT '是否上门取件',
  `DELIVERY_STAFF_ID` bigint(20) DEFAULT NULL COMMENT '配送员ID',
  `IS_TRANSFER` tinyint(4) DEFAULT '0' COMMENT '是否转仓',
  `TRANSFER_LOCATION_ID` bigint(20) DEFAULT NULL COMMENT '转仓分拣中心',
  `DOMAIN_ID` bigint(20) DEFAULT NULL COMMENT '所属平台',
  `RETURN_TIME` timestamp NULL DEFAULT NULL COMMENT '回单时间',
  `RETRUN_DESC` varchar(256) DEFAULT NULL COMMENT '回单备注',
  `ARRIVE_DEPOT_TIME` timestamp NULL DEFAULT NULL COMMENT '入站时间',
  `LEAVE_DEPOT_TIME` timestamp NULL DEFAULT NULL COMMENT '出站时间',
  `DETAINED_REASON` int(11) DEFAULT NULL COMMENT '滞留原因',
  `REJECT_REASON` int(11) DEFAULT NULL COMMENT '拒收原因',
  `IS_BIG` tinyint(4) DEFAULT NULL COMMENT '是否大材',
  `IS_VIRTUAL_RETURN` tinyint(4) DEFAULT '0' COMMENT '是否虚拟退货',
  `REMARK` varchar(256) DEFAULT NULL COMMENT '备注',
  `CREATOR_ID` bigint(20) DEFAULT NULL COMMENT '创建人ID(预留)',
  `CREATOR` varchar(64) DEFAULT NULL COMMENT '创建人姓名(预留)',
  `CREATED_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间(预留)',
  `MODIFIER_ID` bigint(20) DEFAULT NULL COMMENT '修改人ID(预留)',
  `MODIFIER` varchar(64) DEFAULT NULL COMMENT '修改人姓名(预留)',
  `MODIFIED_TIME` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '最后修改时间(预留)',
  `RESPONSE_CODE` varchar(16) DEFAULT NULL COMMENT '回应代码',
  `RESPONSE_MSG` varchar(192) DEFAULT NULL COMMENT '响应详细内容',
  `CONSIGNOR` varchar(128) DEFAULT NULL COMMENT '发件人',
  `CONSIGNOR_TELEPHONE` varchar(32) DEFAULT NULL COMMENT '发件人电话',
  `CONSIGNOR_MOBILE` varchar(32) DEFAULT NULL COMMENT '发件人手机',
  `CONSIGNOR_ZIPCODE` varchar(32) DEFAULT NULL COMMENT '发件人邮编',
  `CONSIGNOR_PROVINCE` varchar(64) DEFAULT NULL COMMENT '发件人省份',
  `CONSIGNOR_CITY` varchar(64) DEFAULT NULL COMMENT '发件人城市',
  `CONSIGNOR_AREA` varchar(64) DEFAULT NULL COMMENT '发件人区域',
  `CONSIGNOR_ADDRESS` varchar(128) DEFAULT NULL COMMENT '发件人详细地址',
  `CONSIGNOR_EMAIL` varchar(64) DEFAULT NULL COMMENT '发件人邮箱',
  `INSURANCE_VALUE` float DEFAULT NULL COMMENT '保险价值',
  `EXPECTED_PICKUP_TIME` varchar(16) DEFAULT NULL COMMENT '预定取件时段',
  `EXPECTED_ARRIVE_TIME` varchar(16) DEFAULT NULL COMMENT '预定配达时段',
  `CUSTOMER_CODE` varchar(32) DEFAULT NULL COMMENT '物流商编号（公司赋予物流商的客户编号）',
  `CUSTOMER_DOOR` varchar(32) DEFAULT NULL COMMENT '大润发的门店号',
  `CUSTOMER_NAME` varchar(32) DEFAULT NULL COMMENT '物流商标识（物流商别名）',
  `ORD_CREATE_DATE` timestamp NULL DEFAULT NULL COMMENT '订单日期',
  `ORD_M_STATUS` int(11) DEFAULT NULL COMMENT '处理过程中间状态',
  `ORD_LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  `ORD_REFERENCE_CODE` varchar(64) DEFAULT NULL COMMENT '取件单的原订单号',
  `WEIGHT` float DEFAULT NULL COMMENT '重量',
  `VOLUME` float DEFAULT NULL COMMENT '体积',
  `CENTER_SHIPPING_ID` bigint(20) DEFAULT NULL COMMENT '分拨中心ID',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_ORDER` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单头';

-- ----------------------------
-- Table structure for order_input_result
-- ----------------------------
DROP TABLE IF EXISTS `order_input_result`;
CREATE TABLE `order_input_result` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `PARDET_CODE` varchar(64) DEFAULT NULL COMMENT '托运母单号',
  `SUB_CODE` varchar(64) DEFAULT NULL COMMENT '托运子单号',
  `CUSTOMER_ORD_CODE` varchar(64) DEFAULT NULL COMMENT '订单编号',
  `RESPOND_CODE` varchar(20) DEFAULT NULL COMMENT '回应代码',
  `RESPOND_CONTENT` varchar(100) DEFAULT NULL COMMENT '响应详细内容',
  `DATE_TIME` timestamp NULL DEFAULT NULL COMMENT '入库时间',
  `CUSTOMER_ID` varchar(64) DEFAULT NULL COMMENT '委托方',
  `LOGISTICS_ACNAME` varchar(64) DEFAULT NULL COMMENT '物流商标识【别名或者类似于英文名】',
  `LOGISTICS_CODE` varchar(64) DEFAULT NULL COMMENT '物流商编号',
  `LOGISTICS_DOOR` varchar(64) DEFAULT NULL COMMENT '4位店号【仓库】',
  `ORIGINATE` int(10) DEFAULT NULL COMMENT '数据来源【字段废弃】',
  `STATUS` int(5) DEFAULT '0' COMMENT '处理状态，0代表未处理，1代表已处理',
  `ORDER_TYPE` int(4) DEFAULT NULL COMMENT '订单类型',
  `MODIFIER_ID` bigint(20) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIER` varchar(64) DEFAULT NULL COMMENT '修改人',
  `MODIFIED_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `CREATOR_ID` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `CREATOR` varchar(64) DEFAULT NULL COMMENT '创建人',
  `CREATED_TIME` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `ORD_FILE_NAME` varchar(100) DEFAULT NULL COMMENT '接入单子的文件名称',
  PRIMARY KEY (`ID`),
  KEY `RESULT_ORDER_FILE_INDEX` (`ORD_FILE_NAME`),
  KEY `IDX_ORDER_INPUT_RESULT_CODE` (`PARDET_CODE`),
  KEY `IDX_ORDER_INPUT_RESULT_SUB_CODE` (`SUB_CODE`),
  KEY `IDX_ORDER_INPUT_RESULT_ORD_CODE` (`CUSTOMER_ORD_CODE`),
  KEY `IDX_ORDER_INPUT_RESULT_CUSTOMER_ID` (`CUSTOMER_ID`),
  KEY `IDX_ORDER_INPUT_RESULT_STATUS` (`STATUS`),
  KEY `IDX_ORDER_INPUT_RESULT_ORDER_TYPE` (`ORDER_TYPE`),
  KEY `IDX_ORDER_INPUT_RESULT_ORD_FILE_NAME` (`ORD_FILE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单处理结果表';

-- ----------------------------
-- Table structure for order_log
-- ----------------------------
DROP TABLE IF EXISTS `order_log`;
CREATE TABLE `order_log` (
  `ID` bigint(20) NOT NULL,
  `ORDER_CODE` varchar(64) DEFAULT NULL COMMENT '母单号',
  `ORDER_SUB_CODE` varchar(64) DEFAULT NULL COMMENT '子单号',
  `CUSTOMER_ORD_CODE` varchar(128) DEFAULT NULL COMMENT '客户订单号',
  `CUSTOMER_CODE` varchar(128) DEFAULT NULL COMMENT '客户代号',
  `BUSINESS_NAME` varchar(128) DEFAULT NULL COMMENT '营业所名称，站点名称',
  `OPERATION_TYPE` int(11) DEFAULT NULL COMMENT '订单状态',
  `OPERATION_TIME` timestamp NULL DEFAULT NULL COMMENT '执行时间【货态输入日期】',
  `TYPE` int(11) DEFAULT NULL COMMENT '订单类型【如配送单，取件单】',
  `OPERATOR_ID` bigint(20) DEFAULT NULL COMMENT '操作人ID',
  `OPERATOR` varchar(50) DEFAULT NULL COMMENT '操作人',
  `REMARK` varchar(200) DEFAULT NULL COMMENT '备注',
  `STATUS` int(2) DEFAULT NULL COMMENT '配送状态',
  `ORIGINATE` bigint(32) DEFAULT NULL COMMENT '数据来源,根据委托方进行判断',
  `RESULT_ID` varchar(20) DEFAULT NULL COMMENT '货态ID',
  `RESULT_MSG` varchar(1024) DEFAULT NULL,
  `OPERATION_STATUS` bigint(2) DEFAULT '0' COMMENT '操作状态 0:未处理的 1:已处理过',
  `DELIVERY_MAN` varchar(64) DEFAULT NULL COMMENT '配送员',
  `DELIVERY_MOBEL` varchar(64) DEFAULT NULL COMMENT '配送员电话',
  `BILL_CODE1` varchar(512) DEFAULT NULL,
  `MODIFIER_ID` bigint(20) NOT NULL DEFAULT '-100000' COMMENT '修改人ID',
  `MODIFIER` varchar(64) NOT NULL DEFAULT '' COMMENT '修改人',
  `MODIFIED_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `CREATOR` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人',
  `CREATED_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `IS_SIGN_RECEIP` varchar(3) DEFAULT '0' COMMENT '签回单',
  PRIMARY KEY (`ID`),
  KEY `idx_ol_oc` (`ORDER_CODE`),
  KEY `idx_ol_osc` (`ORDER_SUB_CODE`),
  KEY `ORDER_LOG_ORIGINATE` (`ORIGINATE`),
  KEY `ORDER_LOG_TYPE` (`TYPE`),
  KEY `ORDER_LOG_OPERATION_STATUS` (`OPERATION_STATUS`),
  KEY `IDX_ORDER_LOG_ORD_CODE` (`CUSTOMER_ORD_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单执行跟踪日志表';

-- ----------------------------
-- Table structure for order_log_fail
-- ----------------------------
DROP TABLE IF EXISTS `order_log_fail`;
CREATE TABLE `order_log_fail` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `ORDER_CODE` varchar(64) NOT NULL COMMENT '母单号',
  `ORDER_SUB_CODE` varchar(64) DEFAULT NULL COMMENT '子单号',
  `CUSTOMER_ORDER_CODE` varchar(128) DEFAULT NULL COMMENT '订单号',
  `CUSTOMER_CODE` varchar(128) DEFAULT NULL COMMENT '客户代号',
  `ORDER_STATUS` int(11) DEFAULT NULL COMMENT '订单状态',
  `DELIVERY_STATE` int(11) DEFAULT NULL COMMENT '配送状态',
  `OPERATION_TIME` timestamp NULL DEFAULT NULL COMMENT '执行时间',
  `ORDER_TYPE` int(11) DEFAULT NULL COMMENT '订单类型【如配送单，取件单】',
  `BUSINESS_NAME` varchar(128) DEFAULT NULL COMMENT '营业所名称，站点名称',
  `BUSINESS_ID` bigint(20) DEFAULT NULL COMMENT '营业所ID，站点名称ID',
  `OPERATOR_ID` bigint(20) DEFAULT NULL COMMENT '操作人ID',
  `OPERATOR` varchar(50) DEFAULT NULL COMMENT '操作人',
  `REMARK` varchar(256) DEFAULT NULL COMMENT '货态描述',
  `ORIGINATE` bigint(20) DEFAULT NULL COMMENT '来源,委托方ID',
  `EXCEPTION_MEMO` varchar(256) DEFAULT NULL COMMENT '异常描述',
  `BILL_CODE1` varchar(512) DEFAULT NULL COMMENT '原配送单号',
  `SOURCE` int(11) DEFAULT NULL COMMENT '数据来源  1：PDA 2:WEB页面  3：接口 4：其他',
  `OPERATION_STATUS` bigint(2) DEFAULT '0' COMMENT '操作状态 0:未处理的 1:已处理过',
  `MODIFIER_ID` bigint(20) NOT NULL DEFAULT '-1' COMMENT '修改人ID',
  `MODIFIER` varchar(64) NOT NULL DEFAULT '' COMMENT '修改人',
  `MODIFIED_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `CREATOR_ID` bigint(20) NOT NULL DEFAULT '-1' COMMENT '创建人ID',
  `CREATOR` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人',
  `CREATED_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT=' 失败日志表';

-- ----------------------------
-- Table structure for order_log_history
-- ----------------------------
DROP TABLE IF EXISTS `order_log_history`;
CREATE TABLE `order_log_history` (
  `ID` bigint(20) NOT NULL,
  `ORDER_CODE` varchar(64) DEFAULT NULL COMMENT '母单号',
  `ORDER_SUB_CODE` varchar(64) DEFAULT NULL COMMENT '子单号',
  `CUSTOMER_ORD_CODE` varchar(128) DEFAULT NULL COMMENT '客户订单号',
  `CUSTOMER_CODE` varchar(128) DEFAULT NULL COMMENT '客户代号',
  `BUSINESS_NAME` varchar(128) DEFAULT NULL COMMENT '营业所名称，站点名称',
  `OPERATION_TYPE` int(11) DEFAULT NULL COMMENT '订单状态',
  `OPERATION_TIME` timestamp NULL DEFAULT NULL COMMENT '执行时间【货态输入日期】',
  `TYPE` int(11) DEFAULT NULL COMMENT '订单类型【如配送单，取件单】',
  `OPERATOR_ID` bigint(20) DEFAULT NULL COMMENT '操作人ID',
  `OPERATOR` varchar(50) DEFAULT NULL COMMENT '操作人',
  `REMARK` varchar(200) DEFAULT NULL COMMENT '备注',
  `STATUS` int(2) DEFAULT NULL COMMENT '配送状态',
  `ORIGINATE` bigint(32) DEFAULT NULL COMMENT '数据来源,根据委托方进行判断',
  `RESULT_ID` varchar(20) DEFAULT NULL COMMENT '货态ID',
  `RESULT_MSG` varchar(1024) DEFAULT NULL,
  `OPERATION_STATUS` bigint(2) DEFAULT '0' COMMENT '操作状态 0:未处理的 1:已处理过',
  `DELIVERY_MAN` varchar(64) DEFAULT NULL COMMENT '配送员',
  `DELIVERY_MOBEL` varchar(64) DEFAULT NULL COMMENT '配送员电话',
  `BILL_CODE1` varchar(512) DEFAULT NULL,
  `MODIFIER_ID` bigint(20) NOT NULL DEFAULT '-100000' COMMENT '修改人ID',
  `MODIFIER` varchar(64) NOT NULL DEFAULT '' COMMENT '修改人',
  `MODIFIED_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `CREATOR` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人',
  `CREATED_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `IS_SIGN_RECEIP` varchar(3) DEFAULT '0' COMMENT '签回单',
  PRIMARY KEY (`ID`),
  KEY `idx_ol_oc` (`ORDER_CODE`),
  KEY `idx_ol_osc` (`ORDER_SUB_CODE`),
  KEY `ORDER_LOG_ORIGINATE` (`ORIGINATE`),
  KEY `ORDER_LOG_TYPE` (`TYPE`),
  KEY `ORDER_LOG_OPERATION_STATUS` (`OPERATION_STATUS`),
  KEY `IDX_ORDER_LOG_ORD_CODE` (`CUSTOMER_ORD_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单执行跟踪日志表';

-- ----------------------------
-- Table structure for order_log_push_erp
-- ----------------------------
DROP TABLE IF EXISTS `order_log_push_erp`;
CREATE TABLE `order_log_push_erp` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `ORDER_LOG_ID` bigint(20) NOT NULL DEFAULT '0' COMMENT '日志表主键',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `MODIFY_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `CREATE_ID` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `MODIFY_ID` bigint(20) NOT NULL DEFAULT '0' COMMENT '修改人',
  `IS_SEND` int(4) NOT NULL DEFAULT '0' COMMENT '是否发送.0未发送,1已推送',
  `IS_PUSH_NUMBER` int(8) NOT NULL DEFAULT '0' COMMENT '推送次数',
  `IS_SEND_MSG` int(4) NOT NULL DEFAULT '0' COMMENT '是否发送报警信息0未发送,1发送',
  PRIMARY KEY (`ID`),
  KEY `IDX_ORDER_LOG_ID` (`ORDER_LOG_ID`),
  KEY `IDX_CREATE_TIME` (`CREATE_TIME`),
  KEY `IDX_SEND_PUSH` (`IS_SEND`,`IS_PUSH_NUMBER`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for order_log_retention
-- ----------------------------
DROP TABLE IF EXISTS `order_log_retention`;
CREATE TABLE `order_log_retention` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `ORDER_CODE` varchar(64) NOT NULL DEFAULT '' COMMENT '母单号',
  `ORDER_SUB_CODE` varchar(64) NOT NULL DEFAULT '' COMMENT '子单号',
  `CUSTOMER_ORDER_CODE` varchar(128) NOT NULL DEFAULT '' COMMENT '订单号',
  `CUSTOMER_CODE` varchar(128) NOT NULL DEFAULT '' COMMENT '客户代号',
  `ORDER_STATUS` int(11) NOT NULL DEFAULT '-1' COMMENT '订单状态',
  `DELIVERY_STATE` int(11) NOT NULL DEFAULT '-1' COMMENT '配送状态',
  `OPERATION_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '执行时间',
  `ORDER_TYPE` int(11) NOT NULL DEFAULT '-1' COMMENT '订单类型【如配送单，取件单】',
  `BUSINESS_NAME` varchar(128) NOT NULL DEFAULT '' COMMENT '营业所名称，站点名称',
  `BUSINESS_ID` bigint(20) NOT NULL DEFAULT '-1' COMMENT '营业所ID，站点名称ID',
  `OPERATOR_ID` bigint(20) NOT NULL DEFAULT '-1' COMMENT '操作人ID',
  `OPERATOR` varchar(50) NOT NULL DEFAULT '' COMMENT '操作人',
  `REMARK` varchar(256) NOT NULL DEFAULT '' COMMENT '货态描述',
  `ORIGINATE` bigint(20) NOT NULL DEFAULT '-1' COMMENT '来源,委托方ID',
  `EXCEPTION_MEMO` varchar(256) NOT NULL DEFAULT '' COMMENT '异常描述',
  `BILL_CODE1` varchar(512) NOT NULL DEFAULT '' COMMENT '原配送单号',
  `SOURCE` int(11) NOT NULL DEFAULT '4' COMMENT '数据来源  1：PDA 2:WEB页面  3：接口 4：其他',
  `OPERATION_STATUS` bigint(2) NOT NULL DEFAULT '0' COMMENT '操作状态 0:未处理的 1:已处理过',
  `MODIFIER_ID` bigint(20) NOT NULL DEFAULT '-1' COMMENT '修改人ID',
  `MODIFIER` varchar(64) NOT NULL DEFAULT '' COMMENT '修改人',
  `MODIFIED_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `CREATOR_ID` bigint(20) NOT NULL DEFAULT '-1' COMMENT '创建人ID',
  `CREATOR` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人',
  `CREATED_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `EXCEPTION_CODE` varchar(10) NOT NULL DEFAULT '' COMMENT '异常CODE',
  `EXCEPTION_TYPE` int(1) NOT NULL DEFAULT '1' COMMENT '异常类型(1-滞留,2-失败)',
  PRIMARY KEY (`ID`),
  KEY `idx_log_r_ordercode` (`ORDER_CODE`),
  KEY `idx_log_r_subcode` (`ORDER_SUB_CODE`),
  KEY `idx_log_r_siteid` (`BUSINESS_ID`),
  KEY `idx_log_r_optime` (`OPERATION_TIME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='滞留日志表';

-- ----------------------------
-- Table structure for order_log_temp
-- ----------------------------
DROP TABLE IF EXISTS `order_log_temp`;
CREATE TABLE `order_log_temp` (
  `ORDER_CODE` varchar(64) DEFAULT NULL COMMENT '母单号',
  `ORDER_SUB_CODE` varchar(64) DEFAULT NULL COMMENT '子单号',
  `CUSTOMER_ORD_CODE` varchar(128) DEFAULT NULL COMMENT '客户订单号',
  `CUSTOMER_CODE` varchar(128) DEFAULT NULL COMMENT '客户代号',
  `BUSINESS_NAME` varchar(128) DEFAULT NULL COMMENT '营业所名称，站点名称',
  `OPERATION_TYPE` int(11) DEFAULT NULL COMMENT '订单状态',
  `OPERATION_TIME` timestamp NULL DEFAULT NULL COMMENT '执行时间【货态输入日期】',
  `TYPE` int(11) DEFAULT NULL COMMENT '订单类型【如配送单，取件单】',
  `OPERATOR_ID` bigint(20) DEFAULT NULL COMMENT '操作人ID',
  `OPERATOR` varchar(50) DEFAULT NULL COMMENT '操作人',
  `REMARK` varchar(200) DEFAULT NULL COMMENT '备注',
  `STATUS` int(2) DEFAULT NULL COMMENT '配送状态',
  `ORIGINATE` bigint(32) DEFAULT NULL COMMENT '数据来源,根据委托方进行判断',
  `RESULT_ID` varchar(20) DEFAULT NULL COMMENT '货态ID',
  `RESULT_MSG` varchar(64) DEFAULT NULL COMMENT '货态说明',
  `OPERATION_STATUS` bigint(2) DEFAULT '0' COMMENT '操作状态 0:未处理的 1:已处理过',
  `DELIVERY_MAN` varchar(64) DEFAULT NULL COMMENT '配送员',
  `DELIVERY_MOBEL` varchar(64) DEFAULT NULL COMMENT '配送员电话',
  `BILL_CODE1` varchar(512) DEFAULT NULL,
  `MODIFIER_ID` bigint(20) NOT NULL DEFAULT '-1' COMMENT '修改人ID',
  `MODIFIER` varchar(64) NOT NULL DEFAULT '' COMMENT '修改人',
  `MODIFIED_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `CREATOR_ID` bigint(20) NOT NULL DEFAULT '-1' COMMENT '创建人ID',
  `CREATOR` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人',
  `CREATED_TIME` timestamp NULL DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for order_payment
-- ----------------------------
DROP TABLE IF EXISTS `order_payment`;
CREATE TABLE `order_payment` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `order_header_id` bigint(20) DEFAULT NULL COMMENT '订单ID',
  `order_code` varchar(64) NOT NULL COMMENT '订单code',
  `location_id` bigint(20) DEFAULT NULL COMMENT '站点ID',
  `company_id` bigint(20) DEFAULT NULL COMMENT '公司ID',
  `payment_mode` tinyint(4) NOT NULL COMMENT '支付方式',
  `amount` float(10,2) NOT NULL COMMENT '支付金额',
  `pos_id` varchar(64) DEFAULT NULL COMMENT 'POS终端ID',
  `pos_serial` varchar(64) DEFAULT NULL COMMENT 'POS流水号',
  `batch_num` varchar(64) DEFAULT NULL COMMENT '批次号',
  `card_code` varchar(32) DEFAULT NULL COMMENT '卡号',
  `state` tinyint(4) DEFAULT NULL COMMENT '状态',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `creator` varchar(64) DEFAULT NULL COMMENT '创建人',
  `creator_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modefier` varchar(64) DEFAULT NULL COMMENT '确认人',
  `modefier_id` bigint(20) DEFAULT NULL COMMENT '确认人id',
  `modified_time` timestamp NULL DEFAULT NULL COMMENT '确认时间',
  `ORDER_DETAIL_ID` bigint(20) NOT NULL DEFAULT '0' COMMENT '子单ID',
  `ORDER_DETAIL_CODE` varchar(64) NOT NULL DEFAULT '' COMMENT '子单编号',
  `FINANCE_CHECK_ID` bigint(20) DEFAULT NULL COMMENT '财务审核人id',
  `FINANCE_CHECKER` varchar(64) DEFAULT NULL COMMENT '财务审核人',
  `FINANCE_CHECK_TIME` timestamp NULL DEFAULT NULL COMMENT '财务审核时间',
  `REVERSE_CHECKER` varchar(64) NOT NULL DEFAULT '' COMMENT '反审核人姓名',
  `REVERSE_CHECK_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '反审核时间',
  `REVERSE_CHECK_ID` bigint(20) NOT NULL DEFAULT '0' COMMENT '反审核人ID',
  `IS_PUSH` bigint(4) NOT NULL DEFAULT '0' COMMENT '是否推送 0-未推送,1-推送成功',
  `PUSH_NUM` bigint(20) NOT NULL DEFAULT '0' COMMENT '推送次数',
  `PAY_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '支付时间',
  PRIMARY KEY (`id`),
  KEY `PAYMENT_DETAIL_ID` (`ORDER_DETAIL_ID`),
  KEY `PAYMENT_DETAIL_CODE` (`ORDER_DETAIL_CODE`),
  KEY `idx_state` (`state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单支付信息表';

-- ----------------------------
-- Table structure for order_test
-- ----------------------------
DROP TABLE IF EXISTS `order_test`;
CREATE TABLE `order_test` (
  `DETAILID` bigint(20) NOT NULL COMMENT '子单ID',
  `DETAILCODE` varchar(64) NOT NULL DEFAULT '' COMMENT '子单号（托运子单号）',
  `HEADERID` bigint(20) NOT NULL COMMENT '母单ID',
  `HEADERCODE` varchar(64) NOT NULL DEFAULT '' COMMENT '母单号（托运母单号）',
  `ISMAIN` int(2) NOT NULL COMMENT '是否主子单',
  `CUSTOMERORDERCODE` varchar(64) NOT NULL DEFAULT '' COMMENT '客户订单号',
  `TYPE` int(11) NOT NULL DEFAULT '0' COMMENT '1，配送单 2：取件单',
  `STATUS` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
  `DELIVERYSTATE` int(11) NOT NULL DEFAULT '0' COMMENT '配送状态',
  `DELIVERYSTAFFID` bigint(20) NOT NULL DEFAULT '0' COMMENT '配送员ID',
  `CENTERSHIPPINGID` bigint(20) NOT NULL DEFAULT '0' COMMENT '分拨中心ID',
  `DEPOTID` bigint(20) NOT NULL DEFAULT '0' COMMENT '配送站点',
  `CARRIERID` bigint(20) NOT NULL DEFAULT '0' COMMENT '配送商ID',
  `CUSTOMERCODE` varchar(64) NOT NULL DEFAULT '' COMMENT '物流商编号（公司赋予物流商的客户编号）',
  `TOLOCATIONID` bigint(20) NOT NULL DEFAULT '0' COMMENT '目的地ID',
  `APPOINTDELIVERYDATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '指定到达日期',
  `APPOINTDELIVERYSTART` varchar(64) NOT NULL DEFAULT '' COMMENT '指定开始结束时段，HHMM (24小时制)',
  `APPOINTDELIVERYEND` varchar(64) NOT NULL DEFAULT '' COMMENT '指定到货结束时段，HHMM (24小时制)',
  `ARRIVEDCTIME` varchar(64) NOT NULL DEFAULT '' COMMENT '入分拣中心时间',
  `LEAVEDCTIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '出分拨中心时间',
  `ARRIVEDEPOTTIME` varchar(64) NOT NULL DEFAULT '' COMMENT '入站时间',
  `LEAVEDEPOTTIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '出配送站时间',
  `RETURNTIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '回单时间',
  `CREATEDTIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `CUSTOMERID` bigint(20) NOT NULL DEFAULT '0' COMMENT '委托方ID',
  `FROMLOCATIONID` bigint(20) NOT NULL DEFAULT '0' COMMENT '发货地',
  `DELIVERYMODE` int(11) NOT NULL DEFAULT '0' COMMENT '配送方式',
  `ISSELFDISTRIBUTION` varchar(4) NOT NULL DEFAULT '' COMMENT '是否自配送',
  KEY `index_depotid` (`DEPOTID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for order_validate_error
-- ----------------------------
DROP TABLE IF EXISTS `order_validate_error`;
CREATE TABLE `order_validate_error` (
  `ID` bigint(20) NOT NULL,
  `CODE` varchar(32) DEFAULT NULL COMMENT '子单号（托运子单号）',
  `PARENT_CODE` varchar(32) DEFAULT NULL COMMENT '主单号（托运母单号）',
  `CUSTOMER_ORDER_CODE` varchar(40) DEFAULT NULL COMMENT '客户订单号',
  `ORDER_DATE` date DEFAULT NULL COMMENT '下单日期',
  `CUSTOMER_ID` bigint(20) DEFAULT NULL COMMENT '委托方ID',
  `COMPANY_ID` bigint(20) DEFAULT NULL COMMENT '公司ID',
  `TYPE` int(11) DEFAULT NULL COMMENT '类型（1，配送单 2：取件单）',
  `GOODS_TYPE` int(11) DEFAULT NULL COMMENT '配送产品类别（1，冷冻 2，冷藏 3，普通）',
  `CONSIGNEE` varchar(128) DEFAULT NULL COMMENT '收货人',
  `CONSIGNEE_TELEPHONE` varchar(64) DEFAULT NULL COMMENT '收货人电话',
  `CONSIGNEE_MOBILE` varchar(64) DEFAULT NULL COMMENT '收货人手机',
  `CONSIGNEE_ADDRESS` varchar(256) DEFAULT NULL COMMENT '送货地址',
  `CONSIGNEE_ZIPCODE` varchar(16) DEFAULT NULL COMMENT '邮编',
  `CONSIGNEE_EMAIL` varchar(64) DEFAULT NULL COMMENT '收货人邮件',
  `IS_NEED_INVOICE` tinyint(4) DEFAULT '0' COMMENT '需要发票',
  `DELIVERY_MODE` int(11) DEFAULT NULL COMMENT '配送方式',
  `EXPECTED_DELIVERY_DATE` date DEFAULT NULL COMMENT '指定收货日期',
  `DELIVERY_PERIOD` varchar(16) DEFAULT NULL COMMENT '指定配送时段',
  `FROM_LOCATION_ID` bigint(20) DEFAULT NULL COMMENT '发货地',
  `TO_LOCATION_ID` bigint(20) DEFAULT NULL COMMENT '目的地ID',
  `PROVINCE` varchar(64) DEFAULT NULL COMMENT '省',
  `CITY` varchar(64) DEFAULT NULL COMMENT '市',
  `DISTRICT` varchar(64) DEFAULT NULL COMMENT '区',
  `AREA` varchar(64) DEFAULT NULL COMMENT '配送区域',
  `DEPOT_ID` bigint(20) DEFAULT NULL COMMENT '配送站点',
  `CARRIER_ID` bigint(20) DEFAULT NULL COMMENT '配送商ID',
  `QUANTITY` int(11) DEFAULT NULL COMMENT '数量',
  `CARTON_QUANTITY` int(11) DEFAULT NULL COMMENT '件数（相同主单号的件数）',
  `WEIGHT` float DEFAULT NULL COMMENT '重量',
  `VOLUME` float DEFAULT NULL COMMENT '体积',
  `STATUS` int(11) DEFAULT NULL COMMENT '状态',
  `PAYMENT_MODE` varchar(32) DEFAULT NULL COMMENT '付款方式',
  `AMOUNT` float DEFAULT NULL COMMENT '货款/取件单货款',
  `TO_COLLECT_AMOUNT` float DEFAULT NULL COMMENT '应收金额/应退金额',
  `COLLECTED_AMOUNT` float DEFAULT NULL COMMENT '收款金额/退现金金额',
  `BILL_CODE_1` varchar(32) DEFAULT NULL COMMENT '相关单号1',
  `BILL_CODE_2` varchar(32) DEFAULT NULL COMMENT '相关单号2',
  `BILL_CODE_3` varchar(32) DEFAULT NULL COMMENT '相关单号3',
  `ARRIVE_DC_TIME` timestamp NULL DEFAULT NULL COMMENT '入分拣中心时间',
  `LEAVE_DC_TIME` timestamp NULL DEFAULT NULL COMMENT '出分拣中心时间',
  `WH_RECEIVE_TIME` timestamp NULL DEFAULT NULL COMMENT '退货入库时间',
  `TRANSFER_OUT_TIME` timestamp NULL DEFAULT NULL COMMENT '转仓出库时间',
  `IS_VALUABLE` tinyint(4) DEFAULT '0' COMMENT '是否贵重物品',
  `DELIVERY_STATE` int(11) DEFAULT NULL COMMENT '配送状态',
  `RETURN_TYPE` varchar(32) DEFAULT NULL COMMENT '退货类型',
  `SEND_SMS_TIMES` int(11) DEFAULT '0' COMMENT '发送短信次数',
  `PRIORITY` int(11) DEFAULT NULL COMMENT '优先级',
  `IS_PICKUP` tinyint(4) DEFAULT NULL COMMENT '是否上门取件',
  `DELIVERY_STAFF_ID` bigint(20) DEFAULT NULL COMMENT '配送员ID',
  `IS_TRANSFER` tinyint(4) DEFAULT '0' COMMENT '是否转仓',
  `TRANSFER_LOCATION_ID` bigint(20) DEFAULT NULL COMMENT '转仓分拣中心',
  `DOMAIN_ID` bigint(20) DEFAULT NULL COMMENT '所属平台',
  `RETURN_TIME` date DEFAULT NULL COMMENT '回单时间',
  `RETRUN_DESC` varchar(256) DEFAULT NULL COMMENT '回单备注',
  `ARRIVE_DEPOT_TIME` date DEFAULT NULL COMMENT '入站时间',
  `LEAVE_DEPOT_TIME` date DEFAULT NULL COMMENT '出站时间',
  `DETAINED_REASON` int(11) DEFAULT NULL COMMENT '滞留原因',
  `REJECT_REASON` int(11) DEFAULT NULL COMMENT '拒收原因',
  `IS_BIG` tinyint(4) DEFAULT NULL COMMENT '是否大材',
  `IS_VIRTUAL_RETURN` tinyint(4) DEFAULT '0' COMMENT '是否虚拟退货',
  `REMARK` varchar(256) DEFAULT NULL COMMENT '备注',
  `CREATOR_ID` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `CREATOR` varchar(64) DEFAULT NULL COMMENT '创建人姓名',
  `CREATED_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `MODIFIER_ID` bigint(20) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIER` varchar(64) DEFAULT NULL COMMENT '修改人姓名',
  `MODIFIED_TIME` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '最后修改时间',
  `insurance_value` float DEFAULT NULL COMMENT '保险价值',
  `length` float DEFAULT NULL COMMENT '箱号长',
  `width` float DEFAULT NULL COMMENT '箱号宽',
  `height` float DEFAULT NULL COMMENT '箱号高',
  `package_id` varchar(20) DEFAULT NULL COMMENT '箱号',
  `response_code` varchar(16) DEFAULT NULL,
  `response_msg` varchar(192) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_ORDER` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单头';

-- ----------------------------
-- Table structure for ordre_transfer_station
-- ----------------------------
DROP TABLE IF EXISTS `ordre_transfer_station`;
CREATE TABLE `ordre_transfer_station` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `order_header_id` bigint(20) DEFAULT NULL COMMENT '母单ID',
  `order_code` varchar(64) DEFAULT NULL COMMENT '母单编号',
  `detail_count` int(11) DEFAULT NULL COMMENT '子单数',
  `order_type` int(1) DEFAULT NULL COMMENT '订单类型',
  `from_location_id` bigint(20) DEFAULT NULL COMMENT '转出站ID',
  `to_location_id` bigint(20) DEFAULT NULL COMMENT '转入站ID',
  `old_address` varchar(256) DEFAULT NULL COMMENT '原地址',
  `new_address` varchar(256) DEFAULT NULL COMMENT '新地址',
  `status` tinyint(4) DEFAULT '0' COMMENT '状态(0-创建,1-转出审核,2-转入审核,3-拒绝转入)',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `creator_name` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(20) DEFAULT NULL COMMENT '修改人ID',
  `modifier_name` varchar(64) DEFAULT NULL COMMENT '修改人姓名',
  `modifier_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint(4) DEFAULT '0' COMMENT '是否删除(0-否，1-是)',
  `transfer_reson` varchar(200) DEFAULT NULL COMMENT '转站原因',
  `ORDER_SUB_CODE` varchar(64) NOT NULL DEFAULT '' COMMENT '子单号',
  `ORDER_SUB_ID` bigint(20) NOT NULL DEFAULT '-1' COMMENT '子单id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单转站表';

-- ----------------------------
-- Table structure for ord_ins
-- ----------------------------
DROP TABLE IF EXISTS `ord_ins`;
CREATE TABLE `ord_ins` (
  `ORD_INS_ID` bigint(20) DEFAULT NULL COMMENT '内部订单ID',
  `LOGISTIC_KIND` tinyint(4) DEFAULT NULL COMMENT '1:送件单,0:取件单',
  `LOGISTIC_CODE` varchar(64) DEFAULT NULL COMMENT '运单号',
  `CUSTOMER_ORD_CODE` varchar(64) DEFAULT NULL COMMENT '订单号',
  `LOGISTIC_TYPE` tinyint(4) DEFAULT NULL COMMENT '物流类型1冷冻2冷藏3普通',
  `CUSTOMER_ID` bigint(20) DEFAULT NULL COMMENT '客户ID',
  `IS_COLLECTION` char(1) DEFAULT 'N' COMMENT '是否代收款.Y,N',
  `COLLECTION_AMT` decimal(10,2) DEFAULT NULL COMMENT '代收款',
  `CONSIGNEE` varchar(128) DEFAULT NULL COMMENT '收件人',
  `CONSIGNEE_TELEPHONE` varchar(32) DEFAULT NULL COMMENT '收件人电话',
  `CONSIGNEE_MOBILE` varchar(32) DEFAULT NULL COMMENT '收件人手机',
  `CONSIGNEE_ZIPCODE` varchar(32) DEFAULT NULL COMMENT '收件人邮编',
  `CONSIGNEE_PROVINCE` varchar(64) DEFAULT NULL COMMENT '收件人省份',
  `CONSIGNEE_CITY` varchar(64) DEFAULT NULL COMMENT '收件人城市',
  `CONSIGNEE_ADDRESS` varchar(128) DEFAULT NULL COMMENT '收件人详细地址',
  `INSURANCE_VALUE` decimal(10,2) DEFAULT NULL COMMENT '保险金额',
  `CONSIGNOR` varchar(128) DEFAULT NULL COMMENT '发件人',
  `CONSIGNOR_TELEPHONE` varchar(32) DEFAULT NULL COMMENT '发件人电话',
  `CONSIGNOR_MOBILE` varchar(32) DEFAULT NULL COMMENT '发件人手机',
  `CONSIGNOR_ZIPCODE` varchar(32) DEFAULT NULL COMMENT '发件人邮编',
  `CONSIGNOR_PROVINCE` varchar(64) DEFAULT NULL COMMENT '发件人省份',
  `CONSIGNOR_CITY` varchar(64) DEFAULT NULL COMMENT '发件人城市',
  `CONSIGNOR_ADDRESS` varchar(128) DEFAULT NULL COMMENT '发件人详细地址',
  `CONSIGNEE_EMAIL` varchar(64) DEFAULT NULL COMMENT '收件人邮箱',
  `CONSIGNOR_EMAIL` varchar(64) DEFAULT NULL COMMENT '发件人邮箱',
  `IS_BIG_FLAG` char(1) DEFAULT NULL COMMENT '是否为大件',
  `IS_NEED_INVOICE` char(1) DEFAULT NULL COMMENT '是否需要发票',
  `ORDER_DATE` datetime DEFAULT NULL COMMENT '下单时间',
  `EXPECTED_PICK_DATE` varchar(32) DEFAULT NULL COMMENT '预定取件时间',
  `EXPECTED_DELIVERY_DATE` varchar(32) DEFAULT NULL COMMENT '预定配件时间',
  `CNT_SUB_ORD` int(11) DEFAULT NULL COMMENT '子订单数目',
  `CUSTOMER_CODE` varchar(32) DEFAULT NULL COMMENT '外部客户code,大润发的门店号',
  `CUSTOMER_NAME` varchar(32) DEFAULT NULL COMMENT '外部客户名称',
  `DELIVERY_DATE` varchar(16) DEFAULT NULL COMMENT '指定收货日期',
  `DELIVERY_START_TIME` varchar(8) DEFAULT NULL COMMENT '指定收货开始时间',
  `DELIVERY_END_TIME` varchar(8) DEFAULT NULL COMMENT '指定收货结束时间',
  `ORD_CREATE_DATE` timestamp NULL DEFAULT NULL COMMENT '订单日期',
  `ORD_STATUS` int(11) DEFAULT NULL COMMENT '处理过程中间状态',
  `ORD_LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  `ORD_REFERENCE_CODE` varchar(64) DEFAULT NULL COMMENT '取件单的原订单号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='母单备份表';

-- ----------------------------
-- Table structure for ord_ins_detail
-- ----------------------------
DROP TABLE IF EXISTS `ord_ins_detail`;
CREATE TABLE `ord_ins_detail` (
  `ORD_INS_ID` bigint(20) DEFAULT NULL COMMENT '内部订单ID',
  `LOGISTIC_CODE` varchar(64) DEFAULT NULL COMMENT '运单号',
  `LOGISTIC_SUB_CODE` varchar(64) DEFAULT NULL COMMENT '运单子号',
  `LOGISTIC_TYPE` tinyint(4) DEFAULT NULL COMMENT '物流类型1冷冻2冷藏3普通',
  `PACKAGE_NO` varchar(64) DEFAULT NULL COMMENT '箱号',
  `PACKAGE_LENGTH` decimal(10,2) DEFAULT NULL COMMENT '箱长',
  `PACKAGE_WIDTH` decimal(10,2) DEFAULT NULL COMMENT '箱宽',
  `PACKAGE_HEIGHT` decimal(10,2) DEFAULT NULL COMMENT '箱高',
  `PACKAGE_WEIGHT` decimal(10,2) DEFAULT NULL COMMENT '箱重量',
  `PACKAGE_VOLUME` decimal(10,2) DEFAULT NULL COMMENT '箱体积',
  `ORD_CREATE_DATE` timestamp NULL DEFAULT NULL COMMENT '订单日期',
  `ORD_STATUS` int(11) DEFAULT NULL COMMENT '子订单状态',
  `REMARKS` varchar(128) DEFAULT NULL COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='子单备份表';

-- ----------------------------
-- Table structure for ord_ins_detail_h_yyyymm
-- ----------------------------
DROP TABLE IF EXISTS `ord_ins_detail_h_yyyymm`;
CREATE TABLE `ord_ins_detail_h_yyyymm` (
  `ORD_INS_ID` bigint(20) DEFAULT NULL COMMENT '内部订单ID',
  `LOGISTIC_CODE` varchar(64) DEFAULT NULL COMMENT '运单号',
  `LOGISTIC_SUB_CODE` varchar(64) DEFAULT NULL COMMENT '运单子号',
  `LOGISTIC_TYPE` tinyint(4) DEFAULT NULL COMMENT '物流类型1冷冻2冷藏3普通',
  `PACKAGE_NO` varchar(64) DEFAULT NULL COMMENT '箱号',
  `PACKAGE_LENGTH` decimal(10,2) DEFAULT NULL COMMENT '箱长',
  `PACKAGE_WIDTH` decimal(10,2) DEFAULT NULL COMMENT '箱宽',
  `PACKAGE_HEIGHT` decimal(10,2) DEFAULT NULL COMMENT '箱高',
  `PACKAGE_WEIGHT` decimal(10,2) DEFAULT NULL COMMENT '箱号',
  `PACKAGE_VOLUME` decimal(10,2) DEFAULT NULL COMMENT '箱体积',
  `ORD_CREATE_DATE` timestamp NULL DEFAULT NULL COMMENT '订单日期',
  `ORD_STATUS` int(11) DEFAULT NULL COMMENT '子订单状态',
  `REMARKS` varchar(128) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ord_ins_goods
-- ----------------------------
DROP TABLE IF EXISTS `ord_ins_goods`;
CREATE TABLE `ord_ins_goods` (
  `ID` bigint(20) DEFAULT NULL COMMENT '内部订单ID',
  `LOGISTIC_CODE` varchar(64) DEFAULT NULL COMMENT '运单号',
  `LOGISTIC_SUB_CODE` varchar(64) DEFAULT NULL COMMENT '运单子号',
  `CUSTOMER_ORD_CODE` varchar(64) DEFAULT NULL COMMENT '客户订单号',
  `GOODS_NAME` varchar(256) DEFAULT NULL COMMENT '商品名称',
  `GOODS_DIMENSION` varchar(64) DEFAULT NULL COMMENT '商品描述',
  `GOODS_COLOR` varchar(32) DEFAULT NULL COMMENT '子订颜色',
  `GOODS_WEIGHT` decimal(10,2) DEFAULT NULL COMMENT '商品重量',
  `IS_BIG_FLAG` char(1) DEFAULT NULL COMMENT '是否为大件',
  `GOODS_QUANTITY` int(11) DEFAULT NULL COMMENT '商品数量',
  `CUSTOMER_ID` bigint(20) DEFAULT NULL COMMENT '客户在TMS唯一ID',
  `CUSTOMER_CODE` varchar(32) DEFAULT NULL COMMENT '客户code',
  `CREATE_DATE` timestamp NULL DEFAULT NULL COMMENT '入库时间',
  `REMARK` varchar(64) DEFAULT NULL COMMENT '说明'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品备份表';

-- ----------------------------
-- Table structure for ord_ins_goods_h_yyyymm
-- ----------------------------
DROP TABLE IF EXISTS `ord_ins_goods_h_yyyymm`;
CREATE TABLE `ord_ins_goods_h_yyyymm` (
  `ID` bigint(20) DEFAULT NULL COMMENT '内部订单ID',
  `LOGISTIC_CODE` varchar(64) DEFAULT NULL COMMENT '运单号',
  `LOGISTIC_SUB_CODE` varchar(64) DEFAULT NULL COMMENT '运单子号',
  `CUSTOMER_ORD_CODE` varchar(64) DEFAULT NULL COMMENT '箱号',
  `GOODS_NAME` varchar(256) DEFAULT NULL COMMENT '订单日期',
  `GOODS_DIMENSION` varchar(64) DEFAULT NULL COMMENT '商品尺寸',
  `GOODS_COLOR` varchar(32) DEFAULT NULL COMMENT '子订单状态',
  `GOODS_WEIGHT` decimal(10,2) DEFAULT NULL COMMENT '商品重量',
  `IS_BIG_FLAG` char(1) DEFAULT NULL COMMENT '是否为大件',
  `GOODS_QUANTITY` int(11) DEFAULT NULL COMMENT '商品数量',
  `CUSTOMER_ID` bigint(20) DEFAULT NULL COMMENT '客户在TMS唯一ID',
  `CUSTOMER_CODE` varchar(32) DEFAULT NULL COMMENT '客户code',
  `CREATE_DATE` timestamp NULL DEFAULT NULL COMMENT '入库时间',
  `REMARK` varchar(64) DEFAULT NULL COMMENT '说明'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ord_ins_h_yyyymm
-- ----------------------------
DROP TABLE IF EXISTS `ord_ins_h_yyyymm`;
CREATE TABLE `ord_ins_h_yyyymm` (
  `ORD_INS_ID` bigint(20) DEFAULT NULL COMMENT '内部订单ID',
  `LOGISTIC_KIND` tinyint(4) DEFAULT NULL COMMENT '1:送件单,0:取件单',
  `LOGISTIC_CODE` varchar(64) DEFAULT NULL COMMENT '运单号',
  `CUSTOMER_ORD_CODE` varchar(64) DEFAULT NULL COMMENT '订单号',
  `LOGISTIC_TYPE` tinyint(4) DEFAULT NULL COMMENT '物流类型1冷冻2冷藏3普通',
  `CUSTOMER_ID` bigint(20) DEFAULT NULL COMMENT '客户ID',
  `IS_COLLECTION` char(1) DEFAULT 'N' COMMENT '是否代收款.Y,N',
  `COLLECTION_AMT` decimal(10,2) DEFAULT NULL COMMENT '代收款',
  `CONSIGNEE` varchar(128) DEFAULT NULL COMMENT '收件人',
  `CONSIGNEE_TELEPHONE` varchar(32) DEFAULT NULL COMMENT '收件人电话',
  `CONSIGNEE_MOBILE` varchar(32) DEFAULT NULL COMMENT '收件人手机',
  `CONSIGNEE_ZIPCODE` varchar(32) DEFAULT NULL COMMENT '收件人邮编',
  `CONSIGNEE_PROVINCE` varchar(64) DEFAULT NULL COMMENT '收件人省份',
  `CONSIGNEE_CITY` varchar(64) DEFAULT NULL COMMENT '收件人城市',
  `CONSIGNEE_ADDRESS` varchar(128) DEFAULT NULL COMMENT '收件人详细地址',
  `INSURANCE_VALUE` decimal(10,2) DEFAULT NULL COMMENT '保险金额',
  `CONSIGNOR` varchar(128) DEFAULT NULL COMMENT '发件人',
  `CONSIGNOR_TELEPHONE` varchar(32) DEFAULT NULL COMMENT '发件人电话',
  `CONSIGNOR_MOBILE` varchar(32) DEFAULT NULL COMMENT '发件人手机',
  `CONSIGNOR_ZIPCODE` varchar(32) DEFAULT NULL COMMENT '发件人邮编',
  `CONSIGNOR_PROVINCE` varchar(64) DEFAULT NULL COMMENT '发件人省份',
  `CONSIGNOR_CITY` varchar(64) DEFAULT NULL COMMENT '发件人城市',
  `CONSIGNOR_ADDRESS` varchar(128) DEFAULT NULL COMMENT '发件人详细地址',
  `CONSIGNEE_EMAIL` varchar(64) DEFAULT NULL COMMENT '收件人邮箱',
  `CONSIGNOR_EMAIL` varchar(64) DEFAULT NULL COMMENT '发件人邮箱',
  `IS_BIG_FLAG` char(1) DEFAULT NULL COMMENT '是否为大件',
  `IS_NEED_INVOICE` char(1) DEFAULT NULL COMMENT '是否需要发票',
  `ORDER_DATE` datetime DEFAULT NULL COMMENT '下单时间',
  `EXPECTED_PICK_DATE` varchar(32) DEFAULT NULL COMMENT '预定取件时间',
  `EXPECTED_DELIVERY_DATE` varchar(32) DEFAULT NULL COMMENT '预定配件时间',
  `CNT_SUB_ORD` int(11) DEFAULT NULL COMMENT '子订单数目',
  `CUSTOMER_CODE` varchar(32) DEFAULT NULL COMMENT '外部客户code,大润发的门店号',
  `CUSTOMER_NAME` varchar(32) DEFAULT NULL COMMENT '外部客户名称',
  `DELIVERY_DATE` varchar(16) DEFAULT NULL COMMENT '指定收货日期',
  `DELIVERY_START_TIME` varchar(8) DEFAULT NULL COMMENT '指定收货开始时间',
  `DELIVERY_END_TIME` varchar(8) DEFAULT NULL COMMENT '指定收货结束时间',
  `ORD_CREATE_DATE` timestamp NULL DEFAULT NULL COMMENT '订单日期',
  `ORD_STATUS` int(11) DEFAULT NULL COMMENT '处理过程中间状态',
  `ORD_LAST_MODIFIED_DATE` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ord_ins_tmp
-- ----------------------------
DROP TABLE IF EXISTS `ord_ins_tmp`;
CREATE TABLE `ord_ins_tmp` (
  `ORD_INS_ID` bigint(20) NOT NULL DEFAULT '0' COMMENT '内部订单ID',
  `LOGISTIC_KIND` tinyint(4) DEFAULT NULL COMMENT '1:送件单,2:取件单',
  `LOGISTIC_CODE` varchar(64) DEFAULT NULL COMMENT '运单号',
  `LOGISTIC_SUB_CODE` varchar(64) DEFAULT NULL COMMENT '运单子号',
  `CUSTOMER_ORD_CODE` varchar(64) DEFAULT NULL COMMENT '订单号',
  `LOGISTIC_TYPE` tinyint(4) DEFAULT NULL COMMENT '物流类型1冷冻2冷藏3普通',
  `CUSTOMER_ID` varchar(32) DEFAULT NULL COMMENT '委托方code【借用字段】',
  `IS_COLLECTION` char(1) DEFAULT 'N' COMMENT '是否代收款.Y,N',
  `COLLECTION_AMT` decimal(10,2) DEFAULT NULL COMMENT '代收款',
  `CONSIGNEE` varchar(128) DEFAULT NULL COMMENT '收件人',
  `CONSIGNEE_TELEPHONE` varchar(32) DEFAULT NULL COMMENT '收件人电话',
  `CONSIGNEE_MOBILE` varchar(32) DEFAULT NULL COMMENT '收件人手机',
  `CONSIGNEE_ZIPCODE` varchar(32) DEFAULT NULL COMMENT '收件人邮编',
  `CONSIGNEE_PROVINCE` varchar(64) DEFAULT NULL COMMENT '收件人省份',
  `CONSIGNEE_CITY` varchar(64) DEFAULT NULL COMMENT '收件人城市',
  `CONSIGNEE_ADDRESS` varchar(128) DEFAULT NULL COMMENT '收件人详细地址',
  `INSURANCE_VALUE` decimal(10,2) DEFAULT NULL COMMENT '保险金额',
  `CONSIGNOR` varchar(128) DEFAULT NULL COMMENT '发件人',
  `CONSIGNOR_TELEPHONE` varchar(32) DEFAULT NULL COMMENT '发件人电话',
  `CONSIGNOR_MOBILE` varchar(32) DEFAULT NULL COMMENT '发件人手机',
  `CONSIGNOR_ZIPCODE` varchar(32) DEFAULT NULL COMMENT '发件人邮编',
  `CONSIGNOR_PROVINCE` varchar(64) DEFAULT NULL COMMENT '发件人省份',
  `CONSIGNOR_CITY` varchar(64) DEFAULT NULL COMMENT '发件人城市',
  `CONSIGNOR_ADDRESS` varchar(128) DEFAULT NULL COMMENT '发件人详细地址',
  `CONSIGNEE_EMAIL` varchar(64) DEFAULT NULL COMMENT '收件人邮箱',
  `CONSIGNOR_EMAIL` varchar(64) DEFAULT NULL COMMENT '发件人邮箱',
  `IS_BIG_FLAG` char(1) DEFAULT NULL COMMENT '大件与否',
  `IS_NEED_INVOICE` int(2) DEFAULT NULL COMMENT '是否需要发票',
  `ORDER_DATE` varchar(64) DEFAULT NULL COMMENT '下单时间',
  `EXPECTED_PICK_DATE` varchar(32) DEFAULT NULL COMMENT '预定取件时间',
  `EXPECTED_DELIVERY_DATE` varchar(32) DEFAULT NULL COMMENT '预定配件时间',
  `PACKAGE_NO` varchar(64) DEFAULT NULL COMMENT '箱号',
  `PACKAGE_LENGTH` decimal(10,2) DEFAULT NULL COMMENT '箱长',
  `PACKAGE_WIDTH` decimal(10,2) DEFAULT NULL COMMENT '箱宽',
  `PACKAGE_HEIGHT` decimal(10,2) DEFAULT NULL COMMENT '箱高',
  `PACKAGE_WEIGHT` decimal(10,2) DEFAULT NULL COMMENT '箱号',
  `PACKAGE_VOLUME` decimal(10,2) DEFAULT NULL COMMENT '箱体积',
  `CNT_SUB_ORD` int(11) DEFAULT NULL COMMENT '子订单数目',
  `CUSTOMER_CODE` varchar(32) DEFAULT NULL COMMENT '外部客户code,大润发的门店号',
  `CUSTOMER_NAME` varchar(32) DEFAULT NULL COMMENT '外部客户名称',
  `CUSTOMER_DOOR` varchar(32) DEFAULT NULL COMMENT '4位门店号，当仓库处理【飞牛默认青浦仓库】',
  `LOCATION_ID` varchar(32) DEFAULT NULL COMMENT '分拣中心',
  `DOMAIN_ID` varchar(32) DEFAULT NULL COMMENT '平台',
  `IS_MAIN_FLAG` char(1) DEFAULT 'N' COMMENT '主单标志',
  `DELIVERY_DATE` varchar(16) DEFAULT NULL COMMENT '指定收货日期',
  `DELIVERY_START_TIME` varchar(8) DEFAULT NULL COMMENT '指定收货开始时间',
  `DELIVERY_END_TIME` varchar(8) DEFAULT NULL COMMENT '指定收货结束时间',
  `ORD_CREATE_DATE` timestamp NULL DEFAULT NULL COMMENT '订单日期',
  `INTEGRATION_STATUS` int(11) DEFAULT NULL COMMENT '处理过程中间状态(0未处理，1处理成功，2处理失败)',
  `ORD_FILE_NAME` varchar(64) DEFAULT NULL COMMENT '订单文件名',
  `RESPOND_CODE` varchar(64) DEFAULT NULL COMMENT '回应代码',
  `RESPOND_CONTENT` varchar(100) DEFAULT NULL COMMENT '响应详细内容',
  `ORIGINATE` int(5) DEFAULT NULL COMMENT '自提处理过程中间状态(0未处理，1处理成功，2处理失败)',
  `BILL_CODE1` varchar(512) DEFAULT NULL,
  `MODIFIER` varchar(64) NOT NULL DEFAULT '' COMMENT '更新人',
  `MODIFIED_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `SEND_FLAG` int(11) NOT NULL DEFAULT '0' COMMENT '邮件发送标识，0未处理，1成功，2失败',
  `AREA_CODE` varchar(64) NOT NULL DEFAULT '' COMMENT '行政区域编码',
  `IS_SIGN_RECEIP` varchar(3) DEFAULT '0' COMMENT '签回单',
  `IS_WELLMEETING` varchar(3) DEFAULT '0' COMMENT '揪团',
  `IS_SAVE` varchar(3) NOT NULL DEFAULT '0' COMMENT '是否报价',
  `DELIVY_TYPE` varchar(3) NOT NULL DEFAULT '0' COMMENT '配送类型，非自提，自提',
  `SELF_EXTRACTION_CODE` varchar(32) NOT NULL DEFAULT '' COMMENT '自提商编码',
  `SELF_STORE_CODE` varchar(32) NOT NULL DEFAULT '' COMMENT '自提点编码',
  `SELF_STORE_NAME` varchar(64) NOT NULL DEFAULT '' COMMENT '自提点名称',
  `SELF_STORE_TEL` varchar(64) NOT NULL DEFAULT '' COMMENT '自提点电话',
  PRIMARY KEY (`ORD_INS_ID`),
  UNIQUE KEY `LOGISTIC_SUB_CODE` (`LOGISTIC_SUB_CODE`),
  KEY `IDX_ORD_INS_TMP_CODE` (`LOGISTIC_CODE`),
  KEY `IDX_ORD_INS_TMP_ORD_CODE` (`CUSTOMER_ORD_CODE`),
  KEY `IDX_ORD_INS_TMP_ORD_FILE_NAME` (`ORD_FILE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ord_ins_tmp_h
-- ----------------------------
DROP TABLE IF EXISTS `ord_ins_tmp_h`;
CREATE TABLE `ord_ins_tmp_h` (
  `ORD_INS_ID` bigint(20) DEFAULT NULL COMMENT '内部订单ID',
  `LOGISTIC_KIND` tinyint(4) DEFAULT NULL COMMENT '1:送件单,0:取件单',
  `LOGISTIC_CODE` varchar(64) DEFAULT NULL COMMENT '运单号',
  `LOGISTIC_SUB_CODE` varchar(64) DEFAULT NULL COMMENT '运单子号',
  `CUSTOMER_ORD_CODE` varchar(64) DEFAULT NULL COMMENT '订单号',
  `LOGISTIC_TYPE` tinyint(4) DEFAULT NULL COMMENT '物流类型1冷冻2冷藏3普通',
  `CUSTOMER_ID` bigint(20) DEFAULT NULL COMMENT '客户ID',
  `IS_COLLECTION` char(1) DEFAULT 'N' COMMENT '是否代收款.Y,N',
  `COLLECTION_AMT` decimal(10,2) DEFAULT NULL COMMENT '代收款',
  `CONSIGNEE` varchar(128) DEFAULT NULL COMMENT '收件人',
  `CONSIGNEE_TELEPHONE` varchar(32) DEFAULT NULL COMMENT '收件人电话',
  `CONSIGNEE_MOBILE` varchar(32) DEFAULT NULL COMMENT '收件人手机',
  `CONSIGNEE_ZIPCODE` varchar(32) DEFAULT NULL COMMENT '收件人邮编',
  `CONSIGNEE_PROVINCE` varchar(64) DEFAULT NULL COMMENT '收件人省份',
  `CONSIGNEE_CITY` varchar(64) DEFAULT NULL COMMENT '收件人城市',
  `CONSIGNEE_ADDRESS` varchar(128) DEFAULT NULL COMMENT '收件人详细地址',
  `INSURANCE_VALUE` decimal(10,2) DEFAULT NULL COMMENT '保险金额',
  `CONSIGNOR` varchar(128) DEFAULT NULL COMMENT '发件人',
  `CONSIGNOR_TELEPHONE` varchar(32) DEFAULT NULL COMMENT '发件人电话',
  `CONSIGNOR_MOBILE` varchar(32) DEFAULT NULL COMMENT '发件人手机',
  `CONSIGNOR_ZIPCODE` varchar(32) DEFAULT NULL COMMENT '发件人邮编',
  `CONSIGNOR_PROVINCE` varchar(64) DEFAULT NULL COMMENT '发件人省份',
  `CONSIGNOR_CITY` varchar(64) DEFAULT NULL COMMENT '发件人城市',
  `CONSIGNOR_ADDRESS` varchar(128) DEFAULT NULL COMMENT '发件人详细地址',
  `CONSIGNEE_EMAIL` varchar(64) DEFAULT NULL COMMENT '收件人邮箱',
  `CONSIGNOR_EMAIL` varchar(64) DEFAULT NULL COMMENT '发件人邮箱',
  `IS_BIG_FLAG` char(1) DEFAULT NULL COMMENT '大件与否',
  `IS_NEED_INVOICE` char(1) DEFAULT NULL COMMENT '是否需要发票',
  `ORDER_DATE` varchar(32) DEFAULT NULL COMMENT '下单时间',
  `EXPECTED_PICK_DATE` varchar(32) DEFAULT NULL COMMENT '预定取件时间',
  `EXPECTED_DELIVERY_DATE` varchar(32) DEFAULT NULL COMMENT '预定配件时间',
  `PACKAGE_NO` varchar(64) DEFAULT NULL COMMENT '箱号',
  `PACKAGE_LENGTH` decimal(10,2) DEFAULT NULL COMMENT '箱长',
  `PACKAGE_WIDTH` decimal(10,2) DEFAULT NULL COMMENT '箱宽',
  `PACKAGE_HEIGHT` decimal(10,2) DEFAULT NULL COMMENT '箱高',
  `PACKAGE_WEIGHT` decimal(10,2) DEFAULT NULL COMMENT '箱号',
  `PACKAGE_VOLUME` decimal(10,2) DEFAULT NULL COMMENT '箱体积',
  `CNT_SUB_ORD` int(11) DEFAULT NULL COMMENT '子订单数目',
  `CUSTOMER_CODE` varchar(32) DEFAULT NULL COMMENT '外部客户code,大润发的门店号',
  `CUSTOMER_NAME` varchar(32) DEFAULT NULL COMMENT '外部客户名称',
  `IS_MAIN_FLAG` char(1) DEFAULT 'N' COMMENT '主单标志',
  `DELIVERY_DATE` varchar(16) DEFAULT NULL COMMENT '指定收货日期',
  `DELIVERY_START_TIME` varchar(8) DEFAULT NULL COMMENT '指定收货开始时间',
  `DELIVERY_END_TIME` varchar(8) DEFAULT NULL COMMENT '指定收货结束时间',
  `ORD_CREATE_DATE` timestamp NULL DEFAULT NULL COMMENT '订单日期',
  `INTEGRATION_STATUS` int(11) DEFAULT NULL COMMENT '处理过程中间状态',
  `ORD_FILE_NAME` varchar(64) DEFAULT NULL COMMENT '订单文件名',
  `MODIFIER` varchar(64) NOT NULL DEFAULT '' COMMENT '更新人',
  `MODIFIED_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `SEND_FLAG` int(11) NOT NULL DEFAULT '0' COMMENT '邮件发送标识，0未处理，1成功，2失败',
  `IS_SAVE` varchar(3) NOT NULL DEFAULT '0' COMMENT '是否报价',
  `DELIVY_TYPE` varchar(3) NOT NULL DEFAULT '0' COMMENT '配送类型，非自提，自提',
  `SELF_EXTRACTION_CODE` varchar(32) NOT NULL DEFAULT '' COMMENT '自提商编码',
  `SELF_STORE_CODE` varchar(32) NOT NULL DEFAULT '' COMMENT '自提点编码',
  `SELF_STORE_NAME` varchar(64) NOT NULL DEFAULT '' COMMENT '自提点名称',
  `SELF_STORE_TEL` varchar(64) NOT NULL DEFAULT '' COMMENT '自提点电话'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pda_pos_records
-- ----------------------------
DROP TABLE IF EXISTS `pda_pos_records`;
CREATE TABLE `pda_pos_records` (
  `id` bigint(32) NOT NULL DEFAULT '0' COMMENT '业务主键Id',
  `transaction_id` varchar(64) DEFAULT NULL COMMENT '主业务编号	刷卡银行名称',
  `accunt` varchar(64) DEFAULT NULL COMMENT '操作账户			派件员',
  `terminal_id` varchar(64) DEFAULT NULL COMMENT '终端ID		POS机ID标识',
  `request_time` varchar(64) DEFAULT NULL COMMENT '交易时间		刷卡时间',
  `varsion` varchar(64) DEFAULT NULL COMMENT '版本号',
  `terminal_charge` varchar(32) DEFAULT NULL COMMENT '收货人(签收人关联订单的收件人姓名)		签收人',
  `order_no` varchar(64) DEFAULT NULL COMMENT '订单号		运单编号',
  `order_par_able_amt` varchar(32) DEFAULT NULL COMMENT '订单金额	代收货款',
  `pay_type` varchar(10) DEFAULT NULL COMMENT '交易类型（如果刷卡成功，这个默认传‘刷卡’失败不处理）	支付方式',
  `trace_no` varchar(64) DEFAULT NULL COMMENT '银行卡号',
  `consignee_sign_flag` varchar(32) DEFAULT NULL COMMENT 'Y/N',
  `status` varchar(10) DEFAULT NULL COMMENT '如果刷卡成功，这个默认传‘1’失败不处理',
  `dealtag` varchar(10) DEFAULT NULL COMMENT '代号含义：1-要处理，0或空-不要处理',
  `dealtime` varchar(64) DEFAULT NULL COMMENT '开始处理的时间',
  `create_date` timestamp NULL DEFAULT NULL COMMENT '入库时间',
  `MODIFIER_ID` bigint(20) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIER` varchar(64) DEFAULT NULL COMMENT '修改人',
  `MODIFIED_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `CREATOR_ID` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `CREATOR` varchar(64) DEFAULT NULL COMMENT '创建人',
  `CREATED_TIME` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `request_time` (`request_time`,`order_no`,`terminal_id`),
  KEY `IDX_ORDER_HEADER_ORDER_NO` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='PDA pos记录表';

-- ----------------------------
-- Table structure for p_report_order
-- ----------------------------
DROP TABLE IF EXISTS `p_report_order`;
CREATE TABLE `p_report_order` (
  `DETAILID` bigint(20) NOT NULL COMMENT '子单ID',
  `DETAILCODE` varchar(64) NOT NULL DEFAULT '' COMMENT '子单号（托运子单号）',
  `HEADERID` bigint(20) NOT NULL COMMENT '母单ID',
  `HEADERCODE` varchar(64) NOT NULL DEFAULT '' COMMENT '母单号（托运母单号）',
  `ISMAIN` int(2) NOT NULL COMMENT '是否主子单',
  `CUSTOMERORDERCODE` varchar(64) NOT NULL DEFAULT '' COMMENT '客户订单号',
  `TYPE` int(11) NOT NULL DEFAULT '0' COMMENT '1，配送单 2：取件单',
  `STATUS` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
  `DELIVERYSTATE` int(11) NOT NULL DEFAULT '0' COMMENT '配送状态',
  `DELIVERYSTAFFID` bigint(20) NOT NULL DEFAULT '0' COMMENT '配送员ID',
  `CENTERSHIPPINGID` bigint(20) NOT NULL DEFAULT '0' COMMENT '分拨中心ID',
  `DEPOTID` bigint(20) NOT NULL DEFAULT '0' COMMENT '配送站点',
  `CARRIERID` bigint(20) NOT NULL DEFAULT '0' COMMENT '配送商ID',
  `CUSTOMERCODE` varchar(64) NOT NULL DEFAULT '' COMMENT '物流商编号（公司赋予物流商的客户编号）',
  `TOLOCATIONID` bigint(20) NOT NULL DEFAULT '0' COMMENT '目的地ID',
  `APPOINTDELIVERYDATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '指定到达日期',
  `APPOINTDELIVERYSTART` varchar(64) NOT NULL DEFAULT '' COMMENT '指定开始结束时段，HHMM (24小时制)',
  `APPOINTDELIVERYEND` varchar(64) NOT NULL DEFAULT '' COMMENT '指定到货结束时段，HHMM (24小时制)',
  `ARRIVEDCTIME` varchar(64) NOT NULL DEFAULT '' COMMENT '入分拣中心时间',
  `LEAVEDCTIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '出分拨中心时间',
  `ARRIVEDEPOTTIME` varchar(64) NOT NULL DEFAULT '' COMMENT '入站时间',
  `LEAVEDEPOTTIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '出配送站时间',
  `RETURNTIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '回单时间',
  `CREATEDTIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `CUSTOMERID` bigint(20) NOT NULL DEFAULT '0' COMMENT '委托方ID',
  `FROMLOCATIONID` bigint(20) NOT NULL DEFAULT '0' COMMENT '发货地',
  `DELIVERYMODE` int(11) NOT NULL DEFAULT '0' COMMENT '配送方式',
  `ISSELFDISTRIBUTION` varchar(4) NOT NULL DEFAULT '' COMMENT '是否自配送',
  KEY `IDX_TYPE` (`TYPE`),
  KEY `IDX_STATUS` (`STATUS`),
  KEY `IDX_DELIVERYSTATE` (`DELIVERYSTATE`),
  KEY `IDX_CENTERSHIPPINGID` (`CENTERSHIPPINGID`),
  KEY `IDX_DEPOTID` (`DEPOTID`),
  KEY `IDX_TOLOCATION` (`TOLOCATIONID`),
  KEY `IDX_FROMLOCATIONID` (`FROMLOCATIONID`),
  KEY `IDX_DELIVERYSTAFFID` (`DELIVERYSTAFFID`),
  KEY `IDX_ARRIVEDCTIME` (`ARRIVEDCTIME`),
  KEY `IDX_LEAVDCTIME` (`LEAVEDCTIME`),
  KEY `IDX_ARRIVEDEPOITTIME` (`ARRIVEDEPOTTIME`),
  KEY `IDX_LEAVEDEPOTTIME` (`LEAVEDEPOTTIME`),
  KEY `IDX_RETURNTIME` (`RETURNTIME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
/*!50100 PARTITION BY RANGE (DETAILID)
(PARTITION p201506 VALUES LESS THAN (2016) ENGINE = InnoDB,
 PARTITION p201507 VALUES LESS THAN (2017) ENGINE = InnoDB) */;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='job设置表';

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='job设置表';

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='job设置表';

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='job设置表';

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='job设置表';

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='job设置表';

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='job设置表';

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='job设置表';

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='job设置表';

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='job设置表';

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='job设置表';

-- ----------------------------
-- Table structure for receipt_record
-- ----------------------------
DROP TABLE IF EXISTS `receipt_record`;
CREATE TABLE `receipt_record` (
  `ID` bigint(20) NOT NULL DEFAULT '0' COMMENT '主键ID',
  `HEADERID` bigint(20) NOT NULL DEFAULT '0' COMMENT '母单ID',
  `HEADERCODE` varchar(64) NOT NULL DEFAULT '' COMMENT '母单CODE',
  `DETAILID` bigint(20) NOT NULL DEFAULT '0' COMMENT '子单ID',
  `DETAILCODE` varchar(64) NOT NULL DEFAULT '' COMMENT '子单CODE',
  `ORDER_TYPE` int(2) NOT NULL DEFAULT '0' COMMENT '订单类型',
  `LOCATIONID` bigint(20) NOT NULL DEFAULT '0' COMMENT '站点ID',
  `STAFFID` bigint(20) NOT NULL DEFAULT '0' COMMENT '配送员ID',
  `ORDERSTATE` int(11) NOT NULL DEFAULT '0' COMMENT '订单状态',
  `DELIVERYSTATE` int(11) NOT NULL DEFAULT '0' COMMENT '配送状态',
  `CREATETIME` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `OPERATOR_ID` bigint(20) NOT NULL DEFAULT '0' COMMENT '操作人ID',
  `OPERATOR` varchar(64) NOT NULL DEFAULT '' COMMENT '操作人',
  `REASON` varchar(200) NOT NULL DEFAULT '' COMMENT '原因',
  `REMARK` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for recover_order
-- ----------------------------
DROP TABLE IF EXISTS `recover_order`;
CREATE TABLE `recover_order` (
  `ID` bigint(20) NOT NULL,
  `DOC_DATE` varchar(64) NOT NULL DEFAULT '' COMMENT '档产生日期',
  `DOC_TYPE` varchar(3) NOT NULL DEFAULT '' COMMENT '单据类型',
  `ORDER_NO` varchar(64) NOT NULL DEFAULT '' COMMENT '订单编号,必填，16位FN订单号+2位字母+6位流水号',
  `DOOR` varchar(32) NOT NULL DEFAULT '' COMMENT '门店',
  `SHIPMENTS` varchar(64) NOT NULL DEFAULT '' COMMENT '出货者',
  `CANCEL_NO` varchar(64) NOT NULL DEFAULT '1' COMMENT '退订单编号',
  `DELIVERY_TYPE` varchar(3) NOT NULL DEFAULT '' COMMENT '订单类型,0：转单；1：门店 2：揪团	转单不解析配送商',
  `CT_NAME` varchar(64) NOT NULL DEFAULT '' COMMENT '收货人名称',
  `CT_ADDRESS_PROVINCE` varchar(32) NOT NULL DEFAULT '' COMMENT '收货地址省',
  `CT_ADDRESS_CITY` varchar(64) NOT NULL DEFAULT '' COMMENT '市',
  `CT_ADDRESS_DISTRICT` varchar(256) NOT NULL DEFAULT '' COMMENT '区',
  `CT_ADDRESS` varchar(256) NOT NULL DEFAULT '' COMMENT '地址',
  `POST_CODE` varchar(64) NOT NULL DEFAULT '' COMMENT '行政代码',
  `CT_TEL` varchar(64) NOT NULL DEFAULT '' COMMENT '收件人电话',
  `CT_MB` varchar(64) NOT NULL DEFAULT '' COMMENT '手机黯然手机',
  `IS_INVOICE` varchar(3) NOT NULL DEFAULT '0' COMMENT '是否打印发票，0:不打印	1:要打印',
  `IV_NAME` varchar(100) NOT NULL DEFAULT '' COMMENT '发票抬头',
  `PAY_TYPE` varchar(3) NOT NULL DEFAULT '0' COMMENT '0:货到付款	1:飞牛收款',
  `BEFOREDIS_COUNT` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '折扣前商品总金额',
  `SHANGPINSHU` varchar(8) NOT NULL DEFAULT '1' COMMENT '商品行数',
  `IS_PREORDER` varchar(4) NOT NULL DEFAULT '0' COMMENT '0 : 非预购商品订单	1 : 预购商品订单',
  `ESTDELIVER_DATE` varchar(16) NOT NULL DEFAULT '' COMMENT 'YYYYMMDD	E.G. 20150110',
  `DELIVER_DATE` varchar(16) NOT NULL DEFAULT '' COMMENT 'YYYYMMDD	E.G. 20150110',
  `ARRIVE_DATE` varchar(16) NOT NULL DEFAULT '' COMMENT 'YYYYMMDD	E.G. 20150110',
  `ARRIVETIME_START` varchar(16) NOT NULL DEFAULT '' COMMENT '指定到货开始时间	STRING(8)	HH:MM (24小时制)	E.G. 10:00	为空',
  `ARRIVETIME_END` varchar(16) NOT NULL DEFAULT '' COMMENT '指定到货结束时间	STRING(8)	HH:MM (24小时制)	E.G. 12:00	为空',
  `RETURN_TYPE` varchar(8) NOT NULL DEFAULT '0' COMMENT '0 : 不处理	1 : 要处理	是否要取回退货商品	>生鲜转单订单:按实际情况赋值	>O2O门店订单:默认值=1',
  `STATUS` int(2) NOT NULL DEFAULT '0' COMMENT '处理状态',
  `MODIFIER` varchar(64) NOT NULL DEFAULT '' COMMENT '修改人',
  `MODIFIED_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `CREATOR` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人',
  `CREATED_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `COMMENT` varchar(256) NOT NULL DEFAULT '' COMMENT '备注',
  `IS_DELETED` int(2) NOT NULL DEFAULT '0' COMMENT '删除标识',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='仓库回收退订单表';

-- ----------------------------
-- Table structure for recover_order_goods
-- ----------------------------
DROP TABLE IF EXISTS `recover_order_goods`;
CREATE TABLE `recover_order_goods` (
  `ID` bigint(20) NOT NULL,
  `ORDER_CODE` varchar(64) NOT NULL DEFAULT '' COMMENT '取件单号',
  `NUM` varchar(8) NOT NULL DEFAULT '1' COMMENT '序号',
  `RTHuoHao` varchar(40) NOT NULL DEFAULT '' COMMENT 'RT货号',
  `IT_NAME` varchar(255) NOT NULL DEFAULT '' COMMENT '商品名称',
  `SHIP_CNT` varchar(6) NOT NULL DEFAULT '0' COMMENT '数量',
  `IT_LONG` varchar(10) NOT NULL DEFAULT '0' COMMENT '长',
  `IT_WIDE` varchar(10) NOT NULL DEFAULT '0' COMMENT '宽',
  `IT_HIGH` varchar(10) NOT NULL DEFAULT '0' COMMENT '高',
  `STATUS` int(2) NOT NULL DEFAULT '0' COMMENT '处理状态',
  `MODIFIER` varchar(64) NOT NULL DEFAULT '' COMMENT '修改人',
  `MODIFIED_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `CREATOR` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人',
  `CREATED_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `REMARK` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `IS_DELETED` int(11) NOT NULL DEFAULT '0' COMMENT '删除标识',
  PRIMARY KEY (`ID`),
  KEY `IDX_RECOVER_ORDER_GOODS_ORDER_COD` (`ORDER_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='仓库回收商品表';

CREATE TABLE `sec_auth_role_set` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `ROLE_ID` bigint(20) DEFAULT NULL COMMENT '角色id',
  `FUNCTION_SET_ID` bigint(20) DEFAULT NULL COMMENT '功能集id',
  `STATE` int(11) DEFAULT NULL COMMENT '状态（全部为1）',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限关联表';

CREATE TABLE `sec_auth_station_type` (
  `ID` bigint(20) NOT NULL,
  `STATION_TYPE` bigint(20) DEFAULT NULL,
  `FUNCTION_SET_ID` bigint(20) DEFAULT NULL,
  `STATE` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限配置表';

CREATE TABLE `sec_auth_user_set` (
  `ID` bigint(20) NOT NULL,
  `USER_ID` bigint(20) DEFAULT NULL,
  `FUNCTION_SET_ID` bigint(20) DEFAULT NULL,
  `STATE` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户功能表';

CREATE TABLE `sec_data` (
  `SEC_USER_ID` bigint(20) DEFAULT NULL,
  `ID` bigint(20) DEFAULT NULL,
  `SEC_ORG_ID` bigint(20) DEFAULT NULL,
  `CREATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `EXPIRE_DATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `EFFECTIVE_DATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sec_entity` (
  `ENTITY_ID` bigint(20) NOT NULL,
  `ENTITY_NAME` varchar(32) DEFAULT NULL,
  `DESCRIPTION` varchar(128) DEFAULT NULL,
  `ENTITY_CLASS_ID` bigint(20) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ENTITY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='实体';

CREATE TABLE `sec_entity_behaviour` (
  `ENTITY_BEHAVIOUR_ID` bigint(20) NOT NULL,
  `ENTITY_BEHAVIOUR_NAME` varchar(32) DEFAULT NULL,
  `DESCRIPTION` varchar(128) DEFAULT NULL,
  `ENTITY_CLASS_ID` bigint(20) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ENTITY_BEHAVIOUR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='实体行为';

CREATE TABLE `sec_entity_class` (
  `ENTITY_CLASS_ID` bigint(20) NOT NULL,
  `ENTITY_CLASS_NAME` varchar(32) DEFAULT NULL,
  `DESCRIPTION` varchar(128) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ENTITY_CLASS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='实体类';

CREATE TABLE `sec_function` (
  `FUNCTION_ID` bigint(20) NOT NULL COMMENT '主键',
  `FUNCTION_NAME` varchar(64) DEFAULT NULL COMMENT '菜单名称',
  `FUNCTION_URL` varchar(128) DEFAULT NULL COMMENT 'url',
  `STATUS` tinyint(4) DEFAULT NULL COMMENT '0 禁用  1启用',
  `FUNTYPE` tinyint(4) NOT NULL DEFAULT '1' COMMENT '菜单类型:1-tms菜单 2-pda菜单',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '最后修改时间',
  `PARENT_FUNCTION_ID` bigint(20) DEFAULT '-1' COMMENT '父菜单名称',
  `FUNCTION_LEVEL` int(11) DEFAULT NULL COMMENT '级别   0:第一级   1:第二级……',
  `FUNCTION_ORDER` int(11) DEFAULT NULL COMMENT '排序',
  `ICON_ID` bigint(20) DEFAULT NULL COMMENT '图标id',
  PRIMARY KEY (`FUNCTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='菜单表';

CREATE TABLE `sec_function_set` (
  `FUNCTION_SET_ID` bigint(20) NOT NULL COMMENT '功能集id',
  `FUNCTION_SET_NAME` varchar(64) DEFAULT NULL COMMENT '功能集名称',
  `FUNCTION_SET_TYPE` varchar(64) DEFAULT NULL COMMENT '功能集类别',
  PRIMARY KEY (`FUNCTION_SET_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='功能集表';

CREATE TABLE `sec_function_set_function` (
  `FUNCTION_SET_ID` bigint(20) NOT NULL COMMENT '功能集id',
  `FUNCTION_ID` bigint(20) DEFAULT NULL COMMENT '菜单id',
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `FUNCTION_OPERATIONS` varchar(256) DEFAULT NULL COMMENT '操作实体id，多个以逗号隔开',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='功能集菜单中间表';

CREATE TABLE `sec_function_set_station` (
  `STATION_ID` bigint(20) DEFAULT NULL COMMENT '岗位id',
  `FUNCTION_SET_ID` bigint(20) DEFAULT NULL COMMENT '功能集id',
  `ID` bigint(20) NOT NULL COMMENT '主键'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='岗位功能集中间表';

CREATE TABLE `sec_icon` (
  `ICON_ID` varchar(32) NOT NULL,
  `EXTEND` varchar(255) DEFAULT NULL,
  `ICONCLAS` varchar(200) DEFAULT NULL,
  `CONTENT` blob,
  `NAME` varchar(100) NOT NULL,
  `PATH` varchar(300) DEFAULT NULL,
  `TYPE` int(11) DEFAULT NULL,
  PRIMARY KEY (`ICON_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sec_operation` (
  `OPERATION_ID` bigint(20) NOT NULL COMMENT '主键',
  `FUNCTION_ID` bigint(20) DEFAULT NULL COMMENT '所属菜单id',
  `OPERATION_CODE` varchar(64) DEFAULT NULL COMMENT '实体编码',
  `OPERATION_NAME` varchar(64) DEFAULT NULL COMMENT '名称',
  `STATUS` bigint(20) DEFAULT NULL COMMENT '0 禁用\n  1启用',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '最后修改时间',
  `ICON_ID` bigint(20) DEFAULT NULL COMMENT '图标id',
  PRIMARY KEY (`OPERATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='操作实体表';

CREATE TABLE `sec_org` (
  `ORG_ID` bigint(20) NOT NULL,
  `ORG_NAME` varchar(64) NOT NULL COMMENT '部门名称',
  `PARENT_ID` bigint(20) DEFAULT NULL COMMENT '上级部门id',
  `ORG_ID_TREE_CODE` varchar(512) DEFAULT NULL,
  `PRINCIPAL_ID` bigint(20) DEFAULT NULL COMMENT '部门负责人id',
  `SHORT_NAME` varchar(32) DEFAULT NULL COMMENT '简称',
  `OFFICE_PHONE` varchar(32) DEFAULT NULL COMMENT '电话',
  `CODE` varchar(32) DEFAULT NULL COMMENT '代码',
  `TYPE` varchar(4) DEFAULT NULL COMMENT '0:公司  1:平台  2：分拣中心  3:配送站 4:3PL  5:仓库 6:自配送的配送商',
  `PROVINCE_ID` bigint(20) DEFAULT NULL COMMENT '省份id',
  `CITY_ID` bigint(20) DEFAULT NULL COMMENT '城市',
  `TELEPHONE` varchar(64) DEFAULT NULL COMMENT '联系电话',
  `CONTACT` varchar(64) DEFAULT NULL COMMENT '联系人',
  `EMAIL` varchar(64) DEFAULT NULL COMMENT '联系邮箱',
  `ADDRESS` varchar(128) DEFAULT NULL COMMENT '地址',
  `ZIPCODE` varchar(16) DEFAULT NULL COMMENT '邮编',
  `REMARK` varchar(256) DEFAULT NULL COMMENT '备注',
  `IS_DELETED` tinyint(4) DEFAULT NULL COMMENT '是否删除',
  `SOUNT_FILE_NAME` varchar(64) DEFAULT NULL COMMENT '声音文件',
  `CREATOR_ID` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `CREATOR` varchar(64) DEFAULT NULL COMMENT '创建人',
  `CREATED_TIME` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `MODIFIER_ID` bigint(20) DEFAULT NULL COMMENT '修改人id',
  `MODIFIER` varchar(64) DEFAULT NULL COMMENT '修改人',
  `MODIFIED_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `EXPRESS_KEY` varchar(256) DEFAULT NULL COMMENT '3pl专用,密钥',
  `EXPRESS_URL` varchar(512) DEFAULT NULL COMMENT '3pl专用,3plurl',
  `SITE_ANALYZE_URL` varchar(512) NOT NULL DEFAULT '' COMMENT '3PL专用，3pl站点解析地址',
  `IS_ENABLED` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否启用',
  `SUB_TYPE` varchar(2) DEFAULT NULL COMMENT '子类别',
  `DG_CODE` varchar(32) DEFAULT NULL COMMENT 'DG编码',
  `IS_RECEIVE` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否接收',
  `TRANSPORT_CAPACITY` bigint(20) NOT NULL DEFAULT '1' COMMENT '运能',
  `PRIORITY` tinyint(4) NOT NULL DEFAULT '1' COMMENT '优先级',
  `DEPOT` bigint(20) NOT NULL DEFAULT '0' COMMENT '仓库',
  `ORG_LEVEL` tinyint(4) NOT NULL DEFAULT '0' COMMENT '级别',
  `IS_DEFAULT` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否默认(1是,0不是)',
  PRIMARY KEY (`ORG_ID`),
  KEY `IDX_SEC_ORG_TYPE` (`TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='组织表';

CREATE TABLE `sec_org_station` (
  `ORG_ID` bigint(20) DEFAULT NULL,
  `STATION_ID` bigint(20) DEFAULT NULL,
  `ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sec_role` (
  `ROLE_ID` bigint(20) NOT NULL COMMENT '角色ID',
  `ROLE_NAME` varchar(64) DEFAULT NULL COMMENT '角色名称',
  `DESCRIPTION` varchar(128) DEFAULT NULL COMMENT '描述',
  `EFFECTIVE_DATE` datetime DEFAULT NULL COMMENT '开始生效日期',
  `EXPIRE_DATE` datetime DEFAULT NULL COMMENT '失效日期2099-12-31',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `ROLE_CODE` varchar(32) DEFAULT NULL COMMENT '角色代码',
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='角色表';

CREATE TABLE `sec_role_user` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `ROLE_ID` bigint(20) DEFAULT NULL COMMENT '角色id',
  `USER_ID` bigint(20) DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='用户角色表';

CREATE TABLE `sec_staff` (
  `ID` bigint(20) NOT NULL,
  `CODE` varchar(32) NOT NULL COMMENT '工号',
  `NAME` varchar(32) NOT NULL COMMENT '姓名',
  `GENDER` tinyint(4) DEFAULT NULL COMMENT '性别',
  `ID_CARD` varchar(32) NOT NULL COMMENT '身份证号',
  `EMPLOYED_DATE` date NOT NULL COMMENT '入职日期',
  `TELEPHONE` varchar(32) DEFAULT NULL COMMENT '联系电话',
  `QUIT_DATE` date DEFAULT NULL COMMENT '离职日期',
  `NATIVE_PROVINCE` varchar(64) DEFAULT NULL COMMENT '籍贯（省）',
  `NATIVE_CITY` varchar(64) DEFAULT NULL COMMENT '籍贯（市）',
  `NATIVE_ADDRESS` varchar(512) DEFAULT NULL COMMENT '户籍地址',
  `CURRENT_ADDRESS` varchar(512) DEFAULT NULL COMMENT '目前住址',
  `IS_DELETED` tinyint(4) DEFAULT NULL COMMENT '是否失效',
  `CREATOR_ID` bigint(20) NOT NULL COMMENT '创建人ID',
  `CREATOR` varchar(64) NOT NULL COMMENT '创建人姓名',
  `CREATED_TIME` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `MODIFIER_ID` bigint(20) NOT NULL COMMENT '修改人ID',
  `MODIFIER` varchar(64) NOT NULL COMMENT '修改人姓名',
  `MODIFIED_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sec_station` (
  `STATION_ID` bigint(20) NOT NULL COMMENT '主键',
  `STATION_CODE` varchar(64) DEFAULT NULL COMMENT '岗位编码',
  `STATION_NAME` varchar(64) DEFAULT NULL COMMENT '岗位名称',
  PRIMARY KEY (`STATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='岗位表';

CREATE TABLE `sec_user` (
  `USER_ID` bigint(20) NOT NULL COMMENT '用户唯一ID',
  `USER_NAME` varchar(32) DEFAULT NULL COMMENT '用户名',
  `REAL_NAME` varchar(64) DEFAULT NULL COMMENT '真实名称',
  `PASSWORD` varchar(128) DEFAULT NULL COMMENT '密码',
  `STATUS` tinyint(4) DEFAULT NULL COMMENT '1：启用  0;禁用',
  `DEPART_ID` bigint(20) DEFAULT NULL COMMENT '所在部门ID',
  `EMAIL` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `MOBILE_PHONE` varchar(32) DEFAULT NULL COMMENT '电话',
  `OFFICE_PHONE` varchar(32) DEFAULT NULL COMMENT '办公电话',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `SUPERVISOR_ID` bigint(20) DEFAULT NULL COMMENT '直属领导',
  `STAFF_ID` bigint(20) DEFAULT NULL COMMENT '员工表id',
  `STATION_id` bigint(20) DEFAULT NULL COMMENT '岗位id',
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='用户表';


