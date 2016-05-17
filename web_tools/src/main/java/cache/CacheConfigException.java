package cache;

public class CacheConfigException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CacheConfigException(String msg) {
		super(msg);
	}

	public CacheConfigException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
