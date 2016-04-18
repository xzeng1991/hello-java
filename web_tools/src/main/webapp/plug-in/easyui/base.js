/**
 * author:zhao.yang
 * email:kefu@feiniu.com
 */

/*****************************************************************
jQuery Ajax封装通用类
*****************************************************************/
$(function() {
	/**
	 * ajax封装 
	 * url 发送请求的地址 
	 * data 发送到服务器的数据，数组存储，如：{"date": new Date().getTime(), "state": 1} 
	 * async 默认值: true。默认设置下，所有请求均为异步请求。如果需要发送同步请求，请将此选项设置为 false。注意，同步请求将锁住浏览器，用户其它操作必须等待请求完成才可以执行。 
	 * type 请求方式("POST" 或 "GET")， 默认为 "GET"
	 * dataType 预期服务器返回的数据类型，常用的如：xml、html、json、text 
	 * successfn 成功回调函数 
	 * errorfn 失败回调函数
	 */
	jQuery.ax = function(url, data, async, type, dataType, successfn, errorfn) {
		async = (async == null || async.length == 0 || typeof (async) == "undefined") ? "true": async;
		type = (type == null || type == "" || typeof (type) == "undefined") ? "post"
				: type;
		dataType = (dataType == null || dataType == "" || typeof (dataType) == "undefined") ? "json"
				: dataType;
		data = (data == null || data == "" || typeof (data) == "undefined") ? {
			"date" : new Date().getTime()
		} : data;
		$.ajax({
			type : type,
			async : async,
			data : data,
			url : url,
			dataType : dataType,
			success : function(d) {
				successfn(d);
			},
			error : function(e) {
				errorfn(e);
			}
		});
	};

	/**
	 * ajax封装 
	 * url 发送请求的地址 
	 * data 发送到服务器的数据，数组存储，如：{"date": new Date().getTime(),"state": 1} 
	 * successfn 成功回调函数
	 */
	jQuery.axs = function(url, data, successfn) {
		data = (data == null || data == "" || typeof (data) == "undefined") ? {
			"date" : new Date().getTime()
		} : data;
		$.ajax({
			type : "post",
			data : data,
			url : url,
			dataType : "json",
			success : function(d) {
				successfn(d);
			}
		});
	};

	/**
	 * ajax封装 
	 * url 发送请求的地址 
	 * data 发送到服务器的数据，数组存储，如：{"date": new Date().getTime(),"state": 1} 
	 * dataType 预期服务器返回的数据类型，常用的如：xml、html、json、text 
	 * successfn 成功回调函数 
	 * errorfn 失败回调函数
	 */
	jQuery.axse = function(url, data, successfn, errorfn) {
		data = (data == null || data == "" || typeof (data) == "undefined") ? {
			"date" : new Date().getTime()
		} : data;
		$.ajax({
			type : "post",
			data : data,
			url : url,
			dataType : "json",
			success : function(d) {
				successfn(d);
			},
			error : function(e) {
				errorfn(e);
			}
		});
	};
	
	
	/*****************************************************************
	 * 通用日期类型扩展格式化方法
	 *****************************************************************/
	/* 默认语言 */
	var defaults = {
		lang: 'zh-cn'
	};
	/* 默认语言值 */
	var lang = {
		'zh-cn': {
			aMonths: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
			aLongMonths: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
			aWeeks: ['日','一','二','三','四','五','六'],
			aLongWeeks: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],
			a: ['上午','下午']
		},
		'en': {
			aMonths: ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'],
			aLongMonths: ['January','February','March','April','May','June','July','August','September','October','November','December'],
			aWeeks:['Sun','Mon','Tue','Wed','Thu','Fri','Sat'],
			aLongWeeks:['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'],
			a: ['AM','PM']
		}
	};
	/* 匹配正则 */
	var RegExpObject = /^(y+|M+|d+|H+|h+|m+|s+|E+|S|a)/;
	/* 参数 */
	var _options;
	jQuery.formatDate = function(pattern, date, options) {
		_options = $.extend({}, defaults, lang[$.extend({}, defaults, options).lang], options);
		/* 未传入时间,设置为当前时间 */
		if(date == undefined)
			date = new Date();
		/* 传入时间为字符串 */
		if($.type(date) === "string"){
			if(date == ""){
				return "";
				/*date = new Date();*/
			}else{
				//if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){  
					var daStr = date.replace(/-/g, "/").split(/\/|\:|\ /);
					var year = null;
					var month = null;
					var day = null;
					var hour = null;
					var minutes = null;
					var seconds = null;
					if(daStr.length>0){
						year=daStr[0];				
					}
					if(daStr.length>1){
						month = daStr[1]-1;
					}
					if(daStr.length>2){
						day = daStr[2];
					}
					if(daStr.length>3){
						hour = daStr[3];
					}
					if(daStr.length>4){
						minutes = daStr[4];
					}
					if(daStr.length>5){
						seconds = daStr[5];
					}
					date = new Date(year,month,day,hour,minutes,seconds);
//				}else{
//					date = new Date(date.replace(/-/g, "/"));
//				}
			} 
		}
		var result = [];
		while (pattern.length > 0) {
			RegExpObject.lastIndex = 0;
			var matched = RegExpObject.exec(pattern);
			if (matched) {
				result.push(patternValue[matched[0]](date));
				pattern = pattern.slice(matched[0].length);
			}else {
				result.push(pattern.charAt(0));
				pattern = pattern.slice(1);
			}
		}
		return result.join('');
	};
	/* 补全 */
	function toFixedWidth(value, length) {
		var result = "00" + value.toString();
		return result.substr(result.length - length);
	};
	/* 匹配值处理 */
	var patternValue = {
		y: function(date) {
			return date.getFullYear().toString().length > 1 ? toFixedWidth(date.getFullYear(), 2) : toFixedWidth(date.getFullYear(), 1);
		},
		yy: function(date) {
			return toFixedWidth(date.getFullYear(), 2);
		},
		yyy: function(date) {
			return toFixedWidth(date.getFullYear(), 3);
		},
		yyyy: function(date) {
			return date.getFullYear().toString();
		},
		M: function(date) {
			return date.getMonth()+1;
		},
		MM: function(date) {
			return toFixedWidth(date.getMonth()+1, 2);
		},
		MMM: function(date) {
			return _options.aMonths[date.getMonth()];
		},
		MMMM: function(date) {
			return _options.aLongMonths[date.getMonth()];
		},
		d: function(date) {
			return date.getDate();
		},
		dd: function(date) {
			return toFixedWidth(date.getDate(), 2);
		},
		E: function(date) {
			return _options.aWeeks[date.getDay()];
		},
		EE: function(date) {
			return _options.aLongWeeks[date.getDay()];
		},
		H: function(date) {
			return date.getHours();
		},
		HH: function(date) {
			return toFixedWidth(date.getHours(),2);
		},
		h: function(date) {
			return date.getHours()%12;
		},
		hh: function(date) {
			return toFixedWidth(date.getHours() > 12 ? date.getHours() - 12 : date.getHours(), 2);
		},
		m: function(date) {
			return date.getMinutes();
		},
		mm: function(date) {
			return toFixedWidth(date.getMinutes(), 2);
		},
		s: function(date) {
			return date.getSeconds();
		},
		ss: function(date) {
			return toFixedWidth(date.getSeconds(), 2);
		},
		S: function(date) {
			return toFixedWidth(date.getMilliseconds(), 3);
		},
		a: function(date) {
			return _options.a[date.getHours() < 12 ? 0:1];
		}
	};
	
	/*****************************************************************
	 * 表单元素转化成json对象
	 *****************************************************************/
	$.fn.serializeObject = function(){
        var o = {};
        var a = this.serializeArray();
        $.each(a, function() {
            if (o[this.name] !== undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };
    
});



/*****************************************************************
* 日期类型扩展格式化方法
*****************************************************************/
Date.prototype.format = function(format)
{
	/*var o = {
		"M+" : this.getMonth() + 1,
		"d+" : this.getDate(),
		"h+" : this.getHours(),
		"m+" : this.getMinutes(),
		"s+" : this.getSeconds(),
		"q+" : Math.floor((this.getMonth() + 3) / 3),
		"S" : this.getMilliseconds()
	};
	if (/(y+)/.test(format))
	{
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4- RegExp.$1.length));
	}
	for (var k in o)
	{
		if (new RegExp("(" + k + ")").test(format))
		{
			format = format.replace(RegExp.$1, RegExp.$1.length == 1? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;*/
	var d = this;
    var zeroize = function (value, length)
    {
        if (!length) length = 2;
        value = String(value);
        for (var i = 0, zeros = ''; i < (length - value.length); i++)
        {
            zeros += '0';
        }
        return zeros + value;
    };
 
    return format.replace(/"[^"]*"|'[^']*'|\b(?:d{1,4}|m{1,4}|yy(?:yy)?|([hHMstT])\1?|[lLZ])\b/g, function ($0)
    {
        switch ($0)
        {
            case 'd': return d.getDate();
            case 'dd': return zeroize(d.getDate());
            case 'ddd': return ['Sun', 'Mon', 'Tue', 'Wed', 'Thr', 'Fri', 'Sat'][d.getDay()];
            case 'dddd': return ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'][d.getDay()];
            case 'M': return d.getMonth() + 1;
            case 'MM': return zeroize(d.getMonth() + 1);
            case 'MMM': return ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][d.getMonth()];
            case 'MMMM': return ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'][d.getMonth()];
            case 'yy': return String(d.getFullYear()).substr(2);
            case 'yyyy': return d.getFullYear();
            case 'h': return d.getHours() % 12 || 12;
            case 'hh': return zeroize(d.getHours() % 12 || 12);
            case 'H': return d.getHours();
            case 'HH': return zeroize(d.getHours());
            case 'm': return d.getMinutes();
            case 'mm': return zeroize(d.getMinutes());
            case 's': return d.getSeconds();
            case 'ss': return zeroize(d.getSeconds());
            case 'l': return zeroize(d.getMilliseconds(), 3);
            case 'L': var m = d.getMilliseconds();
                if (m > 99) m = Math.round(m / 10);
                return zeroize(m);
            case 'tt': return d.getHours() < 12 ? 'am' : 'pm';
            case 'TT': return d.getHours() < 12 ? 'AM' : 'PM';
            case 'Z': return d.toUTCString().match(/[A-Z]+$/);
            default: return $0.substr(1, $0.length - 2);
        }
    });
};


