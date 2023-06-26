package generic;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer
{
	public int count=0;
	public boolean retry(ITestResult result) {
		//printing in Extent Report in (spark.html)
		//we have to access the Extent test variable present in base test class and re use it 
		//for this we should type caste this retry class to base test class and then access the Extent test variable
		//first we have to get the instance of retry class and then we type caste it to base test class
		Object retry = result.getInstance();
		BaseTest baseTest = (BaseTest)retry;
		baseTest.extentTest.info("we are inside retry method");
		String methodName = result.getName();
		baseTest.extentTest.info("The failed method is: "+methodName);
		if(count==0)//how many times to rerun failed
		{
			//if you want to delete the record of how much time the test is executed or skipped then add below statement.
			//baseTest.report.removeTest(methodName);
			baseTest.extentTest.skip("Skipping this test and Re-runing it");
			count++;
			return true;//true- rerun again 
		}
		else
		{
			
			baseTest.extentTest.fail("Already Re-cuted,hence stoping it");
			return false;//false - dont rerun
		}
	}

}
