<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>新 增 用 户</title>
	<script language="javascript">
	</script>
</head>

<body>
	<form method="post" action="${path}/user/addUser.htm">
		<table>
			<tr>
				<td>用户名：</td>
				<td><input type="text" name="userName" /></td>
			</tr>
			<tr>
				<td>密码：</td>
				<td><input type="text" name="userPass" /></td>
			</tr>
			<tr>
				<td>姓名：</td>
				<td><input type="text" name="realName" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" name="提交" /></td>
			</tr>
		</table>
	</form>
</body>