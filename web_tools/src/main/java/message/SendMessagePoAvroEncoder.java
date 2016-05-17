package message;

import java.io.IOException;
import java.util.Properties;

import org.apache.avro.io.DatumWriter;
import org.apache.avro.reflect.ReflectDatumWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;

public class SendMessagePoAvroEncoder implements Encoder<MqMessagePoList>{
	private static final Logger logger = LoggerFactory.getLogger(SendMessagePoAvroEncoder.class);
	private final AvroSerializer<MqMessagePoList> avroSerializer = new AvroSerializer<MqMessagePoList>();
	private final DatumWriter<MqMessagePoList> writer = new ReflectDatumWriter<MqMessagePoList>(MqMessagePoList.class);
	
	public SendMessagePoAvroEncoder(){}
	
	public SendMessagePoAvroEncoder(VerifiableProperties verifiableProperties){
		if(verifiableProperties == null) {
			Properties props = new Properties();
			props.put("serializer.encoding", "UTF-8");
			verifiableProperties = new VerifiableProperties(props);
		}
	}
	
	@Override
	public byte[] toBytes(MqMessagePoList msgList) {
		try {
			return avroSerializer.serialize(msgList, writer);
		} catch (IOException e) {
			logger.error("Failed to encode user: " + e);
		}
		return null;
	}

}
