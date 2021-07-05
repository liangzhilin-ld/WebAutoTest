package com.hteis.webtest.entities;

/***链路数据*/
public class MgLinkData {
	
	/***序号*/
	public int Sequence;
	
	/***中文名称*/
	public String Name;
	
	/***全名称*/
	public String FullName;
	
	/***英文名称*/
	public String EnName;
	
	/***英文全名称*/
	public String FullEnName;
	
	/***链路类型*/
	public MgLinkType Type;
	
	/***链路端口*/
	public String Port;
	
	/***链路地址*/
	public String Address;
	
	/***串口名*/
	public String ComName;
	
	/***波特率*/
	public int BaudRate;
	
	/***数据位*/
	public int DataBits;
	
	/***校验位*/
	public int CheckBits;
	
	/***停止位*/
	public int StopBits;
	
	public static MgLinkData create104Link(String name, String enName, int sequence){
		MgLinkData linkData = new MgLinkData();
		linkData.Name = name;
		linkData.Sequence = sequence;
		linkData.FullName = String.format("链路#%d%s", sequence, name);
		linkData.FullEnName = String.format("link#%d%s", sequence, name);
		linkData.EnName = enName;
		linkData.Type = MgLinkType.Link104;
		linkData.Port = "10001";
		linkData.Address = "172.16.13.88";
		
		return linkData;
	}
	
	public void update104Link(){
		this.Port = "80001";
		this.Address = "192.168.3.2";
	}
	
	public static MgLinkData createModbusRTULink(String name, String enName, int sequence){
		MgLinkData linkData = new MgLinkData();
		linkData.Name = name;
		linkData.Sequence = sequence;
		linkData.FullName = String.format("链路#%d%s", sequence, name);
		linkData.FullEnName = String.format("link#%d%s", sequence, name);
		linkData.EnName = enName;
		linkData.Type = MgLinkType.LinkModBusRTU;
		linkData.ComName = "COM1";
		linkData.BaudRate = 120;
		linkData.DataBits = 8;
		linkData.CheckBits = 1;
		linkData.StopBits = 2;
		
		return linkData;
	}
	
	public void updateModbusRTULink(){
		this.ComName = "COM2";
		this.BaudRate = 240;
		this.DataBits = 10;
		this.CheckBits = 2;
		this.StopBits = 1;
	}

}
