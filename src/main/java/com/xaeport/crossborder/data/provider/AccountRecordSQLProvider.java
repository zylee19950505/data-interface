package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.BwlHeadType;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

public class AccountRecordSQLProvider extends BaseSQLProvider{

    public String crtAccountRecord(
            BwlHeadType bwlHeadType
    ) {
        return new SQL(){
            {
                INSERT_INTO("T_BWL_HEAD_TYPE");
                if(StringUtils.isEmpty(bwlHeadType.getId())){
                    VALUES("ID","#{id}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("CHG_TMS_CNT","#{chg_tms_cnt}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }
                if(StringUtils.isEmpty(bwlHeadType.getBws_no())){
                    VALUES("BWS_NO","#{bws_no}");
                }

            }
        }.toString();
    }

}
