package cache;

public abstract class AbstractCacheFactory implements ICacheFactory {
	protected String clientType;

	public AbstractCacheFactory() {
	}

	public AbstractCacheFactory(String clientType) {
		this.clientType = clientType;
	}

	public String getClientType() {
		return this.clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
}