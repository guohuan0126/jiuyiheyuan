package base.exception;

import base.error.ErrorCode;

/**
 * 账户相关异常
 * @author xiao
 * @datetime 2016年11月7日 下午8:19:58
 */
public class AccountException extends ErrorCodeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5131650282166524389L;

	public AccountException() {
		super();
	}

	public AccountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AccountException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountException(String message) {
		super(message);
	}

	public AccountException(Throwable cause) {
		super(cause);
	}

	public AccountException(ErrorCode code) {
		super(code);
	}

	public AccountException(ErrorCode code, String message) {
		super(code, message);
	}

}
