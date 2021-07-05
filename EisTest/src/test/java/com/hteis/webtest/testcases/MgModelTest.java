package com.hteis.webtest.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.hteis.webtest.business.MicroGrid;
import com.hteis.webtest.entities.*;
import com.hteis.webtest.pages.mgc.*;
import com.hteis.webtest.selenium.*;

public class MgModelTest extends TCBase {

	@Test(suiteName="mg", groups = "模型管理", priority = 1, testName = "新建监控模型-成功", 
			description = "验证输入有效数据时，监控模型可成功保存\r\n"
					+ "验证最后一步可正确添加监控指标\r\n"
					+ "验证设备自动创建成功")
	public void Mg_ModelNew01() throws Exception {
		//使用ag001/ag001访问https://tmgc.hteis.cn/
		MgcHomePage homePage = this.loginMgc(Constants.mgOperatorUsr, Constants.mgOperatorPwd);

		//打开综合监控系统->监控模型管理
		MgModelPage modelPage = homePage.openMgModelPage();

		//点击新建按钮
		modelPage.getNewBtn().click();

		//输入有效的数据，链路数设为2，间隔数设为3
		MgModelData modelData = MgModelData.createModelData();
		MgNewModelStepPage1 stepPage1 = new MgNewModelStepPage1();
		stepPage1.setFieldValues(modelData);

		//点击下一步
		stepPage1.getNextStepBtn().click();

		//验证：页面内有两个链路定义表单
		MgNewModelStepPage2 stepPage2 = new MgNewModelStepPage2();
		Assert.assertEquals(stepPage2.getLinkFormCount(), modelData.LinkCount, "提供的链路输入表单数不对");

		//输入链路数据
		stepPage2.setFieldValues(modelData.Links);

		//点击下一步
		stepPage2.getNextStepBtn().click();

		//输入设备间隔数据
		MgNewModelStepPage3 stepPage3 = new MgNewModelStepPage3();
		stepPage3.setFieldValues(modelData.Bays);

		//点击下一步
		stepPage3.getNextStepBtn().click();
		Driver.wait(1000);
		
		//验证已生成指标页面表格无数据
		MgIndexMaintainPage indexPage = new MgIndexMaintainPage();
		Assert.assertEquals(indexPage.getSelectedTable(), 0, "模型创建后已生成指标不为空");

		//点击标签页“可添加标准指标”, 任选三个非设备指标后点击添加				
		indexPage.getUnSelectedTab().click();
		//String[] indexNames = new String[] { "储能SOC", "年发电量", "累计发电量" };
		indexPage.selectIndex(modelData.indexNames);
		indexPage.getAddBtn().click();

		//点击已生成指标标签页
		indexPage.getSelectedTab().click();
		Driver.wait(1000);

		//验证三个指标在列表中
		indexPage.verifySelectedIndex(modelData);

		//选择综合监控系统->监控模型管理
		indexPage.openMgModelPage();

		//点击刚才创建的微网的编号
		String mgFullName = modelData.getFullName();
		modelPage.openMgModel(mgFullName);
		Driver.wait(1000);

		//验证第一页中的数据正确
		stepPage1.verifyData(modelData);

		//点击下一步
		stepPage1.getNextStepBtn().click();

		//验证第二页数据正确
		stepPage2.verifyData(mgFullName, modelData.Links);

		//点击下一步
		stepPage2.getNextStepBtn().click();
		
		//任选一个间隔和链路后点击下一步
		stepPage3.getForm(1).getBaySelect().selectByText(modelData.Bays[0].BayName);
		stepPage3.getForm(1).getLinkSelect().selectByText(modelData.Links[0].FullName);
		stepPage3.getNextStepBtn().click();

		//点击已选择标签，验证已添加的指标为前面步骤中添加的指标
		indexPage.verifySelectedIndex(modelData);

		//打开综合监控系统->监控设备管理
		MgDevicePage devicePage = indexPage.openMgDevicePage();
		devicePage.selectMg(modelData.getFullName());

		//验证创建的设备数目正确
		HtmlNgPager pager = devicePage.getPager();
		Assert.assertEquals(pager.getTotalCount(), modelData.getDeviceCount(), "创建的设备数目不对");

		//验证创建的设备数据正确
		pager.setNumPerPage(10);
		DeviceData device1 = new DeviceData("3#光伏组件", "3#PV-panel", "间隔1", "链路#1北区链路", MgDeviceType.PVPanel,
				modelData.MgAdmin.name, modelData.MgAdmin.phone);
		Assert.assertTrue(device1.equals(devicePage.getDeviceData(device1.Name)), device1.Name + "设备数据错误");
		
		pager.goNextPage();	
		DeviceData device2 = new DeviceData("2#蓄电池", "2#battery", "间隔2", "链路#1北区链路", MgDeviceType.Battery,
				modelData.MgAdmin.name, modelData.MgAdmin.phone);
		Assert.assertTrue(device2.equals(devicePage.getDeviceData(device2.Name)), device2.Name + "设备数据错误");
		
		pager.goNextPage();
		DeviceData device3 = new DeviceData("2#充电桩", "2#charge-pil", "间隔3", "链路#2南区链路", MgDeviceType.ChargePil,
				modelData.MgAdmin.name, modelData.MgAdmin.phone);
		Assert.assertTrue(device3.equals(devicePage.getDeviceData(device3.Name)), device3.Name + "设备数据错误");
	}
	
