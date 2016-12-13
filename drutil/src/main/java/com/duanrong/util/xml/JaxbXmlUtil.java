package com.duanrong.util.xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Jaxb xml 序列化工具类
 * jaxb 实现对 xml 的序列化和反序列化
 * @author xiao
 * @date 2016/3/15
 * @version 1.0
 */
public class JaxbXmlUtil {

	private static JAXBContext context = null;
	
    /**
     * xml 序列化
     * @param obj
     * @return
     * @throws IOException
     */
    public static String objToXml(Object obj) throws IOException {
        StringWriter writer = null;
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

    /**
     * xml 反序列化
     * @param xml
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T  xmlToObj(String xml, Class<T> clazz) throws IOException {
        try {
            context = JAXBContext.newInstance(clazz);
            Unmarshaller u = context.createUnmarshaller();
            return (T) u.unmarshal(new StringReader(xml));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
}
