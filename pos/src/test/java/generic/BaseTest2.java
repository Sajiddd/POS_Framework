package generic;
import java.lang.reflect.Method;
import java.net.URL;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
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
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
/*
 * receiving parameters gridURl,appURL,pptFile(path of the property file),ITO,ETO,Browser,
 * from jenkins because Execution starts from jenkins,
 * devops team decides which and all information has to pass from jenkins 
 * 
 * specifying these parameters in Property file qa.properties
 *   
 */
@Listeners(TestListener.class)
public class BaseTest2
{
	public static final String DEFAULT_APPURL="https://student1.aksharatraining.in/pos/public/#";
	public static final String DEFAULT_GRID="no";
	public static final String DEFAULT_PPT="qa.properties";
	public static String XLPATH;
	public static final String DEFAULT_HTMLPATH="./target/Spark.html";
	public static ExtentReports report;
	public ExtentTest extentTest;
	public WebDriver driver;
	public WebDriverWait wait;

	@Parameters({"htmlPath"})
	@BeforeSuite
	public void initReport(@Optional(DEFAULT_HTMLPATH) String htmlPath)
	{
		report=new ExtentReports();	
		ExtentSparkReporter spark = new ExtentSparkReporter(htmlPath);
		report.attachReporter(spark);
	}
	
	@AfterSuite
	public void generateReport()
	{
		report.flush();
	}
	
	@Parameters({"appurl","grid","pptfile"})
	@BeforeMethod
	public void preCondition(@Optional(DEFAULT_APPURL) String appURL,@Optional(DEFAULT_GRID) String grid,@Optional(DEFAULT_PPT) String pptFile,Method method) throws Exception
	{
		
		String testName=method.getName();
		extentTest = report.createTest(testName);
		
		//reading the data from the property file browser name 
		String browser = Util.getProperty(pptFile,"browser");
		extentTest.info("browser is "+browser);
		System.out.println(browser);
		
		//reading the data from the property file xlPath
		XLPATH= Util.getProperty(pptFile,"XLPATH");
		extentTest.info("DEFAULT_XLPATH is "+XLPATH);
		/*
		 * ITO=5 in property file numbers are not considered as int 
		 * java considers it as String 
		 * and the methods ITO and ETO accepts Long data type values 
		 * so we have to convert the String values into Long values 
		 * and then pass this as arguments to ITO and ETO. 
		 */
		//reading the data from the property file ImplicitTimeout
		String sITO = Util.getProperty(DEFAULT_PPT,"ITO");
		//reading the data from the property file ExplicitTimeout
		String sETO = Util.getProperty(DEFAULT_PPT,"ETO");
		//converting String type to Long type. using wrapper class
		long lITO = Long.parseLong(sITO);//LongITO
		extentTest.info("ImplicitTimeout is "+lITO);
		long lETO = Long.parseLong(sETO);////LongETO
		extentTest.info("ExplicitTimeout is "+lETO);
				
		if(grid.equalsIgnoreCase("no"))
		{
			extentTest.info("Execution in local system");
			if(browser.equals("chrome"))
			{
				extentTest.info("Browser is: chrome");
				driver=new ChromeDriver();
			}
			else
			{
				extentTest.info("Browser is: Firefox");
				driver=new FirefoxDriver();
			}
		}
		else
		{
			extentTest.info("Execution in remote system");
			if(browser.equals("chrome"))
			{
				extentTest.info("Browser is: chrome");
				driver=new RemoteWebDriver(new URL(grid), new ChromeOptions());
			}
			else
			{
				extentTest.info("Browser is: Firefox");
				driver=new RemoteWebDriver(new URL(grid), new FirefoxOptions());
			}
			
		}
		extentTest.info("Entering the URL:"+appURL);
		driver.get(appURL);
		extentTest.info("maximize the browser");
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(lITO));
		extentTest.info("Set ITO to "+lITO);
		
		wait=new WebDriverWait(driver, Duration.ofSeconds(lETO));
		extentTest.info("Set ETO to "+lETO);
	}
	
	@AfterMethod
	public void postCondition(ITestResult result) {
		
		String testName = result.getName();
		
		int status=result.getStatus();
		if(status==2)
		{
			String timeStamp = DateTime.getTimeStamp();
			String imgPath="./images/"+testName+timeStamp+".png";
			ScreenShot.takePageScreenShotOfPage(driver,imgPath);
			String msg=result.getThrowable().getMessage();
			imgPath="../images/"+testName+timeStamp+".png";
			extentTest.fail(msg,MediaEntityBuilder.createScreenCaptureFromPath(imgPath).build());
		}
		extentTest.info("Closing the browser");
		driver.quit();
	}
}

