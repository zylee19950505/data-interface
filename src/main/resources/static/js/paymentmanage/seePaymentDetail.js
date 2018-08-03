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

// 表头变化
var headChangeKeyVal = {};

// 表体ID匹配正则
var pattern = /^.*_[0-9]+$/;

// Select2初始化
function selecterInitPayment(selectId, value, data) {
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

function inputChangePayment(id) {
    $(".detailPage input,select").change(function () {
        var key = $(this).attr("id");
        var val = $(this).val();
        if (!isNotEmpty(val)) {
            return;
        }
        headChangeKeyVal[key] = val;
    }).focus(function () {
        clearError();
    });
}

sw.page.modules["paymentmanage/seePaymentDetail"] = sw.page.modules["paymentmanage/seePaymentDetail"] || {
    detailParam: {
        url: "",
        callBackUrl: "",
        isShowError: true,
        isEdit: "true",
        disableField: [
            "order_no",
            "pay_code",
            "pay_name",
            "pay_transaction_id",
            "ebp_code",
            "ebp_name",
            "amount_paid",
            "payer_id_type",
            "payer_id_number",
            "payer_name",
            "pay_time",
            "note"
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
        var disableField = sw.page.modules["paymentmanage/seePaymentDetail"].detailParam.disableField;
        for (i = 0; i < disableField.length; i++) {
            $(".detailPage input[id^=" + disableField[i] + "],select[id^=" + disableField[i] + "]").attr("disabled", "disabled");
        }
    },
    // 装载表头信息

    fillEntryHeadInfo: function (entryHead) {
        $("#order_no").val(entryHead.order_no);
        $("#pay_code").val(entryHead.pay_code);
        $("#pay_name").val(entryHead.pay_name);
        $("#pay_transaction_id").val(entryHead.pay_transaction_id);
        $("#ebp_code").val(entryHead.ebp_code);
        $("#ebp_name").val(entryHead.ebp_name);
        $("#amount_paid").val(entryHead.amount_paid);
        selecterInitPayment("payer_id_type",entryHead.payer_id_type,sw.dict.certificateType)
        $("#payer_id_number").val(entryHead.payer_id_number);
        $("#payer_name").val(entryHead.payer_name);
        $("#pay_time").val(moment(entryHead.pay_time).format("YYYY-MM-DD"));
        $("#note").val(entryHead.note);
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
        debugger;

        if (!this.valiField()) {
            return;
        }
        var entryData = {
            entryHead: headChangeKeyVal
        };
        sw.ajax(this.detailParam.url, "POST", "entryJson=" + encodeURIComponent(JSON.stringify(entryData)), function (rsp) {
            if (rsp.data.result) {
                sw.page.modules["paymentmanage/seePaymentDetail"].cancel();
                setTimeout(function () {
                    sw.alert(rsp.data.msg, "提示", null, "modal-info");
                }, 500);
                sw.page.modules["paymentmanage/seePaymentDetail"].callBackQuery(orderNo);
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
        var param = sw.getPageParams("paymentmanage/seePaymentDetail");
        var paytransactionid = param.paytransactionid;
        var orderNo = param.orderNo;
        var data = {
            paytransactionid: paytransactionid,
            orderNo: orderNo
        };
        $.ajax({
            method: "GET",
            url: "api/paymentmanage/querypayment/seePaymentDetail",
            data: data,
            success: function (data, status, xhr) {
                if (xhr.status == 200) {
                    var entryModule = sw.page.modules["paymentmanage/seePaymentDetail"];

                    var entryHead = data.data;
                    // var vertify = data.data.verify;

                    if (isNotEmpty(entryHead)) {
                        entryModule.fillEntryHeadInfo(entryHead);
                    }
                    // 根据错误字段中的值加高亮显示
                    // if (entryModule.detailParam.isShowError) {
                    //     entryModule.errorMessageShow(vertify);
                    // }
                    headChangeKeyVal["entryhead_guid"] = param.guid;
                    debugger;
                    // 添加输入框内容变更事件，捕获数据变更信息
                    inputChangePayment(param.guid);
                    entryModule.disabledFieldInput();
                }
            }
        });
    },
    //校验
    valiField: function () {
        // 校验表头
        var validataHeadField = {
            "order_no": "订单编号",
            "pay_code": "支付企业代码",
            "pay_name": "支付企业名称",
            "pay_transaction_id": "支付交易编码",
            "ebp_code": "电商平台代码",
            "ebp_name": "电商平台名称",
            "amount_paid": "支付金额",
            "payer_id_type": "支付人证件类型",
            "payer_id_number": "支付人证件号码",
            "payer_name": "支付人姓名",
            "pay_time": "支付时间",
            // "note": "备注"
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
        var param = sw.getPageParams("paymentmanage/seePaymentDetail");
        var paytransactionid = param.paytransactionid;
        var type = param.type;
        var orderNo = param.orderNo;
        var isEdit = param.isEdit;

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
                        "order_no",//订单编号
                        "pay_transaction_id",//支付交易编码
                        "payer_id_type"//身份证件类型
                    ];
                }
                //保存的路径
                this.detailParam.url = "/api/paymentmanage/querypayment/savePaymentDetail";
                //返回之后的查询路径
                this.detailParam.callBackUrl = "paymentmanage/paymentQuery";
                this.detailParam.isShowError = false;
                break;
            }
            //逻辑校验(预留)
            case "LJJY": {
                // 不可编辑状态

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
            sw.page.modules["paymentmanage/seePaymentDetail"].saveEntryInfo(orderNo, type, sw.ie);
        });
        //点击取消
        $("#ws-page-back").click(function () {
            sw.page.modules["paymentmanage/seePaymentDetail"].cancel();
        });
    }





}