package com.hteis.webtest.business;

import com.hteis.webtest.entities.MgModelData;
import com.hteis.webtest.pages.*;
import com.hteis.webtest.pages.mgc.*;
import com.hteis.webtest.selenium.*;

public class MicroGrid {

	public static void create(MgModelData modelData ){		

		//打开综合监控系统->监控模型管理
		MgModelPage modelPage = new PageBase().openMgModelPage();

		//点击新建按钮
		modelPage.getNewBtn().click();

		//输入数据
		MgNewModelStepPage1 stepPage1 = new MgNewModelStepPage1();
		stepPage1.setFieldValues(modelData);

		//点击下一步
		stepPage1.getNextStepBtn().click();
		Driver.wait(1000);

		//输入链路数据
		MgNewModelStepPage2 stepPage2 = new MgNewModelStepPage2();
		stepPage2.setFieldValues(modelData.Links);

		//点击下一步
		stepPage2.getNextStepBtn().click();
		Driver.wait(1000);

		//输入设备间隔数据
		MgNewModelStepPage3 stepPage3 = new MgNewModelStepPage3();
		stepPage3.setFieldValues(modelData.Bays);

		//点击下一步
		stepPage3.getNextStepBtn().click();
		Driver.wait(1000);

		//任选三个非设备指标后点击添加
		MgIndexMaintainPage indexPage = new MgIndexMaintainPage();
		String[] indexNames = new String[] { "储能SOC", "年发电量", "累计发电量" };
		indexPage.selectIndex(indexNames);
		indexPage.getAddBtn().click();
	}
}
