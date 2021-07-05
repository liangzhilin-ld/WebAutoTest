package com.hteis.webtest.pages;

import com.hteis.webtest.common.PathUtil;
import com.hteis.webtest.entities.CompanyEleInfo;
import com.hteis.webtest.selenium.*;

public class CompanyEleInfoForm{

	private HtmlElement container;
	
	public CompanyEleInfoForm(HtmlElement container){
		this.container = container;
	}

	/***主变容量*/
	public HtmlInput getMainCapacityInput(){
		return this.container.findElementById("mainCapacity").toHtmlInput();
	}
	
	/***备用容量*/
	public HtmlInput getReserveCapacityInput(){
		return this.container.findElementById("standbyCapacity").toHtmlInput();
	}
	
	/***运行容量*/
	public HtmlInput getRunCapacityInput(){
		return this.container.findElementById("runCapacity").toHtmlInput();
	}
	
	/***可控容量*/
	public HtmlInput getControlCapacityInput(){
		return this.container.findElementById("controlCapacity").toHtmlInput();
	}
	
	/***所属变电站*/
	public HtmlInput getSubstationInput(){
		return this.container.findElementById("subName").toHtmlInput();
	}
	
	/***所属线路*/
	public HtmlInput getLineNameInput(){
		return this.container.findElementById("lineName").toHtmlInput();
	}
	
	/***供电电压*/
	public HtmlInput getLineVoltageInput(){
		return this.container.findElementById("volt").toHtmlInput();
	}
	
	/***年用电量*/
	public HtmlInput getYearConsumeInput(){
		return this.container.findElementById("yearPower").toHtmlInput();
	}
	
	/***最大负荷*/
	public HtmlInput getMaxLoadInput(){
		return this.container.findElementById("maxLoad").toHtmlInput();
	}
	
	/***保安负荷*/
	public HtmlInput getProtectLoadInput(){
		return this.container.findElementById("saveLoad").toHtmlInput();
	}
	
	/***企业电工*/
	public HtmlInput getElectricianInput(){
		return this.container.findElementById("electrician").toHtmlInput();
	}
	
	/***值班电话(电工)*/
	public HtmlInput getDutyPhoneInput(){
		return this.container.findElementById("dutyPhone").toHtmlInput();
	}
	
	/***用电特性*/
	public HtmlSelect getElecFeatureSelect(){
		return this.container.findElementByTag("select").toHtmlSelect();
	}
	
	/***电费结算方式*/
	public HtmlInput getSettleMethodInput(){
		return this.container.findElementById("elecSettle").toHtmlInput();
	}
	
	/***上传附件链接*/
	public HtmlLink getUploadLink(){
		return this.container.findElementByLinkText("上传附件").toHtmlLink();
	}
	
	public HtmlButton getSaveBtn(){
		return this.container.findElementByCss("div > button:first-child").toHtmlButton();
	}
	
	public HtmlButton getCancelBtn(){
		return this.container.findElementByCss("div > button:nth-child(2)").toHtmlButton();
	}
	
	public HtmlButton getDeleteBtn(){
		return this.container.findElementByCss("div > button:nth-child(2)").toHtmlButton();
	}
	
	public HtmlButton getCloseBtn(){
		return this.container.findElementByCss("div > button:last-child").toHtmlButton();
	}
	
	public void setFieldValues(CompanyEleInfo eleInfo) throws Exception{
		this.getMainCapacityInput().setText(Integer.toString(eleInfo.MainCapacity));
		this.getReserveCapacityInput().setText(Integer.toString(eleInfo.ReserveCapacity));
		this.getRunCapacityInput().setText(Integer.toString(eleInfo.RunCapacity));
		this.getControlCapacityInput().setText(Integer.toString(eleInfo.ControlCapacity));
		this.getSubstationInput().setText(eleInfo.SubstationName);
		this.getLineNameInput().setText(eleInfo.LineName);
		this.getLineVoltageInput().setText(Integer.toString(eleInfo.LineVoltage));
		this.getYearConsumeInput().setText(Integer.toString(eleInfo.YearlyConsume));
		this.getMaxLoadInput().setText(Integer.toString(eleInfo.MaxLoad));
		this.getProtectLoadInput().setText(Integer.toString(eleInfo.ProtectiveLoad));
		this.getElectricianInput().setText(eleInfo.Electrician);
		this.getDutyPhoneInput().setText(eleInfo.DutyPhone);
		this.getElecFeatureSelect().selectByText(eleInfo.ElectroFeature);
		this.getSettleMethodInput().setText(eleInfo.SettleMethod);
		
		// 上传授权委托书
		this.getUploadLink().click();
		Driver.wait(1000);
		String path = PathUtil.getResourceFilePath(eleInfo.AttachmentFileName);
		Keyboard.TypeString(path);	
		Keyboard.TypeEnter();
		Driver.wait(1000);
	}
	
}
