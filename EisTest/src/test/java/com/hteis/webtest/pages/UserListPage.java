package com.hteis.webtest.pages;

import com.hteis.webtest.selenium.*;

public class UserListPage extends PageBase {
	public static final int userIdColNo = 3;
	public static final int selectColNo = 1;

	public HtmlTable getUserTable() {
		return this.findElementByTag("table").toHtmlTable();
	}
	
	public HtmlButton getNewButton(){
		return this.findElementByCss(".container-fluid .btn-new:first-child").toHtmlButton();
	}
	
	public HtmlButton getDeleteButton(){
		return this.findElementByCss(".container-fluid .btn-new:nth-child(3)").toHtmlButton();
	}

	public HtmlButton getQueryButton(){
		return this.findElementByCss(".container-fluid .btn-new:last-child").toHtmlButton();
	}
	
	public HtmlButton getConfirmBtn() {
		return this.findElementByCss(".confirm-btn").toHtmlButton();
	}
	
	public void addRole(String userName, String roleName) {
		String roleNames[] = { roleName };
		this.addRoles(userName, roleNames);
	}

	public void addRoles(String userName, String[] roleNames) {
		this.getUserTable().findRow(userIdColNo, userName).getCell(userIdColNo).click();
		EditUserDialog dialog = new EditUserDialog();
		for (String roleName : roleNames) {
			dialog.getRoleCk(roleName).check();
		}

		dialog.getSaveBtn().click();
	}

	public void removeRole(String userName, String roleName) {
		String roleNames[] = { roleName };
		this.removeRoles(userName, roleNames);
	}

	public void removeRoles(String userName, String[] roleNames) {
		this.getUserTable().findRow(userIdColNo, userName).getCell(userIdColNo).click();
		EditUserDialog dialog = new EditUserDialog();
		for (String roleName : roleNames) {
			dialog.getRoleCk(roleName).unCheck();
		}

		dialog.getSaveBtn().click();
	}
	
	public void createUser(String id, String name, String phone, String email, String orgName){
		this.getNewButton().click();
		NewUserDialog dialog = new NewUserDialog();
		dialog.setFields(id, name, phone, email, orgName);
		dialog.getSaveBtn().click();
		Driver.wait(1000);
	}
	
	public void disableUser(String userName){
		HtmlTable userTable = this.getUserTable();
		userTable.findRow(userIdColNo, userName).getCell(userIdColNo).click();
		EditUserDialog dialog = new EditUserDialog();
		dialog.getDisableRadio().check();
		dialog.getSaveBtn().click();
		Driver.wait(1000);
	}
	
	public void deleteUser(String userName){
		HtmlTable userTable = this.getUserTable();
		userTable.findRow(userIdColNo, userName).getCell(selectColNo).findElementByTag("input").toHtmlCheckBox().check();
		this.getDeleteButton().click();		
		this.getConfirmBtn().click();
	}
}
