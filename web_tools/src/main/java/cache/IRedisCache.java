package cache;

import java.util.List;
import java.util.Map;

public abstract interface IRedisCache extends ICache {
	public abstract void hput(String paramString1, String paramString2,
			String paramString3) throws CacheException;

	public abstract String hget(String paramString1, String paramString2)
			throws CacheException;

	public abstract List<String> hmget(String paramString,
			String[] paramArrayOfString);

	public abstract Map<String, String> hgetAll(String paramString);

	public abstract void hremove(String paramString, String[] paramArrayOfString);

	public abstract Long incr(String paramString);

	public abstract Long incr(String paramString, int paramInt);

	public abstract Long incrBy(String paramString, long paramLong);

	public abstract Long incrBy(String paramString, long paramLong, int paramInt);

	public abstract Double incrByFloat(String paramString, double paramDouble);

	public abstract Double incrByFloat(String paramString, double paramDouble,
			int paramInt);

	public abstract Long expire(String paramString, int paramInt);
}