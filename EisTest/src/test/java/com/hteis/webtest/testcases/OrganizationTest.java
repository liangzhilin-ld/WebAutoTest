package com.hteis.webtest.testcases;

import java.util.ArrayList;

import org.testng.*;
import org.testng.annotations.Test;

import com.hteis.webtest.common.DateUtil;
import com.hteis.webtest.entities.Constants;
import com.hteis.webtest.pages.*;
import com.hteis.webtest.selenium.Driver;
import com.hteis.webtest.selenium.HtmlElement;

public class OrganizationTest extends TCBase {

	@Test(groups="Uid", priority=1, testName="创建组织-成功",
			description="选择公司后创建组织可创建一级组织\r\n" 
						+ "选择某个组织后可创建该组织的下属组织\r\n"
						+ "所属公司和上级组织名称自动填充\r\n"
						+ "组织创建成功后显示在左边的树状控件中\r\n")
	public void Uid_OrgNew01() throws Exception{
		//1.访问https://tuid.hteis.cn/cas 
		//2.使用售电管理员用户名和密码登录 sradmin/sradmin
		this.loginUid(Constants.saleAdminUsr, Constants.saleAdminPwd);		

		//3.点击导航栏“用户管理中心”-“组织机构维护”
		PageBase homePage = new PageBase();
		homePage.openNavMenu("用户管理中心", "组织机构维护");
		
		//4. 点击根节点"银河能源贸易集团"
		OrgViewPage orgPage = new OrgViewPage();
		orgPage.expandOrgTree();
		
		//5. 点击新增按钮
		orgPage.getNewButton().click();
		Thread.sleep(1000);
		
		//6. 验证上级组织名称输入框自动变为"银河能源贸易集团(测试专用)"
	    Assert.assertEquals(orgPage.getParentInput().getText(), Constants.saleCompany);
	    
	    //7. 验证所属公司为"银河能源贸易集团(测试专用)"
	    Assert.assertEquals(orgPage.getCompanyNameInput().getText(), Constants.saleCompany);
	    
	    //8. 输入以下信息：
		//组织机构代码：salexxx
		//组织机构名称：业务部
		//排序：1000
	    orgPage.getOrgCodeInput().setText(DateUtil.getNoFromDateTime("sale"));
	    orgPage.getOrgNameInput().setText("业务部");	
	    orgPage.getSortInput().setText("100");
	    
	    //9.点击保存
	    orgPage.getSaveButton().click();
	    
	    //10.点击两次根节点"银河能源贸易集团"刷新组织结构，验证业务部创建成功
	    orgPage.getCompanyNode().click();
	    orgPage.expandOrgTree();
	    Assert.assertNotNull(orgPage.getNodeByText("业务部"));
		
		//11.点击节点"销售部"
	    orgPage.getNodeByText("销售部").click();
		
	    //12.点击新增按钮
	    orgPage.getNewButton().click();
	    
	    //13.验证上级组织名称为“销售部”
	    Assert.assertEquals(orgPage.getParentInput().getText(), "销售部");
	    
	    //14.输入以下信息：
		//组织机构代码：saleNorth
		//组织机构名称：北域销售部
		//排序：101
	    orgPage.getOrgCodeInput().setText("saleNorth");
	    orgPage.getOrgNameInput().setText("北域销售部");	    
	    orgPage.getSortInput().setText("101");
		
		//15.点击“保存”
	    orgPage.getSaveButton().click();
	    
	    //16.点击两次根节点"银河能源贸易集团"刷新组织结构
	    orgPage.getCompanyNode().click();
	    orgPage.expandOrgTree();
	    
	    //17.点击节点“销售部”
	    orgPage.getNodeByText("销售部").click();
	    
	    //18.验证新建节点"北域销售部”出现
	    HtmlElement newNode = orgPage.getNodeByText("北域销售部");
	    Assert.assertNotNull(newNode);
	    Assert.assertTrue(newNode.isDisplayed());
	    
	    //19. 删除新建部门以恢复数据
	    orgPage.deleteOrg("业务部");
	    orgPage.deleteOrg("北域销售部");
	}
	
	
	@Test(groups="Uid", priority=2, testName="给分公司创建组织",
			description="选择某个分公司后可以给分公司创建组织")
	public void Uid_OrgNew02() throws Exception{
		//1.访问https://tuid.hteis.cn/cas
		//2.登录售电管理员用户名和密码sradmin/sradmin，并点击“登录”
		this.loginUid(Constants.saleAdminUsr, Constants.saleAdminPwd);	
		
		//3.点击导航栏“用户管理中心”-“组织机构维护”
		PageBase homePage = new PageBase();
		homePage.openNavMenu("用户管理中心", "组织机构维护");
		
		//4. 展开组织结构树
		OrgViewPage orgPage = new OrgViewPage();
		orgPage.expandOrgTree();
		
		//5.选择分公司“双子能源交易公司”
		orgPage.getNodeByText(Constants.saleSubCompany2).click();
		
		//6.点击新增按钮
		orgPage.getNewButton().click();
		
		//7.验证：所属公司和上级组织名称都为“双子能源交易公司”
		Assert.assertEquals(orgPage.getParentInput().getText(), Constants.saleSubCompany2);
		Assert.assertEquals(orgPage.getCompanyNameInput().getText(), Constants.saleSubCompany2);
		
		//8.输入以下数据
		//组织机构代码：salexxxx
		//组织机构名称：公关部
		//排序：1003
		orgPage.getOrgCodeInput().setText(DateUtil.getNoFromDateTime("pub"));
	    orgPage.getOrgNameInput().setText("公关部");	
	    orgPage.getSortInput().setText("103");
		
		//9. 点击保存
	    orgPage.getSaveButton().click();
	    
		//10. 刷新左侧组织结构树
	    orgPage.getCompanyNode().click();
	    orgPage.expandOrgTree();
	    
		//11.验证新的组织出现在组织结构树中
	    orgPage.getNodeByText(Constants.saleSubCompany2).click();
	    HtmlElement newNode = orgPage.getNodeByText("公关部");
	    Assert.assertNotNull(newNode);
	    Assert.assertTrue(newNode.isDisplayed());
	    
	    //12. 删除新建部门以恢复数据
	    orgPage.deleteOrg("公关部");
	}	
	
