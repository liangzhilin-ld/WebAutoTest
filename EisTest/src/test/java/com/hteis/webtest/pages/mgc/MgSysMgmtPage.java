package com.hteis.webtest.pages.mgc;

import org.testng.Assert;

import com.hteis.webtest.common.DateUtil;
import com.hteis.webtest.entities.*;
import com.hteis.webtest.selenium.*;

public class MgSysMgmtPage extends MgPageBase{
	
	public static final int MgNumberColNo = 2;
	public static final int MgNameColNo = 3;
	public static final int MgAdressColNo = 4;
	public static final int MgAdminColNo = 5;
	public static final int MgContactTelColNo = 6;
	public static final int MgConnectDateColNo = 7;
	public static final int MgServiceEndDateColNo = 8;
	public static final int OpsColNo = 9;
	
	
	public HtmlButton getNewBtn(){
		return this.findElementByCss("button.btn-new").toHtmlButton();
	}
	
	public HtmlButton getSearchBtn(){
		return this.findElementByCss("button.recharge").toHtmlButton();
	}
	
	public HtmlInput getSearchInput(){
		return this.findElementById("mgsearchStr").toHtmlInput();
	}
	
	public HtmlTable getMgTable(){
		return this.findElementByTag("table").toHtmlTable();
	}
	
	public void verifyMgData(MgModelData mgData){
		HtmlRow row = this.getMgTable().findRow(MgModelPage.MgNameColNo, mgData.getFullName());
		Assert.assertNotNull(row, "未找到微网：" + mgData.getFullName());
		
		Assert.assertEquals(row.getCellValue(MgNameColNo), mgData.getFullName());
		Assert.assertEquals(row.getCellValue(MgAdressColNo), mgData.Address);
		Assert.assertEquals(row.getCellValue(MgAdminColNo), mgData.MgAdmin.name);
		Assert.assertEquals(row.getCellValue(MgContactTelColNo), mgData.MgAdmin.phone);
		Assert.assertEquals(row.getCellValue(MgConnectDateColNo), DateUtil.getCNDateStr(mgData.ConnectDate));
		Assert.assertEquals(row.getCellValue(MgServiceEndDateColNo), DateUtil.getCNDateStr(mgData.ServiceEndDate));
		
	}
	
	public void deleteMg(String mgName){
		//点击微网的编号
		HtmlRow row = this.getMgTable().findRow(MgModelPage.MgNameColNo, mgName);
		row.getCell(MgModelPage.MgNumberColNo).click();
		
		//点击删除按钮，点击"是"确认删除
		MgEditDialog editDialog = new MgEditDialog();
		editDialog.getDelBtn().click();	
		Driver.wait(1000);
		this.ClickConfirm();
	}
	
	public MgMgmtIndexPage openIndexPage(String mgName){
		HtmlRow row = this.getMgTable().findRow(MgModelPage.MgNameColNo, mgName);
		row.getCell(MgSysMgmtPage.OpsColNo).findElementByLinkText("维护监控指标").click();
		return new MgMgmtIndexPage();
	}
}
