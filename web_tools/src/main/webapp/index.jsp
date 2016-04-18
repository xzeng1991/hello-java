<%@ page contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <%@include file="/context/mytags.jsp"%>
  <title></title>
  <script type="text/javascript">
	function redirect(){
		window.location.href='<%=basePath%>/loginController.htm?login';
	}
  </script>
</head>
  <body onload="redirect()">
  </body>
</html>