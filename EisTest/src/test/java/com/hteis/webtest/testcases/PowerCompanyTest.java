package com.hteis.webtest.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.hteis.webtest.business.PowerCompany;
import com.hteis.webtest.entities.Constants;
import com.hteis.webtest.entities.PowerCompanyData;
import com.hteis.webtest.pages.cust.*;
import com.hteis.webtest.selenium.*;

public class PowerCompanyTest extends TCBase{

	@Test(suiteName="Sep", groups="客户管理", priority=1, testName="创建发电企业-成功",
			description="验证输入数据正确时企业信息可保存\r\n"
					+ "验证发电企业创建后状态为待审核\r\n"
					+ "验证创建后表中显示数据正确\r\n"
					+ "验证详情对话框中数据正确")
	public void Sep_PowerCompanyNew01() throws Exception{
		
		//使用客户主管账号登录
		CustHomePage homePage = this.loginCust(Constants.custTLUsr, Constants.custTLPwd);		
		
		//打开发电企业管理页面
		PowerCompanyPage companyPage = homePage.openPowerCompanyPage();
	
		//点击创建发电企业
		companyPage.getNewBtn().click();
		
		//输入基础信息数据
		PowerCompanyData data = PowerCompanyData.create();
		PowerCompanyNewDialog dialog = new PowerCompanyNewDialog();
		dialog.setMainInfo(data.MainInfo);
		
		//点击保存按钮，验证提示信息：保存成功
		dialog.getMainInfoSaveBtn().click();
		companyPage.verifyAlert("保存成功");
		//companyPage.closeAlert();
		
		//点击联系人信息标签页
		dialog.getContactTab().click();
		
		//输入联系人信息
		dialog.setContactInfo(data.ContactInfo);
		
		//点击保存按钮，验证提示信息：保存成功
		dialog.getContactSaveBtn().click();
		companyPage.verifyAlert("保存成功");
		//companyPage.closeAlert();
		
		//点击发电信息标签页
		dialog.getEleInfoTab().click();
		
		//输入发电信息
		dialog.setEleInfo(data.EleInfo);
		
		//点击保存按钮，验证提示信息：保存成功
		dialog.getEleSaveBtn().click();
		companyPage.verifyAlert("保存成功");
		//companyPage.closeAlert();
		
		//点击关闭按钮
		dialog.getEleCloseBtn().click();
		Driver.wait(1000);
		
		//验证新建的发电企业出现在表中，状态为“待审核”，并且数据正确
		companyPage.verifyData(data);
		
		//点击详情，在详情对话框中验证数据正确
		PowerCompanyDetailsDialog detailsDialog = companyPage.openDetailsDialog(data.MainInfo.Name);
		detailsDialog.verifyData(data);
	}
	
	@Test(suiteName="Sep", groups="客户管理", priority=1, testName="创建发电企业-失败",
			description="字段验证和错误提示信息测试")
	public void Sep_PowerCompanyNew02() throws Exception{
		
	}
	
	@Test(suiteName="Sep", groups="客户管理", priority=1, testName="编辑发电企业信息",
			description="验证发电企业编辑功能")
	public void Sep_PowerCompanyEdit() throws Exception{
		//使用客户主管账号登录
		this.loginCust(Constants.custTLUsr, Constants.custTLPwd);
		
		//创建一个发电企业
		PowerCompanyData data = PowerCompanyData.create();
		PowerCompany.create(data);
		
		//点击编辑打开编辑对话框
		PowerCompanyPage companyPage = new PowerCompanyPage();
		PowerCompanyNewDialog newDialog = companyPage.openEditDialog(data.MainInfo.Name);
		
		//验证三个标签页中的所有数据正确正确
		newDialog.verifyData(data);
		
		//点击基础信息标签页，修改所有字段后点击保存，验证提示信息：保存成功
		data.update();
		newDialog.getMainInfoTab().click();
		newDialog.setMainInfo(data.MainInfo);
		newDialog.getMainInfoSaveBtn().click();
		companyPage.verifyAlert("保存成功");
		
		//点击联系人信息标签页，修改所有字段后点击保存，验证提示信息：保存成功
		newDialog.getContactTab().click();
		newDialog.setContactInfo(data.ContactInfo);
		newDialog.getContactSaveBtn().click();
		companyPage.verifyAlert("保存成功");
		
		//点击发电信息标签页，修改所有字段后点击保存，验证提示信息：保存成功
		newDialog.getEleInfoTab().click();
		newDialog.setEleInfo(data.EleInfo);
		newDialog.getEleSaveBtn().click();
		companyPage.verifyAlert("保存成功");
		
		//点击基础信息标签页，点击关闭
		newDialog.getMainInfoTab().click();
		newDialog.getMainInfoCloseBtn().click();
		Driver.wait(1000);
		
		//验证表中的数据已更新
		companyPage.verifyData(data);
		
		//点击详情，在详情对话框中验证数据正确
		PowerCompanyDetailsDialog detailsDialog = companyPage.openDetailsDialog(data.MainInfo.Name);
		detailsDialog.verifyData(data);
	}
	
