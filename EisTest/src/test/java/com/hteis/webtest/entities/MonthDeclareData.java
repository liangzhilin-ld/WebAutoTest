package com.hteis.webtest.entities;

import java.util.Random;

public class MonthDeclareData {
	
	/***月份: 1-12*/
	public int Month;
	
	/***月度分解电量*/
	public float MonthAmount;
	
	/***申报电量(增量 )*/
	public float DiffAmount;
	
	/***申报总量*/
	public float TotalAmount;
	
	/***增量比例*/
	public Float DiffPercent;
	
	/***环比增幅*/
	public Float LinkRatio;
	
	/***同比增幅*/
	public Float YearToYearRatio;
	
	public static MonthDeclareData create(float monthAmount, float diffAmount, int month){
		MonthDeclareData data = new MonthDeclareData();
		data.Month = month;
		data.MonthAmount = monthAmount;
		data.DiffAmount = diffAmount;
		data.TotalAmount = (float)Math.round((monthAmount + diffAmount) * 10000) / 10000;
		if(diffAmount == 0){
			data.DiffPercent = null;
		} else {
			data.DiffPercent = (float)Math.round(diffAmount * 10000 / monthAmount) / 100;
		}
		
		data.LinkRatio = null;
		data.YearToYearRatio = null;
		return data;
	}
	
	public static MonthDeclareData[] create(float[] monthAmounts, float[] diffAmounts, int startMonth){
		MonthDeclareData data[] = new MonthDeclareData[monthAmounts.length];
		for(int i = 0; i < data.length; i ++){
			data[i] = create(monthAmounts[i], diffAmounts[i], startMonth + i);
		}
		
		return data;
	}
	
	public static MonthDeclareData[] create(float[] monthAmounts, int startMonth){
		return create(monthAmounts, createDiffAmounts(monthAmounts), startMonth);
	}
	
	public static float[] createDiffAmounts(float[] monthAmounts){
		float[] diffAmounts = new float[monthAmounts.length];
		for(int i = 0; i < diffAmounts.length; i++){
			diffAmounts[i] = (new Random().nextFloat()) * 3 * monthAmounts[i] - monthAmounts[i];
			diffAmounts[i] = (float)Math.round(diffAmounts[i] * 10000) / 10000;
		}
		
		return diffAmounts;
	}
	
	/***计算多月申报中的环比增幅*/
	public static void updateLinkRatio(MonthDeclareData[] dataArr){
		for(int i = 1; i < dataArr.length; i++){
			dataArr[i].LinkRatio = (float)Math.round((dataArr[i].TotalAmount - dataArr[i-1].TotalAmount) * 10000 / dataArr[i-1].TotalAmount) / 100;
		}
	}
}
