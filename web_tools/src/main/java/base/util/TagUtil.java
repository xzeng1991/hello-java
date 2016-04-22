package base.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import base.entity.DataGrid;

public class TagUtil {
	/**
	 * 返回列表JSONObject对象
	 * @param field
	 * @param dataGrid
	 * @return
	 */
	public static String buildJsonStr(DataGrid dg) {
		JSONObject jObject = null;
		try {
			if(!StringUtils.isEmpty(dg.getFooter())){
				
				jObject = listToJson(dg.getField().split(","), dg.getTotal(), dg.getFooter().split(","),dg.getCheckProperty(),dg.getCheckValues(),dg.getResults());
			}else{
				jObject = listToJson(dg.getField().split(","), dg.getTotal(), null,dg.getCheckProperty(),dg.getCheckValues(), dg.getResults());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jObject.toString();
	}
	
	/**
	 * 将list中的数据拼装成easyUI json格式
	 * add by xzeng 2016年4月19日
	 * @param fields 展示数据项
	 * @param total 记录总数
	 * @param list 数据列表
	 * @param footers DATAGRID底部
	 * @param checkProperty
	 * @param checkValue
	 * @return
	 * @throws Exception
	 */
	private static JSONObject listToJson(String[] fields, int total, String[] footers, String checkProperty,
			Set checkValue, List list) throws Exception {
		JSONObject json = new JSONObject();
		
		json.put("total", total);
		// 提取需要展示数据的信息
		JSONArray rowsarry = fieldsArray(fields, checkProperty, checkValue, list);
		json.put("rows", rowsarry);
		// 提取底部数据信息
		JSONArray footerarry = footersArray(footers, checkProperty, checkValue, list);
		json.put("footer", footerarry);
		
		return json;
	}
	
	/**
	 * 提取列元素
	 * add by xzeng 2016年4月19日
	 * @param fields
	 * @param total
	 * @param checkProperty
	 * @param checkValue
	 * @param list
	 * @return
	 */
	private static JSONArray fieldsArray(String[] fields, String checkProperty, Set checkValue, List list){
		JSONArray rowsarry = new JSONArray();	// json列表
		JSONObject dest = null;		// 转换后json对象
		
		if(null != list){
			JSONObject src  = null;	// 数据源json对象
			for (int j = 0; j < list.size(); j++) {
				dest = new JSONObject();
				dest.put("state", "closed");
				// 数据转换为json对象
				src = JSONObject.fromObject(list.get(j));
				Object value = null;	//数据项的值
				for (int i = 0; i < fields.length; i++) {
					value = src.get(fields[i]);
					
					dest.put(fields[i], value);
					// 属性选中
					if(StringUtils.isNotBlank(checkProperty) && checkValue != null && checkValue.size() >0){
						if(fields[i].equals(checkProperty)){
							if(checkValue.contains(value)){
								dest.put("checked", true);
							}
						}
					}
				}
				rowsarry.add(dest);
			}
		}
		return rowsarry;
	}
	/**
	 * 底部数据元素
	 * add by xzeng 2016年4月20日
	 * @param footers
	 * @param total
	 * @param checkProperty
	 * @param checkValue
	 * @param list
	 * @return
	 */
	private static JSONArray footersArray(String[] footers, String checkProperty, Set checkValue, List list){
		JSONArray footerArray = new JSONArray();
		JSONObject obj = null;
		
		if(footers!=null){
			obj = new JSONObject();
			obj.put("name", "合计");
			footerArray.add(obj);
			
			for(String footer:footers){
				obj = new JSONObject();
				String footerFiled = footer.split(":")[0];
				Object value = null;
				if(footer.split(":").length==2){
					value = footer.split(":")[1];
				}else{
					value = getTotalValue(footerFiled,list);
				}
				obj.put(footerFiled, value);
				
				if(StringUtils.isNotBlank(checkProperty) && checkValue != null && checkValue.size() >0){
					if(footerFiled.equals(checkProperty)){
						if(checkValue.contains(value))
							obj.put("checked", true);
					}
				}
			}
		}
		return footerArray;
	}
	/**
	 * 
	 * 获取对象内对应字段的值
	 * @param fields
	 */
	public static Object fieldNametoValues(String FiledName, Object o){
		Object value = "";
		String fieldName = "";
		String childFieldName = null;
		
		ReflectUtils reflectHelper = new ReflectUtils(o);
		if (FiledName.indexOf("_") == -1) {
			if(FiledName.indexOf(".") == -1){ // 无子节点
				fieldName = FiledName;
			}else{	// 分离子元素和父元素
				fieldName = FiledName.substring(0, FiledName.indexOf("."));	//外键字段引用名
				childFieldName = FiledName.substring(FiledName.indexOf(".") + 1);	//外键字段名
			}
		} else {
			fieldName = FiledName.substring(0, FiledName.indexOf("_"));	//外键字段引用名
			childFieldName = FiledName.substring(FiledName.indexOf("_") + 1);	//外键字段名
		}
		// 通过反射取值
		value = reflectHelper.getMethodValue(fieldName);
		// 递归获取子类属性
		if (value != null && (FiledName.indexOf("_") != -1 || FiledName.indexOf(".") != -1)) {
			value = fieldNametoValues(childFieldName, value);
		}
		// 获取值
		if(value != null) {
			value = value.toString().replaceAll("\r\n", "");
		}
		
		return value;
	}
	
	/**
	 * 计算指定列的合计
	 * @param filed 字段名
	 * @param list 列表数据
	 * @return
	 */
	private static Object getTotalValue(String fieldName, List list){
		Double sum = 0D;
		try{
			for (int j = 0; j < list.size(); j++) {
				Double v = 0d;
				String vstr = String.valueOf(fieldNametoValues(fieldName, list.get(j)));
				if(!StringUtils.isEmpty(vstr)){
					v = Double.valueOf(vstr);
				}else{
					
				}
				sum+=v;
			}
		}catch (Exception e) {
			return "";
		}
		return sum;
	}
}
