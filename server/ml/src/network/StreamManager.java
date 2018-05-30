package network;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import proto.menglei.GenProtosList;

public class StreamManager {
	
	public StreamManager()
	{
		init();
	}

	private void init()
	{
		// TODO ���ɵ�Э����Ҫ������ע����
		genMap = new GenProtosList();
		genMap.init();
		
		Utils.log("StreamManager init success");
		messageHandler = new MessageHandler();
	}
	
	// ���������յ���Ϣ
	public void handleZipBytes(int sessionID, ZipBytes zb)
	{
		Map<Integer, ZipProto> map = genMap.getMap();
		if(map.size() == 0)
		{
			Utils.error("StreamManager map size is 0");
			return;
		}
		int protoIndex = zb.getType();
		ZipProto zp = map.get(protoIndex);
		if(zp == null)
		{
			Utils.error("StreamManager.map get proto failed, id = " + protoIndex);
			return;
		}
		Method mtd;
		try {
			mtd = MessageHandler.class.getMethod("onRecv_" + zp.getReq(), int.class, network.ZipBytes.class);
			mtd.invoke(messageHandler, sessionID, zb);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	// ���ͻ��˻���Ϣ
	public static void sendBytes(int sessionID, ZipBytes zb) throws IOException
	{
		Socket socket = Server.getSessionManager().getSocket(sessionID);
		OutputStream outputStream = socket.getOutputStream();
		Stream.pushZipBytes(outputStream, zb);
	}
	
	private static MessageHandler messageHandler ;
	private static GenProtosList genMap;
}
