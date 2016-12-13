package com.duanrong.zhongjin;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by xiao on 2016/3/22.
 */

@XmlRootElement(name = "Body")
@XmlAccessorType(XmlAccessType.FIELD)
public class Body {

    //交易流水
    @XmlElement
    private String TxSN;

    //交易类型
    @XmlElement
    private String TxType;

    //完成状态
    @XmlElement
    private String Finished;

    //支付方
    @XmlElement(name = "Payer")
    private com.duanrong.zhongjin.Payer Payer;

    //收款方
    @XmlElement(name = "Payee")
    private List<com.duanrong.zhongjin.Payee> Payee;

    //支付/代收金额
    @XmlElement
    private Integer Amount;

    //备注
    @XmlElement
    private String Remark;

    //批次号
    @XmlElement
    private String BatchNo;

    //总金额
    @XmlElement
    private Integer TotalAmount;

    //总笔数
    @XmlElement
    private Integer TotalCount;


    public String getTxSN() {
        return TxSN;
    }

    public void setTxSN(String txSN) {
        TxSN = txSN;
    }

    public String getTxType() {
        return TxType;
    }

    public void setTxType(String txType) {
        TxType = txType;
    }

    public String getFinished() {
        return Finished;
    }

    public void setFinished(String finished) {
        Finished = finished;
    }

    public com.duanrong.zhongjin.Payer getPayer() {
        return Payer;
    }

    public void setPayer(com.duanrong.zhongjin.Payer payer) {
        Payer = payer;
    }

    public List<com.duanrong.zhongjin.Payee> getPayee() {
        return Payee;
    }

    public void setPayee(List<com.duanrong.zhongjin.Payee> payee) {
        Payee = payee;
    }

    public Integer getAmount() {
        return Amount;
    }

    public void setAmount(Integer amount) {
        Amount = amount;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getBatchNo() {
        return BatchNo;
    }

    public void setBatchNo(String batchNo) {
        BatchNo = batchNo;
    }

    public Integer getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        TotalAmount = totalAmount;
    }

    public Integer getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(Integer totalCount) {
        TotalCount = totalCount;
    }

    @Override
    public String toString() {
        return "Body{" +
                "TxSN='" + TxSN + '\'' +
                ", TxType='" + TxType + '\'' +
                ", Finished='" + Finished + '\'' +
                ", Payer=" + Payer +
                ", Payee=" + Payee +
                ", Amount=" + Amount +
                ", Remark=" + Remark +
                ", BatchNo='" + BatchNo + '\'' +
                ", TotalAmount=" + TotalAmount +
                ", TotalCount=" + TotalCount +
                '}';
    }

}
