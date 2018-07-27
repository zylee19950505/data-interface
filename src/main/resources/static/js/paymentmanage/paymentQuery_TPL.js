/**
 * Created on 2017-7-23.
 * zwf
 * 支付单单条数据查询
 */

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
sw.page.modules["paymentmanage/paymentQuery_TPL"] = sw.page.modules["paymentmanage/paymentQuery_TPL"] || {
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
        var disableField = sw.page.modules["paymentmanage/paymentQuery_TPL"].detailParam.disableField;
        for (i = 0; i < disableField.length; i++) {
            $(".detailPage input[id^=" + disableField[i] + "],select[id^=" + disableField[i] + "]").attr("disabled", "disabled");
        }
    },
    // 装载表头信息

fillEntryHeadInfo: function (entryHead) {
        $("#order_No").val(entryHead.orderNo);
        $("#pay_Code").val(entryHead.payCode);
        $("#pay_name").val(entryHead.payName);
        $("#pay_transAction_id").val(entryHead.payTransactionId);
        $("#ebp_code").val(entryHead.ebpCode);
        $("#ebp_name").val(entryHead.ebpName);
        $("#amount_Paid").val(entryHead.amountPaid);
        $("#payer_Id_Type").val(entryHead.payerIdType);
        $("#payer_Id_Number").val(entryHead.payerIdNumber);
        $("#payer_Name").val(entryHead.payerName);
        $("#pay_Time").val(moment(entryHead.payTime).format("YYYY-MM-DD"));
        $("#note").val(entryHead.note);
        /* if (sw.ie === "E") {
             $("#note_s").val(entryHead.note)
         }*/
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
    // 查询订单详情
    query: function () {
        // 表头变化
        headChangeKeyValB = {};
        // 表体变化
        listChangeKeyValsB = {};

        //从路径上找参数
        var param = sw.getPageParams("paymentmanage/paymentQuery_TPL");
        var paytransactionid = param.paytransactionid;
        var orderNo = param.orderNo;
        var data = {
            paytransactionid : paytransactionid,
            orderNo:orderNo
        };
        $.ajax({
            method: "GET",
            url: "api/paymentmanage/querypayment/seePaymentDetail",
            data: data,
            success: function (data, status, xhr) {
                if (xhr.status == 200) {
                    var entryModule = sw.page.modules["paymentmanage/paymentQuery_TPL"];

                    var entryHead = data.data.paymentHead;
                    //var vertify = data.data.verify;

                    if (isNotEmpty(entryHead)) {
                        entryModule.fillEntryHeadInfo(entryHead);
                    }
                    // 根据错误字段中的值加高亮显示
                    if (entryModule.detailParam.isShowError) {
                        //entryModule.errorMessageShow(vertify);
                    }
                    // headChangeKeyValB["entryhead_id"] = param.guid;
                    // 添加输入框内容变更事件，捕获数据变更信息
                    inputChangeB(param.paytransactionid);
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
            "currency": "币制",
            "buyer_Reg_No": "平台注册号",
            "buyer_Name": "注册人姓名",
            "buyer_Id_Type": "身份证",
            "buyer_Id_Number": "证件号码",
            "pay_Code": "支付企业编号",
            "payName": "支付企业名称",
            "pay_Transaction_Id": "支付流水号",
            "batch_Numbers": "商品批次号",
            "consignee": "收货人姓名",
            "consignee_Telephone": "收货人联系电话",
            "consignee_Address": "收货地址",
            "consignee_Ditrict": "行政区",
            "note": "备注",
            "crt_id": "创建人ID",
            "crt_tm": "创建时间",
            "upd_id": "更新人ID",
            "upd_tm": "更新时间",
            "data_status": "数据状态",
            "return_status": "回执状态"
            /*   "i_e_date": "进出口时间"*/
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
    },
    init: function () {
        //从路径上获取参数
        var param = sw.getPageParams("paymentmanage/paymentQuery_TPL");
        var paytransactionid = param.paytransactionid;
        var type = param.type;
        var orderNo = param.orderNo;
        var isEdit = param.isEdit;
        /* $("#declareTime").val(declareTime);*/

        if (sw.ie === "E") {
            $("#bzTr").removeClass() //备注
        }
        if (type !== "LJJY") {
            $(".ieDateHide").remove();
        } else {
            $(".ieDate").remove();
            orderNo = param.orderNo;
        }
        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyy-mm-dd",
            autoclose: true
        });
        switch (type) {
            //支付单查询
            case "ZFDCX": {
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
            sw.page.modules["paymentmanage/paymentQuery_TPL"].cancel();
        });
    }


}
