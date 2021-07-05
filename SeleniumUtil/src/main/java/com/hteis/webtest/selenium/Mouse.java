package com.hteis.webtest.selenium;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class Mouse {
	
	public static void Click(int x, int y) throws AWTException{
		Robot r = new Robot();
		r.mouseMove(x, y);
		r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		r.delay(100);
	}
}
