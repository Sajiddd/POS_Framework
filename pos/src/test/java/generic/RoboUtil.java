package generic;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class RoboUtil 
{
	public static void moveMouse(int x,int y)
	{
		try
		{
			Thread.sleep(1000);
			System.out.println("Moving mouse to:"+x+"-"+y);
			Robot r=new Robot();
			r.mouseMove(x,y);
			
		}
		catch (Exception e) 
		{
			
		}
	}
	
	public static void mouseClick()
	{
		try
		{
			Thread.sleep(1000);
			System.out.println("Clicking");
			Robot r=new Robot();
			r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			
		}
		catch (Exception e) 
		{
			
		}
	}
	
	public static void mouseClick(int x,int y)
	{
		try
		{
			moveMouse(x,y);
			mouseClick();
			
		}
		catch (Exception e) 
		{
			
		}
	}
	
	public static void rightClick()
	{
		try
		{
			Thread.sleep(1000);
			System.out.println("Right Clicking");
			Robot r=new Robot();
			r.mousePress(InputEvent.BUTTON3_DOWN_MASK);
			r.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
			
		}
		catch (Exception e) 
		{
			
		}
	}
	public static void rightClick(int x,int y)
	{
		try
		{
			moveMouse(x,y);
			rightClick();
		}
		catch (Exception e) 
		{
			
		}
	}
	
	public static void scrollDown(int numberOfScroll)
	{
		System.out.println("Scrolling down :"+numberOfScroll+" times");
		try
		{
			Robot r=new Robot();
			for(int i=1;i<=numberOfScroll;i++)
			{
				r.mouseWheel(1);
				Thread.sleep(500);
			}
		}
		catch (Exception e)
		{
			
		}
	}
	
	public static void scrollUp(int numberOfScroll)
	{
		System.out.println("Scrolling Up :"+numberOfScroll+" times");
		try
		{
			Robot r=new Robot();
			for(int i=1;i<=numberOfScroll;i++)
			{
				r.mouseWheel(-1);
				Thread.sleep(500);
			}
		}
		catch (Exception e)
		{
			
		}
	}
	
	public static void dragAndDrop(int x1,int y1,int x2,int y2)
	{
		
		moveMouse(x1,y1);
		try 
		{
			Robot r=new Robot();
			r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			Thread.sleep(1000);
			r.mouseMove(x2,y2);
			Thread.sleep(1000);
			r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		}
		catch (Exception e)
		{
			
		}
	}
	
	public static void enter(String input,boolean caps)
	{
		try 
		{
			Robot r=new Robot();

			char[] a = input.toUpperCase().toCharArray();
			if(caps)
			{
				r.keyPress(KeyEvent.VK_SHIFT);
			}
			for(char b:a)
			{
				int n=b;
				System.out.println(n);
				r.keyPress(n);
				r.keyRelease(n);
			}
			
			if(caps)
			{
				r.keyRelease(KeyEvent.VK_SHIFT);
			}
		}
		catch (Exception e) 
		{
			// TODO: handle exception
		}
	}
	
	public static void pressTab()
	{
		try 
		{
			Robot r=new Robot();
			r.keyPress(KeyEvent.VK_TAB);
			r.keyRelease(KeyEvent.VK_TAB);
		}
		catch (Exception e) 
		{
			// TODO: handle exception
		}
	}
	
	public static void pressEnter()
	{
		try 
		{
			Robot r=new Robot();
			r.keyPress(KeyEvent.VK_ENTER);
			r.keyRelease(KeyEvent.VK_ENTER);
		}
		catch (Exception e) 
		{
			// TODO: handle exception
		}
	}
	public static void pressSelectAll()
	{
		try 
		{
			Robot r=new Robot();
			r.keyPress(KeyEvent.VK_CONTROL);
			r.keyPress(KeyEvent.VK_A);
			r.keyRelease(KeyEvent.VK_A);
			r.keyRelease(KeyEvent.VK_CONTROL);
		}
		catch (Exception e) 
		{
			// TODO: handle exception
		}
	}
	
	public static void pressCopy()
	{
		try 
		{
			Robot r=new Robot();
			r.keyPress(KeyEvent.VK_CONTROL);
			r.keyPress(KeyEvent.VK_C);
			r.keyRelease(KeyEvent.VK_C);
			r.keyRelease(KeyEvent.VK_CONTROL);
		}
		catch (Exception e) 
		{
			// TODO: handle exception
		}
	}
	
	public static void pressPaste()
	{
		try 
		{
			Robot r=new Robot();
			r.keyPress(KeyEvent.VK_CONTROL);
			r.keyPress(KeyEvent.VK_V);
			r.keyRelease(KeyEvent.VK_V);
			r.keyRelease(KeyEvent.VK_CONTROL);
		}
		catch (Exception e) 
		{
			// TODO: handle exception
		}
	}
}
