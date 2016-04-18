<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
<head>
<meta charset="utf-8">
<title>xzeng Demo</title>
<t:base type="jquery,easyui,tools,fn,DatePicker,autocomplete"></t:base>
<link rel="shortcut icon" href="${webRoot}/images/favicon.ico">
<link rel="shortcut icon" href="${webRoot}/images/favicon.ico">
<link rel="stylesheet" type="text/css" href="${webRoot}/plug-in/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${webRoot}/plug-in/easyui/themes/icon.css">
</head>
<body class="easyui-layout" style="overflow-y: hidden, width:100%" scroll="no" fit="true">
  <div region="north" border="false" split="false" collapsible="false" style="height: 87px">
	 <div class="header_bg">
	   <span class="logo"></span>
	   <div class="user_conten">
	     <div class="user_head">
	       <!-- <span></span> -->
	       <ul class="user_wa">
			<%--  <li class="font1">Welcome</li>
			 <li class="font2" style="overflow: hidden">(${userName })</li> --%>
		   </ul>
		 </div>
		 <div class="user_btn_warp">
			<a href="#" class="edit_user_btn" onclick="addTab('用户信息','userController.do?userinfo')" title="用户信息" id="btnUserInfo"></a>
		    <a href="loginController.do?logout" class="close_system_btn" title="登出" id="btnLogout"></a>
		 </div>
	   </div>
	   <div style="float: right;margin-top: 50px;font-family: 微软雅黑;font-size: 17px">
		<%--  <span >当前作业点：</span>
		 <span class="font1" id = "">${currLocationCom }</span>
		 <span class="font1" id = "currLocation">${currLocation }</span>
		 <a style="margin-left: 20px" href="#" onclick="createnobtnwindow('切换作业点','userController.do?changeLocation',660,300)">切换</a> --%>
	   </div>
	</div>
	<div class="header_shadow">
		<div class="header_left_t"></div>
	</div>
	</div>
	
	<!-- 左边菜单栏 -->
	<div region="west" border="false" split="false" collapsible="false" href="${webRoot}/loginController.htm?left" style="width: 210px" class="contenter_left left_bg">
	</div>
		
	<div id="mainPanle" region="center" border="false" split="false" style="height: 100%, overflow: hidden;">
		<div id="maintabs" class="easyui-tabs" fit="true" border="false">
			<div class="easyui-tab" title="首页" href="${webRoot}/loginController.htm?home" style="padding: 2px; overflow: hidden;"></div>
		</div>
	</div>
</body>
</html>