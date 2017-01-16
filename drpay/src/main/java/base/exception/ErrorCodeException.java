package base.exception;

import base.error.ErrorCode;

/**
 * ErrorCodeException
 * @author xiao
 * @datetime 2016年11月7日 下午6:23:54
 */
public class ErrorCodeException extends Exception  {

	private ErrorCode code;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5338642548503695754L;

	public ErrorCodeException() {
		super();
	}

	public ErrorCodeException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ErrorCodeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ErrorCodeException(String message) {
		super(message);
	}

	public ErrorCodeException(Throwable cause) {
		super(cause);
	}

	public ErrorCodeException(ErrorCode code) {
		this.code = code;
	}

	public ErrorCodeException(ErrorCode code, String message) {
		this.code = code;
	}
	
	public ErrorCode getCodeMessage(){
		return code;
	}
}