/*****************************************************************
* 展开收起菜单
* @param el
* @param level
*****************************************************************/
function slideMenu(el,code){
	//收起其它
	$(el).parent().parent().find("ul").slideUp();
	$(el).parent().parent().find("span").removeClass("hide_close_"+code).addClass("hide_open_"+code);
	//展开当前
	if($(el).next("ul").is(":hidden")){
		$(el).children("span").removeClass("hide_open_"+code).addClass("hide_close_"+code);
		$(el).next("ul").slideDown();
	}else{
		$(el).children("span").removeClass("hide_close_"+code).addClass("hide_open_"+code);
		$(el).next("ul").slideUp();
	}
}

/*****************************************************************
* 提交查询表单，更新DatdGrid
* @param formObj 表单ID
* @param datagridObj 更新DataGrid的name属性
*****************************************************************/
function getSearchFormAndreloadDataGrid(formObj,datagridObj){
	var queryParamObjs = $("#"+formObj).children(".commSearchCond");
	var conditions = [];
	$(queryParamObjs).each(function(){
		var curDiv = $(this);
		if(curDiv.attr("hideEffect")!=null && curDiv.attr("hideEffect")!="undefined" && curDiv.attr("hideEffect")=='false'){
			if(!curDiv.is(":visible"))return;
		}
		var queryParamIpts = curDiv.find(":input[name]");
		var condition = {};
		condition["name"]=curDiv.attr("name");
		condition["params"]=[];
		$(queryParamIpts).each(function(){
			if(typeof($(this).attr("name"))!="undefined"){
				var name = $(this).attr("name");
				if($(this)[0].tagName.toUpperCase()=="SELECT"){
					var selOpts = $(this).find("option:selected");
					$(selOpts).each(function(){
						var value = $(this).attr("value");
						if(null!=value&&value.length>0){
							var optPair = {};
							optPair["key"]=name;
							optPair["value"]=value;
							optPair["type"]=$(this).attr("type");
							if(typeof($(this).attr("attrId"))!="undefined"){
								var attrId = $(this).attr("attrId");
								if($("#oper_"+attrId).length>0){
									var attrOper = $("#oper_"+attrId).val();
									if(null!=attrOper&&attrOper.length>0&&attrOper!=""){
										optPair["operator"]=attrOper;
									}else{
										if(typeof($(this).attr("opers"))!="undefined"){
											optPair["operator"]=$(this).attr("opers");
										}
									}
								}
							}
							condition["params"].push(optPair);
						}
					});
				}else if($(this).attr("type").toUpperCase()=="CHECKBOX"||$(this).attr("type").toUpperCase()=="RADIO"){
					if($(this).prop("checked")){
						var value = $(this).attr("value");
						if(null!=value&&value.length>0){
							var optPair = {};
							optPair["key"]=name;
							optPair["value"]=value;
							optPair["type"]=$(this).attr("type");
							if(typeof($(this).attr("attrId"))!="undefined"){
								var attrId = $(this).attr("attrId");
								if($("#oper_"+attrId).length>0){
									var attrOper = $("#oper_"+attrId).val();
									if(null!=attrOper&&attrOper.length>0&&attrOper!=""){
										optPair["operator"]=attrOper;
									}else{
										if(typeof($(this).attr("opers"))!="undefined"){
											optPair["operator"]=$(this).attr("opers");
										}
									}
								}
							}
							condition["params"].push(optPair);
						}
					}
				}else{
					var value = $(this).attr("value");
					if(null!=value&&value.length>0){
						var optPair = {};
						optPair["key"]=name;
						optPair["value"]=value;
						optPair["type"]=$(this).attr("type");
						/*获取运算符*/
						if($(this).attr("type").toUpperCase()=="HIDDEN"){
							var dateopr = $(this).parents("span[name='sp_"+name+"']").find("*[dateoper='true']");
							if(typeof(dateopr)!="undefined"){
								optPair["operator"]=$(dateopr).val();
								var operId = $(dateopr).attr("id");
								if(typeof(operId)!="undefined"){
									var attrId = operId.replace("oper_","");
									if(typeof($("#"+attrId))!="undefined"&&typeof($("#"+attrId).attr("edittype"))!="undefined"){
										optPair["attrType"]=$("#"+attrId).attr("edittype");
									}
								}
							}
						}else{
							if(typeof($(this).attr("attrId"))!="undefined"){
								var attrId = $(this).attr("attrId");
								if($("#oper_"+attrId).length>0){
									var attrOper = $("#oper_"+attrId).val();
									if(null!=attrOper&&attrOper.length>0&&attrOper!=""){
										optPair["operator"]=attrOper;
									}else{
										if(typeof($(this).attr("opers"))!="undefined"){
											optPair["operator"]=$(this).attr("opers");
										}
									}
								}
							}
						}
						condition["params"].push(optPair);
					}
				}
			}
		}); 
		conditions.push(condition);
	}); 
	var dgQueryParams = $('#'+datagridObj).datagrid('options').queryParams;  
	if(!dgQueryParams){
		dgQueryParams = {};
		$('#'+datagridObj).datagrid('options').queryParams=dgQueryParams; 
	}
	dgQueryParams["conditions"]=JSON.stringify(conditions);
	/*alert($('#'+datagridObj).datagrid('options').queryParams);*/
//    $('#'+datagridObj).datagrid('reload');
	eval("set"+datagridObj+"URL()");
}

/**
 * 重置查询条件
 * @param id
 */
function reloadQueryFrm(id){
	$("#"+id+" :input").not(":button, :submit, :reset, :radio, :checkbox").not("*[dateoper='true']").not("*[isMultiSelect='true']").val("").removeAttr("checked").remove("selected");
	$("#"+id+" :input").filter(":radio, :checkbox").removeAttr("checked");
	$("#"+id).find(".multiSelect").find("span").text("请选择");
	$("#"+id).find(".multiSelectOptions").find("label").removeClass();
	$("#"+id).find(".multiSelectOptions").find("input[type='checkbox']").removeAttr("checked");
	$selts = $("#"+id).find("select");
	$selts.each(function(){
		var selId = $(this).attr("id");
		var selClass = $(this).attr("class");
		if(selClass&&selClass.indexOf('easyui-combobox')!=(-1)){
			$('#'+selId).combobox('clear');  
		}
	});
}

/**
 * 缓存静态数据
 */
