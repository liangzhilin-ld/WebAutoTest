package com.hteis.webtest.pages;

import java.util.ArrayList;

import com.hteis.webtest.selenium.*;

public class OrgViewPage extends PageBase {

	public HtmlButton getNewButton() {
		return this.findElementByCss(".container-fluid button.btn-new:first-child").toHtmlButton();
	}

	public HtmlButton getSaveButton() {
		return this.findElementByCss(".container-fluid button.btn-new:nth-child(2)").toHtmlButton();
	}

	public HtmlButton getDelButton() {
		return this.findElementByCss(".container-fluid button.btn-new:last-child").toHtmlButton();
	}

	public HtmlInput getCompanyNameInput() {
		return this.findElementById("compId").toHtmlInput();
	}

	public HtmlInput getOrgCodeInput() {
		return this.findElementById("groupCode").toHtmlInput();
	}

	public HtmlInput getOrgNameInput() {
		return this.findElementById("groupName").toHtmlInput();
	}

	public HtmlInput getParentInput() {
		return this.findElementById("paname").toHtmlInput();
	}

	public HtmlInput getSortInput() {
		return this.findElementById("sort").toHtmlInput();
	}

	public HtmlElement getCompanyNode() {
		return this.findElementByClass("tree-node-3");
	}

	public HtmlElement getNodeByText(String nodeText) {
		for (HtmlElement element : this.findElementsByCss(".container-fluid li[ui-tree-node]")) {
			if (nodeText.equals(element.findElementByTag("span").getText())) {
				return element;
			}
		}

		return null;
	}

	public HtmlButton getConfirmBtn() {
		return this.findElementByCss(".confirm-btn").toHtmlButton();
	}

	public HtmlElement getOrgCodeReqErrSpan() {
		return this.findElementByCss("span[ng-show='groupForm.groupCode.$error.required']");
	}

	public HtmlElement getOrgNameReqErrSpan() {
		return this.findElementByCss("span[ng-show='groupForm.groupName.$error.required']");
	}

	public HtmlRadioBox getEnableRadio() {
		return this.findElementById("optionsRadios1").toHtmlRadioBox();
	}

	public HtmlRadioBox getDisableRadio() {
		return this.findElementById("optionsRadios2").toHtmlRadioBox();
	}

	public void expandOrgTree() throws Exception {
		this.getCompanyNode().click();
		Thread.sleep(1000);
	}
	
	public void refreshTree() throws Exception{
		this.getCompanyNode().click();
		this.expandOrgTree();
	}

	/*** 创建组织，假设根节点已展开 */
	public void createOrg(String[] parentPath, String orgCode, String orgName, int sortNo) {
		for (String s : parentPath) {
			this.getNodeByText(s).click();
		}

		this.getNewButton().click();
		this.setOrgFields(orgCode, orgName, sortNo);
		this.getSaveButton().click();
	}

	public void createOrg(String parentNode, String orgCode, String orgName, int sortNo) {
		if (parentNode == null) {
			this.getNewButton().click();
			this.setOrgFields(orgCode, orgName, sortNo);
			this.getSaveButton().click();
		} else {
			String[] parentPath = { parentNode };
			this.createOrg(parentPath, orgCode, orgName, sortNo);
		}
	}

	public void setOrgFields(String orgCode, String orgName, int sortNo) {
		this.getOrgCodeInput().setText(orgCode);
		this.getOrgNameInput().setText(orgName);
		this.getSortInput().setText(Integer.toString(sortNo));
	}

	public void deleteOrg(String orgName) throws Exception {
		String[] orgPath = { orgName };
		this.deleteOrg(orgPath);
	}

	public void deleteOrg(String[] orgPath) throws Exception {
		for (String s : orgPath) {
			this.getNodeByText(s).click();
		}
		Thread.sleep(1000);
		this.getDelButton().click();
		this.getConfirmBtn().click();
	}
	
	public ArrayList<String> getSubOrgNames(String parentOrgName){
		ArrayList<String> subOrgs = new ArrayList<String>();
		for(HtmlElement element : this.getNodeByText(parentOrgName).findElementByTag("ol").findElementsByTag("li")){
			subOrgs.add(element.getText());	
		}
		
		return subOrgs;
	}
}
