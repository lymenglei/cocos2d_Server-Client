package network;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Utils {
	
	public static String getCurrentTime()
	{
		Date date = new Date();
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	
//	private static Logger logger = Logger.getLogger(Utils.class);
	public static void log(String s)
	{
		logImpl(s);
	}
	
	public static void log(int b)
	{
		logImpl(b + "");
	}
	
	public static void log(boolean b)
	{
		logImpl(b ? "true" : "false");
	}
	
	public static void warn(String s)
	{
		String str = "[WARN] " + getCurrentTime() + " " + s;
		System.out.println(str);
	}
	
	public static void error(String s)
	{
		String str = "[ERROR]" + getCurrentTime() + " " + s;
		System.err.println(str);
	}
	
	/////////////////////////////////////
	private static void logImpl(String s)
	{
		String str = "[INFO] " + getCurrentTime() + " " + s;
		System.out.println(str);
	}
}
