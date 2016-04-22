<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<style type="text/css">
	div.formDetails label textarea, select {
	  width: 149px !important;
	}
	.typeRadio input{
		margin-top: 15px;
	}
	.align-center{
		text-align: center;
		width: 30px
	}
	.ordertrackingList table{
		margin: 50px,0,0,100px !important;
		color: #000000;
		border-color: #95B8E7;
		border-collapse:collapse;
		
	}
	.ordertrackingList tr td,table tr th{
		height:35px;
	}
	.ordertrackingList .table-header{
		background-color:#E5EFFF;	
	}
	.ordertrackingList .blank-line{
		margin-top: 30px;
		margin-bottom:5px;
		padding-left: 10px;
	}
</style>

<div class="datagrid-toolbar clearfloat">
  <div class="commSearchFrm" name="searchColums" id="orderTrackingFrm">
	<div class="commSearchCond" name="divInput">
	   	 <div style="float: left;margin-top: 15px;">查询方式：</div>
	   	 <div class="typeRadio" style="float: left;">
	   	 	<input type="radio" id="subOrderType" name="searchType" value="2"  checked="checked"/>&nbsp;子单号<br/>
	   	 	<input type="radio" id="cusOrderType" name="searchType" value="3"  />&nbsp;客户订单号<br/>
	   	  	<input type="radio" id="parOrderType" name="searchType" value="1"/>&nbsp;母单号<br/>
	   	  	<input type="hidden" id="selSearchType" name="selSearchType" value="${searchType}" />
	   	 </div>
	   	 <div style="float: left;margin:15px 0 0 50px">
	   		<input class="easyui-textbox" id="orderCodeSearch" name="orderCodeSearch" missingMessage="请填写登录名" data-options="multiline:true" style="width:500px;height:80px;"></input>
	   		<input type="hidden" id="searchCode" name="searchCode" value="${searchCode}" />
	   	</div>
	  	<div style="float: left;margin:65px 0 0 50px">
	  	  <a href="javascript:void(0);" class="easyui-linkbutton btn-search" iconCls="icon-search" onclick="orderTrackingSearch();">查询</a>
	  	  <a href="javascript:void(0);" class="easyui-linkbutton btn-reload" iconCls="icon-reload" onclick="resetFrm('orderTrackingFrm');">重置</a>
		</div>
	</div>
  </div>
</div>
<div class="ordertrackingList panel datagrid easyui-fluid" style="margin-left: 20px">
</div>

<script type="text/javascript">
$(function(){
	var searchType = $("#selSearchType").val();
	//alert("searchType:" + searchType);
	if(searchType == "1"){
		$("#parOrderType").attr("checked","checked");
	}else if(searchType == "3"){
		$("#cusOrderType").attr("checked","checked");
	}else{
		$("#subOrderType").attr("checked","checked");
	}
	
	var searchCode = $("#searchCode").val();
	//alert("searchCode:" + searchCode);
	if(searchCode == null || searchCode == ""){
		$("#orderCodeSearch").textbox({prompt:'最多可同时查询数量为30，每行一个，重复或超过30行，系统将自动忽略'});
	}else{
		$("#orderCodeSearch").val('aaaa' + '     \r\n' + 'bbbb');
	}
});
// 查询按钮
function orderTrackingSearch() {
	var searchCode = $("input[name='orderCodeSearch']").val();
	var searchType = $("input[name='searchType']:checked ").val();
	
	if (null != searchCode && "" != searchCode && searchCode.length > 0 && searchType != null) {
		if (searchCode.split("\n").length > 30)
			tip("您的查询条数已超过30条，系统将自动忽略第31条以后的记录");
		
		closeTab("订单追踪");
		addTab('订单追踪','takeOrderController.htm?orderTrackingList&searchType=' + searchType + '&searchCode=' + searchCode);
	} else {
		console.log("请输入单号");
	}
}

function resetFrm(id){
	$("#subOrderType").attr("checked","checked");
	$("#orderCodeSearch").textbox('clear');
}
</script>