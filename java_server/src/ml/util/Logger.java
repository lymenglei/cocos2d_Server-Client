package ml.util;

/**
 * @author menglei
 *
 */
public class Logger {
	public Logger(String name)
	{
		this.name = name;
		System.out.println("Logger: set Logger name as [" + name + "]");
	}
	
	public String getName()
	{
		return name;
	}
	
	public void info(String info)
	{
		String out = "INFO  " + GameTime.getData() + " " + info ;
		System.out.println(out);
	}
	
	public void warn(String info)
	{
		String out = "WARN  " + GameTime.getData() + " " + info ;
		System.out.println(out);
	}
	
	public void error(String info)
	{
		String out = "ERROR " + GameTime.getData() + " " + info ;
		System.out.println(out);
	}
	
	private void logOutFile(String fileName, String info)
	{
		//TODO write log info to file
	}
	
	private String name;
}
