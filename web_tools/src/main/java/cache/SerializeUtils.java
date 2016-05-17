package cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;

public class SerializeUtils {
	
	/**
	 * 序列化
	 * @param obj
	 * @return
	 */
	public static <T> byte[] serialize(T obj) {
		if(obj == null) {
			return null;
		}
		return SerializationUtils.serialize((Serializable) obj);
	}
	
	/**
	 * 反序列化
	 * @param bytes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T unSerialize(byte[] bytes) {
		if(bytes == null) {
			return null;
		}
		return (T) SerializationUtils.deserialize(bytes);
	}

	/**
	 * 序列化List<Object>
	 * @param list
	 * @return bytesList
	 */
	public static <T> List<byte[]> serialize(List<T> list) {
		if (list == null) {
			return null;
		}

		List<byte[]> bytesList = new ArrayList<byte[]>();
		for (Object obj : list) {
			byte[] bytes = serialize(obj);
			bytesList.add(bytes);
		}

		return bytesList;
	}

	/**
	 * 反序列化List<byte[]>
	 * @param bytesList
	 * @return
	 */
	public static <T> Collection<T> unSerialize(Collection<byte[]> bytesList) {
		if (bytesList == null) {
			return null;
		}

		List<T> list = new ArrayList<T>();
		for (byte[] bytes : bytesList) {
			T obj = unSerialize(bytes);
			list.add(obj);
		}

		return list;
	}
}
