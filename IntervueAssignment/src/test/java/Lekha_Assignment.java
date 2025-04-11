import org.testng.annotations.Test;
import org.testng.annotations.Parameters;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Lekha_Assignment extends BaseClass {

	@Test(priority = 1)
	@Parameters({ "username", "password" })
	public void testLogin(String username, String password) {

		// Login button under main Website
		driver.findElement(By.linkText("Login")).click();

		// Store the current window handle
		String mainWindowHandle = driver.getWindowHandle();

		// Get all open window handles
		Set<String> allWindowHandles = driver.getWindowHandles();

		// Loop through handles and switch to the new window
		for (String handle : allWindowHandles) {
			if (!handle.equals(mainWindowHandle)) {
				driver.switchTo().window(handle);
				break;
			}
		}
		System.out.println("New Window Title: " + driver.getTitle());

		// Login button under For Companies
		driver.findElement(By.xpath("(//div[text()='Login'])[1]")).click();
		driver.findElement(By.id("login_email")).sendKeys(username);
		driver.findElement(By.id("login_password")).sendKeys(password);
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		boolean isDashboardLoaded = wait.until(ExpectedConditions.urlContains("dashboard"));
		Assert.assertTrue(isDashboardLoaded, "Login failed-  dashboard page not found");
	}

	@Test(priority = 2, dependsOnMethods = "testLogin")
	public void search() throws InterruptedException {
		wait.until(ExpectedConditions
				.elementToBeClickable(driver.findElement(By.xpath("//button[@class='bookMeetingBtn']"))));
		Thread.sleep(3000);
		WebElement searchBtn = driver.findElement(By.xpath("//span[@class='search_placeholder']"));
		wait.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();

		WebElement searchPopUp = driver.findElement(By.xpath("//input[contains(@placeholder, 'Type what you want')]"));
		wait.until(ExpectedConditions.elementToBeClickable(searchPopUp)).sendKeys("Hello");
		driver.findElement(By.xpath("//span[text()='Hello']")).click();
		Thread.sleep(2000);
		System.out.println("After searching Hello title is : " + driver.getTitle());
	}

	@Test(priority = 3, dependsOnMethods = "testLogin")
	public void logoutUser() throws InterruptedException {
		WebElement userName = driver.findElement(By.xpath("//div[contains(@class,'ProfileHeader__UsernameWrap')]"));
		wait.until(ExpectedConditions.elementToBeClickable(userName)).click();
		Thread.sleep(1000);
		driver.findElement(By.linkText("Logout")).click();
		Thread.sleep(3000);
	}

	@AfterMethod
	public void validateTest(ITestResult result) throws IOException {
		if (ITestResult.FAILURE == result.getStatus()) {
			File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			// Save the screenshot to desired location
			FileUtils.copyFile(srcFile, new File("screenshot_" + result.getMethod().getMethodName() + ".png"));
		}
	}
}
