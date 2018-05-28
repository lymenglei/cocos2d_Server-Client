package network;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class StreamManager {
	
	public StreamManager()
	{
		init();
	}

	private void init()
	{
		// TODO 
		map.put(1, new ZipProto(1, "role_regist_req", "role_regist_res"));
		
		Utils.log("StreamManager init success");
		messageHandler = new MessageHandler();
	}
	
	// 服务器接收到消息
	public void handleZipBytes(int sessionID, ZipBytes zb)
	{
		if(map.size() == 0)
		{
			Utils.error("StreamManager map size is 0");
			return;
		}
		int protoIndex = zb.getType();
		ZipProto zp = map.get(protoIndex);
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
	
	// 给客户端回消息
	public static void sendBytes(int sessionID, ZipBytes zb) throws IOException
	{
		Socket socket = Server.getSessionManager().getSocket(sessionID);
		OutputStream outputStream = socket.getOutputStream();
		Stream.pushZipBytes(outputStream, zb);
	}
	
	private static MessageHandler messageHandler ;
	private static Map<Integer, ZipProto> map = new HashMap<>();
}
