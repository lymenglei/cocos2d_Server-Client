package test;

import java.io.IOException;

import protoGen.menglei.Test;

public class TestClient {
	
	
	public static void main(String[] args)
	{
		Test test = new Test.Builder().name("Stegosaurus").count(12).build();
		byte[] bytes = Test.ADAPTER.encode(test);
		
		try {
			Test cons = Test.ADAPTER.decode(bytes);
			System.out.println(cons.name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(test.name);
	}
}
