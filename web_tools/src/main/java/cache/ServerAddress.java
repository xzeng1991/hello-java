package cache;

public class ServerAddress {
	private String host;
	private int port;
	private String weight;
	private String initConn;
	private String minConn;
	private String maxConn;

	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return this.port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getWeight() {
		return this.weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getInitConn() {
		return this.initConn;
	}

	public void setInitConn(String initConn) {
		this.initConn = initConn;
	}

	public String getMinConn() {
		return this.minConn;
	}

	public void setMinConn(String minConn) {
		this.minConn = minConn;
	}

	public String getMaxConn() {
		return this.maxConn;
	}

	public void setMaxConn(String maxConn) {
		this.maxConn = maxConn;
	}
}