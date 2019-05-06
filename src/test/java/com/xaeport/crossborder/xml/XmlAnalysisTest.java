//package com.xaeport.crossborder.xml;
//
//import com.xaeport.crossborder.data.xml.EnvelopInfo;
//import com.xaeport.crossborder.tools.xml.AnalysisXmlUtils;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.dom4j.Document;
//import org.dom4j.DocumentException;
//import org.dom4j.Node;
//import org.dom4j.io.SAXReader;
//import org.junit.Test;
//
//import java.io.StringReader;
//import java.util.List;
//
///**
// * @Author BaoZhe
// * @Date 2019-04-26
// * @Version 1.0
// */
//public class XmlAnalysisTest {
//
//    private Log log = LogFactory.getLog(this.getClass());
//
//
//    @Test
//    public void xmlFileAnalysisTest() {
//        List<EnvelopInfo> packageXmlList = AnalysisXmlUtils.convertXmlToType(xmlContent, EnvelopInfo.class);
//        log.debug(packageXmlList);
//    }
//
//
//    @Test
//    public void xpathTest() {
//
//        StringReader stringReader = new StringReader(xmlContent);
//        SAXReader reader = new SAXReader();
//        Document document = null;
//        try {
//            // 加载XML数据
//            document = reader.read(stringReader);
//            String xpathStr = "//Package";
//            List<? extends Node> rootSubNodes = document.selectNodes(xpathStr);
////            log.debug(rootSubNodes);
//            for (Node node : rootSubNodes) {
//                List<? extends Node> nodes = node.selectNodes("DataInfo/Data");
////                log.debug(nodes);
//                for(Node subNode : nodes){
//                    String subPath = subNode.getPath();
//                    log.debug(subPath);
//                    List<? extends Node> subNodes = subNode.selectNodes("Head");
//                    for(Node subSubNode : subNodes){
//                        subPath = subSubNode.getPath();
//                        log.debug(subPath);
//                    }
//                    subNodes = subNode.selectNodes("List");
//                    for(Node subSubNode : subNodes){
//                        subPath = subSubNode.getPath();
//                        log.debug(subPath);
//                    }
//                }
//            }
//        } catch (DocumentException e) {
//            log.error("Xml转换为Document对象时发生异常", e);
//        }
//    }
//
//
//    String xmlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?> " +
//            "<Package> " +
//            " <EnvelopInfo> " +
//            "  <msg_id>企业内部编码</msg_id> " +
//            "  <msg_type>CEBINV101</msg_type> " +
//            "  <sender_id>企业ID</sender_id> " +
//            "  <recv_id>9009000001</recv_id> " +
//            "  <ic_card>IC卡号</ic_card> " +
//            "  <status>申报为\"1\";暂存为\"0\"（申报时，所有字段为必填）</status> " +
//            " </EnvelopInfo> " +
//            " <DataInfo> " +
//            "  <Data> " +
//            "   <Head> " +
//            "    <EtpsInnerInvtNo>1必填项，企业内部编码:HZQD+海关十位+进出口标志（I/E）+YYYYMMDD+四位流水号（0001起递增）</EtpsInnerInvtNo> " +
//            "    <PutrecNo>1必填项，账册编号</PutrecNo> " +
//            "    <DclPlcCuscd>1必填项，主管海关</DclPlcCuscd> " +
//            "    <ImpexpPortcd>1进出境关别</ImpexpPortcd> " +
//            "    <DclEtpsSccd>1申报企业社会信用代码</DclEtpsSccd> " +
//            "    <DclEtpsno>1申报企业海关十位</DclEtpsno> " +
//            "    <DclEtpsNm>1申报企业名称</DclEtpsNm> " +
//            "    <DclcusTypecd>12-对应报关</DclcusTypecd> " +
//            "    <DecType>1报关单类型代码</DecType> " +
//            "    <ImpexpMarkcd>1入区为\"I\";出区为\"E\"</ImpexpMarkcd> " +
//            "    <TrspModecd>1运输方式代码</TrspModecd> " +
//            "    <StshipTrsarvNatcd>1启运国代码</StshipTrsarvNatcd> " +
//            "    <CorrEntryDclEtpsSccd>1对应报关单申报企业社会信用代码</CorrEntryDclEtpsSccd> " +
//            "    <CorrEntryDclEtpsNo>1对应报关单申报企业海关十位</CorrEntryDclEtpsNo> " +
//            "    <CorrEntryDclEtpsNm>1对应报关单申报企业</CorrEntryDclEtpsNm> " +
//            "   </Head> " +
//            "   <List> " +
//            "    <GdsSeqno>11“1”序号，由1递增的自然数</GdsSeqno> " +
//            "    <GdsMtno>11商品料号</GdsMtno> " +
//            "    <Gdecd>11商品编码</Gdecd> " +
//            "    <GdsNm>11商品名称</GdsNm> " +
//            "    <GdsSpcfModelDesc>11规格型号</GdsSpcfModelDesc> " +
//            "    <DclQty>11数量</DclQty> " +
//            "    <DclUnitcd>11计量单位</DclUnitcd> " +
//            "    <LawfQty>11法定数量</LawfQty> " +
//            "    <LawfUnitcd>11法定单位</LawfUnitcd> " +
//            "    <SecdLawfQty>11选填，第二法定数量，第二计量单位填写时必填</SecdLawfQty> " +
//            "    <SecdLawfUnitcd>11选填，第二计量单位</SecdLawfUnitcd> " +
//            "    <DclTotalAmt>11商品总价</DclTotalAmt> " +
//            "    <DclCurrcd>11币制</DclCurrcd> " +
//            "    <Natcd>11原产国代码</Natcd> " +
//            "    <LvyrlfModecd>11征免方式代码</LvyrlfModecd> " +
//            "    <Rmk>11备注,选填项</Rmk> " +
//            "   </List> " +
//            "   <List> " +
//            "    <GdsSeqno>122</GdsSeqno> " +
//            "    <GdsMtno>12商品料号</GdsMtno> " +
//            "    <Gdecd>12商品编码</Gdecd> " +
//            "    <GdsNm>12商品名称</GdsNm> " +
//            "    <GdsSpcfModelDesc>12规格型号</GdsSpcfModelDesc> " +
//            "    <DclQty>12数量</DclQty> " +
//            "    <DclUnitcd>12计量单位</DclUnitcd> " +
//            "    <LawfQty>12法定数量</LawfQty> " +
//            "    <LawfUnitcd>12法定单位</LawfUnitcd> " +
//            "    <SecdLawfQty>12选填，第二法定数量，第二计量单位填写时必填</SecdLawfQty> " +
//            "    <SecdLawfUnitcd>12选填，第二计量单位</SecdLawfUnitcd> " +
//            "    <DclTotalAmt>12商品总价</DclTotalAmt> " +
//            "    <DclCurrcd>12币制</DclCurrcd> " +
//            "    <Natcd>12原产国代码</Natcd> " +
//            "    <LvyrlfModecd>12征免方式代码</LvyrlfModecd> " +
//            "    <Rmk>12备注,选填项</Rmk> " +
//            "   </List> " +
//            "  </Data> " +
//            "  <Data> " +
//            "   <Head> " +
//            "    <EtpsInnerInvtNo>2必填项，企业内部编码:HZQD+海关十位+进出口标志（I/E）+YYYYMMDD+四位流水号</EtpsInnerInvtNo> " +
//            "    <PutrecNo>2必填项，账册编号</PutrecNo> " +
//            "    <DclPlcCuscd>2必填项，主管海关</DclPlcCuscd> " +
//            "    <ImpexpPortcd>2进出境关别</ImpexpPortcd> " +
//            "    <DclEtpsSccd>2申报企业社会信用代码</DclEtpsSccd> " +
//            "    <DclEtpsno>2申报企业海关十位</DclEtpsno> " +
//            "    <DclEtpsNm>2申报企业名称</DclEtpsNm> " +
//            "    <DclcusTypecd>22</DclcusTypecd> " +
//            "    <DecType>2报关单类型代码</DecType> " +
//            "    <ImpexpMarkcd>2I</ImpexpMarkcd> " +
//            "    <TrspModecd>2运输方式代码</TrspModecd> " +
//            "    <StshipTrsarvNatcd>2启运国代码</StshipTrsarvNatcd> " +
//            "    <CorrEntryDclEtpsSccd>2对应报关单申报企业社会信用代码</CorrEntryDclEtpsSccd> " +
//            "    <CorrEntryDclEtpsNo>2对应报关单申报企业海关十位</CorrEntryDclEtpsNo> " +
//            "    <CorrEntryDclEtpsNm>2对应报关单申报企业</CorrEntryDclEtpsNm> " +
//            "   </Head> " +
//            "   <List> " +
//            "    <GdsSeqno>21“1”序号，由1递增的自然数</GdsSeqno> " +
//            "    <GdsMtno>21商品料号</GdsMtno> " +
//            "    <Gdecd>21商品编码</Gdecd> " +
//            "    <GdsNm>21商品名称</GdsNm> " +
//            "    <GdsSpcfModelDesc>21规格型号</GdsSpcfModelDesc> " +
//            "    <DclQty>21数量</DclQty> " +
//            "    <DclUnitcd>21计量单位</DclUnitcd> " +
//            "    <LawfQty>21法定数量</LawfQty> " +
//            "    <LawfUnitcd>21法定单位</LawfUnitcd> " +
//            "    <SecdLawfQty>21选填，第二法定数量，第二计量单位填写时必填</SecdLawfQty> " +
//            "    <SecdLawfUnitcd>21选填，第二计量单位</SecdLawfUnitcd> " +
//            "    <DclTotalAmt>21商品总价</DclTotalAmt> " +
//            "    <DclCurrcd>21币制</DclCurrcd> " +
//            "    <Natcd>21原产国代码</Natcd> " +
//            "    <LvyrlfModecd>21征免方式代码</LvyrlfModecd> " +
//            "    <Rmk>21备注,选填项</Rmk> " +
//            "   </List> " +
//            "   <List> " +
//            "    <GdsSeqno>222</GdsSeqno> " +
//            "    <GdsMtno>22商品料号</GdsMtno> " +
//            "    <Gdecd>22商品编码</Gdecd> " +
//            "    <GdsNm>22商品名称</GdsNm> " +
//            "    <GdsSpcfModelDesc>22规格型号</GdsSpcfModelDesc> " +
//            "    <DclQty>22数量</DclQty> " +
//            "    <DclUnitcd>22计量单位</DclUnitcd> " +
//            "    <LawfQty>22法定数量</LawfQty> " +
//            "    <LawfUnitcd>22法定单位</LawfUnitcd> " +
//            "    <SecdLawfQty>22选填，第二法定数量，第二计量单位填写时必填</SecdLawfQty> " +
//            "    <SecdLawfUnitcd>22选填，第二计量单位</SecdLawfUnitcd> " +
//            "    <DclTotalAmt>22商品总价</DclTotalAmt> " +
//            "    <DclCurrcd>22币制</DclCurrcd> " +
//            "    <Natcd>22原产国代码</Natcd> " +
//            "    <LvyrlfModecd>22征免方式代码</LvyrlfModecd> " +
//            "    <Rmk>22备注,选填项</Rmk> " +
//            "   </List> " +
//            "  </Data> " +
//            " </DataInfo> " +
//            "</Package> ";
//}
