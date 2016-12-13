package com.duanrong.business.node.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import util.MyStringUtils;
import base.pagehelper.PageInfo;

import com.duanrong.business.node.dao.NodeDao;
import com.duanrong.business.node.model.CategoryTerm;
import com.duanrong.business.node.model.Node;
import com.duanrong.business.node.service.NodeService;
import com.duanrong.util.IdUtil;

@Service
public class NodeServiceImpl implements NodeService {

	@Resource
	NodeDao nodeDao;

	@Override
	public PageInfo<CategoryTerm> readPageInfoForCategoryTerm(int pageNo,
			int pageSize, CategoryTerm categoryTerm) {
			
		return nodeDao.sqlLiteForCategoryTerm(pageNo, pageSize, categoryTerm);
	}

	
	@Override
	
	public List<CategoryTerm> readListForCategoryTerm() {
		
		return nodeDao.getListCategoryTerm();
	}

	@Override
	public Node read(String id) {		
		Node node = nodeDao.get(id);
		List<String> termNames = readTermNameByNodeId(id);
		StringBuffer sb = new StringBuffer();
		for(String termName : termNames){
			sb.append(termName+"、");
		}		
		String content = nodeDao.getBodyById(node.getBodyId());
		node.setContent(content);
		node.setTerm(sb.toString());
		return node;
	}

	@Override
	public List<String> readTermNameByNodeId(String nodeId) {
		
		return nodeDao.getTermNameByNodeId(nodeId);
	}

	@Override
	public String update(Node node) {		
		Node n = nodeDao.get(node.getId());
		if(n != null){
			Map<String, Object> map = new HashMap<>();
			map.put("id", n.getBodyId());
			map.put("body", node.getContent());			
			map.put("nodeId", node.getId());
			if(nodeDao.getBodyById(n.getBodyId())  != null){				
				nodeDao.updateBody(map);
			}else{				
				nodeDao.insertBody(map);												
			}
			nodeDao.update(node);
			nodeDao.deleteNodeTerm(node);
			String[] categoryTerms = this.stringToList(node.getTerm());
			if(categoryTerms != null){
				for(int i = 0; i < categoryTerms.length; i++){
					List<CategoryTerm> list = nodeDao.getCategoryTermByName(categoryTerms[i]);
					if(!list.isEmpty()){
						CategoryTerm categoryTerm = list.get(0);	
						Map<String, Object> map1 = new HashMap<>();
						map1.put("nodeId", node.getId());
						map1.put("termId", categoryTerm.getId());
						nodeDao.insertNodeTerm(map1);
					}
				}
			}
			return "ok";
		}
		return "isNull";
	}

	@Override
	public String insert(Node node) {		
		Node n = nodeDao.get(node.getId());
		if(n == null){
			String bodyId = IdUtil.randomUUID();
			if(MyStringUtils.isNotAnyBlank(node.getContent())){
				Map<String, Object> map = new HashMap<>();
				map.put("id", bodyId);
				map.put("body", node.getContent());		
				map.put("nodeId", node.getId());		
				nodeDao.insertBody(map);
			}			
			node.setBodyId(bodyId);
			nodeDao.insert(node);
			String[] categoryTerms = this.stringToList(node.getTerm());
			for(int i = 0; i < categoryTerms.length; i++){
				List<CategoryTerm> list = nodeDao.getCategoryTermByName(categoryTerms[i]);
				if(!list.isEmpty()){
					CategoryTerm categoryTerm = list.get(0);					
					Map<String, Object> map = new HashMap<>();
					map.put("nodeId", node.getId());
					map.put("termId", categoryTerm.getId());
					nodeDao.insertNodeTerm(map);										
				}				
			}
			return "ok";
		}
		return "notNull";
	}

	private String[] stringToList(String categoryTerm){
		if(MyStringUtils.isNotAnyBlank(categoryTerm.trim())){
			if(categoryTerm.contains("：")){			
				categoryTerm = categoryTerm.substring(categoryTerm.indexOf("：")+1).trim();
				if(categoryTerm.contains("、")){
					if("、".equals(categoryTerm.substring(categoryTerm.length()-2))){
						categoryTerm = categoryTerm.substring(0, categoryTerm.length()-2);
					}
				}
				return categoryTerm.split("、");
			}
			
		}
		return null;
	} 
	
	@Override
	public String insertCategoryTerm(CategoryTerm categoryTerm) {
		if(nodeDao.getCategoryTermById(categoryTerm.getId()) != null ){
			return "notNull";
			
		}else if(nodeDao.getCategoryTermByName(categoryTerm.getName()).size() > 0){
			return "notName";
		}		
		nodeDao.insertCategoryTerm(categoryTerm); 
		return "ok";
	}

	@Override
	public String updateCategoryTerm(CategoryTerm categoryTerm) {			
		if(nodeDao.getCategoryTermById(categoryTerm.getId()) == null ){
			return "isNull";
			
		}else if(nodeDao.getCategoryTermByIdAndName(categoryTerm).size() > 0){
			return "notName";
		}
		nodeDao.updateCategoryTerm(categoryTerm);
		return "ok";
		
	}
	@Override
	public CategoryTerm readCategoryTermById(String id) {		
		return nodeDao.getCategoryTermById(id);
	}

	@Override
	public CategoryTerm readCategoryTermByName(String name) {
		List<CategoryTerm> list = nodeDao.getCategoryTermByName(name);
		if(!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}

	@Override
	public int readSortNum() {
		
		return nodeDao.getSortNum();
	}

	@Override
	public String updateEditSortNumById(String id, String sortNum) {
			
		nodeDao.updateEditSortNumById(id,sortNum);
		return "success";
	}

	@Override
	public List<CategoryTerm> readCategoryTerm() {
		
		return nodeDao.getCategoryTerm();
	}


	@Override
	public PageInfo<Node> readPageLite4Map(int pageNo, int pageSize,
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return nodeDao.pageLite4Map(pageNo, pageSize, params);
	}


	
}