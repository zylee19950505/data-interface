package com.xaeport.crossborder.service.waybillmanage;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.data.entity.ImpLogistics;
import com.xaeport.crossborder.data.entity.ImpLogisticsData;
import com.xaeport.crossborder.data.entity.LogisticsSum;
import com.xaeport.crossborder.data.mapper.WaybillDeclareMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class WaybillDeclareService {

    @Autowired
    WaybillDeclareMapper waybillMapper;

    private Log logger = LogFactory.getLog(this.getClass());



    /*
     * 查询运单申报数据
     */
    public List<LogisticsSum> queryWaybillDeclareDataList(Map<String, String> paramMap) throws Exception {
        return this.waybillMapper.queryWaybillDeclareDataList(paramMap);
    }

    /*
     * 查询运单申报总数
     */
    public Integer queryWaybillDeclareCount(Map<String, String> paramMap) throws Exception {
        return this.waybillMapper.queryWaybillDeclareCount(paramMap);
    }


    /**
     * 更新运单申报状态
     *
     * @return
     */
    @Transactional
    public boolean updateSubmitWaybill(Map<String, String> paramMap) {
        boolean flag;
        try {
            this.waybillMapper.updateSubmitWaybill(paramMap);
            flag = true;
        } catch (Exception e) {
            flag = false;
            String exceptionMsg = String.format("处理运单[bill_no: %s]时发生异常", paramMap.get("submitKeys"));
            logger.error(exceptionMsg, e);
        }
        return flag;
    }
    /**
     * 更新运单状态申报状态
     *
     * @return
     */
    @Transactional
    public boolean updateSubmitWaybillToStatus(Map<String, String> paramMap) {
        boolean flag;
        try {
            this.waybillMapper.updateSubmitWaybillToStatus(paramMap);
            flag = true;
        } catch (Exception e) {
            flag = false;
            String exceptionMsg = String.format("处理运单状态申报[bill_no: %s]时发生异常", paramMap.get("submitKeys"));
            logger.error(exceptionMsg, e);
        }
        return flag;
    }

    /*
    *运单提交时,判断是否符合条件(是否已经提交过,是否有回执)
    * */
    public String queryDateStatus(String submitKeys) {
        String[] split = submitKeys.split(",");
        String billNo;
        for (int i = 0;i<split.length;i++){
            billNo = split[i];
           int countDataStatus =this.waybillMapper.queryDateStatus(billNo);
           if (countDataStatus==0){
               return billNo;
           }
        }
        return "true";
    }

    public String queryStaDateStatus(String submitKeys) {
        String[] split = submitKeys.split(",");
        String billNo;
        for (int i = 0;i<split.length;i++){
            billNo = split[i];
            int countDataStatus =this.waybillMapper.queryStaDateStatus(billNo);
            if (countDataStatus==0){
                return billNo;
            }
        }
        return "true";
    }
}
