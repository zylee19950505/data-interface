/**
 * Created by on 2017-7-23.
 * 运单申报
 */
var BILLNO = "";
var TOTALCOUNT = "";
var APPTIME = "";
sw.page.modules["waybillmanage/waybillDeclare"] = sw.page.modules["waybillmanage/waybillDeclare"] || {
    // 申报列表查询
    query: function () {
        // 获取查询表单参数
        var startFlightTimes = $("[name='startFlightTimes']").val();
        var endFlightTimes = $("[name='endFlightTimes']").val();
        var dataStatus = $("[name='dataStatus']").val();
        var statusDataStatus = $("[name='statusDataStatus']").val();
        var billNo = $("[name='billNo']").val();

        // 拼接URL及参数
        var url = sw.serializeObjectToURL("api/waybillManage/queryWaybillDeclare", {
            startFlightTimes: startFlightTimes,
            endFlightTimes: endFlightTimes,
            dataStatus: dataStatus,
            statusDataStatus: statusDataStatus,
            billNo: billNo
        });

        sw.datatable("#query-waybillDeclare-table", {
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
                        BILLNO = "";

                    },
                    error: function (xhr, status, error) {
                        sw.showErrorMessage(xhr, status, error);
                    }
                });
            },
            // lengthMenu: [[50, 100, 1000], [50, 100, 1000]],
            searching: false,//开启本地搜索
            paging: false,
            info: false,
            columns: [
                {
                    label: '<input type="checkbox" name="cb-check-all"/>',
                    orderable: false,
                    data: null,
                    render: function (data, type, row) {
                        //运单状态已申报(此界面不显示此条信息)
                        if (row.data_status == "CBDS4") {
                            return '<input type="checkbox" class="submitKey" value="' +
                                row.bill_no + '" />';
                        }
                        else if (row.data_status == "CBDS5") {
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
                    if (BILLNO == row.bill_no) {
                        return "";
                    }
                    BILLNO = row.bill_no;
                    TOTALCOUNT = "";
                    APPTIME = "";
                    return BILLNO;
                }
                },
                {
                    label: "申报日期", render: function (data, type, row) {
                    if (!isEmpty(row.appTime)) {
                        if (APPTIME == row.appTime) {
                            return "";
                        }
                        APPTIME = row.appTime;
                        return moment(APPTIME).format("YYYY-MM-DD HH:mm:ss");
                    }
                    return "";
                }
                },
                {
                    label: "运单总数", render: function (data, type, row) {
                    if (TOTALCOUNT == row.totalCount) {
                        return "";
                    }
                    TOTALCOUNT = row.totalCount;
                    return TOTALCOUNT;
                }
                },
                {
                    label: "运单业务状态", render: function (data, type, row) {
                    var value = "";
                    var textColor = "";
                    switch (row.data_status) {
                        case "CBDS1":
                            value = "校验未通过";
                            textColor = "text-red";
                            break;
                        case "CBDS4":
                            value = "运单待申报";
                            textColor = "text-yellow";
                            break;
                        case "CBDS40":
                            value = "运单申报中";
                            textColor = "text-green";
                            break;
                        case "CBDS41":
                            value = "运单已申报";
                            textColor = "text-green";
                            break;
                        case "CBDS42":
                            value = "运单申报成功";
                            textColor = "text-green";
                            break;
                        case "CBDS43":
                            value = "运单重报";
                            textColor = "text-yellow";
                            break;
                        case "CBDS44":
                            value = "运单申报失败";
                            textColor = "text-red";
                            break;
                    }
                    var result = "<span class=" + textColor + ">" + value + "</span>";
                    return result
                }
                },
                {
                    label: "数量", render: function (data, type, row) {
                    if (row.count1 == "0") {
                        return ""
                    }
                    return row.count1
                }
                },
                {
                    label: "运单状态业务状态", render: function (data, type, row) {
                    var value = "";
                    var textColor = "";
                    switch (row.sta_data_status) {
                        case "CBDS5":
                            value = "运单状态待申报";
                            textColor = "text-yellow";
                            break;
                        case "CBDS50":
                            value = "运单状态申报中";
                            textColor = "text-yellow";
                            break;
                        case "CBDS51":
                            value = "运单状态已申报";
                            textColor = "text-green";
                            break;
                        case "CBDS52":
                            value = "运单状态申报成功";
                            textColor = "text-green";
                            break;
                        case "CBDS53":
                            value = "运单状态重报";
                            textColor = "text-red";
                            break;
                        case "CBDS54":
                            value = "运单状态申报失败";
                            textColor = "text-red";
                            break;
                    }
                    var result = "<span class=" + textColor + ">" + value + "</span>";
                    return result
                }
                },
                {
                    label: "数量", render: function (data, type, row) {
                    if (row.count2 == "0") {
                        return ""
                    }
                    return row.count2
                }
                }
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
            sw.alert("请先勾选要提交海关的舱单信息！");
            return;
        }

        sw.confirm("请确认运单总数无误，提交海关", "确认", function () {
            var idCardValidate = $("[name='idCardValidate']").val();
            sw.blockPage();
            var postData = {
                submitKeys: submitKeys,
                idCardValidate: idCardValidate,
                ieFlag: sw.ie,
                entryType: sw.type
            };
            $("#submitManifestBtn").prop("disabled", true);
            sw.ajax("api/waybillManage/submitCustom", "POST", postData, function (rsp) {
                if (rsp.data.result == "true") {
                    sw.alert("提交海关成功", "提示", function () {
                    }, "modal-success");
                    $("#submitManifestBtn").prop("disabled", false);
                    sw.page.modules["waybillmanage/waybillDeclare"].query();
                } else {
                    sw.alert(rsp.data.msg);
                }
                $.unblockUI();
            });
        });
    },
    // 状态申报提交海关
    submitCustomToStatus: function () {

        var submitKeys = "";
        $(".submitKey:checked").each(function () {
            submitKeys += "," + $(this).val();
        });
        if (submitKeys.length > 0) {
            submitKeys = submitKeys.substring(1);
        } else {
            sw.alert("请先勾选要提交海关的舱单信息！");
            return;
        }

        sw.confirm("请确认运单总数无误，提交海关", "确认", function () {
            var idCardValidate = $("[name='idCardValidate']").val();
            sw.blockPage();
            var postData = {
                submitKeys: submitKeys,
                idCardValidate: idCardValidate,
                ieFlag: sw.ie,
                entryType: sw.type
            };
            $("#submitManifestBtn").prop("disabled", true);

            sw.ajax("api/waybillManage/submitCustomToStatus", "POST", postData, function (rsp) {
                if (rsp.data.result == "true") {
                    sw.alert("提交海关成功", "提示", function () {
                    }, "modal-success");
                    $("#submitManifestBtn").prop("disabled", false);
                    sw.page.modules["waybillmanage/waybillDeclare"].query();
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
        $("[ws-search]").unbind("click").click(this.query);
        $("#submitWaybillBtn").unbind("click").click(this.submitCustom);
        $("#submitStatusBtn").unbind("click").click(this.submitCustomToStatus);
        this.query();
        var $table = $("#query-waybillDeclare-table");
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
    }


};

