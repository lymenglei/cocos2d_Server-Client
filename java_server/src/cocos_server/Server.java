package cocos_server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {

	public static void main(String[] args) throws IOException {
		
		// ����ServerSocket�������˿ںţ�12345
		int port = 12345;
		ServerSocket ss = new ServerSocket(port);
		// �������ڹ���ͻ��˵��շ����ݵ����߳���
		ClientThread clientThread = new ClientThread();
		clientThread.start();
		Date date = new Date(); 
		System.out.println("Server start at " + date.toString() + "...");
		System.out.println("now listening port : " + port);
		// �����˿ںţ�12345
		// �ȴ��ͻ����� accept()
		while (true) {
			// ��ʼ���տͻ��˵�����
			Socket socket = ss.accept();
			System.out.println("new client connect from : " + socket.getLocalAddress());
			clientThread.addClient(socket);
		}
	}
}
