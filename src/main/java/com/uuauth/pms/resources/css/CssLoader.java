package com.uuauth.pms.resources.css;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class CssLoader {
	
	private static String	STYLE	= null;
	static {
		STYLE = readFile(CssLoader.class.getResourceAsStream("/"
				+ CssLoader.class.getPackage().getName().replaceAll("\\.", "/")
				+ "/style.css"));
	}
	
	public final static String getCss() {
		return STYLE;
	}
	
	private final static String readFile(InputStream is) {
		String content = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		
		try {
			String temp = reader.readLine();
			
			while (temp != null) {
				content += (temp + "\n");
				temp = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return content;
	}
}
