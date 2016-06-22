package ml.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GameTime {

	private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	private static int timeOffset = 0;
	
	
	private static int getLocalTimeOffset()
	{
		return Calendar.getInstance().get(Calendar.ZONE_OFFSET)/1000;
	}
	public static int getGMTTime(Date date)
	{
		return (int)(date.getTime()/1000);
	}
	public static int getTime(Date date)
	{
		return getGMTTime(date) + getLocalTimeOffset();
	}
	
	public static int getHourTime(int time)
	{
		return (time/3600)*3600;
	}
	
	public static int getHourTime()
	{
		return (getTime()/3600)*3600;
	}
	
	public static int getTimeH0(int time)
	{
		return time - time % 86400;
	}
	
	public static int getTimeH0()
	{
		return getTimeH0(getTime());
	}
	
	//获取当前时间戳
	public static int getTime()
	{
		return getTime(getCurServerDate());
	}
	//获取"yyyy-MM-dd HH:mm:ss"格式的当前日期
	public static String getData()
	{
		return getDateTimeStampStr(new Date());
	}
	
	private static Date getCurServerDate()
	{
		return new Date(new Date().getTime() + timeOffset * 1000);
	}
	
	public static String getDateTimeStampStr(Date date)
	{
		return dateTimeFormat.format(date);
	} 
	public static String getDateStampStr(Date date)
	{
		return dateFormat.format(date);
	}
	public static String getTimeStampStr(Date date)
	{
		return timeFormat.format(date);
	}
	
	public static Date getDateTime(int time)
	{
		return new Date((long)(time-getLocalTimeOffset())* 1000L);
	}
	
	public static String getDateTimeStampStr(int time)
	{
		return getDateTimeStampStr(getDateTime(time));
	}
}
