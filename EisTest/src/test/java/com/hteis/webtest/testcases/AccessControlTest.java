package com.hteis.webtest.testcases;

import java.util.*;
import org.testng.*;
import org.testng.annotations.Test;

import com.hteis.webtest.entities.Constants;
import com.hteis.webtest.pages.*;
import com.hteis.webtest.pages.mgc.MgPageBase;
import com.hteis.webtest.selenium.*;

public class AccessControlTest extends TCBase {

	@Test(suiteName="AccessControl", groups = "AccessControl", priority = 1, testName = "添加/移除权限", 
			description = "添加角色后用户拥有相应的访问权限\r\n移除角色后用户相应的访问权限也被移除")
	public void Auth_Role() throws Exception {
		// 1. 使用sradmin登录https://tuid.hteis.cn/
		this.loginUid(Constants.saleAdminUsr, Constants.saleAdminPwd);

		// 2. 在用户信息维护中给sradmin添加角色“交易结算员”
		PageBase homePage = new PageBase();
		homePage.openNavMenu("用户管理中心", "用户信息维护");
		UserListPage userListPage = new UserListPage();
		userListPage.addRole(Constants.saleAdminUsr, "交易结算员");

		// 3. 刷新页面
		Driver.refresh();

		// 4. 验证：导航栏中出现新的菜单：结算管理->结算结果管理
		Assert.assertTrue(homePage.isNavMenuExist("结算管理", "结算结果管理"), "添加角色后菜单结算结果管理未出现");

		// 5. 在用户信息维护中移除sradmin的角色“交易结算员”
		homePage.openNavMenu("用户管理中心", "用户信息维护");
		userListPage.removeRole(Constants.saleAdminUsr, "交易结算员");

		// 6. 刷新页面后验证：导航栏中没有菜单：结算管理->结算结果管理
		Driver.refresh();
		Assert.assertFalse(homePage.isNavMenuExist("结算管理", "结算结果管理"), "移除角色后菜单结算结果管理未移除");

		// 7. 在用户信息维护中给用户srsalelead添加权限: 交易员、交易结算员
		String[] roleNames = { "交易员", "交易结算员" };
		userListPage.addRoles(Constants.saleTLUsr, roleNames);

		// 8. 使用srsalelead登录https://tsep.hteis.cn/
		Driver.reLaunchBrowser();
		this.loginSep(Constants.saleTLUsr, Constants.saleTLPwd);

		// 9. 验证：导航栏中有菜单->
		// 交易管理：交易场次管理、交易模拟测算、交易预案管理、交易结果管理
		// 结算管理：结算结果管理
		homePage = new PageBase();
		Assert.assertTrue(homePage.isNavMenuExist("交易管理", "交易场次管理"), "添加角色后菜单交易场次管理未出现");
		Assert.assertTrue(homePage.isNavMenuExist("交易管理", "交易模拟测算"), "添加角色后菜单交易模拟测算未出现");
		Assert.assertTrue(homePage.isNavMenuExist("交易管理", "交易预案管理"), "添加角色后菜单交易预案管理未出现");
		Assert.assertTrue(homePage.isNavMenuExist("交易管理", "交易结果管理"), "添加角色后菜单交易结果管理未出现");
		Assert.assertTrue(homePage.isNavMenuExist("结算管理", "结算结果管理"), "添加角色后菜单结算结果管理未出现");

		// 10. 使用sradmin登录，在用户信息中移除srsalelead的权限：交易员、交易结算员
		Driver.reLaunchBrowser();
		this.loginSep(Constants.saleAdminUsr, Constants.saleAdminUsr);
		homePage = new PageBase();
		homePage.openNavMenu("用户管理中心", "用户信息维护");
		userListPage = new UserListPage();
		userListPage.removeRoles(Constants.saleTLUsr, roleNames);

		// 11. 使用srsalelead登录https://tsep.hteis.cn/
		Driver.reLaunchBrowser();
		this.loginSep(Constants.saleTLUsr, Constants.saleTLPwd);

		// 12. 验证：导航栏中没有菜单->
		// 交易管理：交易场次管理、交易模拟测算、交易预案管理、交易结果管理
		homePage = new PageBase();
		Assert.assertFalse(homePage.isNavMenuExist("交易管理", "交易场次管理"), "添加角色后菜单交易场次管理未移除");
		Assert.assertFalse(homePage.isNavMenuExist("交易管理", "交易模拟测算"), "添加角色后菜单交易模拟测算未移除");
		Assert.assertFalse(homePage.isNavMenuExist("交易管理", "交易预案管理"), "添加角色后菜单交易预案管理未移除");
		Assert.assertFalse(homePage.isNavMenuExist("交易管理", "交易结果管理"), "添加角色后菜单交易结果管理未移除");
		Assert.assertFalse(homePage.isNavMenuExist("结算管理", "结算结果管理"), "添加角色后菜单结算结果管理未移除");
	}

