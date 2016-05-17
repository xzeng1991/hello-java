package message;

import java.io.Serializable;

public class MqMessagePo implements Serializable {
	private static final long serialVersionUID = 755813093972050812L;

	private String id; // id
	private String title = ""; // 信息标题
	private String receiver = ""; // 接受者
	private String messageContent = ""; // 消息内容
	// add by xzeng 20151030 邮件附件
	private String attachmentJson = "";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public String getAttachmentJson() {
		return attachmentJson;
	}

	public void setAttachmentJson(String attachmentJson) {
		this.attachmentJson = attachmentJson;
	}
}
