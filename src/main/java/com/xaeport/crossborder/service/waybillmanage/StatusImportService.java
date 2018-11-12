package com.xaeport.crossborder.service.waybillmanage;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.mapper.EnterpriseMapper;
import com.xaeport.crossborder.data.mapper.StatusImportMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.tools.IdUtils;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class StatusImportService {

    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    AppConfiguration appConfiguration;
    @Autowired
    StatusImportMapper statusImportMapper;
    @Autowired
    EnterpriseMapper enterpriseMapper;

    /*
     * 运单状态单导入
     */
    @Transactional
    public int createWaybillForm(Map<String, Object> excelMap, Users user) {
        int flag;
        try {
            String id = user.getId();
            flag = this.createImpLogisticsStatus(excelMap, user);
        } catch (Exception e) {
            flag = 2;
            this.log.error("导入失败", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return flag;
    }

    /*
     * 创建ImpLogisticsStatus信息
     */
    @Transactional
    public int createImpLogisticsStatus(Map<String, Object> excelMap, Users user) throws Exception {
        int flag = 0;
        Enterprise enterprise = enterpriseMapper.getEnterpriseDetail(user.getEnt_Id());
        List<ImpLogistics> ImpLogisticsStatusList = (List<ImpLogistics>) excelMap.get("ImpLogisticsStatus");
        for (ImpLogistics anImpLogisticsStatusList : ImpLogisticsStatusList) {
            ImpLogistics impLogisticsStatus = this.ImpLogisticsStatusData(anImpLogisticsStatusList, user, enterprise);
            try {
                this.statusImportMapper.updateImpLogisticsStatus(impLogisticsStatus);//运单状态导入(插入运单表)
            } catch (Exception e) {
                flag = 1;
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }
        return flag;
    }

    /**
     * 表自生成信息
     */
    private ImpLogistics ImpLogisticsStatusData(ImpLogistics impLogisticsStatus, Users user, Enterprise enterprise) throws Exception {
        impLogisticsStatus.setApp_type("1");//企业报送类型。1-新增2-变更3-删除。默认为1。
        impLogisticsStatus.setApp_status("2");//业务状态:1-暂存,2-申报,默认为2。
        impLogisticsStatus.setLogistics_status("S");//运抵
        impLogisticsStatus.setCrt_id(StringUtils.isEmpty(user.getId()) ? "" : user.getId());//创建人
        impLogisticsStatus.setUpd_id(StringUtils.isEmpty(user.getId()) ? "" : user.getId());//更新人
        impLogisticsStatus.setUpd_tm(new Date());//更新时间
        impLogisticsStatus.setData_status(StatusCode.YDZTDSB);//设置为待申报CBDS5,运单状态不用校验
        impLogisticsStatus.setEnt_id(enterprise.getId());
        impLogisticsStatus.setEnt_name(enterprise.getEnt_name());
        impLogisticsStatus.setEnt_customs_code(enterprise.getCustoms_code());
        return impLogisticsStatus;
    }

    public String getLogisticsNoCount(Map<String, Object> excelMap) {
        int flag = 0;
        List<ImpLogistics> list = (List<ImpLogistics>) excelMap.get("ImpLogisticsStatus");
        for (int i = 0; i < list.size(); i++) {
            ImpLogistics impLogisticsStatus = list.get(i);
            flag = this.statusImportMapper.isEmptyLogisticsNo(impLogisticsStatus);
            if (flag == 0) {
                return impLogisticsStatus.getLogistics_no();
            }
        }
        return "true";
    }

    public String getLogisticsSuccess(Map<String, Object> excelMap) {
        int flag = 0;
        List<ImpLogistics> list = (List<ImpLogistics>) excelMap.get("ImpLogisticsStatus");
        for (int i = 0; i < list.size(); i++) {
            ImpLogistics impLogisticsStatus = list.get(i);
            flag = this.statusImportMapper.getLogisticsSuccess(impLogisticsStatus);
            if (flag == 0) {
                return impLogisticsStatus.getLogistics_no();
            }
        }
        return "true";
    }

    //判断excel里输入的时间是否符合规范
    public String checkLogisticsTime(Map<String, Object> excelMap) {
        List<ImpLogistics> list = (List<ImpLogistics>) excelMap.get("ImpLogisticsStatus");
        //时间yyyyMMddHHmmss正则表达式
        String TimeReg = "^((?:19|20)\\d\\d)(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])(0\\d|1\\d|2[0-3])(0\\d|[1-5]\\d)(0\\d|[1-5]\\d)$";
        for (ImpLogistics impLogistics : list) {
            String logisticsTimeChar = impLogistics.getLogistics_time_char();
            boolean flag = logisticsTimeChar.matches(TimeReg);
            if (!flag) {
                return impLogistics.getLogistics_no();
            }
        }
        return "true";
    }
}
