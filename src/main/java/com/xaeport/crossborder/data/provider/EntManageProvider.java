package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.Enterprise;
import com.xaeport.crossborder.data.entity.SysLog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Created by lzy on 2018/7/4.
 */
public class EntManageProvider extends BaseSQLProvider {

    //系统日志—查询数据
    public String queryAllEntInfo(Map<String,String> paramMap) throws Exception{
        final String entInfo = paramMap.get("entInfo");
        return new SQL(){
            {
                SELECT("t.ID");
                SELECT("t.ENT_NAME");
                SELECT("t.ENT_CODE");
                SELECT("t.ENT_LEGAL");
                SELECT("t.ENT_PHONE");
                SELECT("t.ENT_UNIQUE_CODE");
                SELECT("t.ORG_CODE");
                SELECT("t.BUSINESS_CODE");
                SELECT("t.TAX_CODE");
                SELECT("t.CREDIT_CODE");
                SELECT("t.ENT_TYPE");
                SELECT("t.ENT_NATURE");
                SELECT("t.PORT");
                SELECT("t.CUSTOMS_CODE");
                SELECT("t.ENT_CLASSIFY");
                SELECT("t.CRT_TM");
                SELECT("t.UPD_TM");
                SELECT("t.CRT_ID");
                SELECT("t.UPD_ID");
                SELECT("t.ENT_BUSINESS_TYPE");
                SELECT("t.STATUS as STATUS");
                SELECT("DECODE(STATUS,'1','已激活','0','已冻结','') as ENT_STATUS");
                SELECT("(select CUSTOMS_FULL_NAME from T_CUSTOMS c where t.PORT = c.CUSTOMS_CODE ) as PORT_STR");
                FROM("T_ENTERPRISE t");
                if (!StringUtils.isEmpty(entInfo)) {
                    WHERE("t.ENT_NAME like '%'|| #{entInfo} || '%'").OR().
                            WHERE("t.CUSTOMS_CODE like '%'|| #{entInfo} || '%'");
                }
                ORDER_BY("t.CRT_TM desc");
            }
        }.toString();
    }

    //更改企业信息数据
    public String updateEnterprise(final Enterprise enterprise){
        return new SQL(){
            {
                UPDATE("T_ENTERPRISE");
                if (!StringUtils.isEmpty(enterprise.getId())) {
                    WHERE("ID = #{id}");
                }
                if (!StringUtils.isEmpty(enterprise.getEnt_name())) {
                    SET("ENT_NAME = #{ent_name}");
                }
                if (!StringUtils.isEmpty(enterprise.getEnt_code())) {
                    SET("ENT_CODE = #{ent_code}");
                }
                if (!StringUtils.isEmpty(enterprise.getEnt_legal())) {
                    SET("ENT_LEGAL = #{ent_legal}");
                }
                if (!StringUtils.isEmpty(enterprise.getEnt_phone())) {
                    SET("ENT_PHONE = #{ent_phone}");
                }
                if (!StringUtils.isEmpty(enterprise.getEnt_unique_code())) {
                    SET("ENT_UNIQUE_CODE = #{ent_unique_code}");
                }
                if (!StringUtils.isEmpty(enterprise.getOrg_code())) {
                    SET("ORG_CODE = #{org_code}");
                }
                if (!StringUtils.isEmpty(enterprise.getBusiness_code())) {
                    SET("BUSINESS_CODE = #{business_code}");
                }
                if (!StringUtils.isEmpty(enterprise.getTax_code())) {
                    SET("TAX_CODE = #{tax_code}");
                }
                if (!StringUtils.isEmpty(enterprise.getCredit_code())) {
                    SET("CREDIT_CODE = #{credit_code}");
                }
                if (!StringUtils.isEmpty(enterprise.getEnt_type())) {
                    SET("ENT_TYPE = #{ent_type}");
                }
                if (!StringUtils.isEmpty(enterprise.getEnt_nature())) {
                    SET("ENT_NATURE = #{ent_nature}");
                }
                if (!StringUtils.isEmpty(enterprise.getPort())) {
                    SET("PORT = #{port}");
                }
                if (!StringUtils.isEmpty(enterprise.getCustoms_code())) {
                    SET("CUSTOMS_CODE = #{customs_code}");
                }
                if (!StringUtils.isEmpty(enterprise.getEnt_classify())) {
                    SET("ENT_CLASSIFY = #{ent_classify}");
                }
                if (!StringUtils.isEmpty(enterprise.getStatus())) {
                    SET("STATUS = #{status}");
                }
                if (!StringUtils.isEmpty(enterprise.getUpd_tm())) {
                    SET("UPD_TM = sysdate");
                }
                if (!StringUtils.isEmpty(enterprise.getDxp_id())) {
                    SET("DXP_ID = #{dxp_id}");
                }
                if (!StringUtils.isEmpty(enterprise.getEnt_business_type())) {
                    SET("ENT_BUSINESS_TYPE = #{ent_business_type}");
                }
            }
        }.toString();
    }

