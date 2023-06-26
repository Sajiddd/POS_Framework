package generic;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import ru.yandex.qatools.ashot.shooting.ShootingStrategy;

public class ScreenShot {
	public static void takeElementScreenShot(WebElement element,String path)
	{
		try 
		{
			File srcFile = element.getScreenshotAs(OutputType.FILE);
			File destFile=new File(path);
			FileUtils.copyFile(srcFile, destFile);
		}
		catch (Exception e) 
		{
			System.err.println(e.getMessage());
		}
	}
	
	public static void takePageScreenShotOfPage(WebDriver driver,String path)
	{
		try 
		{
			TakesScreenshot t=(TakesScreenshot)driver;
			File srcFile = t.getScreenshotAs(OutputType.FILE);
			File destFile=new File(path);
			FileUtils.copyFile(srcFile, destFile);
		}
		catch (Exception e) 
		{
			System.err.println(e.getMessage());
		}
	}
	
	public static void takeCompletePageScreenShot(WebDriver driver,int dealyInSec,String path)
	{
		try 
		{
			AShot ashot=new AShot();
			ShootingStrategy s = ShootingStrategies.viewportPasting(dealyInSec*1000);
			Screenshot screenshot = ashot.shootingStrategy(s).takeScreenshot(driver);
			ImageIO.write(screenshot.getImage(),"png",new File(path));
		}
		catch (Exception e) 
		{
			System.err.println(e.getMessage());
		}
	}
	
	public static void takeDesktopScreenShot(String path)
	{
		try 
		{
			Robot robot=new Robot();
			Dimension desktopSize = Toolkit.getDefaultToolkit().getScreenSize();
			BufferedImage img = robot.createScreenCapture(new Rectangle(desktopSize));
			ImageIO.write(img, "png",new File(path));
		}
		catch (Exception e) 
		{
			System.err.println(e.getMessage());
		}
	}
}








