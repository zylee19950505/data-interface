//入区核放单
sw.page.modules["bondedienter/enterManifest"] = sw.page.modules["bondedienter/enterManifest"] || {

    query: function () {
        // 获取查询表单参数
        var startFlightTimes = $("[name='startFlightTimes']").val();
        var inventory_dataStatus = $("[name='inventory_dataStatus']").val();
        var bond_invt_no = $("[name='bond_invt_no']").val();
        var billNo = $("[name='billNo']").val();
        var passport_declareStatus = $("[name='passport_declareStatus']").val();
        var passport_dataStatus = $("[name='passport_dataStatus']").val();
        var passport_no = $("[name='passport_no']").val();

        // 拼接URL及参数
        var url = sw.serializeObjectToURL("api/enterManifest/queryEnterManifest", {
            startFlightTimes: startFlightTimes,//申报开始时间
            inventory_dataStatus: inventory_dataStatus,//核注清单回执状态
            bond_invt_no: bond_invt_no,//核注清单编号
            billNo: billNo,//提运单号
            passport_declareStatus: passport_declareStatus,//核放单申报状态
            passport_dataStatus: passport_dataStatus,//核放单回执状态
            passport_no: passport_no//核放单编号
        });

        // 数据表
        sw.datatable("#query-enterManifest-table", {
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
                        if (row.status == "BDDS33") {
                            return '<input type="checkbox" class="submitKey" value="' +
                                row.etps_preent_no + '" />';
                        }
                        else {
                            return "";
                        }
                    }
                },
                //{data: "etps_preent_no", label: "核放单编号"},//订单编号要点击查看订单详情
                {
                    label: "核放单编号", render: function (data, type, row) {
                        return '<a href="javascript:void(0)"  onclick="' + "javascript:sw.pageModule('bondedienter/enterManifest').seeEnterPassportDetail('"+row.bind_typecd+"','" + row.etps_preent_no + "')" + '">' + row.etps_preent_no + '</a>'
                    }
                },
                {data: "bond_invt_no", label: "核注清单编号"},
                {data: "status", label: "申报状态"},
                {
                    label: "申报日期", render: function (data, type, row) {
                        if (!isEmpty(row.dcl_time)) {
                            return moment(row.dcl_time).format("YYYY-MM-DD HH:mm:ss");
                        }
                        return "";
                    }
                },
                {
                    label: "回执状态", render: function (data, type, row) {
                        var value = "";
                        var textColor = "";
                        switch (row.data_status) {
                            case "CBDS3":
                                value = "核放单待申报";
                                textColor = "text-red";
                                break;
                            default :
                                value = "状态待确认";
                                textColor = "text-red";
                        }
                        var result = "<span class=" + textColor + ">" + value + "</span>";
                        return result
                    }
                },
                {
                    label: "回执时间", render: function (data, type, row) {
                        if (!isEmpty(row.return_date)) {
                            return moment(row.return_date).format("YYYY-MM-DD HH:mm:ss");
                        }
                        return "";
                    }
                },
                {data: "return_info", label: "回执备注"}
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
            sw.alert("请先勾选要提交海关的入区核放单信息！");
            return;
        }

        sw.confirm("请确认数据无误并提交海关", "确认", function () {

            sw.blockPage();

            var postData = {
                submitKeys: submitKeys
            };

            $("#submitCustom").prop("disabled", true);

            sw.ajax("api/enterManifest/submitCustom", "POST", postData, function (rsp) {
                if (rsp.data.result == "true") {
                    sw.alert("提交海关成功", "提示", function () {
                    }, "modal-success");
                    $("#submitCustom").prop("disabled", false);
                    sw.page.modules["bondedienter/enterManifest"].query();
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
            sw.alert("请先勾选要删除的入区核放单信息！");
            return;
        }
        var postData = {
            submitKeys: submitKeys
        };
        sw.confirm("确定删除该入区核放单", "确认", function () {
            sw.ajax("api/enterManifest/deleteEnterManifest", "POST", postData, function (rsp) {
                sw.pageModule("bondedienter/enterManifest").query();
            });
        });
    },
    init: function () {
        // $("[name='startFlightTimes']").val(moment(new Date()).date(1).format("YYYY-MM-DD"));
        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyy-mm-dd",
            autoclose: true
        });
        $("[ws-search]").unbind("click").click(this.query).click();
        $("[ws-delete]").unbind("click").click(this.deleteByCode);
        $("[ws-submit]").unbind("click").click(this.submitCustomByCode);

        $table = $("#query-enterManifest-table");
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

    seeEnterPassportDetail: function (bind_typecd,etps_preent_no) {
        if ("3" != bind_typecd){
            var url = "bondedIEnter/seeEnterPassportDetail?type=RQHFD&isEdit=true&etps_preent_no=" + etps_preent_no;
        }else{
            var url = "bondedIEnter/seeEnterPassportDetailYPDC?type=RQHFD&isEdit=true&etps_preent_no=" + etps_preent_no;
        }
        sw.modelPopup(url, "查看核放单详情", false, 1100, 930);
    }

};

