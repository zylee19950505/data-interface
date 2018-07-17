/**
 * 预览打印
 * Created by Administrator on 2017/7/20.
 */
sw.page.modules["waybillmanage/waybillQuery"] = sw.page.modules["waybillmanage/waybillQuery"] || {
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
        $("[ws-download]").unbind("click").click(this.billDownLoad);
        $("[ws-back]").unbind("click").click(this.back);
        $(".btn[ws-search]").click();
    },

    query: function () {
        // 获取查询表单参数
        var startFlightTimes = $("[name='startFlightTimes']").val();
        var endFlightTimes = $("[name='endFlightTimes']").val();
        var logisticsNo = $("[name='logisticsNo']").val();
        var logisticsStatus = $("[name='logisticsStatus']").val();

        // 拼接URL及参数
        var url = sw.serializeObjectToURL("api/waybillManage/queryWaybillQuery", {
            startFlightTimes: startFlightTimes,
            endFlightTimes: endFlightTimes,
            logisticsNo: logisticsNo,
            logisticsStatus: logisticsStatus
        });

        sw.datatable("#query-waybillQuery-table", {
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
                {data: "logistics_no", label: "物流运单编号"},//订单编号要点击查看订单详情
                {data: "logistics_name", label: "物流企业名称"},
                {data: "consingee", label: "收货人姓名"},
                {data: "consignee_telephone", label: "收货人电话"},
                {data: "consignee_address", label: "收货地址"},
                {data: "data_status", label: "业务状态"},
                {data: "data_status", label: "入库结果"},//入库结果需要确认字段
                {
                    label: "申报日期", render: function (data, type, row) {
                    if (!isEmpty(row.app_time)) {
                        return moment(row.APP_TIME).format("YYYY-MM-DD HH:mm:ss");
                    }
                    return "";
                }
                },
                {
                    label: "物流状态时间", render: function (data, type, row) {
                    if (!isEmpty(row.app_time)) {
                        return moment(row.APP_TIME).format("YYYY-MM-DD HH:mm:ss");
                    }
                    return "";
                }
                }
            ]
        });

    },

    back: function () {
        $("#bill").show();
        $("#preview").hide();
    },

    billDownLoad: function () {
        sw.ajax("api/bill", "GET", {
            ieFlag: sw.ie,
            entryType: sw.type,
            startFlightTimes: $("[name='startFlightTimes']").val(),
            endFlightTimes: $("[name='endFlightTimes']").val(),
            billNo: $("[name='billNo']").val(),
            flag: "1"
        }, function (rsp) {
            if (rsp.status == 200) {
                var fileName = rsp.data;
                window.location.href = "/api/downloadFile?fileName=" + fileName;
            }
        });

    },

    download: function (flightNo, flightTimes, billNo, statusCode, flag) {
        sw.ajax("api/downloadBill", "GET", {
            ieFlag: sw.ie,
            entryType: sw.type,
            flightNo: flightNo,
            flightTimes: flightTimes,
            billNo: billNo,
            statusCode: statusCode,
            flag: flag
        }, function (rsp) {
            if (rsp.status == 200) {
                var fileName = rsp.data;
                window.location.href = "/api/downloadFile?fileName=" + fileName;
            }
        });
    },

    seeAssBillNoDetail: function (flightNo, flightTimes, billNo, statusCode, flag) {
        $("#bill").hide();
        $("#preview").show();
        $("#previewId tr:not(:first)").remove();
        var parameter = {
            entryType: sw.type,
            ieFlag: sw.ie,
            flightNo: flightNo,
            flightTimes: flightTimes,
            billNo: billNo,
            statusCode: statusCode,
            flag: flag
        };
        sw.ajax("api/bill/preview", "GET", parameter, function (rsp) {
            if (rsp.status == 200) {
                var d = rsp.data;
                if (d !== null) {
                    sw.pageModule('express/import_b/bill_inquiry').setBillPreview(d);
                }
            }
        });


        $("[ws-print]").unbind("click").attr("param-data", JSON.stringify(parameter)).click(this.printBill);

    },

    //设置预览数据
    setBillPreview: function (data) {
        var assBillList = data.assBillList;
        var billPreview = data.billPreview;

        $("#bill_no").text(billPreview.billNo);//主运单号
        $("#enter_date").text(billPreview.flight_times.substr(0, 4) + "-" + billPreview.flight_times.substr(4, 2) + "-" + billPreview.flight_times.substr(6)); //进境日期
        $("#del_date").text(moment(billPreview.d_date).format("YYYY-MM-DD"));//申报日期

        var import_port = sw.dict.customs[billPreview.i_e_port];
        if (typeof(import_port) == "undefined" || null == import_port || "" == import_port) import_port = billPreview.i_e_port;
        $("#import_port").text(import_port);//进口口岸

        var del_port = sw.dict.customs[billPreview.customs];
        if (typeof(del_port) == "undefined" || null == del_port || "" == del_port) del_port = billPreview.customs;
        $("#del_port").text(del_port);//申报地海关

        var transport_mode = sw.dict.trafMode[billPreview.traf_mode];
        if (typeof(transport_mode) == "undefined" || null == transport_mode || "" == transport_mode) transport_mode = billPreview.traf_mode;
        $("#transport_mode").text(transport_mode);//运输方式

        $("#transport_name").text(billPreview.traf_name);//运输和工具名称

        var origin_country = sw.dict.countryArea[billPreview.trade_country];
        if (typeof(origin_country) == "undefined" || null == origin_country || "" == origin_country) origin_country = billPreview.trade_country;
        $("#origin_country").text(origin_country);//起运国

        $("#pack_no").text(billPreview.packno);//件数
        $("#weight").text(billPreview.gross_wt);//毛重

        if (typeof(assBillList) != "undefined" && null != assBillList && "" != assBillList) {
            for (var i = 0; i < assBillList.length; i++) {
                var order = i + 1;
                var ass_bill_no = assBillList[i].ass_bill_no;
                var pack_no = assBillList[i].g_no == 1 ? "1" : "0";
                var entry_id = assBillList[i].entry_id;
                if (typeof(entry_id) == "undefined" || null == entry_id || "" == entry_id) entry_id = "";
                var code_ts = assBillList[i].code_ts;
                var rate = (sw.dict.postalRate[code_ts] * 100).toFixed(2);

                var origin_country = sw.dict.countryArea[assBillList[i].origin_country];
                if (typeof(origin_country) == "undefined" || null == origin_country || "" == origin_country) origin_country = assBillList[i].origin_country;
                var trade_country = sw.dict.countryArea[assBillList[i].trade_country];
                if (typeof(trade_country) == "undefined" || null == trade_country || "" == trade_country) trade_country = assBillList[i].trade_country;
                var declare_status = sw.dict.status[assBillList[i].declare_status];
                if (typeof(declare_status) == "undefined" || null == declare_status || "" == declare_status) declare_status = assBillList[i].declare_status;

                var total_estimate = assBillList[i].total_estimate;
                var tax_estimate = 0;
                if (total_estimate > 50) {
                    tax_estimate = parseFloat(assBillList[i].tax_estimate).toFixed(2);
                }
                var decl_total = parseFloat(assBillList[i].decl_total).toFixed(4);

                var str = "<tr class='text-center'><td >" + order + "</td><td>"
                    + entry_id + "</td><td>"
                    + ass_bill_no + "</td><td>"
                    + code_ts + "</td><td>"
                    + assBillList[i].g_name + "</td><td>"
                    + pack_no + "</td><td>"
                    + assBillList[i].g_grosswt + "</td><td>"
                    + rate + "</td><td>"
                    + decl_total + "</td><td>"
                    + tax_estimate + "</td><td>"
                    + origin_country + "</td><td>"
                    + assBillList[i].send_name + "</td><td>"
                    + trade_country + "</td><td>"
                    + assBillList[i].receive_name + "</td><td>"
                    + declare_status + "</td></tr>";
                $("#previewId").append(str);
            }
        }

    },

    printBill: function () {
        var paramData = $(this).attr("param-data");
        localStorage.setItem("bill_inquiry_param", paramData);
        var printWindow = window.open(sw.resolve("admin/page?p=express/import_b/bill_inquiry_print"), "printWindow");
    }
};
