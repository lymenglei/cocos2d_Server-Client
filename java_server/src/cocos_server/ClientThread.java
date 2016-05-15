package cocos_server;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

// �̳�Thread�߳���
public class ClientThread extends Thread {
	// �ͻ��б�
	private ArrayList<Socket> clients = new ArrayList<Socket>();

	// ��ӿͻ�
	public void addClient(Socket socket) {
		clients.add(socket);
	}
	// ɾ���ͻ�
	public void removeClient(Socket socket) {
		clients.remove(socket);
	}
	// ��ͻ���������
	public void sendMessage(Socket socket, String data) throws IOException {
		// ����ҷ�������
		OutputStream os = socket.getOutputStream();
		os.write(data.getBytes("UTF-8"));
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
