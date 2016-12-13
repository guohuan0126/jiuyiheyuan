/**
 * 
 */
package com.duanrong.newadmin.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.duanrong.business.dictionary.model.Dictionary;
import com.duanrong.business.dictionary.service.DictionaryService;
import com.duanrong.newadmin.model.UserCookie;
import com.duanrong.newadmin.utility.IdGenerator;

import base.pagehelper.PageInfo;

/**
 * @author bj
 *
 */
@Controller
@RequestMapping("/dictionary")
public class DictionaryController extends BaseController {

	@Autowired
	private DictionaryService dictionaryService;
	
	/**
	 * 数据字典列表查询
	 * 
	 * @param m
	 * @return
	 */
	@RequestMapping("/dicList")
	public String list(String pageNo,Dictionary dic, Model m) {
		if( pageNo == null || "".equals(pageNo) )
			pageNo = "1";
		//分页，带查询
		PageInfo<Dictionary> pageInfo = dictionaryService.readDicList(pageNo,dic);
		m.addAttribute("pageInfo", pageInfo);
		m.addAttribute("dic", dic);
		return "dictionary/dicList";
	}
	
	/**
	 * 通过字典Id删除数据字典数据
	 * @param id
	 * @return
	 */
	@RequestMapping("/delDicById/{id}")
	@ResponseBody
	public String delDicById(@PathVariable("id")String id){
		int ret = dictionaryService.delDicById(id);
		if( ret>0 )
			return "true";
		return "false";
	}
	
	/**
	 * 编辑保存
	 * @param dic
	 * @param m
	 * @return
	 */
	@RequestMapping("/editSave")
	@ResponseBody
	public String editSave(Dictionary dic,HttpServletRequest request,
			HttpServletResponse response,Model m){
		int ret;
		UserCookie user = GetLoginUser(request, response);
		if( dic.getId() != null && "".equals(dic.getId().trim()) ){
			dic.setId(IdGenerator.randomUUID());
			dic.setCreateId(user.getUserId());
			dic.setCreateTime(new Date());
			ret = dictionaryService.insertDic(dic);
		}else{
			dic.setModifyId(user.getUserId());
			dic.setModifyTime(new Date());
			ret = dictionaryService.updateDic(dic);
		}
		if (ret > 0)
			return "true";
		return "false";
	}

	/**
	 * 编辑&新增字典信息
	 * @return
	 */
	@RequestMapping("/toEditByTypeCode")
	public String toEditByTypeCode(String typeCode,Model m){
		List<Dictionary> dicLst = null;
		if( typeCode != null && !"".equals(typeCode.trim()) ){
			dicLst = dictionaryService.readDicLstByTypeCode(typeCode);
		}
		m.addAttribute("dicLst", dicLst);
		return "dictionary/editDic";
	}
	
	/**
	 * 根据id查询字典数据
	 * @param id
	 * @return
	 */
	@RequestMapping("/getDicById")
	@ResponseBody
	public String getDicById(String id){
		Dictionary dic = dictionaryService.readDicById(id);
		return JSONObject.toJSONString(dic);
	}
}