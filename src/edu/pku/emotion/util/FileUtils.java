package edu.pku.emotion.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class FileUtils {
	
	public static ArrayList<String> readFile(String path) throws Exception {
		ArrayList<String> result=new ArrayList<String>();
		InputStreamReader isReader =new InputStreamReader(new FileInputStream(path),"UTF-8");
		BufferedReader reader = new BufferedReader(isReader);
		String aline;
	    while((aline=reader.readLine())!=null)
	    {
	    	result.add(aline);
	    }
	    reader.close();
		return result;
	}
}
