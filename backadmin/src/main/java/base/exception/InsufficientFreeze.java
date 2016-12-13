package base.exception;

/**
 * Company: jdp2p <br/>
 * Copyright: Copyright (c)2013 <br/>
 * Description: 冻结金额不足
 * 
 * @author: wangzhi
 * @version: 1.0 Create at: 2014-1-21 下午3:45:50
 * 
 *           Modification History: <br/>
 *           Date Author Version Description
 *           ------------------------------------------------------------------
 *           2014-1-21 wangzhi 1.0
 */
public class InsufficientFreeze extends Exception {
	private static final long serialVersionUID = -5962650271498442157L;

	public InsufficientFreeze(String msg) {
		super(msg);
	}

	public InsufficientFreeze() {
	}
}
