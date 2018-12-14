package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.BwlHeadType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Map;

public class BooksManageSQLProvider extends BaseSQLProvider {

    public String queryAllBooksInfo(Map<String, String> paramMap) {

        final String booksInfo = paramMap.get("booksInfo");

        return new SQL() {
            {
                SELECT("*");
                FROM("T_BWL_HEAD_TYPE t");
                if (!StringUtils.isEmpty(booksInfo)) {
                    WHERE("t.BWS_NO = #{booksInfo}");
                }
            }
        }.toString();
    }

    public String getBooksById(String id) {
        return new SQL() {
            {
                SELECT("*");
                FROM("T_BWL_HEAD_TYPE t");
                WHERE("t.id = #{id}");
            }
        }.toString();
    }

    public String updateBooks(@Param("bwlHeadType") BwlHeadType bwlHeadType) throws Exception {
        return new SQL() {
            {
                UPDATE("T_BWL_HEAD_TYPE");
                WHERE("ID = #{bwlHeadType.id}");
                if (!StringUtils.isEmpty(bwlHeadType.getBws_no())) {
                    SET("BWS_NO = #{bwlHeadType.bws_no}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getChg_tms_cnt())) {
                    SET("CHG_TMS_CNT = #{bwlHeadType.chg_tms_cnt}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getEtps_preent_no())) {
                    SET("ETPS_PREENT_NO = #{bwlHeadType.etps_preent_no}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getDcl_typecd())) {
                    SET("DCL_TYPECD = #{bwlHeadType.dcl_typecd}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getBwl_typecd())) {
                    SET("BWL_TYPECD = #{bwlHeadType.bwl_typecd}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getMaster_cuscd())) {
                    SET("MASTER_CUSCD = #{bwlHeadType.master_cuscd}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getBizop_etpsno())) {
                    SET("BIZOP_ETPSNO = #{bwlHeadType.bizop_etpsno}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getBizop_etps_nm())) {
                    SET("BIZOP_ETPS_NM = #{bwlHeadType.bizop_etps_nm}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getBizop_etps_sccd())) {
                    SET("BIZOP_ETPS_SCCD = #{bwlHeadType.bizop_etps_sccd}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getHouse_no())) {
                    SET("HOUSE_NO = #{bwlHeadType.house_no}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getHouse_nm())) {
                    SET("HOUSE_NM = #{bwlHeadType.house_nm}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getDcl_etpsno())) {
                    SET("DCL_ETPSNO = #{bwlHeadType.dcl_etpsno}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getDcl_etps_nm())) {
                    SET("DCL_ETPS_NM = #{bwlHeadType.dcl_etps_nm}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getDcl_etps_sccd())) {
                    SET("DCL_ETPS_SCCD = #{bwlHeadType.dcl_etps_sccd}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getDcl_etps_typecd())) {
                    SET("DCL_ETPS_TYPECD = #{bwlHeadType.dcl_etps_typecd}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getContact_er())) {
                    SET("CONTACT_ER = #{bwlHeadType.contact_er}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getContact_tele())) {
                    SET("CONTACT_TELE = #{bwlHeadType.contact_tele}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getHouse_typecd())) {
                    SET("HOUSE_TYPECD = #{bwlHeadType.house_typecd}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getHouse_area())) {
                    SET("HOUSE_AREA = #{bwlHeadType.house_area}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getHouse_volume())) {
                    SET("HOUSE_VOLUME = #{bwlHeadType.house_volume}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getHouse_address())) {
                    SET("HOUSE_ADDRESS = #{bwlHeadType.house_address}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getDcl_time())) {
                    SET("DCL_TIME = #{bwlHeadType.dcl_time}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getInput_date())) {
                    SET("input_date = #{bwlHeadType.input_date}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getTax_typecd())) {
                    SET("TAX_TYPECD = #{bwlHeadType.tax_typecd}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getPutrec_appr_time())) {
                    SET("PUTREC_APPR_TIME = #{bwlHeadType.putrec_appr_time}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getChg_appr_time())) {
                    SET("CHG_APPR_TIME = #{bwlHeadType.chg_appr_time}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getFinish_valid_date())) {
                    SET("FINISH_VALID_DATE = #{bwlHeadType.finish_valid_date}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getPause_chg_markcd())) {
                    SET("PAUSE_CHG_MARKCD = #{bwlHeadType.pause_chg_markcd}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getEmapv_stucd())) {
                    SET("EMAPV_STUCD = #{bwlHeadType.emapv_stucd}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getDcl_markcd())) {
                    SET("DCL_MARKCD = #{bwlHeadType.dcl_markcd}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getAppend_typecd())) {
                    SET("APPEND_TYPECD = #{bwlHeadType.append_typecd}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getRmk())) {
                    SET("RMK = #{bwlHeadType.rmk}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getOwner_system())) {
                    SET("OWNER_SYSTEM = #{bwlHeadType.owner_system}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getUsage_typecd())) {
                    SET("USAGE_TYPECD = #{bwlHeadType.usage_typecd}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getStatus())) {
                    SET("STATUS = #{bwlHeadType.status}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getReturn_status())) {
                    SET("RETURN_STATUS = #{bwlHeadType.return_status}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getReturn_date())) {
                    SET("RETURN_DATE = #{bwlHeadType.return_date}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getCrt_time())) {
                    SET("CRT_TIME = #{bwlHeadType.crt_time}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getCrt_user())) {
                    SET("CRT_USER = #{bwlHeadType.crt_user}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getUpd_time())) {
                    SET("UPD_TIME = #{bwlHeadType.upd_time}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getUpd_user())) {
                    SET("UPD_USER = #{bwlHeadType.upd_user}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getCrt_ent_id())) {
                    SET("CRT_ENT_ID = #{bwlHeadType.crt_ent_id}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getCrt_ent_name())) {
                    SET("CRT_ENT_NAME = #{bwlHeadType.crt_ent_name}");
                }

            }
        }.toString();
    }

    public String crtBooksInfo(@Param("bwlHeadType") BwlHeadType bwlHeadType) throws Exception {
        return new SQL() {
            {
                INSERT_INTO("T_BWL_HEAD_TYPE");
                if (!StringUtils.isEmpty(bwlHeadType.getId())) {
                    VALUES("ID", "#{bwlHeadType.id}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getBws_no())) {
                    VALUES("BWS_NO", "#{bwlHeadType.bws_no}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getChg_tms_cnt())) {
                    VALUES("CHG_TMS_CNT", "#{bwlHeadType.chg_tms_cnt}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getEtps_preent_no())) {
                    VALUES("ETPS_PREENT_NO", "#{bwlHeadType.etps_preent_no}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getDcl_typecd())) {
                    VALUES("DCL_TYPECD", "#{bwlHeadType.dcl_typecd}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getBwl_typecd())) {
                    VALUES("BWL_TYPECD", "#{bwlHeadType.bwl_typecd}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getMaster_cuscd())) {
                    VALUES("MASTER_CUSCD", "#{bwlHeadType.master_cuscd}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getBizop_etpsno())) {
                    VALUES("BIZOP_ETPSNO", "#{bwlHeadType.bizop_etpsno}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getBizop_etps_nm())) {
                    VALUES("BIZOP_ETPS_NM", "#{bwlHeadType.bizop_etps_nm}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getBizop_etps_sccd())) {
                    VALUES("BIZOP_ETPS_SCCD", "#{bwlHeadType.bizop_etps_sccd}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getHouse_no())) {
                    VALUES("HOUSE_NO", "#{bwlHeadType.house_no}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getHouse_nm())) {
                    VALUES("HOUSE_NM", "#{bwlHeadType.house_nm}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getDcl_etpsno())) {
                    VALUES("DCL_ETPSNO", "#{bwlHeadType.dcl_etpsno}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getDcl_etps_nm())) {
                    VALUES("DCL_ETPS_NM", "#{bwlHeadType.dcl_etps_nm}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getDcl_etps_sccd())) {
                    VALUES("DCL_ETPS_SCCD", "#{bwlHeadType.dcl_etps_sccd}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getDcl_etps_typecd())) {
                    VALUES("DCL_ETPS_TYPECD", "#{bwlHeadType.dcl_etps_typecd}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getContact_er())) {
                    VALUES("CONTACT_ER", "#{bwlHeadType.contact_er}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getContact_tele())) {
                    VALUES("CONTACT_TELE", "#{bwlHeadType.contact_tele}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getHouse_typecd())) {
                    VALUES("HOUSE_TYPECD", "#{bwlHeadType.house_typecd}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getHouse_area())) {
                    VALUES("HOUSE_AREA", "#{bwlHeadType.house_area}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getHouse_volume())) {
                    VALUES("HOUSE_VOLUME", "#{bwlHeadType.house_volume}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getHouse_address())) {
                    VALUES("HOUSE_ADDRESS", "#{bwlHeadType.house_address}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getDcl_time())) {
                    VALUES("DCL_TIME", "#{bwlHeadType.dcl_time}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getInput_date())) {
                    VALUES("INPUT_DATE", "#{bwlHeadType.input_date}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getTax_typecd())) {
                    VALUES("TAX_TYPECD", "#{bwlHeadType.tax_typecd}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getPutrec_appr_time())) {
                    VALUES("PUTREC_APPR_TIME", "#{bwlHeadType.putrec_appr_time}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getChg_appr_time())) {
                    VALUES("CHG_APPR_TIME", "#{bwlHeadType.chg_appr_time}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getFinish_valid_date())) {
                    VALUES("FINISH_VALID_DATE", "#{bwlHeadType.finish_valid_date}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getPause_chg_markcd())) {
                    VALUES("PAUSE_CHG_MARKCD", "#{bwlHeadType.pause_chg_markcd}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getEmapv_stucd())) {
                    VALUES("EMAPV_STUCD", "#{bwlHeadType.emapv_stucd}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getDcl_markcd())) {
                    VALUES("DCL_MARKCD", "#{bwlHeadType.dcl_markcd}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getAppend_typecd())) {
                    VALUES("APPEND_TYPECD", "#{bwlHeadType.append_typecd}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getRmk())) {
                    VALUES("RMK", "#{bwlHeadType.rmk}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getOwner_system())) {
                    VALUES("OWNER_SYSTEM", "#{bwlHeadType.owner_system}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getUsage_typecd())) {
                    VALUES("USAGE_TYPECD", "#{bwlHeadType.usage_typecd}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getStatus())) {
                    VALUES("STATUS", "#{bwlHeadType.status}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getReturn_status())) {
                    VALUES("RETURN_STATUS", "#{bwlHeadType.return_status}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getReturn_date())) {
                    VALUES("RETURN_DATE", "#{bwlHeadType.return_date}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getCrt_time())) {
                    VALUES("CRT_TIME", "#{bwlHeadType.crt_time}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getCrt_user())) {
                    VALUES("CRT_USER", "#{bwlHeadType.crt_user}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getUpd_time())) {
                    VALUES("UPD_TIME", "#{bwlHeadType.upd_time}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getUpd_user())) {
                    VALUES("UPD_USER", "#{bwlHeadType.upd_user}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getCrt_ent_id())) {
                    VALUES("CRT_ENT_ID", "#{bwlHeadType.crt_ent_id}");
                }
                if (!StringUtils.isEmpty(bwlHeadType.getCrt_ent_name())) {
                    VALUES("CRT_ENT_NAME", "#{bwlHeadType.crt_ent_name}");
                }

            }
        }.toString();
    }

}
