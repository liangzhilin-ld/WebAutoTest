package com.hteis.webtest.entities;

import java.util.Random;

import com.hteis.webtest.common.DateUtil;

public class PowerCompanyMainInfo {
	
	/***企业编号*/
	public String Number;
	
	/***企业名称*/
	public String Name;
	
	/***企业性质*/
	public String CompanyType;
	
	/***发电类型*/
	public String PowerType;
	
	/***企业规模*/
	public String Scale;
	
	/***所属区域*/
	public String District;
	
	private static String[] CompanyTypes = {"国企", "外商独资", "代表处", "合资","民营", "股份制企业", "上市公司", "国家机关", "事业单位", "其它"};	
	private static String[] PowerTypes = {"水电","火电","风电", "核电", "光伏", "其他"};
	private static String[] Scales = {"0-20人以下", "20-99人", "100-499人", "500-999人", "1000-9999人", "10000人以上"};
	private static String[] Provinces = {"北京", "四川", "云南", "广州","上海"};
	
	public static PowerCompanyMainInfo create(){
		PowerCompanyMainInfo mainInfo = new PowerCompanyMainInfo();
		mainInfo.Number = DateUtil.getNoFromDateTime();
		mainInfo.Name = DateUtil.getNoFromDateTime("发电企业");		
		mainInfo.CompanyType = CompanyTypes[new Random().nextInt(CompanyTypes.length)];
		mainInfo.PowerType = PowerTypes[new Random().nextInt(PowerTypes.length)];
		mainInfo.Scale = Scales[new Random().nextInt(Scales.length)];
		mainInfo.District = Provinces[new Random().nextInt(Provinces.length)];
		
		
		return mainInfo;
	}
	
	public void update(){
		PowerCompanyMainInfo newInfo = PowerCompanyMainInfo.create();
		this.Number = newInfo.Number;
		this.Name = newInfo.Name;
		this.CompanyType = newInfo.CompanyType;
		this.PowerType = newInfo.PowerType;
		this.Scale = newInfo.Scale;
		this.District = newInfo.District;
	}
}
