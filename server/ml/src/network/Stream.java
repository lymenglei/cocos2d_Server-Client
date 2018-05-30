package network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;


/**
 * <h3>usage:</h3>
 * <pre>
 * 第一个字节首位为0：即0XXXXXXX，表示长度就一个字节，最大128，表示128B
 * 
 * 第一个字节首位为110，那么附带后面一个字节表示长度：即110XXXXX 10XXXXXX，最大2048，表示2K
 * 
 * 第一个字节首位为1110，那么附带后面二个字节表示长度：即110XXXXX 10XXXXXX 10XXXXXX，最大131072，表示128K
 * 
 * 依次类推
 * </pre>
 * @author menglei
 *
 */
public class Stream {
	
	public static String STREAM_SEPARATOR = "\001";
	public static String NIL = "\002";
	

	public static void pushString(OutputStream os, String s) throws UnsupportedEncodingException
	{
		byte[] sendBytes = s.getBytes("UTF-8");
		pushBytes(os, sendBytes);
	}
	
	public static void pushZipBytes(OutputStream os, ZipBytes zb)
	{
		pushBytes(os, zb.getAllBytes());
	}

	public static ZipBytes getZipBytes(InputStream is) throws IOException
	{
		byte[] bytes = getBytes(is);
		return new ZipBytes(bytes);
	}
	
	// 这种bytes数组太容易产生歧义了，尤其是在byte数组结构复杂的时候
	private static void pushBytes(OutputStream outputStream, byte[] bytes)
	{
		try {
			outputStream.write(bytes.length >> 8); // 参数为int，只会写入低8位，高24位忽略掉了
			outputStream.write(bytes.length);
			outputStream.write(bytes);
			outputStream.flush();
		} catch (Exception e)
		{
			e.printStackTrace();
			Utils.error("disconnected...");
		}
	}
	
	// 这种bytes数组太容易产生歧义了，尤其是在byte数组结构复杂的时候
	private static byte[] getBytes(InputStream inputStream)
	{
		byte[] bytes = null ;

		// TODO 优化，如果数据超过了这个长度
		try {
			int first = inputStream.read(); // 每次读8位
			if (first == -1)
			{
				Utils.warn("read end of inputStream. return null");
				return bytes;
			}
			int second = inputStream.read();
			int length = (first << 8) + second;
			bytes = new byte[length];
			inputStream.read(bytes);
			return bytes;
		} catch (IOException e)
		{
			return null;
		}
	}
	
	// 读取一个byte流，直到遇到了分隔符，然后返回一个包的名字
	// 问题，如果遇到了decode编码的字符与分隔符相同怎么办？
	// 遇到第一个分隔符作为分隔符就可以了。
	// 或者编码的字符串本身就含有分隔符，那么就没法解决这个问题了
//	public static String detectPacketName(byte[] bytes)
//	{
//		return "";
//	}
}
