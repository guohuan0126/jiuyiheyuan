package base.error;

/**
 * 错误码
 * @author xiao
 *
 */
public enum ErrorCode {

	SUCCESS(1, "success", "成功"),
	FAIL(0, "fail", "失败"),
	
	REFUSE(406, "request is refuse", "请求被拒"),
	SIGN_INVALID(4061, "sign is invilid", "签名无效");
	
	
	
	
					
	private final int code;
	private final String value;
	private final String message;

	private ErrorCode(int code, String value, String message) {
		this.code = code;
		this.message = message;
		this.value = value;
	}
	
	public int getCode(){
		return this.code;
	}
	
	public String getValue(){
		return this.value;
	}
	
	public String getMessage(){
		return this.message;
	}
}
