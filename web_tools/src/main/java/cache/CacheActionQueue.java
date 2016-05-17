package cache;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


public class CacheActionQueue {
	private static Queue<CacheAction> actionQueue = new ConcurrentLinkedQueue();

	public static void add(CacheAction action) {
		actionQueue.add(action);

		new Thread(new CacheActionConsumer(actionQueue)).start();
	}
}