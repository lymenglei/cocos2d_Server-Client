package network;

// Э�鴫���е��м����ݽṹ
public class ZipBytes {

	public ZipBytes(int type, byte[] bytes)
	{
		this.type = type; // Э���ֶζ�Ӧ�� id ����
		this.bytes = bytes; // Э��������л�����
	}

	
	// ������캯������Ĳ���Ϊ�գ���ô�ͻ����Ĭ�Ϲ��캯���� ������������
	public ZipBytes(byte[] allBytes)
	{
		if (allBytes == null || allBytes.length < 4)
			return;
		byte[] last = new byte[allBytes.length - 4];
		type = bytes2Int(allBytes);
		for(int i = 0; i < allBytes.length - 4; ++i)
		{
			last[i] = allBytes[i + 4];
		}
		bytes = last;
 	}
	
	public byte[] getAllBytes()
	{
		byte[] temp = new byte[4 + bytes.length];
		byte[] iBytes = int2Bytes(type);
		for(int i = 0; i < 4; ++i) 
		{
			temp[i] = iBytes[i];
		}
		for (int i = 0; i < bytes.length; ++i)
		{
			temp[4 + i] = bytes[i];
		}
		return temp;
	}
	
	public int getType()
	{
		return type;
	}
	public byte[] getBytes()
	{
		return bytes;
	}
	
	private static byte[] int2Bytes(int a) 
	{  
        byte[] b = new byte[4];  
        b[0] = (byte) (a >> 24);  
        b[1] = (byte) (a >> 16);  
        b[2] = (byte) (a >> 8);  
        b[3] = (byte) (a);  
  
        return b;  
    }  
  
	private static int bytes2Int(byte[] b) 
    {  
        return ((b[0] & 0xff) << 24) | ((b[1] & 0xff) << 16)  
                | ((b[2] & 0xff) << 8) | (b[3] & 0xff);  
    }
	
	private int type;
	private byte[] bytes; // �������л���bytes
}
