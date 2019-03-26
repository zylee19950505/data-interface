//生成清单申报
sw.page.modules["bondinvenmanage/bondinvenbudDeclare"] = sw.page.modules["bondinvenmanage/bondinvenbudDeclare"] || {

    // 订单申报列表查询
    query: function () {
        // 获取查询表单参数
        var startFlightTimes = $("[name='startFlightTimes']").val();//开始时间
        var endFlightTimes = $("[name='endFlightTimes']").val();//结束时间
        var dataStatus = $("[name='dataStatus']").val();//业务状态
        var billNo = $("[name = 'billNo']").val();//提运单号

        // 拼接URL及参数
        var url = sw.serializeObjectToURL("api/bondinvenmanage/queryBondinvenbudDeclare", {
            startFlightTimes: startFlightTimes,//申报开始时间
            endFlightTimes: endFlightTimes,//申报结束时间
            billNo: billNo,//提运单号
            dataStatus: dataStatus//业务状态
        });

        // 数据表
        sw.datatable("#query-budDeclare-table", {
            ajax: url,
            lengthMenu: [[50, 100, 1000, -1], [50, 100, 1000, "所有"]],
            searching: false,//开启本地搜索
            columns: [
                //还需判断下状态
                {
                    label: '<input type="checkbox" name="cb-check-all"/>',
                    orderable: false,
                    data: null,
                    render: function (data, type, row) {
                        if (row.data_status == "BDDS5") {
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
                            return isEmpty(row.app_time)?"":moment(row.app_time).format("YYYY-MM-DD HH:mm:ss");
                        }
                        return "";
                    }
                },
                {
                    label: "清单总数", render: function (data, type, row) {
                        if (row.no == "1") {
                            return row.sum;
                        } else {
                            return "";
                        }
                    }
                },
                {
                    label: "业务状态", render: function (data, type, row) {
                        var textColor = "";
                        var value = "";
                        switch (row.data_status) {
                            case "CBDS1"://待申报
                                textColor = "text-red";
                                value = "校验未通过";
                                break;
                            case "BDDS5":
                                textColor = "text-yellow";
                                value = "保税清单待申报";
                                break;
                            case "BDDS50":
                                textColor = "text-green";
                                value = "保税清单申报中";
                                break;
                            case "BDDS51":
                                textColor = "text-green";
                                value = "保税清单已申报";
                                break;
                            case "BDDS52":
                                textColor = "text-green";
                                value = "保税清单申报成功";
                                break;
                            case "InvenDoing":
                                textColor = "text-green";
                                value = "清单报文生成中";
                                break;
                            case "InvenOver":
                                textColor = "text-green";
                                value = "清单报文下载完成";
                                break;
                        }
                        return "<span class='" + textColor + "'>" + value + "</span>";
                    }
                },
                {data: "asscount", label: "清单数量"}
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
            sw.alert("请先勾选要提交海关的清单信息！");
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

            $("#submitManifestBtn").prop("disabled", true);

            sw.ajax("api/bondinvenmanage/submitBudCustom", "POST", postData, function (rsp) {
                if (rsp.data.result == "true") {
                    sw.alert("提交海关成功", "提示", function () {
                    }, "modal-success");
                    $("#submitManifestBtn").prop("disabled", false);
                    sw.page.modules["bondinvenmanage/bondinvenbudDeclare"].query();
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
        $table = $("#query-budDeclare-table");
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