var cache = {};
var staticDataUrl = "commonController.do?listData";
/*****************************************************************
 * 动态加载选项(select、checkbox、radio)
 * @param element
 * @param listId
 * @param params
*****************************************************************/
function reLoadOptionsData(url ,type, id, name, listId, param){
	var staticSourceList = cache["listdata_"+listId+"_"+param]; 
	if(null==staticSourceList){
		params = {"datalistId":listId,"params":param};
		$.ax(url, params, false, 'post', 'json', 
	         function(data){
				 var result = $.parseJSON(data.result);
				 staticSourceList = result;
				 cache[("listdata_"+listId+"_"+param)]=result;
	         }, 
	         function(){
	         }
	    );
	}
	$("#"+id).html("");
	if("checkbox"==type||"radio"==type){
		$("#"+id).append("<div style='margin-top:10px;'>");
	}
	
	if("select"==type){
		$("#"+id).append("<option attrId='"+id+"' value=''>请选择</option>");
	}
	
	for (var rs in staticSourceList) {
		 var key = staticSourceList[rs]["key"];
		 var value = staticSourceList[rs]["value"];
		 var showValue = value;
		 if(null!=value&&$.trim(value)!=""&&value.length>6){
			 showValue = value.substring(0,6)+"...";
		 }
	   	 switch (type)
	   	 {
	    	 case "select":
	    	   $("#"+id).append("<option attrId='"+id+"' value='"+key+"'>"+staticSourceList[rs]["value"]+"</option>");
	    	   break;
	    	 case "checkbox":
	    	     $("#"+id).append("<li style='width:150px !important;height:22px !important;list-style:none;float:left;'><input type='checkbox' id='"+name+"_"+key+"' attrId='"+id+"' name='"+name+"' loadId='"+name+"' value='"+key+"'><label for='"+name+"_"+key+"' title='"+value+"' style='cursor:pointer;'>&nbsp;"+showValue+"</label></li>");
	    	     break;
	    	 case "radio":
	    		 $("#"+id).append("<li style='width:150px !important;height:22px !important;list-style:none;float:left;'><input type='radio' id='"+name+"_"+key+"' attrId='"+id+"' name='"+name+"' loadId='"+name+"' value='"+key+"'><label for='"+name+"_"+key+"' title='"+value+"' style='cursor:pointer;'>&nbsp;"+showValue+"</label></li>");
	    	     break;
	   	 }
    }
	if("checkbox"==type||"radio"==type){
		$("#"+id).append("<div>");
	}
}

function loadSelectIsChoose(url ,type, id, name, listId, param, needChooseArray){
	var staticSourceList = cache["listdata_"+listId+"_"+param]; 
	if(null==staticSourceList){
		params = {"datalistId":listId,"params":param};
		$.ax(url, params, false, 'post', 'json', 
	         function(data){
				 var result = $.parseJSON(data.result);
				 staticSourceList = result;
				 cache[("listdata_"+listId+"_"+param)]=result;
	         }, 
	         function(){
	         }
	    );
	}
	$("#"+id).html("");
	if("checkbox"==type||"radio"==type){
		$("#"+id).append("<div style='margin-top:10px;'>");
	}
	
	if("select"==type){
		$("#"+id).append("<option attrId='"+id+"' value=''>请选择</option>");
	}
	
	for (var rs in staticSourceList) {
		 var key = staticSourceList[rs]["key"];
		 var value = staticSourceList[rs]["value"];
		 var showValue = value;
		 if(null!=value&&$.trim(value)!=""&&value.length>6){
			 showValue = value.substring(0,6)+"...";
		 }
	   	 switch (type)
	   	 {
	    	 case "select":
	    		 if($.inArray(key,needChooseArray)>=0){
	    			 $("#"+id).append("<option attrId='"+id+"' value='"+key+"' selected='selected'>"+staticSourceList[rs]["value"]+"</option>");
				 }else{
					 $("#"+id).append("<option attrId='"+id+"' value='"+key+"'>"+staticSourceList[rs]["value"]+"</option>");
				 }
	    	   break;
	    	 case "checkbox":
	    		 if($.inArray(key,needChooseArray)>=0){
	    			 $("#"+id).append("<li style='width:150px !important;height:22px !important;list-style:none;float:left;'><input type='checkbox' id='"+name+"_"+key+"' checked='checked' attrId='"+id+"' name='"+name+"' loadId='"+name+"' value='"+key+"' parentid='"+param+"'><label title='"+value+"' ckid='"+key+"' style='cursor:pointer;'>&nbsp;"+showValue+"</label></li>");
				 }else{
					 $("#"+id).append("<li style='width:150px !important;height:22px !important;list-style:none;float:left;'><input type='checkbox' id='"+name+"_"+key+"' attrId='"+id+"' name='"+name+"' loadId='"+name+"' value='"+key+"' parentid='"+param+"'><label title='"+value+"' ckid='"+key+"' style='cursor:pointer;'>&nbsp;"+showValue+"</label></li>");
				 }
	    	     break;
	    	 case "radio":
	    		 if($.inArray(key,needChooseArray)>=0){
	    			 $("#"+id).append("<li style='width:150px !important;height:22px !important;list-style:none;float:left;'><input type='radio' id='"+name+"_"+key+"' checked='checked' attrId='"+id+"' name='"+name+"' loadId='"+name+"' value='"+key+"' parentid='"+param+"'><label title='"+value+"' ckid='"+key+"' style='cursor:pointer;'>&nbsp;"+showValue+"</label></li>");
				 }else{
					 $("#"+id).append("<li style='width:150px !important;height:22px !important;list-style:none;float:left;'><input type='radio' id='"+name+"_"+key+"' attrId='"+id+"' name='"+name+"' loadId='"+name+"' value='"+key+"' parentid='"+param+"'><label title='"+value+"' ckid='"+key+"' style='cursor:pointer;'>&nbsp;"+showValue+"</label></li>");
				 }
	    	     break;
	   	 }
    }
	if("checkbox"==type||"radio"==type){
		$("#"+id).append("<div>");
	}
}


/*****************************************************************
 * 动态加载选项(select、checkbox、radio)
 * @param element
 * @param listId
 * @param params
 * @param value 选中的值
*****************************************************************/
function reLoadGeoData(url ,type, id, name, listId, param, data){
	var staticSourceList = cache["listdata_"+listId+"_"+param]; 
	if(null==staticSourceList){
		params = {"datalistId":listId,"params":param};
		$.ax(url, params, false, 'post', 'json', 
	         function(data){
				 var result = $.parseJSON(data.result);
				 staticSourceList = result;
				 cache[("listdata_"+listId+"_"+param)]=result;
	         }, 
	         function(){
	         }
	    );
	}
	
	var curVal = data;
	
	$("#"+id).html("");
	for (var rs in staticSourceList) {
		 var key = staticSourceList[rs]["key"];
		 var value = staticSourceList[rs]["value"];
		 var showValue = value;
		 if(null!=value&&$.trim(value)!=""&&value.length>6){
			 showValue = value.substring(0,6)+"...";
		 }
	   	 switch (type)
	   	 {
	    	 case "select":
	    		 if(curVal==key){
	    			 $("#"+id).append("<option attrId='"+id+"' value='"+key+"' selected='selected'>"+staticSourceList[rs]["value"]+"</option>");
	    		 }else{
					 $("#"+id).append("<option attrId='"+id+"' value='"+key+"'>"+staticSourceList[rs]["value"]+"</option>");
	    		 }
	    	   break;
	    	 case "checkbox":
	    		 if(curVal==key){
	    			 $("#"+id).append("<li style='width:150px !important;height:22px !important;list-style:none;float:left;'><input type='checkbox' checked='checked' id='"+name+"_"+key+"' attrId='"+id+"' name='"+name+"' loadId='"+name+"' value='"+key+"'><label for='"+name+"_"+key+"' title='"+value+"' style='cursor:pointer;'>&nbsp;"+showValue+"</label></li>");
	    		 }else{
	    			 $("#"+id).append("<li style='width:150px !important;height:22px !important;list-style:none;float:left;'><input type='checkbox' id='"+name+"_"+key+"' attrId='"+id+"' name='"+name+"' loadId='"+name+"' value='"+key+"'><label for='"+name+"_"+key+"' title='"+value+"' style='cursor:pointer;'>&nbsp;"+showValue+"</label></li>");
	    		 }
	    	     break;
	    	 case "radio":
	    		 if(curVal==key){
	    			 $("#"+id).append("<li style='width:150px !important;height:22px !important;list-style:none;float:left;'><input type='radio' checked='checked' id='"+name+"_"+key+"' attrId='"+id+"' name='"+name+"' loadId='"+name+"' value='"+key+"'><label for='"+name+"_"+key+"' title='"+value+"' style='cursor:pointer;'>&nbsp;"+showValue+"</label></li>");
	    		 }else{
	    			 $("#"+id).append("<li style='width:150px !important;height:22px !important;list-style:none;float:left;'><input type='radio' id='"+name+"_"+key+"' attrId='"+id+"' name='"+name+"' loadId='"+name+"' value='"+key+"'><label for='"+name+"_"+key+"' title='"+value+"' style='cursor:pointer;'>&nbsp;"+showValue+"</label></li>");
	    		 }
	    	     break;
	   	 }
   }
}

