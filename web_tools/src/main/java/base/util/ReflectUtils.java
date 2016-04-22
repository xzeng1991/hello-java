package base.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * 反射基本操作
 * @author xing.zeng
 * create time :2016年4月19日
 */
public class ReflectUtils {
	private Class cls;	// 目标对象class属性
	private Object obj;	// 目标对象
	private HashMap<String, Method> getMethods = null;
	private HashMap<String, Method> setMethods = null;
	
	public ReflectUtils(Object o) {
		obj = o;
		initMethods();
	}
	
	/**
	 * 初始化操作类
	 * add by xzeng 2016年4月19日
	 */
	private void initMethods() {
		getMethods = new HashMap<String, Method>();
		setMethods = new HashMap<String, Method>();
		
		cls = obj.getClass();
		Method[] methods = cls.getMethods();
		
		// 定义正则表达式，从方法中过滤出getter / setter 函数.
		String gs = "get(\\w+)";
		Pattern getM = Pattern.compile(gs);
		String ss = "set(\\w+)";
		Pattern setM = Pattern.compile(ss);
		
		// 把方法中的"set" 或者 "get" 去掉
		String rapl = "$1";
		String param;
		for (int i = 0; i < methods.length; ++i) {
			Method m = methods[i];
			String methodName = m.getName();
			if (Pattern.matches(gs, methodName)) { // 提取
				param = getM.matcher(methodName).replaceAll(rapl).toLowerCase();
				getMethods.put(param, m);
			} else if (Pattern.matches(ss, methodName)) {
				param = setM.matcher(methodName).replaceAll(rapl).toLowerCase();
				setMethods.put(param, m);
			} else {
				// some other method.
			}
		}
	}
	
	/**
	 * 调用get方法
	 * add by xzeng 2016年4月19日
	 * @param property
	 * @return
	 */
	public Object getMethodValue(String property) {
		Object value = null;
		Method m = getMethods.get(property.toLowerCase());
		if (m != null) {
			try {
				value=m.invoke(obj, new Object[] {});
			} catch (Exception ex) {
				// ignore the exception,return null;
			}
		}
		return value;
	}
}
