//清单查询
sw.page.modules["bondediexit/crtExitManifest"] = sw.page.modules["bondedIExit/crtExitManifest"] || {

    query: function () {
        debugger;
        // 获取查询表单参数
        var bondInvtNo = $("[name='bondInvtNo']").val();
        var returnStatus = $("[name='returnStatus']").val();

        // 拼接URL及参数
        var url = sw.serializeObjectToURL("api/bondediexit/queryCrtExitManifest", {
            bondInvtNo: bondInvtNo,//海关清单编号
            returnStatus: returnStatus//回执状态
        });

        // 数据表
        sw.datatable("#query-crtExitManifest-table", {
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
            lengthMenu: [[50, 100, 1000], [50, 100, 1000]],
            searching: false,//开启本地搜索
            columns: [
                {
                    label: '<input type="checkbox" name="cb-check-all"/>',
                    orderable: false,
                    data: null,
                    render: function (data, type, row) {
                        if (row.status == "BDDS22") {
                            return '<input type="checkbox" class="submitKey" value="' +
                                row.id + '" />';
                        }
                        else {
                            return "";
                        }
                    }
                },
                {
                    data: "bond_invt_no", label: "核注清单编号"
                }
            ]
        });
    },

    crtExitManifestData: function () {
        var submitKeys = "";
        $(".submitKey:checked").each(function () {
            submitKeys += "," + $(this).val();
        });
        if (submitKeys.length > 0) {
            submitKeys = submitKeys.substring(1);
        } else {
            sw.alert("请先勾选要新建出区核放单信息！");
            return;
        }
        var postData = {
            submitKeys: submitKeys
        };

        sw.pageModule('bondediexit/crtExitManifest').seeExitManifestDetail(submitKeys);
    },

    seeExitManifestDetail: function (submitKeys) {
        var url = "bondediexit/seeExitManifestDetail?type=CQHFDCJ&isEdit=true&mark=crt&submitKeys=" + submitKeys;
        sw.modelPopup(url, "新建出区核放单信息", false, 1000, 700);
    },

    init: function () {
        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyy-mm-dd",
            autoclose: true
        });
        $("[ws-search]").unbind("click").click(this.query).click();

        $table = $("#query-crtExitManifest-table");
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

    }

    // seeInventoryDetail: function (guid, order_no, return_status) {
    //     var url = "detailmanage/seeInventoryDetail?type=QDCX&isEdit=false&guid=" + guid + "&orderNo=" + order_no;
    //     sw.modelPopup(url, "查看清单详情", false, 1100, 930);
    // }

};

