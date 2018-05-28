package network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;


/**
 * <h3>usage:</h3>
 * <pre>
 * ��һ���ֽ���λΪ0����0XXXXXXX����ʾ���Ⱦ�һ���ֽڣ����128����ʾ128B
 * 
 * ��һ���ֽ���λΪ110����ô��������һ���ֽڱ�ʾ���ȣ���110XXXXX 10XXXXXX�����2048����ʾ2K
 * 
 * ��һ���ֽ���λΪ1110����ô������������ֽڱ�ʾ���ȣ���110XXXXX 10XXXXXX 10XXXXXX�����131072����ʾ128K
 * 
 * ��������
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
	
	public static void pushZipBytes(OutputStream outputStream, ZipBytes zb)
	{
		pushBytes(outputStream, zb.getAllBytes());
	}

	public static ZipBytes getZipBytes(InputStream inputStream) throws IOException
	{
		byte[] bytes = getBytes(inputStream);
		return new ZipBytes(bytes);
	}
	
	// ����bytes����̫���ײ��������ˣ���������byte����ṹ���ӵ�ʱ��
	private static void pushBytes(OutputStream outputStream, byte[] bytes)
	{
		try {
			outputStream.write(bytes.length >> 8); // ����Ϊint��ֻ��д���8λ����24λ���Ե���
			outputStream.write(bytes.length);
			outputStream.write(bytes);
			outputStream.flush();
		} catch (Exception e)
		{
			e.printStackTrace();
			Utils.error("disconnected...");
		}
	}
	
	// ����bytes����̫���ײ��������ˣ���������byte����ṹ���ӵ�ʱ��
	private static byte[] getBytes(InputStream inputStream)
	{
		byte[] bytes = null ;

		// TODO �Ż���������ݳ������������
		try {
			int first = inputStream.read(); // ÿ�ζ�8λ
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
	
	// ��ȡһ��byte����ֱ�������˷ָ�����Ȼ�󷵻�һ����������
	// ���⣬���������decode������ַ���ָ�����ͬ��ô�죿
	// ������һ���ָ�����Ϊ�ָ����Ϳ����ˡ�
	// ���߱�����ַ�������ͺ��зָ�������ô��û��������������
//	public static String detectPacketName(byte[] bytes)
//	{
//		return "";
//	}
}
