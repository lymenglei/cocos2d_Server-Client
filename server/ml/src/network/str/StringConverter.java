package network.str;

import java.io.UnsupportedEncodingException;

public class StringConverter {
	public static String getHexString(String s) throws UnsupportedEncodingException
	{
		byte[] bytes = s.getBytes("UTF-8");
		return getHexStringFromBytes(bytes, 0, bytes.length);
	}
	
	public static String getHexStringFromBytes(byte[] bytes)
	{
		return getHexStringFromBytes(bytes, 0, bytes.length);
	}
	
	public static String getHexStringFromBytes(byte[] bytes, int offset, int len)
	{
		String ret = "";
		for(int i = 0; i < len; ++i)
		{
			String hex = Integer.toHexString(bytes[offset + i] & 0xff);
			if (hex.length() == 1)
			{
				hex = "0" + hex;
			}
			ret += hex.toUpperCase();
		}
		return ret;
	}
	
	public static byte[] getBytesFromHexString(String hexstr)
	{
		if( hexstr.length() % 2 != 0 )
			hexstr = "0" + hexstr;
		byte[] bs = new byte[hexstr.length()/2];
		for(int i=0; i< bs.length; ++i)
		{
			bs[i] = Integer.valueOf(hexstr.substring(i * 2, i * 2 + 2), 16).byteValue();
		}
		return bs;
	}
	
	public static void main(String args[]) throws UnsupportedEncodingException
	{
		System.out.println(System.getProperty("user.dir"));  
		String source = "啦helloworld";
		byte[] bytes = source.getBytes(); //68656C6C6F776F726C64 一个字母占2位，中文有4位
		String ret = getHexStringFromBytes(bytes, 0, bytes.length);
		System.out.println(ret);
		
		byte[] b = getBytesFromHexString(ret);
		System.out.println(new String(b));
		
		System.out.println(getHexString(source));
	}
}
