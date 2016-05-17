package cache;

import java.util.List;


public abstract interface ICacheFactory {
	public abstract ICache createCache(List<ServerAddress> paramList);
}
