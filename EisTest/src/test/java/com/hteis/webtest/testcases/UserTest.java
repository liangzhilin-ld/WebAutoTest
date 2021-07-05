package com.hteis.webtest.testcases;

import org.testng.*;
import org.testng.annotations.Test;
import com.hteis.webtest.common.DateUtil;
import com.hteis.webtest.entities.Constants;
import com.hteis.webtest.pages.*;
import com.hteis.webtest.selenium.*;

public class UserTest extends TCBase {
	
	@Test(groups="Uid", priority=1, testName="创建组织-成功",
			description="数据填写正确时可创建用户\r\n"
						+ "可选的角色为公司被分配的所有系统的所有角色\r\n"
						+ "待选的组织列表为当前管理员所在的公司的组织\r\n"
						+ "新建的用户显示在用户表格中")
	public void Uid_UsrNew01() throws Exception{
		
		//1.访问https://tuid.hteis.cn/cas
		//2.登录售电管理员用户名和密码，并点击“登录”
		//用户名：sradmin
		//密码：sradmin
		this.loginUid(Constants.saleAdminUsr, Constants.saleAdminPwd);
		
		//3.点击导航栏“用户管理中心”-“用户信息维护”
		PageBase homePage = new PageBase();
		homePage.openNavMenu("用户管理中心", "用户信息维护");
		
		//4.点击“新增”
		UserListPage usrPage = new UserListPage();
		usrPage.getNewButton().click();
		NewUserDialog dialog = new NewUserDialog();
		
		//5.填写数据
		String randomNo = DateUtil.getUniquePhone();
		String usrId = "u" + randomNo;
		String usrName = "王霸";
		String id = "478345294857373625";
		String tel = "010-93848783";
		String phone = randomNo;
		String email = randomNo + "@qq.com";
		dialog.getUsrIdInput().setText(usrId);
		dialog.getUsrNameInput().setText(usrName);
		dialog.getIdInput().setText(id);
		dialog.getTelInput().setText(tel);
		dialog.getPhoneInput().setText(phone);
		dialog.getEmailInput().setText(email);
		
		//6. 验证：公司名称为“银河能源贸易集团(测试专用)”
		Assert.assertEquals(dialog.getCompanyInput().getText(),  "银河能源贸易集团(测试专用)");
		
		//7. 验证：可选的组织为公司的组织，选择北域采购部
		HtmlSelect orgSelect = dialog.getOrgSelect();
		Assert.assertTrue(orgSelect.isOptionExist("北域采购部"), "北域采购部不可选");
		Assert.assertTrue(orgSelect.isOptionExist("西域采购部"), "西域采购部不可选");
		Assert.assertTrue(orgSelect.isOptionExist("采购部"), "采购部不可选");
		orgSelect.selectByText("北域采购部");
		
		//8.验证：可选的角色为公司被分配的所有系统的所有角色
		String[] expectedRoles = {"售电合同管理员", "业务主管", "领导层", "发起人", "主管", "客户专员",
				"客户主管", "公司资料员", "购电业务员", "售电结算员", "售电申报员", "交易员", "交易结算员"};
		Assert.assertEquals(dialog.getAllRoles(), expectedRoles, "可选择角色不对");
		dialog.getRoleCk("交易员").check();	
		dialog.getRoleCk("业务主管").check();
		
		//8.点击保存,验证提示信息为“保存成功”
		dialog.getSaveBtn().click();
		this.verifyAlert(usrPage, "保存成功");
		
		//9.验证列表中出现新增用户信息
		HtmlTable userTable = usrPage.getUserTable();
		HtmlRow row = userTable.findRow(UserListPage.userIdColNo, usrId);
		Assert.assertNotNull(row);
		Assert.assertEquals(row.getCellValue(4), usrName);
		Assert.assertEquals(row.getCellValue(5), phone);
		Assert.assertEquals(row.getCellValue(6), tel);
		Assert.assertEquals(row.getCellValue(7), email);
		Assert.assertEquals(row.getCellValue(8), "北域采购部");
		Assert.assertEquals(row.getCellValue(9), "正常");
	}
}
