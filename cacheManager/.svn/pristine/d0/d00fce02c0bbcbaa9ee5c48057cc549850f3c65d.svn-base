package com.duanrong.business.sys.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import base.pagehelper.PageInfo;

import com.duanrong.business.sys.dao.BlackDao;
import com.duanrong.business.sys.model.Black;
import com.duanrong.business.sys.service.BlackService;

@Service
public class BlackServiceImpl implements BlackService {

	@Resource
	BlackDao blackDao;
	
	@Override
	public PageInfo<Black> pageLite(int pageNo, int pageSize, Black black) {		
		return blackDao.pageLite(pageNo, pageSize, black);
	}

	@Override
	public void insert(Black black) {
		blackDao.insert(black);
		
	}

	@Override
	public void delete(String id) {
		blackDao.delete(id);
	}

}
