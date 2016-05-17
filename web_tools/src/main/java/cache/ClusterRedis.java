package cache;

import java.util.*;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import base.util.StringUtils;

/**
 * Created by haijun.zou on 2015-09-08.
 */
public class ClusterRedis implements IECache {
	
	public static byte[] NULL_BYTE=new byte[0];
    private static final Logger LOG = LoggerFactory.getLogger(ClusterRedis.class);
	private CacheClient cacheClient()
	{
		return null;//ServiceManager.cacheManager().getCacheClient();
	}
	
	/**
	 * 从缓存中获取数据
	 */
	@Override
	public <K, V> V get(K key,Class<V> cla)
	{
		try
        {
            Object o =cacheClient().get(JsonUtils.toString(key));
	        return JsonUtils.toBean(String.valueOf(o),cla);
        } catch (Exception e)
        {
        	throw new RuntimeException(e);
        } 
	}
	
	/**
	 * 直接放入缓存中
	 */
    @Override
    public <K, V> boolean put(K key, V value) {
        try {
            cacheClient().put(JsonUtils.toString(key),JsonUtils.toString(value));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 放入缓存中，并设置缓存过期时间
     */
    @Override
    public <K, V> boolean put(K key, V value, int expire) {
        try {
            cacheClient().put(JsonUtils.toString(key), expire, JsonUtils.toString(value));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 无论key是否存在都要在放一次【原本设计是如果key就存在不做处理，不存在就放一次】
     */
    @Override
    public <K, V> boolean putNe(K key, V value) {
        try {
            cacheClient().put(JsonUtils.toString(key),JsonUtils.toString(value));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断缓存中是否存在某个key值
     */
    @Override
    public <K> boolean exists(K key) {
    	if(null != cacheClient().get(JsonUtils.toString(key))){
    		return true;
    	}
    	return false;
    }

    /**
     * 从缓存中删除某个key值
     */
    @Override
    public <K> boolean remove(K key) {
        try {
            cacheClient().remove(JsonUtils.toString(key));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 刷新缓存
     */
    @Override
    public boolean flush() {
//        cacheClient().flushAll();
        return true;
    }

    /**
     * 刷新指定的key的缓存【实际操作是删除指定key的数据】
     */
    @Override
    public boolean flush(Set<String> keys) {
        try {
            for (String s :keys){
                removeMap(s);
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
		return true;
    }

    /**
     * 目前架构组提供的客户端jar不支持此操作【指定某个key在某个时间点失效】
     */
    @Override
    public <K> boolean expiredAt(K key, Date date) {
    	/*if(date == null) return false;
		ShardedJedis r =redis();
		//unix时间戳，将毫秒数/1000
		return r.expireAt(serializeKey(key), date.getTime()/1000)==1;*/
        return false;
    }

    /**
     * 目前架构组提供的客户端jar不支持此操作【指定某个key在某个时间点失效】
     */
    @Override
    public <K> boolean expiredAfter(K key, long times) {
        return false;
    }

    /**
     * 从缓存中批量获取指定key的数据
     */
    @Override
    public <K, V> List<V> getBatch(K[] keys, Class<V> cla) {
    	List<V> vl = new ArrayList<V>();
		for(K k:keys)
		{
			V v = get(k,cla);
			vl.add(v);
		}
		return vl;
    }

    /**
     * 从缓存中批量删除指定key的数据
     */
    @Override
    public <K> boolean removeBatch(K[] keys) {
    	for(K k:keys)
		{
			remove(k);
		}
		return true;
    }
   
    /**
     * 待分析
     */
    @Override
    public <G, K, V> Map<K, V> getMap(G mapKey, Class<V> cla) {
        Map<Object,Object> result = new HashMap<Object,Object>();
        Map<String,String> map = cacheClient().hgetAll(JsonUtils.toString(mapKey));
        if (map!=null && map.size()>0){
            for (Iterator<Entry<String,String>> it = map.entrySet().iterator();it.hasNext();){
                Entry<String,String> entry = it.next();
                result.put(JsonUtils.toBean(entry.getKey(),Object.class),JsonUtils.toBean(entry.getValue(),cla));
            }
        }
        return (Map<K, V>) result;
    }
    @Override
    public <G, K, V> Map<K, V> getMap(G mapKey, Class<K> k, Class<V> cla) {
        Map<K,V> result = new HashMap<K,V>();
        Map<String,String> map = cacheClient().hgetAll(JsonUtils.toString(mapKey));
        if (map!=null && map.size()>0){
            for (Iterator<Entry<String,String>> it = map.entrySet().iterator();it.hasNext();){
                Entry<String,String> entry = it.next();
                if (StringUtils.isNotEmpty(entry.getKey())){
                    result.put(JsonUtils.toBean(entry.getKey(),k),JsonUtils.toBean(entry.getValue(),cla));
                }
            }
        }
        return result;
    }

    /**
     * 待分析
     */
    @Override
    public <K, G, V> V getMap(G g, K k, Class<V> cla) {
        String value = null;
        try {
            value = cacheClient().hget(JsonUtils.toString(g),JsonUtils.toString(k));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtils.isNotEmpty(value)){
            try {
                return JsonUtils.toBean(value,cla);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    @Override
    public <K, G, V> List<V> getMapList(G g, K k, Class<V> cla) {
        String value = null;
        try {
            value = cacheClient().hget(JsonUtils.toString(g),JsonUtils.toString(k));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtils.isNotEmpty(value)){
            try {
                return JsonUtils.toListBean(value, cla);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * 待分析
     */
    @Override
    public <K, G, V> List<V> getMapBatch(G g, K[] ks, Class<V> cla) {
    	List<V> list  =new ArrayList<V>();
        if (ks!=null && ks.length>0){
            for (K k:ks){
                String value = null;
                try {
                    value = cacheClient().hget(JsonUtils.toString(g), JsonUtils.toString(k));
                    if (StringUtils.isNotEmpty(value)){
                        list.add(JsonUtils.toBean(value,cla));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            return  list;
        }
        return list;
    }

    /**
     * 存放的数据形式为：	key----子key-----数据
     */
    @Override
    public <K, G, V> boolean putMap(G g, K k, V v) {

        try {
            String sg = JsonUtils.toString(g);
            String kg = JsonUtils.toString(k);
            String vg = JsonUtils.toString(v);
            cacheClient().hput(sg,kg,vg);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 存放的数据形式为：	key----Map数组
     */
    @Override
    public <K, G, V> boolean putMap(G g, Map<K, V> values) {
		for(Entry<K,V> e:values.entrySet()){
            putMap(g,e.getKey(),e.getValue());
        }
        return true;
    }

    /**
     * 
     */
    @Override
    public <G> boolean removeMap(G g) {
        try {
            cacheClient().remove(JsonUtils.toString(g));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 
     */
    @Override
    public <K, G> boolean removeMap(G g, K k) {
        String srt [] = new String[0];
        try {
            srt = new String[]{JsonUtils.toString(k)};
            cacheClient().hremove(JsonUtils.toString(g), srt);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 
     */
    @Override
    public <K, G> boolean removeMapBatch(G g, K[] k) {
    	String [] str  = new String[k.length];
        try {
            for(int i=0;i<k.length;i++){
                str[i] = JsonUtils.toString(k[i]);
            }
            cacheClient().hremove(JsonUtils.toString(g),str);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 该方法意义不大
     */
    @Override
    public <G, K> Set<K> mapKeys(G g) {
        Map<String,String> map = cacheClient().hgetAll(JsonUtils.toString(g));
        Set<String> set = new HashSet<String>();
        if (map!=null && map.size()>0){
            for (Iterator<Entry<String,String>> it = map.entrySet().iterator();it.hasNext();){
                set.add(JsonUtils.toBean(it.next().getKey(),String.class));
            }
        }
        return (Set<K>) set;
    }

    /**
     * 
     */
    @Override
    public <K, G> boolean exists(G g, K k) {
        try {
            if(null != cacheClient().hget(JsonUtils.toString(g), JsonUtils.toString(k))){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * 
     */
    @Override
    public <K, V> long putToList(K key, V v) {
        try {
            cacheClient().put(JsonUtils.toString(key),JsonUtils.toString(v));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 
     */
    @Override
    public <K, V> long putToList(K key, V[] v) {
    	for(int i=0;i<v.length;i++){
            try {
                cacheClient().put(JsonUtils.toString(key), JsonUtils.toString(v));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * 不支持
     */
    @Override
    public <K, V> void putToList(K key, V v, int index) {
    	
    }

    /**
     * 
     */
    @Override
    public <K, V> void removeList(K key, V v) {
    	
    }

    /**
     * 不支持
     */
    @Override
    public <K, V> V getList(K key, int index, Class<V> cla) {
        return null;
    }

    /**
     * 
     */
    @Override
    public <K, V> List<V> getList(K key, Class<V> cla) {
        return null;
    }

    /**
     * 
     */
    @Override
    public <K, V> void putToSet(K key, V v) {

    }

    /**
     * 
     */
    @Override
    public <K, V> Set<V> getSet(K key, Class<V> cla) {
        return null;
    }

    /**
     * 
     */
    @Override
    public <K, V> void removeSet(K key, V v) {

    }

    /**
     * 
     */
    @Override
    public <K, V> void putToSet(K key, V[] vs) {

    }

    /**
     * 架构组提供的jar支持不了该功能
     */
    @Override
    public <K, V> void putToSortedSet(K key, V v, float score) {

    }

    /**
     * 架构组提供的jar支持不了该功能
     */
    @Override
    public <K, V> Set<V> getSortedSet(K key, Class<V> cla) {
        return null;
    }

    /**
     * 架构组提供的jar支持不了该功能
     */
    @Override
    public <K, V> void removeSortedSet(K key, V v) {

    }

    /**
     * 
     */
    @Override
    public boolean persistence() {
        return false;
    }

}
