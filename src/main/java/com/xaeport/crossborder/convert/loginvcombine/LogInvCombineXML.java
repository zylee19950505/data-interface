package com.xaeport.crossborder.convert.loginvcombine;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.data.entity.EnvelopInfo;
import com.xaeport.crossborder.data.entity.LogInvCombineMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.StringWriter;
import java.util.Properties;

@Component
public class LogInvCombineXML {

    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    AppConfiguration appConfiguration;

    //创建数据报文
    public byte[] createXML(LogInvCombineMessage logInvCombineMessage, String flag, EnvelopInfo envelopInfo) {

        Properties p = new Properties();
        p.setProperty("space.gobbling", "none");

        VelocityEngine v = new VelocityEngine();
        v.init(p);

        VelocityContext context = new VelocityContext();
        context.put("envelopInfo", envelopInfo);
        context.put("LogInvDataList", logInvCombineMessage.getLogInvDataList());
        Template t = v.getTemplate(appConfiguration.getXmlPath().get("xmlTemplatePath") + File.separator + "LogInvCombineXml.vm");

        StringWriter writer = new StringWriter();
        t.merge(context, writer);
        return writer.toString().getBytes();

    }

}
