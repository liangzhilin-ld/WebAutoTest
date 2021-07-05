package com.hteis.webtest.testcases;

import java.io.File;
import java.util.ArrayList;

import org.testng.*;
import org.testng.annotations.Test;

import com.hteis.webtest.business.User;
import com.hteis.webtest.entities.*;
import com.hteis.webtest.selenium.*;
import com.hteis.webtest.pages.*;


public class CompanyInfoTest extends TCBase {
	

	@Test(suiteName="Sep", groups="公司资料管理", priority=1, testName="企业信息完善-基础信息",
			description="验证未完善信息时企业信息页面流程状态正确\r\n"
					+ "企业基础信息填写符合要求时可保存\r\n"
					+ "查看详细信息页面可查看保存的企业基本信息\r\n"					
					+ "填写完基础信息后流程状态变为信息完善中")
	public void Sep_CompanyInfo011() throws Exception{
		//1. 注册一个新用户
		UserInfo buyer = User.registerNewBuyer();
		
		//2. 使用新用户登录到https://tcrm.hteis.cn/
		this.loginCust(buyer.loginName, buyer.password);
		PageBase homePage = new PageBase();
		homePage.openNavMenu("公司资料管理", "企业信息管理");
		CompanyInfoMgmtPage cmpyPage = new CompanyInfoMgmtPage();
		
		//3. 验证：页面中流程图中只有第一个按钮为激活状态，其他按钮为不可用状态
		Boolean[] expectedStatus = {true, true, false, false, false, false, false, false};
		Assert.assertEquals(cmpyPage.getBtnStatus(), expectedStatus, "状态图中的按钮状态不正确");
		
		//4. 点击信息未完善图标，验证系统跳转到企业信息填写界面		
		cmpyPage.getIncompleteBtn().click();
		Assert.assertEquals(Driver.getCurrentUrl(), Constants.urlTcrm + "views/home.html#/salecustmanage/maininfo");
		
		//5. 退回上一页面，点击信息未完善文字，验证系统跳转到企业信息填写界面
		Driver.back();
		cmpyPage.getIncompleteBtn2().click();
		Assert.assertEquals(Driver.getCurrentUrl(), Constants.urlTcrm + "views/home.html#/salecustmanage/maininfo");
		
		//6. 点击基础信息标签
		cmpyPage.getMainInfoTab().click();
		
		//7. 输入基础信息后点击保存，验证提示信息：保存成功
		CompanyMainInfo mainInfo = CompanyMainInfo.createLargeCmpyInfo();
		CompanyMainInfoPage mainInfoPage = new CompanyMainInfoPage();
		mainInfoPage.setFieldValues(mainInfo);
		cmpyPage.getSaveBtn().click();
		Driver.wait(2000);		
		this.verifyAlert(cmpyPage, "保存成功");
		
		//8. 点击查看详细信息按钮
	    cmpyPage.getViewDetailBtn().click();
	    Driver.wait(2000);
	    
	    //9. 验证页面内基础信息数据正确
	    CompanyDetailsPage detailPage = new CompanyDetailsPage();
	    detailPage.verifyMainInfo(mainInfo);
	    
	    //10.点击返回按钮，验证页面返回流程图界面
	    Driver.scrollToTop();
	    detailPage.getBackBtn().click();
	    Assert.assertEquals(Driver.getCurrentUrl(), Constants.urlTcrm + "views/home.html#/salecustindex");
	    
	    //11.点击流程图，选择企业认证信息标签，点击关闭按钮，验证页面返回流程图界面
	    cmpyPage.getCompletingBtn().click();
	    cmpyPage.getMainInfoTab().click();
	    cmpyPage.getCloseBtn().click();
	    Assert.assertEquals(Driver.getCurrentUrl(), Constants.urlTcrm + "views/home.html#/salecustindex");
	    
        //12. 验证当前流程状态为“信息完善中”，流程图中只有信息完善中按钮可用，其他按钮不可用
	    boolean[] expectedStatus2 = {false, false, true, true, false, false, false, false};
		Assert.assertEquals(cmpyPage.getBtnStatus(), expectedStatus2, "填写企业基本信息后状态图中的按钮状态不正确");
	}
	
