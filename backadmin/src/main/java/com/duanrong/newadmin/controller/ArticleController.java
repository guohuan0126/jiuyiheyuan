package com.duanrong.newadmin.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import util.Log;
import util.MyStringUtils;
import util.Uploader;
import base.pagehelper.PageInfo;

import com.duanrong.business.link.model.Link;
import com.duanrong.business.link.model.LinkType;
import com.duanrong.business.link.service.LinkService;
import com.duanrong.business.node.model.CategoryTerm;
import com.duanrong.business.node.model.Node;
import com.duanrong.business.node.service.NodeService;
import com.duanrong.newadmin.model.UserCookie;
import com.duanrong.newadmin.utility.DateUtil;
import com.duanrong.newadmin.utility.FormUtil;
import com.duanrong.util.IdGenerator;


@Controller
public class ArticleController extends BaseController{
	
	@Resource
	LinkService linkService;
	
	@Resource
	NodeService nodeService;
	@Resource
	Log log;
	
	//生产环境HostName，如何是测试环境或者本机需要改名
	private String CacheHost = "web1";
	@RequestMapping(value = "/linkType/typeView")
	public String typeView(HttpServletResponse response,
			HttpServletRequest request) {		
			int pageNo = 0;
			int pageSize = 25;
			if(MyStringUtils.isNumeric(request.getParameter("pageNo"))){
				pageNo = Integer.parseInt(request.getParameter("pageNo"));
			}
			LinkType linkType = new LinkType();
			linkType.setId(request.getParameter("id"));		
			linkType.setName(request.getParameter("name"));				
			PageInfo<LinkType> pageInfo = 
					linkService.readLikeTypePageLite(pageNo, pageSize, 
							(LinkType)FormUtil.getForParamToBean(linkType));
			request.setAttribute("url", "/linkType/typeView");
			request.setAttribute("str", FormUtil.getForParam(linkType));
			request.setAttribute("pageInfo", pageInfo);
			return "article/link/linkType";
		
	}
	
	@RequestMapping(value = "/link/linkView")
	public String linkView(HttpServletResponse response,
			HttpServletRequest request) {		
			int pageNo = 0;
			int pageSize = 25;
			if(MyStringUtils.isNumeric(request.getParameter("pageNo"))){
				pageNo = Integer.parseInt(request.getParameter("pageNo"));
			}
			Link link = new Link();
			link.setId(request.getParameter("id"));		
			link.setName(request.getParameter("name"));				
			PageInfo<LinkType> pageInfo = 
					linkService.readLikeTypePageLite(pageNo, pageSize, 
							(LinkType)FormUtil.getForParamToBean(link));
			request.setAttribute("url", "/linkType/typeView");
			request.setAttribute("str", FormUtil.getForParam(link));
			request.setAttribute("pageInfo", pageInfo);
			return "article/link/linkList";
		
	}

	@RequestMapping(value = "/linkType/toAddType")
	public String toAddLinkType(HttpServletResponse response,
			HttpServletRequest request) {		
			return "article/link/addType";
		
	}
	
	@RequestMapping(value = "/linkType/toEditType")
	public String toEditLinkType(HttpServletResponse response,
			HttpServletRequest request) {
			LinkType linkType = linkService.read(request.getParameter("param"));
			request.setAttribute("type", linkType);
			return "article/link/editType";
		
	}
	
