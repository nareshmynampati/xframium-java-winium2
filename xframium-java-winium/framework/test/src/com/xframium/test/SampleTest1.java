package com.xframium.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SampleTest1 {

	public static void main(String[] args) throws InterruptedException {
		
		WebDriver driver;
		
		System.setProperty("webdriver.chrome.driver", "C:/Users/mnares10/Pictures/chromedriver.exe");
		driver = new ChromeDriver();
		
		driver.get("https://www.google.com/");
		Thread.sleep(12000);

		driver.quit();
	}

}
