package IntervueTests;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;

public class BaseClass {
	public WebDriver driver;
	WebDriverWait wait;
 
  @BeforeTest
  public void setUp() {
	  	driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://www.intervue.io/");
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
  

  @AfterTest
	public void tearDown() {
		driver.quit();
	}
}
