
package com.mtech.server;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class WebClient {
	public static String getData(String urlstr){
		System.out.println(urlstr);
		String data=null;
		try{
			URL url=new URL(urlstr);
			URLConnection uc=url.openConnection();
			InputStream in=uc.getInputStream();
			int ch=0;
			String msg="";
			while((ch=in.read())!=-1)
				msg+=(char)ch;
			data =msg.trim();
			in.close();
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return data;
	}
}
