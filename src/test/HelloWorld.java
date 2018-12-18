/**
 * 
 */
/**
 * @author root
 *
 */
package test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import process.deploy;

public class HelloWorld {
    
    public static void main(String[] args) {
    	 HelloWorld hw=new  HelloWorld();
    	//InputStream in = deploy.class.getClassLoader().getResourceAsStream("config.properties");
    	 InputStream in=null;
    	 File f=new File("web.xml");
    	 
    	System.out.println("文件："+f.exists());
    	 if(in==null){
			   System.out.println("in==null");
		   }
		   Properties properties = new Properties();
		   try {
				properties.load(in);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   String strategyPath= properties.getProperty("strategyPath") ;
		   String programFilePath= properties.getProperty("Server_Type") ;
		   System.out.println("strategyPath："+strategyPath);
		   System.out.println("programFilePath："+programFilePath);
		   
    	  String endTime="201811210900";
        long t1=System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
   	   Date HTime=null;
   	    long t=t1;
   	   while(t1<(t+100*1000)){
   		  t1=System.currentTimeMillis();
   	  	try {
			Thread.sleep(1000);
		  } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		   }
   		   HTime=new Date(t1);
     	     String stime=HTime.toString();
            System.out.println(stime+":测试success");
            System.out.println("strategyPath："+strategyPath);
 		      System.out.println("programFilePath："+programFilePath);
 		      //String s=hw.getClass().getResource("").getPath();
 		      //System.out.println("classLoadPath="+s);
 		   
   	       }
   	  
        
    }

}