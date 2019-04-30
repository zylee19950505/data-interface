package com.xaeport.crossborder.data.xml;

import com.xaeport.crossborder.tools.xml.RootXPath;
import com.xaeport.crossborder.tools.xml.XPath;
import com.xaeport.crossborder.tools.xml.XmlEntry;

import java.util.List;

/**
 * @Author BaoZhe
 * @Date 2019-04-26
 * @Version 1.0
 */
@RootXPath("//Package")
public class PackageXml implements XmlEntry {

    @XPath("EnvelopInfo")
    private EnvelopInfo envelopInfo;

    @XPath("DataInfo/Data")
    private List<DataInfo> dataInfoList;


    public EnvelopInfo getEnvelopInfo() {
        return envelopInfo;
    }

    public void setEnvelopInfo(EnvelopInfo envelopInfo) {
        this.envelopInfo = envelopInfo;
    }

    public List<DataInfo> getDataInfoList() {
        return dataInfoList;
    }

    public void setDataInfoList(List<DataInfo> dataInfoList) {
        this.dataInfoList = dataInfoList;
    }
}
