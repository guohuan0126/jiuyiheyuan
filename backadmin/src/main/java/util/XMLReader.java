package util;

import java.io.StringReader;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * dom4j xml读取器
 * @author xiao
 *
 */
public class XMLReader {
    
    //获取文档根节点
    public static Element getRoot(String xml){
    	SAXReader saxReader = new SAXReader();
        Document document = null;
        try{
            document = saxReader.read(new StringReader(xml));
        }catch(DocumentException e){
            e.printStackTrace();
        }      
        return document.getRootElement();
    }

}
