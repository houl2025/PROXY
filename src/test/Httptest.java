package test;

    /** 
     * 发送GET请求 
     *  
     * @param url 
     *            目的地址 
     * @param parameters 
     *            请求参数，Map类型。 
     * @return 远程响应结果 
     */  

	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStreamReader;
	import java.io.PrintWriter;
	import java.io.UnsupportedEncodingException;
	import java.util.HashMap;
	import java.util.List;
	import java.util.Map;
	import java.util.Map.Entry;


	/**
	 * 用于模拟HTTP请求中GET/POST方式 
	 * @author landa
	 *
	 */
	public class Httptest {  
	    /** 
	     * 发送GET请求 
	     *  
	     * @param url 
	     *            目的地址 
	     * @param parameters 
	     *            请求参数，Map类型。 
	     * @return 远程响应结果 
	     */  
	    public static String sendGet(String url, Map<String, String> parameters) { 
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
	     * 发送POST请求 
	     *  
	     * @param url 
	     *            目的地址 
	     * @param parameters 
	     *            请求参数，Map类型。 
	     * @return 远程响应结果 
	     */  
	    public static String sendPost(String url, Map<String, String> parameters) {  
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
    /** 
     * 主函数，测试请求 
     *  
     * @param args 
     */  
    public static void main(String[] args) {  
        Map<String, String> parameters = new HashMap<String, String>();  
        //parameters.put("name", "sarin"); 
    
        //String result =sendGet("http://192.168.1.15:8080/nodeMgr/program/节点管理.rar", parameters);
        //String result =sendPost("http://192.168.1.15:8080/nodeMgr/program/节点管理.rar", parameters);
      //String result =sendPost("http://127.0.0.1:8080/nodeMgr/json.action", parameters);
     // String result =sendGet("http://127.0.0.1:8080/nodeMgr/json.action", parameters);
      String result =sendGet("http://192.168.214.1:8080/nodeMgr/json.action", parameters);
        System.out.println(result); 
    }  
}