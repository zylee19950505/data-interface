//清单查询
sw.page.modules["bondediexit/crtExitManifest"] = sw.page.modules["bondediexit/crtExitManifest"] || {

    query: function () {
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
                    label: "核注清单编号", render: function (data, type, row) {
                    return '<a href="javascript:void(0)"  onclick="' + "javascript:sw.pageModule('bondediexit/crtExitManifest').seeExitInventory('" + row.etps_inner_invt_no + "')" + '">' + row.bond_invt_no + '</a>'
                }
                },
                {
                    label: "操作", render: function (data, type, row) {
                    return item = '<button class="btn btn-sm btn-info" title="新建出区核放单" id="crtExitManifest" ' +
                        'onclick="' + "javascript:sw.page.modules['bondediexit/crtExitManifest'].crtExitManifest('" + row.bond_invt_no + "')" + '">' +
                        '<i class="fa fa-edit">新建出区核放单</i> </button> ';
                }
                }
            ]
        });
    },

    seeExitInventory: function (etpsInnerInvtNo) {
        var url = "bondediexit/seeExitInventoryDetail?type=CQHZQDXG&isEdit=false&mark=upd&submitKeys=" + etpsInnerInvtNo;
        sw.modelPopup(url, "查看出区核注清单详情", false, 1000, 930);
    },

    crtExitManifest: function (submitKeys) {
        var url = "bondediexit/seeExitManifestDetail?type=CQHFDCJ&isEdit=true&mark=crt&submitKeys=" + submitKeys;
        sw.modelPopup(url, "新建出区核放单", false, 1000, 600);
    },

    init: function () {
        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyy-mm-dd",
            autoclose: true
        });
        $("[ws-search]").unbind("click").click(this.query).click();


    }


};

