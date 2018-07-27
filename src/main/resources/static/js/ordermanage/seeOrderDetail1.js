// 邮件查询--点击查看邮件详情
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
        // allowClear: true,
        // tags:false,
        dropdownParent: $("#dialog-popup")
    }).val(value).trigger('change');
}
function inputChangeB(id) {
    $(".detailPage input,select").change(function () {
        var key = $(this).attr("id");
        var val = $(this).val();
        if (!isNotEmpty(val)) {
            return;
        }
        if (patternB.test(key)) {
            var gno = key.substring(key.lastIndexOf("_") + 1, key.length);
            var keys = key.substring(0, key.lastIndexOf("_"));
            var listChangeKeyVal;
            if (listChangeKeyValsB[gno]) {
                listChangeKeyVal = listChangeKeyValsB[gno];
            } else {
                listChangeKeyVal = {};
            }
            // 修改字段为单价
            if (keys == "decl_price") {// 商品价格
                //var dVal = parseFloat(val).toFixed(4);
                var dVal = parseFloat(val);
                //var g_qty = parseFloat($("#g_qty_" + gno).val()).toFixed(4);
                var g_qty = parseFloat($("#g_qty_" + gno).val());
                sumDeclTotalB(dVal, g_qty, gno, listChangeKeyVal);

                sumTotalPriceB();
            } else if (keys == "g_qty") {// 商品数量
                //var g_qty = parseFloat(val).toFixed(4);
                var g_qty = parseFloat(val);
                //var dVal = parseFloat($("#decl_price_" + gno).val()).toFixed(4);
                var dVal = parseFloat($("#decl_price_" + gno).val());
                sumDeclTotalB(dVal, g_qty, gno, listChangeKeyVal);

                sumTotalPriceB();
            } else if (keys == "g_netwt") {// 商品净重
                var g_grosswt = parseFloat($("#g_grosswt_" + gno).val()).toFixed(4);
                var g_netwt = parseFloat(val).toFixed(4);
                if (g_netwt > g_grosswt) {
                    hasError("序号[" + gno + "][净重]不能大于毛重");
                    return;
                }
                // 计算及更新总净重
                var netWt = 0;
                $(".detailPage input[id^=g_netwt]").each(function () {
                    var gNetWt = $(this).val();
                    netWt = parseFloat(netWt) + parseFloat(gNetWt);
                });
                $("#net_wt").val(parseFloat(netWt).toFixed(2));
                headChangeKeyValB["net_wt"] = $("#net_wt").val();
            } else if (keys == "g_grosswt") {// 商品毛重
                var g_grosswt = parseFloat(val).toFixed(4);
                var g_netwt = parseFloat($("#g_netwt_" + gno).val()).toFixed(4);
                if (g_netwt > g_grosswt) {
                    hasError("序号[" + gno + "][毛重]不能小于净重");
                    return;
                }
                // 计算及更新总毛重
                var gross_wt = 0;
                $(".detailPage input[id^=g_grosswt]").each(function () {
                    var g_grosswt = $(this).val();
                    gross_wt = parseFloat(gross_wt) + parseFloat(g_grosswt);
                });
                $("#gross_wt").val(parseFloat(gross_wt).toFixed(2));
                headChangeKeyValB["gross_wt"] = $("#gross_wt").val();
            }
            // 记录变更信息
            listChangeKeyVal[keys] = val;
            listChangeKeyVal["g_no"] = gno;
            listChangeKeyVal["entryhead_id"] = id;
            listChangeKeyValsB[gno] = listChangeKeyVal;
        } else {
            headChangeKeyValB[key] = val;
        }
        //console.log(headChangeKeyValB, listChangeKeyVal);
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
            "app_Type",
            "app_Time",
            "app_Status",
            "order_Type",
            "order_No",
            "ebp_Code",
            "ebp_Name",
            "ebc_Code",
            "ebc_Name",
            "goods_Value",
            "freight",
            "discount",
            "tax_Total",
            "actural_Paid",
            "currency",
            "buyer_Reg_No",
            "buyer_Name",
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
    callBackQuery: function (billNo, status, type, ieFlag) {
        if (type === "LJJY" && ieFlag === "I") {
            //调到那个查询
            sw.page.modules[this.detailParam.callBackUrl].loadPreview(billNo, status);
        } else {
            sw.page.modules[this.detailParam.callBackUrl].query();
        }
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
        $("#app_Type").val(entryHead.app_Type);
        $("#app_Time").val(moment(entryHead.app_Time).format("YYYY-MM-DD"));
        $("#app_Status").val(entryHead.app_Status);
        $("#order_Type").val(entryHead.order_Type);
        $("#order_No").val(entryHead.order_No);
        $("#ebp_Code").val(entryHead.ebp_Code);
        $("#ebp_Name").val(entryHead.ebp_Name);
        $("#ebc_Code").val(entryHead.ebc_Code);
        $("#ebc_Name").val(entryHead.ebc_Name);
        $("#goods_Value").val(entryHead.goods_Value);
        $("#freight").val(entryHead.freight);
        $("#discount").val(entryHead.discount);
        $("#tax_Total").val(entryHead.tax_Total);
        $("#actural_Paid").val(entryHead.actural_Paid);
        selecterInitB("currency", entryHead.currency, sw.dict.currency);
        $("#buyer_Reg_No").val(entryHead.buyer_Reg_No);
        $("#buyer_Name").val(entryHead.buyer_Name);
        $("#buyer_Id_Type").val(entryHead.buyer_Id_Type);
        $("#buyer_Id_Number").val(entryHead.buyer_Id_Number);
        $("#pay_Code").val(entryHead.pay_Code);
        $("#payName").val(entryHead.payName);
        $("#pay_Transaction_Id").val(entryHead.pay_Transaction_Id);
        $("#batch_Numbers").val(entryHead.batch_Numbers);
        $("#consignee").val(entryHead.consignee);
        $("#consignee_Telephone").val(entryHead.consignee_Telephone);
        $("#consignee_Address").val(entryHead.consignee_Address);
        $("#consignee_Ditrict").val(entryHead.consignee_Ditrict);
        $("#note").val(entryHead.note);
        $("#crt_id").val(entryHead.crt_id);
        $("#crt_tm").val(moment(entryHead.crt_tm).format("YYYY-MM-DD"));
        $("#upd_id").val(entryHead.upd_id);
        $("#upd_tm").val(moment(entryHead.upd_tm).format("YYYY-MM-DD"));
        $("#data_status").val(entryHead.data_status);
        $("#return_status").val(entryHead.return_status);
        /* if (sw.ie === "E") {
             $("#note_s").val(entryHead.note)
         }*/
    },
  /*  // 装载表体信息
    fillEntryListInfo: function (entryLists) {
        for (var i = 0; i < entryLists.length; i++) {
            var g_num = entryLists[i].g_num;
            var str = "<tr>" +
                "<td ><input class=\"form-control input-sm\" id='g_num_" + g_num + "' value='" + entryLists[i].g_num + "' /></td>" +//递增序号
                "<td ><input class=\"form-control input-sm\" maxlength=\"255\" id='order_No" + g_num + "' value='" + entryLists[i].order_No + "' /></td>" +//订单编号
                "<td ><input class=\"form-control input-sm\" maxlength=\"16\" id='item_Name" + g_num + "' value='" + entryLists[i].item_Name + "' /></td>" +//商品名称
                "<td ><input class=\"form-control input-sm\" maxlength=\"16\" id='item_Describe" + g_num + "' value='" + entryLists[i].item_Describe + "' /></td>" +//商品描述
                "<td ><input class=\"form-control input-sm\" maxlength=\"16\" id='qty" + g_num + "' value='" + entryLists[i].qty + "' /></td>" +//商品数量
                "<td ><input class=\"form-control input-sm\" maxlength=\"16\" id='price" + g_num + "' value='" + parseFloat(entryLists[i].price).toFixed(2) + "' /></td>" +//商品单价
                "<td ><input class=\"form-control input-sm\" maxlength=\"16\" id='total_Price" + g_num + "' value='" + parseFloat(entryLists[i].total_Price).toFixed(4) + "' /></td>" +//商品总价
                "<td ><input class=\"form-control input-sm\" maxlength=\"16\" id='note" + g_num + "' value='" + entryLists[i].note + "' /></td>" +//促销活动


               /!* "<td ><input class=\"form-control input-sm\" maxlength=\"255\" id='head_guid" + g_num + "' value='" + entryLists[i].head_guid + "' /></td>" +//订单表头唯一序号
                "<td ><input class=\"form-control input-sm\" maxlength=\"16\" id='item_No" + g_num + "' value='" + entryLists[i].item_No + "' /></td>" +//商品货号
                "<td ><input class=\"form-control input-sm\" maxlength=\"16\" id='bar_Code" + g_num + "' value='" + entryLists[i].bar_Code + "' /></td>" +//条形码前缀
                "<td ><input class=\"form-control input-sm\" maxlength=\"16\" id='unit" + g_num + "' value='" + entryLists[i].unit + "' /></td>" +//海关标准参数代码
                "<td ><select class=\"form-control input-sm\" style=\"width:100%\" maxlength=\"10\" id='currency" + g_num + "' /></td>" +//填写人民币
                "<td ><input class=\"form-control input-sm\" maxlength=\"16\" id='country" + g_num + "' value='" + entryLists[i].country + "' /></td>" +//海关参数*!/
                "</tr>";

            $("#entryList").append(str);
        }
    },*/
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
            guid : guid
        };
        $.ajax({
            method: "GET",
            url: "api/ordermanage/queryOrder/seeOrderDetail",
            data: data,
            success: function (data, status, xhr) {
                if (xhr.status == 200) {
                    var entryModule = sw.page.modules["ordermanage/seeOrderDetail"];

                    var entryHead = data.data.impOrderHead;
                   // var entryLists = data.data.impOrderLists;
                    var vertify = data.data.verify;

                    if (isNotEmpty(entryHead)) {
                        entryModule.fillEntryHeadInfo(entryHead);
                    }
                  //  if (isNotEmpty(entryLists)) {
                   //     entryModule.fillEntryListInfo(entryLists);
                  //  }
                    // 根据错误字段中的值加高亮显示
                    if (entryModule.detailParam.isShowError) {
                        entryModule.errorMessageShow(vertify);
                    }
                   // headChangeKeyValB["entryhead_id"] = param.guid;
                    // 添加输入框内容变更事件，捕获数据变更信息
                    inputChangeB(param.guid);
                    entryModule.disabledFieldInput();
                }
            }
        });
    },
    //校验
    valiField: function () {
        // 校验表头
        var validataHeadField = {
            "app_Type": "企业报送类型",
            "app_Time": "企业报送时间",
            "app_Status": "业务状态",
            "order_Type": "电子订单类型",
            "order_No": "订单编号",
            "ebp_Code": "电商平台编号",
            "ebp_Name": "电商平台名称",
            "ebc_Code": "电商编号",
            "ebc_Name": "电商名称",
            "goods_Value": "实际成交价",
            "freight": "运杂费",
            "discount": "非现金支付金额",
            "tax_Total": "税款金额",
            "actural_Paid": "支付金额",
            "currency":"币制",
            "buyer_Reg_No":"平台注册号",
            "buyer_Name":"注册人姓名",
            "buyer_Id_Type":"身份证",
            "buyer_Id_Number":"证件号码",
            "pay_Code":"支付企业编号",
            "payName":"支付企业名称",
            "pay_Transaction_Id":"支付流水号",
            "batch_Numbers":"商品批次号",
            "consignee":"收货人姓名",
            "consignee_Telephone":"收货人联系电话",
            "consignee_Address":"收货地址",
            "consignee_Ditrict":"行政区",
            "note":"备注",
            "crt_id":"创建人ID",
            "crt_tm":"创建时间",
            "upd_id":"更新人ID",
            "upd_tm":"更新时间",
            "data_status":"数据状态",
            "return_status":"回执状态"
            /*   "i_e_date": "进出口时间"*/
        };

       /* // 校验表体
        var validataListField = {
            "g_num": "序号",
            "order_No": "订单编号",
            "item_No": "货号",
            "item_Name": "商品名称",
            "item_Describe": "商品详情",
            "bar_Code": "条形码",
            "unit": "计量单位",
            "qty": "实际数量",
            "price": "商品单价",
            "total_Price": "商品总价",
            "currency": "人民币",
            "country": "行政区",
            "note": "促销活动",
        };*/

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

                if (key == "g_grosswt") {// 商品毛重
                    var g_grosswt = parseFloat(fieldVal).toFixed(4);
                    var g_netwt = parseFloat($("#g_netwt_" + gno).val()).toFixed(4);
                    if (g_netwt > g_grosswt) {
                        $(fields[i]).focus();
                        hasError("序号[" + gno + "][毛重]不能小于净重");
                        return false;
                    }
                }
            }
        }

        return true;
    },
    //保存

    init: function () {
        //从路径上获取参数
        var param = sw.getPageParams("ordermanage/seeOrderDetail");
        var guid = param.guid;
        var orderNo = param.order_No;
        var type = param.type;
        var isEdit = param.isEdit;
        /* $("#declareTime").val(declareTime);*/

        if (sw.ie === "E") {
            $("#bzTr").removeClass() //备注
        }
        if (type !== "LJJY") {
            $(".ieDateHide").remove();
        } else {
            $(".ieDate").remove();
            orderNo = param.order_No;
        }
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

                        "g_num",//从1开始的递增序号。
                        "head_guid",//出口电子订单表头系统唯一序号
                        "order_No",//交易平台的订单编号，同一交易平台的订单编号应唯一。订单编号长度不能超过60位。
                        "item_No",//电商企业自定义的商品货号（SKU）。
                        "item_Name",//交易平台销售商品的中文名称。
                        "item_Describe",//交易平台销售商品的描述信息。
                        "bar_Code",//商品条形码一般由前缀部分、制造厂商代码、商品代码和校验码组成。
                        "unit",//海关标准的参数代码海关标准的参数代码《JGS-20 海关业务代码集》- 计量单位代码
                        "qty",//商品实际数量
                        "price",//商品单价。赠品单价填写为 0。
                        "total_Price",//商品总价，等于单价乘以数量。
                        "currency",//限定为人民币，填写142。
                        "country",//填写海关标准的参数代码，参照《JGS-20 海关业务代码集》- 国家（地区）代码表。
                        "note"
                    ];
                }
                //保存的路径
                this.detailParam.url = "api/entry/customs/save";
                //返回之后的查询路径
                this.detailParam.callBackUrl = "express/import_b/declaration/customs_declaration";
                this.detailParam.isShowError = false;
                break;
            }
            //逻辑校验(预留)
            case "LJJY": {
                // 不可编辑状态
             /*   if (isEdit == "true") {
                    this.detailParam.disableField = [
                        "ass_bill_no", "g_no", "gross_wt", "net_wt", "total_value", "decl_total"
                    ];
                }
                this.detailParam.url = "api/entry/save";
                if (sw.ie === "I") {
                    this.detailParam.callBackUrl = "express/import_b/logical_inspection";
                }
                if (sw.ie === "E") {
                    this.detailParam.callBackUrl = "express/export_b/logical_inspection";
                }
                this.detailParam.isShowError = true;*/
                break;
            }

        } // 不可编辑状态
        if (isEdit == "false") {
            this.detailParam.disableField = [
                "ass_bill_no",
                "owner_code",
                "district_code",
                "receive_name",
                "send_id",
                "tel",
                "receive_city",
                "wrap_type",
                "curr_code",
                "gross_wt",
                "net_wt",
                "send_country",
                "send_name",
                "send_city",
                "main_gname",
                "send_phone",
                "owner_scc",
                "agent_scc",
                "total_value",
                "g_no",
                "g_name",
                "g_model",
                "code_ts",
                "origin_country",
                "g_grosswt",
                "g_netwt",
                "g_unit",
                "g_qty",
                "decl_price",
                "decl_total"
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
            sw.page.modules["detail/entry_b_detail"].saveEntryInfo(billNo, type, sw.ie);
        });
        //点击取消
        $("#ws-page-back").click(function () {
            sw.page.modules["ordermanage/seeOrderDetail"].cancel();
        });
    }
}





