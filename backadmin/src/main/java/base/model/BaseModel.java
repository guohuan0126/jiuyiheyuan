package base.model;

import java.io.Serializable;

public class BaseModel implements Serializable {
	private static final long serialVersionUID = -194679610105613286L;

	private int resultCode;
	private String resultInfo;

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(String resultInfo) {
		this.resultInfo = resultInfo;
	}

}
