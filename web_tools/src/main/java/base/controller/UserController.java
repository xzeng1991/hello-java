package base.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import base.entity.DataGrid;
import base.entity.SecUser;
import base.util.TagUtil;

@Controller
@RequestMapping("/user")
public class UserController {
	private final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping(params = "userManager")
	public ModelAndView userManager() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/userList");
		return mv;
	}
	
	/**
	 * easyuiAJAX用户列表请求数据 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(SecUser user, HttpServletResponse response, DataGrid dataGrid) {
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		
		List<SecUser> list = queryUserList();	// 查询用户列表
		int total = 2;	// 数据总数
		dataGrid.setResults(list);
		dataGrid.setTotal(total);
		
		String result = TagUtil.buildJsonStr(dataGrid);
		
		try {
			PrintWriter pw = response.getWriter();
			pw.write(result);
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private List<SecUser> queryUserList(){
		List results = new ArrayList();
		
		SecUser user = new SecUser();
		user.setUserId(111L);
		user.setRealName("xzeng");
		user.setDepartId(222L);
		user.setUserName("wolgequ");
		user.setStationId(3333L);
		results.add(user);
		
		user = new SecUser();
		user.setUserId(112L);
		user.setRealName("afsdfs");
		user.setDepartId(222L);
		user.setUserName("fasfda");
		user.setStationId(3333L);
		results.add(user);
		
		return results;
	}
}
