package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.Code;
import com.xaeport.crossborder.data.entity.ProduecCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LoadMapper {

    // 查询申报单位
    @Select("select " +
            "unit_code codeNo," +
            "unit_name codeName " +
            "from T_UNIT_CODE")
    List<Code> getUnitCodeList();

    // 查询运输方式T_TRAF_MODE
    @Select("select " +
            "mode_code codeNo," +
            "mode_name codeName " +
            "from T_TRAF_MODE")
    List<Code> getTrafModeList();

    // 查询港口
    @Select("select " +
            "port_code codeNo," +
            "port_name codeName " +
            "from T_PORT")
    List<Code> getPortList();

    // 查询陕西省关区代码
    @Select("select " +
            "customs_code codeNo," +
            "customs_name codeName " +
            "from T_CUSTOMS " +
            "where customs_code like '90%'")
    List<Code> getCustomsList();

    // 查询全部关区代码
    @Select("select " +
            "customs_code codeNo," +
            "customs_name codeName " +
            "from T_CUSTOMS " +
            "order by customs_code ")
    List<Code> getAllCustomsList();

    // 查询征减免税方式代码
    @Select("select " +
            "TR_CODE codeNo," +
            "TR_NAME codeName " +
            "from T_TAX_RELIEFS_MODE")
    List<Code> getTaxReliefsModeList();

    // 查询币制
    @Select("select " +
            "curr_code codeNo," +
            "curr_cn_name codeName," +
            "curr_en_name codeEnName " +
            "from T_CURRENCY")
    List<Code> getCurrencyList();

    // 查询国家地区代码
    @Select("select " +
            "ca_code codeNo," +
            "ca_cn_name codeName," +
            "ca_en_name codeEnName " +
            "from T_COUNTRY_AREA")
    List<Code> getCountryAreaList();

    // 查询证件类型
    @Select("select " +
            "certificate_code codeNo," +
            "certificate_name codeName " +
            "from T_CERTIFICATE_CODE")
    List<Code> getCertificateTypeList();

    // 企业类别（企业、自然人）
    @Select("select " +
            "agent_code codeNo," +
            "agent_name codeName " +
            "from T_AGENT_TYPE")
    List<Code> getAgentTypeList();

    // 企业性质（国营、合资、私营、集体....）
    @Select("select " +
            "agent_code codeNo," +
            "agent_name codeName " +
            "from T_AGENT_NATURE")
    List<Code> getAgentNatureList();

    // 企业分类（境内、境外）
    @Select("select " +
            "agent_id codeNo," +
            "agent_name codeName " +
            "from T_AGENT_CODE")
    List<Code> getAgentClassifyList();

    // 状态代码
    @Select("select " +
            "status_code codeNo," +
            "status_name codeName " +
            "from T_STATUS")
    List<Code> getStatusList();

    // 状态代码
    @Select("select " +
            "customs_code as customsCode," +
            "product_name as productName," +
            "unit_1 as unit1," +
            "unit_2 as unit2," +
            "import_duties_preference as importDutiesPreference," +
            "import_duties_general as importDutiesGeneral," +
            "added_tax as addedTax," +
            "consumption_tax as consumptionTax," +
            "regulatory_conditions as regulatoryConditions " +
            "from T_PRODUCTCODE")
    List<ProduecCode> getProduceCodeList();

    // 查询包装类型
    @Select("SELECT pack_code codeNo,pack_name codeName FROM T_PACK_TYPE")
    List<Code> getPackTypeList();

//    // 根据代码类型获取代码集
//    @Select("select c.key codeNo,c.value codeName from SYS_CODE c where c.status = '1' and c.parent = #{codeType}")
//    List<Code> getSysCodeListByParent(String codeType);

}
