package com.sophia.web.search;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sophia.api.BaseController;
import com.sophia.domain.SQLGroup;
import com.sophia.request.QueryRequest;
import com.sophia.request.SQLGroupRequest;
import com.sophia.response.GridResponse;
import com.sophia.response.Response;
import com.sophia.service.SQLGroupService;
import com.sophia.service.SQLIDService;
import com.sophia.web.util.GUID;


@Controller
@RequestMapping(SQLGroupController.module)
public class SQLGroupController extends BaseController{
	@Autowired SQLGroupService sqlGroupService;
	@Autowired SQLIDService sqlidService;
	public static final String module = "/search/sqlgroup";
	
	@RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request, ModelMap result) {
        return new ModelAndView(module +"/index", result);
    }
	
	@RequestMapping("/edit")
    public ModelAndView edit(HttpServletRequest request, ModelMap result) {
        return new ModelAndView(module + "/edit", result);
    }
	
	@ResponseBody
	@RequestMapping(value="/list",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object list(@RequestBody @Valid QueryRequest queryRequest) {
		GridResponse<Map<String,Object>> data = sqlGroupService.list(queryRequest);
		return Response.SUCCESS(data);
	}
	
	@RequestMapping("/selector")
    public ModelAndView selector(HttpServletRequest request, ModelMap result) {
        return new ModelAndView(module + "/selector", result);
    }
	
	@ResponseBody
	@RequestMapping(value="/treeData",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object treeData() {
		return JSONObject.toJSONString(sqlGroupService.getRepository().findAll());
	}
	
	@ResponseBody
	@RequestMapping(value="/save",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object save(@RequestBody @Valid SQLGroupRequest request) {
		SQLGroup target = new SQLGroup();
		BeanUtils.copyProperties(request, target);
		if(StringUtils.isBlank(request.getId())){
			target.setId(GUID.nextId());
		}
		sqlGroupService.save(target);
		return Response.SUCCESS();
	}
	
	@ResponseBody
	@RequestMapping(value="/delete",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public  Object  delete(@RequestBody String param) {
		JSONObject json = JSON.parseObject(param);
		sqlGroupService.getRepository().delete(json.getString("id"));
		return Response.SUCCESS();
	}
	
	@ResponseBody
	@RequestMapping(value="/findById",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object findById(@RequestBody JSONObject row) {
		return Response.SUCCESS(sqlGroupService.findById(row.getString("id")));
	}
}
