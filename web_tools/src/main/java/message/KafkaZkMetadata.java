package message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * kafka zookeeper信息
 * @author xing.zeng
 * create time :2016年5月17日
 */
public class KafkaZkMetadata implements Watcher {
	private static final Logger log = LoggerFactory.getLogger(KafkaZkMetadata.class);
	
	private String zkList;	// zk IP列表
	private final String brokerIdPath = "/brokers/ids";
	private final int SESSION_TIMEOUT = 60000;
	private ZooKeeper zk;
	// 同步
	private CountDownLatch connectedSignal = new CountDownLatch(1);
	
	public KafkaZkMetadata(String zkList) {
		this.zkList = zkList;
		connect(this.zkList);
	}
	
	/**
	 * 初始化zookeeper
	 * add by xzeng 2016年5月17日
	 * @param zkList
	 */
	public void connect(String zkList) {
		if (StringUtils.isEmpty(zkList))
			throw new KafkaClientException("zkList must not be empty");
		
		try {
			this.zk = new ZooKeeper(zkList, SESSION_TIMEOUT, this);
			// 多线程同步操作
			this.connectedSignal.await();
			log.info("connected to zookeeper : " + zkList);
		} catch (IOException e) {
			log.error("connect zookeeper server error! zkList:" + zkList);
			throw new KafkaClientException("connect zookeeper server error!", e);
		} catch (InterruptedException e) {
			log.error("connect zookeeper server error! zkList:" + zkList);
			throw new KafkaClientException("connect zookeeper server error!", e);
		}
	}
	
	public void close() throws InterruptedException {
		this.zk.close();
	}
	
	/**
	 * 从zookeeper中获取brokerIP列表
	 * add by xzeng 2016年5月17日
	 * @return
	 */
	private List<String> getBrokerList() {
		List<String> brokerList = null;
		try {
			List<String> children = this.zk.getChildren(brokerIdPath, false);
			if (children.isEmpty()) {
				log.warn("No members under {}", brokerIdPath);
				return null;
			}

			brokerList = new ArrayList<String>(children.size());
			for (String child : children) {
				byte[] byteArray = this.zk.getData(brokerIdPath + "/" + child.trim(), null, null);
				// 解析broker地址
				String brokerAddress = parseBrokerAddress(new String(byteArray));
				brokerList.add(brokerAddress);
			}
		} catch (KeeperException.NoNodeException e) {
			log.error("Path does not Exist, {}", brokerIdPath);
			throw new KafkaClientException(e);
		} catch (KeeperException e) {
			log.error("get broker metadata error!" + e.getMessage());
			throw new KafkaClientException(e);
		} catch (InterruptedException e) {
			log.error("get broker metadata error!" + e.getMessage());
			throw new KafkaClientException(e);
		}

		return brokerList;
	}
	
	/**
	 * 组装broker IP 列表信息
	 * add by xzeng 2016年5月17日
	 * @return
	 */
	public String getBrokerListString() {
		StringBuilder sb = new StringBuilder();

		List<String> brokerList = getBrokerList();

		if ((brokerList != null) && (brokerList.size() > 0)) {
			for (int i = 0; i < brokerList.size(); ++i) {
				if (i < brokerList.size() - 1)
					sb.append(brokerList.get(i) + ",");
				else {
					sb.append(brokerList.get(i));
				}
			}
		}

		return sb.toString();
	}
	
	/**
	 * 解析brokerIP地址
	 * add by xzeng 2016年5月17日
	 * @param origMetadata
	 * @return
	 */
	private String parseBrokerAddress(String origMetadata) {
		String host = null;
		String port = null;

		String[] items = origMetadata.split(",");
		for (String item : items) {
			if (item.contains("host")) {
				String[] keyValue = item.split(":");
				host = keyValue[1].replace("\"", "");
			}
			if (item.contains("port")) {
				String[] keyValue = item.split(":");
				port = keyValue[1].replace("}", "");
			}
		}

		if ((host != null) && (port != null)) {
				log.debug(host + ":" + port);
			return host + ":" + port;
		}
		return null;
	}
	
	@Override
	public void process(WatchedEvent event) {
		if (event.getState() == Watcher.Event.KeeperState.SyncConnected)
			this.connectedSignal.countDown();
	}

}
