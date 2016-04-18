package base.easyui.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import base.util.ConvertUtils;
/**
 * jsp自定义标签--base
 * @author xing.zeng
 * create time :2016年4月13日
 */
public class BaseTag extends TagSupport {
	private static final long serialVersionUID = -4726110277747885239L;
	protected String type = "default";	// 加载类型
	
	public void setType(String type) {
		this.type = type;
	}
	
	public int doStartTag() throws JspException {
		return EVAL_PAGE;
	}
	
	public int doEndTag() throws JspException {
		try {
			JspWriter out = this.pageContext.getOut();
			StringBuffer sb = new StringBuffer();
			
			String types[] = type.split(",");
			if (ConvertUtils.isIn("jquery", types)) {
				sb.append("<script type=\"text/javascript\" src=\"plug-in/jquery/jquery-1.8.3.min.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/jquery/jquery.form-3.20.min.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/jquery/jquery.multiSelect.min.js\"></script>");
				sb.append("<link rel=\"stylesheet\" href=\"plug-in/jquery-ui/multiSelect/jquery.multiSelect.min.css\" type=\"text/css\"></link>");
			}
			if (ConvertUtils.isIn("ckeditor", types)) {
				sb.append("<script type=\"text/javascript\" src=\"plug-in/ckeditor/ckeditor.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/tools/ckeditorTool.js\"></script>");
			}
			if (ConvertUtils.isIn("ckfinder", types)) {
				sb.append("<script type=\"text/javascript\" src=\"plug-in/ckfinder/ckfinder.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/tools/ckfinderTool.js\"></script>");
			}
			if (ConvertUtils.isIn("easyui", types)) {
				sb.append("<script type=\"text/javascript\" src=\"plug-in/tools/dataformat.js\"></script>");
				sb.append("<link id=\"easyuiTheme\" rel=\"stylesheet\" href=\"plug-in/easyui/themes/default/easyui.css\" type=\"text/css\"></link>");
				sb.append("<link rel=\"stylesheet\" href=\"plug-in/easyui/themes/icon.css\" type=\"text/css\"></link>");
				sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"plug-in/accordion/css/accordion.css\">");
				sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"plug-in/easyui/themes/default/easyui.css\">");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/easyui/jquery.easyui.min.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/easyui/ajax.utils.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/easyui/locale/easyui-lang-zh_CN.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/tools/syUtil.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/easyui/extends/datagrid-scrollview.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/easyui/base.min.js\"></script>");
			}
			if (ConvertUtils.isIn("DatePicker", types)) {
				sb.append("<script type=\"text/javascript\" src=\"plug-in/My97DatePicker/WdatePicker.js\"></script>");
			}
			if (ConvertUtils.isIn("jqueryui", types)) {
				sb.append("<link rel=\"stylesheet\" href=\"plug-in/jquery-ui/css/ui-lightness/jquery-ui-1.9.2.custom.min.css\" type=\"text/css\"></link>");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/jquery-ui/js/jquery-ui-1.9.2.custom.min.js\"></script>");
			}
			if (ConvertUtils.isIn("jqueryui-sortable", types)) {
				sb.append("<link rel=\"stylesheet\" href=\"plug-in/jquery-ui/css/ui-lightness/jquery-ui-1.9.2.custom.min.css\" type=\"text/css\"></link>");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/jquery-ui/js/ui/jquery.ui.core.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/jquery-ui/js/ui/jquery.ui.widget.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/jquery-ui/js/ui/jquery.ui.mouse.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/jquery-ui/js/ui/jquery.ui.sortable.js\"></script>");
			}
			if (ConvertUtils.isIn("prohibit", types)) {
				sb.append("<script type=\"text/javascript\" src=\"plug-in/tools/prohibitutil.js\"></script>");		
			}

			if (ConvertUtils.isIn("tools", types)) {
				sb.append("<link rel=\"stylesheet\" href=\"plug-in/tools/css/common.css\" type=\"text/css\"></link>");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/lhgDialog/lhgdialog.min.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/tools/curdtools.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/tools/easyuiextend.js\"></script>");
			}
			if (ConvertUtils.isIn("toptip", types)) {
				sb.append("<link rel=\"stylesheet\" href=\"plug-in/toptip/css/css.css\" type=\"text/css\"></link>");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/toptip/manhua_msgTips.js\"></script>");
			}
			if (ConvertUtils.isIn("autocomplete", types)) {
				sb.append("<link rel=\"stylesheet\" href=\"plug-in/jquery/jquery-autocomplete/jquery.autocomplete.css\" type=\"text/css\"></link>");
				sb.append("<script type=\"text/javascript\" src=\"plug-in/jquery/jquery-autocomplete/jquery.autocomplete.min.js\"></script>");
			}
			if (ConvertUtils.isIn("fn", types)) {
				sb.append("<link rel=\"stylesheet\" href=\"css/base.css\" type=\"text/css\"></link>");
				sb.append("<link rel=\"stylesheet\" href=\"css/content.css\" type=\"text/css\"></link>");
				sb.append("<link rel=\"stylesheet\" href=\"css/header.css\" type=\"text/css\"></link>");
				sb.append("<link rel=\"stylesheet\" href=\"css/left.css\" type=\"text/css\"></link>");
				sb.append("<link rel=\"stylesheet\" href=\"css/login.css\" type=\"text/css\"></link>");
				sb.append("<link rel=\"stylesheet\" href=\"css/scroll.css\" type=\"text/css\"></link>");
				sb.append("<link rel=\"stylesheet\" href=\"css/select.css\" type=\"text/css\"></link>");
			}
			
			// 登录权限判断
			sb.append("<script>$.ajaxSetup({error: function (XMLHttpRequest, textStatus, errorThrown){if(XMLHttpRequest.status==403){alert('您没有权限访问此资源或进行此操作');return false;}},");
			sb.append("complete:function(XMLHttpRequest,textStatus){   var sessionstatus=XMLHttpRequest.getResponseHeader(\"sessionstatus\");if(sessionstatus=='timeout'){var top = getTopWinow(); alert('登录超时, 请重新登录.');top.location.href='http://tms.feiniu.com';}}});");
			sb.append("function getTopWinow(){var p = window; while(p != p.parent){p = p.parent; } return p;}</script>");
			out.print(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	
	}
}
