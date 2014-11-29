package com.example.healthplus.oauth;

import io.oauth.OAuthData;

import com.example.healthplus.database.MySQLiteHelper;

public class SerializableOauthData {
	/**
	 * 
	 */
	private static OAuthData data = null;
	private static MySQLiteHelper sqlHelper;
	
	public static MySQLiteHelper getSqlHelper() {
		return sqlHelper;
	}

	public static void setSqlHelper(MySQLiteHelper sqlHelper) {
		SerializableOauthData.sqlHelper = sqlHelper;
	}

	public static void setOauthData(OAuthData data){
		SerializableOauthData.data = data;
		
	}
	
	public  static OAuthData getOauthData(){
		return data;
		
	}
	
	
	
	
	
}
