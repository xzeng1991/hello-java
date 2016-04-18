<%@ taglib prefix="t" uri="/easyui-tags"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<% 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<c:set var="webRoot" value="<%=basePath%>" />
<!-- jquery  -->
<%-- <script type="text/javascript" src="${webRoot}/plug-in/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${webRoot}/plug-in/jquery/jquery.form-3.20.min.js"></script>
<script type="text/javascript" src="${webRoot}/plug-in/jquery/jquery.multiSelect.min.js"></script>
<link rel="stylesheet" href="${webRoot}/plug-in/jquery-ui/multiSelect/jquery.multiSelect.min.css" type="text/css"></link> --%>

<!-- ckeditor -->
<%-- <script type="text/javascript" src="${webRoot}/plug-in/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="${webRoot}/plug-in/tools/ckeditorTool.js"></script> --%>

<!-- ckfinder -->
<%-- <script type="text/javascript" src="${webRoot}/plug-in/ckfinder/ckfinder.js"></script>
<script type="text/javascript" src="${webRoot}/plug-in/tools/ckfinderTool.js"></script> --%>

<!-- easy UI -->
<%-- <script type="text/javascript" src="${webRoot}/plug-in/tools/dataformat.js"></script>
<script type="text/javascript" src="${webRoot}/plug-in/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${webRoot}/plug-in/easyui/ajax.utils.js"></script>
<script type="text/javascript" src="${webRoot}/plug-in/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${webRoot}/plug-in/tools/syUtil.js"></script>
<script type="text/javascript" src="${webRoot}/plug-in/easyui/extends/datagrid-scrollview.js"></script>
<link rel="stylesheet" href="${webRoot}/plug-in/easyui/themes/default/easyui.css" type="text/css"></link>
<link rel="stylesheet" href="${webRoot}/plug-in/easyui/themes/icon.css" type="text/css"></link>
<link rel="stylesheet" href="${webRoot}/plug-in/accordion/css/accordion.css" type="text/css">
<link rel="stylesheet" href="${webRoot}/plug-in/easyui/themes/default/easyui.css" type="text/css"> --%>

<!-- DatePicker -->
<script type="text/javascript" src="${webRoot}/plug-in/My97DatePicker/WdatePicker.js"></script>

<!-- Jquery UI -->
<%-- <script type="text/javascript" src="${webRoot}/plug-in/jquery-ui/js/jquery-ui-1.9.2.custom.min.js"></script>
<link rel="stylesheet" href="${webRoot}/plug-in/jquery-ui/css/ui-lightness/jquery-ui-1.9.2.custom.min.css" type="text/css"></link> --%>

<!-- jqueryui-sortable -->
<%-- <script type="text/javascript" src="${webRoot}/plug-in/jquery-ui/js/ui/jquery.ui.core.js"></script>
<script type="text/javascript" src="${webRoot}/plug-in/jquery-ui/js/ui/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${webRoot}/plug-in/jquery-ui/js/ui/jquery.ui.mouse.js"></script>
<script type="text/javascript" src="${webRoot}/plug-in/jquery-ui/js/ui/jquery.ui.sortable.js"></script>
<link rel="stylesheet" href="${webRoot}/plug-in/jquery-ui/css/ui-lightness/jquery-ui-1.9.2.custom.min.css" type="text/css"></link> --%>

<!-- prohibit -->
<%-- <script type="text/javascript" src="${webRoot}/plug-in/tools/prohibitutil.js"></script> --%>

<!-- tools -->
<%-- <script type="text/javascript" src="${webRoot}/plug-in/lhgDialog/lhgdialog.min.js"></script>
<script type="text/javascript" src="${webRoot}/plug-in/tools/curdtools.js"></script>
<script type="text/javascript" src="${webRoot}/plug-in/tools/easyuiextend.js"></script>
<link rel="stylesheet" href="${webRoot}/plug-in/tools/css/common.css" type="text/css"></link> --%>

<!-- toptip -->
<%-- <script type="text/javascript" src="${webRoot}/plug-in/toptip/manhua_msgTips.js"></script>
<link rel="stylesheet" href="${webRoot}/plug-in/toptip/css/css.css" type="text/css"></link> --%>

<!-- autocomplete -->
<%-- <script type="text/javascript" src="${webRoot}/plug-in/jquery/jquery-autocomplete/jquery.autocomplete.min.js"></script>
<link rel="stylesheet" href="${webRoot}/plug-in/jquery/jquery-autocomplete/jquery.autocomplete.css" type="text/css"></link> --%>

<!-- FN -->
<%-- <link rel="stylesheet" href="${webRoot}/css/base.css" type="text/css"></link>
<link rel="stylesheet" href="${webRoot}/css/content.css" type="text/css"></link>
<link rel="stylesheet" href="${webRoot}/css/header.css" type="text/css"></link>
<link rel="stylesheet" href="${webRoot}/css/left.css" type="text/css"></link>
<link rel="stylesheet" href="${webRoot}/css/login.css" type="text/css"></link>
<link rel="stylesheet" href="${webRoot}/css/scroll.css" type="text/css"></link>
<link rel="stylesheet" href="${webRoot}/css/select.css" type="text/css"></link>
 --%>
<%-- <script type="text/javascript" src="${webRoot}/plug-in/easyui/base.min.js"></script>

<script>
	$.ajaxSetup({
		error: function (XMLHttpRequest, textStatus, errorThrown){
			if(XMLHttpRequest.status==403){
				alert('您没有权限访问此资源或进行此操作');return false;
			}
		},
		complete:function(XMLHttpRequest,textStatus){
			var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
			if(sessionstatus=='timeout'){
				var top = getTopWinow(); 
				alert('登录超时, 请重新登录.');
				top.location.href='http://tms.feiniu.com';
			}
		}
	});
	// 获取顶部窗口
	function getTopWinow(){
		var p = window; 
		while(p != p.parent){
			p = p.parent; 
		}
		return p;
	}
</script> --%>