/*****************************************************************
 * 获取静态数据源更换展示
 * @param value
 * @param row
 * @param indexq
 * @param url
 * @param listId
 * @param param
*****************************************************************/
function replaceColShow(value, url, listId, param){
	var replaceStr = "";
	if("dgAppendSumRow"==value){
		return '<b>合计：</b>';
	}
	if('thisVal'==param){
		param = value;
	}
	var staticSourceList = cache["listdata_"+listId+"_"+param]; 
	if(null==staticSourceList){
		params = {"datalistId":listId,"params":param};
		$.ax(url, params, false, 'post', 'json', 
	         function(data){
				 var result = $.parseJSON(data.result);
				 staticSourceList = result;
				 cache[("listdata_"+listId+"_"+param)]=result;
	         }, 
	         function(){
	         }
	    );
	}
    for (var rs in staticSourceList) {
	   	 if(value==staticSourceList[rs]["key"]){
	   		 replaceStr = staticSourceList[rs]["value"];
	   		 break;
	   	 }else{
	   		 replaceStr = "";
	   	 }
    }
    return replaceStr;
}

function formatNumberCol(value,valType,fmtType,pattern){
	var replaceStr = value;
	if(typeof(value)!="undefined"){
		if("dateNumber"==valType&&null!=value){
			return splitDateNum(value,null);
			/*var valYear = "";	
			var valMonth = "";
			var valDay = "";
			var valHour = "";
			var valMinute = "";
			var valSeconds = "";
			if(value.length>=4){
				valYear = value.substring(0,4);
			}
			if(value.length>=6){
				valMonth = "-"+value.substring(4,6);
			}
			if(value.length>=8){
				valDay = "-"+value.substring(6,8);
			}
			if(value.length>=10){
				valHour = " "+value.substring(8,10);
			}
			if(value.length>=12){
				valMinute = ":"+value.substring(10,12);
			}
			if(value.length>=14){
				valSeconds = ":"+value.substring(12,14);
			}
			return valYear+valMonth+valDay+valHour+valMinute+valSeconds;*/
		}
	}
	return replaceStr;
}

/*****************************************************************
 * 获取静态数据源
 * @param url
 * @param listId
 * @param param
*****************************************************************/
function getSourceList(listId, param){
	var staticSourceList = cache["listdata_"+listId+"_"+param]; 
	if(null==staticSourceList){
		params = {"datalistId":listId,"params":param};
		$.ax(staticDataUrl, params, false, 'post', 'json', 
	         function(data){
				 var result = $.parseJSON(data.result);
				 staticSourceList = result;
				 cache[("listdata_"+listId+"_"+param)]=result;
	         }, 
	         function(){
	         }
	    );
	}
	return staticSourceList;
}

/*****************************************************************
 * 动态填充数据
 * @param url
 * @param id
 * @param param
*****************************************************************/
function reLoadFormOptions(url, id, param){
	/**无缓存*/
	$.ax(url, param, null, 'post', 'json', 
         function(data){
		    var result= data.result;
			for (var rs in result) {
				if($("#"+id).find("*[loadId='"+rs+"']").get(0)){
					var tagname = $("*[loadId='"+rs+"']").get(0).tagName;
					var type = $("*[loadId='"+rs+"']").attr("type");
					var valStrs;
					if(typeof(result[rs])=="string"&&result[rs].indexOf(",")){
						valStrs = result[rs].split(",");
					}
					if("SPAN"==tagname.toUpperCase()||"FONT"==tagname.toUpperCase()||"DIV"==tagname.toUpperCase()||"LABEL"==tagname.toUpperCase()){
						$("*[loadId='"+rs+"']").html(result[rs]);
					}
					if("INPUT"==tagname.toUpperCase()&&type&&("RADIO"==type.toUpperCase()||"CHECKBOX"==type.toUpperCase())){
						$("#"+id).find("input[name='"+rs+"']").each(function(){
							if($(this).attr("value")==result[rs]||($(this).attr("name")==rs&&$.inArray($(this).attr("value"),valStrs)>=0)){
								$(this).attr("checked","checked");
							}
						})
					}
					if("INPUT"==tagname.toUpperCase()){
						if(!type||("RADIO"!=type.toUpperCase()&&"CHECKBOX"!=type.toUpperCase())){
							var el = $("*[loadId='"+rs+"']");
							var formatter = $(el).attr("dateFormatter");
							var dateNumFormatter = $(el).attr("dateNumFormatter");
							if(formatter&&null!=result[rs]){
								var timeStr = new Date(result[rs]);
								$(el).val(timeStr.format(formatter));
							}else if(dateNumFormatter&&null!=result[rs]){
								$(el).val(splitDateNum(result[rs],dateNumFormatter));
							}else{
								$(el).val(result[rs]);
							}
						}
					}
					if("SELECT"==tagname.toUpperCase()){
						$("#"+id).find("*[loadId='"+rs+"']").find("option").each(function(){
							if($(this).attr("value")==result[rs]){
								$(this).attr("selected","selected");
						    }
						})
					}
				}
			}
         }, 
         function(){
         }
    );
}

/*****************************************************************
 * 动态填充数据(有回调函数)
 * @param url
 * @param id
 * @param param
*****************************************************************/
function reLoadFormValue(url, id, param,funnctionName){
	$.ax(url, param, null, 'post', 'json', 
         function(data){
		    var result= data.result;
			for (var rs in result) {
				if($("#"+id).find("*[loadId='"+rs+"']").get(0)){
					var tagname = $("*[loadId='"+rs+"']").get(0).tagName;
					var type = $("*[loadId='"+rs+"']").attr("type");
					var valStrs;
					if(typeof(result[rs])=="string"&&result[rs].indexOf(",")){
						valStrs = result[rs].split(",");
					}
					if("SPAN"==tagname.toUpperCase()||"FONT"==tagname.toUpperCase()||"DIV"==tagname.toUpperCase()||"LABEL"==tagname.toUpperCase()){
						$("*[loadId='"+rs+"']").html(result[rs]);
					}
					if("INPUT"==tagname.toUpperCase()&&type&&("RADIO"==type.toUpperCase()||"CHECKBOX"==type.toUpperCase())){
						$("#"+id).find("input[name='"+rs+"']").each(function(){
							if($(this).attr("value")==result[rs]||($(this).attr("name")==rs&&$.inArray($(this).attr("value"),valStrs)>=0)){
								$(this).attr("checked","checked");
							}
						})
					}
					if("INPUT"==tagname.toUpperCase()){
						if(!type||("RADIO"!=type.toUpperCase()&&"CHECKBOX"!=type.toUpperCase())){
							var el = $("*[loadId='"+rs+"']");
							var formatter = $(el).attr("dateFormatter");
							var dateNumFormatter = $(el).attr("dateNumFormatter");
							if(formatter&&null!=result[rs]){
								var timeStr = new Date(result[rs]);
								$(el).val(timeStr.format(formatter));
							}else if(dateNumFormatter&&null!=result[rs]){
								$(el).val(splitDateNum(result[rs],dateNumFormatter));
							}else{
								$(el).val(result[rs]);
							}
						}
					}
					if("SELECT"==tagname.toUpperCase()){
						$("#"+id).find("*[loadId='"+rs+"']").find("option").each(function(){
							if($(this).attr("value")==result[rs]){
								$(this).attr("selected","selected");
						    }
						})
					}
				}
			}
			funnctionName(result);
         }, 
         function(){
         }
    );
}


/*****************************************************************
* 设置DataGrid默认选中
* @param divObj
* @param dgObj
*****************************************************************/
function defaultSelectInit(divObj,dgObj){
	var rows = $("#"+dgObj).datagrid('getData')['rows'];
	for (var rw in rows) {
		var rowCheck = rows[rw]['checked'];
		if(typeof(rowCheck)!="rowCheck"&&rowCheck){
			$("#"+dgObj).datagrid('selectRow',rw);
		} 
	}
}



