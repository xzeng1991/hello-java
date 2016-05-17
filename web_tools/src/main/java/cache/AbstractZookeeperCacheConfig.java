package cache;

import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractZookeeperCacheConfig implements ICacheConfig {
	private static final Logger logger = LoggerFactory
			.getLogger(AbstractZookeeperCacheConfig.class);
	protected ZkClient zkClient;

	public void initZk(CachePool pool) {
		if (this.zkClient != null)
			return;
		try {
			FnZkClient zkClient = new FnZkClient(parseZkAddress(pool));
			this.zkClient = zkClient;
		} catch (Exception ex) {
			logger.error("创建zkClient实例失败，错误信息:" + ex);
			if (this.zkClient == null)
				throw new CacheException("创建zkClient失败.");
		}
	}

	String parseZkAddress(CachePool pool) {
		String zkAddress = pool.getZkAddress();
		String[] zks = zkAddress.split(";");
		if ((zks != null) && (zks.length > 1)) {
			zkAddress = zkAddress.replaceAll(";", ",");
			logger.info("valid zkAddress:" + zkAddress);
		}
		return zkAddress;
	}

	void initZkClient(String zkAddress) {
	}
}
