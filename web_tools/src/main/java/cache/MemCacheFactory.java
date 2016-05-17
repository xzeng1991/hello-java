package cache;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemCacheFactory extends AbstractCacheFactory implements ICacheFactory {
	private static final Logger log = LoggerFactory.getLogger(MemCacheFactory.class);
	private static final String DEFAULT_MEM_CACHE_TYPE = "m3";
	private static final String MEM_CACHE_WHALIN = "m1";
	private static final String MEM_CACHE_SPY = "m2";
	private static final String MEM_CACHE_XMEM = "m3";

	public MemCacheFactory() {
	}

	public MemCacheFactory(String clientType) {
		super(clientType);
	}

	public ICache createCache(List<ServerAddress> addressList) {
		if (this.clientType == null) {
			this.clientType = "m3";
		}
		ICache cache = null;
		if ("m1".equals(this.clientType)) {
			cache = createXmemMemCache(addressList);
		} else if ("m2".equals(this.clientType)) {
			cache = createXmemMemCache(addressList);
		} else if ("m3".equals(this.clientType)) {
			cache = createXmemMemCache(addressList);
		} else {
			if (log.isDebugEnabled()) {
				log.debug("暂不支持memcached该[m3]客户端类型.");
			}
			throw new CacheException("暂不支持memcached该[m3]客户端类型.");
		}
		if (cache == null) {
			throw new CacheException("创建cache实例失败.");
		}
		return cache;
	}

	public ICache createXmemMemCache(List<ServerAddress> addressList) {
		try {
			if (addressList.size() == 1) {
				log.info("单机模式...");
				ServerAddress srvAddress = (ServerAddress) addressList.get(0);
				XMemcachedClient mec = new XMemcachedClient(srvAddress.getHost(), srvAddress.getPort());
				return new XMemcachedCache(mec);
			}
			if (addressList.size() > 1) {
				log.info("shard模式...");
				int clusterMode = 1;
				if (clusterMode == 1) {
					List list = new ArrayList();
					int[] weights = new int[addressList.size()];
					for (int i = 0; i < addressList.size(); ++i) {
						ServerAddress srvAddress = (ServerAddress) addressList.get(i);
						InetSocketAddress address = new InetSocketAddress(srvAddress.getHost(), srvAddress.getPort());
						list.add(address);
						weights[i] = 1;
					}
					MemcachedClientBuilder builder = new XMemcachedClientBuilder(list, weights);
					XMemcachedClient mec = (XMemcachedClient) builder.build();
					return new XMemcachedCache(mec);
				}
				if (clusterMode == 2) {
					List mecList = new ArrayList();
					int i = 0;
					while (true) {
						ServerAddress srvAddress = (ServerAddress) addressList.get(i);
						XMemcachedClient mecClient = new XMemcachedClient(srvAddress.getHost(), srvAddress.getPort());
						XMemcachedCache mec = new XMemcachedCache(mecClient);
						mecList.add(mec);

						++i;
						if (i >= addressList.size()) {
							label276: return new XMemcachedClusterCache(mecList);
						}
					}
				}
				throw new CacheException("不支持该集群模式.");
			}

			throw new CacheException("服务器地址不能为空.");
		} catch (IOException e) {
			if (log.isDebugEnabled()) {
				log.debug("创建xMemcachedClient错误.");
			}
			throw new CacheException("创建xMemcachedClient错误", e);
		}
	}
}