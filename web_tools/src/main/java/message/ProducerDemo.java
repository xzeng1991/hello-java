package message;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class ProducerDemo {
	public static void main(String[] args){
		Properties props = new Properties();
		props.put("bootstrap.servers", "10.202.176.61:9092,10.202.176.62:9092");
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		FcmProducerClient.sendMessage("", new MqMessagePoList() );
		
		Producer<String, String> producer = new KafkaProducer<>(props);
		for(int i = 0; i < 1; i++)
		    producer.send(new ProducerRecord<String, String>("DEV_DuanXin2", Integer.toString(i), Integer.toString(i)));

		producer.close();
	}
}
