package springMVC.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/reportController")
public class ReportController {
	private final Logger log = LoggerFactory.getLogger(ReportController.class);
	
	/**
	 * 中转中心单量报表
	 * add by xzeng 2016-04-07
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "loadSecondLevelStationReport")
	public ModelAndView loadSecondLevelStationReport() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("report");
		return mv;
	}
}
