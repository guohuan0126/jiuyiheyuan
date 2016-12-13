package com.duanrong.zhongjin;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by xiao on 2016/3/25.
 */

@XmlRootElement(name = "Response")
@XmlAccessorType(XmlAccessType.FIELD)
public class Response {

    //响应头
    @XmlElement(name = "Header")
    private ResponseHeader responseHeader;

    @XmlElement
    private com.duanrong.zhongjin.Body Body;

}
