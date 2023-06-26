package generic;

import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Excel {
	public static String getData(String path,String sheet,int r,int c)
	{
		String value="";
		try
		{
				Workbook wb = WorkbookFactory.create(new FileInputStream(path));
				value=wb.getSheet(sheet).getRow(r).getCell(c).getStringCellValue();
				wb.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return value;
	}
	
	public static int getRowCount(String path,String sheet)
	{
		int rc=0;
		try
		{
				Workbook wb = WorkbookFactory.create(new FileInputStream(path));
				rc=wb.getSheet(sheet).getLastRowNum();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return rc;
	}
	
}