	@Test(groups="Uid", priority=3, testName="创建组织-失败",
			description="数据填写不正确或必填字段未填时，不能创建组织;\r\n组织机构代码不能重复")
	public void Uid_OrgNew03() throws Exception{
		//1.访问https://tuid.hteis.cn/cas
		//2.登录售电管理员用户名和密码sradmin/sradmin，并点击“登录”
		this.loginUid(Constants.saleAdminUsr, Constants.saleAdminPwd);	
		
		//3.点击导航栏“用户管理中心”-“组织机构维护”
		PageBase homePage = new PageBase();
		homePage.openNavMenu("用户管理中心", "组织机构维护");
		
		//4. 点击“新增”
		OrgViewPage orgPage = new OrgViewPage();
		orgPage.getNewButton().click();
		
		//5. 验证：提示信息：请选择父节点新增
		this.verifyAlert(orgPage, "请选择父节点新增");
		
		//6.点击根节点展开组织结构树
		orgPage.expandOrgTree();
		
		//5.点击“新增”
		orgPage.getNewButton().click();
		
		//6.在以下情况下验证错误提示，错误情况下点保存按钮，验证保存未成功
		//1) 组织机构代码未填，其他正确，字段验证信息为“组织机构代码是必须的。”
		orgPage.getOrgNameInput().setText("公关部");
		orgPage.getSaveButton().click();
		Assert.assertTrue(orgPage.getOrgCodeReqErrSpan().isDisplayed(), "组织机构代码必填错误提示未显示");
		Assert.assertEquals(orgPage.getOrgCodeReqErrSpan().getText(), "组织机构代码是必须的。");
		
		//2) 组织机构名称未填，其他正确，字段验证信息为“组织机构名称是必须的。”
		orgPage.getOrgNameInput().clear();		
		orgPage.getOrgCodeInput().setText(DateUtil.getNoFromDateTime("pub"));
		orgPage.getSaveButton().click();
		Assert.assertTrue(orgPage.getOrgNameReqErrSpan().isDisplayed(), "组织机构名称必填错误提示未显示");
		Assert.assertEquals(orgPage.getOrgNameReqErrSpan().getText(), "组织机构名称是必须的。");		

		//7.输入已存在的组织机构代码
		//8.验证提示信息为“保存失败，组织机构代码已存在”或“保存失败，组织机构名称已使用”
		orgPage.getOrgCodeInput().setText("srsale");
		orgPage.getOrgNameInput().setText("公关部");
		orgPage.getSaveButton().click();
		this.verifyAlert(orgPage, "保存失败!组织机构编码已经存在");
	}
	
