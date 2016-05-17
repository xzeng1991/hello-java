package cache;

import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CacheActionConsumer implements Runnable {
	private static final Logger logger = LoggerFactory
			.getLogger(CacheActionConsumer.class);
	Queue actionQueue;

	public CacheActionConsumer(Queue actionQueue) {
		this.actionQueue = actionQueue;
	}

	public void run() {
		CacheAction ca = (CacheAction) this.actionQueue.poll();
		logger.info("async:[" + ca.getAction() + "]");
		String action = ca.getAction();
		if ("write".equals(action))
			writeCache(ca);
		else if ("remove".equals(action))
			removeCache(ca);
	}

	void writeCache(CacheAction ca) {
		IMemcachedCache cache = ca.getCache();
		if (cache != null)
			cache.oput(ca.getKey(), ca.getValue());
	}

	void removeCache(CacheAction ca) {
		IMemcachedCache cache = ca.getCache();
		if (cache != null)
			try {
				cache.remove(ca.getKey());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}