package com.duanrong.zhongjin;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by xiao on 2016/3/25.
 */

@XmlRootElement(name = "Header")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseHeader {

    //机构编号
    @XmlElement
    private String Code;

    //交易编号
    @XmlElement
    private String Message;

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    @Override
    public String toString() {
        return "ResponseHeader{" +
                "Code='" + Code + '\'' +
                ", Message='" + Message + '\'' +
                '}';
    }
}