	@Test(suiteName = "Sep", groups = "客户管理", priority = 2, testName = "查询发电企业", 
			description = "验证客户服务记录可按客户名称/发电类型/企业性质/企业规模查询\r\n"
					+ "验证重置功能可清除所有查询条件并显示所有数据\r\n" 
					+ "验证没有设置任何搜索条件时查询将显示所有数据")
	public void PowerCompanySearch() throws Exception {
		
		int loadTime = 2000;
		
		//使用客户主管账号登录
		CustHomePage homePage = this.loginCust(Constants.custTLUsr, Constants.custTLPwd);
		
		//打开发电企业管理页面
		PowerCompanyPage companyPage = homePage.openPowerCompanyPage();
		Driver.wait(loadTime);
		int count = companyPage.getPager().getTotalCount();
		
		//按搜索条件搜索:客户名称包含 企业,验证搜索结果正确
		final String name = "企业";
		companyPage.getNameInput().setText(name);
		companyPage.getQueryBtn().click();
		Driver.wait(loadTime);
		HtmlRow result = companyPage.getPowerCompanyTable().VerifyAllRows(companyPage.getPager(), new ICheckRow() {
			public boolean CheckRow(HtmlRow row) throws Exception {
				return row.getCellValue(PowerCompanyPage.NameColNo).contains(name);
			}
		});
		Assert.assertNull(result, "按企业名称搜索结果不对，不符合条件的行：" + companyPage.printData(result));
		companyPage.getNameInput().clear();
		
		//按搜索条件搜索:发电类型=光伏,验证搜索结果正确
		final String powerType = "光伏";
		companyPage.getPowerTypeSelect().selectByText(powerType);
		companyPage.getQueryBtn().click();
		Driver.wait(loadTime);
		result = companyPage.getPowerCompanyTable().VerifyAllRows(companyPage.getPager(), new ICheckRow() {
			public boolean CheckRow(HtmlRow row) throws Exception {
				return row.getCellValue(PowerCompanyPage.PowerTypeColNo).equals(powerType);
			}
		});
		Assert.assertNull(result, "按企业名称搜索结果不对，不符合条件的行：" + companyPage.printData(result));
		companyPage.getPowerTypeSelect().selectByIndex(0);
		
		//按搜索条件搜索:企业性质=国企,验证搜索结果正确
		final String companyType = "国企";
		companyPage.getCompanyTypeSelect().selectByText(companyType);
		companyPage.getQueryBtn().click();
		Driver.wait(loadTime);
		result = companyPage.getPowerCompanyTable().VerifyAllRows(companyPage.getPager(), new ICheckRow() {
			public boolean CheckRow(HtmlRow row) throws Exception {
				return row.getCellValue(PowerCompanyPage.CompanyTypeColNo).equals(powerType);
			}
		});
		Assert.assertNull(result, "按企业性质搜索结果不对，不符合条件的行：" + companyPage.printData(result));
		companyPage.getCompanyTypeSelect().selectByIndex(0);
		
		//按搜索条件搜索:企业规模=500-999人,验证搜索结果正确
		final String scale = "500-999人";
		companyPage.getCompanyScaleSelect().selectByText(scale);
		companyPage.getQueryBtn().click();
		Driver.wait(loadTime);
		HtmlRow row = companyPage.getPowerCompanyTable().getFirstRow();
		row.getCell(PowerCompanyPage.OpsColNo).findElementByLinkText("编辑").click();
		PowerCompanyNewDialog dialog = new PowerCompanyNewDialog();
		String actualScale = dialog.getScaleSelect().getSelectedText();
		dialog.getMainInfoCloseBtn().click();
		Assert.assertEquals(actualScale, scale, "按企业规模搜索结果不对，不符合条件的行：" + companyPage.printData(row));
		
		companyPage.getPager().goLastPage();
		Driver.wait(loadTime);
		row = companyPage.getPowerCompanyTable().getLastRow();
		row.getCell(PowerCompanyPage.OpsColNo).findElementByLinkText("编辑").click();
		actualScale = dialog.getScaleSelect().getSelectedText();
		dialog.getMainInfoCloseBtn().click();
		Assert.assertEquals(actualScale, scale, "按企业规模搜索结果不对，不符合条件的行：" + companyPage.printData(row));
		
		//按符合条件搜索:客户名称包含 企业; 发电类型=光伏; 企业性质=国企; 企业规模=500-999人，验证搜索结果正确	
		companyPage.getNameInput().setText(name);
		companyPage.getCompanyTypeSelect().selectByText(companyType);
		companyPage.getPowerTypeSelect().selectByText(powerType);
		companyPage.getCompanyScaleSelect().selectByText(scale);
		companyPage.getQueryBtn().click();
		Driver.wait(loadTime);
		result = companyPage.getPowerCompanyTable().VerifyAllRows(companyPage.getPager(), new ICheckRow() {
			public boolean CheckRow(HtmlRow row) throws Exception {
				return row.getCellValue(PowerCompanyPage.NameColNo).contains(name)
				 && row.getCellValue(PowerCompanyPage.CompanyTypeColNo).equals(powerType)
				 && row.getCellValue(PowerCompanyPage.PowerTypeColNo).equals(powerType);
			}
		});
		Assert.assertNull(result, "按复合条件搜索结果不对，不符合条件的行：" + companyPage.printData(result));
		
		row = companyPage.getPowerCompanyTable().getFirstRow();
		row.getCell(PowerCompanyPage.OpsColNo).findElementByLinkText("编辑").click();
		actualScale = dialog.getScaleSelect().getSelectedText();
		dialog.getMainInfoCloseBtn().click();
		Assert.assertEquals(actualScale, scale, "按企业规模搜索结果不对，不符合条件的行：" + companyPage.printData(row));
		
		companyPage.getPager().goLastPage();
		Driver.wait(loadTime);
		row = companyPage.getPowerCompanyTable().getLastRow();
		row.getCell(PowerCompanyPage.OpsColNo).findElementByLinkText("编辑").click();
		actualScale = dialog.getScaleSelect().getSelectedText();
		dialog.getMainInfoCloseBtn().click();
		Assert.assertEquals(actualScale, scale, "按企业规模搜索结果不对，不符合条件的行：" + companyPage.printData(row));		
		
		//点击重置按钮，验证搜索条件被重置，表格显示所有发电企业
		companyPage.getResetBtn().click();
		Driver.wait(loadTime);
		Assert.assertEquals(companyPage.getNameInput().getText(), "", "名称未重置");
		Assert.assertEquals(companyPage.getPowerTypeSelect().getSelectedIndex(), 0, "发电类型未重置");
		Assert.assertEquals(companyPage.getCompanyTypeSelect().getSelectedIndex(), 0, "企业性质未重置");
		Assert.assertEquals(companyPage.getCompanyScaleSelect().getSelectedIndex(), 0, "企业规模未重置");
		Assert.assertEquals(companyPage.getPager().getTotalCount(), count, "查询条件重置后未显示所有发电企业");
		
		//点击查询按钮，验证表格显示所有发电企业
		companyPage.getQueryBtn().click();
		Driver.wait(loadTime);
		Assert.assertEquals(companyPage.getPager().getTotalCount(), count, "未设置查询条件时点查询没有显示所有发电企业");
	}
}
