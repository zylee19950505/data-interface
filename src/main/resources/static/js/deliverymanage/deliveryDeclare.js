/**
 * Created on 2017-7-23.
 * 清单申报
 */
sw.page.modules["deliverymanage/deliveryDeclare"] = sw.page.modules["deliverymanage/deliveryDeclare"] || {
    // 订单申报列表查询
    query: function () {
        // 获取查询表单参数
        var startFlightTimes = $("[name='startFlightTimes']").val();
        var endFlightTimes = $("[name='endFlightTimes']").val();
        var billNo = $("[name='billNo']").val();
        var dataStatus = $("[name='dataStatus']").val();

        // 拼接URL及参数
        var url = sw.serializeObjectToURL("api/deliveryManage/queryDeliveryDeclare", {
            startFlightTimes: startFlightTimes,//导入时间
            endFlightTimes: endFlightTimes,//导入时间
            billNo: billNo,//提运单号
            dataStatus: dataStatus//业务状态
        });

        // 数据表
        sw.datatable("#query-deliveryDeclare-table", {
            ajax: url,
            lengthMenu: [[50, 100, 1000], [50, 100, 1000]],
            searching: false,//开启本地搜索
            columns: [
                //还需判断下状态
                {

                    label: '<input type="checkbox" name="cb-check-all"/>',
                    orderable: false,
                    data: null,
                    render: function (data, type, row) {
                        if (row.data_status == "CBDS7" || row.data_status == "CBDS1") {
                            return '<input type="checkbox" class="submitKey" value="' +
                                row.bill_no + '" />';
                        }
                        else {
                            return "";
                        }
                    }
                },
                {data: "bill_no", label: "提运单号"},//订单编号要点击查看订单详情
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
                        case "CBDS1":
                            textColor = "text-yellow";
                            value = "待申报";
                            break;
                        case "CBDS7":
                            textColor = "text-yellow";
                            value = "入库明细单待申报";
                            break;
                        case "CBDS70":
                            textColor = "text-green";
                            value = "入库明细单申报中";
                            break;
                        case "CBDS71":
                            textColor = "text-green";
                            value = "入库明细单已申报";
                            break;
                        case "CBDS72":
                            textColor = "text-green";
                            value = "入库明细单申报成功";
                            break;
                        case "CBDS73":
                            textColor = "text-red";
                            value = "入库明细单重报";
                            break;
                    }

                    return "<span class='" + textColor + "'>" + value + "</span>";
                }
                },
                {data: "return_status", label: "回执状态"},
                {data: "return_info", label: "回执备注"}
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
            sw.alert("请先勾选要提交海关的入库明细单信息！");
            return;
        }
        sw.confirm("请确认分单总数无误，提交海关", "确认", function () {
            var idCardValidate = $("[name='idCardValidate']").val();
            sw.blockPage();
            var postData = {
                submitKeys: submitKeys,
                idCardValidate: idCardValidate,
                ieFlag: sw.ie,
                entryType: sw.type
            };
            $("#submitCustom").prop("disabled", true);
            sw.ajax("api/deliveryManage/submitCustom", "POST", postData, function (rsp) {
                if (rsp.data.result == "true") {
                    sw.alert("提交海关成功", "提示", function () {
                    }, "modal-success");
                    $("#submitCustom").prop("disabled", false);
                    sw.page.modules["deliverymanage/deliveryDeclare"].query();
                } else {
                    sw.alert(rsp.data.msg);
                }
                $.unblockUI();
            });
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
        $("[ws-submit]").unbind("click").click(this.submitCustom);
        $table = $("#query-deliveryDeclare-table");
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