	@Test(suiteName="AccessControl", groups = "AccessControl", priority = 1, testName = "售电公司管理员权限", 
			description = "售电公司管理员访问访问控制\r\n售电公司管理员只能查看和管理本公司用户")
	public void Auth_SepAdmin() throws Exception {
		// 前提：sradmin为售电公司管理员，未被授予任何角色
		// 1. 使用sradmin/sradmin访问https://tuid.hteis.cn/并登录
		this.loginUid(Constants.saleAdminUsr, Constants.saleAdminPwd);

		// 2. 验证：导航栏内有如下菜单项：
		// 用户管理中心的组织机构维护、用户信息维护、消息页面、修改密码、个人信息维护和公司信息
		PageBase homePage = new PageBase();
		Assert.assertEquals(homePage.getNavMenus().size(), 1);
		homePage.openNavMenu("用户管理中心", "用户信息维护");
		List<String> subMenus = homePage.getSubMenus("用户管理中心");
		String[] expSubMenus = { "组织机构维护", "用户信息维护", "消息页面", "修改密码", "个人信息维护", "公司信息" };

		Assert.assertEquals((String[]) subMenus.toArray(new String[1]), expSubMenus);

		// 3. 点击用户信息维护
		// 4. 验证：用户表格只有本公司的用户（全部以sr开头）
		UserListPage userListPage = new UserListPage();
		for (HtmlRow row : userListPage.getUserTable().getRows()) {
			Assert.assertTrue(row.getCell(UserListPage.userIdColNo).getText().startsWith("sr"));
		}
	}

	@Test(suiteName="AccessControl", groups = "AccessControl", priority = 1, testName = "售电公司业务员权限", 
			description = "售电公司业务员相关角色访问控制")
	public void Auth_SepSale() throws Exception {

		// 前提：srsale被授予如下角色：
		// 合同管理：售电合同管理员
		// 待处理工作：发起人
		// 客户管理：客户专员
		// 客户结算：售电结算员
		// 申报管理：售电申报员
		// 1. 使用srsale/srsale登录https://tsep.hteis.cn/
		this.loginSep(Constants.saleTestUsr, Constants.saleTestPwd);

		// 2. 验证：导航栏内有如下菜单：
		// 1) 用户管理中心：消息页面、修改密码、个人信息维护
		// 2) 客户管理：客户信息列表
		// 3) 合同管理：合同列表、套餐管理
		// 4) 申报管理：用电申报统计
		// 5) 客户结算：售电结算列表
		// 6) 待处理工作：任务列表(候选组)、未处理任务、已处理任务
		String[] menus = { "用户管理中心", "客户管理", "合同管理", "申报管理", "客户结算", "待处理工作" };
		String[][] subMenus = { { "消息页面", "修改密码", "个人信息维护" }, { "客户信息列表" }, { "合同列表", "套餐管理" }, { "用电申报统计" },
				{ "用电申报统计" }, { "售电结算列表" }, { "任务列表(候选组)", "未处理任务", "已处理任务" } };
		this.verifyMenus(menus, subMenus);
	}

	@Test(suiteName="AccessControl", groups = "AccessControl", priority = 1, testName = "售电公司交易员权限", 
			description = "售电公司交易员相关角色访问控制")
	public void Auth_SepTrader() throws Exception {

		// 前提：srtrade被授予如下角色：
		// 1) 待处理工作：发起人
		// 2) 交易管理：交易员
		// 3) 结算管理：交易结算员
		// 1. 使用srtrade登录https://tsep.hteis.cn/
		this.loginSep(Constants.tradeTestUsr, Constants.tradeTestPwd);

		// 2. 验证：导航栏内有如下菜单：
		// 1) 用户管理中心：消息页面、修改密码、个人信息维护
		// 2) 交易管理：交易场次管理、交易模拟测算、交易预案管理、交易结果管理
		// 3) 结算管理：结算结果管理
		String[] menus = { "用户管理中心", "交易管理", "结算管理" };
		String[][] subMenus = { { "消息页面", "修改密码", "个人信息维护" }, { "交易场次管理", "交易模拟测算", "交易预案管理", "交易结果管理" },
				{ "结算结果管理" } };
		this.verifyMenus(menus, subMenus);

	}

