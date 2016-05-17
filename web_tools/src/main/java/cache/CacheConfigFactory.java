package cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheConfigFactory {
	private static final Logger log = LoggerFactory
			.getLogger(CacheConfigFactory.class);

	private static String ROUTER_TYPE_DEFAULT = "static";

	private static String ROUTER_TYPE_STATIC = "static";
	private static String ROUTER_TYPE_DYNAMIC = "dynamic";

	public static ICacheConfig getCacheRouter(String routerType) {
		if ((routerType == null) || ("".equals(routerType))) {
			routerType = ROUTER_TYPE_DEFAULT;
		}
		if (routerType.equals(ROUTER_TYPE_STATIC))
			return new LocalCacheConfig();
		if (routerType.equals(ROUTER_TYPE_DYNAMIC)) {
			return new Zookeeper2CacheConfig();
		}
		throw new CacheConfigException("不支持该路由信息配置方式.");
	}
}