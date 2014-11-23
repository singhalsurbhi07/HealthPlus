package com.example.healthplus.oauth;

import io.oauth.OAuthData;
import io.oauth.OAuthRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;

public class SerializableOauthData {
	/**
	 * 
	 */
	private static OAuthData data = null;
	
	public static void setOauthData(OAuthData data){
		SerializableOauthData.data = data;
		
	}
	
	public  static OAuthData getOauthData(){
		return data;
		
	}
	
	
	
}
