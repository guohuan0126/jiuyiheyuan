package base.exception;
/**
 * 验证码过期
 * @author 尹逊志
 * @date 2014-8-28下午6:25:18
 */
public class AuthInfoOutOfDateException extends Exception{
	private static final long serialVersionUID = 5609506527576811992L;

	public AuthInfoOutOfDateException(String msg, Throwable e) {
		super(msg, e);
	}
	
	public AuthInfoOutOfDateException(String msg) {
		super(msg);
	}
}
