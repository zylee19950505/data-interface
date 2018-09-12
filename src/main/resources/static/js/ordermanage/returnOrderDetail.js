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

// 表体ID匹配正则
var patternB = /^.*_[0-9]+$/;
function inputChanged(id){
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
        //console.log(headChangeKeyValB, listChangeKeyVal);
    }).focus(function () {
        clearError();
    });
}

sw.page.modules["ordermanage/returnOrderDetail"] = sw.page.modules["ordermanage/returnOrderDetail"] || {
    detailParam: {
        url: "",
        callBackUrl: "",
        isShowError: true,
        isEdit: "true",
        disableField: [
            "order_no",
            "bill_no",
            "return_info",
            "return_time",
            "return_status"
        ]
    },
    // 禁用字段
    disabledFieldInput: function () {
        var disableField = sw.page.modules["ordermanage/returnOrderDetail"].detailParam.disableField;
        for (i = 0; i < disableField.length; i++) {
            $(".detailPage input[id^=" + disableField[i] + "],select[id^=" + disableField[i] + "]").attr("disabled", "disabled");
        }
    },
    // 装载表头信息
    fillEntryHeadInfo: function (entryHead) {
        $("#bill_no").val(entryHead.bill_No);
        $("#order_no").val(entryHead.order_No);
        $("#return_status").val(entryHead.return_status);
        $("#return_time").val(entryHead.return_time);
        $("#return_info").val(entryHead.return_info);

    },
    // 查询订单详情
    query: function () {
        //从路径上找参数
        var param = sw.getPageParams("ordermanage/returnOrderDetail");
        var guid = param.guid;
        var orderNo = param.orderNo;
        var data = {
            guid : guid,
            orderNo:orderNo
        };
        $.ajax({
            method: "GET",
            url: "api/ordermanage/queryOrder/returnOrderDetail",
            data: data,
            success: function (data, status, xhr) {
                if (xhr.status == 200) {
                    var entryModule = sw.page.modules["ordermanage/returnOrderDetail"];

                    var entryHead = data.data;

                    if (isNotEmpty(entryHead)) {
                        entryModule.fillEntryHeadInfo(entryHead);
                    }
                    entryModule.disabledFieldInput();
                }
            }
        });
    },

    init: function () {
        //从路径上获取参数
        var param = sw.getPageParams("ordermanage/returnOrderDetail");
        var guid = param.guid;
        var orderNo = param.order_No;

        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyy-mm-dd",
            autoclose: true
        });

        // 查询详情
        this.query();
    }
}





