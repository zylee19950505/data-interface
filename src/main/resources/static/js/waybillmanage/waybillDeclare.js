/**
 * Created by baozhe on 2017-7-23.
 * 舱单申报
 */
sw.page.modules["waybillmanage/waybillDeclare"] = sw.page.modules["waybillmanage/waybillDeclare"] || {
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
                startFlightTimes: $("[name='startFlightTimes']").val(),
                endFlightTimes: $("[name='endFlightTimes']").val(),
                idCardValidate: idCardValidate,
                voyageNo: voyageNo,
                flightTimes: flightTimes,
                billNo: billNo
            });

            // 数据表
            sw.datatable("#query-waybillDeclare-table", {
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
                    {
                        label: "物流运单编号", render: function (data, type, row) {
                        if (sw.billNo == row.billNo) return "";
                        return row.flightTimes;
                    }
                    },
                    {
                        label: "物流企业名称", render: function (data, type, row) {
                        if (sw.billNo == row.billNo) return "";
                        return row.flightNo;
                    }
                    },
                    {
                        label: "收货人姓名", render: function (data, type, row) {
                        if (sw.billNo == row.billNo) return "";
                        var startingPointName = sw.dict.port[row.startingPoint];
                        if (typeof(startingPointName) == "undefined" || null == startingPointName || "" == startingPointName) return row.startingPoint;
                        return startingPointName;
                    }
                    },
                    {data: "assBillCount", label: "收货人电话"},
                    /*    {data: "packNo", label: "件数"},*/
                    {
                        label: "收货地址", render: function (data, type, row) {
                        var taxEstimate = parseFloat(row.taxEstimate);
                        if (isNaN(taxEstimate)) return 0;
                        taxEstimate = parseFloat(taxEstimate.toFixed(4));
                        return taxEstimate.toFixed(2);
                    }
                    },
                    {data: "realNcad", label: "业务状态"},
                    {
                        label: "入库结果", render: function (data, type, row) {
                        var grossWt = parseFloat(row.grossWt);
                        if (isNaN(grossWt)) return 0;
                        return grossWt.toFixed(2);
                    }
                    },
                    {
                        label: "申报日期", render: function (data, type, row) {
                        var grossWt = parseFloat(row.grossWt);
                        if (isNaN(grossWt)) return 0;
                        return grossWt.toFixed(2);
                    }
                    },
                    {
                        label: "物流状态时间", render: function (data, type, row) {
                        var grossWt = parseFloat(row.grossWt);
                        if (isNaN(grossWt)) return 0;
                        return grossWt.toFixed(2);
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

            $("[name='startFlightTimes']").val(moment(new Date()).format("YYYYMMDD"));
            $("[name='endFlightTimes']").val(moment(new Date()).format("YYYYMMDD"));
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

