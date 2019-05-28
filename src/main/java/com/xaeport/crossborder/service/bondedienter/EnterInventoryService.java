package com.xaeport.crossborder.service.bondedienter;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.data.entity.BondInvtBsc;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.mapper.EnterInventoryMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import java.util.*;

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
    public void deleteEnterInventory(String submitKeys, Users users) throws Exception{
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("etpsInnerInvtNo", submitKeys);
        paramMap.put("entId", users.getEnt_Id());
        paramMap.put("roleId", users.getRoleId());
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

    //修改入区核注清单逻辑校验数据
    @Transactional
    public Map<String, String> updateEnterInvData(
            LinkedHashMap<String, String> bondInvtBsc,
            ArrayList<LinkedHashMap<String, String>> bondInvtDts,
            Users users
    ) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        if (updateEnterInvData(bondInvtBsc, bondInvtDts, users, rtnMap, "入区核注清单-编辑")) return rtnMap;

        rtnMap.put("result", "true");
        rtnMap.put("msg", "修改信息成功！");
        return rtnMap;
    }

    public boolean updateEnterInvData(
            LinkedHashMap<String, String> bondInvtBsc,
            List<LinkedHashMap<String, String>> bondInvtDts,
            Users users,
            Map<String, String> rtnMap,
            String notes
    ) {
        if ((CollectionUtils.isEmpty(bondInvtBsc) && bondInvtBsc.size() < 1) && CollectionUtils.isEmpty(bondInvtDts)) {
            rtnMap.put("result", "false");
            rtnMap.put("msg", "未发现要修改的入区核注清单数据！");
            return true;
        }
        String etps_inner_invt_no = bondInvtBsc.get("etps_inner_invt_no");
        if (!CollectionUtils.isEmpty(bondInvtBsc) && bondInvtBsc.size() > 1) {
            // 更新表头数据
            this.enterInventoryMapper.updateEnterInvHead(bondInvtBsc, users);
        }
        if (!CollectionUtils.isEmpty(bondInvtDts)) {
            // 更新表体数据
            for (LinkedHashMap<String, String> bondInvtDt : bondInvtDts) {
                if (!CollectionUtils.isEmpty(bondInvtDt) && bondInvtDt.size() > 2) {
                    this.enterInventoryMapper.updateEnterInvBody(bondInvtDt, users);
                }
            }
            //根据表体更新表头状态
            this.enterInventoryMapper.updateEnterInvHeadByBody(bondInvtBsc, users);
        }
        return false;
    }

}
