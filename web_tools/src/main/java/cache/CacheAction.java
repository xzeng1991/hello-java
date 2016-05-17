package cache;

public class CacheAction {
	private IMemcachedCache cache;
	private String key;
	private int expire;
	private Object value;
	private String action;

	public IMemcachedCache getCache() {
		return this.cache;
	}

	public void setCache(IMemcachedCache cache) {
		this.cache = cache;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getExpire() {
		return this.expire;
	}

	public void setExpire(int expire) {
		this.expire = expire;
	}

	public Object getValue() {
		return this.value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}