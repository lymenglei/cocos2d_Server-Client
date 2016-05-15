package cocos_server;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

// 继承Thread线程类
public class ClientThread extends Thread {
	// 客户列表
	private ArrayList<Socket> clients = new ArrayList<Socket>();

	// 添加客户
	public void addClient(Socket socket) {
		clients.add(socket);
	}
	// 删除客户
	public void removeClient(Socket socket) {
		clients.remove(socket);
	}
	// 向客户发送数据
	public void sendMessage(Socket socket, String data) throws IOException {
		// 给玩家发送数据
		OutputStream os = socket.getOutputStream();
		os.write(data.getBytes("UTF-8"));
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
