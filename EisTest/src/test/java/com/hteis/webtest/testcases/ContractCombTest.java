package com.hteis.webtest.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.hteis.webtest.common.DateUtil;
import com.hteis.webtest.entities.Constants;
import com.hteis.webtest.pages.*;
import com.hteis.webtest.selenium.*;

public class ContractCombTest extends TCBase {
	
	private static final String ContractMgMenu = "合同管理";
	private static final String ContractCombMenu = "模板管理";
	private static final String ContractListMenu = "合同列表";
	private static final int IdColumnNo = 2;
	private static final int StatusColumnNo = 6;

	@Test(groups="Sep", priority=2, testName="查看套餐列表",
			description="套餐管理页面可查看套餐列表, 表格翻页功能正常")
	public void Sep_CombView(){
		
		//1.访问https://tuad.hteis.cn/
		//2.登录售电业务员用户名和密码，并点击“登录”
		//售电业务员：用户名：srsale，密码：srsale
		SepHomePage homePage = this.loginSep(Constants.saleTestUsr, Constants.saleTestPwd);		
		
		//3.点击导航栏“合同管理”-“套餐管理”
		homePage.openNavMenu(ContractMgMenu, ContractCombMenu);	
		ContractCombPage combPage = new ContractCombPage();
		
		//4.点击翻页“《”“》”
		//5.验证系统能够跳转的上一页或下一页
		//6.选择每页可显示数目10、15、25等
		//7.验证列表可根据选择数目显示
		// 设置每页数
		HtmlNgPager pager = combPage.getPager();	
		pager.setNumPerPage(25);
		Assert.assertEquals(25, combPage.getContractCombTable().getRowCount(), "设置每页行数失败");
		pager.setNumPerPage(15);
		Assert.assertEquals(15, combPage.getContractCombTable().getRowCount(), "设置每页行数失败");
		pager.setNumPerPage(10);
		Assert.assertEquals(10, combPage.getContractCombTable().getRowCount(), "设置每页行数失败");
		
		// 选择页
		String firstId = combPage.getContractCombTable().getFirstRow().getCellValue(IdColumnNo);
		String lastId = combPage.getContractCombTable().getLastRow().getCellValue(IdColumnNo);
		pager.selectPage(2);
		Assert.assertEquals(10, combPage.getContractCombTable().getRowCount(), "第二页数目不对");
		String firstId2 = combPage.getContractCombTable().getFirstRow().getCellValue(IdColumnNo);
		String lastId2 = combPage.getContractCombTable().getLastRow().getCellValue(IdColumnNo);
		Assert.assertNotEquals(firstId, firstId2, "选择第二页失败");
		Assert.assertNotEquals(lastId, lastId2, "选择第二页失败");
		
		// 向后翻
		pager.goPrevPage();
		String newFirstId = combPage.getContractCombTable().getFirstRow().getCellValue(IdColumnNo);
		String newLastId = combPage.getContractCombTable().getLastRow().getCellValue(IdColumnNo);
		Assert.assertEquals(newFirstId, firstId, "翻回第一页失败");
		Assert.assertEquals(newLastId, lastId, "翻回第一页失败");
		
		// 向前翻
		pager.goNextPage();
		newFirstId = combPage.getContractCombTable().getFirstRow().getCellValue(IdColumnNo);
		newLastId = combPage.getContractCombTable().getLastRow().getCellValue(IdColumnNo);
		Assert.assertEquals(newFirstId, firstId2, "向前翻失败");
		Assert.assertEquals(newLastId, lastId2, "向前翻失败");
		
		// 输入页数
		pager.setPageNum(3);
		newFirstId = combPage.getContractCombTable().getFirstRow().getCellValue(IdColumnNo);
		newLastId = combPage.getContractCombTable().getLastRow().getCellValue(IdColumnNo);
		Assert.assertNotEquals(newFirstId, firstId2, "翻至第三页失败");
		Assert.assertNotEquals(newLastId, lastId2, "翻至第三页失败");
    }
	
