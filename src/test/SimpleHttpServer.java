
package test;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/*
 * a simple static http server
*/
public class SimpleHttpServer {

  public static void main(String[] args) throws Exception {
    HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
    server.createContext("/test", new MyHandler());
    server.setExecutor(null); // creates a default executor
    server.start();
    //http://127.0.0.1:8000/test
  }

  static class MyHandler implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
    	String remoteaddress=t.getRemoteAddress().toString();
    	System.out.println(remoteaddress);
    	InputStream is = t.getRequestBody(); 
    	parseGetParameters(t);
    	parsePostParameters(t);
    	BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));  
         String line;  
         String result="";
         // 读取返回的内容  
        while ((line = in.readLine()) != null) {  
             result += line;  
         }  
    	System.out.println(result);
      String response = "Welcome Real's HowTo test page";
      t.sendResponseHeaders(200, response.length());
      OutputStream os = t.getResponseBody();
      os.write(response.getBytes());
      os.close();
    }
  }
  
     private static void parseGetParameters(HttpExchange exchange)  throws UnsupportedEncodingException { 
	        Map<String, Object> parameters = new HashMap<String, Object>(); 

	        URI requestedUri = exchange.getRequestURI(); 

	        String query = requestedUri.getRawQuery(); 
	        System.out.println("get:Parameters:"+query);

	        //parseQuery(query, parameters); 

	        //exchange.setAttribute("parameters", parameters); 

	    } 
     
       private static void parsePostParameters(HttpExchange exchange) 

    	        throws IOException { 
    	        if ("post".equalsIgnoreCase(exchange.getRequestMethod())) { 

    	            @SuppressWarnings("unchecked") 

    	            Map<String, Object> parameters = 

    	                (Map<String, Object>)exchange.getAttribute("parameters"); 

    	            InputStreamReader isr = 

    	             new InputStreamReader(exchange.getRequestBody(),"utf-8"); 

    	            BufferedReader br = new BufferedReader(isr); 

    	           String query = br.readLine(); 
    	           System.out.println("post:Parameters:"+query);

    	           // parseQuery(query, parameters); 

    	        }
    	   } 
}