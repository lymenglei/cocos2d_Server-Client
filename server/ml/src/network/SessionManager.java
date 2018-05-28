package network;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * manage connections
 * @author menglei
 *
 */
public class SessionManager {
	
	public int addSession(Socket socket)
	{
		synchronized(map)
		{
			int size = map.size();
			if(size >= limitSize)
			{
				Utils.error("SessionManager.map size is upto limit:" + limitSize);
				Utils.error("add session failed, " + socket.toString());
				try {
					Utils.error("close socket:" + socket.toString());
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return -1;
			}
			map.put(size + 1, socket);
			return size + 1;
		}
	}
	
	public boolean removeSession(int key)
	{
		synchronized(map)
		{
			if (! map.containsKey(key))
			{
				Utils.warn("session not found, remove nothing. key:" + key);
				return false;
			}
			map.remove(key);
			return true;
		}
	}
	
	public Socket getSocket(int sessionID)
	{
		synchronized(map)
		{
			return map.get(sessionID);
		}
	}
	
	public int getCurrentSize()
	{
		return map.size();
	}
	
	public static int getLimitSize()
	{
		return limitSize;
	}
	
	private static int limitSize = ServerCfg.SESSION_LIMIT_SIZE;
	private ConcurrentMap<Integer, Socket> map = new ConcurrentHashMap<>(); 
}
