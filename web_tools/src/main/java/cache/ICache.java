package cache;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

public interface ICache<K,V> extends Serializable
{
	/**
	 * 
	* @Title: get
	* @Description: get the value
	* @param key
	* @return
	* @return:T
	* @author: pingan.yuan@gmail.com 
	* @date: 2015年2月25日
	* Modification History:
	* Date  Author  Version  Description
	* ---------------------------------------------------------*
	* 2015年2月25日  pingan.yuan@gmail.com v1.0.0   reason:
	 */
     V get(K key)throws Exception;
     /**
      * 
     * @Title: preLoad
     * @Description: load the cache when start up
     * @return
     * @throws Exception
     * @return:boolean
     * @author: pingan.yuan@gmail.com 
     * @date: 2015年2月25日
     * Modification History:
     * Date  Author  Version  Description
     * ---------------------------------------------------------*
     * 2015年2月25日  pingan.yuan@gmail.com v1.0.0   reason:
      */
     void load() throws Exception;
     
     
     /**
      * 
     * @Title: load
     * @Description: load one key from lower layer when not exists at the memory cache.
     * @param key
     * @throws Exception
     * @return:void
     * @author: pingan.yuan@gmail.com 
     * @date: 2015年2月25日
     * Modification History:
     * Date  Author  Version  Description
     * ---------------------------------------------------------*
     * 2015年2月25日  pingan.yuan@gmail.com v1.0.0   reason:
      */
     V load(K key)throws Exception;
     
     /**
      * 
     * @Title: put
     * @Description: put the key with value to cache.maybe update or add
     * @param key
     * @return
     * @throws Exception
     * @author: pingan.yuan@gmail.com 
     * @date: 2015年2月25日
     * Modification History:
     * Date  Author  Version  Description
     * ---------------------------------------------------------*
     * 2015年2月25日  pingan.yuan@gmail.com v1.0.0   reason:
      */
     V put(K key,V value)throws Exception;
     
     /**
      * 
     * @Title: flush
     * @Description: persistence
     * @param key
     * @param value
     * @return
     * @throws Exception
     * @return:boolean
     * @author: pingan.yuan@gmail.com 
     * @date: 2015年2月25日
     * Modification History:
     * Date  Author  Version  Description
     * ---------------------------------------------------------*
     * 2015年2月25日  pingan.yuan@gmail.com v1.0.0   reason:
      */
     boolean flush(K key,V value)throws Exception;
     
     
     boolean exists(K key)throws Exception;
     
     /**
     * @Title: name
     * @Description: cache name.commonly used the classname. 
     * @return
     * @return:String
     * @author: pingan.yuan@gmail.com 
     * @date: 2015年2月25日
     * Modification History:
     * Date  Author  Version  Description
     * ---------------------------------------------------------*
     * 2015年2月25日  pingan.yuan@gmail.com v1.0.0   reason:
      */
     String name();
     
     
    /**
     *  
    * @Title: replace
    * @Description: update.
    * @param key
    * @param newValue
    * @return
    * @return:V
    * @author: pingan.yuan@gmail.com 
    * @date: 2015年2月25日
    * Modification History:
    * Date  Author  Version  Description
    * ---------------------------------------------------------*
    * 2015年2月25日  pingan.yuan@gmail.com v1.0.0   reason:
     */
 	V replace(K key,V newValue)throws Exception;
 	
 	/**
 	 * 
 	* @Title: clear
 	* @Description:clear the flush
 	* @return:void
 	* @author: pingan.yuan@gmail.com 
 	* @date: 2015年2月25日
 	* Modification History:
 	* Date  Author  Version  Description
 	* ---------------------------------------------------------*
 	* 2015年2月25日  pingan.yuan@gmail.com v1.0.0   reason:
 	 */
 	void clear()throws Exception;
 	
 	
 	void remove(K key) throws Exception;
 	
 	/**
 	 * 
 	* @Title: size
 	* @Description:memory cache size
 	* @return
 	* @return:int
 	* @author: pingan.yuan@gmail.com 
 	* @date: 2015年2月25日
 	* Modification History:
 	* Date  Author  Version  Description
 	* ---------------------------------------------------------*
 	* 2015年2月25日  pingan.yuan@gmail.com v1.0.0   reason:
 	 */
 	int size()throws Exception;
 	
 	Set<K> keys()throws Exception;
 	
 	Collection<V> values()throws Exception;
}
