package generic;

import java.io.FileInputStream;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.openqa.selenium.By;

public class Util {
	public static String getProperty(String path,String key)
	{
		String value="";
		try 
		{
			Properties p=new Properties();
			p.load( new FileInputStream(path));
			value=p.getProperty(key);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return value;
	}
	public static String getTimeStamp()
	{
		 LocalDateTime currentDateTime = LocalDateTime.now();
	     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
	     String formattedDateTime = currentDateTime.format(formatter);
	     return formattedDateTime;
	}
	
	public static String getLocatorDetails(By by)
	{
		String msg="";
		try 
		{
			String FQCN = by.getClass().getCanonicalName();
			String locator=FQCN.split("By")[2];
			Field[] allF = by.getClass().getDeclaredFields();
			allF[0].setAccessible(true);
			String locatorValue = allF[0].get(by).toString();
			msg=locator+" as "+locatorValue;
		}
		catch (Exception e)
		{
		
		}
		return msg;
	}
	
	public static String[][] getDataFromCSVtoArray(String path)
	{
		String[][] data=null;
		
		try {
				FileReader f=new FileReader(path);
				CSVParser csvParser = CSVParser.parse(f,CSVFormat.DEFAULT);
				List<CSVRecord> allRecords = csvParser.getRecords();
				
				int rowCount=allRecords.size();
				int colCount=allRecords.get(0).size();
				
				data=new String[rowCount-1][colCount];
				
				for(int i=1;i<rowCount;i++)
				{
					CSVRecord record = allRecords.get(i);
					
					for(int j=0;j<colCount;j++)
					{
						String v = record.get(j);
						data[i-1][j]=v;
					}
		
				}
				csvParser.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	public static Iterator<String[]> getDataFromCSVtoIterator(String path)
	{
		ArrayList<String[]> data=new ArrayList<String[]>();
		
		try {
				FileReader f=new FileReader(path);
				CSVParser csvParser = CSVParser.parse(f,CSVFormat.DEFAULT);
				List<CSVRecord> allRecords = csvParser.getRecords();
				
				int rowCount=allRecords.size();
				int colCount=allRecords.get(0).size();

				for(int i=1;i<rowCount;i++)
				{
					CSVRecord record = allRecords.get(i);
					String[] a=new String[colCount];
					for(int j=0;j<colCount;j++)
					{
						String v = record.get(j);
						a[j]=v;
					}
		
					data.add(a);
				}
				csvParser.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		Iterator<String[]> iData = data.iterator();
		return iData;
	}
}
