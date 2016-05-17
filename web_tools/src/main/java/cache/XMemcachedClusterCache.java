package cache;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XMemcachedClusterCache extends AbstractCache implements IMemcachedCache {
	private static final Logger log = LoggerFactory.getLogger(XMemcachedClusterCache.class);

	private List<IMemcachedCache> mecList = null;

	public XMemcachedClusterCache() {
	}

	public XMemcachedClusterCache(List<IMemcachedCache> mecList) {
		this.mecList = mecList;
	}

	List<IMemcachedCache> getWriteCacheList() {
		return this.mecList;
	}

	IMemcachedCache getReadCache() {
		int size = this.mecList.size();
		Random rand = new Random();
		int index = rand.nextInt(size);
		log.info("总节点数:" + size + ",当前读取节点:" + index);
		IMemcachedCache cache = (IMemcachedCache) this.mecList.get(index);
		return cache;
	}

	public void put(String key, String value) throws CacheException {
		put(key, value);
	}

	public void put(String key, int expire, String value) throws CacheException {
		put(key, expire, value);
	}

	public String get(String key) throws Exception {
		ICache cache = getReadCache();
		if (cache != null) {
			return (String)cache.get(key);
		}
		return null;
	}

	public void oput(String key, Object value) throws CacheException {
		List cacheList = getWriteCacheList();

		IMemcachedCache mainCache = (IMemcachedCache) cacheList.get(0);
		try {
			mainCache.oput(key, value);
		} catch (Exception ex) {
			log.error("write cache failed.");
			throw new CacheException("write cache failed.", ex);
		}

		for (int i = 1; i < cacheList.size(); ++i) {
			CacheAction action = new CacheAction();
			IMemcachedCache cache = (IMemcachedCache) cacheList.get(i);
			action.setCache(cache);
			action.setKey(key);
			action.setValue(value);
			action.setAction("write");
			CacheActionQueue.add(action);
		}
	}

	public void oput(String key, int expire, Object value) throws CacheException {
		List cacheList = getWriteCacheList();

		IMemcachedCache mainCache = (IMemcachedCache) cacheList.get(0);
		try {
			mainCache.oput(key, expire, value);
		} catch (Exception ex) {
			throw new CacheException("write cache failed.", ex);
		}

		for (int i = 1; i < cacheList.size(); ++i) {
			CacheAction action = new CacheAction();
			IMemcachedCache cache = (IMemcachedCache) cacheList.get(i);
			action.setCache(cache);
			action.setKey(key);
			action.setExpire(expire);
			action.setValue(value);
			action.setAction("write");
			CacheActionQueue.add(action);
		}
	}

	public Object oget(String key) throws CacheException {
		ICache cache = getReadCache();
		if (cache != null) {
			try {
				return cache.get(key);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public Map<String, Object> omget(Collection<String> keys) throws CacheException {
		IMemcachedCache cache = getReadCache();
		if (cache != null) {
			return cache.omget(keys);
		}
		return null;
	}

	public void remove(String key) throws CacheException {
		List cacheList = getWriteCacheList();

		ICache mainCache = (ICache) cacheList.get(0);
		try {
			mainCache.remove(key);
		} catch (Exception ex) {
			throw new CacheException("delete cache failed.", ex);
		}

		for (int i = 1; i < cacheList.size(); ++i) {
			CacheAction action = new CacheAction();
			IMemcachedCache cache = (IMemcachedCache) cacheList.get(i);
			action.setCache(cache);
			action.setKey(key);
			action.setAction("delete");
			CacheActionQueue.add(action);
		}
	}

	public Long expire(String key, int seconds) {
		return null;
	}

	public void flushAll() throws CacheException {
		List cacheList = getWriteCacheList();
		try {
			//for (IMemcachedCache cache : cacheList)
				//cache.flushAll();
		} catch (Exception ex) {
			throw new CacheException("flushAll failed:" + ex);
		}
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