    //创建企业信息数据
    public String createEntInfo(final Enterprise enterprise){
        return new SQL(){
            {
                INSERT_INTO("T_ENTERPRISE");
                if(!StringUtils.isEmpty(enterprise.getId())){
                    VALUES("ID","#{id}");
                }
                if(!StringUtils.isEmpty(enterprise.getEnt_name())){
                    VALUES("ENT_NAME","#{ent_name}");
                }
                if(!StringUtils.isEmpty(enterprise.getEnt_code())){
                    VALUES("ENT_CODE","#{ent_code}");
                }
                if(!StringUtils.isEmpty(enterprise.getEnt_legal())){
                    VALUES("ENT_LEGAL","#{ent_legal}");
                }
                if(!StringUtils.isEmpty(enterprise.getEnt_phone())){
                    VALUES("ENT_PHONE","#{ent_phone}");
                }
                if(!StringUtils.isEmpty(enterprise.getEnt_unique_code())){
                    VALUES("ENT_UNIQUE_CODE","#{ent_unique_code}");
                }
                if(!StringUtils.isEmpty(enterprise.getOrg_code())){
                    VALUES("ORG_CODE","#{org_code}");
                }
                if(!StringUtils.isEmpty(enterprise.getBusiness_code())){
                    VALUES("BUSINESS_CODE","#{business_code}");
                }
                if(!StringUtils.isEmpty(enterprise.getTax_code())){
                    VALUES("TAX_CODE","#{tax_code}");
                }
                if(!StringUtils.isEmpty(enterprise.getCredit_code())){
                    VALUES("CREDIT_CODE","#{credit_code}");
                }
                if(!StringUtils.isEmpty(enterprise.getEnt_type())){
                    VALUES("ENT_TYPE","#{ent_type}");
                }
                if(!StringUtils.isEmpty(enterprise.getEnt_nature())){
                    VALUES("ENT_NATURE","#{ent_nature}");
                }
                if(!StringUtils.isEmpty(enterprise.getPort())){
                    VALUES("PORT","#{port}");
                }
                if(!StringUtils.isEmpty(enterprise.getCustoms_code())){
                    VALUES("CUSTOMS_CODE","#{customs_code}");
                }
                if(!StringUtils.isEmpty(enterprise.getEnt_classify())){
                    VALUES("ENT_CLASSIFY","#{ent_classify}");
                }
                if(!StringUtils.isEmpty(enterprise.getStatus())){
                    VALUES("STATUS","#{status}");
                }
                if(!StringUtils.isEmpty(enterprise.getCrt_tm())){
                    VALUES("CRT_TM","#{crt_tm}");
                }
                if(!StringUtils.isEmpty(enterprise.getDxp_id())){
                    VALUES("DXP_ID","#{dxp_id}");
                }
                if(!StringUtils.isEmpty(enterprise.getEnt_business_type())){
                    VALUES("ENT_BUSINESS_TYPE","#{ent_business_type}");
                }

            }
        }.toString();
    }


















}
