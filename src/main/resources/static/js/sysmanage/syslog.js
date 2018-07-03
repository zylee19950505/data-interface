/**
 * 频繁进口
 * Created by lzy on 2017/12/25.
 */
sw.page.modules["sysmanage/syslog"] = sw.page.modules["sysmanage/syslog"] || {
        init: function () {
            $("[name='startFlightTimes']").val(moment(new Date()).date(1).format("YYYY-MM-DD"));
            $("[name='endFlightTimes']").val(moment(new Date()).format("YYYY-MM-DD"));
            $(".input-daterange").datepicker({
                language: "zh-CN",
                todayHighlight: true,
                format: "yyyy-mm-dd",
                autoclose: true
            });
            $("[ws-search]").unbind("click").click(this.query).click();
            $(".btn[ws-search]").click();
        },

        query: function () {
            var startFlightTimes = $("[name='startFlightTimes']").val();
            var endFlightTimes = $("[name='endFlightTimes']").val();
            var module = $("[name='module']").val();

            var url = sw.serializeObjectToURL("/syslog", {
                startFlightTimes:startFlightTimes,
                endFlightTimes:endFlightTimes,
                module:module
            });
            // 数据表
            sw.datatable("#query-syslog-table", {
                ordering: false,
                bSort: false, //排序功能
                serverSide: true,
                pagingType: 'simple_numbers',
                ajax: function (data, callback, setting) {
                    $.ajax({
                        type: 'GET',
                        url: sw.resolve("api", url),
                        data: data,
                        cache: false,
                        dataType: "json",
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
                searching: false,
                columns: [
                    {
                        label: "序号", render: function (data, type, full, meta) {
                        return meta.row + 1 + meta.settings._iDisplayStart;
                    }
                    },
                    {
                        data: "USERID", label: "用户账号"
                    },
                    {
                        data: "IP", label: "IP地址"
                    },
                    {
                        data: "MODULE", label: "操作模块"
                    },
                    {
                        data: "TYPE", label: "操作类型"
                    },
                    {
                        label: "操作时间", render: function (data, type, row) {
                        return moment(row.OPERATTIME).format("YYYY-MM-DD HH:mm:ss")
                    }
                    }
                ]
            })
        },


    };

$("#query-syslog-table").on("click", "a", function () {
    alert("点击了上一页和下一页的按钮");
});

$('#query-syslog-table').on('page.dt', function () {
    alert("点击了上一页和下一页的按钮");
    var info = table.page.info();
    $('#pageInfo').html('Showing page: ' + info.page + ' of ' + info.pages);
});