	@Test(suiteName="Sep", groups="公司资料管理", priority=1, testName="企业信息完善-企业认证信息",
			description= "验证企业认证信息填写符合要求时可保存\r\n"
					+ "查看详细信息页面可查看保存的企业认证信息\r\n"
					+ "验证上传的附件可在详细信息页面下载")
	public void Sep_CompanyInfo012() throws Exception{

		//1. 注册一个新用户
		UserInfo buyer = User.registerNewBuyer();
		
		//2. 使用新用户登录到https://tcrm.hteis.cn/
		this.loginCust(buyer.loginName, buyer.password);
		//this.loginCust("u20170222100333", "123456");
		
		PageBase homePage = new PageBase();
		homePage.openNavMenu("公司资料管理", "企业信息管理");
		CompanyInfoMgmtPage cmpyPage = new CompanyInfoMgmtPage();
		cmpyPage.getIncompleteBtn().click();
		Driver.wait(1000);

		//3. 点击企业认证信息标签
		cmpyPage.getEntInfoTab().click();
		
		//4. 填写合格数据后点击保存，验证提示信息：保存成功
		CompanyRegInfo regInfo = CompanyRegInfo.createCompanyRegInfo();
		CompanyRegInfoPage regInfoPage = new CompanyRegInfoPage();
		regInfoPage.setFieldValues(regInfo);
		cmpyPage.getSaveBtn().click();
		Driver.wait(2000);		
		this.verifyAlert(cmpyPage, "保存成功");
		
		//5. 点击查看详细信息按钮
	    cmpyPage.getViewDetailBtn().click();
	    Driver.wait(2000);
	    
	    //6. 验证页面内认证信息数据正确
	    CompanyDetailsPage detailPage = new CompanyDetailsPage();
	    detailPage.verifyRegInfo(regInfo);
	    
	    //7. 验证附件清单中可查看附件
	    HtmlTable attachTable = detailPage.getAttachmentTable();
	    for(int i = 1; i < 4; i++){
			attachTable.getCell(i, 2).findElementByTag("a").click();
			Driver.wait(2000);
			String fileName = detailPage.findElementByCss("body > a:last-child").getAttribute("download");
			File file = new File(Config.defaultDownloadFolder + "\\" + fileName);
			Assert.assertTrue(file.exists(), attachTable.getCell(i, 1).getText() + "下载失败");
			file.delete();
			Driver.wait(1000);
	    }
	    
	    //8.点击返回按钮，验证页面返回流程图界面
	    Driver.scrollToTop();
	    detailPage.getBackBtn().click();
	    Assert.assertEquals(Driver.getCurrentUrl(), Constants.urlTcrm + "views/home.html#/salecustindex");
	    
	    //9.点击流程图，选择企业认证信息标签，点击关闭按钮，验证页面返回流程图界面
	    cmpyPage.getIncompleteBtn().click();
	    cmpyPage.getEntInfoTab().click();
	    cmpyPage.getCloseBtn().click();
	    Assert.assertEquals(Driver.getCurrentUrl(), Constants.urlTcrm + "views/home.html#/salecustindex");
	    
        //10. 验证当前流程状态为“信息未完善”，流程图中只有信息未完善按钮可用，其他按钮不可用
	    boolean[] expectedStatus = {true, true, false, false, false, false, false, false};
		Assert.assertEquals(cmpyPage.getBtnStatus(), expectedStatus, "填写企业认证信息后状态图中的按钮状态不正确");
	}
	
