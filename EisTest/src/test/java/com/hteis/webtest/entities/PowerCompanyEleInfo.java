package com.hteis.webtest.entities;

import java.util.Random;

public class PowerCompanyEleInfo {
	
	/***年发电量*/
	public int YearlyPowerGen;
	
	/***年上网电量*/
	public int YearlyPowerOutput;
	
	/***年用电量*/
	public int YearlyPowerConsume;
	
	/***装机容量*/
	public int Capacity;
	
	public static PowerCompanyEleInfo create(){
		PowerCompanyEleInfo data = new PowerCompanyEleInfo();
		data.YearlyPowerGen = 3000 + new Random().nextInt(1000);
		data.YearlyPowerOutput = 2500 + new Random().nextInt(500);
		data.YearlyPowerConsume = 500 + new Random().nextInt(500);
		data.Capacity = 4000 + new Random().nextInt(1000);
		
		return data;
	}
	
	public void update(){
		PowerCompanyEleInfo newInfo = PowerCompanyEleInfo.create();
		this.YearlyPowerGen = newInfo.YearlyPowerGen;
		this.YearlyPowerOutput = newInfo.YearlyPowerOutput;
		this.YearlyPowerConsume = newInfo.YearlyPowerConsume;
		this.Capacity = newInfo.Capacity;
	}
}
