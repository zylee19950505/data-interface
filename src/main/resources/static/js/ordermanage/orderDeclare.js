/**
 * Created on 2017-7-23.
 * 订单申报
 */
sw.page.modules["ordermanage/orderDeclare"] = sw.page.modules["ordermanage/orderDeclare"] || {
    // 订单申报列表查询
    query: function () {
        // 获取查询表单参数
        var startFlightTimes = $("[name='startFlightTimes']").val();//开始时间
        var endFlightTimes = $("[name='endFlightTimes']").val();//结束时间
        var dataStatus = $("[name='dataStatus']").val();//业务状态
        var billNo = $("[name = 'billNo']").val();//提运单号

        // 拼接URL及参数
        var url = sw.serializeObjectToURL("api/orderManage/queryOrderDeclare", {
            startFlightTimes: startFlightTimes,//申报开始时间
            endFlightTimes: endFlightTimes,//申报结束时间
            billNo: billNo,//提运单号
            dataStatus: dataStatus//业务状态
        });

        // 数据表
        sw.datatable("#query-orderDeclare-table", {
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
                        if (row.data_status == "CBDS2") {
                            return '<input type="checkbox" class="submitKey" value="' +
                                row.bill_no + '" />';
                        }
                        else {
                            return "";
                        }
                    }
                },
                {
                    label: "提运单号", render: function (data, type, row) {
                    if (row.no == "1") {
                        return row.bill_no;
                    } else {
                        return "";
                    }
                }
                },
                {
                    label: "申报日期", render: function (data, type, row) {
                    if (row.no == "1") {
                        return isEmpty(row.appTime) ? "" : moment(row.appTime).format("YYYY-MM-DD HH:mm:ss");
                    } else {
                        return "";
                    }
                }
                },
                {
                    label: "订单总数", render: function (data, type, row) {
                    if (row.no == "1") {
                        return row.totalCount;
                    } else {
                        return "";
                    }
                }
                },
                {
                    label: "业务状态", render: function (data, type, row) {
                    var value = "";
                    var textColor = "";
                    switch (row.data_status) {
                        case "CBDS1":
                            value = "校验未通过";
                            textColor = "text-red";
                            break;
                        case "CBDS2":
                            value = "订单待申报";
                            textColor = "text-yellow";
                            break;
                        case "CBDS20":
                            value = "订单申报中";
                            textColor = "text-green";
                            break;
                        case "CBDS21":
                            value = "订单已申报";
                            textColor = "text-green";
                            break;
                        case "CBDS22":
                            value = "订单申报成功";
                            textColor = "text-green";
                            break;
                        case "OrderDoing":
                            value = "订单报文生成中";
                            textColor = "text-green";
                            break;
                        case "OrderOver":
                            value = "订单报文下载完成";
                            textColor = "text-green";
                            break;
                    }
                    var result = "<span class=" + textColor + ">" + value + "</span>";
                    return result
                }
                },
                {data: "count", label: "订单数量"}

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
            sw.alert("请先勾选要提交海关的订单信息！");
            return;
        }

        sw.confirm("请确认分单总数无误，提交海关", "确认", function () {

            var idCardValidate = $("[name='idCardValidate']").val();//身份证校验状态
            sw.blockPage();

            var postData = {
                submitKeys: submitKeys
            };

            $("#submitManifestBtn").prop("disabled", true);

            sw.ajax("api/orderManage/submitCustom", "POST", postData, function (rsp) {
                if (rsp.data.result == "true") {
                    sw.alert("提交海关成功", "提示", function () {
                    }, "modal-success");
                    $("#submitManifestBtn").prop("disabled", false);
                    sw.page.modules["ordermanage/orderDeclare"].query();
                } else {
                    sw.alert(rsp.data.msg);
                }
                $.unblockUI();
            });
        });
    },

    // 报文下载
    orderXmlDownLoad: function () {
        var submitKeys = "";
        $(".submitKey:checked").each(function () {
            submitKeys += "," + $(this).val();
        });
        if (submitKeys.length > 0) {
            submitKeys = submitKeys.substring(1);
        } else {
            sw.alert("请先勾选要生成报文的订单信息！");
            return;
        }
        sw.confirm("请确认分单总数无误，提交海关", "确认", function () {
            var idCardValidate = $("[name='idCardValidate']").val();//身份证校验状态
            sw.blockPage();
            var postData = {
                submitKeys: submitKeys
            };
            $("#orderXmlDownload").prop("disabled", true);
            sw.ajax("api/orderManage/orderXmlDownload", "POST", postData, function (rsp) {
                var str = rsp.data.result;
                if (str.substring(0, 1) == "1") {
                    sw.alert("订单报文生成中", "提示", function () {
                    }, "modal-success");
                    $("#orderXmlDownload").prop("disabled", false);
                    sw.page.modules["ordermanage/orderDeclare"].query();
                    window.location.href = "/api/orderManage/downloadFile?type=" + str.substring(1);
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
        $("[ws-download]").unbind("click").click(this.orderXmlDownLoad);
        $table = $("#query-orderDeclare-table");
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