	@Test(suiteName="Sep", groups="公司资料管理", priority=1, testName="企业信息完善-账号信息",
			description= "验证账号信息填写符合要求时可保存\r\n"
					+ "查看详细信息页面可查看保存的账号信息")
	public void Sep_CompanyInfo013() throws Exception{

		//1. 注册一个新用户
		UserInfo buyer = User.registerNewBuyer();
		
		//2. 使用新用户登录到https://tcrm.hteis.cn/
		this.loginCust(buyer.loginName, buyer.password);
		
		PageBase homePage = new PageBase();
		homePage.openNavMenu("公司资料管理", "企业信息管理");
		CompanyInfoMgmtPage cmpyPage = new CompanyInfoMgmtPage();
		cmpyPage.getIncompleteBtn().click();
		Driver.wait(1000);

		//3. 在基础信息中输入企业名称	
		CompanyMainInfo mainInfo = CompanyMainInfo.createLargeCmpyInfo();		
		CompanyMainInfoPage mainInfoPage = new CompanyMainInfoPage();
		mainInfoPage.setFieldValues(mainInfo);
		cmpyPage.getSaveBtn().click();
		this.verifyAlert(cmpyPage, "保存成功");
		//mainInfoPage.getCompanyNameInput().setText(mainInfo.CompanyName);
		
		//4. 点击账号信息标签
		cmpyPage.getAccInfoTab().click();
		
		//5. 验证银行开户名已设置为企业名称且不可更改
		CompanyAccInfoPage accPage = new CompanyAccInfoPage();
		Assert.assertEquals(accPage.getCustNameInput().getText(), mainInfo.CompanyName, "银行开户名未自动填写");
		Assert.assertTrue(accPage.getCustNameInput().isReadOnly(), "银行开户名不是只读");
		
		//6. 填写其他字段数据
		CompanyBankAccInfo accInfo = CompanyBankAccInfo.createBankData();
		accInfo.CustName = mainInfo.CompanyName;
		accPage.setFieldValues(accInfo);
		
		//7. 点击保存按钮
		cmpyPage.getSaveBtn().click();
		
		//8. 验证提示消息：保存成功
		this.verifyAlert(cmpyPage, "保存成功");
		
		//9. 点击查看详细信息
		cmpyPage.getViewDetailBtn().click();
		Driver.wait(2000);
		
		//10.验证页面内账户信息正确
		CompanyDetailsPage detailPage = new CompanyDetailsPage();
		detailPage.verifyAccInfo(accInfo);
		
		//8.点击返回按钮，验证页面返回流程图界面
	    Driver.scrollToTop();
	    detailPage.getBackBtn().click();
	    Assert.assertEquals(Driver.getCurrentUrl(), Constants.urlTcrm + "views/home.html#/salecustindex");
	    
	    //9.点击流程图，选择账户信息标签，点击关闭按钮，验证页面返回流程图界面
	    cmpyPage.getCompletingBtn().click();
	    cmpyPage.getAccInfoTab().click();
	    cmpyPage.getCloseBtn().click();
	    Assert.assertEquals(Driver.getCurrentUrl(), Constants.urlTcrm + "views/home.html#/salecustindex");
	    
        //13. 验证当前流程状态为“信息未完善”，流程图中只有信息未完善按钮可用，其他按钮不可用
	    boolean[] expectedStatus = {false, false, true, true, false, false, false, false};
		Assert.assertEquals(cmpyPage.getBtnStatus(), expectedStatus, "填写企业账户信息后状态图中的按钮状态不正确");
	}
	
