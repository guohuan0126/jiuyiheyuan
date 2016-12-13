package com.duanrong.newadmin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import util.Log;
import util.MyStringUtils;
import base.pagehelper.PageInfo;

import com.duanrong.business.permission.model.NewMenu;
import com.duanrong.business.permission.model.Permission;
import com.duanrong.business.permission.service.NewMenuService;
import com.duanrong.business.permission.service.PermissionService;
import com.duanrong.newadmin.model.UserCookie;




/**
 * 
 * @author wangjing
 * @date 2014-9-1下午5:44:28
 */
@Controller
public class MenuController extends BaseController {
	
	
	@Resource
	NewMenuService menuService;
	
	@Resource
	PermissionService permissionService;
	
	@Resource
	Log log;
	/**
	 * 
	 * @description 跳转菜单页面
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/newMenu/menuList")
	public String toMenu(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		
		List<NewMenu> mList=new ArrayList<NewMenu>(); //第一级菜单		
		List<Integer> arrays=new ArrayList<>(); //菜单显示顺序		
		List<NewMenu> menuList=new ArrayList<>(); //菜单显示顺序						
		List<NewMenu> list=menuService.readAllNewMenu(1,0, new NewMenu()).getResults(); //全部菜单集合
		
		//遍历第一级菜单
		if(!list.isEmpty()){
			for(NewMenu m : list){
				if(m.getParentId()==0){	
					mList.add(m);
					menuList.add(m);
					arrays.add(0);
					getMenusView(list, menuList, arrays, m.getId());
				}
			}	
		}
		
		model.addAttribute("url", "/newMenu/menuList");
		model.addAttribute("menuList", menuList);
		model.addAttribute("arrays", arrays);
		return "menu/menuList";
	}
		
	//递归封装资源数据
    private void getMenusView(List<NewMenu> list, List<NewMenu> menuList,List<Integer> arrays, int id){ 
    	List<NewMenu> n = new ArrayList<>();	
		for(NewMenu nm : list){					
			if(nm.getParentId() == id){						
				menuList.add(nm);
				arrays.add(getIndexMenuList(menuList, id));
				n.add(nm);
				getMenusView(list, menuList, arrays, nm.getId());
			}
		}			   	
    }
    
    private int getIndexMenuList(List<NewMenu> menuList, int menuId){   	
    	if(!menuList.isEmpty()){
    		for(int i = 0; i < menuList.size(); i++ ){  		
    			if(menuId == menuList.get(i).getId()){
    				return i+1;
    			}   			
    		}
    	}
    	return 0;
    }
	
	/**
	 * 
	 * @description 跳转添加页面
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	 @RequestMapping(value = "/newMenu/{type}")
	public String toAdd(HttpServletRequest request,
			HttpServletResponse response, Model model,@PathVariable String type) {		
		PageInfo<Permission> pageData=permissionService.readPageLite(1,0, new Permission());
		PageInfo<NewMenu> mpageData=menuService.readAllNewMenu(1,0, new NewMenu());
		List<NewMenu> mList=new ArrayList<NewMenu>();
		List<NewMenu> list=mpageData.getResults();		
		//遍历第一级菜单
				if(!list.isEmpty()){
					for(NewMenu m : list){
						if(m.getParentId()==0){	
							mList.add(m);
							getMenus(list, mList, m.getId());
						}
					}	
				}
				List<Permission> lists=pageData.getResults();
				List<Permission> meList=new ArrayList<Permission>();
				List<Permission> aList=new ArrayList<Permission>();
				for(Permission p:lists){
					if(p!=null && p.getType()!=null && p.getType().equals("menu")){
						meList.add(p);
					}
					if(p!=null && p.getType()!=null && p.getType().equals("active")){
						aList.add(p);
					}
				}
		model.addAttribute("url", "/newMenu/menuList");
		model.addAttribute("pageData", pageData);
		model.addAttribute("mList", mList);
		if("toedit".equals(type)){
			String newMenuId = request.getParameter("id");
			NewMenu menu=menuService.readNewMenuById(Integer.parseInt(newMenuId));
			List<Permission> plist=permissionService.readPermissionByMenuId(Integer.parseInt(newMenuId));
			String str="";int count=0;
			for(Permission p:plist){
				count++;
				str+=p.getId();
				if(count!=list.size()){
					str+=",";
				}
			}
			model.addAttribute("menu", menu);
			model.addAttribute("pids", str);
			model.addAttribute("addUrl", "/menu/editmenu");
			
		}else if("tocadd".equals(type)){
			String mtype = request.getParameter("type");
			String mid = request.getParameter("id");
			model.addAttribute("mid",mid);
			model.addAttribute("menu", new NewMenu());
			//model.addAttribute("type",mtype);
			model.addAttribute("addUrl", "/menu/addcmenu");
		}else{
			model.addAttribute("addUrl", "/menu/addmenu");
			model.addAttribute("menu", new NewMenu());
		}
		model.addAttribute("meList", meList);
		model.addAttribute("aList", aList);
		return "menu/addMenu";
	}
	
	/**
	 * 
	 * @description 跳转添加页面
	 * @author wangjing
	 * @time 2014-11-22 下午2:55:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/menu/{item}",method = RequestMethod.POST)
	public void addMenu(HttpServletRequest request,
			HttpServletResponse response, Model model,@PathVariable String item) {
		
		try {			
			String menuName = request.getParameter("menuName");
			String parentId = request.getParameter("pno");
			String status = request.getParameter("bsta");
			String type = request.getParameter("bct");
			String order = request.getParameter("od");
			String url = request.getParameter("cl");
			
			NewMenu menu=new NewMenu();			
			menu.setMenuName(menuName);
			menu.setOrder(Integer.parseInt(order));
			menu.setParentId(Integer.parseInt(parentId));
			menu.setStatus(Integer.parseInt(status));
			menu.setType(type);
			menu.setUrl(url);
			String pid = request.getParameter("pid");
			String str = request.getParameter("ids");
			String[] ids = str.split(","); 
			List<Permission> permissions=new ArrayList<Permission>();			
			for(String id : ids){
				Permission permis=new Permission();
				permis.setId(id);
				permissions.add(permis);
			}
			if("editmenu".equals(item)){
				menu.setId(Integer.parseInt(pid));
				boolean f=menuService.alertNewMenu(menu);
				if(f==false){
					response.getWriter().print("fail");
				}else{
					permissionService.grantMenuPermission(menu, permissions);
					request.getSession().removeAttribute("menus");
					request.getSession().removeAttribute("menu");
					response.getWriter().print("ok");
				}
				
			}else{
				boolean f=menuService.addNewMenu(menu);
				if(f==false){
					response.getWriter().print("fail");
				}else{
					permissionService.grantMenuPermission(menu, permissions);
					request.getSession().removeAttribute("menus");
					request.getSession().removeAttribute("menu");
					response.getWriter().print("ok");
				}
			}
		}catch (Exception e) {
			//e.printStackTrace();
			log.errLog("com.duanrong.newadmin.controller.MenuController.addMenu().Exception", e);
		}
	}
	
	
	/**
	 * 
	 * @description 获得菜单
	 * @author wangjing
	 * @time 2014-11-24 下午12:03:40
	 * @param getLoginUser
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/newMenu/getMenuView",method = RequestMethod.GET)
	public void getMenu(HttpServletRequest request,
			HttpServletResponse response, Model model) throws IOException{
		UserCookie getLoginUser = GetLoginUser(request, response);
		if (getLoginUser == null) {
			response.sendRedirect("user/login");
		}
		List<NewMenu> menus = new ArrayList<>();
		
		if(request.getSession().getAttribute("menu") == null){
			menus = menuService.readNewMenuByUserId(getLoginUser.getUserId());	
			request.getSession().setAttribute("menu", menus); 			
		}else{
			menus = (List<NewMenu>) request.getSession().getAttribute("menu");
		}		
		try {
			response.setCharacterEncoding("utf-8");
			JSONArray jsonArr = JSONArray.fromObject(menus);
			response.getWriter().print(jsonArr.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 删除菜单
	 * @param response
	 * @param request
	 * @throws IOException 
	 */
	@RequestMapping(value = "/newMenu/todel")
	public void deleteMenuById(HttpServletResponse response,
			HttpServletRequest request) throws IOException{		
		String id = request.getParameter("id");
		if(MyStringUtils.isNumeric(id)){			
			if(menuService.deleteNewMenuById(Integer.parseInt(id))){
				request.getSession().removeAttribute("menus");
				request.getSession().removeAttribute("menu");
				response.getWriter().print("ok");
			}else{
				response.getWriter().print("error");
			}				
		}else{
			response.getWriter().print("error");
		}				
	}
	//递归封装资源数据
    private void getMenus(List<NewMenu> list, List<NewMenu> menuList, int id){ 
		for(NewMenu nm : list){					
			if(nm.getParentId() == id && nm.getType().equals("menu")){					
				menuList.add(nm);
				getMenus(list, menuList,  nm.getId());
			}
		}			   	
    }
}