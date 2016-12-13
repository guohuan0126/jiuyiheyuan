package base.exception;

public class QueryTimeTooLongException extends Exception {

	private static final long serialVersionUID = -1913001554651473960L;

	public QueryTimeTooLongException() {
		super();
	}

	public QueryTimeTooLongException(String msg) {
		super(msg);
	}

	public QueryTimeTooLongException(String msg, Throwable e) {
		super(msg, e);
	}
}
