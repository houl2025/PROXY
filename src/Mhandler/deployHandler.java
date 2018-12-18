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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import process.deploy;

public class deployHandler implements HttpHandler {
	
	public static void main(String[] args) throws Exception {
		//http://192.168.1.28:10000/deployServer
	    HttpServer server = HttpServer.create(new InetSocketAddress(9190), 0);
		server.createContext("/testdeploy", new deployHandler());
		//HttpServer server = HttpServer.create(new InetSocketAddress(10000), 0);
	   // server.createContext("/deployServer", new deployHandler());
	    server.setExecutor(null); // creates a default executor
	    server.start();
	  
	    //http://192.168.1.28:9190/testDeploy
	  }
	
	
    public void handle(HttpExchange t) throws IOException {
    	String b="-1";
    	String remoteaddress=t.getRemoteAddress().toString();
    	System.out.println(remoteaddress);
    
    	if ("post".equalsIgnoreCase(t.getRequestMethod())){
    		System.out.println("post:");
    		b=doPost(t);
    	}else{
    		System.out.println("get:Now not Support");
    		//b=doGet(t);
    	}
    
    	 String response="-1";
      	 

  	    if(b!=null){
  		   response=b;
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
    public String doGet(HttpExchange t){
    	  boolean r=false;
    	  String b="-1";
    	try {
			parseGetParameters(t);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
  	  String response = "-1";
  	
  	 
  	 String Key1="strategyFile";
    String Key2="programFile";
  	String Key3="webSvrType";
	String Key4="appName";
	String Key5="port";
  	String strategyFileValue=null;
  	String programFileValue=null;
  
  	String portValue=null;
  
  	Map<String, Object> parameters=(Map<String, Object>) t.getAttribute("parameters");
  	
  	if( parameters==null  || parameters.size()<1){
  		System.out.println("参数个数不符合要求");
  	}else{
  		
  		Object obj1 = parameters.get(Key1); 
  		Object obj2 = parameters.get(Key2); 
  		Object obj3 = parameters.get(Key3); 
  		Object obj4 = parameters.get(Key4); 
  		Object obj5 = parameters.get(Key5); 
 	   if(obj1!=null){
			 if(obj1 instanceof List<?>) { 
			     List<String> values = (List<String>)obj1; 
			     strategyFileValue=values.get(0);
		    } else if(obj1 instanceof String) { 
		    	 strategyFileValue=(String)obj1;
		     } 
		   }
		   
		 if(obj2!=null){
			if(obj2 instanceof List<?>) { 
				 List<String> values = (List<String>)obj2; 
				 programFileValue=values.get(0);
			  } else if(obj1 instanceof String) { 
				  programFileValue=(String)obj2;
			      } 
		 }
		    
		  
		   
		  
		    if(obj5!=null){
		    	if(obj5 instanceof List<?>) { 
	 				 List<String> values = (List<String>)obj5; 
	 				 portValue=values.get(0);
	 			  } else if(obj1 instanceof String) { 
	 				   portValue=(String)obj5;
	 			      } 
		    }
		    
		    
  		if(  programFileValue==null ||  portValue==null){
  			System.out.println("参数值不符合要求");
  	
  		}else{
  			System.out.println("paraments");
  			System.out.println(strategyFileValue);
  			
  			System.out.println(portValue);
  			r=true;
  			
  				System.out.println("deploy");
      	    	deploy adeploy=new deploy();
      	    	b=adeploy.work(programFileValue,portValue);
  			
  			
  			
  		}  
  	}
  	
  	 
  	 
  	 return b;
     }
    public String doPost(HttpExchange t){
    
    	 String b="-1";
    		try {
				parsePostParameters(t);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
    		
    	
    	  	 
    	  	String Key1="strategyFile";
    	   String Key2="programFile";
    	  	String Key3="webSvrType";
    		String Key4="appName";
    		String Key5="port";
    	  	String Value1=null;
    	  	String Value2=null;
    	  	String Value3=null;
    	  	String Value4=null;
    	  	String Value5=null;
    	  
    	  	HashMap<String, Object> parameters=(HashMap<String, Object>) t.getAttribute("parameters");
    	
    	  	if( parameters==null  || parameters.size()<1){
    	  		System.out.println("line204:参数个数不符合要求");
    	  	}else{
    	  		
    	  		Object obj1 = parameters.get(Key1); 
    	  		Object obj2 = parameters.get(Key2); 
    	  		Object obj3 = parameters.get(Key3); 
    	  		Object obj4 = parameters.get(Key4); 
    	  		Object obj5 = parameters.get(Key5); 
    	  		
    	  			 if(obj1!=null){
    	  				 if(obj1 instanceof List<?>) { 
    	  				     List<String> values = (List<String>)obj1; 
    	  				     Value1=values.get(0);
    	  			    } else if(obj1 instanceof String) { 
    	  			    	 Value1=(String)obj1;
    	  			     } else{
    	  			    	 Value1=(String)obj1;
    	  			     }
    	  			   }
    	  			   
    	  			 if(obj2!=null){
    	  				if(obj2 instanceof List<?>) { 
   	     				 List<String> values = (List<String>)obj2; 
   	     				 Value2=values.get(0);
   	     			  } else if(obj1 instanceof String) { 
   	     				Value2=(String)obj2;
   	     			     } else{
	        			    	 Value2=(String)obj2;
	        			      }
    	  			 }
    	  			    
    	  			  if(obj3!=null){
    	  				 if(obj3 instanceof List<?>) { 
	        				 List<String> values = (List<String>)obj3; 
	        				 Value3=values.get(0);
	        			  } else if(obj1 instanceof String) { 
	        				   Value3=(String)obj3;
	        			     } else{
	        			    	 Value3=(String)obj3;
	        			      }
    	  			    }
    	  			   
    	  			    if(obj4!=null){
    	  			    	if(obj4 instanceof List<?>) { 
    	    	 				 List<String> values = (List<String>)obj4; 
    	    	 				 Value4=values.get(0);
    	    	 			  } else if(obj1 instanceof String) { 
    	    	 				   Value4=(String)obj4;
    	    	 			      }  else{
    		        			    	 Value4=(String)obj4;
    	        			      }
    	  			    }
    	  			  
    	  			   if(obj5!=null){
   	  			    	if(obj5 instanceof List<?>) { 
   	    	 				 List<String> values = (List<String>)obj5; 
   	    	 				 Value5=values.get(0);
   	    	 			  } else if(obj1 instanceof String) { 
   	    	 				   Value5=(String)obj5;
   	    	 			      }  else{
   		        			    	 Value5=(String)obj5;
   	        			      }
   	  			    }
    	  		
    	  		if( Value2==null  || Value5==null){
    	  			System.out.println("参数值不符合要求");
    	  	
    	  		}else{
    	  			System.out.println("paraments");
    	  			//System.out.println(Value1);
    	  			System.out.println(Value2);
    	  			//System.out.println(Value3);
    	  			//System.out.println(Value4);
    	  			System.out.println(Value5);
    	  	
    	  		
    	  			System.out.println("deploy");
    	      	deploy adeploy=new deploy();
    	      	b=adeploy.work(Value2,Value5);
    	  			
    	  			
    	  			
    	  		}  
    	
    	  	}
    	  	 return b;
    	
    }
    
    private static void parseGetParameters(HttpExchange exchange)  throws UnsupportedEncodingException { 
        Map<String, Object> parameters = new HashMap<String, Object>(); 

        URI requestedUri = exchange.getRequestURI(); 

        String query = requestedUri.getRawQuery(); 
        
        //query=java.net.URLDecoder.decode(query,   "UTF-8");
        System.out.println("get:query*****:"+query);
        parseQuery(query, parameters); 

        exchange.setAttribute("parameters", parameters); 

    } 
    
    
      private static void parsePostParameters(HttpExchange exchange) 

	        throws IOException { 
	        if ("post".equalsIgnoreCase(exchange.getRequestMethod())) { 

	            

	        	   Map<String, Object> parameters = new HashMap<String, Object>(); 
	          //	Map<String, Object> parameters = new Map<String, Object>(); 
	            InputStreamReader isr = 

	           new InputStreamReader(exchange.getRequestBody(),"utf-8"); 

	            BufferedReader br = new BufferedReader(isr); 

	           String query = br.readLine(); 
	          // query=java.net.URLDecoder.decode(query,   "UTF-8");
	           System.out.println("post:Parameters:"+query);

	            //parseQuery(query, parameters);
	           parseJsonQuery(query,parameters);
	           exchange.setAttribute("parameters", parameters); 
	           //System.out.println(parameters.get("webSvrType"));

	        }
	   } 
      public deployHandler (){
    	  super();
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
      	        		   //System.out.println("294");
      	        	    } 
      	        	 }
      	        } 
      	     
       }
     
      @SuppressWarnings("unchecked") 
      private static void parseJsonQuery(String query, Map<String, Object> parameters) throws UnsupportedEncodingException {
      	 System.out.println("parse:file.encoding:"+System.getProperty("file.encoding"));
      	     
      	 if (query != null) { 
      		JSONObject jb = JSONObject.fromObject(query);
      		
      		 Map<String, Object>  parametermap= (Map<String, Object>)jb;
      		 parameters.putAll(parametermap);
      		    //System.out.println(" parameters="+ parameters.get("webSvrType"));
      		   //System.out.println(" parameters.size="+ parameters.size());
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