package cache;

import java.sql.Timestamp;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

public class JsonUtils {
    private static SerializeConfig mapping = new SerializeConfig();
    private static String dateFormat;
    static {
        dateFormat = "yyyy-MM-dd HH:mm:ss";
        mapping.put(java.util.Date.class, new SimpleDateFormatSerializer(dateFormat));
        mapping.put(Timestamp.class, new SimpleDateFormatSerializer(dateFormat));
    }
    public static String toString(Object o){
        if (o!=null){
//            return JSON.toJSONString(o,mapping);
            return JSON.toJSONString(o);
        }
        return null;
    }
    public static <T> T toBean(String str,Class<T> t){
        return JSON.parseObject(str,t);
    }
    public static <T> List<T> toListBean(String str,Class<T> t){
        return JSON.parseArray(str,t);
    }
}
