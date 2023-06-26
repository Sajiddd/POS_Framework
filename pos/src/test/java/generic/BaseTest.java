package generic;
import java.lang.reflect.Method;
import java.net.URL;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

@Test(retryAnalyzer = Retry.class)
public class BaseTest {
	public static final String DEFAULT_URL="https://demo.actitime.com";
	public static final String DEFAULT_GRID="no";
	public static final String DEFAULT_BROWSER="chrome";
	public static final String XL_PATH="./data/input.xlsx";
	public static ExtentReports report;
	public ExtentTest extentTest;
	
	public WebDriver original_driver;
	public WebDriver driver;
	public WebDriverWait wait;

	@BeforeSuite
	public void intReport()
	{
		report=new ExtentReports();	
		ExtentSparkReporter spark = new ExtentSparkReporter("target/Spark.html");
		report.attachReporter(spark);
	}
	
	@AfterSuite
	public void generateReport()
	{
		report.flush();
	}
	
	@Parameters({"appurl","grid","browser"})
	@BeforeMethod
	public void preCondition(@Optional(DEFAULT_URL) String appURL,@Optional(DEFAULT_GRID) String grid,@Optional(DEFAULT_BROWSER) String browser,Method method) throws Exception {
		String testName=method.getName();
		extentTest = report.createTest(testName);
		if(grid.equalsIgnoreCase("no"))
		{
			extentTest.info("Execution in local system");
			if(browser.equals("chrome"))
			{
				extentTest.info("Browser is: chrome");
				original_driver=new ChromeDriver();
			}
			else
			{
				extentTest.info("Browser is: Firefox");
				original_driver=new FirefoxDriver();
			}
		}
		else
		{
			extentTest.info("Execution in remote system");
			if(browser.equals("chrome"))
			{
				extentTest.info("Browser is: chrome");
				original_driver=new RemoteWebDriver(new URL(grid), new ChromeOptions());
			}
			else
			{
				extentTest.info("Browser is: Firefox");
				original_driver=new RemoteWebDriver(new URL(grid), new FirefoxOptions());
			}
			
		}
		extentTest.info("Enter the URL:"+appURL);
		original_driver.get(appURL);
		extentTest.info("maximize the browser");
		original_driver.manage().window().maximize();
		extentTest.info("Set ITO to 5Sec");
		original_driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		extentTest.info("Set ETO to 5Sec");
		wait=new WebDriverWait(original_driver, Duration.ofSeconds(5));
		

		EventFiringDecorator<WebDriver> decorator=new EventFiringDecorator<WebDriver>(new SeleniumListener(extentTest));
		driver = decorator.decorate(original_driver);
	}
	
	@AfterMethod
	public void postCondition(ITestResult result) {
		//printing reporter.log() methods content in Extent 
		List<String> output = Reporter.getOutput(result);
		for(String s:output)
		{
			extentTest.info(s);
		}
		String testName = result.getName();
		
		int status=result.getStatus();
		if(status==2)
		{
			String timeStamp = DateTime.getTimeStamp();
			String imgPath="./images/"+testName+timeStamp+".png";
			ScreenShot.takePageScreenShotOfPage(driver,imgPath);
			String msg=result.getThrowable().getMessage();
			//attaching the existing img or screen shot in specific line to the extent report.
			imgPath="../images/"+testName+timeStamp+".png";
			extentTest.fail(msg,MediaEntityBuilder.createScreenCaptureFromPath(imgPath).build());
		}
		extentTest.info("Closing the browser");
		driver.quit();
	}
}
