package com.xaeport.crossborder.service.waybillmanage;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.data.entity.Enterprise;
import com.xaeport.crossborder.data.entity.ImpLogistics;
import com.xaeport.crossborder.data.entity.ImpLogisticsStatus;
import com.xaeport.crossborder.data.entity.Users;
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
           // flag = this.statusImportMapper.isRepeatLogisticsStatusNo(impLogisticsStatus);
            //if (flag > 0) {
           //     return 1;
          //  }
            try {
                this.statusImportMapper.insertImpLogisticsStatus(impLogisticsStatus);//运单状态导入(插入运单表)
            } catch (Exception e) {
                flag = 1;
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }
        return flag;
    }

    /*
     * 判断运单表里是否存在这个运单信息
     */
    public String getLogisticsStatusNoCount(Map<String, Object> excelMap) throws Exception {
        int flag = 0;
        List<ImpLogistics> list = (List<ImpLogistics>) excelMap.get("ImpLogisticsStatus");
        for (int i = 0; i < list.size(); i++) {
            ImpLogistics impLogisticsStatus = list.get(i);
            flag = this.statusImportMapper.isRepeatLogisticsStatusNo(impLogisticsStatus);
            if (flag == 0) {
                return impLogisticsStatus.getLogistics_no();
            }
        }
        return "0";
    }

    /**
     * 表自生成信息
     */
    private ImpLogistics ImpLogisticsStatusData(ImpLogistics impLogisticsStatus, Users user, Enterprise enterprise) throws Exception {
        //impLogisticsStatus.setGuid(IdUtils.getUUId());//企业系统生成36 位唯一序号（英文字母大写）
        impLogisticsStatus.setApp_type("1");//企业报送类型。1-新增2-变更3-删除。默认为1。
        impLogisticsStatus.setApp_status("2");//业务状态:1-暂存,2-申报,默认为2。
        impLogisticsStatus.setLogistics_status("S");//运抵
        impLogisticsStatus.setCrt_id(StringUtils.isEmpty(user.getId()) ? "" : user.getId());//创建人
        //impLogisticsStatus.setCrt_tm(new Date());//创建时间
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

    public void updateLogisticsStatus(Map<String, Object> excelMap) throws Exception {
        List<ImpLogistics> list = (List<ImpLogistics>) excelMap.get("ImpLogisticsStatus");
        for (int i = 0; i < list.size(); i++) {
            ImpLogistics impLogisticsStatus = list.get(i);
            this.statusImportMapper.updateLogisticsStatus(impLogisticsStatus);
        }
    }

    public void updateLogistics(Map<String, Object> excelMap) {
        List<ImpLogistics> list = (List<ImpLogistics>) excelMap.get("ImpLogisticsStatus");
        for (int i = 0; i < list.size(); i++) {
            ImpLogistics impLogisticsStatus = list.get(i);
            this.statusImportMapper.updateLogistics(impLogisticsStatus);
        }
    }
//判断excel里输入的时间是否全部是数字
    public String jugerLogisticsTime(Map<String, Object> excelMap) {
        //debug 能把输入的直接放入ImpLogistics 实体date类型的logisticsTime 里吗
        List<ImpLogistics> list = (List<ImpLogistics>) excelMap.get("ImpLogisticsStatus");
        for (ImpLogistics impLogistics:list) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String logisticsTime  = simpleDateFormat.format(impLogistics.getLogistics_time());
            boolean matches = logisticsTime.replaceAll("/(^\\s*)|(\\s*$)/g","").matches("^((?!0000)[0-9]{4}-((0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-8])|(0[13-9]|1[0-2])-(29|30)|(0[13578]|1[02])-31)|([0-9]{2}(0[48]|[2468][048]|[13579][26])|(0[48]|[2468][048]|[13579][26])00)-02-29)\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d$");
            boolean length = logisticsTime.toCharArray().length == 19;
            if (!matches||!length){
                return impLogistics.getLogistics_no();
            }
        }
        return "true";
    }
}
