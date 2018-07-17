/**
 * Created by on 2017-7-23.
 * 运单申报
 */
sw.page.modules["waybillmanage/waybillDeclare"] = sw.page.modules["waybillmanage/waybillDeclare"] || {
    // 申报列表查询
    query: function () {
        // 获取查询表单参数
        var startFlightTimes = $("[name='startFlightTimes']").val();
        var endFlightTimes = $("[name='endFlightTimes']").val();
        var logisticsNo = $("[name='logisticsNo']").val();
        var logisticsStatus = $("[name='logisticsStatus']").val();

        // 拼接URL及参数
        var url = sw.serializeObjectToURL("api/waybillManage/queryWaybillDeclare", {
            startFlightTimes: startFlightTimes,
            endFlightTimes: endFlightTimes,
            logisticsNo: logisticsNo,
            logisticsStatus: logisticsStatus
        });

        sw.datatable("#query-waybillDeclare-table", {
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
            lengthMenu: [[50, 100, 1000, -1], [50, 100, 1000, "所有"]],
            searching: false,//开启本地搜索
            columns: [
                {
                    label: '<input type="checkbox" name="cb-check-all"/>',
                    orderable: false,
                    data: null,
                    render: function (data, type, row) {
                        return '<input type="checkbox" class="submitKey" value="'+ row.logistics_no +'" />';
                    }
                },
                {data: "logistics_no", label: "物流运单编号"},//订单编号要点击查看订单详情
                {data: "logistics_name", label: "物流企业名称"},
                {data: "consingee", label: "收货人姓名"},
                {data: "consignee_telephone", label: "收货人电话"},
                {data: "consignee_address", label: "收货地址"},
                {data: "data_status", label: "业务状态"},
                {data: "data_status", label: "入库结果"},//入库结果需要确认字段
                {
                    label: "申报日期", render: function (data, type, row) {
                    if(!isEmpty(row.app_time)){
                        return moment(row.app_time).format("YYYY-MM-DD HH:mm:ss");
                    }
                    return "";
                }
                },
                {
                    label: "物流状态时间", render: function (data, type, row) {
                    if(!isEmpty(row.app_time)){
                        return moment(row.app_time).format("YYYY-MM-DD HH:mm:ss");
                    }
                    return "";
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
            sw.alert("请先勾选要提交海关的舱单信息！");
            return;
        }

        sw.confirm("请确认分单总数无误，提交海关", "确认", function () {

            var idCardValidate = $("[name='idCardValidate']").val();
            sw.blockPage();

            var postData = {
                submitKeys: submitKeys,
                idCardValidate: idCardValidate,
                ieFlag: sw.ie,
                entryType: sw.type
            };

            $("#submitManifestBtn").prop("disabled", true);

            sw.ajax("api/manifest/submitCustom", "POST", postData, function (rsp) {
                if (rsp.data.result == "true") {
                    sw.alert("提交海关成功", "提示", function () {
                    }, "modal-success");
                    $("#submitManifestBtn").prop("disabled", false);
                    sw.page.modules["express/import_b/declaration/manifest_declaration"].query();
                } else {
                    sw.alert(rsp.data.msg);
                }
                $.unblockUI();
            });
        });

        sw.ajax("api/manifest/submitCustom", "POST", postData, function (rsp) {
            if (rsp.data.result == "true") {
                sw.alert("提交海关成功", "提示", function () {
                }, "modal-success");
                $("#submitManifestBtn").prop("disabled", false);
                sw.page.modules["express/import_b/declaration/manifest_declaration"].query();
            } else {
                sw.alert(rsp.data.msg);
            }
            $.unblockUI();
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
        $("[ws-search]").unbind("click").click(this.query);
        $("[ws-submit]").unbind("click").click(this.submitCustom);
        this.query();
        var $table = $("#query-conveyance-table");
        $table.on("change", ":checkbox", function () {
            if ($(this).is("[name='cb-check-all']")) {
                //全选
                $(":checkbox", $table).prop("checked", $(this).prop("checked"));
            } else {
                //一般复选
                var checkbox = $("tbody :checkbox", $table);
                $(":checkbox[name='cb-check-all']", $table).prop('checked', checkbox.length == checkbox.filter(':checked').length);
            }
        });
    }
};

