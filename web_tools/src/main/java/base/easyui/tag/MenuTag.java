package base.easyui.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import base.util.StringUtils;
import base.entity.SecFunction;
/**
 * 菜单标签
 * @author xing.zeng
 * create time :2016年4月14日
 */
public class MenuTag extends TagSupport {
	private static final long serialVersionUID = 7921790186857340220L;
	private Logger log = LoggerFactory.getLogger(MenuTag.class);
	
	protected String style = "easyui";// 菜单样式
	protected List<SecFunction> parentFun;// 一级菜单
	protected List<SecFunction> childFun;// 二级菜单
	protected Map<Integer, List<SecFunction>> menuFun;// 菜单Map

	public void setParentFun(List<SecFunction> parentFun) {
		this.parentFun = parentFun;
	}

	public void setChildFun(List<SecFunction> childFun) {
		this.childFun = childFun;
	}

	public int doStartTag() throws JspTagException {
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspTagException {
		try {
			JspWriter out = this.pageContext.getOut();
			out.print(buildMenu());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	/**
	 * 根据菜单Map构建菜单
	 * add by xzeng 2016年4月18日
	 * @return
	 */
	private String buildMenu(){
		StringBuilder builder = new StringBuilder();
		
		if(style.equals("easyui")){
			builder.append("<div id=\"sidebar\" class=\"fnj-scrollbar-warp\">");
			builder.append("<div class=\"fnj-scrollbar-viewport\">");
			builder.append("<div class=\"fnj-scrollbar-overview\" style=\"position: relative;\">");
			builder.append("<ul class=\"left_menu_1\">");
			builder.append("<div id=\"menuDiv\" class=\"left_warp nav\">");
			builder.append(buildMultiStageMenu());
			builder.append("</ul></div></div></div></div>");
		}
		if(style.equals("bootstrap")){
			//builder.append(ListtoMenu.getBootMenu(parentFun, childFun));
		}
		if(style.equals("json")){
			builder.append("<script type=\"text/javascript\">");
			//builder.append("var _menus="+ListtoMenu.getMenu(parentFun, childFun));
			builder.append("</script>");
		}
		if(style.equals("june_bootstrap")){
			//builder.append(ListtoMenu.getBootstrapMenu(menuFun));
		}
		log.info("menu:{}", builder.toString());
		
		return builder.toString();
	}
	
	/**
	 * 构建多级菜单
	 * add by xzeng 2016年4月18日
	 * @param map
	 * @return
	 */
	private String buildMultiStageMenu(){
		if (menuFun == null || menuFun.size() == 0 || !menuFun.containsKey(0)) {
			return "不具有任何权限,\n请找管理员分配权限";
		}
		StringBuilder menuString = new StringBuilder();
		// 取一级菜单
		List<SecFunction> list = menuFun.get(0);
		List<SecFunction> childList = null;
		for (SecFunction function : list) {
			String funName = function.getFunctionName();
			// 功能名太长截取
			/*if (StringUtils.isNotBlank(funName) && funName.length() > 8) {
				funName = funName.substring(0, 8) + "...";
			}*/
			String aTagStr = String.format("<a class=\"hide_1t\" title=\"%s\" href=\"javascript:void(0);\" onclick=\"slideMenu(this,'1t');\"><span class=\"hide_open_1t\"></span>%s</a>", funName,funName); 
			menuString.append("<li>").append(aTagStr);
			// 下一级菜单
			childList = childFunction(function);
			menuString.append(buildNextLevelMenu(function, childList));
			menuString.append("</li>");
		}
		
		return menuString.toString();
	}
	
	/**
	 * 构建子级菜单
	 * add by xzeng 2016年4月18日
	 * @param parent
	 * @param map
	 * @return
	 */
	private String buildNextLevelMenu(SecFunction parent, List<SecFunction> childList){
		StringBuilder menuString = new StringBuilder();
		if (childList != null) {
			int menuLevel = parent.getFunctionLevel() + 2;
			String ulTagStr = String.format("<ul class=\"left_menu_%d child\" style=\"display: none;\">", menuLevel);
			menuString.append(ulTagStr);
			// 遍历菜单实体列表
			for (SecFunction function : childList) {
				if (parent.getFunctionId().equals(function.getParentFunctionId())) {
					// 获取该功能的子菜单
					List<SecFunction> childs = childFunction(function);
					
					if (childs == null || childs.size() == 0) {// 不存在子菜单
						menuString.append(buildLeafMenu(function));
					}else {	// 存在下级菜单
						String funName = function.getFunctionName();
						/*if (StringUtils.isNotBlank(funName) && funName.length() > 8) {
							funName = funName.substring(0, 8) + "...";
						}*/
						
						String aTagStr = String.format("<a class=\"hide_%d\" title=\"%s\" style=\"color:#3c3c3c;\" href=\"javascript:void(0);\" onclick=\"slideMenu(this,'%d');\">&nbsp;<span class=\"hide_close_%d\"></span>%s</a>",menuLevel,funName,menuLevel,menuLevel,funName);
						menuString.append("<li>").append(aTagStr);
						menuString.append(buildNextLevelMenu(function, childs));
					}
				}
			}
			menuString.append("</ul>");
		}
		return menuString.toString();
	}
	
	/**
	 * 构建最后一层菜单
	 * add by xzeng 2016年4月15日
	 * @param function
	 * @return
	 */
	private String buildLeafMenu(SecFunction function) {
		StringBuilder menuString = new StringBuilder();
		// 菜单名展示长度处理
		String funName = function.getFunctionName();
		/*if (StringUtils.isNotBlank(funName) && funName.length() > 8) {
			funName = funName.substring(0, 8) + "...";
		}*/
		
		String icon = "folder";
		int menuLevel = function.getFunctionLevel() + 1;
		String aTagStr = null;
		if(StringUtils.isNotBlank(function.getFunctionUrl())&&function.getFunctionUrl().contains("?")){
			menuString.append("&clickFunctionId=");
			menuString.append( function.getFunctionId()) ;
			aTagStr = String.format("<a onclick=\"addTab(\'%s\',\'%s&clickFunctionId=%s\')\" title=\"%s\" class=\"hide_%d\" url=\"%s\" href=\"#\" style=\"color:#3c3c3c;\">&nbsp;%s</a>", funName, function.getFunctionUrl(), function.getFunctionId(), funName, menuLevel, function.getFunctionUrl(), funName);
		}else{
			aTagStr = String.format("<a onclick=\"addTab(\'%s\',\'%s\')\" title=\"%s\" class=\"hide_%d\" url=\"%s\" href=\"#\" style=\"color:#3c3c3c;\">&nbsp;%s</a>", funName, function.getFunctionUrl(), funName, menuLevel, function.getFunctionUrl(), funName);
		}
		String liTagStr = String.format("<li iconCls=\"%s\"> %s</li>", icon, aTagStr);
		
		menuString.append(liTagStr);
		
		return menuString.toString();
	}
	
	/**
	 * 获取子菜单列表
	 * add by xzeng 2016年4月15日
	 * @return
	 */
	private List<SecFunction> childFunction(SecFunction parentFun) {
		List<SecFunction> childs = new ArrayList<SecFunction>();
		if (parentFun == null) {
			return childs;
		}
		// 获取子级菜单列表
		List<SecFunction> funList = menuFun.get(parentFun.getFunctionLevel() + 1);
		if (funList != null) {
			Long parentId = parentFun.getFunctionId();

			for (SecFunction item : funList) {
				if (parentId != null && item.getParentFunctionId() != null
					&& parentId.longValue() == item.getParentFunctionId().longValue()){
					childs.add(item);
				}
			}
		}

		return childs;
	}
	
	public void setStyle(String style) {
		this.style = style;
	}

	public void setMenuFun(Map<Integer, List<SecFunction>> menuFun) {
		this.menuFun = menuFun;
	}

}
