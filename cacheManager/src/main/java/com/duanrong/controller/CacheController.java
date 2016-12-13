package com.duanrong.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import base.pagehelper.PageInfo;

import com.duanrong.business.cache.model.CacheKey;
import com.duanrong.business.cache.service.CacheService;
import com.duanrong.business.permission.model.UserCookie;
import com.duanrong.util.DRedisClient;
import com.duanrong.util.FormUtil;
import com.duanrong.util.jedis.DRJedisCacheUtil;
import com.duanrong.util.json.FastJsonUtil;

@Controller
public class CacheController extends BaseController {

	@Resource
	CacheService cacheService;

	@RequestMapping(value = "cache/toCacheKeyManager")
	public String toCacheKeysManager(CacheKey cacheKey,
			HttpServletRequest request, HttpServletResponse response) {
		String pageNum = request.getParameter("pageNo");
		PageInfo<CacheKey> pageInfo = cacheService.pageInfo(pageNum == null
				|| pageNum.equals("") ? pageNo : Integer.parseInt(pageNum),
				pageSize, FormUtil.getForParamToBean(cacheKey));
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("cacheKey", cacheKey);
		return "cacheKeyManager";
	}
	
	/**
	 * 缓存管理页
	 * 
	 * @param request
	 * @param response
	 * @param sysCacheKey
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/cache/isHasCacheKey")
	public void isHasCacheKey(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		CacheKey sysCacheKey = new CacheKey();
		sysCacheKey.setKey(request.getParameter("key"));
		PageInfo<CacheKey> pageInfo = cacheService.pageInfo(1,
				1, sysCacheKey);
		if(pageInfo.getTotalRecord() > 0){
			response.getWriter().print(true);
		}else{
			response.getWriter().print(false);
		}
		
	}

	
	/**
	 * 缓存管理页
	 * 
	 * @param request
	 * @param response
	 * @param sysCacheKey
	 * @return
	 */
	@RequestMapping(value = "/cache/toAddCacheKey")
	public String toAddCache(HttpServletRequest request,
			HttpServletResponse response, String id) {
		CacheKey cacheKey = null;
		if(id != null && !"".equals(id)){
			cacheKey = cacheService.get(id);
		}
		request.setAttribute("cache", cacheKey);
		return "addCacheKey";

	}
	
	/**
	 * 缓存管理页
	 * 
	 * @param request
	 * @param response
	 * @param sysCacheKey
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/cache/delCacheKey")
	public void delCache(HttpServletRequest request,
			HttpServletResponse response, String id) throws IOException {
		if(id != null && !"".equals(id)){
			try{
				cacheService.delete(id);
				response.getWriter().print("ok");		
			}catch(Exception e){				
				response.getWriter().print("fail");		
			}
			
		}
		
		
		
	}

	/**
	 * 缓存管理页
	 * 
	 * @param request
	 * @param response
	 * @param sysCacheKey
	 * @return
	 */
	@RequestMapping(value = "/cache/cacheKey/{type}")
	public void cacheKey(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String type) {
		UserCookie userCookie = getCookie(request, response);
		CacheKey cacheKey = new CacheKey();
		String id = request.getParameter("id");
		cacheKey.setId(id != null && !("").equals(id) ? Integer.parseInt(id)
				: 0);
		cacheKey.setKey(request.getParameter("key"));
		cacheKey.setType(request.getParameter("type"));
		cacheKey.setDomain(request.getParameter("domain"));
		cacheKey.setInfo(request.getParameter("info"));
		cacheKey.setDescrip(request.getParameter("descrip"));
		cacheKey.setApplicant(request.getParameter("applicant"));
		cacheKey.setRemark(request.getParameter("remark"));
		try {
			if ("add".equals(type)) {
				cacheKey.setCreateUser(userCookie.getUserId());
				cacheKey.setCreateTime(new Date());
				try {
					cacheService.insert(cacheKey);
					response.getWriter().print("ok");				
				} catch (Exception e) {
					e.printStackTrace();
					response.getWriter().print("faild");
				}

			} else if ("edit".equals(type)) {
				cacheKey.setUpdateUser(userCookie.getUserId());
				cacheKey.setUpdateTime(new Date());
				try {
					cacheService.update(cacheKey);
					response.getWriter().print("ok");				
				} catch (Exception e) {
					e.printStackTrace();;
					response.getWriter().print("faild");
				}

			} else if ("del".equals(type)) {
				try {
					cacheService.delete(id);
					DRJedisCacheUtil.del(cacheKey.getKey());
					response.getWriter().print("ok");				
				} catch (Exception e) {
					e.printStackTrace();
					response.getWriter().print("faild");
				}
			}

		} catch (IOException e) {

		}
	}	
	
