package com.xaeport.crossborder.service.bondedIEnter;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.data.entity.BondInvtBsc;
import com.xaeport.crossborder.data.mapper.EnterInventoryMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EnterInventoryService {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    EnterInventoryMapper enterInventoryMapper;

    public List<BondInvtBsc> queryEnterInventory(Map<String, String> paramMap) {
        return enterInventoryMapper.queryEnterInventory(paramMap);
    }

    public Integer queryEnterInventoryCount(Map<String, String> paramMap) {
        return enterInventoryMapper.queryEnterInventoryCount(paramMap);
    }

    @Transactional
    public void deleteEnterInventory(String submitKeys, String entId) throws Exception{
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("etpsInnerInvtNo", submitKeys);
        paramMap.put("entId", entId);
        paramMap.put("status", StatusCode.RQHZQDDSB);
        List<BondInvtBsc> bondInvtBscList;
        try {
            bondInvtBscList = this.enterInventoryMapper.queryDeleteDataByCode(paramMap);
            if (CollectionUtils.isEmpty(bondInvtBscList)) return;
            String etpsInnerInvtNo;
            String invtNo;
            for (int i = 0; i < bondInvtBscList.size(); i++) {
                etpsInnerInvtNo = bondInvtBscList.get(i).getEtps_inner_invt_no();
                //invtNo = bondInvtBscList.get(i).getInvt_no();
                //this.enterInventoryMapper.updateInventoryByInvtNo(invtNo);
                //this.enterInventoryMapper.deleteNemsInvtCbecBillTypeByNo(etpsInnerInvtNo);
                this.enterInventoryMapper.deleteBondInvtBscByNo(etpsInnerInvtNo);
                this.enterInventoryMapper.deleteBondInvtDtByNo(etpsInnerInvtNo);
            }
        } catch (Exception e) {
            this.logger.error("删除入区核注清单数据失败", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new Exception("删除入区核注清单数据失败");
        }
    }
    @Transactional
    public boolean updateSubmitCustom(Map<String, String> paramMap) {
        boolean flag;
        try {
            this.enterInventoryMapper.updateSubmitCustom(paramMap);
            flag = true;
        } catch (Exception e) {
            flag = false;
            String exceptionMsg = String.format("申报入区核注清单[invtNo: %s]时发生异常", paramMap.get("submitKeys"));
            logger.error(exceptionMsg, e);
        }
        return flag;
    }
}
