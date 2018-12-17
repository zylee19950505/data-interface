package com.xaeport.crossborder.convert.payment411;


import com.xaeport.crossborder.data.entity.CEB411Message;
import com.xaeport.crossborder.data.entity.ImpPayment;
import com.xaeport.crossborder.data.entity.PaymentHead;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 生成支付单报文
 * Created by zwj on 2017/07/18.
 */
@Component
public class PaymentXml {

    private Log log = LogFactory.getLog(this.getClass());

    /**
     * 构建EntryList 节点
     */
    public void getPaymentList(Document document, Element rootElement, CEB411Message ceb411Message) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        List<PaymentHead> paymentHeadList = ceb411Message.getPaymentHeadList();

        Element Payment;
        Element PaymentHead;
        Element guid;
        Element appType;
        Element appTime;
        Element appStatus;
        Element payCode;
        Element payName;
        Element payTransactionId;
        Element orderNo;
        Element ebpCode;
        Element ebpName;
        Element payerIdType;
        Element payerIdNumber;
        Element payerName;
        Element telephone;
        Element amountPaid;
        Element currency;
        Element payTime;
        Element note;

        for (int i = 0; i < paymentHeadList.size(); i++) {
            Payment = document.createElement("ceb:Payment");
            PaymentHead = document.createElement("ceb:PaymentHead");

            guid = document.createElement("ceb:guid");
            guid.setTextContent(paymentHeadList.get(i).getGuid());

            appType = document.createElement("ceb:appType");
            appType.setTextContent(paymentHeadList.get(i).getAppType());

            appTime = document.createElement("ceb:appTime");
            appTime.setTextContent(paymentHeadList.get(i).getAppTime());

            appStatus = document.createElement("ceb:appStatus");
            appStatus.setTextContent(paymentHeadList.get(i).getAppStatus());

            payCode = document.createElement("ceb:payCode");
            payCode.setTextContent(paymentHeadList.get(i).getPayCode());

            payName = document.createElement("ceb:payName");
            payName.setTextContent(paymentHeadList.get(i).getPayName());

            payTransactionId = document.createElement("ceb:payTransactionId");
            payTransactionId.setTextContent(paymentHeadList.get(i).getPayTransactionId());

            orderNo = document.createElement("ceb:orderNo");
            orderNo.setTextContent(paymentHeadList.get(i).getOrderNo());

            ebpCode = document.createElement("ceb:ebpCode");
            ebpCode.setTextContent(paymentHeadList.get(i).getEbpCode());

            ebpName = document.createElement("ceb:ebpName");
            ebpName.setTextContent(paymentHeadList.get(i).getEbpName());

            payerIdType = document.createElement("ceb:payerIdType");
            payerIdType.setTextContent(paymentHeadList.get(i).getPayerIdType());

            payerIdNumber = document.createElement("ceb:payerIdNumber");
            payerIdNumber.setTextContent(paymentHeadList.get(i).getPayerIdNumber());

            payerName = document.createElement("ceb:payerName");
            payerName.setTextContent(paymentHeadList.get(i).getPayerName());

            telephone = document.createElement("ceb:telephone");
            telephone.setTextContent(paymentHeadList.get(i).getTelephone());

            amountPaid = document.createElement("ceb:amountPaid");
            amountPaid.setTextContent(paymentHeadList.get(i).getAmountPaid());

            currency = document.createElement("ceb:currency");
            currency.setTextContent(paymentHeadList.get(i).getCurrency());

            payTime = document.createElement("ceb:payTime");
            payTime.setTextContent(paymentHeadList.get(i).getPayTime());

            note = document.createElement("ceb:note");
            note.setTextContent(paymentHeadList.get(i).getNote());

            PaymentHead.appendChild(guid);
            PaymentHead.appendChild(appType);
            PaymentHead.appendChild(appTime);
            PaymentHead.appendChild(appStatus);
            PaymentHead.appendChild(payCode);
            PaymentHead.appendChild(payName);
            PaymentHead.appendChild(payTransactionId);
            PaymentHead.appendChild(orderNo);
            PaymentHead.appendChild(ebpCode);
            PaymentHead.appendChild(ebpName);
            PaymentHead.appendChild(payerIdType);
            PaymentHead.appendChild(payerIdNumber);
            PaymentHead.appendChild(payerName);
            PaymentHead.appendChild(telephone);
            PaymentHead.appendChild(amountPaid);
            PaymentHead.appendChild(currency);
            PaymentHead.appendChild(payTime);
            PaymentHead.appendChild(note);
            Payment.appendChild(PaymentHead);

            rootElement.appendChild(Payment);

        }
    }

}
