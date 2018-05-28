package network;

import redis.clients.jedis.Jedis;

public class RedisManager {

	public RedisManager()
	{
		jedis = new Jedis(ServerCfg.REDIS_HOST);
	}
	
	public static boolean getJedisIsReady()
	{
		return jedis.ping().equals("PONG");
	}
	
	
	public static Jedis getJedis()
	{
		return jedis;
	}
	
	public boolean set(String key, String value)
	{
		String res = jedis.set(key, value);
		return res.equals("OK");
	}
	
	public String get(String key)
	{
		String res = jedis.get(key);
		return res;
	}
	
	private static Jedis jedis;
}
