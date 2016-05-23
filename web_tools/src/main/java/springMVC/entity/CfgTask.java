package springMVC.entity;

import java.sql.Timestamp;
/**
 * 任务实体
 * @author xing.zeng
 * create time :2016年5月23日
 */
public class CfgTask {
	private Long cfgTaskId;	// 任务ID
	private String taskName;	// 任务名称
	private String cfgTaskTypeCode;	// 任务code
	private String triggerType;	// Corn or Simple
	private String triggerExpr;	// 定时表达式
	private long pripority;	// 优先级
	private String createUser;	// 创建人
	private String taskState;	// 任务状态
	private Timestamp createDate = new Timestamp(System.currentTimeMillis());
	private String remarks;	// 备注
	private Timestamp startDate = new Timestamp(System.currentTimeMillis());
	private Timestamp endDate = new Timestamp(System.currentTimeMillis());
	private String jobType;	// 任务类型
	private String jobExpr;	// 任务类
	private String taskGroup;	// 任务组
	private String taskParams;	// 任务参数

	public Long getCfgTaskId() {
		return cfgTaskId;
	}

	public void setCfgTaskId(Long cfgTaskId) {
		this.cfgTaskId = cfgTaskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getCfgTaskTypeCode() {
		return cfgTaskTypeCode;
	}

	public void setCfgTaskTypeCode(String cfgTaskTypeCode) {
		this.cfgTaskTypeCode = cfgTaskTypeCode;
	}

	public String getTriggerType() {
		return triggerType;
	}

	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}

	public String getTriggerExpr() {
		return triggerExpr;
	}

	public void setTriggerExpr(String triggerExpr) {
		this.triggerExpr = triggerExpr;
	}

	public long getPripority() {
		return pripority;
	}

	public void setPripority(long pripority) {
		this.pripority = pripority;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getTaskState() {
		return taskState;
	}

	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getJobExpr() {
		return jobExpr;
	}

	public void setJobExpr(String jobExpr) {
		this.jobExpr = jobExpr;
	}

	public String getTaskGroup() {
		return taskGroup;
	}

	public void setTaskGroup(String taskGroup) {
		this.taskGroup = taskGroup;
	}

	public String getTaskParams() {
		return taskParams;
	}

	public void setTaskParams(String taskParams) {
		this.taskParams = taskParams;
	}

}
