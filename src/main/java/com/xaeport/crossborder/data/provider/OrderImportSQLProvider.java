package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.ImpOrderGoodsList;
import com.xaeport.crossborder.data.entity.ImpOrderHead;
import org.apache.ibatis.jdbc.SQL;

public class OrderImportSQLProvider {

    public String insertImpOrderHead(ImpOrderHead impOrderHead){
        return new SQL(){
            {
                INSERT_INTO("");

            }
        }.toString();
    }

    public String insertImpOrderGoodsList(ImpOrderGoodsList impOrderGoodsList){
        return new SQL(){
            {
                INSERT_INTO("");

            }
        }.toString();
    }

}
