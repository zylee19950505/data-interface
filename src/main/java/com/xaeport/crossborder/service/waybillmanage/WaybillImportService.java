package com.xaeport.crossborder.service.waybillmanage;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.data.entity.Enterprise;
import com.xaeport.crossborder.data.entity.ImpLogistics;
import com.xaeport.crossborder.data.entity.ImpOrderHead;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.mapper.EnterpriseMapper;
import com.xaeport.crossborder.data.mapper.WaybillImportMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.tools.IdUtils;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class WaybillImportService {
    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    AppConfiguration appConfiguration;
    @Autowired
    WaybillImportMapper waybillImportMapper;
    @Autowired
    EnterpriseMapper enterpriseMapper;

    /*
     * 订单导入
     * @param importTime 申报时间
     */
    @Transactional
    public int createWaybillForm(Map<String, Object> excelMap, Users user) {
        int flag;
        try {
            String id = user.getId();
            flag = this.createImpLogistics(excelMap, user);
        } catch (Exception e) {
            flag = 2;
            this.log.error("导入失败", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return flag;
    }

    /*
     * 创建ImpOrderHead信息
     */
    private int createImpLogistics(Map<String, Object> excelMap, Users user) throws Exception {
        int flag = 0;
        Enterprise enterprise = enterpriseMapper.getEnterpriseDetail(user.getEnt_Id());
        List<ImpLogistics> impLogisticsList = (List<ImpLogistics>) excelMap.get("ImpLogistics");
        for (ImpLogistics anImpLogisticsList : impLogisticsList) {
            ImpLogistics impLogistics = this.impLogisticsData(anImpLogisticsList, user, enterprise);
            flag = this.waybillImportMapper.isRepeatLogisticsNo(impLogistics);
            if (flag > 0) {
                return 1;
            }
            this.waybillImportMapper.insertImpLogistics(impLogistics);//查询无订单号则插入ImpOrderHead数据
        }
        return flag;
    }

    /*
     * 查询有无重复物流运单编号
     */
    public String getLogisticsNoCount(Map<String, Object> excelMap) throws Exception {
        int flag = 0;
        List<ImpLogistics> list = (List<ImpLogistics>) excelMap.get("ImpLogistics");
        for (int i = 0; i < list.size(); i++) {
            ImpLogistics impLogistics = list.get(i);
            flag = this.waybillImportMapper.isRepeatLogisticsNo(impLogistics);
            if (flag > 0) {
                return impLogistics.getLogistics_no();
            }
        }
        return "0";
    }

    /**
     * 表自生成信息
     */
    private ImpLogistics impLogisticsData(ImpLogistics impLogistics, Users user, Enterprise enterprise) throws Exception {
        impLogistics.setGuid(IdUtils.getUUId());//企业系统生成36 位唯一序号（英文字母大写）
        impLogistics.setApp_type("1");//企业报送类型。1-新增2-变更3-删除。默认为1。
        impLogistics.setApp_status("2");//业务状态:1-暂存,2-申报,默认为2。
        impLogistics.setCurrency("142");//币制
        impLogistics.setPack_no("1");//件数
        impLogistics.setData_status(StatusCode.EXPORT);//设置为已导入CBDS1
        impLogistics.setCrt_id(StringUtils.isEmpty(user.getId()) ? "" : user.getId());//创建人
        impLogistics.setCrt_tm(new Date());//创建时间
        impLogistics.setUpd_id(StringUtils.isEmpty(user.getId()) ? "" : user.getId());//更新人
        impLogistics.setUpd_tm(new Date());//更新时间
        impLogistics.setEnt_id(enterprise.getId());
        impLogistics.setEnt_name(enterprise.getEnt_name());
        impLogistics.setEnt_customs_code(enterprise.getCustoms_code());
        return impLogistics;
    }

}
