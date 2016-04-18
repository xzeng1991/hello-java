<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<link rel="stylesheet" href="${webRoot}/plug-in/accordion/css/icons.css" type="text/css"></link>
<link rel="stylesheet" href="${webRoot}/plug-in/accordion/css/accordion.css" type="text/css"></link>

<script type="text/javascript" src="${webRoot}/plug-in/accordion/js/leftmenu.js"></script>

<%-- <t:menu menuFun="${menuMap}"></t:menu> --%>
<%-- <div id="sidebar" class="fnj-scrollbar-warp">
  <div class="fnj-scrollbar-viewport">
    <div class="fnj-scrollbar-overview" style="position: relative;">
      <ul class="left_menu_1">
        <div id="menuDiv" class="left_warp nav">
          <c:forEach var="item" items="${menuMap}" step="1" varStatus="status">
          <li>
            <a class="hide_1t" title="funNameTitle" href="javascript:void(0);" onclick="slideMenu(this,'1t');"><span class="hide_open_1t"></span>${item.value.parent.functionName}</a>
            <c:forEach var="itemChild" items="${item.value.childs}" step="1" varStatus="statusChild">
            <ul class="left_menu_2 child" style="display: none;">
              <li>
                <a class="hide_2" title="subFunNameTitle" style="color:#3c3c3c;" href="javascript:void(0);" onclick="slideMenu(this,'2');">&nbsp;<span class="hide_close_2"></span>${itemChild.functionName}</a>
              </li>
              <li iconCls="folder"> <a onclick="addTab('${itemChild.functionName}','${webRoot}${itemChild.functionUrl}')" title="funNameTitle" class="hide_2" url="${webRoot}${itemChild.functionUrl}" href="#" style="color:#3c3c3c;">&nbsp;${itemChild.functionName}</a></li>
            </ul> 
            </c:forEach>
          </li>
          </c:forEach>
        </div>
      </ul>
    </div>
  </div>
</div> --%>