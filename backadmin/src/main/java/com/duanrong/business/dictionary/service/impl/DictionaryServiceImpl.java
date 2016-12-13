/**
 * 
 */
package com.duanrong.business.dictionary.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duanrong.business.dictionary.mapper.DictionaryMapper;
import com.duanrong.business.dictionary.model.Dictionary;
import com.duanrong.business.dictionary.service.DictionaryService;

import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

/**
 * @author chenwei
 * 数据字典维护
 */
@Service
@Transactional
public class DictionaryServiceImpl implements DictionaryService {

	@Autowired
	private DictionaryMapper dictionaryMapper;
	/**
	 * 查询字典数据，带分页
	 * @param pageNo
	 * @param dic
	 * @return
	 */
	public PageInfo<Dictionary> readDicList(String pageNo, Dictionary dic){
		PageHelper.startPage(Integer.parseInt(pageNo), 10);
		List<Dictionary> lst = dictionaryMapper.getDicList(dic); 
		return new PageInfo<>(lst);
	}
	
	/**
	 * 通过id查询字典信息
	 * @param dicId
	 * @return
	 */
	public Dictionary readDicById(String dicId) {
		return dictionaryMapper.selectByPrimaryKey(dicId);
	}

	/**
	 * 新增字典信息
	 */
	public int insertDic(Dictionary dic) {
		return dictionaryMapper.insertSelective(dic);
	}

	/**
	 * 修改字典信息
	 */
	public int updateDic(Dictionary dic) {
		return dictionaryMapper.updateByPrimaryKeySelective(dic);
	}

	/**
	 * 通过id删除字典数据
	 */
	public int delDicById(String id) {
		return dictionaryMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 通过typecode查询字典数据列表
	 */
	public List<Dictionary> readDicLstByTypeCode(String typeCode) {
		return dictionaryMapper.getDicLstByTypeCode(typeCode);
	}
}