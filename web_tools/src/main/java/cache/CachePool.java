package cache;

public class CachePool {
	private String cache = "memcached";

	private static String ROUTER_STATIC = "static";

	private static String ROUTER_DYNAMIC = "dynamic";
	private String servers;
	private String zkAddress;
	private String appName;
	private String weights;
	private String initConn = "10";

	private String minConn = "5";

	private String maxConn = "50";

	public String getZkAddress() {
		return this.zkAddress;
	}

	public void setZkAddress(String zkAddress) {
		this.zkAddress = zkAddress;
	}

	public String getAppName() {
		return this.appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getServers() {
		return this.servers;
	}

	public void setServers(String servers) {
		this.servers = servers;
	}

	public String getWeights() {
		return this.weights;
	}

	public void setWeights(String weights) {
		this.weights = weights;
	}

	public String getInitConn() {
		return this.initConn;
	}

	public void setInitConn(String initConn) {
		this.initConn = initConn;
	}

	public String getMinConn() {
		return this.minConn;
	}

	public void setMinConn(String minConn) {
		this.minConn = minConn;
	}

	public String getMaxConn() {
		return this.maxConn;
	}

	public void setMaxConn(String maxConn) {
		this.maxConn = maxConn;
	}

	public String getCache() {
		return this.cache;
	}

	public void setCache(String cache) {
		this.cache = cache;
	}

	public String getRouter() {
		if ((this.servers != null) && (this.servers.length() > 0))
			return ROUTER_STATIC;
		if ((this.zkAddress != null) && (this.zkAddress.length() > 0)) {
			return ROUTER_DYNAMIC;
		}
		throw new CacheConfigException("servers/zkAddress不能同时为空.");
	}

	public void setRouter(String router) {
	}
}