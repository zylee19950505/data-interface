/**
 * Created on 2018-09-14
 * 核放单管理
 */
sw.page.modules["manifest/manifestmanage"] = sw.page.modules["manifest/manifestmanage"] || {

    query: function () {
        // 获取查询表单参数
        var startFlightTimes = $("[name='startFlightTimes']").val();
        var endFlightTimes = $("[name='endFlightTimes']").val();
        var checkReleaseNo = $("[name='checkReleaseNo']").val();

        // 拼接URL及参数
        var url = sw.serializeObjectToURL("api/detailManage/queryDetailQuery", {
            startFlightTimes: startFlightTimes,//申报开始时间
            endFlightTimes: endFlightTimes,//申报结束时间
            checkReleaseNo: checkReleaseNo
        });

        // 数据表
        sw.datatable("#query-manifestManage-table", {
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
                {data: "logistics_no", label: "核放单号"},
                {data: "logistics_no", label: "总件数"},
                {data: "logistics_no", label: "总毛重"},
                {data: "logistics_no", label: "总净重"},
                {data: "logistics_no", label: "总货值"},
                {data: "logistics_no", label: "车牌号"},
                {data: "logistics_no", label: "IC卡号"},
                {data: "logistics_no", label: "状态"},
                {data: "logistics_no", label: "操作"}
            ]
        });
    },

    init: function () {
        $("[name='startFlightTimes']").val(moment(new Date()).format("YYYYMMDD"));
        $("[name='endFlightTimes']").val(moment(new Date()).format("YYYYMMDD"));
        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyymmdd",
            autoclose: true
        });
        $("[ws-search]").unbind("click").click(this.query).click();
        $(".btn[ws-search]").click();
    },

    seeOrderNoDetail: function (guid, order_no) {
        console.log(guid, order_no)
        var url = "detailmanage/seeInventoryDetail?type=QDCX&isEdit=true&guid=" + guid + "&orderNo=" + order_no;
        sw.modelPopup(url, "查看清单详情", false, 1000, 930);
    }


};
