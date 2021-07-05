package com.hteis.webtest.pages;

import com.hteis.webtest.entities.CompanyBankAccInfo;
import com.hteis.webtest.selenium.*;

public class CompanyAccInfoPage extends PageBase {

	/***银行开户名*/
	public HtmlInput getCustNameInput(){
		return this.findElementById("accountName").toHtmlInput();
	}
	/***开户银行*/
	public HtmlInput getBankNameInput(){
		return this.findElementById("bankName").toHtmlInput();
	}
	
	/***开户银行所在城市*/
	public HtmlSelect getCitySelect(){
		return this.findElementByName("bankAddr").toHtmlSelect();
	}
	
	/***开户银行支行名称*/
	public HtmlInput getBankBranchInput(){
		return this.findElementById("bankBranch").toHtmlInput();
	}
	
	/***公司对公账户*/
	public HtmlInput getBankAccNoInput(){
		return this.findElementById("accountNo").toHtmlInput();
	}
	
	public void setFieldValues(CompanyBankAccInfo accInfo){
		this.getBankNameInput().setText(accInfo.BankName);
		this.getCitySelect().selectByText(accInfo.City);
		this.getBankBranchInput().setText(accInfo.BankBranchName);
		this.getBankAccNoInput().setText(accInfo.BankAccNo);
	}
}
