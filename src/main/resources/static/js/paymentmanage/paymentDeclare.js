/**
 * Created on 2017-7-23.
 * zwf
 * 支付单申报
 */
sw.page.modules["paymentmanage/paymentDeclare"] = sw.page.modules["paymentmanage/paymentDeclare"] || {
    // 支付单申报列表查询
    query: function () {
        // 获取查询表单参数
        var orderNo = $("[name='orderNo']").val();//订单编号
        var payTransactionId = $("[name='payTransactionId']").val();//支付交易编号

        // 拼接URL及参数
        var url = sw.serializeObjectToURL("api/paymentManage/queryPaymentDeclare", {
            orderNo: orderNo,
            payTransactionId: payTransactionId
        });

        // 数据表
        sw.datatable("#query-paymentDeclare-table", {
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
                {
                    label: '<input type="checkbox" name="cb-check-all"/>',
                    orderable: false,
                    data: null,
                    render: function (data, type, row) {
                        if(row.data_status == "CBDS31"){
                            return "";
                        }else {
                            return '<input type="checkbox" class="submitKey" value="' +
                                row.order_no + '" />';
                        }
                    }
                },
                {data: "pay_transaction_id", label: "支付交易编号"},
                {data: "order_no", label: "订单编号"},
                {data: "pay_name", label: "支付企业名称"},
                {data: "ebp_name", label: "电商平台名称"},
                {data: "payer_name", label: "支付人"},
                {
                    label: "支付金额（元）", render: function (data, type, row) {
                    return row.amount_paid;
                }
                },
                {
                    data: "data_status", label: "业务状态",render:function(data,type,row){
                        switch (row.data_status){
                            case "CBDS1"://待申报
                                textColor="text-yellow";
                                break;
                            case "CBDS3"://支付单待申报
                                textColor="text-yellow";
                                break;
                            case "CBDS30"://支付单申报中
                                textColor="text-yellow";
                                break;
                            case "CBDS32"://支付单申报成功
                                textColor="text-yellow";
                                break;
                            case "CBDS33"://支付单重报
                                textColor="text-yellow";
                                break;
                        }
                        return "<span class='" + textColor + "'>" + row.data_status + "</span>";
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
                {data: "return_status", label: "入库结果"}
            ]
        });
    },
    // 提交海关
    submitCustom: function () {
        var submitKeys = "";
        $(".submitKey:checked").each(function () {
            submitKeys += "," + $(this).val();
        });
        if (submitKeys.length > 0) {
            submitKeys = submitKeys.substring(1);
        } else {
            sw.alert("请先勾选要提交海关的支付单信息！");
            return;
        }

        sw.confirm("请确认支付单数据无误，提交海关", "确认", function () {

            var idCardValidate = $("[name='idCardValidate']").val();
            sw.blockPage();

            var postData = {
                submitKeys: submitKeys
            };

            $("#submitPaymentBtn").prop("disabled", true);

            sw.ajax("api/paymentManage/submitCustom", "POST", postData, function (rsp) {
                if (rsp.data.result == "true") {
                    sw.alert("提交海关成功", "提示", function () {
                    }, "modal-success");
                    $("#submitPaymentBtn").prop("disabled", false);
                    sw.page.modules["paymentmanage/paymentDeclare"].query();
                } else {
                    sw.alert(rsp.data.msg);
                }
                $.unblockUI();
            });
        });
    },
    init: function () {
        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyy-mm-dd",
            autoclose: true
        });
        $("[ws-search]").unbind("click").click(this.query).click();
        $("[ws-submit]").unbind("click").click(this.submitCustom);
        $table = $("#query-paymentDeclare-table");
        $table.on("change", ":checkbox", function () {
            if ($(this).is("[name='cb-check-all']")) {
                //全选
                $(":checkbox", $table).prop("checked", $(this).prop("checked"));
            } else {
                //复选
                var checkbox = $("tbody :checkbox", $table);
                $(":checkbox[name='cb-check-all']", $table).prop('checked', checkbox.length == checkbox.filter(':checked').length);
            }
        });
    }
};