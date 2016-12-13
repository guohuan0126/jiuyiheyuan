package com.duanrong.zhongjin;


import javax.xml.bind.annotation.*;

/**
 * Created by xiao on 2016/3/22.
 */
@XmlRootElement(name = "Request")
@XmlAccessorType(XmlAccessType.FIELD)
public class Request {

    @XmlAttribute
    private String version;

    @XmlElement(name="Header")
    private RequestHeader Header;

    @XmlElement(name="Body")
    private Body Body;


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public RequestHeader getHeader() {
        return Header;
    }

    public void setHeader(RequestHeader header) {
        Header = header;
    }

    public Body getBody() {
        return Body;
    }

    public void setBody(Body body) {
        Body = body;
    }

    @Override
    public String toString() {
        return "Request{" +
                "version='" + version + '\'' +
                ", Header=" + Header +
                ", Body=" + Body +
                '}';
    }
}
