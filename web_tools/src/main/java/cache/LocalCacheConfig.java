package cache;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalCacheConfig implements ICacheConfig {
	private static final Logger log = LoggerFactory
			.getLogger(LocalCacheConfig.class);

	public List<ServerAddress> getAddressList(CachePool connPool) {
		String[] srvs = getSrvs(connPool.getServers());
		int[] ports = getPorts(connPool.getServers());
		String[] weights = getWeis(connPool.getWeights());
		List list = new ArrayList();
		for (int i = 0; i < srvs.length; ++i) {
			ServerAddress fr = new ServerAddress();
			fr.setHost(srvs[i]);
			fr.setPort(ports[i]);
			if ((weights != null) && (weights.length == srvs.length)) {
				fr.setWeight(weights[i]);
			}
			list.add(fr);
		}
		return list;
	}

	public String[] getSrvs(String servers) {
		String[] sArrs = servers.split(";");
		String[] srvs = new String[sArrs.length];
		for (int i = 0; i < sArrs.length; ++i) {
			String srv = sArrs[i].split(":")[0];
			srvs[i] = srv;
		}
		return srvs;
	}

	public int[] getPorts(String servers) {
		String[] sArrs = servers.split(";");
		int[] srvs = new int[sArrs.length];
		for (int i = 0; i < sArrs.length; ++i) {
			String port = sArrs[i].split(":")[1];
			srvs[i] = Integer.parseInt(port);
		}
		return srvs;
	}

	public String[] getWeis(String weights) {
		if (weights != null) {
			String[] sArrs = weights.split(";");
			return sArrs;
		}
		return null;
	}
}