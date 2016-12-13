package base.exception;

/**
 * @Description: 参数异常
 * @Author: 林志明
 * @CreateDate: Sep 13, 2014
 */
public class ParameterException extends Exception {
	
	private static final long serialVersionUID = 5026502878782569236L;

	public ParameterException(String msg, Throwable e) {
		super(msg, e);
	}

	public ParameterException(String msg) {
		super(msg);
	}
	
	public ParameterException() {
		super();
	}
}
