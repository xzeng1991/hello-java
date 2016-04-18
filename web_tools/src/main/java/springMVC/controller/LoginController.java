package springMVC.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
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
}
