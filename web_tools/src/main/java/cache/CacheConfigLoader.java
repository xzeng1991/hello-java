package cache;

import java.util.List;

public class CacheConfigLoader {
	private static CacheConfigLoader instance = null;
	private List<ServerAddress> serverAddressList;

	public static CacheConfigLoader newInstance() {
		if (instance == null) {
			instance = new CacheConfigLoader();
		}
		return instance;
	}

	public List<ServerAddress> getServerAddressList() {
		return this.serverAddressList;
	}

	public void setServerAddressList(List<ServerAddress> fnAddressList) {
		this.serverAddressList = fnAddressList;
	}

	public void initCacheServer(CachePool cachePool) {
		ICacheConfig cacheRouter = CacheConfigFactory.getCacheRouter(cachePool
				.getRouter());
		List serverAddressList = cacheRouter.getAddressList(cachePool);
		if ((serverAddressList == null) || (serverAddressList.size() == 0)) {
			throw new CacheConfigException("服务器地址不能为空，请检测配置.");
		}
		setServerAddressList(serverAddressList);
	}

	public List<ServerAddress> getServerAddressList(CachePool connectionPool) {
		return getServerAddressList();
	}
}