package base.exception;

import base.error.ErrorCode;

/**
 * @author xiao
 * @version 1.0.0
 * @datetime 2016-12-2 9:37
 */
public class PayException extends ErrorCodeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2057680524075442815L;

	public PayException() {
    }

    public PayException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PayException(String message, Throwable cause) {
        super(message, cause);
    }

    public PayException(String message) {
        super(message);
    }

    public PayException(Throwable cause) {
        super(cause);
    }

    public PayException(ErrorCode code) {
        super(code);
    }

    public PayException(ErrorCode code, String message) {
        super(code, message);
    }
}
