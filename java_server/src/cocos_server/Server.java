package cocos_server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {

	public static void main(String[] args) throws IOException {
		
		// 创建ServerSocket，监听端口号：12345
		int port = 12345;
		ServerSocket ss = new ServerSocket(port);
		// 创建用于管理客户端的收发数据的子线程类
		ClientThread clientThread = new ClientThread();
		clientThread.start();
		Date date = new Date(); 
		System.out.println("Server start at " + date.toString() + "...");
		System.out.println("now listening port : " + port);
		// 监听端口号：12345
		// 等待客户连接 accept()
		while (true) {
			// 开始接收客户端的连接
			Socket socket = ss.accept();
			System.out.println("new client connect from : " + socket.getLocalAddress());
			clientThread.addClient(socket);
		}
	}
}
