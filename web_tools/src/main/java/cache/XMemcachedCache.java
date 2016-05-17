package cache;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XMemcachedCache extends AbstractCache implements IMemcachedCache {
	private static final Logger log = LoggerFactory.getLogger(XMemcachedCache.class);
	private XMemcachedClient mec;
	private static String CACHE_TYPE = "memcached";

	public XMemcachedCache() {
	}

	public XMemcachedCache(XMemcachedClient mec) {
		this.mec = mec;
	}

	public void put(String key, String value) throws CacheException {
		try {
			this.mec.set(key, 0, value);
		} catch (TimeoutException e) {
			throw new CacheException("发生超时异常", e);
		} catch (InterruptedException e) {
			throw new CacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new CacheException("发生异常", e);
		}
	}

	public void put(String key, int expire, String value) throws CacheException {
		try {
			this.mec.set(key, expire, value);
		} catch (TimeoutException e) {
			throw new CacheException("发生超时异常", e);
		} catch (InterruptedException e) {
			throw new CacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new CacheException("发生异常", e);
		}
	}

	public String get(String key) throws CacheException {
		try {
			return ((String) this.mec.get(key));
		} catch (TimeoutException e) {
			throw new CacheException("发生超时异常", e);
		} catch (InterruptedException e) {
			throw new CacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new CacheException("发生异常", e);
		}
	}

	public void oput(String key, Object value) throws CacheException {
		try {
			this.mec.set(key, 0, value);
		} catch (TimeoutException e) {
			throw new CacheException("发生超时异常", e);
		} catch (InterruptedException e) {
			throw new CacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new CacheException("发生异常", e);
		}
	}

	public void oput(String key, int expire, Object value) throws CacheException {
		try {
			this.mec.set(key, expire, value);
		} catch (TimeoutException e) {
			throw new CacheException("发生超时异常", e);
		} catch (InterruptedException e) {
			throw new CacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new CacheException("发生异常", e);
		}
	}

	public Object oget(String key) throws CacheException {
		try {
			return this.mec.get(key);
		} catch (TimeoutException e) {
			throw new CacheException("发生超时异常", e);
		} catch (InterruptedException e) {
			throw new CacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new CacheException("发生异常", e);
		}
	}

	public Map<String, Object> omget(Collection<String> keys) throws CacheException {
		try {
			return this.mec.get(keys);
		} catch (TimeoutException e) {
			throw new CacheException("发生超时异常", e);
		} catch (InterruptedException e) {
			throw new CacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new CacheException("发生异常", e);
		}
	}

	public void remove(String key) throws CacheException {
		try {
			this.mec.delete(key);
		} catch (TimeoutException e) {
			throw new CacheException("发生超时异常", e);
		} catch (InterruptedException e) {
			throw new CacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new CacheException("发生异常", e);
		}
	}

	public Long expire(String key, int seconds) {
		return null;
	}

	public void flushAll() throws CacheException {
		try {
			this.mec.flushAll();
		} catch (TimeoutException e) {
			throw new CacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new CacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new CacheException("发生异常", e);
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