	@Test(groups="Sep", priority=1, testName="新建套餐-成功",
			description="套餐管理页面点击新增按钮弹出新增对话框\r\n"
						+"正确填写数据并选择配置项后点确定，套餐创建成功\r\n"
						+"新建套餐出现在表格中")
	public void Sep_CombNew01() throws Exception{
		//1.访问https://tuad.hteis.cn/
		//2.登录售电业务员用户名和密码，并点击“登录”
		//售电业务员：用户名：srsale，密码：srsale
		SepHomePage homePage = this.loginSep(Constants.saleTestUsr, Constants.saleTestPwd);	
		
		//3.点击导航栏“合同管理”-“套餐管理”
		homePage.openNavMenu(ContractMgMenu, ContractCombMenu);	
		ContractCombPage combPage = new ContractCombPage();
		
		//4.点击“新增”按钮
		combPage.getNewBtn().click();
		
		//5.验证弹出新增对话框
		ContractCombDialog dialog = new ContractCombDialog();
		
		//6.输入以下信息：
		//套餐编号：TC0001
		//套餐名称：园区企业套餐
		//开始时间：2017-01-01
		//结束时间：2027-12-31
		//7.模板选择：
		//电量：模板一
		//电价：模板一
		//结算：模板一
		//考核：模板一
		
		String id = DateUtil.getNoFromDateTime("C");
		String name = DateUtil.getNoFromDateTime("套餐");		
		dialog.setValus(id, name, 1, 1, 1, 2);
		
		//8. 验证所选模板字段显示正确
		Assert.assertEquals(dialog.getSelectedTempDiv().getText(), "【电量模板一】【电价模板一】【结算模板一】【考核模板二】");
		dialog.setConfig(2, 3, 4, 5);
		Assert.assertEquals(dialog.getSelectedTempDiv().getText(), "【电量模板二】【电价模板三】【结算模板四】【考核模板五】");		
		
		//9.点击“确定”
		dialog.getConfirmBtn().click();
		
		//10.验证提示信息为“新增成功”	
		this.verifyAlert(combPage, "新增成功");
		
		//11.验证新建套餐在列表中显示
		HtmlTable table = combPage.getContractCombTable();
		HtmlRow row = table.findRow(IdColumnNo, id);
		Assert.assertEquals(row.getCellValue(3), name);
		Assert.assertEquals(row.getCellValue(4), DateUtil.getCNTodayStr());
		Assert.assertEquals(row.getCellValue(5), DateUtil.getCNNextYearTodayStr());
		Assert.assertEquals(row.getCellValue(StatusColumnNo), "编辑");
		Assert.assertEquals(row.getCellValue(7), Constants.saleTestName);
		
	}
	
	
	@Test(groups="Sep", priority=3, testName="新建套餐-失败",
			description="当数据填写不正确时提交失败，字段验证信息正确")
	public void Sep_CombNew02() throws Exception{
		//1.访问https://tuad.hteis.cn/
		//2.登录售电业务员用户名和密码，并点击“登录”
		//售电业务员：用户名：srsale，密码：srsale
		SepHomePage homePage = this.loginSep(Constants.saleTestUsr, Constants.saleTestPwd);	
		
		//3.点击导航栏“合同管理”-“套餐管理”
		homePage.openNavMenu(ContractMgMenu, ContractCombMenu);	
		ContractCombPage combPage = new ContractCombPage();
		
		//4.点击“新增”按钮
		combPage.getNewBtn().click();
		ContractCombDialog dialog = new ContractCombDialog();
		
		//2.在以下情况下验证错误提示，错误情况下点确认按钮，验证确认未成功
		//1）套餐编码输入“TC”时，字段验证信息为“套餐编码至少有3个字符。”
		dialog.getComboNoInput().setText("TC");
		Assert.assertTrue(dialog.GetNoMinErrorSmall().isDisplayed(), "套餐编码最小长度错误提示未显示");
		Assert.assertEquals(dialog.GetNoMinErrorSmall().getText(), "套餐编码至少有3个字符", "套餐编码最小长度错误提示不对");
	
		//2) 套餐编号未填，其他正确，字段验证提示信息为“套餐编号不能为空。”
		dialog.getComboNoInput().clear();
		Assert.assertTrue(dialog.GetNoReqErrorSmall().isDisplayed(), "套餐编码必填错误提示未显示");
		Assert.assertEquals(dialog.GetNoReqErrorSmall().getText(), "套餐编码必填", "套餐编码必填错误提示不对");
		
		//3) 套餐编号输入超过15个字符时，字段验证信息为“套餐编码不能超过15个字符”
	    dialog.getComboNoInput().setText("C123456789123456");
	    Assert.assertTrue(dialog.GetNoMaxErrorSmall().isDisplayed(), "套餐编码最大长度错误提示未显示");
		Assert.assertEquals(dialog.GetNoMaxErrorSmall().getText(), "套餐编码不能超过15个字符", "套餐编码最大长度错误提示不对");
		
		//4) 套餐名称输入“园区”时，字段验证信息为“套餐名称至少有3个字符。”
		dialog.getComboNameInput().setText("园区");
		Assert.assertTrue(dialog.GetNameMinErrorSmall().isDisplayed(), "套餐名称最小长度错误提示未显示");
		Assert.assertEquals(dialog.GetNameMinErrorSmall().getText(), "套餐名称至少有3个字符", "套餐名称最小长度错误提示不对");
		
		//5) 套餐名称未填，其他正确，字段验证信息为“套餐名称不能为空。”
		dialog.getComboNameInput().clear();
		Assert.assertTrue(dialog.GetNameReqErrorSmall().isDisplayed(), "套餐名称必填错误提示未显示");
		Assert.assertEquals(dialog.GetNameReqErrorSmall().getText(), "套餐名称必填", "套餐名称必填错误提示不对");
		
		//3) 套餐名称输入超过80个字符时，字段验证信息为“套餐名称不能超过80个字符”
	    dialog.getComboNameInput().setText("套餐12345678901234567890123456789012345678901234567890123456789012345678901234567890");
	    Assert.assertTrue(dialog.GetNameMaxErrorSmall().isDisplayed(), "套餐名称最大长度错误提示未显示");
		Assert.assertEquals(dialog.GetNameMaxErrorSmall().getText(), "套餐名称不能超过80个字符", "套餐名称最大长度错误提示不对");
	
		//3）电量模板未填，其他正确，字段验证信息为“电量模板未选。”
		dialog.getConfirmBtn().click();
		this.verifyAlert(dialog, "电量模板未选");
		Driver.wait(1000);
		
		//4）电价模板未填，其他正确，字段验证信息为“电价模板未选。”
		dialog.selectCharge(1);
		dialog.getConfirmBtn().click();
		this.verifyAlert(dialog, "电价模板未选");	
		Driver.wait(1000);
		
		//5）结算模板未填，其他正确，字段验证信息为“结算模板未选。”
		dialog.selectPrice(1);
		dialog.getConfirmBtn().click();
		this.verifyAlert(dialog, "结算模板未选");
		Driver.wait(1000);
		
		//6）考核模板未填，其他正确，字段验证信息为“考核模板未选。”
		dialog.selectSettle(1);
		dialog.getConfirmBtn().click();
		this.verifyAlert(dialog, "考核模板未选");
		Driver.wait(1000);
		
		//7) 使用已存在的套餐编号，提示“套餐已存在”
		dialog.selectEvaluate(1);
		dialog.getComboNoInput().setText("TC0001");
		dialog.getComboNameInput().setText("abc");
		dialog.getConfirmBtn().click();
		this.verifyAlert(dialog, "套餐已存在");	
	}
	
