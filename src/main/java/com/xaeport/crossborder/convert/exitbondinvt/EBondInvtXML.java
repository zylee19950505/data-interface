package com.xaeport.crossborder.convert.exitbondinvt;


import com.xaeport.crossborder.data.entity.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

/**
 * 进口保税清单报文生成
 */
@Component
public class EBondInvtXML {

    private Log log = LogFactory.getLog(this.getClass());

    /**
     * 构建出区核注清单报文表头节点
     */
    public void getDataInfo(Document document, InvtMessage invtMessage, Element Package) {

        InvtHeadType invtHeadType = invtMessage.getInvtHeadType();

        Element DataInfo;
        Element BussinessData;
        Element DelcareFlag;

        Element InvtMessage;
        Element InvtHeadType;
        Element OperCusRegCode;
        Element SysId;

        Element BondInvtNo;
        Element SeqNo;
        Element PutrecNo;
        Element EtpsInnerInvtNo;
        Element BizopEtpsSccd;
        Element BizopEtpsno;
        Element BizopEtpsNm;
        Element RcvgdEtpsno;
        Element RvsngdEtpsSccd;
        Element RcvgdEtpsNm;
        Element DclEtpsSccd;
        Element DclEtpsno;
        Element DclEtpsNm;
        Element InvtDclTime;
        Element ImpexpPortcd;
        Element DclPlcCuscd;
        Element ImpexpMarkcd;
        Element MtpckEndprdMarkcd;
        Element SupvModecd;
        Element TrspModecd;
        Element DclcusFlag;
        Element DclcusTypecd;
        Element VrfdedMarkcd;
        Element InputCode;
        Element InputName;
        Element InputTime;
        Element ListStat;
        Element CorrEntryDclEtpsNo;
        Element CorrEntryDclEtpsNm;
        Element DecType;
        Element AddTime;
        Element StshipTrsarvNatcd;
        Element InvtType;

        DataInfo = document.createElement("DataInfo");
        BussinessData = document.createElement("BussinessData");

        DelcareFlag = document.createElement("DelcareFlag");
        DelcareFlag.setTextContent("0");

        InvtMessage = document.createElement("InvtMessage");
        InvtHeadType = document.createElement("InvtHeadType");

        OperCusRegCode = document.createElement("OperCusRegCode");
        OperCusRegCode.setTextContent(invtMessage.getOperCusRegCode());

        SysId = document.createElement("SysId");
        SysId.setTextContent(invtMessage.getSysId());

        BondInvtNo = document.createElement("BondInvtNo");
        BondInvtNo.setTextContent(invtHeadType.getBondInvtNo());

        SeqNo = document.createElement("SeqNo");
        SeqNo.setTextContent(invtHeadType.getSeqNo());

        PutrecNo = document.createElement("PutrecNo");
        PutrecNo.setTextContent(invtHeadType.getPutrecNo());

        EtpsInnerInvtNo = document.createElement("EtpsInnerInvtNo");
        EtpsInnerInvtNo.setTextContent(invtHeadType.getEtpsInnerInvtNo());

        BizopEtpsSccd = document.createElement("BizopEtpsSccd");
        BizopEtpsSccd.setTextContent(invtHeadType.getBizopEtpsSccd());

        BizopEtpsno = document.createElement("BizopEtpsno");
        BizopEtpsno.setTextContent(invtHeadType.getBizopEtpsno());

        BizopEtpsNm = document.createElement("BizopEtpsNm");
        BizopEtpsNm.setTextContent(invtHeadType.getBizopEtpsNm());

        RcvgdEtpsno = document.createElement("RcvgdEtpsno");
        RcvgdEtpsno.setTextContent(invtHeadType.getRcvgdEtpsno());

        RvsngdEtpsSccd = document.createElement("RvsngdEtpsSccd");
        RvsngdEtpsSccd.setTextContent(invtHeadType.getRvsngdEtpsSccd());

        RcvgdEtpsNm = document.createElement("RcvgdEtpsNm");
        RcvgdEtpsNm.setTextContent(invtHeadType.getRcvgdEtpsNm());

        DclEtpsSccd = document.createElement("DclEtpsSccd");
        DclEtpsSccd.setTextContent(invtHeadType.getDclEtpsSccd());

        DclEtpsno = document.createElement("DclEtpsno");
        DclEtpsno.setTextContent(invtHeadType.getDclEtpsno());

        DclEtpsNm = document.createElement("DclEtpsNm");
        DclEtpsNm.setTextContent(invtHeadType.getDclEtpsNm());

        InvtDclTime = document.createElement("InvtDclTime");
        InvtDclTime.setTextContent(invtHeadType.getInvtDclTime());

        ImpexpPortcd = document.createElement("ImpexpPortcd");
        ImpexpPortcd.setTextContent(invtHeadType.getImpexpPortcd());

        DclPlcCuscd = document.createElement("DclPlcCuscd");
        DclPlcCuscd.setTextContent(invtHeadType.getDclPlcCuscd());

        ImpexpMarkcd = document.createElement("ImpexpMarkcd");
        ImpexpMarkcd.setTextContent(invtHeadType.getImpexpMarkcd());

        MtpckEndprdMarkcd = document.createElement("MtpckEndprdMarkcd");
        MtpckEndprdMarkcd.setTextContent(invtHeadType.getMtpckEndprdMarkcd());

        SupvModecd = document.createElement("SupvModecd");
        SupvModecd.setTextContent(invtHeadType.getSupvModecd());

        TrspModecd = document.createElement("TrspModecd");
        TrspModecd.setTextContent(invtHeadType.getTrspModecd());

        DclcusFlag = document.createElement("DclcusFlag");
        DclcusFlag.setTextContent(invtHeadType.getDclcusFlag());

        DclcusTypecd = document.createElement("DclcusTypecd");
        DclcusTypecd.setTextContent(invtHeadType.getDclcusTypecd());

        VrfdedMarkcd = document.createElement("VrfdedMarkcd");
        VrfdedMarkcd.setTextContent(invtHeadType.getVrfdedMarkcd());

        InputCode = document.createElement("InputCode");
        InputCode.setTextContent(invtHeadType.getInputCode());

        InputName = document.createElement("InputName");
        InputName.setTextContent(invtHeadType.getInputName());

        InputTime = document.createElement("InputTime");
        InputTime.setTextContent(invtHeadType.getInputTime());

        ListStat = document.createElement("ListStat");
        ListStat.setTextContent(invtHeadType.getListStat());

        CorrEntryDclEtpsNo = document.createElement("CorrEntryDclEtpsNo");
        CorrEntryDclEtpsNo.setTextContent(invtHeadType.getCorrEntryDclEtpsNo());

        CorrEntryDclEtpsNm = document.createElement("CorrEntryDclEtpsNm");
        CorrEntryDclEtpsNm.setTextContent(invtHeadType.getCorrEntryDclEtpsNm());

        DecType = document.createElement("DecType");
        DecType.setTextContent(invtHeadType.getDecType());

        AddTime = document.createElement("AddTime");
        AddTime.setTextContent(invtHeadType.getAddTime());

        StshipTrsarvNatcd = document.createElement("StshipTrsarvNatcd");
        StshipTrsarvNatcd.setTextContent(invtHeadType.getStshipTrsarvNatcd());

        InvtType = document.createElement("InvtType");
        InvtType.setTextContent(invtHeadType.getInvtType());

        InvtHeadType.appendChild(BondInvtNo);
        InvtHeadType.appendChild(SeqNo);
        InvtHeadType.appendChild(PutrecNo);
        InvtHeadType.appendChild(EtpsInnerInvtNo);
        InvtHeadType.appendChild(BizopEtpsSccd);
        InvtHeadType.appendChild(BizopEtpsno);
        InvtHeadType.appendChild(BizopEtpsNm);
        InvtHeadType.appendChild(RcvgdEtpsno);
        InvtHeadType.appendChild(RvsngdEtpsSccd);
        InvtHeadType.appendChild(RcvgdEtpsNm);
        InvtHeadType.appendChild(DclEtpsSccd);
        InvtHeadType.appendChild(DclEtpsno);
        InvtHeadType.appendChild(DclEtpsNm);
        InvtHeadType.appendChild(InvtDclTime);
        InvtHeadType.appendChild(ImpexpPortcd);
        InvtHeadType.appendChild(DclPlcCuscd);
        InvtHeadType.appendChild(ImpexpMarkcd);
        InvtHeadType.appendChild(MtpckEndprdMarkcd);
        InvtHeadType.appendChild(SupvModecd);
        InvtHeadType.appendChild(TrspModecd);
        InvtHeadType.appendChild(DclcusFlag);
        InvtHeadType.appendChild(DclcusTypecd);
        InvtHeadType.appendChild(VrfdedMarkcd);
        InvtHeadType.appendChild(InputCode);
        InvtHeadType.appendChild(InputName);
        InvtHeadType.appendChild(InputTime);
        InvtHeadType.appendChild(ListStat);
        InvtHeadType.appendChild(CorrEntryDclEtpsNo);
        InvtHeadType.appendChild(CorrEntryDclEtpsNm);
        InvtHeadType.appendChild(DecType);
        InvtHeadType.appendChild(AddTime);
        InvtHeadType.appendChild(StshipTrsarvNatcd);
        InvtHeadType.appendChild(InvtType);

//        String etpsInnerInvtNo = invtHeadType.getEtpsInnerInvtNo();

        InvtMessage.appendChild(InvtHeadType);
        this.getBondInvenList(document, invtMessage, InvtMessage);

        InvtMessage.appendChild(OperCusRegCode);
        InvtMessage.appendChild(SysId);

        BussinessData.appendChild(InvtMessage);
        BussinessData.appendChild(DelcareFlag);

        DataInfo.appendChild(BussinessData);

        Package.appendChild(DataInfo);

    }

    /**
     * 构建出区核注清单报文表体节点
     */
    public void getBondInvenList(Document document, InvtMessage invtMessage, Element InvtMessage) {

        List<InvtListType> invtListTypeList = invtMessage.getInvtListTypeList();

        Element InvtListType;
        Element SeqNo;
        Element BondInvtNo;
        Element CbecBillNo;

        for (int i = 0; i < invtListTypeList.size(); i++) {
            InvtListType = document.createElement("InvtListType");

            SeqNo = document.createElement("SeqNo");
            SeqNo.setTextContent(String.valueOf(invtListTypeList.get(i).getSeqNo()));

            BondInvtNo = document.createElement("BondInvtNo");
            BondInvtNo.setTextContent(invtListTypeList.get(i).getBondInvtNo());

            CbecBillNo = document.createElement("CbecBillNo");
            CbecBillNo.setTextContent(invtListTypeList.get(i).getCbecBillNo());

            InvtListType.appendChild(SeqNo);
            InvtListType.appendChild(BondInvtNo);
            InvtListType.appendChild(CbecBillNo);
            InvtMessage.appendChild(InvtListType);
        }
    }


}
