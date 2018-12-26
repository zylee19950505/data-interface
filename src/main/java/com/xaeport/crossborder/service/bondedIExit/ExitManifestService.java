package com.xaeport.crossborder.service.bondedIExit;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.data.entity.PassPortAcmp;
import com.xaeport.crossborder.data.entity.PassPortHead;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.mapper.ExitManifestMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class ExitManifestService {

    @Autowired
    ExitManifestMapper exitManifestMapper;

    private Log logger = LogFactory.getLog(this.getClass());


    //查询出区核放单数据
    public List<PassPortHead> queryExitManifestData(Map<String, String> paramMap) throws Exception {
        return this.exitManifestMapper.queryExitManifestData(paramMap);
    }

    //查询出区核放单总数
    public Integer queryExitManifestCount(Map<String, String> paramMap) throws Exception {
        return this.exitManifestMapper.queryExitManifestCount(paramMap);
    }

    //获取出区核放单表头数据
    public PassPortHead queryPassPortHead(Map<String, String> paramMap) throws Exception {
        return this.exitManifestMapper.queryPassPortHead(paramMap);
    }

    //获取出区核放单表体数据
    public List<PassPortAcmp> queryPassPortAcmp(Map<String, String> paramMap) throws Exception {
        return this.exitManifestMapper.queryPassPortAcmp(paramMap);
    }

    //删除清单逻辑校验未通过数据
    @Transactional
    public void deleteExitManifest(String submitKeys, String entId) throws Exception {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("etps_preent_no", submitKeys);
        paramMap.put("entId", entId);
        paramMap.put("status", StatusCode.CQHZQDDSB);
        List<PassPortHead> passPortHeadList;
        List<PassPortAcmp> passPortAcmpList;
        try {
            passPortHeadList = this.exitManifestMapper.queryPassPortHeadList(paramMap);
            passPortAcmpList = this.exitManifestMapper.queryPassPortAcmpList(paramMap);
            if (CollectionUtils.isEmpty(passPortHeadList)) return;
            if (CollectionUtils.isEmpty(passPortAcmpList)) return;
            String etpsPreentNo;
            String bondInvtNo;
            for (int i = 0; i < passPortHeadList.size(); i++) {
                bondInvtNo = passPortHeadList.get(i).getBond_invt_no();
                etpsPreentNo = passPortHeadList.get(i).getEtps_preent_no();
                this.exitManifestMapper.updateBondInvtBsc(bondInvtNo);
                this.exitManifestMapper.deletePassPortAcmp(etpsPreentNo);
                this.exitManifestMapper.deletePassPortHead(etpsPreentNo);
            }
        } catch (Exception e) {
            this.logger.error("删除出区核放单数据失败", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new Exception("删除出区核放单数据失败");
        }
    }


    //更新提交海关的出区核放单数据
    @Transactional
    public boolean updateSubmitCustom(Map<String, String> paramMap) {
        boolean flag;
        try {
            this.exitManifestMapper.updateSubmitCustom(paramMap);
            flag = true;
        } catch (Exception e) {
            flag = false;
            String exceptionMsg = String.format("申报出区核放单[etps_preent_no: %s]时发生异常", paramMap.get("submitKeys"));
            logger.error(exceptionMsg, e);
        }
        return flag;
    }


    //修改出区核放单数据
    @Transactional
    public Map<String, String> updateExitManifest(LinkedHashMap<String, String> passPortHead, ArrayList<LinkedHashMap<String, String>> passPortAcmpList, Users userInfo) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        if (saveExitManifest(passPortHead, passPortAcmpList, userInfo, rtnMap, "出区核放单-编辑")) return rtnMap;

        rtnMap.put("result", "true");
        rtnMap.put("msg", "编辑信息成功，请到“出区核放单”处重新进行申报！");
        return rtnMap;

    }

    //修改出区核放单数据
    public boolean saveExitManifest(
            LinkedHashMap<String, String> passPortHead,
            List<LinkedHashMap<String, String>> passPortAcmpList,
            Users userInfo,
            Map<String, String> rtnMap, String notes
    ) {
        if ((CollectionUtils.isEmpty(passPortHead) && passPortHead.size() < 1) && CollectionUtils.isEmpty(passPortAcmpList)) {
            rtnMap.put("result", "false");
            rtnMap.put("msg", "未发现需要修改数据！");
            return true;
        }
        String entryHeadId = passPortHead.get("entryhead_guid");
        if (!CollectionUtils.isEmpty(passPortHead) && passPortHead.size() > 1) {
            // 更新表头数据
            this.exitManifestMapper.updatePassPortHead(passPortHead, userInfo);
        }
        if (!CollectionUtils.isEmpty(passPortAcmpList)) {
            // 更新表体数据
            for (LinkedHashMap<String, String> passPortAcmp : passPortAcmpList) {
                if (!CollectionUtils.isEmpty(passPortAcmp)) {
                    exitManifestMapper.updatePassPortAcmp(passPortHead, userInfo);
                }
            }
        }
        return false;
    }

}
