
package base.validationCode;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 */

public class ValidationException extends Exception {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */

	private static final long serialVersionUID = 1L;

	/**
	 * <p>
	 * title:
	 * </p>
	 * <p>
	 * description:
	 * </p>
	 */
	public ValidationException() {
		super();
	}

	/**
	 * 
	 * <p>
	 * title:
	 * </p>
	 * <p>
	 * description:
	 * </p>
	 * 
	 * @param message
	 * @param cause
	 */
	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 
	 * <p>
	 * title:
	 * </p>
	 * <p>
	 * description:
	 * </p>
	 * 
	 * @param message
	 */
	public ValidationException(String message) {
		super(message);
	}

	/**
	 * 
	 * <p>
	 * title:
	 * </p>
	 * <p>
	 * description:
	 * </p>
	 * 
	 * @param cause
	 */
	public ValidationException(Throwable cause) {
		super(cause);
	}

}
