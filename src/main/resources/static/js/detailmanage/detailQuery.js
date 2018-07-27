/**
 * 预览打印
 * Created by Administrator on 2017/7/20.
 */
sw.page.modules["detailmanage/detailQuery"] = sw.page.modules["detailmanage/detailQuery"] || {
        init: function () {
            $("[name='startFlightTimes']").val(moment(new Date()).date(1).format("YYYYMMDD"));
            $("[name='endFlightTimes']").val(moment(new Date()).format("YYYYMMDD"));
            $(".input-daterange").datepicker({
                language: "zh-CN",
                todayHighlight: true,
                format: "yyyymmdd",
                autoclose: true
            });
            $("[ws-search]").unbind("click").click(this.query);
            $("[ws-download]").unbind("click").click(this.billDownLoad);
            $("[ws-back]").unbind("click").click(this.back);
            $(".btn[ws-search]").click();
        },

        back: function () {
            $("#bill").show();
            $("#preview").hide();
        },

        billDownLoad: function () {
            sw.ajax("api/bill", "GET", {
                ieFlag: sw.ie,
                entryType: sw.type,
                startFlightTimes: $("[name='startFlightTimes']").val(),
                endFlightTimes: $("[name='endFlightTimes']").val(),
                billNo: $("[name='billNo']").val(),
                flag: "1"
            }, function (rsp) {
                if (rsp.status == 200) {
                    var fileName = rsp.data;
                    window.location.href = "/api/downloadFile?fileName=" + fileName;
                }
            });

        },

    query: function () {
        // 获取查询表单参数
        var startFlightTimes = $("[name='startFlightTimes']").val();
        var endFlightTimes = $("[name='endFlightTimes']").val();
        var orderNo = $("[name='orderNo']").val();

        // 拼接URL及参数
        var url = sw.serializeObjectToURL("api/detailManage/queryDetailQuery", {
            startFlightTimes: startFlightTimes,//申报开始时间
            endFlightTimes: endFlightTimes,//申报结束时间
            orderNo: orderNo
        });

        // 数据表
        sw.datatable("#query-detailQuery-table", {
            ordering: false,
            bSort: false, //排序功能
            serverSide: true,////服务器端获取数据
            pagingType: 'simple_numbers',
            ajax: function (data, callback, setting) {
                $.ajax({
                    type: 'GET',
                    url: sw.resolve(url),
                    data: data,
                    cache: false,
                    dataType: "json",
                    beforeSend: function () {
                        $("tbody").html('<tr class="odd"><td valign="top" colspan="13" class="dataTables_empty">载入中...</td></tr>');
                    },
                    success: function (res) {
                        var returnData = {};
                        returnData.data = res.data.data;
                        returnData.recordsFiltered = res.data.recordsFiltered;
                        returnData.draw = res.data.draw;
                        returnData.recordsTotal = res.data.recordsTotal;
                        returnData.start = data.start;
                        returnData.length = data.length;
                        callback(returnData);
                    },
                    error: function (xhr, status, error) {
                        sw.showErrorMessage(xhr, status, error);
                    }
                });
            },
            lengthMenu: [[10, 20, 50, -1], [10, 20, 50, "所有"]],
            searching: false,//开启本地搜索
            columns: [
                {data: "order_no", label: "订单编号"},//订单编号要点击查看订单详情
                {data: "logistics_code", label: "物流运单编号"},
                {data: "ebc_name", label: "电商企业名称"},
                {data: "ebc_name", label: "支付企业名称"},
                {data: "logistics_name", label: "物流企业名称"},
                {data: "g_name", label: "商品名称"},
                {
                    label: "业务状态", render: function (data, type, row) {
                    switch (row.data_status) {
                        case "CBDS1"://待申报
                            textColor="text-yellow";
                            row.data_status="待申报";
                            break;
                        case "CBDS6":
                            textColor = "text-yellow";
                            row.data_status = "订单待申报";
                            break;
                        case "CBDS60":
                            textColor = "text-green";
                            row.data_status = "订单申报中";
                            break;
                        case "CBDS61":
                            textColor = "text-green";
                            row.data_status = "订单已申报";
                            break;
                        case "CBDS63":
                            textColor = "text-red";
                            row.data_status = "订单重报";
                            break;
                    }

                    return "<span class='" + textColor + "'>" + row.data_status + "</span>";
                }
                },
                {data: "return_status", label: "入库结果"},//入库结果需要确认字段
                {
                    label: "申报日期", render: function (data, type, row) {
                    if(!isEmpty(row.app_Time)){
                        return moment(row.app_Time).format("YYYY-MM-DD HH:mm:ss");
                    }
                    return "";
                }
                }
            ]
        });
    },

        download: function (flightNo, flightTimes, billNo, statusCode, flag) {
            sw.ajax("api/downloadBill", "GET", {
                ieFlag: sw.ie,
                entryType: sw.type,
                flightNo: flightNo,
                flightTimes: flightTimes,
                billNo: billNo,
                statusCode: statusCode,
                flag: flag
            }, function (rsp) {
                if (rsp.status == 200) {
                    var fileName = rsp.data;
                    window.location.href = "/api/downloadFile?fileName=" + fileName;
                }
            });
        },

        seeAssBillNoDetail: function (flightNo, flightTimes, billNo, statusCode, flag) {
            $("#bill").hide();
            $("#preview").show();
            $("#previewId tr:not(:first)").remove();
            var parameter = {
                entryType: sw.type,
                ieFlag: sw.ie,
                flightNo: flightNo,
                flightTimes: flightTimes,
                billNo: billNo,
                statusCode: statusCode,
                flag: flag
            };
            sw.ajax("api/bill/preview", "GET", parameter, function (rsp) {
                if (rsp.status == 200) {
                    var d = rsp.data;
                    if (d !== null) {
                        sw.pageModule('express/import_b/bill_inquiry').setBillPreview(d);
                    }
                }
            });


            $("[ws-print]").unbind("click").attr("param-data", JSON.stringify(parameter)).click(this.printBill);

        },


















    };
