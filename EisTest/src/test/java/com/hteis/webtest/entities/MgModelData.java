package com.hteis.webtest.entities;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import com.hteis.webtest.common.DateUtil;

public class MgModelData {

	/*** 微网中文名 */
	public String MgName;

	/*** 微网英文名 */
	public String MgEngName;

	/*** 微网负责人 */
	public UserInfo MgAdmin;

	/*** 微网成员 */
	public UserInfo[] MgMembers;

	/*** 所属电网 */
	public String OwnedGrid;

	/*** 电压等级 */
	public String VoltageLevel;

	/*** 链路数量 */
	public int LinkCount;

	/*** 间隔数量 */
	public int BayCount;

	/*** 并网日期 */
	public Date ConnectDate;

	/*** 服务截至日期 */
	public Date ServiceEndDate;

	/*** 地址 */
	public String Address;

	/*** 描述 */
	public String Description;

	/*** 总装机 */
	public int TotalCapacity;

	/*** 储能 */
	public int StorageCapacity;

	/*** 光伏发电 */
	public int PvCapacity;

	/*** 总负荷 */
	public int TotalLoad;

	/*** 风力发电 */
	public int WpCapacity;

	/*** 清洁能源占比 */
	public int CleanEnergyPct;

	public MgLinkData[] Links;

	public MgBayData[] Bays;
	
	/***要添加的监控指标*/
	public ArrayList<String> indexNames;
	
	public int getDeviceCount(){
		int count = 0;
		for(MgBayData bay : this.Bays){
			count += bay.getDeviceCount();
		}
		
		return count;
	}
	
	public String getFullName(){
		return String.format("%s.%s微网", this.OwnedGrid, this.MgName);
	}
	
	public String getFullEnName(){
		return String.format("%s.%s-mg", "SC", this.MgEngName);
	}
	
	/***返回除管理员之外的成员*/
	public UserInfo[] getActualMembers(){
		ArrayList<UserInfo> users = new ArrayList<UserInfo>();
		for(UserInfo usr : this.MgMembers){
			if(!usr.loginName.equals(this.MgAdmin.loginName)){
				users.add(usr);
			}
		}
		
		return (UserInfo[])users.toArray(new UserInfo[]{});
	}
	
	public String[] getDisplayMembers(){
		ArrayList<String> members = new ArrayList<String>();
		for(UserInfo usr : this.MgMembers){
			if(!usr.loginName.equals(this.MgAdmin.loginName)){
				members.add(String.format("%s(%s)", usr.loginName, usr.name));
			}
		}
		
		return (String[])members.toArray(new String[]{});
	}

	public static MgModelData createMgData() throws ParseException{
		MgModelData modelData = new MgModelData();

		modelData.MgName = DateUtil.getNoFromDateTime("阳光小镇");
		modelData.MgEngName = DateUtil.getNoFromDateTime("MG");
		
		modelData.MgAdmin = new UserInfo("ag001", "阿零一", "13812345501");
		modelData.MgMembers = new UserInfo[] { modelData.MgAdmin, new UserInfo("ag002", "阿零二", "13812345502")};
		modelData.OwnedGrid = "四川";
		modelData.VoltageLevel = "0.4kV";
		modelData.ConnectDate = DateUtil.getDateFromString("2017-1-1");
		modelData.ServiceEndDate = DateUtil.getDateFromString("2028-12-31");
		modelData.Address = DateUtil.getNoFromDateTime("四川省绵阳市阳光小镇");
		modelData.Description = DateUtil.getNoFromDateTime("四川绵阳阳光小镇");
		modelData.TotalCapacity = 2000;
		modelData.StorageCapacity = 400;
		modelData.PvCapacity = 1500;
		modelData.TotalLoad = 4000;
		modelData.WpCapacity = 500;
		modelData.CleanEnergyPct = 50;
		
		return modelData;
	}
	
	public void updateMgData() throws ParseException{
		
		this.MgAdmin = this.MgMembers[1];
		this.ConnectDate = DateUtil.getDateFromString("2016-6-6");
		this.ServiceEndDate = DateUtil.getDateFromString("2026-10-30");
		this.Address = DateUtil.getNoFromDateTime("四川省绵阳市阳光小镇2号");
		this.Description = DateUtil.getNoFromDateTime("四川绵阳阳光小镇2号");
		this.TotalCapacity = 800;
		this.StorageCapacity = 100;
		this.PvCapacity = 500;
		this.TotalLoad = 3000;
		this.WpCapacity = 300;
		this.CleanEnergyPct = 20;
	}
	
