package com.xaeport.crossborder.convert.exitpassport;


import com.xaeport.crossborder.data.entity.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

/**
 * 进口保税清单报文生成
 */
@Component
public class EPassPortXML {

    private Log log = LogFactory.getLog(this.getClass());

    /**
     * 构建报文表头节点
     */
    public void getDataInfo(Document document, PassPortMessage passPortMessage, Element Package) {

        PassportHeadXml passportHeadXml = passPortMessage.getPassportHeadXml();

        Element DataInfo;
        Element PocketInfo;
        Element BussinessData;
        Element DelcareFlag;

        Element is_unstructured;

        Element PassPortMessage;
        Element PassportHead;
        Element OperCusRegCode;

        Element PassportTypecd;
        Element MasterCuscd;
        Element DclTypecd;
        Element IoTypecd;
        Element BindTypecd;
        Element RltTbTypecd;
        Element RltNo;
        Element AreainEtpsno;
        Element AreainEtpsNm;
        Element VehicleNo;
        Element VehicleIcNo;
        Element VehicleWt;
        Element VehicleFrameWt;
        Element ContainerWt;
        Element ContainerType;
        Element TotalWt;
        Element TotalGrossWt;
        Element TotalNetWt;
        Element DclErConc;
        Element DclEtpsno;
        Element DclEtpsNm;
        Element InputCode;
        Element InputName;
        Element EtpsPreentNo;

        DataInfo = document.createElement("DataInfo");
        PocketInfo = document.createElement("PocketInfo");
        BussinessData = document.createElement("BussinessData");

        is_unstructured = document.createElement("is_unstructured");
        is_unstructured.setTextContent("1");

        PocketInfo.appendChild(is_unstructured);

        DelcareFlag = document.createElement("DelcareFlag");
        DelcareFlag.setTextContent("1");

        PassPortMessage = document.createElement("PassPortMessage");
        PassportHead = document.createElement("PassportHead");

        OperCusRegCode = document.createElement("OperCusRegCode");
        OperCusRegCode.setTextContent(passPortMessage.getOperCusRegCode());

        PassportTypecd = document.createElement("PassportTypecd");
        PassportTypecd.setTextContent(passportHeadXml.getPassportTypecd());

        MasterCuscd = document.createElement("MasterCuscd");
        MasterCuscd.setTextContent(passportHeadXml.getMasterCuscd());

        DclTypecd = document.createElement("DclTypecd");
        DclTypecd.setTextContent(passportHeadXml.getDclTypecd());

        IoTypecd = document.createElement("IoTypecd");
        IoTypecd.setTextContent(passportHeadXml.getIoTypecd());

        BindTypecd = document.createElement("BindTypecd");
        BindTypecd.setTextContent(passportHeadXml.getBindTypecd());

        RltTbTypecd = document.createElement("RltTbTypecd");
        RltTbTypecd.setTextContent(passportHeadXml.getRltTbTypecd());

        RltNo = document.createElement("RltNo");
        RltNo.setTextContent(passportHeadXml.getRltNo());

        AreainEtpsno = document.createElement("AreainEtpsno");
        AreainEtpsno.setTextContent(passportHeadXml.getAreainEtpsno());

        AreainEtpsNm = document.createElement("AreainEtpsNm");
        AreainEtpsNm.setTextContent(passportHeadXml.getAreainEtpsNm());

        VehicleNo = document.createElement("VehicleNo");
        VehicleNo.setTextContent(passportHeadXml.getVehicleNo());

        VehicleIcNo = document.createElement("VehicleIcNo");
        VehicleIcNo.setTextContent(passportHeadXml.getVehicleIcNo());

        VehicleWt = document.createElement("VehicleWt");
        VehicleWt.setTextContent(passportHeadXml.getVehicleWt());

        VehicleFrameWt = document.createElement("VehicleFrameWt");
        VehicleFrameWt.setTextContent(passportHeadXml.getVehicleFrameWt());

        ContainerWt = document.createElement("ContainerWt");
        ContainerWt.setTextContent(passportHeadXml.getContainerWt());

        ContainerType = document.createElement("ContainerType");
        ContainerType.setTextContent(passportHeadXml.getContainerType());

        TotalWt = document.createElement("TotalWt");
        TotalWt.setTextContent(passportHeadXml.getTotalWt());

        TotalGrossWt = document.createElement("TotalGrossWt");
        TotalGrossWt.setTextContent(passportHeadXml.getTotalGrossWt());

        TotalNetWt = document.createElement("TotalNetWt");
        TotalNetWt.setTextContent(passportHeadXml.getTotalNetWt());

        DclErConc = document.createElement("DclErConc");
        DclErConc.setTextContent(passportHeadXml.getDclErConc());

        DclEtpsno = document.createElement("DclEtpsno");
        DclEtpsno.setTextContent(passportHeadXml.getDclEtpsno());

        DclEtpsNm = document.createElement("DclEtpsNm");
        DclEtpsNm.setTextContent(passportHeadXml.getDclEtpsNm());

        InputCode = document.createElement("InputCode");
        InputCode.setTextContent(passportHeadXml.getInputCode());

        InputName = document.createElement("InputName");
        InputName.setTextContent(passportHeadXml.getInputName());

        EtpsPreentNo = document.createElement("EtpsPreentNo");
        EtpsPreentNo.setTextContent(passportHeadXml.getEtpsPreentNo());

        PassportHead.appendChild(PassportTypecd);
        PassportHead.appendChild(MasterCuscd);
        PassportHead.appendChild(DclTypecd);
        PassportHead.appendChild(IoTypecd);
        PassportHead.appendChild(BindTypecd);
        PassportHead.appendChild(RltTbTypecd);
        PassportHead.appendChild(RltNo);
        PassportHead.appendChild(AreainEtpsno);
        PassportHead.appendChild(AreainEtpsNm);
        PassportHead.appendChild(VehicleNo);
        PassportHead.appendChild(VehicleIcNo);
        PassportHead.appendChild(VehicleWt);
        PassportHead.appendChild(VehicleFrameWt);
        PassportHead.appendChild(ContainerWt);
        PassportHead.appendChild(ContainerType);
        PassportHead.appendChild(TotalWt);
        PassportHead.appendChild(TotalGrossWt);
        PassportHead.appendChild(TotalNetWt);
        PassportHead.appendChild(DclErConc);
        PassportHead.appendChild(DclEtpsno);
        PassportHead.appendChild(DclEtpsNm);
        PassportHead.appendChild(InputCode);
        PassportHead.appendChild(InputName);
        PassportHead.appendChild(EtpsPreentNo);

        PassPortMessage.appendChild(PassportHead);
        this.getPassportAcmp(document, passPortMessage, PassPortMessage);

        PassPortMessage.appendChild(OperCusRegCode);

        BussinessData.appendChild(PassPortMessage);
        BussinessData.appendChild(DelcareFlag);

        DataInfo.appendChild(PocketInfo);
        DataInfo.appendChild(BussinessData);

        Package.appendChild(DataInfo);

    }

    /**
     * 构建出区核放单报文表体节点
     */
    public void getPassportAcmp(Document document, PassPortMessage passPortMessage, Element PassPortMessage) {

        List<PassportAcmpXml> passportAcmpXmlList = passPortMessage.getPassportAcmpXmlList();

        Element PassportAcmp;
        Element RtlBillTypecd;
        Element RtlBillNo;

        for (int i = 0; i < passportAcmpXmlList.size(); i++) {
            PassportAcmp = document.createElement("PassportAcmp");

            RtlBillTypecd = document.createElement("RtlBillTypecd");
            RtlBillTypecd.setTextContent(passportAcmpXmlList.get(i).getRtlBillTypecd());

            RtlBillNo = document.createElement("RtlBillNo");
            RtlBillNo.setTextContent(passportAcmpXmlList.get(i).getRtlBillNo());

            PassportAcmp.appendChild(RtlBillTypecd);
            PassportAcmp.appendChild(RtlBillNo);
            PassPortMessage.appendChild(PassportAcmp);

        }
    }


}
