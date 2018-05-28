package test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import network.Utils;
import network.ZipBytes;

public class OtherTest {
	
	public void onRecv_role_regist_req(int a)
	{
		Utils.log(a);
	}
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException
	{
		
		
		Class classType;
		classType = Class.forName("protoGen.menglei.Test");
		OtherTest t = new OtherTest();
		
		
		String reqName = "role_regist_req";
		Method mth = OtherTest.class.getMethod("onRecv_" + reqName, int.class);
		mth.invoke(t, 12);
		
		
		String s = "role_regist_reqrole_regist_req"; // 30 ¸ö×Ö·û
		byte[] bytes = s.getBytes(); // 15¸ö
		System.out.println(bytes.length);
		
		
		String STREAM_SEPARATOR = "\001";
		String NIL = "\002";
		System.out.println(STREAM_SEPARATOR);
		System.out.println(NIL);
		
		int first = 38221350;
		String str = "helloworld";
		
		ZipBytes zb = new ZipBytes(first, str.getBytes());
		byte[] zbBytes = zb.getAllBytes();
		ZipBytes zb2 = new ZipBytes(zbBytes);
		System.out.println(zb2.getType() + "|" + new String(zb2.getBytes()));
		
		
		
		int count = 1000;
		System.out.println(count >> 8);
		System.out.println( (byte)149 << 8);
		System.out.println( 1 << 8);
	}
}
