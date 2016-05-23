package message;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
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
		props.put("serializer.class", "message.SendMessagePoAvroEncoder");
		//props.put("serializer.class", "kafka.serializer.StringEncoder");
		props.put("request.required.acks", "1");
		props.put("zookeeper.connection.timeout.ms", "100000");
		props.put("producer.type", "async");
		props.put("batch.num.messages", "500");
		
		//ProducerClient producerClient = new ProducerClient(zkList, props);
		MqMessagePoList mqMsgPoList = new MqMessagePoList();
		List<MqMessagePo> msgPoList = new ArrayList<MqMessagePo>();
		mqMsgPoList.setReceiveChannel("11111");
		mqMsgPoList.setReceiveChannelType("11");
		mqMsgPoList.setBadge("0");
		mqMsgPoList.setDistricts("222");
		mqMsgPoList.setFilterNum(0);
		mqMsgPoList.setMessageNum(1);
		mqMsgPoList.setOfflineExpireTime(2);
		mqMsgPoList.setPushProvider("qwer");
		mqMsgPoList.setReceiveChannel("fsadfa");
		mqMsgPoList.setReceiveChannelType("asdf");
		mqMsgPoList.setSendMsgEndTime("12345");
		mqMsgPoList.setSendMsgType(2);
		mqMsgPoList.setServiceType("tyui");
		mqMsgPoList.setTags("111");
		mqMsgPoList.setTaskId(new BigInteger("46846512"));
		mqMsgPoList.setUrl("www.ho123.com");
		mqMsgPoList.setWxAccount("12568");
		mqMsgPoList.setWxMaterialType("some thing");
		mqMsgPoList.setWxMediaId("12345");
		mqMsgPoList.setWxType("123");
		MqMessagePo msgPo = new MqMessagePo();
		msgPo.setId("12345");
		msgPo.setMessageContent("This is a message.");
		msgPo.setAttachmentJson("55");
		msgPo.setReceiver("hello w");
		msgPo.setTitle("tilte");
		msgPoList.add(msgPo);
		mqMsgPoList.setMsgList(msgPoList);
		//producerClient.sendMessage("DEV_DuanXin5", String.valueOf(System.currentTimeMillis()), "message");
		FcmProducerClient.sendMessage("DEV_DuanXin5", mqMsgPoList);
		
		while(true){}
	}
}
