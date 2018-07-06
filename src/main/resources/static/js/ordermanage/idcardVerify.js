/**
 * Created by Administrator on 2017/7/20.
 */
sw.page.modules["ordermanage/idcardVerify"] = sw.page.modules["ordermanage/idcardVerify"] || {
    init: function () {
        $("[name='flightTimes']").val("");
        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyymmdd",
            autoclose: true
        });
        $("[ws-search]").unbind("click").click(this.query);
        $(".btn[ws-search]").click();
        $("[ws-submit]").unbind("click").click(this.submitPolice);
        $("[ws-delete]").unbind("click").click(this.deleteVerify);
        $("[ws-refresh]").unbind("click").click(this.refreshVerify);
        $("[ws-convert]").unbind("click").click(this.convertVerify);
        $("[ws-download]").unbind("click").click(this.download);

        $table = $("#query-idCardVerify-table");
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

        // 查询累计校验次数和判断视为设置未校验的按钮
        $("#verify_count").html("0");
        sw.ajax("api/verify/count/", "GET", {}, function (rsp) {
            if (rsp.status == 200) {
                var count = rsp.data.count;
                var validate = rsp.data.isValidate;
                var surplus = rsp.data.surplus;
                var noPassCount = rsp.data.noPassCount;
                $("#verify_count").html(count);
                $("#verify_surplus").html(surplus);
                $("#notPass").html(noPassCount);
                if (validate === "Y") {
                    $("#validateBtn").removeClass("hide")
                } else {
                    $("#validateBtn").addClass("hide")
                }
            }
        });
    },

    // 提交公安部
    submitPolice: function () {
        var submitKeys = "";
        $(".submitKey:checked").each(function () {
            submitKeys += "," + $(this).val();
        });
        if (submitKeys.length > 0) {
            submitKeys = submitKeys.substring(1);
        } else {
            sw.alert("请先勾选要提交公安部校验实名制的运单信息！");
            return;
        }
        var postData = {
            submitKeys: submitKeys
        };
        // 显示Loading图标
        //$("#loadingDiv").removeClass("hidden");
        sw.blockPage();
        sw.ajax("api/verify/idCard", "PUT", postData, function (rsp) {
            sw.pageModule("express/import_b/verify_idcard").query();
            // 移除Loading图标
            //$("#loadingDiv").addClass("hidden");
            $.unblockUI();
        });
    },

    //转化为未校验状态
    convertVerify: function () {
        var submitKeys = "";
        $(".submitKey:checked").each(function () {
            submitKeys += "," + $(this).val();
        });
        if (submitKeys.length > 0) {
            submitKeys = submitKeys.substring(1);
        } else {
            sw.alert("请先勾选要转为未校验状态的运单信息！");
            return;
        }
        var postData = {
            submitKeys: submitKeys
        };
        // 显示Loading图标
        //$("#loadingDiv").removeClass("hidden");
        sw.blockPage();
        sw.ajax("api/verify/idCard/convert", "POST", postData, function (rsp) {
            sw.pageModule("express/import_b/verify_idcard").query();
            // 移除Loading图标
            //$("#loadingDiv").addClass("hidden");
            $.unblockUI();
        });
    },

    deleteVerify: function () {
        var submitKeys = "";
        $(".submitKey:checked").each(function () {
            submitKeys += "," + $(this).val();
        });
        if (submitKeys.length > 0) {
            submitKeys = submitKeys.substring(1);
        } else {
            sw.alert("请先勾选要删除运单信息！");
            return;
        }
        var postData = {
            submitKeys: submitKeys
        };
        sw.confirm("确定删除该运单", "确认", function () {
            sw.ajax("api/verify/deleteIdCard", "POST", postData, function (rsp) {
                sw.pageModule("express/import_b/verify_idcard").query();
            });
        });

    },

    refreshVerify: function () {
        sw.pageModule("express/import_b/verify_idcard").query();
    },

    seeAssBillNoDetail: function (entryheadId) {
        sw.popup("express/import_b/verify_update&id=" + entryheadId, "收件人身份证", false, 480, 310);
    },

    query: function () {

        var url = sw.serializeObjectToURL($("[ws-search]").attr("ws-search"), {
            ieFlag: sw.ie,
            entryType: sw.type,
            enterpriseId: sw.user.enterpriseId,
            flightNo: $("[name='flightNo']").val(),
            flightTimes: $("[name='flightTimes']").val(),
            billNo: $("[name='billNo']").val(),
            idCardStatus: $("[name='idCardStatus']").val()
        });


        // 数据表
        sw.datatable("#query-idCardVerify-table", {
            ajax: sw.resolve("api", url),
            lengthMenu: [[200, 500, 1000], [200, 500, 1000]],
            searching: false,
            // order: [0, 'asc'],
            columns: [
                {
                    label: ' <input type="checkbox" name="cb-check-all"/>',
                    orderable: false,
                    data: null,
                    render: function (data, type, row) {
                        if (row.status == "ING" || row.status == "Y" || row.opStatus == "SWOP12" || row.opStatus == "SWOP13") {
                            return "";
                        }
                        return '<input type="checkbox" class="submitKey" value="' + row.entryheadId + '" />';
                    }
                },
                {data: "billNo", label: "主运单号"},
                {
                    label: "分运单号", render: function (data, type, row) {
                    return '<a href="javascript:void(0)"  onclick="' + "javascript:sw.pageModule('express/import_b/verify_idcard').seeAssBillNoDetail('" + row.entryheadId + "')" + '">' + row.assBillNo + '</a>'
                }
                },
                {data: "mainGname", label: "商品名称"},
                {data: "sendId", label: "证件号"},
                {data: "receiveName", label: "收件人"},
                {data: "voyageNo", label: "航班号"},
                {data: "flightTimes", label: "运输时间"},
                {
                    label: "校验状态", render: function (data, type, row) {
                    var status = row.status;
                    var opStatus = row.opStatus;
                    if (opStatus != "SWOP12" && (typeof(status) == "undefined" || null == status || "" == status)) {
                        return "<span style='color:orange'>待校验<span>";
                    } else if (status == "N") {
                        return "<span style='color:red'>未通过<span>";
                    } else if (status == "ING" || opStatus == "SWOP12") {
                        return "<span style='color:blue'>校验中<span>";
                    } else if (status == "Y") {
                        return "<span style='color:green'>已通过<span>";
                    } else {
                        return "未知";
                    }
                }
                },
                {data: "result", label: "校验信息"}
            ]
        });
    },
    setButton: function () {
        var status = $("[name='idCardStatus']").val();
        if (status === "NO") {
            $("#sumitBtn").prop('disabled', false);
        } else {
            $("#sumitBtn").prop('disabled', true);
        }
    },

    //身份证校验下载
    download: function () {
        sw.ajax("api/verify/downloadVerify", "GET", {
            ieFlag: sw.ie,
            entryType: sw.type,
            enterpriseId: sw.user.enterpriseId,
            flightNo: $("[name='flightNo']").val(),
            flightTimes: $("[name='flightTimes']").val(),
            billNo: $("[name='billNo']").val(),
            idCardStatus: $("[name='idCardStatus']").val()
        }, function (rsp) {
            if (rsp.status == 200) {
                var fileName = rsp.data;
                window.location.href = "/api/downloadFile?fileName=" + fileName;
            }
        });
    }
};