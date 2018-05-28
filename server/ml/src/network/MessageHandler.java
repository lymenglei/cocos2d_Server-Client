package network;

import java.io.IOException;

import protoGen.menglei.*;

/**
 * ÿ���¼�Э�飬��Ҫ�޸Ĵ��ļ�������onRecv������
 * StreamManager.init������Ҫ��map�����һ��ӳ��
 * @author menglei
 *
 */

// ��������Ϣ����ķַ�����
public class MessageHandler {
	
	public MessageHandler()
	{
		rm = new RedisManager();
		Utils.log("init MessageHandler");
		if(!rm.getJedisIsReady())
		{
			Utils.error("redis server is not running now!");
		}
		else
		{
			Utils.log("redis is running!");
		}
	}

	// �޸ģ���Ҫ��bytes��Ҳ����������ʲô���ͣ����Ǵ��������ȽϿ���
	public void onRecv_role_regist_req(int sessionID, ZipBytes zb) throws ClassNotFoundException, IOException
	{
		role_regist_req req = role_regist_req.ADAPTER.decode(zb.getBytes());
		Utils.log("get message from client:--" + req.name);
		
		//�����ݿ�
		boolean ok = rm.set("name", req.name);
		String value = rm.get("name");
		Utils.log("redis get :" + value);
		
		role_regist_res res = new role_regist_res.Builder().ok(ok ? 1 : 0).build();
		byte[] bytesRes = role_regist_res.ADAPTER.encode(res);
		
		// �����bytes�ǿ���ֱ�����л��ɶ����bytes
		ZipBytes resZipBytes = new ZipBytes(zb.getType(), bytesRes);
		StreamManager.sendBytes(sessionID, resZipBytes);
	}
	
	// your codes here
	
	private RedisManager rm ;
}
