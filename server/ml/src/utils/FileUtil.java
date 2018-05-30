package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import network.Utils;

public class FileUtil {

	public static String getUserDir()
	{
		return System.getProperty("user.dir");
	}
	
	public static void genFile(String absoluteFilePath, String context)
	{
		Utils.log("generate write file at " + absoluteFilePath);
		File file = new File(absoluteFilePath);
		try {
			if(!file.exists())
			{
				file.createNewFile();
			}
			FileOutputStream outStream = new FileOutputStream(file);  
	        outStream.write(context.getBytes());  
	        outStream.close();  
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
