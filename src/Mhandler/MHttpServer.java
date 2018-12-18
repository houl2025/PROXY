package Mhandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.sun.net.httpserver.HttpServer;

import Utils.commandUtils;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


public class MHttpServer {
   public static String IpAddress="http://127.0.0.1";
	public static String Port="10000";
	public static String deployServerPath="/deployServer";
	public static String cleanServerPath="/deployServer";
  
   private List<nodeStatusThread> nodeStatusThreads=new ArrayList<>();
   public  HttpServer server;
	public static void main(String[] args) {
		
		   
	    	 System.out.println("user_dir:" + System.getProperty("user.dir"));     
	    	 
		  InputStream in = MHttpServer.class.getClassLoader().getResourceAsStream("config.properties");
      	  Properties properties = new Properties();
      	   try {
      			properties.load(in);
      		} catch (IOException e) {
      			// TODO Auto-generated catch block
      			e.printStackTrace();
      	         }
		 // TODO Auto-generated method stub
		   //http://127.0.0.1:8000/cleanServer?clwjFile=clwjFile&yycxFile=yycxFile
		   //http://127.0.0.1:8000/deployServer?type=0&yycxFile=yycxFile
		  //http://127.0.0.1:8080/nodeMgr/json.action?ip=127.0.0.1:8000
      	// http://127.0.0.1:8080/nodeMgr/servlet/serlvetjsondemo
      	   
      	   
      	   
		    MHttpServer aMCOEHttpServer=new MHttpServer();
		    boolean b=false;
		   
		      
		   aMCOEHttpServer.IpAddress= MHttpServer.getNonLocalHostIP () ;
		   String proxyconf = properties.getProperty("proxy_conf") ;
		   File f=new File(proxyconf);
		   System.out.println("xml 配置文件路径："+proxyconf);
	      System.out.println("xml 配置文件存在？："+f.exists());
		    
		    String proxy_portKey="proxy_port";
		    
		    String proxy_port=properties.getProperty(proxy_portKey);
		    if(proxy_port!=null){
		    	aMCOEHttpServer.Port=proxy_port;
		    }
		    
		    //String  Address=IpAddress+":"+Port;
		    String  Address=IpAddress;
		    int iPort=Integer.valueOf(Port);
		    
		    System.out.println("本地代理服务端口："+IpAddress+":"+iPort);
		    
		    HttpServer aserver=null;
			
			try {
				aserver = HttpServer.create(new InetSocketAddress(iPort), 0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 if(aserver!=null){
				    aMCOEHttpServer.setHttpServer(aserver);
				    deployServerPath=properties.getProperty("deployServerPath");
				    cleanServerPath=properties.getProperty("cleanServePath");
				    
				    System.out.println("deployServerPath："+deployServerPath);
				    System.out.println("cleanServerPath："+cleanServerPath);
				    
				    aMCOEHttpServer.server.createContext( deployServerPath, new deployHandler());
				    aMCOEHttpServer.server.createContext(cleanServerPath, new cleanHandler());
				    aMCOEHttpServer.server.setExecutor(null); // creates a default executor
				    aMCOEHttpServer.server.start();
				    
				
				    String URL="http://127.0.0.1:8080/nodeMgr/servlet/servletDemo1";
				    String R_urlKey="RemoteUrl";
				    String Remote_url=properties.getProperty(R_urlKey);
				    if( Remote_url!=null){
				    	URL= Remote_url;
				    }
				    
				    
				    System.out.println("管理服务端口："+URL);
				    nodeStatusThread nst=new nodeStatusThread(Address,Port,URL);
				    
				    nst.setName("statusMonitor");
				    String statusTimeGap=properties.getProperty("statusTimeGap");
				    
				    int TimeGap=Integer.valueOf(statusTimeGap);
				    
				    nst.MillSecond=TimeGap;
				    System.out.println("状态时间间隔："+nst.MillSecond +"ms");
				    
				    
				    aMCOEHttpServer.addNodeStatusThread(nst);
				   
				    aMCOEHttpServer.start();
				   /*
				    while(true){
					   System.out.println( "main:preveTime="+aMCOEHttpServer.nodeStatusThreads.get(0).preveTime);
				        try {
							Thread.sleep(7*1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				       
				    }
				    
				    */
					
			 }else{
				 System.out.println("初始化 httpServer 服务失败");
			 }
		   
	}
	

	public void addNodeStatusThread(nodeStatusThread nst){
		nodeStatusThreads.add(nst);
	}
	public void setHttpServer(HttpServer server){
		this.server=server;
	}
	
	
	
	public void start(){
		for(int i=0;i<nodeStatusThreads.size();i++){
			nodeStatusThreads.get(i).start();
		
		}
	}
	private static String getIpAddress() throws UnknownHostException {  
        InetAddress address = InetAddress.getLocalHost();  

        return address.getHostAddress();  
   }  
	public static String getNonLocalHostIP (){
		  String ip="127.0.0.1";
		    try {
		        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
		            NetworkInterface intf = en.nextElement();
		            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
		                InetAddress inetAddress = enumIpAddr.nextElement();
		                if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
		                         String str=inetAddress.getHostAddress().toString();
		                        if(!str.equals("127.0.0.1")){
		                        	ip=inetAddress.getHostAddress().toString();
		                               }
		                	
		                }

		            }
		        }
		    } catch (SocketException ex) {
		    }
		    return ip;
	 }
	public static boolean checkFileStatus(String Fname,boolean stopCondition ){
		boolean r=false;
		return r;
	}
}
