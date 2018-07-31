package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.CEB412Message;
import com.xaeport.crossborder.data.entity.ImpPayment;
import com.xaeport.crossborder.data.entity.ImpRecPayment;
import com.xaeport.crossborder.data.provider.ReceiptSQLProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;

@Mapper
@Transactional
public interface ReceiptMapper {


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

    @InsertProvider(type = ReceiptSQLProvider.class, method = "createImpRecPayment")
    boolean createImpRecPayment(@Param("impRecPayment") ImpRecPayment impRecPayment) throws Exception;

//    @UpdateProvider(type = ReceiptSQLProvider.class, method = "updateImpPaymentStatus")
//    boolean updateImpPaymentStatus(@Param("impPayment") ImpPayment impPayment) throws Exception;

    @UpdateProvider(type = ReceiptSQLProvider.class, method = "updateImpPayment")
    boolean updateImpPayment(@Param("impPayment") ImpPayment impPayment) throws Exception;

//    @Select("select declare_status from entryhead where bill_no=#{bill_no} and ass_bill_no=#{ass_bill_no}")
//    String getEntryhead(@Param("bill_no") String bill_no, @Param("ass_bill_no") String ass_bill_no);
//
//    @Select("select max(to_number(op_time)) from declare_receipt  where   bill_no=#{bill_no}  and ass_bill_no=#{ass_bill_no}")
//    String getOpTime(@Param("bill_no") String bill_no, @Param("ass_bill_no") String ass_bill_no);

}
