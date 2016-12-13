package com.duanrong.util.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * XStream xml 序列化工具类
 * XStream 实现对 xml 的序列化和反序列化
 * @author xiao
 * @date 2016/4/11
 * @version 1.0
 */
public class XstreamXmlUtil {

    private static XStream xStream;

    static {

        xStream = new XStream(new DomDriver());
    }

    /**
     * xml 序列化
     * @param obj
     * @return
     */
    public static String ObjToXml(Object obj){

        //为要序列化的类设置注解
        xStream.processAnnotations(obj.getClass());
        //启用Annotation
        xStream.autodetectAnnotations(true);

        //序列化
        return xStream.toXML(obj);
    }


    /**
     * xml　反序列化
     * @param xml
     * @return
     */
    public static <T> T XmlToObj(String xml, Class<T> clazz){
        //反序列化
        return (T)xStream.fromXML(xml, clazz);
    }
}
