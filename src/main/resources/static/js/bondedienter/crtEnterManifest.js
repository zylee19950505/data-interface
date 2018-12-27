//新建入区核注清单
sw.page.modules["bondedienter/crtEnterManifest"] = sw.page.modules["bondedienter/crtEnterManifest"] || {
    query: function () {
        // 获取查询表单参数
        var dataStatus = $("[name='dataStatus']").val();
        var recordDataStatus = $("[name='recordDataStatus']").val();
        var invtNo = $("[name='invtNo']").val();

        // 拼接URL及参数
        var url = sw.serializeObjectToURL("api/crtEnterManifest/queryCrtEnterManifest", {
            dataStatus: dataStatus,
            recordDataStatus: recordDataStatus,
            invtNo: invtNo//核注清单编号
        });

        // 数据表
        var table = sw.datatable("#query-crtEnterManifest-table", {
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
                        if (row.status == "BDDS1") {
                            return '<input type="checkbox" class="submitKey" value="' +
                                row.etps_inner_invt_no + '" />';
                        }
                        else {
                            return "";
                        }
                    }
                },
                /*{
                    label: "企业内部清单编码", render: function (data, type, row) {
                        return '<a href="javascript:void(0)"  onclick="' + "javascript:sw.pageModule('bondedienter/enterInventory').seeEnterInventoryInfo('" + row.etps_inner_invt_no + "')" + '">' + row.etps_inner_invt_no + '</a>'
                    }
                },*/
                {
                    data: "etps_inner_invt_no", label: "核注清单编号"
                }
            ]
        });
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


