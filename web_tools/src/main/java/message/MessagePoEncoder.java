package message;

import java.io.IOException;
import java.util.Properties;

import org.apache.avro.io.DatumWriter;
import org.apache.avro.reflect.ReflectDatumWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;

/**
 * 消息序列化类
 * @author xing.zeng
 * create time :2016年5月17日
 */
public class MessagePoEncoder implements Encoder<MqMessagePo> {
	private static final Logger logger = LoggerFactory.getLogger(MessagePoEncoder.class);
	private final AvroSerializer<MqMessagePo> avroSerializer = new AvroSerializer<MqMessagePo>();
	private final DatumWriter<MqMessagePo> writer = new ReflectDatumWriter<MqMessagePo>(MqMessagePo.class);
	
	public MessagePoEncoder(){
		
	}
	
	public MessagePoEncoder(VerifiableProperties verifiableProperties){
		if(verifiableProperties == null) {
			Properties props = new Properties();
			props.put("serializer.encoding", "UTF-8");
			verifiableProperties = new VerifiableProperties(props);
		}
	}
	
	@Override
	public byte[] toBytes(MqMessagePo mqPo) {
		try {
			return avroSerializer.serialize(mqPo, writer);
		} catch (IOException e) {
			logger.error("Failed to encode user: " + e);
		}
		return null;
	}

}
