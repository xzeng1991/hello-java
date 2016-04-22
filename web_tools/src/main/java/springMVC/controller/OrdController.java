package springMVC.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/takeOrderController")
public class OrdController {
	private static Logger log = LoggerFactory.getLogger(OrdController.class);
	
	@RequestMapping(params = "orderTracking")
	public ModelAndView orderTrackingPage(){
		ModelAndView mv = new ModelAndView();
		mv.addObject("searchCode", "");
		mv.addObject("searchType","");
		mv.setViewName("page1");
		return mv;
	}
	
	@RequestMapping(params = "orderTrackingList")
	public ModelAndView orderTrackingList(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		String searchCode = request.getParameter("searchCode");
		String searchType = request.getParameter("searchType");
		
		mv.addObject("searchCode",searchCode);
		mv.addObject("searchType", searchType);
		
		if(searchCode != null && searchCode.length() > 0 && Arrays.asList("1","2","3").contains(searchType)){
			
		}
		mv.setViewName("page1");
		return mv;
	}
}
