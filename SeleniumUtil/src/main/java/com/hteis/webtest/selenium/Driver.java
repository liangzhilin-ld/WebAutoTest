package com.hteis.webtest.selenium;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.openqa.selenium.*;
import org.openqa.selenium.ie.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;

public class Driver {

	public static WebDriver webDriver;
	private static BrowserMobProxy webProxy;
	static {
		Config.loadConfig();
	}

	public static void create() throws Exception {
		createDriver(Config.browserType);
	}

	public static void open(String url) {

		try {
			webDriver.get(url);
		} catch (TimeoutException e2) {
			webDriver.navigate().refresh();
		}

		// webDriver.get(url);
		wait(Config.stepInterval);
	}

	public static void quit() {
		webDriver.quit();
	}

	public static void refresh(){
		webDriver.navigate().refresh();
		wait(1000);
	}

	public static void reLaunchBrowser() throws Exception {
		webDriver.quit();
		wait(1000);
		create();
	}

	public static void addCookie(String name, String value) {
		webDriver.manage().addCookie(new Cookie(name, value));
	}

	public static void addCookie(Cookie cookie) {
		webDriver.manage().addCookie(cookie);
	}

	public static Cookie getCookie(String name) {
		return webDriver.manage().getCookieNamed(name);
	}

	public static void wait(int milisecond) {
		try {
			Thread.sleep(milisecond);
		} catch (Exception ex) {
		}
	}

	public static String getCurrentUrl() {
		return webDriver.getCurrentUrl();
	}

	public static void back() {
		webDriver.navigate().back();
	}
	
	public static void scrollToTop() throws Exception{
		Keyboard.TypeHome();
		wait(1000);
	}

	public static boolean isUrlOpened(String url) {
		return isUrlOpened(url, true);
	}
	
	public static boolean isUrlOpened(String url, boolean exact) {

		String currentUrl;
		for (String handle : webDriver.getWindowHandles()) {
			webDriver.switchTo().window(handle);
			currentUrl = getCurrentUrl();
			if (currentUrl.equals(url) || (currentUrl.startsWith(url) && !exact)) {
				return true;
			}
		}

		return false;
	}

	// 使webDriver切换到当前窗口，用于打开新窗口时
	// public static void switchToCurrentWindow(){
	// String currentHandle = webDriver.getWindowHandle();
	// webDriver.switchTo().window(currentHandle);
	// }

	public static void closeWindow() {
		webDriver.close();
		wait(Config.stepInterval);
	}
	
	public static void switchToFirstWindow(){
		for (String handle : webDriver.getWindowHandles()) {
			webDriver.switchTo().window(handle);
			break;
		}
	}
	
//	public static Point getCenter(){
//		Dimension dim = webDriver.manage().window().getSize();
//		return new Point(dim.width / 2 , dim.height / 2);
//	}
//	
//	public static void ClickCenter() throws Exception{
//		Point center = getCenter();
//		Mouse.Click(center.x, center.y);
//	}
	
	public static void pressDownArrow(){
		Actions action = new Actions(webDriver);
		action.sendKeys(Keys.ARROW_DOWN).perform();
		wait(Config.stepInterval);
	}

	public static void pressEnter() {
		Actions action = new Actions(webDriver);
		action.sendKeys(Keys.ENTER).perform();
		wait(Config.stepInterval);
	}

	public static void pressEnter(HtmlElement element) {
		Actions action = new Actions(webDriver);
		action.sendKeys(element.webElement, Keys.RETURN).perform();
		wait(Config.stepInterval);
	}

	// private Function<WebDriver, Boolean> isPageLoaded() {
	// return new Function<WebDriver, Boolean>() {
	// @Override
	// public Boolean apply(WebDriver driver) {
	// return ((JavascriptExecutor) driver).executeScript("return
	// document.readyState").equals("complete");
	// }
	// };
	// }

	public static Boolean waitPageLoad() {
		WebDriverWait wait = new WebDriverWait(webDriver, Config.pageLoadTimeout);
		return wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		});
	}
	
	public static void switchToFrame(HtmlElement frame){
		webDriver.switchTo().frame(frame.webElement);
	}
	
	public static void switchBack(){
		webDriver.switchTo().defaultContent();
	}
	

	private static void createDriver(BrowserType browserType) throws Exception {
		switch (Config.browserType) {
		case IE:
			webDriver = new InternetExplorerDriver();
			break;
		case Chrome:
			System.setProperty("webdriver.chrome.driver", "c:\\windows\\chromedriver.exe");
			// 设置下载不弹出提示自动下载，设置默认下载位置
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("download.default_directory", Config.defaultDownloadFolder);
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", chromePrefs);
//			DesiredCapabilities cap = DesiredCapabilities.chrome();
//			cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//			cap.setCapability(ChromeOptions.CAPABILITY, options);
//			webDriver = new ChromeDriver(cap);
			options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			webDriver = new ChromeDriver(options);
			break;
		case FireFox:
			System.setProperty("webdriver.gecko.driver", "c:\\windows\\geckodriver.exe");
			// ProfilesIni ini = new ProfilesIni();
			FirefoxProfile profile = new FirefoxProfile();
			// FirefoxProfile profile = new FirefoxProfile(new
			// File("C:\\Users\\chenwei2\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\bboh3u34.ignoreSSLerror"));
			// FirefoxProfile profile =
			// ini.getProfile("C:\\Users\\chenwei2\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\bboh3u34.ignoreSSLerror");
			profile.setAcceptUntrustedCertificates(true);
			profile.setAssumeUntrustedCertificateIssuer(false);
			profile.setPreference("webdriver_enable_native_events",false);
			profile.setPreference("browser.download.dir", Config.defaultDownloadFolder);
			profile.setPreference("browser.helperapps.neverAsk.saveToDisk","jpeg");
//			webDriver = new FirefoxDriver(profile);
			FirefoxOptions optionsf=new FirefoxOptions();
			optionsf.setCapability(FirefoxDriver.PROFILE, profile);
			webDriver = new FirefoxDriver(optionsf);
			break;
		default:
			throw new Exception("不支持的浏览器类型：" + browserType.toString());
		}

		webDriver.manage().timeouts().implicitlyWait(Config.waitTimeout, TimeUnit.MILLISECONDS)
				.pageLoadTimeout(Config.pageLoadTimeout, TimeUnit.MILLISECONDS);

		webDriver.manage().window().maximize();
	}

	private static ChromeOptions createBrowserProxy(ChromeOptions options) {
		LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
		BrowserMobProxy proxy = new BrowserMobProxyServer();
        proxy.start(4566);
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
        options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        options.setCapability(CapabilityType.PROXY, seleniumProxy);
        webProxy=proxy;
        return options;
	}
    public static BrowserMobProxy getProxy() {
        return webProxy;
    }
    //开启手机模式
	private ChromeOptions setOptions(String deviceName) {
		ChromeOptions options = new ChromeOptions();
		Map<String, Object> mobileEmulation = new HashMap<String, Object>();
		mobileEmulation.put("deviceName", deviceName);
		options.setExperimentalOption("mobileEmulation", mobileEmulation);
		options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
		options.addArguments("--auto-open-devtools-for-tabs");
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
	    return options;
	}
}
