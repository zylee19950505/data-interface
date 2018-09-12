//清单查询
sw.page.modules["detailmanage/detailQuery"] = sw.page.modules["detailmanage/detailQuery"] || {

    query: function () {
        // 获取查询表单参数
        var startFlightTimes = $("[name='startFlightTimes']").val();
        var endFlightTimes = $("[name='endFlightTimes']").val();
        var billNo = $("[name='billNo']").val();
        var orderNo = $("[name='orderNo']").val();
        var logisticsNo = $("[name='logisticsNo']").val();
        var returnStatus = $("[name='returnStatus']").val();

        // 拼接URL及参数
        var url = sw.serializeObjectToURL("api/detailManage/queryDetailQuery", {
            startFlightTimes: startFlightTimes,//申报开始时间
            endFlightTimes: endFlightTimes,//申报结束时间
            billNo: billNo,//提运单号
            orderNo: orderNo,//订单编号
            logisticsNo: logisticsNo,//物流运单编号
            returnStatus: returnStatus//回执状态
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
            lengthMenu: [[50, 100, 1000, -1], [50, 100, 1000, "所有"]],
            searching: false,//开启本地搜索
            columns: [
                {data: "bill_no", label: "提运单号"},//订单编号要点击查看订单详情
                {
                    label: "订单编号", render: function (data, type, row) {
                    return '<a href="javascript:void(0)"  onclick="' + "javascript:sw.pageModule('detailmanage/detailQuery').seeInventoryDetail('" + row.guid + "','" + row.order_no + "')" + '">' + row.order_no + '</a>'
                }
                },
                {data: "logistics_no", label: "物流运单编号"},
                {data: "invt_no", label: "海关清单编号"},
                {
                    label: "电商平台名称", render: function (data, type, row) {
                    return '<div style="width:100px;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;" ' +
                        'title="' + row.ebp_name + '">' + row.ebp_name + '</div>';
                }
                },
                {
                    label: "电商企业名称", render: function (data, type, row) {
                    return '<div style="width:100px;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;" ' +
                        'title="' + row.ebc_name + '">' + row.ebc_name + '</div>';
                }
                },
                {
                    label: "物流企业名称", render: function (data, type, row) {
                    return '<div style="width:100px;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;" ' +
                        'title="' + row.logistics_name + '">' + row.logistics_name + '</div>';
                }
                },
                {
                    label: "申报日期", render: function (data, type, row) {
                    if (!isEmpty(row.app_time)) {
                        return moment(row.app_time).format("YYYY-MM-DD HH:mm:ss");
                    }
                    return "";
                }
                },
                {
                    label: "业务状态", render: function (data, type, row) {
                    var textColor = "";
                    var value = "";
                    switch (row.data_status) {
                        case "CBDS62"://待申报
                            textColor = "text-green";
                            value = "清单申报成功";
                            break;
                        case "CBDS63":
                            textColor = "text-red";
                            value = "清单重报";
                            break;
                        case "CBDS64":
                            textColor = "text-red";
                            value = "清单申报失败";
                            break;
                    }
                    // return "<span class='" + textColor + "'>" + value + "</span>";
                    return '<a href="javascript:void(0)"  onclick="' + "javascript:sw.pageModule('detailmanage/detailQuery').seeInventoryRec('" + row.guid + "','" + row.data_status + "')" + '">' + value + '</a>'
                }
                },
            ]
        });
    },

    init: function () {
        $("[name='startFlightTimes']").val(moment(new Date()).date(1).format("YYYYMMDD"));
        $("[name='endFlightTimes']").val(moment(new Date()).format("YYYYMMDD"));
        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyymmdd",
            autoclose: true
        });
        $("[ws-search]").unbind("click").click(this.query).click();
        $(".btn[ws-search]").click();
    },

    seeInventoryDetail: function (guid, order_no) {
        console.log(guid, order_no)
        var url = "detailmanage/seeInventoryDetail?type=QDCX&isEdit=true&guid=" + guid + "&orderNo=" + order_no;
        sw.modelPopup(url, "查看清单详情", false, 1000, 930);
    },

    seeInventoryRec: function (guid, data_status) {
        console.log(guid, data_status)
        var url = "detailmanage/seeInventoryRec?type=QDCX&isEdit=true&guid=" + guid + "&data_status=" + data_status;
        sw.modelPopup(url, "查看清单回执详情", false, 800, 300);
    }


};
