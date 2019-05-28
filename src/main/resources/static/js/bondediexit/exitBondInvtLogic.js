/**
 * 逻辑校验
 */
sw.page.modules["bondediexit/exitBondInvtLogic"] = sw.page.modules["bondediexit/exitBondInvtLogic"] || {
    //查询
    query: function () {
        var url = sw.serializeObjectToURL($("[ws-search]").attr("ws-search"), {
            etps_inner_invt_no: $("[name='etps_inner_invt_no']").val(),
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
                        return '<input type="checkbox" class="submitKey" value="' + row.etps_inner_invt_no + '" />';
                    }
                },
                {
                    label: "核注清单内部编码", render: function (data, type, row) {
                    return '<a href="javascript:void(0)"  onclick="' + "javascript:sw.pageModule('bondediexit/exitBondInvtLogic').seeExitBondInvtLogicDetail('" + row.id + "','" + row.etps_inner_invt_no + "')" + '">' + row.etps_inner_invt_no + '</a>'
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
            sw.alert("请先勾选要删除的出区核注清单信息！");
            return;
        }
        var postData = {
            submitKeys: submitKeys
        };
        sw.confirm("确定删除该出区核注清单", "确认", function () {
            sw.ajax("api/exitbondinvt/deletelogicdata", "POST", postData, function (rsp) {
                sw.pageModule("bondediexit/exitBondInvtLogic").query();
            });
        });
    },

    seeExitBondInvtLogicDetail: function (id, etpsInnerInvtNo) {
        var url = "bondediexit/seeExitInventoryDetail?type=LJJY&isEdit=true&mark=upd&submitKeys=" + etpsInnerInvtNo;
        sw.modelPopup(url, "查看出区核注清单详情", false, 1100, 930);
    }


};