	@Test(groups="Uid", priority=2, testName="修改组织",
			description="选择某个组织后可修改组织信息\r\n"
						+ "点击保存按钮后组织信息修改\r\n"
						+ "组织机构名称修改后，左侧树状结构中的名称也相应修改")
	public void Uid_OrgUpdate() throws Exception{
		//1.访问https://tuid.hteis.cn/cas
		//2.登录售电管理员用户名和密码sradmin/sradmin，并点击“登录”
		this.loginUid(Constants.saleAdminUsr, Constants.saleAdminPwd);	
		
		//3.点击导航栏“用户管理中心”-“组织机构维护”
		PageBase homePage = new PageBase();
		homePage.openNavMenu("用户管理中心", "组织机构维护");
		
		//4.展开组织结构树，选择组织“管理层”
		//5.对组织机构名称/组织机构代码/排序进行修改
		//6.点击“保存”
		OrgViewPage orgPage = new OrgViewPage();
		orgPage.expandOrgTree();
		orgPage.createOrg("管理层", "srleaders", "领导层", 11);
		
		//7.验证提示信息为保存成功
		this.verifyAlert(orgPage, "保存成功");
		
		//8.验证当修改机构名称时，左侧树状结构中的名称也相应修改
		orgPage.getCompanyNode().click();
		orgPage.expandOrgTree();
		orgPage.getNodeByText("领导层").click();
		Assert.assertEquals(orgPage.getOrgCodeInput().getText(), "srleaders");
		Assert.assertEquals(orgPage.getOrgNameInput().getText(), "领导层");
		Assert.assertEquals(orgPage.getSortInput().getText(), "11");
		
		// 恢复数据
		orgPage.setOrgFields("leaders", "管理层", 1);
		orgPage.getSaveButton().click();
	}
	
