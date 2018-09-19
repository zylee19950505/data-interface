// 非空判断
function isNotEmpty(obj) {
    /*<![CDATA[*/
    if (typeof(obj) == "undefined" || null == obj || "" == obj) {
        return false;
    }
    /*]]>*/
    return true;
}

// 错误提示
function hasError(errorMsg) {
    /*<![CDATA[*/
    $("#errorMsg").html(errorMsg).removeClass("hidden");
    /*]]>*/
}

// 清楚错误提示
function clearError() {
    /*<![CDATA[*/
    $("#errorMsg").html("").addClass("hidden");
    /*]]>*/
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
var headChangeKeyVal = {};

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
            if (listChangeKeyVals[gno]) {
                listChangeKeyVal = listChangeKeyVals[gno];
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
            listChangeKeyVals[gno] = listChangeKeyVal;
        } else {
            headChangeKeyVal[key] = val;
        }
    }).focus(function () {
        clearError();
    });
}


sw.page.modules["manifest/seeManifestInfo"] = sw.page.modules["manifest/seeManifestInfo"] || {
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
            "i_e_mark",

            "trade_mode",
            "delivery_way",

            "start_land",
            "goal_land",

            "goods_wt",
            "fact_weight",
            "pack_no",
            "sum_goods_value",

            "m_satus",
            "b_status",
            "status",
            "port_status",

            "input_name",
            "input_code",
            "trade_name",
            "trade_code",

            "app_person",
            "region_code",
            "plat_from",
            "note",
            "extend_field_3",

            "car_no",
            "car_wt",
            "ic_code"
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
        var disableField = sw.page.modules["manifest/seeManifestInfo"].detailParam.disableField;
        for (i = 0; i < disableField.length; i++) {
            $(".detailPage input[id^=" + disableField[i] + "],select[id^=" + disableField[i] + "]").attr("disabled", "disabled");
        }
    },
    // 装载表头信息
    fillManifestInfo: function (entryHead) {
        $("#auto_id").val(entryHead.auto_id);
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
        $("#sum_goods_value").val(entryHead.sum_goods_value);

        $("#m_status").val(entryHead.m_status);
        $("#b_status").val(entryHead.b_status);
        $("#status").val(entryHead.status);
        $("#port_status").val(entryHead.port_status);

        $("#input_name").val(entryHead.input_name);
        $("#input_code").val(entryHead.input_code);
        $("#trade_name").val(entryHead.trade_name);
        $("#trade_code").val(entryHead.trade_code);

        $("#app_person").val(entryHead.app_person);
        $("#region_code").val(entryHead.region_code);
        $("#plat_from").val(entryHead.plat_from);
        $("#note").val(entryHead.note);
        $("#extend_field_3").val(entryHead.extend_field_3);

        $("#car_no").val(entryHead.car_no);
        $("#car_wt").val(entryHead.car_wt);
        $("#ic_code").val(entryHead.ic_code);

    },

    // 标记问题字段
    errorMessageShow: function (vertify) {
        if (vertify) {
            var result = JSON.parse(vertify.result);
            var gno = result.gno;
            var field = result.field;
            if (isNotEmpty(gno)) {
                $("#" + field + "_" + gno).addClass("bg-red");
                $("#" + field + "_" + gno).parent().find(".select2-selection--single").addClass("bg-red");
            } else {
                $("#" + field).addClass("bg-red");
                $("#" + field).parent().find(".select2-selection--single").addClass("bg-red");
            }
        }
    },

    // 保存订单编辑信息
    saveManifestInfo: function (totalLogisticsNo) {
        debugger;
        if (!this.valiFieldInventory()) {
            return;
        }
        var entryData = {
            auto_id: $("#auto_id").val(),
            manifest_no: $("#manifest_no").val(),
            customs_code: $("#customs_code").val(),
            biz_type: $("#biz_type").val(),
            biz_mode: $("#biz_mode").val(),
            i_e_flag: $("#i_e_flag").val(),
            i_e_mark: $("#i_e_mark").val(),

            trade_mode: $("#trade_mode").val(),
            delivery_way: $("#delivery_way").val(),

            start_land: $("#start_land").val(),
            goal_land: $("#goal_land").val(),

            goods_wt: $("#goods_wt").val(),
            fact_weight: $("#fact_weight").val(),
            pack_no: $("#pack_no").val(),
            sum_goods_value: $("#sum_goods_value").val(),

            m_status: $("#m_status").val(),
            b_status: $("#b_status").val(),
            status: $("#status").val(),
            port_status: $("#port_status").val(),

            input_name: $("#input_name").val(),
            input_code: $("#input_code").val(),
            trade_name: $("#trade_name").val(),
            trade_code: $("#trade_code").val(),

            app_person: $("#app_person").val(),
            region_code: $("#region_code").val(),
            plat_from: $("#plat_from").val(),
            note: $("#note").val(),
            extend_field_3: $("#extend_field_3").val(),

            car_no: $("#car_no").val(),
            car_wt: $("#car_wt").val(),
            ic_code: $("#ic_code").val()
        };
        sw.ajax(this.detailParam.url, "POST", "entryJson=" + encodeURIComponent(JSON.stringify(entryData)), function (rsp) {
            if (rsp.data.result) {
                sw.page.modules["manifest/seeManifestInfo"].cancel();
                setTimeout(function () {
                    sw.alert(rsp.data.msg, "提示", null, "modal-info");
                }, 500);
                sw.page.modules["manifest/seeManifestInfo"].callBackQuery();
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
        headChangeKeyVal = {};
        //从路径上找参数
        var param = sw.getPageParams("manifest/seeManifestInfo");
        var totalLogisticsNo = param.submitKeys;
        var data = {
            totalLogisticsNo: totalLogisticsNo
        };
        $.ajax({
            method: "GET",
            url: "api/manifest/manifestCreate",
            data: data,
            success: function (data, status, xhr) {
                if (xhr.status == 200) {
                    var entryModule = sw.page.modules["manifest/seeManifestInfo"];
                    var entryHead = data.data;
                    var vertify = data.data.verify;

                    if (isNotEmpty(entryHead)) {
                        entryModule.fillManifestInfo(entryHead);
                    }
                    // 根据错误字段中的值加高亮显示
                    if (entryModule.detailParam.isShowError) {
                        entryModule.errorMessageShow(vertify);
                    }
                    headChangeKeyVal["entryhead_guid"] = param.guid;
                    // 添加输入框内容变更事件，捕获数据变更信息
                    inputChange(param.submitKeys);
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
            "i_e_mark": "出入区标志",

            "trade_mode": "出入区方式",
            "delivery_way": "运载方式",

            "start_land": "起始地",
            "goal_land": "目的地",

            "goods_wt": "总毛重",
            "fact_weight": "总净重",
            "pack_no": "总件数",
            "sum_goods_value": "总货值",

            "m_status": "人工操作状态",
            "b_status": "b_status",
            "status": "status",
            "port_status": "卡口状态",

            "input_name": "录入单位名称",
            "input_code": "录入单位代码",
            "trade_name": "trade_name",
            "trade_code": "trade_code",

            "app_person": "申请人",
            "region_code": "区域标志",
            "plat_from": "数据来源",
            "extend_field_3": "extend_field_3",

            "car_no": "车牌号",
            "car_wt": "车自重",
            "ic_code": "IC卡号"
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
        var param = sw.getPageParams("manifest/seeManifestInfo");
        var totalLogisticsNo = param.submitKeys;
        var type = param.type;
        var isEdit = param.isEdit;
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
                        "pack_no",//总件数
                        "sum_goods_value",//总货值

                        "m_status",
                        "b_status",
                        "status",
                        "port_status",

                        "input_name",
                        "input_code",
                        "trade_name",
                        "trade_code",

                        "app_person",
                        "region_code",
                        "plat_from",
                        "note",
                        "extend_field_3"

                    ];
                }
                //保存的路径
                this.detailParam.url = "/api/manifest/saveManifestInfo";
                //返回之后的查询路径
                this.detailParam.callBackUrl = "manifest/detailQuery";
                this.detailParam.isShowError = false;
                break;
            }
            //逻辑校验(预留)
            case "LJJY": {
                // 不可编辑状态

            }
        } // 不可编辑状态
        if (isEdit == "false") {
            this.detailParam.disableField = [];
            // 屏蔽保存取消按钮
            $("#btnDiv").addClass("hidden");
        } else {
            // 显示保存取消按钮
            $("#btnDiv").removeClass("hidden");
        }
        this.query();
        //点击保存(未确认数据)
        $("#ws-page-apply").click(function () {
            sw.page.modules["manifest/seeManifestInfo"].saveManifestInfo(totalLogisticsNo);
        });
        //点击取消
        $("#ws-page-back").click(function () {
            sw.page.modules["manifest/seeManifestInfo"].cancel();
        });
    },


}