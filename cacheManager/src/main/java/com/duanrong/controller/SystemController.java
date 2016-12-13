package com.duanrong.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import base.pagehelper.PageInfo;

import com.duanrong.business.permission.model.NewMenu;
import com.duanrong.business.permission.model.UserCookie;
import com.duanrong.business.sys.model.Black;
import com.duanrong.business.sys.service.BlackService;
import com.duanrong.util.FormUtil;
import com.duanrong.util.RedisCacheKey;
import com.duanrong.util.jedis.DRJedisCacheUtil;
import com.duanrong.util.json.FastJsonUtil;

@Controller
public class SystemController extends BaseController {

	@Resource
	BlackService blackService;
	
	@RequestMapping(value="/")
	public String index(){
		return "index";
	}
	
	/**
	 * 得到用户所拥有的菜单
	 * @param systypeId
	 * @param request
	 * @param response
	 */
	@RequestMapping("/menus")
	public void getMenu(HttpServletRequest request,HttpServletResponse response){
		UserCookie user = getCookie(request, response);		
		List<NewMenu> menus=authService.getMenu(user.getUserId(), pd.readProperty("sysid"));
		log.debug(menus);			
		try {
			response.setCharacterEncoding("UTF-8");
	        response.getWriter().print(FastJsonUtil.objToJson(menus));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	
	@RequestMapping("/sys/toBlackManager")
	public String getBlacks(HttpServletRequest request, Black black){
		String pageNum = request.getParameter("pageNo");
		PageInfo<Black> pageInfo = blackService.pageLite(pageNum == null
				|| pageNum.equals("") ? pageNo : Integer.parseInt(pageNum),
				pageSize, FormUtil.getForParamToBean(black));
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("black", black);
		return "black/blackManager";
	}
	
	
	@RequestMapping("/sys/toAddblack")
	public String toAddBack(){
		return "black/addBlack";
	}
	
		
	@RequestMapping("/sys/black/{otype}")
	public void black(HttpServletRequest request, HttpServletResponse response, 
			Black black, @PathVariable String otype) throws IOException{
		response.setCharacterEncoding(ENCODING);
		Writer out = response.getWriter();
		switch (otype){	
		case "init":	
			black = new Black();
			PageInfo<Black> pageInfo = blackService.pageLite(1, 100, black);
			int pageNo = 1;
			int pageTotal = pageInfo.getTotalPage();
			while(pageNo <= pageTotal){
				pageInfo = blackService.pageLite(pageNo, 100, black);
				List<Black> blacks = pageInfo.getResults();
				String[] b = new String[pageInfo.getPageSize()];
				for(int i = 0; i < blacks.size(); i++){
					b[i] = blacks.get(i).getContent();
				}
				DRJedisCacheUtil.sadd(RedisCacheKey.SYS_REQ_BLACKS, b);
				pageNo++ ;
			}	
			out.write("init success！！");
			break;
		case "add":
			try{
				blackService.insert(black);
				DRJedisCacheUtil.sadd(RedisCacheKey.SYS_REQ_BLACKS, black.getContent());				
				out.write("success");
			}catch(Exception e){
				e.printStackTrace();
				out.write("error");
			}
			break;
		
		case "del":
			try{
				blackService.delete(""+black.getId());
				DRJedisCacheUtil.srem(RedisCacheKey.SYS_REQ_BLACKS, black.getContent());
				out.write("success");
			}catch(Exception e){
				e.printStackTrace();
				out.write("error");
			}			
			break;
		case "req":
			String num = DRJedisCacheUtil.hget(RedisCacheKey.SYS_REQ_NUM, black.getContent()).get(black.getContent());
			out.write(num == null ? "0" : num);
			break;
		case "clear":
			DRJedisCacheUtil.hdel(RedisCacheKey.SYS_REQ_NUM, black.getContent());
			out.write("clear success!!");
			break;
		default:				
		}
	}
}
