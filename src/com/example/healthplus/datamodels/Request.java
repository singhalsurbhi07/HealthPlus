package com.example.healthplus.datamodels;

public class Request {

	private static String MasterDevice;
	private static String UserName;
	private static String Query;
	private static String TBL_NAME;
	private static String COL_NAME;
	private static String COL_FUNCTION;

	
	public Request() {
		
	}
	
	public Request(String masterDevice, String userName, String query, String tblName, String colName, String colFunction) {
		
		MasterDevice = masterDevice;
		UserName = userName;
		Query = query;
		TBL_NAME = tblName;
		COL_NAME = colName;
		COL_FUNCTION = colFunction;	
	}
	
	public static String getTBL_NAME() {
		return TBL_NAME;
	}
	
	public static void setTBL_NAME(String tBL_NAME) {
		TBL_NAME = tBL_NAME;
	}
	
	public  static String getCOL_NAME() {
		return COL_NAME;
	}
	
	public  static void setCOL_NAME(String cOL_NAME) {
		COL_NAME = cOL_NAME;
	}
	
	public  static String getCOL_FUNCTION() {
		return COL_FUNCTION;
	}
	
	public  static void setCOL_FUNCTION(String cOL_FUNCTION) {
		COL_FUNCTION = cOL_FUNCTION;
	}
	
	public  static String getMasterDevice() {
		return MasterDevice;
	}
	
	public  static void setMasterDevice(String masterDevice) {
		MasterDevice = masterDevice;
	}
	
	public  static String getUserName() {
		return UserName;
	}
	
	public  static void setUserName(String userName) {
		UserName = userName;
	}
	
	public  static String getQuery() {
		return Query;
	}
	
	public  static void setQuery(String query) {
		Query = query;
	}
}