package base.exception;

public class RedPacketUseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public RedPacketUseException() {
		super();
	}

	public RedPacketUseException(String msg, Throwable e) {
		super(msg, e);
	}

	public RedPacketUseException(String msg) {
		super(msg);
	}
}
