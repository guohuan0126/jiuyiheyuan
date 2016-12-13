package com.duanrong.business.flow.dao;



import com.duanrong.business.flow.model.Flow;

import base.dao.BaseDao;

/**
 * @author xiao
 * @date 2015年2月3日 下午6:10:16
 */
public interface FlowDao extends BaseDao<Flow> {
		
	/**
	 * 删除流程信息（逻辑删除）
	 * @param flowId
	 * @return
	 */
	public int deleteFlow(int flowId);

}
