package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.BwlHeadType;
import com.xaeport.crossborder.data.provider.BooksManageSQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface BooksManageMapper {

    @SelectProvider(type = BooksManageSQLProvider.class, method = "queryAllBooksInfo")
    List<BwlHeadType> queryAllBooksInfo(Map<String, String> paramMap) throws Exception;

    @SelectProvider(type = BooksManageSQLProvider.class, method = "getBooksById")
    BwlHeadType getBooksById(String id);

    @InsertProvider(type = BooksManageSQLProvider.class, method = "crtBooksInfo")
    boolean crtBooksInfo(@Param("bwlHeadType") BwlHeadType bwlHeadType) throws Exception;

    @Select("SELECT count(1) FROM T_BWL_HEAD_TYPE t WHERE t.id = #{id}")
    Integer getBooksCount(String id);

    @UpdateProvider(type = BooksManageSQLProvider.class, method = "updateBooks")
    boolean updateBooks(@Param("bwlHeadType") BwlHeadType bwlHeadType) throws Exception;

}
