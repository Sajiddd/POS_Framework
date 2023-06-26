package pos.scripts;
import org.testng.Reporter;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import generic.BaseTest2;
public class Demo1 extends BaseTest2 {
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
		test.info("Executing testA");
		Reporter.log("Executing testA",true);
	}
}
