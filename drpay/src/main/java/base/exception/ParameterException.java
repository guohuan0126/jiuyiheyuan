package base.exception;

import base.error.ErrorCode;

/**
 * 参数异常
 * @author xiao
 * @datetime 2016年11月7日 下午8:53:27
 */
public class ParameterException extends ErrorCodeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5250143561212217833L;

	public ParameterException() {
	}

	public ParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ParameterException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParameterException(String message) {
		super(message);
	}

	public ParameterException(Throwable cause) {
		super(cause);
	}

	public ParameterException(ErrorCode code) {
		super(code);
	}

	public ParameterException(ErrorCode code, String message) {
		super(code, message);
	}
}