/*****************************************************************
 * 添加查询条件
*****************************************************************/
function addQueryCondition(pageId,panelId){
	createnobtnwindow('添加查询条件','commonController.do?pageattrs&pageId='+pageId+'&panelId='+panelId,1000,500);
}

function setQueryConditionDefault(pageId,selId){
	var condId = $("#"+selId).val();
//	if(null!=condId&&$.trim(condId)!=""){
		var url = 'commonController.do?saveDefault';
		var params = {"condId":condId,"pageId":pageId};
		$.ax(url, params, false, 'post', 'json', 
	         function(data){
				 if(data.success == true){
					 $.messager.alert('提示',data.msg,'info');
			     }else{
			    	 $.messager.alert('错误',data.msg,'error');
			     }
	         }, 
	         function(){
	         }
	    );
//	}else{
//		 $.messager.alert('错误','请选择默认值','error');
//	}
}

/*****************************************************************
 * 展示自定义查询条件
*****************************************************************/
function showCustomConditionChose(sel,pageId,currentId,panel){
	var url = 'commonController.do?loadconds';
	var params = {"pageId":pageId};
	$.ax(url, params, false, 'post', 'json', 
         function(data){
			 var result = JSON.parse(data.result);
			 $(sel).html("");
			 $(sel).append("<option value='' selected='selected'></option>");
			 for (rs in result) {
				if((null!=currentId&&result[rs]["id"]==currentId)||1==result[rs]["isDefault"]){
					$(sel).append("<option value='"+result[rs]["id"]+"' selected='selected'>"+result[rs]["name"]+"</option>");
				}else{
					$(sel).append("<option value='"+result[rs]["id"]+"'>"+result[rs]["name"]+"</option>");
				}
		    }
         }, 
         function(){
         }
    );
	customQueryInit($(sel),$(panel));
}

/*****************************************************************
 * 加载自定义查询条件
*****************************************************************/
function customQueryInit(sel,condsDiv){
	var condId = $(sel).val();
	$(condsDiv).html("");
	if(null!=condId&&condId!=""&&condId.length>0){
		var divId = $(condsDiv).attr("id");
		var attrsSourceList = cache["attrSource_"+divId+"_"+condId]; 
		if(null==attrsSourceList){
			var url = 'commonController.do?loadcondattrs';
			var params = {"condId":condId};
			$.ax(url, params, false, 'post', 'json', 
		         function(data){
					 var result = JSON.parse(data.result);
					 attrsSourceList = result;
					 cache[("attrSource_"+divId+"_"+condId)] = attrsSourceList;
		         }, 
		         function(){
		         }
		    );
		}
		/*alert(JSON.stringify(attrsSourceList));*/
		if(null!=attrsSourceList&&attrsSourceList.length>0){
			var date_default = "";
			for (rs in attrsSourceList) {
				var editType = $.trim(attrsSourceList[rs]["editType"]);
		    	var insId = $.trim(attrsSourceList[rs]["insId"]);
		    	var attrValue = $.trim(attrsSourceList[rs]["attrValue"]);
		    	var label = $.trim(attrsSourceList[rs]["label"]);
		    	var name = $.trim(attrsSourceList[rs]["name"]);
		    	var oper = $.trim(attrsSourceList[rs]["operator"]);
		    	var max = attrsSourceList[rs]["max"];
		    	var min = attrsSourceList[rs]["min"];
		    	var type = $.trim(attrsSourceList[rs]["type"]);
		    	
		    	var strHtml = "";
		    	
		    	strHtml += "<span style='display:block;float:left;margin:0px 10px 10px 0px;height:22px !important;min-width:320px !important;'>" + label+"：<span name='sp_"+name+"'>";
		    	
		    	var operHtml = "<select style='margin-right:5px;width:80px !important;' id='oper_"+insId+"' dateoper='true'>"+chooseOperator(type,editType,oper)+"<select>";
		    	
				switch (editType)
		      	{
		       	 case "text":
		       		 strHtml += operHtml;
		       		 strHtml += "<input type='text' style='margin-right:5px;' id='"+insId+"' attrId='"+insId+"' name='"+name+"' value='"+attrValue+"' min='"+min+"' max='"+max+"' opers='"+oper+"' editType='"+editType+"' insId='"+insId+"'>";
		       	     break;
		       	 case "date":
		       		 if(oper=="between"){
			        	  strHtml += "<input type='text' class='easyui-datetimebox' data-options='showSeconds:false' id='start_"+insId+"' attrId='"+insId+"' name='"+name+"' label='"+label+"' opers='"+oper+"' editType='"+editType+"' insId='"+insId+"'><label>&nbsp;&nbsp;—&nbsp;&nbsp;</label>";
			        	  strHtml += "<input type='text' class='easyui-datetimebox' data-options='showSeconds:false' id='end_"+insId+"' attrId='"+insId+"' name='"+name+"' label='"+label+"' opers='"+oper+"' editType='"+editType+"' insId='"+insId+"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		       		 }else{
		       			  strHtml += operHtml;
			        	  strHtml += "<input type='text' class='easyui-datetimebox' data-options='showSeconds:false' id='"+insId+"' attrId='"+insId+"' name='"+name+"' label='"+label+"' opers='"+oper+"' editType='"+editType+"' insId='"+insId+"'><label>&nbsp;</label>";
		       		 }
		       	   break;
		       	 case "select":
		       		 strHtml += operHtml;
		       		 strHtml += "<select style='margin-right:5px;' id='"+insId+"' attrId='"+insId+"' name='"+name+"' class='easyui-combobox' data-options='multiple:true' label='"+label+"' opers='"+oper+"' editType='"+editType+"' insId='"+insId+"'></select>";
		       		 /*strHtml += "<select style='margin-right:5px;' id='"+insId+"' attrId='"+insId+"' name='"+name+"' label='"+label+"' opers='"+oper+"' editType='"+editType+"' insId='"+insId+"'><option value=''>请选择</option></select>";*/
		       		 break;
		       	 case "checkbox":
		       		 strHtml += "<ul id='"+insId+"' attrId='"+insId+"' name='"+name+"' label='"+label+"' opers='"+oper+"' editType='"+editType+"' insId='"+insId+"'></ul>";
		       		 break;
		       	 case "radio":
		       		 strHtml += "<ul id='"+insId+"' attrId='"+insId+"' name='"+name+"' label='"+label+"' opers='"+oper+"' editType='"+editType+"' insId='"+insId+"'></ul>";
		       		 break;
		      	}
				
				$(condsDiv).append(strHtml);
				
				if(editType=="date"){
					/*重绘时间控件*/
		    		$.parser.parse($(condsDiv));
		    		
		    		date_default += insId+"&"+oper+"&"+attrValue+"#";
		    	}
				
				/*初始化下拉框、多选框、单选框默认值*/
		    	if(editType=="select"||editType=="checkbox"||editType=="radio"){
		    		 var listId = attrsSourceList[rs]["dataListId"];
		       		 var param = attrsSourceList[rs]["params"];
		       		 
		       		 if(editType=="select"){
		       			loadSelectCombobox(insId,'commonController.do?listData',listId,param,name,attrValue);
		       			 /*$("#"+insId).val(attrValue);
		       			 $("#"+insId).multiSelect();*/
		       		 }
		       		 if(editType=="checkbox"){
		       			reLoadOptionsData(staticDataUrl, editType, insId, name, listId, param);
		       			var vals = attrValue.split(",");
		       			for(var v in vals){
		       				$("input:checkbox[value='"+vals[v]+"']").attr('checked','true');
		       			}
		       		 }
		       		if(editType=="radio"){
		       			reLoadOptionsData(staticDataUrl, editType, insId, name, listId, param);
		       			var vals = attrValue.split(",");
		       			for(var v in vals){
		       				$("input:radio[value='"+vals[v]+"']").attr('checked','true');
		       			}
		       		 }
		    	}
		    }
			
			/*初始化时间默认值*/
			if(date_default!=null&&date_default!=""&&date_default.length>0){
				var dafaultVals = date_default.split("#");
				if(dafaultVals.length>0){
					for (var df in dafaultVals) {
						var _vls = dafaultVals[df];
						if(_vls!=null&&$.trim(_vls)!=""){
							var _vs = _vls.split("&");
							if(null!=_vs&&_vs.length==3){
								var _insId = _vs[0];
								var _oper = _vs[1];
								var _attrValue = _vs[2];
								if(_oper=="between"){
					       			var _vals = _attrValue.split(",");
					       			if(_vals.length==2){
					       				$("#start_"+_insId).next("span").find("input").val(_vals[0]);
					       				$("#end_"+_insId).next("span").find("input").val(_vals[1]);
					       			}
					       		 }else{
					       			//$("#"+_insId).next("span").find("input:visible").val(_attrValue);
//					       			alert(_insId+"==="+_attrValue);
					       			$("#"+_insId).next("span").find("input").val(_attrValue);
//					       			alert($("#"+_insId).next("span").find("input").val());
					       		 }
							}
						}
					}
				}
			}
		}
	}
}


