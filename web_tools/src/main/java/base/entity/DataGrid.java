package base.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
//import org.apache.commons.collections.map.HashedMap;
import base.common.SortDirection;

/**
 * easyui 的datagrid实体
 * @author xing.zeng
 * create time :2016年4月19日
 */
public class DataGrid implements Serializable{
	private static final long serialVersionUID = -1906098579873826771L;
	
	private int page = 1;	// 当前页
	private int rows = 10;	// 每页显示记录数
	private String sort = null;	// 排序字段名
	private SortDirection order = SortDirection.asc;// 按什么排序(asc,desc)
	private String field;	//字段
	private String treefield;	//树形数据表文本字段
	private List results;	// 结果集
	private int total;	//总记录数
	private String footer;	//合计列
	private String checkProperty;	//result中javabean的属性名，判断页面checkbox是否选中
	private Set<String> checkValues;	//result的checkProperty属性的值是否在values中
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public SortDirection getOrder() {
		return order;
	}
	public void setOrder(SortDirection order) {
		this.order = order;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getTreefield() {
		return treefield;
	}
	public void setTreefield(String treefield) {
		this.treefield = treefield;
	}
	public List getResults() {
		return results;
	}
	public void setResults(List results) {
		this.results = results;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getFooter() {
		return footer;
	}
	public void setFooter(String footer) {
		this.footer = footer;
	}
	public String getCheckProperty() {
		return checkProperty;
	}
	public void setCheckProperty(String checkProperty) {
		this.checkProperty = checkProperty;
	}
	public Set<String> getCheckValues() {
		return checkValues;
	}
	public void setCheckValues(Set<String> checkValues) {
		this.checkValues = checkValues;
	}
	//private Map params = new HashedMap();
	
}