	@RequestMapping(value = "cache/toCacheManager")
	public String toCacheManager() {		
		return "cacheManager";
	}
	
	@RequestMapping(value = "cache/cacheManager/{otype}")
	public void cacheManager(CacheKey cacheKey, HttpServletResponse response, 
			HttpServletRequest request, @PathVariable String otype) throws IOException {		
		response.setCharacterEncoding(ENCODING);
		Writer out = response.getWriter();
		
		if(!DRJedisCacheUtil.exists(cacheKey.getKey())){
			out.write("exist");
			return ;
		}
		String data = "";
		log.debug("key ------" + cacheKey);
		if("get".equals(otype)){
			switch (cacheKey.getType()){
				case "1":
					data = FastJsonUtil.objToJson(DRJedisCacheUtil.get(cacheKey.getKey()));
					break;
				case "2":
					String[] hashkey = null;
					if(null != cacheKey.getHashKey() && !"".equals(cacheKey.getHashKey())){
						hashkey = cacheKey.getHashKey().split(",");
						data = FastJsonUtil.objToJson(DRJedisCacheUtil.hget(cacheKey.getKey(), hashkey));
					}else{
						data = FastJsonUtil.objToJson(DRJedisCacheUtil.hget(cacheKey.getKey()));
					}
					break;
				case "3":
					data = FastJsonUtil.objToJson(DRJedisCacheUtil.lrange(cacheKey.getKey(), 0, (int)DRJedisCacheUtil.llen(cacheKey.getKey())));
					break;
				case "4":
					data = FastJsonUtil.objToJson(DRJedisCacheUtil.sget(cacheKey.getKey()));
					break;
				default:
					break;
			}
			out.write(data);
			
		}else if("del".equals(otype)){
			switch (cacheKey.getType()){
			case "1":
				DRJedisCacheUtil.del(cacheKey.getKey());
				break;
			case "2":
				String[] hashkey = null;
				if(null != request.getParameter("d") && !"".equals(request.getParameter("d"))){
					hashkey = request.getParameter("d").split(",");
					DRJedisCacheUtil.hdel(cacheKey.getKey(), hashkey);
				}else{
					DRJedisCacheUtil.hdel(cacheKey.getKey());
				}
				
				break;
			case "3":
				DRJedisCacheUtil.lrem(cacheKey.getKey(), 0, request.getParameter("d"));
				break;
			case "4":
				DRJedisCacheUtil.srem(cacheKey.getKey(), 0, request.getParameter("d"));
				break;
			default:
				break;
			}
		}
	}

	@RequestMapping(value = "cache/cacheManager/excute")
	public void cacheManagerExcute(HttpServletResponse response, 
			HttpServletRequest request) throws IOException {		
		response.setCharacterEncoding(ENCODING);
		Writer out = response.getWriter();
		String msg = request.getParameter("msg");
		
		log.debug(msg);
		try{
			if(msg != null && !msg.trim().equals("")){
				DRedisClient client = DRedisClient.geClient();
				String s = client.send(msg);			
				log.debug(s);			
				out.write(s);
			}else{
				out.write(msg);
			}
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
}
