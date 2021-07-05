package com.hteis.webtest.pages.cust;

import com.hteis.webtest.entities.CustServiceData;
import com.hteis.webtest.selenium.*;

public class CustServiceEditDialog extends HtmlPage {
	
	public HtmlElement getDialog() {
		return this.findElementByClass("ngdialog");
	}

	/*** 客户名称 */
	public HtmlInput getCustNameInput() {
		return this.getDialog().findElementById("custName").toHtmlInput();
	}

	/*** 单位名称（非注册用户） */
	public HtmlInput getCompanyInput() {
		return this.getDialog().findElementById("enterName").toHtmlInput();
	}

	/*** 单位名称（注册用户） */
	public HtmlNgSuggestInput getRegCompanyInput() {
		return this.getDialog().findElementByTag("angucomplete").toHtmlNgSuggestInput();
	}

	/*** 用户类型 */
	public HtmlSelect getUserTypeSelect() {
		return this.getDialog().findElementByName("userType").toHtmlSelect();
	}

	/*** 问题分类 */
	public HtmlSelect getIssueTypeSelect() {
		return this.getDialog().findElementByName("classification").toHtmlSelect();
	}

	/*** 问题描述 */
	public HtmlInput getIssueInput() {
		return this.getDialog().findElementById("description").toHtmlInput();
	}

	/*** 手机号码 */
	public HtmlInput getPhoneInput() {
		return this.getDialog().findElementById("mobile").toHtmlInput();
	}

	/*** 处理状态 */
	public HtmlSelect getStatusSelect() {
		return this.getDialog().findElementByName("handleStatus").toHtmlSelect();
	}

	/*** 处理结果 */
	public HtmlInput getResultInput() {
		return this.getDialog().findElementById("handleResult").toHtmlInput();
	}

	/*** 保存按钮 */
	public HtmlButton getSaveBtn() {
		return this.getDialog().findElementByCss(".modal-footer button:last-child").toHtmlButton();
	}

	/*** 关闭按钮 */
	public HtmlButton getCloseBtn() {
		return this.getDialog().findElementByCss(".modal-footer button:first-child").toHtmlButton();
	}

	public void setFieldValues(CustServiceData data) {
		this.getCustNameInput().setText(data.CustName);
//		this.getUserTypeSelect().selectByText(data.UserType);
//		if (data.UserType.equals("注册用户")) {
//			this.getRegCompanyInput().clear();
//			this.getRegCompanyInput().InputAndSelect(data.Company);
//		} else {
//			this.getCompanyInput().setText(data.Company);
//		}

		this.getIssueTypeSelect().selectByText(data.IssueType);
		this.getIssueInput().setText(data.Description);
		this.getPhoneInput().setText(data.Phone);
		this.getStatusSelect().selectByText(data.Status);
		if (data.ProcessResult != null) {
			this.getResultInput().setText(data.ProcessResult);
		}
	}
}
