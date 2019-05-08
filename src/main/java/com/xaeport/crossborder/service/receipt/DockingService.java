package com.xaeport.crossborder.service.receipt;

import com.xaeport.crossborder.configuration.SystemConstants;
import com.xaeport.crossborder.data.entity.BondInvtBsc;
import com.xaeport.crossborder.data.entity.BondInvtDt;
import com.xaeport.crossborder.data.entity.ImpOrderBody;
import com.xaeport.crossborder.data.entity.ImpOrderHead;
import com.xaeport.crossborder.data.mapper.DockingMapper;
import com.xaeport.crossborder.data.xml.*;
import com.xaeport.crossborder.tools.IdUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

//对接报文数据写入
@Service
public class DockingService {
    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    DockingMapper dockingMapper;

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

            List<DataInfo> dataInfoList = packageXml.getDataInfoList();
            for (DataInfo dataInfo : dataInfoList) {
                DataHead dataHead = dataInfo.getDataHead();
                impOrderHead = new ImpOrderHead();
                impOrderHead.setGuid(dataHead.getOrderid());
                impOrderHead.setTrade_mode(dataHead.getTradeMode());
                impOrderHead.setOrder_No(dataHead.getOrderNo());
                impOrderHead.setBatch_Numbers(dataHead.getBatchNumbers());
                impOrderHead.setEbp_Code(dataHead.getEbpCode());
                impOrderHead.setEbp_Name(dataHead.getEbpName());
                impOrderHead.setPort_code(dataHead.getPortCode());
                impOrderHead.setBuyer_Reg_No(dataHead.getBuyerRegNo());
                impOrderHead.setBuyer_Name(dataHead.getBuyerName());
                impOrderHead.setBuyer_Id_Number(dataHead.getBuyerIdNumber());
                impOrderHead.setBuyer_TelePhone(dataHead.getBuyerTelephone());
                impOrderHead.setConsignee(dataHead.getConsignee());
                impOrderHead.setConsignee_Telephone(dataHead.getConsigneeTelephone());
                impOrderHead.setConsignee_Address(dataHead.getConsigneeAddress());
                impOrderHead.setInsured_fee(dataHead.getInsuredFee());
                impOrderHead.setFreight(dataHead.getFreight());
                impOrderHead.setDiscount(dataHead.getDiscount());
                impOrderHead.setTax_Total(dataHead.getTaxTotal());
                impOrderHead.setGross_weight(dataHead.getGrossWeight());
                impOrderHead.setNet_weight(dataHead.getNetWeight());
                this.dockingMapper.insertImpOrderHead(impOrderHead);

                List<DataBody> dataBodyList = dataInfo.getDataBodyList();
                for (DataBody dataBody : dataBodyList) {
                    impOrderBody = new ImpOrderBody();
                    impOrderBody.setG_num(Integer.valueOf(dataBody.getGnum()));
                    impOrderBody.setHead_guid(dataHead.getOrderid());
                    impOrderBody.setItem_Name(dataBody.getItemName());
                    impOrderBody.setItem_No(dataBody.getItemNo());
                    impOrderBody.setG_Model(dataBody.getGmodel());
                    impOrderBody.setQty(dataBody.getQty());
                    impOrderBody.setUnit(dataBody.getUnit());
                    impOrderBody.setTotal_Price(dataBody.getTotalPrice());
                    this.dockingMapper.insertImpOrderBody(impOrderBody);
                }

            }

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
            EnvelopInfo envelopInfo = packageXml.getEnvelopInfo();
            msgId = envelopInfo.getMsgId();
            msgIype = envelopInfo.getMsgIype();
            senderId = envelopInfo.getSenderId();
            recvId = envelopInfo.getRecvId();
            icCard = envelopInfo.getIcCard();
            status = envelopInfo.getStatus();

            List<DataInfo> dataInfoList = packageXml.getDataInfoList();
            for (DataInfo dataInfo : dataInfoList) {
                DataHead dataHead = dataInfo.getDataHead();
                bondInvtBsc = new BondInvtBsc();
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
                this.dockingMapper.insertBondInvtBsc(bondInvtBsc);

                List<DataBody> dataBodyList = dataInfo.getDataBodyList();
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
                    bondInvtDt.setDcl_currcd(dataBody.getDclCurrcd());
                    bondInvtDt.setNatcd(dataBody.getNatcd());
                    bondInvtDt.setLvyrlf_modecd(dataBody.getLvyrlfModecd());
                    bondInvtDt.setRmk(dataBody.getRmk());
                    this.dockingMapper.insertBondInvtDt(bondInvtDt);
                }

            }

        }

    }

}
