//入区核注清单
sw.page.modules["bondedienter/enterInventory"] = sw.page.modules["bondedienter/enterInventory"] || {
    query: function () {
        // 获取查询表单参数
        var startFlightTimes = $("[name='startFlightTimes']").val();
        var dataStatus = $("[name='dataStatus']").val();
        var returnDataStatus = $("[name='returnDataStatus']").val();
        var invtNo = $("[name='invtNo']").val();

        // 拼接URL及参数
        var url = sw.serializeObjectToURL("api/enterInventory/queryEnterInventory", {
            startFlightTimes: startFlightTimes,//申报开始时间
            dataStatus: dataStatus,//申报状态
            returnDataStatus: returnDataStatus,//物流运单编号
            invtNo: invtNo//核注清单编号
        });

        // 数据表
        sw.datatable("#query-enterInventory-table", {
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
                    label: '<input type="checkbox" name="cb-check-all"/>',
                    orderable: false,
                    data: null,
                    render: function (data, type, row) {
                        if (row.status == "BDDS1") {
                            return '<input type="checkbox" class="submitKey" value="' +
                                row.etps_inner_invt_no + '" />';
                        }
                        else {
                            return "";
                        }
                    }
                },
                {
                    label: "企业内部编码", render: function (data, type, row) {
                        return '<a href="javascript:void(0)"  onclick="' + "javascript:sw.pageModule('bondedienter/enterInventory').seeEnterInventoryInfo('" + row.etps_inner_invt_no + "','" + row.status + "')" + '">' + row.etps_inner_invt_no + '</a>'
                    }
                },
                {
                    data: "bond_invt_no", label: "核注清单号"
                },
                {
                    data: "invt_preent_no", label: "预录入编号"
                },
                {
                    label: "申报状态", render: function (data, type, row) {
                        var textColor = "";
                        var value = "";
                        switch (row.status) {
                            case "BDDS1":
                                textColor = "text-yellow";
                                value = "核注清单待申报";
                                break;
                            case "BDDS10":
                                textColor = "text-green";
                                value = "核注清单申报中";
                                break;
                            case "BDDS11":
                                textColor = "text-green";
                                value = "核注清单正在发往海关";
                                break;
                            case "BDDS12":
                                textColor = "text-green";
                                value = "核注清单申报成功";
                                break;
                        }

                        return "<span class='" + textColor + "'>" + value + "</span>";
                    }
                },
                {
                    label: "申报时间", render: function (data, type, row) {
                        if (!isEmpty(row.invt_dcl_time)) {
                            return moment(row.invt_dcl_time).format("YYYY-MM-DD HH:mm:ss");
                        }
                        return "";
                    }
                },
                {
                    data: "return_status", label: "回执状态"
                },
                {
                    label: "回执时间", render: function (data, type, row) {
                        if (!isEmpty(row.return_time)) {
                            return moment(row.return_time).format("YYYY-MM-DD HH:mm:ss");
                        }
                        return "";
                    }
                },
                {
                    data: "return_info", label: "回执备注"
                }
            ]
        });
    },
    // 提交海关
    submitCustomByCode: function () {
        var submitKeys = "";
        $(".submitKey:checked").each(function () {
            submitKeys += "," + $(this).val();
        });
        if (submitKeys.length > 0) {
            submitKeys = submitKeys.substring(1);
        } else {
            sw.alert("请先勾选要提交海关的入区核注清单信息！");
            return;
        }

        sw.confirm("请确认数据无误并提交海关", "确认", function () {

            sw.blockPage();

            var postData = {
                submitKeys: submitKeys
            };

            $("#submitCustom").prop("disabled", true);

            sw.ajax("api/enterInventory/enterinventory/submitCustom", "POST", postData, function (rsp) {
                if (rsp.data.result == "true") {
                    sw.alert("提交海关成功", "提示", function () {
                    }, "modal-success");
                    $("#submitCustom").prop("disabled", false);
                    sw.page.modules["bondediexit/exitInventory"].query();
                } else {
                    sw.alert(rsp.data.msg);
                }
                $.unblockUI();
            });
        });
    },

    deleteByCode: function () {
        var submitKeys = "";
        $(".submitKey:checked").each(function () {
            submitKeys += "," + $(this).val();
        });
        if (submitKeys.length > 0) {
            submitKeys = submitKeys.substring(1);
        } else {
            sw.alert("请先勾选要删除的入区核注清单信息！");
            return;
        }
        var postData = {
            submitKeys: submitKeys
        };
        sw.confirm("确定删除该入区核注清单", "确认", function () {
            sw.ajax("api/enterInventory/enterinventory/deleteEnterInventory", "POST", postData, function (rsp) {
                sw.pageModule("bondedienter/enterInventory").query();
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
        $("[ws-search]").unbind("click").click(this.query);
        // $("[ws-download]").unbind("click").click(this.download);
        $(".btn[ws-search]").click();
        $("[ws-delete]").unbind("click").click(this.deleteByCode);
        $("[ws-submit]").unbind("click").click(this.submitCustomByCode);

        $table = $("#query-enterInventory-table");
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
    seeEnterInventoryInfo: function (etpsInnerInvtNo,status) {
        if ("BDDS10"==status || "BDDS11"==status || "BDDS12"==status){
            var url = "bondedienter/seeEnterInventoryDetail?type=RQHZQD&isEdit=false&etps_inner_invt_no=" + etpsInnerInvtNo;
        }else{
            var url = "bondedienter/seeEnterInventoryDetail?type=RQHZQD&isEdit=true&etps_inner_invt_no=" + etpsInnerInvtNo;
        }
        sw.modelPopup(url, "查看清单详情", false, 1100, 930);
    }


};

