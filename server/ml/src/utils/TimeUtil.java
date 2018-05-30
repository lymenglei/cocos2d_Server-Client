package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	public static String getCurrentTime()
	{
		Date date = new Date();
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
}