	@Test(suiteName="mg", groups = "模型管理", priority = 3, testName = "新建监控模型-字段验证", 
			description = "检查字段验证及错误提示")
	public void Mg_ModelNew02() throws Exception { 
		
	}
	
	@Test(suiteName="mg", groups = "模型管理", priority = 1, testName = "创建微网-成功", 
			description = "当数据有效时微网可创建成功\r\n")
	public void Mg_CreateMg01() throws Exception { 
		//使用ag001/ag001访问https://tmgc.hteis.cn/
		MgcHomePage homePage = this.loginMgc(Constants.mgOperatorUsr, Constants.mgOperatorPwd);

		//打开综合监控系统->监控系统管理
		MgSysMgmtPage mgPage = homePage.openSysMgmtPage();
		
		//点击新增按钮
		mgPage.getNewBtn().click();
		
		//在对话框中输入有效数据
		MgModelData mgData = MgModelData.createMgData();
		MgNewDialog dialog = new MgNewDialog();
		dialog.setFieldValues(mgData);
		
		//点击确定按钮
		dialog.getConfirmBtn().click();
		
		//验证新建的微网出现在表格中，验证数据正确
		mgPage.verifyMgData(mgData);		
		
		//点击刚才创建的微网的编号
		HtmlRow row = mgPage.getMgTable().findRow(MgModelPage.MgNameColNo, mgData.getFullName());
		row.getCell(MgModelPage.MgNumberColNo).click();
				
		//验证对话框中数据正确
		MgEditDialog editDialog = new MgEditDialog();
		editDialog.verifyData(mgData);
		editDialog.getDelBtn().click();	
		Driver.wait(1000);
		mgPage.ClickConfirm();
		
	}
	
	@Test(suiteName="mg", groups = "模型管理", priority = 2, testName = "创建微网-字段验证", 
			description = "检查字段验证及提示消息-监控系统管理")
	public void Mg_CreateMg02() throws Exception {
		
	}
	
