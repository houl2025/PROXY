package Utils;

import java.util.HashMap;

public class container {
	
   public HashMap<String,String>AttributeMap;
 
   public String port;
   public String getCommand(String Name){
	   return AttributeMap.get(Name);
   }
   public String getPort(){
	   return port;
	   
   }
}
