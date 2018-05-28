package network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import protoGen.menglei.role_regist_req;
import protoGen.menglei.role_regist_res;

public class Client {
	
	public static boolean runClientPool3(ArgsMap am) 
	{
		String host = am.get("host", ServerCfg.localhost);
		int port = am.getInt("port", ServerCfg.port);
		String structName = am.get("structName", "abc");
		int structCount = am.getInt("structCount", 0);
		
		Socket socket;
		OutputStream outputStream;
		try {
			socket = new Socket(host, port);
			Utils.log("run Client at " + host + ":" + port);
			while(true)
			{
				// 客户端发送消息
				role_regist_req req = new role_regist_req.Builder().name(structName + structCount).build();
				byte[] testBytes = role_regist_req.ADAPTER.encode(req);
				ZipBytes zb = new ZipBytes(1, testBytes);// index = 1
				
				outputStream = socket.getOutputStream();
				Stream.pushZipBytes(outputStream, zb);
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					return false;
				}
				
				//接受服务器的消息
				InputStream inputStream = socket.getInputStream();
				ZipBytes zbRes = Stream.getZipBytes(inputStream);
				if (zbRes.getType() == 0)
				{
					Utils.error("server side maybe closed or sever is full...");
					return false;
				}
				role_regist_res res = role_regist_res.ADAPTER.decode(zbRes.getBytes());
				Utils.log("get message from server: ok = " + res.ok);
				
			}
		} catch (UnknownHostException e1) {
			Utils.error("UnknownHostException exception");
			return false;
		} catch(java.net.ConnectException e)
		{
			Utils.error("Connection refused. Server site not open. " + host + ":" + port);
			return false;
		}catch (IOException e1) {
			Utils.error("io exception");
			e1.printStackTrace();
			return false;
		}
//		return true;
	}
	
	public static void main(String[] args) throws IOException
	{
		Utils.log("run client...");
		boolean res = runClientPool3(new ArgsMap(args));
		Utils.log("client run status:" + res + ", exit...");
	}
}
