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

// 表体变化
var listChangeKeyVals = {};

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


sw.page.modules["detailmanage/seeInventoryDetail"] = sw.page.modules["detailmanage/seeInventoryDetail"] || {
    detailParam: {
        url: "",
        callBackUrl: "",
        isShowError: true,
        isEdit: "true",
        disableField: [
            "order_no",
            "cop_no",
            "logistics_no",
            "invt_no",
            "pre_no",
            "ebp_code",
            "ebp_name",
            "ebc_code",
            "ebc_name",
            "assure_code",
            "customs_code",
            "port_code",
            "ie_date",
            "buyer_id_number",
            "buyer_name",
            "buyer_telephone",
            "consignee_address",
            "freight",
            "wrap_type",
            "agent_code",
            "agent_name",
            "traf_mode",
            "traf_no",
            "voyage_no",
            "bill_no",
            "country",
            "gross_weight",
            "note",
            "g_num",
            "g_name",
            "g_code",
            "g_model",
            "g_qty",
            "g_unit",
            "qty_1",
            "unit_1",
            "qty_2",
            "unit_2",
            "total_price",
            "no1"
        ]
    },
    // 保存成功时回调查询
    callBackQuery: function (orderNo) {
        sw.page.modules[this.detailParam.callBackUrl].query();
    },
    // 取消返回
    cancel: function () {
        $("#dialog-popup").modal("hide");
    },
    // 禁用字段
    disabledFieldInput: function () {
        var disableField = sw.page.modules["detailmanage/seeInventoryDetail"].detailParam.disableField;
        for (i = 0; i < disableField.length; i++) {
            $(".detailPage input[id^=" + disableField[i] + "],select[id^=" + disableField[i] + "]").attr("disabled", "disabled");
        }
    },
    // 装载表头信息
    fillEntryHeadInfo: function (entryHead) {
        $("#order_no").val(entryHead.order_no);
        $("#cop_no").val(entryHead.cop_no);
        $("#logistics_no").val(entryHead.logistics_no);
        $("#invt_no").val(entryHead.invt_no);
        $("#pre_no").val(entryHead.pre_no);
        $("#ebp_code").val(entryHead.ebp_code);
        $("#ebp_name").val(entryHead.ebp_name);
        $("#ebc_code").val(entryHead.ebc_code);
        $("#ebc_name").val(entryHead.ebc_name);
        $("#assure_code").val(entryHead.assure_code);
        selecterInitDetail("customs_code", entryHead.customs_code, sw.dict.customs)
        selecterInitDetail("port_code", entryHead.port_code, sw.dict.customs)
        $("#ie_date").val(moment(entryHead.ie_date).format("YYYY-MM-DD"));
        $("#buyer_id_number").val(entryHead.buyer_id_number);
        $("#buyer_name").val(entryHead.buyer_name);
        $("#buyer_telephone").val(entryHead.buyer_telephone);
        $("#consignee_address").val(entryHead.consignee_address);
        $("#freight").val(parseFloat(entryHead.freight).toFixed(5));
        selecterInitDetail("wrap_type", entryHead.wrap_type, sw.dict.packType)
        $("#agent_code").val(entryHead.agent_code);
        $("#agent_name").val(entryHead.agent_name);
        selecterInitDetail("traf_mode", entryHead.traf_mode, sw.dict.trafMode)
        $("#traf_no").val(entryHead.traf_no);
        $("#voyage_no").val(entryHead.voyage_no);
        $("#bill_no").val(entryHead.bill_no);
        selecterInitDetail("country", entryHead.country, sw.dict.countryArea);
        $("#gross_weight").val(parseFloat(entryHead.gross_weight).toFixed(5));
        $("#note").val(entryHead.note);
    },

    //加载表体信息
    fillEntryListInfo: function (entryLists) {
        for (var i = 0; i < entryLists.length; i++) {
            var g_num = entryLists[i].g_num;
            var str =
                "<tr><td ><input class=\"form-control input-sm\" maxlength=\"4\" id='g_num_" + g_num + "' value='" + entryLists[i].g_num + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"250\" id='g_name_" + g_num + "' value='" + entryLists[i].g_name + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"10\" id='g_code_" + g_num + "' value='" + entryLists[i].g_code + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"250\" id='g_model_" + g_num + "' value='" + entryLists[i].g_model + "' /></td>" +
                "<td ><select class=\"form-control input-sm\" style=\"width:100%\"  maxlength=\"10\" id='country_" + g_num + "' value='" + entryLists[i].country + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"19\" id='g_qty_" + g_num + "' value='" + parseFloat(entryLists[i].qty).toFixed(5) + "' /></td>" +
                "<td ><select class=\"form-control input-sm\"  style=\"width:100%\" maxlength=\"10\" id='g_unit_" + g_num + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"19\" id='qty_1_" + g_num + "' value='" + parseFloat(entryLists[i].qty1).toFixed(5) + "' /></td>" +
                "<td ><select class=\"form-control input-sm\"  style=\"width:100%\" maxlength=\"10\" id='unit_1_" + g_num + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"19\" id='qty_2_" + g_num + "' value='" + parseFloat(entryLists[i].qty2).toFixed(5) + "' /></td>" +
                "<td ><select class=\"form-control input-sm\"  style=\"width:100%\" maxlength=\"10\" id='unit_2_" + g_num + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"19\" id='price_" + g_num + "' value='" + parseFloat(entryLists[i].price).toFixed(5) + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"19\" id='total_price_" + g_num + "' value='" + parseFloat(entryLists[i].total_price).toFixed(5) + "' /></td></tr>";
            $("#entryList").append(str);
            selecterInitDetail("country_" + g_num, entryLists[i].country, sw.dict.countryArea);
            selecterInitDetail("g_unit_" + g_num, entryLists[i].unit, sw.dict.unitCodes);
            selecterInitDetail("unit_1_" + g_num, entryLists[i].unit1, sw.dict.unitCodes);
            selecterInitDetail("unit_2_" + g_num, entryLists[i].unit2, sw.dict.unitCodes);
        }
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
    saveEntryInfo: function (orderNo, type, ieFlag) {
        if (!this.valiFieldInventory()) {
            return;
        }
        var entryLists = new Array();
        for (var key in listChangeKeyVals) {
            entryLists.push(listChangeKeyVals[key]);
        }

        var entryData = {
            entryHead: headChangeKeyVal,
            entryList: entryLists
        };
        sw.ajax(this.detailParam.url, "POST", "entryJson=" + encodeURIComponent(JSON.stringify(entryData)), function (rsp) {
            if (rsp.data.result) {
                sw.page.modules["detailmanage/seeInventoryDetail"].cancel();
                setTimeout(function () {
                    sw.alert(rsp.data.msg, "提示", null, "modal-info");
                }, 500);
                sw.page.modules["detailmanage/seeInventoryDetail"].callBackQuery(orderNo);
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
        // 表体变化
        listChangeKeyVals = {};

        //从路径上找参数
        var param = sw.getPageParams("detailmanage/seeInventoryDetail");
        var guid = param.guid;
        var data = {
            guid: guid
        };
        $.ajax({
            method: "GET",
            url: "api/detailManage/seeOrderDetail",
            data: data,
            success: function (data, status, xhr) {
                if (xhr.status == 200) {
                    var entryModule = sw.page.modules["detailmanage/seeInventoryDetail"];

                    var entryHead = data.data.impInventoryHead;
                    var entryLists = data.data.impInventoryBodies;
                    var vertify = data.data.verify;

                    if (isNotEmpty(entryHead)) {
                        entryModule.fillEntryHeadInfo(entryHead);
                    }
                    if (isNotEmpty(entryLists)) {
                        entryModule.fillEntryListInfo(entryLists);
                    }
                    // 根据错误字段中的值加高亮显示
                    if (entryModule.detailParam.isShowError) {
                        entryModule.errorMessageShow(vertify);
                    }
                    headChangeKeyVal["entryhead_guid"] = param.guid;
                    // 添加输入框内容变更事件，捕获数据变更信息
                    inputChange(param.guid);
                    entryModule.disabledFieldInput();
                }
            }
        });
    },
    //校验
    valiFieldInventory: function () {
        // 校验表头
        var validataHeadField = {
            "order_no": "订单编号",
            "cop_no": "企业内部编号",
            "logistics_no": "物流运单编号",
            // "invt_no": "海关清单编号",
            // "pre_no": "电子口岸标识编号",
            "ebp_code": "电商平台代码",
            "ebp_name": "电商平台名称",
            "ebc_code": "电商企业代码",
            "ebc_name": "电商企业名称",
            "assure_code": "担保企业编号",
            "customs_code": "申报海关代码",
            "port_code": "口岸海关代码",
            "ie_date": "进口日期",
            "buyer_id_number": "订购人证件号码",
            "buyer_name": "订购人姓名",
            "buyer_telephone": "订购人电话",
            "consignee_address": "收件地址",
            "freight": "运费",
            // "wrap_type": "包装种类",
            "agent_code": "申报企业代码",
            "agent_name": "申报企业名称",
            "traf_mode": "运输方式",
            "traf_no": "运输工具编号",
            "voyage_no": "航班航次号",
            "bill_no": "提运单号",
            "country": "起运国（地区）",
            "gross_weight": "净重"
            // "note":"备注",
        };

        // 校验表体
        var validataListField = {
            "g_num": "序号",
            "g_name": "商品名称",
            "g_code": "商品编码",
            "g_model": "商品规格/型号",
            "country": "原产国（地区）",
            "g_qty": "数量",
            "g_unit": "计量单位",
            "qty_1": "第一法定数量",
            "unit_1": "第一法定单位",
            "qty_2": "第二法定数量",
            // "unit2": "第二法定单位",
            "price": "单价",
            "total_price": "总价"
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

        var gno, fields;
        // 表体数据校验
        for (var key in validataListField) {
            fields = $("input[id^=" + key + "],select[id^=" + key + "]");
            for (var i = 0; i < fields.length; i++) {
                fieldId = $(fields[i]).attr("id");
                fieldVal = $(fields[i]).val();
                gno = fieldId.substring(fieldId.lastIndexOf("_") + 1, fieldId.length);
                if (!isNotEmpty(fieldVal)) {
                    hasError("序号[" + gno + "]-[" + validataListField[key] + "]不能为空");
                    return false;
                }
            }
        }

        return true;
    },

    init: function () {
        //从路径上获取参数
        var param = sw.getPageParams("detailmanage/seeInventoryDetail");
        var guid = param.guid;
        var orderNo = param.orderNo;
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
            case "QDCX": {
                // 不可编辑状态
                if (isEdit == "true") {
                    this.detailParam.disableField = [
                        //当前禁用的字段,需要禁用的字段值在这里改

                        "order_no",//订单编号。
                        "cop_no",//企业内部编号
                        "logistics_no",//物流运单编号
                        "invt_no",//海关清单编号
                        "pre_no",//电子口岸标识编号
                        "g_num",//表体序号
                        "no1"

                    ];
                }
                //保存的路径
                this.detailParam.url = "/api/detailManage/saveInventoryDetail";
                //返回之后的查询路径
                this.detailParam.callBackUrl = "detailmanage/detailQuery";
                this.detailParam.isShowError = false;
                break;
            }
            //逻辑校验(预留)
            case "LJJY": {
                // 不可编辑状态
                if (isEdit == "true") {
                    this.detailParam.disableField = [
                        //当前禁用的字段,需要禁用的字段值在这里改

                        "order_no",//订单编号。
                        "cop_no",//企业内部编号
                        "logistics_no",//物流运单编号
                        "invt_no",//海关清单编号
                        "pre_no",//电子口岸标识编号
                        "g_num",//表体序号

                    ];
                }
                //保存的路径
                this.detailParam.url = "/api/inventory/saveLogicalDetail";
                //返回之后的查询路径
                this.detailParam.callBackUrl = "detailmanage/InventoryLogicVerify";
                this.detailParam.isShowError = false;
                break;
            }

        } // 不可编辑状态
        if (isEdit == "false") {
            this.detailParam.disableField = [
                // "ass_bill_no",
                // "owner_code",
                // "district_code",
            ];
            // 屏蔽保存取消按钮
            $("#btnDiv").addClass("hidden");
        } else {
            // 显示保存取消按钮
            $("#btnDiv").removeClass("hidden");
        }
        // 查询详情
        this.query();

        //点击保存(未确认数据)
        $("#ws-page-apply").click(function () {
            sw.page.modules["detailmanage/seeInventoryDetail"].saveEntryInfo(orderNo, type, sw.ie);
        });
        //点击取消
        $("#ws-page-back").click(function () {
            sw.page.modules["detailmanage/seeInventoryDetail"].cancel();
        });
    },


}