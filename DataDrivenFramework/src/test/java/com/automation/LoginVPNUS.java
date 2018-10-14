package com.automation;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
public class LoginVPNUS {
	
	public WebDriver driver;
	
	@DataProvider
	public String[][] getExcelData() throws InvalidFormatException, IOException{
		ReadExcel read = new ReadExcel();
		return read.getCellData("Testdata/loginsandpasswords.xls", "Sheet1");
				
	}
	@BeforeSuite
	        public void launchApp(){
			
		
			//LaunchChromeWithUSA
			System.setProperty("webdriver.chrome.driver", "/Users/admin/Downloads/reddit_automation-master/DataDrivenAutomation/Driver/chromedriver");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			ChromeOptions options = new ChromeOptions();
		    options.addExtensions(new File("/Users/admin/Documents/PureVPN-Free-VPN-Proxy_-Unblock-with-Privacy_v4.3.1.crx"));

		    DesiredCapabilities capabilities = new DesiredCapabilities();
		    capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		    ChromeDriver driver = new ChromeDriver(capabilities);
		    
		    String originalHandle = driver.getWindowHandle();
		    System.out.println("Opening extension");
		    
		    //Do something to open new tabs
		    driver.get("chrome-extension://bfidboloedlamgdmenmlbipfnccokknp/ui/popup/template.html");
		    for(String handle : driver.getWindowHandles()) {
		        if (!handle.equals(originalHandle)) {
		            driver.switchTo().window(handle);
		            driver.close();
		        }
		    }

		    driver.switchTo().window(originalHandle);
		    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		    
		    
		   driver.navigate().refresh();
		   // System.out.println("Refresh successfully");
		   driver.findElement(By.cssSelector(".btn-login")).click();
		   driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		   driver.findElement(By.cssSelector(".username")).sendKeys("******");
		   driver.findElement(By.cssSelector(".password")).sendKeys("******");
		   driver.findElement(By.cssSelector(".button-default")).click();
		   driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
		   
		   WebElement element = driver.findElementByXPath("//*[contains(text(), 'Connect')]");
		   JavascriptExecutor executor = (JavascriptExecutor)driver;
		   executor.executeScript("arguments[0].click();", element);
		   
		   driver.navigate().refresh();
		   
		}
	@Test(testName="sencha",dataProvider = "getExcelData")
public void testSenchaLogin(String Username, String Password) throws InterruptedException{
		WebDriverWait wait = new WebDriverWait(driver, 30);
		driver.findElement(By.name("user")).clear();
		driver.findElement(By.name("user")).sendKeys(Username);
		driver.findElement(By.name("passwd")).clear();
		driver.findElement(By.name("passwd")).sendKeys(Password);
		driver.findElement(By.cssSelector(".btn")).click();
		Thread.sleep(2000);
		
		if(driver.getPageSource().contains("invalid user name")){
			System.out.println("Login fail");
			}else{
			System.out.println("Success");
			}
		//String InvalidLogin = driver.findElement(By.xpath("//a[contains(text(), 'Support Team')]")).getText();
		//if(InvalidLogin.contains("Support Team")){
			//System.out.println("Login Failed");
		//}
		//else{
			//wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("(//font[text()='Sencha Ext JS Overview'])"), "Sencha Ext JS Overview"));
			//Assert.assertEquals("Sencha Ext JS Overview", driver.findElement(By.xpath("//font[text()='Sencha Ext JS Overview']")).getText());
			//System.out.println("Login successfull");	
		//}
	
	}
		
	@AfterSuite
	public void closeBrowser(){
		driver.quit();
	}
}
