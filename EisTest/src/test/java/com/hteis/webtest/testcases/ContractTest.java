package com.hteis.webtest.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.hteis.webtest.business.Company;
import com.hteis.webtest.common.DateUtil;
import com.hteis.webtest.entities.CompanyData;
import com.hteis.webtest.entities.Constants;
import com.hteis.webtest.entities.ContractData;
import com.hteis.webtest.pages.*;
import com.hteis.webtest.selenium.*;

public class ContractTest extends TCBase{

	private static final String ContractMgMenu = "合同管理";
	private static final String ContractListMenu = "合同列表";
	private static final String ContractCombMenu = "模板管理";
	private static final int StatusColumnNo = 7;
	private static final int IdColumnNo = 2;
	
	@Test(groups="Sep", priority=1, testName="起草合同-成功",
			description="填入正确数据后点确定，合同创建成功， 自动填充的数据正确\r\n"
					+ "新建的合同出现在表格中，合同状态为编辑状态，版本号显示为V1")
	public void Sep_ContractNew01() throws Exception{
		//前提：创建一个新购电用户并登录
		CompanyData companyData = CompanyData.createCompanyData();
		Company.createAndApproveBuyerCompany(companyData);
		Driver.reLaunchBrowser();
		
		//1.访问https://tuad.hteis.cn/
		//2.登录售电业务员用户名和密码，并点击“登录”
		//售电业务员：用户名：srsale，密码：srsale
		SepHomePage homePage = this.loginSep(Constants.saleTestUsr, Constants.saleTestPwd);	
		
		//3.点击导航栏“合同管理”-“合同列表”
		homePage.openNavMenu(ContractMgMenu, ContractListMenu);	
		ContractListPage listPage = new ContractListPage();
		
		//4.点击“起草合同”
		listPage.getNewContractBtn().click();				
	
		//5.输入有效的数据
		ContractData data = ContractData.createData(companyData.MainInfo.CompanyName, DateUtil.getThisYear(), "大客户套餐");
		ContractNewPage newPage = new ContractNewPage();
		newPage.setFieldValues(data);		
		
		//6.验证以下信息自动生成并且数据正确：
		//电压等级
		//客户企业所在地
		//客户企业地址
		//合同类型
		//用电编号
		//购电人
		Assert.assertEquals(newPage.getVoltLevelInput().getText(), companyData.MainInfo.VoltageLevel, "未自动填入电压等级");
		Assert.assertEquals(newPage.getCustProvinceInput().getText(), companyData.RegInfo.RegLocation, "未自动填入企业所在地");
		Assert.assertEquals(newPage.getCustAddressInput().getText(), companyData.RegInfo.Address, "未自动填入企业地址");
		Assert.assertEquals(newPage.getElectricNoInput().getText(), companyData.MainInfo.ElectronNo, "未自动填入用电编号");
		Assert.assertEquals(newPage.getContactNameInput().getText(), companyData.ContactInfo.Name, "未自动填入购电人");
		Assert.assertEquals(newPage.getContractTypeInput().getText(), data.ContractType, "未自动填入合同类型");				
		
		//7.验证购电总量正确		
		Assert.assertEquals(newPage.getTotalElectronInput().getText(), Float.toString(data.TotalAmount), "购电总量错误");
	
		//8.点击“保存”
		newPage.getConfirmBtn().click();
		
		//9.验证提示信息“保存成功”
		newPage.verifyAlert("保存成功");
		
		//10.验证合同状态为草稿状态
		HtmlRow row = listPage.getContractTable().findRow(ContractListPage.CustNameColNo, companyData.MainInfo.CompanyName);
	    Assert.assertEquals(row.getCellValue(StatusColumnNo), "草稿", "合同状态不是草稿");
		
		//11.验证合同版本号显示为V1
	    String id = row.getCellValue(IdColumnNo);
	    Assert.assertTrue(id.endsWith("V1"), "无版本号，合同编号为" + id);
	}
	
