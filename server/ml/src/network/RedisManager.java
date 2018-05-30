package network;

import redis.clients.jedis.Jedis;

public class RedisManager {

	public RedisManager()
	{
		jedis = new Jedis(ServerCfg.REDIS_HOST);
	}
	
	private static void logJedisNotReady()
	{
		Utils.warn("jedis is not ready, return deault value");
	}
	
	public static boolean getJedisIsReady()
	{
		if (ServerCfg.USE_JEDIS)
		{
			return jedis.ping().equals("PONG");
		}else
		{
			logJedisNotReady();
			return false;
		}
		
	}
	
	public static Jedis getJedis()
	{
		return jedis;
	}
	
	public boolean set(String key, String value)
	{
		if (ServerCfg.USE_JEDIS)
		{
			String res = jedis.set(key, value);
			return res.equals("OK");
		}else
		{
			logJedisNotReady();
			return true;
		}
	}
	
	public String get(String key)
	{
		
		if (ServerCfg.USE_JEDIS)
		{
			String res = jedis.get(key);
			return res;
		}else
		{
			logJedisNotReady();
			return "";
		}
	}
	
	private static Jedis jedis;
}
