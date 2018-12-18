package Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException; 

public class documentUtils{
	 
	public static void main(String[] args){
		documentUtils Adeploy=new documentUtils();
		String webxml="/usr/local/apache-tomcat-8.5.20/webapps/typhoonCetcTest/WEB-INF/web.xml";
		File file = new File(webxml);
		String str=null;
		if(!file.exists()){
			System.out.println("xml文件不存在,请确认!");
		} else {
			str=Adeploy.parseDocument2Str(file);
		}
		
		String xmlFile="/usr/local/myweb.xml";
		boolean b=Adeploy.str2xml(str, xmlFile);
		
	}
	
	public  String parseDocument2Str(File file){
		String str=null;
		try{
			// 初始化xml解析工厂
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			
			// 创建DocumentBuilder
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			// 创建解析xml的Document
			Document doc = builder.parse(file);
			// 根节点名称
			String rootName = doc.getDocumentElement().getTagName();
			System.out.println("根节点: " + rootName);
			
			System.out.println("递归解析-----begin-------");
			// 递归解析Element
			Element element = doc.getDocumentElement();
			 str=parseElement(element);
			System.out.println("递归解析--------end---------");
			System.out.print(str);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public  String parseElement(Element element){
		String xmlString="";
		//System.out.print("<"+ element.getTagName());
		xmlString=xmlString+"<"+ element.getTagName();
		NamedNodeMap attris = element.getAttributes();
		for(int i = 0; i < attris.getLength(); i++) {
			Attr attr = (Attr) attris.item(i);
			//System.out.print(" " +attr.getName() + "=\"" + attr.getValue() + "\"");
			xmlString=xmlString+" " +attr.getName() + "=\"" + attr.getValue() + "\"";
			
		}
		//System.out.println(">");
		xmlString=xmlString+">";
		NodeList nodeList = element.getChildNodes();
		Node childNode;
		for (int temp = 0; temp < nodeList.getLength(); temp++) {
			
			childNode = nodeList.item(temp);
			
			// 判断是否属于节点
			if (childNode.getNodeType() == Node.ELEMENT_NODE) {
				// 判断是否还有子节点
				if(childNode.hasChildNodes()){
					xmlString=xmlString+parseElement((Element) childNode);
				} 
			}else if (childNode.getNodeType() != Node.COMMENT_NODE) {
				//System.out.print(childNode.getTextContent());
				xmlString=xmlString+childNode.getTextContent();
			}else if(childNode.getNodeType() == Node.COMMENT_NODE)
			{
				//System.out.print("<!-- "+childNode.getTextContent()+"-->\n");
				xmlString=xmlString+"<!-- "+childNode.getTextContent()+"-->\n";
			}
		}
		xmlString=xmlString+"</" + element.getTagName() + ">";
		//System.out.println("</" + element.getTagName() + ">");
		return xmlString;
	}
	public boolean str2xml(String xmlStr,String xmlFile){
		if(xmlStr==null|| xmlFile==null){
			return false;
		}
		boolean b=false;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder=null;
		try {
			builder = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(builder==null){
			return false;
		}
		Document  doc=null;
		try {
			doc=builder.parse( new InputSource( new StringReader(xmlStr)  )  );
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
		if(doc==null){
			return false;
		}
		
		// 创建TransformerFactory对象
		TransformerFactory tff = TransformerFactory.newInstance();
		Transformer tf =null;
		try {
			tf = tff.newTransformer();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		try {
			tf.transform(new DOMSource(doc), new StreamResult(xmlFile));
			b=true;
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}
	
	public static boolean docToxml(Document  doc,String xmlFile){
		boolean b=false;
		
		
		if(doc==null || xmlFile==null){
			return false;
		}
		
		// 创建TransformerFactory对象
		TransformerFactory tff = TransformerFactory.newInstance();
		Transformer tf =null;
		try {
			tf = tff.newTransformer();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		try {
			tf.transform(new DOMSource(doc), new StreamResult(xmlFile));
			b=true;
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}
	/*
     * 把dom文件转换为xml字符串
     */
    public static String toStringFromDoc(Document document) {
        String result = null;
 
        if (document != null) {
            StringWriter strWtr = new StringWriter();
            StreamResult strResult = new StreamResult(strWtr);
            TransformerFactory tfac = TransformerFactory.newInstance();
            try {
                javax.xml.transform.Transformer t = tfac.newTransformer();
                t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                t.setOutputProperty(OutputKeys.INDENT, "yes");
                t.setOutputProperty(OutputKeys.METHOD, "xml"); // xml, html,
                // text
                t.setOutputProperty(
                        "{http://xml.apache.org/xslt}indent-amount", "4");
                t.transform(new DOMSource(document.getDocumentElement()),
                        strResult);
            } catch (Exception e) {
                System.err.println("XML.toString(Document): " + e);
            }
            result = strResult.getWriter().toString();
            try {
                strWtr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
 
        return result;
    }
 
    public static Document parseXMLDocument(String xmlStr) {
        if (xmlStr == null) {
            return null;
          }
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder=null;
		try {
			builder = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(builder==null){
			return null;
		}
		Document  doc=null;
		try {
			doc=builder.parse( new InputSource( new StringReader(xmlStr)  )  );
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
		
		return doc;
    }
}
