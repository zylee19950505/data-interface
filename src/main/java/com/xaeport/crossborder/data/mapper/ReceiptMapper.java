package com.xaeport.crossborder.data.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;

@Mapper
@Transactional
public interface ReceiptMapper {

//    @Insert("insert into receipt_envelop(re_id,message_id,file_name,message_type,sender_id,receiver_id,send_time,create_time,version,refile_name)" +
//            " values(#{re_id},#{message_id},#{file_name},#{message_type},#{sender_id},#{receiver_id},#{send_time},#{create_time},#{version},#{refile_name})")
//    boolean createReceiptEnvelop(EnvelopInfo envelopInfo) throws Exception;
//
//    @Insert("insert into tax_receipt_record(tr_id,re_id,bill_no,ass_bill_no,i_e_flag,entry_id,op_time,real_duty,real_tax,real_reg,real_anti,real_rsv1,real_rsv2,real_ncad,refile_name)" +
//            " values(#{tr_id},#{re_id},#{bill_no},#{ass_bill_no},#{i_e_flag},#{entry_id},#{op_time},#{real_duty},#{real_tax},#{real_reg},#{real_anti},#{real_rsv1},#{real_rsv2},#{real_ncad},#{refile_name})")
//    boolean createTaxReceiptRecord(TaxReceiptRecord taxReceiptRecord) throws Exception;
//
//    @Update("update entryhead set  entry_id=#{entryHead.entry_id},real_duty=#{entryHead.real_duty},real_tax=#{entryHead.real_tax},real_reg=#{entryHead.real_reg},real_anti=#{entryHead.real_anti},real_rsv1=#{entryHead.real_rsv1}," +
//            " real_rsv2=#{entryHead.real_rsv2}, real_ncad=#{entryHead.real_ncad}  where bill_no=#{entryHead.bill_no}  and ass_bill_no =#{entryHead.ass_bill_no}")
//    boolean updateEntryHead(@Param("entryHead") EntryHead entryHead) throws Exception;
//
//    @Insert("insert into declare_receipt(dr_id,re_id,pre_entry_id,optype,bill_no,ass_bill_no,i_e_flag,entry_id,op_time,op_result,notes,refile_name) " +
//            " values(#{dr_id},#{re_id},#{pre_entry_id},#{optype},#{bill_no},#{ass_bill_no},#{i_e_flag},#{entry_id},#{op_time},#{op_result},#{notes},#{refile_name})")
//    boolean createDeclareReceipt(DeclareReceipt declareReceipt) throws Exception;
//
//    @Update("update entryhead set entry_id=#{entryHead.entry_id},declare_status=#{entryHead.declare_status},op_status=#{entryHead.op_status},update_time=#{entryHead.update_time},declare_result=#{entryHead.declare_result}" +
//            " where bill_no=#{entryHead.bill_no}  and ass_bill_no =#{entryHead.ass_bill_no}")
//    boolean updateDeclareStatus(@Param("entryHead") EntryHead entryHead) throws Exception;
//
//    @Update("update entryhead set entry_id=#{entryHead.entry_id},declare_status=#{entryHead.declare_status},update_time=#{entryHead.update_time},declare_result=#{entryHead.declare_result}" +
//            " where bill_no=#{entryHead.bill_no}  and ass_bill_no =#{entryHead.ass_bill_no}")
//    boolean updateDeclare(@Param("entryHead") EntryHead entryHead) throws Exception;
//
//    @Update("update entryhead set entry_id=#{entryHead.entry_id},declare_status=#{entryHead.declare_status},op_status=#{entryHead.op_status},is_check=#{entryHead.is_check},update_time=#{entryHead.update_time},declare_result=#{entryHead.declare_result}" +
//            " where bill_no=#{entryHead.bill_no}  and ass_bill_no =#{entryHead.ass_bill_no}")
//    boolean updateCheckDeclare(@Param("entryHead") EntryHead entryHead) throws Exception;
//
//    @Update("update entryhead set entry_id=#{entryHead.entry_id},declare_status=#{entryHead.declare_status},op_status=#{entryHead.op_status},is_clearance=#{entryHead.is_clearance},update_time=#{entryHead.update_time},declare_result=#{entryHead.declare_result}" +
//            " where bill_no=#{entryHead.bill_no}  and ass_bill_no =#{entryHead.ass_bill_no}")
//    boolean updateClearanceDeclare(@Param("entryHead") EntryHead entryHead) throws Exception;
//
//    @Insert("insert into status_record(sr_id,status_code,belong,odd_no,create_time,notes) values(#{statusRecord.sr_id},#{statusRecord.status_code},#{statusRecord.belong},#{statusRecord.odd_no},#{statusRecord.create_time},#{statusRecord.notes})")
//    boolean createStatusRecord(@Param("statusRecord") StatusRecord statusRecord) throws Exception;
//
//    @Insert("insert into manifest_head_receipt (mhr_id,re_id,bill_no,voyage_no,entry_date,rtn_flag,notes,refile_name) " +
//            "values(#{manifestHeadReceipt.mhr_id},#{manifestHeadReceipt.re_id},#{manifestHeadReceipt.bill_no},#{manifestHeadReceipt.voyage_no}," +
//            "#{manifestHeadReceipt.entry_date},#{manifestHeadReceipt.rtn_flag},#{manifestHeadReceipt.notes},#{manifestHeadReceipt.refile_name})")
//    boolean createManifestHeadReceipt(@Param("manifestHeadReceipt") ManifestHeadReceipt manifestHeadReceipt) throws Exception;
//
//    @Insert("insert into manifest_list_receipt(mlr_id,mhr_id,bill_no,ass_bill_no,entry_date,rtn_flag,notes,refile_name) values" +
//            " (#{manifestListReceipt.mlr_id},#{manifestListReceipt.mhr_id},#{manifestListReceipt.bill_no},#{manifestListReceipt.ass_bill_no}," +
//            " #{manifestListReceipt.entry_date},#{manifestListReceipt.rtn_flag},#{manifestListReceipt.notes},#{manifestListReceipt.refile_name})")
//    boolean createManifestListReceipt(@Param("manifestListReceipt") ManifestListReceipt manifestListReceipt) throws Exception;
//
//    @Update("update entryhead set manifest_status=#{entryHead.manifest_status},op_status=#{entryHead.op_status}，declare_result=#{entryHead.declare_result}" +
//            " where bill_no=#{entryHead.bill_no}  and op_status ='SWOP21'")
//    boolean updateManifestStatus(@Param("entryHead") EntryHead entryHead) throws Exception;
//
//
//    @Update("update entryhead set manifest_status=#{entryHead.manifest_status}" +
//            " where bill_no=#{entryHead.bill_no}  and op_status not in ('SWOP1','SWOP2')")
//    boolean updateManifest(@Param("entryHead") EntryHead entryHead) throws Exception;

    @Select("select declare_status from entryhead where bill_no=#{bill_no} and ass_bill_no=#{ass_bill_no}")
    String getEntryhead(@Param("bill_no") String bill_no, @Param("ass_bill_no") String ass_bill_no);

    @Select("select max(to_number(op_time)) from declare_receipt  where   bill_no=#{bill_no}  and ass_bill_no=#{ass_bill_no}")
    String getOpTime(@Param("bill_no") String bill_no, @Param("ass_bill_no") String ass_bill_no);

}
