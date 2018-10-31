//清单查询
sw.page.modules["querystatistics/customsStatistics"] = sw.page.modules["querystatistics/customsStatistics"] || {

    query: function () {
        // 获取查询表单参数
        var startFlightTimes = $("[name='startFlightTimes']").val();
        var endFlightTimes = $("[name='endFlightTimes']").val();
        var ieFlag = $("[name='ieFlag']").val();
        var entId = $("[name='entId']").val();

        // 拼接URL及参数
        var url = sw.serializeObjectToURL("customs/queryCustoms", {
            startFlightTimes: startFlightTimes,//申报开始时间
            endFlightTimes: endFlightTimes,//申报结束时间
            ieFlag: ieFlag,//进出口标识
            entId: entId//企业ID码
        });

        // 数据表
        sw.datatable("#query-customs-table", {
            ajax: sw.resolve("api", url),
            lengthMenu: [[50, 100, 1000, -1], [50, 100, 1000, "所有"]],
            searching: false,//开启本地搜索
            columns: [
                {
                    label: "电商企业名称", render: function (data, type, row) {
                    return row.ent_name;
                }
                },
                {
                    label: "企业编码", render: function (data, type, row) {
                    return row.ent_customs_code;
                }
                },
                {
                    label: "提运单数", render: function (data, type, row) {
                    return row.billNoCount;
                }
                },
                {
                    label: "放行清单数", render: function (data, type, row) {
                    return row.amount;
                }
                },
                {
                    label: "商品总价", render: function (data, type, row) {
                    var totalPrice = parseFloat(row.totalPrice);
                    if (isNaN(totalPrice)) return 0;
                    return totalPrice.toFixed(2);
                }
                },
                {
                    label: "币制", render: function (data, type, row) {
                    return row.currency;
                }
                },
                {
                    label: "税额", render: function (data, type, row) {
                    var totalTax = parseFloat(row.totalTax);
                    if (isNaN(totalTax)) return 0;
                    return totalTax.toFixed(2);
                }
                },
                {
                    label: "毛重(KG)", render: function (data, type, row) {
                    var totalGrossWeight = parseFloat(row.totalGrossWeight);
                    if (isNaN(totalGrossWeight)) return 0;
                    return totalGrossWeight.toFixed(2);
                }
                }
            ]
        });
    },

    EbusinessEnt: function () {
        sw.ajax("api/querystatistics/EbusinessEnt", "GET", "", function (rsp) {
            var result = rsp.data;
            for (var idx in result) {
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
        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyymmdd",
            autoclose: true
        });
        this.EbusinessEnt();
        $("[ws-search]").unbind("click").click(this.query);
        $(".btn[ws-search]").click();
    },

};
