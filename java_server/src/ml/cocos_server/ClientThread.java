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
 * Э��������ɣ�
 * 
 */


// �̳�Thread�߳���
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
					// ��ȡ�ͻ��˷���������
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

					// ������յ�������
					String read = new String(buff);
					System.out.println("�յ����ݣ�" + read);

					//  "10001|json"
					// ����ҷ�������
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
