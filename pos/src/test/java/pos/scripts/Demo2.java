package pos.scripts;
import org.openqa.selenium.By;
import org.testng.Reporter;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import generic.BaseTest2;
public class Demo2 extends BaseTest2 {
	/* User details
	 * username:admin
	 * password:pointofsale
	 * Page:LoginPage (test case Valid Login)
	 * Script to test or verify homePage is Displayed
	 * Print the Automation Steps in Console and In Extent Report(Spark.html).
	 */
	@Test
	public void testA() throws InterruptedException
	{
		ExtentTest test = report.createTest("Test Valid Login");
		test.info("Getting the Title of the Login Page");
		//enter the UserName 
		test.info("Entering the valid UserName as: admin");
		Reporter.log("entering the username",true);
		driver.findElement(By.id("input-username")).sendKeys("admin");
		//enter the Password
		test.info("Entering the valid Password as: pointofsale");
		Reporter.log("entering the Password",true);
		driver.findElement(By.id("input-password")).sendKeys("pointofsale");
		//click on the Login button
		test.info("clicking on the Login button");
		Reporter.log("clicking on the login button",true);
		driver.findElement(By.name("login-button")).click();
		//get the title of the HomePage
		test.info("getting the HomePage Title");
		String homePageTitle = driver.getTitle();
		test.info("HomePage Title is "+homePageTitle);
		Reporter.log("title of the homePage is "+driver.getTitle(),true);
		//verifying homePage title
		Reporter.log("HomePage is Displayed so Test Valid Login is passed",true);
		test.info("HomePage is Displayed so Test Valid Login is passed ");
		test.info("Execution Over");
	}
}
