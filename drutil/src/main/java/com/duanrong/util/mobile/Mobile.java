package com.duanrong.util.mobile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 手机号实体
 */
@XmlRootElement(name="root")
@XmlAccessorType(XmlAccessType.FIELD)
public class Mobile {

    @XmlElement
    private String chgmobile;

    @XmlElement
    private String province;

    @XmlElement
    private String city;

    @XmlElement
    private String supplier;

    @XmlElement
    private String retcode;

    @XmlElement
    private String retmsg;


    public String getChgmobile() {
        return chgmobile;
    }

    public void setChgmobile(String chgmobile) {
        this.chgmobile = chgmobile;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    public String getRetmsg() {
        return retmsg;
    }

    public void setRetmsg(String retmsg) {
        this.retmsg = retmsg;
    }

    @Override
    public String toString() {
        return "Mobile{" +
                "chgmobile='" + chgmobile + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", supplier='" + supplier + '\'' +
                ", retcode='" + retcode + '\'' +
                ", retmsg='" + retmsg + '\'' +
                '}';
    }
}
