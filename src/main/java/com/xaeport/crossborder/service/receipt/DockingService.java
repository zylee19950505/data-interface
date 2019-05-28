package com.xaeport.crossborder.service.receipt;

import com.xaeport.crossborder.configuration.SystemConstants;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.mapper.BondOrderImpMapper;
import com.xaeport.crossborder.data.mapper.DockingMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.data.xml.*;
import com.xaeport.crossborder.data.xml.EnvelopInfo;
import com.xaeport.crossborder.tools.IdUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

//对接报文数据写入
@Service
public class DockingService {
    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    DockingMapper dockingMapper;
    @Autowired
    BondOrderImpMapper bondOrderImpMapper;

    @Transactional(rollbackForClassName = "Exception")
    public boolean crtDockingData(List<PackageXml> packageXmlList, String refileName) {
        boolean flag = true;
        try {
            String type = packageXmlList.get(0).getEnvelopInfo().getMsgIype();

            switch (type) {
                case SystemConstants.DJ_DD:
                    this.crtImpOrderData(packageXmlList, refileName);
                    break;
                case SystemConstants.DJ_HZQD:
                    this.crtBondInvtData(packageXmlList, refileName);
                    break;
            }
        } catch (Exception e) {
            flag = false;
            this.logger.error(String.format("报文回执入库失败,文件名为:%s", refileName), e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return flag;
    }

    /**
     * 对接订单报文数据
     */
    @Transactional(rollbackFor = NullPointerException.class)
    private void crtImpOrderData(List<PackageXml> packageXmlList, String refileName) throws Exception {
        ImpOrderHead impOrderHead;
        ImpOrderBody impOrderBody;
        String msgId;
        String msgIype;
        String senderId;
        String recvId;
        String icCard;
        String status;

        for (PackageXml packageXml : packageXmlList) {
            EnvelopInfo envelopInfo = packageXml.getEnvelopInfo();
            msgId = envelopInfo.getMsgId();
            msgIype = envelopInfo.getMsgIype();
            senderId = envelopInfo.getSenderId();
            recvId = envelopInfo.getRecvId();
            icCard = envelopInfo.getIcCard();
            status = envelopInfo.getStatus();

            Enterprise enterprise = this.dockingMapper.queryEntInfoByDxpId(senderId);

            List<DataInfo> dataInfoList = packageXml.getDataInfoList();
            for (DataInfo dataInfo : dataInfoList) {

                DataHead dataHead = dataInfo.getDataHead();
                List<DataBody> dataBodyList = dataInfo.getDataBodyList();

                int count = this.dockingMapper.findRepeatOrder(dataHead.getOrderid(), dataHead.getOrderNo());
                if (count > 0) {
                    this.logger.debug(String.format("》》》》》》》》》》》对接订单报文重复数据:[guid：%s,orderNo：%s]", dataHead.getOrderid(), dataHead.getOrderNo()));
                    continue;
                }

                impOrderHead = new ImpOrderHead();
                impOrderHead.setApp_Type("1");//企业报送类型。1-新增2-变更3-删除。默认为1。
                impOrderHead.setApp_Status("2");//业务状态:1-暂存,2-申报,默认为2。
                impOrderHead.setOrder_Type("I");//电子订单类型：I进口
                impOrderHead.setBuyer_Id_Type("1");//订购人证件类型
                impOrderHead.setCurrency("142");//币制
                impOrderHead.setData_status(StatusCode.BSDDDSB);//数据状态

                impOrderHead.setCrt_id("system");//创建人
                impOrderHead.setEnt_id(enterprise.getId());
                impOrderHead.setEnt_name(enterprise.getEnt_name());
                impOrderHead.setEnt_customs_code(enterprise.getCustoms_code());
                impOrderHead.setBusiness_type(SystemConstants.T_IMP_BOND_ORDER);

                impOrderHead.setGuid(dataHead.getOrderid());
                impOrderHead.setTrade_mode(dataHead.getTradeMode());
                impOrderHead.setOrder_No(dataHead.getOrderNo());
                impOrderHead.setBatch_Numbers(dataHead.getBatchNumbers());
                impOrderHead.setBill_No(dataHead.getBatchNumbers());
                impOrderHead.setEbp_Code(dataHead.getEbpCode());
                impOrderHead.setEbp_Name(dataHead.getEbpName());
                impOrderHead.setEbc_Code(enterprise.getCustoms_code());
                impOrderHead.setEbc_Name(enterprise.getEnt_name());
                impOrderHead.setPort_code(dataHead.getPortCode());
                impOrderHead.setBuyer_Reg_No(dataHead.getBuyerRegNo());
                impOrderHead.setBuyer_Name(dataHead.getBuyerName());
                impOrderHead.setBuyer_Id_Number(dataHead.getBuyerIdNumber());
                impOrderHead.setBuyer_TelePhone(dataHead.getBuyerTelephone());
                impOrderHead.setConsignee(dataHead.getConsignee());
                impOrderHead.setConsignee_Telephone(dataHead.getConsigneeTelephone());
                impOrderHead.setConsignee_Address(dataHead.getConsigneeAddress());
                impOrderHead.setInsured_fee(this.getDouble(dataHead.getInsuredFee()));
                impOrderHead.setFreight(this.getDouble(dataHead.getFreight()));
                impOrderHead.setDiscount(this.getDouble(dataHead.getDiscount()));
                impOrderHead.setTax_Total(this.getDouble(dataHead.getTaxTotal()));
                impOrderHead.setGross_weight(this.getDouble(dataHead.getGrossWeight()));
                impOrderHead.setNet_weight(this.getDouble(dataHead.getNetWeight()));

                for (DataBody dataBody : dataBodyList) {
                    dataBody.setPriceSum(Double.parseDouble(dataBody.getTotalPrice()));
                }

                Double goodsValue = dataBodyList.stream().mapToDouble(DataBody::getPriceSum).sum();
                // 实际支付金额 = 商品价格 + 运杂费 + 代扣税款 - 非现金抵扣金额，与支付凭证的支付金额一致。
                Double actural_Paid = goodsValue + Double.parseDouble(impOrderHead.getFreight()) + Double.parseDouble(impOrderHead.getTax_Total()) - Double.parseDouble(impOrderHead.getDiscount());

                impOrderHead.setGoods_Value(goodsValue.toString());
                impOrderHead.setActural_Paid(actural_Paid.toString());
                impOrderHead.setWriting_mode(StatusCode.DJBW);
                this.dockingMapper.insertImpOrderHead(impOrderHead);

                for (DataBody dataBody : dataBodyList) {
                    Double price = Double.parseDouble(dataBody.getTotalPrice()) / Double.parseDouble(dataBody.getQty());
                    BwlListType bwlListType = this.bondOrderImpMapper.queryBwlListTypeByItemNo(dataBody.getItemNo(), enterprise.getBrevity_code());

                    impOrderBody = new ImpOrderBody();
                    impOrderBody.setHead_guid(dataHead.getOrderid());
                    impOrderBody.setOrder_No(dataHead.getOrderNo());
                    impOrderBody.setG_num(Integer.valueOf(dataBody.getGnum()));
                    impOrderBody.setItem_Name(dataBody.getItemName());
                    impOrderBody.setItem_No(dataBody.getItemNo());
                    impOrderBody.setG_Model(dataBody.getGmodel());
                    impOrderBody.setUnit(dataBody.getUnit());
                    impOrderBody.setQty(dataBody.getQty());
                    impOrderBody.setTotal_Price(dataBody.getTotalPrice());
                    impOrderBody.setPrice(price.toString());

                    impOrderBody.setGds_seqno(StringUtils.isEmpty(bwlListType.getGds_seqno()) ? "无" : bwlListType.getGds_seqno());//账册对应项号
                    impOrderBody.setCountry(StringUtils.isEmpty(bwlListType.getNatcd()) ? "无" : bwlListType.getNatcd());//账册商品国别码
                    impOrderBody.setCurrency("142");//币制
                    impOrderBody.setBar_Code("无");//非必填项，没有必须写“无”
                    impOrderBody.setWriting_mode(StatusCode.DJBW);

                    this.dockingMapper.insertImpOrderBody(impOrderBody);
                }

                //插入订单表
                this.insertOrderNo(impOrderHead);
                this.logger.debug(String.format("》》》》》》》》》》》对接订单报文入库成功:[guid：%s,orderNo：%s]", dataHead.getOrderid(), dataHead.getOrderNo()));

            }

        }
    }

    private void insertOrderNo(ImpOrderHead impOrderHead) {
        String billNo = impOrderHead.getBill_No();
        String brevityCode = billNo.substring(0, 2);
        Integer sum = this.bondOrderImpMapper.queryEntInfoByBrevityCode(brevityCode);
        if (sum > 0) {
            OrderNo orderNo = new OrderNo();
            orderNo.setId(IdUtils.getUUId());
            orderNo.setOrder_no(impOrderHead.getOrder_No());
            orderNo.setCrt_tm(new Date());
            orderNo.setUsed("0");
            this.bondOrderImpMapper.insertOrderNo(orderNo);
        }
    }

    /**
     * 对接核注清单报文数据
     */
    @Transactional(rollbackFor = NullPointerException.class)
    private void crtBondInvtData(List<PackageXml> packageXmlList, String refileName) throws Exception {
        BondInvtBsc bondInvtBsc;
        BondInvtDt bondInvtDt;
        String msgId;
        String msgIype;
        String senderId;
        String recvId;
        String icCard;
        String status;

        for (PackageXml packageXml : packageXmlList) {
            DecimalFormat dfTwo = new DecimalFormat("0.00");
            EnvelopInfo envelopInfo = packageXml.getEnvelopInfo();
            msgId = envelopInfo.getMsgId();
            msgIype = envelopInfo.getMsgIype();
            senderId = envelopInfo.getSenderId();
            recvId = envelopInfo.getRecvId();
            icCard = envelopInfo.getIcCard();
            status = envelopInfo.getStatus();

            Enterprise enterprise = this.dockingMapper.queryEntInfoByDxpId(senderId);

            List<DataInfo> dataInfoList = packageXml.getDataInfoList();
            for (DataInfo dataInfo : dataInfoList) {
                DataHead dataHead = dataInfo.getDataHead();
                List<DataBody> dataBodyList = dataInfo.getDataBodyList();

                int count = this.dockingMapper.findRepeatBondInvt(dataHead.getEtpsInnerInvtNo());
                if (count > 0) {
                    this.logger.debug(String.format("》》》》》》》》》》》对接入区核注清单报文重复数据:[EtpsInnerInvtNo：%s]", dataHead.getEtpsInnerInvtNo()));
                    continue;
                }

                bondInvtBsc = new BondInvtBsc();
                int original_nm = 0;
                for (DataBody dataBody : dataBodyList) {
                    bondInvtDt = new BondInvtDt();
                    bondInvtDt.setId(IdUtils.getUUId());
                    bondInvtDt.setHead_etps_inner_invt_no(dataHead.getEtpsInnerInvtNo());
                    bondInvtDt.setGds_seqno(Integer.valueOf(dataBody.getGdsSeqno()));
                    bondInvtDt.setGds_mtno(dataBody.getGdsMtno());
                    bondInvtDt.setGdecd(dataBody.getGdecd());
                    bondInvtDt.setGds_nm(dataBody.getGdsNm());
                    bondInvtDt.setGds_spcf_model_desc(dataBody.getGdsSpcfModelDesc());
                    bondInvtDt.setDcl_qty(dataBody.getDclQty());
                    bondInvtDt.setDcl_unitcd(dataBody.getDclUnitcd());
                    bondInvtDt.setLawf_qty(dataBody.getLawfQty());
                    bondInvtDt.setLawf_unitcd(dataBody.getLawfUnitcd());
                    bondInvtDt.setSecd_lawf_qty(dataBody.getSecdLawfQty());
                    bondInvtDt.setSecd_lawf_unitcd(dataBody.getSecdLawfUnitcd());
                    bondInvtDt.setDcl_total_amt(dataBody.getDclTotalAmt());
                    String dclUprcAmt = dfTwo.format(Double.parseDouble(dataBody.getDclTotalAmt()) / Double.parseDouble(dataBody.getDclQty()));
                    bondInvtDt.setDcl_uprc_amt(dclUprcAmt);
                    bondInvtDt.setDcl_currcd(dataBody.getDclCurrcd());
                    bondInvtDt.setNatcd(dataBody.getNatcd());
                    bondInvtDt.setLvyrlf_modecd(dataBody.getLvyrlfModecd());
                    bondInvtDt.setRmk(dataBody.getRmk());
                    bondInvtDt.setDestination_natcd("142");
                    bondInvtDt.setModf_markcd("3");
                    bondInvtDt.setEntry_gds_seqno(Integer.valueOf(dataBody.getGdsSeqno()));
                    bondInvtDt.setWriting_mode(StatusCode.DJBW);
                    original_nm += Double.parseDouble(dataBody.getDclQty());
                    this.dockingMapper.insertBondInvtDt(bondInvtDt);
                }
                bondInvtBsc.setId(IdUtils.getUUId());
                bondInvtBsc.setEtps_inner_invt_no(dataHead.getEtpsInnerInvtNo());
                bondInvtBsc.setPutrec_no(dataHead.getPutrecNo());
                bondInvtBsc.setDcl_plc_cuscd(dataHead.getDclPlcCuscd());
                bondInvtBsc.setImpexp_portcd(dataHead.getImpexpPortcd());
                bondInvtBsc.setDcl_etps_sccd(dataHead.getDclEtpsSccd());
                bondInvtBsc.setDcl_etpsno(dataHead.getDclEtpsno());
                bondInvtBsc.setDcl_etps_nm(dataHead.getDclEtpsNm());
                bondInvtBsc.setDclcus_typecd(dataHead.getDclcusTypecd());
                bondInvtBsc.setDec_type(dataHead.getDecType());
                bondInvtBsc.setImpexp_markcd(dataHead.getImpexpMarkcd());
                bondInvtBsc.setTrsp_modecd(dataHead.getTrspModecd());
                bondInvtBsc.setStship_trsarv_natcd(dataHead.getStshipTrsarvNatcd());
                bondInvtBsc.setCorr_entry_dcl_etps_sccd(dataHead.getCorrEntryDclEtpsSccd());
                bondInvtBsc.setCorr_entry_dcl_etps_no(dataHead.getCorrEntryDclEtpsNo());
                bondInvtBsc.setCorr_entry_dcl_etps_nm(dataHead.getCorrEntryDclEtpsNm());

                //设置核注清单原有数量,可绑定数量,绑定数量
                bondInvtBsc.setOriginal_nm(original_nm);
                bondInvtBsc.setUsable_nm(original_nm);
                bondInvtBsc.setBound_nm(original_nm);

                bondInvtBsc.setCrt_ent_id(enterprise.getId());
                bondInvtBsc.setCrt_ent_name(enterprise.getEnt_name());
                bondInvtBsc.setRcvgd_etpsno(enterprise.getCustoms_code());
                bondInvtBsc.setRcvgd_etps_nm(enterprise.getEnt_name());

                bondInvtBsc.setCrt_user("system");
                bondInvtBsc.setImpexp_markcd("I");
                bondInvtBsc.setMtpck_endprd_markcd("I");
                bondInvtBsc.setSupv_modecd("1210");
                bondInvtBsc.setDclcus_flag("1");
                bondInvtBsc.setBond_invt_typecd("0");
                bondInvtBsc.setDcl_typecd("1");
                bondInvtBsc.setChg_tms_cnt(0);

                bondInvtBsc.setFlag(SystemConstants.BSRQ);
                bondInvtBsc.setWriting_mode(StatusCode.DJBW);
                bondInvtBsc.setStatus(StatusCode.RQHZQDDSB);
                bondInvtBsc.setBusiness_type(SystemConstants.T_BOND_INVT);

                this.dockingMapper.insertBondInvtBsc(bondInvtBsc);

                this.logger.debug(String.format("》》》》》》》》》》》对接入区核注清单报文入库成功:[EtpsInnerInvtNo：%s]", dataHead.getEtpsInnerInvtNo()));
            }

        }

    }

    protected String getString(String str) {
        if (!StringUtils.isEmpty(str)) {
            return str;
        } else {
            return "";
        }
    }

    protected String getDouble(String str) {
        DecimalFormat df = new DecimalFormat("0.00");
        if (!StringUtils.isEmpty(str)) {
            return df.format(Double.parseDouble(str));
        } else {
            return "0";
        }
    }

}
