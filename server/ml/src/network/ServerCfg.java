package network;

public class ServerCfg {
	public static final int port = 55533; // default port
	public static final String localhost = "127.0.0.1"; // client use only
	
	public static final int THREAD_POOL_SIZE = 10;
	public static final int SESSION_LIMIT_SIZE = 2;
	
	public static final String REDIS_HOST = "localhost";
	public static boolean USE_JEDIS = false;
}
