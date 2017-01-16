package base.exception;

import base.error.ErrorCode;

/**
 * 用户相关异常
 * @author xiao
 * @datetime 2016年11月7日 下午8:18:12
 */
public class UserInfoException extends ErrorCodeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2627853582474489417L;

	public UserInfoException() {
	}

	public UserInfoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UserInfoException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserInfoException(String message) {
		super(message);
	}

	public UserInfoException(Throwable cause) {
		super(cause);
	}

	public UserInfoException(ErrorCode code) {
		super(code);
	}

	public UserInfoException(ErrorCode code, String message) {
		super(code, message);
	}
}
