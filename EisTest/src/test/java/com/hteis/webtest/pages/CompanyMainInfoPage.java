package com.hteis.webtest.pages;

import com.hteis.webtest.entities.CompanyMainInfo;
import com.hteis.webtest.selenium.*;

public class CompanyMainInfoPage extends PageBase {
	
	/***企业名称*/
	public HtmlInput getCompanyNameInput(){
		return this.findElementById("custName").toHtmlInput();
	}
	
	/***用电编号*/
	public HtmlInput getElectronNoInput(){
		return this.findElementById("eleCode").toHtmlInput();
	}
	
	/***目录电价*/
	public HtmlInput getCatalogPriceInput(){
		return this.findElementById("catalogPrice").toHtmlInput();
	}
	
	/***销售电价*/
	public HtmlInput getSalePriceInput(){
		return this.findElementById("salePrice").toHtmlInput();
	}
	
	/***所属行业*/
	public HtmlSelect getIndustrySelect(){
		return this.findElementByName("enterTrad").toHtmlSelect();
	}
	
	/***用户类型*/
	public HtmlSelect getUserTypeSelect(){
		return this.findElementByCss("[ng-model='cust.custType']").toHtmlSelect();
	}
	
	/***企业性质*/
	public HtmlSelect getCompanyTypeSelect(){
		return this.findElementByName("enterCharacter").toHtmlSelect();
	}
	
	/***企业规模*/
	public HtmlSelect getCompanyScaleSelect(){
		return this.findElementByCss("select[ng-model='cust.enterScale '").toHtmlSelect();
	}
	
	/***用电性质*/
	public HtmlSelect getEleUsageTypeSelect(){
		return this.findElementByName("eleCharacter").toHtmlSelect();
	}
	
	/***高能耗行业 - 是*/
	public HtmlRadioBox getHighEnergyYesRb(){
		return this.findElementsByCss("input[name='highEnergy']").get(0).toHtmlRadioBox();
	}
	
	/***高能耗行业 - 否*/
	public HtmlRadioBox getHighEnergyNoRb(){
		return this.findElementsByCss("input[name='highEnergy']").get(1).toHtmlRadioBox();
	}
	
	/***工作班制*/
	public HtmlSelect getShiftTypeSelect(){
		return this.findElementByName("workShift").toHtmlSelect();
	}
	
	/***公休情况*/
	public HtmlSelect getVacationType(){
		return this.findElementByName("publicHoliday").toHtmlSelect();
	}
	
	/***电压等级*/
	public HtmlSelect getVoltageLevel(){
		return this.findElementByName("volt").toHtmlSelect();
	}
	
	/***主营业务/产品*/
	public HtmlInput getBusinessScope(){
		return this.findElementById("mainBusiness").toHtmlInput();
	}
	
	public void setFieldValues(CompanyMainInfo mainInfo){
		this.getCompanyNameInput().setText(mainInfo.CompanyName);
		this.getElectronNoInput().setText(mainInfo.ElectronNo);
		this.getCatalogPriceInput().setText(Float.toString(mainInfo.CatalogPrice));
		this.getSalePriceInput().setText(Float.toString(mainInfo.SalePrice));
		this.getIndustrySelect().selectByText(mainInfo.Industry);
		this.getUserTypeSelect().selectByText(mainInfo.UserType);
		this.getCompanyTypeSelect().selectByText(mainInfo.CompanyType);
		this.getCompanyScaleSelect().selectByText(mainInfo.CompanyScale);
		this.getEleUsageTypeSelect().selectByText(mainInfo.ElectronUsageType);
		this.getHighEnergyYesRb().check();
		this.getShiftTypeSelect().selectByText(mainInfo.ShiftType);
		this.getVacationType().selectByText(mainInfo.VacationType);
		this.getVoltageLevel().selectByText(mainInfo.VoltageLevel);
		this.getBusinessScope().setText(mainInfo.BusinessScope);		
	}
	
}
