package base.validationCode;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;


public class ValidateCodeCheckCommand implements Command {
	private String checkName;
	private String actionName;

	public String getCheckName() {
		return checkName;
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	@Override
	public <T> void excuteCommand(T t) throws ValidationException {
		HttpServletRequest request = (HttpServletRequest) t;
		String requestPath = request.getServletPath();
		if (requestPath.contains(this.actionName)) {
			String value = request.getParameter(checkName);
			String valueOr = (String) request.getSession().getAttribute(
					Constants.VALIDATE_CODE);
			System.out.println("validateCode-----------input:"+value+";----sessionCode:"+valueOr+"");
			if (StringUtils.isNotBlank(value)
					&& StringUtils.isNotBlank(valueOr)
					&& value.equalsIgnoreCase(valueOr)) {
				request.getSession().removeAttribute(Constants.VALIDATE_CODE);
			} else {
			   throw new ValidationException("validation.0001");//验证码校验失败
			}
		}
	}
}
