package com.duanrong.zhongjin;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * 请求头
 */
@XmlRootElement(name = "Header")
@XmlAccessorType(XmlAccessType.FIELD)
public class RequestHeader {

    //机构编号
    @XmlElement
    private String InstitutionID;

    //交易类型
    @XmlElement
    private String TxCode;

    //交易流水
    @XmlElement
    private String TxSN;


    public RequestHeader(){

    }

    public  RequestHeader(String InstitutionID, String TxCode){
        this.InstitutionID = InstitutionID;
        this.TxCode = TxCode;
    }

    public  RequestHeader(String InstitutionID, String TxCode, String TxSN){
        this.InstitutionID = InstitutionID;
        this.TxCode = TxCode;
        this.TxSN = TxSN;
    }

    public String getInstitutionID() {
        return InstitutionID;
    }

    public void setInstitutionID(String institutionID) {
        InstitutionID = institutionID;
    }

    public String getTxCode() {
        return TxCode;
    }

    public void setTxCode(String txCode) {
        TxCode = txCode;
    }

    public String getTxSN() {
        return TxSN;
    }

    public void setTxSN(String txSN) {
        TxSN = txSN;
    }


    @Override
    public String toString() {
        return "RequestHeader{" +
                "InstitutionID='" + InstitutionID + '\'' +
                ", TxCode='" + TxCode + '\'' +
                ", TxSN='" + TxSN + '\'' +
                '}';
    }
}
