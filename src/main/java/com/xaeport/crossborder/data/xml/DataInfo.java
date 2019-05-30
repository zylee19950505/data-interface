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
@RootXPath("DataInfo/Data")
public class DataInfo implements XmlEntry {

    @XPath("DataHead")
    private DataHead dataHead;
    @XPath("DataList")
    private List<DataBody> dataBodyList;

    public DataHead getDataHead() {
        return dataHead;
    }

    public void setDataHead(DataHead dataHead) {
        this.dataHead = dataHead;
    }

    public List<DataBody> getDataBodyList() {
        return dataBodyList;
    }

    public void setDataBodyList(List<DataBody> dataBodyList) {
        this.dataBodyList = dataBodyList;
    }
}
