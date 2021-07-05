package com.hteis.webtest.pages.mgc;

import com.hteis.webtest.entities.MgLinkType;

import org.testng.*;
import com.hteis.webtest.entities.MgLinkData;
import com.hteis.webtest.selenium.*;

public class MgLinkForm {

	private HtmlElement container;

	public MgLinkForm(HtmlElement div) {
		this.container = div;
	}

	/*** 所属微网 */
	public HtmlInput getMGNameInput() {
		return this.container.findElementByCss("input[name^='mgName']").toHtmlInput();
	}

	/*** 中文名称 */
	public HtmlInput getLinkNameInput() {
		return this.container.findElementByCss("input[name^='linkName']").toHtmlInput();
	}

	/*** 英文名称 */
	public HtmlInput getLinkEnNameInput() {
		return this.container.findElementByCss("input[name^='linkEnName']").toHtmlInput();
	}

	/*** 链路类型 */
	public HtmlSelect getLinkTypeSelect() {
		return this.container.findElementByTag("select").toHtmlSelect();
	}

	/*** 链路端口 */
	public HtmlInput getLinkPortInput() {
		return this.container.findElementByCss("input[name^='linkPort']").toHtmlInput();
	}

	/*** 链路地址 */
	public HtmlInput getLinkAddressInput() {
		return this.container.findElementByCss("input[name^='linkAddr']").toHtmlInput();
	}

	/*** 串口名 */
	public HtmlInput getComNameInput() {
		return this.container.findElementByCss("input[name^='serialPort']").toHtmlInput();
	}

	/*** 波特率 */
	public HtmlInput getBaudRateInput() {
		return this.container.findElementByCss("input[name^='baudRate']").toHtmlInput();
	}

	/*** 数据位 */
	public HtmlInput getDataBitsInput() {
		return this.container.findElementByCss("input[name^='dataBit']").toHtmlInput();
	}

	/*** 校验位 */
	public HtmlInput getCheckBitsInput() {
		return this.container.findElementByCss("input[name^='checkBit']").toHtmlInput();
	}

	/*** 停止位 */
	public HtmlInput getStopBitsInput() {
		return this.container.findElementByCss("input[name^='stopBit']").toHtmlInput();
	}

	public void setFieldValues(MgLinkData data) {
		this.getLinkNameInput().input(data.Name);
		this.getLinkEnNameInput().input(data.EnName);
		this.getLinkTypeSelect().selectByText(data.Type.toString());
		if (data.Type == MgLinkType.LinkModBusRTU) {
			this.getComNameInput().input(data.ComName);
			this.getBaudRateInput().input(Integer.toString(data.BaudRate));
			this.getDataBitsInput().input(Integer.toString(data.DataBits));
			this.getCheckBitsInput().input(Integer.toString(data.CheckBits));
			this.getStopBitsInput().input(Integer.toString(data.StopBits));
		} else {
			this.getLinkAddressInput().input(data.Address);
			this.getLinkPortInput().input(data.Port);
		}
	}
	
	public void updateFieldValues(MgLinkData data){
		if (data.Type == MgLinkType.LinkModBusRTU) {
			this.getComNameInput().setText(data.ComName);
			this.getBaudRateInput().setText(Integer.toString(data.BaudRate));
			this.getDataBitsInput().setText(Integer.toString(data.DataBits));
			this.getCheckBitsInput().setText(Integer.toString(data.CheckBits));
			this.getStopBitsInput().setText(Integer.toString(data.StopBits));
		} else {
			this.getLinkAddressInput().setText(data.Address);
			this.getLinkPortInput().setText(data.Port);
		}
	}

	public void verifyData(String mgName, MgLinkData data) {
		Assert.assertEquals(this.getMGNameInput().getText(), mgName);
		Assert.assertEquals(this.getLinkNameInput().getText(), data.FullName);
		Assert.assertEquals(this.getLinkEnNameInput().getText(), data.FullEnName);
		Assert.assertEquals(this.getLinkTypeSelect().getSelectedText(), data.Type.toString());
		if (data.Type == MgLinkType.LinkModBusRTU) {
			Assert.assertEquals(this.getComNameInput().getText(), data.ComName);
			Assert.assertEquals(this.getBaudRateInput().getText(), Integer.toString(data.BaudRate));
			Assert.assertEquals(this.getDataBitsInput().getText(), Integer.toString(data.DataBits));
			Assert.assertEquals(this.getCheckBitsInput().getText(), Integer.toString(data.CheckBits));
			Assert.assertEquals(this.getStopBitsInput().getText(), Integer.toString(data.StopBits));
		} else {
			Assert.assertEquals(this.getLinkPortInput().getText(), data.Port);
			Assert.assertEquals(this.getLinkAddressInput().getText(), data.Address);
		}
	}

}
