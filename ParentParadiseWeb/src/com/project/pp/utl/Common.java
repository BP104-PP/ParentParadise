package com.project.pp.utl;

import java.util.ArrayList;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Common {
	
	public static String joinArrayListString(ArrayList<String> array, String delimiter) {
        if(array == null || array.size() == 0 ){
            return "";
        }
        StringBuffer sb = new StringBuffer();
        int i, len = array.size() - 1;
        for (i = 0; i < len; i++){
            sb.append(array.get(i) + delimiter);
        }
        return sb.toString() + array.get(i);
    }
	
	public static java.sql.Date getCurrentDateTime() {
		java.util.Date today = new java.util.Date();
		return new java.sql.Date(today.getTime());
	}
	
	public static String encrypt(String password) {
        String algorithm = "HmacSHA1";
        String keyString = "stevenKey3";
        
        SecretKey key = new SecretKeySpec(keyString.getBytes(), algorithm);
        
        try {
            Mac m = Mac.getInstance(algorithm);
            m.init(key);
            m.update(password.getBytes());
            byte[] mac = m.doFinal();
            return toHexString(mac);
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        
        return "";
    }
	
	public static String toHexString(byte[] in) {
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < in.length; i++){
            String hex = Integer.toHexString(0xFF & in[i]);
            if (hex.length() == 1){
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
