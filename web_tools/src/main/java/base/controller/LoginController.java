package base.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import base.entity.SecFunction;

/**
 * 登录控制器
 * @author xing.zeng
 * create time :2016年4月18日
 */
@Controller
@RequestMapping("/loginController")
public class LoginController {
	private final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	/**
	 * 处理登录请求
	 * add by xzeng 2016年4月18日
	 * @param request
	 * @param resp
	 * @return
	 */
	@RequestMapping(params = "login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse resp) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("main/main");
		return mv;
	}
	
	/**
	 * 返回菜单页面
	 * add by xzeng 2016年4月18日
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "left")
	public ModelAndView left(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		// 查询出菜单列表
		List<SecFunction> menuList = querySecFunctionAll();
		// 组装层级菜单
		Map<Integer,List<SecFunction>> menuMap = buildLevelMenuMap(menuList);
		
		mv.addObject("menuMap", menuMap);
		mv.setViewName("main/left");
		return mv;
	}
	
	/**
	 * 返回主页面
	 * add by xzeng 2016年4月18日
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "home")
	public ModelAndView home(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("main/home");
		return mv;
	}
	
	/**
	 * 将功能菜单通过级别区分
	 * add by xzeng 2016年4月18日
	 * @param funList
	 * @return
	 */
	private Map<Integer, List<SecFunction>> buildLevelMenuMap(List<SecFunction> funList){
		Map<Integer, List<SecFunction>> functionMap = new LinkedHashMap<Integer, List<SecFunction>>();
		for (SecFunction function : funList) {
			if (!functionMap.containsKey(function.getFunctionLevel())) {
				functionMap.put(function.getFunctionLevel(),new ArrayList<SecFunction>());
			}
			functionMap.get(function.getFunctionLevel()).add(function);
		}
		return functionMap;
	}
	
	private List<SecFunction> querySecFunctionAll(){
		List<SecFunction> funList = new ArrayList<SecFunction>();
		// ======= 一级菜单 =========
		SecFunction secFun = new SecFunction();
		secFun.setFunctionId(1L);
		secFun.setFunctionLevel(0);
		secFun.setFunctionName("系统管理");
		secFun.setFunctionUrl("");
		funList.add(secFun);
		
		secFun = new SecFunction();
		secFun.setFunctionId(2L);
		secFun.setFunctionLevel(0);
		secFun.setFunctionName("统计报表");
		secFun.setFunctionUrl("");
		funList.add(secFun);
		
		// ======== 二级菜单 （一）=============
		secFun = new SecFunction();
		secFun.setFunctionId(11L);
		secFun.setParentFunctionId(1L);
		secFun.setFunctionLevel(1);
		secFun.setFunctionName("用户管理");
		secFun.setFunctionUrl("/web_tools/user.htm?userManager");
		funList.add(secFun);
		
		secFun = new SecFunction();
		secFun.setFunctionId(12L);
		secFun.setParentFunctionId(1L);
		secFun.setFunctionLevel(1);
		secFun.setFunctionName("菜单管理");
		secFun.setFunctionUrl("/web_tools/user.htm?userManager");
		funList.add(secFun);
		
		secFun = new SecFunction();
		secFun.setFunctionId(111L);
		secFun.setParentFunctionId(1L);
		secFun.setFunctionLevel(1);
		secFun.setFunctionName("订单追踪");
		secFun.setFunctionUrl("/web_tools/takeOrderController.htm?orderTracking");
		funList.add(secFun);
		
		// ======== 二级菜单 （二）=============
		secFun = new SecFunction();
		secFun.setFunctionId(21L);
		secFun.setParentFunctionId(2L);
		secFun.setFunctionLevel(1);
		secFun.setFunctionName("报表一");
		secFun.setFunctionUrl("reportController.htm?loadSecondLevelStationReport");
		funList.add(secFun);
		
		secFun = new SecFunction();
		secFun.setFunctionId(12L);
		secFun.setParentFunctionId(1L);
		secFun.setFunctionLevel(1);
		secFun.setFunctionName("一级菜单（2）");
		secFun.setFunctionUrl("");
		
		funList.add(secFun);
		
		return funList;
	}
}
