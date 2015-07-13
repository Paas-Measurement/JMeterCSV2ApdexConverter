package de.model;

import java.io.Serializable;

public class SampleError implements Serializable{

	private static final long serialVersionUID = 1L;

	private String errorMsg;
	private String errorCode;
	private int errorCount;
	
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public int getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

	@Override
	public String toString() {
		return "["+ errorCode + " | " + errorMsg + " | " + errorCount + "]";
	}
}
