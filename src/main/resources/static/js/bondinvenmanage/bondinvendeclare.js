/**
 * 保税清单申报
 * Created by lzy on 2018-12-26
 */
sw.page.modules["bondinvenmanage/bondinvendeclare"] = sw.page.modules["bondinvenmanage/bondinvendeclare"] || {
    // 订单申报列表查询
    query: function () {
        // 获取查询表单参数
        var startFlightTimes = $("[name='startFlightTimes']").val();
        var endFlightTimes = $("[name='endFlightTimes']").val();
        var orderNo = $("[name='orderNo']").val();
        var logisticsNo = $("[name='logisticsNo']").val();
        var dataStatus = $("[name='dataStatus']").val();

        // 拼接URL及参数
        var url = sw.serializeObjectToURL("api/bondinvenmanage/querybondinvendeclare", {
            startFlightTimes: startFlightTimes,//申报开始时间
            endFlightTimes: endFlightTimes,//申报结束时间
            orderNo: orderNo,//订单编号
            logisticsNo: logisticsNo,//物流编号
            dataStatus: dataStatus//业务状态
        });

        // 数据表
        sw.datatable("#query-BondInvenDeclare-table", {
            sLoadingRecords: true,
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
            lengthMenu: [[50, 100, 1000], [50, 100, 1000]],
            searching: false,//开启本地搜索
            columns: [
                {
                    label: '<input type="checkbox" name="cb-check-all"/>',
                    orderable: false,
                    data: null,
                    render: function (data, type, row) {
                        if (row.data_status == "BDDS5") {
                            return '<input type="checkbox" class="submitKey" value="' +
                                row.guid + '" />';
                        }
                        else {
                            return "";
                        }
                    }
                },
                {
                    label: "订单编号", render: function (data, type, row) {
                    return '<a href="javascript:void(0)"  onclick="' + "javascript:sw.pageModule('bondinvenmanage/bondinvendeclare').seeBondInvenDetail('" + row.guid + "','" + row.order_no + "','" + row.data_status + "')" + '">' + row.order_no + '</a>'
                }
                },
                {data: "logistics_no", label: "物流编号"},
                {
                    label: "电商平台名称", render: function (data, type, row) {
                    return '<div style="width:100px;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;" ' +
                        'title="' + row.ebp_name + '">' + row.ebp_name + '</div>';
                }
                },
                {
                    label: "电商企业名称", render: function (data, type, row) {
                    return '<div style="width:100px;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;" ' +
                        'title="' + row.ebc_name + '">' + row.ebc_name + '</div>';
                }
                },
                {
                    label: "物流企业名称", render: function (data, type, row) {
                    return '<div style="width:100px;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;" ' +
                        'title="' + row.logistics_name + '">' + row.logistics_name + '</div>';
                }
                },
                {
                    label: "申报日期", render: function (data, type, row) {
                    if (!isEmpty(row.app_time)) {
                        return moment(row.app_time).format("YYYY-MM-DD HH:mm:ss");
                    }
                    return "";
                }
                },
                {
                    data: "data_status", label: "业务状态", render: function (data, type, row) {
                    switch (row.data_status) {
                        case "BDDS5"://保税清单待申报
                            textColor = "text-yellow";
                            row.data_status = "保税清单待申报";
                            break;
                        case "BDDS50"://保税清单申报中
                            textColor = "text-green";
                            row.data_status = "保税清单申报中";
                            break;
                        case "BDDS51"://保税清单正在发往海关
                            textColor = "text-green";
                            row.data_status = "保税清单正在发往海关";
                            break;
                    }
                    return "<span class='" + textColor + "'>" + row.data_status + "</span>";
                }
                }
            ]
        });
    },

    // 提交海关
    submitCustom: function () {
        var submitKeys = "";
        $(".submitKey:checked").each(function () {
            submitKeys += "," + $(this).val();
        });
        if (submitKeys.length > 0) {
            submitKeys = submitKeys.substring(1);
        } else {
            sw.alert("请先勾选要提交海关的保税清单信息！");
            return;
        }
        sw.confirm("请确认数据无误，提交海关", "确认", function () {
            sw.blockPage();
            var postData = {
                submitKeys: submitKeys
            };
            $("#submitByBondInven").prop("disabled", true);
            sw.ajax("api/bondinvenmanage/submitCustom", "POST", postData, function (rsp) {
                if (rsp.data.result == "true") {
                    sw.alert("提交海关成功", "提示", function () {
                    }, "modal-success");
                    $("#submitByBondInven").prop("disabled", false);
                    sw.page.modules["bondinvenmanage/bondinvendeclare"].query();
                } else {
                    sw.alert(rsp.data.msg);
                }
                $.unblockUI();
            });
        });
    },

    seeBondInvenDetail: function (guid, order_no, dataStatus) {
        if (dataStatus == 'BDDS5') {
            var url = "bondinvenmanage/seebondinvendetail?type=BSQDSB&isEdit=true&guid=" + guid + "&orderNo=" + order_no;
        } else {
            var url = "bondinvenmanage/seebondinvendetail?type=BSQDSB&isEdit=false&guid=" + guid + "&orderNo=" + order_no;
        }
        sw.modelPopup(url, "查看保税清单详情", false, 1100, 930);
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
        $("[ws-submit]").unbind("click").click(this.submitCustom);

        $table = $("#query-BondInvenDeclare-table");
        $table.on("change", ":checkbox", function () {
            if ($(this).is("[name='cb-check-all']")) {
                //全选
                $(":checkbox", $table).prop("checked", $(this).prop("checked"));
            } else {
                //复选
                var checkbox = $("tbody :checkbox", $table);
                $(":checkbox[name='cb-check-all']", $table).prop('checked', checkbox.length == checkbox.filter(':checked').length);
            }
        });
    }
};

