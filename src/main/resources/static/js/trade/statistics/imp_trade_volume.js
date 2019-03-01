/**
 * 跨境贸易统计-进口贸易额
 * @type {*|{init: sw.page.modules.trade/statistics/imp_trade_volume.init, query: sw.page.modules.trade/statistics/imp_trade_volume.query}}
 */
sw.page.modules["trade/statistics/imp_trade_volume"] = sw.page.modules["trade/statistics/imp_trade_volume"] || {

    query:function(){
        var startFlightTimes = $('[name="startFlightTimes"]').val();//统计时间
        var endFlightTimes = $('[name="endFlightTimes"]').val();//结束时间
        var customsCode = $('[name="customsCode"]').val();//贸易关区
        var tradeMode = $("[name = 'tradeMode']").val();//贸易方式

        var url = sw.serializeObjectToURL("api/statistics/queryImpTradeVolumeList", {
            startFlightTimes: startFlightTimes,
            endFlightTimes: endFlightTimes,
            customsCode: customsCode,
            tradeMode: tradeMode
        });

        sw.datatable("#query-customs-table", {
            ordering: false,
            bSort: false, //排序功能
            serverSide: true,////服务器端获取数据
            pagingType: 'simple_numbers',
            paging:false,
            info:false,
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
                        var cargoValue = 0;
                        var detailedCount = 0;
                        var dataArray = res.data.data;
                        for (var i = 0; i < dataArray.length; i++) {
                            cargoValue += dataArray[i].cargoValue;
                            detailedCount += dataArray[i].detailedCount;
                        }
                        res.data.data.push({
                            "statisticsDate":"合计",
                            "cargoValue":parseFloat(cargoValue).toFixed(5),
                            "detailedCount":detailedCount
                        })
                        returnData.data = res.data.data;
                        callback(returnData);
                    },
                    error: function (xhr, status, error) {
                        sw.showErrorMessage(xhr, status, error);
                    }
                });
            },
            //lengthMenu: [[50, 100, 1000, -1], [50, 100, 1000, "所有"]],
            searching: false,//开启本地搜索
            columns: [
                {data: "statisticsDate", label: "年月"},
                {
                    label: "货值(万元)", render: function (data, type, row) {
                        if (!isEmpty(row.cargoValue)) {
                            return parseFloat(row.cargoValue).toFixed(5);
                        }
                        return "";
                    }
                },
                {data: "detailedCount", label: "清单量"},
            ],
        });
    },

    initChart:function(){

        var mainChart = echarts.init(document.getElementById("main"));
        var option = {
            color: [
                "#5FC9E3"
            ],
            title: {
                text: '跨境电子商务进口总值表',
                x: 'center'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data:['','','','','货值','清单量']
            },
            calculable: true,
            xAxis: [
                {
                    type: 'category',
                    data: []
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    data:[]
                }
            ],
            series: [
                {
                    name:'货值',
                    type:'line',
                    data:[]
                },
                {
                    name:'清单量',
                    type:'bar',
                    data:[]
                }
            ]
        };

        mainChart.showLoading();    //数据加载完之前先显示一段简单的loading动画

        var cargoValueList=[];    //货值数组
        var detailedCountList=[];    //清单量数组
        //以下数据异步加载x,y轴数据
        var statisticsDateList=[];    //清单量数组
        //以下数据异步加载x,y轴数据
        var startFlightTimes = $('[name="startFlightTimes"]').val();//统计时间
        var endFlightTimes = $('[name="endFlightTimes"]').val();//结束时间
        var customsCode = $('[name="customsCode"]').val();//贸易关区
        var tradeMode = $("[name = 'tradeMode']").val();//贸易方式
        var data = {
            startFlightTimes:startFlightTimes,
            endFlightTimes:endFlightTimes,
            customsCode:customsCode,
            tradeMode:tradeMode
        };
        $.ajax({
            type : "post",
            async : true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
            url : "api/statistics/queryImpTradeVolumeEChart",    //请求发送到TestServlet处
            data: data,
            dataType : "json",        //返回数据形式为json
            success : function(result) {
                //请求成功时执行该函数内容，result即为服务器返回的json对象
                if (true) {
                    for(var i=0;i<result.data.data.length;i++){
                        cargoValueList.push(result.data.data[i].cargoValue.toFixed(5));    //挨个取出类别并填入类别数组
                        detailedCountList.push(result.data.data[i].detailedCount);//挨个取出销量并填入销量数组
                        statisticsDateList.push(result.data.data[i].statisticsDate);//挨个取出销量并填入销量数组

                    }
                    /*for(var i=0;i<result.data.data.length;i++){

                    }*/
                    mainChart.hideLoading();    //隐藏加载动画
                    mainChart.setOption({        //加载数据图表
                        xAxis: {
                            data: statisticsDateList
                        },
                        series: [{
                            // 根据名字对应到相应的系列
                            name: '货值',
                            data: cargoValueList
                        },{
                            // 根据名字对应到相应的系列
                            name: '清单量',
                            data: detailedCountList
                        }
                        ]
                    });

                }

            },
            error : function(errorMsg) {
                //请求失败时执行该函数
                alert("图表请求数据失败!");
                mainChart.hideLoading();
            }
        })

        mainChart.setOption(option);
    },

    toLoad:function(){

        //datetables表格加载
        sw.page.modules['trade/statistics/imp_trade_volume'].query();
        //echarts初始化
        sw.page.modules['trade/statistics/imp_trade_volume'].initChart();
    },

    init: function () {
        $("[name='startFlightTimes']").val(moment(new Date().setMonth(0)).date(1).format("YYYY-MM-DD"));
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

