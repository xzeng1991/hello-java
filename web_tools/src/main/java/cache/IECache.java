package cache;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

public interface IECache
{
	
	<K,V> V get(K key,Class<V> cla);
	
	<K,V> boolean put(K key,V value);
	
	<K,V> boolean put(K key,V value,int expire);
	
	<K,V> boolean putNe(K key,V value);
	
	<K> boolean exists(K key);
	<K> boolean remove(K key);
	
	boolean flush();
	
	boolean flush(Set<String> keys);
	
	<K> boolean expiredAt(K key,Date date);
	/***
	 * 
	* @Title: expiredAfter
	* @Description: seconds
	* @param key
	* @param times  间隔时间，单位秒
	* @return
	* @return:boolean
	* @author: pingan.yuan@gmail.com 
	* @date: 2015年6月2日
	* Modification History:
	* Date  Author  Version  Description
	* ---------------------------------------------------------*
	* 2015年6月2日  pingan.yuan@gmail.com v1.0.0   reason:
	 */
	<K> boolean expiredAfter(K key,long times);
	
	<K,V> List<V>  getBatch(K [] keys,Class<V> cla);
	
	<K> boolean removeBatch(K [] keys);
	
	<G,K,V> Map<K,V> getMap(G mapKey,Class<V> cla);

	<G, K, V> Map<K, V> getMap(G mapKey, Class<K> k, Class<V> cla);

	<K,G,V> V  getMap(G g,K k,Class<V> cla);

	<K,G,V> List<V> getMapList(G g, K k, Class<V> cla);
	
	<K,G,V> List<V>  getMapBatch(G g,K[] ks ,Class<V> cla);
	
	<K,G,V> boolean putMap(G g,K k,V v);
	
	<K,G,V> boolean putMap(G g,Map<K,V> values);
	
//	<K,G,V> boolean putMap(G g,K k,V v,long expired);
	
	<G> boolean removeMap(G g);
	
	<K,G> boolean removeMap(G g,K k);
	
	<K,G> boolean removeMapBatch(G g,K[] k);
	
	 <G,K>  Set<K> mapKeys(G g);
	
	
	<K,G> boolean exists(G g,K k);
	
	
	<K,V> long putToList(K key,V v);
	<K,V> long putToList(K key,V[] v);
	<K,V> void putToList(K key,V v,int index);
	<K,V> void removeList(K key,V v);
	<K,V> V getList(K key,int index,Class<V> cla);
	<K,V> List<V> getList(K key,Class<V> cla);
	
	<K,V> void putToSet(K key,V v);
	<K,V> Set<V> getSet(K key,Class<V> cla); 
	<K,V> void removeSet(K key,V v);
	
	<K,V> void putToSet(K key,V[] vs);
	
	<K,V> void putToSortedSet(K key,V v,float score);
	<K,V> Set<V> getSortedSet(K key,Class<V> cla);
	<K,V> void removeSortedSet(K key,V v);
	
	boolean persistence();
	
	
	
	
}
