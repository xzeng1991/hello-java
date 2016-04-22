package base.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import base.entity.DataGrid;
import base.entity.SecUser;

public class TestTagUtil {
	private Logger log = LoggerFactory.getLogger(TestTagUtil.class);
	
	@Test
	public void testBuildJsonStr(){
		List results = new ArrayList();
		SecUser user = new SecUser();
		user.setUserId(111L);
		user.setRealName("xzeng");
		user.setDepartId(222L);
		user.setUserName("wolgequ");
		user.setStationId(3333L);
		results.add(user);
		
		DataGrid dg = new DataGrid();
		dg.setField("userId,userName,departId,realName,stationId");
		dg.setTotal(1);
		dg.setResults(results);
		
		String jsonStr = TagUtil.buildJsonStr(dg);
		
		log.info("result:{}", jsonStr);
	}
}
