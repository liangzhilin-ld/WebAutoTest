package com.hteis.webtest.pages.mgc;

import org.testng.Assert;

import com.hteis.webtest.common.DateUtil;
import com.hteis.webtest.entities.*;
import com.hteis.webtest.selenium.*;

public class MgEditDialog extends HtmlPage {

	
	public MgEditDialog(){
	}
	
	public HtmlElement getContainer(){
		return this.findElementByClass("ngdialog-content");
	}
	
	/***微网中文名*/
	public HtmlInput getNameInput(){
		return this.getContainer().findElementByName("mgName").toHtmlInput();
	}
	
	/***微网英文名*/
	public HtmlInput getEngNameInput(){
		return this.getContainer().findElementByName("mgEnName").toHtmlInput();
	}
	
	/***负责人*/
	public HtmlNgSuggestInput getAdminInput(){
		return this.getContainer().findElementById("mgManager").toHtmlNgSuggestInput();
	}
	
	/***微网成员*/
	public HtmlNgTagInput getMembersInput(){
		return this.getContainer().findElementByTag("tags-input").toHtmlNgTagInput();			
	}
	
	/***所属电网*/
	public HtmlSelect getOwnedGridSelect(){
		return this.getContainer().findElementByName("gridBinary").toHtmlSelect();
	}
	
	/***电压等级*/
	public HtmlSelect getVoltageLevelSelect(){
		return this.getContainer().findElementByName("volLevel").toHtmlSelect();
	}
	
	/***并网日期*/
	public HtmlWDatePicker getConnectTimePicker(){
		return this.getContainer().findElementById("mgEnterTime").toHtmlWDatePicker();
	}
	
	/***服务截至日期*/
	public HtmlWDatePicker getServiceEndDatePicker(){
		return this.getContainer().findElementById("mgServiceUptoTime").toHtmlWDatePicker();
	}
	
	/***地址*/
	public HtmlInput getAddressInput(){
		return this.getContainer().findElementById("mgAddress").toHtmlInput();
	}
	/***描述*/
	public HtmlInput getDescInput(){
		return this.getContainer().findElementById("mgRemark").toHtmlInput();				
	}
	
	/***总装机*/
	public HtmlInput getTotalCapacityInput(){
		return this.getContainer().findElementById("mgInstalled").toHtmlInput();	
	}
	
	/***储能*/
	public HtmlInput getStorageCapacityInput(){
		return this.getContainer().findElementById("mgStorage").toHtmlInput();	
	}
	
	/***光伏发电*/
	public HtmlInput getPvCapacityInput(){
		return this.getContainer().findElementById("mgPvPower").toHtmlInput();	
	}
	
	/***总负荷*/
	public HtmlInput getTotalLoadInput(){
		return this.getContainer().findElementById("mgAllLoad").toHtmlInput();	
	}
	
	/***风力发电*/
	public HtmlInput getWpCapacityInput(){
		return this.getContainer().findElementById("mgWindPower").toHtmlInput();
	}
	
	/***清洁能源占比*/
	public HtmlInput getCleanEnergyPctInput(){
		return this.getContainer().findElementById("mgCleanEnergy").toHtmlInput();
	}
	
	/***联系电话*/
	public HtmlInput getPhoneInput(){
		return this.getContainer().findElementById("userMobile").toHtmlInput();
	}
	
	/***导出按钮*/
	public HtmlButton getExportBtn(){
		return this.findElementByCss(".modal-footer > button:last-child").toHtmlButton();
	}
	
	/***保存按钮*/
	public HtmlButton getSaveBtn(){
		return this.findElementByCss(".modal-footer > button:first-child").toHtmlButton(); 
	}
	
	/***删除按钮*/
	public HtmlButton getDelBtn(){
		return this.findElementByCss(".modal-footer > button:nth-child(2)").toHtmlButton(); 
	}
	
	/***关闭按钮X*/
	public HtmlElement getCancelBtn(){
		return this.findElementByCss(".modal-header > img");
	}
	
	public void setFieldValues(MgModelData modelData){
		this.getAdminInput().clear();
		this.getAdminInput().InputAndSelect(modelData.MgAdmin.loginName);
		this.getMembersInput().clear();
		for(UserInfo member : modelData.MgMembers){
			this.getMembersInput().InputAndSelect(member.loginName);
		}
		
		this.getConnectTimePicker().setText(DateUtil.getCNDateStr(modelData.ConnectDate));
		this.getServiceEndDatePicker().setText(DateUtil.getCNDateStr(modelData.ServiceEndDate));
		this.getAddressInput().setText(modelData.Address);
		this.getDescInput().setText(modelData.Description);
		this.getTotalCapacityInput().setText(Integer.toString(modelData.TotalCapacity));
		this.getStorageCapacityInput().setText(Integer.toString(modelData.StorageCapacity));
		this.getPvCapacityInput().setText(Integer.toString(modelData.PvCapacity));
		this.getTotalLoadInput().setText(Integer.toString(modelData.TotalLoad));
		this.getWpCapacityInput().setText(Integer.toString(modelData.WpCapacity));
		this.getCleanEnergyPctInput().setText(Integer.toString(modelData.CleanEnergyPct));
	}
	
	public void verifyData(MgModelData modelData){
		Assert.assertEquals(this.getNameInput().getText(), modelData.getFullName());
		Assert.assertEquals(this.getEngNameInput().getText(), modelData.getFullEnName());
		Assert.assertEquals(this.getAdminInput().getText(), String.format("%s %s", modelData.MgAdmin.loginName, modelData.MgAdmin.name));	
		Assert.assertEquals(this.getMembersInput().getTags(), modelData.getDisplayMembers());
		Assert.assertEquals(this.getOwnedGridSelect().getSelectedText(), modelData.OwnedGrid);
		Assert.assertEquals(this.getVoltageLevelSelect().getSelectedText(), modelData.VoltageLevel);
		Assert.assertEquals(this.getConnectTimePicker().getText(), DateUtil.getCNDateStr(modelData.ConnectDate));
		Assert.assertEquals(this.getServiceEndDatePicker().getText(), DateUtil.getCNDateStr(modelData.ServiceEndDate));
		Assert.assertEquals(this.getAddressInput().getText(), modelData.Address);
		Assert.assertEquals(this.getDescInput().getText(), modelData.Description);
		Assert.assertEquals(this.getTotalCapacityInput().getText(), modelData.TotalCapacity);
		Assert.assertEquals(this.getStorageCapacityInput().getText(), Integer.toString(modelData.StorageCapacity));
		Assert.assertEquals(this.getPvCapacityInput().getText(), Integer.toString(modelData.PvCapacity));
		Assert.assertEquals(this.getWpCapacityInput().getText(), Integer.toString(modelData.WpCapacity));
		Assert.assertEquals(this.getCleanEnergyPctInput().getText(), modelData.CleanEnergyPct);
		Assert.assertEquals(this.getPhoneInput().getText(), modelData.MgAdmin.phone);
		
	}
}
