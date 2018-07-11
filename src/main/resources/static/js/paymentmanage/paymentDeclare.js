/**
 * Created by baozhe on 2017-7-23.
 * 舱单申报
 */
sw.page.modules["paymentmanage/paymentDeclare"] = sw.page.modules["paymentmanage/paymentDeclare"] || {
        // 舱单申报列表查询
        query: function () {
            // 获取查询表单参数
            var idCardValidate = $("[name='idCardValidate']").val();
            var flightTimes = $("[name='flightTimes']").val();
            var voyageNo = $("[name='voyageNo']").val();
            var billNo = $("[name='billNo']").val();

            // 拼接URL及参数
            var url = sw.serializeObjectToURL("api/manifestDeclaration", {
                ieFlag: sw.ie,
                entryType: sw.type,
                idCardValidate: idCardValidate,
                voyageNo: voyageNo,
                flightTimes: flightTimes,
                billNo: billNo
            });

            // 数据表
            sw.datatable("#query-paymentDeclare-table", {
                ajax: url,
                lengthMenu: [[200, 500, 1000], [200, 500, 1000]],
                searching: false,
                columns: [
                    {
                        label: '<input type="checkbox" name="cb-check-all"/>',
                        orderable: false,
                        data: null,
                        render: function (data, type, row) {
                            if (row.OP_STATUS == "SWOP21") {
                                return "";
                            }
                            return '<input type="checkbox" class="submitKey" value="'
                                + row.BILL_NO + '" />';
                        }
                    },
                    {data: "BILL_NO", label: "订单编号"},
                    {data: "VOYAGE_NO", label: "电商企业名称"},
                    {data: "FLIGHT_TIMES", label: "电商平台名称"},
                    {data: "STARTINGPOINT", label: "商品名称"},
                    {
                      label:"总价"  ,render: function (data, type, row) {
                        return '<div style="font-weight:bold;color:red;">'+row.ASSCOUNT+'<div>';
                    }
                    },
                    {
                        label: "订购人", render: function (data, type, row) {
                        var gross_wt = parseFloat(row.GROSS_WT);
                        if (isNaN(gross_wt))return 0;
                        return gross_wt.toFixed(2);
                    }
                    },
                    {data: "MANIFEST_STATUS_STR", label: "业务状态"},
                    {data: "DECLARE_RESULT", label: "入库结果"},
                    {data: "DECLARE_RESULT", label: "申报结果"}
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

                sw.ajax("api/manifest/submitCustom", "POST", postData, function (rsp) {
                    if (rsp.data.result == "true") {
                        sw.alert("提交海关成功", "提示", function () {
                        }, "modal-success");
                        $("#submitManifestBtn").prop("disabled", false);
                        sw.page.modules["express/import_b/declaration/manifest_declaration"].query();
                    } else {
                        sw.alert(rsp.data.msg);
                    }
                    $.unblockUI();
                });
            });

            // sw.ajax("api/manifest/submitCustom", "POST", postData, function (rsp) {
            //     if (rsp.data.result == "true") {
            //         sw.alert("提交海关成功", "提示", function () {
            //         }, "modal-success");
            //         $("#submitManifestBtn").prop("disabled", false);
            //         sw.page.modules["express/import_b/declaration/manifest_declaration"].query();
            //     } else {
            //         sw.alert(rsp.data.msg);
            //     }
            //     $.unblockUI();
            // });
        },
        init: function () {
            $(".input-daterange").datepicker({
                language: "zh-CN",
                todayHighlight: true,
                format: "yyyymmdd",
                autoclose: true
            });
            $("[ws-search]").unbind("click").click(this.query);
            $("[ws-submit]").unbind("click").click(this.submitCustom);
            this.query();
            var $table = $("#query-conveyance-table");
            $table.on("change", ":checkbox", function () {
                if ($(this).is("[name='cb-check-all']")) {
                    //全选
                    $(":checkbox", $table).prop("checked", $(this).prop("checked"));
                } else {
                    //一般复选
                    var checkbox = $("tbody :checkbox", $table);
                    $(":checkbox[name='cb-check-all']", $table)
                        .prop('checked', checkbox.length == checkbox.filter(':checked').length);
                }
            });
        }
    };

