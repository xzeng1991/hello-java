package cache;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Zookeeper2CacheConfig extends AbstractZookeeperCacheConfig implements ICacheConfig {
	private static final Logger logger = LoggerFactory.getLogger(Zookeeper2CacheConfig.class);
	private static final String APP_ROOT = "/sweed/apps";

	public List<ServerAddress> getAddressList(CachePool pool) {
		List srvAddresslist = new ArrayList();

		initZk(pool);

		String path = "/sweed/apps/" + pool.getAppName();
		boolean isExist = this.zkClient.exists(path);
		if (!(isExist)) {
			throw new CacheConfigException("appName[" + pool.getAppName() + "]配置不存在，请检查配置.");
		}

		List shardNodes = this.zkClient.getChildren(path);
		if ((shardNodes == null) || (shardNodes.size() == 0)) {
			throw new CacheConfigException("shard配置不能为空.");
		}

		for (Object shardNode : shardNodes) {
			logger.info("shard:" + shardNode);
			List nodes = this.zkClient.getChildren(path + "/" + shardNode + "/instances");
			if ((nodes == null) || (nodes.size() == 0)) {
				throw new CacheConfigException("node不能为空.");
			}
			for (Object node : nodes) {
				boolean isMaster = true;
				logger.info("node:" + node);

				List nodeAttrs = this.zkClient.getChildren(path + "/" + shardNode + "/instances/" + node);
				for (Object nodeAttr : nodeAttrs) {
					Object nodeAttrValue = this.zkClient.readData(path + "/" + shardNode + "/instances/" + node + "/"
							+ nodeAttr);
					logger.info("nodeAttr[" + nodeAttr + "]:" + nodeAttrValue);
					if ((!("ismaster".equalsIgnoreCase(String.valueOf(nodeAttr))))
							|| (!("0".equalsIgnoreCase(String.valueOf(nodeAttrValue)))))
						continue;
					isMaster = false;
				}

				if (isMaster) {
					ServerAddress srvAddress = parseNode((String)node);
					srvAddresslist.add(srvAddress);
				}
			}
			logger.info("------------------\n\n");
		}
		return srvAddresslist;
	}

	ServerAddress parseNode(String node) {
		ServerAddress srvAddress = new ServerAddress();

		String[] arr = node.split("_");
		if ((arr == null) || (arr.length != 2)) {
			throw new CacheConfigException("node命名不符合规则.");
		}
		String host = arr[0];
		String port = arr[1];
		srvAddress.setHost(host);
		srvAddress.setPort(Integer.parseInt(port));
		return srvAddress;
	}

	public static void main(String[] args) {
		CachePool pool = new CachePool();
		pool.setZkAddress("10.202.249.175:2181");
		pool.setAppName("weixin_dev");
		new Zookeeper2CacheConfig().getAddressList(pool);
	}
}