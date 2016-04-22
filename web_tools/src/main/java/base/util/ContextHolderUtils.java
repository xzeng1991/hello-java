package base.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * HTTP 上下文信息
 * @author xing.zeng
 * create time :2016年4月18日
 */
public class ContextHolderUtils {
	/**
	 * 获取HTTP request
	 * add by xzeng 2016年4月18日
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
	
	/**
	 * 获取HTTP session
	 * add by xzeng 2016年4月18日
	 * @return
	 */
	public static HttpSession getSession() {
		HttpSession session = getRequest().getSession();
		return session;
	}
}