	@Test(groups="Sep", priority=2, testName="修改套餐",
			description="套餐管理页面点击套餐编号弹出编辑对话框\r\n"
						+"修改数据或配置项后点确定，套餐修改成功\r\n"
						+"表格中的数据也做了相应修改")
	public void Sep_CombEdit01() throws Exception{
		//1.访问https://tuad.hteis.cn/
		//2.登录售电业务员用户名和密码，并点击“登录”
		//售电业务员：用户名：srsale，密码：srsale
		SepHomePage homePage = this.loginSep(Constants.saleTestUsr, Constants.saleTestPwd);	
		
		//3.点击导航栏“合同管理”-“套餐管理”
		homePage.openNavMenu(ContractMgMenu, ContractCombMenu);	
		ContractCombPage combPage = new ContractCombPage();		
		
		//4.新建一个套餐
		combPage.getNewBtn().click();
		ContractCombDialog dialog = new ContractCombDialog();
		String id = DateUtil.getNoFromDateTime("C");
		String name = DateUtil.getNoFromDateTime("套餐");		
		dialog.setValus(id, name, 2, 1, 2, 5);
		dialog.getConfirmBtn().click();	
		Driver.wait(1000);
		
		//5.点击套餐编码，弹出修改对话框，将信息修改为：
		 combPage.getContractCombTable().findRow(IdColumnNo, id).getCell(IdColumnNo).click();		
		
		//套餐名称：大客户套餐
		//开始时间：2018-01-01
		//结束时间：2028-12-31
		String newName = "大客户套餐";
		String newStartDateStr = "2018-01-01";
		String newEndDateStr = "2028-12-31";
		dialog.getComboNameInput().setText(newName);
		dialog.getComboStartTimeInput().setText(newStartDateStr);
		dialog.getComboEndTimeInput().setText(newEndDateStr);		
		
		//6.模板选择：
		//电量：模板二
		//电价：模板二
		//结算：模板二
		//考核：模板二
		dialog.setConfig(2, 3, 4, 5);		
		
		//7.点击“确定”
		dialog.getConfirmBtn().click();
		
		//8.验证提示信息为“保存成功”
		this.verifyAlert(dialog, "保存成功");
		
		//9.验证新建套餐在列表中的信息已变化
		HtmlRow row = combPage.getContractCombTable().findRow(IdColumnNo, id);
		Assert.assertEquals(row.getCellValue(3), newName, "套餐名称不正确");
		Assert.assertEquals(row.getCellValue(4), newStartDateStr, "套餐开始时间不正确");
		Assert.assertEquals(row.getCellValue(5), newEndDateStr, "套餐结束时间不正确");
		
		//10.点击列表中套餐编号字段
		row.getCell(IdColumnNo).click();	
		
		//11.验证已修改信息正确显示
		Assert.assertEquals(dialog.getComboNameInput().getText(), newName, "套餐名称不正确");
		Assert.assertEquals(dialog.getComboStartTimeInput().getText(), newStartDateStr, "套餐开始时间不正确");
		Assert.assertEquals(dialog.getComboEndTimeInput().getText(), newEndDateStr, "套餐结束时间不正确");
		Assert.assertEquals(dialog.getChargeSelection(), 2, "电量模板选择不正确");
		Assert.assertEquals(dialog.getPriceSelection(), 3, "价格模板选择不正确");
		Assert.assertEquals(dialog.getSettleSelection(), 4, "结算模板选择不正确");
		Assert.assertEquals(dialog.getEvalSelection(), 5, "考核模板选择不正确");
	}
	
