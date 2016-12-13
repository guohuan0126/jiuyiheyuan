package com.duanrong.business.netloaneye.mapper;

import com.duanrong.business.netloaneye.model.PushLoan;

public interface PushLoanMapper {
    int deleteByPrimaryKey(String id);

    int insert(PushLoan record);

    int insertSelective(PushLoan record);

    PushLoan selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PushLoan record);

    int updateByPrimaryKey(PushLoan record);
    //根据loanid查询push_loan
    public PushLoan selectByLoanId(String loanId);
}