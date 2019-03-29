//package com.xaeport.crossborder.service.parametermanage;
//
//import com.xaeport.crossborder.data.mapper.DeclareEntMapper;
//import org.apache.ibatis.annotations.Param;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class DeclareEntService {
//
//    @Autowired
//    DeclareEntMapper sendNameMapper;
//
//    public List<SendReceiveName> querySendNameInfo(Map<String, String> map) throws Exception {
//        return this.sendNameMapper.querySendNameInfo(map);
//    }
//
//    public List<SendReceiveName> getSendName(Map<String, String> map) throws Exception {
//        return this.sendNameMapper.getSendName(map);
//    }
//
//    public void deleteSendNameInfo(String sn_id) throws Exception {
//        this.sendNameMapper.deleteSendNameInfo(sn_id);
//    }
//
//    public void createSendName(@Param("sendReceiveName") SendReceiveName sendReceiveName) throws Exception {
//        this.sendNameMapper.createSendName(sendReceiveName);
//    }
//
//
//}