	@RequestMapping(value = "/linkType/addType")
	public void addLinkType(HttpServletResponse response,
			HttpServletRequest request) throws IOException {				
			LinkType linkType = new LinkType();
			linkType.setId(request.getParameter("id"));
			linkType.setName(request.getParameter("name"));
			linkType.setDescription(request.getParameter("description"));
			if(linkService.read(request.getParameter("id")) == null ){
				try{
					linkService.insert(linkType);
					response.getWriter().print("ok");
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}else{				
				response.getWriter().print("notEmpty");
			}
	
	}
	
	@RequestMapping(value = "/linkType/editType")
	public void editLinkType(HttpServletResponse response,
			HttpServletRequest request) throws IOException {				
			LinkType linkType = new LinkType();
			linkType.setId(request.getParameter("id"));
			linkType.setName(request.getParameter("name"));
			linkType.setDescription(request.getParameter("description"));
			if(linkService.read(request.getParameter("id")) != null ){
				try{
					linkService.update(linkType);
					response.getWriter().print("ok");
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}else{			
				response.getWriter().print("empty");
			}
	
	}	
	
	@RequestMapping(value = "/article/linkList")
	public String toLinkView(HttpServletResponse response,
			HttpServletRequest request){
		int pageNo = 0;		
		if(MyStringUtils.isNumeric(request.getParameter("pageNo"))){
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}
		Link link = new Link();
		link.setId(request.getParameter("id"));
		link.setName(request.getParameter("name"));
		link.setType(request.getParameter("type"));
		link.setStatus(request.getParameter("picStatus"));
		
		PageInfo<Link> pageInfo = 
				linkService.readPageLite(pageNo, getPageSize(), 
						(Link)FormUtil.getForParamToBean(link));
		List<LinkType> linkTypes = linkService.readTypeList();
		request.setAttribute("url", "/article/linkList");
		request.setAttribute("str", FormUtil.getForParam(link));
		request.setAttribute("picStatus",link.getStatus());
		request.setAttribute("pageInfo", pageInfo);		
		request.setAttribute("types", linkTypes);
		return "article/link/linkList";
	}
	
	@RequestMapping(value = "/article/toLink/{type}")
	public String toLink(HttpServletResponse response,
			HttpServletRequest request, @PathVariable String type) throws UnsupportedEncodingException{		
		List<LinkType> linkTypes = linkService.readTypeList();
		if(type.equals("add")){			
			request.setAttribute("types", linkTypes);
			return "article/link/addLink";
		}else if(type.equals("edit")){
			String id = request.getParameter("param");
			id = new String(id.getBytes("ISO8859-1"), "UTF-8");
			Link link = linkService.readLink(id);
			request.setAttribute("link", link);
			request.setAttribute("types", linkTypes);
			return "article/link/editLink";
		}	
		return redirect+"article/linkList";
	}
	
	@RequestMapping(value = "/article/link/{type}")
	public void link(HttpServletResponse response,
			@RequestParam("jiuyiTime")String jiuyiTime,
			HttpServletRequest request, @PathVariable String type) throws ParseException{
		Link link = new Link();
		link.setId(request.getParameter("id"));
		link.setName(request.getParameter("name"));
		link.setDescription(request.getParameter("description"));
		link.setLogo(request.getParameter("logo"));
		link.setMasterEmail(request.getParameter("masterEmail"));
		link.setPosition(request.getParameter("position"));
		link.setType(request.getParameter("type"));
		link.setUrl(request.getParameter("url"));
		link.setStatus(request.getParameter("picStatus"));
		link.setSecondTitle(request.getParameter("secondTitle"));
		SimpleDateFormat TIMEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
		link.setJiuyiTime(TIMEFORMAT.parse(jiuyiTime));
		if(MyStringUtils.isNumeric(request.getParameter("seqNum"))){
			link.setSeqNum(Integer.parseInt(request.getParameter("seqNum")));
		}	
		try{
			String status = null;
			if(type.equals("add")){
			    status = linkService.insertLink(link);
			}else if(type.equals("edit")){
				status = linkService.updateLink(link);
			}
			response.getWriter().print(status);
		}catch(Exception e){
			e.printStackTrace();
		}			
	
	}
	
	
	
	@RequestMapping(value = "/article/categoryTermList")
	public String toCategoryItemList(HttpServletResponse response,
			HttpServletRequest request){
		int pageNo = 0;		
		if(MyStringUtils.isNumericSpace(request.getParameter("pageNo"))){
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}
		CategoryTerm categoryTerm = new CategoryTerm();
		categoryTerm.setId(request.getParameter("id"));
		categoryTerm.setName(request.getParameter("name"));
		PageInfo<CategoryTerm> pageInfo = 
				nodeService.readPageInfoForCategoryTerm(pageNo, getPageSize(), 
						(CategoryTerm)FormUtil.getForParamToBean(categoryTerm));
		request.setAttribute("url", "/article/categoryTermList");
		request.setAttribute("str", FormUtil.getForParam(categoryTerm));
		request.setAttribute("pageInfo", pageInfo);		
		return "article/categoryTerm/categoryTermList";
	}
	
	@RequestMapping(value = "/article/toCategoryTerm/{type}")
	public String toCategoryTerm(HttpServletResponse response,
			HttpServletRequest request, @PathVariable String type) throws UnsupportedEncodingException{		
		if(type.equals("create")){						
			return "article/categoryTerm/addCategoryTerm";
		}else if(type.equals("edit")){
			String id = request.getParameter("param");
			id = new String(id.getBytes("ISO8859-1"), "UTF-8");
			CategoryTerm categoryTerm = nodeService.readCategoryTermById(id);
			request.setAttribute("categoryTerm", categoryTerm);
			return "article/categoryTerm/editCategoryTerm";
		}	
		return "article/categoryTerm/categoryTermList";
	}
	
	@RequestMapping(value = "/article/categoryTerm/{type}")
	public void categoryTerm(HttpServletResponse response,
			HttpServletRequest request, @PathVariable String type){
		CategoryTerm categoryTerm = new CategoryTerm();
		categoryTerm.setId(request.getParameter("id"));
		categoryTerm.setName(request.getParameter("name"));	
		categoryTerm.setType("ARTICLE");
		if(MyStringUtils.isNumeric(request.getParameter("seqNum"))){
			
			categoryTerm.setSeqNum(Integer.parseInt(request.getParameter("seqNum")));
		}
		categoryTerm.setDescription(request.getParameter("description"));
		if(type.equals("create")){						
			try{
				String status = nodeService.insertCategoryTerm(categoryTerm);
				response.getWriter().print(status);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}else if(type.equals("edit")){
			try{
				String status = nodeService.updateCategoryTerm(categoryTerm);
				response.getWriter().print(status);
			}catch(Exception e){
				e.printStackTrace();
			}		
		}	
	}
	
	@RequestMapping(value = "/article/nodeList")
	public String toNodeList(HttpServletResponse response,
			HttpServletRequest request){
		
		int pageNo = 0;
		int pageSize = 25;
 		List<CategoryTerm> list=nodeService.readCategoryTerm();
 		String cId=request.getParameter("CategoryTerm");
 		String cId1=request.getParameter("cId");
        if(cId==null||cId.equals("")){
        	cId=cId1;
        }
 		if(MyStringUtils.isNumeric(request.getParameter("pageNo"))){
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}		
			Map<String, Object>params=new HashMap<>();
		if(cId!=null){
			params.put("cId", cId);
		}
		params.put("id", request.getParameter("id"));
		params.put("title", request.getParameter("title"));
		if(MyStringUtils.isNumeric(request.getParameter("status"))){
			params.put("status", request.getParameter("status"));
		}	
		if(MyStringUtils.isNumeric(request.getParameter("flag"))){
 			int flag=Integer.parseInt(request.getParameter("flag"));
 			if(flag==1){
 				params.put("flag",flag);
 			}
 		}
		PageInfo<Node> pageInfo = 
				nodeService.readPageLite4Map(pageNo, pageSize, 
						params);
		request.setAttribute("type", cId);
		request.setAttribute("categoryTermList", list);
		request.setAttribute("url", "/article/nodeList");
		request.setAttribute("str", FormUtil.getForParam(params));
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("flag",request.getParameter("flag"));
		return "article/node/nodeList";
	}
	
	@RequestMapping(value = "article/toCreateNode/{type}")
	public String toEditNode(HttpServletResponse response,
			HttpServletRequest request, @PathVariable String type){
		List<CategoryTerm> categoryTerms = nodeService.readListForCategoryTerm();
		if(type.equals("create")){			
			request.setAttribute("categoryTerms", categoryTerms);
			return "article/node/createNode";
		}else if(type.equals("edit")){
			Node node = nodeService.read(request.getParameter("param"));			
			request.setAttribute("categoryTerms", categoryTerms);
			request.setAttribute("node", node);
			return "article/node/editNode";
		}
		return "article/node/nodeList";
	}
	@RequestMapping(value = "article/createNode/{type}")
	public void EditNode(HttpServletResponse response,
			HttpServletRequest request, @PathVariable String type){		
		UserCookie loginUser = GetLoginUser(request, response);
		Node node = new Node();
		String id=IdGenerator.randomUUID();
		
		node.setId(id);
		node.setTitle(request.getParameter("title"));
		node.setKeywords(request.getParameter("keywords"));
		node.setSubTitle(request.getParameter("subTitle"));
		node.setStatus(request.getParameter("status"));
		node.setTerm(request.getParameter("categoryTerm"));
		node.setContent(request.getParameter("content"));
		node.setDescription(request.getParameter("description"));
		node.setLastModifyUser(loginUser.getUserId());
		node.setUpdateTime(new Date());
		node.setPictureUrl(request.getParameter("pictureUrl"));
		node.setSource(request.getParameter("source"));
		//String str= Uploader.uploadImageFile(files, request,path);
		node.setFlag(request.getParameter("flag"));
		
		String isSort=request.getParameter("getTop");
		if(type.equals("create")){					
			try{
				node.setCreator(loginUser.getUserId());
				node.setCreateTime(new Date());
				if (isSort==null) {
					node.setSortNum(0);
				}else {
					int sortNum=nodeService.readSortNum()+50;
					node.setSortNum(sortNum);
				}
				String status = nodeService.insert(node);	
				response.getWriter().print(status);
			}catch(Exception e){
				e.printStackTrace();
			}			
		}else if(type.equals("edit")){
			try{		
				node.setId(request.getParameter("id"));
				String status = nodeService.update(node);			
				response.getWriter().print(status);
 			}catch(Exception e){	
				e.printStackTrace();
			}			
		}
	}
	
	/**
	 * 
	 * @description 处理上传
	 * @author zhangyingwei
	 * @time 2015年6月20日12:00:04
	 * @param files
	 * @param response
	 * @param request
	 * @throws IOException 
	 */
	@RequestMapping(value = "/article/uploadImages", method = RequestMethod.POST)
	public void uploadImages(
			@RequestParam("file") CommonsMultipartFile[] files,
			HttpServletResponse response, HttpServletRequest request) throws IOException {
		response.setCharacterEncoding("UTF-8");
		String path ="article";
		String str= Uploader.uploadImageFile(files, request,path);
		str=str.substring(2, str.length()-2);
		response.getWriter().print(str); 
	}	
	/**
	 * 
	 * @description 处理上传
	 * @author wangjing
	 * @time 2015-3-11 下午7:53:19
	 * @param files
	 * @param response
	 * @param request
	 * @throws IOException 
	 */
	@RequestMapping(value = "/article/uploadArticleData", method = RequestMethod.POST)
	public void uploadArticleData(
			@RequestParam("file") CommonsMultipartFile[] files,
			HttpServletResponse response, HttpServletRequest request) throws IOException {
		response.setCharacterEncoding("UTF-8");
		String str=linkService.uploadArticleData(files, request);
		response.getWriter().print(str);
	}
	
	@ResponseBody
	@RequestMapping(value="/article/nodeList/toEditSortNum")
	public String toEditSortNum(HttpServletResponse response, HttpServletRequest request){
		String id=request.getParameter("id");
		String sortNum=request.getParameter("sortNum");
		return nodeService.updateEditSortNumById(id,sortNum);			
	}
}
