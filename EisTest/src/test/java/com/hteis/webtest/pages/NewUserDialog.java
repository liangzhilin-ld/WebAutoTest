package com.hteis.webtest.pages;

import java.util.ArrayList;

import com.hteis.webtest.selenium.*;

public class NewUserDialog extends HtmlPage {

	public HtmlCheckBox getRoleCk(String roleName) {
		for (HtmlElement element : this.findElementsByCss("form[name='userForm'] div[ng-repeat*='role in roleList']")) {
			if (element.findElementByTag("span").getText().equals(roleName) && element.isDisplayed()) {
				return element.findElementByTag("input").toHtmlCheckBox();
			}
		}

		return null;
	}

	public String[] getAllRoles() {
		ArrayList<String> roleNames = new ArrayList<String>();
		for (HtmlElement element : this.findElementsByCss("form[name='userForm'] div[ng-repeat*='role in roleList']")) {
			if (element.isDisplayed()) {
				roleNames.add(element.findElementByTag("span").getText());
			}
		}

		String[] roles = (String[]) roleNames.toArray(new String[] { "" });
		return roles;
	}

	public HtmlInput getUsrIdInput() {
		return this.findElementById("userName").toHtmlInput();
	}

	public HtmlInput getUsrNameInput() {
		return this.findElementById("cnName").toHtmlInput();
	}

	public HtmlInput getIdInput() {
		return this.findElementById("cerNo").toHtmlInput();
	}

	public HtmlInput getTelInput() {
		return this.findElementById("phone").toHtmlInput();
	}

	public HtmlInput getPhoneInput() {
		return this.findElementById("mobile").toHtmlInput();
	}

	public HtmlInput getEmailInput() {
		return this.findElementById("email").toHtmlInput();
	}

	public HtmlInput getCompanyInput() {
		return this.findElementById("comp").toHtmlInput();
	}

	public HtmlButton getSaveBtn() {
		return this.findElementByCss("div.modal-footer button:first-child").toHtmlButton();
	}

	public HtmlButton getCancelBtn() {
		return this.findElementByCss("div.modal-footer button:last-child").toHtmlButton();
	}

	public HtmlSelect getOrgSelect() {
		return this.findElementById("group").toHtmlSelect();
	}

	public void setFields(String id, String name, String phone, String email, String orgName) {
		this.getUsrIdInput().setText(id);
		this.getUsrNameInput().setText(name);
		this.getPhoneInput().setText(phone);
		this.getEmailInput().setText(email);
		this.getOrgSelect().selectByText(orgName);
	}
}
