package com.duanrong.drpay.business.payment.dao;


import base.dao.BaseDao;
import com.duanrong.drpay.business.payment.model.WithdrawCash;

import java.util.Date;

/**
 * @Description: 提现
 * @Author: 林志明
 * @CreateDate: Sep 11, 2014
 */
public interface WithdrawCashDao extends BaseDao<WithdrawCash> {

    /**
     * 查询（加锁 慎用）
     *
     * @param id
     * @return
     */
    WithdrawCash getWithLock(String id);

    /**
     * 查询工作日、节假日表数据
     */
    Integer getHolidayDate(Date date, String type);

}