/*****************************************************************
 * 删除查询条件
*****************************************************************/
function delQueryCondition(condsId,divId){
	$.messager.confirm("操作提示", "您确定要执行删除操作吗？", function (data) {
        if (data) {
            var condId = $("#"+condsId).val();
        	var url = 'commonController.do?delcondattrs';
        	var params = {"condId":condId};
        	$.ax(url, params, false, 'post', 'json', 
                 function(data){
        			if("1"==data.code){
        		    	$.messager.alert('提示','删除成功!',"info",function(){
        		    		$("#"+divId).empty();
        		    		$("#"+condsId).find("option[value='"+condId+"']").remove();
        		    	});
        		    }else{
        		    	$.messager.alert('提示',data.msg);
        		    }
                 }, 
                 function(){
                 }
            );
        }
        else {
          
        }
    });
}

/*****************************************************************
 * 展示Datagrid附带数据
*****************************************************************/
function showDatagridParams(json){
	var dgparams = json["params"];
	if(typeof(dgparams)!="undefined"){
		for(var par in dgparams){
			var message = dgparams[par]["key"];
			if(message=="message"&&!dgparams[par]["value"]["success"]){
				$.messager.alert('错误',dgparams[par]["value"]["msg"],'error');
			}else{
				$("#"+dgparams[par]["key"]).html(dgparams[par]["value"]);
			}
		}
	}
}

/*****************************************************************
 * 刷新tab页
*****************************************************************/
function reloadTab(title){
	var currTab;
	if(null==title){
		currTab = self.parent.$('#maintabs').tabs('getSelected'); 
	}else{
		currTab = self.parent.$('#maintabs').tabs('getTab',title); 
	}
    var cuueUrl = currTab.panel('options').href;
    self.parent.$('#maintabs').tabs('update', {
        tab : currTab,
        options : {
         	 content : createFrame(cuueUrl)
        }
   });
}


/*****************************************************************
 * 解析运算符----中文
*****************************************************************/
function analyticOperatorZh_CN(oper){
	var operVal = "";
	oper = $.trim(oper);
	switch (oper){
	 	case "eq":
	 		operVal = "等于";
	 		break;
	 	case "ne":
	 		operVal = "不等于";
	 		break;
	 	case "gt":
	 		operVal = "大于";
	 		break;
	 	case "lt":
	 		operVal = "小于";
	 		break;
	 	case "ge":
	 		operVal = "大于等于";
	 		break;
	 	case "le":
	 		operVal = "小于等于";
	 		break;
	 	case "in":
	 		operVal = "包含";
	 		break;
	 	case "between":
	 		operVal = "两者之间";
	 		break;
	 	case "like":
	 		operVal = "模糊匹配";
	 		break;
	}
	return operVal;
}
/*****************************************************************
 * 解析运算符----符号
*****************************************************************/
function analyticOperatorSymbol(oper){
	var operVal = "";
	oper = $.trim(oper);
	switch (oper){
	 	case "eq":
	 		operVal = "=";
	 		break;
	 	case "ne":
	 		operVal = "!=";
	 		break;
	 	case "gt":
	 		operVal = ">";
	 		break;
	 	case "lt":
	 		operVal = "<";
	 		break;
	 	case "ge":
	 		operVal = ">=";
	 		break;
	 	case "le":
	 		operVal = "<=";
	 		break;
	 	case "in":
	 		operVal = "in";
	 		break;
	 	case "between":
	 		operVal = "between";
	 		break;
	}
	return operVal;
}

/*选择运算符*/
function chooseOperator(type,editType,defaultSel){
	var opers = '';
	switch(editType){
		case 'text':
			if(type=="int"){
				opers = 'eq, ne, gt, lt, ge, le';
			}
			if(type=="string"){
				opers = 'eq, ne, like';
			}
		  	break;
		case 'date':
			opers = ' gt, lt, ge, le';
		  	break;
		case 'select':
			if(type=="int"){
				opers = 'eq, ne, gt, lt, ge, le';
			}
			if(type=="string"){
				opers = 'eq, ne';
			}
		  	break;
	}
	return initOperator(opers,defaultSel);
}

/*添加运算符选项*/
function initOperator(opers,defaultSel){
	var selHtml = "";
	if(null!=opers&&opers!=""&&opers.length>0){
		var ops = opers.split(",");
		if(null!=ops&&ops.length>0){
			for (var o in ops) {
				var val = $.trim(ops[o]);
				var text = analyticOperatorZh_CN(val);
				if(null!=defaultSel&&val==defaultSel){
					selHtml+="<option value='"+val+"' selected='selected'>"+text+"</option>";
				}else{
					selHtml+="<option value='"+val+"'>"+text+"</option>";
				}
			}
		}
	}
	return selHtml;
}


/*****************************************************************
 * 切换tab页
*****************************************************************/
function hideTabInit(panelId,tabId){
	if("true"==$("#"+panelId).find("#isInit").val()){
		$("#"+tabId).hide();
		$("#"+panelId).find("#isInit").val("false");
		
		$(".pagination-page-list").attr("disabled",false);
		$(".pagination-num").attr("disabled",false);
	}
}

function switchTabShowOrHide(el,tabId,showId){
	$(el).parent().parent().children().removeClass("tabs-selected");
	$("#"+tabId).children().hide();
	
	$(el).parent().addClass("tabs-selected");
	$("#"+showId).show();
}
/**
 * 根据dataGrid里面checkbox获取对应属性的值
 */
function getCheckedIds(panelId, dataGridId, property){
	var data = $('#'+panelId).find("input[name='ck']:checked");
	var values = "";
	$(data).each(function(index){
		var checkindex = $(this).parents("tr").attr("datagrid-row-index");
		var value = $('#'+dataGridId).datagrid('getRows')[checkindex][property];
		if(typeof(value)!="undefined"){
			values += value;
			if(index!=data.length-1){
				values +=",";
			}
		}
	});
	return values;
}

/**
 * 编号宽度自适应
 */
$.extend($.fn.datagrid.methods, {
    fixRownumber : function (jq) {
        return jq.each(function () {
            var panel = $(this).datagrid("getPanel");
            /*获取最后一行的number容器,并拷贝一份*/
            var clone = $(".datagrid-cell-rownumber", panel).last().clone();
            /*由于在某些浏览器里面,是不支持获取隐藏元素的宽度,所以取巧一下*/
            clone.css({
                "position" : "absolute",
                left : -1000
            }).appendTo("body");
            var width = clone.width("auto").width();
            /*默认宽度是25,所以只有大于25的时候才进行fix*/
            if (width > 25) {
                /*多加5个像素,保持一点边距*/
                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width + 5);
                /*修改了宽度之后,需要对容器进行重新计算,所以调用resize*/
                $(this).datagrid("resize");
                /*一些清理工作*/
                clone.remove();
                clone = null;
            } else {
                /*还原成默认状态*/
                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).removeAttr("style");
            }
        });
    }
});

/*只能输入数字和两位小数*/
function clearNoNum(obj)  
{  
   obj.value = obj.value.replace(/[^\d.]/g,"");  /*清除“数字”和“.”以外的字符  */
   obj.value = obj.value.replace(/^\./g,"");  /*验证第一个字符是数字而不是. */ 
   obj.value = obj.value.replace(/\.{2,}/g,"."); /*只保留第一个. 清除多余的 */ 
   obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");  
   obj.value=obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');/*只能输入两个小数  */
}

/**
 * Excel导出进度条
 * @param datagridName 下载数据
 * @param subFrm  提交表单
 * @param progressKey 进度条Key
 * @param keyNum 进度
 * @param progressbar 进度条ID
 * @param intervalTsakName 定时任务名称
 * @param intervalTime 间隔时间
 */
