package util;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * @Description: XML工具类 
 * @Author:	林志明
 * @CreateDate:	Oct 10, 2014
 */
public class XMLUtil {
	public static String getXML(Object obj) throws IOException {
		StringWriter writer = null;
		JAXBContext context;  
        try {  
            context = JAXBContext.newInstance(obj.getClass());  
            Marshaller mar = context.createMarshaller(); 
            
            // FIXME：正式环境需要注释
            // 格式化输出
            mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
            
            mar.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");  
              
            writer = new StringWriter();  
              
            mar.marshal(obj, writer);  
              
           return writer.toString();
        } catch (JAXBException e) {  
            e.printStackTrace();  
        }  finally {
        	if(writer != null) {
        		writer.close();
        	}
        }
        
        return null;
	}
}