	@Test(groups="Sep", priority=2, testName="修改套餐-失败",
			description="当套餐状态不是编辑时不可修改")
	public void Sep_CombEdit02() throws Exception{
		//1.访问https://tuad.hteis.cn/
		//2.登录售电业务员用户名和密码，并点击“登录”
		//售电业务员：用户名：srsale，密码：srsale
		SepHomePage homePage = this.loginSep(Constants.saleTestUsr, Constants.saleTestPwd);	
		
		//3.点击导航栏“合同管理”-“套餐管理”
		homePage.openNavMenu(ContractMgMenu, ContractCombMenu);	
		ContractCombPage combPage = new ContractCombPage();		
		
		//4.新建一个套餐
		combPage.getNewBtn().click();
		ContractCombDialog dialog = new ContractCombDialog();
		String id = DateUtil.getNoFromDateTime("C");
		String name = DateUtil.getNoFromDateTime("套餐");		
		dialog.setValus(id, name, 2, 1, 2, 5);
		dialog.getConfirmBtn().click();		
		
		//5.点击新套餐的“启用”按钮
		HtmlRow row = combPage.getContractCombTable().findRow(IdColumnNo, id);
		row.findElementByLinkText("启用").click();
		
		//6.点击套餐编码编辑套餐
		combPage.getContractCombTable().findRow(IdColumnNo, id).getCell(IdColumnNo).click();
		Driver.wait(1000);
		
		//7.修改套餐名称为任意值
		dialog = new ContractCombDialog();
		dialog.getComboNameInput().setText(DateUtil.getNoFromDateTime("套餐"));
		
		//8.点击确定
		dialog.getConfirmBtn().click();
		this.verifyAlert(dialog, "合同套餐不是编辑状态，不可修改");		
	}
	
