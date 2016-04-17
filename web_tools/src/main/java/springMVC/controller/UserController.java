package springMVC.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMethod;
import springMVC.entity.User;

@Controller
@RequestMapping("/user")
public class UserController {
	private final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping(value="/urlMap",method=RequestMethod.GET)
	public String urlMap(){
		log.info("======== url Mapping ========");
		log.info("======== url Mapping end ========");
		return "success";
	}
	
	@RequestMapping(value="/urlMap",method=RequestMethod.POST)
	public ModelAndView methodMap(){
		log.info("======== request method Mapping ========");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("success");
		log.info("======== request method Mapping end ========");
		return mv;
	}
	
	@RequestMapping(params="userId")
	public ModelAndView paramMap(){
		log.info("======== params Mapping ========");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("success");
		log.info("======== params Mapping end ========");
		return mv;
	}
	
	@RequestMapping(headers="content-type=text/*")
	public ModelAndView headerMap(){
		log.info("======== header Mapping ========");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("success");
		log.info("======== header Mapping end ========");
		return mv;
	}
}
