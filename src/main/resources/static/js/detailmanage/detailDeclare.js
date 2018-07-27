/**
 * Created on 2017-7-23.
 * 订单申报
 */
sw.page.modules["detailmanage/detailDeclare"] = sw.page.modules["detailmanage/detailDeclare"] || {
        // 订单申报列表查询
        query: function () {
            // 获取查询表单参数
            var startFlightTimes = $("[name='startFlightTimes']").val();
            var endFlightTimes = $("[name='endFlightTimes']").val();
            var orderNo = $("[name='orderNo']").val();

            // 拼接URL及参数
            var url = sw.serializeObjectToURL("api/detailManage/queryDetailDeclare", {
                startFlightTimes: startFlightTimes,//申报开始时间
                endFlightTimes: endFlightTimes,//申报结束时间
                orderNo: orderNo
            });

            // 数据表
            sw.datatable("#query-detailDeclare-table", {
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
                    //还需判断下状态
                    {
                        label: '<input type="checkbox" name="cb-check-all"/>',
                        orderable: false,
                        data: null,
                        render: function (data, type, row) {
                            //订单已经申报时,不提交
                            if (row.data_status == "CBDS61") {
                                return "";
                            }
                            return '<input type="checkbox" class="submitKey" value="'+ row.order_No +'" />';
                        }
                    },
                    {data: "order_no", label: "订单编号"},//订单编号要点击查看订单详情
                    {data: "logistics_code", label: "物流运单编号"},
                    {data: "ebc_name", label: "电商企业名称"},
                    {data: "ebc_name", label: "支付企业名称"},
                    {data: "logistics_name", label: "物流企业名称"},
                    {data: "g_name", label: "商品名称"},
                    {
                        label: "业务状态", render: function (data, type, row) {
                        switch (row.data_status) {
                            case "CBDS1"://待申报
                                textColor="text-yellow";
                                row.data_status="待申报";
                                break;
                            case "CBDS6":
                                textColor = "text-yellow";
                                row.data_status = "订单待申报";
                                break;
                            case "CBDS60":
                                textColor = "text-green";
                                row.data_status = "订单申报中";
                                break;
                            case "CBDS61":
                                textColor = "text-green";
                                row.data_status = "订单已申报";
                                break;
                            case "CBDS63":
                                textColor = "text-red";
                                row.data_status = "订单重报";
                                break;
                        }

                        return "<span class='" + textColor + "'>" + row.data_status + "</span>";
                    }
                    },

                    {data: "return_status", label: "入库结果"},//入库结果需要确认字段
                    {
                        label: "申报日期", render: function (data, type, row) {
                        if(!isEmpty(row.app_Time)){
                            return moment(row.app_Time).format("YYYY-MM-DD HH:mm:ss");
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

                sw.ajax("api/detailmanage/submitCustom", "POST", postData, function (rsp) {
                    if (rsp.data.result == "true") {
                        sw.alert("提交海关成功", "提示", function () {
                        }, "modal-success");
                        $("#submitManifestBtn").prop("disabled", false);
                        sw.page.modules["detailmanage/detailDeclare"].query();
                    } else {
                        sw.alert(rsp.data.msg);
                    }
                    $.unblockUI();
                });
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
            $("[ws-submit]").unbind("click").click(this.submitCustom);
            $table = $("#query-detailDeclare-table");
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

