// This file is auto gen by CodeGenerator, DO NOT MODIFY!
// 2018-05-30 20:11:01
package proto.menglei;

import java.util.HashMap;
import java.util.Map;

import network.ZipProto;

public class GenProtosList {
	public static final int PROTO_ID_ROLE_REGIST = 1;
	public static final int PROTO_ID_ROLE_PAY = 2;

	public void init()
	{
		map.put(PROTO_ID_ROLE_REGIST, new ZipProto(PROTO_ID_ROLE_REGIST, "role_regist_req", "role_regist_res"));
		map.put(PROTO_ID_ROLE_PAY, new ZipProto(PROTO_ID_ROLE_PAY, "role_pay_req", "role_pay_res"));

	}


	public Map<Integer, ZipProto> getMap()
	{
		return map;
	}
	public ZipProto get(int key)
	{
		return map.get(key);
	}

	private Map<Integer, ZipProto> map = new HashMap<>();
}
