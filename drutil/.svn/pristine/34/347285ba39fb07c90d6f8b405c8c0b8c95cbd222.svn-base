package com.duanrong.zhongjin;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by xiao on 2016/3/24.
 */

@XmlRootElement(name = "Payer")
@XmlAccessorType(XmlAccessType.FIELD)
public class Payer {

    //支付账户名称
    @XmlElement
    private String PaymentAccountName;

    //支付账户密码
    @XmlElement
    private String PaymentAccountNumber;

    public Payer(){

    }

    public Payer(String PaymentAccountName, String PaymentAccountNumber){
        this.PaymentAccountName = PaymentAccountName;
        this.PaymentAccountNumber = PaymentAccountNumber;
    }

    public String getPaymentAccountName() {
        return PaymentAccountName;
    }

    public void setPaymentAccountName(String paymentAccountName) {
        PaymentAccountName = paymentAccountName;
    }

    public String getPaymentAccountNumber() {
        return PaymentAccountNumber;
    }

    public void setPaymentAccountNumber(String paymentAccountNumber) {
        PaymentAccountNumber = paymentAccountNumber;
    }

    @Override
    public String toString() {
        return "ZhongJinPayer{" +
                "PaymentAccountName='" + PaymentAccountName + '\'' +
                ", PaymentAccountNumber='" + PaymentAccountNumber + '\'' +
                '}';
    }
}
