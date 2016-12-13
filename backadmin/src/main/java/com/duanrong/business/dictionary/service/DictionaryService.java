/**
 * 
 */
package com.duanrong.business.dictionary.service;

import java.util.List;

import com.duanrong.business.dictionary.model.Dictionary;

import base.pagehelper.PageInfo;

/**
 * @author chenwei
 *	数据字典维护
 */
public interface DictionaryService {

	/**
	 * 查询字典数据，带分页
	 * @param pageNo
	 * @param dic
	 * @return
	 */
	PageInfo<Dictionary> readDicList(String pageNo, Dictionary dic);

	/**
	 * 通过id查询字典信息
	 * @param dicId
	 * @return
	 */
	Dictionary readDicById(String dicId);

	/**
	 * 新增字典信息
	 * @param dic
	 * @return
	 */
	int insertDic(Dictionary dic);

	/**
	 * 修改字典信息
	 * @param dic
	 * @return
	 */
	int updateDic(Dictionary dic);

	/**
	 * 通过id删除字典数据
	 * @param id
	 * @return
	 */
	int delDicById(String id);

	/**
	 * 通过typecode查询字典数据列表
	 * @param typeCode
	 * @return
	 */
	List<Dictionary> readDicLstByTypeCode(String typeCode);

}