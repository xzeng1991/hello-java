<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<style>
	.home_message_num{
		font-size:30px;
		color: red;
		cursor:pointer;
	}
	.home_message{
		position: fixed; 
		_position:absolute; 
		left:80%;
		top: 80%;	
		font-size:16px;
	}
</style>

<div style="margin-top: 14px;">
	<h3>简介</h3>
	Will be back soon.
	<div class="home_message">您有<font class="home_message_num">0</font>条未读消息</div>
</div>

<script type="text/javascript">
$(function(){
   /* $(".home_message_num").click(function(){
		addTab('系统消息管理','messageController.do?listPage');
    });  */
	//查询后台当前作业点的数据
	/* $.ax("messageController.do?get", null, false, 'post', 'json', 
	    function(data){
			if(data!='0'){
			     $(".home_message_num").html(data);
			}else{
				$(".home_message_num").html(0);
			}
	    }, 
	    function(){
	    	$(".home_message_num").html(0);
	    }
	); */
});
/* function ss(){
	parent.closeTab("系统消息管理");
	$('#maintabs').tabs('add',{
		title : '系统消息管理',
		content : createFrame('messageController.do?listPage'),
		closable : true,
		icon : null
	}); 
} */
</script>