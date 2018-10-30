//清单查询
sw.page.modules["querystatistics/inventoryQuery"] = sw.page.modules["querystatistics/inventoryQuery"] || {

    query: function () {
        // 获取查询表单参数
        var startFlightTimes = $("[name='startFlightTimes']").val();
        var endFlightTimes = $("[name='endFlightTimes']").val();
        var ieFlag = $("[name='ieFlag']").val();
        var entId = $("[name='entId']").val();
        var billNo = $("[name='billNo']").val();
        var invtNo = $("[name='invtNo']").val();
        var gName = $("[name='gName']").val();

        // 拼接URL及参数
        var url = sw.serializeObjectToURL("api/queryStatistics/queryInventory", {
            startFlightTimes: startFlightTimes,//申报开始时间
            endFlightTimes: endFlightTimes,//申报结束时间
            ieFlag: ieFlag,//进出口标识
            entId: entId,//企业ID码
            billNo: billNo,//提运单号
            invtNo: invtNo,//清单编号
            gName: gName//商品名称
        });

        // 数据表
        sw.datatable("#query-inventoryQuery-table", {
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
                    data: "invt_no", label: "海关清单编号"
                },
                {
                    data: "bill_no", label: "提运单号"
                },
                {
                    data: "logistics_no", label: "运单编号"
                },
                {
                    label: "申报日期", render: function (data, type, row) {
                    if (!isEmpty(row.app_time)) {
                        return moment(row.app_time).format("YYYY-MM-DD");
                    }
                    return "";
                }
                },
                {
                    label: "商品总价", render: function (data, type, row) {
                    var total_prices = parseFloat(row.total_prices);
                    if (isNaN(total_prices)) return 0;
                    return total_prices.toFixed(2);
                }
                },
                {
                    label: "税额", render: function (data, type, row) {
                    var value_added_tax = parseFloat(row.value_added_tax);
                    if (isNaN(value_added_tax)) return 0;
                    return value_added_tax.toFixed(2);
                }
                },
                {
                    label: "毛重(KG)", render: function (data, type, row) {
                    var gross_weight = parseFloat(row.gross_weight);
                    if (isNaN(gross_weight)) return 0;
                    return gross_weight.toFixed(2);
                }
                },
                {
                    label: "净重(KG)", render: function (data, type, row) {
                    var net_weight = parseFloat(row.net_weight);
                    if (isNaN(net_weight)) return 0;
                    return net_weight.toFixed(2);
                }
                },
                {
                    data: "buyer_name", label: "订购人"
                },
            ]
        });
    },

    //导出excel
    download: function () {
        var oTable = $('#query-inventoryQuery-table').dataTable();
        var oSettings = oTable.fnSettings();
        var paramJson = {
            startFlightTimes: $("[name='startFlightTimes']").val(),
            endFlightTimes: $("[name='endFlightTimes']").val(),
            ieFlag: $("[name='ieFlag']").val(),
            entId: $("[name='entId']").val(),
            billNo: $("[name='billNo']").val(),
            invtNo: $("[name='invtNo']").val(),
            gName: $("[name='gName']").val(),
            startStr: oSettings._iDisplayStart,
            length: oSettings._iDisplayLength
        };
        sw.ajax("api/queryStatistics/load", "GET", paramJson, function (rsp) {
            if (rsp.status == 200) {
                var fileName = rsp.data;
                window.location.href = "/api/queryStatistics/query/downloadFile?fileName=" + fileName;
            }
        })
    },

    EbusinessEnt:function () {
        sw.ajax("api/queryStatistics/EbusinessEnt","GET","",function(rsp){
            var result = rsp.data;
            for(var idx in result){
                var id = result[idx].id;
                var name = result[idx].ent_name;
                var option = $("<option>").text(name).val(id);
                $("#entId").append(option);
            }
        });
    },

    init: function () {
        $("[name='startFlightTimes']").val(moment(new Date()).date(1).format("YYYYMMDD"));
        $("[name='endFlightTimes']").val(moment(new Date()).format("YYYYMMDD"));
        this.EbusinessEnt();
        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyymmdd",
            autoclose: true
        });
        $("[ws-search]").unbind("click").click(this.query);
        $("[ws-download]").unbind("click").click(this.download);
        $(".btn[ws-search]").click();
    },

};
