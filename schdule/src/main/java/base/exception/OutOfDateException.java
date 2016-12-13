package base.exception;
/**
 * 过期
 * @author 尹逊志
 * @date 2014-8-28下午6:25:18
 */
public class OutOfDateException extends Exception{

	private static final long serialVersionUID = 6563789304127191225L;
	public OutOfDateException(String msg, Throwable e) {
		super(msg, e);
	}
	
	public OutOfDateException(String msg) {
		super(msg);
	}
	public OutOfDateException(){
		
	}
}
