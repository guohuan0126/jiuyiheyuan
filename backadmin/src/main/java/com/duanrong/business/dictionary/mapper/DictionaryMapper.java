package com.duanrong.business.dictionary.mapper;

import java.util.List;

import com.duanrong.business.dictionary.model.Dictionary;

public interface DictionaryMapper {
    int deleteByPrimaryKey(String id);

    int insert(Dictionary record);

    int insertSelective(Dictionary record);

    Dictionary selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Dictionary record);

    int updateByPrimaryKey(Dictionary record);

	List<Dictionary> getDicList(Dictionary dic);

	List<Dictionary> getDicLstByTypeCode(String typeCode);
}