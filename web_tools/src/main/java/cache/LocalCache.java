package cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class LocalCache implements IECache
{
	private  ConcurrentHashMap<Object, ExpireObject> caches = new ConcurrentHashMap<Object, ExpireObject>();
	
	{
		Timer t = new Timer();
		t.scheduleAtFixedRate(new TimerTask()
		{
			
			@Override
			public void run()
			{
				Set<Object> keys = caches.keySet();
				for(Object k:keys)
				{
					ExpireObject o = caches.get(k);
					if(o.expire())
						remove(k);
				}
			}
		}, 10000, 60*60*1000);
	}
	public LocalCache()
	{
		
	}
	@Override
    public <K, V> V get(K key,Class<V> cla)
    {
		ExpireObject o = caches.get(key);
		if(o==null)
			return null;
		if(o.expire())
		{
			caches.remove(key);
			return null;
		}
	    return o.value();
    }

	@Override
    public <K, V> boolean put(K key, V value)
    {
		if(value==null)
			{
				remove(key);
				return true;
			}
		
		ExpireObject o = new ExpireObject(-1,value);
		ExpireObject p = caches.put(key, o);
		return o.equals(p);
    }

	@Override
    public <K, V> boolean put(K key, V value, int expire)
    {
		if(value==null)
		{
			remove(key);
			return true;
		}
	
	ExpireObject o = new ExpireObject(System.currentTimeMillis()+expire*1000,value);
	ExpireObject p = caches.put(key, o);
	return o.equals(p);
    }

	@Override
    public <K, V> boolean putNe(K key, V value)
    {
		if(value==null)
		{
			remove(key);
			return true;
		}
	
	ExpireObject o = new ExpireObject(System.currentTimeMillis(),value);
	ExpireObject p = caches.putIfAbsent(key, o);
	return o.equals(p);
    }

	@Override
    public <K> boolean exists(K key)
    {
	    // TODO Auto-generated method stub
	    return caches.contains(key);
    }

	@Override
    public <K> boolean remove(K key)
    {
	     caches.remove(key);
	     return true;
    }

	@Override
    public boolean flush()
    {
	     caches.clear();
	     return true;
    }
	
	@Override
	public boolean flush(Set<String> keys){
		Set<Object> strs =   caches.keySet();
		if(strs!=null && strs.size()>0){
			for(String key :keys){
				if(strs.contains(key))
					remove(key);
			}
		}
		return true;
	}

	@Override
    public <K> boolean expiredAt(K key, Date date)
    {
		ExpireObject p = caches.get(key);
		if(p==null)
			return false;
		p.setExpire(date.getTime());
		return true;
    }

	@Override
    public <K> boolean expiredAfter(K key, long times)
    {
		ExpireObject p = caches.get(key);
		if(p==null)
			return false;
		p.setExpire(System.currentTimeMillis()+times*1000);
		return true;
    }

	@Override
    public <K, V> List<V> getBatch(K[] keys,Class<V> cla)
    {
	   List<V> values = new ArrayList<V>();
	   for(K key:keys)
	   {
		   V o = get(key,cla);
		   values.add(o);
	   }
	    return values;
    }

	@Override
    public <K> boolean removeBatch(K[] keys)
    {
		 for(K key:keys)
		   {
			  remove(key);
		   }
		 return true;
    }

	@Override
    public <G,K, V> Map<K, V> getMap(G mapKey,Class<V> cla)
    {
	    return get(mapKey,Map.class);
    }

	@Override
	public <G, K, V> Map<K, V> getMap(G mapKey, Class<K> k, Class<V> cla) {
		return get(mapKey,Map.class);
	}

	@Override
    public <K, G, V> V getMap(G g, K k,Class<V> cla)
    {
		Map<K, V> map = getMap(g,cla);
		if(map!=null)
			return map.get(k);
	    return null;
    }

	@Override
	public <K, G, V> List getMapList(G g, K k, Class<V> cla) {
		Map<K, V> map = getMap(g,cla);
		if(map!=null){
			V v=map.get(k);
            List ls = (List) v;
			return ls;
		}
	    return null;
    }

	@Override
    public <K, G, V> List<V> getMapBatch(G g, K[] ks,Class<V> cla)
    {
		Map<K, V> map = getMap(g,cla);
		if(map!=null)
		{
			List<V> vs = new ArrayList<V>();
			for(K k:ks)
			{
				vs.add(map.get(k));
			}
			return vs;
		}
	    return null;
    }

	@Override
    public <K, G, V> boolean putMap(G g, K k, V v)
    {
		Map<K, V> map = (Map<K, V>) getMap(g,Object.class);
		if(map==null)
		{
			synchronized (caches)
            {
				map = (Map<K, V>) getMap(g,Object.class);
				if(map==null)
				{
					map = new HashMap<K,V>();
					put(g, map);
				}
            }
		}
		map.put(k, v);
	    return true;
    }

	@Override
    public <K, G, V> boolean putMap(G g, Map<K, V> values)
    {
		Map<K, V> map = (Map<K, V>) getMap(g,Object.class);
		if(map==null)
		{
			synchronized (caches)
            {
				map = (Map<K, V>) getMap(g,Object.class);
				if(map==null)
				{
					map = new HashMap<K,V>();
					put(g, map);
				}
            }
		}
		map.putAll(values);;
	    return true;
    }

	@Override
    public <G> boolean removeMap(G g)
    {
	    return remove(g);
    }

	@Override
    public <K, G> boolean removeMap(G g, K k)
    {
		Map<K, ?> map = getMap(g,Object.class);
		if(map!=null)
		{
			map.remove(k);
		}
		return true;
    }

	@Override
    public <K, G> boolean removeMapBatch(G g, K[] ks)
    {
		Map<K, ?> map = getMap(g,Object.class);
		if(map!=null)
		{
			for(K k:ks)
			{
				map.remove(k);
			}
		}
		return true;
    }

	@Override
    public <K, G> boolean exists(G g, K k)
    {
		Map<K, ?> map = getMap(g,Object.class);
		if(map!=null)
		{
			return map.containsKey(k);
		}
	    return false;
    }

	@Override
    public boolean persistence()
    {
	    return true;
    }
	
	
	
	
	
	@Override
    public <G, K> Set<K> mapKeys(G g)
    {
	    Map<K,?> map = getMap(g,Object.class);
	    
	    return null==map?null:map.keySet();
    }
	@Override
    public <K, V> long putToList(K key, V v)
    {
		List<V> list = get(key,List.class);
		if(list==null)
		{
			synchronized (caches)
            {
				if(list==null)
				{
					list =new ArrayList<V>();
				    put(key, list);
				}
            }
		}
		list.add(v);
	    return list.size()-1;
    }
	@Override
    public <K, V> void putToList(K key, V v, int index)
    {
		List<V> list = get(key,List.class);
		if(list==null)
		{
			synchronized (caches)
            {
				list = get(key,List.class);
				if(list==null)
				{
					list = new ArrayList<V>();
					put(key, list);
				}
            }
		}
		synchronized (list)
        {
	        if(index<list.size())
	        {
	        	list.set(index, v);
	        }else
	        {
	        	for(int i=list.size();i<index;i++)
	        	{
	        		list.add(null);
	        	}
	        	list.add(v);
	        }
        }
			
    }
	@Override
    public <K, V> void removeList(K key, V v)
    {
		List<V> list = get(key,List.class);
		if(list!=null)
		{
			synchronized (list)
            {
				list.remove(v);			
            }
		}
    }
	@Override
    public <K, V> V getList(K key, int index,Class<V> cla)
    {
		List<V> list = get(key,List.class);
		if(list!=null)
		{
			synchronized (list)
            {
	            return list.get(index);
            }
		}
	    return null;
    }
	@Override
    public <K, V> List<V> getList(K key,Class<V> cla)
    {
	    return  get(key,List.class);
    }
	@Override
    public <K, V> void putToSet(K key, V v)
    {
		Set<V> list = get(key,Set.class);
		if(list==null)
		{
			synchronized (caches)
            {
				list = get(key,Set.class);
				if(list==null)
				{
					list = new HashSet<V>();
				}
            }
		}
		synchronized (list)
        {
			list.add(v);
        }
    }
	@Override
    public <K, V> Set<V> getSet(K key,Class<V> cla)
    {
	    return get(key,Set.class);
    }
	
	
	
	
	@Override
    public <K, V> long putToList(K key, V[] vs)
    {
	    List<V> list =get(key,List.class);
	    if(list==null)
	    {
	    	synchronized (caches)
            {
	    		list =get(key,List.class);
	            if(list==null)
	            {
	            	list=new ArrayList<V>();
	            	put(key, list);
	            }
	            for(V v:vs)
        	    {
        	    	list.add(v);
        	    }
            }
	    }
	    return list.size();
    }
	@Override
    public <K, V> void putToSet(K key, V[] vs)
    {
		Set<V> list =get(key,Set.class);
	    if(list==null)
	    {
	    	synchronized (caches)
            {
	    		list =get(key,Set.class);
	            if(list==null)
	            {
	            	list=new HashSet<V>();
	            	put(key, list);
	            }
	            for(V v:vs)
        	    {
        	    	list.add(v);
        	    }
            }
	    }
    }
	@Override
    public <K, V> void removeSet(K key, V v)
    {
		Set<V> list = get(key,Set.class);
		if(list!=null)
		{
			list.remove(v);
		}
    }
	@Override
    public <K, V> void putToSortedSet(K key, V v, float score)
    {
	    putToSet(key, v);
	    
    }
	@Override
    public <K, V> Set<V> getSortedSet(K key,Class<V> cla)
    {
	    return getSet(key,cla);
    }
	@Override
    public <K, V> void removeSortedSet(K key, V v)
    {
		 removeSet(key, v);
    }

	
	




	private static class ExpireObject
	{
		private   long expireTimeInMills;
		private final Object value;
		public ExpireObject(long expireTimeInMills, Object value)
        {
	        super();
	        this.expireTimeInMills = expireTimeInMills;
	        this.value = value;
        }
		
		public boolean expire()
		{
			return expireTimeInMills>0&&System.currentTimeMillis()>expireTimeInMills;
		}
		
		public void setExpire(long expireTimeInMills)
		{
			this.expireTimeInMills=expireTimeInMills;
		}
		
		@SuppressWarnings("unchecked")
        public <T> T value()
		{
			return (T)value;
		}
		
		

		@Override
        public int hashCode()
        {
	        final int prime = 31;
	        int result = 1;
	        result = prime * result + ((value == null) ? 0 : value.hashCode());
	        return result;
        }

		@Override
        public boolean equals(Object obj)
        {
	        if (this == obj) return true;
	        if (obj == null) return false;
	        if (getClass() != obj.getClass()) return false;
	        ExpireObject other = (ExpireObject) obj;
	        if (value == null)
	        {
		        if (other.value != null) return false;
	        }
	        else
		        if (!value.equals(other.value)) return false;
	        return true;
        }
		
		
		
		
		
		
		public static void main(String[] args) throws Exception 
        {
			LocalCache c = new LocalCache();
			c.put("name", "袁平安");
			System.out.println(c.get("name",String.class));
			c.expiredAfter("name", 2);
			TimeUnit.SECONDS.sleep(1);
			System.out.println(c.get("name",String.class));
        }
	}

}
