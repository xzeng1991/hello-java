package base.util.excel;

import java.util.List;
import java.util.Map;

/**
 * Created by haijun.zou on 2015-07-16.
 */
public class ExcelParam {
    private List<?> list;
    private Map<String,String> map;
    private String name;
    private Map<String,Map<Object,Object>> replaceValues;

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public Map<String, Map<Object, Object>> getReplaceValues()
	{
		return replaceValues;
	}

	public void setReplaceValues(Map<String, Map<Object, Object>> replaceValues)
	{
		this.replaceValues = replaceValues;
	}

}
