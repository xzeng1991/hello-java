package message;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestKafkaMetadata {
	private static final Logger log = LoggerFactory.getLogger(TestKafkaMetadata.class);
	
	@Test
	public void testGetBrokerListString(){
		String zkList = "10.202.176.61:2181,10.202.176.62:2181,10.202.176.63:2181";
		KafkaZkMetadata kafkaMetadata = new KafkaZkMetadata(zkList);
		
		String brokerList = kafkaMetadata.getBrokerListString();
		log.info("List:{}", brokerList);
	}
}
