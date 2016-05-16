package base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理常用操作
 * @author xing.zeng
 * create time :2016年4月22日
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils{
	private static final int DAY_MILLS = 24 * 60 * 60 * 1000;	// 一天毫秒数
	
	private static final SimpleDateFormat SDF_DATETIME_STYLE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat SDF_DATETIME_S = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
	private static final SimpleDateFormat SDF_DATETIME_E = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
	private static final SimpleDateFormat SDF_DATETIME_STYLE1 = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final SimpleDateFormat SDF_DATE_STYLE = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat SDF_TIME_STYLE = new SimpleDateFormat("HH:mm:ss");
	private static final SimpleDateFormat SDF_DATE_STYLE1 = new SimpleDateFormat("yyyyMMdd");
	private static final SimpleDateFormat SDF_DATE_STYLE2 = new SimpleDateFormat("yyyy/MM/dd");
	
	/**
	 * 日期转化为字符格式：yyyy-MM-dd HH:mm:ss
	 * add by xzeng 2016年4月22日
	 * @param date
	 * @return
	 */
	public static String formatDatetime(Date date) {
		return SDF_DATETIME_STYLE.format(date);
	}
	
	/**
	 * 字符转化为日期，格式：yyyy-MM-dd HH:mm:ss
	 * add by xzeng 2016年4月22日
	 * @param strDate
	 * @return
	 */
	public static Date parseDatetime(String strDate) {
		try {
			return SDF_DATETIME_STYLE.parse(strDate);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 字符转化为日期，格式：yyyy-MM-dd 00:00:00
	 * add by xzeng 2016年4月22日
	 * @param strDate
	 * @return
	 */
	public static Date parseDatetimeStart(String strDate) {
		try {
			return SDF_DATETIME_S.parse(strDate);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 日期转化为字符，格式：yyyy-MM-dd 00:00:00
	 * add by xzeng 2016年4月22日
	 * @param date
	 * @return
	 */
	public static String formatDatetimeStart(Date date) {
		return SDF_DATETIME_S.format(date);
	}
	
	/**
	 * 日期转化为字符串，格式：yyyy-MM-dd 23:59:59
	 * add by xzeng 2016年4月22日
	 * @param date
	 * @return
	 */
	public static String formatDatetimeEnd(Date date) {
		return SDF_DATETIME_E.format(date);
	}
	
	/**
	 * 获取昨天日期
	 * add by xzeng 2016年4月22日
	 * @param date
	 * @return
	 */
	public static Date getYesterday(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar.getTime();
	}
	
	/**
	 * 设置一天的开始
	 * add by xzeng 2016年4月22日
	 * @param calendar
	 */
	private static void setStartOfDay(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}

	/**
	 * 设置一天的结束
	 * add by xzeng 2016年4月22日
	 * @param calendar
	 */
	private static void setEndOfDay(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
	}
	
	/**
	 * 获取上个月最后一天
	 * add by xzeng 2016年4月22日
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfLastMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar.getTime();
	}
	
	/**
	 * 格式化yyyyMMdd
	 * add by xzeng 2016年5月16日
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date){
		return SDF_DATE_STYLE1.format(date);
	}
}
