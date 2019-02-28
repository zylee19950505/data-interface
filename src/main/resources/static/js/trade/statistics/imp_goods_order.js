/**
 * 跨境贸易统计-进口贸易额
 * @type {*|{init: sw.page.modules.trade/statistics/imp_trade_volume.init, query: sw.page.modules.trade/statistics/imp_trade_volume.query}}
 */
sw.page.modules["trade/statistics/imp_goods_order"] = sw.page.modules["trade/statistics/imp_goods_order"] || {

    query:function(){
        var startFlightTimes = $('[name="startFlightTimes"]').val();//统计时间
        var endFlightTimes = $('[name="endFlightTimes"]').val();//结束时间
        var customsCode = $('[name="customsCode"]').val();//贸易关区
        var tradeMode = $("[name = 'tradeMode']").val();//贸易方式

        var url = sw.serializeObjectToURL("api/statistics/queryImpGoodsOrderList", {
            startFlightTimes: startFlightTimes,
            endFlightTimes: endFlightTimes,
            customsCode: customsCode,
            tradeMode: tradeMode
        });

        sw.datatable("#query-goodsOrder-table", {
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
                    label: "序号", render: function (data, type, full, meta) {
                        return meta.row + 1 + meta.settings._iDisplayStart;
                    }
                },
                {data: "hsCode", label: "HS编码"},
                {data: "goodsName", label: "商品名称"},
                {
                    label: "货值(万元)", render: function (data, type, row) {
                        if (!isEmpty(row.cargoValue)) {
                            return parseFloat(row.cargoValue).toFixed(5);
                        }
                        return "";
                    }
                }
            ],
            order: [[1, 'asc']]
        });
    },

    toLoad:function(){

        //datetables表格加载
        sw.page.modules['trade/statistics/imp_goods_order'].query();
    },

    init: function () {
        $("[name='startFlightTimes']").val(moment(new Date()).date(1).format("YYYY-MM-DD"));
        $("[name='endFlightTimes']").val(moment(new Date()).format("YYYY-MM-DD"));
        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyy-mm-dd",
            autoclose: true
        });

       // $("[ws-search]").unbind("click").click(this.toLoad());
        $("[ws-search]").unbind("click").click(this.toLoad);

    }

};

