package generic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTime {

	public static String getTimeStamp()
	{
		 LocalDateTime currentDateTime = LocalDateTime.now();
	     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-mm-yyyy hh-mm-ss");
	     String formattedDateTime = currentDateTime.format(formatter);
	     return formattedDateTime;
	}
}
