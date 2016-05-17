package cache;

public class CacheFactoryBuilder {
	private static final String DEFAULT_CACHE = "memcached";
	private static final String CACHE_MEM = "memcached";
	private static final String CACHE_REDIS = "redis";

	public static ICacheFactory createCacheFactory(String cacheType) {
		if (cacheType == null) {
			cacheType = "memcached";
		}
		if (cacheType.equals("memcached"))
			return new MemCacheFactory("m3");
		if (cacheType.equals("redis")) {
			return new RedisCacheFactory("r1");
		}
		throw new CacheConfigException("不支持该类型cache.");
	}
}
