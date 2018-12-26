package com.xaeport.crossborder.controller.api.bondedIEnter;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.BondInvtBsc;
import com.xaeport.crossborder.service.bondedIExit.CrtEnterManifestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/crtEnterManifest")
public class CrtEnterManifestApi extends BaseApi {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    CrtEnterManifestService crtEnterManifestService;

    /*
     *  新建入区核放单
     */
    @RequestMapping("/queryCrtEnterManifest")
    public ResponseData queryCrtEnterManifest(
            @RequestParam(required = false) String dataStatus,
            @RequestParam(required = false) String recordDataStatus,
            @RequestParam(required = false) String invtNo,
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String length,
            @RequestParam(required = false) String draw,
            HttpServletRequest request
    ) {
        this.logger.debug(String.format("新建入区核放单查询条件参数:[invtNo:%s]", invtNo));
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("invtNo", invtNo);
        paramMap.put("dataStatus", dataStatus);
        paramMap.put("recordDataStatus", recordDataStatus);
        paramMap.put("start", start);
        paramMap.put("length", length);
        paramMap.put("draw", draw);
        paramMap.put("entId", this.getCurrentUserEntId());
        paramMap.put("roleId", this.getCurrentUserRoleId());

        List<BondInvtBsc> resultList = new ArrayList<BondInvtBsc>();
        try {
            //查询列表
            resultList = this.crtEnterManifestService.queryCrtEnterManifest(paramMap);
        } catch (Exception e) {
            this.logger.error("查询入区核放单数据失败", e);
            return new ResponseData("查询入区核放单数据失败", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(resultList);

    }

}
