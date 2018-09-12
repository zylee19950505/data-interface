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

sw.page.modules["detailmanage/seeInventoryRec"] = sw.page.modules["detailmanage/seeInventoryRec"] || {
    detailParam: {
        url: "",
        callBackUrl: "",
        isShowError: true,
        isEdit: "true",
        disableField: [
            "bill_no",
            "order_no",
            "logistics_no",
            "cop_no",
            "return_status",
            "return_info",
            "return_time",
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
        var disableField = sw.page.modules["detailmanage/seeInventoryRec"].detailParam.disableField;
        for (i = 0; i < disableField.length; i++) {
            $(".detailPage input[id^=" + disableField[i] + "],select[id^=" + disableField[i] + "]").attr("disabled", "disabled");
        }
    },
    // 装载表头信息

    fillInventoryRec: function (entryHead) {
        $("#bill_no").val(entryHead.bill_no);
        $("#order_no").val(entryHead.order_no);
        $("#logistics_no").val(entryHead.logistics_no);
        $("#cop_no").val(entryHead.cop_no);
        $("#return_status").val(entryHead.return_status);
        $("#return_info").val(entryHead.return_info);
        $("#return_time").val(entryHead.return_time);
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
        headChangeKeyVal = {};
        //从路径上找参数
        var param = sw.getPageParams("detailmanage/seeInventoryRec");
        var guid = param.guid;
        var data = {
            guid: guid
        };
        $.ajax({
            method: "GET",
            url: "api/detailManage/seeInventoryRec",
            data: data,
            success: function (data, status, xhr) {
                if (xhr.status == 200) {
                    var entryModule = sw.page.modules["detailmanage/seeInventoryRec"];
                    var entryHead = data.data;

                    if (isNotEmpty(entryHead)) {
                        entryModule.fillInventoryRec(entryHead);
                    }
                    headChangeKeyVal["entryhead_guid"] = param.guid;
                    // 添加输入框内容变更事件，捕获数据变更信息
                    inputChangePayment(param.guid);
                    entryModule.disabledFieldInput();
                }
            }
        });
    },

    init: function () {
        //从路径上获取参数
        var param = sw.getPageParams("detailmanage/seeInventoryRec");
        var paytransactionid = param.paytransactionid;
        var type = param.type;
        var isEdit = param.isEdit;
        var guid = param.guid;

        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyy-mm-dd",
            autoclose: true
        });
        switch (type) {
            //支付单查询
            case "QDCX": {
                // 不可编辑状态
                if (isEdit == "true") {
                    this.detailParam.disableField = [
                        //当前禁用的字段,需要禁用的字段值在这里改
                        // "bill_no",
                        // "order_no",
                        // "logistics_no",
                        // "cop_no",
                        // "return_status",
                        // "return_time"
                        // "return_info",
                    ];
                }
                //返回之后的查询路径
                this.detailParam.callBackUrl = "detailmanage/seeInventoryRec";
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

    }





}