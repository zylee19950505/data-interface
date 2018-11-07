/**
 * 逻辑校验
 */
sw.page.modules["paymentmanage/paymentLogicVerify"] = sw.page.modules["paymentmanage/paymentLogicVerify"] || {
    //查询
    query: function () {
        var url = sw.serializeObjectToURL($("[ws-search]").attr("ws-search"), {
            order_no: $("[name='order_no']").val(),
            pay_transaction_id: $("[name='pay_transaction_id']").val(),
            status: "N"
        });
        // 数据表
        sw.datatable("#query-logic-table", {
            ajax: sw.resolve("api", url),
            lengthMenu: [[200, 500, 1000], [200, 500, 1000]],
            searching: false,
            columns: [
                {
                    label: ' <input type="checkbox" name="cb-check-all"/>',
                    orderable: false,
                    data: null,
                    render: function (data, type, row) {
                        if (row.status == "ING" || row.status == "Y") {
                            return "";
                        }
                        return '<input type="checkbox" class="submitKey" value="' + row.guid + '" />';
                    }
                },
                {
                    label: "订单编号", render: function (data, type, row) {
                    return row.order_no;
                }
                },
                {
                    label: "支付流水号", render: function (data, type, row) {
                    return '<a href="javascript:void(0)"  onclick="' + "javascript:sw.pageModule('paymentmanage/paymentLogicVerify').seePaymentLogicDetail('" + row.pay_transaction_id + "','" + row.order_no + "','" + row.guid + "')" + '">' + row.pay_transaction_id + '</a>';
                }
                },
                {
                    label: "错误原因",
                    render: function (data, type, row) {
                        var message = "<span class='text-red'>" + JSON.parse(row.vs_result).message + "</span>";
                        return message;
                    }
                }
            ]
        });
    },

    //初始化
    init: function () {
        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyymmdd",
            autoclose: true
        });

        $("[ws-search]").unbind("click").click(this.query);
        $(".btn[ws-search]").click();
        // $("[ws-delete]").unbind("click").click(this.deleteVerify);
        // $("[ws-back]").unbind("click").click(this.back);

        $table = $("#query-logic-table");
        $table.on("change", ":checkbox", function () {
            if ($(this).is("[name='cb-check-all']")) {
                //全选
                $(":checkbox", $table).prop("checked", $(this).prop("checked"));
            } else {
                //一般复选
                var checkbox = $("tbody :checkbox", $table);
                $(":checkbox[name='cb-check-all']", $table).prop('checked', checkbox.length == checkbox.filter(':checked').length);
            }
        });
    },

    seePaymentLogicDetail: function (pay_transaction_id, order_no, guid) {
        var url = "paymentmanage/seePaymentDetail?type=LJJY&isEdit=true&paytransactionid=" + pay_transaction_id + "&orderNo=" + order_no + "&guid=" + guid;
        sw.modelPopup(url, "查看支付单详情", false, 900, 400);
    }

};