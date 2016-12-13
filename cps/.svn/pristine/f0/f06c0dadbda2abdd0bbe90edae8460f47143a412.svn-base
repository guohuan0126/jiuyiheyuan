package com.duanrong.cps.business.permission.service.impl;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duanrong.cps.business.permission.dao.PermissionDao;
import com.duanrong.cps.business.permission.service.PermissionService;
import com.duanrong.cps.util.MyStringUtils;
import com.sun.tools.javac.util.List;


@Service
public class PermissionServiceImpl implements PermissionService {
	
	@Resource
	private PermissionDao permissionDao;
	//查询超级密码
	@Override
	public String getSpwd(String key) {
		return permissionDao.getSpwd(key);
	}
	
	//查询ip白名单list
	@Override
	public ArrayList<String> getIps(String id) {
		String wIps=permissionDao.getIps(id);
		//System.out.println(wIps);
		ArrayList<String> ips=new ArrayList<String>();
		if (MyStringUtils.isNotAnyBlank(wIps) && wIps.contains(",")) {
			String [] s=wIps.split(",");
			for (int i = 0; i < s.length; i++) {
				//System.out.println(i);
				ips.add(s[i]);
				System.out.println(ips);
			}
		}
		return ips;
	
	}


}
