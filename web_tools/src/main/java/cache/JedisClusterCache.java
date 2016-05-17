package cache;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisCluster;

public class JedisClusterCache extends AbstractCache implements IRedisCache {
	private static final Logger logger = LoggerFactory
			.getLogger(ICacheFactory.class);

	private JedisCluster jedisCluster = null;

	public JedisClusterCache() {
	}

	public JedisClusterCache(JedisCluster jedisCluster) {
		this.jedisCluster = jedisCluster;
	}

	public String get(String key) throws CacheException {
		return this.jedisCluster.get(key);
	}

	public void put(String key, String value) throws CacheException {
		this.jedisCluster.set(key, String.valueOf(value));
	}

	public void put(String key, int expire, String value) throws CacheException {
		this.jedisCluster.set(key, String.valueOf(value));
		if (expire > 0)
			this.jedisCluster.expire(key, expire);
	}

	public void remove(String key) throws CacheException {
		this.jedisCluster.del(key);
	}

	public void hput(String key, String field, String value)
			throws CacheException {
		this.jedisCluster.hset(key, field, value);
	}

	public String hget(String key, String field) throws CacheException {
		return this.jedisCluster.hget(key, field);
	}

	/*public List<String> hmget(String key, String[] fields) {
		return this.jedisCluster.hmget(key, fields);
	}*/

	/*public Map<String, String> hgetAll(String key) {
		return this.jedisCluster.hgetAll(key);
	}*/

	public void hremove(String key, String[] fields) {
		this.jedisCluster.hdel(key, fields);
	}

	public Long incr(String key) {
		return this.jedisCluster.incr(key);
	}

	public Long incr(String key, int expire) {
		Long v = this.jedisCluster.incr(key);
		this.jedisCluster.expire(key, expire);
		return v;
	}

	public Long incrBy(String key, long integer) {
		return this.jedisCluster.incrBy(key, integer);
	}

	public Long incrBy(String key, long integer, int expire) {
		Long v = this.jedisCluster.incrBy(key, integer);
		this.jedisCluster.expire(key, expire);
		return v;
	}

	public Double incrByFloat(String key, double value) {
		return this.jedisCluster.incrByFloat(key, value);
	}

	public Double incrByFloat(String key, double value, int expire) {
		Double v = this.jedisCluster.incrByFloat(key, value);
		this.jedisCluster.expire(key, expire);
		return v;
	}

	public Long expire(String key, int seconds) {
		return this.jedisCluster.expire(key, seconds);
	}

	public void flushAll() throws CacheException {
		throw new CacheException("cluster模式不支持flushAll操作.");
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

	@Override
	public List<String> hmget(String paramString, String[] paramArrayOfString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> hgetAll(String paramString) {
		// TODO Auto-generated method stub
		return null;
	}
}