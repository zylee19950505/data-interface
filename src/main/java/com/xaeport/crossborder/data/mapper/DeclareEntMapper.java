//package com.xaeport.crossborder.data.mapper;
//
//import com.xaeport.crossborder.data.provider.DeclareEntSQLProvider;
//import org.apache.ibatis.annotations.*;
//
//import java.util.List;
//import java.util.Map;
//
//@Mapper
//public interface DeclareEntMapper {
//
//    @SelectProvider(type = DeclareEntSQLProvider.class, method = "querySendNameInfo")
//    List<SendReceiveName> querySendNameInfo(Map<String, String> map) throws Exception;
//
//    @SelectProvider(type = DeclareEntSQLProvider.class, method = "getSendName")
//    List<SendReceiveName> getSendName(Map<String, String> map) throws Exception;
//
//    @DeleteProvider(type = DeclareEntSQLProvider.class, method = "deleteSendNameInfo")
//    void deleteSendNameInfo(String sn_id) throws Exception;
//
//    @InsertProvider(type = DeclareEntSQLProvider.class, method = "createSendName")
//    void createSendName(@Param("sendReceiveName") SendReceiveName sendReceiveName) throws Exception;
//
//}
