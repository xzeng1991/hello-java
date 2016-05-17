package cache;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheClient implements IMemcachedCache, IRedisCache {
	private static final Logger logger = LoggerFactory
			.getLogger(CacheClient.class);
	private String cacheType;
	private CachePool cachePool;
	private String expire;
	private ICache cache;

	public void init() {
		valid();
		initCache();
	}

	void valid() {
		validConfig();
		validCachePool(this.cachePool);
	}

	void validConfig() {
	}

	void initCache() {
		CacheConfigLoader.newInstance().initCacheServer(this.cachePool);
		ICacheFactory cacheFactory = CacheFactoryBuilder
				.createCacheFactory(this.cacheType);
		if (cacheFactory == null) {
			logger.error("cacheFactory创建失败.");
			throw new CacheException("cacheFactory创建失败.");
		}

		List srvAddressList = CacheConfigLoader.newInstance()
				.getServerAddressList();
		if (srvAddressList != null) {
			for (Object srvAddress : srvAddressList) {
				//logger.info("srvAddress:" + srvAddress.getHost() + ":"+ srvAddress.getPort());
			}
		}
		this.cache = cacheFactory.createCache(srvAddressList);
		if (this.cache == null) {
			logger.error("cache创建失败.");
			throw new CacheException("cache创建失败.");
		}
		logger.info("cache:" + this.cache);
	}

	void validCachePool(CachePool cachePool) {
		if (cachePool == null) {
			throw new CacheConfigException("cachePool配置不能为空.");
		}
		String router = cachePool.getRouter();
		if ("static".equals(router)) {
			String servers = cachePool.getServers();
			if (servers == null)
				throw new CacheConfigException("cachePool.servers不能为空.");
		}
	}

	public void put(String key, String value) throws CacheException {
		try {
			this.cache.put(key, value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void put(String key, int expire, String value) throws CacheException {
		//this.cache.put(key, expire, value);
	}

	public String get(String key) throws CacheException {
		try {
			return (String)this.cache.get(key);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public void remove(String key) throws CacheException {
		try {
			this.cache.remove(key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void flushAll() throws CacheException {
		//this.cache.flushAll();
	}

	public void oput(String key, Object value) throws CacheException {
		if (this.cache instanceof IRedisCache) {
			IMemcachedCache memcachedCache = null;//(IRedisCache) this.cache;
			memcachedCache.oput(key, value);
		} else {
			throw new IllegalArgumentException("redis不支持该操作.");
		}
	}

	public void oput(String key, int expire, Object value)
			throws CacheException {
		if (this.cache instanceof IRedisCache) {
			IMemcachedCache memcachedCache = null;// (IRedisCache) this.cache;
			memcachedCache.oput(key, expire, value);
		} else {
			throw new IllegalArgumentException("redis不支持该操作.");
		}
	}

	public Object oget(String key) throws CacheException {
		if (this.cache instanceof IRedisCache) {
			IMemcachedCache memcachedCache = null;//(IRedisCache) this.cache;
			return memcachedCache.oget(key);
		}
		throw new IllegalArgumentException("redis不支持该操作.");
	}

	public Map<String, Object> omget(Collection<String> keys)
			throws CacheException {
		if (this.cache instanceof IRedisCache) {
			IMemcachedCache memcachedCache = null;//(IRedisCache) this.cache;
			return memcachedCache.omget(keys);
		}
		throw new IllegalArgumentException("redis不支持该操作.");
	}

	public void hput(String key, String field, String value)
			throws CacheException {
		if (this.cache instanceof IRedisCache) {
			IRedisCache redisCache = (IRedisCache) this.cache;
			redisCache.hput(key, field, value);
		} else {
			throw new IllegalArgumentException("memcached不支持该操作.");
		}
	}

	public String hget(String key, String field) throws CacheException {
		if (this.cache instanceof IRedisCache) {
			IRedisCache redisCache = (IRedisCache) this.cache;
			return redisCache.hget(key, field);
		}
		throw new IllegalArgumentException("memcached不支持该操作.");
	}

	public List<String> hmget(String key, String[] fields) {
		if (this.cache instanceof IRedisCache) {
			IRedisCache redisCache = (IRedisCache) this.cache;
			return redisCache.hmget(key, fields);
		}
		throw new IllegalArgumentException("memcached不支持该操作.");
	}

	public Map<String, String> hgetAll(String key) {
		if (this.cache instanceof IRedisCache) {
			IRedisCache redisCache = (IRedisCache) this.cache;
			return redisCache.hgetAll(key);
		}
		throw new IllegalArgumentException("memcached不支持该操作.");
	}

	public void hremove(String key, String[] fields) {
		if (this.cache instanceof IRedisCache) {
			IRedisCache redisCache = (IRedisCache) this.cache;
			redisCache.hremove(key, fields);
		} else {
			throw new IllegalArgumentException("memcached不支持该操作.");
		}
	}

	public Long incr(String key) {
		if (this.cache instanceof IRedisCache) {
			IRedisCache redisCache = (IRedisCache) this.cache;
			return redisCache.incr(key);
		}
		throw new IllegalArgumentException("memcached不支持该操作.");
	}

	public Long incr(String key, int expire) {
		if (this.cache instanceof IRedisCache) {
			IRedisCache redisCache = (IRedisCache) this.cache;
			return redisCache.incr(key, expire);
		}
		throw new IllegalArgumentException("memcached不支持该操作.");
	}

	public Long incrBy(String key, long integer) {
		if (this.cache instanceof IRedisCache) {
			IRedisCache redisCache = (IRedisCache) this.cache;
			return redisCache.incrBy(key, integer);
		}
		throw new IllegalArgumentException("memcached不支持该操作.");
	}

	public Long incrBy(String key, long integer, int expire) {
		if (this.cache instanceof IRedisCache) {
			IRedisCache redisCache = (IRedisCache) this.cache;
			return redisCache.incrBy(key, integer, expire);
		}
		throw new IllegalArgumentException("memcached不支持该操作.");
	}

	public Double incrByFloat(String key, double value) {
		if (this.cache instanceof IRedisCache) {
			IRedisCache redisCache = (IRedisCache) this.cache;
			return redisCache.incrByFloat(key, value);
		}
		throw new IllegalArgumentException("memcached不支持该操作.");
	}

	public Double incrByFloat(String key, double value, int expire) {
		if (this.cache instanceof IRedisCache) {
			IRedisCache redisCache = (IRedisCache) this.cache;
			return redisCache.incrByFloat(key, value, expire);
		}
		throw new IllegalArgumentException("memcached不支持该操作.");
	}

	public Long expire(String key, int seconds) {
		if (this.cache instanceof IRedisCache) {
			IRedisCache redisCache = (IRedisCache) this.cache;
			return redisCache.expire(key, seconds);
		}
		throw new IllegalArgumentException("memcached不支持该操作.");
	}

	public CachePool getcachePool() {
		return this.cachePool;
	}

	public void setcachePool(CachePool cachePool) {
		validCachePool(cachePool);
		this.cachePool = cachePool;
	}

	public String getCacheType() {
		return this.cacheType;
	}

	public void setCacheType(String cacheType) {
		this.cacheType = cacheType;
	}

	public CachePool getCachePool() {
		return this.cachePool;
	}

	public void setCachePool(CachePool cachePool) {
		this.cachePool = cachePool;
	}

	public String getExpire() {
		return this.expire;
	}

	public void setExpire(String expire) {
		this.expire = expire;
	}

	@Override
	public Object get(Object key) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void load() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object load(Object key) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object put(Object key, Object value) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean flush(Object key, Object value) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean exists(Object key) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object replace(Object key, Object newValue) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Object key) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int size() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set keys() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection values() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
