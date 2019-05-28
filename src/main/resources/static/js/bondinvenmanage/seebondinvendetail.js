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
function selecterInitBondInven(selectId, value, data) {
    $("#" + selectId).select2({
        data: $.map(data, function (val, key) {
            var obj = {
                id: key,
                text: val + "[" + key + "]"
            };
            return obj;
        }),
        placeholder: value,
        dropdownParent: $("#dialog-popup")
    }).val(value).trigger('change');
}

// 表头变化
var headChangeValBondInven = {};

// 表体变化
var listChangeValBondInven = {};

// 表体ID匹配正则
var pattern = /^.*_[0-9]+$/;

// 计算表头总价值
function sumTotalBondInvent() {
    var totalPrices = 0;
    $(".detailPage input[id^=total_price]").each(function () {
        var decTotal = $(this).val();
        totalPrices = parseFloat(totalPrices) + parseFloat(decTotal);
    });
    $("#total_sum").val(parseFloat(totalPrices).toFixed(5));
    headChangeValBondInven["total_sum"] = $("#total_sum").val();
}

// 计算表体申报总价
function sumDeclTotalInvent(dVal, qty, gno, listChangeKeyVal) {
    var declTotal = parseFloat(dVal * qty).toFixed(5);
    $("#total_price_" + gno).val(declTotal);
    listChangeKeyVal["total_price"] = $("#total_price_" + gno).val();
}

function inputChangeBondInvent(id) {
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
            if (listChangeValBondInven[gno]) {
                listChangeKeyVal = listChangeValBondInven[gno];
            } else {
                listChangeKeyVal = {};
            }
            // 修改字段为单价
            if (keys == "price") {// 单价
                var dVal = parseFloat(val);
                var qty = parseFloat($("#g_qty_" + gno).val());
                sumDeclTotalInvent(dVal, qty, gno, listChangeKeyVal);
                sumTotalBondInvent();
            } else if (keys == "g_qty") {// 数量
                console.log(keys);
                var qty = parseFloat(val);
                var dVal = parseFloat($("#price_" + gno).val());
                sumDeclTotalInvent(dVal, qty, gno, listChangeKeyVal);
                sumTotalBondInvent();
            }
            // 记录变更信息
            listChangeKeyVal[keys] = val;
            listChangeKeyVal["g_no"] = gno;
            listChangeKeyVal["entryhead_guid"] = id;
            listChangeValBondInven[gno] = listChangeKeyVal;
        } else {
            headChangeValBondInven[key] = val;
        }
        console.log(headChangeValBondInven, listChangeKeyVal);
    }).focus(function () {
        clearError();
    });
}

/**
 * 保税清单详情查询
 * Created by lzy on 2018-12-26
 */
