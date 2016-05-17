package cache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;


public class RedisCache implements IECache
{

	public static byte[] NULL_BYTE=new byte[0];
	private ShardedJedis redis()
	{
		return null;//ServiceManager.cacheManager().getRedis();
	}
	
	
	private byte[] serialize(Object v) 
	{
		if(v==null)
			return NULL_BYTE;
		/*try
        {
	        return SerializeUtils.serialze(v);
        } catch (IOException e)
        {
	       throw new RuntimeException("Serialize error!",e);
        }*/
		return null;
	}
	
	private byte[] serializeKey(Object v) 
	{
		try
        {
	        /*return *///SerializeUtils.serialze(v);
	        if(v==null)
	        	return null;
	        return v.toString().getBytes();
        } catch (Exception e)
        {
	       throw new RuntimeException("Serialize error!",e);
        }
	}
	
	
	@SuppressWarnings("unchecked")
    private <T> T deserializeKey(byte[] v){
		if(v==null||Arrays.equals(v, NULL_BYTE))
			return null;
		try
		{ 
			return (T)new String(v);
//			return (T)SerializeUtils.deserialze(v,Object.class);
		} catch (Exception e)
		{
			throw new RuntimeException("Serialize error!",e);
		}

	}
	
	
	
	
	private byte[][] serializeKey(Object[] keys) 
	{
		byte[][] bs = new byte[keys.length][];
		for(int i=keys.length-1;i>=0;i--)
		{
			bs[i]=serializeKey(keys[i]);
		}
		return bs;
	}
	
