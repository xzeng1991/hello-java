package message;

import kafka.consumer.KafkaStream;

import com.feiniu.kafka.client.exception.KafkaClientException;

public abstract interface KafkaStreamHandler {
	public abstract void handle(KafkaStream<byte[], byte[]> paramKafkaStream)
			throws KafkaClientException;
}
