package com.xaeport.crossborder.convert.enterpassport;


import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.data.entity.EnvelopInfo;
import com.xaeport.crossborder.data.entity.PassPortMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.transform.TransformerException;
import java.io.StringWriter;
import java.util.Properties;

/**
 * 进口核放单报文生成
 */
@Component
public class EnterBasePassPortXML {

    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    AppConfiguration appConfiguration;


    /**
     * 创建clientDxp数据报文
     *
     * @param passPortMessage
     */
    public byte[] createXML(PassPortMessage passPortMessage, String flag, EnvelopInfo envelopInfo) throws TransformerException {


        //if for 是否占用空格
        Properties p = new Properties();
        p.setProperty("space.gobbling", "none");

        //velocity初始化
        VelocityEngine ve = new VelocityEngine();
        ve.init(p);

        VelocityContext context = new VelocityContext();
        //可以放入多个object,用来装不同的节点
        context.put("envelopInfo",envelopInfo);
        context.put("passPortHead",passPortMessage.getPassportHeadXml());
        context.put("passPortAcmpXmlList",passPortMessage.getPassportAcmpXmlList());
        context.put("passPortListXmlList",passPortMessage.getPassPortListXmlList());
        Template t;
        if (!"3".equals(passPortMessage.getPassportHeadXml().getBindTypecd())){
            //一车一票和一车多票
             t = ve.getTemplate("./template/EnterPassPortXml.vm");
        }else {
            //一票多车
             t = ve.getTemplate("./template/EnterPassPortYPDCXml.vm");
        }
        StringWriter writer = new StringWriter();
        t.merge(context, writer);
        return writer.toString().getBytes();
    }

}
