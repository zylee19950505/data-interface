//清单生成
sw.page.modules["bondinvenmanage/bondinvenbuilder"] = sw.page.modules["bondinvenmanage/bondinvenbuilder"] || {

    query: function () {
        // 获取查询表单参数
        var billNo = $("[name='billNo']").val();
        var orderNo = $("[name='orderNo']").val();
        var logisticsNo = $("[name='logisticsNo']").val();
        var dataStatus = $("[name='dataStatus']").val();
        // 拼接URL及参数
        var url = sw.serializeObjectToURL("api/bondinvenmanage/queryDetailBuilder", {
            billNo: billNo,//提运单号
            orderNo: orderNo,//订单编号
            logisticsNo: logisticsNo,//物流运单编号
            dataStatus:dataStatus
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
                {
                    label: '<input type="checkbox" name="cb-check-all"/>',
                    orderable: false,
                    data: null,
                    render: function (data, type, row) {
                        if (row.orderStatus == "BDDS62" && row.logisticsStatus == "CBDS42" && row.dataStatus == null) {
                            return '<input type="checkbox" class="submitKey" value="' +
                                row.order_no + '" />';
                        }
                        else if (row.orderStatus == "BDDS62" && row.logisticsStatus == "CBDS42" && row.dataStatus == "QDSCSB") {
                            return '<input type="checkbox" class="submitKey" value="' +
                                row.order_no + '" />';
                        }
                        else {
                            return "";
                        }
                    }
                },
                {data: "order_no", label: "订单编号"},
                {data: "logistics_no", label: "物流运单编号"},
                {
                    label: "订单状态", render: function (data, type, row) {
                    var textColor = "";
                    var value = "";
                    switch (row.orderStatus) {
                        case "BDDS6"://待申报
                            textColor = "text-yellow";
                            value = "保税订单待申报";
                            break;
                        case "BDDS60"://待申报
                            textColor = "text-yellow";
                            value = "保税订单待申报";
                            break;
                        case "BDDS61":
                            textColor = "text-green";
                            value = "保税订单正在发往海关";
                            break;
                        case "BDDS62":
                            textColor = "text-green";
                            value = "保税订单申报成功";
                            break;
                        case "BDDS63":
                            textColor = "text-yellow";
                            value = "保税订单重报";
                            break;
                        case "BDDS64":
                            textColor = "text-red";
                            value = "保税订单申报失败";
                            break;
                    }
                    return "<span class='" + textColor + "'>" + value + "</span>";
                }
                },
                {
                    label: "运单状态", render: function (data, type, row) {
                    var textColor = "";
                    var value = "";
                    switch (row.logisticsStatus) {
                        case "CBDS4"://待申报
                            textColor = "text-yellow";
                            value = "运单待申报";
                            break;
                        case "CBDS40"://待申报
                            textColor = "text-yellow";
                            value = "运单待申报";
                            break;
                        case "CBDS41":
                            textColor = "text-green";
                            value = "运单正在发往海关";
                            break;
                        case "CBDS42":
                            textColor = "text-green";
                            value = "运单申报成功";
                            break;
                        case "CBDS43":
                            textColor = "text-yellow";
                            value = "运单重报";
                            break;
                        case "CBDS44":
                            textColor = "text-red";
                            value = "运单申报失败";
                            break;
                    }

                    return "<span class='" + textColor + "'>" + value + "</span>";
                }
                },
                {
                    label: "清单生成状态", render: function (data, type, row) {
                    var textColor = "";
                    var value = "";
                    switch (row.dataStatus) {
                        case null://可生成清单
                            textColor = "text-yellow";
                            value = "清单可生成";
                            break;
                        case "QDSCZ"://清单生成中
                            textColor = "text-blue";
                            value = "清单生成中";
                            break;
                        case "QDYSC"://清单已生成
                            textColor = "text-green";
                            value = "清单已生成";
                            break;
                        case "QDSCSB"://清单生成失败
                            textColor = "text-red";
                            value = "清单生成失败";
                            break;
                    }

                    return "<span class='" + textColor + "'>" + value + "</span>";
                }
                }
            ]
        });
    },

    init: function () {
        $("[name='startFlightTimes']").val(moment(new Date()).subtract('days',7).format("YYYY-MM-DD"));
        $("[name='endFlightTimes']").val(moment(new Date()).format("YYYY-MM-DD"));
        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyy-mm-dd",
            autoclose: true
        });
        $("[ws-search]").unbind("click").click(this.query).click();
        $("#submitDetailBtn").unbind("click").click(this.submitCustom);
        $(".btn[ws-search]").click();
        $table = $("#query-detailQuery-table");
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
    },
    // 生成清单
    submitCustom: function () {
        var submitKeys = "";
        $(".submitKey:checked").each(function () {
            submitKeys += "," + $(this).val();
        });
        if (submitKeys.length > 0) {
            submitKeys = submitKeys.substring(1);
        } else {
            sw.alert("请先勾选要生成清单的信息！");
            return;
        }

        sw.confirm("请确认数据无误，生成清单", "确认", function () {

            //var idCardValidate = $("[name='idCardValidate']").val();
            sw.blockPage();

            var postData = {
                submitKeys: submitKeys,
            };

            $("#submitManifestBtn").prop("disabled", true);

            sw.ajax("api/bondinvenmanage/builderDetail", "POST", postData, function (rsp) {
                if (rsp.data.result == "true") {
                    sw.alert("生成清单成功!", "提示", function () {
                    }, "modal-success");
                    $("#submitManifestBtn").prop("disabled", false);
                    sw.page.modules["bondinvenmanage/bondinvenbuilder"].query();
                } else {
                    sw.alert(rsp.data.msg);
                }
                $.unblockUI();
            });
        });
    },
};
