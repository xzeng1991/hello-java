package springMVC.entity;

import java.io.Serializable;
/**
 * 用户信息实体
 * @author xzeng
 * 2016年4月16日
 */
public class User implements Serializable{
	private static final long serialVersionUID = 6771531598352059454L;
	
	private String userName;
	private String userPass;
	private String realName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

}
