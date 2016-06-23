package ml;

import org.json.JSONException;
import org.json.JSONObject;

public class Bean {
	
	interface BaseBean{
		public JSONObject toJsonObj() throws JSONException;
		public String toJsonString() throws JSONException;
	}
	
	private static JSONObject getJsonObj(String context) {
		JSONObject obj = null;
		try {
			obj = new JSONObject(context);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	// testBean
	public static class testBean implements BaseBean
	{

		public testBean() {}
		public testBean(int v1, String v2)
		{
			this.testValue1 = v1;
			this.testValue2 = v2;
		}
		public testBean(String json) throws JSONException
		{
			JSONObject obj = getJsonObj(json);
			this.testValue1 = obj.getInt("testValue1");
			this.testValue2 = obj.getString("testValue2");
		}
		
		@Override
		public JSONObject toJsonObj() throws JSONException
		{
			JSONObject obj = new JSONObject();
			obj.put("testValue1", this.testValue1);
			obj.put("testValue2", this.testValue2);
			return obj;
		}
		
		@Override
		public String toJsonString() throws JSONException
		{
			return this.toJsonObj().toString();
		}
		
		private int testValue1;
		private String testValue2;
		public static final int CLASSID = 10001;
	}
	
	// testBean2
	public static class testBean2 implements BaseBean
	{
		public testBean2(){}
		public testBean2(int v1, testBean v2)
		{
			this.v1 = v1;
			this.v2 = v2;
		}
		
		public testBean2(String json) throws JSONException
		{
			JSONObject obj = getJsonObj(json);
			this.v1 = obj.getInt("v1");
			String s = obj.getString("v2");
			this.v2 = new testBean(s);
		}
		
		@Override
		public JSONObject toJsonObj() throws JSONException
		{
			JSONObject obj = new JSONObject();
			obj.put("v1", v1);
			obj.put("v2", v2.toJsonObj());
			return obj;
		}
		
		@Override
		public String toJsonString() throws JSONException
		{
			return this.toJsonObj().toString();
		}
		
		private int v1;
		private testBean v2;
		public static final int CLASSID = 10002;

	}
}