	@Test(groups="Uid", priority=2, testName="禁止组织",
			description="创建用户时禁止的组织不可选\r\n"
						+ "有活动用户的组织不能禁止\r\n"
						+ "组织内所有用户禁止后组织可禁止")
	public void Uid_OrgDisable() throws Exception{
		//1.访问https://tuid.hteis.cn/cas
		//2.登录售电管理员用户名和密码，并点击“登录”
		//用户名：sradmin
		//密码：sradmin
		this.loginUid(Constants.saleAdminUsr, Constants.saleAdminPwd);	
		
		//3.点击导航栏“用户管理中心”-“组织机构维护”
		PageBase homePage = new PageBase();
		homePage.openNavMenu("用户管理中心", "组织机构维护");
		
		//4. 在销售部下新建组织“北域销售部”
		String parentNode = "销售部";
		String newOrgName = DateUtil.getNoFromDateTime("北域销售部");
		OrgViewPage orgPage = new OrgViewPage();
		orgPage.expandOrgTree();
		orgPage.createOrg(parentNode, DateUtil.getNoFromDateTime("sale"), newOrgName, 1);		
		
		//5. 进入用户信息维护页面，打开创建用户页面
		orgPage.openNavMenu("用户管理中心", "用户信息维护");
		UserListPage userListPage = new UserListPage();
		userListPage.getNewButton().click();
		NewUserDialog dialog = new NewUserDialog();	
		
		//6. 验证“北域销售部”可选
		dialog.getOrgSelect().click();
		Assert.assertTrue(dialog.getOrgSelect().isOptionExist(newOrgName), "创建用户时新建的组织未出现");
		dialog.getCancelBtn().click();
		Driver.wait(1000);
		
		//7. 回到组织页面，禁止组织“北域销售部”
		String[] orgPath = {parentNode, newOrgName};
		this.enableOrg(homePage, orgPath, false);
		
		//8. 进入用户信息维护页面，打开创建用户页面
		orgPage.openNavMenu("用户管理中心", "用户信息维护");
		userListPage = new UserListPage();
		userListPage.getNewButton().click();
		dialog = new NewUserDialog();
		
		//9. 验证“北域销售部”不可选
		dialog.getOrgSelect().click();
		Assert.assertFalse(dialog.getOrgSelect().isOptionExist(newOrgName), "创建用户时禁止的组织可选");
		dialog.getCancelBtn().click();
		Driver.wait(1000);
		
		//10. 回到组织页面，激活组织“北域销售部”
		this.enableOrg(homePage, orgPath, true);
		
		//11.进入用户信息维护页面，打开创建用户页面
		orgPage.openNavMenu("用户管理中心", "用户信息维护");
		
		//12.创建一个用户，选择组织“北域销售部”
		String phone = DateUtil.getUniquePhone();
		String usrId = "sr" + phone;
		userListPage.createUser(usrId, "测试用户", phone, phone + "@qq.com", newOrgName);		
		
		//13.回到组织页面，禁止组织“北域销售部”
		this.enableOrg(homePage, orgPath, false);
		
		//14.验证：保存失败!提示该机构下有用户，请先禁止用户再操作
		this.verifyAlert(orgPage, "该机构下有用户，请先禁止用户再操作");
		
		//15.进入用户信息维护页面，禁止新建的用户
		orgPage.openNavMenu("用户管理中心", "用户信息维护");
		userListPage.disableUser(usrId);
		
		//16.回到组织页面，禁止组织“北域销售部”
		this.enableOrg(homePage, orgPath, false);
		
		//17.验证：“北域销售部”被成功禁止
		this.verifyAlert(orgPage, "保存成功");
		
		//恢复数据
		orgPage.openNavMenu("用户管理中心", "用户信息维护");
		userListPage.deleteUser(usrId);
		userListPage.openNavMenu("用户管理中心", "组织机构维护");
		orgPage.expandOrgTree();
		orgPage.deleteOrg(orgPath);
	}
	
	@Test(groups="Uid", priority=2, testName="删除组织",
			description="选中某个组织后点删除按钮可删除组织\r\n"
						+ "删除的组织从左侧树状控件中移除\r\n"
						+ "有下级组织的组织不能删除\r\n"
						+ "有用户的组织不能删除\r\n"
						+ "子公司不能删除")
	public void Uid_OrgDel() throws Exception{
		//1.访问https://tuid.hteis.cn/cas
		//2.登录售电管理员用户名和密码，并点击“登录”
		//用户名：sradmin
		//密码：sradmin
		this.loginUid(Constants.saleAdminUsr, Constants.saleAdminPwd);	
		
		//3.点击导航栏“用户管理中心”-“组织机构维护”
		PageBase homePage = new PageBase();
		homePage.openNavMenu("用户管理中心", "组织机构维护");
		
		//4. 展开组织后选择采购部，点击删除后点确认按钮
		//5. 验证错误信息: "该节点不是最底层节点无法删除"
		OrgViewPage orgPage = new OrgViewPage();
		orgPage.expandOrgTree();
		orgPage.deleteOrg("采购部");
		this.verifyAlert(orgPage, "该节点不是最底层节点无法删除");
		
		//6. 选择管理层，点击删除后点确认按钮
		//7. 验证错误信息: "该组织机构下有用户存在，请先处理用户信息再操作"
		orgPage.deleteOrg("管理层");
		this.verifyAlert(orgPage, "该组织机构下有用户存在，请先处理用户信息再操作");
		
		//8. 选择"巨蟹能源交易公司 "，点击删除后点确认按钮
		//9. 验证错误信息：“删除失败”
		this.verifyAlert(orgPage, "删除失败");
		
		//10. 在采购部下新建一个组织
		String parentOrg = "采购部";
		String newOrgName = DateUtil.getNoFromDateTime("南域采购部");
		orgPage.createOrg(parentOrg, DateUtil.getNoFromDateTime("pur"), newOrgName, 2);
		
		//11. 删除新建组织
		orgPage.refreshTree();
		orgPage.deleteOrg(new String[]{parentOrg, newOrgName});
		
		//12. 验证组织树中"南域采购部已删除"
		orgPage.refreshTree();
		orgPage.getNodeByText(parentOrg).click();
		Assert.assertNull(orgPage.getNodeByText(newOrgName), "删除未成功");
	}
	
