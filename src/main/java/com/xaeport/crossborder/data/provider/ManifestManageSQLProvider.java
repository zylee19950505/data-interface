package com.xaeport.crossborder.data.provider;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class ManifestManageSQLProvider extends BaseSQLProvider {


    /*
    * 核放单管理查询
    * */
    public String queryManifestManageList(Map<String, String> paramMap) throws Exception {
        final String manifestNo = paramMap.get("manifestNo");
        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String endFlightTimes = paramMap.get("endFlightTimes");
        final String start = paramMap.get("start");
        final String length = paramMap.get("length");
        final String entId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");

        return new SQL() {
            {
                SELECT(" * from ( select w.*, ROWNUM AS rn from ( " +
                        " select " +
                        " t.manifest_no," +
                        " t.pack_no," +
                        " t.goods_wt," +
                        " t.fact_weight," +
                        " t.car_wt," +
                        " t.sum_goods_value," +
                        " t.car_no," +
                        " t.ic_code," +
                        " t.data_status " +
                        " from T_MANIFEST_HEAD t ");
                WHERE("1=1");
                /*if(!roleId.equals("admin")){
					WHERE("t.ent_id = #{entId}");
				}*/
                if (!StringUtils.isEmpty(manifestNo)) {
                    WHERE("t.MANIFEST_NO = #{manifestNo}");
                }
//				WHERE("t.DATA_STATUS = #{dataStatus}");
                if (!StringUtils.isEmpty(startFlightTimes)) {
                    WHERE(" t.CREATE_TIME >= to_date(#{startFlightTimes}||'00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(endFlightTimes)) {
                    WHERE(" t.CREATE_TIME <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!"-1".equals(length)) {
                    ORDER_BY("t.CREATE_TIME desc ) w  )   WHERE rn >= #{start} AND rn < #{start} + #{length} ");
                } else {
                    ORDER_BY("t.CREATE_TIME desc ) w  )   WHERE rn >= #{start}");
                }

            }
        }.toString();
    }


    /*核放单查询条数
    * queryOrderHeadListCount
    * */
    public String queryManifestManageCount(Map<String, String> paramMap) {
        final String manifestNo = paramMap.get("manifestNo");
        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String endFlightTimes = paramMap.get("endFlightTimes");
        final String start = paramMap.get("start");
        final String length = paramMap.get("length");
        final String entId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");

        return new SQL() {
            {
                SELECT(" count(*) count from T_MANIFEST_HEAD t ");
                WHERE("1=1");
//                if (!roleId.equals("admin")) {
//                    WHERE("t.ent_id = #{entId}");
//                }
                if (!StringUtils.isEmpty(manifestNo)) {
                    WHERE("t.MANIFEST_NO = #{manifestNo}");
                }
//                WHERE("t.DATA_STATUS = #{dataStatus}");
                if (!StringUtils.isEmpty(startFlightTimes)) {
                    WHERE(" t.CREATE_TIME >= to_date(#{startFlightTimes}||'00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(endFlightTimes)) {
                    WHERE(" t.CREATE_TIME <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
                }
            }
        }.toString();
    }

    /*
    * 核放单申报
    * */
    public String manifestDeclare(Map<String, String> paramMap) {
        final String manifestNo = paramMap.get("manifestNo");
        return new SQL() {
            {
                UPDATE("T_MANIFEST_HEAD t");
                SET("t.DATA_STATUS = #{dataStatus}");
                SET("t.APP_DATE = sysdate");
                WHERE("t.MANIFEST_NO = #{manifestNo}");
            }
        }.toString();
    }

    /*
    * 查找核放单申报中的数据
    * */
    public String findManifestManage(Map<String, String> paramMap) {
        return new SQL() {
            {
                SELECT("t.*");
                FROM("T_MANIFEST_HEAD t");
                WHERE("t.DATA_STATUS = #{dataStatus}");
            }
        }.toString();
    }

    /*
    * 把核放单数据改为核放单正在发往海关
    * */
    public String updateManifestManage(@Param("manifestNo") String manifestNo) {
        return new SQL() {
            {
                UPDATE("T_MANIFEST_HEAD t");
                SET("t.data_status = 'CBDS81'");
                WHERE("t.MANIFEST_NO = #{manifestNo}");
            }
        }.toString();
    }


    //根据核放单号更新预定数据状态
    public String updateCheckGoodsInfo(@Param("manifest_no") String manifest_no) {
        return new SQL() {
            {
                UPDATE("T_CHECK_GOODS_INFO t");
                SET("t.IS_MANIFEST = 'N'");
                SET("t.UPD_TM = sysdate");
                WHERE("t.MANIFEST_NO = #{manifest_no}");
                WHERE("t.IS_MANIFEST = 'Y'");
            }
        }.toString();
    }

    //根据核放单号更新预定数据状态
    public String queryCheckGoodsInfo(@Param("manifest_no") String manifest_no) {
        return new SQL() {
            {
                SELECT("t.*");
                FROM("T_CHECK_GOODS_INFO t");
                WHERE("t.MANIFEST_NO = #{manifest_no}");
                WHERE("t.IS_MANIFEST = 'Y'");
            }
        }.toString();
    }

    //根据核放单号删除核放单数据
    public String manifestDelete(@Param("manifest_no") String manifest_no) {
        return new SQL() {
            {
                DELETE_FROM("T_MANIFEST_HEAD t");
                WHERE("t.MANIFEST_NO = #{manifest_no}");
            }
        }.toString();
    }

    //查询核放单表头打印数据
    public String queryManifestHead(Map<String, String> paramMap) throws Exception {
        final String manifest_no = paramMap.get("manifest_no");

        return new SQL() {
            {
                SELECT("*");
                FROM("T_MANIFEST_HEAD t");
                if (!StringUtils.isEmpty(manifest_no)) {
                    WHERE("t.MANIFEST_NO = #{manifest_no}");
                }
            }
        }.toString();
    }

    //查询核放单表头打印数据
    public String queryCheckGoodsInfoList(Map<String, String> paramMap) throws Exception {
        final String manifest_no = paramMap.get("manifest_no");

        return new SQL() {
            {
                SELECT("*");
                FROM("T_CHECK_GOODS_INFO t ");
                WHERE("t.IS_MANIFEST = 'Y'");
                WHERE("t.STATUS = '26'");
                if (!StringUtils.isEmpty(manifest_no)) {
                    WHERE("t.MANIFEST_NO = #{manifest_no}");
                }
            }
        }.toString();
    }


}
