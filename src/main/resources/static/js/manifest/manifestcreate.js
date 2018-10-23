/**
 * Created on 2018-09-14
 * 新建核放单
 */
sw.page.modules["manifest/manifestcreate"] = sw.page.modules["manifest/manifestcreate"] || {
    // 订单申报列表查询
    query: function () {
        // 获取查询表单参数
        // var startFlightTimes = $("[name='startFlightTimes']").val();
        // var endFlightTimes = $("[name='endFlightTimes']").val();
        var billNo = $("[name='billNo']").val();
        // var returnStatus = $("[name='returnStatus']").val();

        // 拼接URL及参数
        var url = sw.serializeObjectToURL("api/manifest/queryManifestCreate", {
            // startFlightTimes: startFlightTimes,//导入时间
            // endFlightTimes: endFlightTimes,//导入时间
            billNo: billNo,//提运单号
            // returnStatus: returnStatus//业务状态
        });

        // 数据表
        sw.datatable("#query-manifestCreate-table", {
            ajax: url,
            lengthMenu: [[50, 100, 1000], [50, 100, 1000]],
            searching: false,//开启本地搜索
            columns: [
                //还需判断下状态
                {

                    label: '<input type="checkbox" name="cb-check-all"/>',
                    orderable: false,
                    data: null,
                    render: function (data, type, row) {
                        if (row.is_manifest == "N" || isEmpty(row.is_manifest)) {
                            return '<input type="checkbox" class="submitKey" value="' +
                                row.total_logistics_no + '" />';
                        }
                        else {
                            return "";
                        }
                    }
                },
                {data: "total_logistics_no", label: "提运单号"},//订单编号要点击查看订单详情
                {data: "totalSum", label: "总单量"},
                {data: "releaseSum", label: "放行单量"},
                {data: "releasePackSum", label: "放行件数"},
                {data: "grossWtSum", label: "放行毛重"},
                {data: "netWtSum", label: "放行净重"},
                {data: "goodsValueSum", label: "放行货值"}
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
            sw.alert("请先勾选要新建的核放单信息！");
            return;
        }
        var postData = {
            submitKeys: submitKeys
        };

        sw.pageModule('manifest/manifestcreate').seeInventoryRec(submitKeys);
    },

    seeInventoryRec: function (submitKeys) {
        var url = "manifest/seeManifestInfo?type=HFDBTXX&isEdit=true&submitKeys=" + submitKeys;
        sw.modelPopup(url, "核放单表头信息", false, 1000, 700);
    },

    privateManifestInfo: function () {
        var url = "manifest/seeNewManifestInfo?type=HFDBTXX&isEdit=true";
        sw.modelPopup(url, "核放单信息", false, 1000, 500);
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
        $("[ws-download]").unbind("click").click(this.privateManifestInfo);
        $table = $("#query-manifestCreate-table");
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

