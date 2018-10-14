package com.automation;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginVPNFR {
	
	public WebDriver driver;
	
	@DataProvider
	public String[][] getExcelData() throws InvalidFormatException, IOException{
		ReadExcel read = new ReadExcel();
		return read.getCellData("Testdata/loginsandpasswords.xls", "Sheet1");
				
	}
	@BeforeSuite
	        public void launchApp() throws InterruptedException {//setup to extends from base class
			
		
			//LaunchChromeWithUSVPN
			System.setProperty("webdriver.chrome.driver", "/Users/admin/Downloads/reddit_automation-master/DataDrivenAutomation/Driver/chromedriver");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			ChromeOptions options = new ChromeOptions();
			//Add PureVPN extension on browser launch
		    options.addExtensions(new File("/Users/admin/Documents/PureVPN-Free-VPN-Proxy_-Unblock-with-Privacy_v4.3.1.crx"));
		    
		    DesiredCapabilities capabilities = new DesiredCapabilities();
		    capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		    
		    ChromeDriver driver = new ChromeDriver(capabilities);
		    
		    String originalHandle = driver.getWindowHandle();
		    System.out.println("Opening extension");
		    
		    //Navigate to template.html to get pop up window rendered in web page
		    driver.get("chrome-extension://bfidboloedlamgdmenmlbipfnccokknp/ui/popup/template.html");
		    
		    //Make some manipulation to close "installation greeting message window" and switch to actual setting of extension
		    for(String handle : driver.getWindowHandles()) {
		        if (!handle.equals(originalHandle)) {
		            driver.switchTo().window(handle);
		            driver.close();
		        }
		    }

		    driver.switchTo().window(originalHandle);
		    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		    JavascriptExecutor js = (JavascriptExecutor) driver;
		    js.executeScript("window.scrollBy(0,1000)");
		    
		   driver.navigate().refresh();
		   // System.out.println("Refresh successfully");
		   
		   //Go thru selectors, login to VPN and the country
		   driver.findElement(By.cssSelector(".btn-login")).click();
		   driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		   driver.findElement(By.cssSelector(".username")).sendKeys("*****");
		   driver.findElement(By.cssSelector(".password")).sendKeys("*****");
		   driver.findElement(By.cssSelector(".button-default")).click();
		   driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
		   
		   WebElement element = driver.findElementByXPath("//*[contains(text(), 'Select Country')]");
		   JavascriptExecutor executor = (JavascriptExecutor)driver;
		   executor.executeScript("arguments[0].click();", element);
		   driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		   
		   WebElement element_2 = driver.findElementByXPath("//*[contains(text(), 'France')]");
		   JavascriptExecutor executor_2 = (JavascriptExecutor)driver;
		   executor.executeScript("arguments[0].click();", element_2);
		   
		   //Check if there is no leakage and IP has been changed
		   Thread.sleep(6000);
		   driver.get("https://whoer.net/");
		   Thread.sleep(4000);
		   driver.get("https://www.browserleaks.com/webrtc");
		   
 
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
		
		driver.get("https://old.reddit.com/");
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
