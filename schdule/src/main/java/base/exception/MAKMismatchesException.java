package base.exception;

/**
 * @Description: 密钥不匹配异常 
 * @Author:	林志明
 * @CreateDate:	Sep 23, 2014
 */
public class MAKMismatchesException extends Exception {
	private static final long serialVersionUID = -678389771494296768L;

	public MAKMismatchesException() {
		super();
	}
	
	public MAKMismatchesException(String msg, Throwable e) {
		super(msg, e);
	}

	public MAKMismatchesException(String msg) {
		super(msg);
	}
	
}