	public static MgModelData createModelData() throws ParseException {
		MgModelData modelData = new MgModelData();

		modelData.MgName = DateUtil.getNoFromDateTime("小镇微网");
		modelData.MgEngName = DateUtil.getNoFromDateTime("MG");
		
		modelData.MgAdmin = new UserInfo("ag001", "阿零一", "13812345501");
		modelData.MgMembers = new UserInfo[] { modelData.MgAdmin, new UserInfo("ag002", "阿零二", "13812345502")};
		modelData.OwnedGrid = "四川";
		modelData.VoltageLevel = "15kV";
		modelData.LinkCount = 2;
		modelData.BayCount = 3;
		modelData.ConnectDate = DateUtil.getDateFromString("2016-1-1");
		modelData.ServiceEndDate = DateUtil.getDateFromString("2099-12-31");
		modelData.Address = DateUtil.getNoFromDateTime("四川省成都市阳光小镇");
		modelData.Description = DateUtil.getNoFromDateTime("四川成都阳光小镇");
		modelData.TotalCapacity = 1500;
		modelData.StorageCapacity = 400;
		modelData.PvCapacity = 1000;
		modelData.TotalLoad = 5000;
		modelData.WpCapacity = 500;
		modelData.CleanEnergyPct = 30;

		modelData.Links = new MgLinkData[] { MgLinkData.create104Link("北区链路", "NorthLink", 1),
				MgLinkData.createModbusRTULink("南区链路", "SouthLink", 2) };

		modelData.Bays = new MgBayData[] { MgBayData.createBayData1("间隔1", modelData.Links[0].FullName),
				MgBayData.createBayData2("间隔2", modelData.Links[0].FullName),
				MgBayData.createBayData1("间隔3", modelData.Links[1].FullName) };

		modelData.indexNames = new ArrayList<String>();
		modelData.indexNames.add("储能SOC");
		modelData.indexNames.add("年发电量");
		modelData.indexNames.add("累计发电量");
		return modelData;
	}
	
	public void updateModelData() throws Exception{
		this.MgMembers = new UserInfo[] { this.MgAdmin, new UserInfo("ag003", "阿零三", "13812345503")};
		this.ConnectDate = DateUtil.getDateFromString("2012-1-1");
		this.ServiceEndDate = DateUtil.getDateFromString("2027-12-11");
		this.Address = this.Address + "_modified";
		this.Description = this.Description + "_modified";
		this.TotalCapacity = this.TotalCapacity * 2;
		this.StorageCapacity = this.StorageCapacity * 2;
		this.PvCapacity = this.PvCapacity * 2;
		this.TotalLoad = this.TotalLoad * 2;
		this.WpCapacity = this.WpCapacity * 2;
		this.CleanEnergyPct = this.CleanEnergyPct + 10;
		
		for(MgLinkData linkData : this.Links){
			if(linkData.Type == MgLinkType.LinkModBusRTU){
				linkData.updateModbusRTULink();
			} else {
				linkData.update104Link();
			}
		}		
	}
	
	public static MgModelData createSimpleModelData() throws Exception{
		MgModelData modelData = new MgModelData();

		modelData.MgName = DateUtil.getNoFromDateTime("园区微网");
		modelData.MgEngName = DateUtil.getNoFromDateTime("MG");
		
		modelData.MgAdmin = new UserInfo("ag001", "阿零一", "13812345501");
		modelData.MgMembers = new UserInfo[] { modelData.MgAdmin, new UserInfo("ag002", "阿零二", "13812345502")};
		modelData.OwnedGrid = "四川";
		modelData.VoltageLevel = "15kV";
		modelData.LinkCount = 1;
		modelData.BayCount = 1;
		modelData.ConnectDate = DateUtil.getDateFromString("2010-1-1");
		modelData.ServiceEndDate = DateUtil.getDateFromString("2025-12-11");
		modelData.Address = DateUtil.getNoFromDateTime("四川省成都市科技园");
		modelData.Description = DateUtil.getNoFromDateTime("四川成都科技园");
		modelData.TotalCapacity = 1400;
		modelData.StorageCapacity = 400;
		modelData.PvCapacity = 1000;
		modelData.TotalLoad = 2000;
		modelData.WpCapacity = 500;
		modelData.CleanEnergyPct = 100;

		modelData.Links = new MgLinkData[] { MgLinkData.create104Link("链路一", "Link1", 1) };

		modelData.Bays = new MgBayData[] { MgBayData.createSimpleBayData("间隔1", modelData.Links[0].FullName)};

		return modelData;
	}
	
}
