<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
  	<!-- tool button area -->   
    <div id="userListtb" style="height: auto">
    	<!-- search condition area -->
      <div name="searchColums">
        <div style="margin:0px 0px 0px 20px;border-color: #dddddd;border-width: 0 0 1px 0;border-style: solid;">
          <span style="display:-moz-inline-box;display:inline-block;margin:10px 50px 10px 0px;">
            <span style="display:-moz-inline-box;display:inline-block;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis;white-space:nowrap;" title="用户名">
       	                  用户名：
            </span>
            <input type="text" name="userName" style="width: 100px" />
          </span>
          <span style="display:-moz-inline-box;display:inline-block;margin:10px 50px 10px 0px;">
            <span style="display:-moz-inline-box;display:inline-block;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis;white-space:nowrap;" title="部门">
                                         部门：
            </span>
            <select name="departId" width="100" style="width: 104px"> 
               <option value="0">未选择</option>
            </select>
         </span>
         <span style="display:-moz-inline-box;display:inline-block;margin:10px 50px 10px 0px;">
            <span style="display:-moz-inline-box;display:inline-block;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis;white-space:nowrap;" title="真实姓名">
       	                  真实姓名：
            </span>
            <input type="text" name="realName" style="width: 100px" />
        </span>
        <!-- a tag area -->
        <span>
          <a href="#" class="easyui-linkbutton btn-search" style="margin:0px 10px 5px 0px;" iconcls="icon-search" onclick="userListsearch()">查询</a>
          <a href="#" class="easyui-linkbutton btn-reload" style="margin:0px 0px 5px 0px;" iconcls="icon-reload" onclick="searchReset('userList')">重置</a>
        </span>
      </div>
     </div>
     <!-- DataGrid tool button -->
     <div style="width:100%;overflow:auto;_height:1%;border:0px;" class="datagrid-toolbar tooltip tooltip-bottom">
       <div class="tooltip-content">
          <div id="toolbar">
            <span style="float:left;margin-left:10px;">
              <a href="#" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="add('用户录入','userController.do?addorupdate','userList',null,350)">用户录入</a>
              <a href="#" class="easyui-linkbutton" plain="true" icon="icon-edit" onclick="update('用户编辑','userController.do?addorupdate','userList',null,250)">用户编辑</a>
            </span>
          </div>
       </div>
    </div> 
  </div>
  
  <table width="100%" id="userList" toolbar="#userListtb"></table>
  </div>
</div>

<script type = "text/javascript" > 
   $(function() {
    $('#userList').datagrid({
        idField: 'userId',
        title: '用户管理',
        url: 'user.htm?datagrid&field=userId,userName,departId,realName,stationId,',
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
            field: 'userId',
            title: '编号',
            hidden: true,
            sortable: true
        },{
            field: 'userName',
            title: '用户名',
            width: 100
        },{
            field: 'departId',
            title: '部门',
            width: 100,
            sortable: true,
            formatter: function(value, rec, index) {
           
            }
        },{
            field: 'realName',
            title: '真实姓名',
            width: 100,
            sortable: true
        },{
            field: 'stationId',
            title: '岗位',
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
    
    $('#userList').datagrid('getPager').pagination({
        beforePageText: '',
        afterPageText: '/{pages}',
        displayMsg: '{from}-{to}共{total}条',
        showPageList: true,
        showRefresh: true
    });
    
    $('#userList').datagrid('getPager').pagination({
        onBeforeRefresh: function(pageNumber, pageSize) {
            $(this).pagination('loading');
            $(this).pagination('loaded');
        }
    });
});
   
function reloadTable() {
    try {
        $('#' + gridname).datagrid('reload');
        $('#' + gridname).treegrid('reload');
    } catch(ex) {}
}

function reloaduserList() {
    $('#userList').datagrid('reload');
}

function setuserListURL() {
    $("#userList").datagrid({
        url: 'userController.do?datagrid&field=userId,userName,departId,realName,stationId,'
    });
}

function getuserListSelected(field) {
    return getSelected(field);
}

function getSelected(field) {
    var row = $('#' + gridname).datagrid('getSelected');
    if (row != null) {
        value = row[field];
    } else {
        value = '';
    }
    return value;
}

function getuserListSelections(field) {
    var ids = [];
    var rows = $('#userList').datagrid('getSelections');
    for (var i = 0; i < rows.length; i++) {
        ids.push(rows[i][field]);
    }
    ids.join(',');
    return ids;
}

function getSelectRows() {
    return $('#userList').datagrid('getChecked');
}

function userListsearch() {
    var queryParams = $('#userList').datagrid('options').queryParams;
    $('#userListtb').find('*').each(function() {
        queryParams[$(this).attr('name')] = $(this).val();
    });
    $('#userList').datagrid({
        url: 'userController.do?datagrid&field=userId,userName,departId,realName,stationId,',
        pageNumber: 1
    });
}

function dosearch(params) {
    var jsonparams = $.parseJSON(params);
    $('#userList').datagrid({
        url: 'userController.do?datagrid&field=userId,userName,departId,realName,stationId,',
        queryParams: jsonparams
    });
}

function userListsearchbox(value, name) {
    var queryParams = $('#userList').datagrid('options').queryParams;
    queryParams[name] = value;
    queryParams.searchfield = name;
    $('#userList').datagrid('reload');
}

$('#userListsearchbox').searchbox({
    searcher: function(value, name) {
        userListsearchbox(value, name);
    },
    menu: '#userListmm',
    prompt: '请输入查询关键字'
});

function searchReset(name) {
    $("#" + name + "tb").find(":input").val("");
    userListsearch();
} 
</script>
 