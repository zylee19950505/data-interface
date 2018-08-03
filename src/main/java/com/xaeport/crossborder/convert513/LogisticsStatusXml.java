package com.xaeport.crossborder.convert513;


import com.xaeport.crossborder.data.entity.CEB511Message;
import com.xaeport.crossborder.data.entity.CEB513Message;
import com.xaeport.crossborder.data.entity.LogisticsHead;
import com.xaeport.crossborder.data.entity.LogisticsStatusHead;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 生成支付单报文
 * Created by zwj on 2017/07/18.
 */
@Component
public class LogisticsStatusXml {

    private Log log = LogFactory.getLog(this.getClass());

    /**
     * 构建EntryList 节点
     */
    public void getLogisticsList(Document document, Element rootElement, CEB513Message ceb513Message) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        List<LogisticsStatusHead> logisticsStatusHead = ceb513Message.getLogisticsStatusHeadList();

        Element LogisticsStatus;
        Element LogisticsStatusHead;
        Element guid;
        Element appType;
        Element appTime;
        Element appStatus;
        Element logisticsCode;
        Element logisticsName;
        Element logisticsNo;
        Element logisticsStatus;
        Element logisticsTime;
        Element note;

        for (int i = 0; i < logisticsStatusHead.size(); i++) {

            LogisticsStatus = document.createElement("ceb:LogisticsStatus");

            guid = document.createElement("ceb:guid");
            guid.setTextContent(logisticsStatusHead.get(i).getGuid());

            appType = document.createElement("ceb:appType");
            appType.setTextContent(logisticsStatusHead.get(i).getAppType());

            appTime = document.createElement("ceb:appTime");
            appTime.setTextContent(logisticsStatusHead.get(i).getAppTime());

            appStatus = document.createElement("ceb:appStatus");
            appStatus.setTextContent(logisticsStatusHead.get(i).getAppStatus());

            logisticsCode = document.createElement("ceb:logisticsCode");
            logisticsCode.setTextContent(logisticsStatusHead.get(i).getLogisticsCode());

            logisticsName = document.createElement("ceb:logisticsName");
            logisticsName.setTextContent(logisticsStatusHead.get(i).getLogisticsName());

            logisticsNo = document.createElement("ceb:logisticsNo");
            logisticsNo.setTextContent(logisticsStatusHead.get(i).getLogisticsNo());

            logisticsStatus = document.createElement("ceb:logisticsStatus");
            logisticsStatus.setTextContent(logisticsStatusHead.get(i).getLogisticsStatus());

            logisticsTime = document.createElement("ceb:logisticsTime");
            logisticsTime.setTextContent(sdf.format(logisticsStatusHead.get(i).getLogisticsTime()));

            note = document.createElement("ceb:note");
            note.setTextContent(logisticsStatusHead.get(i).getNote());

            LogisticsStatus.appendChild(guid);
            LogisticsStatus.appendChild(appType);
            LogisticsStatus.appendChild(appTime);
            LogisticsStatus.appendChild(appStatus);
            LogisticsStatus.appendChild(logisticsCode);
            LogisticsStatus.appendChild(logisticsName);
            LogisticsStatus.appendChild(logisticsNo);
            LogisticsStatus.appendChild(logisticsStatus);
            LogisticsStatus.appendChild(logisticsTime);
            LogisticsStatus.appendChild(note);

            rootElement.appendChild(LogisticsStatus);
        }
    }
}
