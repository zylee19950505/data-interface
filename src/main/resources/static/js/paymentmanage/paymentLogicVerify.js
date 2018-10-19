/**
 * 逻辑校验
 * Created by zwj on 2017/7/20.
 */
sw.page.modules["paymentmanage/paymentLogicVerify"] = sw.page.modules["paymentmanage/paymentLogicVerify"] || {
        //查询
        query: function () {
            var url = sw.serializeObjectToURL($("[ws-search]").attr("ws-search"), {
                entryType: sw.type,
                ieFlag: sw.ie,
                flightNo: $("[name='flightNo']").val(),
                flightTimes: $("[name='flightTimes']").val(),
                billNo: $("[name='billNo']").val()
            });
            sw.billNo = "";
            // 数据表
            sw.datatable("#query-idCardVerify-table", {
                ajax: sw.resolve("api", url),
                lengthMenu: [[200, 500, 1000], [200, 500, 1000]],
                searching: false,
                columns: [
                    {
                        label: ' <input type="checkbox" name="cb-check-all"/>',
                        orderable: false,
                        data: null,
                        render: function (data, type, row) {
                            if (sw.billNo === row.billNo)return "";
                            if (row.status == "ING") {
                                return "";
                            }
                            return '<input type="checkbox" class="submitKey" value="' + row.billNo + '" />';
                        }
                    },
                    {
                        label: "航班号", render: function (data, type, row) {
                        if (sw.billNo === row.billNo) return "";
                        return row.voyageNo;
                    }
                    },
                    {
                        label: "运输时间", render: function (data, type, row) {
                        if (sw.billNo === row.billNo) return "";
                        return row.flightTimes;

                    }
                    },
                    {
                        label: "主运单号", render: function (data, type, row) {
                        if (sw.billNo === row.billNo) return "";
                        sw.billNo = row.billNo;
                        return row.billNo;
                    }
                    },
                    {
                        label: "校验状态", render: function (data, type, row) {
                        var state = "通过";
                        if (row.status === "N") {
                            state = "未通过";
                        }
                        if (row.status === "ING" || row.status === null)return state = "校验中";
                        return '<a href="javascript:void(0)"  onclick="' + "javascript:sw.pageModule('express/import_b/logical_inspection').preview('" + row.billNo + "', '" + row.status + "')" + '">' + state + '</a> ';
                    }
                    },
                    {data: "assBillCount", label: "分单数"},
                    {
                        label: "预估税额", render: function (data, type, row) {
                        var taxEstimate = parseFloat(row.taxEstimate);
                        return taxEstimate.toFixed(2);
                    }
                    },
                    {
                        label: "重量", render: function (data, type, row) {
                        var grossWt = parseFloat(row.grossWt);
                        return grossWt.toFixed(2);
                    }
                    },
                    {
                        label: "操作", render: function (data, type, row) {
                        if (row.status === "ING" || row.status === null)return "";
                        return item = '<button class="btn btn-sm btn-primary" title="下载" ' +
                            'onclick="' + "javascript:sw.page.modules['express/import_b/logical_inspection'].download('" + row.billNo + "', '" + row.status + "')" + '">' +
                            '<i class="fa fa-download"></i> </button> ';
                    }
                    }
                ]
            });
        },
        //预览
        preview: function (billNo, status) {
            $("#logicalId").hide();
            $("#preview").show();
            this.loadPreview(billNo, status)
        },
        //逻辑校验预览数据加载
        loadPreview: function (billNo, status) {
            var url = sw.serializeObjectToURL("/bill/logical", {
                ieFlag: sw.ie,
                entryType: sw.type,
                enterpriseId: sw.user.enterpriseId,
                flightNo: $("[name='flightNo']").val(),
                flightTimes: $("[name='flightTimes']").val(),
                billNo: billNo,
                logicStatus: status
            });

            // 数据表
            sw.datatable("#query-idCardVerify-table2", {
                ajax: sw.resolve("api", url),
                lengthMenu: [[200, 500, 1000], [200, 500, 1000]],
                searching: false,
                columns: [
                    {
                        label: ' <input type="checkbox" name="cb-check-all"/>',
                        orderable: false,
                        data: null,
                        render: function (data, type, row) {
                            if (row.status == "ING" || row.status == "Y") {
                                return "";
                            }
                            return '<input type="checkbox" class="submitKey" value="' + row.entryheadId + '" />';
                        }
                    },
                    {data: "voyageNo", label: "航班号"},
                    {data: "flightTimes", label: "运输时间"},
                    {data: "billNo", label: "主运单号"},
                    {
                        label: "分运单号", render: function (data, type, row) {
                        if (row.status === "N") return '<a href="javascript:void(0)"  onclick="' + "javascript:sw.pageModule('express/import_b/logical_inspection').seeAssBillNoDetail('" + row.entryheadId + "','" + row.billNo + "')" + '">' + row.assBillNo + '</a>'
                        return row.assBillNo;
                    }
                    },
                    {data: "mainGname", label: "商品名称"},
                    {data: "packNo", label: "件数"},
                    {
                        label: "商品总价", render: function (data, type, row) {
                        var totalValue = parseFloat(row.totalValue);
                        return totalValue.toFixed(2);
                    }
                    },
                    {
                        label: "重量", render: function (data, type, row) {
                        var grossWt = parseFloat(row.grossWt);
                        return grossWt.toFixed(2);
                    }
                    },
                    {
                        label: "预估税额", render: function (data, type, row) {
                        var taxEstimate = parseFloat(row.taxEstimate);
                        return taxEstimate.toFixed(2);
                    }
                    },
                    {
                        label: "币制", render: function (data, type, row) {
                        if (row.currCodeName) {
                            return row.currCodeName;
                        }
                        return row.currCode;
                    }
                    },
                    {
                        label: "错误原因",
                        render: function (data, type, row) {
                            var message = "";
                            if (row.status === "N") {
                                // message = JSON.parse(row.result).message;
                                message = "<span class='text-red'>" + JSON.parse(row.result).message + "</span>";
                            }
                            return message;
                        }
                    }
                ]
            });
        },

        //预览编辑界面
        seeAssBillNoDetail: function (entryheadId, billNo) {
            var url = "detail/entry_b_detail?type=LJJY&isEdit=true&id=" + entryheadId + "&billNo=" + billNo;
            sw.modelPopup(url, "查看分单详情", false, 1000, 930);
        },

        //删除运单信息
        deleteVerify: function () {
            var submitKey = [];
            $(".submitKey:checked").each(function () {
                submitKey.push($(this).val())
            });
            if (submitKey.length <= 0) {
                sw.alert("请先勾选要删除运单信息！");
                return;
            }
            var postData = {
                submitKey: submitKey
            };
            sw.confirm("确定删除该运单", "确认", function () {
                sw.ajax("api/bill/logical/merge", "POST", postData, function (rsp) {
                    sw.pageModule("express/import_b/logical_inspection").query();
                });
            });
        },

        //计算预估税额
        taxCalculate: function () {
            var submitKey = [];
            $(".submitKey:checked").each(function () {
                submitKey.push($(this).val())
            });
            if (submitKey.length <= 0) {
                sw.alert("请先勾选要计算预估税额的运单信息！");
                return;
            }
            var postData = {
                submitKey: submitKey
            };
            sw.ajax("api/bill/logical/taxEstimate", "POST", postData, function (rsp) {
                if (rsp.status === 200) {
                    sw.alert(rsp.data, "提示", "", "modal-info");
                    sw.pageModule("express/import_b/logical_inspection").query();
                }
            });
        },

        //预估税额下载
        download: function (billNo, status) {
            sw.ajax("api/bill/logical/taxDownload", "GET", {
                enterpriseId: sw.user.enterpriseId,
                entryType: sw.type,
                ieFlag: sw.ie,
                flightNo: $("[name='flightNo']").val(),
                flightTimes: $("[name='flightTimes']").val(),
                billNo: billNo,
                logicStatus: status
            }, function (rsp) {
                if (rsp.status == 200) {
                    var fileName = rsp.data;
                    window.location.href = "/api/downloadFile?fileName=" + fileName;
                }
            });
        },

        //返回
        back: function () {
            $("#logicalId").show();
            $("#preview").hide();
            sw.pageModule("express/import_b/logical_inspection").query();
        },

        //初始化
        init: function () {
            /*   $("#taxBtn").prop('disabled', true);*/
            $("[name='flightTimes']").val("");
            $(".input-daterange").datepicker({
                language: "zh-CN",
                todayHighlight: true,
                format: "yyyymmdd",
                autoclose: true
            });

            $("[ws-search]").unbind("click").click(this.query);
            $(".btn[ws-search]").click();
            $("[ws-delete]").unbind("click").click(this.deleteVerify);
            $("[ws-tax]").unbind("click").click(this.taxCalculate);
            $("[ws-download]").unbind("click").click(this.download);
            $("[ws-back]").unbind("click").click(this.back);

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
        }
    };