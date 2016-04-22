<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<div class="datagrid-toolbar">
  <div style="margin:10px;" name="searchColums" id="secondLevelStationListSearchFrm" style="min-width:950px;">
	<div class="commSearchCond" name="divInput">
	   分拨中心：
	   <select id="deliveryCenter" name="deliveryCenterId" class="easyui-combobox" data-options="multiple:true"></select> 
	</div>
	<div class="commSearchCond" name="divInput">
	   配送商：
	   <select id="carorrier" name="carrierId" class="easyui-combobox" data-options="multiple:true"></select> 
	</div>
	<div class="commSearchCond" name="divDate">
  	     创建时间段：
  		<input type="text" id="createdTime_S" class='easyui-datetimebox' data-options='showSeconds:true' name="createdTime_S" defaultSpinner="00:00"/>&nbsp;&nbsp;—&nbsp;&nbsp;
  		<input type="text"  id="createdTime_N" class='easyui-datetimebox' data-options='showSeconds:true' name="createdTime_N" defaultSpinner="23:59:59"/>
	</div>
	
	<!-- button -->
	<div class="" align="right" name="divInput">
  	  	<a href="javascript:void(0);" class="easyui-linkbutton btn-search" iconCls="icon-search" onclick="getSearchFormAndreloadDataGrid('secondLevelStationListSearchFrm','secondLevelStationDataGrid');">查询</a>
  	  	<a href="javascript:void(0);" class="easyui-linkbutton btn-reload" iconCls="icon-reload" onclick="reloadsecondLevelStationListSearchFrm('secondLevelStationListSearchFrm');">重置</a>
  	  </div> 
  </div>
</div>

<div id="secondLevelStationProgressbar" class="easyui-progressbar" style="width:100%;display:none;"></div>

<input type="hidden" id="secondLevelStationKeyNum" value="0">

<div class="fieldpanel" id="p_3PLDelivery">
	<div class="fields" id="f_3PLDelivery">
	</div>
</div>

<div region="center" style="padding: 1px;">
</div>

<form action="reportController.do?orderList2LSExcel" id="secondLevelStationReportFrm" method="post">
	<input type="hidden" id="conditions" name="conditions"/>
	<input type="hidden" id="type" name="type" value="1"/>
</form>

<script type="text/javascript">
$(function() {
	//loadSelectCombobox('deliveryCenter','commonController.do?listData','31','1');
	//loadSelectCombobox('carorrier','commonController.do?listData','6','2');
	
	var curr_time = new Date();
	
	var strDateS = curr_time.getFullYear()+"-";
	strDateS += curr_time.getMonth()+1+"-";
	strDateS += curr_time.getDate()+" ";
	strDateS += "00:";
	strDateS += "00:";
	strDateS += "00";
	
	var strDateE = curr_time.getFullYear()+"-";
	strDateE += curr_time.getMonth()+1+"-";
	strDateE += curr_time.getDate()+" ";
	strDateE += "23:";
	strDateE += "59:";
	strDateE += "59";
	$("#secondLevelStationListSearchFrm").find("#createdTime_S").val(strDateS);
	$("#secondLevelStationListSearchFrm").find("#createdTime_N").val(strDateE);
});
</script>