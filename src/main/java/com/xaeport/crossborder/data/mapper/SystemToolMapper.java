package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.UnitCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by baozhe on 2017-7-24.
 * 系统工具Mapper
 */
@Mapper
public interface SystemToolMapper {

    /**
     * 获取MessageId序列
     * @return
     */
    @Select("select Lpad(SEQ_MESSAGE_ID_SERIAL.NEXTVAL,11,'0') as SERIAL_NO from dual")
    String getMessageIdSequeue();

    @Select("select count(1) from T_CURRENCY c where c.curr_code = #{currCode} ")
    int countCurrencyByCode(@Param("currCode") String currCode);

    @Select("select count(1) from country_area c where c.ca_code = #{caCode} ")
    int countCountryByCode(@Param("caCode") String caCode);

    @Select("select curr_code from T_CURRENCY c ")
    List<String> findAllCurrencyCode();

    @Select("select ca_code from country_area c")
    List<String> findAllCountryCode();

    @Select("select unit_code from T_UNIT_CODE c ")
    List<String> findAllUnitCode();

    @Select("select unit_name,unit_code from T_UNIT_CODE ")
    List<UnitCode>queryUnitCode();

    @Select("select certificate_code from certificate_code c ")
    List<String> findAllCertificateCode();

}
