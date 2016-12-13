package base.exception;

/**
 * Exception 验证码不匹配 对象不匹配
 * @author 尹逊志
 * @date 2014-8-28下午6:24:22
 */
@SuppressWarnings("rawtypes")
public class NoMatchingObjectsException  extends Exception{
	private static final long serialVersionUID = 826211177338703143L;
	private Class clazz;

	public NoMatchingObjectsException(Class clazz, String msg, Throwable e) {
		super(msg, e);
		this.clazz = clazz;
	}
	
	public NoMatchingObjectsException(Class clazz, String msg) {
		super(msg);
		this.clazz = clazz;
	}

	public Class getClazz() {
		return clazz;
	}
}
