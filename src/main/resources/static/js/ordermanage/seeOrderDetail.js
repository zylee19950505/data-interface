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
function selecterInitB(selectId, value, data) {
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
var headChangeKeyValB = {};

// 表体变化
var listChangeKeyValsB = {};

// 表体ID匹配正则
var patternB = /^.*_[0-9]+$/;

// 计算表头总价值
function sumTotalPriceB() {
    var totalPrice = 0;
    $(".detailPage input[id^=total_Price]").each(function () {
        var decTotal = $(this).val();
        totalPrice = parseFloat(totalPrice) + parseFloat(decTotal);
    });
    $("#goods_Value").val(parseFloat(totalPrice).toFixed(4));
    headChangeKeyValB["goods_Value"] = $("#goods_Value").val();
}

// 计算表体申报总价
function sumDeclTotalB(dVal, g_qty, gno, listChangeKeyVal) {
    var declTotal = parseFloat(dVal * g_qty).toFixed(4);
    $("#total_Price_" + gno).val(declTotal);
    listChangeKeyVal["total_Price"] = $("#total_Price_" + gno).val();
}

function inputChanged(id) {
    $(".detailPage input,select").change(function () {
        var key = $(this).attr("id");
        var val = $(this).val();
        if (!isNotEmpty(val)) {
            return;
        }
        if (patternB.test(key)) {
            //通过id的拼接和截取来获得g_num
            var gno = key.substring(key.lastIndexOf("_") + 1, key.length);
            var keys = key.substring(0, key.lastIndexOf("_"));
            var listChangeKeyVal;
            if (listChangeKeyValsB[gno]) {
                listChangeKeyVal = listChangeKeyValsB[gno];
            } else {
                listChangeKeyVal = {};
            }
            // 修改字段为单价
            if (keys == "price") {// 商品价格
                var dVal = parseFloat(val);
                var g_qty = parseFloat($("#qty_" + gno).val());
                sumDeclTotalB(dVal, g_qty, gno, listChangeKeyVal);

                sumTotalPriceB();
            } else if (keys == "qty") {// 商品数量
                //var g_qty = parseFloat(val).toFixed(4);
                var g_qty = parseFloat(val);
                //var dVal = parseFloat($("#decl_price_" + gno).val()).toFixed(4);
                var dVal = parseFloat($("#price_" + gno).val());
                sumDeclTotalB(dVal, g_qty, gno, listChangeKeyVal);

                sumTotalPriceB();
            }
            // 记录变更信息
            listChangeKeyVal[keys] = val;
            listChangeKeyVal["g_no"] = gno;
            listChangeKeyVal["entryhead_guid"] = id;
            listChangeKeyValsB[gno] = listChangeKeyVal;
        } else {
            headChangeKeyValB[key] = val;
        }
    }).focus(function () {
        clearError();
    });
}

sw.page.modules["ordermanage/seeOrderDetail"] = sw.page.modules["ordermanage/seeOrderDetail"] || {
    detailParam: {
        url: "",
        callBackUrl: "",
        isShowError: true,
        isEdit: "true",
        disableField: [
            "order_No",
            "bill_No",
            "goods_Value",
            "total_Price",
            "app_Type",
            "app_Time",
            "app_Status",
            "order_Type",
            "ebp_Code",
            "ebp_Name",
            "ebc_Code",
            "ebc_Name",
            "freight",
            "discount",
            "tax_Total",
            "actural_Paid",
            "currency",
            "buyer_Reg_No",
            "buyer_Name",
            "buyer_TelePhone",
            "buyer_Id_Type",
            "buyer_Id_Number",
            "pay_Code",
            "payName",
            "pay_Transaction_Id",
            "batch_Numbers",
            "consignee",
            "consignee_Telephone",
            "consignee_Address",
            "consignee_Ditrict",
            "note",
            "crt_id",
            "crt_tm",
            "upd_id",
            "upd_tm",
            "data_status",
            "return_status"
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
        var disableField = sw.page.modules["ordermanage/seeOrderDetail"].detailParam.disableField;
        for (i = 0; i < disableField.length; i++) {
            $(".detailPage input[id^=" + disableField[i] + "],select[id^=" + disableField[i] + "]").attr("disabled", "disabled");
        }
    },
    // 装载表头信息
    fillEntryHeadInfo: function (entryHead) {
        $("#guid").val(entryHead.guid);
        $("#order_No").val(entryHead.order_No);
        $("#bill_No").val(entryHead.bill_No);
        $("#goods_Value").val(parseFloat(entryHead.goods_Value).toFixed(5));
        $("#ebp_Code").val(entryHead.ebp_Code);
        $("#ebp_Name").val(entryHead.ebp_Name);
        $("#ebc_Code").val(entryHead.ebc_Code);
        $("#ebc_Name").val(entryHead.ebc_Name);
        $("#buyer_Reg_No").val(entryHead.buyer_Reg_No);
        $("#buyer_Id_Number").val(entryHead.buyer_Id_Number);
        $("#buyer_Name").val(entryHead.buyer_Name);
        $("#buyer_TelePhone").val(entryHead.buyer_TelePhone);
        $("#consignee").val(entryHead.consignee);
        $("#consignee_Telephone").val(entryHead.consignee_Telephone);
        $("#consignee_Address").val(entryHead.consignee_Address);
        $("#discount").val(parseFloat(entryHead.discount).toFixed(5));
        $("#tax_Total").val(parseFloat(entryHead.tax_Total).toFixed(5));
        $("#freight").val(parseFloat(entryHead.freight).toFixed(5));
        // selecterInitDetail("consignee_Ditrict",entryHead.consignee_Ditrict,sw.dict.countryArea);
        $("#note").val(entryHead.note);

    },

    //加载表体信息
    fillEntryListInfo: function (entryLists) {
        for (var i = 0; i < entryLists.length; i++) {
            var g_num = entryLists[i].g_num;
            var str = "<tr>" +
                "<td ><input class=\"form-control input-sm\" id='g_num_" + g_num + "' value='" + entryLists[i].g_num + "' /></td>" +//递增序号
                "<td ><input class=\"form-control input-sm\" maxlength=\"60\" id='order_No_" + g_num + "' value='" + entryLists[i].order_No + "' /></td>" +//订单编号
                "<td ><input class=\"form-control input-sm\" maxlength=\"250\" id='item_Name_" + g_num + "' value='" + entryLists[i].item_Name + "' /></td>" +//商品名称
                "<td ><input class=\"form-control input-sm\" maxlength=\"510\" id='g_Model_" + g_num + "' value='" + entryLists[i].g_Model + "' /></td>" +//商品规格型号
                "<td ><select class=\"form-control input-sm\" style=\"width:100%\"  maxlength=\"100\" id='country_" + g_num + "' value='" + entryLists[i].country + "' /></td>" +//原产国
                "<td ><input class=\"form-control input-sm\" maxlength=\"19\" id='qty_" + g_num + "' value='" + parseFloat(entryLists[i].qty).toFixed(5) + "' /></td>" +//商品数量
                "<td ><select class=\"form-control input-sm\" style=\"width:100%\" maxlength=\"50\" id='unit_" + g_num + "' value='" + entryLists[i].unit + "' /></td>" +//商品单位
                "<td ><input class=\"form-control input-sm\" maxlength=\"19\" id='price_" + g_num + "' value='" + parseFloat(entryLists[i].price).toFixed(5) + "' /></td>" +//商品单价
                "<td ><input class=\"form-control input-sm\" maxlength=\"19\" id='total_Price_" + g_num + "' value='" + parseFloat(entryLists[i].total_Price).toFixed(5) + "' /></td>" +//商品总价
                "<td ><input class=\"form-control input-sm\" maxlength=\"1000\" id='note_" + g_num + "' value='" + (isEmpty(entryLists[i].note) ? "" : entryLists[i].note) + "' /></td>" +//促销活动
                "</tr>";
            $("#entryList").append(str);
            selecterInitDetail("country_" + g_num, entryLists[i].country, sw.dict.countryArea);
            selecterInitDetail("unit_" + g_num, entryLists[i].unit, sw.dict.unitCodes);
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

    // 保存订单编辑信息
    saveEntryInfo: function (orderNo, type, ieFlag) {
        if (!this.valiField()) {
            return;
        }
        var entryLists = new Array();
        for (var key in listChangeKeyValsB) {
            entryLists.push(listChangeKeyValsB[key]);
        }

        var entryData = {
            entryHead: headChangeKeyValB,
            entryList: entryLists
        };
        sw.ajax(this.detailParam.url, "POST", "entryJson=" + encodeURIComponent(JSON.stringify(entryData)), function (rsp) {
            if (rsp.data.result) {
                sw.page.modules["ordermanage/seeOrderDetail"].cancel();
                setTimeout(function () {
                    sw.alert(rsp.data.msg, "提示", null, "modal-info");
                }, 500);
                sw.page.modules["ordermanage/seeOrderDetail"].callBackQuery(orderNo);
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
        headChangeKeyValB = {};
        // 表体变化
        listChangeKeyValsB = {};

        //从路径上找参数
        var param = sw.getPageParams("ordermanage/seeOrderDetail");
        var guid = param.guid;
        var data = {
            guid: guid
        };
        $.ajax({
            method: "GET",
            url: "api/ordermanage/queryOrder/seeOrderDetail",
            data: data,
            success: function (data, status, xhr) {
                debugger;
                if (xhr.status == 200) {
                    var entryModule = sw.page.modules["ordermanage/seeOrderDetail"];

                    var entryHead = data.data.impOrderHead;
                    var entryLists = data.data.impOrderLists;
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
                    headChangeKeyValB["entryhead_guid"] = param.guid;
                    // 添加输入框内容变更事件，捕获数据变更信息
                    inputChanged(param.guid);
                    entryModule.disabledFieldInput();
                }
            }
        });
    },

    //校验
    valiField: function () {
        // 校验表头
        var validataHeadField = {
            "bill_No": "提运单号",
            "order_No": "订单编号",
            "goods_Value": "商品总价",
            "ebp_Code": "电商平台编号",
            "ebp_Name": "电商平台名称",
            "ebc_Code": "电商编号",
            "ebc_Name": "电商名称",
            "buyer_Reg_No": "订购人注册号",
            "buyer_Id_Number": "证件号码",
            "buyer_Name": "订购人姓名",
            "buyer_TelePhone": "订购人电话",
            "consignee": "收货人姓名",
            "consignee_Address": "收货地址",
            "consignee_Telephone": "收货人电话",
            "discount": "非现金支付金额",
            "tax_Total": "代扣税款",
            "freight": "运杂费"
        };

        // 校验表体
        var validataListField = {
            "g_num": "序号",
            "order_No": "订单编号",
            "item_Name": "企业商品名称",
            "country": "原产国",
            "qty": "数量",
            "g_Model": "商品规格型号",
            "price": "单价",
            "unit": "单位",
            "total_Price": "总价"
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
                if ((!isNotEmpty(fieldVal)) || fieldVal == "null") {
                    hasError("序号[" + gno + "]-[" + validataListField[key] + "]不能为空");
                    return false;
                }
            }
        }

        return true;
    },

    init: function () {
        //从路径上获取参数
        var param = sw.getPageParams("ordermanage/seeOrderDetail");
        var guid = param.guid;
        var orderNo = param.order_No;
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
            case "DDCX": {
                // 不可编辑状态
                if (isEdit == "true") {
                    this.detailParam.disableField = [
                        //当前禁用的字段,需要禁用的字段值在这里改
                        "bill_No",
                        "order_No",//交易平台的订单编号，同一交易平台的订单编号应唯一。订单编号长度不能超过60位。
                        "goods_Value",
                        "total_Price",//商品总价，等于单价乘以数量。
                        "g_num"

                    ];
                }
                //保存的路径
                this.detailParam.url = "/api/ordermanage/queryOrder/saveOrderDetail";
                //返回之后的查询路径
                this.detailParam.callBackUrl = "ordermanage/orderQuery";
                this.detailParam.isShowError = false;
                break;
            }
            //逻辑校验(预留)
            case "LJJY": {
                if (isEdit == "true") {
                    this.detailParam.disableField = [
                        "bill_No",
                        "order_No",//交易平台的订单编号，同一交易平台的订单编号应唯一。订单编号长度不能超过60位。
                        "goods_Value",
                        "total_Price",//商品总价，等于单价乘以数量。
                        "g_num",
                        "note"
                    ]
                }
                //保存的路径
                this.detailParam.url = "/api/order/saveLogicalDetail";
                //返回之后的查询路径
                this.detailParam.callBackUrl = "ordermanage/orderLogicVerify";
                this.detailParam.isShowError = true;
                break;
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
        // 查询详情
        this.query();

        //点击保存(未确认数据)
        $("#ws-page-apply").click(function () {
            sw.page.modules["ordermanage/seeOrderDetail"].saveEntryInfo(orderNo, type, sw.ie);
        });
        //点击取消
        $("#ws-page-back").click(function () {
            sw.page.modules["ordermanage/seeOrderDetail"].cancel();
        });
    }
}





