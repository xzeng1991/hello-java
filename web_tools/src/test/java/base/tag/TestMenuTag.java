package base.tag;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import base.easyui.tag.MenuTag;
import base.entity.SecFunction;

public class TestMenuTag {
	Logger log = LoggerFactory.getLogger(TestMenuTag.class);
	
	@Test
	public void testBuildLeafMenu(){
		MenuTag menuTag = new MenuTag();
		SecFunction fun = new SecFunction();
		fun.setFunctionId(123456L);
		fun.setFunctionLevel(1);
		fun.setFunctionName("菜单一");
		fun.setFunctionUrl("http://localhost:8080/index/page.htm?");
		
		String tag = null;//menuTag.buildLeafMenu(fun);
		
		log.info(tag);
	}
}
