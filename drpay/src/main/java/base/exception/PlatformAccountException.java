package base.exception;

import base.error.ErrorCode;

/**
 * 平台账户异常
 * @author xiao
 * @datetime 2016年11月8日 上午9:20:48
 */
public class PlatformAccountException extends AccountException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4855886928868848878L;

	public PlatformAccountException() {
	}

	public PlatformAccountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PlatformAccountException(String message, Throwable cause) {
		super(message, cause);
	}

	public PlatformAccountException(String message) {
		super(message);
	}

	public PlatformAccountException(Throwable cause) {
		super(cause);
	}

	public PlatformAccountException(ErrorCode code) {
		super(code);
	}

	public PlatformAccountException(ErrorCode code, String message) {
		super(code, message);
	}
}
