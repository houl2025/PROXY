package Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.List;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import process.deploy;


 
public class commandUtils {
 
    public static void main(String[] args) {
    	InputStream in = commandUtils.class.getClassLoader().getResourceAsStream("config.properties");
 	  
    	//getSystemProperties();
      
     //String command = "/usr/local/apache-tomcat-7.0.81/bin/startup.sh";//关闭tomcat命令
       
    	//String command = "/usr/local/apache-tomcat-7.0.81/bin/shutdown.sh";//关闭tomcat命令
    	String command = "/usr/local/apache-tomcat-8.5.20/shutdown.sh";
      String command2 ="shutdown -r -f -t 1";
       
     commandUtils.callCommand(command2);
        
         
    //	test();
    	System.out.println("ver");
    	boolean b=isLocalPortUsing(8080);
    	System.out.println("b="+b);
    	String type="1";
    	//b=restart( type);
    	System.out.println("restart="+b);
    }
     
    /**
     * 执行命令
     *
     * @throws IOException
     */
    private static boolean callCommand(String command)  {
         
        Runtime runtime = Runtime.getRuntime();//返回与当前的Java应用相关的运行时对象
        //指示Java虚拟机创建一个子进程执行指定的可执行程序，并返回与该子进程对应的Process对象实例
        Process process=null;
		 try {
			process = runtime.exec(command);
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }finally{
			  if( process==null){
				  return false;
			  }
		  }
        runtime.gc();//运行垃圾回收器
        
        String line = null;
        String content = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        try {
			while((line = br.readLine()) != null) {
			    content += line + "\r\n";
			 }
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
        System.out.println(content);
         
        return true;
    }
    
    public static String getHtmlContext(String tempurl, String username, String password)  {
        URL url = null;
        BufferedReader breader = null;
        InputStream is = null;
        StringBuffer resultBuffer = new StringBuffer();
       
            try {
				url = new URL(tempurl);
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            if(url==null){
            	   return null;
                }
            String userPassword = username + ":" + password;
            String encoding = encryptBASE64 (userPassword.getBytes());//
 
            HttpURLConnection conn=null;
            
			try {
				conn = (HttpURLConnection) url.openConnection();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 if(conn==null){
				return null;
			 }
			 
          conn.setRequestProperty("Authorization", "Basic " + encoding);
          try {
				is = conn.getInputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(is==null){
	        	    return null;
	              }
			}
          
            breader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            boolean r=true;
            while (r) {
            	line=null;
              try {
					line = breader.readLine();
				  } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				  }finally{
					  if(line==null){
		            		r=false;
		            }else{
		           	  resultBuffer.append(line);
		            	}
				  }
             
                
                }
       
           if(breader != null){
            	 try {
					breader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
               }
               
            if(is != null){
            	 try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
               }
            
        return resultBuffer.toString();
    }
    
    /**
     * BASE64Encoder 加密
     * 
     * @param data
     *            要加密的数据
     * @return 加密后的字符串
     */
    public static String encryptBASE64(byte[] data) {
        // BASE64Encoder encoder = new BASE64Encoder();
        // String encode = encoder.encode(data);
        // 从JKD 9开始rt.jar包已废除，从JDK 1.8开始使用java.util.Base64.Encoder
        Encoder encoder = Base64.getEncoder();
        String encode = encoder.encodeToString(data);
        return encode;
    }
    /**
     * BASE64Decoder 解密
     * 
     * @param data
     *            要解密的字符串
     * @return 解密后的byte[]
     * @throws Exception
     */
    public static byte[] decryptBASE64(String data) throws Exception {
        // BASE64Decoder decoder = new BASE64Decoder();
        // byte[] buffer = decoder.decodeBuffer(data);
        // 从JKD 9开始rt.jar包已废除，从JDK 1.8开始使用java.util.Base64.Decoder
        Decoder decoder = Base64.getDecoder();
        byte[] buffer = decoder.decode(data);
        return buffer;
    }
    public static void test() {
        String result = "";
        Document document = null;//引入org.dom4j包
        
          result = getHtmlContext("http://localhost:8080/manager/status?XML=true", "admin", "tomcat");
         
          
          
          if(result!=null){
        	  try {
      			document = DocumentHelper.parseText(result);
      		} catch (DocumentException e) {
      			// TODO Auto-generated catch block
      			e.printStackTrace();
      		   }//将字符串转化为XML的Document
              
              System.out.println(document.asXML());
            }else{
            	System.out.println("result==null");
            }
        
    }
    
     public static boolean restart(String type){
    	  InputStream in = commandUtils.class.getClassLoader().getResourceAsStream("config.properties");
      	  Properties properties = new Properties();
      	   try {
      			properties.load(in);
      		} catch (IOException e) {
      			// TODO Auto-generated catch block
      			e.printStackTrace();
      	 }
    	 boolean restartResult=false;
    	 int itype=Integer.valueOf(type);
    	 List<String > sys=getSystemProperties();
    	 String osName=sys.get(0);
    	 String startCommandKey=osName+"_startup_Type"+itype;
    	 String shutCommandKey=osName+"_shutdown_Type"+itype;
    	 String startCommand=properties.getProperty(startCommandKey);
    	 String shutCommand=properties.getProperty(shutCommandKey);
    	 int serverType=Integer.valueOf(type);
       String portkey="serverPort_Type"+serverType;
       String portValue=properties.getProperty(portkey);
       if(portValue==null){
    	   return false;
         }
       int server_Port=Integer.valueOf(portValue);
    	 boolean alive=false;
    	 
    	alive=isLocalPortUsing(server_Port);
    	System.out.println("Fisrt:alive="+alive);
    	int Num=3;
    	int try_Num=0;
    	boolean commandR=false;
    	while(alive && try_Num<Num){
    		
    		
    			
    		System.out.println("shut :tryNum:"+try_Num+":"+alive);
    		commandR=callCommand(shutCommand);
    		try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   
    		alive=isLocalPortUsing(server_Port);
    		try_Num++;
    	}
    	 
    	if(alive){
    		return false;
    	}
    	
    	try_Num=0;
    	commandR=false;
    	while((!alive )&& try_Num<Num){
    		
    		System.out.println("start :tryNum:"+try_Num+":"+alive);
    		commandR=callCommand(startCommand);
    		try_Num++;
    		try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		alive=isLocalPortUsing(server_Port);
    	}
    	if(!alive){
    		return false;
    	}
    	restartResult=alive;
    	 return restartResult;
     }
     
     public static boolean restart(String startCommand, String shutCommand,String port){
   	 boolean restartResult=false;
   	 boolean alive=false;
   	int server_Port=Integer.valueOf(port);
   	alive=isLocalPortUsing(server_Port);
   	System.out.println("Fisrt:alive="+alive);
   	int Num=3;
   	int try_Num=0;
   	boolean commandR=false;
   	while(alive && try_Num<Num){
   		
   		
   			
   		System.out.println("shut :tryNum:"+try_Num+":"+alive);
   		commandR=callCommand(shutCommand);
   		try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   
   		alive=isLocalPortUsing(server_Port);
   		try_Num++;
   	}
   	 
   	if(alive){
   		return false;
   	}
   	
   	try_Num=0;
   	commandR=false;
   	while((!alive )&& try_Num<Num){
   		
   		System.out.println("start :tryNum:"+try_Num+":"+alive);
   		commandR=callCommand(startCommand);
   		try_Num++;
   		try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
   		alive=isLocalPortUsing(server_Port);
   	}
   	if(!alive){
   		return false;
   	}
   	restartResult=alive;
   	 return restartResult;
    }
     
     public static List<String> getSystemProperties(){
    	   List<String> sysPro=new ArrayList<>();
    	   Properties props=System.getProperties(); //获得系统属性集    
    	   String osName = props.getProperty("os.name"); //操作系统名称    
    	   System.out.println(osName);
    	   sysPro.add(osName);
    	   return  sysPro;
      }
     
     
     public static boolean IsAlive(String type,String osName){
    	  boolean Alive=false;
    	  InputStream in = commandUtils.class.getClassLoader().getResourceAsStream("config.properties");
   	  Properties properties = new Properties();
   	   try {
   			properties.load(in);
   		} catch (IOException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		  }
   	  int serverType=Integer.valueOf(type);
   	  String key="serverPort_Type"+serverType;
   	  int server_port=Integer.valueOf(properties.getProperty(key));
    	  Alive=isLocalPortUsing( server_port );
    	 
    	 return Alive;
      }
     
     /**
      * 测试本机端口是否被使用
      * @param port
      * @return
      */
     public static boolean isLocalPortUsing(int port){  
         boolean flag = false;  
         try {
             //如果该端口还在使用则返回true,否则返回false,127.0.0.1代表本机
             flag = isPortUsing("127.0.0.1", port);  
         } catch (Exception e) {  
         }  
         return flag;  
     }  
     /*** 
      * 测试主机Host的port端口是否被使用
      * @param host 
      * @param port 
      * @throws UnknownHostException  
      */ 
     public static boolean isPortUsing(String host,int port) throws UnknownHostException{  
         boolean flag = false;  
         InetAddress Address = InetAddress.getByName(host);  
         try {  
             Socket socket = new Socket(Address,port);  //建立一个Socket连接
             flag = true;  
         } catch (IOException e) {  
  
         }  
         return flag;  
     } 
}
