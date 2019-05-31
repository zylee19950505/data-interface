/**
 * 逻辑校验
 */
sw.page.modules["bondordermanage/bondOrderLogVerify"] = sw.page.modules["bondordermanage/bondOrderLogVerify"] || {
    //查询
    query: function () {
        var url = sw.serializeObjectToURL($("[ws-search]").attr("ws-search"), {
            bill_no: $("[name='bill_no']").val(),
            order_no: $("[name='order_no']").val(),
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
                    label: "商品批次号", render: function (data, type, row) {
                    return row.bill_no;
                }
                },
                {
                    label: "订单编号", render: function (data, type, row) {
                    return '<a href="javascript:void(0)"  onclick="' + "javascript:sw.pageModule('bondordermanage/bondOrderLogVerify').seeBondOrderLogicDetail('" + row.guid + "','" + row.order_no + "')" + '">' + row.order_no + '</a>'
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
            sw.alert("请先勾选要删除订单信息！");
            return;
        }
        var postData = {
            submitKeys: submitKeys
        };
        sw.confirm("确定删除该订单", "确认", function () {
            sw.ajax("api/bondorder/deleteLogical", "POST", postData, function (rsp) {
                sw.pageModule("bondordermanage/bondOrderLogVerify").query();
            });
        });
    },

    seeBondOrderLogicDetail: function (guid, order_no) {
        var url = "bondordermanage/seeBondOrderDetail?type=LJJY&isEdit=true&guid=" + guid + "&orderNo=" + order_no;
        sw.modelPopup(url, "查看保税订单详情", false, 1000, 930);
    }


};