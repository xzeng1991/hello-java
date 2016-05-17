package message;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

public class MqMessagePoList implements Serializable {
	private static final long serialVersionUID = 6186004639217058113L;

	private String receiveChannel = ""; // 消息接收渠道("1"短信/"2"邮件/"3"站内信/"4"哞哞/"5"Android/"6"IOS/"7"微博/"8"微信/"9"QQ)
	private String receiveChannelType = ""; // 消息渠道类型("0":营销类；"1":非营销类；"2":通用类；[空]:非限定)
	private int sendMsgType = 0; // 0：单条 1：分组批量 2：单条批量 3:应用 4:邮件抄送
	private BigInteger taskId; // 发送消息组 对应一个组别
	private Integer messageNum; // 消息总数
	private Integer filterNum;// 过滤掉的数目
	private String sendMsgEndTime = ""; // 消息发送结束时间
	private List<MqMessagePo> msgList; // 消息实体集合
	private String tags = "";// 标签
	private String districts = "";// 区域
	private Integer offlineExpireTime = 0;// 离线过期时间
	private String url = "";// 透传的url
	private String wxAccount = ""; // 微信账号
	private String wxType = ""; // 微信消息类型
	private String serviceType = "";
	private String wxMediaId = "";
	private String wxMaterialType = "";
	private String badge = "";
	private String pushProvider = "";

	public String getReceiveChannel() {
		return receiveChannel;
	}

	public void setReceiveChannel(String receiveChannel) {
		this.receiveChannel = receiveChannel;
	}

	public String getReceiveChannelType() {
		return receiveChannelType;
	}

	public void setReceiveChannelType(String receiveChannelType) {
		this.receiveChannelType = receiveChannelType;
	}

	public int getSendMsgType() {
		return sendMsgType;
	}

	public void setSendMsgType(int sendMsgType) {
		this.sendMsgType = sendMsgType;
	}

	public BigInteger getTaskId() {
		return taskId;
	}

	public void setTaskId(BigInteger taskId) {
		this.taskId = taskId;
	}

	public Integer getMessageNum() {
		return messageNum;
	}

	public void setMessageNum(Integer messageNum) {
		this.messageNum = messageNum;
	}

	public Integer getFilterNum() {
		return filterNum;
	}

	public void setFilterNum(Integer filterNum) {
		this.filterNum = filterNum;
	}

	public String getSendMsgEndTime() {
		return sendMsgEndTime;
	}

	public void setSendMsgEndTime(String sendMsgEndTime) {
		this.sendMsgEndTime = sendMsgEndTime;
	}

	public List<MqMessagePo> getMsgList() {
		return msgList;
	}

	public void setMsgList(List<MqMessagePo> msgList) {
		this.msgList = msgList;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getDistricts() {
		return districts;
	}

	public void setDistricts(String districts) {
		this.districts = districts;
	}

	public Integer getOfflineExpireTime() {
		return offlineExpireTime;
	}

	public void setOfflineExpireTime(Integer offlineExpireTime) {
		this.offlineExpireTime = offlineExpireTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getWxAccount() {
		return wxAccount;
	}

	public void setWxAccount(String wxAccount) {
		this.wxAccount = wxAccount;
	}

	public String getWxType() {
		return wxType;
	}

	public void setWxType(String wxType) {
		this.wxType = wxType;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getWxMediaId() {
		return wxMediaId;
	}

	public void setWxMediaId(String wxMediaId) {
		this.wxMediaId = wxMediaId;
	}

	public String getWxMaterialType() {
		return wxMaterialType;
	}

	public void setWxMaterialType(String wxMaterialType) {
		this.wxMaterialType = wxMaterialType;
	}

	public String getBadge() {
		return badge;
	}

	public void setBadge(String badge) {
		this.badge = badge;
	}

	public String getPushProvider() {
		return pushProvider;
	}

	public void setPushProvider(String pushProvider) {
		this.pushProvider = pushProvider;
	}
}
