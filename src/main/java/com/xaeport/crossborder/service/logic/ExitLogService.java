package com.xaeport.crossborder.service.logic;

import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.entity.VerifyBondHead;
import com.xaeport.crossborder.data.mapper.ExitLogMapper;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class ExitLogService {

    private Log log = LogFactory.getLog(this.getClass());


    @Autowired
    ExitLogMapper exitLogMapper;

    public List<VerifyBondHead> getLogicDataByExitBondInvt(Map<String, String> map) {
        return exitLogMapper.getLogicDataByExitBondInvt(map);
    }

    public List<VerifyBondHead> getLogicDataByExitPassPort(Map<String, String> map) {
        return exitLogMapper.getLogicDataByExitPassPort(map);
    }

    //修改入区核注清单逻辑校验数据
    @Transactional
    public Map<String, String> updateEnterBondInvtLog(
            LinkedHashMap<String, String> bondInvtBsc,
            ArrayList<LinkedHashMap<String, String>> bondInvtDts,
            Users users
    ) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        if (updateEnterLogic(bondInvtBsc, bondInvtDts, users, rtnMap, "入区核注清单-编辑")) return rtnMap;

        rtnMap.put("result", "true");
        rtnMap.put("msg", "编辑信息成功！");
        return rtnMap;

    }

    public boolean updateEnterLogic(
            LinkedHashMap<String, String> bondInvtBsc,
            List<LinkedHashMap<String, String>> bondInvtDts,
            Users users,
            Map<String, String> rtnMap,
            String notes
    ) {
        if ((CollectionUtils.isEmpty(bondInvtBsc) && bondInvtBsc.size() < 1) && CollectionUtils.isEmpty(bondInvtDts)) {
            rtnMap.put("result", "false");
            rtnMap.put("msg", "未发现要修改的入区核注清单数据数据！");
            return true;
        }
        String etps_inner_invt_no = bondInvtBsc.get("etps_inner_invt_no");
        if (!CollectionUtils.isEmpty(bondInvtBsc) && bondInvtBsc.size() > 1) {
            // 更新表头数据
            this.exitLogMapper.updateEnterBondInvtBscLogic(bondInvtBsc, users);
        }
        if (!CollectionUtils.isEmpty(bondInvtDts)) {
            // 更新表体数据
            for (LinkedHashMap<String, String> bondInvtDt : bondInvtDts) {
                if (!CollectionUtils.isEmpty(bondInvtDt) && bondInvtDt.size() > 2) {
                    exitLogMapper.updateEnterBondInvtDtLogic(bondInvtDt, users);
                }
            }
        }
        exitLogMapper.deleteVerifyStatus(etps_inner_invt_no);
        return false;
    }

}
