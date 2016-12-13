package base.exception;

/**
 * @Description: 投资人与融资人ID一致
 * @Author: 林志明
 * @CreateDate: Dec 9, 2014
 */
public class InvestorsAndFinanciersIDException extends Exception {
	private static final long serialVersionUID = -677264983546989846L;

	public InvestorsAndFinanciersIDException() {
	}

	public InvestorsAndFinanciersIDException(String msg) {
		super(msg);
	}
}
