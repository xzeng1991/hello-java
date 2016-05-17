package message;

import java.util.Properties;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestProducerClient {
	private static final Logger log = LoggerFactory.getLogger(TestProducerClient.class);
	
	@Test
	public void testSendMessage(){
		String zkList = "10.202.176.61:2181,10.202.176.62:2181,10.202.176.63:2181";
		
		Properties props = new Properties();
		props.put("key.serializer.class", "kafka.serializer.StringEncoder");
		props.put("serializer.class", "message.MessagePoEncoder");
		props.put("request.required.acks", "1");
		props.put("zookeeper.connection.timeout.ms", "100000");
		props.put("producer.type", "async");
		props.put("batch.num.messages", "500");
		
		ProducerClient producerClient = new ProducerClient(zkList, props);
		MqMessagePo po = new MqMessagePo();
		po.setId("111111");
		po.setTitle("Test Title");
		po.setReceiver("xzeng");
		po.setMessageContent("some thing test");
		
		producerClient.sendMessage("DEV_DuanXin5", String.valueOf(System.currentTimeMillis()), po);
	}
}
