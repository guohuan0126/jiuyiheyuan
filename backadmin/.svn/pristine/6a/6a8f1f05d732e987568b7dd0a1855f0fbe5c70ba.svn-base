package com.duanrong.newadmin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import util.MyStringUtils;

import com.duanrong.business.flow.model.Flow;
import com.duanrong.business.flow.model.FlowNode;
import com.duanrong.business.flow.service.FlowService;
import com.duanrong.business.user.model.Role;
import com.duanrong.business.user.service.RoleService;
import com.duanrong.newadmin.model.UserCookie;


@Controller
public class FlowController extends BaseController {

	@Resource
	FlowService flowService;

	@Resource
	RoleService roleService;
	
	/**
	 * 流程列表
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/flow/getFlowList")
	public String getFlowList(HttpServletResponse response,
			HttpServletRequest request){
	
		UserCookie getLoginUser = GetLoginUser(request, response);
		if (getLoginUser == null) {
			return "user/login";
		} else {
			String pageNo = request.getParameter("pageNo");
			if(MyStringUtils.isAnyBlank(pageNo)){
				pageNo = "1";
			}
			request.setAttribute("pageInfo", flowService.readAllFlow(Integer.parseInt(pageNo), 10, new Flow()));
			request.setAttribute("url", "/flow/getFlowList");
			return "flow/flowList";
		}		
	}
	
	/**
	 * 获取角色
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "/flow/getRoles", method = RequestMethod.GET)
	public void getRoles(HttpServletResponse response,
			HttpServletRequest request){	
			List<Role> roles = roleService.readAllRoles();			
			JSONArray jsonArr = JSONArray.fromObject(roles);
			try {
				
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print(jsonArr.toString());
			} catch (IOException e) {				
				e.printStackTrace();
			}
				
	}
	
	/**
	 * 跳转到添加页面
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/flow/toAddFlow")
	public String toAddFlow(HttpServletResponse response,
			HttpServletRequest request){
		UserCookie getLoginUser = GetLoginUser(request, response);
		if (getLoginUser == null) {
			return "user/login";
		} else {
			return "flow/addFlow";
		}		
	}
	
	/**
	 * 创建流程
	 * @param response
	 * @param request
	 * @throws IOException 
	 */
	@RequestMapping(value = "/flow/addFlow", method = RequestMethod.POST)
	public void addFlow(HttpServletResponse response,
			HttpServletRequest request) throws IOException{	
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String nodes = request.getParameter("nodes");
		if(MyStringUtils.isNotAnyBlank(nodes)){				
			Flow flow = new Flow();
			flow.setFlowName(name);
			flow.setDescription(description);
			flow.setStatus(1);
			if(nodes.substring(nodes.length()-1).equals(";")){				
				nodes = nodes.substring(0, nodes.length()-1);														
			}			
			try{
				flow.setNodes(toNodeFromStr(nodes));
				flowService.addFlow(flow);
				response.getWriter().print("ok");			
			}catch(Exception e){
				e.printStackTrace();
				response.getWriter().print("error");
			}
		}	
	}
	
	
	private List<FlowNode> toNodeFromStr(String nodes){		
		String[] flowNodes = nodes.split(";");
		List<FlowNode> list = new ArrayList<>();
		for(int i=0; i<flowNodes.length; i++){				
			String[] node = flowNodes[i].split("、");					
			FlowNode flowNode = new FlowNode();
			flowNode.setNodeId(Integer.parseInt(node[0]));
			flowNode.setNodeName(node[1]);
			flowNode.setStatus(1);
			flowNode.setNodeOrder(Integer.parseInt(node[2]));
			flowNode.setDescription(node[4]);
			List<Role> roles = new ArrayList<Role>();
			String[] roleIds = node[3].trim().split(",");
			for(String roleId : roleIds){
				Role role = new Role();
				role.setId(roleId);
				roles.add(role);
			}			
			flowNode.setRoles(roles);
			list.add(flowNode);
		}	
		return list;
	}
	
	/**
	 * 查询流程
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/flow/getFlowById")
	public String getFlowById(HttpServletResponse response,
			HttpServletRequest request){
	
		UserCookie getLoginUser = GetLoginUser(request, response);
		if (getLoginUser == null) {
			return "user/login";
		} else {
			String flowId = request.getParameter("flowId");
			if(MyStringUtils.isNotAnyBlank(flowId)){
				
				Flow flow = flowService.readFlowByFlowId(Integer.parseInt(flowId));
				List<FlowNode> nodes = flow.getNodes();
				JSONArray jsonNodes = JSONArray.fromObject(nodes);
				List<Role> roles = roleService.readAllRoles();			
				JSONArray jsonRole = JSONArray.fromObject(roles);
				request.setAttribute("flow", flow);
				request.setAttribute("jsonNodes", jsonNodes);
				request.setAttribute("roles", roles);
				request.setAttribute("jsonRole", jsonRole);
			}				
			return "flow/editFlow";
		}		
	}
	
	/**
	 * 获取流程详细信息
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "/flow/getFlowNodeByFlowId")
	public void getFlowNodeByFlowId(HttpServletResponse response,
			HttpServletRequest request) throws IOException{					
			String flowId = request.getParameter("flowId");
			if(MyStringUtils.isNumeric(flowId)){			
				List<FlowNode> nodes = flowService.readFlowByFlowId(Integer.parseInt(flowId)).getNodes();
				JSONArray jsonNode = JSONArray.fromObject(nodes);
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print(jsonNode.toString());
			}						
	}
	
	/**
	 * 修改流程
	 * @param response
	 * @param request
	 * @throws IOException 
	 */
	@RequestMapping(value = "/flow/updateFlow", method = RequestMethod.POST)
	public void updateFlow(HttpServletResponse response,
			HttpServletRequest request) throws IOException{	
		String flowId = request.getParameter("flowId");
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String nodes = request.getParameter("nodes");
		if(MyStringUtils.isNumeric(flowId)){
			if(MyStringUtils.isNotAnyBlank(nodes)){				
				Flow flow = new Flow();
				flow.setFlowId(Integer.parseInt(flowId));
				flow.setFlowName(name);
				flow.setDescription(description);
				flow.setStatus(1);
				if(nodes.substring(nodes.length()-1).equals(";")){				
					nodes = nodes.substring(0, nodes.length()-1);														
				}
				flow.setNodes(toNodeFromStr(nodes));	
				try{
					flowService.updateFlow(flow);
					response.getWriter().print("ok");			
				}catch(Exception e){
					e.printStackTrace();
					response.getWriter().print("error");
				}
			}
		}else{
			response.getWriter().print("error");
		}
	}
	
	
	/**
	 * 删除流程
	 * @param response
	 * @param request
	 * @throws IOException 
	 */
	@RequestMapping(value = "/flow/deleteFlowById")
	public void deleteFlowById(HttpServletResponse response,
			HttpServletRequest request) throws IOException{		
		String flowId = request.getParameter("flowId");
		if(MyStringUtils.isNumeric(flowId)){			
			if(flowService.deleteFlowById(Integer.parseInt(flowId))){
				response.getWriter().print("ok");
			}else{
				response.getWriter().print("error");
			}				
		}else{
			response.getWriter().print("error");
		}				
	}
}
