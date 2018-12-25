package com.xaeport.crossborder.service.bondedIExit;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.data.entity.BondInvtBsc;
import com.xaeport.crossborder.data.entity.BondInvtDt;
import com.xaeport.crossborder.data.entity.NemsInvtCbecBillType;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.mapper.ExitInventoryMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class ExitInventoryService {

    @Autowired
    ExitInventoryMapper exitInventoryMapper;

    private Log logger = LogFactory.getLog(this.getClass());


    //查询跨境清单数据
    public List<BondInvtBsc> queryEInventoryList(Map<String, String> paramMap) throws Exception {
        return this.exitInventoryMapper.queryEInventoryList(paramMap);
    }

    //查询跨境清单总数
    public Integer queryEInventoryCount(Map<String, String> paramMap) throws Exception {
        return this.exitInventoryMapper.queryEInventoryCount(paramMap);
    }

    public BondInvtBsc queryBondInvtBsc(Map<String, String> paramMap) throws Exception {
        return this.exitInventoryMapper.queryBondInvtBsc(paramMap);
    }

    public List<NemsInvtCbecBillType> queryNemsInvtCbecBillTypeList(Map<String, String> paramMap) throws Exception {
        return this.exitInventoryMapper.queryNemsInvtCbecBillTypeList(paramMap);
    }

    //删除清单逻辑校验未通过数据
    @Transactional
    public void deleteExitInventory(String submitKeys, String entId) throws Exception {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("etpsInnerInvtNo", submitKeys);
        paramMap.put("entId", entId);
        paramMap.put("status", StatusCode.CQHZQDDSB);
        List<BondInvtBsc> bondInvtBscList;
        List<NemsInvtCbecBillType> nemsInvtCbecBillTypeList;
        try {
            bondInvtBscList = this.exitInventoryMapper.queryDeleteHeadByCode(paramMap);
            nemsInvtCbecBillTypeList = this.exitInventoryMapper.queryDeleteListByCode(paramMap);
            if (CollectionUtils.isEmpty(bondInvtBscList)) return;
            if (CollectionUtils.isEmpty(nemsInvtCbecBillTypeList)) return;
            String etpsInnerInvtNo;
            String invtNo;
            for (int i = 0; i < nemsInvtCbecBillTypeList.size(); i++) {
                invtNo = nemsInvtCbecBillTypeList.get(i).getCbec_bill_no();
                this.exitInventoryMapper.updateInventoryByInvtNo(invtNo);
            }
            for (int i = 0; i < bondInvtBscList.size(); i++) {
                etpsInnerInvtNo = bondInvtBscList.get(i).getEtps_inner_invt_no();
                this.exitInventoryMapper.deleteNemsInvtCbecBillTypeByNo(etpsInnerInvtNo);
                this.exitInventoryMapper.deleteBondInvtBscByNo(etpsInnerInvtNo);
            }
        } catch (Exception e) {
            this.logger.error("删除出区核注清单数据失败", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new Exception("删除出区核注清单数据失败");
        }
    }

    @Transactional
    public boolean updateSubmitCustom(Map<String, String> paramMap) {
        boolean flag;
        try {
            this.exitInventoryMapper.updateSubmitCustom(paramMap);
            flag = true;
        } catch (Exception e) {
            flag = false;
            String exceptionMsg = String.format("申报出区核注清单[orderNo: %s]时发生异常", paramMap.get("submitKeys"));
            logger.error(exceptionMsg, e);
        }
        return flag;
    }

    @Transactional
    public Map<String, String> updateExitInventory(LinkedHashMap<String, String> BondInvtBsc, ArrayList<LinkedHashMap<String, String>> nemsInvtCbecBillTypeList, Users userInfo) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        if (saveExitBondInvt(BondInvtBsc, nemsInvtCbecBillTypeList, userInfo, rtnMap, "出区核注清单-编辑")) return rtnMap;

        rtnMap.put("result", "true");
        rtnMap.put("msg", "编辑信息成功，请到“出区核注清单”处重新进行申报！");
        return rtnMap;

    }

    public boolean saveExitBondInvt(
            LinkedHashMap<String, String> BondInvtBsc,
            List<LinkedHashMap<String, String>> nemsInvtCbecBillTypeList,
            Users userInfo,
            Map<String, String> rtnMap, String notes
    ) {
        if ((CollectionUtils.isEmpty(BondInvtBsc) && BondInvtBsc.size() < 1) && CollectionUtils.isEmpty(nemsInvtCbecBillTypeList)) {
            rtnMap.put("result", "false");
            rtnMap.put("msg", "未发现需要修改数据！");
            return true;
        }
        String entryHeadId = BondInvtBsc.get("entryhead_guid");
        if (!CollectionUtils.isEmpty(BondInvtBsc) && BondInvtBsc.size() > 1) {
            // 更新表头数据
            this.exitInventoryMapper.updateBondInvtBsc(BondInvtBsc,userInfo);
        }
        if (!CollectionUtils.isEmpty(nemsInvtCbecBillTypeList)) {
            // 更新表体数据
            for (LinkedHashMap<String, String> nemsInvtCbecBillType : nemsInvtCbecBillTypeList) {
                if (!CollectionUtils.isEmpty(nemsInvtCbecBillType) && nemsInvtCbecBillType.size() > 2) {
                    exitInventoryMapper.updateNemsInvtCbecBillType(nemsInvtCbecBillType,userInfo);
                }
            }
            this.exitInventoryMapper.updateBondInvtBscByList(BondInvtBsc,userInfo);
        }
        return false;
    }

}
