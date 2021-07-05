package com.hteis.webtest.entities;

import com.hteis.webtest.common.*;

public class UserInfo {

	public UserInfo(){
		
	}
	
	public UserInfo(String loginName, String name, String phone){
		this.loginName = loginName;
		this.name = name;
		this.phone = phone;
	}
	
	public String loginName;
	
	public String name;
	
	public String phone;
	
	public String email;
	
	public String province;
	
	public String password;
	
	public static UserInfo createBuyer(){
		UserInfo data = new UserInfo();
		data.loginName = DateUtil.getNoFromDateTime("u");
		data.phone = DateUtil.getUniquePhone();
		data.email = data.phone + "@qq.com";
		data.province = Constants.custProvice;
		data.password = "123456";
		
		return data;
	}
}