function timeCycleEvent(datagridName,subFrm,progressKey,keyNum,progressbar,intervalTsakName,intervalTime){
	var currentProgress = $("#"+keyNum).val();
	if(currentProgress!=0){
		$.messager.alert('错误','下载中，请稍后。。。。。。','error');
		return;
	}
	var params = $('#'+datagridName).datagrid('options').queryParams["conditions"];
	var queryParmas = JSON.stringify(params);
	if('{}'!=queryParmas){
		$("#"+subFrm).find("#conditions").val(params);
	}
	$("#"+subFrm).submit();
	$('#'+progressbar).show();
	var intervalTsakName = setInterval(function(){
		$.ax('commonController.do?getProgress', {'progressKey':progressKey}, false, 'post', 'json', 
	         function(data){
			    var progress = data.progress==null?currentProgress:data.progress;
			    $('#'+progressbar).progressbar({ 
					value: progress 
				});
			    $("#"+keyNum).val(progress);
				if('100' == progress){
					clearInterval(intervalTsakName);
					$("#"+keyNum).val(0);
					$('#'+progressbar).hide();
					$('#'+progressbar).progressbar({ 
						value: 0
					});
					$.messager.alert('提示','下载完成','info');
				}
				if('101' == progress || null==progress){
					progress = 0;
					clearInterval(intervalTsakName);
					$('#'+progressbar).hide();
					$("#"+keyNum).val(0);
					$('#'+progressbar).progressbar({ 
						value: 0 
					});
					$.messager.alert('错误','数据异常,下载失败','error');
				}
	         },function(){
	        	 clearInterval(intervalTsakName);
	        	 $('#'+progressbar).hide();
	        	 $("#"+keyNum).val(0);
	        	 $('#'+progressbar).progressbar({ 
	        		 value: 0 
	        	 });
				 $.messager.alert('错误','数据异常,下载失败','error');
	         }
	 	);
	},intervalTime);
}


function loadSelectCombobox(id, url, listId, param, name, initVal){
	var staticSourceList = cache["listdata_"+listId+"_"+param]; 
	if(staticSourceList||null==staticSourceList){
		params = {"datalistId":listId,"params":param};
		if(null==url){
			url = 'commonController.do?listData';
		}
		$.ax(url, params, false, 'post', 'json', 
	         function(data){
				 var result = $.parseJSON(data.result);
				 staticSourceList = result;
				 cache[("listdata_"+listId+"_"+param)]=result;
	         }, 
	         function(){}
	    );
	}
	$("#"+id).combobox({
		data:staticSourceList,
		valueField:'key',
		textField:'value',
		panelHeight:'200',
        onLoadSuccess: function() { 
        	/*var val = $(this).combobox("getData");*/
            var val = staticSourceList;
            if(null!=val&&null!=initVal){
            	for (var item in val) {
                    if (initVal==val[item]["key"]) {
                    	$(this).combobox('setText', val[item]["value"]);
                    	$(this).combo("panel").find("div.combobox-item:eq("+item+")").addClass("combobox-item-selected");
                    	$(this).combo("textbox").after("<input type='hidden' class='textbox-value' name='"+name+"' value='"+initVal+"'>");
                    }
                }
            }
        }
	});
}

function splitDateNum(value,formatter){
	var valYear = "";	
	var valMonth = "";
	var valDay = "";
	var valHour = "";
	var valMinute = "";
	var valSeconds = "";
	if(null!=value&&$.trim(value)!=""){
		if(value.length>=4){
			valYear = value.substring(0,4);
		}
		if(value.length>=6){
			valMonth = "-"+value.substring(4,6);
		}
		if(value.length>=8){
			valDay = "-"+value.substring(6,8);
		}
		if(value.length>=10){
			valHour = " "+value.substring(8,10);
		}
		if(value.length>=12){
			valMinute = ":"+value.substring(10,12);
		}
		if(value.length>=14){
			valSeconds = ":"+value.substring(12,14);
		}
	}
	return valYear+valMonth+valDay+valHour+valMinute+valSeconds;
	
}

/*处理金额截取2位小数*/
function changeTwoDecimal_f(x) {
    var f_x = parseFloat(x);
    if (isNaN(f_x)) {
        alert('function:changeTwoDecimal->parameter error');
        return false;
    }
    var f_x = Math.round(x * 100) / 100;
    var s_x = f_x.toString();
    var pos_decimal = s_x.indexOf('.');
    if (pos_decimal < 0) {
        pos_decimal = s_x.length;
        s_x += '.';
    }
    while (s_x.length <= pos_decimal + 2) {
        s_x += '0';
    }
    return s_x;
} 