	private byte[][] serialize(Object[] keys)
	{
		byte[][] bs = new byte[keys.length][];
		for(int i=keys.length-1;i>=0;i--)
		{
			bs[i]=serialize(keys[i]);
		}
		return bs;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
    private <T> T deserialize(byte[] v,Class<T> cla) 
	{
		/*if(v==null||Arrays.equals(v, NULL_BYTE))
			return null;
		try
        { 
			return (T)SerializeUtils.deserialze(v,cla);
        } catch (Exception e)
        {	
        	throw new RuntimeException("Serialize error!",e);
        }*/
		return null;
	}
	
	
    private <T> List<T> deserialize(Collection<byte[]> vc,Class<T> cla) 
	{
    	List<T> rs = new ArrayList<T>();
		for(byte[] b : vc)
		{
			T v = deserialize(b,cla);
			rs.add(v);
		}
		return rs;
	}
    
    
	@Override
	public <K, V> V get(K key,Class<V> cla)
	{
		ShardedJedis r =redis();
		try
        {
	        return deserialize(r.get(serializeKey(key)),cla) ;
        } catch (Exception e)
        {
        	throw new RuntimeException(e);
        } 
	}

	@Override
	public <K, V> boolean put(K key, V value)
	{
		ShardedJedis r =redis();
		r.set(serializeKey(key), serialize(value));
		return true;
	}

	@Override
	public <K, V> boolean put(K key, V value, int expire)
	{
		ShardedJedis r =redis();
		byte[] k = serializeKey(key);
		r.set(k, serialize(value));
		r.expire(k, expire);
		return true;
	}

	@Override
	public <K, V> boolean putNe(K key, V value)
	{
		ShardedJedis r =redis();
		return r.setnx(serializeKey(key), serialize(value))==1;
	}

	@Override
	public <K> boolean exists(K key)
	{
		ShardedJedis r =redis();
		return r.equals(serializeKey(key));
	}

	@Override
	public <K> boolean remove(K key)
	{
		ShardedJedis r =redis();
		return r.del(serializeKey(key))==1;
	}

	@Override
	public boolean flush()
	{
		ShardedJedis r =redis();
		Collection<Jedis> c = r.getAllShards();
		Iterator<Jedis> i =  c.iterator();
		if(i.hasNext()){
			Jedis j = i.next();
			//Set<String> keys = j.keys("*");
			j.flushDB();
		}
		return true;
	}
	@Override
	public boolean flush(Set<String> keys){
		ShardedJedis r =redis();
		Collection<Jedis> c = r.getAllShards();
		Iterator<Jedis> i =  c.iterator();
		if(i.hasNext()){
			Jedis j = i.next();
			Set<String> strs = j.keys("*");
			if(strs!=null && strs.size()>0){
				for(String key:keys){
					if(strs.contains(key)){
						remove(key);
					}
				}
			}
		}
		return true;
	}

	@Override
	public <K> boolean expiredAt(K key, Date date)
	{
		if(date == null) return false;
		ShardedJedis r =redis();
		//unix时间戳，将毫秒数/1000
		return r.expireAt(serializeKey(key), date.getTime()/1000)==1;
	}

	@Override
	public <K> boolean expiredAfter(K key, long times)
	{
		
		ShardedJedis r =redis();
		
		return r.expireAt(serializeKey(key), System.currentTimeMillis()/1000+times)==1;
	}

	@Override
	public <K, V> List<V> getBatch(K[] keys,Class<V> cla)
	{
		List<V> vl = new ArrayList<V>();
		for(K k:keys)
		{
			V v = get(k,cla);
			vl.add(v);
		}
		return vl;
	}

	@Override
	public <K> boolean removeBatch(K[] keys)
	{
		for(K k:keys)
		{
			remove(k);
		}
		return true;
	}

	@Override
	public <G, K, V> Map<K, V> getMap(G mapKey,Class<V> cla)
	{
		ShardedJedis r =redis();
		Map<byte[],byte[]> rms = r.hgetAll(serializeKey(mapKey));
		if(rms==null||(rms.size()==0&&!exists(serializeKey(mapKey))))
			return null;
		Map<K,V> frms = new HashMap<K,V>();
		for(Entry<byte[],byte[]> e:rms.entrySet())
		{
			K k = deserializeKey(e.getKey());
			V v=deserialize(e.getValue(),cla);
			frms.put(k,v);
		}
		return frms;
	}

	@Override
	public <G, K, V> Map<K, V> getMap(G mapKey, Class<K> k, Class<V> cla) {
		ShardedJedis r =redis();
		Map<byte[],byte[]> rms = r.hgetAll(serializeKey(mapKey));
		if(rms==null||(rms.size()==0&&!exists(serializeKey(mapKey))))
			return null;
		Map<K,V> frms = new HashMap<K,V>();
		for(Entry<byte[],byte[]> e:rms.entrySet())
		{
			K k1 = deserializeKey(e.getKey());
			V v=deserialize(e.getValue(),cla);
			frms.put(k1,v);
		}
		return frms;
	}

	@Override
	public <K, G, V> V getMap(G g, K k,Class<V> cla)
	{
		ShardedJedis r =redis();
		return deserialize(r.hget(serializeKey(g), serializeKey(k)),cla);
	}

	@Override
	public <K, G, V> List<V> getMapList(G g, K k, Class<V> cla) {
		ShardedJedis r =redis();
		return deserialize(r.hget(serializeKey(g), serializeKey(k)),List.class);
	}

	@Override
	public <K, G, V> List<V> getMapBatch(G g, K[] ks,Class<V> cla)
	{
		ShardedJedis r =redis();
		List<byte[]> brs = r.hmget(serializeKey(g), serializeKey(ks));
		List<V> rs = new ArrayList<V>();
		for(byte[] br:brs)
		{
			V v = deserialize(br,cla);
			rs.add(v);
		}
		return rs;
	}

	@Override
	public <K, G, V> boolean putMap(G g, K k, V v)
	{
		ShardedJedis r =redis();
		return r.hset(serializeKey(g), serializeKey(k), serialize(v))==1;
	}

	@Override
	public <K, G, V> boolean putMap(G g, Map<K, V> values)
	{
		ShardedJedis r =redis();
		Map<byte[],byte[]> mbs = new HashMap<byte[],byte[]>();
		for(Entry<K,V> e:values.entrySet())
		{
			mbs.put(serializeKey(e.getKey()), serialize(e.getValue()));
		}
		if (mbs.size()>0){
			r.hmset(serializeKey(g), mbs);
		}
		return true;
	}

	@Override
	public <G> boolean removeMap(G g)
	{
		ShardedJedis r =redis();
		return r.del(serializeKey(g))==1;
	}

	@Override
	public <K, G> boolean removeMap(G g, K k)
	{
		ShardedJedis r =redis();
		return r.hdel(serializeKey(g), serializeKey(k))==1;
	}

	@Override
	public <K, G> boolean removeMapBatch(G g, K[] k)
	{
		ShardedJedis r =redis();
		return r.hdel(serializeKey(g), serializeKey(k))==1;
	}

	
	
	@Override
    public <G, K> Set<K> mapKeys(G g)
    {
		ShardedJedis r =redis();
		Set<byte[]> brs = r.hkeys(serializeKey(g));
		Set<K> rs = new HashSet<K>();
		if(brs!=null&&brs.size()>0)
		{
			for(byte[] br:brs)
			{
				K rd = deserializeKey(br);
				rs.add(rd);
			}
		}
	    return rs;
    }


	@Override
	public <K, G> boolean exists(G g, K k)
	{
		ShardedJedis r =redis();
		return r.hexists(serializeKey(g), serializeKey(k));
	}

	@Override
	public boolean persistence()
	{
		return false;
	}
	
	
	
	
	
	@Override
    public <K, V> long putToList(K key, V v)
    {
		ShardedJedis r =redis();
		byte[] bk = serializeKey(key);
		return r.rpush(bk, serialize(v))-1;
    }
	
    public <K, V> void putToList(K key, V v,int index)
    {
		ShardedJedis r =redis();
		r.lset(serializeKey(key), index, serialize(v));
    }







	@Override
    public <K, V> void removeList(K key, V v)
    {
		ShardedJedis r =redis();
		r.lrem(serializeKey(key), 0, serialize(v));
    }


	@Override
    public <K, V> V getList(K key, int index,Class<V> cla)
    {
		ShardedJedis r =redis();
	    return deserialize(r.lindex(serializeKey(key), index),cla);
    }


	@Override
    public <K, V> List<V> getList(K key,Class<V> cla)
    {
		ShardedJedis r =redis();
		List<byte[]> brs = r.lrange(serializeKey(key), 0, -1);
		List<V> rs = new ArrayList<V>();
		if(brs!=null&&brs.size()>0)
		{
			for(byte[] br:brs)
			{
				V rd = deserialize(br,cla);
				rs.add(rd);
			}
		}
	    return rs;
    }


	@Override
    public <K, V> void putToSet(K key, V v)
    {
		ShardedJedis r =redis();
		r.sadd(serializeKey(key), serialize(v));
	    
    }
	
	


	@Override
    public <K, V> long putToList(K key, V[] vs)
    {
		byte[][] bvs =serialize(vs);
		ShardedJedis r =redis();
	    return r.rpush(serializeKey(key), bvs);
    }


	@Override
    public <K, V> void putToSet(K key, V[] vs)
    {
		byte[][] bvs =serialize(vs);
		ShardedJedis r =redis();
		r.sadd(serializeKey(key), bvs);
    }


	@Override
    public <K, V> Set<V> getSet(K key,Class<V> cla)
    {
		ShardedJedis r =redis();
		Set<byte[]> brs = r.smembers(serializeKey(key));
		Set<V> rs = new HashSet<V>();
		if(brs!=null&&brs.size()>0)
		{
			for(byte[] br:brs)
			{
				V rd = deserialize(br,cla);
				rs.add(rd);
			}
		}
	    return rs;
    }
	
	


	@Override
    public <K, V> void removeSet(K key, V v)
    {
		ShardedJedis r =redis();
		r.srem(serializeKey(key), serialize(v));
    }


	@Override
    public <K, V> void putToSortedSet(K key, V v, float score)
    {
		ShardedJedis r =redis();
		r.zadd(serializeKey(key), score, serialize(v));
    }


	@Override
    public <K, V> void removeSortedSet(K key, V v)
    {
		ShardedJedis r =redis();
		r.zrem(serializeKey(key), serialize(v));
	    
    }


	@Override
    public <K, V> Set<V> getSortedSet(K key, Class<V> cla)
    {
		ShardedJedis r =redis();
		Set<byte[]> brs = r.zrange(serializeKey(key),0,-1);
		Set<V> rs = new HashSet<V>();
		if(brs!=null&&brs.size()>0)
		{
			for(byte[] br:brs)
			{
				V rd = deserialize(br,cla);
				rs.add(rd);
			}
		}
	    return rs;
    }

}
