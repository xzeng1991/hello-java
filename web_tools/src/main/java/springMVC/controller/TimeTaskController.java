package springMVC.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import springMVC.entity.CfgTask;
import base.entity.DataGrid;
import base.util.TagUtil;

@Controller
@RequestMapping("/timeTaskController")
public class TimeTaskController {
	Logger log = LoggerFactory.getLogger(TimeTaskController.class);
	
	@RequestMapping(params = "timeTask")
	public ModelAndView timeTask(HttpServletRequest request) {
		return new ModelAndView("timetask/timeTaskList");
	}
	
	@RequestMapping(params = "datagrid")
	public void datagrid(CfgTask task,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		
		List<CfgTask> list = new ArrayList<CfgTask>();
		dataGrid.setResults(list);
		dataGrid.setTotal(0);
		
		String result = TagUtil.buildJsonStr(dataGrid);

		try {
			PrintWriter pw = response.getWriter();
			pw.write(result);
			pw.flush();
		} catch (IOException e) {
			log.info("Exception:{}", e.getMessage());
		}
	}
}
