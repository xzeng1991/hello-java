package cache;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisCacheFactory extends AbstractCacheFactory implements
ICacheFactory {
private static final Logger log = LoggerFactory
	.getLogger(RedisCacheFactory.class);
private static final String DEFAULT_REDIS_CACHE_TYPE = "r1";
private static final String REDIS_CACHE_JEDIS = "r1";
private static final String REDIS_CACHE_OTHER = "r2";

public RedisCacheFactory() {
}

public RedisCacheFactory(String clientType) {
super(clientType);
}

public ICache createCache(List<ServerAddress> addressList) {
if (this.clientType == null) {
	this.clientType = "r1";
}
if ("r1".equals("r1"))
	return createJedisCache(addressList);
if ("r2".equals("r1")) {
	return createJedisCache(addressList);
}
if (log.isDebugEnabled()) {
	log.debug("暂不支持该cache类型[r1].");
}
throw new CacheException("不支持该cache类型.");
}

public ICache createJedisCache(List<ServerAddress> addressList) {
if (addressList.size() == 1) {
	log.info("单机模式...");

	int model = 2;
	ServerAddress srvAddress = (ServerAddress) addressList.get(0);
	if (model == 1) {
		Jedis jedis = new Jedis(srvAddress.getHost(),
				srvAddress.getPort());
		return new JedisCache(jedis);
	}
	if (model == 2) {
		JedisPool jedisPool = new JedisPool(new JedisPoolConfig(),
				srvAddress.getHost(), srvAddress.getPort());
		return new JedisCache(jedisPool.getResource());
	}
	throw new IllegalArgumentException("不支持的Jedis实例创建模式.");
}
if (addressList.size() > 1) {
	log.info("shard模式...");
	Set nodes = new HashSet();
	for (int i = 0; i < addressList.size(); ++i) {
		ServerAddress srvAddress = (ServerAddress) addressList.get(i);
		HostAndPort node = new HostAndPort(srvAddress.getHost(),
				srvAddress.getPort());
		nodes.add(node);
	}
	JedisCluster jedisCluster = new JedisCluster(nodes);
	return new JedisClusterCache(jedisCluster);
}
throw new IllegalArgumentException("redis服务器地址不能为空.");
}
}