//清单查询
sw.page.modules["detailmanage/detailQuery"] = sw.page.modules["detailmanage/detailQuery"] || {

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
            lengthMenu: [[50, 100, 1000, -1], [50, 100, 1000, "所有"]],
            searching: false,//开启本地搜索
            columns: [
                // {data: "order_no", label: "订单编号"},//订单编号要点击查看订单详情
                {
                    label: "订单编号", render: function (data, type, row) {
                    return '<a href="javascript:void(0)"  onclick="' + "javascript:sw.pageModule('detailmanage/detailQuery').seeOrderNoDetail('" + row.guid + "','"+row.order_no+"')" + '">' + row.order_no + '</a>'
                }
                },
                {data: "logistics_code", label: "物流运单编号"},
                {data: "ebc_name", label: "电商企业名称"},
                {data: "ebc_name", label: "支付企业名称"},
                {data: "logistics_name", label: "物流企业名称"},
                // {data: "g_name", label: "商品名称"},
                {
                    label: "申报日期", render: function (data, type, row) {
                    if(!isEmpty(row.app_time)){
                        return moment(row.app_time).format("YYYY-MM-DD HH:mm:ss");
                    }
                    return "";
                }
                },
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
                {data: "return_status", label: "回执状态"},
                {data: "return_info", label: "回执备注"}
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

    seeOrderNoDetail: function (guid,order_no) {
        console.log(guid,order_no)
        var url = "detailmanage/seeInventoryDetail?type=QDCX&isEdit=true&guid="+guid+"&orderNo="+order_no;
        sw.modelPopup(url, "查看清单详情", false, 1000, 930);
    }
















};
