package com.example.healthplus.datamodels;

import java.io.Serializable;

public class Response implements Serializable {
	String userName;
	String resultValue;
	String dataType;
	
	public Response(String userName, String resultValue, String dataType){
		this.userName = userName;
		this.resultValue = resultValue;
		this.dataType = dataType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getResultValue() {
		return resultValue;
	}
	public void setResultValue(String resultValue) {
		this.resultValue = resultValue;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
}