sw.page.modules["bondinvenmanage/seebondinvendetail"] = sw.page.modules["bondinvenmanage/seebondinvendetail"] || {
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
            "area_code",
            "area_name",
            "head_country",
            "net_weight",
            "gross_weight",
            "note",
            "total_sum",

            "g_num",
            "g_itemRecordNo",
            "g_itemNo",
            "g_name",
            "g_code",
            "g_model",
            "g_qty",
            "g_unit",
            "qty_1",
            "unit_1",
            "qty_2",
            "unit_2",
            "total_price"
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
        var disableField = sw.page.modules["bondinvenmanage/seebondinvendetail"].detailParam.disableField;
        for (i = 0; i < disableField.length; i++) {
            $(".detailPage input[id^=" + disableField[i] + "],select[id^=" + disableField[i] + "]").attr("disabled", "disabled");
        }
    },
    // 装载表头信息
    fillBondInvenHead: function (entryHead) {
        $("#customs_code").val(entryHead.customs_code);
        $("#port_code").val(entryHead.port_code);
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
        $("#ie_date").val(moment(entryHead.ie_date).format("YYYY-MM-DD"));
        $("#buyer_id_number").val(entryHead.buyer_id_number);
        $("#buyer_name").val(entryHead.buyer_name);
        $("#buyer_telephone").val(entryHead.buyer_telephone);
        $("#consignee_address").val(entryHead.consignee_address);
        $("#freight").val(parseFloat(entryHead.freight).toFixed(5));
        $("#agent_code").val(entryHead.agent_code);
        $("#agent_name").val(entryHead.agent_name);
        $("#area_code").val(entryHead.area_code);
        $("#area_name").val(entryHead.area_name);
        $("#net_weight").val(parseFloat(entryHead.net_weight).toFixed(5));
        $("#gross_weight").val(parseFloat(entryHead.gross_weight).toFixed(5));
        $("#total_sum").val(parseFloat(entryHead.total_prices).toFixed(5));
        $("#note").val(entryHead.note);
        selecterInitBondInven("customs_code", entryHead.customs_code, sw.dict.allCustoms);
        selecterInitBondInven("port_code", entryHead.port_code, sw.dict.allCustoms);
        selecterInitBondInven("wrap_type", entryHead.wrap_type, sw.dict.packType);
        selecterInitBondInven("traf_mode", entryHead.traf_mode, sw.dict.trafMode);
        selecterInitBondInven("head_country", entryHead.country, sw.dict.countryArea);
        // $("#customs_tax").val(isEmpty(entryHead.customs_tax) ? "" : parseFloat(entryHead.customs_tax).toFixed(5));
        // $("#value_added_tax").val(isEmpty(entryHead.value_added_tax) ? "" : parseFloat(entryHead.value_added_tax).toFixed(5));
        // $("#consumption_tax").val(isEmpty(entryHead.consumption_tax) ? "" : parseFloat(entryHead.consumption_tax).toFixed(5));
    },

    //加载表体信息
    fillBondInvenList: function (entryLists) {
        for (var i = 0; i < entryLists.length; i++) {
            var g_num = entryLists[i].g_num;
            var str =
                "<tr>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"4\" id='g_num_" + g_num + "' value='" + entryLists[i].g_num + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"30\" id='g_itemRecordNo_" + g_num + "' value='" + entryLists[i].item_record_no + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"19\" id='g_itemNo_" + g_num + "' value='" + entryLists[i].item_no + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"250\" id='g_name_" + g_num + "' value='" + entryLists[i].g_name + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"10\" id='g_code_" + g_num + "' value='" + entryLists[i].g_code + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"510\" id='g_model_" + g_num + "' value='" + entryLists[i].g_model + "' /></td>" +
                "<td ><select class=\"form-control input-sm\" style=\"width:100%\"  maxlength=\"100\" id='country_" + g_num + "' value='" + entryLists[i].country + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"19\" id='g_qty_" + g_num + "' value='" + parseFloat(entryLists[i].qty).toFixed(5) + "' /></td>" +
                "<td ><select class=\"form-control input-sm\"  style=\"width:100%\" maxlength=\"50\" id='g_unit_" + g_num + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"19\" id='qty_1_" + g_num + "' value='" + parseFloat(entryLists[i].qty1).toFixed(5) + "' /></td>" +
                "<td ><select class=\"form-control input-sm\"  style=\"width:100%\" maxlength=\"50\" id='unit_1_" + g_num + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"19\" id='qty_2_" + g_num + "' value='" + (isEmpty(entryLists[i].qty2) ? "" : parseFloat(entryLists[i].qty2).toFixed(5)) + "' /></td>" +
                "<td ><select class=\"form-control input-sm\"  style=\"width:100%\" maxlength=\"50\" id='unit_2_" + g_num + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"19\" id='price_" + g_num + "' value='" + parseFloat(entryLists[i].price).toFixed(5) + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"19\" id='total_price_" + g_num + "' value='" + parseFloat(entryLists[i].total_price).toFixed(5) + "' /></td>" +
                // "<td ><input class=\"form-control input-sm\" maxlength=\"19\" id='customs_tax_" + g_num + "' value='" + (isEmpty(entryLists[i].customs_tax) ? "" : parseFloat(entryLists[i].customs_tax).toFixed(5)) + "' /></td>" +
                // "<td ><input class=\"form-control input-sm\" maxlength=\"19\" id='value_added_tax_" + g_num + "' value='" + (isEmpty(entryLists[i].value_added_tax) ? "" : parseFloat(entryLists[i].value_added_tax).toFixed(5)) + "' /></td>" +
                // "<td ><input class=\"form-control input-sm\" maxlength=\"19\" id='consumption_tax_" + g_num + "' value='" + (isEmpty(entryLists[i].consumption_tax) ? "" : parseFloat(entryLists[i].consumption_tax).toFixed(5)) + "' /></td>" +
                "</tr>";
            $("#entryList").append(str);
            selecterInitBondInven("country_" + g_num, entryLists[i].country, sw.dict.countryArea);
            selecterInitBondInven("g_unit_" + g_num, entryLists[i].unit, sw.dict.unitCodes);
            selecterInitBondInven("unit_1_" + g_num, entryLists[i].unit1, sw.dict.unitCodes);
            selecterInitBondInven("unit_2_" + g_num, entryLists[i].unit2, sw.dict.unitCodes);
        }
    },

    // 标记问题字段
    errorMessageShow: function (vertify) {
        if (vertify) {
            var result = JSON.parse(vertify.result);
            var gno = result.g_num;
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

    // 保存清单编辑信息
    saveBondInvenInfo: function () {
        if (!this.valiFieldInventory()) {
            return;
        }
        var entryLists = new Array();
        for (var key in listChangeValBondInven) {
            entryLists.push(listChangeValBondInven[key]);
        }
        var agent_name = $("#agent_name").val();
        var entryData = {
            entryHead: headChangeValBondInven,
            entryList: entryLists,
            agent_name: agent_name
        };
        sw.ajax(this.detailParam.url, "POST", "entryJson=" + encodeURIComponent(JSON.stringify(entryData)), function (rsp) {
            if (rsp.data.result) {
                sw.page.modules["bondinvenmanage/seebondinvendetail"].cancel();
                setTimeout(function () {
                    sw.alert(rsp.data.msg, "提示", null, "modal-info");
                }, 500);
                sw.page.modules["bondinvenmanage/seebondinvendetail"].callBackQuery();
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
        headChangeValBondInven = {};
        // 表体变化
        listChangeValBondInven = {};

        //从路径上找参数
        var param = sw.getPageParams("bondinvenmanage/seebondinvendetail");
        var guid = param.guid;
        var data = {
            guid: guid
        };
        $.ajax({
            method: "GET",
            url: "/api/bondinvenmanage/seebondinvendetail",
            data: data,
            success: function (data, status, xhr) {
                if (xhr.status == 200) {
                    var entryModule = sw.page.modules["bondinvenmanage/seebondinvendetail"];

                    var entryHead = data.data.impInventoryHead;
                    var entryLists = data.data.impInventoryBodies;
                    var vertify = data.data.verify;

                    if (isNotEmpty(entryHead)) {
                        entryModule.fillBondInvenHead(entryHead);
                    }
                    if (isNotEmpty(entryLists)) {
                        entryModule.fillBondInvenList(entryLists);
                    }
                    // 根据错误字段中的值加高亮显示
                    if (entryModule.detailParam.isShowError) {
                        entryModule.errorMessageShow(vertify);
                    }
                    headChangeValBondInven["entryhead_guid"] = param.guid;
                    // 添加输入框内容变更事件，捕获数据变更信息
                    inputChangeBondInvent(param.guid);
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

            "area_code": "区内企业编号",
            "area_name": "区内企业名称",

            "head_country": "起运国（地区）",
            "net_weight": "净重",
            "gross_weight": "毛重",
            "total_sum": "商品总价"
            // "note":"备注",
        };

        // 校验表体
        var validataListField = {
            "g_num": "序号",
            "g_itemRecordNo": "备案序号",
            "g_itemNo": "账册料号",
            "g_name": "商品名称",
            "g_code": "商品编码",
            "g_model": "商品规格/型号",
            "country": "原产国（地区）",
            "g_qty": "数量",
            "g_unit": "计量单位",
            "qty_1": "第一法定数量",
            "unit_1": "第一法定单位",
            //"qty_2": "第二法定数量",
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
            if (fieldId == "net_weight") {
                var net_weight = parseFloat($("#net_weight").val());
                var gross_weight = parseFloat($("#gross_weight").val());
                if (net_weight > gross_weight) {
                    hasError("[净重]不能大于毛重");
                    return false;
                }
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

    dclEtps: function () {
        sw.ajax("api/getDclEtps", "GET", {}, function (rsp) {
            var data = rsp.data;
            for (var idx in data) {
                var dclEtpsCustomsCode = data[idx].dcl_etps_customs_code;
                var dclEtpsName = data[idx].dcl_etps_name;
                var option = $("<option>").text(dclEtpsCustomsCode).val(dclEtpsCustomsCode).attr("name", dclEtpsName);
                $("#agent_code").append(option);
            }
        })
    },

    dclEtpsName: function () {
        $("#agent_code").change(function () {
            var name = $("#agent_code option:selected").attr("name");
            $("#agent_name").text(name).val(name);
        })
    },


    init: function () {
        //从路径上获取参数
        var param = sw.getPageParams("bondinvenmanage/seebondinvendetail");
        var guid = param.guid;
        var orderNo = param.orderNo;
        var type = param.type;
        var isEdit = param.isEdit;

        this.dclEtps();
        this.dclEtpsName();

        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyy-mm-dd",
            autoclose: true
        });

        switch (type) {
            //订单查询
            case "BSQDSB": {
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
                        "total_sum",//商品总价
                        "customs_tax",
                        "value_added_tax",
                        "consumption_tax",
                        "head_country"
                    ];
                }
                //保存的路径
                this.detailParam.url = "/api/bondinvenmanage/savebondinvenbefore";
                //返回之后的查询路径
                this.detailParam.callBackUrl = "bondinvenmanage/bondinvendeclare";
                this.detailParam.isShowError = false;
                break;
            }
            case "BSQDCX": {
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
                        "total_sum",//商品总价
                        "customs_tax",
                        "value_added_tax",
                        "consumption_tax",
                        "head_country"
                    ];
                }
                //保存的路径
                this.detailParam.url = "/api/bondinvenmanage/savebondinvenafter";
                //返回之后的查询路径
                this.detailParam.callBackUrl = "bondinvenmanage/bondinvenlogicverify";
                this.detailParam.isShowError = false;
                break;
            }
            // //逻辑校验(预留)
            case "LJJY": {
                // 不可编辑状态
                if (isEdit == "true") {
                    this.detailParam.disableField = [
                        //当前禁用的字段,需要禁用的字段值在这里改
                        "cop_no",//企业内部编号
                        "invt_no",//海关清单编号
                        "pre_no",//电子口岸标识编号
                        "g_num",//表体序号
                        "total_sum",//商品总价
                        "customs_tax",
                        "value_added_tax",
                        "consumption_tax"
                    ];
                }
                //保存的路径
                this.detailParam.url = "/api/bondinven/saveLogicalDetail";
                //返回之后的查询路径
                this.detailParam.callBackUrl = "bondinvenmanage/bondinvenlogicverify";
                this.detailParam.isShowError = true;
                break;
            }

        } // 不可编辑状态
        if (isEdit == "false") {
            this.detailParam.disableField = [

                "order_no",
                "cop_no",
                "logistics_no",
                "invt_no",
                "pre_no",
                "total_sum",
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
                "area_code",
                "area_name",
                "head_country",
                "net_weight",
                "gross_weight",
                "note",

                "g_num",
                "g_itemRecordNo",
                "g_itemNo",
                "g_name",
                "g_code",
                "g_model",
                "g_qty",
                "g_unit",
                "qty_1",
                "unit_1",
                "qty_2",
                "unit_2",
                "price",
                "total_price",
                "country",

                "customs_tax",
                "value_added_tax",
                "consumption_tax"
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
            sw.page.modules["bondinvenmanage/seebondinvendetail"].saveBondInvenInfo();
        });
        //点击取消
        $("#ws-page-back").click(function () {
            sw.page.modules["bondinvenmanage/seebondinvendetail"].cancel();
        });
    }


};