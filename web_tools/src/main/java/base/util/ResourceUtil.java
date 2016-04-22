package base.util;

import javax.servlet.http.HttpSession;

import base.entity.SecUser;

public class ResourceUtil {
	/**
	 * 获取登录用户信息
	 * add by xzeng 2016年4月18日
	 * @return
	 */
	public static final SecUser getSessionUserName() {
		HttpSession session = ContextHolderUtils.getSession();
		if(session != null){
			/*if(ClientManager.getInstance().getClient(session.getId())!=null){
				return ClientManager.getInstance().getClient(session.getId()).getUser();
			}*/
			SecUser user = new SecUser();
			user.setUserName("admin");
			return user;
		}
		return null;
	}
}
