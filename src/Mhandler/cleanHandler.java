package Mhandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import net.sf.json.JSONObject;
import process.clean;



public class cleanHandler implements HttpHandler {
	//http://192.168.1.28:10000/cleanServer
	public static void main(String[] args) throws Exception {
	    HttpServer server = HttpServer.create(new InetSocketAddress(9090), 0);
	    server.createContext("/testClean", new cleanHandler());
	    server.setExecutor(null); // creates a default executor
	    server.start();
	    //http://192.168.1.28:9090/testClean
	  }
	
    public void handle(HttpExchange t) throws IOException {
    	String remoteaddress=t.getRemoteAddress().toString();
    	 Headers headers = t.getResponseHeaders();
    	
    	//t.getResponseHeaders()
    	System.out.println(remoteaddress);
    	boolean b=false;
     String response = "";
    	
    	if ("post".equalsIgnoreCase(t.getRequestMethod())){
    		System.out.println("post:");
    		b=doPost(t);
    	}else{
    		System.out.println("get:Now not Support");
    		
    		//b=doGet(t);
    	}
    	 //返回结果
    	
      	 

     	  //返回结果
       	if(b){
       		response="1";
       	}else{
       		response="-1";
       	}
  	 
      try {
			t.sendResponseHeaders(200, response.length());
		 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
      
      OutputStream os = t.getResponseBody();
      try {
			os.write(response.getBytes());
		 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
      try {
			os.close();
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
  	
      
    }
    public boolean doGet(HttpExchange t){
    	 boolean b=false;
    	try {
			parseGetParameters(t);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	 String response = "";
    	 
    	 
    	String Key1="QXFSNM";
  
    	String Value1=null;
    	
    	  String Key2="port";
    	  
    	  String Value2=null;
    
    	Map<String, Object> parameters=(Map<String, Object>) t.getAttribute("parameters");
    	
    	if( parameters==null ||parameters.size()<1 ){
    		System.out.println("参数个数不符合要求");
    	}else{
    		
    		Object obj1 = parameters.get(Key1); 
    		Object obj2 = parameters.get(Key2); 
    		
    		if(obj1!=null ){
    			   if(obj1 instanceof List<?>) { 
    				     List<String> values = (List<String>)obj1; 
    				    Value1=values.get(0);
    				    
    			    } else if(obj1 instanceof String) { 
    			    	 Value1=(String)obj1;
    			     } 
    			 
    		}
    		
    		if(obj2!=null ){
 			   if(obj2 instanceof List<?>) { 
 				     List<String> values = (List<String>)obj2; 
 				    Value2=values.get(0);
 				    
 			    } else if(obj1 instanceof String) { 
 			    	 Value2=(String)obj2;
 			     } 
 		    	 
 		}
 		
    		
    		if(Value1==null ||  Value2==null){
    			System.out.println("参数值不符合要求");
    		}else{
    			System.out.println(Key1+":"+Value1);
    			System.out.println(Key2+":"+Value2);
    			
    			b=isNumeric(Value1);
    	    	if(b){
    	    		 clean clean_get =new clean();
    	    	    b=clean_get.work(Value1,Value2);
    	    	    
    	    	}else{
    	    		System.out.println("参数值必须是整数字符串");
    	    	}
    		}  
    		
    		
    	}
    	
    	 return b;
     }
    public boolean doPost(HttpExchange t){
    	boolean b=false;
    	try {
			parsePostParameters(t);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
	  String response = "";
	  
	 
	  String Key1="QXFSNM";
  
	  String Value1=null;

	  String Key2="port";
	  
	  String Value2=null;
	  
	  
	HashMap<String, Object> parameters=(HashMap<String, Object>) t.getAttribute("parameters");
	
	if( parameters==null || parameters.size()<1 ){
		System.out.println("参数个数不符合要求");
	}else{
		
		Object obj1 = parameters.get(Key1); 
		Object obj2 = parameters.get(Key2); 
		
		if(obj1!=null ){
			   if(obj1 instanceof List<?>) { 
				     List<String> values = (List<String>)obj1; 
				     Value1=values.get(0);
			    } else if(obj1 instanceof String) { 
			    	 Value1=(String)obj1;
			     } else{
			    	 Value1=(String) obj1;
			     }
			
		}
		
		if(obj2!=null ){
			   if(obj2 instanceof List<?>) { 
				     List<String> values = (List<String>)obj2; 
				     Value2=values.get(0);
			    } else if(obj1 instanceof String) { 
			    	 Value2=(String)obj2;
			     } else{
			    	 Value2=(String) obj2;
			     }
			 
		}
		
		if( Value1==null || Value2==null){
			System.out.println("参数值不符合要求");
			b=false;
		}else{
			System.out.println("paraments:");
			System.out.println("QXFSNM:"+Value1);
			System.out.println("port:"+Value2);
			
			b=isNumeric(Value1);
			if(b){
				clean clean_post =new clean();
			   b=clean_post.work(Value1,Value2);
			}else{
				System.out.println("参数值必须是整数字符串");
			}
			
		}
	}
	

	return b;
  }
    
    private static void parseGetParameters(HttpExchange exchange)  throws UnsupportedEncodingException { 
        Map<String, Object> parameters = new HashMap<String, Object>(); 

        URI requestedUri = exchange.getRequestURI(); 

        String query = requestedUri.getRawQuery(); 
        
        //query=java.net.URLDecoder.decode(query,   "UTF-8");
        System.out.println("get:query:"+query);
        parseQuery(query, parameters); 

        exchange.setAttribute("parameters", parameters); 

    } 
    
    private static void parsePostParameters(HttpExchange exchange) 

	        throws IOException { 
	        if ("post".equalsIgnoreCase(exchange.getRequestMethod())) { 

	            

	        	   Map<String, Object> parameters = new HashMap<String, Object>(); 
	            InputStreamReader isr = 

	             new InputStreamReader(exchange.getRequestBody(),"utf-8"); 

	            BufferedReader br = new BufferedReader(isr); 

	           String query = br.readLine(); 
	           //query=java.net.URLDecoder.decode(query,   "UTF-8");
	           System.out.println("post:Parameters:"+query);
	           parseJsonQuery(query,parameters);
	            //parseQuery(query, parameters);
	            exchange.setAttribute("parameters", parameters); 

	        }
	   } 
    
     public cleanHandler(){
    	super();
      }
     
     @SuppressWarnings("unchecked") 
     private static void parseJsonQuery(String query, Map<String, Object> parameters) throws UnsupportedEncodingException {
     	 System.out.println("parse:file.encoding:"+System.getProperty("file.encoding"));
     	     
     	 if (query != null) { 
     		JSONObject jb = JSONObject.fromObject(query);
     		
     		 Map<String, Object>  parametermap= (Map<String, Object>)jb;
     		 parameters.putAll(parametermap);
     		    
     	      } 
     	     
      }
     @SuppressWarnings("unchecked") 
    private static void parseQuery(String query, Map<String, Object> parameters) throws UnsupportedEncodingException {
    	 System.out.println("parse:file.encoding:"+System.getProperty("file.encoding"));
    	
    	 if (query != null) { 
    	        	String pairs[] = query.split("[&]");
    	        	for (String pair : pairs){ 
    	        	  String param[] = pair.split("[=]"); 
    	        	  String key = null;
    	        	  String value = null; 
    	        	  if (param.length > 0) {
    	        		 // key = URLDecoder.decode(param[0],System.getProperty("file.encoding")); 
    	        		  key = param[0]; 
    	        		  value = URLDecoder.decode(key, "utf-8");
    	        		  System.out.println("key="+key);
    	        	  } 
    	        	  if (param.length > 1) { 
    	        		  //value = URLDecoder.decode(param[1], System.getProperty("file.encoding"));
    	        		  
    	        		  //System.out.println(value);
    	        		  value = URLDecoder.decode(param[1], "utf-8");
    	        		  System.out.println("value="+value);
    	        	  } 
    	        	  
    	        	  if (parameters.containsKey(key)) { 
    	        		    Object obj = parameters.get(key); 
    	        		    if(obj instanceof List<?>) { 
    	        			   List<String> values = (List<String>)obj; 
    	        			   values.add(value); 
    	        			  
    	        		    } else if(obj instanceof String) { 
    	        			   List<String> values = new ArrayList<String>(); 
    	        			   values.add((String)obj); 
    	        			   values.add(value); 
    	        			   parameters.put(key, values); 
    	        		     } 
    	        	     } 
    	        	   else { 
    	        		   parameters.put(key, value); 
    	        		  
    	        	    } 
    	        	 }
    	        } 
    	     
     }
   
     public boolean isNumeric(String str){
         Pattern pattern = Pattern.compile("[0-9]*");
         Matcher isNum = pattern.matcher(str);
         if( !isNum.matches() ){
             return false;
         }
         return true;
      }
}
