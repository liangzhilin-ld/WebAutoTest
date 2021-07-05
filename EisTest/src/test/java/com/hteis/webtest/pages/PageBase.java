package com.hteis.webtest.pages;

import java.util.ArrayList;

import org.testng.Assert;

import com.hteis.webtest.pages.cust.*;
import com.hteis.webtest.pages.mgc.*;
import com.hteis.webtest.pages.shop.*;
import com.hteis.webtest.pages.trade.*;
import com.hteis.webtest.selenium.*;

public class PageBase extends HtmlPage {
	
	public HtmlElement getLoginNameSpan(){
		return this.findElementByCss("img[alt='个人中心'] + span");
	}
	
	public HtmlLink getQuitLink(){
		return this.findElementByLinkText("退出").toHtmlLink();
	}
	
	public HtmlLink getChangePwdLink(){
		return this.findElementByLinkText("修改密码").toHtmlLink();
	}
	
	public HtmlLink getPersonalInfoLink(){
		return this.findElementByLinkText("个人信息").toHtmlLink();
	}
	
	public void logOut(){
		this.getLoginNameSpan().click();
		this.getLoginNameSpan().click();		
		this.getQuitLink().click();
	}
	
	public String getLoginName(){
		return this.getLoginNameSpan().getText();
	}
	
	
	public void changePassword(String oldPwd, String newPwd){
		this.getLoginNameSpan().click();
		this.getLoginNameSpan().click();	
		this.getChangePwdLink().click();
		
		ChangePwdPage changePwdPage = new ChangePwdPage();
		changePwdPage.getOldPwdInput().setText(oldPwd);
		changePwdPage.getNewPwdInput().setText(newPwd);
		changePwdPage.getConfirmNewPwdInput().setText(newPwd);
		changePwdPage.getConfirmButton().click();
	}
	
	public PersonalInfoPage openPersonalInfo(){
		this.getLoginNameSpan().click();
		this.getLoginNameSpan().click();
		this.getPersonalInfoLink().click();
		
		PersonalInfoPage personalInfoPage = new PersonalInfoPage();
		return personalInfoPage;
	}
	
	public HtmlElement getNav(){
		return this.findElementByClass("accordion"); 
	}
	
	public void openNavMenu(String menu, String subMenu){
		HtmlElement nav = this.getNav();
		
		if(!nav.existByLinkText(subMenu)){
			nav.findElementByLinkText(menu).click();
			Driver.waitPageLoad();
			nav = this.getNav();
		}
		
		nav.findElementByLinkText(subMenu).click();
		Driver.waitPageLoad();
		Driver.wait(1000);
	}
	
	/***打开未处理任务页面*/
	public TaskActivePage openTaskPage(){
		this.openNavMenu("待处理工作", "未处理任务");
		return new TaskActivePage();
	}
	
	/***打开已处理任务页面*/
	public TaskCompletedPage openCompletedTaskPage(){
		this.openNavMenu("待处理工作", "已处理任务");
		return new TaskCompletedPage();
	}
	
	/***打开合同列表页面*/
	public ContractListPage openContractListPage(){
		this.openNavMenu("合同管理", "合同列表");
		return new ContractListPage();
	}
	
	/***打开合同查询页面*/
	public CustContractPage openCustContractPage(){
		this.openNavMenu("购电业务", "合同查询");
		return new CustContractPage();
	}
	
	/***打开监控模型管理页面*/
	public MgModelPage openMgModelPage(){
		this.openNavMenu("综合监控系统", "监控模型管理");
		return new MgModelPage();
	}
	
	/***打开监控系统管理页面*/
	public MgSysMgmtPage openSysMgmtPage(){
		this.openNavMenu("综合监控系统", "监控系统管理");
		return new MgSysMgmtPage();
	}
	
	/***打开监控设备管理页面*/
	public MgDevicePage openMgDevicePage(){
		this.openNavMenu("综合监控系统", "监控设备管理");
		return new MgDevicePage();
	}	
	
	/***打开交易场次管理页面*/
	public TradeFairPage openTradeFairPage(){
		this.openNavMenu("交易管理", "交易场次管理");
		return new TradeFairPage();
	}
	
	/***打开交易模拟测算页面*/
	public TradeSimulatePage openTradeSimulatePage(){
		this.openNavMenu("交易管理", "交易模拟测算");
		return new TradeSimulatePage();
	}
	
	/***打开交易预案管理页面*/
	public TradePlanPage openTradePlanPage(){
		this.openNavMenu("交易管理", "交易预案管理");
		return new TradePlanPage();
	}
	
	/***打开客户服务管理页面*/
	public CustServicePage openCustServicePage(){
		this.openNavMenu("客户管理", "客户服务管理");
		return new CustServicePage();
	}
	
	/***打开客户投诉录入页面*/
	public CustComplaintPage openCustComplaintPage(){
		this.openNavMenu("客户管理", "客户投诉录入");
		return new CustComplaintPage();
	}
	
	/***打开客户投诉处理页面*/
	public CustComplaintPage openCustComplaintHandlePage(){
		this.openNavMenu("客户管理", "客户投诉处理");
		return new CustComplaintPage();
	}
	
	/***打开发电企业管理页面*/
	public PowerCompanyPage openPowerCompanyPage(){
		this.openNavMenu("客户管理", "发电企业管理");
		return new PowerCompanyPage();
	}
	
	/***打开月度购电申报页面*/
	public CustDeclarePage openCustDeclarePage(){
		this.openNavMenu("客户管理", "月度购电申报");
		return new CustDeclarePage();
	}	
	
	public boolean isNavMenuExist(String menu, String subMenu){
		HtmlElement nav = this.getNav();
		
		if(!nav.existByLinkText(menu)){
			return false;
		}
		else			
		{
			if(!nav.existByLinkText(subMenu)){
				nav.findElementByLinkText(menu).click();
				Driver.waitPageLoad();
				nav = this.getNav();
			}
			
			return nav.existByLinkText(subMenu);			
		}
	}
	
	public ArrayList<String> getNavMenus(){
		ArrayList<String> menus = new ArrayList<String>();
		HtmlElement nav = this.getNav();
		
		for(HtmlElement menu : nav.findElementsByCss("div.main-item")){
			menus.add(menu.findElementByCss("a:last-child").getText());
		}
		
		return menus;
	}
	
	public ArrayList<String> getSubMenus(String menu){
		ArrayList<String>  subMenus = new ArrayList<String>();
		HtmlElement nav = this.getNav();
		
		int index = this.getNavMenus().indexOf(menu);
		
		HtmlElement subMenuDiv = nav.findElementByCss(String.format("div.mine-menu:nth-child(%d) > div:nth-child(2)", index + 2));
		
		for(HtmlElement subMenu : subMenuDiv.findElementsByTag("a")){
			subMenus.add(subMenu.getText());
		}
		
		return subMenus;
	}
	
	/***点击提示信息里的是按钮*/
	public void ClickConfirm(){
		this.findElementByCss(".ez-confirm .confirm-btn").toHtmlButton().click();
	}
	
	/***验证提示消息*/
	public void verifyAlert(String message){
    	String actualMsg = this.findElementByCss("span[ng-bind='alert.msg']").getText();
    	Assert.assertTrue(actualMsg.contains(message), "未发现提示消息：" + message + "；当前提示消息为：" + actualMsg);
    }
	
	/***关闭提示消息*/
	public void closeAlert(){
		this.findElementByCss("div.remind-message button.close").toHtmlButton().click();
	}
}
