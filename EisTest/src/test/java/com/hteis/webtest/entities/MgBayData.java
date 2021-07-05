package com.hteis.webtest.entities;

/*** 间隔内设备数据 */
public class MgBayData {

	public String BayName;

	public String LinkName;

	public int PVPanelNo;

	public int PVArrayNo;

	public int PVInverterNo;

	public int WindFarmNo;

	public int WindTurbineNo;

	public int WindInverterNo;

	public int BatteryNo;

	public int PCSNo;

	public int ChargePilNo;
	
	public int UPSNo;

	public int getDeviceCount() {
		return this.PVPanelNo + this.PVArrayNo + this.PVInverterNo + this.WindFarmNo + this.WindTurbineNo
				+ this.WindInverterNo + this.BatteryNo + this.PCSNo + this.ChargePilNo + this.UPSNo;
	}
	
	public static MgBayData createSimpleBayData(String bayName, String linkName) {
		MgBayData data = new MgBayData();
		data.BayName = bayName;
		data.LinkName = linkName;

		data.PVPanelNo = 1;
		return data;
	} 

	public static MgBayData createBayData1(String bayName, String linkName) {
		MgBayData data = new MgBayData();
		data.BayName = bayName;
		data.LinkName = linkName;

		data.PVPanelNo = 3;
		data.PVInverterNo = 2;
		data.WindFarmNo = 1;
		data.WindInverterNo = 1;
		data.BatteryNo = 1;
		data.PCSNo = 1;
		data.ChargePilNo = 2;

		return data;
	}

	public static MgBayData createBayData2(String bayName, String linkName) {
		MgBayData data = new MgBayData();
		data.BayName = bayName;
		data.LinkName = linkName;

		data.PVArrayNo = 1;
		data.PVInverterNo = 1;
		data.BatteryNo = 2;
		data.PCSNo = 1;

		return data;
	}
	
	/***向已有间隔添加的设备*/
	public static MgBayData createAddedBayData(MgBayData bay){
		MgBayData data = new MgBayData();
		data.BayName = bay.BayName;
		data.LinkName = bay.LinkName;
		
		data.PVPanelNo = 1;
		data.UPSNo = 2;
		
		bay.PVPanelNo += data.PVPanelNo;
		bay.UPSNo += data.UPSNo;
		
		return data;
	}
	
	
	
	
	
}
