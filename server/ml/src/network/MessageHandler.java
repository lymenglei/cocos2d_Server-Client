package network;

import java.io.IOException;

import protoGen.menglei.*;

/**
 * 每次新加协议，需要修改此文件，增加onRecv函数，
 * StreamManager.init方法需要向map中添加一个映射
 * @author menglei
 *
 */

// 做网络消息处理的分发工作
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

	// 修改，不要传bytes，也看不出来是什么类型，还是传递类对象比较靠谱
	public void onRecv_role_regist_req(int sessionID, ZipBytes zb) throws ClassNotFoundException, IOException
	{
		role_regist_req req = role_regist_req.ADAPTER.decode(zb.getBytes());
		Utils.log("get message from client:--" + req.name);
		
		//用数据库
		boolean ok = rm.set("name", req.name);
		String value = rm.get("name");
		Utils.log("redis get :" + value);
		
		role_regist_res res = new role_regist_res.Builder().ok(ok ? 1 : 0).build();
		byte[] bytesRes = role_regist_res.ADAPTER.encode(res);
		
		// 这里的bytes是可以直接序列化成对象的bytes
		ZipBytes resZipBytes = new ZipBytes(zb.getType(), bytesRes);
		StreamManager.sendBytes(sessionID, resZipBytes);
	}
	
	// your codes here
	
	private RedisManager rm ;
}