	@Test(groups="Sep", priority=2, testName="预览套餐",
			description="套餐管理页面点击某个套餐数据中的预览按钮弹出预览对话框，验证内容正确")
	public void Sep_CombPreview() throws Exception{
		//1.访问https://tuad.hteis.cn/
		//2.登录售电业务员用户名和密码，并点击“登录”
		//售电业务员：用户名：srsale，密码：srsale
		SepHomePage homePage = this.loginSep(Constants.saleTestUsr, Constants.saleTestPwd);	
		
		//3.点击导航栏“合同管理”-“套餐管理”
		homePage.openNavMenu(ContractMgMenu, ContractCombMenu);	
		ContractCombPage combPage = new ContractCombPage();		
		
		//4.新建一个套餐
		combPage.getNewBtn().click();
		ContractCombDialog dialog = new ContractCombDialog();
		String id = DateUtil.getNoFromDateTime("C");
		String name = DateUtil.getNoFromDateTime("套餐");		
		dialog.setValus(id, name, 2, 1, 2, 5);
		String chargeTerm = dialog.getChargeTerm(2);
		String priceTerm = dialog.getPriceTerm(1);
		String settleTerm = dialog.getSettleTerm(2);
		String evalTerm = dialog.getEvalTerm(5);
		
		dialog.getConfirmBtn().click();		
		
		//5. 点击预览
		HtmlRow row =combPage.getContractCombTable().findRow(IdColumnNo, id);
		row.findElementByLinkText("预览").click();
		
		CombPreviewDialog previewDialog = new CombPreviewDialog();
		Assert.assertEquals(previewDialog.getComboNoDiv().getText(), id);
		Assert.assertEquals(previewDialog.getComboNameDiv().getText(), name);
		Assert.assertEquals(previewDialog.getChargeTermDiv().getText(), chargeTerm);
		Assert.assertEquals(previewDialog.getPriceTermDiv().getText(), priceTerm);
		Assert.assertEquals(previewDialog.getSettleTermDiv().getText(), settleTerm);
		Assert.assertEquals(previewDialog.getEvalTermDiv().getText(), evalTerm);
	}
	
	@Test(groups="Sep", priority=2, testName="启用和停用套餐",
			description="套餐管理页面点击启用和停用套餐\r\n"
						+"当启用一个套餐时，创建合同时可以选择该套餐\r\n"
						+"当停用一个套餐时，创建合同时不能选择该套餐")
	public void Sep_CombEnable() throws Exception{
		//1.访问https://tuad.hteis.cn/
		//2.登录售电业务员用户名和密码，并点击“登录”
		//售电业务员：用户名：srsale，密码：srsale
		SepHomePage homePage = this.loginSep(Constants.saleTestUsr, Constants.saleTestPwd);	
		
		//3.点击导航栏“合同管理”-“套餐管理”
		homePage.openNavMenu(ContractMgMenu, ContractCombMenu);	
		ContractCombPage combPage = new ContractCombPage();		
		
		//4.新建一个套餐
		combPage.getNewBtn().click();
		ContractCombDialog dialog = new ContractCombDialog();
		String id = DateUtil.getNoFromDateTime("C");
		String name = DateUtil.getNoFromDateTime("套餐");		
		dialog.setValus(id, name, 2, 1, 2, 5);
		dialog.getConfirmBtn().click();		
		
		//4.点击新套餐的“启用”按钮
		HtmlRow row = combPage.getContractCombTable().findRow(IdColumnNo, id);
		row.findElementByLinkText("启用").click();
		
		//5.验证新套餐状态由“编辑”变为“启用”
		row = combPage.getContractCombTable().findRow(IdColumnNo, id);
		Assert.assertEquals(row.getCellValue(StatusColumnNo), "启用");
		
		//6.点击导航栏“合同管理”-“合同列表”
		homePage.openNavMenu(ContractMgMenu,  ContractListMenu);		
		ContractListPage listPage = new ContractListPage();
		
		//7.点击“起草合同”
		listPage.getNewContractBtn().click();
		ContractNewPage newPage = new ContractNewPage();
		
		//8.验证：在“合同套餐信息”可选列表中，新的套餐可选
		Assert.assertTrue(newPage.getCombSelect().isOptionExist(name), "套餐启用后仍然不可用");
		
		//9.点击导航栏“合同管理”-“套餐管理”
		homePage.openNavMenu(ContractMgMenu, ContractCombMenu);	
		
		//10.点击新套餐的“停用”按钮
		row =combPage.getContractCombTable().findRow(IdColumnNo, id);
		row.findElementByLinkText("停用").click();
		
		// 验证套餐在列表中已不可见
		Assert.assertNull(combPage.getContractCombTable().findRow(IdColumnNo, id), "套餐停用后未在表中隐藏");
		
		//11.勾选操作“含停用”
		combPage.getIncludeDisabledCk().check();
		
		//12.验证TC0001套餐状态变为“停用”
		row = combPage.getContractCombTable().findRow(IdColumnNo, id);
		Assert.assertEquals(row.getCellValue(StatusColumnNo), "停用");
		
		//13.点击导航栏“合同管理”-“合同列表”
		homePage.openNavMenu(ContractMgMenu, ContractListMenu);	
		
		//14.点击“起草合同”
		listPage.getNewContractBtn().click();
		
		//15.验证：在“合同套餐信息”可选列表中，没有新建的套餐
		Assert.assertFalse(newPage.getCombSelect().isOptionExist(name), "套餐停用后仍可用");
	}
}
