package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import protoGen.menglei.Test;

public class Server {
	
	public static void runServerPool2() throws IOException
	{
		int port = ServerCfg.port;
		ServerSocket server = new ServerSocket(port);
		Utils.log("waiting...");
		ExecutorService threadPool = Executors.newFixedThreadPool(ServerCfg.THREAD_POOL_SIZE);
		
		StreamManager streamManager = new StreamManager();
		while(true)
		{
			Socket socket = server.accept();
			Utils.log("curren sessions count: " + sm.getCurrentSize() );
			Utils.log("new socket connected, " + socket.toString());
			int errorCode = sm.addSession(socket);
			Runnable runnable = ()->{
				try {
					InputStream inputStream = socket.getInputStream();
					while(true)
					{
						try {
							ZipBytes zb = Stream.getZipBytes(inputStream);
							if(zb.getType() == 0) // 如果是个空对象，因为正常的zb对象的type要大于0
							{
								Utils.error("client disconnected..." + socket.toString());
								sm.removeSession(errorCode);
								return;
							}
							streamManager.handleZipBytes(errorCode, zb);
						} catch (Exception e)
						{
							e.printStackTrace();
							Utils.error("client disconnected..." + socket.toString());
							sm.removeSession(errorCode);
							return;
						}
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			};
			
			if (errorCode > 0)
				threadPool.submit(runnable);
		}
	}
	
	public static SessionManager getSessionManager()
	{
		return sm;
	}
	
	private static SessionManager sm = new SessionManager();
	
	
	public static void main(String[] args)
	{
//		BasicConfigurator.configure();
		try {
			runServerPool2();
		} catch (java.net.BindException be)
		{
			System.err.println("Address already in use, shutdown");
			be.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