	@Test(groups="Sep", priority=2, testName="起草合同-选择套餐",
			description="起草合同时选择不同套餐，合同条款显示正确，套餐信息填写显示正确")
	public void Sep_ContractNew02(){
		//1.访问https://tuad.hteis.cn/
		//2.登录售电业务员用户名和密码，并点击“登录”
		//售电业务员：用户名：srsale，密码：srsale
		SepHomePage homePage = this.loginSep(Constants.saleTLUsr, Constants.saleTLPwd);		
		
		//3.点击导航栏“合同管理”-“套餐管理”
		homePage.openNavMenu(ContractMgMenu, ContractCombMenu);	
		ContractCombPage combPage = new ContractCombPage();
		
		//4.获取套餐TC0001和TC003的条款
		HtmlNgPager pager = combPage.getPager();
		pager.goLastPage();
		combPage.previewCombo(Constants.contractComb1No);
		CombPreviewDialog dialog = new CombPreviewDialog();
		String chargeTerm1 = dialog.getChargeTermDiv().getText();
		String priceTerm1 = dialog.getPriceTermDiv().getText();
		String settleTerm1 = dialog.getSettleTermDiv().getText();
		String evalTerm1 = dialog.getEvalTermDiv().getText();		
		dialog.close();
		
		combPage.previewCombo(Constants.contractComb2No);
		String chargeTerm2 = dialog.getChargeTermDiv().getText();
		String priceTerm2 = dialog.getPriceTermDiv().getText();
		String settleTerm2 = dialog.getSettleTermDiv().getText();
		String evalTerm2 = dialog.getEvalTermDiv().getText();
		dialog.close();
		
		//5.点击导航栏“合同管理”-“合同列表”		
		homePage.openNavMenu(ContractMgMenu, ContractListMenu);	
		
		//6.点击起草合同
		ContractListPage listPage = new ContractListPage();
		listPage.getNewContractBtn().click();
		ContractNewPage newPage = new ContractNewPage();
		
		//7.选择套餐"大客户套餐"
		newPage.getCombSelect().selectByText(Constants.contractComb1Name);
		Driver.wait(1000);
		
		//8.验证合同条款
		Assert.assertEquals(chargeTerm1, newPage.getChargeTerm(), "电量条款不对");
		Assert.assertEquals(priceTerm1, newPage.getPriceTerm(), "电价条款不对");
		Assert.assertEquals(settleTerm1, newPage.getSettleTerm(), "结算条款不对");
		Assert.assertEquals(evalTerm1, newPage.getEvaluateTerm(), "考核条款不对");
		
		//9.验证套餐信息填写项
		String[] expectedInfo1 = {"* 交易开始日期", "* 交易结束日期", "* 委托服务费(元)"};
		Assert.assertEquals(newPage.getCombInfoLabels(), expectedInfo1, "套餐信息填写项不对");
		
		//10.选择套餐"123"
		newPage.getCombSelect().selectByText(Constants.contractComb2Name);
		Driver.wait(1000);
		
		//11.验证合同条款
		Assert.assertEquals(chargeTerm2, newPage.getChargeTerm(), "电量条款不对");
		Assert.assertEquals(priceTerm2, newPage.getPriceTerm(), "电价条款不对");
		Assert.assertEquals(settleTerm2, newPage.getSettleTerm(), "结算条款不对");
		Assert.assertEquals(evalTerm2, newPage.getEvaluateTerm(), "考核条款不对");
		
		//12.验证套餐信息填写项
		String[] expectedInfo2 = {"* 交易开始日期", "* 交易结束日期", "* 年购电量(万kWh)", "* 增量比例下限(%)", "* 增量比例上限(%)", "* 结算价差（元）"};
		Assert.assertEquals(newPage.getCombInfoLabels(), expectedInfo2, "套餐信息填写项不对");
	}
}