	@Test(suiteName="AccessControl", groups = "AccessControl", priority = 1, testName = "客户数据访问权限", 
			description = "售电平台用户只能访问本公司的数据，不能访问其他公司的数据")
	public void Auth_SepData() throws Exception {
		// 1. 使用srsale/srsale登录https://tcrm.hteis.cn/
		this.loginCust(Constants.saleTestUsr, Constants.saleTestPwd);

		// 2. 点击客户管理->客户信息列表
		PageBase homePage = new PageBase();
		homePage.openNavMenu("客户管理", "客户信息列表");

		// 3. 查看客户表格中的数据
		// 4. 验证：表格中的客户都是银河能源(四川)的客户
		CustListPage custListPage = new CustListPage();
		for (HtmlRow row : custListPage.getCustTable().getRows()) {
			Assert.assertTrue(row.getCellValue(CustListPage.custNameColNo).startsWith("四川"));
		}
	}

	@Test(suiteName="AccessControl", groups = "AccessControl", priority = 1, testName = "购电公司业务员权限", 
			description = "购电公司业务员相关角色访问控制")
	public void Auth_Buyer() throws Exception {
		// 1. 注册一个购电公司(地点：四川)
		// 2. 验证：登录后导航栏内有如下菜单：
		// 1) 用户管理中心：消息页面、修改密码、个人信息维护
		// 2) 公司资料管理：企业信息管理
		// 3) 购电合同管理：合同查询
		// 4) 购电业务：月度购电申报
		// 5) 结算账单：账单查询、历史账单查询
		// 6) 待处理工作：任务列表(候选组)、未处理任务、已处理任务
		this.LoginShop(Constants.buyerTestUsr, Constants.buyerTestPwd);
		String[] menus = { "用户管理中心", "公司资料管理", "购电合同管理", "购电业务", "结算账单", "待处理工作" };
		String[][] subMenus = { { "消息页面", "修改密码", "个人信息维护" }, { "企业信息管理" }, { "合同查询" }, { "月度购电申报" },
				{ "账单查询", "历史账单查询" }, { "任务列表(候选组)", "未处理任务", "已处理任务" } };
		this.verifyMenus(menus, subMenus);
	}

	@Test(suiteName="AccessControl", groups = "AccessControl", priority = 1, testName = "微网公司管理员权限", 
			description = "微网公司管理员访问控制\r\n微网公司管理员只能查看和管理本公司用户")
	public void Auth_Mg() throws Exception {
		//1. 使用agadmin登录https://tmgc.hteis.cn/
		//2. 验证：当航栏内有如下菜单：
		//1) 用户管理中心：组织机构维护、用户信息维护、消息页面、修改密码、个人信息维护、公司信息
		this.loginMgc(Constants.mgAdminUsr, Constants.mgAdminPwd);
		String[] menus = { "用户管理中心" };
		String[][] subMenus = { { "组织机构维护", "用户信息维护", "消息页面", "修改密码", "个人信息维护", "公司信息" } };
		this.verifyMenus(menus, subMenus);
		
		//3. 点击菜单用户信息维护
		PageBase homePage = new PageBase();
		homePage.openNavMenu("用户管理中心", "用户信息维护");
		
		//4. 验证：用户列表里只有本公司用户
		UserListPage userListPage = new UserListPage();
		for (HtmlRow row : userListPage.getUserTable().getRows()) {
			Assert.assertTrue(row.getCellValue(UserListPage.userIdColNo).startsWith("ag"));
		}
	}
	
