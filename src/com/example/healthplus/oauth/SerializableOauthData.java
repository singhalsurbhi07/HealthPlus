package com.example.healthplus.oauth;

import io.oauth.OAuthData;
import io.oauth.OAuthRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;

import com.example.healthplus.database.MySQLiteHelper;
import com.example.healthplus.datamodels.WaterConsumeModel;
import com.example.healthplus.utils.DateUtil;

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
