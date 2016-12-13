package com.duanrong.zhongjin;

import com.duanrong.util.xml.JaxbDateSerializer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

/**
 * Created by xiao on 2016/3/24.
 */
@XmlRootElement(name = "Header")
@XmlAccessorType(XmlAccessType.FIELD)
public class Payee {

    //明细
    @XmlElement
    private String ItemNo;

    //金额
    @XmlElement
    private Integer Amount;

    //账户类型 11个人账户\12企业账户
    @XmlElement
    private String AccountType;

    //银行编号
    @XmlElement
    private String BankID;

    //银行账户名称
    @XmlElement
    private String BankAccountName;

    //银行账户号码
    @XmlElement
    private String BankAccountNumber;

    //手机号
    @XmlElement
    private String PhoneNumber;

    //备注
    @XmlElement
    private String Note;

    //状态 10未处理\20正在处理\30代付成功\40代付失败
    @XmlElement
    private String Status;

    //银行响应时间
    @XmlElement
    @XmlJavaTypeAdapter(JaxbDateSerializer.class)
    private Date BankTxTime;

    //响应代码
    @XmlElement
    private String ResponseCode;

    //响应信息
    @XmlElement
    private String ResponseMessage;

    public String getItemNo() {
        return ItemNo;
    }

    public void setItemNo(String itemNo) {
        ItemNo = itemNo;
    }

    public Integer getAmount() {
        return Amount;
    }

    public void setAmount(Integer amount) {
        Amount = amount;
    }

    public String getAccountType() {
        return AccountType;
    }

    public void setAccountType(String accountType) {
        AccountType = accountType;
    }

    public String getBankID() {
        return BankID;
    }

    public void setBankID(String bankID) {
        BankID = bankID;
    }

    public String getBankAccountName() {
        return BankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        BankAccountName = bankAccountName;
    }

    public String getBankAccountNumber() {
        return BankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        BankAccountNumber = bankAccountNumber;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Date getBankTxTime() {
        return BankTxTime;
    }

    public void setBankTxTime(Date bankTxTime) {
        BankTxTime = bankTxTime;
    }

    public String getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(String responseCode) {
        ResponseCode = responseCode;
    }

    public String getResponseMessage() {
        return ResponseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        ResponseMessage = responseMessage;
    }


    @Override
    public String toString() {
        return "Payee{" +
                "ItemNo='" + ItemNo + '\'' +
                ", Amount=" + Amount +
                ", AccountType='" + AccountType + '\'' +
                ", BankID='" + BankID + '\'' +
                ", BankAccountName='" + BankAccountName + '\'' +
                ", BankAccountNumber='" + BankAccountNumber + '\'' +
                ", PhoneNumber='" + PhoneNumber + '\'' +
                ", Note='" + Note + '\'' +
                ", Status='" + Status + '\'' +
                ", BankTxTime=" + BankTxTime +
                ", ResponseCode='" + ResponseCode + '\'' +
                ", ResponseMessage='" + ResponseMessage + '\'' +
                '}';
    }
}