	@Test(groups="Uid", priority=2, testName="组织机构展示",
			description="左侧显示组织机构树状结构\r\n"
						+ "在组织机构中选择某个组织时右侧显示该组织的信息\r\n"
						+ "组织结构树中以排序字段大小进行排序")
	public void Uid_OrgView() throws Exception{
		//1.访问https://tuid.hteis.cn/cas
		//2.登录售电管理员用户名和密码，并点击“登录”
		//用户名：sradmin
		//密码：sradmin
		this.loginUid(Constants.saleAdminUsr, Constants.saleAdminPwd);	
		
		//3.点击导航栏“用户管理中心”-“组织机构维护”
		PageBase homePage = new PageBase();
		homePage.openNavMenu("用户管理中心", "组织机构维护");
		
		//4.展开采购部		
		OrgViewPage orgPage = new OrgViewPage();
		orgPage.expandOrgTree();
		orgPage.getNodeByText("采购部").click();
		
		//5.选择北域采购部，验证组织机构代码显示为"srpuchasen"，设置排序为1
		orgPage.getNodeByText("北域采购部").click();
		Assert.assertEquals(orgPage.getOrgCodeInput().getText(), "srpuchasen");
		orgPage.getSortInput().setText("1");
		orgPage.getSaveButton().click();
		
		//6.选择西域采购部，验证组织机构代码显示为"srpuchasew"，设置排序为1
		orgPage.getNodeByText("西域采购部").click();
		Assert.assertEquals(orgPage.getOrgCodeInput().getText(), "srpuchasew");
		orgPage.getSortInput().setText("2");
		orgPage.getSaveButton().click();

		//7.刷新组织结构树，验证北域采购部在西域采购部前面
		orgPage.refreshTree();
		orgPage.getNodeByText("采购部").click();
		ArrayList<String> subOrgs = orgPage.getSubOrgNames("采购部");
		Assert.assertTrue(subOrgs.indexOf("北域采购部") < subOrgs.indexOf("西域采购部"), "组织排序不正确");
		
		//8. 设置北域采购部排序为2，西域采购部排序为1
		orgPage.getNodeByText("北域采购部").click();
		orgPage.getSortInput().setText("2");
		orgPage.getSaveButton().click();
		orgPage.getNodeByText("西域采购部").click();
		orgPage.getSortInput().setText("1")	;
		orgPage.getSaveButton().click();

		//9.刷新组织结构树，验证北域采购部在西域采购部前面
		orgPage.refreshTree();
		orgPage.getNodeByText("采购部").click();
		subOrgs = orgPage.getSubOrgNames("采购部");
		Assert.assertTrue(subOrgs.indexOf("北域采购部") > subOrgs.indexOf("西域采购部"), "组织排序不正确");
	}
	
	
	private void enableOrg(PageBase page, String[] path, boolean enable) throws Exception{
		page.openNavMenu("用户管理中心", "组织机构维护");
		OrgViewPage orgPage = new OrgViewPage();
		orgPage.expandOrgTree();
		for(String s : path){			
			orgPage.getNodeByText(s).click();
		}
		
		if(enable){
			orgPage.getEnableRadio().check();			
		}
		else{
			orgPage.getDisableRadio().check();
		}
		
		orgPage.getSaveButton().click();
	}
}
