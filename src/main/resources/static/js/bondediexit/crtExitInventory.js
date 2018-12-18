//清单查询
sw.page.modules["bondediexit/crtExitInventory"] = sw.page.modules["bondediexit/crtExitInventory"] || {

    query: function () {
        // 获取查询表单参数
        var returnStatus = $("[name='returnStatus']").val();

        // 拼接URL及参数
        var url = sw.serializeObjectToURL("api/bondediexit/querycrtexitinventory", {
            returnStatus: returnStatus//回执状态
        });

        // 数据表
        sw.datatable("#query-crtExitInventory-table", {
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
                        if (row.return_status == "800") {
                            return '<input type="checkbox" class="submitKey" value="' +
                                row.invt_no + '" />';
                        }
                        else {
                            return "";
                        }
                    }
                },
                {
                    label: "跨境清单编号", render: function (data, type, row) {
                    return '<a href="javascript:void(0)"  onclick="' + "javascript:sw.pageModule('bondediexit/crtExitInventory').seeExitInfo('" + row.guid + "','" + row.order_no + "')" + '">' + row.invt_no + '</a>'
                }
                }
            ]
        });
    },

    // 提交海关
    crtExitInventoryData: function () {
        var submitKeys = "";
        $(".submitKey:checked").each(function () {
            submitKeys += "," + $(this).val();
        });
        if (submitKeys.length > 0) {
            submitKeys = submitKeys.substring(1);
        } else {
            sw.alert("请先勾选要新建的跨境清单信息！");
            return;
        }
        var postData = {
            submitKeys: submitKeys
        };

        sw.pageModule('bondediexit/crtExitInventory').seeExitInventoryDetail(submitKeys);
    },

    seeExitInventoryDetail: function (submitKeys) {
        var url = "bondediexit/seeExitInventoryDetail?type=CQHZQDCX&isEdit=true&mark=crt&submitKeys=" + submitKeys;
        sw.modelPopup(url, "新建出区核注清单信息", false, 1000, 700);
    },

    init: function () {
        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyy-mm-dd",
            autoclose: true
        });
        $("[ws-search]").unbind("click").click(this.query).click();
        $("#crtExitInventory").unbind("click").click(this.crtExitInventoryData);
        $(".btn[ws-search]").click();
        $table = $("#query-crtExitInventory-table");
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

    seeExitInfo: function (guid, order_no) {
        var url = "detailmanage/seeInventoryDetail?type=QDCX&isEdit=false&guid=" + guid + "&orderNo=" + order_no;
        sw.modelPopup(url, "查看清单详情", false, 1100, 930);
    }

};

