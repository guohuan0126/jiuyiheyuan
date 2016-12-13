package com.duanrong.business.ruralfinance.service.imp;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.duanrong.business.ruralfinance.dao.AgricultureZhongjinbankDao;
import com.duanrong.business.ruralfinance.model.AgricultureZhongjinbank;
import com.duanrong.business.ruralfinance.service.AgricultureZhongjinbankService;
@Service
public class AgricultureZhongjinbankServiceImpl<T> implements AgricultureZhongjinbankService {	
	@Resource
	private  AgricultureZhongjinbankDao agricultureZhongjinbankDao;
	
	@Override
	public AgricultureZhongjinbank findByCondition(String bankname) {
		
		return  agricultureZhongjinbankDao.findByCondition(bankname);
	}
	
	
}
