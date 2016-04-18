package base.util;
/**
 * 对象常用操作
 * @author xing.zeng
 * create time :2016年4月13日
 */
public class ConvertUtils {
	/**
	 * 校验字符串是否存在字符数组
	 * add by xzeng 2016年4月13日
	 * @param str
	 * @param source
	 * @return
	 */
	public static boolean isIn(String str, String[] source) {
		if (source == null || source.length == 0) {
			return false;
		}
		for (int i = 0; i < source.length; i++) {
			String aSource = source[i];
			if (aSource.equals(str)) {
				return true;
			}
		}
		return false;
	}
}
