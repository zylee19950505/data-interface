//清单查询
sw.page.modules["outInventoryList/entranceInventoryList"] = sw.page.modules["outInventoryList/entranceInventoryList"] || {

    query: function () {
        // 获取查询表单参数
        var startFlightTimes = $("[name='startFlightTimes']").val();
        var endFlightTimes = $("[name='endFlightTimes']").val();
        var billNo = $("[name='billNo']").val();
        var orderNo = $("[name='orderNo']").val();
        var logisticsNo = $("[name='logisticsNo']").val();
        var preNo = $("[name='preNo']").val();
        var invtNo = $("[name='invtNo']").val();
        var returnStatus = $("[name='returnStatus']").val();

        // 拼接URL及参数
        var url = sw.serializeObjectToURL("api/detailManage/queryDetailQuery", {
            startFlightTimes: startFlightTimes,//申报开始时间
            endFlightTimes: endFlightTimes,//申报结束时间
            billNo: billNo,//提运单号
            orderNo: orderNo,//订单编号
            logisticsNo: logisticsNo,//物流运单编号
            preNo: preNo,//电子口岸标识编号
            invtNo: invtNo,//海关清单编号
            returnStatus: returnStatus//回执状态
        });

        // 数据表
        sw.datatable("#query-entranceInventoryList-table", {
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
                {
                    label: '<input type="checkbox" name="cb-check-all"/>',
                    orderable: false,
                    data: null,
                    render: function (data, type, row) {
                        if (row.data_status == "CBDS6") {
                            return '<input type="checkbox" class="submitKey" value="' +
                                row.bill_no + '" />';
                        }
                        else {
                            return "";
                        }
                    }
                },
                {data: "bill_no", label: "核注清单编号"},//订单编号要点击查看订单详情
                {data: "logistics_no", label: "申报状态"},
                {data: "invt_no", label: "申报时间"},
                {
                    label: "回执状态", render: function (data, type, row) {
                        return '<div style="width:100px;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;" ' +
                            'title="' + row.ebp_name + '">' + row.ebp_name + '</div>';
                    }
                },
                {
                    label: "回执时间", render: function (data, type, row) {
                        return '<div style="width:100px;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;" ' +
                            'title="' + row.ebc_name + '">' + row.ebc_name + '</div>';
                    }
                },
                {
                    label: "回执备注", render: function (data, type, row) {
                        return '<div style="width:100px;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;" ' +
                            'title="' + row.ebc_name + '">' + row.ebc_name + '</div>';
                    }
                }
            ]
        });
    },

    init: function () {
        $("[name='startFlightTimes']").val(moment(new Date()).date(1).format("YYYY-MM-DD"));
        $("[name='endFlightTimes']").val(moment(new Date()).format("YYYY-MM-DD"));
        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyy-mm-dd",
            autoclose: true
        });
        $("[ws-search]").unbind("click").click(this.query).click();
        // $("[ws-download]").unbind("click").click(this.download);
        $(".btn[ws-search]").click();
    },

    seeInventoryDetail: function (guid, order_no, return_status) {
        if (return_status == 100) {
            var url = "detailmanage/seeInventoryDetail?type=QDCX&isEdit=true&guid=" + guid + "&orderNo=" + order_no;
        } else {
            var url = "detailmanage/seeInventoryDetail?type=QDCX&isEdit=false&guid=" + guid + "&orderNo=" + order_no;
        }
        sw.modelPopup(url, "查看清单详情", false, 1100, 930);
    },

    seeInventoryRec: function (guid, data_status) {
        var url = "detailmanage/seeInventoryRec?type=QDCX&isEdit=true&guid=" + guid + "&data_status=" + data_status;
        sw.modelPopup(url, "查看清单回执详情", false, 800, 300);
    }

};

