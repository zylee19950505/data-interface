//package com.xaeport.crossborder.data.provider;
//
//import org.apache.ibatis.annotations.Param;
//import org.apache.ibatis.jdbc.SQL;
//import org.springframework.util.StringUtils;
//
//import java.util.Map;
//
//public class DeclareEntSQLProvider extends BaseSQLProvider{
//
//    public String querySendNameInfo(Map<String, String> map){
//        final String send_name = map.get("send_name");
//        final String ent_id = map.get("ent_id");
//        return new SQL(){
//            {
//                SELECT("SN_ID");
//                SELECT("ENTRY_TYPE");
//                SELECT("I_E_FLAG");
//                SELECT("SEND_NAME");
//                SELECT("SEND_NAME_EN");
//                SELECT("RECEIVE_NAME");
//                SELECT("RECEIVE_NAME_EN");
//                SELECT("ENT_ID");
//                SELECT("ENT_CUSTOMS_CODE");
//                SELECT("ENT_NAME");
//                SELECT("CREATE_TIME");
//                FROM("SEND_RECEIVE_NAME_INFO t");
//                if(!StringUtils.isEmpty(send_name)){
//                    WHERE("t.SEND_NAME like '%'||#{send_name}||'%'");
//                }
//                if(!StringUtils.isEmpty(ent_id)){
//                    WHERE("t.ENT_ID = #{ent_id}");
//                }
//                ORDER_BY("SEND_NAME");
//            }
//        }.toString();
//    }
//
//    public String getSendName(Map<String, String> map){
//        final String entry_type = map.get("entry_type");
//        final String i_e_flag = map.get("i_e_flag");
//        final String ent_id = map.get("ent_id");
//        return new SQL(){
//            {
//                SELECT("SN_ID");
//                SELECT("SEND_NAME");
//                SELECT("SEND_NAME_EN");
//                SELECT("ENT_ID");
//                FROM("SEND_RECEIVE_NAME_INFO t");
//                if(!StringUtils.isEmpty(entry_type)){
//                    WHERE("t.ENTRY_TYPE = #{entry_type}");
//                }
//                if(!StringUtils.isEmpty(i_e_flag)){
//                    WHERE("t.I_E_FLAG = #{i_e_flag}");
//                }
//                if(!StringUtils.isEmpty(ent_id)){
//                    WHERE("t.ENT_ID = #{ent_id}");
//                }
//                ORDER_BY("SEND_NAME");
//            }
//        }.toString();
//    }
//
//    public String deleteSendNameInfo(String sn_id){
//        return new SQL(){
//            {
//                DELETE_FROM("SEND_RECEIVE_NAME_INFO t");
//                WHERE("t.sn_id = #{sn_id}");
//            }
//        }.toString();
//    }
//
//    public String createSendName(@Param("sendReceiveName") SendReceiveName sendReceiveName){
//        return new SQL(){
//            {
//                INSERT_INTO("SEND_RECEIVE_NAME_INFO");
//                if(!StringUtils.isEmpty(sendReceiveName.getSn_id())){
//                    VALUES("SN_ID","#{sendReceiveName.sn_id}");
//                }
//                if(!StringUtils.isEmpty(sendReceiveName.getEntry_type())){
//                    VALUES("ENTRY_TYPE","#{sendReceiveName.entry_type}");
//                }
//                if(!StringUtils.isEmpty(sendReceiveName.getI_e_flag())){
//                    VALUES("I_E_FLAG","#{sendReceiveName.i_e_flag}");
//                }
//                if(!StringUtils.isEmpty(sendReceiveName.getSend_name())){
//                    VALUES("SEND_NAME","#{sendReceiveName.send_name}");
//                }
//                if(!StringUtils.isEmpty(sendReceiveName.getSend_name_en())){
//                    VALUES("SEND_NAME_EN","#{sendReceiveName.send_name_en}");
//                }
//                if(!StringUtils.isEmpty(sendReceiveName.getReceive_name())){
//                    VALUES("RECEIVE_NAME","#{sendReceiveName.receive_name}");
//                }
//                if(!StringUtils.isEmpty(sendReceiveName.getReceive_name_en())){
//                    VALUES("RECEIVE_NAME_EN","#{sendReceiveName.receive_name_en}");
//                }
//                if(!StringUtils.isEmpty(sendReceiveName.getEnt_id())){
//                    VALUES("ENT_ID","#{sendReceiveName.ent_id}");
//                }
//                if(!StringUtils.isEmpty(sendReceiveName.getEnt_customs_code())){
//                    VALUES("ENT_CUSTOMS_CODE","#{sendReceiveName.ent_customs_code}");
//                }
//                if(!StringUtils.isEmpty(sendReceiveName.getEnt_name())){
//                    VALUES("ENT_NAME","#{sendReceiveName.ent_name}");
//                }
//                if(!StringUtils.isEmpty(sendReceiveName.getCreate_time())){
//                    VALUES("CREATE_TIME","#{sendReceiveName.create_time}");
//                }
//            }
//        }.toString();
//    }
//
//}
