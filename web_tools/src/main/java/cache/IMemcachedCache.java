package cache;

import java.util.Collection;
import java.util.Map;

public abstract interface IMemcachedCache extends ICache {
	public abstract void oput(String paramString, Object paramObject)
			throws CacheException;

	public abstract void oput(String paramString, int paramInt,
			Object paramObject) throws CacheException;

	public abstract Object oget(String paramString) throws CacheException;

	public abstract Map<String, Object> omget(Collection<String> paramCollection)
			throws CacheException;
}