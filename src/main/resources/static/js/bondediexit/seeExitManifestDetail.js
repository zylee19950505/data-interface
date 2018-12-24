// 非空判断
function isNotEmpty(obj) {
    if (typeof(obj) == "undefined" || null == obj || "" == obj) {
        return false;
    }
    return true;
}

// 错误提示
function hasError(errorMsg) {
    $("#errorMsg").html(errorMsg).removeClass("hidden");
}

// 清楚错误提示
function clearError() {
    $("#errorMsg").html("").addClass("hidden");
}

// Select2初始化
function selecterInitDetail(selectId, value, data) {
    $("#" + selectId).select2({
        data: $.map(data, function (val, key) {
            var obj = {
                id: key,
                text: val + "[" + key + "]"
            }
            return obj;
        }),
        placeholder: value,
        dropdownParent: $("#dialog-popup")
    }).val(value).trigger('change');
}

// 表头变化
var headChangeKeyValEManifest = {};

// 表体变化
var listChangeKeyValsEManifest = {};

// 表体ID匹配正则
var pattern = /^.*_[0-9]+$/;

// 计算表体申报总价
function sumDeclTotal(dVal, qty, gno, listChangeKeyVal) {
    var declTotal = parseFloat(dVal * qty).toFixed(5);
    $("#total_price_" + gno).val(declTotal);
    listChangeKeyVal["total_price"] = $("#total_price_" + gno).val();
}

function inputChange(id) {
    $(".detailPage input,select").change(function () {
        var key = $(this).attr("id");
        var val = $(this).val();
        if (!isNotEmpty(val)) {
            return;
        }
        if (pattern.test(key)) {
            var gno = key.substring(key.lastIndexOf("_") + 1, key.length);
            var keys = key.substring(0, key.lastIndexOf("_"));
            var listChangeKeyVal;
            if (listChangeKeyValsEManifest[gno]) {
                listChangeKeyVal = listChangeKeyValsEManifest[gno];
            } else {
                listChangeKeyVal = {};
            }
            // 修改字段为单价
            if (keys == "price") {// 单价
                var dVal = parseFloat(val);
                var qty = parseFloat($("#g_qty_" + gno).val());
                sumDeclTotal(dVal, qty, gno, listChangeKeyVal);
            } else if (keys == "g_qty") {// 数量
                var qty = parseFloat(val);
                var dVal = parseFloat($("#price_" + gno).val());
                sumDeclTotal(dVal, qty, gno, listChangeKeyVal);
            }
            // 记录变更信息
            listChangeKeyVal[keys] = val;
            listChangeKeyVal["g_no"] = gno;
            listChangeKeyVal["entryhead_guid"] = id;
            listChangeKeyValsEManifest[gno] = listChangeKeyVal;
        } else {
            headChangeKeyValEManifest[key] = val;
        }
    }).focus(function () {
        clearError();
    });
}


