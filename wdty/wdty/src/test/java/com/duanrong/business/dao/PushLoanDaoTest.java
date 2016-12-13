package com.duanrong.business.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/resources/applicationContext.xml"})
public class PushLoanDaoTest {

	@Autowired
	public PushLoanDao pushLoanDao;
	@Test
	public void testUpdateStatus() {
		int num = this.pushLoanDao.updateStatus(3, "完成", "157d146fb9754f88b4dd6baf31fec373");
		System.out.println(num);
	}

}
