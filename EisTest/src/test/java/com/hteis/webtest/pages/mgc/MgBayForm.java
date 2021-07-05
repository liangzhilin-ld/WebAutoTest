package com.hteis.webtest.pages.mgc;

import com.hteis.webtest.entities.MgBayData;
import com.hteis.webtest.entities.MgDeviceType;
import com.hteis.webtest.selenium.*;

public class MgBayForm extends HtmlPage {

	public HtmlElement container;
	
	public MgBayForm(HtmlElement div){
		this.container = div;
	}
	
	public HtmlSelect getBaySelect(){
		return this.container.findElementByCss("select[name^='bay']").toHtmlSelect();
	}
	
	public HtmlSelect getLinkSelect(){
		return this.container.findElementByCss("select[name^='link']").toHtmlSelect();
	}
	
	public HtmlInput getDeviceNoInput(MgDeviceType deviceType){
		for(HtmlElement div : this.container.findElementsByCss("div[ng-repeat='eqType in eq.eqTypeList']")){
			if(div.findElementByTag("p").getText().equals(deviceType.getName() + ":")){
				return div.findElementByTag("input").toHtmlInput();
			}
		}
		
		return null;
	}
	
	public void setFieldValues(MgBayData data){
		this.getBaySelect().selectByText(data.BayName);
		this.getLinkSelect().selectByText(data.LinkName);
		this.getDeviceNoInput(MgDeviceType.PVPanel).input(Integer.toString(data.PVPanelNo));
		this.getDeviceNoInput(MgDeviceType.PVArray).input(Integer.toString(data.PVArrayNo));
		this.getDeviceNoInput(MgDeviceType.PVInverter).input(Integer.toString(data.PVInverterNo));
		this.getDeviceNoInput(MgDeviceType.WindFarm).input(Integer.toString(data.WindFarmNo));
		this.getDeviceNoInput(MgDeviceType.WindTurbine).input(Integer.toString(data.WindTurbineNo));
		this.getDeviceNoInput(MgDeviceType.WindInverter).input(Integer.toString(data.WindInverterNo));
		this.getDeviceNoInput(MgDeviceType.Battery).input(Integer.toString(data.BatteryNo));
		this.getDeviceNoInput(MgDeviceType.ChargePil).input(Integer.toString(data.ChargePilNo));
		this.getDeviceNoInput(MgDeviceType.PCS).input(Integer.toString(data.PCSNo));
	}
}
