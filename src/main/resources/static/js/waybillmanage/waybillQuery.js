/**
 * 运单查询
 * Created by Administrator on 2017/7/20.
 */
sw.page.modules["waybillmanage/waybillQuery"] = sw.page.modules["waybillmanage/waybillQuery"] || {
    init: function () {
        $("[name='startFlightTimes']").val(moment(new Date()).date(1).format("YYYY-MM-DD"));
        $("[name='endFlightTimes']").val(moment(new Date()).format("YYYY-MM-DD"));
        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyy-mm-dd",
            autoclose: true
        });
        $("[ws-search]").unbind("click").click(this.query);
        // $("[ws-download]").unbind("click").click(this.download);
        $(".btn[ws-search]").click();
    },

    query: function () {
        // 获取查询表单参数
        var startFlightTimes = $("[name='startFlightTimes']").val();
        var endFlightTimes = $("[name='endFlightTimes']").val();
        var billNo = $("[name='billNo']").val();
        var orderNo = $("[name='orderNo']").val();
        var logisticsNo = $("[name='logisticsNo']").val();
        var logisticsStatus = $("[name='logisticsStatus']").val();//业务状态

        // 拼接URL及参数
        var url = sw.serializeObjectToURL("api/waybillManage/queryWaybillQuery", {
            startFlightTimes: startFlightTimes,
            endFlightTimes: endFlightTimes,
            billNo: billNo,
            orderNo: orderNo,//订单编号
            logisticsNo: logisticsNo,
            logisticsStatus: logisticsStatus
        });

        sw.datatable("#query-waybillQuery-table", {
            sLoadingRecords: true,
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
            lengthMenu: [[50, 100, 1000, -1], [50, 100, 1000, "所有"]],
            searching: false,//开启本地搜索
            columns: [
                {data: "bill_no", label: "提运单号"},
                {
                    label: "物流运单编号", render: function (data, type, row) {
                    //a链接跳转到querypaymentbyid方法。并赋值参数。 cursor:pointer鼠标移动上去变手掌样式
                    var result = '<a style="cursor:pointer" title="查看" ' +
                        'onclick="' + "javascript:sw.pageModule('waybillmanage/waybillQuery').queryWaybillbyid('" + row.guid + "','" + row.logistics_no + "')" + '">' + row.logistics_no + '</a>';
                    return result;
                }
                },
                {data: "order_no", label: "订单编号"},
                {data: "consingee", label: "收货人姓名"},
                {
                    label: "申报日期", render: function (data, type, row) {
                    if (!isEmpty(row.app_time)) {
                        return moment(row.app_time).format("YYYY-MM-DD HH:mm:ss");
                    }
                    return "";
                }
                },
                {
                    label: "物流签收状态", render: function (data, type, row) {
                    if ("S" == row.logistics_status) {
                        return "已签收"
                    }
                    return "未签收"
                }
                },
                {
                    label: "物流状态时间", render: function (data, type, row) {
                    if (!isEmpty(row.logistics_time)) {
                        return moment(row.logistics_time).format("YYYY-MM-DD HH:mm:ss");
                    } else if (!isEmpty(row.logistics_time_char)) {
                        var data = row.logistics_time_char;
                        var logistics_time = data.substr(0, 4) + "-" + data.substr(4, 2) + "-" + data.substr(6, 2) + "  " + data.substr(8, 2) + ":" + data.substr(10, 2) + ":" + data.substr(12, 2);
                        return logistics_time;
                    }
                    return "";
                }
                },
                {
                    label: "回执状态", render: function (data, type, row) {
                    var value = "";
                    if (!isEmpty(row.return_status_name)) {
                        value = row.return_status_name
                    } else {
                        value = row.return_status;
                    }

                    return '<a style="cursor:pointer" title="运单回执详情信息" ' +
                        'onclick="' + "javascript:sw.pageModule('waybillmanage/waybillQuery').returnDetails('" + row.guid + "','" + row.logistics_no + "')" + '">' + value + '</a>';
                }
                }
            ]
        });

    },

    //打开一个页面，并且用路径传递参数
    queryWaybillbyid: function (guid, logistics_no) {
        var url = "waybillmanage/seeWaybillDetail?type=YDCX&isEdit=true&guid=" + guid + "&logistics_no=" + logistics_no;
        sw.modelPopup(url, "运单详情信息", false, 900, 400);
    },

    returnDetails: function (guid, logistics_no) {
        var url = "waybillmanage/returnDetail?guid=" + guid + "&logistics_no=" + logistics_no;
        sw.modelPopup(url, "回执备注详情", false, 900, 350);
    },

    download: function () {

        sw.alert("运单数据下载啦！！！","提示","","modal-info");

        // var oTable = $('#query-waybillQuery-table').dataTable();
        // var oSettings = oTable.fnSettings();
        // var paramJson = {
        //     billNo: $("[name='billNo']").val(),
        //     startStr: oSettings._iDisplayStart,
        //     length: oSettings._iDisplayLength
        // };
        // sw.ajax("api/waybillManage/load", "GET", paramJson, function (rsp) {
        //     if (rsp.status == 200) {
        //         var fileName = rsp.data;
        //         window.location.href = "/api/waybillManage/downloadFile?fileName=" + fileName;
        //     }
        // })
    }

};
