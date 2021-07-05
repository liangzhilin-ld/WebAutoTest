package com.hteis.webtest.selenium;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
 
/**
 * BrowserProxy ����
 * �ο�https://blog.csdn.net/fontcolor0/article/details/103297635
 * https://www.it1352.com/761092.html
 * https://blog.csdn.net/eighttoes/article/details/86762222
 * @author ant
 * @date 2019-11-21 10:38:20
 */
public class BrowserProxy {
    /**
     * BrowserMobProxy
     */
    private BrowserMobProxy proxy;
 
    /**
     * WebDriver
     */
    private WebDriver driver;
 
 
    public BrowserMobProxy getProxy() {
        return proxy;
    }
 
    private void setProxy(BrowserMobProxy proxy) {
        this.proxy = proxy;
    }
 
    public WebDriver getDriver() {
        return driver;
    }
 
    private void setDriver(WebDriver driver) {
        this.driver = driver;
    }
 
    /**
     * �޲ι��� ��ʼ������
     * ˽�й��� ��ֹ�ⲿnew
     */
    private BrowserProxy() {
        //String webDriverPath = System.getProperty("user.dir") + "/resources/chromedriver.exe";
        String path="src/test/resources/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", path);
 
        ChromeOptions options = new ChromeOptions();
        // ������ֹ��������
        options.addArguments("--disable-popup-blocking");
        // ������ɳ��ģʽ����
        options.addArguments("no-sandbox");
        // ������չ
        options.addArguments("disable-extensions");
        // Ĭ����������
        options.addArguments("no-default-browser-check");
        //��ͷģʽ
        //options.addArguments("-headless");
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        // ���ñ���������ʾ��
        options.setExperimentalOption("prefs", prefs);
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        // set performance logger
        // this sends Network.enable to chromedriver
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        // start the proxy
        BrowserMobProxy proxy = new BrowserMobProxyServer();
        // �˿ں�
        proxy.start(4566);
 
        // get the Selenium proxy object
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
        // start the browser up
        //����1ʵ�ִ���
        //options.addArguments("--proxy-server=http://127.0.0.1:4566");
        //����2ʵ�ִ���
        //options.setCapability(ChromeOptions.CAPABILITY, options);
        options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        options.setCapability(CapabilityType.PROXY, seleniumProxy);
        WebDriver driver = new ChromeDriver(options);
      // �ȴ�10��
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
 
        // enable more detailed HAR capture, if desired (see CaptureType for the complete list)
        proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
        // enable cookies  --> REQUEST_COOKIES, RESPONSE_COOKIES
        proxy.enableHarCaptureTypes(CaptureType.getCookieCaptureTypes());
        // enable headers --> REQUEST_HEADERS, RESPONSE_HEADERS
        proxy.enableHarCaptureTypes(CaptureType.getHeaderCaptureTypes());
        // �����ֵ
        this.setProxy(proxy);
        this.setDriver(driver);
    }
 
    /**
     * �༶���ڲ��࣬Ҳ���Ǿ�̬�ĳ�Աʽ�ڲ��࣬���ڲ����ʵ�����ⲿ���ʵ��û�а󶨹�ϵ������ֻ�б����õ��Ż�װ�أ��Ӷ�ʵ�����ӳټ���
     */
    private static class BrowserProxyHolder {
        private final static BrowserProxy instance = new BrowserProxy();
    }
 
    /**
     * �ṩ���е��÷���
     *
     * @return
     */
    public static BrowserProxy getInstance() {
        return BrowserProxyHolder.instance;
    }
 
}
