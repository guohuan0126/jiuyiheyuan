package base.exception;

/**
 * @Description: 还款异常
 * @Author: 林志明
 * @CreateDate: Nov 24, 2014
 */
public class RepayException extends Exception {

	private static final long serialVersionUID = 1692313084260961792L;

	public RepayException() {
		super();
	}

	public RepayException(String msg, Throwable e) {
		super(msg, e);
	}

	public RepayException(String msg) {
		super(msg);
	}

}
