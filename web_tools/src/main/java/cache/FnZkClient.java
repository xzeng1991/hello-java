package cache;

import java.io.UnsupportedEncodingException;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.data.Stat;

public class FnZkClient extends ZkClient {
	public FnZkClient(String serverstring) {
		super(serverstring);
	}

	protected  String readData(String path, Stat stat, boolean watch)
  {
    byte[] data = null;//(byte[])retryUntilConnected(new 1(this, path, stat, watch));

    String result = null;
    try {
      result = new String(data, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new CacheException("不支持的编码格式错误.");
    }
    return result;
  }
}