package com.hteis.webtest.pages.mgc;

import org.testng.Assert;

import com.hteis.webtest.common.DateUtil;
import com.hteis.webtest.entities.MgModelData;
import com.hteis.webtest.entities.UserInfo;
import com.hteis.webtest.selenium.*;

public class MgNewModelStepPage1 extends MgPageBase{
	
	/***微网中文名*/
	public HtmlInput getNameInput(){
		return this.findElementByName("mgName").toHtmlInput();
	}
	
	/***微网英文名*/
	public HtmlInput getEngNameInput(){
		return this.findElementByName("mgEnName").toHtmlInput();
	}
	
	/***负责人*/
	public HtmlNgSuggestInput getAdminInput(){
		return this.findElementById("mgManager").toHtmlNgSuggestInput();
	}
	
	/***微网成员*/
	public HtmlNgTagInput getMembersInput(){
		return this.findElementById("mgMemberArr").toHtmlNgTagInput();				
	}
	
	/***所属电网*/
	public HtmlSelect getOwnedGridSelect(){
		return this.findElementById("gridBinary").toHtmlSelect();
	}
	
	/***电压等级*/
	public HtmlSelect getVoltageLevelSelect(){
		return this.findElementByName("volLevel").toHtmlSelect();
	}
	
	/***链路数量*/
	public HtmlInput getLinkCountInput(){
		return this.findElementByName("linkCount").toHtmlInput();
	}
	
	/***间隔数量*/
	public HtmlInput getBayCountInput(){
		return this.findElementById("bayCount").toHtmlInput();
	}
	
	/***并网日期*/
	public HtmlWDatePicker getConnectTimePicker(){
		return this.findElementById("mgEnterTime").toHtmlWDatePicker();
	}
	
	/***服务截至日期*/
	public HtmlWDatePicker getServiceEndDatePicker(){
		return this.findElementById("mgServiceUptoTime").toHtmlWDatePicker();
	}
	
	/***地址*/
	public HtmlInput getAddressInput(){
		return this.findElementById("mgAddress").toHtmlInput();
	}
	/***描述*/
	public HtmlInput getDescInput(){
		return this.findElementById("mgRemark").toHtmlInput();				
	}
	
	/***总装机*/
	public HtmlInput getTotalCapacityInput(){
		return this.findElementById("mgInstalled").toHtmlInput();	
	}
	
	/***储能*/
	public HtmlInput getStorageCapacityInput(){
		return this.findElementById("mgStorage").toHtmlInput();	
	}
	
	/***光伏发电*/
	public HtmlInput getPvCapacityInput(){
		return this.findElementById("mgPvPower").toHtmlInput();	
	}
	
	/***总负荷*/
	public HtmlInput getTotalLoadInput(){
		return this.findElementById("mgAllLoad").toHtmlInput();	
	}
	
	/***风力发电*/
	public HtmlInput getWpCapacityInput(){
		return this.findElementById("mgWindPower").toHtmlInput();
	}
	
	/***清洁能源占比*/
	public HtmlInput getCleanEnergyPctInput(){
		return this.findElementById("mgCleanEnergy").toHtmlInput();
	}
	
	/***下一步*/
	public HtmlButton getNextStepBtn(){
		return this.findElementByCss(".modal-footer button:first-child").toHtmlButton();
	}
	
	/***取消*/
	public HtmlButton getCancelBtn(){
		return this.findElementByCss(".modal-footer button:last-child").toHtmlButton();
	}	
	
	public void setFieldValues(MgModelData modelData){
		this.getNameInput().input(modelData.MgName);
		this.getEngNameInput().input(modelData.MgEngName);
		this.getAdminInput().InputAndSelect(modelData.MgAdmin.loginName);
		for(UserInfo member : modelData.MgMembers){
			this.getMembersInput().InputAndSelect(member.loginName);
		}
		
		this.getOwnedGridSelect().selectByText(modelData.OwnedGrid);
		this.getVoltageLevelSelect().selectByText(modelData.VoltageLevel);
		this.getLinkCountInput().input(Integer.toString(modelData.LinkCount));
		this.getBayCountInput().input(Integer.toString(modelData.BayCount));
		this.getConnectTimePicker().input(DateUtil.getCNDateStr(modelData.ConnectDate));
		this.getServiceEndDatePicker().input(DateUtil.getCNDateStr(modelData.ServiceEndDate));
		this.getAddressInput().input(modelData.Address);
		this.getDescInput().input(modelData.Description);
		this.getTotalCapacityInput().input(Integer.toString(modelData.TotalCapacity));
		this.getStorageCapacityInput().input(Integer.toString(modelData.StorageCapacity));
		this.getPvCapacityInput().input(Integer.toString(modelData.PvCapacity));
		this.getTotalLoadInput().input(Integer.toString(modelData.TotalLoad));
		this.getWpCapacityInput().input(Integer.toString(modelData.WpCapacity));
		this.getCleanEnergyPctInput().input(Integer.toString(modelData.CleanEnergyPct));
	}
	
	public void verifyData(MgModelData modelData){
		Assert.assertEquals(this.getNameInput().getText(), modelData.getFullName());
		Assert.assertEquals(this.getEngNameInput().getText(), modelData.getFullEnName());
		Assert.assertEquals(this.getAdminInput().getText(), String.format("%s %s", modelData.MgAdmin.loginName, modelData.MgAdmin.name));	
		Assert.assertEquals(this.getMembersInput().getTags(), modelData.getDisplayMembers());
		Assert.assertEquals(this.getOwnedGridSelect().getSelectedText(), modelData.OwnedGrid);
		Assert.assertEquals(this.getVoltageLevelSelect().getSelectedText(), modelData.VoltageLevel);
		Assert.assertEquals(this.getLinkCountInput().getText(), Integer.toString(modelData.LinkCount));
		Assert.assertEquals(this.getBayCountInput().getText(), Integer.toString(modelData.BayCount));
		Assert.assertEquals(this.getConnectTimePicker().getText(), DateUtil.getCNDateStr(modelData.ConnectDate));
		Assert.assertEquals(this.getServiceEndDatePicker().getText(), DateUtil.getCNDateStr(modelData.ServiceEndDate));
		Assert.assertEquals(this.getAddressInput().getText(), modelData.Address);
		Assert.assertEquals(this.getDescInput().getText(), modelData.Description);
		Assert.assertEquals(this.getTotalCapacityInput().getText(), modelData.TotalCapacity);
		Assert.assertEquals(this.getStorageCapacityInput().getText(), Integer.toString(modelData.StorageCapacity));
		Assert.assertEquals(this.getPvCapacityInput().getText(), Integer.toString(modelData.PvCapacity));
		Assert.assertEquals(this.getWpCapacityInput().getText(), Integer.toString(modelData.WpCapacity));
		Assert.assertEquals(this.getCleanEnergyPctInput().getText(), modelData.CleanEnergyPct);
		
	}
}