$(function(){
	/*
	 * 去除所有空格
	 * 禁止所有输入框输入如下字符
	 * "'<>
	 * */
	$("input").live('change',function(){
		if(typeof($(this).attr('notVerifiedCharacter'))==="undefined"){
    		var clas = $(this).attr('class');
    	    if(clas==null||clas.indexOf('textbox')<0){
    			var valStr = $(this).val();
    			valStr = valStr.replace(/(["'<>])/g,'').replace(/(^\s+)|(\s+$)/g,"");
    			$(this).val(valStr);
    	    }
		} 
	});
	$('input').live('keyup', function() {  
		if(typeof($(this).attr('notVerifiedCharacter'))==="undefined"){
    		var clas = $(this).attr('class');
    	    if(clas==null||clas.indexOf('textbox')<0){
    			var valStr = $(this).val();
    			valStr = valStr.replace(/(["'<>])/g,'').replace(/(^\s+)|(\s+$)/g,"");
    			$(this).val(valStr);
    	    }
		} 
    });  
    $('input').live('keydown', function() {  
    	if(typeof($(this).attr('notVerifiedCharacter'))==="undefined"){
    		var clas = $(this).attr('class');
    	    if(clas==null||clas.indexOf('textbox')<0){
    			var valStr = $(this).val();
    			valStr = valStr.replace(/(["'<>])/g,'').replace(/(^\s+)|(\s+$)/g,"");
    			$(this).val(valStr);
    	    }
		} 
    });  
    $('input').live('keypress', function() {  
    	if(typeof($(this).attr('notVerifiedCharacter'))==="undefined"){
    		var clas = $(this).attr('class');
    	    if(clas==null||clas.indexOf('textbox')<0){
    			var valStr = $(this).val();
    			valStr = valStr.replace(/(["'<>])/g,'').replace(/(^\s+)|(\s+$)/g,"");
    			$(this).val(valStr);
    	    }
		} 
    });  
});



//判断所有的checkbox的选中状态
//@id : checkbox的id
function checkedStatus(planId,id) {
	//获取checkbox
	var temp = $('#'+planId).find('#'+id);
	//debugger;
	if(temp){
		//设置checkbox的下级checkbox的状态
		setChildCheckBox(planId,temp);
		//设置checkbox的上级checkbox的状态
		setParentCheckBox(planId,temp);
	}
}

//获取checkbox的下级checkbox信息
//@entity : checkbox的DOM对象
function findChildCheckBox(planId,entity) {
	//存放下级checkbox的数组
	var chkArray = new Array();
	if(entity){
		var pId = $(entity).attr('id');
		var cks = $("#"+planId).find("input[parentId='"+pId+"']");
		if(cks){
			$(cks).each(function(){
				chkArray.push($(this));
			});
		}
	}
	return chkArray;
}

//获取checkbox的同级checkbox信息
//@entity : checkbox的DOM对象
function findBrotherCheckBox(planId,entity) {
	//存放同级checkbox的数组
	var chkArray = new Array();
	if(entity){
		var parentId = $(entity).attr("parentId");
//		console.log("B===="+parentId);
		if(parentId){
			var cks = $("#"+planId).find("input[parentId='"+parentId+"']");
			if(cks){
				$(cks).each(function(){
					chkArray.push($(this));
				});
			}
		}
	}
	return chkArray;
}

//获取checkbox的上级checkbox信息
//@entity : checkbox的DOM对象
function findParentCheckBox(planId,entity) {
	//存放上级checkbox的对象
	var ck = null;
	var parentId = $(entity).attr("parentId");
//	console.log("P===="+parentId);
	if(parentId){
		ck = $("#"+planId).find("input[id='"+parentId+"']");
	}
	return ck;
}


//设置checkbox的下级checkbox的状态
//@entity : checkbox的DOM对象
function setChildCheckBox(planId,entity) {
	//entity的选中状态
	var status = $(entity).attr("checked") === 'checked' ? true : false;
	//获取entity的下级checkbox
	var childList = findChildCheckBox(planId,entity);
	//判断是否有下级
	if (childList.length > 0) {
		//遍历下级checkbox，并设置状态
		for (var i = 0; i < childList.length; i++) {
			if($(childList[i])){
				$(childList[i]).attr('checked',status);
				//childList[i].checked = status;
				//设置childList[i]的下级checkbox的状态
				setChildCheckBox(planId,childList[i]);
			}
		}
	}
}

//设置checkbox的上级checkbox的状态
//@entity : checkbox的DOM对象
function setParentCheckBox(planId,entity) {
	//entity的上级checkbox的选中状态
	var parentChecked = false;
	//获取entity的上级checkbox
	var parentCheckBox = findParentCheckBox(planId,entity);
	//判断是否有上级
	if (parentCheckBox) {
		//获取entity的同级checkbox
		var brotherList = findBrotherCheckBox(planId,entity);
		console.log(brotherList.length);
		//判断是否有同级
		if (brotherList.length > 0) {
			//遍历同级checkbox
			for (var i = 0; i < brotherList.length; i++) {
				if($(brotherList[i])){
					var status = $(brotherList[i]).attr("checked") === 'checked' ? true : false;
					//如果同级的checkbox有未选中的状态，则设置上级的checkbox的状态为false
					if (status) {
						parentChecked = true;
						break;
					}
				}
			}
		}
		//设置上级checkbox的选中状态
		$(parentCheckBox).attr('checked',parentChecked);
		//parentCheckBox.checked = parentChecked;
		//设置parentCheckbox的上级checkbox的状态
		setParentCheckBox(planId,parentCheckBox);
	}
}


function chengeDgFildsSlide(panel, fieldDiv, uploadExcelFrm) {
	if ($("#" + panel).find("#" + fieldDiv).is(":visible")) {
		$("#" + panel).find("#" + fieldDiv).slideUp();
		$("#" + panel).find(".moreSelect").css("background-position", "90% 0");
	} else {
		$("#" + panel).find("#" + fieldDiv).slideDown();
		$("#" + panel).find(".moreSelect").css("background-position", "10% 0");
	}
	var selCks = $("#" + panel).find("input[type='checkbox']");
	var showFields = $("#" + uploadExcelFrm).find("#fields").val();
	$(selCks).each(function() {
		var _showFields = showFields.split(",");
		var f = $(this).val();
		var isShow = _showFields.indexOf(f) == -1 ? false : true;
		$(this).attr("checked",isShow);
	});
}

function refreshDgFieldsDisplay(dgId,panel,uploadExcelFrm){
	var selCks = $("#"+panel).find("input[type='checkbox']");
	var showCks = $("#"+panel).find("input:checked");
	if(showCks.length===0){
		$.messager.alert('错误','不能隐藏所有的列！','error');
		return;
	}
	var showFields = "";
	$(selCks).each(function(){
		var isShow = $(this).attr("checked") === 'checked' ? true : false;
		var field = $(this).val();
		if(isShow){
			showFields += field + ",";
			$('#'+dgId).datagrid('showColumn',field);
		}else{
			$('#'+dgId).datagrid('hideColumn',field);
		}
	});
	$("#"+uploadExcelFrm).find("#fields").val(showFields);
	
	var url = 'commonController.do?saveDgFields';
	var params = {"dgName":dgId,"dgFields":showFields};
	$.ax(url, params, function(data){});
}

function loadDgFields(dgId,panel,uploadExcelFrm){
	var url = 'commonController.do?loadDgFields';
	var params = {"dgName":dgId};
	$.ax(url, params, false, 'post', 'json', 
        function(data){
			var showFields = data.result;
			if(null!=showFields&&""!=showFields&&typeof(showFields)!="undefined"){
				$("#"+uploadExcelFrm).find("#fields").val(showFields);
				var selCks = $("#"+panel).find("input[type='checkbox']");
				$(selCks).each(function(){
					var _showFields = showFields.split(",");
					var f = $(this).val();
					var isShow = _showFields.indexOf(f) == -1 ? false : true;
					if(isShow){
						$('#'+dgId).datagrid('showColumn',f);
					}else{
						$('#'+dgId).datagrid('hideColumn',f);
					}
				});
		    }
        }, 
        function(){
        }
   );
}

function getDgQueryParamsStrs(formObj){
	var queryParamObjs = $("#"+formObj).children(".commSearchCond");
	var conditions = [];
	$(queryParamObjs).each(function(){
		var curDiv = $(this);
		if(curDiv.attr("hideEffect")!=null && curDiv.attr("hideEffect")!="undefined" && curDiv.attr("hideEffect")=='false'){
			if(!curDiv.is(":visible"))return;
		}
		var queryParamIpts = curDiv.find(":input[name]");
		var condition = {};
		condition["name"]=curDiv.attr("name");
		condition["params"]=[];
		$(queryParamIpts).each(function(){
			if(typeof($(this).attr("name"))!="undefined"){
				var name = $(this).attr("name");
				if($(this)[0].tagName.toUpperCase()=="SELECT"){
					var selOpts = $(this).find("option:selected");
					$(selOpts).each(function(){
						var value = $(this).attr("value");
						if(null!=value&&value.length>0){
							var optPair = {};
							optPair["key"]=name;
							optPair["value"]=value;
							optPair["type"]=$(this).attr("type");
							if(typeof($(this).attr("attrId"))!="undefined"){
								var attrId = $(this).attr("attrId");
								if($("#oper_"+attrId).length>0){
									var attrOper = $("#oper_"+attrId).val();
									if(null!=attrOper&&attrOper.length>0&&attrOper!=""){
										optPair["operator"]=attrOper;
									}else{
										if(typeof($(this).attr("opers"))!="undefined"){
											optPair["operator"]=$(this).attr("opers");
										}
									}
								}
							}
							condition["params"].push(optPair);
						}
					});
				}else if($(this).attr("type").toUpperCase()=="CHECKBOX"||$(this).attr("type").toUpperCase()=="RADIO"){
					if($(this).prop("checked")){
						var value = $(this).attr("value");
						if(null!=value&&value.length>0){
							var optPair = {};
							optPair["key"]=name;
							optPair["value"]=value;
							optPair["type"]=$(this).attr("type");
							if(typeof($(this).attr("attrId"))!="undefined"){
								var attrId = $(this).attr("attrId");
								if($("#oper_"+attrId).length>0){
									var attrOper = $("#oper_"+attrId).val();
									if(null!=attrOper&&attrOper.length>0&&attrOper!=""){
										optPair["operator"]=attrOper;
									}else{
										if(typeof($(this).attr("opers"))!="undefined"){
											optPair["operator"]=$(this).attr("opers");
										}
									}
								}
							}
							condition["params"].push(optPair);
						}
					}
				}else{
					var value = $(this).attr("value");
					if(null!=value&&value.length>0){
						var optPair = {};
						optPair["key"]=name;
						optPair["value"]=value;
						optPair["type"]=$(this).attr("type");
						/*获取运算符*/
						if($(this).attr("type").toUpperCase()=="HIDDEN"){
							var dateopr = $(this).parents("span[name='sp_"+name+"']").find("*[dateoper='true']");
							if(typeof(dateopr)!="undefined"){
								optPair["operator"]=$(dateopr).val();
								var operId = $(dateopr).attr("id");
								if(typeof(operId)!="undefined"){
									var attrId = operId.replace("oper_","");
									if(typeof($("#"+attrId))!="undefined"&&typeof($("#"+attrId).attr("edittype"))!="undefined"){
										optPair["attrType"]=$("#"+attrId).attr("edittype");
									}
								}
							}
						}else{
							if(typeof($(this).attr("attrId"))!="undefined"){
								var attrId = $(this).attr("attrId");
								if($("#oper_"+attrId).length>0){
									var attrOper = $("#oper_"+attrId).val();
									if(null!=attrOper&&attrOper.length>0&&attrOper!=""){
										optPair["operator"]=attrOper;
									}else{
										if(typeof($(this).attr("opers"))!="undefined"){
											optPair["operator"]=$(this).attr("opers");
										}
									}
								}
							}
						}
						condition["params"].push(optPair);
					}
				}
			}
		}); 
		conditions.push(condition);
	}); 
	return JSON.stringify(conditions);
}

function isChangeQuery(frm,dg){
	var queryStrs = '"'+getDgQueryParamsStrs(frm)+'"';
	var params = $('#'+dg).datagrid('options').queryParams["conditions"];
	var queryParmas = JSON.stringify(params).replace(/\\/g,'');
	if(queryStrs!=queryParmas){
		return true;
	}
	return false;
}