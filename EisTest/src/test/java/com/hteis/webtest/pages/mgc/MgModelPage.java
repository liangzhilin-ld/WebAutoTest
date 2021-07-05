package com.hteis.webtest.pages.mgc;

import com.hteis.webtest.selenium.*;

public class MgModelPage extends MgPageBase {
	
	public static final int MgNumberColNo = 2;
	public static final int MgNameColNo = 3;
	public static final int MgAdressColNo = 4;
	public static final int MgAdminColNo = 5;
	public static final int MgContactTelColNo = 6;
	public static final int MgConnectDateColNo = 7;
	public static final int MgServiceEndDateColNo = 8;
	
	
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
	
	public MgEditModelStepPage1 openMgModel(String mgName){
		HtmlRow row = this.getMgTable().findRow(MgNameColNo, mgName);
		row.getCell(MgNumberColNo).findElementByTag("a").click();
		Driver.wait(1000);
		
		return new MgEditModelStepPage1();
	}
	
	 
}
