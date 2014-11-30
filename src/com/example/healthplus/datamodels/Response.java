package com.example.healthplus.datamodels;

public class Response {

	private String userName;
	private String Result;
	private String TBL_NAME;
	private String COL_NAME;
	private String COL_FUNCTION;

	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getResult() {
		return Result;
	}
	
	public void setResult(String result) {
		Result = result;
	}	
	
	public String getTBL_NAME() {
		return TBL_NAME;
	}
	
	public void setTBL_NAME(String tBL_NAME) {
		TBL_NAME = tBL_NAME;
	}
	
	public String getCOL_NAME() {
		return COL_NAME;
	}
	
	public void setCOL_NAME(String cOL_NAME) {
		COL_NAME = cOL_NAME;
	}
	
	public String getCOL_FUNCTION() {
		return COL_FUNCTION;
	}
	
	public void setCOL_FUNCTION(String cOL_FUNCTION) {
		COL_FUNCTION = cOL_FUNCTION;
	}
}
