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
                        return moment(row.ie_date).format("YYYY-MM-DD");
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
        $("[ws-delete]").unbind("click").click(this.deleteVerify);
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

    deleteVerify: function () {
        var submitKeys = "";
        $(".submitKey:checked").each(function () {
            submitKeys += "," + $(this).val();
        });
        if (submitKeys.length > 0) {
            submitKeys = submitKeys.substring(1);
        } else {
            sw.alert("请先勾选要删除清单信息！");
            return;
        }
        var postData = {
            submitKeys: submitKeys
        };
        sw.confirm("确定删除该清单", "确认", function () {
            sw.ajax("api/inventory/deleteLogical", "POST", postData, function (rsp) {
                sw.pageModule("detailmanage/InventoryLogicVerify").query();
            });
        });
    },

    seeInventoryLogicDetail: function (guid, order_no) {
        var url = "detailmanage/seeInventoryDetail?type=LJJY&isEdit=true&guid=" + guid + "&orderNo=" + order_no;
        sw.modelPopup(url, "查看清单详情", false, 1100, 930);
    }

};