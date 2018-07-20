// 报关单状态统计
sw.page.modules["ordermanage/orderQuery"] = sw.page.modules["ordermanage/orderQuery"] || {
    query: function () {

        var startDeclareTime = $('[name="startDeclareTime"]').val();//申报时间
        var endDeclareTime = $('[name="endDeclareTime"]').val();//申报结束时间
        var orderNo = $('[name="orderNo"]').val();//订单编号


        var url = sw.serializeObjectToURL("api/ordermanage/queryOrder/queryOrderHeadList", {
            startDeclareTime: startDeclareTime,
            endDeclareTime: endDeclareTime,
            orderNov: orderNo
        });

        var table = sw.datatable("#query-orderHead-table", {
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
            lengthMenu: [[10, 20, 50, -1], [10, 20, 50, "所有"]],
            searching: false,//开启本地搜索
            columns: [
                {
                    label: "详情",
                    class:'details-control',
                    orderable:  false,
                    data:null,
                    defaultContent: ''
                },
                {data: "order_No", label: "订单编号"},//订单编号要点击查看订单详情
                {data: "ebp_Name", label: "电商企业名称"},
                {data: "ebc_Name", label: "电商平台名称"},
                /*  {data: "item_Name", label: "商品名称"},*/
                {data: "goods_Value", label: "总价"},
                {data: "buyer_Name", label: "订购人"},
                //要区分开
                /* {data: "data_status", label: "业务状态"},*/
                {
                    label: "业务状态", render: function (data, type, row) {
                    var textColor = "";
                    var value = "";
                    switch (row.data_status) {
                        case "CBDS2":
                            textColor = "text-yellow";
                            value = "订单待申报";
                            break;
                        case "CBDS20":
                            textColor = "text-muted";
                            value = "订单申报中";
                            break;
                        case "CBDS21":
                            textColor = "text-green";
                            value = "订单已申报";
                            break;
                        case "CBDS22":
                            textColor = "text-red";
                            value = "订单重报";
                            break;
                        default:
                            textColor = "";
                            value = "";
                    }

                    return "<span class='" + textColor + "'>" + value + "</span>";
                }
                },

                {data: "note", label: "入库结果"},//入库结果需要确认字段
                {
                    label: "申报日期", render: function (data, type, row) {
                    if(!isEmpty(row.app_Time)){
                        return moment(row.app_Time).format("YYYY-MM-DD HH:mm:ss");
                    }
                    return "";
                }
                }
            ],
            order: [[1, 'asc']]
        });
        console.debug(table);
        $('#query-orderHead-table tbody').on('click', 'td.details-control', function () {
            var tr = $(this).closest('tr');
            console.debug(tr);
            var row = table.row(tr);
            if ( row.child.isShown() ) {
                // This row is already open - close it
                row.child.hide();
                tr.removeClass('shown');
            }
            else {
                // Open this row
                row.child( sw.page.modules["ordermanage/orderQuery"].format1(row.data().guid)).show();
                tr.addClass('shown');
                sw.datatable("#show-orderBody-table_"+row.data().guid, {
                    ordering: false,//排序功能
                    bSort: false, //排序功能
                    //serverSide: true,//当设为true时,列表的过滤,搜索和排序信息会传递到Server端进行处理,实现真翻页方案的必需属性.反之,所有的列表功能都在客户端计算并执行
                    //pagingType: 'simple_numbers',// - “上一页”和“下一页”按钮以及页码
                    ajax: function (data, callback, setting) {
                        $.ajax({
                            type: 'get',
                            url: 'api/ordermanage/queryOrder/queryOrderBodyList',
                            data: {
                            "guid":row.data().guid,
                            "orderNo":row.data().order_No
                            },
                            cache: false,
                            dataType: "json",
                            success: function (res) {
                                var returnData = {};
                                returnData.data = res.data.data;
                                callback(returnData);
                            },
                            error: function (xhr, status, error) {
                                sw.showErrorMessage(xhr, status, error);
                            }
                        });
                    },

                    paging : false,
                    searching: false,
                    info:false,
                    columns: [
                        {data: "order_No", label: "订单编号"},
                        {data: "item_Name", label: "商品名称"},
                        {data: "item_Describe", label: "商品详情"},
                        {data: "qty", label: "数量"},
                        {data: "price", label: "单价"},
                        {data: "total_Price", label: "总价"},
                        {data: "note", label: "促销活动"}
                    ],
                /*    lengthMenu: [[ 5, 10, -1], [ 5, 10, "ALL"]],*/
                })
            }
        } );
    },
    init: function () {
        $("[name='startDeclareTime']").val(moment(new Date()).date(1).format("YYYY-MM-DD"));
        $("[name='endDeclareTime']").val(moment(new Date()).format("YYYY-MM-DD"));
        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyy-mm-dd",
            autoclose: true
        });
        $("[ws-search]").unbind("click").click(this.query).click();
    },
    format1 :function  ( id ) {// #show-customsDet-table_进口_9001
            // `d` is the original data object for the row
            return  '<table id="show-orderBody-table_'+id+'"' +
                ' class="table table-striped table-bordered table-hover table-condensed"' +
                ' width="80%"></table>';
    }
}


