<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<div class="datagrid-toolbar clearfloat">
  <div class="commSearchFrm" name="searchColums" id="takseSearchFrm">
      	<div class="commSearchCond" name="divInput">
		   	 任务名称：<input type="text" name="taskName" value="">
		</div>
		<div class="commSearchCond" name="divTriggerType">
		    <label style="vertical-align:top;">触发类别：</label>
		    <select name="triggerType" style="width:141px" id="triggerType">
	          <option value="">请选择</option>
	        </select>
		</div>
		<div class="commSearchCond" name="divTaskState">
		            运行状态：
		    <select name="taskState" style="width:141px" id="taskState">
	          <option value="">请选择</option>
	        </select>
		</div>
		<div class="commSearchCond" name="divTaskState">
		            功能类别：
		    <select name="jobType" style="width:141px" id="jobType">
	          <option value="">请选择</option>
	        </select>
		</div>
		<div class="commSearchCond" name="divDate">
	  		开始时间：<input type="text" id="start" class="easyui-datetimebox" name="createDate">&nbsp;&nbsp;&nbsp;—&nbsp;&nbsp;
	  		 结束时间：<input type="text"  id="end" class="easyui-datetimebox" name="createDate">
		</div>
		<div  style="float: right;margin-right: 100px">
  	 	  <a href="javascript:void(0);" class="easyui-linkbutton btn-search" iconCls="icon-search" onclick="getSearchFormAndreloadDataGrid('takseSearchFrm','timeTaskList');">查询</a>
 	      <a href="javascript:void(0);" class="easyui-linkbutton btn-reload" iconCls="icon-reload" onclick="reloadQueryFrm('takseSearchFrm');">重置</a>
 	    </div>
  </div>
</div>

<div region="center" style="padding: 1px;">
  <div id="timeTaskListtb" style="height: auto">
	  <div style="width:100%;overflow:auto;_height:1%;border:0px;" class="datagrid-toolbar tooltip tooltip-bottom">
	       <div class="tooltip-content">
	          <div id="toolbar">
	            <span style="float:left;margin-left:10px;">
	              <a href="#" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="addorupdate('录入','userController.do?addorupdate','userList',null,350)">录入</a>
	              <a href="#" class="easyui-linkbutton" plain="true" icon="icon-edit" onclick="addorupdate('编辑','userController.do?addorupdate','userList',null,250)">编辑</a>
	              <a href="#" class="easyui-linkbutton" plain="true" icon="icon-search" onclick="addorupdate('查看','userController.do?addorupdate','userList',null,250)">查看</a>
	              <a href="#" class="easyui-linkbutton" plain="true" icon="icon-remove" onclick="addorupdate('删除','userController.do?addorupdate','userList',null,250)">删除</a>
	            </span>
	          </div>
	       </div>
	    </div> 
    </div>
  <table width="100%" id="timeTaskList" toolbar="#timeTaskListtb"></table>
</div>

<script type="text/javascript">
$(function() {
	$('#timeTaskList').datagrid({
        idField: 'cfgTaskId',
        title: '定时任务管理',
        url: 'timeTaskController.htm?datagrid&field=cfgTaskId,taskName,triggerType,taskState,createUser,taskGroup,jobType',
        fit: false,
        loadMsg: '数据加载中...',
        pageSize: 10,
        pagination: true,
        pageList: [10, 20, 30],
        sortOrder: 'asc',
        rownumbers: true,
        singleSelect: true,
        fitColumns: true,
        showFooter: true,
        frozenColumns: [[]],
        columns: [[{
            field: 'cfgTaskId',
            title: '任务ID',
            hidden: true,
            sortable: true,
            width: 50
        },{
            field: 'taskName',
            title: '任务名称',
            width: 150
        },{
            field: 'triggerType',
            title: '触发类别',
            width: 100,
            sortable: true,
            formatter: function(value, rec, index) {
           
            }
        },{
            field: 'taskState',
            title: '运行状态',
            width: 100,
            sortable: true
        },{
            field: 'createUser',
            title: '创建人',
            width: 100,
            sortable: true,
            formatter: function(value, rec, index) {
                if (value != null && value != '' && value.length > 0) {
                    return replaceColShow(value, 'commonController.do?listData', '16', '');
                } else {
                    return value;
                }
            }
        },{
            field: 'taskGroup',
            title: '任务组别',
            width: 100,
            sortable: true
        },{
            field: 'jobType',
            title: '功能类别',
            width: 100,
            sortable: true
        },{
            field: 'opt',
            title: '操作',
            width: 100,
            formatter: function(value, rec, index) {
                if (!rec.userId) {
                    return '';
                }
                var href = '';
                if (rec.userId > '0') {
                    href += "<span style='color:#0066cc'>[</span><a href='#' onclick=delObj('userController.do?del&userId=" + rec.userId + "&userName=" + rec.userName + "&stationId=" + rec.stationId + "','userList')>";
                    href += "删除</a><span style='color:#0066cc'>]</span>";
                }
                href += "<span style='color:#0066cc'>[</span><a href='#' onclick=resetPassword('" + rec.userId + "','" + index + "')>";
                href += "重置密码</a><span style='color:#0066cc'>]</span>";
                href += "<span style='color:#0066cc'>[</span><a href='#' onclick=dataAuthority('" + rec.userId + "','" + index + "')>";
                href += "数据权限</a><span style='color:#0066cc'>]</span>";
                return href;
            }
        }]],
        onLoadSuccess: function(data) {
            $("#userList").datagrid("clearSelections");
            showDatagridParams(data);
            $(this).datagrid('fixRownumber');
        },
        onDblClickRow: function(rowIndex, rowData) {
            rowid = rowData.id;
            gridname = 'userList';
        }
    });
});
</script>