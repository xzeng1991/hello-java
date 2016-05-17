package message;

import java.util.ArrayList;
import java.util.List;

import kafka.admin.TopicCommand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feiniu.kafka.client.exception.KafkaClientException;


public class TopicClient {
	private static final Logger log = LoggerFactory.getLogger(TopicClient.class);
	private String topic;
	private String zkList;
	private Integer factor = Integer.valueOf(1);

	private Integer partition = Integer.valueOf(1);
	private List<String> command;

	public TopicClient() {
	}

	public TopicClient(String zkList) {
		this.zkList = zkList;

		if ((this.zkList == null) || (this.zkList.length() <= 0))
			throw new KafkaClientException("zkList must not be empty!");
	}

	public TopicClient(String zkList, Integer factor, Integer partition) {
		this.zkList = zkList;
		this.factor = factor;
		this.partition = partition;

		if ((this.zkList == null) || (this.zkList.length() <= 0))
			throw new KafkaClientException("zkList must not be empty!");
	}

	public void createTopic(String topic) {
		this.command = new ArrayList();
		this.command.add("--create");

		this.topic = topic;
		if ((this.topic != null) && (this.topic.length() > 0)
				&& (this.topic.length() <= 255)) {
			this.command.add("--topic");
			this.command.add(this.topic);
		} else {
			throw new KafkaClientException("topic length must be [1 - 255]");
		}

		if ((this.zkList != null) && (this.zkList.length() > 0)) {
			this.command.add("--zookeeper");
			this.command.add(this.zkList);
		} else {
			throw new KafkaClientException("zkList must not be empty!");
		}

		if (this.factor.intValue() <= 0) {
			this.factor = Integer.valueOf(1);
		}
		this.command.add("--replication-factor");
		this.command.add(this.factor.toString());

		if (this.partition.intValue() <= 0) {
			this.partition = Integer.valueOf(1);
		}
		this.command.add("--partitions");
		this.command.add(this.partition.toString());

		TopicCommand.main((String[]) this.command
				.toArray(new String[this.command.size()]));
		log.info("Topic [" + this.topic + "] created");
	}

	public String getTopic() {
		return this.topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getZkList() {
		return this.zkList;
	}

	public void setZkList(String zkList) {
		this.zkList = zkList;
	}

	public Integer getFactor() {
		return this.factor;
	}

	public void setFactor(Integer factor) {
		this.factor = factor;
	}

	public Integer getPartition() {
		return this.partition;
	}

	public void setPartition(Integer partition) {
		this.partition = partition;
	}
}
