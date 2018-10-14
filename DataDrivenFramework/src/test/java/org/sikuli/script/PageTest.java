package org.sikuli.script;
import java.io.File; 
import java.util.Set; 

import org.openqa.selenium.WebDriver; 
import org.openqa.selenium.chrome.ChromeDriver; 
import org.openqa.selenium.chrome.ChromeOptions; 
import org.openqa.selenium.support.ui.ExpectedConditions; 
import org.openqa.selenium.support.ui.WebDriverWait; 
import org.sikuli.script.FindFailed; 
import org.sikuli.script.Screen;
import org.testng.annotations.Test; 

@Test
public class PageTest { 
	
    public static void main(String[] args) { 
     // Opening chrome with that addon 
     ChromeOptions options = new ChromeOptions(); 
     options.addExtensions(new File("/Users/admin/Documents/PureVPN-Free-VPN-Proxy_-Unblock-with-Privacy_v4.3.1.crx"));  
     System.setProperty("webdriver.chrome.driver", "/Users/admin/Downloads/reddit_automation-master/DataDrivenAutomation/Driver/chromedriver"); 
     WebDriver driver = new ChromeDriver(options); 
     driver.manage().window().maximize(); 

     // Creating object to the Sukali screen class 
     Screen s=new Screen(); 

     //Finding and clicking on the Addon image 
     try { 
      s.find("/Users/admin/Downloads/reddit_automation-master/DataDrivenAutomation/Driver/AddonIcon.png"); 
      s.click("/Users/admin/Downloads/reddit_automation-master/DataDrivenAutomation/Driver/AddonIcon.png"); 
     } catch (FindFailed e) {    
      e.printStackTrace(); 
     } 

     //Wait until new Addon popup is opened. 
     WebDriverWait wait = new WebDriverWait(driver, 5);  
     wait.until(ExpectedConditions.numberOfWindowsToBe(2)); 

     // Switch to the Addon Pop up 
     String parentWindow= driver.getWindowHandle(); 
     Set<String> allWindows = driver.getWindowHandles(); 
     for(String curWindow : allWindows){    
      if(!parentWindow.equals(curWindow)){ 
      driver.switchTo().window(curWindow); 
      } 
     } 

     /***********Ur code to work on Add-on popup************************/ 
    } 
}