package process;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Properties;

import Utils.container;
import Utils.documentUtils;
import Utils.downUtils;
import Utils.parseProxyConf;



public class deploy {
	public static void main(String []args){
		   InputStream in = deploy.class.getClassLoader().getResourceAsStream("config.properties");
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
		   
		   
	        int count = 0;
	        //遍历所有的属性
	        for (String key : properties.stringPropertyNames()) {
	            System.out.println(key + "=" + properties.getProperty(key));
	            count++;
	           

	        }
	        System.out.println(count);
	        String s="http://192.168.1.9:8040/nodeMgr/downloadFile.action?fileName=webstore.war";
	        String [] pp=getappNameFromURL(s);
	        System.out.println(pp[0]+" "+pp[1]);
	}
   
  
  
   
   public String work(String Url,String port){
	   String b="-1";
	   String strategyPath= null;
	   String programFilePath=null;
	   InputStream in = deploy.class.getClassLoader().getResourceAsStream("config.properties");
	   Properties properties = new Properties();
	   try {
			properties.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	
	   
	   String user_dir=System.getProperty("user.dir") ;
	   System.out.println("deploy:"+user_dir);
	  
	    String proxyconf = properties.getProperty("proxy_conf") ;
	    System.out.println("proxyconf:"+proxyconf);
	    container MyContainer=null;
	    boolean hasFind=false;
	    List<container> list=null;
		 try {
			list = parseProxyConf.getContainers(proxyconf);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
        for(container aContainer :list){ 
        	if( aContainer.port.equals( port)){
        		 System.out.println( aContainer.port);  
        		 hasFind=true;
        		 MyContainer=aContainer;
        	}
           
        }  
      if(MyContainer!=null){
        	 programFilePath=MyContainer.getCommand("warPath");
        	 strategyPath=MyContainer.getCommand("strategyPath");
       }else{
    	   System.out.println("no found port in proxyconf.xml");
    	   return "-1";
        }
        
	   System.out.println("strategyPath："+strategyPath);
	   System.out.println("programFilePath："+programFilePath);
	   
		if(programFilePath==null){
			 return "-1";
		}
		
		String downResult="-2";
		boolean docResult=false;
		
	    
	    String [] urlNames=this.getNameFromURL(Url);
	    String [] apps=getappNameFromURL(Url);
	    
	    if(apps.length<2){
	    	System.out.println("文件下载地址参数不符合要求："+Url);
	    	return "-1";
	    }
	    
	    
		String endNames=urlNames[1];
		String firstNames=urlNames[0];
		
		System.out.println(firstNames+"****"+endNames);
		
		String utf8Url="";
		
		try {
			utf8Url=URLEncoder.encode(endNames,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		utf8Url=firstNames+"/"+utf8Url;
		
		String application=apps[1];
		
		System.out.println("FULLPATH:"+programFilePath+"****"+application);
		
		System.out.println("down:Url:"+Url);
		System.out.println("down:utf8Url:"+utf8Url);
		System.out.println("down:application:"+application);
		
	
		downResult= downUtils.down(Url,application, programFilePath);
		
		//downResult= downUtils.down(utf8Url,application, programFilePath);
		
		System.out.println("downResult："+downResult);
		
		int dr=-1;
		if(downResult!=null){
			 dr=Integer.valueOf(downResult);
		}
	
		b=downResult;
		docResult=true;
		  /*
			if(strategyFilesStr!=null){
				String[] xmlNames=application.split("\\.");
				 String xmlPath="web.xml";
				if(xmlNames.length>1){
					xmlPath=xmlNames[1];
				}else{
					xmlPath=xmlNames[0];
				}
				xmlPath=strategyPath+xmlPath+".xml";
				File f=new File(strategyPath);
				
				if(!f.exists()){
					f.mkdirs();
				}
				
				documentUtils docUtils=new documentUtils();
				docResult=docUtils.str2xml(strategyFilesStr, xmlPath);
				System.out.println("docResult："+docResult);
			}else{
				docResult=true;
			}
		
		*/
		
		
		
		if(dr>0){
			if(!docResult){
				b="-1";
			}
		
		}
		
		System.out.println("b:"+b);
	   return b;
   }
   
  
   
   
   public String[] getNameFromURL(String programFileUrl){
	   if(programFileUrl==null){
		   return null;
	   }
	   String aprogramFileUrl=programFileUrl;
	   
	   String [] Namestr=new String[4];
	   int len=programFileUrl.length();
	   int index=programFileUrl.lastIndexOf("/");
	    
	   String [] path1=aprogramFileUrl.split("/");
	  
	   
	   String str1="";
	   String str2="";
	  
	   str1=programFileUrl.substring(0, index);
	   str2=programFileUrl.substring(index+1, len);
	  
	   
	   Namestr[0]=str1;
	   Namestr[1]=str2;
	   
	   return Namestr;
    }
   
   
   public static  String[] getappNameFromURL(String programFileUrl){
	   if(programFileUrl==null){
		   return null;
	   }
	   String aprogramFileUrl=programFileUrl;
	   
	   String [] Namestr=new String[4];
	   int len=programFileUrl.length();
	   int index=programFileUrl.lastIndexOf("?");
	    

	   String str1="";
	   String str2="";
	  
	   str1=programFileUrl.substring(0, index);
	   str2=programFileUrl.substring(index+1, len);
	   System.out.println("FileName=appname"+"  ->  "+str2);
	  //fileName=webstore.war
	   String []para=str2.split("[=]");
	   String app=para[0];
	   if(para.length>1){
		  app=para[1];
	   }
	   
	   
	   Namestr[0]=str1;
	   Namestr[1]=app;
	   
	   return Namestr;
    }
   
}