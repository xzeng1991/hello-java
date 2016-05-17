package message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import com.feiniu.kafka.client.exception.KafkaClientException;
import com.feiniu.kafka.client.utils.KafkaZkMetadata;

public class ProducerClient<K, V> {
	private String brokerList;
	private String zkList;
	private Properties props;
	private String topic;
	private KafkaZkMetadata kafkaMetadata;
	private Producer<K, V> producer;

	public ProducerClient(Properties props) {
		this.props = props;
		ProducerConfig config = new ProducerConfig(this.props);
		this.producer = new Producer<K,V>(config);
	}

	public ProducerClient(String zkList, Properties props) {
		this.zkList = zkList;

		this.kafkaMetadata = new KafkaZkMetadata(this.zkList);
		this.brokerList = this.kafkaMetadata.getBrokerListString();

		this.props = props;
		this.props.put("metadata.broker.list", this.brokerList);
		ProducerConfig config = new ProducerConfig(this.props);
		this.producer = new Producer<K,V>(config);
	}

	public ProducerClient(String zkList, String topic, Properties props) {
		this.zkList = zkList;
		this.topic = topic;

		this.kafkaMetadata = new KafkaZkMetadata(this.zkList);
		this.brokerList = this.kafkaMetadata.getBrokerListString();

		this.props = props;
		this.props.put("metadata.broker.list", this.brokerList);
		ProducerConfig config = new ProducerConfig(this.props);
		this.producer = new Producer<K,V>(config);
	}

	public void sendMessage(String topic, V message) {
		if (StringUtils.isEmpty(topic)) {
			throw new KafkaClientException("topic must not be empty");
		}
		KeyedMessage<K,V> kMsg = new KeyedMessage<K,V>(topic, message);
		this.producer.send(kMsg);
	}

	/**
	 * 往TOPIC下发送消息
	 * add by xzeng 2016年5月17日
	 * @param topic
	 * @param key
	 * @param message
	 */
	public void sendMessage(String topic, K key, V message) {
		if (StringUtils.isEmpty(topic)) {
			throw new KafkaClientException("topic must not be empty");
		}
		
		KeyedMessage<K,V> kMsg = new KeyedMessage<K,V>(topic, key, message);
		this.producer.send(kMsg);
	}

	public void sendMessage(String topic, List<V> messages) {
		if (StringUtils.isEmpty(topic)) {
			throw new KafkaClientException("topic must not be empty");
		}

		if ((messages == null) || (messages.size() == 0)) {
			throw new KafkaClientException("messages list must not be empty");
		}
		
		List<KeyedMessage<K,V>> kMsgList = new ArrayList<KeyedMessage<K,V>>(messages.size());
		for (Iterator i$ = messages.iterator(); i$.hasNext();) {
			Object msg = i$.next();
			kMsgList.add(new KeyedMessage(topic, msg));
		}

		this.producer.send(kMsgList);
	}

	public void sendMessage(V message) {
		if (StringUtils.isEmpty(topic)) {
			throw new KafkaClientException("topic must not be empty");
		}
		
		KeyedMessage<K,V> kMsg = new KeyedMessage<K,V>(topic, message);
		this.producer.send(kMsg);
	}

	public void sendMessage(List<V> messages) {
		if (StringUtils.isEmpty(topic)) {
			throw new KafkaClientException("topic must not be empty");
		}

		if ((messages == null) || (messages.size() == 0)) {
			throw new KafkaClientException("messages list must not be empty");
		}
		
		List kMsgList = new ArrayList(messages.size());
		for (Iterator i$ = messages.iterator(); i$.hasNext();) {
			Object msg = i$.next();
			kMsgList.add(new KeyedMessage(this.topic, msg));
		}

		this.producer.send(kMsgList);
	}

	public void close() {
		if (this.producer != null)
			this.producer.close();
	}

	public String getBrokerList() {
		return brokerList;
	}

	public void setBrokerList(String brokerList) {
		this.brokerList = brokerList;
	}

	public String getZkList() {
		return zkList;
	}

	public void setZkList(String zkList) {
		this.zkList = zkList;
	}

	public Properties getProps() {
		return props;
	}

	public void setProps(Properties props) {
		this.props = props;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public KafkaZkMetadata getKafkaMetadata() {
		return kafkaMetadata;
	}

	public void setKafkaMetadata(KafkaZkMetadata kafkaMetadata) {
		this.kafkaMetadata = kafkaMetadata;
	}

	public Producer<K, V> getProducer() {
		return producer;
	}

	public void setProducer(Producer<K, V> producer) {
		this.producer = producer;
	}

}
