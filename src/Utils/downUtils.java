package Utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;

import org.omg.CORBA.portable.InputStream;

public class downUtils {

	/**
     *  从Url中下载文件
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static String  downFromUrl(String urlStr,String fileName,String savePath) {
        String b= "-2";
    	  URL url=null;
    	  
    	 System.out.println(urlStr);
		  try {
			url = new URL(urlStr);
		 } catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }  
        HttpURLConnection conn=null;
		 try {
			conn = (HttpURLConnection)url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "-2";
		}  
		 
		 
		 if(conn==null){
			 System.out.println("conn null");
			 b="-2";
			 return b;
		 }
		  try {
			conn.setRequestMethod("GET");
		} catch (ProtocolException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		  conn.setRequestProperty("Accept", "*/*");  
        conn.setRequestProperty("Connection", "Keep-Alive");  
        conn.setRequestProperty("Charset", "UTF-8");
                //设置超时间为3秒
        conn.setConnectTimeout(3*1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
       
        try {
			conn.connect();
		 } catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "-2";
		}
        //得到输入流
       
        try {
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			    	System.out.println("connect failed!");
			    	return "-2";
			   }
		  } catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		} //获取响应内容体
        BufferedInputStream in=null;
       
        
		 try {
			 // 定义输入流来读取URL的响应
			
			 in =  new BufferedInputStream(conn.getInputStream());
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "-2";
		}  
		 
		 if(in==null){
				System.out.println("inputStream null");
			   return "-2";
		 }
		
		 //System.out.println("in");
       
        byte[] getData=null;
      
		try {
			
			getData = readInputStream(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
		 
		if(getData==null){
			System.out.println("getData null");
			b="-2";
			return b;
		}
        //文件保存位置
        File saveDir = new File(savePath);
        if(!saveDir.exists()){
            saveDir.mkdir();
          }
        
     
      String des=savePath+fileName;
      
      File file = new File(des);  
      
      
      FileOutputStream fos=null;
      
		try {
			fos = new FileOutputStream(file);
			 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     
		
		try {
			fos.write(getData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        if(fos!=null){
            try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
          }
        
        
     
        
        if(in!=null){
            try {
            in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         }
        b="1";

        System.out.println("down_info:"+url+"->"+des+" :success"); 
        
        return b;

    }
    
  

    public static String downFromLocal(String srcfile,String fileName,String savePath){
    	String b="-2";
    	File f=new File(srcfile);
    	System.out.println("savePath"+savePath);
    	if(!f.exists()){
    		System.out.println("源文件不存在");
    		b="-2";
    		return b;
    	}
    	
    	int sLen=savePath.length();
    	String des=savePath+fileName;
    
     	File saveDir = new File(savePath);
        if(!saveDir.exists()){
            saveDir.mkdir();
          }
        /*
    	int lastSeparatorIndex=savePath.lastIndexOf("/");
    	String save=savePath.substring(0, sLen-1);
      des=save+File.separator+fileName;
      System.out.println("des="+des);
      System.out.println((sLen-1)+":"+lastSeparatorIndex);
      if((sLen-1)== lastSeparatorIndex){
        	des=savePath+fileName;
        }
      
      */
      File file = new File(des);  
      FileInputStream input=null;
      BufferedInputStream in=null;
	  try {
		input = new FileInputStream(f);
	  } catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  } 
	
      FileOutputStream output=null;
	  try {
		output = new FileOutputStream(file);
	  } catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  } 

	   if(input==null||output==null){
		   b="-2";
		   return b;
	   }
      byte[] buffer = new byte[1024]; 
      int length=0; 
      int byteread =0;
      
      while (  byteread!= -1) { 
    	  try {
			byteread = input.read(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 b="-2";
			   return b;
		}
    	  if(byteread!=-1){
    		 // length=length+byteread;
    		  try {
				output.write(buffer, 0, byteread);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				 b="-2";
				   return b;
			} 
    	  }
    	  
        } 
      
      
      try {
		input.close();
	  } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  } 
      try {
		 output.close();
	  } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
    	}
      b="1";
      //System.out.println(length);
      System.out.println("down_info:"+srcfile+"->"+des+" :success"); 
    
    	return b;
    	
    }
    public static String down(String urlStr,String fileName,String savePath){
    	String b="-1";
    	String[] Strs=urlStr.split(":");
    	
    	   if(Strs==null){
    		   return "-1";
    	    }
    	   int len=Strs.length;
    		System.out.println(Strs[0]);
    		String Header=Strs[0];
    		if(Header.equals("http")){
    			b=downFromUrl(urlStr,fileName,savePath);
    		}else if(len==1){
    			b=downFromLocal(urlStr,fileName,savePath);
    			
    		}else{
    			System.out.print("暂不支持此类方式下载");
    		}
    	
    	
    	return b;
    }
    public static  byte[] readInputStream(BufferedInputStream  inputStream) throws IOException {  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        while((len = inputStream.read(buffer)) != -1) {  
            bos.write(buffer, 0, len);  
          }  
        bos.close();  
        return bos.toByteArray();  
    }  
    
   
    
    public static void main(String[] args) {
    	  String b="-1";
    	 
        try{
         String path="/usr/local/apache-tomcat-8.5.20/webapps/nodeMgr/program/节点管理.rar"	;
         String remote="http://192.168.1.28";
         String url=remote+path;
         //b= down(url,"jj.rar","/");
        // b= down("http://192.168.1.15:8080/nodeMgr/servlet/servletjson","my.json","/");
         // b= down("http://img03.tooopen.com/uploadfile/downs/images/20110714/sy_20110714135215645030.jpg", "百度.jpg","/");
         String RemoteUrl="http://192.168.1.15:8080/nodeMgr/downloadFile.action?";
         String key="fileName=";
         String value=URLEncoder.encode("节点管理.rar","utf-8");
         System.out.println("value"+value);
         String UrlStr=RemoteUrl+key+value;
         //b= down(UrlStr, "node.rar","/");
         String value2=URLEncoder.encode("应用程序包名称","utf-8");
         String RemoteUrl2="http://192.168.1.15:8080/nodeMgr/program/";
         String RemoteUrl3="http://192.168.1.9:8040/nodeMgr/program/a.xt";
         String UrlStr2=RemoteUrl3+value2;
          String uuu="http://192.168.1.9:8040/nodeMgr/downloadFile.action?fileName=webstore.war";
         b= down(uuu, "node2.war","/");
         
          //b= down("http://192.168.1.15:8080/nodeMgr/program/"+value, "node.rar","/");
         // b= down("http://192.168.1.28:8080/nodeMgr/","h.txt","/");
         //downloadFile("http://192.168.1.15:8080/nodeMgr/program/节点管理.rar", "node.rar");
          //b= downFromUrl2("http://192.168.1.15:8080/nodeMgr/program/节点管理.rar", "node.rar","/");
          //http://localhost:8080/nodeMgr/program/nodeMgr.rar
        }catch (Exception e) {
            // TODO: handle exception
          }
        System.out.println(b);
    }
	
   
}
