package base.exception;

import base.error.ErrorCode;

/**
 * 支付账户异常
 * @author xiao
 * @datetime 2016年11月8日 上午9:21:57
 */
public class PaymentAccountException extends AccountException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7628856595348607083L;

	public PaymentAccountException() {
	}

	public PaymentAccountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PaymentAccountException(String message, Throwable cause) {
		super(message, cause);
	}

	public PaymentAccountException(String message) {
		super(message);
	}

	public PaymentAccountException(Throwable cause) {
		super(cause);
	}

	public PaymentAccountException(ErrorCode code) {
		super(code);
	}

	public PaymentAccountException(ErrorCode code, String message) {
		super(code, message);
	}
}
