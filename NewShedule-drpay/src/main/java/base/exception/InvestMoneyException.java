package base.exception;

/**
 * Company: jdp2p <br/>
 * Copyright: Copyright (c)2013 <br/>
 * Description: 投资金额异常
 * 
 * @author: wangzhi
 * @version: 1.0 Create at: 2014-1-22 下午3:53:05
 * 
 *           Modification History: <br/>
 *           Date Author Version Description
 *           ------------------------------------------------------------------
 *           2014-1-22 wangzhi 1.0
 */
public class InvestMoneyException extends Exception {
	private static final long serialVersionUID = 5986298053051239741L;

	public InvestMoneyException() {
	}

	public InvestMoneyException(String msg) {
		super(msg);
	}

}
