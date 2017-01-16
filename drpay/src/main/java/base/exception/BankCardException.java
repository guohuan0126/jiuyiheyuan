package base.exception;

import base.error.ErrorCode;

/**
 * 银行卡异常
 * @author xiao
 * @datetime 2016年11月7日 下午8:50:19
 */
public class BankCardException extends ErrorCodeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1719695551576321583L;

	public BankCardException() {
	}

	public BankCardException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BankCardException(String message, Throwable cause) {
		super(message, cause);
	}

	public BankCardException(String message) {
		super(message);
	}

	public BankCardException(Throwable cause) {
		super(cause);
	}

	public BankCardException(ErrorCode code) {
		super(code);
	}

	public BankCardException(ErrorCode code, String message) {
		super(code, message);
	}
}
