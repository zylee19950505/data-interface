/**
 * 跨境贸易统计-企业清单量
 * @type {*|{init: sw.page.modules.trade/statistics/imp_trade_volume.init, query: sw.page.modules.trade/statistics/imp_trade_volume.query}}
 */
sw.page.modules["trade/statistics/enterprise_bill_quantity"] = sw.page.modules["trade/statistics/enterprise_bill_quantity"] || {

    query:function(){
        var startFlightTimes = $('[name="startFlightTimes"]').val();//申报时间
        var endFlightTimes = $('[name="endFlightTimes"]').val();//申报结束时间
        var customsCode = $('[name="customsCode"]').val();//贸易关区
        var tradeMode = $("[name = 'tradeMode']").val();//贸易方式
        var entName = $("[name = 'entName']").val();//企业名称
        var entCustomsCode = $("[name = 'entCustomsCode']").val();//海关10位代码
        var creditCode = $("[name = 'creditCode']").val();//统一社会信用代码

        var url = sw.serializeObjectToURL("api/statistics/queryEnterpriseBillQuantityList", {
            startFlightTimes: startFlightTimes,
            endFlightTimes: endFlightTimes,
            customsCode: customsCode,
            tradeMode: tradeMode,
            entName: entName,
            entCustomsCode: entCustomsCode,
            creditCode: creditCode
        });

        sw.datatable("#query-enterpriseBillQuantity-table", {
            ordering: false,
            bSort: false, //排序功能
            serverSide: true,////服务器端获取数据
            pagingType: 'simple_numbers',
            //paging:false,
            //info:false,
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
                {data: "entCustomsCode", label: "海关十位代码"},
                {data: "creditCode", label: "统一社会信用代码"},
                {data: "entName", label: "企业名称"},
                {data: "iInventoryValue", label: "进口清单量"},
                {data: "eInventoryValue", label: "出口清单量"},
                {data: "inventoryValue", label: "进出口清单量"}
            ],
            order: [[1, 'asc']]
        });
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
        $("[ws-search]").unbind("click").click(this.query);

    }

};

