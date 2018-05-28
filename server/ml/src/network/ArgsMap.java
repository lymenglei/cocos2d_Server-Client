package network;

import java.util.Map;
import java.util.HashMap;

public class ArgsMap
{	
	
	public ArgsMap(String[] args)
	{
		parse(args);
	}
	
	public boolean containsKey(String key)
	{
		return map.containsKey(key);
	}
	
	public String get(String key)
	{
		return map.get(key);
	}
	
	public String get(String key, String defaultvalue)
	{
		String v = map.get(key);
		return v == null ? defaultvalue : v;
	}
	
	public int getInt(String key, int defaultValue)
	{
		String v = map.get(key);
		if( v != null )
		{
			try
			{
				int i = Integer.parseInt(v);
				return i;
			}
			catch(Exception ex)
			{			
			}
		}
		return defaultValue;
	}
	
	public int size()
	{
		return map.size();
	}
	
	public void dump()
	{
		System.out.println("-----------dump-----------");
		for(Map.Entry<String, String> e : map.entrySet())
		{
			System.out.println("[" + e.getKey() + "] = [" + e.getValue() + "]");
		}
	}
	
	private void parse(String[] args)
	{
		for(String arg : args)
		{
			if( ! arg.startsWith("-") )
				continue;
			arg = arg.substring(1);
			int i = arg.indexOf("=");
			if( i == -1 )
				map.put(arg, "");
			else
			{
				String[] v = arg.split("=");
				String k = v[0].trim();
				if( !k.isEmpty() )
				{
					if( v[1].isEmpty() )
						map.put(k, "");
					else
						map.put(k, arg.substring(i+1).trim());
				}
			}
		}
	}
	
	public static void main(String[] args)
	{
		String[] s = {"--arg=ss", "-s", "--c", "-d", "-arg1"};
		ArgsMap am = new ArgsMap(s);
		am.dump();
	}
	
	private Map<String, String> map = new HashMap<String, String>();
}
