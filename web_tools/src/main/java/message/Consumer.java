package message;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.feiniu.kafka.client.ConsumerClient;

public class Consumer {
	public static void main(String[] args) {
		Properties props = new Properties();
		props.put("bootstrap.servers", "10.202.176.61:9092,10.202.176.62:9092");
		props.put("group.id", "FCM-TEST");
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
		ConsumerClient consumerMsg = new ConsumerClient("", "", props);
		consumer.subscribe(Arrays.asList("DEV_DuanXin2"));
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(100);
			for (ConsumerRecord<String, String> record : records)
				System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());
		}
	}
}
