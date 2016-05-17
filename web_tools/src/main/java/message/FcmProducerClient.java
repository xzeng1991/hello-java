package message;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FcmProducerClient implements Callable<String>{
	private static Logger log = LoggerFactory.getLogger(FcmProducerClient.class);
	
	private static final String corePoolSize = "10";
	private static final String maximumPoolSize = "100";
	private static final String keepAliveTime = "3";
	
	private static ThreadPoolExecutor threadPool = null;
	private static ProducerClient producer ;
	
	static {
		// 初始化线程池
		threadPool = new ThreadPoolExecutor(Integer.valueOf(corePoolSize), Integer.valueOf(maximumPoolSize),
				Long.valueOf(keepAliveTime), TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.CallerRunsPolicy());
		
		String zkList = "10.202.176.61:2181,10.202.176.62:2181,10.202.176.63:2181";
		Properties props = new Properties();
		props.put("key.serializer.class", "kafka.serializer.StringEncoder");
		props.put("serializer.class", "message.SendMessagePoAvroEncoder");
		props.put("request.required.acks", "1");
		props.put("zookeeper.connection.timeout.ms", "100000");
		props.put("producer.type", "async");
		props.put("batch.num.messages", "500");
		
		producer = new ProducerClient(zkList, props);
	}
	
	private String topic = null;
	private MqMessagePoList sendMessage = null;
	private List<MqMessagePo> list = null;
	
	private FcmProducerClient(String topic, MqMessagePoList sendMessage, List<MqMessagePo> list) {
		this.topic = topic;
		this.sendMessage = sendMessage;
		this.list = list;
	}
	
	public static void sendMessage(String topic, MqMessagePoList sendMessage) {
		threadPool.submit(new FcmProducerClient(topic, sendMessage, null));
	}
	
	public static void sendMessage(String topic, List<MqMessagePo> list) {
		threadPool.submit(new FcmProducerClient(topic, null, list));
	}
	
	@Override
	public String call() throws Exception {
		log.info("开始往MQ【" + topic + "】主题下发送消息");
		long start = System.currentTimeMillis();
		
		try{
			if (this.sendMessage != null) {
				producer.sendMessage(this.topic, String.valueOf(System.currentTimeMillis()), this.sendMessage);
			} else if (this.list != null) {
				producer.sendMessage(this.topic, String.valueOf(System.currentTimeMillis()), this.list);
			}
		}catch(Throwable e){
			log.error(e.getMessage(), e);
		}
		long end = System.currentTimeMillis();
		log.info("Signle >> Execute time(ms) : " + (end - start));
		log.info("往MQ【" + topic + "】主题下发送消息结束");
		return "";
	}

}
