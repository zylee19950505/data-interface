/**
 * 逻辑校验
 * Created by zwj on 2017/7/20.
 */
sw.page.modules["detailmanage/InventoryLogicVerify"] = sw.page.modules["detailmanage/InventoryLogicVerify"] || {
    //查询
    query: function () {
        var url = sw.serializeObjectToURL($("[ws-search]").attr("ws-search"), {
            bill_no: $("[name='bill_no']").val(),
            order_no: $("[name='order_no']").val(),
            voyage_no: $("[name='voyage_no']").val(),
            ie_date: $("[name='ie_date']").val(),
            status: "N"

        });
        // 数据表
        sw.datatable("#query-logic-table", {
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
                        return '<input type="checkbox" class="submitKey" value="' + row.guid + '" />';
                    }
                },
                {
                    label: "主运单号", render: function (data, type, row) {
                    return row.bill_no;
                }
                },
                {
                    label: "订单编号", render: function (data, type, row) {
                    return '<a href="javascript:void(0)"  onclick="' + "javascript:sw.pageModule('detailmanage/InventoryLogicVerify').seeInventoryLogicDetail('" + row.guid + "','" + row.order_no + "')" + '">' + row.order_no + '</a>'
                }
                },
                {
                    label: "航班航次号", render: function (data, type, row) {
                    return row.voyage_no;
                }
                },
                {
                    label: "进口时间", render: function (data, type, row) {
                    if (!isEmpty(row.ie_date)) {
                        return moment(row.ie_date).format("YYYY-MM-DD HH:mm:ss");
                    }
                    return "";
                }
                },
                {
                    label: "错误原因",
                    render: function (data, type, row) {
                        var message = "<span class='text-red'>" + JSON.parse(row.vs_result).message + "</span>";
                        return message;
                    }
                }
            ]
        });
    },

    //初始化
    init: function () {
        $("[name='ie_date']").val("");
        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyymmdd",
            autoclose: true
        });

        $("[ws-search]").unbind("click").click(this.query);
        $(".btn[ws-search]").click();
        // $("[ws-delete]").unbind("click").click(this.deleteVerify);
        // $("[ws-back]").unbind("click").click(this.back);

        $table = $("#query-logic-table");
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
    },

    seeInventoryLogicDetail: function (guid, order_no) {
        var url = "detailmanage/seeInventoryDetail?type=LJJY&isEdit=true&guid=" + guid + "&orderNo=" + order_no;
        sw.modelPopup(url, "查看清单详情", false, 1000, 930);
    }


    // //预览
    // preview: function (billNo, status) {
    //     $("#logicalId").hide();
    //     $("#preview").show();
    //     this.loadPreview(billNo, status)
    // },
    // //逻辑校验预览数据加载
    // loadPreview: function (billNo, status) {
    //     var url = sw.serializeObjectToURL("/bill/logical", {
    //         ieFlag: sw.ie,
    //         entryType: sw.type,
    //         enterpriseId: sw.user.enterpriseId,
    //         flightNo: $("[name='flightNo']").val(),
    //         flightTimes: $("[name='flightTimes']").val(),
    //         billNo: billNo,
    //         logicStatus: status
    //     });
    //
    //     // 数据表
    //     sw.datatable("#query-idCardVerify-table2", {
    //         ajax: sw.resolve("api", url),
    //         lengthMenu: [[200, 500, 1000], [200, 500, 1000]],
    //         searching: false,
    //         columns: [
    //             {
    //                 label: ' <input type="checkbox" name="cb-check-all"/>',
    //                 orderable: false,
    //                 data: null,
    //                 render: function (data, type, row) {
    //                     if (row.status == "ING" || row.status == "Y") {
    //                         return "";
    //                     }
    //                     return '<input type="checkbox" class="submitKey" value="' + row.entryheadId + '" />';
    //                 }
    //             },
    //             {data: "voyageNo", label: "航班号"},
    //             {data: "flightTimes", label: "运输时间"},
    //             {data: "billNo", label: "主运单号"},
    //             {
    //                 label: "分运单号", render: function (data, type, row) {
    //                 if (row.status === "N") return '<a href="javascript:void(0)"  onclick="' + "javascript:sw.pageModule('express/import_b/logical_inspection').seeAssBillNoDetail('" + row.entryheadId + "','" + row.billNo + "')" + '">' + row.assBillNo + '</a>'
    //                 return row.assBillNo;
    //             }
    //             },
    //             {data: "mainGname", label: "商品名称"},
    //             {data: "packNo", label: "件数"},
    //             {
    //                 label: "商品总价", render: function (data, type, row) {
    //                 var totalValue = parseFloat(row.totalValue);
    //                 return totalValue.toFixed(2);
    //             }
    //             },
    //             {
    //                 label: "重量", render: function (data, type, row) {
    //                 var grossWt = parseFloat(row.grossWt);
    //                 return grossWt.toFixed(2);
    //             }
    //             },
    //             {
    //                 label: "预估税额", render: function (data, type, row) {
    //                 var taxEstimate = parseFloat(row.taxEstimate);
    //                 return taxEstimate.toFixed(2);
    //             }
    //             },
    //             {
    //                 label: "币制", render: function (data, type, row) {
    //                 if (row.currCodeName) {
    //                     return row.currCodeName;
    //                 }
    //                 return row.currCode;
    //             }
    //             },
    //             {
    //                 label: "错误原因",
    //                 render: function (data, type, row) {
    //                     var message = "";
    //                     if (row.status === "N") {
    //                         // message = JSON.parse(row.result).message;
    //                         message = "<span class='text-red'>" + JSON.parse(row.result).message + "</span>";
    //                     }
    //                     return message;
    //                 }
    //             }
    //         ]
    //     });
    // },
    //
    // //删除运单信息
    // deleteVerify: function () {
    //     var submitKey = [];
    //     $(".submitKey:checked").each(function () {
    //         submitKey.push($(this).val())
    //     });
    //     if (submitKey.length <= 0) {
    //         sw.alert("请先勾选要删除运单信息！");
    //         return;
    //     }
    //     var postData = {
    //         submitKey: submitKey
    //     };
    //     sw.confirm("确定删除该运单", "确认", function () {
    //         sw.ajax("api/bill/logical/merge", "POST", postData, function (rsp) {
    //             sw.pageModule("express/import_b/logical_inspection").query();
    //         });
    //     });
    // },
    //
    // //预览编辑界面
    // seeAssBillNoDetail: function (entryheadId, billNo) {
    //     var url = "detail/entry_b_detail?type=LJJY&isEdit=true&id=" + entryheadId + "&billNo=" + billNo;
    //     sw.modelPopup(url, "查看分单详情", false, 1000, 930);
    // },
    //
    // //返回
    // back: function () {
    //     $("#logicalId").show();
    //     $("#preview").hide();
    //     sw.pageModule("express/import_b/logical_inspection").query();
    // }

};