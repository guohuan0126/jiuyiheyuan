package com.duanrong.util.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * jaxb date转换器
 * @author xiao
 * @date 2016/3/15
 * @version 1.0
 */
public class JaxbDateSerializer extends XmlAdapter<String, Date>{

    private SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

    @Override
    public Date unmarshal(String v) throws Exception {
        return format.parse(v);
    }

    @Override
    public String marshal(Date v) throws Exception {
        return format.format(v);
    }
}
