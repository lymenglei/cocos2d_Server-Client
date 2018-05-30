package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import network.Utils;


// 自动生成代码工具类
public class CodeGenerator {

	public static StringBuffer getFinalIndexListText(List<String> names)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		for(int i = 0; i < names.size(); ++i)
		{
			String name = names.get(i).toUpperCase();
			int id = i + 1;
			String IDStr = getFinalIntName(names, i);
			sb.append("\tpublic static final int " + IDStr + " = " + id + ";\n");
		}
		return sb;
	}
	
	public static String getFinalIntName(List<String> names, int index)
	{
		String name = names.get(index).toUpperCase();
		StringBuffer sb = new StringBuffer("PROTO_ID_" + name);
		return sb.toString();
	}
	
	public static StringBuffer getInsertMapListText(List<String> names)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\n\tpublic void init()\n\t{\n");
		for(int i = 0; i < names.size(); ++i)
		{
			String IDStr = getFinalIntName(names, i);
			String name = names.get(i);
			
			sb.append("\t\tmap.put(" + IDStr + ", new ZipProto(" + IDStr + ", \"" + name + "_req\", \"" + name + "_res\"));\n" );
		}
		sb.append("\n\t}\n\n");
		return sb;
	}
	
	
	// 自动生成协议的map
	public static void genProtoMap(List<String> names)
	{
		// 读入protos.proto文件，搜寻匹配的字段，
		// 将对应的req，res文件添加到map中
		// 将生成的文件写入到proto.menglei/GenProtosList.java文件中
		String userDir = FileUtil.getUserDir();
		String fileDir = "\\src\\proto\\menglei\\GenProtosList.java";
		
		String genInfo = "// This file is auto gen by CodeGenerator, DO NOT MODIFY!\n// " + TimeUtil.getCurrentTime() + "\n";
		
		String finalInt = getFinalIndexListText(names).toString();
		String functionInit = getInsertMapListText(names).toString();
		String context = genInfo +
				"package proto.menglei;\n\n" + 
				"import java.util.HashMap;\n" + 
				"import java.util.Map;\n\n" + 
				"import network.ZipProto;\n\n" +
				"public class GenProtosList {" + 
				finalInt +
				functionInit +
				
				"	public ZipProto get(int key)\n" + 
				"	{\n" + 
				"		return map.get(key);\n" + 
				"	}\n\n" + 
				"	private Map<Integer, ZipProto> map = new HashMap<>();" +
				"\n}\r\n";
		
		FileUtil.genFile(userDir + fileDir, context);
	}
	
	
	//message role_regist_req
	public static String handlerSingleLine(String str)
	{
		if(str.startsWith("message"))
		{
			str = str.replace("message", "");
			str = str.replace(" ", "");
			str = str.replace("{", "");
			Utils.log("FIND proto : " + str);
			return str;
		}
		return null;
	}
	
	public static boolean checkReqRes(List<String> names, Set<String> hash)
	{
		for(int i = 0; i < names.size(); ++i)
		{
			String name = names.get(i);
			String req = name + "_req";
			String res = name + "_res";
			if( !hash.contains(req))
			{
				Utils.error("checkReqRes failed, " + req + " not found");
				return false;
			}
			if( !hash.contains(res))
			{
				Utils.error("checkReqRes failed, " + res + " not found");
				return false;
			}
		}
		return true;
	}
	
	
	public static List<String> getProtosList(String absoluteFilePath)
	{
		File file = new File(absoluteFilePath);
		List<String> list = new ArrayList<>();
		if (file.exists() && file.isFile())
		{
			try {
				FileInputStream fis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(fis);
				BufferedReader br = new BufferedReader(isr);
				String str = null;
				Set<String> hash = new HashSet<>(); // 做协议校验的
				while((str = br.readLine()) != null)
				{
					String res = handlerSingleLine(str);
					if (res != null)
					{
						if(res.endsWith("_req"))
						{
							String t = res.replace("_req", "");
							if(!hash.contains(t + "_req") && !hash.contains(t + "_res"))
							{
								list.add(t);
							}
							hash.add(res);
						}else if(res.endsWith("_res"))
						{
							String t = res.replace("_res", "");
							if(!hash.contains(t + "_req") && !hash.contains(t + "_res"))
							{
								list.add(t);
							}
							hash.add(res);
						}
					}
				}
				br.close();
				isr.close();
				fis.close();
				
				boolean pass = checkReqRes(list, hash);
				return pass ? list : null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}else
		{
			Utils.error("file " + absoluteFilePath + " not found!");
			return null;
		}
	}
	
	public static void main(String[] args)
	{
//		ArrayList<String> names = new ArrayList();
//		names.add("role_regist");
//		names.add("role_true");
//		names.add("role_false");
		String userDir = FileUtil.getUserDir();
		String fileDir = "\\src\\proto\\menglei\\protos.proto";
		List<String> names = getProtosList(userDir + fileDir);
		if(names != null)
			genProtoMap(names);
	}
}
