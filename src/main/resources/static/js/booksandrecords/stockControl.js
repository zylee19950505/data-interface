//清单查询
sw.page.modules["booksandrecords/stockControl"] = sw.page.modules["booksandrecords/stockControl"] || {

    query: function () {
        // 获取查询表单参数
        var gds_seqno = $("[name='gds_seqno']").val();
        var gds_mtno = $("[name='gds_mtno']").val();
        var gdecd = $("[name='gdecd']").val();
        var gds_nm = $("[name='gds_nm']").val();
        var surplus = $("[name='surplus']").val();

        // 拼接URL及参数
        var url = sw.serializeObjectToURL("api/booksandrecords/stockcontrol", {
            gds_seqno: gds_seqno,//商品序号
            gds_mtno: gds_mtno,//商品料号
            gdecd: gdecd,//商品税号
            gds_nm: gds_nm,//商品名称
            surplus: surplus//剩余库存量
        });

        // 数据表
        sw.datatable("#query-stockcontrol-table", {
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
                    data: "gds_seqno", label: "序号"
                },
                {
                    data: "gds_mtno", label: "料号"
                },
                {
                    data: "gdecd", label: "税号"
                },
                {
                    data: "gds_nm", label: "商品名称"
                },
                // {
                //     data: "dcl_unitcd", label: "申报计量单位"
                // },
                {
                    label: "申报计量单位", render: function (data, type, row) {
                    return row.dcl_unitcd_name + '(' + row.dcl_unitcd + ')';
                }
                },
                {
                    data: "surplus", label: "当前库存数量"
                }
            ]
        });
    },

    init: function () {
        $("[ws-search]").unbind("click").click(this.query).click();
    },

};

