package ml.cocos_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;

import ml.Bean;
import ml.util.ExecutorTool;
import ml.util.GameTime;
import ml.util.Logger;

public class Server {
	
	// this is a second task
	public void onTimer(int timeTick)
	{
		logger.info("this is a string of info");
	}
	
	private class TimerTask implements Runnable
	{
		@Override
		public void run()
		{
			try
			{
				onTimer(GameTime.getTime());
			}
			catch (Throwable t)
			{
				System.exit(0);
			}
		}
		public ScheduledFuture<?> future = null;
	}

	
	public void testBean()
	{
		Bean.testBean test = new Bean.testBean(1, "sss");
		Bean.testBean2 test2 = new Bean.testBean2(2, test);
		try {
			logger.warn(test.toJsonString());
			logger.warn(test2.toJsonString());
			Bean.testBean2 test3 = new Bean.testBean2(test2.toJsonString());
			logger.warn(test3.toJsonString());
			/*
			WARN  2016-06-23 16:38:10 {"testValue2":"sss","testValue1":1}
			WARN  2016-06-23 16:38:10 {"v1":2,"v2":{"testValue2":"sss","testValue1":1}}
			WARN  2016-06-23 16:38:10 {"v1":2,"v2":{"testValue2":"sss","testValue1":1}}
			 */
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
	}
	
	public void start()
	{
		testBean();
		timertask.future = executor.scheduleAtFixedRate(timertask, 1, 1, TimeUnit.SECONDS);
		try {
			init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void destroy()
	{
		if (timertask.future != null)
			timertask.future.cancel(false);
	}
	
	/**
	 * 创建ServerSocket，监听端口号：12345
	 * 等待客户连接 accept()
	 * @throws IOException
	 */
	public void init() throws IOException
	{
		//TODO read this from cfg file.
		int port = 12345;
		
		// 创建用于管理客户端的收发数据的子线程类
		ClientThread clientThread = new ClientThread();
		clientThread.start();
		System.out.println("Server start at " + new Date().toString() + "...");
		System.out.println("now listening port : " + port);
		logger.info("=========server start===========");
		try{
			ServerSocket ss = new ServerSocket(port);
			while (true) {
				Socket socket = ss.accept();
				logger.info("new client connect from : " + socket.getLocalAddress());
				clientThread.addClient(socket);
			}
		} catch(java.net.BindException e)
		{
			logger.error(e.toString());
			System.exit(0);
		}
	}
	
	public static void main(String[] args)
	{
		Server t = new Server();
		t.start();
	}
	
	
	private ScheduledExecutorService executor = ExecutorTool.newSingleThreadScheduledExecutor("TimerThread");
	private final TimerTask timertask = new TimerTask();
	private Logger logger = new Logger("server");
	
}
