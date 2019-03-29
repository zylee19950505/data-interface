package com.xaeport.crossborder.convert.enterbondinvt;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.data.entity.EnvelopInfo;
import com.xaeport.crossborder.data.entity.InvtMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 进口保税清单报文生成
 */
@Component
public class EnterBaseBondInvtXML {

    private Log log = LogFactory.getLog(this.getClass());
   // @Autowired
   // EnterBondInvtXML eBondInvtXML;
    @Autowired
    AppConfiguration appConfiguration;

    /**
     * 创建clientDxp数据报文
     * 根据velocity模板方式创建
     *
     * @param invtMessage
     */
    public byte[] createXML(InvtMessage invtMessage, String flag, EnvelopInfo envelopInfo) throws TransformerException {


        Map envelopInfoMap = new HashMap();
        envelopInfoMap.put("version",envelopInfo.getVersion());
        envelopInfoMap.put("message_id",envelopInfo.getMessage_id());
        envelopInfoMap.put("file_name",envelopInfo.getFile_name());
        envelopInfoMap.put("message_type",envelopInfo.getMessage_type());
        envelopInfoMap.put("sender_id",envelopInfo.getSender_id());
        envelopInfoMap.put("send_time",envelopInfo.getSend_time());
        envelopInfoMap.put("Ic_Card",envelopInfo.getIc_Card());
        envelopInfoMap.put("receiver_id",envelopInfo.getReceiver_id());

        Map invtHeadType = new HashMap();
        invtHeadType.put("PutrecNo",invtMessage.getInvtHeadType().getPutrecNo());
        invtHeadType.put("EtpsInnerInvtNo",invtMessage.getInvtHeadType().getEtpsInnerInvtNo());
        invtHeadType.put("BizopEtpsSccd",invtMessage.getInvtHeadType().getBizopEtpsSccd());
        invtHeadType.put("BizopEtpsno",invtMessage.getInvtHeadType().getBizopEtpsno());
        invtHeadType.put("BizopEtpsNm",invtMessage.getInvtHeadType().getBizopEtpsNm());
        invtHeadType.put("RcvgdEtpsno",invtMessage.getInvtHeadType().getRcvgdEtpsno());
        invtHeadType.put("RvsngdEtpsSccd",invtMessage.getInvtHeadType().getRvsngdEtpsSccd());
        invtHeadType.put("RcvgdEtpsNm",invtMessage.getInvtHeadType().getRcvgdEtpsNm());
        invtHeadType.put("DclEtpsSccd",invtMessage.getInvtHeadType().getDclEtpsSccd());
        invtHeadType.put("DclEtpsno",invtMessage.getInvtHeadType().getDclEtpsno());
        invtHeadType.put("DclEtpsNm",invtMessage.getInvtHeadType().getDclEtpsNm());
        invtHeadType.put("InvtDclTime",invtMessage.getInvtHeadType().getInvtDclTime());
        invtHeadType.put("RltInvtNo",invtMessage.getInvtHeadType().getRltPutrecNo());
        invtHeadType.put("RltPutrecNo",invtMessage.getInvtHeadType().getRltPutrecNo());
        invtHeadType.put("ImpexpPortcd",invtMessage.getInvtHeadType().getImpexpPortcd());
        invtHeadType.put("DclPlcCuscd",invtMessage.getInvtHeadType().getDclPlcCuscd());
        invtHeadType.put("ImpexpMarkcd",invtMessage.getInvtHeadType().getImpexpMarkcd());
        invtHeadType.put("MtpckEndprdMarkcd",invtMessage.getInvtHeadType().getMtpckEndprdMarkcd());
        invtHeadType.put("SupvModecd",invtMessage.getInvtHeadType().getSupvModecd());
        invtHeadType.put("TrspModecd",invtMessage.getInvtHeadType().getTrspModecd());
        invtHeadType.put("DclcusFlag",invtMessage.getInvtHeadType().getDclcusFlag());
        invtHeadType.put("DclcusTypecd",invtMessage.getInvtHeadType().getDclcusTypecd());
        invtHeadType.put("DecType",invtMessage.getInvtHeadType().getDecType());
        invtHeadType.put("VrfdedMarkcd",invtMessage.getInvtHeadType().getVrfdedMarkcd());
        invtHeadType.put("InputCode",invtMessage.getInvtHeadType().getInputCode());
        invtHeadType.put("InputName",invtMessage.getInvtHeadType().getInputName());
        invtHeadType.put("InputTime",invtMessage.getInvtHeadType().getInputTime());
        invtHeadType.put("ListStat",invtMessage.getInvtHeadType().getListStat());
        invtHeadType.put("AddTime",invtMessage.getInvtHeadType().getAddTime());
        invtHeadType.put("StshipTrsarvNatcd",invtMessage.getInvtHeadType().getStshipTrsarvNatcd());
        invtHeadType.put("InvtType",invtMessage.getInvtHeadType().getInvtType());

        invtHeadType.put("CorrEntryDclEtpsSccd",invtMessage.getInvtHeadType().getCorr_entry_dcl_etps_sccd());
        invtHeadType.put("CorrEntryDclEtpsNo",invtMessage.getInvtHeadType().getCorr_entry_dcl_etps_no());
        invtHeadType.put("CorrEntryDclEtpsNm",invtMessage.getInvtHeadType().getCorr_entry_dcl_etps_nm());

        invtHeadType.put("Rmk",invtMessage.getInvtHeadType().getRmk());
        invtHeadType.put("OperCusRegCode",invtMessage.getInvtHeadType().getOperCusRegCode());

        //if for 是否占用空格
        Properties p = new Properties();
        p.setProperty("space.gobbling", "none");

        //velocity初始化
        VelocityEngine ve = new VelocityEngine();
        ve.init(p);

        VelocityContext context = new VelocityContext();
        //可以放入多个object,用来装不同的节点
        context.put("envelopInfo",envelopInfoMap);
        context.put("invtHeadType",invtHeadType);
        context.put("invtListTypeList",invtMessage.getInvtListTypeList());

        Template t = ve.getTemplate("./template/EnterBondInvtXML.vm");

        StringWriter writer = new StringWriter();
        t.merge(context, writer);
        return writer.toString().getBytes();
    }

}
