package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.provider.ReceiptSQLProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;

@Mapper
@Transactional
public interface ReceiptMapper {

    @InsertProvider(type = ReceiptSQLProvider.class, method = "createImpRecPayment")
    boolean createImpRecPayment(@Param("impRecPayment") ImpRecPayment impRecPayment) throws Exception;

//    @UpdateProvider(type = ReceiptSQLProvider.class, method = "updateImpPaymentStatus")
//    boolean updateImpPaymentStatus(@Param("impPayment") ImpPayment impPayment) throws Exception;

    @UpdateProvider(type = ReceiptSQLProvider.class, method = "updateImpPayment")
    boolean updateImpPayment(@Param("impPayment") ImpPayment impPayment) throws Exception;

	@InsertProvider(type = ReceiptSQLProvider.class, method = "createImpRecOrder")
	void createImpRecOrder(@Param("impRecOrder") ImpRecOrder impRecOrder);

	@UpdateProvider(type = ReceiptSQLProvider.class,method = "updateImpOrder")
	void updateImpOrder(@Param("impOrderHead") ImpOrderHead impOrderHead);

//    @Select("select declare_status from entryhead where bill_no=#{bill_no} and ass_bill_no=#{ass_bill_no}")
//    String getEntryhead(@Param("bill_no") String bill_no, @Param("ass_bill_no") String ass_bill_no);
//
//    @Select("select max(to_number(op_time)) from declare_receipt  where   bill_no=#{bill_no}  and ass_bill_no=#{ass_bill_no}")
//    String getOpTime(@Param("bill_no") String bill_no, @Param("ass_bill_no") String ass_bill_no);

}
