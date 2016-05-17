package cache;

import java.util.List;

public abstract interface ICacheConfig {
	public abstract List<ServerAddress> getAddressList(CachePool paramCachePool);
}
