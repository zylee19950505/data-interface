// 订单查询
sw.page.modules["ordermanage/orderQuery"] = sw.page.modules["ordermanage/orderQuery"] || {
    query: function () {

        var startDeclareTime = $('[name="startDeclareTime"]').val();//申报时间
        var endDeclareTime = $('[name="endDeclareTime"]').val();//申报结束时间
        var orderNo = $('[name="orderNo"]').val();//订单编号
        var billNo =  $("[name = 'billNo']").val();//提运单号

        var url = sw.serializeObjectToURL("api/ordermanage/queryOrder/queryOrderHeadList", {
            startDeclareTime: startDeclareTime,
            endDeclareTime: endDeclareTime,
            orderNo: orderNo,
            billNo:billNo
        });

        var table = sw.datatable("#query-orderHead-table", {
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
                    label: "订单编号", render: function (data, type, row) {
                    return '<a href="javascript:void(0)"  onclick="' + "javascript:sw.pageModule('ordermanage/orderQuery').seeOrderNoDetail('" + row.guid + "','" + row.order_No + "')" + '">' + row.order_No + '</a>'
                }
                },

                {data: "ebp_Name", label: "电商企业名称"},
                {data: "ebc_Name", label: "电商平台名称"},
                {data: "goods_Value", label: "总价"},
                {data: "buyer_Name", label: "订购人"},
                {
                    label: "申报日期", render: function (data, type, row) {
                    if (!isEmpty(row.app_Time)) {
                        return moment(row.app_Time).format("YYYY-MM-DD HH:mm:ss");
                    }
                    return "";
                }
                },
                {
                    label: "业务状态", render: function (data, type, row) {
                    var textColor = "";
                    var value = "";
                    switch (row.data_status) {
                        case "CBDS22":
                            textColor = "text-green";
                            value = "订单申报成功";
                            break;
                        case "CBDS23":
                            textColor = "text-red";
                            value = "订单重报";
                            break;
                        case "CBDS24":
                            textColor = "text-red";
                            value = "订单申报失败";
                            break;
                    }
                    return "<span class='" + textColor + "'>" + value + "</span>";
                }
                },

                {data: "return_status", label: "回执状态"},
                {data: "return_info", label: "回执备注"},
            ],
            order: [[1, 'asc']]
        });
    },
    init: function () {
        $("[name='startDeclareTime']").val(moment(new Date()).date(1).format("YYYY-MM-DD"));
        $("[name='endDeclareTime']").val(moment(new Date()).format("YYYY-MM-DD"));
        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyy-mm-dd",
            autoclose: true
        });
        $("[ws-search]").unbind("click").click(this.query).click();
    },
    seeOrderNoDetail: function (guid, order_No) {
        console.log(guid, order_No)
        var url = "ordermanage/seeOrderDetail?type=DDCX&isEdit=true&guid=" + guid + "&orderNo=" + order_No;
        sw.modelPopup(url, "查看订单详情", false, 1000, 930);
    }
}


