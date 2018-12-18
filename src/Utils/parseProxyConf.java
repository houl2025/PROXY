package Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;  
import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.parsers.ParserConfigurationException;  
  
import org.w3c.dom.Document;  
import org.w3c.dom.NamedNodeMap;  
import org.w3c.dom.NodeList;

import process.deploy;  
  


public class parseProxyConf {

	
	
	private static DocumentBuilderFactory dbFactory = null;  
    private static DocumentBuilder db = null;  
    private static Document document = null;  
    private static List<container> containers = null;  
    static{  
        try {  
            dbFactory = DocumentBuilderFactory.newInstance();  
            db = dbFactory.newDocumentBuilder();  
        } catch (ParserConfigurationException e) {  
            e.printStackTrace();  
        }  
    }  
      
    public static List<container> getContainers(String fileName) throws Exception{  
        //将给定 URI 的内容解析为一个 XML 文档,并返回Document对象  
        document = db.parse(fileName);  
        //按文档顺序返回包含在文档中且具有给定标记名称的所有 Element 的 NodeList  
        NodeList containerList = document.getElementsByTagName("container");  
        containers = new ArrayList<container>();  
        //遍历  containers
        for(int i=0;i<containerList.getLength();i++){  
        	container acontainer= new container();  
            //获取第i个container结点  
            org.w3c.dom.Node node = containerList.item(i);  
            //获取第i个container的所有属性  
            NamedNodeMap namedNodeMap = node.getAttributes();  
              //获取已知名为port的属性值  
            String port = namedNodeMap.getNamedItem("port").getTextContent();//System.out.println(port); 
            acontainer.port=port;
            acontainer.AttributeMap=new HashMap<>(); 
         
              
            //获取container节点的子节点,
            NodeList cList = node.getChildNodes();//
              
            //将一个container里面的属性加入map  
            ArrayList<String> contents = new ArrayList<>();  
            String key;
            String value;
            System.out.println("cList.getLength()="+cList.getLength());
            for(int j=1;j<cList.getLength();j+=2){  
                  
                org.w3c.dom.Node cNode = cList.item(j);  
              
                String content = cNode.getFirstChild().getTextContent();  
                contents.add(content);  
                key=cNode.getNodeName();
                value=content;
               //System.out.println(  cNode.getNodeName()+"-->"+content);  
                if(value!=null&& key!=null){
                	acontainer.AttributeMap.put(key,value);
                   }
            }  
              
          
            containers.add(acontainer);  
        }  
          
        return containers;  
          
    }  
      
    public static void main(String args[]){  
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
  	    strategyPath= properties.getProperty("strategyPath") ;
  	    programFilePath= properties.getProperty("Server_Type") ;
  	    String proxyconf = properties.getProperty("proxy_conf") ;
  	    System.out.println(proxyconf);
        String fileName = proxyconf; 
  	  //  String fileName ="/opt/testproxy/bin/proxyconf.xml";
        try {  
            List<container> list = parseProxyConf.getContainers(fileName);  
            for(container aContainer :list){  
                System.out.println( aContainer.port+":"+aContainer.getCommand("warPath"));  
            }  
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }  
}
