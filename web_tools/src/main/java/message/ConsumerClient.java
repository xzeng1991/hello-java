package message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feiniu.kafka.client.KafkaStreamHandler;
import com.feiniu.kafka.client.exception.KafkaClientException;

public class ConsumerClient {
	private static final Logger logger = LoggerFactory.getLogger(ConsumerClient.class);

	private String topic;
	private String group;
	private String zkList;
	private Properties props;
	private KafkaStreamHandler kafkaStreamHandler;
	private ConsumerConnector consumerConnector;
	private KafkaStream<byte[], byte[]> kafkaStream;
	private List<KafkaStream<byte[], byte[]>> kafkaStreamList;
	private ExecutorService executor;

	public ConsumerClient(String zkList, String group, Properties props) {
		this.zkList = zkList;
		if ((this.zkList == null) || (this.zkList.length() == 0)) {
			throw new KafkaClientException("zkList must not be empty");
		}
		this.group = group;
		if ((this.group == null) || (this.group.length() == 0)) {
			throw new KafkaClientException("group must not be empty");
		}

		this.props = props;
		this.props.put("zookeeper.connect", zkList);
		this.props.put("group.id", group);

		ConsumerConfig config = new ConsumerConfig(this.props);
		this.consumerConnector = Consumer.createJavaConsumerConnector(config);
	}

	public ConsumerClient(String zkList, String group, Properties props, String topic,
			KafkaStreamHandler kafkaStreamHandler) {
		this.zkList = zkList;
		if ((this.zkList == null) || (this.zkList.length() == 0)) {
			throw new KafkaClientException("zkList must not be empty");
		}

		this.group = group;
		if ((this.group == null) || (this.group.length() == 0)) {
			throw new KafkaClientException("group must not be empty");
		}

		this.props = props;
		this.props.put("zookeeper.connect", zkList);
		this.props.put("group.id", group);

		ConsumerConfig config = new ConsumerConfig(this.props);
		this.consumerConnector = Consumer.createJavaConsumerConnector(config);

		this.topic = topic;
		if ((this.topic == null) || (this.topic.length() == 0)) {
			throw new KafkaClientException("topic must not be empty");
		}

		this.kafkaStreamHandler = kafkaStreamHandler;
		if (this.kafkaStreamHandler == null)
			throw new KafkaClientException("KafkaStreamHandler Impl must not be null");
	}

	public void run() {
		run(1);
	}

	public void run(int numThread)
  {
    if (numThread <= 0) {
      throw new KafkaClientException("ThreadPool size must greater than 0");
    }
    if ((this.topic == null) || (this.topic.length() == 0)) {
      throw new KafkaClientException("topic must not be empty");
    }

    logger.info("Kafka StreamHandler with " + numThread + " Thread starting...");
    Map topicCountMap = new HashMap();
    topicCountMap.put(this.topic, new Integer(numThread));
    Map consumerMap = this.consumerConnector.createMessageStreams(topicCountMap);
    this.kafkaStreamList = ((List)consumerMap.get(this.topic));

    this.executor = Executors.newFixedThreadPool(numThread);
    if (this.kafkaStreamHandler != null) {
      for (KafkaStream stream : this.kafkaStreamList) {
       // this.executor.submit(new 1(this, stream));
      }

    }
    else
    {
      throw new KafkaClientException("kafkaStreamHandler must not be null");
    }
  }

	public void consume(int n) {
		run(n);
	}

	public void consume() {
		run(1);
	}

	public void shutdown() {
		if (this.consumerConnector != null) {
			this.consumerConnector.shutdown();
		}
		if (this.executor != null)
			this.executor.shutdown();
	}

	public void close() {
		shutdown();
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
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

	public KafkaStreamHandler getKafkaStreamHandler() {
		return kafkaStreamHandler;
	}

	public void setKafkaStreamHandler(KafkaStreamHandler kafkaStreamHandler) {
		this.kafkaStreamHandler = kafkaStreamHandler;
	}

	public ConsumerConnector getConsumerConnector() {
		return consumerConnector;
	}

	public void setConsumerConnector(ConsumerConnector consumerConnector) {
		this.consumerConnector = consumerConnector;
	}

	public KafkaStream<byte[], byte[]> getKafkaStream() {
		return kafkaStream;
	}

	public void setKafkaStream(KafkaStream<byte[], byte[]> kafkaStream) {
		this.kafkaStream = kafkaStream;
	}

	public List<KafkaStream<byte[], byte[]>> getKafkaStreamList() {
		return kafkaStreamList;
	}

	public void setKafkaStreamList(List<KafkaStream<byte[], byte[]>> kafkaStreamList) {
		this.kafkaStreamList = kafkaStreamList;
	}

	public ExecutorService getExecutor() {
		return executor;
	}

	public void setExecutor(ExecutorService executor) {
		this.executor = executor;
	}

}