	@Test(suiteName="AccessControl", groups = "AccessControl", priority = 1, testName = "微网公司运营人员权限", 
			description = "微网公司运营人员访问控制")
	public void Auth_MgWorker() throws Exception{
		//前提：ag001被授予如下角色：微网运营人员、微网信息管理、微网领导
		//1. 使用ag001登录https://tmgc.hteis.cn/
		//2. 验证：导航栏内有如下菜单：
		//1)用户管理中心：消息页面、修改密码、个人信息维护
		//2)综合监控系统：项目信息概览、实时监控、效益评价、监控数据查询、监控系统管理、监控设备管理、监控模型管理、实时监控设置
		this.loginMgc(Constants.mgOperatorUsr, Constants.mgOperatorPwd);
		String[] menus = { "用户管理中心", "综合监控系统"};
		String[][] subMenus = { { "消息页面", "修改密码", "个人信息维护" },
				{"项目信息概览", "实时监控", "告警信息查询", "效益评价", "监控数据查询", "监控系统管理", "监控设备管理", "监控模型管理", "实时监控设置"} };
		this.verifyMenus(menus, subMenus);		
	}
	
	@Test(suiteName="AccessControl", groups = "AccessControl", priority = 1, testName = "微网公司横向数据权限", 
			description = "微网用户只能访问本公司微网数据，不能访问其他公司的微网")
	public void Auth_MgData01() throws Exception{
		//1. 使用afd001登录https://tmgc.hteis.cn/
		this.loginMgc(Constants.mgOperatorUsr, Constants.mgOperatorPwd);
		
		//2. 访问各数据页面
		//3. 验证：微网列表中只有本公司的微网
		String[] expMgList = {"四川.成都城市微网", "华北.test001微网"};
		PageBase homePage = new PageBase();
		homePage.openNavMenu("综合监控系统", "项目信息概览");
		MgPageBase mgPageBase = new MgPageBase();
		this.verifyMG(mgPageBase, expMgList);
		
		homePage.openNavMenu("综合监控系统", "实时监控");
		this.verifyMG(mgPageBase, expMgList);
		
		homePage.openNavMenu("综合监控系统", "告警信息查询");
		this.verifyMG(mgPageBase, expMgList);
		
		homePage.openNavMenu("综合监控系统", "效益评价");
		this.verifyMG(mgPageBase, expMgList);
		
		homePage.openNavMenu("综合监控系统", "监控数据查询");
		this.verifyMG(mgPageBase, expMgList);
	}
	
	@Test(suiteName="AccessControl", groups = "AccessControl", priority = 1, testName = "微网公司内部权限控制", 
			description = "微网用户只能访问其所属的微网的数据，不是微网成员不能访问该微网数据")
	public void Auth_MgData02() throws Exception{
		//1. 使用afd001登录https://tmgc.hteis.cn/
		this.loginMgc(Constants.mgOperator2Usr, Constants.mgOperator2Pwd);
				
		//2. 访问各数据页面
		//3. 验证：页面上方的微网列表中只有“四川.城市微网”
		String[] expMgList = {"四川.成都城市微网"};
		PageBase homePage = new PageBase();
		homePage.openNavMenu("综合监控系统", "项目信息概览");
		MgPageBase mgPageBase = new MgPageBase();
		this.verifyMG(mgPageBase, expMgList);
		
		homePage.openNavMenu("综合监控系统", "实时监控");
		this.verifyMG(mgPageBase, expMgList);
		
		homePage.openNavMenu("综合监控系统", "告警信息查询");
		this.verifyMG(mgPageBase, expMgList);
		
		homePage.openNavMenu("综合监控系统", "效益评价");
		this.verifyMG(mgPageBase, expMgList);
		
		homePage.openNavMenu("综合监控系统", "监控数据查询");
		this.verifyMG(mgPageBase, expMgList);
	}
	
	/***验证微网列表*/
	private void verifyMG(MgPageBase mgPageBase, String[] expMgList){
		ArrayList<String> actualMgList = mgPageBase.getMgList();
		Assert.assertEquals((String[])actualMgList.toArray(new String[1]), expMgList);
	}

	private void verifyMenus(String[] menus, String[][] subMenus) {
		PageBase homePage = new PageBase();
		Assert.assertEquals(homePage.getNavMenus().size(), menus.length);

		for (int i = 0; i < menus.length; i++) {
			homePage.openNavMenu(menus[i], subMenus[i][0]);
			Assert.assertEquals((String[]) homePage.getSubMenus(menus[i]).toArray(new String[1]), subMenus[i]);
		}
	}
}
