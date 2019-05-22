/**
 * Created on 2018-09-14
 * 核放单管理
 */
sw.page.modules["manifest/manifestmanage"] = sw.page.modules["manifest/manifestmanage"] || {

    query: function () {
        // 获取查询表单参数
        var startFlightTimes = $("[name='startFlightTimes']").val();
        var endFlightTimes = $("[name='endFlightTimes']").val();
        var manifestNo = $("[name='manifestNo']").val();

        // 拼接URL及参数
        var url = sw.serializeObjectToURL("api/manifestManage/queryManifestManage", {
            startFlightTimes: startFlightTimes,//申报开始时间
            endFlightTimes: endFlightTimes,//申报结束时间
            manifestNo: manifestNo//核放单号
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
                {data: "manifest_no", label: "核放单号"},
                {data: "pack_no", label: "总件数"},
                {data: "goods_wt", label: "总毛重"},
                {data: "fact_weight", label: "总净重"},
                {data: "sum_goods_value", label: "总货值"},
                {data: "car_no", label: "车牌号"},
                {data: "ic_code", label: "IC卡号"},
                {
                    label: "状态", render: function (data, type, row) {
                    var textColor = "";
                    var value = "";
                    switch (row.data_status) {
                        case null:
                            textColor = "text-yellow";
                            value = "待申报";
                            break;
                        case "CBDS8":
                            textColor = "text-yellow";
                            value = "核放单待申报";
                            break;
                        case "CBDS80":
                            textColor = "text-green";
                            value = "核放单申报中";
                            break;
                        case "CBDS81":
                            textColor = "text-green";
                            value = "核放单正在发往海关";
                            break;
                        case "CBDS82":
                            textColor = "text-green";
                            value = "核放单申报成功";
                            break;
                        case "CBDS83":
                            textColor = "text-red";
                            value = "核放单重报";
                            break;
                    }

                    return "<span class='" + textColor + "'>" + value + "</span>";
                }
                },
                {
                    label: "操作", render: function (data, type, row) {
                    return item = '<button class="btn btn-sm btn-primary" title="申报" id="submitCustomBtn" ' +
                        'onclick="' + "javascript:sw.page.modules['manifest/manifestmanage'].manifestDeclare('" + row.manifest_no + "')" + '">' +
                        '<i class="fa fa-edit">申报</i> </button> ' +
                        '<button class="btn btn-sm btn-info" id="manifestPrint" ws-print="" title="打印" ' +
                        'onclick="' + "javascript:sw.page.modules['manifest/manifestmanage'].manifestPrint('" + row.manifest_no + "')" + '">' +
                        '<i class="fa fa-print">打印</i> </button> ' +
                        '<button class="btn btn-sm btn-danger" title="删除" ' +
                        'onclick="' + "javascript:sw.page.modules['manifest/manifestmanage'].manifestDelete('" + row.manifest_no + "')" + '">' +
                        '<i class="fa fa-remove">删除</i> </button> ';
                }, width: "300px"
                }
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
        $("[ws-search]").unbind("click").click(this.query).click();
    },
    //申报
    manifestDeclare: function (manifest_no) {
        sw.blockPage();
        var postData = {
            manifestNo: manifest_no
        };
        sw.ajax("api/manifestManage/manifestDeclare", "POST", postData, function (rsp) {
            if (rsp.data.result == "true") {
                $("#submitCustomBtn").prop("disabled", false);
                sw.alert("申报成功", "提示", function () {
                }, "modal-success");

                sw.page.modules["manifest/manifestmanage"].query();
            } else {
                sw.alert(rsp.data.msg);
            }
            $.unblockUI();
        });
    },

    manifestDelete: function (manifest_no) {
        sw.confirm("确认删除核放单号【" + manifest_no + "】", "确认", function () {
            sw.ajax("api/manifestManage/manifestDelete/" + manifest_no, "DELETE", {}, function (rsp) {
                sw.page.modules["manifest/manifestmanage"].query();
            })
        })
    },

    manifestPrint: function (manifest_no) {
        var parameter = {
            manifest_no: manifest_no
        }
        var param = JSON.stringify(parameter);
        this.printBill(param);

    },

    printBill: function (param) {
        localStorage.setItem("assbill_inquiry_param", param);
        var printWindow = window.open(sw.resolve("admin/page?p=manifest/manifestPrint"), "printWindow");
    }

};
