package test;

import network.ServerCfg;
import redis.clients.jedis.Jedis;

public class TestRedis {
	
	public static void test()
	{
		Jedis jedis = new Jedis(ServerCfg.REDIS_HOST);
		String resCall = jedis.set("menglei", "null");
		String res = jedis.get("menglei");
		System.out.println(res+ " " + resCall);
		
		res = jedis.get("menglei11");
		System.out.println(res);
	}
	
	
	public static void main(String[] args)
	{
		test();
	}
}
