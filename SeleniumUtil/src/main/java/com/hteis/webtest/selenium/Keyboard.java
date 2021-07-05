package com.hteis.webtest.selenium;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;

public class Keyboard {

	public static void TypeString(String str) throws Exception {
		Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();// 获取剪切板
		Transferable tText = new StringSelection(str);

		clip.setContents(tText, null); // 设置剪切板内容
		Robot r = new Robot();
		TypeKeyWithCtrl(KeyEvent.VK_V);
		r.delay(100);
	}

	public static void TypeKeyWithCtrl(int key) throws Exception {
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(key);
		r.keyRelease(key);
		r.keyRelease(KeyEvent.VK_CONTROL);
		r.delay(100);
	}
	
	public static void TypeEnter() throws Exception{
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
		r.delay(100);
	}
	
	public static void TypeHome() throws Exception{
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_HOME);
		r.keyRelease(KeyEvent.VK_HOME);
		r.delay(100);
	}
}