	@Test(suiteName="mg", groups = "模型管理", priority = 2, testName = "编辑模型", 
			description = "验证微网编辑功能-监控模型管理\r\n"
					+ "验证在编辑界面最后一步可以添加新设备")
	public void Mg_ModelEdit() throws Exception { 
		//使用ag001/ag001访问https://tmgc.hteis.cn/
		this.loginMgc(Constants.mgOperatorUsr, Constants.mgOperatorPwd);
		
		//创建一个微网模型
		MgModelData modelData = MgModelData.createModelData();
		String mgName = modelData.getFullName();
		MicroGrid.create(modelData);
		
		//点击微网编号进入编辑界面
		MgModelPage modelPage = new MgModelPage();
		MgEditModelStepPage1 editPage1 = modelPage.openMgModel(mgName);
		
		//在第一步页面修改所有能修改的数
		modelData.updateModelData();
		editPage1.updateFieldValues(modelData);
		
		//点击下一步,修改链路数据
		editPage1.getNextStepBtn().click();
		MgEditModelStepPage2 editPage2 = new MgEditModelStepPage2();
		editPage2.setFieldValues(modelData.Links);
		
		//点击下一步,选择一个间隔和链路,输入要添加的设备数目		
		MgBayData addedBayData1 = MgBayData.createAddedBayData(modelData.Bays[0]);		
		MgBayData addedBayData2 = MgBayData.createAddedBayData(modelData.Bays[1]);
		MgEditModelStepPage3 editPage3 = new MgEditModelStepPage3();
		editPage3.updateFieldValues(new MgBayData[]{addedBayData1, addedBayData2});
		
		//点击下一步，选择可添加指标
		editPage3.getNextStepBtn().click();
		MgIndexMaintainPage indexPage = new MgIndexMaintainPage();
		indexPage.getUnSelectedTab().click();
		
		
		//任选三个非设备指标后点击添加				
		String[] indexNames = new String[] { "累计发电量", "频率", "总负荷" };
		for(String indexName : indexNames){
			modelData.indexNames.add(indexName);
		}
		indexPage.selectIndex(indexNames);
		indexPage.getAddBtn().click();

		//点击已生成指标标签页, 验证三个指标在列表中
		indexPage.verifySelectedIndex(modelData);
		
		//点击返回按钮，验证系统返回监控模型管理页面
		indexPage.getBackBtn().click();
		Driver.wait(1000);
		Assert.assertEquals(Driver.getCurrentUrl(), String.format("%sviews/home.html#/modelmanager", Constants.urlTmgc), "添加指标后返回页面错误");

		//点击刚才创建的微网的编号再次打开微网编辑页面
		modelPage.openMgModel(mgName);

		//验证第一页中的数据正确
		editPage1.verifyData(modelData);

		//点击下一步
		editPage1.getNextStepBtn().click();

		//验证第二页数据正确
		editPage2.verifyData(mgName, modelData.Links);

		//点击下一步
		editPage2.getNextStepBtn().click();
		
		//任选一个间隔和链路后点击下一步
		editPage3.getForm(1).getBaySelect().selectByText(modelData.Bays[0].BayName);
		editPage3.getForm(1).getLinkSelect().selectByText(modelData.Links[0].FullName);
		editPage3.getNextStepBtn().click();

		//点击已选择标签，验证已添加的指标为前面步骤中添加的指标
		Assert.assertEquals(indexPage.getSelectedIndex(), indexNames, "添加的指标显示错误");

		//打开综合监控系统->监控设备管理
		MgDevicePage devicePage = indexPage.openMgDevicePage();		
		devicePage.selectMg(mgName);

		//验证创建的设备数目正确
		HtmlNgPager pager = devicePage.getPager();
		Assert.assertEquals(pager.getTotalCount(), modelData.getDeviceCount(), "创建的设备数目不对");

		//验证创建的设备数据正确
		pager.setNumPerPage(10);
		pager.goNextPage();
		pager.goNextPage();
		DeviceData device1 = new DeviceData("28#光伏组件", "28#PV-panel", "间隔1", "北区链路", MgDeviceType.PVPanel,
				modelData.MgAdmin.name, modelData.MgAdmin.phone);
		Assert.assertTrue(device1.equals(devicePage.getDeviceData(device1.Name)), device1.Name + "设备数据错误");
		
		DeviceData device2 = new DeviceData("30#不间断电源", "30#UPS", "间隔1", "北区链路", MgDeviceType.UPS,
				modelData.MgAdmin.name, modelData.MgAdmin.phone);
		Assert.assertTrue(device2.equals(devicePage.getDeviceData(device2.Name)), device2.Name + "设备数据错误");
		
		pager.goNextPage();
		DeviceData device3 = new DeviceData("33#不间断电源", "33#UPS", "间隔2", "南区链路", MgDeviceType.UPS,
				modelData.MgAdmin.name, modelData.MgAdmin.phone);
		Assert.assertTrue(device3.equals(devicePage.getDeviceData(device3.Name)), device3.Name + "设备数据错误");
	}
	
