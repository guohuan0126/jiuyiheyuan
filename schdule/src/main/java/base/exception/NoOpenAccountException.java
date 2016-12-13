package base.exception;

/**
 * 未开户
 * @author xiao
 * @datetime 2016年10月27日 下午4:28:08
 */
public class NoOpenAccountException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6713657340238577558L;

	public NoOpenAccountException(String msg, Throwable e) {
		super(msg, e);
	}
	
	public NoOpenAccountException(String msg) {
		super(msg);
	}
	
	public NoOpenAccountException() {
		super();
	}
}
