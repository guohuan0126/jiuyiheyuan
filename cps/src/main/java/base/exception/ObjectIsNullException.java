package base.exception;

/**
 * @Description: 对象为空异常
 * @Author: 林志明
 * @CreateDate: Sep 13, 2014
 */
public class ObjectIsNullException extends Exception {
	
	private static final long serialVersionUID = 5700005265726496767L;

	public ObjectIsNullException(String msg, Throwable e) {
		super(msg, e);
	}

	public ObjectIsNullException(String msg) {
		super(msg);
	}
}