	@Test(suiteName="Sep", groups="公司资料管理", priority=1, testName="企业信息完善-主要联系人信息",
			description= "验证主要联系人填写符合要求时可保存\r\n"
					+ "验证详细信息页面可查看保存的主要联系人信息\r\n"
					+ "验证上传的附件可在详细信息页面下载")
	public void Sep_CompanyInfo014() throws Exception{

		//1. 注册一个新用户
		UserInfo buyer = User.registerNewBuyer();
		
		//2. 使用新用户登录到https://tcrm.hteis.cn/
		this.loginCust(buyer.loginName, buyer.password);
		
		PageBase homePage = new PageBase();
		homePage.openNavMenu("公司资料管理", "企业信息管理");
		CompanyInfoMgmtPage cmpyPage = new CompanyInfoMgmtPage();
		cmpyPage.getIncompleteBtn().click();
		Driver.wait(1000);

		//3. 点击主要联系人信息标签
		cmpyPage.getContactInfoTab().click();
		
		//4. 填写合格数据后点击保存，验证提示信息：保存成功
		CompanyContactInfo contactInfo = CompanyContactInfo.create(); 
		CompanyContactPage contactPage = new CompanyContactPage();
		contactPage.setFieldValues(contactInfo);
		cmpyPage.getSaveBtn().click();
		Driver.wait(2000);
		this.verifyAlert(cmpyPage, "保存成功");
		
		//5. 点击查看详细信息按钮
	    cmpyPage.getViewDetailBtn().click();
	    Driver.wait(2000);
	    
	    //6. 验证页面内认证信息数据正确
	    CompanyDetailsPage detailPage = new CompanyDetailsPage();
	    detailPage.verifyContactInfo(contactInfo);
	    
	    //7. 验证附件清单中可查看附件
	    HtmlTable attachTable = detailPage.getAttachmentTable();
	    for(int i = 4; i < 7; i++){
			attachTable.getCell(i, 2).findElementByTag("a").click();
			Driver.wait(2000);
			String fileName = detailPage.findElementByCss("body > a:last-child").getAttribute("download");
			File file = new File(Config.defaultDownloadFolder + "\\" + fileName);
			Assert.assertTrue(file.exists(), attachTable.getCell(i, 1).getText() + "下载失败");
			file.delete();
			Driver.wait(1000);
	    }
	    
	    //8.点击返回按钮，验证页面返回流程图界面
	    Driver.scrollToTop();
	    detailPage.getBackBtn().click();
	    Assert.assertEquals(Driver.getCurrentUrl(), Constants.urlTcrm + "views/home.html#/salecustindex");
	    
	    //9.点击流程图，选择联系人标签，点击关闭按钮，验证页面返回流程图界面
	    cmpyPage.getIncompleteBtn().click();
	    cmpyPage.getContactInfoTab().click();
	    cmpyPage.getCloseBtn().click();
	    Assert.assertEquals(Driver.getCurrentUrl(), Constants.urlTcrm + "views/home.html#/salecustindex");
	    
        //10. 验证当前流程状态为“信息未完善”，流程图中只有信息未完善按钮可用，其他按钮不可用
	    boolean[] expectedStatus = {true, true, false, false, false, false, false, false};
		Assert.assertEquals(cmpyPage.getBtnStatus(), expectedStatus, "填写企业认证信息后状态图中的按钮状态不正确");
	}
	
