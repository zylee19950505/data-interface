package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.BondInvtDt;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class EnterInventorySQLProvider {

    //预先给数据表体里插入数据
    public String insertEnterInventory(@Param("bondInvtDt") BondInvtDt bondInvtDt)throws Exception{
        return new SQL(){
            {
                INSERT_INTO("T_BOND_INVT_DT t");
                VALUES("id","#{bondInvtDt.id}");
                if(!StringUtils.isEmpty(bondInvtDt.getBond_invt_no())){
                    VALUES("BOND_INVT_NO","#{bondInvtDt.bond_invt_no}");
                }
                if(!StringUtils.isEmpty(bondInvtDt.getGds_seqno())){
                    VALUES("gds_seqno","#{bondInvtDt.gds_seqno}");
                }
                if(!StringUtils.isEmpty(bondInvtDt.getPutrec_seqno())){
                    VALUES("putrec_seqno","#{bondInvtDt.putrec_seqno}");
                }
                if(!StringUtils.isEmpty(bondInvtDt.getGds_mtno())){
                    VALUES("gds_mtno","#{bondInvtDt.gds_mtno}");
                }
                if(!StringUtils.isEmpty(bondInvtDt.getGdecd())){
                    VALUES("gdecd","#{bondInvtDt.gdecd}");
                }
                if(!StringUtils.isEmpty(bondInvtDt.getGds_nm())){
                    VALUES("gds_nm","#{bondInvtDt.gds_nm}");
                }
                if(!StringUtils.isEmpty(bondInvtDt.getGds_spcf_model_desc())){
                    VALUES("gds_spcf_model_desc","#{bondInvtDt.gds_spcf_model_desc}");
                }
                if(!StringUtils.isEmpty(bondInvtDt.getDcl_unitcd())){
                    VALUES("dcl_unitcd","#{bondInvtDt.dcl_unitcd}");
                }
                if(!StringUtils.isEmpty(bondInvtDt.getLawf_unitcd())){
                    VALUES("lawf_unitcd","#{bondInvtDt.lawf_unitcd}");
                }

                if(!StringUtils.isEmpty(bondInvtDt.getSecd_lawf_unitcd())){
                    VALUES("secd_lawf_unitcd","#{bondInvtDt.secd_lawf_unitcd}");
                }
                if(!StringUtils.isEmpty(bondInvtDt.getNatcd())){
                    VALUES("natcd","#{bondInvtDt.natcd}");
                }
                if(!StringUtils.isEmpty(bondInvtDt.getDcl_total_amt())){
                    VALUES("dcl_total_amt","#{bondInvtDt.dcl_total_amt}");
                }
                if(!StringUtils.isEmpty(bondInvtDt.getUsd_stat_total_amt())){
                    VALUES("usd_stat_total_amt","#{bondInvtDt.usd_stat_total_amt}");
                }
                if(!StringUtils.isEmpty(bondInvtDt.getLawf_qty())){
                    VALUES("lawf_qty","#{bondInvtDt.lawf_qty}");
                }
                if(!StringUtils.isEmpty(bondInvtDt.getSecd_lawf_qty())){
                    VALUES("secd_lawf_qty","#{bondInvtDt.secd_lawf_qty}");
                }
                if(!StringUtils.isEmpty(bondInvtDt.getDcl_qty())){
                    VALUES("dcl_qty","#{bondInvtDt.dcl_qty}");
                }
                if(!StringUtils.isEmpty(bondInvtDt.getRmk())){
                    VALUES("rmk","#{bondInvtDt.rmk}");
                }
            }
        }.toString();
    }
}
