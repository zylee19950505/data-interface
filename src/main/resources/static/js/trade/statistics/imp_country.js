/**
 * 跨境贸易统计-进口国家
 * @type {*|{init: sw.page.modules.trade/statistics/imp_trade_volume.init, query: sw.page.modules.trade/statistics/imp_trade_volume.query}}
 */
sw.page.modules["trade/statistics/imp_country"] = sw.page.modules["trade/statistics/imp_country"] || {

    query:function(){
        var startFlightTimes = $('[name="startFlightTimes"]').val();//统计时间
        var endFlightTimes = $('[name="endFlightTimes"]').val();//结束时间
        var customsCode = $('[name="customsCode"]').val();//贸易关区
        var tradeMode = $("[name = 'tradeMode']").val();//贸易方式

        var url = sw.serializeObjectToURL("api/statistics/queryImpCountryList", {
            startFlightTimes: startFlightTimes,
            endFlightTimes: endFlightTimes,
            customsCode: customsCode,
            tradeMode: tradeMode
        });

        sw.datatable("#query-country-table", {
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
                            "countryCode":"合计",
                            "countryName":"",
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
                {data: "countryCode", label: "代码"},
                {data: "countryName", label: "国家名称"},
                {
                    label: "货值(万元)", render: function (data, type, row) {
                        if (!isEmpty(row.cargoValue)) {
                            return parseFloat(row.cargoValue).toFixed(5);
                        }
                        return "";
                    }
                },
                {data: "detailedCount", label: "清单量"}
            ],
        });
    },

    initChart:function(){

        var mainChart = echarts.init(document.getElementById("main"));
        var option = {
            title : {
                text: '进口贸易国top5',
                x:'center'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient : 'vertical',
                x : 'left',
                //data:['日本','韩国','澳大利亚','美国','新西兰']
                data:[]
            },
            toolbox: {
                show : true,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType : {
                        show: true,
                        type: ['pie', 'funnel'],
                        option: {
                            funnel: {
                                x: '25%',
                                width: '50%',
                                funnelAlign: 'left',
                                max: 1548
                            }
                        }
                    },
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            series : [
                {
                    name:'进口贸易国',
                    type:'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data:[]
                }
            ]
        };

        mainChart.showLoading();    //数据加载完之前先显示一段简单的loading动画

        var cargoValueList=[];    //货值数组
        var countryNameList=[];    //国家名称数组
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
            url : "api/statistics/queryImpCountryEChart",    //请求发送到TestServlet处
            data: data,
            dataType : "json",        //返回数据形式为json
            success : function(result) {
                //请求成功时执行该函数内容，result即为服务器返回的json对象
                if (true) {
                    for(var i=0;i<result.data.data.length;i++){
                        countryNameList.push(result.data.data[i].countryName);
                        cargoValueList.push(
                            {
                                name:result.data.data[i].countryName,
                                value:result.data.data[i].cargoValue
                            }
                        )

                    }
                    mainChart.hideLoading();    //隐藏加载动画
                    mainChart.setOption({        //加载数据图表
                        legend: {
                            data: countryNameList
                        },
                        series: [{
                            // 根据名字对应到相应的系列
                            name: '进口贸易国',
                            data: cargoValueList
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
        sw.page.modules['trade/statistics/imp_country'].query();
        //echarts初始化
        sw.page.modules['trade/statistics/imp_country'].initChart();
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