	@Test(suiteName="Sep", groups="公司资料管理", priority=1, testName="企业信息完善-用电信息",
			description= "验证用电信息填写符合要求时可保存\r\n"
					+ "验证用电信息可填写多个\r\n"
					+ "验证详细信息页面可查看用电信息\r\n"
					+ "验证用电信息可删除")
	public void Sep_CompanyInfo015() throws Exception{
		//1. 注册一个新用户
		UserInfo buyer = User.registerNewBuyer();
		
		//2. 使用新用户登录到https://tcrm.hteis.cn/
		this.loginCust(buyer.loginName, buyer.password);
		
		PageBase homePage = new PageBase();
		homePage.openNavMenu("公司资料管理", "企业信息管理");
		CompanyInfoMgmtPage cmpyPage = new CompanyInfoMgmtPage();
		cmpyPage.getIncompleteBtn().click();
		Driver.wait(1000);

		//3. 点击用电信息标签
		cmpyPage.getEleInfoTab().click();
		
		//4. 点击添加用电信息，验证页面上出现一个用电信息填写表单
		cmpyPage.getAddEleInfoLink().click();
		Driver.wait(1000);
		ArrayList<HtmlElement> forms = cmpyPage.getEleInfoForms();
		Assert.assertEquals(forms.size(), 1);
		
		//5. 输入正确的数据后点击保存
		CompanyEleInfo eleInfo1 = CompanyEleInfo.createLargeElectroData();
		CompanyEleInfoForm form1 = new CompanyEleInfoForm(forms.get(0));
		form1.setFieldValues(eleInfo1);
		form1.getSaveBtn().click();
		Driver.wait(1000);
		this.verifyAlert(cmpyPage, "保存成功");
		
		//6. 点击添加用电信息，验证页面上又出现一个新的用电信息填写表单，处于原表单上面
		cmpyPage.getAddEleInfoLink().click();
		Driver.wait(1000);
		forms = cmpyPage.getEleInfoForms();
		Assert.assertEquals(forms.size(), 2);
		
		//7. 点击新表单的取消按钮，验证表单被移除
		CompanyEleInfoForm form2 = new CompanyEleInfoForm(forms.get(0));
		form2.getCancelBtn().click();
		Driver.wait(1000);
		forms = cmpyPage.getEleInfoForms();
		Assert.assertEquals(forms.size(), 1);
		
		//8. 再次点击添加用电信息，填写第二个表单
		cmpyPage.getAddEleInfoLink().click();
		Driver.wait(1000);
		forms = cmpyPage.getEleInfoForms();
		form2 = new CompanyEleInfoForm(forms.get(0));
		CompanyEleInfo eleInfo2 = CompanyEleInfo.createSmallElectroData();
		form2.setFieldValues(eleInfo2);
		form2.getSaveBtn().click();
		Driver.wait(1000);
		this.verifyAlert(cmpyPage, "保存成功");
		
		//9. 点击查看详细信息按钮
		cmpyPage.getViewDetailBtn().click();
	    Driver.wait(2000);
	    
	    //10.验证页面内有两条用电信息，验证数据正确
	    CompanyDetailsPage detailPage = new CompanyDetailsPage();
	    detailPage.verifyEleInfo(1, eleInfo1);
	    detailPage.verifyEleInfo(2, eleInfo2);
		
		//11.点击返回按钮
	    Driver.scrollToTop();
	    detailPage.getBackBtn().click();
	    
	    //12.点击用电信息标签, 点击关闭按钮，验证页面返回流程图界面	  
	    cmpyPage.getIncompleteBtn().click();
	    cmpyPage.getEleInfoTab().click();
		form2 = new CompanyEleInfoForm(cmpyPage.getEleInfoForms().get(1));
		form2.getCloseBtn().click();
	    Assert.assertEquals(Driver.getCurrentUrl(), Constants.urlTcrm + "views/home.html#/salecustindex");
	    
	    //13. 验证当前流程状态为“信息未完善”，流程图中只有信息未完善按钮可用，其他按钮不可用
	    boolean[] expectedStatus = {true, true, false, false, false, false, false, false};
		Assert.assertEquals(cmpyPage.getBtnStatus(), expectedStatus, "填写企业认证信息后状态图中的按钮状态不正确");
	    
	    //14.再次进入用电信息编辑页面，删除第二条用电信息
	    cmpyPage.getIncompleteBtn().click();
	    cmpyPage.getEleInfoTab().click();
		form2 = new CompanyEleInfoForm(cmpyPage.getEleInfoForms().get(1));
		form2.getDeleteBtn().click();
		cmpyPage.getDelConfirmBtn().click();
		Driver.wait(1000);
		Assert.assertEquals(cmpyPage.getEleInfoForms().size(), 1, "用电信息未删除");
		
		//15.点击查看详细信息
		cmpyPage.getViewDetailBtn().click();
		
		//16.验证用电信息只有一条
		detailPage.verifyEleInfo(2, eleInfo2);		
	}

}
