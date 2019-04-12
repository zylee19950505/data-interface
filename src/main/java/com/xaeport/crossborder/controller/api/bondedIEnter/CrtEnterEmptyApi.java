package com.xaeport.crossborder.controller.api.bondedienter;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.service.bondedIEnter.CrtEnterEmptyService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/api/crtEnterEmpty")
public class CrtEnterEmptyApi extends BaseApi {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    CrtEnterEmptyService crtEnterEmptyService;

   /*
   * 新建入区空车核放单
   * */
    @RequestMapping("/queryEnterEmpty")
    public ResponseData queryCrtEnterManifest(
            @RequestParam(required = false) String vehicle_no,//车牌号
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String length,
            @RequestParam(required = false) String draw,
            HttpServletRequest request
    ) {
        this.logger.debug(String.format("新建入区空车核放单查询条件参数:[vehicle_no:%s]", vehicle_no));
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("vehicle_no", vehicle_no);
        paramMap.put("passport_typecd", "6");
        paramMap.put("start", start);
        paramMap.put("length", length);
        paramMap.put("draw", draw);
        paramMap.put("entId", this.getCurrentUserEntId());
        paramMap.put("roleId", this.getCurrentUserRoleId());

        DataList<PassPortHead> dataList = new DataList<>();
        List<PassPortHead> resultList = new ArrayList<PassPortHead>();
        try {
            //查询列表
            //查找可用的大于0,和enter的
            resultList = this.crtEnterEmptyService.queryCrtEnterEmptyList(paramMap);
            Integer count = this.crtEnterEmptyService.queryCrtEnterEmptyCount(paramMap);
            dataList.setDraw(draw);
            dataList.setData(resultList);
            dataList.setRecordsTotal(count);
            dataList.setRecordsFiltered(count);
            return new ResponseData(dataList);
        } catch (Exception e) {
            this.logger.error("查询入区核放单数据失败", e);
            return new ResponseData("查询入区核放单数据失败", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 保存入区空车核放单
     */
    @RequestMapping(value = "saveEntryEmptyInfo")
    public ResponseData saveEntryEmptyInfo(@Param("entryJson") String entryJson) {
        LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>) JSONUtils.parse(entryJson);
        Map<String, String> rtnMap = new HashMap<>();
        Users users = this.getCurrentUsers();
        try {
            // 保存表头信息和关联单信息
            rtnMap = crtEnterEmptyService.saveEntryEmptyInfo(object, users);
        } catch (Exception e) {
            this.logger.error("保存入区空车核放单时发生异常", e);
            rtnMap.put("result", "false");
            rtnMap.put("msg", "保存入区空车核放单时发生异常");
        }
        return new ResponseData(rtnMap);
    }

    //空车核放单删除
    @RequestMapping(value = "/deleteEnterEmpty/{id}", method = RequestMethod.DELETE)
    public ResponseData manifestDelete(
            @PathVariable(value = "id") String id
    ) {
        this.logger.debug(String.format("核放单删除[id:%s]", id));
        this.crtEnterEmptyService.deleteEnterEmpty(id);
        return new ResponseData();
    }

}
