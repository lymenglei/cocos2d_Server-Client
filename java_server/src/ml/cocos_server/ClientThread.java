package ml.cocos_server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;


/**
 * @date 2016.6.22 19:16
 * @author menglei
 * 协议如何生成？
 * 
 */


// 继承Thread线程类
public class ClientThread extends Thread {
	
	public static final int MAX_CONNECTED_COUNT_PER_THREAD = 100;
	
	private ArrayList<Socket> clients = new ArrayList<Socket>();

	public void addClient(Socket socket) {
		clients.add(socket);
	}
	public void removeClient(Socket socket) {
		clients.remove(socket);
	}
	public void sendMessage(Socket socket, String data) throws IOException {
		OutputStream os = socket.getOutputStream();
		os.write(data.getBytes("UTF-8"));
	}
	
	public int getClientsCount()
	{
		synchronized(clients)
		{
			return clients.size();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				for (Socket socket : clients) {
					// 获取客户端发来的数据
					InputStream is = socket.getInputStream();
					int len = is.available() + 1;
					byte[] buff = new byte[len];
					try{
						  is.read(buff);
						}catch(SocketException e){
						  System.out.println("disconnect from: "+ socket.getLocalAddress());
						  this.removeClient(socket);
						  break;
						}

					// 输出接收到的数据
					String read = new String(buff);
					System.out.println("收到数据：" + read);

					//  "10001|json"
					// 给玩家发送数据
					String data = "this is a message from server.";
					sendMessage(socket, data);
				}
				sleep(10);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