	@Test(suiteName="mg", groups = "模型管理", priority = 2, testName = "编辑微网", 
			description = "验证微网编辑功能-监控系统管理")
	public void Mg_Edit() throws Exception { 
		//使用ag001/ag001访问https://tmgc.hteis.cn/
		MgcHomePage homePage = this.loginMgc(Constants.mgOperatorUsr, Constants.mgOperatorPwd);

		//打开综合监控系统->监控系统管理
		MgSysMgmtPage mgPage = homePage.openSysMgmtPage();
		
		//点击新增按钮
		mgPage.getNewBtn().click();
		
		//在对话框中输入有效数据
		MgModelData mgData1 = MgModelData.createMgData();
		MgNewDialog dialog = new MgNewDialog();
		dialog.setFieldValues(mgData1);
		
		//点击确定按钮
		dialog.getConfirmBtn().click();
		Driver.wait(1000);
		
		//点击刚才创建的微网的编号
		HtmlRow row = mgPage.getMgTable().findRow(MgModelPage.MgNameColNo, mgData1.getFullName());
		row.getCell(MgModelPage.MgNumberColNo).click();
		
		//修改能编辑的所有数据
		mgData1.updateMgData();
		MgEditDialog editDialog = new MgEditDialog();
		editDialog.setFieldValues(mgData1);
		editDialog.getSaveBtn().click();
		Driver.wait(1000);
		
		//验证表中数据已更改
		mgPage.verifyMgData(mgData1);
		
		//再次打开编辑对话框，验证数据正确
		row = mgPage.getMgTable().findRow(MgModelPage.MgNameColNo, mgData1.getFullName());
		row.getCell(MgModelPage.MgNumberColNo).click();
		editDialog.verifyData(mgData1);	
		editDialog.getDelBtn().click();	
		Driver.wait(1000);
		mgPage.ClickConfirm();
	}
	
	@Test(suiteName="mg", groups = "模型管理", priority = 3, testName = "删除微网", 
			description = "当没有设备和指标时微网可删除\r\n"
					+ "当有设备时监控模型不可删除\r\n"
					+ "当有微网指标时监控模型不可删除")
	public void Mg_ModelDel() throws Exception { 
		//使用ag001/ag001访问https://tmgc.hteis.cn/
		MgcHomePage homePage = this.loginMgc(Constants.mgOperatorUsr, Constants.mgOperatorPwd);
		
		//创建一个有一个设备的微网
		MgModelData modelData = MgModelData.createSimpleModelData();
		String mgName = modelData.getFullName();
		MicroGrid.create(modelData);
		
		//打开综合监控系统->监控系统管理
		MgSysMgmtPage mgPage = homePage.openSysMgmtPage();
		
		//点击刚才创建的微网的编号
		//点击删除按钮，点击"是"确认删除
		mgPage.deleteMg(mgName);

		//验证提示信息：删除失败!请删除设备信息后再进行此操作
		mgPage.verifyAlert("删除失败!请删除设备信息后再进行此操作");
		mgPage.closeAlert();
		
		//验证微网未从表中删除
		Assert.assertNotNull(mgPage.getMgTable().findRow(MgModelPage.MgNameColNo, mgName),"有设备的微网被删除");
		
		//打开综合监控系统->监控设备管理，选择刚创建的微网
		MgDevicePage devicePage = mgPage.openMgDevicePage();		
		devicePage.selectMg(modelData.getFullName());
		
		//删除微网的设备
		devicePage.getDeviceTable().getFirstRow().getCell(MgDevicePage.NumberColNo).click();
		MgDeviceEditDialog devDialog = new MgDeviceEditDialog();
		devDialog.getDelBtn().click();
		devicePage.ClickConfirm();
		
		//打开综合监控系统->监控系统管理
		homePage.openSysMgmtPage();
		
		//点击刚才创建的微网的编号
		//点击删除按钮，点击"是"确认删除
		mgPage.deleteMg(mgName);
		
		//验证提示消息：删除失败!请删除微网指标后再进行此操作
		mgPage.verifyAlert("删除失败!请删除微网指标后再进行此操");
		mgPage.closeAlert();
		
		//验证微网未从表中删除
		Assert.assertNotNull(mgPage.getMgTable().findRow(MgModelPage.MgNameColNo, modelData.getFullName()),"有设备的微网被删除");		
		
		//点击维护监控指标
		MgMgmtIndexPage indexPage = mgPage.openIndexPage(modelData.getFullName());
		
		//点击已选择标签，勾选全部已选指标，点击删除
		indexPage.getSelectedBtn().click();
		indexPage.getSelAllSelectedCk().check();
		indexPage.getDelBtn().click();
		
		//点击返回
		indexPage.getSelectedBackBtn().click();
		Driver.wait(1000);
		
		//再次删除微网
		mgPage.deleteMg(mgName);

		//验证提示消息：删除成功
		mgPage.verifyAlert("删除成功");	
		
		//验证：微网从列表中移除
		Assert.assertNull(mgPage.getMgTable().findRow(MgModelPage.MgNameColNo, modelData.getFullName()), "微网删除失败");
	}
}
