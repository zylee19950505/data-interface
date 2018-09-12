/**
 * 支付单查询
 * Created by zwf
 */
sw.page.modules["paymentmanage/paymentQuery"] = sw.page.modules["paymentmanage/paymentQuery"] || {

    query: function () {
        // 获取查询表单参数
        var orderNo = $("[name='orderNo']").val();//订单编号
        var payTransactionId = $("[name='payTransactionId']").val();//支付交易编号
        var startFlightTimes = $("[name='startFlightTimes']").val();
        var endFlightTimes = $("[name='endFlightTimes']").val();
        var returnStatus = $("[name='returnStatus']").val();

        // 拼接URL及参数
        var url = sw.serializeObjectToURL("api/paymentManage/querypayment/queryPaymentQuery", {
            orderNo: orderNo,
            payTransactionId: payTransactionId,
            startFlightTimes: startFlightTimes,
            endFlightTimes: endFlightTimes,
            returnStatus: returnStatus//回执状态
        });

        // 数据表
        sw.datatable("#query-paymentQuery-table", {
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
            lengthMenu: [[50, 100, 1000], [50, 100, 1000]],
            searching: false,//开启本地搜索
            columns: [
                {
                    label: "支付交易编号", render: function (data, type, row) {
                    //a链接跳转到querypaymentbyid方法。并赋值参数。 cursor:pointer鼠标移动上去变手掌样式
                    var result = '<a style="cursor:pointer" title="查看" ' +
                        'onclick="' + "javascript:sw.pageModule('paymentmanage/paymentQuery')" +
                        ".seePaymentDetail('" + row.pay_transaction_id + "','" + row.order_no + "','" + row.guid + "')" + '">' + row.pay_transaction_id + '</a>';
                    return result;
                }
                },
                {data: "order_no", label: "订单编号"},
                {data: "ebp_name", label: "电商平台名称"},
                {data: "payer_name", label: "支付人"},
                {
                    label: "支付金额（元）", render: function (data, type, row) {
                    return row.amount_paid;
                }
                },
                {
                    label: "支付时间", render: function (data, type, row) {
                    if (!isEmpty(row.pay_time)) {
                        return moment(row.pay_time).format("YYYY-MM-DD HH:mm:ss");
                    }
                    return "";
                }
                },
                {
                    data: "data_status", label: "业务状态", render: function (data, type, row) {
                    switch (row.data_status) {
                        case "CBDS32"://支付单申报成功
                            textColor = "text-green";
                            row.data_status = "支付单申报成功";
                            break;
                        case "CBDS33"://支付单申报成功
                            textColor = "text-red";
                            row.data_status = "支付单重报";
                            break;
                        case "CBDS34"://支付单申报成功
                            textColor = "text-red";
                            row.data_status = "支付单申报失败";
                            break;
                    }
                    // return "<span class='" + textColor + "'>" + row.data_status + "</span>";
                    return '<a href="javascript:void(0)"  onclick="' + "javascript:sw.pageModule('paymentmanage/paymentQuery').seePaymentRec('" + row.guid + "','" + row.pay_transaction_id + "')" + '">' + row.data_status + '</a>'
                }
                }
            ]
        });
    },

    seePaymentDetail: function (pay_transaction_id, order_no, guid) {
        var url = "paymentmanage/seePaymentDetail?type=ZFDCX&isEdit=true&paytransactionid=" + pay_transaction_id + "&orderNo=" + order_no + "&guid=" + guid;
        sw.modelPopup(url, "支付单详情信息", false, 900, 400);
    },

    seePaymentRec: function (guid, pay_transaction_id) {
        var url = "paymentmanage/seePaymentRec?type=ZFDCX&isEdit=true&paytransactionid=" + pay_transaction_id + "&guid=" + guid;
        sw.modelPopup(url, "查看支付单回执详情", false, 800, 300);
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
    }

};
