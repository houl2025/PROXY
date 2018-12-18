package process;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import Utils.commandUtils;
import Utils.container;
import Utils.documentUtils;
import Utils.downUtils;
import Utils.parseProxyConf;

public class clean {

	  public static void main(String[] args) {
		// TODO Auto-generated method stub

	  }
    
	
	   public boolean work(String QXFSNMType){
		   boolean b=false;
		   int iQXFSNMType=Integer.valueOf(QXFSNMType);
		  
		   if(iQXFSNMType==1){
			   String commandType=String.valueOf(iQXFSNMType);
			   b= commandUtils.restart(commandType);
		   }
		   
			System.out.println("b:"+b);
		   return b;
	   }
	   
	   public boolean work(String QXFSNMType,String port){
		   boolean b=false;
		   
		   InputStream in = deploy.class.getClassLoader().getResourceAsStream("config.properties");
	  	   Properties properties = new Properties();
	  	   try {
	  			properties.load(in);
	  		} catch (IOException e) {
	  			// TODO Auto-generated catch block
	  			e.printStackTrace();
	  		}
	  	   
	  	
	  	   
	  	  // String user_dir=System.getProperty("user.dir") ;
	  	   
	  	   
	  	   String proxyconf = properties.getProperty("proxy_conf") ;
	  	    
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
	  	    if(!hasFind){
	  	    	return false;
	  	    }
	  	    
		   int iQXFSNMType=Integer.valueOf(QXFSNMType);
		   
		  
		   if(iQXFSNMType==1){
			   
			   String startCommand= MyContainer.getCommand("startup");
			   String shutCommand=MyContainer.getCommand("shutdown");
			 System.out.println("startCommand="+startCommand);
			 System.out.println("shutCommand="+shutCommand);
			   b= commandUtils.restart(startCommand,shutCommand,port);
		   }
		   
			System.out.println("b:"+b);
		   return b;
	   }
}
