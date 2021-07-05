package com.hteis.webtest.pages.mgc;

import com.hteis.webtest.selenium.*;
import com.hteis.webtest.entities.*;

public class MgDevicePage extends MgPageBase {
	
	public static final int NumberColNo = 2;
	public static final int EnNameColNo = 3;
	public static final int NameColNo = 4;
	public static final int BayColNo = 5;
	public static final int LinkColNo = 6;
	public static final int TypeColNo = 7;
	public static final int ContactColNo = 8;
	public static final int PhoneColNo = 9;
	
	public HtmlTable getDeviceTable(){
		return this.findElementByTag("table").toHtmlTable();
	}
	
	public HtmlNgPager getPager(){
		return this.findElementByClass("page-list").toHtmlNgPager();
	}
	
	public DeviceData getDeviceData(String deviceName){
		HtmlRow row = this.getDeviceTable().findRow(NameColNo, deviceName);
		DeviceData dev = new DeviceData();
		dev.No = row.getCellValue(NumberColNo);
		dev.EnName = row.getCellValue(EnNameColNo);
		dev.Name = row.getCellValue(NameColNo);
		dev.BayName = row.getCellValue(BayColNo);
		dev.LinkName = row.getCellValue(LinkColNo);
		dev.Contact = row.getCellValue(ContactColNo);
		dev.Phone = row.getCellValue(PhoneColNo);
		dev.Type = MgDeviceType.getTypeFromId(row.getCellValue(TypeColNo));
		
		return dev;
	}
}