sw.page.modules["bondediexit/seeExitManifestDetail"] = sw.page.modules["bondediexit/seeExitManifestDetail"] || {
    detailParam: {
        url: "",
        callBackUrl: "",
        isShowError: true,
        isEdit: "true",
        disableField: [
            "auto_id",
            "manifest_no",
            "customs_code",
            "biz_type",
            "biz_mode",
            "i_e_flag",
            "i_e_mark"
        ]
    },
    // 保存成功时回调查询
    callBackQuery: function () {
        sw.page.modules[this.detailParam.callBackUrl].query();
    },
    // 取消返回
    cancel: function () {
        $("#dialog-popup").modal("hide");
    },
    // 禁用字段
    disabledFieldInput: function () {
        var disableField = sw.page.modules["bondediexit/seeExitManifestDetail"].detailParam.disableField;
        for (i = 0; i < disableField.length; i++) {
            $(".detailPage input[id^=" + disableField[i] + "],select[id^=" + disableField[i] + "]").attr("disabled", "disabled");
        }
    },
    // 装载表头信息
    fillManifestInfo: function (entryHead) {
        $("#auto_id").val(entryHead.auto_id);
        $("#bill_nos").val(entryHead.bill_nos);
        $("#manifest_no").val(entryHead.manifest_no);
        $("#customs_code").val(entryHead.customs_code);
        $("#biz_type").val(entryHead.biz_type);
        $("#biz_mode").val(entryHead.biz_mode);
        $("#i_e_flag").val(entryHead.i_e_flag);
        $("#i_e_mark").val(entryHead.i_e_mark);
        $("#trade_mode").val(entryHead.trade_mode);
        $("#delivery_way").val(entryHead.delivery_way);
        $("#start_land").val(entryHead.start_land);
        $("#goal_land").val(entryHead.goal_land);
        $("#goods_wt").val(entryHead.goods_wt);
        $("#fact_weight").val(entryHead.fact_weight);
        $("#pack_no").val(entryHead.pack_no);
    },

    //加载表体信息
    fillEntryListInfo: function (entryLists) {
        for (var i = 0; i < entryLists.length; i++) {
            var str =
                "<tr><td ><input class=\"form-control input-sm\" maxlength=\"1000\" id='list_total_logistics_no_" + i + "' value='" + entryLists[i].total_logistics_no + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"1000\" id='list_totalSum_" + i + "' value='" + parseFloat(entryLists[i].totalSum).toFixed(5) + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"1000\" id='list_releaseSum_" + i + "' value='" + parseFloat(entryLists[i].releaseSum).toFixed(5) + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"1000\" id='list_releasePackSum_" + i + "' value='" + parseFloat(entryLists[i].releasePackSum).toFixed(5) + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"1000\" id='list_grossWtSum_" + i + "' value='" + parseFloat(entryLists[i].grossWtSum).toFixed(5) + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"1000\" id='list_etWtSum_" + i + "' value='" + parseFloat(entryLists[i].netWtSum).toFixed(5) + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"1000\" id='list_goodsValueSum_" + i + "' value='" + parseFloat(entryLists[i].goodsValueSum).toFixed(5) + "' /></td></tr>";
            $("#entryList").append(str);
        }
    },

    // // 标记问题字段
    // errorMessageShow: function (vertify) {
    //     if (vertify) {
    //         var result = JSON.parse(vertify.result);
    //         var gno = result.gno;
    //         var field = result.field;
    //         if (isNotEmpty(gno)) {
    //             $("#" + field + "_" + gno).addClass("bg-red");
    //             $("#" + field + "_" + gno).parent().find(".select2-selection--single").addClass("bg-red");
    //         } else {
    //             $("#" + field).addClass("bg-red");
    //             $("#" + field).parent().find(".select2-selection--single").addClass("bg-red");
    //         }
    //     }
    // },

    // 保存订单编辑信息
    saveExitManifestInfo: function () {
        if (!this.valiFieldInventory()) {
            return;
        }
        var entryData = {
            auto_id: $("#auto_id").val(),
            bill_nos: $("#bill_nos").val(),
            manifest_no: $("#manifest_no").val(),
            customs_code: $("#customs_code").val(),
            biz_type: $("#biz_type").val(),
            biz_mode: $("#biz_mode").val(),
            i_e_flag: $("#i_e_flag").val(),
            i_e_mark: $("#i_e_mark").val(),
            trade_mode: $("#rade_mode").val(),
            delivery_way: $("#delivery_way").val(),
            start_lan: $("#start_land").val(),
            goal_land: $("#goal_land").val()

        };
        sw.ajax(this.detailParam.url, "POST", "entryJson=" + encodeURIComponent(JSON.stringify(entryData)), function (rsp) {
            if (rsp.data.result) {
                sw.page.modules["bondediexit/seeExitManifestDetail"].cancel();
                setTimeout(function () {
                    sw.alert(rsp.data.msg, "提示", null, "modal-info");
                }, 500);
                sw.page.modules["bondediexit/seeExitManifestDetail"].callBackQuery();
            } else {
                hasError(rsp.data.msg);
            }
        }, function (status, err, xhr) {
            hasError(xhr.data);
        });
    },

    // 查询订单详情
    query: function () {
        // 表头变化
        headChangeKeyValEManifest = {};
        // 表体变化
        listChangeKeyValsEManifest = {};

        //从路径上找参数
        var param = sw.getPageParams("bondediexit/seeExitManifestDetail");
        var totalLogisticsNo = param.submitKeys;

        var data = {
            totalLogisticsNo: totalLogisticsNo
        };
        $.ajax({
            method: "GET",
            url: "api/bondediexit/getExitInventoryData",
            data: data,
            success: function (data, status, xhr) {
                if (xhr.status == 200) {
                    var entryModule = sw.page.modules["bondediexit/seeExitManifestDetail"];

                    var entryHead = data.data.bondInvtBsc;
                    var entryLists = data.data.bondInvtDtList;
                    // var vertify = data.data.verify;

                    if (isNotEmpty(entryHead)) {
                        entryModule.fillManifestInfo(entryHead);
                    }
                    if (isNotEmpty(entryLists)) {
                        entryModule.fillEntryListInfo(entryLists);
                    }
                    // 根据错误字段中的值加高亮显示
                    // if (entryModule.detailParam.isShowError) {
                    //     entryModule.errorMessageShow(vertify);
                    // }
                    // headChangeKeyValEManifest["entryhead_guid"] = param.guid;
                    // 添加输入框内容变更事件，捕获数据变更信息
                    // inputChange(param.submitKeys);
                    entryModule.disabledFieldInput();
                }
            }
        });
    },
    //校验
    valiFieldInventory: function () {
        // 校验表头
        var validataHeadField = {
            "manifest_no": "核放单号",
            "customs_code": "申报地海关",
            "biz_type": "申报地海关",
            "biz_mode": "申报地海关",
            "i_e_flag": "申报地海关",
            "i_e_mark": "出入区标志"
        };

        var fieldId, fieldName, fieldVal;
        // 表头数据校验
        for (fieldId in validataHeadField) {
            fieldName = validataHeadField[fieldId];
            fieldVal = $("#" + fieldId).val();
            if (!isNotEmpty(fieldVal)) {
                hasError("[" + fieldName + "]不能为空");
                return false;
            }
        }

        return true;
    },

    init: function () {
        //从路径上获取参数
        var param = sw.getPageParams("bondediexit/seeExitManifestDetail");
        var totalLogisticsNo = param.submitKeys;
        var type = param.type;
        var isEdit = param.isEdit;
        var mark = param.mark;

        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyy-mm-dd",
            autoclose: true
        });

        switch (type) {
            //订单查询
            case "HFDBTXX": {
                // 不可编辑状态
                if (isEdit == "true") {
                    this.detailParam.disableField = [
                        //当前禁用的字段,需要禁用的字段值在这里改
                        "auto_id",
                        "manifest_no",//核放单号
                        "customs_code",//申报地海关
                        "biz_type",
                        "biz_mode",
                        "i_e_flag",
                        "i_e_mark",//出入区标志
                        "trade_mode",//出入区方式
                        "delivery_way",//运载方式
                        "start_land",//起始地
                        "goal_land",//目的地
                        "goods_wt",//总毛重
                        "fact_weight",//总净重
                        "pack_no"//总件数
                    ];
                }
                //保存的路径
                this.detailParam.url = "/api/bondediexit/saveExitManifest";
                //返回之后的查询路径
                this.detailParam.callBackUrl = "bondediexit/crtExitManifest";
                this.detailParam.isShowError = false;
                break;
            }
            //逻辑校验(预留)
            case "LJJY": {
                // 不可编辑状态

            }
        } // 不可编辑状态
        if (isEdit == "false") {
            this.detailParam.disableField = [

            ];
            // 屏蔽保存取消按钮
            $("#btnDiv").addClass("hidden");
        } else {
            // 显示保存取消按钮
            $("#btnDiv").removeClass("hidden");
        }

        if(mark == "crt"){
            this.query(mark);

            //点击保存(未确认数据)
            $("#ws-page-apply").click(function () {
                sw.page.modules["bondediexit/seeExitManifestDetail"].saveExitManifestInfo();
            });
        }else if(mark == "upd"){
            this.query(mark);

            $("#ws-page-apply").click(function () {
                sw.page.modules["bondediexit/seeExitManifestDetail"].updateExitManifestInfo();
            });
        }

        //点击取消
        $("#ws-page-back").click(function () {
            sw.page.modules["bondediexit/seeExitManifestDetail"].cancel();
        });
    },


}