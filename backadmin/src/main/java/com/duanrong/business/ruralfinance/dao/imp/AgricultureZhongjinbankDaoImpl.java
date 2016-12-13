package com.duanrong.business.ruralfinance.dao.imp;



import org.springframework.stereotype.Repository;
import base.dao.impl.BaseDaoImpl;
import com.duanrong.business.ruralfinance.dao.AgricultureZhongjinbankDao;
import com.duanrong.business.ruralfinance.model.AgricultureZhongjinbank;

@Repository
public class AgricultureZhongjinbankDaoImpl extends BaseDaoImpl<AgricultureZhongjinbank> implements AgricultureZhongjinbankDao {

	
	public AgricultureZhongjinbankDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.ruralfinance.mapper.AgricultureZhongjinbankMapper");
	}
	@Override
	public AgricultureZhongjinbank findByCondition(String bankName) {
		
		return (AgricultureZhongjinbank) this.getSqlSession().selectOne(this.getMapperNameSpace()+".selectByCondition", bankName);
		
	}


	



	
}
