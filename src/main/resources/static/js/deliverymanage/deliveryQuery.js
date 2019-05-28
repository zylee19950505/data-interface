/**
 * Created lzy on 2019-01-16.
 * 入库明细单查询
 */
sw.page.modules["deliverymanage/deliveryQuery"] = sw.page.modules["deliverymanage/deliveryQuery"] || {

    // 入库明细单查询
    query: function () {
        // 获取查询表单参数
        var startFlightTimes = $("[name='startFlightTimes']").val();
        var endFlightTimes = $("[name='endFlightTimes']").val();
        var billNo = $("[name='billNo']").val();
        var returnStatus = $("[name='returnStatus']").val();

        // 拼接URL及参数
        var url = sw.serializeObjectToURL("api/deliveryManage/queryDeliveryQuery", {
            startFlightTimes: startFlightTimes,//导入时间
            endFlightTimes: endFlightTimes,//导入时间
            billNo: billNo,//提运单号
            returnStatus: returnStatus//业务状态
        });

        // 数据表
        sw.datatable("#query-deliveryQuery-table", {
            ajax: url,
            lengthMenu: [[50, 100, 1000], [50, 100, 1000]],
            searching: false,//开启本地搜索
            columns: [
                {
                    data: "bill_no", label: "提运单号"
                },
                // {
                //     label: "提运单号", render: function (data, type, row) {
                //     return '<a href="javascript:void(0)"  onclick="' + "javascript:sw.pageModule('deliverymanage/deliveryDeclare').seeDetailsssss('" + row.guid + "')" + '">' + row.bill_no + '</a>'
                // }
                // },
                {
                    label: "申报日期", render: function (data, type, row) {
                    if (!isEmpty(row.app_time)) {
                        return moment(row.app_time).format("YYYY-MM-DD HH:mm:ss");
                    }
                    return "";
                }
                },
                {data: "logistics_code", label: "物流企业编号"},
                {data: "logistics_name", label: "物流企业名称"},
                {data: "asscount", label: "运单数量"},
                {
                    label: "业务状态", render: function (data, type, row) {
                    var textColor = "";
                    var value = "";
                    switch (row.data_status) {
                        case "CBDS72":
                            textColor = "text-green";
                            value = "入库明细单申报成功";
                            break;
                    }

                    return "<span class='" + textColor + "'>" + value + "</span>";
                }
                },
                {
                    data: "return_status", label: "回执状态"
                },
                {
                    data: "return_time", label: "回执时间"
                },
                {
                    label: "回执备注", render: function (data, type, row) {
                    return '<div style="width:100px;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;" ' +
                        'title="' + row.return_info + '">' + row.return_info + '</div>';
                }
                }
            ]
        });
    },

    init: function () {
        $("[name='startFlightTimes']").val(moment(new Date()).subtract('days',7).format("YYYY-MM-DD"));
        $("[name='endFlightTimes']").val(moment(new Date()).format("YYYY-MM-DD"));
        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyy-mm-dd",
            autoclose: true
        });
        $("[ws-search]").unbind("click").click(this.query).click();
    }

};
