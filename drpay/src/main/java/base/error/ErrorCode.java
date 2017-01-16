package base.error;

/**
 * 错误码
 * 
 * @author xiao
 * 错误码枚举定义 统一大写字母，单词之间"_"下划线隔开
 */
public enum ErrorCode {
	/**
	 * 0	请求处理失败
	 * 1	请求处理成功
	 */
	
	SUCCESS("1", "成功"),
	FAIL("0", "失败"),

	
	/**
	 * 20 基本信息异常
	 */
	RequestTooMany("20000", "您请求的次数过多，暂时被屏蔽，请联系客服人员。"),
	ParametersError("20001","传入的参数错误"),
	QueryTimeTooLong("20002","查询时间间隔超过一个月"),
	BusinessEnumNotFind("20003", "未知的业务类型"),
	
	/**
	 * 30 项目异常
	 */
	LoanNotFind("30000", "项目不存在"),
	RepayTypeNotFind("30001", "未知的还款方式"),
	LoanStatusError("30002", "项目状态不正确"),
	LoanNoInvestor("30003", "没有有效投资人"),
	MoneyRaiseNoTotal("30004", "募集金额不等于借款金额"),
	
	/**
	 * 31 充值异常
	 */
	RechargeOutMoney("31000","充值金额不能低于1元"),
	RechargeNoAmount("31001","充值额度已用完"),
	RechargeNotFindChannel("31002","未找到充值渠道"),
	RechargeFail("31003","充值失败"),
	RechargeWayWrong("31004","当前充值方式不正确"),
	
	/**
	 * 32 投资异常
	 */
	InvestNoPermission("32000", "借款人不能投资"),
	InvestOutRepayTime("32000","投资失败，已超过支付时间"),
	InvestSuccessNoAgainRepay("32001","投资已成功，无需再次支付"),
	InvestNotFind("32002", "投资不存在"),
	InvestStatusError("32003", "投资状态不正确"),
	OldUserNotInvestNewbieEnjoy("32004","老用户不能投资新手专享项目"),
	InvestMoneyGreaterThanLoanNeedMoney("32005", "投资金额大于项目可投金额"),

	AutoInvestFail("33000","自动投标失败"),
	AutoInvestMoneyError("33001","金额请输入100的整数倍"),
	AutoInvestMinMoneyError("33002","最小金额不能小于100"),
	AutoInvestMaxMoneyError("33003","最大金额不能大于1000000"),
	AutoInvestRateError("33004","请输入正确的区间范围"),
	
	/**
	 * 40 鉴权错误
	 */
	REFUSE("40006", "请求被拒"),	
	SIGN_INVALID("40061", "签名无效"),
	TIMESTAMP_INVALID("40062", "时间戳无效"),
	TIMESTAMP_EXPIRE("40063", "时间戳过期"),
	VERSION_INVALID("40064", "接口版本号无效"),
	PASSWORD_ERROR("40065", "交易密码错误"),
	REFUSEIP("40066", "无权调用此接口"),
	/**
	 * 41 用户异常
	 */
	UserNoRegist("41000", "用户未注册"),	
	UserDisable("41001", "用户被禁用"),
	UserNoInvestor("41002", "用户没有投资者权限"),
	UserNoLoaner("41003", "用户没有借款者或居间人权限"),
	TargetUserNotFind("41004", "转账用户不存在"),
	TargetUserNotInvestor("41005", "只能给普通用户转账"),
	IdCardNoAlreadyExist("41006","身份证号已被使用，请重新输入"),
	AuthCodeFail("41007","验证码错误"),
	LoginPasswordError("41008","登录密码错误"),
	OldMobileError("41009","原手机号码错误"),
	IdCardNoError("41010","身份证号码错误"),
	MobileFormatError("41011","手机号格式不正确"),
	MobileExist("41012","手机号已存在"),
	RequestFailTryAgain("41013","请求失败，请重新提交"),
	
	/**
	 * 42  账户异常
	 */
	UserAccountOpened("42000", "已开户"),
	UserAccountError("42001", "账户异常"),
	UserAccountNoOpened("42002", "用户未开户"),
	UserAccountFreeze("42003", "账户已冻结"),
	UserAccountNoActivate("42004", "账户未激活"),
	UserAccountStatusError("42008","账户状态不正确"),
	BalanceToLow("42005", "余额不足"),
	FrozeToLow("42006", "冻结金额不足"),
	UserAccountNoBalance("42007", "冻结金额不足"),
	PaymentBalanceToLow("42101", "支付账户余额不足"),
	PaymentFrozeToLow("42102", "支付账户冻结金额不足"),
	PlatformBalanceToLow("42201", "平台账户余额不足"),
	PlatformFrozeToLow("42202", "平台账户冻结金额不足"),
	RewardExist("43301", "奖励已发送"),
	RewardNoFind("43302", "该奖励记录不存在"),
	RewardError("43302", "奖励发送失败，请更换流水号"),
	WithdrawCashNoFind("43303", "提现记录不存在"),
	TransferNoFind("43304", "转账记录不存在"),
	TransferStatusError("43305", "转账状态不正确"),
	MoneyFreezeNotFind("43306", "未找到资金冻结记录"),
	
	/**
	 * 43 银行卡异常
	 */
	BindCardExist("43001", "银行已绑定"),
	BankCardNoFound("43002","未绑卡"),
	BindCardFail("43002", "绑卡失败"),
	BindCardFail1("43003", "持卡人信息有误，请修改预留手机号码重新提交"),
	BankCardValid("43004", "输入卡号有误，请重新输入"),//卡号不合法
	BankCardNoDiscern("43005", "无法识别此银行卡"),
	CreditCardNoBind("43006", "暂不支持绑定信用卡"),
	CardNoNotIdenticalWithBankNo("43007","输入的银行卡号与所选银行不一致"),
	BankCardNotSupported("43008","暂不支持该行绑卡"),
	UnbindingCardNotFound("43009","不存在解绑中的银行卡"),
	UnbindCardFail("43010","解绑银行卡失败"),
	UnbindingCardExist("43011","已存在待审核的解绑申请"),
	
	/**
	 * 44 活期宝异常
	 */
	DemandTransferOutNotSended("44001","活期宝转出申请已处理，不可撤销"),
	DemandBalanceToLow("44002"," 活期宝账户金额不足"),
	DemandTransferOutNoUser("44003","没有可转出的用户"),
	DemandTransferOutNotComplete("44004","存在未处理完成的转出"),
	DemandTransferOutGuOutNotSended("44005","GuOut状态不为sended"),
	DemandTransferOutOutMoney("44006","转出金额不能低于0元或大于两位小数"),
	DemandTransferOutTooManyTimes("44007","转出次数超过限制"),
	DemandTransferOutOutMaxMoney("44008","转出金额大于今日转出上限"),
	DemandTransferOutNotInTime("44009","提现时间不在指定范围内"),
	
	
	/**
	 * 45 存管通接口调用异常
	 */
	CgtCodeError("45001","存管通接口响应code不为0"),
	CgtRespDataIsNull("45002","存管通接口响应数据为空"),
	
	ServerFail("50001", "系统错误");

	private final String errorCode;
	private final String errorMessage;

	ErrorCode(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	@Override
	public String toString() {
		return "[" + "\"errorCode\":\"" + this.errorCode + "\", "
				+ "\", \"errorMessage\":\"" + this.errorMessage + "\"]";
	}
}
