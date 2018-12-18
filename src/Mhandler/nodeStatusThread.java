package Mhandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class nodeStatusThread   extends Thread {
		 
	   public String IpAddress="http://127.0.0.1";//需要上报的ip
	   public String Port="8000";//代理服务端口
	   public String RemoteURL;//远程URL
	   public long MillSecond=1000*20;// 上报状态的最小时间间隔 （ms）
	   public long preveTime=0;
	   public boolean HadReported=false;// 是否已经上报过状态并正确响应
		 public nodeStatusThread(String name) {
		     super(name);
		 }
		 
		 public nodeStatusThread(){
			 super();
		}
		 public nodeStatusThread(String IpAddress, String Port,String URL){
			   super();
				this.IpAddress=IpAddress;
				this.Port=Port;
				this.RemoteURL=URL;
		}
		 
		 @Override
		 public void run() {
		     try {  
		        long i=0;
		        while (!isInterrupted()) {
		           	 sendStatusInfo();
		             long sleepTime=MillSecond;
		             Thread.sleep( sleepTime); // 休眠100ms
		             System.out.println(Thread.currentThread().getName()+" ("+this.getState()+")  " + i);  
		             System.out.println(Thread.currentThread().getName()+" ("+this.preveTime);  
		             i++;
		             System.out.println();
		         }
		     } catch (InterruptedException e) {  
		         System.out.println(Thread.currentThread().getName() +" ("+this.getState()+") catch InterruptedException.");  
		     }
		 }

		 public static String sendGetInfo(String url, Map<String, String> parameters) { 
		        String result="";
		        BufferedReader in = null;// 读取响应输入流  
		        StringBuffer sb = new StringBuffer();// 存储参数  
		        String params = null;// 编码之后的参数
		        String utf8Name="";
		        String utf8URL=url;
		        /*
		        try {
					utf8URL=java.net.URLEncoder.encode(url, "UTF-8");
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				*/
		        try {
		            // 编码请求参数  
		            if(parameters.size()==1){
		                for(String name:parameters.keySet()){
		                	  utf8Name=java.net.URLEncoder.encode(name,  "UTF-8");
		                    sb.append(utf8Name).append("=").append(
		                            java.net.URLEncoder.encode(parameters.get(name),  
		                            "UTF-8"));
		                }
		                params=sb.toString();
		            }else{
		                for (String name : parameters.keySet()) { 
		                	  utf8Name=java.net.URLEncoder.encode(name,  "UTF-8");
		                    sb.append(utf8Name).append("=").append(  
		                            java.net.URLEncoder.encode(parameters.get(name),  
		                                    "UTF-8")).append("&");  
		                     }  
		                String temp_params = sb.toString();  
		                if(temp_params.length()>=1){
		                	params = temp_params.substring(0, temp_params.length() - 1);  
		                     }
		                
		              }
		            String full_url = utf8URL;
		            if(params!=null){
		            	full_url=full_url+"?"+params;
		                }
		            
		            System.out.println(full_url); 
		            // 创建URL对象  
		            java.net.URL connURL = new java.net.URL(full_url);  
		            // 打开URL连接  
		            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL  
		                    .openConnection();  
		            // 设置通用属性  
		            httpConn.setRequestProperty("Accept", "*/*");  
		            httpConn.setRequestProperty("Connection", "Keep-Alive");  
		            httpConn.setRequestProperty("User-Agent",  
		                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");  
		            // 建立实际的连接  
		            httpConn.connect();  
		            // 响应头部获取  
		            Map<String, List<String>> headers = httpConn.getHeaderFields();  
		            // 遍历所有的响应头字段  
		            for (String key : headers.keySet()) {  
		                System.out.println(key + "\t：\t" + headers.get(key));  
		            }  
		            // 定义BufferedReader输入流来读取URL的响应,并设置编码方式  
		            in = new BufferedReader(new InputStreamReader(httpConn  
		                    .getInputStream(), "UTF-8"));  
		            String line;  
		            // 读取返回的内容  
		            while ((line = in.readLine()) != null) {  
		                result += line;  
		            }  
		        } catch (Exception e) {
		            e.printStackTrace();
		        }finally{
		            try {  
		                if (in != null) {  
		                    in.close();  
		                }  
		            } catch (IOException ex) {  
		                ex.printStackTrace();  
		            }  
		        }
		        return result ;
		    }  
		  
		 
		 
			/** 
		     * 发送post请求 
		     *  
		     * @param url 
		     *            目的地址 
		     * @param parameters 
		     *            请求参数，Map类型。 
		     * @return 远程响应结果 
		     */  
		    public static String sendPostInfo(String url, Map<String, String> parameters) { 
		    	 String result = "";// 返回的结果  
			        BufferedReader in = null;// 读取响应输入流  
			        PrintWriter out = null;  
			        StringBuffer sb = new StringBuffer();// 处理请求参数  
			        String params = "";// 编码之后的参数  
			        String utf8Name="";
			        
			        String utf8URL=url;
			        try {  
			            // 编码请求参数  
			            if (parameters.size() == 1) {  
			                for (String name : parameters.keySet()) {  
			                	 utf8Name=java.net.URLEncoder.encode(name,  "UTF-8");
			                     sb.append(utf8Name).append("=").append(
			                             java.net.URLEncoder.encode(parameters.get(name),  
			                             "UTF-8"));
			                }  
			                params = sb.toString();  
			            } else {  
			                for (String name : parameters.keySet()) {  
			                	  utf8Name=java.net.URLEncoder.encode(name,  "UTF-8");
			                      sb.append(utf8Name).append("=").append(  
			                              java.net.URLEncoder.encode(parameters.get(name),  
			                                      "UTF-8")).append("&"); 
			                }  
			                String temp_params = sb.toString();  
			                if(temp_params.length()>=1){
			                	params = temp_params.substring(0, temp_params.length() - 1);  
			                    }
			                
			            }  
			            // 创建URL对象  
			            java.net.URL connURL = new java.net.URL(url);  
			            // 打开URL连接  
			            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL  
			                    .openConnection();  
			            // 设置通用属性  
			            httpConn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			            httpConn.setRequestProperty("Accept", "*/*");  
			            httpConn.setRequestProperty("Connection", "Keep-Alive");  
			            httpConn.setRequestProperty("User-Agent",  
			                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");  
			            // 设置POST方式  
			            httpConn.setDoInput(true);  
			            httpConn.setDoOutput(true);  
			            // 获取HttpURLConnection对象对应的输出流  
			            out = new PrintWriter(httpConn.getOutputStream());  
			            // 发送请求参数  
			            //System.out.println("params:"+params);
			           // System.out.println(java.net.URLDecoder.decode(params,   "UTF-8"));
			            out.write(params);  
			            // flush输出流的缓冲  
			            out.flush();  
			            // 定义BufferedReader输入流来读取URL的响应，设置编码方式  
			            in = new BufferedReader(new InputStreamReader(httpConn  
			                    .getInputStream(), "UTF-8"));  
			            String line;  
			            // 读取返回的内容  
			            while ((line = in.readLine()) != null) {  
			                result += line;  
			            }  
			        } catch (Exception e) {  
			            e.printStackTrace();  
			        } finally {  
			            try {  
			                if (out != null) {  
			                    out.close();  
			                }  
			                if (in != null) {  
			                    in.close();  
			                }  
			            } catch (IOException ex) {  
			                ex.printStackTrace();  
			            }  
			        }  
			        return result;  
		    }  
		    
		    public boolean willReportStatus(long t){
		    	boolean b=false;
		    	if(!HadReported){
		    		return true;
		    	}else{
		    		//long t=System.currentTimeMillis();
		    		if((t-preveTime)>=MillSecond){
		    			b=true;
		    		}
		    	}
		    	return b;
		    }
		    
		    
		    public void sendStatusInfo(){
		    	 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		    	 Date HTime=null;
		    	
		    	System.out.println("发送状态");
		      Map<String, String> parameters = new HashMap<String, String>();  
				// String ipValue=IpAddress+":"+Port;
		      String ipValue=IpAddress;
				System.out.println(ipValue);
			   parameters.put("IP", ipValue);  
			   long t=System.currentTimeMillis();
				
		    	boolean b=willReportStatus(t);
		    	//System.out.println(t+":"+b);
		    	HTime=new Date(t);
	   	   String stime=HTime.toString();
	   	   
		    	if(b){
		    		   String result =sendPostInfo(RemoteURL, parameters);
		    		   //String result =sendGetInfo(RemoteURL, parameters);
		    		   if(result==null){
		    		    	result="0";
		    		    }
		    		   System.out.println(stime+"*****result*******"+result);
		    		   System.out.println(result);
		    		   //boolean sendResult=Boolean.valueOf(result);
		    		   boolean sendResult=(result.equals("1"));
		    		   System.out.println("sendResult="+sendResult);
		    	     //System.out.println(result);
		    	    // sendResult=Boolean.valueOf(result);
		    	    if(sendResult){
		    	    	this.HadReported=true;
		    		   this.preveTime=t;
		    	     }
		    	}
			    
			    
		    }
}
